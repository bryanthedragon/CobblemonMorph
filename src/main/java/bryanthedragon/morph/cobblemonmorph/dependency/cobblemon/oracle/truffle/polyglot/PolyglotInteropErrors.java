package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.interop.UnsupportedTypeException;
import java.lang.reflect.Type;
import java.util.Arrays;

final class PolyglotInteropErrors {
   private PolyglotInteropErrors() {
   }

   @CompilerDirectives.TruffleBoundary
   static RuntimeException cannotConvertPrimitive(PolyglotLanguageContext context, Object value, Class<?> targetType) {
      String reason;
      if (EngineAccessor.HOST.isPrimitiveTarget(targetType)) {
         reason = "Invalid or lossy primitive coercion.";
      } else {
         reason = "Unsupported target type.";
      }

      return PolyglotEngineException.classCast(
         String.format("Cannot convert %s to Java type '%s': %s", getValueInfo(context, value), targetType.getTypeName(), reason)
      );
   }

   @CompilerDirectives.TruffleBoundary
   static RuntimeException invalidListIndex(PolyglotLanguageContext context, Object receiver, Type componentType, long index) {
      String message = String.format("Invalid index %s for List<%s> %s.", index, formatComponentType(componentType), getValueInfo(context, receiver));
      throw PolyglotEngineException.arrayIndexOutOfBounds(message);
   }

   private static Object formatComponentType(Type componentType) {
      return componentType != null && componentType != Object.class ? componentType.getTypeName() : "Object";
   }

   @CompilerDirectives.TruffleBoundary
   static RuntimeException listUnsupported(PolyglotLanguageContext context, Object receiver, Type componentType, String operation) {
      String message = String.format(
         "Unsupported operation %s for List<%s> %s.", operation, formatComponentType(componentType), getValueInfo(context, receiver)
      );
      throw PolyglotEngineException.unsupported(message);
   }

   @CompilerDirectives.TruffleBoundary
   static RuntimeException iterableUnsupported(PolyglotLanguageContext context, Object receiver, Type componentType, String operation) {
      String message = String.format(
         "Unsupported operation %s for Iterable<%s> %s.", operation, formatComponentType(componentType), getValueInfo(context, receiver)
      );
      throw PolyglotEngineException.unsupported(message);
   }

   @CompilerDirectives.TruffleBoundary
   static RuntimeException iteratorUnsupported(PolyglotLanguageContext context, Object receiver, Type componentType, String operation) {
      String message = String.format(
         "Unsupported operation %s for Iterator<%s> %s.", operation, formatComponentType(componentType), getValueInfo(context, receiver)
      );
      throw PolyglotEngineException.unsupported(message);
   }

   @CompilerDirectives.TruffleBoundary
   static RuntimeException stopIteration(PolyglotLanguageContext context, Object receiver, Type componentType) {
      String message = String.format("Iteration was stopped for Iterator<%s> %s.", formatComponentType(componentType), getValueInfo(context, receiver));
      throw PolyglotEngineException.noSuchElement(message);
   }

   @CompilerDirectives.TruffleBoundary
   static RuntimeException iteratorConcurrentlyModified(PolyglotLanguageContext context, Object receiver, Type componentType) {
      String message = String.format(
         "Content was modified during iteration of Iterator<%s> %s.", formatComponentType(componentType), getValueInfo(context, receiver)
      );
      throw PolyglotEngineException.concurrentModificationException(message);
   }

   @CompilerDirectives.TruffleBoundary
   static RuntimeException iteratorElementUnreadable(PolyglotLanguageContext context, Object receiver, Type componentType) {
      String message = String.format("Element is not readable for Iterator<%s> %s.", formatComponentType(componentType), getValueInfo(context, receiver));
      throw PolyglotEngineException.unsupported(message);
   }

   @CompilerDirectives.TruffleBoundary
   static RuntimeException mapUnsupported(PolyglotLanguageContext context, Object receiver, Type keyType, Type valueType, String operation) {
      String message = String.format(
         "Unsupported operation %s for Map<%s, %s> %s.",
         operation,
         formatComponentType(keyType),
         formatComponentType(valueType),
         getValueInfo(context, receiver)
      );
      throw PolyglotEngineException.unsupported(message);
   }

   @CompilerDirectives.TruffleBoundary
   static RuntimeException invalidMapValue(PolyglotLanguageContext context, Object receiver, Type keyType, Type valueType, Object identifier, Object value) {
      throw PolyglotEngineException.classCast(
         String.format(
            "Invalid value %s for Map<%s, %s> %s and identifier '%s'.",
            getValueInfo(context, value),
            formatComponentType(keyType),
            formatComponentType(valueType),
            getValueInfo(context, receiver),
            identifier
         )
      );
   }

   @CompilerDirectives.TruffleBoundary
   static RuntimeException invalidMapIdentifier(PolyglotLanguageContext context, Object receiver, Type keyType, Type valueType, Object identifier) {
      if (!(identifier instanceof Number) && !(identifier instanceof String)) {
         throw PolyglotEngineException.illegalArgument(
            String.format(
               "Illegal identifier type '%s' for Map<%s, %s> %s.",
               identifier == null ? "null" : identifier.getClass().getTypeName(),
               formatComponentType(keyType),
               formatComponentType(valueType),
               getValueInfo(context, receiver)
            )
         );
      } else {
         throw PolyglotEngineException.illegalArgument(
            String.format(
               "Invalid or unmodifiable value for identifier '%s' for Map<%s, %s> %s.",
               identifier,
               formatComponentType(keyType),
               formatComponentType(valueType),
               getValueInfo(context, receiver)
            )
         );
      }
   }

