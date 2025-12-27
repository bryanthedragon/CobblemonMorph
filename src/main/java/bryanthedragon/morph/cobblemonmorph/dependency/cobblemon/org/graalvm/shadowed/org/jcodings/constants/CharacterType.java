package org.graalvm.shadowed.org.jcodings.constants;

public interface CharacterType {
   int NEWLINE = 0;
   int ALPHA = 1;
   int BLANK = 2;
   int CNTRL = 3;
   int DIGIT = 4;
   int GRAPH = 5;
   int LOWER = 6;
   int PRINT = 7;
   int PUNCT = 8;
   int SPACE = 9;
   int UPPER = 10;
   int XDIGIT = 11;
   int WORD = 12;
   int ALNUM = 13;
   int ASCII = 14;
   int MAX_STD_CTYPE = 14;
   int BIT_NEWLINE = 1;
   int BIT_ALPHA = 2;
   int BIT_BLANK = 4;
   int BIT_CNTRL = 8;
   int BIT_DIGIT = 16;
   int BIT_GRAPH = 32;
   int BIT_LOWER = 64;
   int BIT_PRINT = 128;
   int BIT_PUNCT = 256;
   int BIT_SPACE = 512;
   int BIT_UPPER = 1024;
   int BIT_XDIGIT = 2048;
   int BIT_WORD = 4096;
   int BIT_ALNUM = 8192;
   int BIT_ASCII = 16384;
}
