package com.oracle.truffle.js.runtime.util;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.GenerateUncached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.ArityException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.interop.UnsupportedTypeException;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.objects.Undefined;

public final class TRegexUtil {
   private static final String NUMBER_OF_REGEX_RESULT_TYPES = "9";
   private static final int NUMBER_OF_REGEX_RESULT_TYPES_INT = 9;

   private TRegexUtil() {
   }

   public static final class Constants {
      public static final int CAPTURE_GROUP_NO_MATCH = -1;

      private Constants() {
      }
   }

   @GenerateUncached
   public abstract static class InteropIsMemberReadableNode extends Node {
      public abstract boolean execute(Object obj, String key);

      @Specialization(limit = "9")
      static boolean read(Object obj, String key, @CachedLibrary("obj") InteropLibrary objs) {
         return objs.isMemberReadable(obj, key);
      }

      public static TRegexUtil.InteropIsMemberReadableNode create() {
         return TRegexUtilFactory.InteropIsMemberReadableNodeGen.create();
      }
   }

   @GenerateUncached
   public abstract static class InteropReadBooleanMemberNode extends Node {
      public abstract boolean execute(Object obj, String key);

      @Specialization(guards = "objs.isMemberReadable(obj, key)", limit = "9")
      static boolean read(Object obj, String key, @Cached TRegexUtil.InteropToBooleanNode coerceNode, @CachedLibrary("obj") InteropLibrary objs) {
         try {
            return coerceNode.execute(objs.readMember(obj, key));
         } catch (UnknownIdentifierException | UnsupportedMessageException var5) {
            throw CompilerDirectives.shouldNotReachHere(var5);
         }
      }

      public static TRegexUtil.InteropReadBooleanMemberNode create() {
         return TRegexUtilFactory.InteropReadBooleanMemberNodeGen.create();
      }

      public static TRegexUtil.InteropReadBooleanMemberNode getUncached() {
         return TRegexUtilFactory.InteropReadBooleanMemberNodeGen.getUncached();
      }
   }

   @GenerateUncached
   public abstract static class InteropReadIntMemberNode extends Node {
      public abstract int execute(Object obj, String key);

      @Specialization(guards = "objs.isMemberReadable(obj, key)", limit = "9")
      static int read(Object obj, String key, @Cached TRegexUtil.InteropToIntNode coerceNode, @CachedLibrary("obj") InteropLibrary objs) {
         try {
            return coerceNode.execute(objs.readMember(obj, key));
         } catch (UnknownIdentifierException | UnsupportedMessageException var5) {
            throw CompilerDirectives.shouldNotReachHere(var5);
         }
      }

      public static TRegexUtil.InteropReadIntMemberNode create() {
         return TRegexUtilFactory.InteropReadIntMemberNodeGen.create();
      }

      public static TRegexUtil.InteropReadIntMemberNode getUncached() {
         return TRegexUtilFactory.InteropReadIntMemberNodeGen.getUncached();
      }
   }

   @GenerateUncached
   public abstract static class InteropReadMemberNode extends Node {
      public abstract Object execute(Object obj, String key);

      @Specialization(guards = "objs.isMemberReadable(obj, key)", limit = "9")
      static Object read(Object obj, String key, @CachedLibrary("obj") InteropLibrary objs) {
         try {
            return objs.readMember(obj, key);
         } catch (UnknownIdentifierException | UnsupportedMessageException var4) {
            throw CompilerDirectives.shouldNotReachHere(var4);
         }
      }

      public static TRegexUtil.InteropReadMemberNode create() {
         return TRegexUtilFactory.InteropReadMemberNodeGen.create();
      }

      public static TRegexUtil.InteropReadMemberNode getUncached() {
         return TRegexUtilFactory.InteropReadMemberNodeGen.getUncached();
      }
   }

   @GenerateUncached
   public abstract static class InteropReadStringMemberNode extends Node {
      public abstract TruffleString execute(Object obj, String key);

