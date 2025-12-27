package com.oracle.truffle.regex;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.GenerateUncached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.ReportPolymorphism;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.ArityException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.interop.UnsupportedTypeException;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.nodes.DirectCallNode;
import com.oracle.truffle.api.nodes.IndirectCallNode;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.regex.result.RegexResult;
import com.oracle.truffle.regex.runtime.nodes.ExpectByteArrayHostObjectNode;
import com.oracle.truffle.regex.runtime.nodes.ExpectStringOrTruffleObjectNode;
import com.oracle.truffle.regex.runtime.nodes.ToLongNode;
import com.oracle.truffle.regex.tregex.TRegexCompilationRequest;
import com.oracle.truffle.regex.util.TruffleReadOnlyKeysArray;

@ExportLibrary(InteropLibrary.class)
public final class RegexObject extends AbstractConstantKeysObject {
   static final String PROP_EXEC = "exec";
   static final String PROP_EXEC_BOOLEAN = "execBoolean";
   static final String PROP_EXEC_BYTES = "execBytes";
   private static final String PROP_PATTERN = "pattern";
   private static final String PROP_FLAGS = "flags";
   private static final String PROP_GROUP_COUNT = "groupCount";
   private static final String PROP_GROUPS = "groups";
   private static final String PROP_IS_BACKTRACKING = "isBacktracking";
   private static final TruffleReadOnlyKeysArray KEYS = new TruffleReadOnlyKeysArray(
      "exec", "execBoolean", "pattern", "flags", "groupCount", "groups", "isBacktracking"
   );
   private final RegexLanguage language;
   private final RegexSource source;
   private final AbstractRegexObject flags;
   private final int numberOfCaptureGroups;
   private final AbstractRegexObject namedCaptureGroups;
   @CompilerDirectives.CompilationFinal
   private RegexRootNode execRootNode;
   @CompilerDirectives.CompilationFinal
   private RegexRootNode execBooleanRootNode;
   private final boolean backtracking;
   private static final String N_METHODS = "3";

   public RegexObject(RegexExecNode execNode, RegexSource source, AbstractRegexObject flags, int numberOfCaptureGroups, AbstractRegexObject namedCaptureGroups) {
      this.language = execNode.getRegexLanguage();
      this.source = source;
      this.flags = flags;
      this.numberOfCaptureGroups = numberOfCaptureGroups;
      this.namedCaptureGroups = namedCaptureGroups;
      RegexRootNode rootNode = new RegexRootNode(execNode.getRegexLanguage(), execNode);
      if (execNode.isBooleanMatch()) {
         this.execBooleanRootNode = rootNode;
      } else {
         this.execRootNode = rootNode;
      }

      this.backtracking = execNode.isBacktracking();
   }

   public RegexSource getSource() {
      return this.source;
   }

   public TruffleObject getFlags() {
      return this.flags;
   }

   public int getNumberOfCaptureGroups() {
      return this.numberOfCaptureGroups;
   }

   public TruffleObject getNamedCaptureGroups() {
      return this.namedCaptureGroups;
   }

   public CallTarget getExecCallTarget() {
      if (this.execRootNode == null) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.execRootNode = new RegexRootNode(
            this.language, new TRegexCompilationRequest(this.language, this.execBooleanRootNode.getSource().withoutBooleanMatch()).compile()
         );
      }

