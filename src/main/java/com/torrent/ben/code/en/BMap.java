package com.torrent.ben.code.en;

import java.util.Map;

public class BMap implements BElement {

    private Map<BString, BElement> map;

    public BMap(Map<BString, BElement> map) {
        this.map = map;
    }

    @Override
    public String encode() {
        StringBuffer buffer = new StringBuffer();
        buffer.append('d');
        map.forEach((k, v) -> {
            buffer.append(k.encode()).append(v.encode());
        });
        return buffer.append('e').toString();
    }
}
