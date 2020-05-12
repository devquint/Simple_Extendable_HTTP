package edu.skku.cs;

import edu.skku.cs.httphandler.FixedResponseHandler;
import edu.skku.cs.httphandler.ForbiddenResponseHandler;
import edu.skku.cs.httphandler.core.HttpHandlerByMethod;

import java.io.IOException;

public class Main {
    public static final int PORT = 8080;
    public static final String HOST = "";
    public static HttpServerManager serverManager;

    public static void main(String[] args) throws IOException {
        serverManager = new HttpServerManager(HOST, PORT);

        serverManager.registerContext("/fixed", new FixedResponseHandler("It's Fixed, not broken anymore."));
        serverManager.registerContext("/onlyput", new HttpHandlerByMethod(
                new ForbiddenResponseHandler(), new ForbiddenResponseHandler(),
                new FixedResponseHandler("Hi, put."), new ForbiddenResponseHandler()));

        serverManager.start();
    }
}
