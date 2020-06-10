package com.techyourchance.unittestingfundamentals.example1;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PositiveNumberValidatorTest {

    private PositiveNumberValidator SUT;

    @Before
    public void setup() {
        this.SUT = new PositiveNumberValidator();
    }

    @Test
    public void test1() {
        boolean result = this.SUT.isPositive(-1);
        assertFalse(result);
    }

    @Test
    public void test2() {
        boolean result = this.SUT.isPositive(0);
        assertThat(result, is(false));
    }

    @Test
    public void test3() {
        boolean result = this.SUT.isPositive(1);
        assertTrue(result);
    }

}