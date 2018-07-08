package it.polimi.ingsw.sagrada;

import it.polimi.ingsw.sagrada.network.server.protocols.application.NanoHTTPd;
import it.polimi.ingsw.sagrada.network.server.rmi.ServerRMI;
import it.polimi.ingsw.sagrada.network.server.socket.SocketServer;
import it.polimi.ingsw.sagrada.network.server.tools.PortDiscovery;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Enumeration;
import java.util.concurrent.ExecutionException;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 * The Class GameServer.
 */
public class GameServer {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(GameServer.class.getName());

    /** The rmi port. */
    private static int rmiPort = 1099;

    /** The Constant registry. */
    private static final Registry registry = getRegistry();
    
    /** The Constant CLASS_DIR. */
    private static final String CLASS_DIR = new File("").getAbsolutePath().replace("\\", "/") + "/bytecode/";

    /** The Constant RES_DIR. */
    private static final String RES_DIR = new File("").getAbsolutePath().replace("\\", "/") + "/resource/";
    
    /** The Constant JAR_DIR. */
    private static final String JAR_DIR = new File("").getAbsolutePath().replace("\\", "/") + "/" + getJarName();

    /**
     * The main method.
     *
     * @param args the arguments
     * @throws InterruptedException the interrupted exception
     * @throws ExecutionException the execution exception
     * @throws SocketException the socket exception
     * @throws RemoteException the remote exception
     */
    public static void main(String[] args) throws InterruptedException, ExecutionException, SocketException, RemoteException {
        if(new File(JAR_DIR).isFile()) {
            initDynamicClassLoading();
            initResourceFiles();
            new Thread(() -> NanoHTTPd.init(new String[]{}, CLASS_DIR)).start();
        }
        else
            LOGGER.log(Level.SEVERE, () -> "Could not initialize dynamic class loading service");
        new SocketServer();
        new ServerRMI(rmiPort);
    }

    /**
     * Gets the registry.
     *
     * @return the registry
     */
    private static Registry getRegistry() {
        try {
            return LocateRegistry.createRegistry(rmiPort);
        }
        catch (RemoteException exc) {
            rmiPort = new PortDiscovery().obtainAvailableTCPPort();
            return getRegistry(rmiPort);
        }
    }

    /**
     * Gets the registry.
     *
     * @param port binding rmi port
     * @return the registry
     */
    private static Registry getRegistry(int port) {
        try {
            return LocateRegistry.createRegistry(port);
        }
        catch (RemoteException exc) {
            LOGGER.log(Level.SEVERE, exc::getMessage);
            System.exit(-1);
            return null;
        }
    }

    /**
     * Init dynamic class loading.
     */
    @SuppressWarnings("Suppress init warnings")
    private static void initDynamicClassLoading() {
        try (JarFile jar = new JarFile(JAR_DIR)) {
            Enumeration classEnum = jar.entries();
            while (classEnum.hasMoreElements()) {
                JarEntry file = (JarEntry) classEnum.nextElement();
                if(file.getName().startsWith("it")) {
                    File f = new File(CLASS_DIR + file.getName());
                    Logger.getLogger(GameServer.class.getName()).log(Level.INFO, () ->"Extracting " + file.getName());
                    f.getParentFile().mkdirs();
                    if (file.isDirectory()) {
                        f.mkdir();
                        continue;
                    }
                    else if(!f.exists() && !f.createNewFile())
                        LOGGER.log(Level.SEVERE, () -> "Error creating file");
                    InputStream is =  jar.getInputStream(file);
                    try(FileOutputStream fos = new FileOutputStream(f)) {
                        while (is.available() > 0)
                            fos.write(is.read());
                    }
                    is.close();
                }
            }
        }
        catch (IOException exc) {
            LOGGER.log(Level.SEVERE, exc::getMessage);
        }
    }

    /**
     * Init resource files.
     */
    @SuppressWarnings("Suppress init warnings")
    private static void initResourceFiles() {
        try (JarFile jar = new JarFile(JAR_DIR)) {
            Enumeration classEnum = jar.entries();
            while (classEnum.hasMoreElements()) {
                JarEntry file = (JarEntry) classEnum.nextElement();
                if(file.getName().startsWith("database") || file.getName().startsWith("json/config")) {
                    File f = new File(RES_DIR + file.getName());
                    Logger.getLogger(GameServer.class.getName()).log(Level.INFO, () ->"Extracting " + file.getName());
                    f.getParentFile().mkdirs();
                    if (file.isDirectory()) {
                        f.mkdir();
                        continue;
                    }
                    else if(!f.exists() && !f.createNewFile())
                        LOGGER.log(Level.SEVERE, () -> "Error creating file");
                    InputStream is =  jar.getInputStream(file);
                    try(FileOutputStream fos = new FileOutputStream(f)) {
                        while (is.available() > 0)
                            fos.write(is.read());
                    }
                    is.close();
                }
            }
        }
        catch (IOException exc) {
            LOGGER.log(Level.SEVERE, exc::getMessage);
        }
    }

    /**
     * Gets the jar name.
     *
     * @return the jar name
     */
    private static String getJarName() {
        return new File(GameServer.class.getProtectionDomain()
                                        .getCodeSource()
                                        .getLocation()
                                        .getPath())
                                        .getName();
    }
}
