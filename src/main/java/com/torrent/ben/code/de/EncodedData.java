package com.torrent.ben.code.de;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicInteger;

public class EncodedData {

    public Object decode(String data) {
        return data.isEmpty() ? null : this.decode(data.getBytes(StandardCharsets.UTF_8), new AtomicInteger(0));
    }

    public Object decode(byte[] data) {
        return data.length == 0 ? null : this.decode(data, new AtomicInteger(0));
    }

    private Object decode(byte[] data, AtomicInteger index) {
        try {
            if (String.valueOf((char) (data[index.get()] & 0xFF)).matches("\\b\\d+\\b"))
                return DecodeType.S.parse(data, index);
            return DecodeType.valueOf(String.valueOf((char) (data[index.get()] & 0xFF)).toUpperCase()).parse(data, index);
        } catch (Exception e) {
            throw e;
        }
    }
}
