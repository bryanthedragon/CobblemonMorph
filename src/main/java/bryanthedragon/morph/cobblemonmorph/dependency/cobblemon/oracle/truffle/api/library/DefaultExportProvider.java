package com.oracle.truffle.api.library;

public interface DefaultExportProvider {
   String getLibraryClassName();

   Class<?> getDefaultExport();

   Class<?> getReceiverClass();

   int getPriority();
}
