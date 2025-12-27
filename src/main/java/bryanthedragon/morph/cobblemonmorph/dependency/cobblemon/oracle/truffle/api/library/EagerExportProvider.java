package com.oracle.truffle.api.library;

public interface EagerExportProvider {
   void ensureRegistered();

   String getLibraryClassName();
}
