package org.graalvm.shadowed.org.jcodings.transcode;

interface TranscodingInstruction {
   int NOMAP = 1;
   int ONEbt = 2;
   int TWObt = 3;
   int THREEbt = 5;
   int FOURbt = 6;
   int INVALID = 7;
   int UNDEF = 9;
   int ZERObt = 10;
   int FUNii = 11;
   int FUNsi = 13;
   int FUNio = 14;
   int FUNso = 15;
   int STR1 = 17;
   int GB4bt = 18;
   int FUNsio = 19;
   int LAST = 28;
   int NOMAP_RESUME_1 = 29;
   int ZeroXResume_1 = 30;
   int ZeroXResume_2 = 31;
}