   @CompilerDirectives.TruffleBoundary
   static RuntimeException mapEntryUnsupported(PolyglotLanguageContext context, Object receiver, Type keyType, Type valueType, String operation) {
      String message = String.format(
         "Unsupported operation %s for Map.Entry<%s, %s> %s.",
         operation,
         formatComponentType(keyType),
         formatComponentType(valueType),
         getValueInfo(context, receiver)
      );
      throw PolyglotEngineException.unsupported(message);
   }

   @CompilerDirectives.TruffleBoundary
   static RuntimeException invalidMapEntryArrayIndex(PolyglotLanguageContext context, Object receiver, Type keyType, Type valueType, long index) {
      throw PolyglotEngineException.classCast(
         String.format(
            "Invalid index %d for Map.Entry<%s, %s> %s.", index, formatComponentType(keyType), formatComponentType(valueType), getValueInfo(context, receiver)
         )
      );
   }

   @CompilerDirectives.TruffleBoundary
   static RuntimeException invalidListValue(PolyglotLanguageContext context, Object receiver, Type componentType, long identifier, Object value) {
      throw PolyglotEngineException.classCast(
         String.format(
            "Invalid value %s for List<%s> %s and index %s.",
            getValueInfo(context, value),
            formatComponentType(componentType),
            getValueInfo(context, receiver),
            identifier
         )
      );
   }

   @CompilerDirectives.TruffleBoundary
   static RuntimeException invalidExecuteArgumentType(PolyglotLanguageContext context, Object receiver, Object[] arguments) {
      String[] formattedArgs = formatArgs(context, arguments);
      String message = String.format("Invalid argument when executing %s with arguments %s.", getValueInfo(context, receiver), Arrays.asList(formattedArgs));
      throw PolyglotEngineException.illegalArgument(message);
   }

   @CompilerDirectives.TruffleBoundary
   static RuntimeException invalidInstantiateArgumentType(PolyglotLanguageContext context, Object receiver, Object[] arguments) {
      String[] formattedArgs = formatArgs(context, arguments);
      String message = String.format("Invalid argument when instantiating %s with arguments %s.", getValueInfo(context, receiver), Arrays.asList(formattedArgs));
      throw PolyglotEngineException.illegalArgument(message);
   }

   @CompilerDirectives.TruffleBoundary
   static RuntimeException invalidInstantiateArity(PolyglotLanguageContext context, Object receiver, Object[] arguments, int minArity, int maxArity, int actual) {
      String[] formattedArgs = formatArgs(context, arguments);
      String message = String.format(
         "Invalid argument count when instantiating %s with arguments %s. %s",
         getValueInfo(context, receiver),
         Arrays.asList(formattedArgs),
         PolyglotValueDispatch.formatExpectedArguments(minArity, maxArity, actual)
      );
      throw PolyglotEngineException.illegalArgument(message);
   }

   @CompilerDirectives.TruffleBoundary
   static RuntimeException invalidExecuteArity(PolyglotLanguageContext context, Object receiver, Object[] arguments, int minArity, int maxArity, int actual) {
      String[] formattedArgs = formatArgs(context, arguments);
      String message = String.format(
         "Invalid argument count when executing %s with arguments %s. %s",
         getValueInfo(context, receiver),
         Arrays.asList(formattedArgs),
         PolyglotValueDispatch.formatExpectedArguments(minArity, maxArity, actual)
      );
      throw PolyglotEngineException.illegalArgument(message);
   }

   @CompilerDirectives.TruffleBoundary
   static RuntimeException invokeUnsupported(PolyglotLanguageContext context, Object receiver, String identifier) {
      String message = String.format(
         "Unsupported operation identifier '%s' and  object %s. Identifier is not executable or instantiable.", identifier, getValueInfo(context, receiver)
      );
      throw PolyglotEngineException.unsupported(message);
   }

   @CompilerDirectives.TruffleBoundary
   static RuntimeException executeUnsupported(PolyglotLanguageContext context, Object receiver) {
      String message = String.format("Unsupported operation for object %s. Object is not executable or instantiable.", getValueInfo(context, receiver));
      throw PolyglotEngineException.unsupported(message);
   }

   private static String[] formatArgs(PolyglotLanguageContext context, Object[] arguments) {
      return formatArgs(context.context, arguments);
   }

   private static String[] formatArgs(PolyglotContextImpl context, Object[] arguments) {
      String[] formattedArgs = new String[arguments.length];

      for (int i = 0; i < arguments.length; i++) {
         formattedArgs[i] = getValueInfo(context, arguments[i]);
      }

      return formattedArgs;
   }

   static String getValueInfo(PolyglotLanguageContext context, Object value) {
      return PolyglotValueDispatch.getValueInfo(context != null ? context.context : null, value);
   }

   static String getValueInfo(PolyglotContextImpl context, Object value) {
      return PolyglotValueDispatch.getValueInfo(context, value);
   }

   @CompilerDirectives.TruffleBoundary
   static UnsupportedTypeException unsupportedTypeException(Object[] args, Throwable e) {
      return UnsupportedTypeException.create(args, e.getMessage());
   }

   @CompilerDirectives.TruffleBoundary
   static UnsupportedTypeException unsupportedTypeException(Object arg, Throwable e) {
      return UnsupportedTypeException.create(new Object[]{arg}, e.getMessage());
   }
}
