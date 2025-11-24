
package org.graalvm.shadowed.org.jcodings.transcode;

public interface EConvFlags {
    public static final int ERROR_HANDLER_MASK = 255;
    public static final int INVALID_MASK = 15;
    public static final int INVALID_REPLACE = 2;
    public static final int UNDEF_MASK = 240;
    public static final int UNDEF_REPLACE = 32;
    public static final int UNDEF_HEX_CHARREF = 48;
    public static final int DECORATOR_MASK = 65280;
    public static final int NEWLINE_DECORATOR_MASK = 16128;
    public static final int NEWLINE_DECORATOR_READ_MASK = 3840;
    public static final int NEWLINE_DECORATOR_WRITE_MASK = 12288;
    public static final int UNIVERSAL_NEWLINE_DECORATOR = 256;
    public static final int CRLF_NEWLINE_DECORATOR = 4096;
    public static final int CR_NEWLINE_DECORATOR = 8192;
    public static final int XML_TEXT_DECORATOR = 16384;
    public static final int XML_ATTR_CONTENT_DECORATOR = 32768;
    public static final int STATEFUL_DECORATOR_MASK = 0xF00000;
    public static final int XML_ATTR_QUOTE_DECORATOR = 0x100000;
    public static final int PARTIAL_INPUT = 65536;
    public static final int AFTER_OUTPUT = 131072;
    public static final int MAX_ECFLAGS_DECORATORS = 32;
}

