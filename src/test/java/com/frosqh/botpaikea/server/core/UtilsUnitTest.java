package com.frosqh.botpaikea.server.core;

import org.junit.Assert;
import org.junit.Test;

public class UtilsUnitTest {
    @Test
    public void isIntegerZeroTest(){
        Assert.assertTrue(Utils.isInteger("0"));
    }

    @Test
    public void isIntegerClassicTest(){
        Assert.assertTrue(Utils.isInteger("42"));
    }

    @Test
    public void isIntegerNegativeTest(){
        Assert.assertTrue(Utils.isInteger("-1998"));
    }

    @Test
    public void isIntegerDoubleTest(){
        Assert.assertFalse(Utils.isInteger("01.5"));
    }

    @Test
    public void isIntegerStringTest(){
        Assert.assertFalse(Utils.isInteger("This is a test"));
    }

    @Test
    public void isIntegerNegativeDoubleTest(){
        Assert.assertFalse(Utils.isInteger("-5.3"));
    }
}
