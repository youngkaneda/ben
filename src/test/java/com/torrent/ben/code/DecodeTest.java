package com.torrent.ben.code;

import com.torrent.ben.code.de.EncodedData;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class DecodeTest {

    private EncodedData encodedData;
    {
        this.encodedData = new EncodedData();
    }

    // number tests
    @Test
    public void decodeInt() {
        Object decoded = this.encodedData.decode("i-3e");
        Assert.assertEquals(decoded.toString(), "-3");
    }

    @Test(expected = NumberFormatException.class)
    public void decodeMalformedInt() {
        this.encodedData.decode("i4f5e");
    }

    @Test(expected = NumberFormatException.class)
    public void decodeEmptyInt() {
        this.encodedData.decode("ie");
    }

    // string tests
    @Test
    public void decodeString() {
        Object decoded = this.encodedData.decode("5:aberf");
        Assert.assertEquals(decoded.toString(), "aberf");
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void decodeMalformedString() {
        // length > string.length
        Object decoded = this.encodedData.decode("6:aberf");
    }

    @Test
    public void decodeEmptyString() {
        Object decoded = this.encodedData.decode("0:");
        Assert.assertEquals(decoded.toString(), "");
    }

    // list tests
    @Test
    public void decodeList() {
        Object decoded = this.encodedData.decode("li5e3:abce");
        Assert.assertEquals(decoded.toString(), "[5, abc]");
    }

    @Test
    public void decodeEmptyList() {
        Object decoded = this.encodedData.decode("le");
        Assert.assertEquals(decoded.toString(), "[]");
    }

    // dict tests
    @Test
    public void decodeDict() {
        Object decoded = this.encodedData.decode("d4:name6:kanedae");
        Assert.assertEquals(decoded.toString(), "{name=kaneda}");
    }

    @Test
    public void decodeEmptyDict() {
        Object decoded = this.encodedData.decode("de");
        Assert.assertEquals(decoded.toString(), "{}");
    }

    // other tests
    @Test
    public void decodeEmptyData() {
        Object decoded = this.encodedData.decode("");
        Assert.assertEquals(decoded, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void decodeInvalidDelimiter() {
        this.encodedData.decode("r4e");
    }

    @Test
    public void decodeFile() throws IOException {
        File file = new File(getClass().getClassLoader().getResource("the-hobbit.torrent").getFile());
        byte[] bytes = Files.readAllBytes(Paths.get(file.toURI()));
        Map<String, Object> map = (Map<String, Object>) this.encodedData.decode(bytes);
    }
}
