import com.sun.net.httpserver.HttpServer;
import httphandler.FixedResponseHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static final int PORT = 8080;
    public static final String HOST = "";
    public static HttpServerManager serverManager;

    public static void main(String[] args) throws IOException {
        serverManager = new HttpServerManager(HOST, PORT);

        serverManager.registerContext("/fixed", new FixedResponseHandler("It's Fixed, not broken anymore."));

        serverManager.start();
    }
}
