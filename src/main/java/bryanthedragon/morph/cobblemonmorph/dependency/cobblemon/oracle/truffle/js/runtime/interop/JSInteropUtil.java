package com.oracle.truffle.js.runtime.interop;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.interop.ArityException;
import com.oracle.truffle.api.interop.InteropException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.InvalidArrayIndexException;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.interop.UnsupportedTypeException;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.interop.ExportValueNode;
import com.oracle.truffle.js.nodes.interop.ImportValueNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.Null;
import com.oracle.truffle.js.runtime.objects.PropertyDescriptor;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.ArrayList;
import java.util.List;

public final class JSInteropUtil {
   private JSInteropUtil() {
   }

   public static long getArraySize(Object foreignObj, InteropLibrary interop, Node originatingNode) {
      try {
         return interop.getArraySize(foreignObj);
      } catch (UnsupportedMessageException var4) {
         throw Errors.createTypeErrorInteropException(foreignObj, var4, "getArraySize", originatingNode);
      }
   }

   @CompilerDirectives.TruffleBoundary
   public static Object get(Object obj, Object key) {
      assert JSRuntime.isPropertyKey(key);

      return JSDynamicObject.isJSDynamicObject(obj) ? JSObject.get((JSDynamicObject)obj, key) : readMemberOrDefault(obj, key, Undefined.instance);
   }

   @CompilerDirectives.TruffleBoundary
   public static Object get(Object obj, long index) {
      return JSDynamicObject.isJSDynamicObject(obj) ? JSObject.get((JSDynamicObject)obj, index) : readArrayElementOrDefault(obj, index, Undefined.instance);
   }

   public static Object readMemberOrDefault(Object obj, Object member, Object defaultValue) {
      return readMemberOrDefault(obj, member, defaultValue, InteropLibrary.getUncached(), ImportValueNode.getUncached(), null);
   }

   public static Object readMemberOrDefault(
      Object obj, Object member, Object defaultValue, InteropLibrary interop, ImportValueNode importValue, Node originatingNode
   ) {
      if (!Strings.isTString(member)) {
         return defaultValue;
      } else {
         try {
            return importValue.executeWithTarget(interop.readMember(obj, Strings.toJavaString((TruffleString)member)));
         } catch (UnknownIdentifierException var7) {
            return defaultValue;
         } catch (UnsupportedMessageException var8) {
            throw Errors.createTypeErrorInteropException(obj, var8, "readMember", member, originatingNode);
         }
      }
   }

   public static Object readArrayElementOrDefault(
      Object obj, long index, Object defaultValue, InteropLibrary interop, ImportValueNode importValue, Node originatingNode
   ) {
      try {
         return importValue.executeWithTarget(interop.readArrayElement(obj, index));
      } catch (InvalidArrayIndexException var8) {
         return defaultValue;
      } catch (UnsupportedMessageException var9) {
         throw Errors.createTypeErrorInteropException(obj, var9, "readArrayElement", index, originatingNode);
      }
   }

   public static Object readArrayElementOrDefault(Object obj, long index, Object defaultValue) {
      return readArrayElementOrDefault(obj, index, defaultValue, InteropLibrary.getUncached(), ImportValueNode.getUncached(), null);
   }

   public static void writeMember(Object obj, Object member, Object value) {
      writeMember(obj, member, value, InteropLibrary.getUncached(), ExportValueNode.getUncached(), null);
   }

   public static void writeMember(Object obj, Object member, Object value, InteropLibrary interop, ExportValueNode exportValue, Node originatingNode) {
      if (Strings.isTString(member)) {
         try {
            interop.writeMember(obj, Strings.toJavaString((TruffleString)member), exportValue.execute(value));
         } catch (UnknownIdentifierException | UnsupportedTypeException | UnsupportedMessageException var7) {
            throw Errors.createTypeErrorInteropException(obj, var7, "writeMember", member, originatingNode);
         }
      }
   }

   public static Object toPrimitiveOrDefault(Object obj, Object defaultValue, InteropLibrary interop, Node originatingNode) {
      if (interop.isNull(obj)) {
         return Null.instance;
      } else {
         try {
            if (interop.isBoolean(obj)) {
               return interop.asBoolean(obj);
            } else if (interop.isString(obj)) {
               return interop.asTruffleString(obj);
            } else {
               if (interop.isNumber(obj)) {
                  if (interop.fitsInInt(obj)) {
                     return interop.asInt(obj);
                  }

                  if (interop.fitsInLong(obj)) {
                     return interop.asLong(obj);
                  }

                  if (interop.fitsInDouble(obj)) {
                     return interop.asDouble(obj);
                  }
               }

               return defaultValue;
            }
         } catch (UnsupportedMessageException var5) {
            throw Errors.createTypeErrorUnboxException(obj, var5, originatingNode);
         }
      }
   }

