package com.oracle.truffle.js.runtime.util;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;

public final class PrintWriterWrapper extends PrintWriter {
   private OutputStreamWrapper outWrapper;

   public PrintWriterWrapper(OutputStream out, boolean autoFlush, Charset charset) {
      this(new OutputStreamWrapper(out), autoFlush, charset);
   }

   private PrintWriterWrapper(OutputStreamWrapper outWrapper, boolean autoFlush, Charset charset) {
      super(new OutputStreamWriter(outWrapper, charset), autoFlush);

      assert outWrapper != null;

      this.outWrapper = outWrapper;
   }

   public void setDelegate(OutputStream out) {
      synchronized (this.lock) {
         this.outWrapper.setDelegate(out);
      }
   }

   public OutputStream getDelegate() {
      return this.outWrapper.getDelegate();
   }
}
