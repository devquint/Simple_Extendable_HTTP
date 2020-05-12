package edu.skku.cs.httphandler.core;

import com.sun.net.httpserver.Headers;
import edu.skku.cs.httphandler.ForbiddenResponseHandler;

public class HttpHandlerByMethod extends HttpHandlerBase {

    public final HttpHandlerBase postHandler;
    public final HttpHandlerBase getHandler;
    public final HttpHandlerBase putHandler;
    public final HttpHandlerBase deleteHandler;

    public HttpHandlerByMethod(HttpHandlerBase postHandler, HttpHandlerBase getHandler,
                               HttpHandlerBase putHandler, HttpHandlerBase deleteHandler) {
        this.postHandler = postHandler != null ? postHandler : new ForbiddenResponseHandler();
        this.getHandler = getHandler != null ? getHandler : new ForbiddenResponseHandler();
        this.putHandler = putHandler != null ? putHandler : new ForbiddenResponseHandler();
        this.deleteHandler = deleteHandler != null ? deleteHandler : new ForbiddenResponseHandler();
    }

    @Override
    public ResponseItem handler(String body, String method, Headers headers) {
        method = method.toLowerCase();
        switch (method) {
            case "post":
                return postHandler.handler(body, method, headers);
            case "get":
                return getHandler.handler(body, method, headers);
            case "put":
                return putHandler.handler(body, method, headers);
            case "delete":
                return deleteHandler.handler(body, method, headers);
            default:
                return new ResponseItem(404, ResponseItem.ERROR_MESSAGE);
        }
    }

    @Override
    public String getHandlerType() {
        return this.getClass().getName();
    }
}
