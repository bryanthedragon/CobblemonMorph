
package com.oracle.truffle.api.source;

import java.io.IOException;
import java.io.Reader;

final class CharSequenceReader
extends Reader {
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

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int read() throws IOException {
        Object object = this.lock;
        synchronized (object) {
            this.ensureOpen();
            if (this.next >= this.length) {
                return -1;
            }
            return this.seq.charAt(this.next++);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
        Object object = this.lock;
        synchronized (object) {
            this.ensureOpen();
            if (off < 0 || off > cbuf.length || len < 0 || off + len > cbuf.length || off + len < 0) {
                throw new IndexOutOfBoundsException();
            }
            if (len == 0) {
                return 0;
            }
            if (this.next >= this.length) {
                return -1;
            }
            int n = Math.min(this.length - this.next, len);
            for (int i = 0; i < n; ++i) {
                cbuf[off + i] = this.seq.charAt(this.next + i);
            }
            this.next += n;
            return n;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public long skip(long ns) throws IOException {
        Object object = this.lock;
        synchronized (object) {
            this.ensureOpen();
            if (this.next >= this.length) {
                return 0L;
            }
            long n = Math.min((long)(this.length - this.next), ns);
            n = Math.max((long)(-this.next), n);
            this.next = (int)((long)this.next + n);
            return n;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean ready() throws IOException {
        Object object = this.lock;
        synchronized (object) {
            this.ensureOpen();
            return true;
        }
    }

    @Override
    public boolean markSupported() {
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void mark(int readAheadLimit) throws IOException {
        if (readAheadLimit < 0) {
            throw new IllegalArgumentException("Read-ahead limit < 0");
        }
        Object object = this.lock;
        synchronized (object) {
            this.ensureOpen();
            this.mark = this.next;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void reset() throws IOException {
        Object object = this.lock;
        synchronized (object) {
            this.ensureOpen();
            this.next = this.mark;
        }
    }

    @Override
    public void close() {
        this.seq = null;
    }
}

