package com.techyourchance.unittestingfundamentals.example2;

import com.sun.istack.internal.Nullable;

public class StringReverser {

    public String reverse(String string) {
        StringBuilder sb = new StringBuilder();
        for (int i = string.length() - 1; i >= 0; i--) {
            sb.append(string.charAt(i));
        }
        return sb.toString();
    }
}
