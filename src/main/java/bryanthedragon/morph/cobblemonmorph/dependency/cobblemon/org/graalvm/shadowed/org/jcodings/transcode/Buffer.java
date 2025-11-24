
package org.graalvm.shadowed.org.jcodings.transcode;

class Buffer {
    int bufStart;
    int dataStart;
    int dataEnd;
    int bufEnd;
    byte[] bytes;

    Buffer() {
    }

    void allocate(int num) {
        this.bytes = new byte[num];
        this.dataEnd = 0;
        this.dataStart = 0;
        this.bufStart = 0;
        this.bufEnd = num;
    }
}