      @Specialization(guards = "objs.isMemberReadable(obj, key)", limit = "3")
      static TruffleString read(Object obj, String key, @Cached TRegexUtil.InteropToStringNode coerceNode, @CachedLibrary("obj") InteropLibrary objs) {
         try {
            return coerceNode.execute(objs.readMember(obj, key));
         } catch (UnknownIdentifierException | UnsupportedMessageException var5) {
            throw CompilerDirectives.shouldNotReachHere(var5);
         }
      }

      public static TRegexUtil.InteropReadStringMemberNode create() {
         return TRegexUtilFactory.InteropReadStringMemberNodeGen.create();
      }

      public static TRegexUtil.InteropReadStringMemberNode getUncached() {
         return TRegexUtilFactory.InteropReadStringMemberNodeGen.getUncached();
      }
   }

   @GenerateUncached
   public abstract static class InteropToBooleanNode extends Node {
      public abstract boolean execute(Object obj);

      @Specialization
      static boolean coerceDirect(boolean obj) {
         return obj;
      }

      @Specialization(guards = "objs.isBoolean(obj)", limit = "3")
      static boolean coerce(Object obj, @CachedLibrary("obj") InteropLibrary objs) {
         try {
            return objs.asBoolean(obj);
         } catch (UnsupportedMessageException var3) {
            throw CompilerDirectives.shouldNotReachHere(var3);
         }
      }
   }

   @GenerateUncached
   public abstract static class InteropToIntNode extends Node {
      public abstract int execute(Object obj);

      @Specialization
      static int coerceDirect(int obj) {
         return obj;
      }

      @Specialization(guards = "objs.fitsInInt(obj)", limit = "3")
      static int coerce(Object obj, @CachedLibrary("obj") InteropLibrary objs) {
         try {
            return objs.asInt(obj);
         } catch (UnsupportedMessageException var3) {
            throw CompilerDirectives.shouldNotReachHere(var3);
         }
      }
   }

   @ImportStatic(JSGuards.class)
   @GenerateUncached
   public abstract static class InteropToStringNode extends Node {
      public abstract TruffleString execute(Object obj);

      @Specialization
      static TruffleString coerceJavaString(String obj) {
         return Strings.fromJavaString(obj);
      }

      @Specialization
      static TruffleString coerceDirect(TruffleString obj) {
         return obj;
      }

      @Specialization(guards = {"!isTruffleString(obj)", "objs.isString(obj)"}, limit = "3")
      static TruffleString coerce(Object obj, @CachedLibrary("obj") InteropLibrary objs) {
         try {
            return objs.asTruffleString(obj);
         } catch (UnsupportedMessageException var3) {
            throw CompilerDirectives.shouldNotReachHere(var3);
         }
      }
   }

   @ImportStatic(TRegexUtil.Props.CompiledRegex.class)
   @GenerateUncached
   public abstract static class InvokeExecMethodNode extends Node {
      public abstract Object execute(Object compiledRegex, Object input, long fromIndex);

      @Specialization(guards = "objs.isMemberInvocable(compiledRegex, EXEC)", limit = "3")
      static Object exec(Object compiledRegex, Object input, long fromIndex, @CachedLibrary("compiledRegex") InteropLibrary objs) {
         try {
            return objs.invokeMember(compiledRegex, "exec", input, fromIndex);
         } catch (UnsupportedTypeException | ArityException | UnknownIdentifierException | UnsupportedMessageException var6) {
            throw CompilerDirectives.shouldNotReachHere(var6);
         }
      }

      public static TRegexUtil.InvokeExecMethodNode create() {
         return TRegexUtilFactory.InvokeExecMethodNodeGen.create();
      }

      public static TRegexUtil.InvokeExecMethodNode getUncached() {
         return TRegexUtilFactory.InvokeExecMethodNodeGen.getUncached();
      }
   }

   @GenerateUncached
   public abstract static class InvokeGetGroupBoundariesMethodNode extends Node {
      public abstract int execute(Object regexResult, Object method, int groupNumber);

