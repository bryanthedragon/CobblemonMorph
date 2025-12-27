package com.oracle.truffle.regex.tregex.nodes;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.DirectCallNode;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.api.profiles.ValueProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.regex.RegexLanguage;
import com.oracle.truffle.regex.RegexRootNode;
import com.oracle.truffle.regex.util.TRegexGuards;
import java.lang.reflect.Field;
import sun.misc.Unsafe;

@ImportStatic({TRegexGuards.class, TruffleString.CodeRange.class})
public abstract class TRegexExecutorEntryNode extends Node {
   private static final Unsafe UNSAFE;
   private static final long coderFieldOffset;
   private final RegexLanguage language;
   @Node.Child
   TRegexExecutorBaseNode executor;

   private static Unsafe getUnsafe() {
      try {
         return Unsafe.getUnsafe();
      } catch (SecurityException var3) {
         try {
            Field theUnsafeInstance = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafeInstance.setAccessible(true);
            return (Unsafe)theUnsafeInstance.get(Unsafe.class);
         } catch (Exception var2) {
            throw new RuntimeException("exception while trying to get Unsafe.theUnsafe via reflection:", var2);
         }
      }
   }

   public TRegexExecutorEntryNode(RegexLanguage language, TRegexExecutorNode executor) {
      this.language = language;
      this.executor = executor;
   }

   public static TRegexExecutorEntryNode create(RegexLanguage language, TRegexExecutorNode executor) {
      return executor == null ? null : TRegexExecutorEntryNodeGen.create(language, executor);
   }

   public TRegexExecutorNode getExecutor() {
      return (TRegexExecutorNode)(
         this.executor instanceof TRegexExecutorBaseNodeWrapper ? ((TRegexExecutorBaseNodeWrapper)this.executor).getDelegateNode() : this.executor
      );
   }

   public abstract Object execute(VirtualFrame frame, Object input, int fromIndex, int index, int maxIndex);

   @Specialization
   Object doByteArray(
      VirtualFrame frame, byte[] input, int fromIndex, int index, int maxIndex, @Cached("createCallTarget(BROKEN, false)") DirectCallNode callNode
   ) {
      return this.runExecutor(frame, input, fromIndex, index, maxIndex, callNode, TruffleString.CodeRange.BROKEN, false);
   }

   @Specialization(guards = "isCompactString(input)")
   Object doStringCompact(
      VirtualFrame frame, String input, int fromIndex, int index, int maxIndex, @Cached("createCallTarget(LATIN_1, false)") DirectCallNode callNode
   ) {
      return this.runExecutor(frame, input, fromIndex, index, maxIndex, callNode, TruffleString.CodeRange.LATIN_1, false);
   }

   @Specialization(guards = "!isCompactString(input)")
   Object doStringNonCompact(
      VirtualFrame frame, String input, int fromIndex, int index, int maxIndex, @Cached("createCallTarget(BROKEN, false)") DirectCallNode callNode
   ) {
      return this.runExecutor(frame, input, fromIndex, index, maxIndex, callNode, TruffleString.CodeRange.BROKEN, false);
   }

   @Specialization(guards = "codeRangeEqualsNode.execute(input, cachedCodeRange)", limit = "5")
   Object doTString(
      VirtualFrame frame,
      TruffleString input,
      int fromIndex,
      int index,
      int maxIndex,
      @Cached TruffleString.MaterializeNode materializeNode,
      @Cached TruffleString.GetCodeRangeNode codeRangeNode,
      @Cached TruffleString.CodeRangeEqualsNode codeRangeEqualsNode,
      @Cached("codeRangeNode.execute(input, getExecutor().getEncoding().getTStringEncoding())") TruffleString.CodeRange cachedCodeRange,
      @Cached("createCallTarget(cachedCodeRange, true)") DirectCallNode callNode
   ) {
      materializeNode.execute(input, this.getExecutor().getEncoding().getTStringEncoding());
      return this.runExecutor(frame, input, fromIndex, index, maxIndex, callNode, cachedCodeRange, true);
   }

   @Specialization(guards = "neitherByteArrayNorString(input)")
   Object doTruffleObject(
      VirtualFrame frame,
      Object input,
      int fromIndex,
      int index,
      int maxIndex,
      @Cached("createClassProfile()") ValueProfile inputClassProfile,
      @Cached("createCallTarget(BROKEN, false)") DirectCallNode callNode
   ) {
      return this.runExecutor(frame, inputClassProfile.profile(input), fromIndex, index, maxIndex, callNode, TruffleString.CodeRange.BROKEN, false);
   }

   DirectCallNode createCallTarget(TruffleString.CodeRange codeRange, boolean isTString) {
      return this.getExecutor().getNumberOfTransitions() <= 100
         ? null
         : DirectCallNode.create(
            new TRegexExecutorEntryNode.TRegexExecutorRootNode(this.language, this.getExecutor().shallowCopy(), codeRange, isTString).getCallTarget()
         );
   }

   private Object runExecutor(
      VirtualFrame frame,
      Object input,
      int fromIndex,
      int index,
      int maxIndex,
      DirectCallNode callNode,
      TruffleString.CodeRange cachedCodeRange,
      boolean isTString
   ) {
      CompilerAsserts.partialEvaluationConstant(cachedCodeRange);
      CompilerAsserts.partialEvaluationConstant(isTString);
      CompilerAsserts.partialEvaluationConstant(callNode);
      return callNode == null
         ? this.executor.execute(frame, this.getExecutor().createLocals(input, fromIndex, index, maxIndex), cachedCodeRange, isTString)
         : callNode.call(input, fromIndex, index, maxIndex);
   }

   static boolean isCompactString(String str) {
      return UNSAFE != null && UNSAFE.getByte(str, coderFieldOffset) == 0;
   }

   static {
      String javaVersion = System.getProperty("java.specification.version");
      if (javaVersion != null && javaVersion.compareTo("1.9") < 0) {
         UNSAFE = null;
         coderFieldOffset = 0L;
      } else {
         UNSAFE = getUnsafe();

         Field coderField;
         try {
            coderField = String.class.getDeclaredField("coder");
         } catch (NoSuchFieldException var3) {
            throw new RuntimeException("failed to get coder field offset", var3);
         }

         coderFieldOffset = UNSAFE.objectFieldOffset(coderField);
      }
   }

   private static final class TRegexExecutorRootNode extends RootNode {
      @Node.Child
      TRegexExecutorNode executor;
      private final TruffleString.CodeRange codeRange;
      private final boolean isTString;

      private TRegexExecutorRootNode(RegexLanguage language, TRegexExecutorNode executor, TruffleString.CodeRange codeRange, boolean isTString) {
         super(language, RegexRootNode.SHARED_EMPTY_FRAMEDESCRIPTOR);
         this.executor = this.insert(executor);
         this.codeRange = codeRange;
         this.isTString = isTString;
      }

      @Override
      public Object execute(VirtualFrame frame) {
         Object[] arguments = frame.getArguments();
         Object input = arguments[0];
         int fromIndex = (Integer)arguments[1];
         int index = (Integer)arguments[2];
         int maxIndex = (Integer)arguments[3];
         return this.executor.execute(frame, this.executor.createLocals(input, fromIndex, index, maxIndex), this.codeRange, this.isTString);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public String toString() {
         String src = this.executor.getSource().toStringEscaped();
         return "tregex " + this.executor.getName() + " " + this.codeRange + ": " + (src.length() > 30 ? src.substring(0, 30) + "..." : src);
      }
   }
}
