package com.torrent.ben;

import com.torrent.ben.code.de.BDecoder;

public class Loader {

    public static void main(String[] args) throws Throwable {
//        BElement bElement = new BList(Arrays.asList(new BString("as"), new BInteger(5)));
//        Map<BString, BElement> map = new HashMap<>();
//        map.put(new BString("asas"), new BInteger(-54));
//        BElement bElement = new BMap(map);
//        System.out.println(bElement.encode());
        BDecoder decoder = new BDecoder();
        System.out.println(decoder.decode("d9:publisher3:bob17:publisher-webpage15:www.example.com18:publisher.location4:homee"));
    }
}
