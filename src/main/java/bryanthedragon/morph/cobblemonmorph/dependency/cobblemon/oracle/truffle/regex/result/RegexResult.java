package com.oracle.truffle.regex.result;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.GenerateUncached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.ReportPolymorphism;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.ArityException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.interop.UnsupportedTypeException;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.regex.AbstractConstantKeysObject;
import com.oracle.truffle.regex.AbstractRegexObject;
import com.oracle.truffle.regex.runtime.nodes.DispatchNode;
import com.oracle.truffle.regex.runtime.nodes.ToIntNode;
import com.oracle.truffle.regex.util.EmptyArrays;
import com.oracle.truffle.regex.util.TruffleReadOnlyKeysArray;
import java.util.Arrays;

@ExportLibrary(InteropLibrary.class)
public final class RegexResult extends AbstractConstantKeysObject {
   static final String PROP_IS_MATCH = "isMatch";
   static final String PROP_GET_START = "getStart";
   static final String PROP_GET_END = "getEnd";
   static final String PROP_LAST_GROUP = "lastGroup";
   private static final TruffleReadOnlyKeysArray KEYS = new TruffleReadOnlyKeysArray("isMatch", "getStart", "getEnd", "lastGroup");
   private final Object input;
   private final int fromIndex;
   private final int start;
   private final int end;
   private int[] result;
   private final CallTarget lazyCallTarget;
   private static final RegexResult NO_MATCH_RESULT = new RegexResult(null, -1, -1, -1, EmptyArrays.INT, null);
   private static final RegexResult BOOLEAN_MATCH_RESULT = new RegexResult(null, -1, -1, -1, EmptyArrays.INT, null);
   private static final int INVALID_RESULT_INDEX = -1;

   protected RegexResult(Object input, int fromIndex, int start, int end, int[] result, CallTarget lazyCallTarget) {
      this.input = input;
      this.fromIndex = fromIndex;
      this.start = start;
      this.end = end;
      this.result = result;
      this.lazyCallTarget = lazyCallTarget;
   }

   public static RegexResult getNoMatchInstance() {
      return NO_MATCH_RESULT;
   }

   public static RegexResult getBooleanMatchInstance() {
      return BOOLEAN_MATCH_RESULT;
   }

   public static RegexResult create(int start, int end) {
      return new RegexResult(null, -1, 0, 0, new int[]{start, end}, null);
   }

   public static RegexResult create(int[] result) {
      assert result != null && result.length >= 2;

      return new RegexResult(null, -1, 0, 0, result, null);
   }

   public static RegexResult createFromExecutorResult(Object executorResult) {
      return executorResult == null ? getNoMatchInstance() : create((int[])executorResult);
   }

   public static RegexResult createLazy(Object input, int fromIndex, int start, int end, CallTarget lazyCallTarget) {
      return new RegexResult(input, fromIndex, start, end, null, lazyCallTarget);
   }

   public Object getInput() {
      return this.input;
   }

   public int getFromIndex() {
      return this.fromIndex;
   }

   public int getStart() {
      return this.start;
   }

   public int getEnd() {
      return this.end;
   }

   public void setResult(int[] result) {
      this.result = result;
   }

   public int getStart(int groupNumber) {
      int index = groupNumber * 2;
      return groupNumber >= this.result.length >> 1 ? -1 : this.result[index];
   }

   public int getEnd(int groupNumber) {
      int index = groupNumber * 2 + 1;
      return groupNumber >= this.result.length >> 1 ? -1 : this.result[index];
   }

   public int getLastGroup() {
      return (this.result.length & 1) == 0 ? -1 : this.result[this.result.length - 1];
   }

   @ExportMessage
   @Override
   public Object getMembers(boolean includeInternal) {
      return KEYS;
   }

   @Override
   public TruffleReadOnlyKeysArray getKeys() {
      return KEYS;
   }

   @Override
   public boolean isMemberReadableImpl(String symbol) {
      switch (symbol) {
         case "isMatch":
         case "getStart":
         case "getEnd":
         case "lastGroup":
            return true;
         default:
            return false;
      }
   }

