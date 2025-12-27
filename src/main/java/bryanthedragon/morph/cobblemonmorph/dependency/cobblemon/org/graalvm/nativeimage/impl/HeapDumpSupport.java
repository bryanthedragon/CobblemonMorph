package org.graalvm.nativeimage.impl;

import java.io.IOException;

public interface HeapDumpSupport {
   void dumpHeap(String outputFile, boolean live) throws IOException;
}