      return this.execRootNode.getCallTarget();
   }

   public CallTarget getExecBooleanCallTarget() {
      if (this.execBooleanRootNode == null) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.execBooleanRootNode = new RegexRootNode(
            this.language, new TRegexCompilationRequest(this.language, this.execRootNode.getSource().withBooleanMatch()).compile()
         );
      }

      return this.execBooleanRootNode.getCallTarget();
   }

   public boolean isBacktracking() {
      return this.backtracking;
   }

   public RegexObject.RegexObjectExecMethod getExecMethod() {
      return new RegexObject.RegexObjectExecMethod(this);
   }

   public RegexObject.RegexObjectExecBooleanMethod getExecBooleanMethod() {
      return new RegexObject.RegexObjectExecBooleanMethod(this);
   }

   public RegexObject.RegexObjectExecUTF8Method getExecUTF8Method() {
      return new RegexObject.RegexObjectExecUTF8Method(this);
   }

   @Override
   public TruffleReadOnlyKeysArray getKeys() {
      return KEYS;
   }

   @Override
   public boolean isMemberReadableImpl(String symbol) {
      switch (symbol) {
         case "exec":
         case "execBoolean":
         case "execBytes":
         case "pattern":
         case "flags":
         case "groupCount":
         case "groups":
         case "isBacktracking":
            return true;
         default:
            return false;
      }
   }

   @Override
   public Object readMemberImpl(String symbol) throws UnknownIdentifierException {
      switch (symbol) {
         case "exec":
            return this.getExecMethod();
         case "execBoolean":
            return this.getExecBooleanMethod();
         case "execBytes":
            return this.getExecUTF8Method();
         case "pattern":
            return this.getSource().getPattern();
         case "flags":
            return this.getFlags();
         case "groupCount":
            return this.getNumberOfCaptureGroups();
         case "groups":
            return this.getNamedCaptureGroups();
         case "isBacktracking":
            return this.isBacktracking();
         default:
            CompilerDirectives.transferToInterpreterAndInvalidate();
            throw UnknownIdentifierException.create(symbol);
      }
   }

   @ExportMessage
   Object invokeMember(String member, Object[] args, @Cached ToLongNode toLongNode, @Cached RegexObject.InvokeCacheNode invokeCache) throws UnknownIdentifierException, ArityException, UnsupportedTypeException, UnsupportedMessageException {
      if (args.length != 2) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         throw ArityException.create(2, 2, args.length);
      } else {
         Object input = args[0];
         long fromIndex = toLongNode.execute(args[1]);
         return fromIndex > 2147483647L ? RegexResult.getNoMatchInstance() : invokeCache.execute(member, this, input, (int)fromIndex);
      }
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public String toString() {
      return "TRegexObject{source=" + this.source + "}";
   }

   @ImportStatic(RegexObject.class)
   @GenerateUncached
   abstract static class ExecCompiledRegexNode extends Node {
      abstract Object execute(CallTarget receiver, Object input, int fromIndex) throws UnsupportedMessageException, ArityException, UnsupportedTypeException;

      @Specialization(guards = "receiver == cachedCallTarget", limit = "4")
      static Object executeDirectCall(
         CallTarget receiver,
         Object input,
         int fromIndex,
         @Cached("receiver") CallTarget cachedCallTarget,
         @Cached("create(cachedCallTarget)") DirectCallNode directCallNode
      ) {
         return directCallNode.call(input, fromIndex);
      }

      @Specialization(replaces = "executeDirectCall")
      @ReportPolymorphism.Megamorphic
      static Object executeIndirectCall(CallTarget receiver, Object input, int fromIndex, @Cached IndirectCallNode indirectCallNode) {
         return indirectCallNode.call(receiver, input, fromIndex);
      }
   }

   @ImportStatic(RegexObject.class)
   @GenerateUncached
   abstract static class InvokeCacheNode extends Node {
      abstract Object execute(String symbol, RegexObject receiver, Object input, int fromIndex) throws UnsupportedMessageException, ArityException, UnsupportedTypeException, UnknownIdentifierException;

      @Specialization(guards = {"symbol == cachedSymbol", "cachedSymbol.equals(PROP_EXEC)"}, limit = "3")
      Object execIdentity(
         String symbol,
         RegexObject receiver,
         Object input,
         int fromIndex,
         @Cached("symbol") String cachedSymbol,
         @Cached ExpectStringOrTruffleObjectNode expectStringOrTruffleObjectNode,
         @Cached RegexObject.ExecCompiledRegexNode execNode
      ) throws UnsupportedMessageException, ArityException, UnsupportedTypeException {
         return execNode.execute(receiver.getExecCallTarget(), expectStringOrTruffleObjectNode.execute(input), fromIndex);
      }

      @Specialization(guards = {"symbol.equals(cachedSymbol)", "cachedSymbol.equals(PROP_EXEC)"}, limit = "3", replaces = "execIdentity")
      Object execEquals(
         String symbol,
         RegexObject receiver,
         Object input,
         int fromIndex,
         @Cached("symbol") String cachedSymbol,
         @Cached ExpectStringOrTruffleObjectNode expectStringOrTruffleObjectNode,
         @Cached RegexObject.ExecCompiledRegexNode execNode
      ) throws UnsupportedMessageException, ArityException, UnsupportedTypeException {
         return execNode.execute(receiver.getExecCallTarget(), expectStringOrTruffleObjectNode.execute(input), fromIndex);
      }

      @Specialization(guards = {"symbol == cachedSymbol", "cachedSymbol.equals(PROP_EXEC_BOOLEAN)"}, limit = "3")
      boolean execBooleanIdentity(
         String symbol,
         RegexObject receiver,
         Object input,
         int fromIndex,
         @Cached("symbol") String cachedSymbol,
         @Cached ExpectStringOrTruffleObjectNode expectStringOrTruffleObjectNode,
         @Cached RegexObject.ExecCompiledRegexNode execNode
      ) throws UnsupportedMessageException, ArityException, UnsupportedTypeException {
         return execNode.execute(receiver.getExecBooleanCallTarget(), expectStringOrTruffleObjectNode.execute(input), fromIndex)
            != RegexResult.getNoMatchInstance();
      }

      @Specialization(guards = {"symbol.equals(cachedSymbol)", "cachedSymbol.equals(PROP_EXEC_BOOLEAN)"}, limit = "3", replaces = "execBooleanIdentity")
      boolean execBooleanEquals(
         String symbol,
         RegexObject receiver,
         Object input,
         int fromIndex,
         @Cached("symbol") String cachedSymbol,
         @Cached ExpectStringOrTruffleObjectNode expectStringOrTruffleObjectNode,
         @Cached RegexObject.ExecCompiledRegexNode execNode
      ) throws UnsupportedMessageException, ArityException, UnsupportedTypeException {
         return execNode.execute(receiver.getExecBooleanCallTarget(), expectStringOrTruffleObjectNode.execute(input), fromIndex)
            != RegexResult.getNoMatchInstance();
      }

      @Specialization(guards = {"symbol == cachedSymbol", "cachedSymbol.equals(PROP_EXEC_BYTES)"}, limit = "3")
      Object execBytesIdentity(
         String symbol,
         RegexObject receiver,
         Object input,
         int fromIndex,
         @Cached("symbol") String cachedSymbol,
         @Cached ExpectByteArrayHostObjectNode expectByteArrayHostObjectNode,
         @Cached RegexObject.ExecCompiledRegexNode execNode
      ) throws UnsupportedMessageException, ArityException, UnsupportedTypeException {
         return execNode.execute(receiver.getExecCallTarget(), expectByteArrayHostObjectNode.execute(input), fromIndex);
      }

      @Specialization(guards = {"symbol.equals(cachedSymbol)", "cachedSymbol.equals(PROP_EXEC_BYTES)"}, limit = "3", replaces = "execBytesIdentity")
      Object execBytesEquals(
         String symbol,
         RegexObject receiver,
         Object input,
         int fromIndex,
         @Cached("symbol") String cachedSymbol,
         @Cached ExpectByteArrayHostObjectNode expectByteArrayHostObjectNode,
         @Cached RegexObject.ExecCompiledRegexNode execNode
      ) throws UnsupportedMessageException, ArityException, UnsupportedTypeException {
         return execNode.execute(receiver.getExecCallTarget(), expectByteArrayHostObjectNode.execute(input), fromIndex);
      }

      @Specialization(replaces = {"execEquals", "execBooleanEquals", "execBytesEquals"})
      @ReportPolymorphism.Megamorphic
      static Object invokeGeneric(
         String symbol,
         RegexObject receiver,
         Object input,
         int fromIndex,
         @Cached ExpectStringOrTruffleObjectNode expectStringOrTruffleObjectNode,
         @Cached ExpectByteArrayHostObjectNode expectByteArrayHostObjectNode,
         @Cached RegexObject.ExecCompiledRegexNode execNode
      ) throws UnsupportedMessageException, ArityException, UnsupportedTypeException, UnknownIdentifierException {
         switch (symbol) {
            case "exec":
               return execNode.execute(receiver.getExecCallTarget(), expectStringOrTruffleObjectNode.execute(input), fromIndex);
            case "execBoolean":
               return execNode.execute(receiver.getExecBooleanCallTarget(), expectStringOrTruffleObjectNode.execute(input), fromIndex)
                  != RegexResult.getNoMatchInstance();
            case "execBytes":
               return execNode.execute(receiver.getExecCallTarget(), expectByteArrayHostObjectNode.execute(input), fromIndex);
            default:
               CompilerDirectives.transferToInterpreterAndInvalidate();
               throw UnknownIdentifierException.create(symbol);
         }
      }
   }

   @ExportMessage
   abstract static class IsMemberInvocable {
      @Specialization(guards = {"symbol == cachedSymbol", "result"}, limit = "3")
      static boolean cacheIdentity(
         RegexObject receiver, String symbol, @Cached("symbol") String cachedSymbol, @Cached("isInvocable(receiver, cachedSymbol)") boolean result
      ) {
         return result;
      }

      @Specialization(guards = {"symbol.equals(cachedSymbol)", "result"}, limit = "3", replaces = "cacheIdentity")
      static boolean cacheEquals(
         RegexObject receiver, String symbol, @Cached("symbol") String cachedSymbol, @Cached("isInvocable(receiver, cachedSymbol)") boolean result
      ) {
         return result;
      }

      @Specialization(replaces = "cacheEquals")
      static boolean isInvocable(RegexObject receiver, String symbol) {
         return "exec".equals(symbol) || "execBoolean".equals(symbol) || "execBytes".equals(symbol);
      }
   }

   @ExportLibrary(InteropLibrary.class)
   public static final class RegexObjectExecBooleanMethod extends AbstractRegexObject {
      private final RegexObject regex;

      public RegexObjectExecBooleanMethod(RegexObject regex) {
         this.regex = regex;
      }

      public RegexObject getRegexObject() {
         return this.regex;
      }

      @ExportMessage
      boolean isExecutable() {
         return true;
      }

      @ExportMessage
      boolean execute(
         Object[] args,
         @Cached ExpectStringOrTruffleObjectNode expectStringOrTruffleObjectNode,
         @Cached ToLongNode toLongNode,
         @Cached RegexObject.ExecCompiledRegexNode execNode
      ) throws ArityException, UnsupportedTypeException, UnsupportedMessageException {
         if (args.length != 2) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            throw ArityException.create(2, 2, args.length);
         } else {
            Object input = expectStringOrTruffleObjectNode.execute(args[0]);
            long fromIndex = toLongNode.execute(args[1]);
            return fromIndex > 2147483647L
               ? false
               : execNode.execute(this.getRegexObject().getExecBooleanCallTarget(), input, (int)fromIndex) != RegexResult.getNoMatchInstance();
         }
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public String toString() {
         return "TRegexObjectExecMethod{regex=" + this.regex + "}";
      }
   }

   @ExportLibrary(InteropLibrary.class)
   public static final class RegexObjectExecMethod extends AbstractRegexObject {
      private final RegexObject regex;

      public RegexObjectExecMethod(RegexObject regex) {
         this.regex = regex;
      }

      public RegexObject getRegexObject() {
         return this.regex;
      }

      @ExportMessage
      boolean isExecutable() {
         return true;
      }

      @ExportMessage
      Object execute(
         Object[] args,
         @Cached ExpectStringOrTruffleObjectNode expectStringOrTruffleObjectNode,
         @Cached ToLongNode toLongNode,
         @Cached RegexObject.ExecCompiledRegexNode execNode
      ) throws ArityException, UnsupportedTypeException, UnsupportedMessageException {
         if (args.length != 2) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            throw ArityException.create(2, 2, args.length);
         } else {
            Object input = expectStringOrTruffleObjectNode.execute(args[0]);
            long fromIndex = toLongNode.execute(args[1]);
            return fromIndex > 2147483647L
               ? RegexResult.getNoMatchInstance()
               : execNode.execute(this.getRegexObject().getExecCallTarget(), input, (int)fromIndex);
         }
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public String toString() {
         return "TRegexObjectExecMethod{regex=" + this.regex + "}";
      }
   }

   @ExportLibrary(InteropLibrary.class)
   public static final class RegexObjectExecUTF8Method extends AbstractRegexObject {
      private final RegexObject regex;

      public RegexObjectExecUTF8Method(RegexObject regex) {
         this.regex = regex;
      }

      public RegexObject getRegexObject() {
         return this.regex;
      }

      @ExportMessage
      boolean isExecutable() {
         return true;
      }

      @ExportMessage
      Object execute(
         Object[] args,
         @Cached ExpectByteArrayHostObjectNode expectByteArrayHostObjectNode,
         @Cached ToLongNode toLongNode,
         @Cached RegexObject.ExecCompiledRegexNode execNode
      ) throws ArityException, UnsupportedTypeException, UnsupportedMessageException {
         RegexObject regexObj = this.getRegexObject();
         if (args.length != 2) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            throw ArityException.create(2, 2, args.length);
         } else {
            byte[] input = expectByteArrayHostObjectNode.execute(args[0]);
            long fromIndex = toLongNode.execute(args[1]);
            return fromIndex > 2147483647L ? RegexResult.getNoMatchInstance() : execNode.execute(regexObj.getExecCallTarget(), input, (int)fromIndex);
         }
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public String toString() {
         return "TRegexObjectExecUTF8Method{regex=" + this.regex + "}";
      }
   }
}
