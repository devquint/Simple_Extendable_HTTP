package edu.skku.cs.httphandler.core;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class ResponseItem {
    public static final String ERROR_MESSAGE = "Response Error, ask to server manager";

    public final int header;
    public final byte[] response;

    public ResponseItem(int header, byte[] response){
        this.header = header;
        this.response = response;
    }

    public ResponseItem(int header, String response){
        this.header = header;
        byte[] tmpByte;
        try {
            tmpByte = response.getBytes(StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            tmpByte = ERROR_MESSAGE.getBytes();
            e.printStackTrace();
        }
        this.response = tmpByte;
    }
}
