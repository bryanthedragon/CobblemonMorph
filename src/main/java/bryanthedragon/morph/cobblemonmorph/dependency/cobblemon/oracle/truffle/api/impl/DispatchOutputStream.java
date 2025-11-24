
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

public final class DispatchOutputStream
extends OutputStream {
    private final OutputStream out;
    @CompilerDirectives.CompilationFinal
    private volatile OutputStreamList outList;
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
            this.outList = new OutputStreamList();
            this.outListChanged();
        }
        this.outList.add(outConsumer);
    }

    synchronized void detach(OutputStream outConsumer) {
        if (this.outList == null) {
            return;
        }
        this.outList.remove(outConsumer);
        if (this.outList.isEmpty()) {
            this.outList = null;
            this.outListChanged();
        }
    }

    private void outListChanged() {
        Assumption changed = this.outListUnchanged;
        this.outListUnchanged = Truffle.getRuntime().createAssumption("Unchanged list");
        changed.invalidate();
    }

    OutputStreamList getOutList() {
        if (this.outListUnchanged.isValid()) {
            return this.outList;
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.outList;
    }

    @Override
    public void write(int b) throws IOException {
        OutputStreamList outs = this.getOutList();
        if (outs != null) {
            outs.writeMulti(b);
        }
        this.out.write(b);
    }

    @Override
    public void write(byte[] b) throws IOException {
        OutputStreamList outs = this.getOutList();
        if (outs != null) {
            outs.writeMulti(b);
        }
        this.out.write(b);
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        OutputStreamList outs = this.getOutList();
        if (outs != null) {
            outs.writeMulti(b, off, len);
        }
        this.out.write(b, off, len);
    }

    @Override
    public void flush() throws IOException {
        OutputStreamList outs = this.getOutList();
        if (outs != null) {
            outs.flushMulti();
        }
        this.out.flush();
    }

    @Override
    public void close() throws IOException {
        OutputStreamList outs = this.getOutList();
        if (outs != null) {
            outs.closeMulti();
        }
        this.out.close();
    }

    class OutputStreamList {
        private final List<OutputStream> outs = new CopyOnWriteArrayList<OutputStream>();
        @CompilerDirectives.CompilationFinal
        private boolean seenException;
        private Map<OutputStream, String> reportedExceptions;

        OutputStreamList() {
        }

        void add(OutputStream outConsumer) {
            this.outs.add(outConsumer);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        void remove(OutputStream outConsumer) {
            this.outs.remove(outConsumer);
            OutputStreamList outputStreamList = this;
            synchronized (outputStreamList) {
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
                }
                catch (Throwable t) {
                    if (!this.seenException) {
                        CompilerDirectives.transferToInterpreterAndInvalidate();
                        this.seenException = true;
                    }
                    this.handleException("write(I)", os, t);
                }
            }
        }

        @CompilerDirectives.TruffleBoundary
        void writeMulti(byte[] b) {
            for (OutputStream os : this.outs) {
                try {
                    os.write(b);
                }
                catch (Throwable t) {
                    if (!this.seenException) {
                        CompilerDirectives.transferToInterpreterAndInvalidate();
                        this.seenException = true;
                    }
                    this.handleException("write(B[)", os, t);
                }
            }
        }

        @CompilerDirectives.TruffleBoundary
        void writeMulti(byte[] b, int off, int len) {
            for (OutputStream os : this.outs) {
                try {
                    os.write(b, off, len);
                }
                catch (Throwable t) {
                    if (!this.seenException) {
                        CompilerDirectives.transferToInterpreterAndInvalidate();
                        this.seenException = true;
                    }
                    this.handleException("write(B[II)", os, t);
                }
            }
        }

        @CompilerDirectives.TruffleBoundary
        void flushMulti() {
            for (OutputStream os : this.outs) {
                try {
                    os.flush();
                }
                catch (Throwable t) {
                    if (!this.seenException) {
                        CompilerDirectives.transferToInterpreterAndInvalidate();
                        this.seenException = true;
                    }
                    this.handleException("flush()", os, t);
                }
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @CompilerDirectives.TruffleBoundary
        void closeMulti() {
            for (OutputStream os : this.outs) {
                try {
                    os.close();
                }
                catch (Throwable t) {
                    if (!this.seenException) {
                        CompilerDirectives.transferToInterpreterAndInvalidate();
                        this.seenException = true;
                    }
                    this.handleException("close()", os, t);
                }
            }
            this.outs.clear();
            OutputStreamList outputStreamList = this;
            synchronized (outputStreamList) {
                this.reportedExceptions = null;
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private void handleException(String method, OutputStream os, Throwable t) {
            boolean report;
            if (t instanceof ThreadDeath) {
                throw (ThreadDeath)t;
            }
            String description = method + t.getMessage() + t.getClass().getName();
            OutputStreamList outputStreamList = this;
            synchronized (outputStreamList) {
                if (this.reportedExceptions == null) {
                    this.reportedExceptions = new HashMap<OutputStream, String>();
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