      @Specialization(guards = "objs.isMemberInvocable(regexResult, method)", limit = "9")
      static int exec(
         Object regexResult, String method, int groupNumber, @CachedLibrary("regexResult") InteropLibrary objs, @Cached TRegexUtil.InteropToIntNode toIntNode
      ) {
         try {
            return toIntNode.execute(objs.invokeMember(regexResult, method, groupNumber));
         } catch (UnsupportedTypeException | ArityException | UnknownIdentifierException | UnsupportedMessageException var6) {
            throw CompilerDirectives.shouldNotReachHere(var6);
         }
      }

      public static TRegexUtil.InvokeGetGroupBoundariesMethodNode create() {
         return TRegexUtilFactory.InvokeGetGroupBoundariesMethodNodeGen.create();
      }

      public static TRegexUtil.InvokeGetGroupBoundariesMethodNode getUncached() {
         return TRegexUtilFactory.InvokeGetGroupBoundariesMethodNodeGen.getUncached();
      }
   }

   public static final class Props {
      private Props() {
      }

      public static final class CompiledRegex {
         public static final String PATTERN = "pattern";
         public static final String FLAGS = "flags";
         public static final String EXEC = "exec";
         public static final String GROUP_COUNT = "groupCount";
         public static final String GROUPS = "groups";

         private CompiledRegex() {
         }
      }

      public static final class Flags {
         public static final String SOURCE = "source";
         public static final String GLOBAL = "global";
         public static final String MULTILINE = "multiline";
         public static final String IGNORE_CASE = "ignoreCase";
         public static final String STICKY = "sticky";
         public static final String UNICODE = "unicode";
         public static final String DOT_ALL = "dotAll";
         public static final String HAS_INDICES = "hasIndices";

         private Flags() {
         }
      }

      public static final class RegexResult {
         public static final String IS_MATCH = "isMatch";
         public static final String GET_START = "getStart";
         public static final String GET_END = "getEnd";

         private RegexResult() {
         }
      }
   }

   public static final class TRegexCompiledRegexAccessor extends Node {
      @Node.Child
      private TRegexUtil.InteropReadStringMemberNode readPatternNode;
      @Node.Child
      private TRegexUtil.InteropReadMemberNode readFlagsNode;
      @Node.Child
      private TRegexUtil.InvokeExecMethodNode invokeExecMethodNode;
      @Node.Child
      private TRegexUtil.InteropReadIntMemberNode readGroupCountNode;
      @Node.Child
      private TRegexUtil.InteropReadMemberNode readGroupsNode;

      private TRegexCompiledRegexAccessor() {
      }

      public static TRegexUtil.TRegexCompiledRegexAccessor create() {
         return new TRegexUtil.TRegexCompiledRegexAccessor();
      }

      public Object pattern(Object compiledRegexObject) {
         return this.getReadPatternNode().execute(compiledRegexObject, "pattern");
      }

      public Object flags(Object compiledRegexObject) {
         return this.getReadFlagsNode().execute(compiledRegexObject, "flags");
      }

      public Object exec(Object compiledRegexObject, Object input, long fromIndex) {
         return this.getInvokeExecMethodNode().execute(compiledRegexObject, input, fromIndex);
      }

      public int groupCount(Object regexResultObject) {
         return this.getReadGroupCountNode().execute(regexResultObject, "groupCount");
      }

      public Object namedCaptureGroups(Object compiledRegexObject) {
         return this.getReadGroupsNode().execute(compiledRegexObject, "groups");
      }

      private TRegexUtil.InteropReadStringMemberNode getReadPatternNode() {
         if (this.readPatternNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.readPatternNode = this.insert(TRegexUtil.InteropReadStringMemberNode.create());
         }

         return this.readPatternNode;
      }

      private TRegexUtil.InteropReadMemberNode getReadFlagsNode() {
         if (this.readFlagsNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.readFlagsNode = this.insert(TRegexUtil.InteropReadMemberNode.create());
         }

         return this.readFlagsNode;
      }

      private TRegexUtil.InvokeExecMethodNode getInvokeExecMethodNode() {
         if (this.invokeExecMethodNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.invokeExecMethodNode = this.insert(TRegexUtil.InvokeExecMethodNode.create());
         }

         return this.invokeExecMethodNode;
      }

