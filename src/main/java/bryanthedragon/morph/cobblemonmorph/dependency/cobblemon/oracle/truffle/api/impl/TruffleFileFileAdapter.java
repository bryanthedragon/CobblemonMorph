
package com.oracle.truffle.api.impl;

import com.oracle.truffle.api.TruffleFile;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.LinkOption;

final class TruffleFileFileAdapter
extends File {
    private final TruffleFile truffleFile;

    TruffleFileFileAdapter(TruffleFile truffleFile) {
        super(truffleFile.getPath());
        this.truffleFile = truffleFile;
    }

    TruffleFile getTruffleFile() {
        return this.truffleFile;
    }

    @Override
    public String getName() {
        return this.truffleFile.getName();
    }

    @Override
    public String getPath() {
        return this.truffleFile.getPath();
    }

    @Override
    public File getAbsoluteFile() {
        return new TruffleFileFileAdapter(this.truffleFile.getAbsoluteFile());
    }

    @Override
    public File getCanonicalFile() throws IOException {
        return new TruffleFileFileAdapter(this.truffleFile.getCanonicalFile(new LinkOption[0]));
    }

    @Override
    public URI toURI() {
        return this.truffleFile.toUri();
    }
}

