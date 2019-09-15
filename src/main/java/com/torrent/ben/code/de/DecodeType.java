package com.torrent.ben.code.de;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public enum DecodeType {

    I {
        @Override
        public Object parse(byte[] data, AtomicInteger index) {
            // space 'i' delimiter
            int aux = index.incrementAndGet();
            //
            StringBuffer buffer = new StringBuffer();
            while(convertByteToChar(data[aux]) != 'e') {
                buffer.append(convertByteToChar(data[aux]));
                aux++;
            }
            index.set(++aux); // scape 'e' delimiter
            return Long.valueOf(buffer.toString());
        }
    },
    S {
        @Override
        public Object parse(byte[] data, AtomicInteger index) {
            int aux = index.get();
            //
            StringBuffer buffer = new StringBuffer();
            do {
                buffer.append(convertByteToChar(data[aux]));
                aux++;
            }while(String.valueOf(convertByteToChar(data[aux])).matches("\\b\\d+\\b"));
            // verify if the semicolon comes after the string length
            if(convertByteToChar(data[aux]) != ':') {
                throw new RuntimeException(String.format("malformed encoded string, error trying to parse -> %s", convertByteToChar(data[aux])));
            }
            // scape the semi colon
            aux++;
            //
            int length = Integer.valueOf(buffer.toString());
            buffer = new StringBuffer();
            for (int i = 0; i < length; i++) {
                buffer.append(convertByteToChar(data[aux]));
                aux++;
            }
            index.set(aux);
            return buffer.toString();
        }
    },
    L {
        @Override
        public Object parse(byte[] data, AtomicInteger index) {
            // scape 'l' delimiter
            index.set(index.get() + 1);
            //
            List<Object> list = new ArrayList<>();
            while(index.get() < data.length && data[index.get()] != 'e') {
                list.add(this.findByDelimiter(data[index.get()]).parse(data, index));
            }
            // scape 'e' delimiter;
            index.set(index.get() + 1);
            return list;
        }
    },
    D {
        @Override
        public Object parse(byte[] data, AtomicInteger index) {
            // scape 'd' delimiter;
            index.set(index.get() + 1);
            //
            Map<Object, Object> map = new HashMap<>();
            Object key;
            Object value;
            while(index.get() < data.length && data[index.get()] != 'e') {
                key = this.findByDelimiter(data[index.get()]).parse(data, index);
                value = this.findByDelimiter(data[index.get()]).parse(data, index);
                map.put(key, value);
            }
            // scape 'e' delimiter
            index.set(index.get() + 1);
            return map;

        }
    };

    public abstract Object parse(byte[] data, AtomicInteger index);

    public DecodeType findByDelimiter(byte delimiter) {
        try {
            if (String.valueOf(convertByteToChar(delimiter)).matches("\\b\\d+\\b"))
                return DecodeType.S;
            return DecodeType.valueOf(String.valueOf(convertByteToChar(delimiter)).toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(String.format("error trying to parse, theres no valid '%s' delimiter", convertByteToChar(delimiter)));
        }
    }

    public char convertByteToChar(byte b) {
        return (char)(b & 0xFF);
    }
}
