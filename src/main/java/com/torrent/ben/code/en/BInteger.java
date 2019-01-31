package com.torrent.ben.code.en;

public class BInteger implements BElement {

    private Integer value;

    public BInteger(Integer value) {
        this.value = value;
    }

    @Override
    public String encode() {
        return new StringBuffer().append('i').append(value).append('e').toString();
    }
}