      private TRegexUtil.InteropReadIntMemberNode getReadGroupCountNode() {
         if (this.readGroupCountNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.readGroupCountNode = this.insert(TRegexUtil.InteropReadIntMemberNode.create());
         }

         return this.readGroupCountNode;
      }

      private TRegexUtil.InteropReadMemberNode getReadGroupsNode() {
         if (this.readGroupsNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.readGroupsNode = this.insert(TRegexUtil.InteropReadMemberNode.create());
         }

         return this.readGroupsNode;
      }
   }

   public static final class TRegexCompiledRegexSingleFlagAccessor extends Node {
      private final String flag;
      @Node.Child
      private TRegexUtil.InteropReadMemberNode readFlagsObjectNode = TRegexUtil.InteropReadMemberNode.create();
      @Node.Child
      private TRegexUtil.InteropReadBooleanMemberNode readFlagNode = TRegexUtil.InteropReadBooleanMemberNode.create();

      private TRegexCompiledRegexSingleFlagAccessor(String flag) {
         this.flag = flag;
      }

      public static TRegexUtil.TRegexCompiledRegexSingleFlagAccessor create(String flag) {
         return new TRegexUtil.TRegexCompiledRegexSingleFlagAccessor(flag);
      }

      public boolean get(Object compiledRegex) {
         return this.readFlagNode.execute(this.readFlagsObjectNode.execute(compiledRegex, "flags"), this.flag);
      }
   }

   public static final class TRegexFlagsAccessor extends Node {
      @Node.Child
      private TRegexUtil.InteropReadStringMemberNode readSourceNode;
      @Node.Child
      private TRegexUtil.InteropReadBooleanMemberNode readGlobalNode;
      @Node.Child
      private TRegexUtil.InteropReadBooleanMemberNode readMultilineNode;
      @Node.Child
      private TRegexUtil.InteropReadBooleanMemberNode readIgnoreCaseNode;
      @Node.Child
      private TRegexUtil.InteropReadBooleanMemberNode readStickyNode;
      @Node.Child
      private TRegexUtil.InteropReadBooleanMemberNode readUnicodeNode;
      @Node.Child
      private TRegexUtil.InteropReadBooleanMemberNode readDotAllNode;
      @Node.Child
      private TRegexUtil.InteropReadBooleanMemberNode readHasIndicesNode;

      private TRegexFlagsAccessor() {
      }

      public static TRegexUtil.TRegexFlagsAccessor create() {
         return new TRegexUtil.TRegexFlagsAccessor();
      }

      public Object source(Object regexFlagsObject) {
         return this.getReadSourceNode().execute(regexFlagsObject, "source");
      }

      public boolean global(Object regexFlagsObject) {
         return this.getReadGlobalNode().execute(regexFlagsObject, "global");
      }

      public boolean multiline(Object regexFlagsObject) {
         return this.getReadMultilineNode().execute(regexFlagsObject, "multiline");
      }

      public boolean ignoreCase(Object regexFlagsObject) {
         return this.getReadIgnoreCaseNode().execute(regexFlagsObject, "ignoreCase");
      }

      public boolean sticky(Object regexFlagsObject) {
         return this.getReadStickyNode().execute(regexFlagsObject, "sticky");
      }

      public boolean unicode(Object regexFlagsObject) {
         return this.getReadUnicodeNode().execute(regexFlagsObject, "unicode");
      }

      public boolean dotAll(Object regexFlagsObject) {
         return this.getReadDotAllNode().execute(regexFlagsObject, "dotAll");
      }

      public boolean hasIndices(Object regexFlagsObject) {
         return this.getReadHasIndicesNode().execute(regexFlagsObject, "hasIndices");
      }

      private TRegexUtil.InteropReadStringMemberNode getReadSourceNode() {
         if (this.readSourceNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.readSourceNode = this.insert(TRegexUtil.InteropReadStringMemberNode.create());
         }

         return this.readSourceNode;
      }

