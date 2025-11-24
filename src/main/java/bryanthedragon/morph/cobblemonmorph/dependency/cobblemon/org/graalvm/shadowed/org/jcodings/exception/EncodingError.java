
package org.graalvm.shadowed.org.jcodings.exception;

import org.graalvm.shadowed.org.jcodings.util.IntHash;

public enum EncodingError {
    ERR_TYPE_BUG("undefined type (bug)", -6),
    ERR_TOO_BIG_WIDE_CHAR_VALUE("too big wide-char value", -401),
    ERR_TOO_LONG_WIDE_CHAR_VALUE("too long wide-char value", -212),
    ERR_INVALID_CHAR_PROPERTY_NAME("invalid character property name <%n>", -223),
    ERR_INVALID_CODE_POINT_VALUE("invalid code point value", -400),
    ERR_ENCODING_CLASS_DEF_NOT_FOUND("encoding class <%n> not found", -1000),
    ERR_ENCODING_LOAD_ERROR("problem loading encoding <%n>", -1001),
    ERR_ENCODING_ALREADY_REGISTERED("encoding already registerd <%n>", -1002),
    ERR_ENCODING_ALIAS_ALREADY_REGISTERED("encoding alias already registerd <%n>", -1003),
    ERR_ENCODING_REPLICA_ALREADY_REGISTERED("encoding replica already registerd <%n>", -1004),
    ERR_NO_SUCH_ENCODNG("no such encoding <%n>", -1005),
    ERR_COULD_NOT_REPLICATE("could not replicate <%n> encoding", -1006),
    ERR_TRANSCODER_ALREADY_REGISTERED("transcoder from <%n> has been already registered", -1007),
    ERR_TRANSCODER_CLASS_DEF_NOT_FOUND("transcoder class <%n> not found", -1008),
    ERR_TRANSCODER_LOAD_ERROR("problem loading transcoder <%n>", -1009);

    private final String message;
    private final int code;
    private static final IntHash<EncodingError> CODE_TO_ERROR;

    private EncodingError(String message, int code) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public int getCode() {
        return this.code;
    }

    public static EncodingError fromCode(int code) {
        return CODE_TO_ERROR.get(code);
    }

    static {
        CODE_TO_ERROR = new IntHash();
        for (EncodingError error : EncodingError.values()) {
            CODE_TO_ERROR.put(error.getCode(), error);
        }
    }
}

