package com.oracle.truffle.api.impl;

import java.io.IOException;
import java.io.OutputStream;

public final class DelegatingOutputStream extends OutputStream {
   private final OutputStream out;
   private final DispatchOutputStream delegate;

   DelegatingOutputStream(OutputStream out, DispatchOutputStream delegate) {
      this.out = out;
      this.delegate = delegate;
   }

   @Override
   public void write(int b) throws IOException {
      DispatchOutputStream.OutputStreamList outs = this.delegate.getOutList();
      if (outs != null) {
         outs.writeMulti(b);
      }

      this.out.write(b);
   }

   @Override
   public void write(byte[] b) throws IOException {
      DispatchOutputStream.OutputStreamList outs = this.delegate.getOutList();
      if (outs != null) {
         outs.writeMulti(b);
      }

      this.out.write(b);
   }

   @Override
   public void write(byte[] b, int off, int len) throws IOException {
      DispatchOutputStream.OutputStreamList outs = this.delegate.getOutList();
      if (outs != null) {
         outs.writeMulti(b, off, len);
      }

      this.out.write(b, off, len);
   }

   @Override
   public void flush() throws IOException {
      DispatchOutputStream.OutputStreamList outs = this.delegate.getOutList();
      if (outs != null) {
         outs.flushMulti();
      }

      this.out.flush();
   }

   @Override
   public void close() throws IOException {
      DispatchOutputStream.OutputStreamList outs = this.delegate.getOutList();
      if (outs != null) {
         outs.closeMulti();
      }

      this.out.close();
   }
}
