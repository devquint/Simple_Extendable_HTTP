package edu.skku.cs.test;

import com.google.api.client.http.*;
import com.google.api.client.testing.http.HttpTesting;
import com.google.api.client.testing.http.MockHttpTransport;
import com.google.api.client.testing.http.MockLowLevelHttpRequest;
import com.google.api.client.testing.http.MockLowLevelHttpResponse;
import com.sun.net.httpserver.HttpServer;
import edu.skku.cs.HttpServerManager;
import edu.skku.cs.httphandler.FixedResponseHandler;
import edu.skku.cs.httphandler.ForbiddenResponseHandler;
import edu.skku.cs.httphandler.core.HttpHandlerByMethod;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.*;

import javax.swing.text.html.parser.Entity;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.Executors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MainTest {
    public static final int PORT = 8080;
    public static final String HOST_IP = "";
    public static final String HOST_HTTP_HTTPS = "http://"; // https일 경우 https://
    public static final String HOST_ADDR = ""; // 별도의 주소가 있을 경우 http(s):// 이외 입력
    public static HttpServerManager serverManager;

    @BeforeClass
    public static void serverSetup() throws IOException {
        serverManager = new HttpServerManager(HOST_IP, PORT);
        assertNotNull(serverManager);
        serverManager.start();
    }

    public static final String TEST_FIXED_MESSAGE = "FIXed";
    public static final String TEST_FIXED_PATH = "/fixed";

    @Before
    public void registerFixedHandler() {
        serverManager.registerContext(TEST_FIXED_PATH, new FixedResponseHandler(TEST_FIXED_MESSAGE));
    }

    @Test
    public void FixedTest() throws IOException, URISyntaxException {
        URI uri = new URI(HOST_HTTP_HTTPS + (HOST_ADDR == "" ? "localhost" : HOST_ADDR) +
                ":" + PORT + TEST_FIXED_PATH);
        HttpUriRequest request = new HttpGet(uri);
        CloseableHttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        HttpEntity entity = httpResponse.getEntity();
        String responseMessage = EntityUtils.toString(entity, "utf-8");

        System.out.println(request.getURI() + " responded " + statusCode + ": \n" + responseMessage);
        assertEquals(TEST_FIXED_MESSAGE, responseMessage);
    }

    @After
    public void unregisterFixedHandler() {
        serverManager.unregisterContext(TEST_FIXED_PATH);
    }


    public static final String TEST_PUT_ONLY_PUT_MESSAGE = "Oh, you are put";
    public static final String TEST_PUT_ONLY_FORIBDDEN_MESSAGE = "Oops. you are not put";
    public static final String TEST_PUT_ONLY_PATH = "/put_only";

    @Before
    public void registerPutOnlyHandler() {
        serverManager.registerContext(TEST_PUT_ONLY_PATH, new HttpHandlerByMethod(
                new ForbiddenResponseHandler(TEST_PUT_ONLY_FORIBDDEN_MESSAGE),
                new ForbiddenResponseHandler(TEST_PUT_ONLY_FORIBDDEN_MESSAGE),
                new FixedResponseHandler(TEST_PUT_ONLY_PUT_MESSAGE),
                new ForbiddenResponseHandler(TEST_PUT_ONLY_FORIBDDEN_MESSAGE)));
    }

    @Test
    public void PutOnlyTest() throws IOException, URISyntaxException {
        URI uri = new URI(HOST_HTTP_HTTPS + (HOST_ADDR == "" ? "localhost" : HOST_ADDR) +
                ":" + PORT + TEST_PUT_ONLY_PATH);

        String[] methods = {"get", "put", "post", "delete"};
        for(String method: methods){
            method = method.toLowerCase();

            HttpUriRequest request;
            switch (method){
                case "get":
                    request = new HttpGet(uri);
                    break;
                case "put":
                    request = new HttpPut(uri);
                    break;
                case "post":
                    request = new HttpPost(uri);
                    break;
                case "delete":
                    request = new HttpDelete(uri);
                    break;
                default:
                    request = null;
                    System.out.println("Something went wrong in PutOnlyTest:for-loop");
                    break;
            }
            assert request != null;

            CloseableHttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            HttpEntity entity = httpResponse.getEntity();
            String responseMessage = EntityUtils.toString(entity, "utf-8");

            System.out.println(method + " request " + request.getURI() + " responded " +
                     statusCode + ": \n" + responseMessage);

            String expectedMessage = method.equals("put") ? TEST_PUT_ONLY_PUT_MESSAGE : TEST_PUT_ONLY_FORIBDDEN_MESSAGE;
            assertEquals(expectedMessage, responseMessage);
        }

   }

    @After
    public void unregisterPutOnlyHandler() {
        serverManager.unregisterContext(TEST_PUT_ONLY_PATH);
    }


    @Ignore("Template edu.skku.cs.test code for Mocking HTTP response ignored.")
    @Test
    public void MockingTestTemplate() throws IOException {
        HttpTransport transport =
                new MockHttpTransport() {
                    @Override
                    public LowLevelHttpRequest buildRequest(String method, String url) throws IOException {
                        return new MockLowLevelHttpRequest() {
                            @Override
                            public LowLevelHttpResponse execute() throws IOException {
                                MockLowLevelHttpResponse result = new MockLowLevelHttpResponse();
                                result.setContent(TEST_FIXED_MESSAGE);
                                return result;
                            }
                        };
                    }
                };
        GenericUrl fixedGenericUrl = HttpTesting.SIMPLE_GENERIC_URL;
        HttpRequest request = transport.createRequestFactory().buildGetRequest(fixedGenericUrl);
        HttpResponse response = request.execute();
        assertEquals(TEST_FIXED_MESSAGE, response.parseAsString());
    }

    @AfterClass
    public static void serverClose() {
        serverManager.stop();
    }
}
