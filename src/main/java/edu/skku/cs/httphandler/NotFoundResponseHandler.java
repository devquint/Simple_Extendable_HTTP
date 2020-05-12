package edu.skku.cs.httphandler;

import com.sun.net.httpserver.Headers;
import edu.skku.cs.httphandler.core.HttpHandlerBase;
import edu.skku.cs.httphandler.core.HttpStatusCode;
import edu.skku.cs.httphandler.core.ResponseItem;

public class NotFoundResponseHandler extends HttpHandlerBase implements HttpStatusCode {

    public static final String DEFAULT_NOT_FOUND_MESSAGE = "404 Not Found\nPage Not Found.";

    public final String notFoundMessage;

    public NotFoundResponseHandler() {
        this.notFoundMessage = DEFAULT_NOT_FOUND_MESSAGE;
    }

    public NotFoundResponseHandler(String notFoundMessage) {
        this.notFoundMessage = notFoundMessage;
    }

    @Override
    public ResponseItem handler(String body, String method, Headers headers) {
        return new ResponseItem(STATUS_NOT_FOUND, this.notFoundMessage);
    }

    @Override
    public String getHandlerType() {
        return this.getClass().getName();
    }
}
