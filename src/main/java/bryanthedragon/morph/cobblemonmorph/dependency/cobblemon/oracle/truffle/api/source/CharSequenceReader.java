package com.oracle.truffle.api.source;

import java.io.IOException;
import java.io.Reader;

final class CharSequenceReader extends Reader {
   private CharSequence seq;
   private int length;
   private int next = 0;
   private int mark = 0;

   CharSequenceReader(CharSequence s) {
      this.seq = s;
      this.length = s.length();
   }

   private void ensureOpen() throws IOException {
      if (this.seq == null) {
         throw new IOException("Stream closed");
      }
   }

   @Override
   public int read() throws IOException {
      synchronized (this.lock) {
         this.ensureOpen();
         return this.next >= this.length ? -1 : this.seq.charAt(this.next++);
      }
   }

   @Override
   public int read(char[] cbuf, int off, int len) throws IOException {
      synchronized (this.lock) {
         this.ensureOpen();
         if (off < 0 || off > cbuf.length || len < 0 || off + len > cbuf.length || off + len < 0) {
            throw new IndexOutOfBoundsException();
         } else if (len == 0) {
            return 0;
         } else if (this.next >= this.length) {
            return -1;
         } else {
            int n = Math.min(this.length - this.next, len);

            for (int i = 0; i < n; i++) {
               cbuf[off + i] = this.seq.charAt(this.next + i);
            }

            this.next += n;
            return n;
         }
      }
   }

   @Override
   public long skip(long ns) throws IOException {
      synchronized (this.lock) {
         this.ensureOpen();
         if (this.next >= this.length) {
            return 0L;
         } else {
            long n = Math.min((long)(this.length - this.next), ns);
            n = Math.max((long)(-this.next), n);
            this.next = (int)(this.next + n);
            return n;
         }
      }
   }

   @Override
   public boolean ready() throws IOException {
      synchronized (this.lock) {
         this.ensureOpen();
         return true;
      }
   }

   @Override
   public boolean markSupported() {
      return true;
   }

   @Override
   public void mark(int readAheadLimit) throws IOException {
      if (readAheadLimit < 0) {
         throw new IllegalArgumentException("Read-ahead limit < 0");
      } else {
         synchronized (this.lock) {
            this.ensureOpen();
            this.mark = this.next;
         }
      }
   }

   @Override
   public void reset() throws IOException {
      synchronized (this.lock) {
         this.ensureOpen();
         this.next = this.mark;
      }
   }

   @Override
   public void close() {
      this.seq = null;
   }
}