      private TRegexUtil.InteropReadBooleanMemberNode getReadGlobalNode() {
         if (this.readGlobalNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.readGlobalNode = this.insert(TRegexUtil.InteropReadBooleanMemberNode.create());
         }

         return this.readGlobalNode;
      }

      private TRegexUtil.InteropReadBooleanMemberNode getReadMultilineNode() {
         if (this.readMultilineNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.readMultilineNode = this.insert(TRegexUtil.InteropReadBooleanMemberNode.create());
         }

         return this.readMultilineNode;
      }

      private TRegexUtil.InteropReadBooleanMemberNode getReadIgnoreCaseNode() {
         if (this.readIgnoreCaseNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.readIgnoreCaseNode = this.insert(TRegexUtil.InteropReadBooleanMemberNode.create());
         }

         return this.readIgnoreCaseNode;
      }

      private TRegexUtil.InteropReadBooleanMemberNode getReadStickyNode() {
         if (this.readStickyNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.readStickyNode = this.insert(TRegexUtil.InteropReadBooleanMemberNode.create());
         }

         return this.readStickyNode;
      }

      private TRegexUtil.InteropReadBooleanMemberNode getReadUnicodeNode() {
         if (this.readUnicodeNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.readUnicodeNode = this.insert(TRegexUtil.InteropReadBooleanMemberNode.create());
         }

         return this.readUnicodeNode;
      }

      private TRegexUtil.InteropReadBooleanMemberNode getReadDotAllNode() {
         if (this.readDotAllNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.readDotAllNode = this.insert(TRegexUtil.InteropReadBooleanMemberNode.create());
         }

         return this.readDotAllNode;
      }

      private TRegexUtil.InteropReadBooleanMemberNode getReadHasIndicesNode() {
         if (this.readHasIndicesNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.readHasIndicesNode = this.insert(TRegexUtil.InteropReadBooleanMemberNode.create());
         }

         return this.readHasIndicesNode;
      }
   }

   public static final class TRegexMaterializeResultNode extends Node {
      private static final TRegexUtil.TRegexMaterializeResultNode UNCACHED = new TRegexUtil.TRegexMaterializeResultNode(false);
      @Node.Child
      TRegexUtil.TRegexResultAccessor resultAccessor;
      @Node.Child
      TruffleString.SubstringByteIndexNode substringNode;

      private TRegexMaterializeResultNode(boolean cached) {
         this.resultAccessor = cached ? TRegexUtil.TRegexResultAccessor.create() : TRegexUtil.TRegexResultAccessor.getUncached();
         this.substringNode = cached ? TruffleString.SubstringByteIndexNode.create() : TruffleString.SubstringByteIndexNode.getUncached();
      }

      public static TRegexUtil.TRegexMaterializeResultNode create() {
         return new TRegexUtil.TRegexMaterializeResultNode(true);
      }

      public static TRegexUtil.TRegexMaterializeResultNode getUncached() {
         return UNCACHED;
      }

      public Object materializeGroup(JSContext context, Object regexResult, int i, TruffleString input) {
         return materializeGroup(context, this.resultAccessor, this.substringNode, regexResult, i, input);
      }

      public static Object materializeGroup(
         JSContext context,
         TRegexUtil.TRegexResultAccessor accessor,
         TruffleString.SubstringByteIndexNode substringNode,
         Object regexResult,
         int i,
         TruffleString input
      ) {
         int beginIndex = accessor.captureGroupStart(regexResult, i);
         if (beginIndex == -1) {
            assert i > 0;

            return Undefined.instance;
         } else {
            return Strings.substring(context, substringNode, input, beginIndex, accessor.captureGroupEnd(regexResult, i) - beginIndex);
         }
      }

      public Object[] materializeFull(JSContext context, Object regexResult, int groupCount, TruffleString input) {
         Object[] result = new Object[groupCount];

         for (int i = 0; i < groupCount; i++) {
            result[i] = this.materializeGroup(context, regexResult, i, input);
         }

         return result;
      }
   }

