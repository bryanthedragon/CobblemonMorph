package com.oracle.truffle.js.nodes.cast;

import com.oracle.truffle.api.dsl.GenerateUncached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.Symbol;

@GenerateUncached
@ImportStatic(JSConfig.class)
public abstract class JSToBooleanNode extends JavaScriptBaseNode {
   protected JSToBooleanNode() {
   }

   public abstract boolean executeBoolean(Object value);

   public static JSToBooleanNode create() {
      return JSToBooleanNodeGen.create();
   }

   @Specialization
   protected static boolean doBoolean(boolean value) {
      return value;
   }

   @Specialization(guards = "isJSNull(value)")
   protected static boolean doNull(Object value) {
      return false;
   }

   @Specialization(guards = "isUndefined(value)")
   protected static boolean doUndefined(Object value) {
      return false;
   }

   @Specialization
   protected static boolean doInt(int value) {
      return value != 0;
   }

   @Specialization
   protected static boolean doLong(long value) {
      return value != 0L;
   }

   @Specialization
   protected static boolean doDouble(double value) {
      return value != 0.0 && !Double.isNaN(value);
   }

   @Specialization
   protected static boolean doBigInt(BigInt value) {
      return value.compareTo(BigInt.ZERO) != 0;
   }

   @Specialization
   protected static boolean doString(TruffleString value) {
      return Strings.length(value) != 0;
   }

   @Specialization(guards = "isJSObject(value)")
   protected static boolean doObject(Object value) {
      return true;
   }

   @Specialization
   protected static boolean doSymbol(Symbol value) {
      return true;
   }

   @Specialization(guards = "isForeignObject(value)", limit = "InteropLibraryLimit")
   protected final boolean doForeignObject(Object value, @CachedLibrary("value") InteropLibrary interop) {
      if (interop.isNull(value)) {
         return false;
      } else {
         try {
            if (interop.isBoolean(value)) {
               return interop.asBoolean(value);
            } else if (interop.isString(value)) {
               return !Strings.isEmpty(interop.asTruffleString(value));
            } else if (interop.isNumber(value)) {
               if (interop.fitsInInt(value)) {
                  return doInt(interop.asInt(value));
               } else if (interop.fitsInLong(value)) {
                  return doLong(interop.asLong(value));
               } else {
                  return interop.fitsInDouble(value) ? doDouble(interop.asDouble(value)) : true;
               }
            } else {
               return true;
            }
         } catch (UnsupportedMessageException var4) {
            throw Errors.createTypeErrorUnboxException(value, var4, this);
         }
      }
   }
}
