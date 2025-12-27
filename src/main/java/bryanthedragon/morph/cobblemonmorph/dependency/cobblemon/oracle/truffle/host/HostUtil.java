package com.oracle.truffle.host;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;

final class HostUtil {
   private HostUtil() {
   }

   static Object convertLossLess(Object value, Class<?> requestedType, InteropLibrary interop) {
      try {
         if (interop.isNumber(value)) {
            if (requestedType != byte.class && requestedType != Byte.class) {
               if (requestedType != short.class && requestedType != Short.class) {
                  if (requestedType != int.class && requestedType != Integer.class) {
                     if (requestedType != long.class && requestedType != Long.class) {
                        if (requestedType != float.class && requestedType != Float.class) {
                           if (requestedType != double.class && requestedType != Double.class) {
                              if (requestedType == Number.class) {
                                 return convertToNumber(value, interop);
                              }

                              return null;
                           }

                           return interop.asDouble(value);
                        }

                        return interop.asFloat(value);
                     }

                     return interop.asLong(value);
                  }

                  return interop.asInt(value);
               }

               return interop.asShort(value);
            }

            return interop.asByte(value);
         } else if (interop.isBoolean(value)) {
            if (requestedType == boolean.class || requestedType == Boolean.class) {
               return interop.asBoolean(value);
            }
         } else if (interop.isString(value)) {
            if (requestedType != char.class && requestedType != Character.class) {
               if (requestedType == String.class || requestedType == CharSequence.class) {
                  return interop.asString(value);
               }
            } else {
               String str = interop.asString(value);
               if (str.length() == 1) {
                  return str.charAt(0);
               }
            }
         }
      } catch (UnsupportedMessageException var4) {
      }

      return null;
   }

   static Object convertToNumber(Object value, InteropLibrary interop) {
      try {
         if (value instanceof Number) {
            return value;
         }

         if (interop.fitsInByte(value)) {
            return interop.asByte(value);
         }

         if (interop.fitsInShort(value)) {
            return interop.asShort(value);
         }

         if (interop.fitsInInt(value)) {
            return interop.asInt(value);
         }

         if (interop.fitsInLong(value)) {
            return interop.asLong(value);
         }

         if (interop.fitsInFloat(value)) {
            return interop.asFloat(value);
         }

         if (interop.fitsInDouble(value)) {
            return interop.asDouble(value);
         }
      } catch (UnsupportedMessageException var3) {
      }

      return null;
   }

   static Object convertLossy(Object value, Class<?> targetType, InteropLibrary interop) {
      if ((targetType == char.class || targetType == Character.class) && interop.fitsInInt(value)) {
         try {
            int v = interop.asInt(value);
            if (v >= 0 && v < 65536) {
               return (char)v;
            }
         } catch (UnsupportedMessageException var4) {
            CompilerDirectives.shouldNotReachHere(var4);
         }
      }

      return null;
   }
}
