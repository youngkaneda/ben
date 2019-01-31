package com.torrent.ben.code.de;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public enum DecodeType {

    I {
        @Override
        public Object parse(char[] data, AtomicInteger index) {
            // space 'i' delimiter
            int aux = index.incrementAndGet();
            //
            StringBuffer buffer = new StringBuffer();
            while(data[aux] != 'e') {
                buffer.append(data[index.get()]);
                aux++;
            }
            index.set(++aux); // scape 'e' delimiter
            System.out.println("(i) index equals to length -> " + index);
            return Integer.valueOf(buffer.toString());
        }
    },
    S {
        @Override
        public Object parse(char[] data, AtomicInteger index) {
            int aux = index.get();
            //
            StringBuffer buffer = new StringBuffer();
            do {
                buffer.append(data[aux]);
                aux++;
            }while(String.valueOf(data[aux]).matches("\\b\\d+\\b"));
            // verify if the semicolon comes after the string length
            if(data[aux] != ':') {
                throw new RuntimeException("malformed encoded string, error trying to parse");
            }
            // scape the semi colon
            aux++;
            //
            int length = Integer.valueOf(buffer.toString());
            buffer = new StringBuffer();
            for (int i = 0; i < length; i++) {
                buffer.append(data[aux]);
                aux++;
            }
            index.set(aux);
            System.out.println("(s) index equals to length -> " + index);
            return buffer.toString();
        }
    },
    L {
        @Override
        public Object parse(char[] data, AtomicInteger index) {
            // scape 'l' delimiter
            index.set(index.get() + 1);
            //
            List<Object> list = new ArrayList<>();
            while(index.get() < data.length && data[index.get()] != 'e') {
                list.add(this.findByDelimiter(data[index.get()]).parse(data, index));
            }
            // scape 'e' delimiter;
            index.set(index.get() + 1);
            System.out.println("(l) index equals to length -> " + index);
            return list;
        }
    },
    D {
        @Override
        public Object parse(char[] data, AtomicInteger index) {
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
            System.out.println("(d) index equals to length -> " + index);
            return map;

        }
    };

    public abstract Object parse(char[] data, AtomicInteger index);

    public DecodeType findByDelimiter(char delimiter) {
        try {
            if (String.valueOf(delimiter).matches("\\b\\d+\\b"))
                return DecodeType.S;
            return DecodeType.valueOf(String.valueOf(delimiter).toUpperCase());
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }
}
