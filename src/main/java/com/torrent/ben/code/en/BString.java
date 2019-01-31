package com.torrent.ben.code.en;

public class BString implements BElement {

    private String value;

    public BString(String value) {
        this.value = value;
    }

    @Override
    public String encode() {
        return new StringBuffer().append(value.length()).append(':').append(value).toString();
    }
}
