package com.example.nextstep;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

public class SplitTest {
    @Test
    public void split() {
        assertArrayEquals(new String[] {"1"}, "1".split(","));
        assertArrayEquals(new String[] {"1","2"}, "1,2".split(","));
    }
}
