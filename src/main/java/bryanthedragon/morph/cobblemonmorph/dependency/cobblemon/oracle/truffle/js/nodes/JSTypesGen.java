package com.oracle.truffle.js.nodes;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.SafeInteger;

@GeneratedBy(JSTypes.class)
public final class JSTypesGen extends JSTypes {
   protected JSTypesGen() {
   }

   public static boolean isBoolean(Object value) {
      return value instanceof Boolean;
   }

   public static boolean asBoolean(Object value) {
      assert value instanceof Boolean : "JSTypesGen.asBoolean: boolean expected";

      return (Boolean)value;
   }

   public static boolean expectBoolean(Object value) throws UnexpectedResultException {
      if (value instanceof Boolean) {
         return (Boolean)value;
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         throw new UnexpectedResultException(value);
      }
   }

   public static boolean isInteger(Object value) {
      return value instanceof Integer;
   }

   public static int asInteger(Object value) {
      assert value instanceof Integer : "JSTypesGen.asInteger: int expected";

      return (Integer)value;
   }

   public static int expectInteger(Object value) throws UnexpectedResultException {
      if (value instanceof Integer) {
         return (Integer)value;
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         throw new UnexpectedResultException(value);
      }
   }

   public static boolean isDouble(Object value) {
      return value instanceof Double;
   }

   public static double asDouble(Object value) {
      assert value instanceof Double : "JSTypesGen.asDouble: double expected";

      return (Double)value;
   }

   public static double expectDouble(Object value) throws UnexpectedResultException {
      if (value instanceof Double) {
         return (Double)value;
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         throw new UnexpectedResultException(value);
      }
   }

   public static boolean isLong(Object value) {
      return value instanceof Long;
   }

   public static long asLong(Object value) {
      assert value instanceof Long : "JSTypesGen.asLong: long expected";

      return (Long)value;
   }

   public static long expectLong(Object value) throws UnexpectedResultException {
      if (value instanceof Long) {
         return (Long)value;
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         throw new UnexpectedResultException(value);
      }
   }

   public static boolean isSafeInteger(Object value) {
      return value instanceof SafeInteger;
   }

   public static SafeInteger asSafeInteger(Object value) {
      assert value instanceof SafeInteger : "JSTypesGen.asSafeInteger: SafeInteger expected";

      return (SafeInteger)value;
   }

   public static SafeInteger expectSafeInteger(Object value) throws UnexpectedResultException {
      if (value instanceof SafeInteger) {
         return (SafeInteger)value;
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         throw new UnexpectedResultException(value);
      }
   }

   public static boolean isBigInt(Object value) {
      return value instanceof BigInt;
   }

   public static BigInt asBigInt(Object value) {
      assert value instanceof BigInt : "JSTypesGen.asBigInt: BigInt expected";

      return (BigInt)value;
   }

   public static BigInt expectBigInt(Object value) throws UnexpectedResultException {
      if (value instanceof BigInt) {
         return (BigInt)value;
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         throw new UnexpectedResultException(value);
      }
   }

   public static double expectImplicitDouble(int state, Object value) throws UnexpectedResultException {
      if ((state & 1) != 0 && value instanceof Double) {
         return (Double)value;
      } else if ((state & 2) != 0 && value instanceof Integer) {
         return intToDouble((Integer)value);
      } else if ((state & 4) != 0 && value instanceof SafeInteger) {
         return safeIntegerToDouble((SafeInteger)value);
      } else if ((state & 8) != 0 && value instanceof Long) {
         return longToDouble((Long)value);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         throw new UnexpectedResultException(value);
      }
   }

   public static boolean isImplicitDouble(int state, Object value) {
      return (state & 1) != 0 && value instanceof Double
         || (state & 2) != 0 && value instanceof Integer
         || (state & 4) != 0 && value instanceof SafeInteger
         || (state & 8) != 0 && value instanceof Long;
   }

   public static boolean isImplicitDouble(Object value) {
      return value instanceof Double || value instanceof Integer || value instanceof SafeInteger || value instanceof Long;
   }

   public static double asImplicitDouble(int state, Object value) {
      if (CompilerDirectives.inInterpreter()) {
         return asImplicitDouble(value);
      } else if ((state & 1) != 0 && value instanceof Double) {
         return (Double)value;
      } else if ((state & 2) != 0 && value instanceof Integer) {
         return intToDouble((Integer)value);
      } else if ((state & 4) != 0 && value instanceof SafeInteger) {
         return safeIntegerToDouble((SafeInteger)value);
      } else if ((state & 8) != 0 && value instanceof Long) {
         return longToDouble((Long)value);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         throw new IllegalArgumentException("Illegal implicit source type.");
      }
   }

   public static double asImplicitDouble(Object value) {
      if (value instanceof Double) {
         return (Double)value;
      } else if (value instanceof Integer) {
         return intToDouble((Integer)value);
      } else if (value instanceof SafeInteger) {
         return safeIntegerToDouble((SafeInteger)value);
      } else if (value instanceof Long) {
         return longToDouble((Long)value);
      } else {
         throw new IllegalArgumentException("Illegal implicit source type.");
      }
   }

   public static int specializeImplicitDouble(Object value) {
      if (value instanceof Double) {
         return 1;
      } else if (value instanceof Integer) {
         return 2;
      } else if (value instanceof SafeInteger) {
         return 4;
      } else {
         return value instanceof Long ? 8 : 0;
      }
   }
}
