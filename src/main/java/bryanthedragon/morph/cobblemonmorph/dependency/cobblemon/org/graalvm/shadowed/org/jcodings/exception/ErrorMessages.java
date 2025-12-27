package org.graalvm.shadowed.org.jcodings.exception;

public interface ErrorMessages {
   String ERR_TYPE_BUG = "undefined type (bug)";
   String ERR_TOO_BIG_WIDE_CHAR_VALUE = "too big wide-char value";
   String ERR_TOO_LONG_WIDE_CHAR_VALUE = "too long wide-char value";
   String ERR_INVALID_CHAR_PROPERTY_NAME = "invalid character property name <%n>";
   String ERR_INVALID_CODE_POINT_VALUE = "invalid code point value";
   String ERR_ENCODING_CLASS_DEF_NOT_FOUND = "encoding class <%n> not found";
   String ERR_ENCODING_LOAD_ERROR = "problem loading encoding <%n>";
   String ERR_ILLEGAL_CHARACTER = "illegal character";
   String ERR_ENCODING_ALREADY_REGISTERED = "encoding already registerd <%n>";
   String ERR_ENCODING_ALIAS_ALREADY_REGISTERED = "encoding alias already registerd <%n>";
   String ERR_ENCODING_REPLICA_ALREADY_REGISTERED = "encoding replica already registerd <%n>";
   String ERR_NO_SUCH_ENCODNG = "no such encoding <%n>";
   String ERR_COULD_NOT_REPLICATE = "could not replicate <%n> encoding";
   String ERR_TRANSCODER_ALREADY_REGISTERED = "transcoder from <%n> has been already registered";
   String ERR_TRANSCODER_CLASS_DEF_NOT_FOUND = "transcoder class <%n> not found";
   String ERR_TRANSCODER_LOAD_ERROR = "problem loading transcoder <%n>";
}
