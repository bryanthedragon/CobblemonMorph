
package com.oracle.truffle.api.library;

public interface DefaultExportProvider {
    public String getLibraryClassName();

    public Class<?> getDefaultExport();

    public Class<?> getReceiverClass();

    public int getPriority();
}

