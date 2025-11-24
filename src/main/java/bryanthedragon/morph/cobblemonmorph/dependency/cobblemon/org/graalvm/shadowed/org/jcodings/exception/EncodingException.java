
package org.graalvm.shadowed.org.jcodings.exception;

import org.graalvm.shadowed.org.jcodings.exception.EncodingError;
import org.graalvm.shadowed.org.jcodings.exception.JCodingsException;

public class EncodingException
extends JCodingsException {
    private final EncodingError error;

    public EncodingException(EncodingError error) {
        super(error.getMessage());
        this.error = error;
    }

    public EncodingException(EncodingError error, String str) {
        super(error.getMessage());
        this.error = error;
    }

    public EncodingException(EncodingError error, byte[] bytes, int p, int end2) {
        super(error.getMessage(), bytes, p, end2);
        this.error = error;
    }

    public EncodingError getError() {
        return this.error;
    }

    @Deprecated
    public EncodingException(String message) {
        super(message);
        this.error = null;
    }

    @Deprecated
    public EncodingException(String message, String str) {
        super(message, str);
        this.error = null;
    }

    @Deprecated
    public EncodingException(String message, byte[] bytes, int p, int end2) {
        super(message, bytes, p, end2);
        this.error = null;
    }
}

