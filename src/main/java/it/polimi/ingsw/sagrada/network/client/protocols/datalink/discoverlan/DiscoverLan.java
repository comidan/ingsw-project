package it.polimi.ingsw.sagrada.network.client.protocols.datalink.discoverlan;

import org.apache.commons.lang3.SystemUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.NetworkInterface;
import java.net.InterfaceAddress;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Logger;


/**
 * The Class DiscoverLan.
 */
public class DiscoverLan {
    
    /**
     * The Class Network.
     */
    private static class Network {
        
        /** The network. */
        int network;
        
        /** The mask. */
        int mask;

        /**
         * Instantiates a new network.
         *
         * @param n the n
         * @param m the m
         */
        Network(int n, int m) {
            network = n;
            mask = m;
        }
    };

    private static final Logger LOGGER = Logger.getLogger(DiscoverLan.class.getName());

    /** The m directly attached networks. */
    // list of networks on interfaces of machine this code is being run on
    private List<Network> mDirectlyAttachedNetworks = new ArrayList<>();
    
    /** The assigned local ip. */
    private List<InetAddress> assignedLocalIp = new ArrayList<>();

    /**
     * Addr bytes to int.
     *
     * @param addr the addr
     * @return the int
     */
    private int addrBytesToInt(byte[] addr) {
        int addri = 0;
        for (int i = 0; i < addr.length; ++i)
            addri = (addri << 8) | ((int)addr[i] & 0xFF);
        return addri;
    }

    /**
     * Collect local addresses.
     */
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

    /**
     * Checks if is directly attached and reachable.
     *
     * @param address the address
     * @return true, if is directly attached and reachable
     */
    public boolean isDirectlyAttachedAndReachable(InetAddress address) {
        int checkedAddr = addrBytesToInt(address.getAddress());
        try {
            if (!address.isReachable(3000))
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

    /**
     * Checks if is host theoretically reachable.
     *
     * @param address the address
     * @return true, if is host reachable
     */
    public boolean isHostDirectlyReachable(InetAddress address) {
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

    /**
     * Checks if is host reachable.
     *
     * @param address the address
     * @return true, if is host reachable
     */
    public boolean isHostReachable(InetAddress address) {
        try {
            Process p;
            if(SystemUtils.IS_OS_WINDOWS) {
                p = Runtime.getRuntime().exec("ping -n 1 " + address.getHostAddress());
                BufferedReader stdInput = new BufferedReader(new
                        InputStreamReader(p.getInputStream()));
                String s;
                StringBuilder output = new StringBuilder();
                while ((s = stdInput.readLine()) != null) {
                    output.append(s);
                }
                return output.toString().contains("TTL");
            }
            else
                p = Runtime.getRuntime().exec("ping -c 1 " + address.getHostAddress());
            return p.waitFor() == 0;
        }
        catch (IOException | InterruptedException exc) {
            return false;
        }
    }


    /**
     * Instantiates a new discover lan.
     */
    public DiscoverLan() {
        collectLocalAddresses();
    }

}
