
package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.profiles.ValueProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.api.strings.TruffleStringBuilder;
import com.oracle.truffle.js.builtins.ArrayPrototypeBuiltins;
import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.builtins.RegExpPrototypeBuiltinsFactory;
import com.oracle.truffle.js.builtins.StringPrototypeBuiltins;
import com.oracle.truffle.js.builtins.helper.IsPristineObjectNode;
import com.oracle.truffle.js.builtins.helper.JSRegExpExecIntlNode;
import com.oracle.truffle.js.builtins.helper.ReplaceStringParser;
import com.oracle.truffle.js.nodes.CompileRegexNode;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.HasHiddenKeyCacheNode;
import com.oracle.truffle.js.nodes.access.IsJSObjectNode;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.access.PropertySetNode;
import com.oracle.truffle.js.nodes.access.ReadElementNode;
import com.oracle.truffle.js.nodes.access.WriteElementNode;
import com.oracle.truffle.js.nodes.binary.JSIdenticalNode;
import com.oracle.truffle.js.nodes.cast.JSToBooleanNode;
import com.oracle.truffle.js.nodes.cast.JSToIntegerAsIntNode;
import com.oracle.truffle.js.nodes.cast.JSToLengthNode;
import com.oracle.truffle.js.nodes.cast.JSToObjectNode;
import com.oracle.truffle.js.nodes.cast.JSToPrimitiveNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.nodes.cast.JSToUInt32Node;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.nodes.unary.IsCallableNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSException;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.JSAbstractArray;
import com.oracle.truffle.js.runtime.builtins.JSArray;
import com.oracle.truffle.js.runtime.builtins.JSArrayObject;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.builtins.JSRegExp;
import com.oracle.truffle.js.runtime.builtins.JSRegExpObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.Null;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.SimpleArrayList;
import com.oracle.truffle.js.runtime.util.StringBuilderProfile;
import com.oracle.truffle.js.runtime.util.TRegexUtil;
import java.util.EnumSet;

