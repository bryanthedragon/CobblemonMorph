
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
import com.oracle.truffle.js.runtime.util.TRegexUtilFactory;

public final class TRegexUtil {
    private static final String NUMBER_OF_REGEX_RESULT_TYPES = "9";
    private static final int NUMBER_OF_REGEX_RESULT_TYPES_INT = 9;

    private TRegexUtil() {
    }

    public static final class TRegexMaterializeResultNode
    extends Node {
        private static final TRegexMaterializeResultNode UNCACHED = new TRegexMaterializeResultNode(false);
        @Node.Child
        TRegexResultAccessor resultAccessor;
        @Node.Child
        TruffleString.SubstringByteIndexNode substringNode;

        private TRegexMaterializeResultNode(boolean cached) {
            this.resultAccessor = cached ? TRegexResultAccessor.create() : TRegexResultAccessor.getUncached();
            this.substringNode = cached ? TruffleString.SubstringByteIndexNode.create() : TruffleString.SubstringByteIndexNode.getUncached();
        }

        public static TRegexMaterializeResultNode create() {
            return new TRegexMaterializeResultNode(true);
        }

        public static TRegexMaterializeResultNode getUncached() {
            return UNCACHED;
        }

        public Object materializeGroup(JSContext context, Object regexResult, int i, TruffleString input) {
            return TRegexMaterializeResultNode.materializeGroup(context, this.resultAccessor, this.substringNode, regexResult, i, input);
        }

        public static Object materializeGroup(JSContext context, TRegexResultAccessor accessor, TruffleString.SubstringByteIndexNode substringNode, Object regexResult, int i, TruffleString input) {
            int beginIndex = accessor.captureGroupStart(regexResult, i);
            if (beginIndex == -1) {
                assert (i > 0);
                return Undefined.instance;
            }
            return Strings.substring(context, substringNode, input, beginIndex, accessor.captureGroupEnd(regexResult, i) - beginIndex);
        }

        public Object[] materializeFull(JSContext context, Object regexResult, int groupCount, TruffleString input) {
            Object[] result = new Object[groupCount];
            for (int i = 0; i < groupCount; ++i) {
                result[i] = this.materializeGroup(context, regexResult, i, input);
            }
            return result;
        }
    }

