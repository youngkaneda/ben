package com.torrent.ben.code;

import com.torrent.ben.code.de.BDecoder;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class DecodeTest {

    private BDecoder decoder;
    {
        this.decoder = new BDecoder();
    }

    // number tests
    @Test
    public void decodeInt() {
        Object decoded = this.decoder.decode("i-3e");
        Assert.assertEquals(decoded.toString(), "-3");
    }

    @Test(expected = NumberFormatException.class)
    public void decodeMalformedInt() {
        this.decoder.decode("i4f5e");
    }

    @Test(expected = NumberFormatException.class)
    public void decodeEmptyInt() {
        this.decoder.decode("ie");
    }

    // string tests
    @Test
    public void decodeString() {
        Object decoded = this.decoder.decode("5:aberf");
        Assert.assertEquals(decoded.toString(), "aberf");
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void decodeMalformedString() {
        // length > string.length
        Object decoded = this.decoder.decode("6:aberf");
    }

    @Test
    public void decodeEmptyString() {
        Object decoded = this.decoder.decode("0:");
        Assert.assertEquals(decoded.toString(), "");
    }

    // list tests
    @Test
    public void decodeList() {
        Object decoded = this.decoder.decode("li5e3:abce");
        Assert.assertEquals(decoded.toString(), "[5, abc]");
    }

    @Test
    public void decodeEmptyList() {
        Object decoded = this.decoder.decode("le");
        Assert.assertEquals(decoded.toString(), "[]");
    }

    // dict tests
    @Test
    public void decodeDict() {
        Object decoded = this.decoder.decode("d4:name6:kanedae");
        Assert.assertEquals(decoded.toString(), "{name=kaneda}");
    }

    @Test
    public void decodeEmptyDict() {
        Object decoded = this.decoder.decode("de");
        Assert.assertEquals(decoded.toString(), "{}");
    }

    // other tests
    @Test
    public void decodeEmptyData() {
        Object decoded = this.decoder.decode("");
        Assert.assertEquals(decoded, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void decodeInvalidDelimiter() {
        this.decoder.decode("r4e");
    }

    @Test
    public void decodeFile() throws IOException {
        File file = new File(getClass().getClassLoader().getResource("alice.torrent").getFile());
        byte[] bytes = Files.readAllBytes(Paths.get(file.toURI()));
        Map<String, Object> map = (Map<String, Object>) this.decoder.decode(bytes);
    }
}
