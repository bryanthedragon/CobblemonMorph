
package com.oracle.truffle.js.runtime.util;

import java.io.IOException;
import java.io.OutputStream;

final class OutputStreamWrapper
extends OutputStream {
    private volatile OutputStream out;

    OutputStreamWrapper(OutputStream out) {
        this.out = out;
    }

    void setDelegate(OutputStream out) {
        this.out = out;
    }

    OutputStream getDelegate() {
        return this.out;
    }

    @Override
    public void write(int b) throws IOException {
        this.out.write(b);
    }

    @Override
    public void write(byte[] b) throws IOException {
        this.out.write(b);
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        this.out.write(b, off, len);
    }

    @Override
    public void flush() throws IOException {
        this.out.flush();
    }

    @Override
    public void close() throws IOException {
        this.out.close();
    }
}