   @Override
   public Object readMemberImpl(String symbol) throws UnknownIdentifierException {
      switch (symbol) {
         case "isMatch":
            return this != getNoMatchInstance();
         case "getStart":
            return new RegexResult.RegexResultGetStartMethod(this);
         case "getEnd":
            return new RegexResult.RegexResultGetEndMethod(this);
         case "lastGroup":
            return this.getLastGroup();
         default:
            CompilerDirectives.transferToInterpreterAndInvalidate();
            throw UnknownIdentifierException.create(symbol);
      }
   }

   @ExportMessage
   Object invokeMember(String member, Object[] args, @Cached ToIntNode toIntNode, @Cached RegexResult.InvokeCacheNode invokeCache) throws UnknownIdentifierException, ArityException, UnsupportedTypeException {
      if (args.length != 1) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         throw ArityException.create(1, 1, args.length);
      } else {
         return invokeCache.execute(this, member, toIntNode.execute(args[0]));
      }
   }

   @CompilerDirectives.TruffleBoundary
   public void debugForceEvaluation() {
      CompilerAsserts.neverPartOfCompilation();

      assert this != getNoMatchInstance();

      if (this.result == null) {
         this.lazyCallTarget.call(this);
      }

      assert this.result != null;
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public String toString() {
      if (this == getNoMatchInstance()) {
         return "NO_MATCH";
      } else {
         return this.result == null ? "[ _lazy_ ]" : Arrays.toString(this.result);
      }
   }

   @CompilerDirectives.TruffleBoundary
   @ExportMessage
   @Override
   public Object toDisplayString(boolean allowSideEffects) {
      if (allowSideEffects) {
         this.debugForceEvaluation();
         return "TRegexResult" + this;
      } else {
         return "TRegexResult";
      }
   }

   @ImportStatic(RegexResult.class)
   @GenerateUncached
   abstract static class InvokeCacheNode extends Node {
      abstract Object execute(RegexResult receiver, String symbol, int groupNumber) throws UnknownIdentifierException;

      @Specialization(guards = {"symbol == cachedSymbol", "cachedSymbol.equals(PROP_GET_START)"}, limit = "2")
      Object getStartIdentity(
         RegexResult receiver, String symbol, int groupNumber, @Cached("symbol") String cachedSymbol, @Cached RegexResult.RegexResultGetStartNode getStartNode
      ) {
         return getStartNode.execute(receiver, groupNumber);
      }

      @Specialization(guards = {"symbol.equals(cachedSymbol)", "cachedSymbol.equals(PROP_GET_START)"}, limit = "2", replaces = "getStartIdentity")
      Object getStartEquals(
         RegexResult receiver, String symbol, int groupNumber, @Cached("symbol") String cachedSymbol, @Cached RegexResult.RegexResultGetStartNode getStartNode
      ) {
         return getStartNode.execute(receiver, groupNumber);
      }

      @Specialization(guards = {"symbol == cachedSymbol", "cachedSymbol.equals(PROP_GET_END)"}, limit = "2")
      Object getEndIdentity(
         RegexResult receiver, String symbol, int groupNumber, @Cached("symbol") String cachedSymbol, @Cached RegexResult.RegexResultGetEndNode getEndNode
      ) {
         return getEndNode.execute(receiver, groupNumber);
      }

      @Specialization(guards = {"symbol.equals(cachedSymbol)", "cachedSymbol.equals(PROP_GET_END)"}, limit = "2", replaces = "getEndIdentity")
      Object getEndEquals(
         RegexResult receiver, String symbol, int groupNumber, @Cached("symbol") String cachedSymbol, @Cached RegexResult.RegexResultGetEndNode getEndNode
      ) {
         return getEndNode.execute(receiver, groupNumber);
      }

      @Specialization(replaces = {"getStartEquals", "getEndEquals"})
      @ReportPolymorphism.Megamorphic
      static Object invokeGeneric(
         RegexResult receiver,
         String symbol,
         int groupNumber,
         @Cached RegexResult.RegexResultGetStartNode getStartNode,
         @Cached RegexResult.RegexResultGetEndNode getEndNode
      ) throws UnknownIdentifierException {
         switch (symbol) {
            case "getStart":
               return getStartNode.execute(receiver, groupNumber);
            case "getEnd":
               return getEndNode.execute(receiver, groupNumber);
            default:
               CompilerDirectives.transferToInterpreterAndInvalidate();
               throw UnknownIdentifierException.create(symbol);
         }
      }
   }

   @ExportMessage
   abstract static class IsMemberInvocable {
      @Specialization(guards = {"symbol == cachedSymbol", "result"}, limit = "2")
      static boolean cacheIdentity(
         RegexResult receiver, String symbol, @Cached("symbol") String cachedSymbol, @Cached("isInvocable(receiver, cachedSymbol)") boolean result
      ) {
         return result;
      }

      @Specialization(guards = {"symbol.equals(cachedSymbol)", "result"}, limit = "2", replaces = "cacheIdentity")
      static boolean cacheEquals(
         RegexResult receiver, String symbol, @Cached("symbol") String cachedSymbol, @Cached("isInvocable(receiver, cachedSymbol)") boolean result
      ) {
         return result;
      }

      @Specialization(replaces = "cacheEquals")
      static boolean isInvocable(RegexResult receiver, String symbol) {
         return "getStart".equals(symbol) || "getEnd".equals(symbol);
      }
   }

   @ExportMessage
   abstract static class IsMemberReadable {
      @Specialization(guards = {"symbol == cachedSymbol", "result"}, limit = "4")
      static boolean cacheIdentity(
         RegexResult receiver, String symbol, @Cached("symbol") String cachedSymbol, @Cached("isReadable(receiver, cachedSymbol)") boolean result
      ) {
         return result;
      }

      @Specialization(guards = {"symbol.equals(cachedSymbol)", "result"}, limit = "4", replaces = "cacheIdentity")
      static boolean cacheEquals(
         RegexResult receiver, String symbol, @Cached("symbol") String cachedSymbol, @Cached("isReadable(receiver, cachedSymbol)") boolean result
      ) {
         return result;
      }

      @Specialization(replaces = "cacheEquals")
      static boolean isReadable(RegexResult receiver, String symbol) {
         return RegexResult.KEYS.contains(symbol);
      }
   }

   @ExportMessage
   abstract static class ReadMember {
      @Specialization(guards = {"symbol == cachedSymbol", "cachedSymbol.equals(PROP_IS_MATCH)"}, limit = "2")
      static boolean isMatchIdentity(RegexResult receiver, String symbol, @Cached("symbol") String cachedSymbol) {
         return receiver != RegexResult.getNoMatchInstance();
      }

      @Specialization(guards = {"symbol.equals(cachedSymbol)", "cachedSymbol.equals(PROP_IS_MATCH)"}, limit = "2", replaces = "isMatchIdentity")
      static boolean isMatchEquals(RegexResult receiver, String symbol, @Cached("symbol") String cachedSymbol) {
         return receiver != RegexResult.getNoMatchInstance();
      }

      @Specialization(guards = {"symbol == cachedSymbol", "cachedSymbol.equals(PROP_GET_START)"}, limit = "2")
      static RegexResult.RegexResultGetStartMethod getStartIdentity(RegexResult receiver, String symbol, @Cached("symbol") String cachedSymbol) {
         return new RegexResult.RegexResultGetStartMethod(receiver);
      }

      @Specialization(guards = {"symbol.equals(cachedSymbol)", "cachedSymbol.equals(PROP_GET_START)"}, limit = "2", replaces = "getStartIdentity")
      static RegexResult.RegexResultGetStartMethod getStartEquals(RegexResult receiver, String symbol, @Cached("symbol") String cachedSymbol) {
         return new RegexResult.RegexResultGetStartMethod(receiver);
      }

      @Specialization(guards = {"symbol == cachedSymbol", "cachedSymbol.equals(PROP_GET_END)"}, limit = "2")
      static RegexResult.RegexResultGetEndMethod getEndIdentity(RegexResult receiver, String symbol, @Cached("symbol") String cachedSymbol) {
         return new RegexResult.RegexResultGetEndMethod(receiver);
      }

      @Specialization(guards = {"symbol.equals(cachedSymbol)", "cachedSymbol.equals(PROP_GET_END)"}, limit = "2", replaces = "getEndIdentity")
      static RegexResult.RegexResultGetEndMethod getEndEquals(RegexResult receiver, String symbol, @Cached("symbol") String cachedSymbol) {
         return new RegexResult.RegexResultGetEndMethod(receiver);
      }

      @Specialization(guards = {"symbol == cachedSymbol", "cachedSymbol.equals(PROP_LAST_GROUP)"}, limit = "2")
      static int lastGroupIdentity(RegexResult receiver, String symbol, @Cached("symbol") String cachedSymbol) {
         return receiver.getLastGroup();
      }

      @Specialization(guards = {"symbol.equals(cachedSymbol)", "cachedSymbol.equals(PROP_LAST_GROUP)"}, limit = "2", replaces = "lastGroupIdentity")
      static int lastGroupEquals(RegexResult receiver, String symbol, @Cached("symbol") String cachedSymbol) {
         return receiver.getLastGroup();
      }

      @Specialization(replaces = {"isMatchEquals", "getStartEquals", "getEndEquals"})
      @ReportPolymorphism.Megamorphic
      static Object readGeneric(RegexResult receiver, String symbol) throws UnknownIdentifierException {
         switch (symbol) {
            case "isMatch":
               return receiver != RegexResult.getNoMatchInstance();
            case "getStart":
               return new RegexResult.RegexResultGetStartMethod(receiver);
            case "getEnd":
               return new RegexResult.RegexResultGetEndMethod(receiver);
            case "lastGroup":
               return receiver.getLastGroup();
            default:
               CompilerDirectives.transferToInterpreterAndInvalidate();
               throw UnknownIdentifierException.create(symbol);
         }
      }
   }

   @ExportLibrary(InteropLibrary.class)
   static final class RegexResultGetEndMethod extends AbstractRegexObject {
      private final RegexResult result;

      RegexResultGetEndMethod(RegexResult result) {
         this.result = result;
      }

      @ExportMessage
      boolean isExecutable() {
         return true;
      }

      @ExportMessage
      int execute(Object[] args, @Cached ToIntNode toIntNode, @Cached RegexResult.RegexResultGetEndNode getEndNode) throws ArityException, UnsupportedTypeException {
         if (args.length != 1) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            throw ArityException.create(1, 1, args.length);
         } else {
            return getEndNode.execute(this.result, toIntNode.execute(args[0]));
         }
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public String toString() {
         return "TRegexResultGetEndMethod{result=" + this.result + "}";
      }
   }

   @GenerateUncached
   abstract static class RegexResultGetEndNode extends Node {
      abstract int execute(Object receiver, int groupNumber);

      @Specialization
      static int doResult(RegexResult receiver, int groupNumber, @Cached BranchProfile lazyProfile, @Cached DispatchNode getIndicesCall) {
         if (receiver.result == null) {
            assert receiver.lazyCallTarget != null;

            lazyProfile.enter();
            getIndicesCall.execute(receiver.lazyCallTarget, receiver);
         }

         int i = groupNumber * 2 + 1;
         return i >= 0 && i < receiver.result.length ? receiver.result[i] : -1;
      }
   }

   @ExportLibrary(InteropLibrary.class)
   static final class RegexResultGetStartMethod extends AbstractRegexObject {
      private final RegexResult result;

      RegexResultGetStartMethod(RegexResult result) {
         this.result = result;
      }

      @ExportMessage
      boolean isExecutable() {
         return true;
      }

      @ExportMessage
      int execute(Object[] args, @Cached ToIntNode toIntNode, @Cached RegexResult.RegexResultGetStartNode getStartNode) throws ArityException, UnsupportedTypeException {
         if (args.length != 1) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            throw ArityException.create(1, 1, args.length);
         } else {
            return getStartNode.execute(this.result, toIntNode.execute(args[0]));
         }
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public String toString() {
         return "TRegexResultGetStartMethod{result=" + this.result + "}";
      }
   }

   @GenerateUncached
   public abstract static class RegexResultGetStartNode extends Node {
      public static RegexResult.RegexResultGetStartNode create() {
         return RegexResultFactory.RegexResultGetStartNodeGen.create();
      }

      public abstract int execute(Object receiver, int groupNumber);

      @Specialization
      static int doResult(RegexResult receiver, int groupNumber, @Cached BranchProfile lazyProfile, @Cached DispatchNode getIndicesCall) {
         if (receiver.result == null) {
            assert receiver.lazyCallTarget != null;

            lazyProfile.enter();
            getIndicesCall.execute(receiver.lazyCallTarget, receiver);
         }

         int i = groupNumber * 2;
         return i >= 0 && i < receiver.result.length ? receiver.result[i] : -1;
      }

      public static RegexResult.RegexResultGetStartNode getUncached() {
         return RegexResultFactory.RegexResultGetStartNodeGen.getUncached();
      }
   }
}
