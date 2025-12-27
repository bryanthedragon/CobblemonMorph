package com.oracle.truffle.js.nodes;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.nodes.UnexpectedResultException;

@GeneratedBy(IntToLongTypeSystem.class)
public final class IntToLongTypeSystemGen extends IntToLongTypeSystem {
   protected IntToLongTypeSystemGen() {
   }

   public static long expectImplicitLong(int state, Object value) throws UnexpectedResultException {
      if ((state & 1) != 0 && value instanceof Long) {
         return (Long)value;
      } else if ((state & 2) != 0 && value instanceof Integer) {
         return intToLong((Integer)value);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         throw new UnexpectedResultException(value);
      }
   }

   public static boolean isImplicitLong(int state, Object value) {
      return (state & 1) != 0 && value instanceof Long || (state & 2) != 0 && value instanceof Integer;
   }

   public static boolean isImplicitLong(Object value) {
      return value instanceof Long || value instanceof Integer;
   }

   public static long asImplicitLong(int state, Object value) {
      if (CompilerDirectives.inInterpreter()) {
         return asImplicitLong(value);
      } else if ((state & 1) != 0 && value instanceof Long) {
         return (Long)value;
      } else if ((state & 2) != 0 && value instanceof Integer) {
         return intToLong((Integer)value);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         throw new IllegalArgumentException("Illegal implicit source type.");
      }
   }

   public static long asImplicitLong(Object value) {
      if (value instanceof Long) {
         return (Long)value;
      } else if (value instanceof Integer) {
         return intToLong((Integer)value);
      } else {
         throw new IllegalArgumentException("Illegal implicit source type.");
      }
   }

   public static int specializeImplicitLong(Object value) {
      if (value instanceof Long) {
         return 1;
      } else {
         return value instanceof Integer ? 2 : 0;
      }
   }
}
