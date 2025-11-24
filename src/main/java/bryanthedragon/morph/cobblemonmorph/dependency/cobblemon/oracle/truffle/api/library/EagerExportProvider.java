
package com.oracle.truffle.api.library;

public interface EagerExportProvider {
    public void ensureRegistered();

    public String getLibraryClassName();
}

