package com.oracle.truffle.host;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.GenerateUncached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.InvalidArrayIndexException;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.strings.TruffleString;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;
import java.util.function.Function;
import org.graalvm.polyglot.PolyglotException;
import org.graalvm.polyglot.Value;

@GenerateUncached
abstract class HostToTypeNode extends Node {
   static final int LIMIT = 5;
   static final int HIGHEST = 0;
   static final int STRICT = 1;
   static final int LOOSE = 2;
   static final int COERCE = 3;
   static final int FUNCTION_PROXY = 4;
   static final int OBJECT_PROXY_IFACE = 5;
   static final int OBJECT_PROXY_CLASS = 6;
   static final int HOST_PROXY = 7;
   static final int LOWEST = 8;
   static final int[] PRIORITIES = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8};

   public abstract Object execute(HostContext context, Object value, Class<?> targetType, Type genericType, boolean useTargetMapping);

   @Specialization(guards = "targetType == cachedTargetType", limit = "LIMIT")
   protected Object doCached(
      HostContext context,
      Object operand,
      Class<?> targetType,
      Type genericType,
      boolean useCustomTargetTypes,
      @CachedLibrary("operand") InteropLibrary interop,
      @Cached("targetType") Class<?> cachedTargetType,
      @Cached("isPrimitiveTarget(cachedTargetType)") boolean primitiveTarget,
      @Cached("allowsImplementation(context, targetType)") boolean allowsImplementation,
      @Cached HostTargetMappingNode targetMapping,
      @Cached BranchProfile error
   ) {
      return convertImpl(
         operand, cachedTargetType, genericType, allowsImplementation, primitiveTarget, context, interop, useCustomTargetTypes, targetMapping, error
      );
   }

   @CompilerDirectives.TruffleBoundary
   static boolean allowsImplementation(HostContext hostContext, Class<?> type) {
      if (hostContext == null) {
         return false;
      } else if (!HostInteropReflect.isAbstractType(type)) {
         return false;
      } else {
         HostClassDesc classDesc = hostContext.getHostClassCache().forClass(type);
         return classDesc.isAllowsImplementation() && classDesc.isAllowedTargetType();
      }
   }

   @Specialization(replaces = "doCached")
   @CompilerDirectives.TruffleBoundary
   protected static Object doGeneric(HostContext context, Object operand, Class<?> targetType, Type genericType, boolean useTargetMapping) {
      return convertImpl(
         operand,
         targetType,
         genericType,
         allowsImplementation(context, targetType),
         isPrimitiveTarget(targetType),
         context,
         InteropLibrary.getUncached(operand),
         useTargetMapping,
         HostTargetMappingNode.getUncached(),
         BranchProfile.getUncached()
      );
   }

   @CompilerDirectives.TruffleBoundary
   private static String toString(Object value) {
      return value.toString();
   }

   private static Object convertImpl(
      Object value,
      Class<?> targetType,
      Type genericType,
      boolean allowsImplementation,
      boolean primitiveTargetType,
      HostContext context,
      InteropLibrary interop,
      boolean useCustomTargetTypes,
      HostTargetMappingNode targetMapping,
      BranchProfile error
   ) {
      if (useCustomTargetTypes) {
         Object result = targetMapping.execute(value, targetType, context, interop, false, 0, 1);
         if (result != HostTargetMappingNode.NO_RESULT) {
            return result;
         }
      }

      if (primitiveTargetType) {
         Object convertedValue = HostUtil.convertLossLess(value, targetType, interop);
         if (convertedValue != null) {
            return convertedValue;
         }
      }

      HostLanguage language = HostLanguage.get(interop);
      if (HostObject.isJavaInstance(language, targetType, value)) {
         return HostObject.valueOf(language, value);
      } else {
         if (useCustomTargetTypes) {
            Object convertedValue = targetMapping.execute(value, targetType, context, interop, false, 2, 2);
            if (convertedValue != HostTargetMappingNode.NO_RESULT) {
               return convertedValue;
            }
         }

         if (primitiveTargetType) {
            Object convertedValue = HostUtil.convertLossy(value, targetType, interop);
            if (convertedValue != null) {
               return convertedValue;
            }
         }

         if (targetType == Value.class && context != null) {
            return value instanceof Value ? value : context.asValue(interop, value);
         } else if (interop.isNull(value)) {
            if (targetType.isPrimitive()) {
               throw HostInteropErrors.nullCoercion(context, value, targetType);
            } else {
               return null;
            }
         } else {
            if (value instanceof TruffleObject) {
               Object convertedValue = asJavaObject(context, (TruffleObject)value, targetType, genericType, allowsImplementation);
               if (convertedValue != null) {
                  return convertedValue;
               }
            } else if (value instanceof TruffleString && targetType.isAssignableFrom(String.class)) {
               try {
                  return interop.asString(value);
               } catch (UnsupportedMessageException var13) {
                  throw CompilerDirectives.shouldNotReachHere(var13);
               }
            }

            if (targetType.isInstance(value)) {
               return targetType.cast(value);
            } else {
               if (useCustomTargetTypes) {
                  Object result = targetMapping.execute(value, targetType, context, interop, false, 3, 8);
                  if (result != HostTargetMappingNode.NO_RESULT) {
                     return result;
                  }
               }

               error.enter();
               throw HostInteropErrors.cannotConvertPrimitive(context, value, targetType);
            }
         }
      }
   }

   static boolean canConvert(
      Object value,
      Class<?> targetType,
      Type genericType,
      Boolean allowsImplementation,
      HostContext hostContext,
      int priority,
      InteropLibrary interop,
      HostTargetMappingNode targetMapping
   ) {
      if (targetMapping != null && targetMapping.execute(value, targetType, hostContext, interop, true, 0, priority) == Boolean.TRUE) {
         return true;
      } else if (priority <= 0) {
         return false;
      } else if (interop.isNull(value)) {
         return !targetType.isPrimitive();
      } else if (targetType == Value.class && hostContext != null) {
         return true;
      } else {
         if (isPrimitiveTarget(targetType)) {
            Object convertedValue = HostUtil.convertLossLess(value, targetType, interop);
            if (convertedValue != null) {
               return true;
            }
         }

         HostLanguage language = HostLanguage.get(interop);
         if (HostObject.isJavaInstance(language, targetType, value)) {
            return true;
         } else if (priority <= 1) {
            return false;
         } else if (targetType == Object.class) {
            return true;
         } else if (targetType == List.class) {
            return interop.hasArrayElements(value);
         } else if (targetType == Map.class) {
            return interop.hasMembers(value);
         } else if (targetType == Function.class) {
            return interop.isExecutable(value) || interop.isInstantiable(value);
         } else if (targetType == LocalDate.class) {
            return interop.isDate(value);
         } else if (targetType == LocalTime.class) {
            return interop.isTime(value);
         } else if (targetType == LocalDateTime.class) {
            return interop.isDate(value) && interop.isTime(value);
         } else if (targetType == ZonedDateTime.class || targetType == Date.class || targetType == Instant.class) {
            return interop.isInstant(value);
         } else if (targetType == ZoneId.class) {
            return interop.isTimeZone(value);
         } else if (targetType == Duration.class) {
            return interop.isDuration(value);
         } else if (targetType == PolyglotException.class) {
            return interop.isException(value);
         } else if (priority <= 2) {
            return false;
         } else if (targetType.isArray()) {
            return interop.hasArrayElements(value);
         } else {
            if (isPrimitiveTarget(targetType)) {
               Object convertedValue = HostUtil.convertLossy(value, targetType, interop);
               if (convertedValue != null) {
                  return true;
               }
            }

            if (value instanceof TruffleObject) {
               if (priority < 7 && HostObject.isInstance(language, value)) {
                  return false;
               } else {
                  return priority >= 4
                        && HostInteropReflect.isFunctionalInterface(targetType)
                        && (interop.isExecutable(value) || interop.isInstantiable(value))
                        && checkAllowsImplementation(targetType, allowsImplementation, hostContext)
                     ? true
                     : (priority >= 5 && targetType.isInterface() || priority >= 6 && HostInteropReflect.isAbstractType(targetType))
                        && interop.hasMembers(value)
                        && checkAllowsImplementation(targetType, allowsImplementation, hostContext);
               }
            } else {
               assert !(value instanceof TruffleObject);

               return targetType.isInstance(value);
            }
         }
      }
   }

   private static boolean checkAllowsImplementation(Class<?> targetType, Boolean allowsImplementation, HostContext hostContext) {
      boolean implementations;
      if (allowsImplementation == null) {
         implementations = allowsImplementation(hostContext, targetType);
      } else {
         implementations = allowsImplementation;
      }

      return implementations;
   }

   static boolean isPrimitiveTarget(Class<?> clazz) {
      return clazz == int.class
         || clazz == Integer.class
         || clazz == boolean.class
         || clazz == Boolean.class
         || clazz == byte.class
         || clazz == Byte.class
         || clazz == short.class
         || clazz == Short.class
         || clazz == long.class
         || clazz == Long.class
         || clazz == float.class
         || clazz == Float.class
         || clazz == double.class
         || clazz == Double.class
         || clazz == char.class
         || clazz == Character.class
         || clazz == Number.class
         || CharSequence.class.isAssignableFrom(clazz);
   }

   static Object convertToObject(HostContext hostContext, Object value, InteropLibrary interop) {
      try {
         if (interop.isNull(value)) {
            return null;
         } else if (interop.isString(value)) {
            return interop.asString(value);
         } else if (interop.isBoolean(value)) {
            return interop.asBoolean(value);
         } else {
            if (interop.isNumber(value)) {
               Object result = HostUtil.convertToNumber(value, interop);
               if (result != null) {
                  return result;
               }
            } else {
               if (interop.hasArrayElements(value)) {
                  return asJavaObject(hostContext, value, List.class, null, false);
               }

               if (interop.hasHashEntries(value) || interop.hasMembers(value)) {
                  return asJavaObject(hostContext, value, Map.class, null, false);
               }

               if (interop.hasIterator(value)) {
                  return asJavaObject(hostContext, value, Iterable.class, null, false);
               }

               if (interop.isIterator(value)) {
                  return asJavaObject(hostContext, value, Iterator.class, null, false);
               }

               if (interop.isExecutable(value) || interop.isInstantiable(value)) {
                  return asJavaObject(hostContext, value, Function.class, null, false);
               }
            }

            return hostContext.language.access.toValue(hostContext.internalContext, value);
         }
      } catch (UnsupportedMessageException var4) {
         throw CompilerDirectives.shouldNotReachHere(var4);
      }
   }

   @CompilerDirectives.TruffleBoundary
   private static <T> T asJavaObject(HostContext hostContext, Object value, Class<T> targetType, Type genericType, boolean allowsImplementation) {
      InteropLibrary interop = InteropLibrary.getFactory().getUncached(value);

      assert !interop.isNull(value);

      Object obj;
      if (HostObject.isJavaInstance(hostContext.language, targetType, value)) {
         obj = HostObject.valueOf(hostContext.language, value);
      } else if (targetType == Object.class) {
         obj = convertToObject(hostContext, value, interop);
      } else if (targetType == List.class) {
         if (!interop.hasArrayElements(value)) {
            throw HostInteropErrors.cannotConvert(hostContext, value, targetType, "Value must have array elements.");
         }

         boolean implementsFunction = shouldImplementFunction(value, interop);
         HostToTypeNode.TypeAndClass<?> elementType = getGenericParameterType(genericType, 0);
         obj = hostContext.language.access.toList(hostContext.internalContext, value, implementsFunction, elementType.clazz, elementType.type);
      } else if (targetType == Map.class) {
         HostToTypeNode.TypeAndClass<?> keyType = getGenericParameterType(genericType, 0);
         HostToTypeNode.TypeAndClass<?> valueType = getGenericParameterType(genericType, 1);
         boolean hasHashEntries = interop.hasHashEntries(value);
         if (!hasHashEntries && !isSupportedMapKeyType(keyType.clazz)) {
            throw newInvalidKeyTypeException(keyType.clazz, hostContext);
         }

         boolean hasSize = Number.class.isAssignableFrom(keyType.clazz) && interop.hasArrayElements(value);
         boolean hasKeys = (keyType.clazz == Object.class || keyType.clazz == String.class) && interop.hasMembers(value);
         if (!hasKeys && !hasSize && !hasHashEntries) {
            throw HostInteropErrors.cannotConvert(hostContext, value, targetType, "Value must have members, array elements or hash entries.");
         }

         boolean implementsFunction = shouldImplementFunction(value, interop);
         obj = hostContext.language
            .access
            .toMap(hostContext.internalContext, value, implementsFunction, keyType.clazz, keyType.type, valueType.clazz, valueType.type);
      } else if (targetType == Entry.class) {
         if (!interop.hasArrayElements(value)) {
            throw HostInteropErrors.cannotConvert(hostContext, value, targetType, "Value must have array elements.");
         }

         HostToTypeNode.TypeAndClass<?> keyTypex = getGenericParameterType(genericType, 0);
         HostToTypeNode.TypeAndClass<?> valueTypex = getGenericParameterType(genericType, 1);
         boolean implementsFunction = shouldImplementFunction(value, interop);
         obj = hostContext.language
            .access
            .toMapEntry(hostContext.internalContext, value, implementsFunction, keyTypex.clazz, keyTypex.type, valueTypex.clazz, valueTypex.type);
      } else if (targetType == Function.class) {
         HostToTypeNode.TypeAndClass<?> paramType = getGenericParameterType(genericType, 0);
         HostToTypeNode.TypeAndClass<?> returnType = getGenericParameterType(genericType, 1);
         if (!interop.isExecutable(value) && !interop.isInstantiable(value)) {
            if (!interop.hasMembers(value)) {
               throw HostInteropErrors.cannotConvert(hostContext, value, targetType, "Value must be executable or instantiable.");
            }

            obj = hostContext.language.access.toObjectProxy(hostContext.internalContext, targetType, value);
         } else {
            obj = hostContext.language
               .access
               .toFunction(hostContext.internalContext, value, returnType.clazz, returnType.type, paramType.clazz, paramType.type);
         }
      } else if (targetType.isArray()) {
         if (!interop.hasArrayElements(value)) {
            throw HostInteropErrors.cannotConvert(hostContext, value, targetType, "Value must have array elements.");
         }

         obj = truffleObjectToArray(hostContext, interop, value, targetType, genericType);
      } else if (targetType == LocalDate.class) {
         if (!interop.isDate(value)) {
            throw HostInteropErrors.cannotConvert(hostContext, value, targetType, "Value must have date and time information.");
         }

         try {
            obj = interop.asDate(value);
         } catch (UnsupportedMessageException var19) {
            throw CompilerDirectives.shouldNotReachHere(var19);
         }
      } else if (targetType == LocalTime.class) {
         if (!interop.isTime(value)) {
            throw HostInteropErrors.cannotConvert(hostContext, value, targetType, "Value must have date and time information.");
         }

         try {
            obj = interop.asTime(value);
         } catch (UnsupportedMessageException var18) {
            throw CompilerDirectives.shouldNotReachHere(var18);
         }
      } else if (targetType == LocalDateTime.class) {
         if (!interop.isDate(value) || !interop.isTime(value)) {
            throw HostInteropErrors.cannotConvert(hostContext, value, targetType, "Value must have date and time information.");
         }

         LocalDate date;
         LocalTime time;
         try {
            date = interop.asDate(value);
            time = interop.asTime(value);
         } catch (UnsupportedMessageException var17) {
            throw CompilerDirectives.shouldNotReachHere(var17);
         }

         obj = createDateTime(date, time);
      } else if (targetType == ZonedDateTime.class) {
         if (!interop.isDate(value) || !interop.isTime(value) || !interop.isTimeZone(value)) {
            throw HostInteropErrors.cannotConvert(hostContext, value, targetType, "Value must have date, time and time-zone information.");
         }

         ZoneId timeZone;
         LocalDate datex;
         LocalTime time;
         try {
            datex = interop.asDate(value);
            time = interop.asTime(value);
            timeZone = interop.asTimeZone(value);
         } catch (UnsupportedMessageException var16) {
            throw CompilerDirectives.shouldNotReachHere(var16);
         }

         obj = createZonedDateTime(datex, time, timeZone);
      } else if (targetType == ZoneId.class) {
         if (!interop.isTimeZone(value)) {
            throw HostInteropErrors.cannotConvert(hostContext, value, targetType, "Value must have time-zone information.");
         }

         try {
            obj = interop.asTimeZone(value);
         } catch (UnsupportedMessageException var15) {
            throw CompilerDirectives.shouldNotReachHere(var15);
         }
      } else if (targetType != Instant.class && targetType != Date.class) {
         if (targetType == Duration.class) {
            if (!interop.isDuration(value)) {
               throw HostInteropErrors.cannotConvert(hostContext, value, targetType, "Value must have duration information.");
            }

            try {
               obj = interop.asDuration(value);
            } catch (UnsupportedMessageException var13) {
               throw CompilerDirectives.shouldNotReachHere(var13);
            }
         } else if (targetType == PolyglotException.class) {
            if (!interop.isException(value)) {
               throw HostInteropErrors.cannotConvert(hostContext, value, targetType, "Value must be an exception.");
            }

            obj = asPolyglotException(hostContext, value, interop);
         } else if (targetType == Iterable.class) {
            if (interop.hasIterator(value)) {
               boolean implementsFunction = shouldImplementFunction(value, interop);
               HostToTypeNode.TypeAndClass<?> elementType = getGenericParameterType(genericType, 0);
               obj = hostContext.language.access.toIterable(hostContext.internalContext, value, implementsFunction, elementType.clazz, elementType.type);
            } else {
               if (!allowsImplementation || !interop.hasMembers(value)) {
                  throw HostInteropErrors.cannotConvert(hostContext, value, targetType, "Value must have an iterator.");
               }

               obj = hostContext.language.access.toObjectProxy(hostContext.internalContext, targetType, value);
            }
         } else if (targetType == Iterator.class) {
            if (interop.isIterator(value)) {
               boolean implementsFunction = shouldImplementFunction(value, interop);
               HostToTypeNode.TypeAndClass<?> elementType = getGenericParameterType(genericType, 0);
               obj = hostContext.language.access.toIterator(hostContext.internalContext, value, implementsFunction, elementType.clazz, elementType.type);
            } else {
               if (!allowsImplementation || !interop.hasMembers(value)) {
                  throw HostInteropErrors.cannotConvert(hostContext, value, targetType, "Value must be an iterator.");
               }

               obj = hostContext.language.access.toObjectProxy(hostContext.internalContext, targetType, value);
            }
         } else {
            if (!allowsImplementation || !HostInteropReflect.isAbstractType(targetType)) {
               return null;
            }

            if (!HostInteropReflect.isFunctionalInterface(targetType) || !interop.isExecutable(value) && !interop.isInstantiable(value)) {
               if (!interop.hasMembers(value)) {
                  throw HostInteropErrors.cannotConvert(hostContext, value, targetType, "Value must have members.");
               }

               if (targetType.isInterface()) {
                  obj = hostContext.language.access.toObjectProxy(hostContext.internalContext, targetType, value);
               } else {
                  obj = HostInteropReflect.newAdapterInstance(hostContext, targetType, value);
               }
            } else {
               obj = hostContext.language.access.toFunctionProxy(hostContext.internalContext, targetType, value);
            }
         }
      } else {
         if (!interop.isDate(value) || !interop.isTime(value) || !interop.isTimeZone(value)) {
            throw HostInteropErrors.cannotConvert(hostContext, value, targetType, "Value must have date, time and time-zone information.");
         }

         Instant instantValue;
         try {
            instantValue = interop.asInstant(value);
         } catch (UnsupportedMessageException var14) {
            throw CompilerDirectives.shouldNotReachHere(var14);
         }

         if (targetType == Date.class) {
            obj = Date.from(instantValue);
         } else {
            obj = targetType.cast(instantValue);
         }
      }

      assert targetType.isInstance(obj);

      return targetType.cast(obj);
   }

   private static Object asPolyglotException(HostContext hostContext, Object value, InteropLibrary interop) {
      try {
         interop.throwException(value);
         throw UnsupportedMessageException.create();
      } catch (UnsupportedMessageException var4) {
         throw CompilerDirectives.shouldNotReachHere(var4);
      } catch (ThreadDeath var5) {
         throw var5;
      } catch (Throwable var6) {
         return hostContext.language.access.toPolyglotException(hostContext.internalContext, var6);
      }
   }

   @CompilerDirectives.TruffleBoundary
   private static ZonedDateTime createZonedDateTime(LocalDate date, LocalTime time, ZoneId timeZone) {
      return ZonedDateTime.of(date, time, timeZone);
   }

   @CompilerDirectives.TruffleBoundary
   private static LocalDateTime createDateTime(LocalDate date, LocalTime time) {
      return LocalDateTime.of(date, time);
   }

   private static boolean shouldImplementFunction(Object truffleObject, InteropLibrary interop) {
      boolean executable = interop.isExecutable(truffleObject);
      boolean instantiable = false;
      if (!executable) {
         instantiable = interop.isInstantiable(truffleObject);
      }

      return executable || instantiable;
   }

   private static boolean isSupportedMapKeyType(Class<?> keyType) {
      return keyType == Object.class || keyType == String.class || keyType == Long.class || keyType == Integer.class || keyType == Number.class;
   }

   @CompilerDirectives.TruffleBoundary
   private static RuntimeException newInvalidKeyTypeException(Type targetType, HostContext context) {
      String message = "Unsupported Map key type: " + targetType;
      return HostEngineException.classCast(context.access, message);
   }

   private static HostToTypeNode.TypeAndClass<?> getGenericParameterType(Type genericType, int index) {
      if (genericType instanceof ParameterizedType) {
         ParameterizedType parametrizedType = (ParameterizedType)genericType;
         Type[] typeArguments = parametrizedType.getActualTypeArguments();
         Class<?> elementClass = Object.class;
         if (index < typeArguments.length) {
            Type elementType = typeArguments[index];
            if (elementType instanceof ParameterizedType) {
               elementType = ((ParameterizedType)elementType).getRawType();
            }

            if (elementType instanceof Class) {
               elementClass = (Class<?>)elementType;
            }

            return new HostToTypeNode.TypeAndClass<>(typeArguments[index], elementClass);
         }
      }

      return HostToTypeNode.TypeAndClass.ANY;
   }

   private static Type getGenericArrayComponentType(Type genericType) {
      Type genericComponentType = null;
      if (genericType instanceof GenericArrayType) {
         GenericArrayType genericArrayType = (GenericArrayType)genericType;
         genericComponentType = genericArrayType.getGenericComponentType();
      }

      return genericComponentType;
   }

   private static Object truffleObjectToArray(HostContext hostContext, InteropLibrary interop, Object receiver, Class<?> arrayType, Type genericArrayType) {
      Class<?> componentType = arrayType.getComponentType();

      long size;
      try {
         size = interop.getArraySize(receiver);
      } catch (UnsupportedMessageException var15) {
         assert false : "unexpected language behavior";

         size = 0L;
      }

      size = Math.min(size, 2147483647L);
      Object array = Array.newInstance(componentType, (int)size);
      Type genericComponentType = getGenericArrayComponentType(genericArrayType);

      for (int i = 0; i < size; i++) {
         Object guestValue;
         try {
            guestValue = interop.readArrayElement(receiver, i);
         } catch (InvalidArrayIndexException var13) {
            throw HostInteropErrors.invalidArrayIndex(hostContext, receiver, componentType, i);
         } catch (UnsupportedMessageException var14) {
            throw HostInteropErrors.arrayReadUnsupported(hostContext, receiver, componentType);
         }

         Object hostValue = HostToTypeNodeGen.getUncached().execute(hostContext, guestValue, componentType, genericComponentType, true);
         Array.set(array, i, hostValue);
      }

      return array;
   }

   static final class TypeAndClass<T> {
      static final HostToTypeNode.TypeAndClass<Object> ANY = new HostToTypeNode.TypeAndClass<>(null, Object.class);
      final Type type;
      final Class<T> clazz;

      TypeAndClass(Type type, Class<T> clazz) {
         this.type = type;
         this.clazz = clazz;
      }

      @Override
      public String toString() {
         return "[" + this.clazz + ": " + Objects.toString(this.type) + "]";
      }

      @Override
      public int hashCode() {
         int prime = 31;
         int result = 1;
         result = 31 * result + (this.clazz == null ? 0 : this.clazz.hashCode());
         return 31 * result + (this.type == null ? 0 : this.type.hashCode());
      }

      @Override
      public boolean equals(Object obj) {
         if (this == obj) {
            return true;
         } else if (!(obj instanceof HostToTypeNode.TypeAndClass)) {
            return false;
         } else {
            HostToTypeNode.TypeAndClass<?> other = (HostToTypeNode.TypeAndClass<?>)obj;
            return Objects.equals(this.clazz, other.clazz) && Objects.equals(this.type, other.type);
         }
      }
   }
}
