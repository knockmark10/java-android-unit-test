package com.techyourchance.unittestingfundamentals.example2;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class StringReverserTest {

    StringReverser SUT;

    @Before
    public void setup() throws Exception {
        SUT = new StringReverser();
    }

    @Test
    public void reverse_emptyString_emptyStringReturned() {
        String result = this.SUT.reverse("");
        assertThat(result, is(""));
    }

    @Test
    public void reverse_oneWordString_reversedWordReturned() {
        String result = this.SUT.reverse("Marco");
        assertThat(result, is("ocraM"));
    }

    @Test
    public void reverse_longString_sameStringReturned() {
        String result = this.SUT.reverse("Marco Chavez");
        assertThat(result, is("zevahC ocraM"));
    }

}