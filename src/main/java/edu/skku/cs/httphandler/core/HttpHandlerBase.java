package edu.skku.cs.httphandler.core;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public abstract class HttpHandlerBase implements HttpHandler {

    /**
     * 핸들러 이벤트 발생 시 String으로 변환 후 호출
     *
     * @param body body가 UTF-8로 변환된 문자열
     * @param method HTTP method
     * @param headers HTTP headers
     * @return {@link ResponseItem} 객체
     */
    public abstract ResponseItem handler(String body, String method, Headers headers);

    /**
     * @return HTTP 핸들러의 타입(클래스명)
     */
    public abstract String getHandlerType();

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        byte[] readBytes = httpExchange.getRequestBody().readAllBytes();
        String readString = new String(readBytes, StandardCharsets.UTF_8.name());
        String method = httpExchange.getRequestMethod();
        Headers header = httpExchange.getRequestHeaders();
        ResponseItem response = handler(readString, method, header);
        httpExchange.sendResponseHeaders(response.header, response.response.length);
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.response);
        os.flush();
    }
}
