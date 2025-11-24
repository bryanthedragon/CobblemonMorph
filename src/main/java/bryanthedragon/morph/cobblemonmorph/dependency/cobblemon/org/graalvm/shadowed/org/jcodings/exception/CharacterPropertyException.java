
package org.graalvm.shadowed.org.jcodings.exception;

import org.graalvm.shadowed.org.jcodings.exception.EncodingError;
import org.graalvm.shadowed.org.jcodings.exception.EncodingException;

public class CharacterPropertyException
extends EncodingException {
    public CharacterPropertyException(EncodingError error) {
        super(error);
    }

    public CharacterPropertyException(EncodingError error, String str) {
        super(error, str);
    }

    public CharacterPropertyException(EncodingError error, byte[] bytes, int p, int end2) {
        super(error, bytes, p, end2);
    }

    @Deprecated
    public CharacterPropertyException(String message) {
        super(message);
    }

    @Deprecated
    public CharacterPropertyException(String message, String str) {
        super(message, str);
    }

    @Deprecated
    public CharacterPropertyException(String message, byte[] bytes, int p, int end2) {
        super(message, bytes, p, end2);
    }
}

