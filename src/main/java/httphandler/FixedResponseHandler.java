package httphandler;

import com.sun.net.httpserver.Headers;
import httphandler.core.HttpHandlerBase;
import httphandler.core.ResponseItem;

public class FixedResponseHandler extends HttpHandlerBase {
    public final String fixed_response;

    public FixedResponseHandler(String response){
        this.fixed_response = response;
    }

    @Override
    public ResponseItem handler(String body, String method, Headers headers) {
        return new ResponseItem(200, fixed_response);
    }

    @Override
    public String getHandlerType() {
        return this.getClass().getName();
    }
}
