
package org.graalvm.nativeimage.impl;

import java.io.IOException;

public interface HeapDumpSupport {
    public void dumpHeap(String var1, boolean var2) throws IOException;
}

