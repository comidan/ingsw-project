package it.polimi.ingsw.sagrada.network.client.protocols.datalink.discoverlan;

import java.io.IOException;
import java.net.NetworkInterface;
import java.net.InterfaceAddress;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class DiscoverLan {
    private static class Network {
        int network;
        int mask;

        Network(int n, int m) {
            network = n;
            mask = m;
        }
    };

    // list of networks on interfaces of machine this code is being run on
    private List<Network> mDirectlyAttachedNetworks = new ArrayList<>();
    private List<InetAddress> assignedLocalIp = new ArrayList<>();

    private int addrBytesToInt(byte[] addr) {
        int addri = 0;
        for (int i = 0; i < addr.length; ++i)
            addri = (addri << 8) | ((int)addr[i] & 0xFF);
        return addri;
    }

    private void collectLocalAddresses() {
        try {
            Enumeration<NetworkInterface> nifs = NetworkInterface.getNetworkInterfaces();

            while (nifs.hasMoreElements()) {
                NetworkInterface nif = nifs.nextElement();
                if (nif.isUp()) {
                    List<InterfaceAddress> ias = nif.getInterfaceAddresses();
                    for (InterfaceAddress ia : ias) {
                        InetAddress ina = ia.getAddress();
                        if (ina instanceof Inet4Address) {
                            int addri = addrBytesToInt(ina.getAddress());
                            int mask = -1 << (32 - ia.getNetworkPrefixLength());
                            addri &= mask;
                            assignedLocalIp.add(ina);
                            mDirectlyAttachedNetworks.add(new Network(addri, mask));
                        }
                    }
                }
            }
        } catch (SocketException ex) {
            System.err.println("Socket i/o error: " + ex.getLocalizedMessage());
        }
    }

    public boolean isDirectlyAttachedAndReachable(InetAddress address) {
        int checkedAddr = addrBytesToInt(address.getAddress());
        try {
            if (!address.isReachable(1000))
                return false;
        } catch (IOException ex) {
            System.err.println("Failed to check reachability: " + ex.getLocalizedMessage());
            return false;
        }

        for (Network n : mDirectlyAttachedNetworks) {
            if ((checkedAddr & n.mask) == n.network)
                return true;
        }
        return false;
    }

    public boolean isHostReachable(InetAddress address) {
        mDirectlyAttachedNetworks.clear();
        assignedLocalIp.clear();
        collectLocalAddresses();
        for(InetAddress inetAddress : assignedLocalIp) {
            String assignedIp = inetAddress.getHostAddress();
            String ipAddress = address.getHostAddress();
            if(assignedIp.split("\\.")[2].equals(ipAddress.split("\\.")[2]))
                return true;
        }
        return false;
    }

    public DiscoverLan() {
        collectLocalAddresses();
    }

}
