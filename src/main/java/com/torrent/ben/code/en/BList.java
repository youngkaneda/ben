package com.torrent.ben.code.en;

import java.util.*;

public class BList implements BElement {

    private List<BElement> elements;

    public BList(List<BElement> elements) {
        this.elements = elements;
    }

    @Override
    public String encode() {
        StringBuffer buffer = new StringBuffer().append('l');
        elements.forEach(e -> buffer.append(e.encode()));
        return buffer.append('e').toString();
    }
}