public final class RegExpPrototypeBuiltins
extends JSBuiltinsContainer.SwitchEnum<RegExpPrototype> {
    public static final JSBuiltinsContainer BUILTINS = new RegExpPrototypeBuiltins();

    protected RegExpPrototypeBuiltins() {
        super(JSRegExp.PROTOTYPE_NAME, RegExpPrototype.class);
    }

    @Override
    protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, RegExpPrototype builtinEnum) {
        switch (builtinEnum) {
            case exec: {
                if (context.getEcmaScriptVersion() >= 6) {
                    return RegExpPrototypeBuiltinsFactory.JSRegExpExecNodeGen.create(context, builtin, RegExpPrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
                }
                return RegExpPrototypeBuiltinsFactory.JSRegExpExecES5NodeGen.create(context, builtin, RegExpPrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
            case test: {
                return RegExpPrototypeBuiltinsFactory.JSRegExpTestNodeGen.create(context, builtin, RegExpPrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
            case toString: {
                return RegExpPrototypeBuiltinsFactory.JSRegExpToStringNodeGen.create(context, builtin, RegExpPrototypeBuiltins.args().withThis().fixedArgs(0).createArgumentNodes(context));
            }
            case _match: {
                return RegExpPrototypeBuiltinsFactory.JSRegExpMatchNodeGen.create(context, builtin, RegExpPrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
            case _replace: {
                return RegExpPrototypeBuiltinsFactory.JSRegExpReplaceNodeGen.create(context, builtin, RegExpPrototypeBuiltins.args().withThis().fixedArgs(2).createArgumentNodes(context));
            }
            case _search: {
                return RegExpPrototypeBuiltinsFactory.JSRegExpSearchNodeGen.create(context, builtin, RegExpPrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
            case _split: {
                return RegExpPrototypeBuiltinsFactory.JSRegExpSplitNodeGen.create(context, builtin, RegExpPrototypeBuiltins.args().withThis().fixedArgs(2).createArgumentNodes(context));
            }
            case compile: {
                return RegExpPrototypeBuiltinsFactory.JSRegExpCompileNodeGen.create(context, builtin, RegExpPrototypeBuiltins.args().withThis().fixedArgs(2).createArgumentNodes(context));
            }
            case _matchAll: {
                return RegExpPrototypeBuiltinsFactory.JSRegExpMatchAllNodeGen.create(context, builtin, RegExpPrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
        }
        return null;
    }

    @CompilerDirectives.TruffleBoundary
    private static JSException createNoRegExpError(Object obj) {
        TruffleString objName = Strings.fromObject(JSRuntime.toPrimitive(obj, JSToPrimitiveNode.Hint.String));
        return Errors.createTypeError(Strings.toJavaString(objName) + " is not a RegExp");
    }

    static abstract class CompiledRegexPatternAccessor
    extends JSBuiltinNode {
        @Node.Child
        TRegexUtil.InteropReadStringMemberNode readPatternNode = TRegexUtil.InteropReadStringMemberNode.create();

        CompiledRegexPatternAccessor(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        Object doRegExp(JSRegExpObject obj) {
            return JSRegExp.escapeRegExpPattern(this.readPatternNode.execute(JSRegExp.getCompiledRegex(obj), "pattern"));
        }

        @Specialization(guards={"isRegExpPrototype(obj)"})
        Object doPrototype(JSDynamicObject obj) {
            return Strings.EMPTY_REGEX;
        }

        @Fallback
        public Object doObject(Object obj) {
            throw Errors.createTypeErrorIncompatibleReceiver(obj);
        }

        boolean isRegExpPrototype(JSDynamicObject obj) {
            return obj == this.getRealm().getRegExpPrototype();
        }

        static CompiledRegexPatternAccessor create(JSContext context, JSBuiltin builtin, JavaScriptNode[] args) {
            return RegExpPrototypeBuiltinsFactory.CompiledRegexPatternAccessorNodeGen.create(context, builtin, args);
        }
    }

    static abstract class CompiledRegexFlagPropertyAccessor
    extends JSBuiltinNode {
        @Node.Child
        TRegexUtil.TRegexCompiledRegexSingleFlagAccessor readNode;

        CompiledRegexFlagPropertyAccessor(JSContext context, JSBuiltin builtin, String flagName) {
            super(context, builtin);
            this.readNode = TRegexUtil.TRegexCompiledRegexSingleFlagAccessor.create(flagName);
        }

        @Specialization
        Object doRegExp(JSRegExpObject obj) {
            return this.readNode.get(JSRegExp.getCompiledRegex(obj));
        }

        @Specialization(guards={"isRegExpPrototype(obj)"})
        Object doPrototype(JSDynamicObject obj) {
            return Undefined.instance;
        }

        @Fallback
        public Object doObject(Object obj) {
            throw Errors.createTypeErrorIncompatibleReceiver(obj);
        }

        boolean isRegExpPrototype(JSDynamicObject obj) {
            return obj == this.getRealm().getRegExpPrototype();
        }

        public static CompiledRegexFlagPropertyAccessor create(JSContext context, JSBuiltin builtin, String flagName, JavaScriptNode[] args) {
            return RegExpPrototypeBuiltinsFactory.CompiledRegexFlagPropertyAccessorNodeGen.create(context, builtin, flagName, args);
        }
    }

    public static abstract class RegExpFlagsGetterNode
    extends JSBuiltinNode {
        @Node.Child
        private PropertyGetNode getGlobal;
        @Node.Child
        private PropertyGetNode getIgnoreCase;
        @Node.Child
        private PropertyGetNode getMultiline;
        @Node.Child
        private PropertyGetNode getDotAll;
        @Node.Child
        private PropertyGetNode getUnicode;
        @Node.Child
        private PropertyGetNode getSticky;
        @Node.Child
        private PropertyGetNode getHasIndices;
        @Node.Child
        private JSToBooleanNode toBoolean;

        public RegExpFlagsGetterNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
            this.getGlobal = PropertyGetNode.create(JSRegExp.GLOBAL, context);
            this.getIgnoreCase = PropertyGetNode.create(JSRegExp.IGNORE_CASE, context);
            this.getMultiline = PropertyGetNode.create(JSRegExp.MULTILINE, context);
            if (context.getEcmaScriptVersion() >= 9) {
                this.getDotAll = PropertyGetNode.create(JSRegExp.DOT_ALL, context);
            }
            this.getUnicode = PropertyGetNode.create(JSRegExp.UNICODE, context);
            this.getSticky = PropertyGetNode.create(JSRegExp.STICKY, context);
            if (context.isOptionRegexpMatchIndices()) {
                this.getHasIndices = PropertyGetNode.create(JSRegExp.HAS_INDICES, context);
            }
        }

        @Specialization(guards={"isObjectNode.executeBoolean(rx)"}, limit="1")
        protected Object doObject(JSDynamicObject rx, @Cached IsJSObjectNode isObjectNode, @Cached TruffleString.FromCharArrayUTF16Node fromCharArrayNode) {
            char[] flags = new char[7];
            int len = 0;
            if (this.getHasIndices != null && this.getFlag(rx, this.getHasIndices)) {
                flags[len++] = 100;
            }
            if (this.getFlag(rx, this.getGlobal)) {
                flags[len++] = 103;
            }
            if (this.getFlag(rx, this.getIgnoreCase)) {
                flags[len++] = 105;
            }
            if (this.getFlag(rx, this.getMultiline)) {
                flags[len++] = 109;
            }
            if (this.getDotAll != null && this.getFlag(rx, this.getDotAll)) {
                flags[len++] = 115;
            }
            if (this.getFlag(rx, this.getUnicode)) {
                flags[len++] = 117;
            }
            if (this.getFlag(rx, this.getSticky)) {
                flags[len++] = 121;
            }
            if (len == 0) {
                return Strings.EMPTY_STRING;
            }
            return Strings.fromCharArray(fromCharArrayNode, flags, 0, len);
        }

        @Fallback
        protected Object doNotObject(Object thisObj) {
            throw Errors.createTypeErrorNotAnObject(thisObj);
        }

        private boolean getFlag(JSDynamicObject re, PropertyGetNode getNode) {
            boolean flag;
            if (this.toBoolean == null) {
                try {
                    flag = getNode.getValueBoolean(re);
                }
                catch (UnexpectedResultException e) {
                    CompilerDirectives.transferToInterpreterAndInvalidate();
                    this.toBoolean = this.insert(JSToBooleanNode.create());
                    flag = this.toBoolean.executeBoolean(e.getResult());
                }
            } else {
                flag = this.toBoolean.executeBoolean(getNode.getValue(re));
            }
            return flag;
        }
    }

    public static final class RegExpPrototypeGetterBuiltins
    extends JSBuiltinsContainer.SwitchEnum<RegExpPrototypeGetters> {
        public static final JSBuiltinsContainer BUILTINS = new RegExpPrototypeGetterBuiltins();

        protected RegExpPrototypeGetterBuiltins() {
            super(JSRegExp.PROTOTYPE_NAME, RegExpPrototypeGetters.class);
        }

        @Override
        protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, RegExpPrototypeGetters builtinEnum) {
            switch (builtinEnum) {
                case source: {
                    return CompiledRegexPatternAccessor.create(context, builtin, RegExpPrototypeGetterBuiltins.args().withThis().fixedArgs(0).createArgumentNodes(context));
                }
                case flags: {
                    return RegExpPrototypeBuiltinsFactory.RegExpFlagsGetterNodeGen.create(context, builtin, RegExpPrototypeGetterBuiltins.args().withThis().fixedArgs(0).createArgumentNodes(context));
                }
            }
            return CompiledRegexFlagPropertyAccessor.create(context, builtin, builtinEnum.name(), RegExpPrototypeGetterBuiltins.args().withThis().fixedArgs(0).createArgumentNodes(context));
        }

        public static enum RegExpPrototypeGetters implements BuiltinEnum<RegExpPrototypeGetters>
        {
            flags(0),
            source(0),
            global(0),
            multiline(0),
            ignoreCase(0),
            sticky(0),
            unicode(0),
            dotAll(0),
            hasIndices(0);

            private final int length;

            private RegExpPrototypeGetters(int length) {
                this.length = length;
            }

            @Override
            public int getLength() {
                return this.length;
            }

            @Override
            public int getECMAScriptVersion() {
                if (EnumSet.of(sticky, unicode).contains(this)) {
                    return 6;
                }
                if (this == dotAll) {
                    return 9;
                }
                return BuiltinEnum.super.getECMAScriptVersion();
            }

            @Override
            public boolean isGetter() {
                return true;
            }
        }
    }

    @ImportStatic(value={JSRegExp.class})
    public static abstract class JSRegExpMatchAllNode
    extends JSBuiltinNode {
        public JSRegExpMatchAllNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization(guards={"isObjectNode.executeBoolean(regex)"}, limit="1")
        protected Object matchAll(JSDynamicObject regex, Object stringObj, @Cached JSToStringNode toStringNodeForInput, @Cached(value="createSpeciesConstructNode()") ArrayPrototypeBuiltins.ArraySpeciesConstructorNode speciesConstructNode, @Cached(value="create(FLAGS, getContext())") PropertyGetNode getFlagsNode, @Cached JSToStringNode toStringNodeForFlags, @Cached(value="create(LAST_INDEX, getContext())") PropertyGetNode getLastIndexNode, @Cached JSToLengthNode toLengthNode, @Cached(value="create(LAST_INDEX, false, getContext(), true)") PropertySetNode setLastIndexNode, @Cached(value="createCreateRegExpStringIteratorNode()") StringPrototypeBuiltins.CreateRegExpStringIteratorNode createRegExpStringIteratorNode, @Cached IsJSObjectNode isObjectNode, @Cached ConditionProfile indexInIntRangeProf, @Cached TruffleString.ByteIndexOfCodePointNode stringIndexOfNode) {
            TruffleString string = toStringNodeForInput.executeString(stringObj);
            JSFunctionObject regExpConstructor = this.getRealm().getRegExpConstructor();
            JSDynamicObject constructor = speciesConstructNode.speciesConstructor(regex, regExpConstructor);
            TruffleString flags = toStringNodeForFlags.executeString(getFlagsNode.getValue(regex));
            Object matcher = speciesConstructNode.construct(constructor, regex, flags);
            long lastIndex = toLengthNode.executeLong(getLastIndexNode.getValue(regex));
            setLastIndexNode.setValue(matcher, JSRuntime.boxIndex(lastIndex, indexInIntRangeProf));
            boolean global = Strings.indexOf(stringIndexOfNode, flags, 103) != -1;
            boolean fullUnicode = Strings.indexOf(stringIndexOfNode, flags, 117) != -1;
            return createRegExpStringIteratorNode.createIterator(matcher, string, global, fullUnicode);
        }

        ArrayPrototypeBuiltins.ArraySpeciesConstructorNode createSpeciesConstructNode() {
            return ArrayPrototypeBuiltins.ArraySpeciesConstructorNode.create(this.getContext(), false);
        }

        StringPrototypeBuiltins.CreateRegExpStringIteratorNode createCreateRegExpStringIteratorNode() {
            return new StringPrototypeBuiltins.CreateRegExpStringIteratorNode(this.getContext());
        }

        @Fallback
        protected Object matchAll(Object thisObj, Object string) {
            throw Errors.createTypeErrorIncompatibleReceiver("RegExp.prototype.@@matchAll", thisObj);
        }
    }

    public static abstract class JSRegExpSearchNode
    extends RegExpPrototypeSymbolOperation {
        @Node.Child
        private PropertyGetNode getIndexNode;
        @Node.Child
        private JSIdenticalNode sameValueNode;

        protected JSRegExpSearchNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
            this.getIndexNode = PropertyGetNode.create(JSRegExp.INDEX, false, context);
            this.sameValueNode = JSIdenticalNode.createSameValue();
        }

        @Specialization(guards={"isObjectNode.executeBoolean(rx)"}, limit="1")
        protected Object search(JSDynamicObject rx, Object param, @Cached IsJSObjectNode isObjectNode, @Cached JSToStringNode toString1Node) {
            TruffleString s = toString1Node.executeString(param);
            Object previousLastIndex = this.getLastIndex(rx);
            if (!this.sameValueNode.executeBoolean(previousLastIndex, 0)) {
                this.setLastIndex((Object)rx, 0);
            }
            Object result = this.regexExecIntl(rx, s);
            Object currentLastIndex = this.getLastIndex(rx);
            if (!this.sameValueNode.executeBoolean(currentLastIndex, previousLastIndex)) {
                this.setLastIndex((Object)rx, previousLastIndex);
            }
            return result == Null.instance ? Integer.valueOf(-1) : this.getIndexNode.getValue(result);
        }

        @Fallback
        protected Object search(Object thisObj, Object string) {
            throw Errors.createTypeErrorIncompatibleReceiver("RegExp.prototype.@@search", thisObj);
        }
    }

    public static abstract class JSRegExpMatchNode
    extends RegExpPrototypeSymbolOperation {
        @Node.Child
        private PropertyGetNode getUnicodeNode;
        @Node.Child
        private PropertyGetNode getGlobalNode;
        @Node.Child
        private JSToLengthNode toLengthNode;
        private final ConditionProfile isGlobalProfile = ConditionProfile.createBinaryProfile();
        private final ConditionProfile unicodeProfile = ConditionProfile.createBinaryProfile();

        protected JSRegExpMatchNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
            this.getUnicodeNode = PropertyGetNode.create(JSRegExp.UNICODE, false, context);
            this.getGlobalNode = PropertyGetNode.create(JSRegExp.GLOBAL, false, context);
        }

        @Specialization(guards={"isObjectNode.executeBoolean(rx)"}, limit="1")
        protected Object match(JSDynamicObject rx, Object param, @Cached IsJSObjectNode isObjectNode, @Cached JSToStringNode toString1Node, @Cached JSToStringNode toString2Node) {
            TruffleString s = toString1Node.executeString(param);
            if (this.isGlobalProfile.profile(!this.getFlag(rx, this.getGlobalNode))) {
                return this.regexExecIntl(rx, s);
            }
            boolean fullUnicode = this.getFlag(rx, this.getUnicodeNode);
            this.setLastIndex((Object)rx, 0);
            JSArrayObject array = JSArray.createEmptyZeroLength(this.getContext(), this.getRealm());
            int n = 0;
            JSDynamicObject result;
            while ((result = (JSDynamicObject)this.regexExecIntl(rx, s)) != Null.instance) {
                TruffleString matchStr = toString2Node.executeString(this.read(result, 0));
                this.write(array, n, matchStr);
                if (Strings.length(matchStr) == 0) {
                    int lastI = this.toLength(this.getLastIndex(rx));
                    this.setLastIndex((Object)rx, this.unicodeProfile.profile(fullUnicode) ? this.advanceStringIndexUnicode(s, lastI) : lastI + 1);
                }
                ++n;
            }
            return n == 0 ? Null.instance : array;
        }

        @Fallback
        protected Object match(Object thisObj, Object string) {
            throw Errors.createTypeErrorIncompatibleReceiver("RegExp.prototype.@@match", thisObj);
        }

        private int toLength(Object obj) {
            if (this.toLengthNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.toLengthNode = this.insert(JSToLengthNode.create());
            }
            return (int)this.toLengthNode.executeLong(obj);
        }
    }

    public static final class ReplaceStringConsumerTRegex
    implements ReplaceStringParser.Consumer<ParentNode, TruffleStringBuilder> {
        private final TruffleStringBuilder sb;
        private final TruffleString input;
        private final TruffleString replaceStr;
        private final int startPos;
        private final int endPos;
        private final Object tRegexResult;
        private final Object tRegexCompiledRegex;

        public ReplaceStringConsumerTRegex(TruffleStringBuilder sb, TruffleString input, TruffleString replaceStr, int startPos, int endPos, Object tRegexResult, Object tRegexCompiledRegex) {
            this.sb = sb;
            this.input = input;
            this.replaceStr = replaceStr;
            this.startPos = startPos;
            this.endPos = endPos;
            this.tRegexResult = tRegexResult;
            this.tRegexCompiledRegex = tRegexCompiledRegex;
        }

        @Override
        public void literal(ParentNode node, int start2, int end2) {
            node.append(this.sb, this.replaceStr, start2, end2);
        }

        @Override
        public void match(ParentNode node) {
            node.append(this.sb, this.input, this.startPos, this.endPos);
        }

        @Override
        public void matchHead(ParentNode node) {
            node.append(this.sb, this.input, 0, this.startPos);
        }

        @Override
        public void matchTail(ParentNode node) {
            node.append(this.sb, this.input, this.endPos, Strings.length(this.input));
        }

        @Override
        public void captureGroup(ParentNode node, int groupNumber, int literalStart, int literalEnd) {
            TRegexUtil.TRegexResultAccessor resultAccessor = node.getResultAccessor();
            int groupCount = node.getCompiledRegexAccessor().groupCount(this.tRegexCompiledRegex);
            if (groupNumber < groupCount) {
                int start2 = resultAccessor.captureGroupStart(this.tRegexResult, groupNumber);
                if (start2 >= 0) {
                    node.append(this.sb, this.input, start2, resultAccessor.captureGroupEnd(this.tRegexResult, groupNumber));
                }
            } else if (groupNumber > 9 && groupNumber / 10 < groupCount) {
                int start3 = resultAccessor.captureGroupStart(this.tRegexResult, groupNumber / 10);
                if (start3 >= 0) {
                    node.append(this.sb, this.input, start3, resultAccessor.captureGroupEnd(this.tRegexResult, groupNumber / 10));
                }
                node.append(this.sb, this.replaceStr, literalStart + 2, literalEnd);
            } else {
                node.getInvalidGroupNumberProfile().enter();
                node.append(this.sb, this.replaceStr, literalStart, literalEnd);
            }
        }

        @Override
        public void namedCaptureGroup(ParentNode node, TruffleString groupName) {
            Object map = node.getCompiledRegexAccessor().namedCaptureGroups(this.tRegexCompiledRegex);
            if (node.getNamedCaptureGroupsAccessor().hasGroup(map, groupName)) {
                int groupNumber = node.getNamedCaptureGroupsAccessor().getGroupNumber(map, groupName);
                int start2 = node.getResultAccessor().captureGroupStart(this.tRegexResult, groupNumber);
                if (start2 >= 0) {
                    node.append(this.sb, this.input, start2, node.getResultAccessor().captureGroupEnd(this.tRegexResult, groupNumber));
                }
            }
        }

        @Override
        public TruffleStringBuilder getResult() {
            return this.sb;
        }

        public static interface ParentNode {
            public TRegexUtil.TRegexCompiledRegexAccessor getCompiledRegexAccessor();

            public TRegexUtil.TRegexResultAccessor getResultAccessor();

            public TRegexUtil.TRegexNamedCaptureGroupsAccessor getNamedCaptureGroupsAccessor();

            public void append(TruffleStringBuilder var1, TruffleString var2);

            public void append(TruffleStringBuilder var1, TruffleString var2, int var3, int var4);

            public BranchProfile getInvalidGroupNumberProfile();
        }
    }

    public static final class ReplaceStringConsumer
    implements ReplaceStringParser.Consumer<JSRegExpReplaceNode, TruffleStringBuilder> {
        private final TruffleStringBuilder sb;
        private final TruffleString input;
        private final TruffleString replaceStr;
        private final int startPos;
        private final int endPos;
        private final JSDynamicObject result;
        private final JSDynamicObject namedCaptures;

        private ReplaceStringConsumer(TruffleStringBuilder sb, TruffleString input, TruffleString replaceStr, int startPos, int endPos, JSDynamicObject result, JSDynamicObject namedCaptures) {
            this.sb = sb;
            this.input = input;
            this.replaceStr = replaceStr;
            this.startPos = startPos;
            this.endPos = endPos;
            this.result = result;
            this.namedCaptures = namedCaptures;
        }

        @Override
        public void literal(JSRegExpReplaceNode node, int start2, int end2) {
            node.append(this.sb, this.replaceStr, start2, end2);
        }

        @Override
        public void match(JSRegExpReplaceNode node) {
            node.append(this.sb, (TruffleString)node.read(this.result, 0));
        }

        @Override
        public void matchHead(JSRegExpReplaceNode node) {
            node.append(this.sb, this.input, 0, this.startPos);
        }

        @Override
        public void matchTail(JSRegExpReplaceNode node) {
            node.append(this.sb, this.input, this.endPos, Strings.length(this.input));
        }

        @Override
        public void captureGroup(JSRegExpReplaceNode node, int groupNumber, int literalStart, int literalEnd) {
            Object capture = node.read(this.result, groupNumber);
            if (capture != Undefined.instance) {
                node.append(this.sb, (TruffleString)capture);
            }
        }

        @Override
        public void namedCaptureGroup(JSRegExpReplaceNode node, TruffleString groupName) {
            Object namedCapture = node.readNamedCaptureGroup(this.namedCaptures, groupName);
            if (namedCapture != Undefined.instance) {
                node.append(this.sb, node.toString4(namedCapture));
            }
        }

        @Override
        public TruffleStringBuilder getResult() {
            return this.sb;
        }
    }

    public static abstract class JSRegExpReplaceNode
    extends RegExpPrototypeSymbolOperation
    implements ReplaceStringConsumerTRegex.ParentNode {
        @Node.Child
        private PropertyGetNode getGlobalNode;
        @Node.Child
        private PropertyGetNode getUnicodeNode;
        @Node.Child
        private PropertyGetNode getLengthNode;
        @Node.Child
        private PropertyGetNode getIndexNode;
        @Node.Child
        private PropertyGetNode getGroupsNode;
        @Node.Child
        private TRegexUtil.TRegexResultAccessor readLazyLengthNode;
        @Node.Child
        private DynamicObjectLibrary lazyRegexResultNode;
        @Node.Child
        private JSToLengthNode toLengthNode;
        @Node.Child
        private JSToIntegerAsIntNode toIntegerNode;
        @Node.Child
        private JSToStringNode toString2Node;
        @Node.Child
        private JSToStringNode toString3Node;
        @Node.Child
        private JSToStringNode toString4Node;
        @Node.Child
        private JSFunctionCallNode functionCallNode;
        @Node.Child
        private IsJSObjectNode isObjectNode;
        @Node.Child
        private IsCallableNode isCallableNode;
        @Node.Child
        private ReadElementNode readNamedCaptureGroupNode;
        @Node.Child
        private JSToObjectNode toObjectNode;
        @Node.Child
        private HasHiddenKeyCacheNode hasLazyRegexResultNode;
        @Node.Child
        private JSRegExpExecIntlNode.JSRegExpExecIntlIgnoreLastIndexNode execIgnoreLastIndexNode;
        @Node.Child
        TRegexUtil.TRegexCompiledRegexAccessor compiledRegexAccessor;
        @Node.Child
        private TRegexUtil.TRegexFlagsAccessor flagsAccessor;
        @Node.Child
        TRegexUtil.TRegexResultAccessor resultAccessor;
        @Node.Child
        private TRegexUtil.TRegexNamedCaptureGroupsAccessor namedCaptureGroupsAccessor;
        @Node.Child
        private IsPristineObjectNode isPristineObjectNode;
        @Node.Child
        private TruffleStringBuilder.AppendStringNode appendStringNode;
        @Node.Child
        private TruffleStringBuilder.AppendSubstringByteIndexNode appendSubStringNode;
        @Node.Child
        private TruffleStringBuilder.ToStringNode builderToStringNode;
        private final ConditionProfile unicodeProfile = ConditionProfile.createBinaryProfile();
        private final ConditionProfile globalProfile = ConditionProfile.createBinaryProfile();
        private final ConditionProfile stickyProfile = ConditionProfile.createBinaryProfile();
        private final ConditionProfile functionalReplaceProfile = ConditionProfile.createBinaryProfile();
        private final ConditionProfile lazyResultArrayProfile = ConditionProfile.createBinaryProfile();
        private final ConditionProfile noMatchProfile = ConditionProfile.createBinaryProfile();
        private final ConditionProfile validPositionProfile = ConditionProfile.createBinaryProfile();
        private final ConditionProfile hasNamedCaptureGroupsProfile = ConditionProfile.createBinaryProfile();
        private final BranchProfile dollarProfile = BranchProfile.create();
        final StringBuilderProfile stringBuilderProfile;
        final BranchProfile invalidGroupNumberProfile = BranchProfile.create();
        private final ValueProfile compiledRegexProfile = ValueProfile.createIdentityProfile();
        private final BranchProfile growProfile = BranchProfile.create();

        JSRegExpReplaceNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
            this.getGlobalNode = PropertyGetNode.create(JSRegExp.GLOBAL, false, context);
            this.getIndexNode = PropertyGetNode.create(JSRegExp.INDEX, false, context);
            this.toIntegerNode = JSToIntegerAsIntNode.create();
            this.isObjectNode = IsJSObjectNode.create();
            this.isCallableNode = IsCallableNode.create();
            this.hasLazyRegexResultNode = HasHiddenKeyCacheNode.create(JSArray.LAZY_REGEX_RESULT_ID);
            this.stringBuilderProfile = StringBuilderProfile.create(context.getStringLengthLimit());
            this.lazyRegexResultNode = DynamicObjectLibrary.getFactory().createDispatched(5);
        }

        @Specialization(guards={"stringEquals(equalsNode, cachedReplaceValue, replaceValue)"})
        protected Object replaceCached(JSDynamicObject rx, Object searchString, TruffleString replaceValue, @Cached(value="replaceValue") TruffleString cachedReplaceValue, @Cached(value="parseReplaceValueWithNCG(replaceValue)", dimensions=1) ReplaceStringParser.Token[] cachedParsedReplaceValueWithNamedCG, @Cached(value="parseReplaceValueWithoutNCG(replaceValue)", dimensions=1) ReplaceStringParser.Token[] cachedParsedReplaceValueWithoutNamedCG, @Cached JSToStringNode toString1Node, @Cached TruffleString.EqualNode equalsNode) {
            this.checkObject(rx);
            if (this.isPristine(rx)) {
                return this.replaceInternal(rx, toString1Node.executeString(searchString), cachedReplaceValue, cachedParsedReplaceValueWithNamedCG, cachedParsedReplaceValueWithoutNamedCG);
            }
            return this.replaceAccordingToSpec(rx, toString1Node.executeString(searchString), cachedReplaceValue, false);
        }

        @Specialization(replaces={"replaceCached"})
        protected Object replaceDynamic(JSDynamicObject rx, Object searchString, Object replaceValue, @Cached JSToStringNode toString1Node) {
            Object replaceVal;
            this.checkObject(rx);
            boolean functionalReplace = this.functionalReplaceProfile.profile(this.isCallableNode.executeBoolean(replaceValue));
            if (functionalReplace) {
                replaceVal = replaceValue;
            } else {
                TruffleString replaceString = this.toString2(replaceValue);
                replaceVal = replaceString;
                if (this.isPristine(rx)) {
                    return this.replaceInternal(rx, toString1Node.executeString(searchString), replaceString, null, null);
                }
            }
            return this.replaceAccordingToSpec(rx, toString1Node.executeString(searchString), replaceVal, functionalReplace);
        }

        @Fallback
        protected Object doNoObject(Object rx, Object searchString, Object replaceValue) {
            throw Errors.createTypeErrorIncompatibleReceiver("RegExp.prototype.@@replace", rx);
        }

        private void checkObject(JSDynamicObject rx) {
            if (!this.isObjectNode.executeBoolean(rx)) {
                throw Errors.createTypeErrorIncompatibleReceiver("RegExp.prototype.@@replace", (Object)rx);
            }
        }

        ReplaceStringParser.Token[] parseReplaceValueWithNCG(TruffleString replaceValue) {
            return this.parseReplaceValue(replaceValue, true);
        }

        ReplaceStringParser.Token[] parseReplaceValueWithoutNCG(TruffleString replaceValue) {
            return this.parseReplaceValue(replaceValue, false);
        }

        ReplaceStringParser.Token[] parseReplaceValue(TruffleString replaceValue, boolean parseNamedCG) {
            return ReplaceStringParser.parse(this.getContext(), replaceValue, 100, parseNamedCG);
        }

        private void initTRegexAccessors() {
            if (this.compiledRegexAccessor == null || this.flagsAccessor == null || this.resultAccessor == null || this.execIgnoreLastIndexNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.compiledRegexAccessor = this.insert(TRegexUtil.TRegexCompiledRegexAccessor.create());
                this.flagsAccessor = this.insert(TRegexUtil.TRegexFlagsAccessor.create());
                this.resultAccessor = this.insert(TRegexUtil.TRegexResultAccessor.create());
                this.execIgnoreLastIndexNode = this.insert(JSRegExpExecIntlNode.JSRegExpExecIntlIgnoreLastIndexNode.create(this.getContext(), false));
            }
        }

        private Object replaceInternal(JSDynamicObject rx, TruffleString s, TruffleString replaceString, ReplaceStringParser.Token[] parsedWithNamedCG, ReplaceStringParser.Token[] parsedWithoutNamedCG) {
            this.initTRegexAccessors();
            Object tRegexCompiledRegex = this.compiledRegexProfile.profile(JSRegExp.getCompiledRegex(rx));
            Object tRegexFlags = this.compiledRegexAccessor.flags(tRegexCompiledRegex);
            boolean global = this.globalProfile.profile(this.flagsAccessor.global(tRegexFlags));
            boolean unicode = this.unicodeProfile.profile(this.flagsAccessor.unicode(tRegexFlags));
            boolean sticky = this.stickyProfile.profile(this.flagsAccessor.sticky(tRegexFlags));
            int length = Strings.length(s);
            TruffleStringBuilder sb = this.stringBuilderProfile.newStringBuilder(length + 16);
            int lastMatchEnd = 0;
            int matchStart = -1;
            int lastIndex = sticky ? (int)this.toLength(this.getLastIndex(rx)) : 0;
            Object lastRegexResult = null;
            while (lastIndex <= length) {
                Object tRegexResult = this.execIgnoreLastIndexNode.execute(rx, s, lastIndex);
                if (this.noMatchProfile.profile(!this.resultAccessor.isMatch(tRegexResult))) {
                    if (matchStart >= 0) break;
                    if (global || sticky) {
                        this.setLastIndex((Object)rx, 0);
                    }
                    return s;
                }
                if (!this.getContext().getRegExpStaticResultUnusedAssumption().isValid()) {
                    lastRegexResult = tRegexResult;
                }
                matchStart = this.resultAccessor.captureGroupStart(tRegexResult, 0);
                int matchEnd = this.resultAccessor.captureGroupEnd(tRegexResult, 0);
                assert (matchStart >= 0 && matchStart <= length && matchStart >= lastMatchEnd);
                this.append(sb, s, lastMatchEnd, matchStart);
                boolean namedCG = this.hasNamedCaptureGroupsProfile.profile(!this.getNamedCaptureGroupsAccessor().isNull(this.compiledRegexAccessor.namedCaptureGroups(tRegexCompiledRegex)));
                if (parsedWithNamedCG == null) {
                    ReplaceStringParser.process(this.getContext(), replaceString, this.compiledRegexAccessor.groupCount(tRegexCompiledRegex), namedCG, this.dollarProfile, new ReplaceStringConsumerTRegex(sb, s, replaceString, matchStart, matchEnd, tRegexResult, tRegexCompiledRegex), this);
                } else {
                    ReplaceStringParser.processParsed(namedCG ? parsedWithNamedCG : parsedWithoutNamedCG, new ReplaceStringConsumerTRegex(sb, s, replaceString, matchStart, matchEnd, tRegexResult, tRegexCompiledRegex), this);
                }
                lastMatchEnd = matchEnd;
                if (!global) break;
                if (matchStart == matchEnd) {
                    lastIndex = unicode ? this.advanceStringIndexUnicode(s, matchEnd) : matchEnd + 1;
                    continue;
                }
                lastIndex = matchEnd;
            }
            if (this.getContext().isOptionRegexpStaticResult() && matchStart >= 0) {
                this.getRealm().setStaticRegexResult(this.getContext(), tRegexCompiledRegex, s, matchStart, lastRegexResult);
            }
            if (global || sticky) {
                this.setLastIndex((Object)rx, sticky ? lastMatchEnd : 0);
            }
            if (lastMatchEnd < length) {
                this.append(sb, s, lastMatchEnd, length);
            }
            return this.builderToString(sb);
        }

        private Object replaceAccordingToSpec(JSDynamicObject rx, TruffleString s, Object replaceValue, boolean functionalReplace) {
            TruffleString replaceString = null;
            JSDynamicObject replaceFunction = null;
            if (functionalReplace) {
                replaceFunction = (JSDynamicObject)replaceValue;
            } else {
                replaceString = (TruffleString)replaceValue;
            }
            boolean global = this.globalProfile.profile(this.getFlag(rx, this.getGlobalNode));
            boolean fullUnicode = false;
            if (global) {
                fullUnicode = this.unicodeProfile.profile(this.getFlag(rx, this.getGetUnicodeNode()));
                this.setLastIndex((Object)rx, 0);
            }
            SimpleArrayList<JSDynamicObject> results2 = null;
            if (functionalReplace) {
                results2 = new SimpleArrayList<JSDynamicObject>();
            }
            int length = Strings.length(s);
            TruffleStringBuilder sb = this.stringBuilderProfile.newStringBuilder(length + 16);
            int nextSourcePosition = 0;
            int matchLength = -1;
            while (true) {
                JSDynamicObject result;
                if (this.noMatchProfile.profile((result = (JSDynamicObject)this.regexExecIntl(rx, s)) == Null.instance)) {
                    if (matchLength >= 0) break;
                    return s;
                }
                matchLength = this.lazyResultArrayProfile.profile(this.isLazyResultArray(result)) ? this.getLazyLength(result) : this.processNonLazy(result);
                if (functionalReplace) {
                    results2.add(result, this.growProfile);
                } else {
                    int position = Math.max(Math.min(this.toIntegerNode.executeInt(this.getIndexNode.getValue(result)), Strings.length(s)), 0);
                    if (this.validPositionProfile.profile(position >= nextSourcePosition)) {
                        this.append(sb, s, nextSourcePosition, position);
                        Object namedCaptures = this.getGroups(result);
                        if (namedCaptures != Undefined.instance) {
                            namedCaptures = this.toObject(namedCaptures);
                        }
                        ReplaceStringParser.process(this.getContext(), replaceString, (int)this.toLength(this.getLength(result)), namedCaptures != Undefined.instance, this.dollarProfile, new ReplaceStringConsumer(sb, s, replaceString, position, Math.min(position + matchLength, Strings.length(s)), result, (JSDynamicObject)namedCaptures), this);
                        nextSourcePosition = position + matchLength;
                    }
                }
                if (!global) break;
                if (matchLength != 0) continue;
                long lastI = this.toLength(this.getLastIndex(rx));
                long nextIndex = lastI + 1L;
                if (JSRuntime.longIsRepresentableAsInt(nextIndex)) {
                    this.setLastIndex((Object)rx, fullUnicode ? this.advanceStringIndexUnicode(s, (int)lastI) : (int)nextIndex);
                    continue;
                }
                this.setLastIndex((Object)rx, nextIndex);
            }
            if (functionalReplace) {
                for (int i = 0; i < results2.size(); ++i) {
                    JSDynamicObject result = (JSDynamicObject)results2.get(i);
                    int position = Math.max(Math.min(this.toIntegerNode.executeInt(this.getIndexNode.getValue(result)), Strings.length(s)), 0);
                    int resultsLength = (int)this.toLength(this.getLength(result));
                    Object namedCaptures = this.getGroups(result);
                    Object[] arguments = new Object[resultsLength + 4 + (namedCaptures != Undefined.instance ? 1 : 0)];
                    arguments[0] = Undefined.instance;
                    arguments[1] = replaceFunction;
                    for (int i1 = 0; i1 < resultsLength; ++i1) {
                        arguments[i1 + 2] = this.read(result, i1);
                    }
                    arguments[resultsLength + 2] = position;
                    arguments[resultsLength + 3] = s;
                    if (namedCaptures != Undefined.instance) {
                        arguments[resultsLength + 4] = namedCaptures;
                    }
                    Object callResult = this.callFunction(arguments);
                    TruffleString replacement = this.toString2(callResult);
                    if (!this.validPositionProfile.profile(position >= nextSourcePosition)) continue;
                    this.append(sb, s, nextSourcePosition, position);
                    this.append(sb, replacement);
                    nextSourcePosition = this.lazyResultArrayProfile.profile(this.isLazyResultArray(result)) ? position + this.getLazyLength(result) : position + Strings.length((TruffleString)this.read(result, 0));
                }
            }
            if (nextSourcePosition < length) {
                this.append(sb, s, nextSourcePosition, length);
            }
            return this.builderToString(sb);
        }

        private int processNonLazy(JSDynamicObject result) {
            int resultLength = (int)this.toLength(this.getLength(result));
            TruffleString result0Str = this.toString3(this.read(result, 0));
            this.write(result, 0, result0Str);
            for (int n = 1; n < resultLength; ++n) {
                Object value2 = this.read(result, n);
                if (value2 == Undefined.instance) continue;
                this.write(result, n, this.toString3(value2));
            }
            return Strings.length(result0Str);
        }

        private boolean isLazyResultArray(JSDynamicObject result) {
            boolean isLazyResultArray = this.hasLazyRegexResultNode.executeHasHiddenKey(result);
            assert (isLazyResultArray == JSDynamicObject.hasProperty(result, JSArray.LAZY_REGEX_RESULT_ID));
            return isLazyResultArray;
        }

        private Object callFunction(Object[] arguments) {
            if (this.functionCallNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.functionCallNode = this.insert(JSFunctionCallNode.createCall());
            }
            return this.functionCallNode.executeCall(arguments);
        }

        private TruffleString toString2(Object obj) {
            if (this.toString2Node == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.toString2Node = this.insert(JSToStringNode.create());
            }
            return this.toString2Node.executeString(obj);
        }

        private TruffleString toString3(Object obj) {
            if (this.toString3Node == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.toString3Node = this.insert(JSToStringNode.create());
            }
            return this.toString3Node.executeString(obj);
        }

        final TruffleString toString4(Object obj) {
            if (this.toString4Node == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.toString4Node = this.insert(JSToStringNode.create());
            }
            return this.toString4Node.executeString(obj);
        }

        private int getLazyLength(JSDynamicObject obj) {
            if (this.readLazyLengthNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.readLazyLengthNode = this.insert(TRegexUtil.TRegexResultAccessor.create());
            }
            return this.readLazyLengthNode.captureGroupLength(JSAbstractArray.arrayGetRegexResult(obj, this.lazyRegexResultNode), 0);
        }

        private PropertyGetNode getGetUnicodeNode() {
            if (this.getUnicodeNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.getUnicodeNode = this.insert(PropertyGetNode.create(JSRegExp.UNICODE, false, this.getContext()));
            }
            return this.getUnicodeNode;
        }

        private boolean isPristine(JSDynamicObject rx) {
            if (this.isPristineObjectNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.isPristineObjectNode = this.insert(IsPristineObjectNode.createRegExpExecAndMatch(this.getContext()));
            }
            return this.isPristineObjectNode.execute(rx);
        }

        private Object getLength(Object obj) {
            if (this.getLengthNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.getLengthNode = this.insert(PropertyGetNode.create(JSArray.LENGTH, false, this.getContext()));
            }
            return this.getLengthNode.getValue(obj);
        }

        private long toLength(Object value2) {
            if (this.toLengthNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.toLengthNode = this.insert(JSToLengthNode.create());
            }
            return this.toLengthNode.executeLong(value2);
        }

        private Object getGroups(Object regexResult) {
            if (this.getGroupsNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.getGroupsNode = this.insert(PropertyGetNode.create(JSRegExp.GROUPS, false, this.getContext()));
            }
            return this.getGroupsNode.getValue(regexResult);
        }

        final Object readNamedCaptureGroup(Object namedCaptureGroups, Object groupNameStr) {
            if (this.readNamedCaptureGroupNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.readNamedCaptureGroupNode = this.insert(ReadElementNode.create(this.getContext()));
            }
            return this.readNamedCaptureGroupNode.executeWithTargetAndIndex(namedCaptureGroups, groupNameStr);
        }

        private Object toObject(Object obj) {
            if (this.toObjectNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.toObjectNode = this.insert(JSToObjectNode.createToObject(this.getContext()));
            }
            return this.toObjectNode.execute(obj);
        }

        @Override
        public void append(TruffleStringBuilder sb, TruffleString s) {
            if (this.appendStringNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.appendStringNode = this.insert(TruffleStringBuilder.AppendStringNode.create());
            }
            this.stringBuilderProfile.append(this.appendStringNode, sb, s);
        }

        @Override
        public void append(TruffleStringBuilder sb, TruffleString s, int fromIndex, int toIndex) {
            if (this.appendSubStringNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.appendSubStringNode = this.insert(TruffleStringBuilder.AppendSubstringByteIndexNode.create());
            }
            this.stringBuilderProfile.append(this.appendSubStringNode, sb, s, fromIndex, toIndex);
        }

        private TruffleString builderToString(TruffleStringBuilder sb) {
            if (this.builderToStringNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.builderToStringNode = this.insert(TruffleStringBuilder.ToStringNode.create());
            }
            return StringBuilderProfile.toString(this.builderToStringNode, sb);
        }

        @Override
        public final TRegexUtil.TRegexNamedCaptureGroupsAccessor getNamedCaptureGroupsAccessor() {
            if (this.namedCaptureGroupsAccessor == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.namedCaptureGroupsAccessor = this.insert(TRegexUtil.TRegexNamedCaptureGroupsAccessor.create());
            }
            return this.namedCaptureGroupsAccessor;
        }

        @Override
        public TRegexUtil.TRegexCompiledRegexAccessor getCompiledRegexAccessor() {
            return this.compiledRegexAccessor;
        }

        @Override
        public TRegexUtil.TRegexResultAccessor getResultAccessor() {
            return this.resultAccessor;
        }

        @Override
        public BranchProfile getInvalidGroupNumberProfile() {
            return this.invalidGroupNumberProfile;
        }
    }

    @ImportStatic(value={JSGuards.class})
    public static abstract class JSRegExpSplitNode
    extends RegExpPrototypeSymbolOperation {
        @Node.Child
        private IsJSObjectNode isObjectNode;
        @Node.Child
        private JSToStringNode toString1Node;
        @Node.Child
        private JSToStringNode toString2Node;
        @Node.Child
        private PropertyGetNode getFlagsNode;
        @Node.Child
        private PropertyGetNode getLengthNode;
        @Node.Child
        private JSToUInt32Node toUInt32Node;
        @Node.Child
        private JSToLengthNode toLengthNode;
        @Node.Child
        private JSRegExpExecIntlNode.JSRegExpExecIntlIgnoreLastIndexNode execIgnoreLastIndexNode;
        @Node.Child
        private TRegexUtil.TRegexCompiledRegexAccessor compiledRegexAccessor;
        @Node.Child
        private TRegexUtil.TRegexFlagsAccessor flagsAccessor;
        @Node.Child
        private TRegexUtil.TRegexResultAccessor resultAccessor;
        @Node.Child
        private IsPristineObjectNode isPristineObjectNode;
        @Node.Child
        private TruffleString.ConcatNode stringConcatNode;
        @Node.Child
        private TruffleString.SubstringByteIndexNode substringNode;
        @Node.Child
        private TruffleString.ByteIndexOfCodePointNode stringIndexOfNode;
        private final ConditionProfile sizeZeroProfile = ConditionProfile.createBinaryProfile();
        private final ConditionProfile sameMatchEnd = ConditionProfile.createBinaryProfile();
        private final ConditionProfile resultIsNull = ConditionProfile.createBinaryProfile();
        private final ConditionProfile isUnicode = ConditionProfile.createBinaryProfile();
        private final ConditionProfile limitProfile = ConditionProfile.createBinaryProfile();
        private final BranchProfile prematureReturnBranch = BranchProfile.create();
        private final ConditionProfile emptyFlags = ConditionProfile.createBinaryProfile();
        private final ConditionProfile stickyFlagSet = ConditionProfile.createBinaryProfile();
        private final ValueProfile compiledRegexProfile = ValueProfile.createIdentityProfile();

        JSRegExpSplitNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        JSDynamicObject splitIntLimit(JSDynamicObject rx, Object input, int limit) {
            return this.doSplit(rx, input, this.toUInt32(limit));
        }

        @Specialization
        JSDynamicObject splitLongLimit(JSDynamicObject rx, Object input, long limit) {
            return this.doSplit(rx, input, this.toUInt32(limit));
        }

        @Specialization(guards={"isUndefined(limit)"})
        JSDynamicObject splitUndefinedLimit(JSDynamicObject rx, Object input, Object limit) {
            return this.doSplit(rx, input, JSRuntime.MAX_SAFE_INTEGER_LONG);
        }

        @Specialization(guards={"!isUndefined(limit)"})
        JSDynamicObject splitObjectLimit(JSDynamicObject rx, Object input, Object limit) {
            this.checkObject(rx);
            TruffleString str = this.toString1(input);
            JSDynamicObject constructor = this.getSpeciesConstructor(rx);
            return this.splitAccordingToSpec(rx, str, limit, constructor);
        }

        @Fallback
        Object doNoObject(Object rx, Object input, Object flags) {
            throw Errors.createTypeErrorIncompatibleReceiver("RegExp.prototype.@@split", rx);
        }

        private JSDynamicObject doSplit(JSDynamicObject rx, Object input, long limit) {
            this.checkObject(rx);
            TruffleString str = this.toString1(input);
            if (limit == 0L) {
                this.prematureReturnBranch.enter();
                return JSArray.createEmptyZeroLength(this.getContext(), this.getRealm());
            }
            JSDynamicObject constructor = this.getSpeciesConstructor(rx);
            if (constructor == this.getRealm().getRegExpConstructor() && this.isPristine(rx)) {
                return this.splitInternal(rx, str, limit);
            }
            return this.splitAccordingToSpec(rx, str, limit, constructor);
        }

        private void checkObject(JSDynamicObject rx) {
            if (!this.isJSObject(rx)) {
                throw Errors.createTypeErrorIncompatibleReceiver("RegExp.prototype.@@split", (Object)rx);
            }
        }

        private JSDynamicObject getSpeciesConstructor(JSDynamicObject rx) {
            JSFunctionObject regexpConstructor = this.getRealm().getRegExpConstructor();
            return this.getArraySpeciesConstructorNode().speciesConstructor(rx, regexpConstructor);
        }

        private JSDynamicObject splitAccordingToSpec(JSDynamicObject rx, TruffleString str, Object limit, JSDynamicObject constructor) {
            long lim;
            TruffleString flags = this.toString2(this.getFlags(rx));
            boolean unicodeMatching = this.indexOf(flags, 117) >= 0;
            JSDynamicObject splitter = (JSDynamicObject)this.getArraySpeciesConstructorNode().construct(constructor, rx, this.ensureSticky(flags));
            JSArrayObject array = JSArray.createEmptyZeroLength(this.getContext(), this.getRealm());
            if (this.limitProfile.profile(limit == Undefined.instance)) {
                lim = JSRuntime.MAX_SAFE_INTEGER_LONG;
            } else {
                lim = this.toUInt32(limit);
                if (lim == 0L) {
                    this.prematureReturnBranch.enter();
                    return array;
                }
            }
            int size = Strings.length(str);
            if (this.sizeZeroProfile.profile(size == 0)) {
                if (this.regexExecIntl(splitter, str) == Null.instance) {
                    this.write(array, 0, str);
                }
                return array;
            }
            int arrayLength = 0;
            int prevMatchEnd = 0;
            int fromIndex = 0;
            while (fromIndex < size) {
                this.setLastIndex((Object)splitter, fromIndex);
                JSDynamicObject regexResult = (JSDynamicObject)this.regexExecIntl(splitter, str);
                if (this.resultIsNull.profile(regexResult == Null.instance)) {
                    fromIndex = this.movePosition(str, unicodeMatching, fromIndex);
                    continue;
                }
                int matchEnd = (int)this.toLength(this.getLastIndex(splitter));
                if (this.sameMatchEnd.profile(matchEnd == prevMatchEnd)) {
                    fromIndex = this.movePosition(str, unicodeMatching, fromIndex);
                    continue;
                }
                this.write(array, arrayLength, this.substring(str, prevMatchEnd, fromIndex - prevMatchEnd));
                if ((long)(++arrayLength) == lim) {
                    this.prematureReturnBranch.enter();
                    return array;
                }
                prevMatchEnd = matchEnd;
                fromIndex = matchEnd;
                long numberOfCaptures = this.toLength(this.getLength(regexResult));
                int i = 1;
                while ((long)i < numberOfCaptures) {
                    this.write(array, arrayLength, this.read(regexResult, i));
                    if ((long)(++arrayLength) == lim) {
                        this.prematureReturnBranch.enter();
                        return array;
                    }
                    ++i;
                }
            }
            int begin = Math.min(prevMatchEnd, Strings.length(str));
            this.write(array, arrayLength, this.substring(str, begin, size - begin));
            return array;
        }

        private JSDynamicObject splitInternal(JSDynamicObject rx, TruffleString str, long lim) {
            JSDynamicObject splitter;
            this.initTRegexAccessors();
            Object tRegexCompiledRegex = this.compiledRegexProfile.profile(JSRegExp.getCompiledRegex(rx));
            Object tRegexFlags = this.compiledRegexAccessor.flags(tRegexCompiledRegex);
            boolean unicodeMatching = this.flagsAccessor.unicode(tRegexFlags);
            if (this.stickyFlagSet.profile(this.flagsAccessor.sticky(tRegexFlags))) {
                JSFunctionObject regexpConstructor = this.getRealm().getRegExpConstructor();
                splitter = (JSDynamicObject)this.getArraySpeciesConstructorNode().construct(regexpConstructor, rx, this.removeStickyFlag(tRegexFlags));
            } else {
                splitter = rx;
            }
            JSArrayObject array = JSArray.createEmptyZeroLength(this.getContext(), this.getRealm());
            int size = Strings.length(str);
            int arrayLength = 0;
            int prevMatchEnd = 0;
            int fromIndex = 0;
            int matchStart = -1;
            int matchEnd = -1;
            Object lastRegexResult = null;
            do {
                Object tRegexResult;
                if (this.resultIsNull.profile(!this.resultAccessor.isMatch(tRegexResult = this.execIgnoreLastIndexNode.execute(splitter, str, fromIndex)))) {
                    if (!this.sizeZeroProfile.profile(size == 0) && matchStart >= 0) break;
                    this.write(array, 0, str);
                    return array;
                }
                if (!this.getContext().getRegExpStaticResultUnusedAssumption().isValid()) {
                    lastRegexResult = tRegexResult;
                }
                matchStart = this.resultAccessor.captureGroupStart(tRegexResult, 0);
                matchEnd = this.resultAccessor.captureGroupEnd(tRegexResult, 0);
                if (matchEnd == prevMatchEnd) {
                    fromIndex = this.movePosition(str, unicodeMatching, fromIndex);
                    continue;
                }
                this.write(array, arrayLength++, this.substring(str, prevMatchEnd, matchStart - prevMatchEnd));
                if ((long)arrayLength == lim) {
                    this.prematureReturnBranch.enter();
                    return array;
                }
                prevMatchEnd = matchEnd;
                long numberOfCaptures = this.compiledRegexAccessor.groupCount(tRegexCompiledRegex);
                int i = 1;
                while ((long)i < numberOfCaptures) {
                    this.write(array, arrayLength, TRegexUtil.TRegexMaterializeResultNode.materializeGroup(this.getContext(), this.resultAccessor, this.substringNode, tRegexResult, i, str));
                    if ((long)(++arrayLength) == lim) {
                        this.prematureReturnBranch.enter();
                        return array;
                    }
                    ++i;
                }
                fromIndex = matchStart == matchEnd ? this.movePosition(str, unicodeMatching, fromIndex) : matchEnd;
            } while (fromIndex < size);
            if (this.getContext().isOptionRegexpStaticResult() && matchStart >= 0 && matchStart < size) {
                this.getRealm().setStaticRegexResult(this.getContext(), tRegexCompiledRegex, str, matchStart, lastRegexResult);
            }
            if (matchStart != matchEnd || prevMatchEnd < size) {
                this.write(array, arrayLength, this.substring(str, prevMatchEnd, size - prevMatchEnd));
            }
            return array;
        }

        private Object removeStickyFlag(Object tRegexFlags) {
            char[] flags = new char[6];
            int len = 0;
            if (this.flagsAccessor.hasIndices(tRegexFlags)) {
                flags[len++] = 100;
            }
            if (this.flagsAccessor.global(tRegexFlags)) {
                flags[len++] = 103;
            }
            if (this.flagsAccessor.ignoreCase(tRegexFlags)) {
                flags[len++] = 105;
            }
            if (this.flagsAccessor.multiline(tRegexFlags)) {
                flags[len++] = 109;
            }
            if (this.flagsAccessor.dotAll(tRegexFlags)) {
                flags[len++] = 115;
            }
            if (this.flagsAccessor.unicode(tRegexFlags)) {
                flags[len++] = 117;
            }
            if (len == 0) {
                return Strings.EMPTY_STRING;
            }
            return Strings.fromCharArray(flags, 0, len);
        }

        private Object ensureSticky(TruffleString flags) {
            if (this.emptyFlags.profile(Strings.length(flags) == 0)) {
                return Strings.Y;
            }
            if (this.stickyFlagSet.profile(this.indexOf(flags, 121) >= 0)) {
                return flags;
            }
            return this.concat(flags, Strings.Y);
        }

        private void initTRegexAccessors() {
            if (this.compiledRegexAccessor == null || this.flagsAccessor == null || this.resultAccessor == null || this.execIgnoreLastIndexNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.compiledRegexAccessor = this.insert(TRegexUtil.TRegexCompiledRegexAccessor.create());
                this.flagsAccessor = this.insert(TRegexUtil.TRegexFlagsAccessor.create());
                this.resultAccessor = this.insert(TRegexUtil.TRegexResultAccessor.create());
                this.execIgnoreLastIndexNode = this.insert(JSRegExpExecIntlNode.JSRegExpExecIntlIgnoreLastIndexNode.create(this.getContext(), false));
            }
        }

        private boolean isJSObject(JSDynamicObject rx) {
            if (this.isObjectNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.isObjectNode = this.insert(IsJSObjectNode.create());
            }
            return this.isObjectNode.executeBoolean(rx);
        }

        private TruffleString toString1(Object obj) {
            if (this.toString1Node == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.toString1Node = this.insert(JSToStringNode.create());
            }
            return this.toString1Node.executeString(obj);
        }

        private TruffleString toString2(Object obj) {
            if (this.toString2Node == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.toString2Node = this.insert(JSToStringNode.create());
            }
            return this.toString2Node.executeString(obj);
        }

        private Object getFlags(JSDynamicObject rx) {
            if (this.getFlagsNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.getFlagsNode = this.insert(PropertyGetNode.create(JSRegExp.FLAGS, false, this.getContext()));
            }
            return this.getFlagsNode.getValue(rx);
        }

        private boolean isPristine(JSDynamicObject rx) {
            if (this.isPristineObjectNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.isPristineObjectNode = this.insert(IsPristineObjectNode.createRegExpExecAndMatch(this.getContext()));
            }
            return this.isPristineObjectNode.execute(rx);
        }

        private long toLength(Object obj) {
            if (this.toLengthNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.toLengthNode = this.insert(JSToLengthNode.create());
            }
            return this.toLengthNode.executeLong(obj);
        }

        private Object getLength(JSDynamicObject obj) {
            if (this.getLengthNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.getLengthNode = this.insert(PropertyGetNode.create(Strings.LENGTH, false, this.getContext()));
            }
            return this.getLengthNode.getValue(obj);
        }

        private int movePosition(TruffleString s, boolean unicodeMatching, int lastIndex) {
            return this.isUnicode.profile(unicodeMatching) ? this.advanceStringIndexUnicode(s, lastIndex) : lastIndex + 1;
        }

        private long toUInt32(Object obj) {
            if (this.toUInt32Node == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.toUInt32Node = this.insert(JSToUInt32Node.create());
            }
            return this.toUInt32Node.executeLong(obj);
        }

        private int indexOf(TruffleString a, int codepoint) {
            if (this.stringIndexOfNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.stringIndexOfNode = this.insert(TruffleString.ByteIndexOfCodePointNode.create());
            }
            return Strings.indexOf(this.stringIndexOfNode, a, codepoint);
        }

        private TruffleString concat(TruffleString a, TruffleString b) {
            if (this.stringConcatNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.stringConcatNode = this.insert(TruffleString.ConcatNode.create());
            }
            return Strings.concat(this.stringConcatNode, a, b);
        }

        private TruffleString substring(TruffleString a, int fromIndex, int length) {
            if (this.substringNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.substringNode = this.insert(TruffleString.SubstringByteIndexNode.create());
            }
            return Strings.substring(this.getContext(), this.substringNode, a, fromIndex, length);
        }
    }

    public static abstract class RegExpPrototypeSymbolOperation
    extends JSBuiltinNode {
        @Node.Child
        private JSRegExpExecIntlNode regexExecIntlNode;
        @Node.Child
        private PropertyGetNode getLastIndexNode;
        @Node.Child
        private PropertySetNode setLastIndexNode;
        @Node.Child
        private WriteElementNode writeNode;
        @Node.Child
        private ReadElementNode readNode;
        @Node.Child
        private ArrayPrototypeBuiltins.ArraySpeciesConstructorNode arraySpeciesCreateNode;
        @Node.Child
        private JSToBooleanNode toBooleanNode;
        @Node.Child
        private TruffleString.ReadCharUTF16Node stringReadNode;
        private final ConditionProfile advanceIndexLengthProfile = ConditionProfile.createBinaryProfile();
        private final ConditionProfile advanceIndexFirstProfile = ConditionProfile.createBinaryProfile();
        private final ConditionProfile advanceIndexSecondProfile = ConditionProfile.createBinaryProfile();

        public RegExpPrototypeSymbolOperation(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        protected Object read(Object target, int index) {
            if (this.readNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.readNode = this.insert(ReadElementNode.create(this.getContext()));
            }
            return this.readNode.executeWithTargetAndIndex(target, index);
        }

        protected void write(Object target, int index, Object value2) {
            if (this.writeNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.writeNode = this.insert(WriteElementNode.create(this.getContext(), true, true));
            }
            this.writeNode.executeWithTargetAndIndexAndValue(target, index, value2);
        }

        private char charAt(TruffleString s, int i) {
            if (this.stringReadNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.stringReadNode = this.insert(TruffleString.ReadCharUTF16Node.create());
            }
            return Strings.charAt(this.stringReadNode, s, i);
        }

        protected final ArrayPrototypeBuiltins.ArraySpeciesConstructorNode getArraySpeciesConstructorNode() {
            if (this.arraySpeciesCreateNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.arraySpeciesCreateNode = this.insert(ArrayPrototypeBuiltins.ArraySpeciesConstructorNode.create(this.getContext(), false));
            }
            return this.arraySpeciesCreateNode;
        }

        protected int advanceStringIndexUnicode(TruffleString s, int index) {
            if (this.advanceIndexLengthProfile.profile(index + 1 >= Strings.length(s))) {
                return index + 1;
            }
            char first = this.charAt(s, index);
            if (this.advanceIndexFirstProfile.profile(first < '\ud800' || first > '\udbff')) {
                return index + 1;
            }
            char second = this.charAt(s, index + 1);
            if (this.advanceIndexSecondProfile.profile(second < '\udc00' || second > '\udfff')) {
                return index + 1;
            }
            return index + 2;
        }

        private void initLastIndexNode() {
            if (this.setLastIndexNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.setLastIndexNode = this.insert(PropertySetNode.create(JSRegExp.LAST_INDEX, false, this.getContext(), true));
            }
        }

        protected void setLastIndex(Object obj, int value2) {
            this.initLastIndexNode();
            this.setLastIndexNode.setValueInt(obj, value2);
        }

        protected void setLastIndex(Object obj, Object value2) {
            this.initLastIndexNode();
            this.setLastIndexNode.setValue(obj, value2);
        }

        protected Object getLastIndex(Object obj) {
            if (this.getLastIndexNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.getLastIndexNode = this.insert(PropertyGetNode.create(JSRegExp.LAST_INDEX, false, this.getContext()));
            }
            return this.getLastIndexNode.getValue(obj);
        }

        protected Object regexExecIntl(JSDynamicObject regex, Object input) {
            if (this.regexExecIntlNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.regexExecIntlNode = this.insert(JSRegExpExecIntlNode.create(this.getContext()));
            }
            return this.regexExecIntlNode.execute(regex, input);
        }

        protected final boolean getFlag(JSDynamicObject re, PropertyGetNode getNode) {
            boolean flag;
            if (this.toBooleanNode == null) {
                try {
                    flag = getNode.getValueBoolean(re);
                }
                catch (UnexpectedResultException e) {
                    CompilerDirectives.transferToInterpreterAndInvalidate();
                    this.toBooleanNode = this.insert(JSToBooleanNode.create());
                    flag = this.toBooleanNode.executeBoolean(e.getResult());
                }
            } else {
                flag = this.toBooleanNode.executeBoolean(getNode.getValue(re));
            }
            return flag;
        }
    }

    public static abstract class JSRegExpToStringNode
    extends JSBuiltinNode {
        @Node.Child
        private PropertyGetNode getSourceNode;
        @Node.Child
        private PropertyGetNode getFlagsNode;

        protected JSRegExpToStringNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
            this.getFlagsNode = PropertyGetNode.create(Strings.FLAGS, false, context);
            this.getSourceNode = PropertyGetNode.create(Strings.SOURCE, false, context);
        }

        @Specialization(guards={"isObjectNode.executeBoolean(thisObj)"}, limit="1")
        protected Object toString(JSDynamicObject thisObj, @Cached IsJSObjectNode isObjectNode, @Cached JSToStringNode toString1Node, @Cached JSToStringNode toString2Node) {
            TruffleString source = toString1Node.executeString(this.getSourceNode.getValue(thisObj));
            TruffleString flags = toString2Node.executeString(this.getFlagsNode.getValue(thisObj));
            return JSRegExpToStringNode.toStringIntl(source, flags);
        }

        @Fallback
        protected Object toString(Object thisNonObj) {
            throw Errors.createTypeErrorIncompatibleReceiver("RegExp.prototype.toString", thisNonObj);
        }

        @CompilerDirectives.TruffleBoundary
        private static Object toStringIntl(TruffleString source, TruffleString flags) {
            return Strings.concatAll(Strings.SLASH, source, Strings.SLASH, flags);
        }
    }

    public static abstract class JSRegExpTestNode
    extends JSBuiltinNode {
        @Node.Child
        private TRegexUtil.InteropReadBooleanMemberNode readIsMatch;

        protected JSRegExpTestNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization(guards={"isObjectNode.executeBoolean(thisObj)"}, limit="1")
        protected Object testGeneric(JSDynamicObject thisObj, Object input, @Cached IsJSObjectNode isObjectNode, @Cached JSToStringNode toStringNode, @Cached(value="create(getContext())") JSRegExpExecIntlNode regExpNode) {
            TruffleString inputStr = toStringNode.executeString(input);
            Object result = regExpNode.execute(thisObj, inputStr);
            if (this.getContext().getEcmaScriptVersion() >= 6) {
                return result != Null.instance;
            }
            return this.getReadIsMatch().execute(result, "isMatch");
        }

        @Fallback
        protected Object testError(Object thisNonObj, Object input) {
            throw Errors.createTypeErrorIncompatibleReceiver("RegExp.prototype.test", thisNonObj);
        }

        private TRegexUtil.InteropReadBooleanMemberNode getReadIsMatch() {
            if (this.readIsMatch == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.readIsMatch = this.insert(TRegexUtil.InteropReadBooleanMemberNode.create());
            }
            return this.readIsMatch;
        }
    }

    public static abstract class JSRegExpExecES5Node
    extends JSBuiltinNode {
        @Node.Child
        private JSToStringNode toStringNode;
        @Node.Child
        private JSRegExpExecIntlNode.JSRegExpExecBuiltinNode regExpNode;
        @Node.Child
        private PropertySetNode setIndexNode;
        @Node.Child
        private PropertySetNode setInputNode;
        @Node.Child
        private TRegexUtil.TRegexCompiledRegexAccessor compiledRegexAccessor = TRegexUtil.TRegexCompiledRegexAccessor.create();
        @Node.Child
        private TRegexUtil.TRegexResultAccessor resultAccessor = TRegexUtil.TRegexResultAccessor.create();
        @Node.Child
        private TRegexUtil.TRegexMaterializeResultNode resultMaterializer = TRegexUtil.TRegexMaterializeResultNode.create();
        private final ConditionProfile match = ConditionProfile.createCountingProfile();

        protected JSRegExpExecES5Node(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
            assert (context.getEcmaScriptVersion() < 6);
            this.regExpNode = JSRegExpExecIntlNode.JSRegExpExecBuiltinNode.create(context);
            this.toStringNode = JSToStringNode.create();
        }

        @Specialization
        protected JSDynamicObject exec(JSRegExpObject thisRegExp, Object input) {
            TruffleString inputStr = this.toStringNode.executeString(input);
            Object result = this.regExpNode.execute(thisRegExp, inputStr);
            if (this.match.profile(this.resultAccessor.isMatch(result))) {
                return this.getMatchResult(result, this.compiledRegexAccessor.groupCount(JSRegExp.getCompiledRegex(thisRegExp)), inputStr);
            }
            return Null.instance;
        }

        @Fallback
        protected Object exec(Object thisObj, Object input) {
            throw RegExpPrototypeBuiltins.createNoRegExpError(thisObj);
        }

        protected JSDynamicObject getMatchResult(Object result, int groupCount, TruffleString inputStr) {
            assert (this.getContext().getEcmaScriptVersion() < 6);
            if (this.setIndexNode == null || this.setInputNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.setIndexNode = this.insert(PropertySetNode.create(JSRegExp.INDEX, false, this.getContext(), false));
                this.setInputNode = this.insert(PropertySetNode.create(JSRegExp.INPUT, false, this.getContext(), false));
            }
            Object[] matches2 = this.resultMaterializer.materializeFull(this.getContext(), result, groupCount, inputStr);
            JSArrayObject array = JSArray.createConstant(this.getContext(), this.getRealm(), matches2);
            this.setIndexNode.setValueInt(array, this.resultAccessor.captureGroupStart(result, 0));
            this.setInputNode.setValue(array, inputStr);
            return array;
        }
    }

    public static abstract class JSRegExpExecNode
    extends JSBuiltinNode {
        @Node.Child
        private JSRegExpExecIntlNode.JSRegExpExecBuiltinNode regExpNode;

        JSRegExpExecNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
            assert (context.getEcmaScriptVersion() >= 6);
            this.regExpNode = JSRegExpExecIntlNode.JSRegExpExecBuiltinNode.create(context);
        }

        @Specialization
        JSDynamicObject doString(JSRegExpObject thisRegExp, TruffleString inputStr) {
            return (JSDynamicObject)this.regExpNode.execute(thisRegExp, inputStr);
        }

        @Specialization(replaces={"doString"})
        JSDynamicObject doObject(JSRegExpObject thisRegExp, Object input, @Cached JSToStringNode toStringNode) {
            return (JSDynamicObject)this.regExpNode.execute(thisRegExp, toStringNode.executeString(input));
        }

        @Fallback
        Object doNoRegExp(Object thisObj, Object input) {
            throw RegExpPrototypeBuiltins.createNoRegExpError(thisObj);
        }
    }

    public static abstract class JSRegExpCompileNode
    extends JSBuiltinNode {
        @Node.Child
        private PropertySetNode setLastIndexNode;

        protected JSRegExpCompileNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
            this.setLastIndexNode = PropertySetNode.create(JSRegExp.LAST_INDEX, false, context, true);
        }

        @Specialization
        protected JSRegExpObject compile(JSRegExpObject thisRegExp, Object patternObj, Object flagsObj, @Cached(value="create(getContext())") CompileRegexNode compileRegexNode, @Cached(value="createUndefinedToEmpty()") JSToStringNode toStringNode, @Cached(value="createBinaryProfile()") ConditionProfile isRegExpProfile, @Cached TRegexUtil.TRegexCompiledRegexAccessor compiledRegexAccessor, @Cached TRegexUtil.TRegexFlagsAccessor flagsAccessor) {
            Object flags;
            Object pattern;
            Object regex;
            if (this.getRealm() != JSRegExp.getRealm(thisRegExp)) {
                throw Errors.createTypeError("RegExp.prototype.compile cannot be used on a RegExp from a different Realm.");
            }
            if (!JSRegExp.getLegacyFeaturesEnabled(thisRegExp)) {
                throw Errors.createTypeError("RegExp.prototype.compile cannot be used on subclasses of RegExp.");
            }
            boolean isRegExp = isRegExpProfile.profile(JSRegExp.isJSRegExp(patternObj));
            if (isRegExp) {
                if (flagsObj != Undefined.instance) {
                    throw Errors.createTypeError("flags must be undefined", (Node)this);
                }
                regex = JSRegExp.getCompiledRegex((JSDynamicObject)patternObj);
                pattern = compiledRegexAccessor.pattern(regex);
                flags = flagsAccessor.source(compiledRegexAccessor.flags(regex));
            } else {
                pattern = toStringNode.executeString(patternObj);
                flags = toStringNode.executeString(flagsObj);
            }
            regex = compileRegexNode.compile(pattern, flags);
            JSRegExp.updateCompilation(this.getContext(), thisRegExp, regex);
            this.setLastIndexNode.setValueInt(thisRegExp, 0);
            return thisRegExp;
        }

        @Fallback
        protected Object compile(Object thisObj, Object pattern, Object flags) {
            throw RegExpPrototypeBuiltins.createNoRegExpError(thisObj);
        }
    }

    public static enum RegExpPrototype implements BuiltinEnum<RegExpPrototype>
    {
        exec(1),
        test(1),
        toString(0),
        _match(1, Symbol.SYMBOL_MATCH),
        _replace(2, Symbol.SYMBOL_REPLACE),
        _search(1, Symbol.SYMBOL_SEARCH),
        _split(2, Symbol.SYMBOL_SPLIT),
        compile(2),
        _matchAll(1, Symbol.SYMBOL_MATCH_ALL);

        private final int length;
        private final Symbol key;

        private RegExpPrototype(int length) {
            this(length, null);
        }

        private RegExpPrototype(int length, Symbol symbol) {
            this.length = length;
            this.key = symbol;
        }

        @Override
        public int getLength() {
            return this.length;
        }

        @Override
        public boolean isAnnexB() {
            return this == compile;
        }

        @Override
        public int getECMAScriptVersion() {
            if (EnumSet.of(_match, _replace, _search, _split).contains(this)) {
                return 6;
            }
            if (this.equals(_matchAll)) {
                return 10;
            }
            return BuiltinEnum.super.getECMAScriptVersion();
        }

        @Override
        public Object getKey() {
            return this.key != null ? this.key : BuiltinEnum.super.getKey();
        }
    }
}

