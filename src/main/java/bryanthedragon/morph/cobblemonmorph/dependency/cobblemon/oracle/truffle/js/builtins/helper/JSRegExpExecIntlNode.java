
package com.oracle.truffle.js.builtins.helper;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.profiles.ValueProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.helper.IsPristineObjectNode;
import com.oracle.truffle.js.builtins.helper.JSRegExpExecIntlNodeGen;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.access.IsJSClassNode;
import com.oracle.truffle.js.nodes.access.IsJSObjectNode;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.access.PropertySetNode;
import com.oracle.truffle.js.nodes.cast.JSToLengthNode;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.nodes.unary.IsCallableNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.JSArray;
import com.oracle.truffle.js.runtime.builtins.JSArrayObject;
import com.oracle.truffle.js.runtime.builtins.JSObjectFactory;
import com.oracle.truffle.js.runtime.builtins.JSRegExp;
import com.oracle.truffle.js.runtime.builtins.JSRegExpObject;
import com.oracle.truffle.js.runtime.objects.JSAttributes;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;
import com.oracle.truffle.js.runtime.objects.Null;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.TRegexUtil;

public abstract class JSRegExpExecIntlNode
extends JavaScriptBaseNode {
    private final JSContext context;
    @Node.Child
    private JSRegExpExecBuiltinNode regExpBuiltinNode;
    @Node.Child
    private PropertyGetNode getExecNode;
    @Node.Child
    private IsJSObjectNode isJSObjectNode;
    @Node.Child
    private IsPristineObjectNode isPristineObjectNode;
    @Node.Child
    private IsCallableNode isCallableNode = IsCallableNode.create();
    @Node.Child
    private JSFunctionCallNode specialCallNode;
    private final ConditionProfile isPristineProfile = ConditionProfile.createBinaryProfile();
    private final ConditionProfile isCallableProfile = ConditionProfile.createBinaryProfile();
    private final ConditionProfile validResultProfile = ConditionProfile.createBinaryProfile();
    private final ConditionProfile isRegExpProfile = ConditionProfile.createBinaryProfile();

    JSRegExpExecIntlNode(JSContext context) {
        this.context = context;
    }

    public static JSRegExpExecIntlNode create(JSContext context) {
        return JSRegExpExecIntlNodeGen.create(context);
    }

    public abstract Object execute(JSDynamicObject var1, Object var2);

    @Specialization
    Object doGeneric(JSDynamicObject regExp, TruffleString input) {
        if (this.context.getEcmaScriptVersion() >= 6) {
            if (this.isPristineProfile.profile(this.isPristine(regExp))) {
                return this.builtinExec((JSRegExpObject)regExp, input);
            }
            Object exec = this.getExecProperty(regExp);
            if (this.isCallableProfile.profile(this.isCallable(exec))) {
                return this.callJSFunction(regExp, input, exec);
            }
        }
        if (!this.isRegExpProfile.profile(JSRegExp.isJSRegExp(regExp))) {
            throw Errors.createTypeError("RegExp expected");
        }
        return this.builtinExec((JSRegExpObject)regExp, input);
    }

    private Object callJSFunction(JSDynamicObject regExp, Object input, Object exec) {
        Object result = this.doCallJSFunction(exec, regExp, input);
        if (this.validResultProfile.profile(result == Null.instance || this.isJSObject(result) && result != Undefined.instance)) {
            return result;
        }
        throw Errors.createTypeError("object or null expected");
    }

    private boolean isPristine(JSDynamicObject regExp) {
        if (this.isPristineObjectNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.isPristineObjectNode = this.insert(IsPristineObjectNode.createRegExpExecAndMatch(this.context));
        }
        return this.isPristineObjectNode.execute(regExp);
    }

    private Object getExecProperty(JSDynamicObject regExp) {
        if (this.getExecNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.getExecNode = this.insert(PropertyGetNode.create(Strings.EXEC, false, this.context));
        }
        return this.getExecNode.getValue(regExp);
    }

    private boolean isCallable(Object obj) {
        if (this.isCallableNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.isCallableNode = this.insert(IsCallableNode.create());
        }
        return this.isCallableNode.executeBoolean(obj);
    }

    private Object builtinExec(JSRegExpObject regExp, TruffleString input) {
        if (this.regExpBuiltinNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.regExpBuiltinNode = this.insert(JSRegExpExecBuiltinNode.create(this.context));
        }
        return this.regExpBuiltinNode.execute(regExp, input);
    }

    private boolean isJSObject(Object obj) {
        if (this.isJSObjectNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.isJSObjectNode = this.insert(IsJSObjectNode.create());
        }
        return this.isJSObjectNode.executeBoolean(obj);
    }

    private Object doCallJSFunction(Object exec, JSDynamicObject regExp, Object input) {
        if (this.specialCallNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.specialCallNode = this.insert(JSFunctionCallNode.createCall());
        }
        return this.specialCallNode.executeCall(JSArguments.createOneArg(regExp, exec, input));
    }

    public static IsJSClassNode createIsJSRegExpNode() {
        return IsJSClassNode.create(JSRegExp.INSTANCE);
    }

    private static Object executeCompiledRegex(Object compiledRegex, Object input, long fromIndex, TRegexUtil.TRegexCompiledRegexAccessor compiledRegexAccessor) {
        return compiledRegexAccessor.exec(compiledRegex, input, fromIndex);
    }

    @ImportStatic(value={JSRegExp.class, JSRegExpExecIntlNode.class})
    public static abstract class JSRegExpExecBuiltinNode
    extends JavaScriptBaseNode {
        private final JSContext context;
        @Node.Child
        private PropertySetNode setRegexResultNode;
        @Node.Child
        private PropertySetNode setRegexOriginalInputNode;
        @Node.Child
        private DynamicObjectLibrary setInputNode;
        @Node.Child
        private DynamicObjectLibrary setIndexNode;
        @Node.Child
        private DynamicObjectLibrary setGroupsNode;
        @Node.Child
        private DynamicObjectLibrary setIndicesNode;
        @Node.Child
        private DynamicObjectLibrary setIndicesRegexResultNode;
        @Node.Child
        private DynamicObjectLibrary setIndicesGroupsNode;
        private final ConditionProfile invalidLastIndex = ConditionProfile.createBinaryProfile();
        private final ConditionProfile match = ConditionProfile.createCountingProfile();
        private final ConditionProfile areLegacyFeaturesEnabled = ConditionProfile.createBinaryProfile();
        private final int ecmaScriptVersion;
        @Node.Child
        private JSToLengthNode toLengthNode;
        @Node.Child
        private PropertyGetNode getLastIndexNode;
        @Node.Child
        private PropertySetNode setLastIndexNode;
        @Node.Child
        private TRegexUtil.TRegexCompiledRegexAccessor compiledRegexAccessor = TRegexUtil.TRegexCompiledRegexAccessor.create();
        @Node.Child
        private TRegexUtil.TRegexFlagsAccessor flagsAccessor = TRegexUtil.TRegexFlagsAccessor.create();
        @Node.Child
        private TRegexUtil.TRegexResultAccessor regexResultAccessor = TRegexUtil.TRegexResultAccessor.create();
        @Node.Child
        private BuildGroupsObjectNode groupsBuilder;

        JSRegExpExecBuiltinNode(JSContext context) {
            this.context = context;
            this.ecmaScriptVersion = context.getEcmaScriptVersion();
            this.setRegexResultNode = PropertySetNode.createSetHidden(JSArray.LAZY_REGEX_RESULT_ID, context);
            this.setRegexOriginalInputNode = PropertySetNode.createSetHidden(JSArray.LAZY_REGEX_ORIGINAL_INPUT_ID, context);
            this.setInputNode = JSObjectUtil.createDispatched(JSRegExp.INPUT);
            this.setIndexNode = JSObjectUtil.createDispatched(JSRegExp.INDEX);
            this.setGroupsNode = JSObjectUtil.createDispatched(JSRegExp.GROUPS);
            this.setIndicesNode = JSObjectUtil.createDispatched(JSRegExp.INDICES);
            this.setIndicesRegexResultNode = JSObjectUtil.createDispatched(JSArray.LAZY_REGEX_RESULT_ID);
            this.setIndicesGroupsNode = JSObjectUtil.createDispatched(JSRegExp.GROUPS);
        }

        public static JSRegExpExecBuiltinNode create(JSContext context) {
            return JSRegExpExecIntlNodeGen.JSRegExpExecBuiltinNodeGen.create(context);
        }

        private Object getEmptyResult() {
            return this.ecmaScriptVersion >= 6 ? Null.instance : this.context.getTRegexEmptyResult();
        }

        public abstract Object execute(JSRegExpObject var1, TruffleString var2);

        @Specialization(guards={"getCompiledRegex(regExp) == cachedCompiledRegex"})
        Object doCached(JSRegExpObject regExp, TruffleString input, @Cached(value="getCompiledRegex(regExp)") Object cachedCompiledRegex) {
            return this.doExec(regExp, cachedCompiledRegex, input);
        }

        @Specialization(replaces={"doCached"})
        Object doDynamic(JSRegExpObject regExp, TruffleString input) {
            return this.doExec(regExp, JSRegExp.getCompiledRegex(regExp), input);
        }

        private Object doExec(JSRegExpObject regExp, Object compiledRegex, TruffleString input) {
            Object flags = this.compiledRegexAccessor.flags(compiledRegex);
            boolean global = this.flagsAccessor.global(flags);
            boolean sticky = this.ecmaScriptVersion >= 6 && this.flagsAccessor.sticky(flags);
            boolean hasIndices = this.flagsAccessor.hasIndices(flags);
            long lastIndex = this.getLastIndex(regExp);
            if (global || sticky) {
                if (this.invalidLastIndex.profile(lastIndex < 0L || lastIndex > (long)Strings.length(input))) {
                    this.setLastIndex(regExp, 0);
                    return this.getEmptyResult();
                }
            } else {
                lastIndex = 0L;
            }
            JSRealm thisRealm = this.getRealm();
            Object result = JSRegExpExecIntlNode.executeCompiledRegex(compiledRegex, input, lastIndex, this.compiledRegexAccessor);
            if (this.context.isOptionRegexpStaticResult() && this.regexResultAccessor.isMatch(result) && this.isSameRealm(regExp, thisRealm)) {
                if (this.areLegacyFeaturesEnabled.profile(JSRegExp.getLegacyFeaturesEnabled(regExp))) {
                    thisRealm.setStaticRegexResult(this.context, compiledRegex, input, lastIndex, result);
                } else {
                    thisRealm.invalidateStaticRegexResult();
                }
            }
            if (this.match.profile(this.regexResultAccessor.isMatch(result))) {
                assert (!sticky || (long)this.regexResultAccessor.captureGroupStart(result, 0) == lastIndex);
                if (global || sticky) {
                    this.setLastIndex(regExp, this.regexResultAccessor.captureGroupEnd(result, 0));
                }
                if (this.ecmaScriptVersion < 6) {
                    return result;
                }
                int groupCount = this.compiledRegexAccessor.groupCount(compiledRegex);
                return this.getMatchResult(regExp, result, groupCount, input, hasIndices, thisRealm);
            }
            if (this.ecmaScriptVersion < 8 || global || sticky) {
                this.setLastIndex(regExp, 0);
            }
            return this.getEmptyResult();
        }

        private boolean isSameRealm(JSRegExpObject regExp, JSRealm thisRealm) {
            if (this.context.isSingleRealm()) {
                assert (JSRegExp.getRealm(regExp) == thisRealm);
                return true;
            }
            return JSRegExp.getRealm(regExp) == thisRealm;
        }

        private JSArrayObject getMatchResult(JSRegExpObject regExp, Object regexResult, int groupCount, TruffleString inputStr, boolean hasIndices, JSRealm realm) {
            JSDynamicObject groups = this.getGroupsObject(regExp, regexResult, inputStr, false);
            JSArrayObject resultArray = JSArray.createLazyRegexArray(this.context, realm, groupCount);
            this.setRegexResultNode.setValue(resultArray, regexResult);
            this.setRegexOriginalInputNode.setValue(resultArray, inputStr);
            this.setIndexNode.putConstant(resultArray, JSRegExp.INDEX, JSRegExp.LAZY_INDEX_PROXY, JSAttributes.getDefault() | 0x10);
            this.setInputNode.put(resultArray, JSRegExp.INPUT, inputStr);
            this.setGroupsNode.put(resultArray, JSRegExp.GROUPS, groups);
            if (this.context.isOptionRegexpMatchIndices() && hasIndices) {
                JSDynamicObject indicesGroups = this.getGroupsObject(regExp, regexResult, inputStr, true);
                JSArrayObject indicesArray = JSArray.createLazyRegexIndicesArray(this.context, realm, groupCount);
                this.setIndicesRegexResultNode.put(indicesArray, JSRegExp.GROUPS_RESULT_ID, regexResult);
                this.setIndicesGroupsNode.put(indicesArray, JSRegExp.GROUPS, indicesGroups);
                this.setIndicesNode.put(resultArray, JSRegExp.INDICES, indicesArray);
            }
            return resultArray;
        }

        private JSDynamicObject getGroupsObject(JSDynamicObject regExp, Object result, Object input, boolean isIndices) {
            if (this.groupsBuilder == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.groupsBuilder = this.insert(BuildGroupsObjectNode.create());
            }
            return this.groupsBuilder.execute(this.context, regExp, result, input, isIndices);
        }

        private long getLastIndex(JSDynamicObject regExp) {
            if (this.getLastIndexNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.getLastIndexNode = this.insert(PropertyGetNode.create(JSRegExp.LAST_INDEX, false, this.context));
            }
            Object lastIndex = this.getLastIndexNode.getValue(regExp);
            if (this.ecmaScriptVersion < 6) {
                return JSRuntime.toInteger(lastIndex);
            }
            if (this.toLengthNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.toLengthNode = this.insert(JSToLengthNode.create());
            }
            return this.toLengthNode.executeLong(lastIndex);
        }

        private void setLastIndex(JSDynamicObject regExp, int value2) {
            if (this.setLastIndexNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.setLastIndexNode = this.insert(PropertySetNode.create(JSRegExp.LAST_INDEX, false, this.context, true));
            }
            this.setLastIndexNode.setValueInt(regExp, value2);
        }
    }

    @ImportStatic(value={JSRegExp.class, JSRegExpExecIntlNode.class})
    public static abstract class BuildGroupsObjectNode
    extends JavaScriptBaseNode {
        public static BuildGroupsObjectNode create() {
            return JSRegExpExecIntlNodeGen.BuildGroupsObjectNodeGen.create();
        }

        public abstract JSDynamicObject execute(JSContext var1, JSDynamicObject var2, Object var3, Object var4, boolean var5);

        @Specialization(guards={"getGroupsFactory(regExp) == cachedGroupsFactory || getCompiledRegex(regExp) == cachedCompiledRegex"})
        final JSDynamicObject doCachedGroupsFactory(JSContext context, JSDynamicObject regExp, Object regexResult, TruffleString input, boolean isIndices, @Cached(value="getCompiledRegex(regExp)") Object cachedCompiledRegex, @Cached(value="getGroupsFactory(regExp)") JSObjectFactory cachedGroupsFactory, @Cached(value="createIsJSRegExpNode()") IsJSClassNode isJSRegExpNode) {
            return BuildGroupsObjectNode.doIt(context, this.getRealm(), cachedGroupsFactory, regexResult, input, isIndices);
        }

        @Specialization
        @CompilerDirectives.TruffleBoundary
        final JSDynamicObject doVaryingGroupsFactory(JSContext context, JSDynamicObject regExp, Object regexResult, TruffleString input, boolean isIndices) {
            return BuildGroupsObjectNode.doIt(context, this.getRealm(), JSRegExp.getGroupsFactory(regExp), regexResult, input, isIndices);
        }

        private static JSDynamicObject doIt(JSContext context, JSRealm realm, JSObjectFactory groupsFactory, Object regexResult, TruffleString input, boolean isIndices) {
            if (groupsFactory == null) {
                return Undefined.instance;
            }
            return JSRegExp.createGroupsObject(context, realm, groupsFactory, regexResult, input, isIndices);
        }
    }

    @ImportStatic(value={JSRegExpExecIntlNode.class})
    public static abstract class JSRegExpExecIntlIgnoreLastIndexNode
    extends JavaScriptBaseNode {
        private final JSContext context;
        private final boolean doStaticResultUpdate;
        private final ValueProfile compiledRegexProfile = ValueProfile.createIdentityProfile();
        private final ConditionProfile areLegacyFeaturesEnabled = ConditionProfile.createBinaryProfile();

        JSRegExpExecIntlIgnoreLastIndexNode(JSContext context, boolean doStaticResultUpdate) {
            this.context = context;
            this.doStaticResultUpdate = doStaticResultUpdate;
        }

        public static JSRegExpExecIntlIgnoreLastIndexNode create(JSContext context, boolean doStaticResultUpdate) {
            return JSRegExpExecIntlNodeGen.JSRegExpExecIntlIgnoreLastIndexNodeGen.create(context, doStaticResultUpdate);
        }

        public abstract Object execute(JSDynamicObject var1, Object var2, long var3);

        @Specialization
        Object doGeneric(JSDynamicObject regExp, TruffleString input, long lastIndex, @Cached(value="create()") TRegexUtil.TRegexCompiledRegexAccessor compiledRegexAccessor, @Cached(value="create()") TRegexUtil.TRegexResultAccessor regexResultAccessor) {
            JSRealm thisRealm;
            assert (JSRegExp.isJSRegExp(regExp));
            Object compiledRegex = this.compiledRegexProfile.profile(JSRegExp.getCompiledRegex(regExp));
            Object result = JSRegExpExecIntlNode.executeCompiledRegex(compiledRegex, input, lastIndex, compiledRegexAccessor);
            if (this.doStaticResultUpdate && this.context.isOptionRegexpStaticResult() && regexResultAccessor.isMatch(result) && (thisRealm = this.getRealm()) == JSRegExp.getRealm(regExp)) {
                if (this.areLegacyFeaturesEnabled.profile(JSRegExp.getLegacyFeaturesEnabled(regExp))) {
                    thisRealm.setStaticRegexResult(this.context, compiledRegex, input, lastIndex, result);
                } else {
                    thisRealm.invalidateStaticRegexResult();
                }
            }
            return result;
        }
    }
}

