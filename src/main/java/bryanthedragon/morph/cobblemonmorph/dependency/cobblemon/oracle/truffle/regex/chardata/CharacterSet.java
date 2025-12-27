package com.oracle.truffle.regex.chardata;

public interface CharacterSet {
   boolean contains(int codePoint);
}
