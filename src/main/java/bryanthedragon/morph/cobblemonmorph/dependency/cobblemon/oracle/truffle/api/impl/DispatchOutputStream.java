package com.oracle.truffle.api.impl;

import com.oracle.truffle.api.Assumption;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.Truffle;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public final class DispatchOutputStream extends OutputStream {
   private final OutputStream out;
   @CompilerDirectives.CompilationFinal
   private volatile DispatchOutputStream.OutputStreamList outList;
   @CompilerDirectives.CompilationFinal
   private volatile Assumption outListUnchanged;

   DispatchOutputStream(OutputStream out) {
      this.out = out;
      this.outListUnchanged = Truffle.getRuntime().createAssumption("Unchanged list");
   }

   OutputStream getOut() {
      return this.out;
   }

   synchronized void attach(OutputStream outConsumer) {
      if (this.outList == null) {
         this.outList = new DispatchOutputStream.OutputStreamList();
         this.outListChanged();
      }

      this.outList.add(outConsumer);
   }

   synchronized void detach(OutputStream outConsumer) {
      if (this.outList != null) {
         this.outList.remove(outConsumer);
         if (this.outList.isEmpty()) {
            this.outList = null;
            this.outListChanged();
         }
      }
   }

   private void outListChanged() {
      Assumption changed = this.outListUnchanged;
      this.outListUnchanged = Truffle.getRuntime().createAssumption("Unchanged list");
      changed.invalidate();
   }

   DispatchOutputStream.OutputStreamList getOutList() {
      if (this.outListUnchanged.isValid()) {
         return this.outList;
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.outList;
      }
   }

   @Override
   public void write(int b) throws IOException {
      DispatchOutputStream.OutputStreamList outs = this.getOutList();
      if (outs != null) {
         outs.writeMulti(b);
      }

      this.out.write(b);
   }

   @Override
   public void write(byte[] b) throws IOException {
      DispatchOutputStream.OutputStreamList outs = this.getOutList();
      if (outs != null) {
         outs.writeMulti(b);
      }

      this.out.write(b);
   }

   @Override
   public void write(byte[] b, int off, int len) throws IOException {
      DispatchOutputStream.OutputStreamList outs = this.getOutList();
      if (outs != null) {
         outs.writeMulti(b, off, len);
      }

      this.out.write(b, off, len);
   }

   @Override
   public void flush() throws IOException {
      DispatchOutputStream.OutputStreamList outs = this.getOutList();
      if (outs != null) {
         outs.flushMulti();
      }

      this.out.flush();
   }

   @Override
   public void close() throws IOException {
      DispatchOutputStream.OutputStreamList outs = this.getOutList();
      if (outs != null) {
         outs.closeMulti();
      }

      this.out.close();
   }

   class OutputStreamList {
      private final List<OutputStream> outs = new CopyOnWriteArrayList<>();
      @CompilerDirectives.CompilationFinal
      private boolean seenException;
      private Map<OutputStream, String> reportedExceptions;

      void add(OutputStream outConsumer) {
         this.outs.add(outConsumer);
      }

      void remove(OutputStream outConsumer) {
         this.outs.remove(outConsumer);
         synchronized (this) {
            if (this.reportedExceptions != null) {
               this.reportedExceptions.remove(outConsumer);
            }
         }
      }

      boolean isEmpty() {
         return this.outs.isEmpty();
      }

      @CompilerDirectives.TruffleBoundary
      void writeMulti(int b) {
         for (OutputStream os : this.outs) {
            try {
               os.write(b);
            } catch (Throwable var5) {
               if (!this.seenException) {
                  CompilerDirectives.transferToInterpreterAndInvalidate();
                  this.seenException = true;
               }

               this.handleException("write(I)", os, var5);
            }
         }
      }

      @CompilerDirectives.TruffleBoundary
      void writeMulti(byte[] b) {
         for (OutputStream os : this.outs) {
            try {
               os.write(b);
            } catch (Throwable var5) {
               if (!this.seenException) {
                  CompilerDirectives.transferToInterpreterAndInvalidate();
                  this.seenException = true;
               }

               this.handleException("write(B[)", os, var5);
            }
         }
      }

      @CompilerDirectives.TruffleBoundary
      void writeMulti(byte[] b, int off, int len) {
         for (OutputStream os : this.outs) {
            try {
               os.write(b, off, len);
            } catch (Throwable var7) {
               if (!this.seenException) {
                  CompilerDirectives.transferToInterpreterAndInvalidate();
                  this.seenException = true;
               }

               this.handleException("write(B[II)", os, var7);
            }
         }
      }

      @CompilerDirectives.TruffleBoundary
      void flushMulti() {
         for (OutputStream os : this.outs) {
            try {
               os.flush();
            } catch (Throwable var4) {
               if (!this.seenException) {
                  CompilerDirectives.transferToInterpreterAndInvalidate();
                  this.seenException = true;
               }

               this.handleException("flush()", os, var4);
            }
         }
      }

      @CompilerDirectives.TruffleBoundary
      void closeMulti() {
         for (OutputStream os : this.outs) {
            try {
               os.close();
            } catch (Throwable var6) {
               if (!this.seenException) {
                  CompilerDirectives.transferToInterpreterAndInvalidate();
                  this.seenException = true;
               }

               this.handleException("close()", os, var6);
            }
         }

         this.outs.clear();
         synchronized (this) {
            this.reportedExceptions = null;
         }
      }

      private void handleException(String method, OutputStream os, Throwable t) {
         if (t instanceof ThreadDeath) {
            throw (ThreadDeath)t;
         } else {
            String description = method + t.getMessage() + t.getClass().getName();
            boolean report;
            synchronized (this) {
               if (this.reportedExceptions == null) {
                  this.reportedExceptions = new HashMap<>();
               }

               report = this.reportedExceptions.put(os, description) == null;
            }

            if (report) {
               String message = String.format("Output operation %s failed for %s.", method, os);
               Exception exception = new Exception(message, t);
               PrintStream stream = new PrintStream(DispatchOutputStream.this.out);
               exception.printStackTrace(stream);
            }
         }
      }
   }
}