    public static final class TRegexResultAccessor
    extends Node {
        private static final TRegexResultAccessor UNCACHED = new TRegexResultAccessor(false);
        @Node.Child
        private InteropReadBooleanMemberNode readIsMatchNode;
        @Node.Child
        private InvokeGetGroupBoundariesMethodNode getStartNode;
        @Node.Child
        private InvokeGetGroupBoundariesMethodNode getEndNode;

        private TRegexResultAccessor(boolean cached) {
            if (!cached) {
                this.readIsMatchNode = TRegexUtilFactory.InteropReadBooleanMemberNodeGen.getUncached();
                this.getStartNode = TRegexUtilFactory.InvokeGetGroupBoundariesMethodNodeGen.getUncached();
                this.getEndNode = TRegexUtilFactory.InvokeGetGroupBoundariesMethodNodeGen.getUncached();
            }
        }

        public static TRegexResultAccessor create() {
            return new TRegexResultAccessor(true);
        }

        public static TRegexResultAccessor getUncached() {
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

        private InteropReadBooleanMemberNode getReadIsMatchNode() {
            if (this.readIsMatchNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.readIsMatchNode = this.insert(InteropReadBooleanMemberNode.create());
            }
            return this.readIsMatchNode;
        }

        private InvokeGetGroupBoundariesMethodNode getGetStartNode() {
            if (this.getStartNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.getStartNode = this.insert(InvokeGetGroupBoundariesMethodNode.create());
            }
            return this.getStartNode;
        }

        private InvokeGetGroupBoundariesMethodNode getGetEndNode() {
            if (this.getEndNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.getEndNode = this.insert(InvokeGetGroupBoundariesMethodNode.create());
            }
            return this.getEndNode;
        }
    }

    public static final class TRegexCompiledRegexSingleFlagAccessor
    extends Node {
        private final String flag;
        @Node.Child
        private InteropReadMemberNode readFlagsObjectNode = InteropReadMemberNode.create();
        @Node.Child
        private InteropReadBooleanMemberNode readFlagNode = InteropReadBooleanMemberNode.create();

        private TRegexCompiledRegexSingleFlagAccessor(String flag) {
            this.flag = flag;
        }

        public static TRegexCompiledRegexSingleFlagAccessor create(String flag) {
            return new TRegexCompiledRegexSingleFlagAccessor(flag);
        }

        public boolean get(Object compiledRegex) {
            return this.readFlagNode.execute(this.readFlagsObjectNode.execute(compiledRegex, "flags"), this.flag);
        }
    }

    public static final class TRegexFlagsAccessor
    extends Node {
        @Node.Child
        private InteropReadStringMemberNode readSourceNode;
        @Node.Child
        private InteropReadBooleanMemberNode readGlobalNode;
        @Node.Child
        private InteropReadBooleanMemberNode readMultilineNode;
        @Node.Child
        private InteropReadBooleanMemberNode readIgnoreCaseNode;
        @Node.Child
        private InteropReadBooleanMemberNode readStickyNode;
        @Node.Child
        private InteropReadBooleanMemberNode readUnicodeNode;
        @Node.Child
        private InteropReadBooleanMemberNode readDotAllNode;
        @Node.Child
        private InteropReadBooleanMemberNode readHasIndicesNode;

        private TRegexFlagsAccessor() {
        }

        public static TRegexFlagsAccessor create() {
            return new TRegexFlagsAccessor();
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

        private InteropReadStringMemberNode getReadSourceNode() {
            if (this.readSourceNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.readSourceNode = this.insert(InteropReadStringMemberNode.create());
            }
            return this.readSourceNode;
        }

        private InteropReadBooleanMemberNode getReadGlobalNode() {
            if (this.readGlobalNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.readGlobalNode = this.insert(InteropReadBooleanMemberNode.create());
            }
            return this.readGlobalNode;
        }

        private InteropReadBooleanMemberNode getReadMultilineNode() {
            if (this.readMultilineNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.readMultilineNode = this.insert(InteropReadBooleanMemberNode.create());
            }
            return this.readMultilineNode;
        }

        private InteropReadBooleanMemberNode getReadIgnoreCaseNode() {
            if (this.readIgnoreCaseNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.readIgnoreCaseNode = this.insert(InteropReadBooleanMemberNode.create());
            }
            return this.readIgnoreCaseNode;
        }

        private InteropReadBooleanMemberNode getReadStickyNode() {
            if (this.readStickyNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.readStickyNode = this.insert(InteropReadBooleanMemberNode.create());
            }
            return this.readStickyNode;
        }

        private InteropReadBooleanMemberNode getReadUnicodeNode() {
            if (this.readUnicodeNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.readUnicodeNode = this.insert(InteropReadBooleanMemberNode.create());
            }
            return this.readUnicodeNode;
        }

        private InteropReadBooleanMemberNode getReadDotAllNode() {
            if (this.readDotAllNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.readDotAllNode = this.insert(InteropReadBooleanMemberNode.create());
            }
            return this.readDotAllNode;
        }

        private InteropReadBooleanMemberNode getReadHasIndicesNode() {
            if (this.readHasIndicesNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.readHasIndicesNode = this.insert(InteropReadBooleanMemberNode.create());
            }
            return this.readHasIndicesNode;
        }
    }

    public static final class TRegexNamedCaptureGroupsAccessor
    extends Node {
        @Node.Child
        private InteropLibrary interop = InteropLibrary.getFactory().createDispatched(9);
        @Node.Child
        private InteropToIntNode toIntNode;

        private TRegexNamedCaptureGroupsAccessor() {
        }

        public static TRegexNamedCaptureGroupsAccessor create() {
            return new TRegexNamedCaptureGroupsAccessor();
        }

        public boolean isNull(Object namedCaptureGroupsMap) {
            return this.interop.isNull(namedCaptureGroupsMap);
        }

        public boolean hasGroup(Object namedCaptureGroupsMap, TruffleString name) {
            return this.interop.isMemberReadable(namedCaptureGroupsMap, Strings.toJavaString(name));
        }

        public int getGroupNumber(Object namedCaptureGroupsMap, TruffleString name) {
            InteropToIntNode toInt = this.toIntNode;
            if (toInt == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.toIntNode = toInt = this.insert(TRegexUtilFactory.InteropToIntNodeGen.create());
            }
            try {
                return toInt.execute(this.interop.readMember(namedCaptureGroupsMap, Strings.toJavaString(name)));
            }
            catch (UnknownIdentifierException | UnsupportedMessageException e) {
                throw CompilerDirectives.shouldNotReachHere(e);
            }
        }
    }

    public static final class TRegexCompiledRegexAccessor
    extends Node {
        @Node.Child
        private InteropReadStringMemberNode readPatternNode;
        @Node.Child
        private InteropReadMemberNode readFlagsNode;
        @Node.Child
        private InvokeExecMethodNode invokeExecMethodNode;
        @Node.Child
        private InteropReadIntMemberNode readGroupCountNode;
        @Node.Child
        private InteropReadMemberNode readGroupsNode;

        private TRegexCompiledRegexAccessor() {
        }

        public static TRegexCompiledRegexAccessor create() {
            return new TRegexCompiledRegexAccessor();
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

        private InteropReadStringMemberNode getReadPatternNode() {
            if (this.readPatternNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.readPatternNode = this.insert(InteropReadStringMemberNode.create());
            }
            return this.readPatternNode;
        }

        private InteropReadMemberNode getReadFlagsNode() {
            if (this.readFlagsNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.readFlagsNode = this.insert(InteropReadMemberNode.create());
            }
            return this.readFlagsNode;
        }

        private InvokeExecMethodNode getInvokeExecMethodNode() {
            if (this.invokeExecMethodNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.invokeExecMethodNode = this.insert(InvokeExecMethodNode.create());
            }
            return this.invokeExecMethodNode;
        }

        private InteropReadIntMemberNode getReadGroupCountNode() {
            if (this.readGroupCountNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.readGroupCountNode = this.insert(InteropReadIntMemberNode.create());
            }
            return this.readGroupCountNode;
        }

        private InteropReadMemberNode getReadGroupsNode() {
            if (this.readGroupsNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.readGroupsNode = this.insert(InteropReadMemberNode.create());
            }
            return this.readGroupsNode;
        }
    }

    @GenerateUncached
    public static abstract class InvokeGetGroupBoundariesMethodNode
    extends Node {
        public abstract int execute(Object var1, Object var2, int var3);

        @Specialization(guards={"objs.isMemberInvocable(regexResult, method)"}, limit="9")
        static int exec(Object regexResult, String method, int groupNumber, @CachedLibrary(value="regexResult") InteropLibrary objs, @Cached InteropToIntNode toIntNode) {
            try {
                return toIntNode.execute(objs.invokeMember(regexResult, method, groupNumber));
            }
            catch (ArityException | UnknownIdentifierException | UnsupportedMessageException | UnsupportedTypeException e) {
                throw CompilerDirectives.shouldNotReachHere(e);
            }
        }

        public static InvokeGetGroupBoundariesMethodNode create() {
            return TRegexUtilFactory.InvokeGetGroupBoundariesMethodNodeGen.create();
        }

        public static InvokeGetGroupBoundariesMethodNode getUncached() {
            return TRegexUtilFactory.InvokeGetGroupBoundariesMethodNodeGen.getUncached();
        }
    }

    @ImportStatic(value={Props.CompiledRegex.class})
    @GenerateUncached
    public static abstract class InvokeExecMethodNode
    extends Node {
        public abstract Object execute(Object var1, Object var2, long var3);

        @Specialization(guards={"objs.isMemberInvocable(compiledRegex, EXEC)"}, limit="3")
        static Object exec(Object compiledRegex, Object input, long fromIndex, @CachedLibrary(value="compiledRegex") InteropLibrary objs) {
            try {
                return objs.invokeMember(compiledRegex, "exec", input, fromIndex);
            }
            catch (ArityException | UnknownIdentifierException | UnsupportedMessageException | UnsupportedTypeException e) {
                throw CompilerDirectives.shouldNotReachHere(e);
            }
        }

        public static InvokeExecMethodNode create() {
            return TRegexUtilFactory.InvokeExecMethodNodeGen.create();
        }

        public static InvokeExecMethodNode getUncached() {
            return TRegexUtilFactory.InvokeExecMethodNodeGen.getUncached();
        }
    }

    @ImportStatic(value={JSGuards.class})
    @GenerateUncached
    public static abstract class InteropToStringNode
    extends Node {
        public abstract TruffleString execute(Object var1);

        @Specialization
        static TruffleString coerceJavaString(String obj) {
            return Strings.fromJavaString(obj);
        }

        @Specialization
        static TruffleString coerceDirect(TruffleString obj) {
            return obj;
        }

        @Specialization(guards={"!isTruffleString(obj)", "objs.isString(obj)"}, limit="3")
        static TruffleString coerce(Object obj, @CachedLibrary(value="obj") InteropLibrary objs) {
            try {
                return objs.asTruffleString(obj);
            }
            catch (UnsupportedMessageException e) {
                throw CompilerDirectives.shouldNotReachHere(e);
            }
        }
    }

    @GenerateUncached
    public static abstract class InteropToIntNode
    extends Node {
        public abstract int execute(Object var1);

        @Specialization
        static int coerceDirect(int obj) {
            return obj;
        }

        @Specialization(guards={"objs.fitsInInt(obj)"}, limit="3")
        static int coerce(Object obj, @CachedLibrary(value="obj") InteropLibrary objs) {
            try {
                return objs.asInt(obj);
            }
            catch (UnsupportedMessageException e) {
                throw CompilerDirectives.shouldNotReachHere(e);
            }
        }
    }

    @GenerateUncached
    public static abstract class InteropToBooleanNode
    extends Node {
        public abstract boolean execute(Object var1);

        @Specialization
        static boolean coerceDirect(boolean obj) {
            return obj;
        }

        @Specialization(guards={"objs.isBoolean(obj)"}, limit="3")
        static boolean coerce(Object obj, @CachedLibrary(value="obj") InteropLibrary objs) {
            try {
                return objs.asBoolean(obj);
            }
            catch (UnsupportedMessageException e) {
                throw CompilerDirectives.shouldNotReachHere(e);
            }
        }
    }

    @GenerateUncached
    public static abstract class InteropReadStringMemberNode
    extends Node {
        public abstract TruffleString execute(Object var1, String var2);

        @Specialization(guards={"objs.isMemberReadable(obj, key)"}, limit="3")
        static TruffleString read(Object obj, String key, @Cached InteropToStringNode coerceNode, @CachedLibrary(value="obj") InteropLibrary objs) {
            try {
                return coerceNode.execute(objs.readMember(obj, key));
            }
            catch (UnknownIdentifierException | UnsupportedMessageException e) {
                throw CompilerDirectives.shouldNotReachHere(e);
            }
        }

        public static InteropReadStringMemberNode create() {
            return TRegexUtilFactory.InteropReadStringMemberNodeGen.create();
        }

        public static InteropReadStringMemberNode getUncached() {
            return TRegexUtilFactory.InteropReadStringMemberNodeGen.getUncached();
        }
    }

    @GenerateUncached
    public static abstract class InteropReadBooleanMemberNode
    extends Node {
        public abstract boolean execute(Object var1, String var2);

        @Specialization(guards={"objs.isMemberReadable(obj, key)"}, limit="9")
        static boolean read(Object obj, String key, @Cached InteropToBooleanNode coerceNode, @CachedLibrary(value="obj") InteropLibrary objs) {
            try {
                return coerceNode.execute(objs.readMember(obj, key));
            }
            catch (UnknownIdentifierException | UnsupportedMessageException e) {
                throw CompilerDirectives.shouldNotReachHere(e);
            }
        }

        public static InteropReadBooleanMemberNode create() {
            return TRegexUtilFactory.InteropReadBooleanMemberNodeGen.create();
        }

        public static InteropReadBooleanMemberNode getUncached() {
            return TRegexUtilFactory.InteropReadBooleanMemberNodeGen.getUncached();
        }
    }

    @GenerateUncached
    public static abstract class InteropReadIntMemberNode
    extends Node {
        public abstract int execute(Object var1, String var2);

        @Specialization(guards={"objs.isMemberReadable(obj, key)"}, limit="9")
        static int read(Object obj, String key, @Cached InteropToIntNode coerceNode, @CachedLibrary(value="obj") InteropLibrary objs) {
            try {
                return coerceNode.execute(objs.readMember(obj, key));
            }
            catch (UnknownIdentifierException | UnsupportedMessageException e) {
                throw CompilerDirectives.shouldNotReachHere(e);
            }
        }

        public static InteropReadIntMemberNode create() {
            return TRegexUtilFactory.InteropReadIntMemberNodeGen.create();
        }

        public static InteropReadIntMemberNode getUncached() {
            return TRegexUtilFactory.InteropReadIntMemberNodeGen.getUncached();
        }
    }

    @GenerateUncached
    public static abstract class InteropReadMemberNode
    extends Node {
        public abstract Object execute(Object var1, String var2);

        @Specialization(guards={"objs.isMemberReadable(obj, key)"}, limit="9")
        static Object read(Object obj, String key, @CachedLibrary(value="obj") InteropLibrary objs) {
            try {
                return objs.readMember(obj, key);
            }
            catch (UnknownIdentifierException | UnsupportedMessageException e) {
                throw CompilerDirectives.shouldNotReachHere(e);
            }
        }

        public static InteropReadMemberNode create() {
            return TRegexUtilFactory.InteropReadMemberNodeGen.create();
        }

        public static InteropReadMemberNode getUncached() {
            return TRegexUtilFactory.InteropReadMemberNodeGen.getUncached();
        }
    }

    @GenerateUncached
    public static abstract class InteropIsMemberReadableNode
    extends Node {
        public abstract boolean execute(Object var1, String var2);

        @Specialization(limit="9")
        static boolean read(Object obj, String key, @CachedLibrary(value="obj") InteropLibrary objs) {
            return objs.isMemberReadable(obj, key);
        }

        public static InteropIsMemberReadableNode create() {
            return TRegexUtilFactory.InteropIsMemberReadableNodeGen.create();
        }
    }

    public static final class Constants {
        public static final int CAPTURE_GROUP_NO_MATCH = -1;

        private Constants() {
        }
    }

    public static final class Props {
        private Props() {
        }

        public static final class RegexResult {
            public static final String IS_MATCH = "isMatch";
            public static final String GET_START = "getStart";
            public static final String GET_END = "getEnd";

            private RegexResult() {
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

        public static final class CompiledRegex {
            public static final String PATTERN = "pattern";
            public static final String FLAGS = "flags";
            public static final String EXEC = "exec";
            public static final String GROUP_COUNT = "groupCount";
            public static final String GROUPS = "groups";

            private CompiledRegex() {
            }
        }
    }
}

