package it.polimi.ingsw.sagrada;

import it.polimi.ingsw.sagrada.network.server.protocols.application.NanoHTTPd;
import it.polimi.ingsw.sagrada.network.server.rmi.ServerRMI;
import it.polimi.ingsw.sagrada.network.server.socket.SocketServer;

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
    
    /** The Constant registry. */
    private static final Registry registry = getRegistry();
    
    /** The Constant CLASS_DIR. */
    private static final String CLASS_DIR = new File("").getAbsolutePath().replace("\\", "/") + "/bytecode/";
    
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
            initDynamicClassLoading(CLASS_DIR, JAR_DIR);
            new Thread(() -> NanoHTTPd.init(new String[]{}, CLASS_DIR)).start();
        }
        else
            LOGGER.log(Level.SEVERE, () -> "Could not initialize dynamic class loading service");
        new SocketServer();
        new ServerRMI();
    }

    /**
     * Gets the registry.
     *
     * @return the registry
     */
    private static Registry getRegistry() {
        try {
            return LocateRegistry.createRegistry(1099);
        }
        catch (RemoteException exc) {
            LOGGER.log(Level.SEVERE, exc::getMessage);
            System.exit(-1);
            return null;
        }
    }

    /**
     * Inits the dynamic class loading.
     *
     * @param destDir the dest dir
     * @param jarFile the jar file
     */
    @SuppressWarnings("Suppress init warnings")
    private static void initDynamicClassLoading(String destDir, String jarFile) {
        try (JarFile jar = new java.util.jar.JarFile(jarFile)) {
            Enumeration classEnum = jar.entries();
            while (classEnum.hasMoreElements()) {
                JarEntry file = (java.util.jar.JarEntry) classEnum.nextElement();
                if(file.getName().startsWith("it")) {
                    File f = new java.io.File(destDir + file.getName());
                    System.out.println("Extracting " + file.getName());
                    f.getParentFile().mkdirs();
                    if (file.isDirectory()) {
                        f.mkdir();
                        continue;
                    }
                    else if(!f.createNewFile())
                        LOGGER.log(Level.SEVERE, () -> "Error creating file");
                    InputStream is =  jar.getInputStream(file);
                    try(FileOutputStream fos = new java.io.FileOutputStream(f)) {
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
