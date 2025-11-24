
package org.graalvm.shadowed.org.jcodings.exception;

import org.graalvm.shadowed.org.jcodings.exception.JCodingsException;

public class InternalException
extends JCodingsException {
    private static final long serialVersionUID = -3871816465397927992L;

    public InternalException(String message) {
        super(message);
    }

    public InternalException(String message, String str) {
        super(message, str);
    }

    public InternalException(String message, byte[] bytes, int p, int end2) {
        super(message, bytes, p, end2);
    }
}