   public static final class TRegexNamedCaptureGroupsAccessor extends Node {
      @Node.Child
      private InteropLibrary interop = InteropLibrary.getFactory().createDispatched(9);
      @Node.Child
      private TRegexUtil.InteropToIntNode toIntNode;

      private TRegexNamedCaptureGroupsAccessor() {
      }

      public static TRegexUtil.TRegexNamedCaptureGroupsAccessor create() {
         return new TRegexUtil.TRegexNamedCaptureGroupsAccessor();
      }

      public boolean isNull(Object namedCaptureGroupsMap) {
         return this.interop.isNull(namedCaptureGroupsMap);
      }

      public boolean hasGroup(Object namedCaptureGroupsMap, TruffleString name) {
         return this.interop.isMemberReadable(namedCaptureGroupsMap, Strings.toJavaString(name));
      }

      public int getGroupNumber(Object namedCaptureGroupsMap, TruffleString name) {
         TRegexUtil.InteropToIntNode toInt = this.toIntNode;
         if (toInt == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.toIntNode = toInt = this.insert(TRegexUtilFactory.InteropToIntNodeGen.create());
         }

         try {
            return toInt.execute(this.interop.readMember(namedCaptureGroupsMap, Strings.toJavaString(name)));
         } catch (UnknownIdentifierException | UnsupportedMessageException var5) {
            throw CompilerDirectives.shouldNotReachHere(var5);
         }
      }
   }

   public static final class TRegexResultAccessor extends Node {
      private static final TRegexUtil.TRegexResultAccessor UNCACHED = new TRegexUtil.TRegexResultAccessor(false);
      @Node.Child
      private TRegexUtil.InteropReadBooleanMemberNode readIsMatchNode;
      @Node.Child
      private TRegexUtil.InvokeGetGroupBoundariesMethodNode getStartNode;
      @Node.Child
      private TRegexUtil.InvokeGetGroupBoundariesMethodNode getEndNode;

      private TRegexResultAccessor(boolean cached) {
         if (!cached) {
            this.readIsMatchNode = TRegexUtilFactory.InteropReadBooleanMemberNodeGen.getUncached();
            this.getStartNode = TRegexUtilFactory.InvokeGetGroupBoundariesMethodNodeGen.getUncached();
            this.getEndNode = TRegexUtilFactory.InvokeGetGroupBoundariesMethodNodeGen.getUncached();
         }
      }

      public static TRegexUtil.TRegexResultAccessor create() {
         return new TRegexUtil.TRegexResultAccessor(true);
      }

      public static TRegexUtil.TRegexResultAccessor getUncached() {
         return UNCACHED;
      }

      public boolean isMatch(Object regexResultObject) {
         return this.getReadIsMatchNode().execute(regexResultObject, "isMatch");
      }

      public int captureGroupStart(Object regexResultObject, int i) {
         return this.getGetStartNode().execute(regexResultObject, "getStart", i);
      }

      public int captureGroupEnd(Object regexResultObject, int i) {
         return this.getGetEndNode().execute(regexResultObject, "getEnd", i);
      }

      public int captureGroupLength(Object regexResultObject, int i) {
         return this.captureGroupEnd(regexResultObject, i) - this.captureGroupStart(regexResultObject, i);
      }

      private TRegexUtil.InteropReadBooleanMemberNode getReadIsMatchNode() {
         if (this.readIsMatchNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.readIsMatchNode = this.insert(TRegexUtil.InteropReadBooleanMemberNode.create());
         }

         return this.readIsMatchNode;
      }

      private TRegexUtil.InvokeGetGroupBoundariesMethodNode getGetStartNode() {
         if (this.getStartNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.getStartNode = this.insert(TRegexUtil.InvokeGetGroupBoundariesMethodNode.create());
         }

         return this.getStartNode;
      }

      private TRegexUtil.InvokeGetGroupBoundariesMethodNode getGetEndNode() {
         if (this.getEndNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.getEndNode = this.insert(TRegexUtil.InvokeGetGroupBoundariesMethodNode.create());
         }

         return this.getEndNode;
      }
   }
}