   @CompilerDirectives.TruffleBoundary
   public static List<Object> keys(Object obj) {
      try {
         Object keysObj = InteropLibrary.getUncached().getMembers(obj);
         InteropLibrary keysInterop = InteropLibrary.getUncached(keysObj);
         long size = keysInterop.getArraySize(keysObj);
         if (size >= 0L && size < 2147483647L) {
            List<Object> keys = new ArrayList<>((int)size);

            for (int i = 0; i < size; i++) {
               Object key = keysInterop.readArrayElement(keysObj, i);

               assert InteropLibrary.getUncached().isString(key);

               keys.add(InteropLibrary.getUncached().asTruffleString(key));
            }

            return keys;
         } else {
            throw Errors.createRangeErrorInvalidArrayLength();
         }
      } catch (InvalidArrayIndexException | UnsupportedMessageException var8) {
         throw Errors.createTypeErrorInteropException(obj, var8, "readArrayElement", null);
      }
   }

   @CompilerDirectives.TruffleBoundary
   public static boolean hasProperty(Object obj, Object key) {
      return key instanceof TruffleString ? InteropLibrary.getUncached().isMemberExisting(obj, Strings.toJavaString((TruffleString)key)) : false;
   }

   @CompilerDirectives.TruffleBoundary
   public static boolean remove(Object obj, Object key) {
      if (key instanceof TruffleString) {
         try {
            InteropLibrary.getUncached().removeMember(obj, Strings.toJavaString((TruffleString)key));
            return true;
         } catch (UnknownIdentifierException | UnsupportedMessageException var3) {
            throw Errors.createTypeErrorInteropException(obj, var3, "removeMember", key, null);
         }
      } else {
         return false;
      }
   }

   @CompilerDirectives.TruffleBoundary
   public static Object call(Object function, Object[] args) {
      Object[] exportedArgs = JSRuntime.exportValueArray(args);

      try {
         return JSRuntime.importValue(InteropLibrary.getUncached().execute(function, exportedArgs));
      } catch (UnsupportedTypeException | ArityException | UnsupportedMessageException var4) {
         throw Errors.createTypeErrorInteropException(function, var4, "execute", null);
      }
   }

   @CompilerDirectives.TruffleBoundary
   public static Object construct(Object target, Object[] args) {
      Object[] exportedArgs = JSRuntime.exportValueArray(args);

      try {
         return JSRuntime.importValue(InteropLibrary.getUncached().instantiate(target, exportedArgs));
      } catch (UnsupportedTypeException | ArityException | UnsupportedMessageException var4) {
         throw Errors.createTypeErrorInteropException(target, var4, "instantiate", null);
      }
   }

   public static boolean isBoxedPrimitive(Object receiver, InteropLibrary interop) {
      return interop.isString(receiver) || interop.isNumber(receiver) || interop.isBoolean(receiver);
   }

   public static PropertyDescriptor getOwnProperty(Object object, TruffleString propertyKey) {
      return getOwnProperty(object, propertyKey, InteropLibrary.getUncached(), ImportValueNode.getUncached(), TruffleString.ReadCharUTF16Node.getUncached());
   }

   public static PropertyDescriptor getOwnProperty(
      Object object, TruffleString propertyKey, InteropLibrary interop, ImportValueNode importValueNode, TruffleString.ReadCharUTF16Node charAtNode
   ) {
      try {
         String key = Strings.toJavaString(propertyKey);
         if (interop.hasMembers(object) && interop.isMemberExisting(object, key)) {
            PropertyDescriptor desc = getExistingMemberProperty(object, key, interop, importValueNode);
            if (desc != null) {
               return desc;
            }
         }

         long index = JSRuntime.propertyNameToArrayIndex(propertyKey, charAtNode);
         if (JSRuntime.isArrayIndex(index) && interop.hasArrayElements(object)) {
            return getArrayElementProperty(object, index, interop, importValueNode);
         }
      } catch (InteropException var8) {
      }

      return null;
   }

   public static PropertyDescriptor getExistingMemberProperty(Object object, String key, InteropLibrary interop, ImportValueNode importValueNode) throws InteropException {
      assert interop.hasMembers(object) && interop.isMemberExisting(object, key);

      return interop.isMemberReadable(object, key)
         ? PropertyDescriptor.createData(
            importValueNode.executeWithTarget(interop.readMember(object, key)),
            !interop.isMemberInternal(object, key),
            interop.isMemberWritable(object, key),
            interop.isMemberRemovable(object, key)
         )
         : null;
   }

   public static PropertyDescriptor getArrayElementProperty(Object object, long index, InteropLibrary interop, ImportValueNode importValueNode) throws InteropException {
      assert interop.hasArrayElements(object) && JSRuntime.isArrayIndex(index);

      return interop.isArrayElementExisting(object, index) && interop.isArrayElementReadable(object, index)
         ? PropertyDescriptor.createData(
            importValueNode.executeWithTarget(interop.readArrayElement(object, index)),
            true,
            interop.isArrayElementWritable(object, index),
            interop.isArrayElementRemovable(object, index)
         )
         : null;
   }
}
