package com.oracle.truffle.regex;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.instrumentation.GenerateWrapper;
import com.oracle.truffle.api.instrumentation.InstrumentableNode;
import com.oracle.truffle.api.instrumentation.ProbeNode;
import com.oracle.truffle.api.instrumentation.StandardTags;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.ExecutableNode;
import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.api.source.SourceSection;
import com.oracle.truffle.regex.tregex.string.Encodings;

@GenerateWrapper
public abstract class RegexBodyNode extends ExecutableNode implements InstrumentableNode {
   protected final RegexSource source;
   private final RegexLanguage language;
   private SourceSection sourceSection;

   protected RegexBodyNode(RegexLanguage language, RegexSource source) {
      super(language);
      this.source = source;
      this.language = language;
   }

   protected RegexBodyNode(RegexBodyNode copy) {
      this(copy.language, copy.source);
   }

   public RegexSource getSource() {
      return this.source;
   }

   public RegexLanguage getRegexLanguage() {
      return this.language;
   }

   public Encodings.Encoding getEncoding() {
      return this.source.getEncoding();
   }

   public boolean isBooleanMatch() {
      boolean booleanMatch = this.source.getOptions().isBooleanMatch();
      CompilerAsserts.partialEvaluationConstant(booleanMatch);
      return booleanMatch;
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public SourceSection getSourceSection() {
      if (this.sourceSection == null) {
         String patternSrc = this.source.toStringEscaped();
         String name = patternSrc.length() > 30 ? patternSrc.substring(0, 30) + "..." : patternSrc;
         Source src = Source.newBuilder("regex", patternSrc, name).internal(true).mimeType("application/js-regex").build();
         this.sourceSection = src.createSection(0, patternSrc.length());
      }

      return this.sourceSection;
   }

   @Override
   public boolean hasTag(Class<? extends Tag> tag) {
      return tag == StandardTags.RootTag.class;
   }

   @Override
   public boolean isInstrumentable() {
      return true;
   }

   @Override
   public InstrumentableNode.WrapperNode createWrapper(ProbeNode probe) {
      return new RegexBodyNodeWrapper(this, this, probe);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public final String toString() {
      String src = this.source.toStringEscaped();
      return "regex " + this.getEngineLabel() + ": " + (src.length() > 30 ? src.substring(0, 30) + "..." : src);
   }

   protected String getEngineLabel() {
      return "no_engine_label";
   }
}
