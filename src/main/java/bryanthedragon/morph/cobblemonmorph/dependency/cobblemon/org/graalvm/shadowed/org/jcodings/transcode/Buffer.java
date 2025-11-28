package org.graalvm.shadowed.org.jcodings.transcode;

class Buffer {
   int bufStart;
   int dataStart;
   int dataEnd;
   int bufEnd;
   byte[] bytes;

   void allocate(int num) {
      this.bytes = new byte[num];
      this.bufStart = this.dataStart = this.dataEnd = 0;
      this.bufEnd = num;
   }
}
