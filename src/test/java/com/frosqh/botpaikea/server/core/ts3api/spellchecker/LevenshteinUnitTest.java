package com.frosqh.botpaikea.server.core.ts3api.spellchecker;

import org.junit.Assert;
import org.junit.Test;

public class LevenshteinUnitTest {

    @Test
    public void sameWordTest(){
        String word = "Paikea";
        Assert.assertEquals(LevenshteinDistance.getDistance(word,word),0);
    }

    @Test
    public void oneSwapLetterTest(){
        String base = "whale";
        String swapped = "whele";
        Assert.assertEquals(LevenshteinDistance.getDistance(base,swapped),1);
    }
}
