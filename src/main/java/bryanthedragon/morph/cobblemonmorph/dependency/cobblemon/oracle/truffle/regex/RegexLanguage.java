package com.oracle.truffle.regex;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.instrumentation.ProvidedTags;
import com.oracle.truffle.api.instrumentation.StandardTags;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.regex.tregex.TRegexCompiler;
import com.oracle.truffle.regex.tregex.parser.RegexParserGlobals;
import com.oracle.truffle.regex.tregex.parser.RegexValidator;
import com.oracle.truffle.regex.tregex.parser.ast.GroupBoundaries;
import com.oracle.truffle.regex.tregex.parser.flavors.ECMAScriptFlavor;
import com.oracle.truffle.regex.tregex.parser.flavors.RegexFlavor;
import com.oracle.truffle.regex.tregex.string.Encodings;
import com.oracle.truffle.regex.util.TruffleNull;

@TruffleLanguage.Registration(
   name = "REGEX",
   id = "regex",
   characterMimeTypes = "application/tregex",
   version = "0.1",
   contextPolicy = TruffleLanguage.ContextPolicy.SHARED,
   internal = true,
   interactive = false,
   website = "https://github.com/oracle/graal/tree/master/regex"
)
@ProvidedTags(StandardTags.RootTag.class)
public final class RegexLanguage extends TruffleLanguage<RegexLanguage.RegexContext> {
   public static final String NAME = "REGEX";
   public static final String ID = "regex";
   public static final String MIME_TYPE = "application/tregex";
   private final GroupBoundaries[] cachedGroupBoundaries = GroupBoundaries.createCachedGroupBoundaries();
   public final RegexParserGlobals parserGlobals = new RegexParserGlobals(this);

   public GroupBoundaries[] getCachedGroupBoundaries() {
      return this.cachedGroupBoundaries;
   }

   @Override
   protected CallTarget parse(TruffleLanguage.ParsingRequest parsingRequest) {
      return RootNode.createConstantNode(this.createRegexObject(createRegexSource(parsingRequest.getSource()))).getCallTarget();
   }

   private static RegexSource createRegexSource(Source source) {
      String srcStr = source.getCharacters().toString();
      if (srcStr.length() < 2) {
         throw CompilerDirectives.shouldNotReachHere("malformed regex");
      } else {
         RegexOptions.Builder optBuilder = RegexOptions.builder(source, srcStr);
         int firstSlash = optBuilder.parseOptions();
         int lastSlash = srcStr.lastIndexOf(47);

         assert firstSlash >= 0 && firstSlash <= srcStr.length();

         if (lastSlash <= firstSlash) {
            throw CompilerDirectives.shouldNotReachHere("malformed regex");
         } else {
            String pattern = srcStr.substring(firstSlash + 1, lastSlash);
            String flags = srcStr.substring(lastSlash + 1);
            if (optBuilder.getFlavor() == ECMAScriptFlavor.INSTANCE
               && !optBuilder.isUtf16ExplodeAstralSymbols()
               && optBuilder.getEncoding() == Encodings.UTF_16_RAW
               && flags.indexOf(117) >= 0) {
               optBuilder.encoding(Encodings.UTF_16);
            }

            return new RegexSource(pattern, flags, optBuilder.build(), source);
         }
      }
   }

   private Object createRegexObject(RegexSource source) {
      if (source.getOptions().isValidate()) {
         RegexFlavor flavor = source.getOptions().getFlavor();
         RegexValidator validator = flavor.createValidator(source);
         validator.validate();
         return TruffleNull.INSTANCE;
      } else {
         try {
            return TRegexCompiler.compile(this, source);
         } catch (UnsupportedRegexException var4) {
            return TruffleNull.INSTANCE;
         }
      }
   }

   protected RegexLanguage.RegexContext createContext(TruffleLanguage.Env env) {
      return new RegexLanguage.RegexContext(env);
   }

   protected boolean patchContext(RegexLanguage.RegexContext context, TruffleLanguage.Env newEnv) {
      context.patchContext(newEnv);
      return true;
   }

   protected Object getScope(RegexLanguage.RegexContext context) {
      return null;
   }

   @Override
   protected boolean isThreadAccessAllowed(Thread thread, boolean singleThreaded) {
      return true;
   }

   public static final class RegexContext {
      @CompilerDirectives.CompilationFinal
      private TruffleLanguage.Env env;
      private static final TruffleLanguage.ContextReference<RegexLanguage.RegexContext> REFERENCE = TruffleLanguage.ContextReference.create(RegexLanguage.class);

      RegexContext(TruffleLanguage.Env env) {
         this.env = env;
      }

      void patchContext(TruffleLanguage.Env patchedEnv) {
         this.env = patchedEnv;
      }

      public TruffleLanguage.Env getEnv() {
         return this.env;
      }

      public static RegexLanguage.RegexContext get(Node node) {
         return REFERENCE.get(node);
      }
   }
}
