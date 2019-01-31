package com.torrent.ben.code.de;

import java.util.concurrent.atomic.AtomicInteger;

public class BDecoder {

    public Object decode(String data) {
        return data.isEmpty() ? null : this.decode(data.toCharArray(), new AtomicInteger(0));
    }

    private Object decode(char[] data, AtomicInteger index) {
        try {
            if (String.valueOf(data[index.get()]).matches("\\b\\d+\\b"))
                return DecodeType.S.parse(data, index);
            return DecodeType.valueOf(String.valueOf(data[index.get()]).toUpperCase()).parse(data, index);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(String.format("error trying to parse, theres no valid '%s' delimiter", data[index.get()]));
        }
    }
}
