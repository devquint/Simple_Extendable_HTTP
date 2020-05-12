package edu.skku.cs;

import com.sun.net.httpserver.HttpServer;
import edu.skku.cs.httphandler.core.HttpHandlerBase;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServerManager {
    private final String hostIp;
    private final int port;
    private final HttpServer server;
    private final ExecutorService httpExecutor;

    /**
     * HashMap<path, handler> 경로 -> 핸들러
     */
    private final HashMap<String, HttpHandlerBase> contexts = new HashMap<>();

    public HttpServerManager(String hostIp, int port) throws IOException {
        this.hostIp = hostIp;
        this.port = port;
        this.server = HttpServer.create(new InetSocketAddress(hostIp, port), 0);
        this.httpExecutor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        server.setExecutor(httpExecutor);
    }

    public void start() {
        server.start();
    }

    public void stop() {
        server.stop(0); // 인자는 delay
        httpExecutor.shutdown();
    }

    public boolean registerContext(String path, HttpHandlerBase handler) {
        if (!path.startsWith("/")) {
            System.out.println("Registering " + path + " failed. Path should starts with '/'");
            return false;
        }
        if (contexts.containsKey(path)) {
            System.out.println("Registering " + path + " failed. Already " + path + " exists. Try after delete.");
            return false;
        }

        contexts.put(path, handler);
        server.createContext(path, handler);
        return true;
    }

    public boolean unregisterContext(String path) {
        if (!path.startsWith("/")) {
            System.out.println("Unregistering " + path + " failed. Path should starts with '/'");
            return false;
        }
        if (!contexts.containsKey(path)) {
            System.out.println("Unregistering " + path + " failed. Path " + path + " not exists in context");
            return false;
        }

        contexts.remove(path);
        server.removeContext(path);
        return true;
    }
}
