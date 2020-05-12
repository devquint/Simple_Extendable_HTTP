package edu.skku.cs.httphandler;

import com.sun.net.httpserver.Headers;
import edu.skku.cs.httphandler.core.HttpHandlerBase;
import edu.skku.cs.httphandler.core.HttpStatusCode;
import edu.skku.cs.httphandler.core.ResponseItem;

public class ForbiddenResponseHandler extends HttpHandlerBase implements HttpStatusCode {

    public static final String DEFAULT_FORBIDDEN_MESSAGE = "403 Forbidden\nYou cannot access to this page";

    public final String forbiddenMessage;

    public ForbiddenResponseHandler() {
        this.forbiddenMessage = DEFAULT_FORBIDDEN_MESSAGE;
    }

    public ForbiddenResponseHandler(String forbiddenMessage) {
        this.forbiddenMessage = forbiddenMessage;
    }

    @Override
    public ResponseItem handler(String body, String method, Headers headers) {
        return new ResponseItem(STATUS_FORBIDDEN, this.forbiddenMessage);
    }

    @Override
    public String getHandlerType() {
        return this.getClass().getName();
    }
}
