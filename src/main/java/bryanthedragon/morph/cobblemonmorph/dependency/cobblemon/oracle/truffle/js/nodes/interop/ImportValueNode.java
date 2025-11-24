
package com.oracle.truffle.js.nodes.interop;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.GenerateUncached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.interop.ImportValueNodeGen;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.GraalJSException;
import com.oracle.truffle.js.runtime.JSException;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.UserScriptException;
import com.oracle.truffle.js.runtime.interop.InteropFunction;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

@GenerateUncached
public abstract class ImportValueNode
extends JavaScriptBaseNode {
    public abstract Object executeWithTarget(Object var1);

    public static ImportValueNode create() {
        return ImportValueNodeGen.create();
    }

    public static ImportValueNode getUncached() {
        return ImportValueNodeGen.getUncached();
    }

    @Specialization
    static int fromInt(int value2) {
        return value2;
    }

    @Specialization
    static TruffleString fromString(String value2, @Cached TruffleString.FromJavaStringNode fromJavaStringNode) {
        return Strings.fromJavaString(fromJavaStringNode, value2);
    }

    @Specialization
    static TruffleString fromTruffleString(TruffleString value2, @Cached TruffleString.SwitchEncodingNode switchEncodingNode) {
        return switchEncodingNode.execute(value2, TruffleString.Encoding.UTF_16);
    }

    @Specialization
    static boolean fromBoolean(boolean value2) {
        return value2;
    }

    @Specialization
    static BigInt fromBigInt(BigInt value2) {
        return value2;
    }

    @Specialization(guards={"isLongRepresentableAsInt32(value)"})
    static int fromLongToInt(long value2) {
        return (int)value2;
    }

    @Specialization(guards={"!isLongRepresentableAsInt32(value)"})
    static long fromLong(long value2) {
        return value2;
    }

    @Specialization
    static double fromDouble(double value2) {
        return value2;
    }

    @Specialization
    static int fromNumber(byte value2) {
        return value2;
    }

    @Specialization
    static int fromNumber(short value2) {
        return value2;
    }

    @Specialization
    static double fromNumber(float value2) {
        return value2;
    }

    @Specialization
    static TruffleString fromChar(char value2, @Cached TruffleString.FromCodePointNode fromCodePointNode) {
        return Strings.fromCodePoint(fromCodePointNode, value2);
    }

    @Specialization
    static Object fromDynamicObject(JSDynamicObject value2) {
        return value2;
    }

    @Specialization
    static Object fromInteropFunction(InteropFunction value2) {
        return value2.getFunction();
    }

    @Specialization
    static Object fromJSException(JSException value2) {
        return value2.getErrorObject();
    }

    @Specialization
    static Object fromException(UserScriptException value2) {
        return value2.getErrorObject();
    }

    @Specialization(guards={"!isSpecial(value)"})
    static Object fromTruffleObject(TruffleObject value2) {
        return value2;
    }

    static boolean isSpecial(Object value2) {
        return value2 instanceof InteropFunction || value2 instanceof GraalJSException;
    }

    @Fallback
    static TruffleString fallbackCase(Object value2) {
        if (InteropLibrary.getUncached().isString(value2)) {
            try {
                return InteropLibrary.getUncached().asTruffleString(value2).switchEncodingUncached(TruffleString.Encoding.UTF_16);
            }
            catch (UnsupportedMessageException e) {
                throw CompilerDirectives.shouldNotReachHere(e);
            }
        }
        throw Errors.createTypeErrorUnsupportedInteropType(value2);
    }
}

