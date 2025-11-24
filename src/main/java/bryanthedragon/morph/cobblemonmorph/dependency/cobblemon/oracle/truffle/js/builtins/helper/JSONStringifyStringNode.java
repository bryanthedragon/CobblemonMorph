
package com.oracle.truffle.js.builtins.helper;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.InvalidArrayIndexException;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.api.strings.TruffleStringBuilder;
import com.oracle.truffle.js.builtins.helper.JSONData;
import com.oracle.truffle.js.builtins.helper.JSONStringifyStringNodeGen;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.builtins.JSArray;
import com.oracle.truffle.js.runtime.builtins.JSBigInt;
import com.oracle.truffle.js.runtime.builtins.JSBoolean;
import com.oracle.truffle.js.runtime.builtins.JSClass;
import com.oracle.truffle.js.runtime.builtins.JSNumber;
import com.oracle.truffle.js.runtime.builtins.JSString;
import com.oracle.truffle.js.runtime.interop.JSInteropUtil;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.Null;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.StringBuilderProfile;
import java.util.List;

public abstract class JSONStringifyStringNode
extends JavaScriptBaseNode {
    private final JSContext context;
    @Node.Child
    private PropertyGetNode getToJSONProperty;
    @Node.Child
    private JSFunctionCallNode callToJSONFunction;
    @Node.Child
    private TruffleStringBuilder.AppendCharUTF16Node appendCharNode;
    @Node.Child
    private TruffleStringBuilder.AppendIntNumberNode appendIntNode;
    @Node.Child
    private TruffleStringBuilder.AppendLongNumberNode appendLongNode;
    @Node.Child
    private TruffleStringBuilder.AppendStringNode appendStringNode;
    @Node.Child
    private TruffleStringBuilder.ToStringNode builderToStringNode;
    private final StringBuilderProfile stringBuilderProfile;

    protected JSONStringifyStringNode(JSContext context) {
        this.context = context;
        this.stringBuilderProfile = StringBuilderProfile.create(context.getStringLengthLimit());
    }

    public abstract Object execute(Object var1, Object var2, JSDynamicObject var3);

    public static JSONStringifyStringNode create(JSContext context) {
        return JSONStringifyStringNodeGen.create(context);
    }

    @Specialization
    public Object jsonStrMain(Object jsonData, TruffleString keyStr, JSDynamicObject holder) {
        try {
            assert (jsonData instanceof JSONData);
            JSONData data = (JSONData)jsonData;
            Object value2 = this.jsonStrPrepare(data, keyStr, holder);
            if (!JSONStringifyStringNode.isStringifyable(value2)) {
                return Undefined.instance;
            }
            TruffleStringBuilder sb = this.stringBuilderProfile.newStringBuilder();
            this.jsonStrExecute(sb, data, value2);
            return this.builderToString(sb);
        }
        catch (StackOverflowError ex) {
            JSONStringifyStringNode.throwStackError();
            return null;
        }
    }

    private static boolean isStringifyable(Object value2) {
        return value2 != Undefined.instance;
    }

    @CompilerDirectives.TruffleBoundary
    private void jsonStrExecute(TruffleStringBuilder builder, JSONData data, Object value2) {
        assert (JSONStringifyStringNode.isStringifyable(value2));
        if (value2 == Null.instance) {
            this.append(builder, Null.NAME);
        } else if (value2 instanceof Boolean) {
            this.append(builder, (Boolean)value2 != false ? JSBoolean.TRUE_NAME : JSBoolean.FALSE_NAME);
        } else if (Strings.isTString(value2)) {
            this.jsonQuote(builder, (TruffleString)value2);
        } else if (JSRuntime.isNumber(value2)) {
            this.appendNumber(builder, (Number)value2);
        } else {
            if (JSRuntime.isBigInt(value2)) {
                throw Errors.createTypeError("Do not know how to serialize a BigInt");
            }
            if (JSDynamicObject.isJSDynamicObject(value2) && !JSRuntime.isCallableIsJSObject((JSDynamicObject)value2)) {
                JSDynamicObject valueObj = (JSDynamicObject)value2;
                if (JSRuntime.isArray(valueObj)) {
                    this.jsonJA(builder, data, valueObj);
                } else {
                    this.jsonJO(builder, data, valueObj);
                }
            } else if (value2 instanceof TruffleObject) {
                assert (JSGuards.isForeignObject(value2));
                this.jsonForeignObject(builder, data, value2);
            } else if (JSRuntime.isJavaPrimitive(value2)) {
                this.jsonQuote(builder, Strings.fromJavaString(value2.toString()));
            } else {
                throw new RuntimeException("JSON.stringify: should never reach here, unknown type: " + value2 + " " + value2.getClass());
            }
        }
    }

    private void jsonForeignObject(TruffleStringBuilder sb, JSONData data, Object obj) {
        InteropLibrary interop = InteropLibrary.getFactory().getUncached(obj);
        if (interop.isNull(obj)) {
            this.append(sb, Null.NAME);
        } else if (JSInteropUtil.isBoxedPrimitive(obj, interop)) {
            Object unboxed = JSInteropUtil.toPrimitiveOrDefault(obj, Null.instance, interop, this);
            assert (!JSGuards.isForeignObject(unboxed));
            this.jsonStrExecute(sb, data, unboxed);
        } else if (interop.hasArrayElements(obj)) {
            this.jsonJA(sb, data, obj);
        } else {
            this.jsonJO(sb, data, obj);
        }
    }

    private void appendNumber(TruffleStringBuilder builder, Number n) {
        double d = JSRuntime.doubleValue(n);
        if (Double.isNaN(d) || Double.isInfinite(d)) {
            this.append(builder, Null.NAME);
        } else if (n instanceof Integer) {
            this.append(builder, (Integer)n);
        } else if (n instanceof Long) {
            this.append(builder, (Long)n);
        } else {
            this.append(builder, JSRuntime.doubleToString(d));
        }
    }

    @CompilerDirectives.TruffleBoundary
    private Object jsonStrPrepare(JSONData data, TruffleString keyStr, Object holder) {
        Object value2 = JSDynamicObject.isJSDynamicObject(holder) ? JSObject.get((JSDynamicObject)holder, keyStr) : this.truffleRead(holder, keyStr);
        return this.jsonStrPreparePart2(data, keyStr, holder, value2);
    }

    @CompilerDirectives.TruffleBoundary
    private Object jsonStrPrepareArray(JSONData data, int key, JSDynamicObject holder) {
        Object value2 = JSObject.get(holder, key);
        return this.jsonStrPreparePart2(data, Strings.fromInt(key), holder, value2);
    }

    @CompilerDirectives.TruffleBoundary
    private Object jsonStrPrepareForeign(JSONData data, int key, Object holder) {
        assert (JSGuards.isForeignObject(holder));
        Object value2 = this.truffleRead(holder, key);
        return this.jsonStrPreparePart2(data, Strings.fromInt(key), holder, value2);
    }

    private Object jsonStrPreparePart2(JSONData data, Object key, Object holder, Object valueArg) {
        Object value2 = valueArg;
        boolean tryToJSON = false;
        if (JSRuntime.isObject(value2) || JSRuntime.isBigInt(value2)) {
            tryToJSON = true;
        } else if (JSRuntime.isForeignObject(value2)) {
            InteropLibrary interop = InteropLibrary.getUncached(value2);
            boolean bl = tryToJSON = interop.hasMembers(value2) && !interop.isNull(value2) && !JSInteropUtil.isBoxedPrimitive(value2, interop);
        }
        if (tryToJSON) {
            value2 = this.jsonStrPrepareObject(key, value2);
        }
        if (data.getReplacerFnObj() != null) {
            value2 = JSRuntime.call(data.getReplacerFnObj(), holder, new Object[]{key, value2});
        }
        if (JSDynamicObject.isJSDynamicObject(value2)) {
            return JSONStringifyStringNode.jsonStrPrepareJSObject((JSDynamicObject)value2);
        }
        if (value2 instanceof Symbol) {
            return Undefined.instance;
        }
        if (JSRuntime.isCallableForeign(value2)) {
            return Undefined.instance;
        }
        return value2;
    }

    private static Object jsonStrPrepareJSObject(JSDynamicObject valueObj) {
        JSClass builtinClass = JSObject.getJSClass(valueObj);
        if (builtinClass == JSNumber.INSTANCE) {
            return JSRuntime.toNumber(valueObj);
        }
        if (builtinClass == JSBigInt.INSTANCE) {
            return JSBigInt.valueOf(valueObj);
        }
        if (builtinClass == JSString.INSTANCE) {
            return JSRuntime.toString(valueObj);
        }
        if (builtinClass == JSBoolean.INSTANCE) {
            return JSBoolean.valueOf(valueObj);
        }
        if (JSRuntime.isCallableIsJSObject(valueObj)) {
            return Undefined.instance;
        }
        return valueObj;
    }

    private Object jsonStrPrepareObject(Object key, Object value2) {
        Object toJSON;
        assert (JSRuntime.isPropertyKey(key));
        if (this.getToJSONProperty == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.getToJSONProperty = this.insert(PropertyGetNode.create(Strings.TO_JSON, false, this.context));
        }
        if (JSRuntime.isCallable(toJSON = this.getToJSONProperty.getValue(value2))) {
            return this.jsonStrPrepareObjectFunction(key, value2, toJSON);
        }
        return value2;
    }

    private Object jsonStrPrepareObjectFunction(Object key, Object value2, Object toJSON) {
        if (this.callToJSONFunction == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.callToJSONFunction = this.insert(JSFunctionCallNode.createCall());
        }
        return this.callToJSONFunction.executeCall(JSArguments.createOneArg(value2, toJSON, key));
    }

    @CompilerDirectives.TruffleBoundary
    private TruffleStringBuilder jsonJO(TruffleStringBuilder sb, JSONData data, Object value2) {
        JSONStringifyStringNode.checkCycle(data, value2);
        data.pushStack(value2);
        JSONStringifyStringNode.checkStackDepth(data);
        int stepback = data.getIndent();
        int indent = data.getIndent() + 1;
        data.setIndent(indent);
        this.concatStart(sb, '{');
        int lengthBefore = StringBuilderProfile.length(sb);
        if (data.getPropertyList() == null) {
            if (JSDynamicObject.isJSDynamicObject(value2)) {
                this.serializeJSONObjectProperties(sb, data, value2, indent, JSObject.enumerableOwnNames((JSDynamicObject)value2));
            } else {
                this.serializeForeignObjectProperties(sb, data, value2, indent);
            }
        } else {
            this.serializeJSONObjectProperties(sb, data, value2, indent, data.getPropertyList());
        }
        this.concatEnd(sb, data, stepback, '}', lengthBefore != StringBuilderProfile.length(sb));
        data.popStack();
        data.setIndent(stepback);
        return sb;
    }

    private TruffleStringBuilder serializeJSONObjectProperties(TruffleStringBuilder sb, JSONData data, Object value2, int indent, List<? extends Object> keys) {
        boolean isFirst = true;
        for (Object object : keys) {
            TruffleString name = (TruffleString)object;
            Object strPPrepared = this.jsonStrPrepare(data, name, value2);
            if (!JSONStringifyStringNode.isStringifyable(strPPrepared)) continue;
            if (isFirst) {
                this.concatFirstStep(sb, data);
                isFirst = false;
            } else {
                this.appendSeparator(sb, data, indent);
            }
            this.jsonQuote(sb, name);
            this.appendColon(sb, data);
            this.jsonStrExecute(sb, data, strPPrepared);
        }
        return sb;
    }

    private void appendColon(TruffleStringBuilder sb, JSONData data) {
        this.append(sb, ':');
        if (Strings.length(data.getGap()) > 0) {
            this.append(sb, ' ');
        }
    }

    private void serializeForeignObjectProperties(TruffleStringBuilder sb, JSONData data, Object obj, int indent) {
        try {
            InteropLibrary objInterop = InteropLibrary.getFactory().getUncached(obj);
            if (!objInterop.hasMembers(obj)) {
                return;
            }
            Object keysObj = objInterop.getMembers(obj);
            InteropLibrary keysInterop = InteropLibrary.getFactory().getUncached(keysObj);
            long size = keysInterop.getArraySize(keysObj);
            boolean isFirst = true;
            for (long i = 0L; i < size; ++i) {
                Object memberValue;
                Object strPPrepared;
                TruffleString stringKey;
                Object key = keysInterop.readArrayElement(keysObj, i);
                assert (InteropLibrary.getUncached().isString(key));
                TruffleString truffleString = stringKey = key instanceof TruffleString ? (TruffleString)key : InteropLibrary.getUncached().asTruffleString(key);
                if (!objInterop.isMemberReadable(obj, Strings.toJavaString(stringKey)) || !JSONStringifyStringNode.isStringifyable(strPPrepared = this.jsonStrPreparePart2(data, stringKey, obj, memberValue = this.truffleRead(obj, stringKey)))) continue;
                if (isFirst) {
                    this.concatFirstStep(sb, data);
                    isFirst = false;
                } else {
                    this.appendSeparator(sb, data, indent);
                }
                this.jsonQuote(sb, stringKey);
                this.appendColon(sb, data);
                this.jsonStrExecute(sb, data, strPPrepared);
            }
        }
        catch (InvalidArrayIndexException | UnsupportedMessageException e) {
            throw Errors.createTypeErrorInteropException(obj, e, "SerializeJSONObject", this);
        }
    }

    @CompilerDirectives.TruffleBoundary
    private TruffleStringBuilder jsonJA(TruffleStringBuilder sb, JSONData data, Object value2) {
        long length;
        Object lenObject;
        JSONStringifyStringNode.checkCycle(data, value2);
        assert (JSRuntime.isArray(value2) || InteropLibrary.getUncached().hasArrayElements(value2));
        data.pushStack(value2);
        JSONStringifyStringNode.checkStackDepth(data);
        int stepback = data.getIndent();
        int indent = data.getIndent() + 1;
        data.setIndent(indent);
        boolean isForeign = false;
        boolean isArray = false;
        if (JSDynamicObject.isJSDynamicObject(value2)) {
            lenObject = JSObject.get((JSDynamicObject)value2, JSArray.LENGTH);
            if (JSArray.isJSArray(value2)) {
                isArray = true;
            }
        } else {
            lenObject = this.truffleGetSize(value2);
            isForeign = true;
        }
        if ((length = JSRuntime.toLength(lenObject)) > (long)this.context.getStringLengthLimit()) {
            throw Errors.createRangeErrorInvalidStringLength();
        }
        int len = (int)length;
        this.concatStart(sb, '[');
        for (int index = 0; index < len; ++index) {
            if (index == 0) {
                this.concatFirstStep(sb, data);
            } else {
                this.appendSeparator(sb, data, indent);
            }
            Object strPPrepared = isArray ? this.jsonStrPrepareArray(data, index, (JSDynamicObject)value2) : (isForeign ? this.jsonStrPrepareForeign(data, index, value2) : this.jsonStrPrepare(data, Strings.fromInt(index), value2));
            if (JSONStringifyStringNode.isStringifyable(strPPrepared)) {
                this.jsonStrExecute(sb, data, strPPrepared);
                continue;
            }
            this.append(sb, Null.NAME);
        }
        this.concatEnd(sb, data, stepback, ']', len > 0);
        data.popStack();
        data.setIndent(stepback);
        return sb;
    }

    private static void checkStackDepth(JSONData data) {
        if (data.stackTooDeep()) {
            JSONStringifyStringNode.throwStackError();
        }
    }

    private static void throwStackError() {
        throw Errors.createRangeError("cannot stringify objects nested that deep");
    }

    private void concatStart(TruffleStringBuilder builder, char c) {
        this.append(builder, c);
    }

    private void concatFirstStep(TruffleStringBuilder sb, JSONData data) {
        if (Strings.length(data.getGap()) > 0) {
            this.append(sb, '\n');
            for (int i = 0; i < data.getIndent(); ++i) {
                this.append(sb, data.getGap());
            }
        }
    }

    private void concatEnd(TruffleStringBuilder sb, JSONData data, int stepback, char close, boolean hasContent) {
        if (Strings.length(data.getGap()) > 0 && hasContent) {
            this.append(sb, '\n');
            for (int i = 0; i < stepback; ++i) {
                this.append(sb, data.getGap());
            }
        }
        this.append(sb, close);
    }

    @CompilerDirectives.TruffleBoundary
    private void appendSeparator(TruffleStringBuilder sb, JSONData data, int indent) {
        if (Strings.length(data.getGap()) <= 0) {
            this.append(sb, ',');
        } else {
            this.append(sb, Strings.COMMA_NEWLINE);
            for (int i = 0; i < indent; ++i) {
                this.append(sb, data.getGap());
            }
        }
    }

    private static void checkCycle(JSONData data, Object value2) {
        if (data.stack.contains(value2)) {
            throw Errors.createTypeError("Converting circular structure to JSON");
        }
    }

    private TruffleStringBuilder jsonQuote(TruffleStringBuilder builder, TruffleString valueStr) {
        return JSONStringifyStringNode.jsonQuote(this.stringBuilderProfile, builder, valueStr, this.getAppendCharNode(), this.getAppendStringNode());
    }

    public static TruffleStringBuilder jsonQuote(StringBuilderProfile stringBuilderProfile, TruffleStringBuilder sb, TruffleString valueStr, TruffleStringBuilder.AppendCharUTF16Node appendCharNode, TruffleStringBuilder.AppendStringNode appendStringNode) {
        stringBuilderProfile.append(appendCharNode, sb, '\"');
        for (int i = 0; i < Strings.length(valueStr); ++i) {
            char ch = Strings.charAt(valueStr, i);
            if (ch < ' ') {
                if (ch == '\b') {
                    stringBuilderProfile.append(appendStringNode, sb, Strings.BACKSLASH_B);
                    continue;
                }
                if (ch == '\f') {
                    stringBuilderProfile.append(appendStringNode, sb, Strings.BACKSLASH_F);
                    continue;
                }
                if (ch == '\n') {
                    stringBuilderProfile.append(appendStringNode, sb, Strings.BACKSLASH_N);
                    continue;
                }
                if (ch == '\r') {
                    stringBuilderProfile.append(appendStringNode, sb, Strings.BACKSLASH_R);
                    continue;
                }
                if (ch == '\t') {
                    stringBuilderProfile.append(appendStringNode, sb, Strings.BACKSLASH_T);
                    continue;
                }
                JSONStringifyStringNode.jsonQuoteUnicode(stringBuilderProfile, sb, ch, appendCharNode, appendStringNode);
                continue;
            }
            if (ch == '\\') {
                stringBuilderProfile.append(appendStringNode, sb, Strings.BACKSLASH_BACKSLASH);
                continue;
            }
            if (ch == '\"') {
                stringBuilderProfile.append(appendStringNode, sb, Strings.BACKSLASH_DOUBLE_QUOTE);
                continue;
            }
            if (Character.isSurrogate(ch)) {
                if (Character.isHighSurrogate(ch)) {
                    char nextCh;
                    if (i + 1 < Strings.length(valueStr) && Character.isLowSurrogate(nextCh = Strings.charAt(valueStr, i + 1))) {
                        stringBuilderProfile.append(appendCharNode, sb, ch);
                        stringBuilderProfile.append(appendCharNode, sb, nextCh);
                        ++i;
                        continue;
                    }
                    JSONStringifyStringNode.jsonQuoteSurrogate(stringBuilderProfile, sb, ch, appendCharNode, appendStringNode);
                    continue;
                }
                JSONStringifyStringNode.jsonQuoteSurrogate(stringBuilderProfile, sb, ch, appendCharNode, appendStringNode);
                continue;
            }
            stringBuilderProfile.append(appendCharNode, sb, ch);
        }
        stringBuilderProfile.append(appendCharNode, sb, '\"');
        return sb;
    }

    private static void jsonQuoteUnicode(StringBuilderProfile stringBuilderProfile, TruffleStringBuilder sb, char c, TruffleStringBuilder.AppendCharUTF16Node appendCharNode, TruffleStringBuilder.AppendStringNode appendStringNode) {
        stringBuilderProfile.append(appendStringNode, sb, Strings.BACKSLASH_U00);
        stringBuilderProfile.append(appendCharNode, sb, Character.forDigit(c >> 4 & 0xF, 16));
        stringBuilderProfile.append(appendCharNode, sb, Character.forDigit(c & 0xF, 16));
    }

    private static void jsonQuoteSurrogate(StringBuilderProfile stringBuilderProfile, TruffleStringBuilder sb, char c, TruffleStringBuilder.AppendCharUTF16Node appendCharNode, TruffleStringBuilder.AppendStringNode appendStringNode) {
        stringBuilderProfile.append(appendStringNode, sb, Strings.BACKSLASH_UD);
        stringBuilderProfile.append(appendCharNode, sb, Character.forDigit(c >> 8 & 0xF, 16));
        stringBuilderProfile.append(appendCharNode, sb, Character.forDigit(c >> 4 & 0xF, 16));
        stringBuilderProfile.append(appendCharNode, sb, Character.forDigit(c & 0xF, 16));
    }

    private Object truffleGetSize(Object obj) {
        return JSInteropUtil.getArraySize(obj, InteropLibrary.getUncached(), this);
    }

    private Object truffleRead(Object obj, TruffleString keyStr) {
        try {
            return JSRuntime.importValue(InteropLibrary.getUncached().readMember(obj, Strings.toJavaString(keyStr)));
        }
        catch (UnknownIdentifierException | UnsupportedMessageException e) {
            throw Errors.createTypeErrorInteropException(obj, e, "readMember", keyStr, this);
        }
    }

    private Object truffleRead(Object obj, int index) {
        try {
            return JSRuntime.importValue(InteropLibrary.getUncached().readArrayElement(obj, index));
        }
        catch (InvalidArrayIndexException | UnsupportedMessageException e) {
            throw Errors.createTypeErrorInteropException(obj, e, "readArrayElement", index, this);
        }
    }

    private TruffleStringBuilder.AppendStringNode getAppendStringNode() {
        if (this.appendStringNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.appendStringNode = this.insert(TruffleStringBuilder.AppendStringNode.create());
        }
        return this.appendStringNode;
    }

    private void append(TruffleStringBuilder sb, TruffleString s) {
        this.stringBuilderProfile.append(this.getAppendStringNode(), sb, s);
    }

    private TruffleStringBuilder.AppendCharUTF16Node getAppendCharNode() {
        if (this.appendCharNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.appendCharNode = this.insert(TruffleStringBuilder.AppendCharUTF16Node.create());
        }
        return this.appendCharNode;
    }

    private void append(TruffleStringBuilder sb, char value2) {
        this.stringBuilderProfile.append(this.getAppendCharNode(), sb, value2);
    }

    private void append(TruffleStringBuilder sb, int value2) {
        if (this.appendIntNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.appendIntNode = this.insert(TruffleStringBuilder.AppendIntNumberNode.create());
        }
        this.stringBuilderProfile.append(this.appendIntNode, sb, value2);
    }

    private void append(TruffleStringBuilder sb, long value2) {
        if (this.appendLongNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.appendLongNode = this.insert(TruffleStringBuilder.AppendLongNumberNode.create());
        }
        this.stringBuilderProfile.append(this.appendLongNode, sb, value2);
    }

    private TruffleString builderToString(TruffleStringBuilder sb) {
        if (this.builderToStringNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.builderToStringNode = this.insert(TruffleStringBuilder.ToStringNode.create());
        }
        return StringBuilderProfile.toString(this.builderToStringNode, sb);
    }
}

