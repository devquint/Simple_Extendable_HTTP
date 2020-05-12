package test;

import com.google.api.client.http.*;
import com.google.api.client.testing.http.HttpTesting;
import com.google.api.client.testing.http.MockHttpTransport;
import com.google.api.client.testing.http.MockLowLevelHttpRequest;
import com.google.api.client.testing.http.MockLowLevelHttpResponse;
import com.sun.net.httpserver.HttpServer;
import httphandler.FixedResponseHandler;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MainTest {
    public static final int PORT = 8080;
    public static final String HOST_IP = "";
    public static final String HOST_HTTP_HTTPS = "http://"; // https일 경우 https://
    public static final String HOST_ADDR = ""; // 별도의 주소가 있을 경우 http(s):// 이외 입력
    private static HttpServer testServer;
    private static ExecutorService httpExecutor;

    @BeforeClass
    public static void serverSetup() throws IOException {
        testServer = HttpServer.create(new InetSocketAddress(HOST_IP, PORT), 0);
        assertNotNull(testServer);
        httpExecutor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        assertNotNull(httpExecutor);
        testServer.setExecutor(httpExecutor);
        testServer.start();
    }

    public static final String TEST_FIXED_MESSAGE = "FIXed";
    public static final String TEST_FIXED_PATH = "/fixed";

    @Before
    public void registerFixedHandler() {
        testServer.createContext(TEST_FIXED_PATH, new FixedResponseHandler(TEST_FIXED_MESSAGE));
    }

    @Test
    public void FixedTest() throws IOException, URISyntaxException {
        URI uri = new URI(HOST_HTTP_HTTPS + (HOST_ADDR == "" ? "localhost" : HOST_ADDR) +
                ":" + PORT + TEST_FIXED_PATH);
        HttpUriRequest request = new HttpGet(uri);
        CloseableHttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        HttpEntity entity = httpResponse.getEntity();
        String responseMessage = EntityUtils.toString(entity, "utf-8");

        System.out.println(request.getURI() + " responded: \n" + responseMessage);
        assertEquals(TEST_FIXED_MESSAGE, responseMessage);
    }

    @After
    public void unregisterFixedHandler() {
        testServer.removeContext(TEST_FIXED_PATH);
    }

    @Ignore("Template test code for Mocking HTTP response ignored.")
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
        testServer.stop(0); // 인자는 delay
        httpExecutor.shutdown();
    }
}
