package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.ArityException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.nodes.DirectCallNode;
import com.oracle.truffle.api.nodes.IndirectCallNode;
import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.api.utilities.TriState;
import java.util.Objects;

@ExportLibrary(InteropLibrary.class)
final class PolyglotParsedEval implements TruffleObject {
   final PolyglotLanguageContext languageContext;
   final Source source;
   final CallTarget target;

   PolyglotParsedEval(PolyglotLanguageContext languageContext, Source source, CallTarget target) {
      this.languageContext = languageContext;
      this.source = source;
      this.target = target;
   }

   @ExportMessage
   static boolean isExecutable(PolyglotParsedEval eval) {
      return true;
   }

   @ExportMessage
   @CompilerDirectives.TruffleBoundary
   static int identityHashCode(PolyglotParsedEval receiver) {
      return Objects.hash(receiver.languageContext, System.identityHashCode(receiver.target));
   }

   @ExportMessage
   @CompilerDirectives.TruffleBoundary
   static String toDisplayString(PolyglotParsedEval receiver, boolean sideEffects) {
      return "Parsed[Source=" + receiver.source + "]";
   }

   @ExportMessage
   static class Execute {
      @Specialization(guards = "eval.target == callNode.getCallTarget()", limit = "5")
      static Object doCached(PolyglotParsedEval eval, Object[] args, @Cached("create(eval.target)") DirectCallNode callNode) throws ArityException {
         Object result = callNode.call(args);
         if (eval.source.isInteractive()) {
            PolyglotContextImpl.printResult(eval.languageContext, result);
         }

         return result;
      }

      @Specialization(replaces = "doCached")
      static Object doIndirect(PolyglotParsedEval eval, Object[] args, @Cached IndirectCallNode callNode) {
         Object result = callNode.call(eval.target, args);
         if (eval.source.isInteractive()) {
            PolyglotContextImpl.printResult(eval.languageContext, result);
         }

         return result;
      }
   }

   @ExportMessage
   static final class IsIdenticalOrUndefined {
      @Specialization
      static TriState doDefault(PolyglotParsedEval receiver, PolyglotParsedEval other) {
         return receiver.target == other.target && receiver.languageContext == other.languageContext ? TriState.TRUE : TriState.FALSE;
      }

      @Fallback
      static TriState doOther(PolyglotParsedEval receiver, Object other) {
         return TriState.UNDEFINED;
      }
   }
}
