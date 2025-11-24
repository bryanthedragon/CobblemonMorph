
package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import com.oracle.truffle.api.object.HiddenKey;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.RegExpPrototypeBuiltins;
import com.oracle.truffle.js.lang.JavaScriptLanguage;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.ToDisplayStringFormat;
import com.oracle.truffle.js.runtime.array.dyn.LazyRegexResultIndicesArray;
import com.oracle.truffle.js.runtime.builtins.JSAbstractArray;
import com.oracle.truffle.js.runtime.builtins.JSConstructor;
import com.oracle.truffle.js.runtime.builtins.JSConstructorFactory;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.builtins.JSNonProxy;
import com.oracle.truffle.js.runtime.builtins.JSObjectFactory;
import com.oracle.truffle.js.runtime.builtins.JSOrdinary;
import com.oracle.truffle.js.runtime.builtins.JSRegExpGroupsObject;
import com.oracle.truffle.js.runtime.builtins.JSRegExpObject;
import com.oracle.truffle.js.runtime.builtins.PrototypeSupplier;
import com.oracle.truffle.js.runtime.interop.JSInteropUtil;
import com.oracle.truffle.js.runtime.objects.JSAttributes;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;
import com.oracle.truffle.js.runtime.objects.JSShape;
import com.oracle.truffle.js.runtime.objects.Null;
import com.oracle.truffle.js.runtime.objects.PropertyProxy;
import com.oracle.truffle.js.runtime.util.Pair;
import com.oracle.truffle.js.runtime.util.TRegexUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public final class JSRegExp
extends JSNonProxy
implements JSConstructorFactory.Default,
PrototypeSupplier {
    private static final TruffleString BRACKET_REG_EXP_SPC = Strings.constant("[RegExp ");
    public static final JSRegExp INSTANCE = new JSRegExp();
    public static final TruffleString CLASS_NAME = Strings.constant("RegExp");
    public static final TruffleString PROTOTYPE_NAME = Strings.concat(CLASS_NAME, Strings.DOT_PROTOTYPE);
    public static final TruffleString MULTILINE = Strings.constant("multiline");
    public static final TruffleString GLOBAL = Strings.constant("global");
    public static final TruffleString IGNORE_CASE = Strings.constant("ignoreCase");
    public static final TruffleString STICKY = Strings.constant("sticky");
    public static final TruffleString UNICODE = Strings.constant("unicode");
    public static final TruffleString DOT_ALL = Strings.constant("dotAll");
    public static final TruffleString SOURCE = Strings.constant("source");
    public static final TruffleString FLAGS = Strings.constant("flags");
    public static final TruffleString LAST_INDEX = Strings.constant("lastIndex");
    public static final TruffleString INPUT = Strings.constant("input");
    public static final TruffleString GROUPS = Strings.constant("groups");
    public static final TruffleString INDEX = Strings.constant("index");
    public static final TruffleString INDICES = Strings.constant("indices");
    public static final TruffleString HAS_INDICES = Strings.constant("hasIndices");
    public static final PropertyProxy LAZY_INDEX_PROXY = new LazyRegexResultIndexProxyProperty();
    public static final HiddenKey GROUPS_RESULT_ID = new HiddenKey("regexResult");
    public static final int MAX_FLAGS_LENGTH = 7;
    private static final Comparator<Pair<Integer, TruffleString>> NAMED_GROUPS_COMPARATOR = new Comparator<Pair<Integer, TruffleString>>(){

        @Override
        public int compare(Pair<Integer, TruffleString> group1, Pair<Integer, TruffleString> group2) {
            return group1.getFirst() - group2.getFirst();
        }
    };

    private JSRegExp() {
    }

    public static Object getCompiledRegex(JSDynamicObject thisObj) {
        assert (JSRegExp.isJSRegExp(thisObj));
        return ((JSRegExpObject)thisObj).getCompiledRegex();
    }

    public static JSObjectFactory getGroupsFactory(JSDynamicObject thisObj) {
        assert (JSRegExp.isJSRegExp(thisObj));
        return ((JSRegExpObject)thisObj).getGroupsFactory();
    }

    public static Object getRealm(JSDynamicObject thisObj) {
        assert (JSRegExp.isJSRegExp(thisObj));
        return ((JSRegExpObject)thisObj).getRealm();
    }

    public static boolean getLegacyFeaturesEnabled(JSDynamicObject thisObj) {
        assert (JSRegExp.isJSRegExp(thisObj));
        return ((JSRegExpObject)thisObj).getLegacyFeaturesEnabled();
    }

    public static JSRegExpObject create(JSContext ctx, JSRealm realm, Object compiledRegex) {
        JSObjectFactory groupsFactory = JSRegExp.computeGroupsFactory(ctx, compiledRegex);
        JSRegExpObject obj = JSRegExp.create(ctx, realm, compiledRegex, groupsFactory);
        JSObjectUtil.putDataProperty(ctx, obj, LAST_INDEX, 0, JSAttributes.notConfigurableNotEnumerableWritable());
        return obj;
    }

    public static JSRegExpObject create(JSContext context, JSRealm realm, Object compiledRegex, JSObjectFactory groupsFactory) {
        return JSRegExp.create(context, realm, compiledRegex, groupsFactory, true);
    }

    public static JSRegExpObject create(JSContext context, JSRealm realm, Object compiledRegex, JSObjectFactory groupsFactory, boolean legacyFeaturesEnabled) {
        JSRegExpObject regExp = JSRegExpObject.create(realm, context.getRegExpFactory(), compiledRegex, groupsFactory, legacyFeaturesEnabled);
        return context.trackAllocation(regExp);
    }

    private static void initialize(JSContext ctx, JSDynamicObject regExp, Object regex) {
        ((JSRegExpObject)regExp).setCompiledRegex(regex);
        ((JSRegExpObject)regExp).setGroupsFactory(JSRegExp.computeGroupsFactory(ctx, regex));
    }

    public static void updateCompilation(JSContext ctx, JSDynamicObject thisObj, Object regex) {
        assert (JSRegExp.isJSRegExp(thisObj) && regex != null);
        JSRegExp.initialize(ctx, thisObj, regex);
    }

    public static JSDynamicObject createGroupsObject(JSContext context, JSRealm realm, JSObjectFactory groupsFactory, Object regexResult, TruffleString input, boolean isIndices) {
        JSObject obj = JSRegExpGroupsObject.create(realm, groupsFactory, regexResult, input, isIndices);
        return context.trackAllocation(obj);
    }

    @CompilerDirectives.TruffleBoundary
    private static JSObjectFactory computeGroupsFactory(JSContext ctx, Object compiledRegex) {
        Object namedCaptureGroups = TRegexUtil.InteropReadMemberNode.getUncached().execute(compiledRegex, "groups");
        if (InteropLibrary.getUncached().isNull(namedCaptureGroups)) {
            return null;
        }
        return JSRegExp.buildGroupsFactory(ctx, namedCaptureGroups);
    }

    @CompilerDirectives.TruffleBoundary
    public static JSObjectFactory buildGroupsFactory(JSContext ctx, Object namedCaptureGroups) {
        try {
            Shape groupsShape = ctx.getRegExpGroupsEmptyShape();
            List<Object> keys = JSInteropUtil.keys(namedCaptureGroups);
            ArrayList<Pair<Integer, TruffleString>> pairs = new ArrayList<Pair<Integer, TruffleString>>(keys.size());
            for (Object key : keys) {
                int n = TRegexUtil.InteropReadIntMemberNode.getUncached().execute(namedCaptureGroups, InteropLibrary.getUncached().asString(key));
                TruffleString groupName = InteropLibrary.getUncached().asTruffleString(key);
                pairs.add(new Pair<Integer, TruffleString>(n, groupName));
            }
            Collections.sort(pairs, NAMED_GROUPS_COMPARATOR);
            Shape.DerivedBuilder builder = Shape.newBuilder(groupsShape);
            for (Pair pair : pairs) {
                int groupIndex = (Integer)pair.getFirst();
                TruffleString groupName = (TruffleString)pair.getSecond();
                builder.addConstantProperty(groupName, new LazyNamedCaptureGroupProperty(ctx, groupName, groupIndex), JSAttributes.getDefault() | 0x10);
            }
            groupsShape = builder.build();
            return JSObjectFactory.createBound(ctx, Null.instance, groupsShape);
        }
        catch (UnsupportedMessageException e) {
            throw CompilerDirectives.shouldNotReachHere();
        }
    }

    @CompilerDirectives.TruffleBoundary
    public static TruffleString prototypeToString(JSDynamicObject thisObj) {
        Object regex = JSRegExp.getCompiledRegex(thisObj);
        TRegexUtil.InteropReadStringMemberNode readString = TRegexUtil.InteropReadStringMemberNode.getUncached();
        TruffleString pattern = readString.execute(regex, "pattern");
        if (Strings.length(pattern) == 0) {
            pattern = Strings.EMPTY_REGEX;
        }
        TruffleString flags = readString.execute(TRegexUtil.InteropReadMemberNode.getUncached().execute(regex, "flags"), "source");
        return Strings.concatAll(Strings.SLASH, pattern, Strings.SLASH, flags);
    }

    public static boolean isJSRegExp(Object obj) {
        return obj instanceof JSRegExpObject;
    }

    @Override
    public JSDynamicObject createPrototype(JSRealm realm, JSFunctionObject ctor) {
        JSObject prototype;
        JSContext ctx = realm.getContext();
        if (ctx.getEcmaScriptVersion() < 6) {
            Shape shape = JSShape.createPrototypeShape(realm.getContext(), INSTANCE, realm.getObjectPrototype());
            prototype = JSRegExpObject.create(shape, JSRegExp.es5GetEmptyRegexEarly(realm), realm);
            JSObjectUtil.setOrVerifyPrototype(ctx, prototype, realm.getObjectPrototype());
            JSObjectUtil.putDataProperty(ctx, prototype, LAST_INDEX, 0, JSAttributes.notConfigurableNotEnumerableWritable());
        } else {
            prototype = JSObjectUtil.createOrdinaryPrototypeObject(realm);
        }
        JSRegExp.putRegExpPropertyAccessor(realm, prototype, SOURCE);
        JSRegExp.putRegExpPropertyAccessor(realm, prototype, FLAGS);
        JSRegExp.putRegExpPropertyAccessor(realm, prototype, MULTILINE);
        JSRegExp.putRegExpPropertyAccessor(realm, prototype, GLOBAL);
        JSRegExp.putRegExpPropertyAccessor(realm, prototype, IGNORE_CASE);
        if (ctx.getEcmaScriptVersion() >= 6) {
            JSRegExp.putRegExpPropertyAccessor(realm, prototype, STICKY);
            JSRegExp.putRegExpPropertyAccessor(realm, prototype, UNICODE);
        }
        if (ctx.getEcmaScriptVersion() >= 9) {
            JSRegExp.putRegExpPropertyAccessor(realm, prototype, DOT_ALL);
        }
        if (ctx.isOptionRegexpMatchIndices()) {
            JSRegExp.putRegExpPropertyAccessor(realm, prototype, HAS_INDICES);
        }
        JSObjectUtil.putConstructorProperty(ctx, prototype, ctor);
        JSObjectUtil.putFunctionsFromContainer(realm, prototype, RegExpPrototypeBuiltins.BUILTINS);
        return prototype;
    }

    private static void putRegExpPropertyAccessor(JSRealm realm, JSDynamicObject prototype, TruffleString name) {
        JSObjectUtil.putBuiltinAccessorProperty(prototype, name, realm.lookupAccessor(RegExpPrototypeBuiltins.RegExpPrototypeGetterBuiltins.BUILTINS, name));
    }

    private static Object es5GetEmptyRegexEarly(JSRealm realm) {
        return realm.getEnv().parseInternal(Source.newBuilder("regex", "//", "//").mimeType("application/tregex").internal(true).build(), new String[0]).call(new Object[0]);
    }

    @Override
    public Shape makeInitialShape(JSContext ctx, JSDynamicObject thisObj) {
        return JSObjectUtil.getProtoChildShape(thisObj, INSTANCE, ctx);
    }

    public static Shape makeInitialGroupsObjectShape(JSContext context) {
        CompilerAsserts.neverPartOfCompilation();
        return JSShape.createRootWithNullProto(context, JSOrdinary.BARE_INSTANCE);
    }

    @Override
    public void fillConstructor(JSRealm realm, JSDynamicObject constructor) {
        JSRegExp.putConstructorSpeciesGetter(realm, constructor);
    }

    public static JSConstructor createConstructor(JSRealm realm) {
        return INSTANCE.createConstructorAndPrototype(realm);
    }

    @Override
    public TruffleString getClassName() {
        return CLASS_NAME;
    }

    @Override
    public TruffleString getClassName(JSDynamicObject object) {
        return this.getClassName();
    }

    @Override
    public TruffleString getBuiltinToStringTag(JSDynamicObject object) {
        return this.getClassName(object);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public TruffleString toDisplayStringImpl(JSDynamicObject obj, boolean allowSideEffects, ToDisplayStringFormat format, int depth) {
        if (JavaScriptLanguage.get(null).getJSContext().isOptionNashornCompatibilityMode()) {
            return Strings.concatAll(BRACKET_REG_EXP_SPC, JSRegExp.prototypeToString(obj), Strings.BRACKET_CLOSE);
        }
        return JSRegExp.prototypeToString(obj);
    }

    @Override
    public JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
        return realm.getRegExpPrototype();
    }

    @CompilerDirectives.TruffleBoundary
    public static TruffleString escapeRegExpPattern(TruffleString pattern) {
        if (Strings.length(pattern) == 0) {
            return Strings.EMPTY_REGEX;
        }
        int extraChars = JSRegExp.escapeRegExpExtraCharCount(pattern);
        if (extraChars == 0) {
            return pattern;
        }
        return JSRegExp.escapeRegExpPattern(pattern, extraChars);
    }

    private static int escapeRegExpExtraCharCount(TruffleString pattern) {
        int extraChars = 0;
        boolean insideCharClass = false;
        block12: for (int i = 0; i < Strings.length(pattern); ++i) {
            switch (Strings.charAt(pattern, i)) {
                case '\\': {
                    assert (i + 1 < Strings.length(pattern));
                    switch (Strings.charAt(pattern, ++i)) {
                        case '\n': 
                        case '\r': {
                            extraChars = Math.max(extraChars, 1);
                            break;
                        }
                        case '\u2028': 
                        case '\u2029': {
                            extraChars += 4;
                        }
                    }
                    continue block12;
                }
                case '\n': 
                case '\r': {
                    ++extraChars;
                    continue block12;
                }
                case '\u2028': 
                case '\u2029': {
                    extraChars += 5;
                    continue block12;
                }
                case '/': {
                    if (insideCharClass) continue block12;
                    ++extraChars;
                    continue block12;
                }
                case '[': {
                    insideCharClass = true;
                    continue block12;
                }
                case ']': {
                    insideCharClass = false;
                }
            }
        }
        return extraChars;
    }

    @CompilerDirectives.TruffleBoundary
    private static TruffleString escapeRegExpPattern(TruffleString pattern, int extraChars) {
        StringBuilder sb = new StringBuilder(Strings.length(pattern) + extraChars);
        boolean insideCharClass = false;
        block16: for (int i = 0; i < Strings.length(pattern); ++i) {
            char c = Strings.charAt(pattern, i);
            switch (c) {
                case '\\': {
                    assert (i + 1 < Strings.length(pattern));
                    sb.append(c);
                    c = Strings.charAt(pattern, ++i);
                    switch (c) {
                        case '\n': {
                            sb.append('n');
                            continue block16;
                        }
                        case '\r': {
                            sb.append('r');
                            continue block16;
                        }
                        case '\u2028': {
                            sb.append("u2028");
                            continue block16;
                        }
                        case '\u2029': {
                            sb.append("u2029");
                            continue block16;
                        }
                    }
                    sb.append(c);
                    continue block16;
                }
                case '\n': {
                    sb.append("\\n");
                    continue block16;
                }
                case '\r': {
                    sb.append("\\r");
                    continue block16;
                }
                case '\u2028': {
                    sb.append("\\u2028");
                    continue block16;
                }
                case '\u2029': {
                    sb.append("\\u2029");
                    continue block16;
                }
                case '/': {
                    if (!insideCharClass) {
                        sb.append("\\/");
                        continue block16;
                    }
                    sb.append('/');
                    continue block16;
                }
                case '[': {
                    insideCharClass = true;
                    sb.append(c);
                    continue block16;
                }
                case ']': {
                    insideCharClass = false;
                    sb.append(c);
                    continue block16;
                }
                default: {
                    sb.append(c);
                }
            }
        }
        return Strings.fromJavaString(sb.toString());
    }

    public static final class LazyNamedCaptureGroupProperty
    extends PropertyProxy {
        private final JSContext context;
        private final TruffleString groupName;
        private final int groupIndex;
        private final ConditionProfile isIndicesObject = ConditionProfile.createBinaryProfile();
        private final TRegexUtil.TRegexMaterializeResultNode materializeNode = TRegexUtil.TRegexMaterializeResultNode.getUncached();

        public LazyNamedCaptureGroupProperty(JSContext context, TruffleString groupName, int groupIndex) {
            this.context = context;
            this.groupName = groupName;
            this.groupIndex = groupIndex;
        }

        public int getGroupIndex() {
            return this.groupIndex;
        }

        @Override
        public Object get(JSDynamicObject object) {
            JSRegExpGroupsObject groups = (JSRegExpGroupsObject)object;
            Object regexResult = groups.getRegexResult();
            if (this.isIndicesObject.profile(groups.isIndices())) {
                return LazyRegexResultIndicesArray.getIntIndicesArray(JavaScriptLanguage.getCurrentLanguage().getJSContext(), TRegexUtil.TRegexResultAccessor.getUncached(), regexResult, this.groupIndex);
            }
            TruffleString input = groups.getInputString();
            return this.materializeNode.materializeGroup(this.context, regexResult, this.groupIndex, input);
        }

        @Override
        public boolean set(JSDynamicObject object, Object value2) {
            JSObjectUtil.defineDataProperty(object, this.groupName, value2, JSAttributes.getDefault());
            return true;
        }
    }

    public static final class LazyRegexResultIndexProxyProperty
    extends PropertyProxy {
        @Override
        public Object get(JSDynamicObject object) {
            return TRegexUtil.InvokeGetGroupBoundariesMethodNode.getUncached().execute(JSAbstractArray.arrayGetRegexResult(object, DynamicObjectLibrary.getUncached()), "getStart", 0);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean set(JSDynamicObject object, Object value2) {
            JSObjectUtil.defineDataProperty(object, INDEX, value2, JSAttributes.getDefault());
            return true;
        }
    }
}

