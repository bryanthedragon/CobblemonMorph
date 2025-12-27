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
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.GraalJSException;
import com.oracle.truffle.js.runtime.JSException;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.UserScriptException;
import com.oracle.truffle.js.runtime.interop.InteropFunction;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

@GenerateUncached
public abstract class ImportValueNode extends JavaScriptBaseNode {
   public abstract Object executeWithTarget(Object target);

   public static ImportValueNode create() {
      return ImportValueNodeGen.create();
   }

   public static ImportValueNode getUncached() {
      return ImportValueNodeGen.getUncached();
   }

   @Specialization
   static int fromInt(int value) {
      return value;
   }

   @Specialization
   static TruffleString fromString(String value, @Cached TruffleString.FromJavaStringNode fromJavaStringNode) {
      return Strings.fromJavaString(fromJavaStringNode, value);
   }

   @Specialization
   static TruffleString fromTruffleString(TruffleString value, @Cached TruffleString.SwitchEncodingNode switchEncodingNode) {
      return switchEncodingNode.execute(value, TruffleString.Encoding.UTF_16);
   }

   @Specialization
   static boolean fromBoolean(boolean value) {
      return value;
   }

   @Specialization
   static BigInt fromBigInt(BigInt value) {
      return value;
   }

   @Specialization(guards = "isLongRepresentableAsInt32(value)")
   static int fromLongToInt(long value) {
      return (int)value;
   }

   @Specialization(guards = "!isLongRepresentableAsInt32(value)")
   static long fromLong(long value) {
      return value;
   }

   @Specialization
   static double fromDouble(double value) {
      return value;
   }

   @Specialization
   static int fromNumber(byte value) {
      return value;
   }

   @Specialization
   static int fromNumber(short value) {
      return value;
   }

   @Specialization
   static double fromNumber(float value) {
      return value;
   }

   @Specialization
   static TruffleString fromChar(char value, @Cached TruffleString.FromCodePointNode fromCodePointNode) {
      return Strings.fromCodePoint(fromCodePointNode, value);
   }

   @Specialization
   static Object fromDynamicObject(JSDynamicObject value) {
      return value;
   }

   @Specialization
   static Object fromInteropFunction(InteropFunction value) {
      return value.getFunction();
   }

   @Specialization
   static Object fromJSException(JSException value) {
      return value.getErrorObject();
   }

   @Specialization
   static Object fromException(UserScriptException value) {
      return value.getErrorObject();
   }

   @Specialization(guards = "!isSpecial(value)")
   static Object fromTruffleObject(TruffleObject value) {
      return value;
   }

   static boolean isSpecial(Object value) {
      return value instanceof InteropFunction || value instanceof GraalJSException;
   }

   @Fallback
   static TruffleString fallbackCase(Object value) {
      if (InteropLibrary.getUncached().isString(value)) {
         try {
            return InteropLibrary.getUncached().asTruffleString(value).switchEncodingUncached(TruffleString.Encoding.UTF_16);
         } catch (UnsupportedMessageException var2) {
            throw CompilerDirectives.shouldNotReachHere(var2);
         }
      } else {
         throw Errors.createTypeErrorUnsupportedInteropType(value);
      }
   }
}
