import com.sun.net.httpserver.HttpServer;
import httphandler.FixedResponseHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class Main {
    public static final int PORT = 8080;
    public static final String HOST = "";

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(HOST, PORT), 0);
        server.createContext("/fixed", new FixedResponseHandler("It's Fixed, not broken."));
        server.setExecutor(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()));
        server.start();
    }
}
