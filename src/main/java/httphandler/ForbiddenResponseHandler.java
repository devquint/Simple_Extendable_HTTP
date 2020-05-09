package httphandler;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpHandler;
import httphandler.core.HttpHandlerBase;
import httphandler.core.ResponseItem;

public class ForbiddenResponseHandler extends HttpHandlerBase {

    public static final String DEFAULT_FORBIDDEN_MESSAGE = "403 Forbidden\nYou cannot access to this page";

    public final String forbiddenMessage;

    public ForbiddenResponseHandler(){
        this.forbiddenMessage = DEFAULT_FORBIDDEN_MESSAGE;
    }

    public ForbiddenResponseHandler(String forbiddenMessage){
        this.forbiddenMessage = forbiddenMessage;
    }

    @Override
    public ResponseItem handler(String body, String method, Headers headers) {
        return new ResponseItem(403, this.forbiddenMessage);
    }

    @Override
    public String getHandlerType() {
        return this.getClass().getName();
    }
}
