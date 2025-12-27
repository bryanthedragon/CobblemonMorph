package com.oracle.truffle.regex;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.exception.AbstractTruffleException;
import com.oracle.truffle.api.interop.ExceptionType;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.api.source.SourceSection;

@ExportLibrary(InteropLibrary.class)
public final class RegexSyntaxException extends AbstractTruffleException {
   private final SourceSection sourceSection;
   private static final long serialVersionUID = 1L;

   public static RegexSyntaxException createOptions(Source source, String msg, int position) {
      return new RegexSyntaxException(msg, source, position);
   }

   public static RegexSyntaxException createPattern(RegexSource source, String msg, int position) {
      return new RegexSyntaxException(msg, patternSource(source), position);
   }

   public static RegexSyntaxException createFlags(RegexSource source, String msg) {
      return new RegexSyntaxException(msg, flagsSource(source), 0);
   }

   public static RegexSyntaxException createFlags(RegexSource source, String msg, int position) {
      return new RegexSyntaxException(msg, flagsSource(source), position);
   }

   @CompilerDirectives.TruffleBoundary
   private static Source patternSource(RegexSource regexSource) {
      String src = regexSource.getSource().getCharacters().toString();
      int firstPos = src.indexOf(47) + 1;
      int lastPos = src.lastIndexOf(47);

      assert firstPos > 0;

      assert lastPos > firstPos;

      return regexSource.getSource().subSource(firstPos, lastPos - firstPos);
   }

   @CompilerDirectives.TruffleBoundary
   private static Source flagsSource(RegexSource regexSource) {
      String src = regexSource.getSource().getCharacters().toString();
      int lastPos = src.lastIndexOf(47) + 1;

      assert lastPos > 0;

      return regexSource.getSource().subSource(lastPos, src.length() - lastPos);
   }

   @CompilerDirectives.TruffleBoundary
   private RegexSyntaxException(String reason, Source src, int position) {
      super(reason);

      assert position <= src.getLength();

      this.sourceSection = src.createSection(position, src.getLength() - position);
   }

   @ExportMessage
   ExceptionType getExceptionType() {
      return ExceptionType.PARSE_ERROR;
   }

   @ExportMessage
   boolean hasSourceLocation() {
      return true;
   }

   @ExportMessage(name = "getSourceLocation")
   SourceSection getSourceSection() {
      return this.sourceSection;
   }
}
