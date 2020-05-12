package edu.skku.cs.httphandler;

import com.sun.net.httpserver.Headers;
import edu.skku.cs.httphandler.core.HttpHandlerBase;
import edu.skku.cs.httphandler.core.HttpStatusCode;
import edu.skku.cs.httphandler.core.ResponseItem;

public class FixedResponseHandler extends HttpHandlerBase implements HttpStatusCode {
    public final String fixed_response;

    public FixedResponseHandler(String response) {
        this.fixed_response = response;
    }

    @Override
    public ResponseItem handler(String body, String method, Headers headers) {
        return new ResponseItem(STATUS_OK, fixed_response);
    }

    @Override
    public String getHandlerType() {
        return this.getClass().getName();
    }
}
