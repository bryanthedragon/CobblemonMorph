
package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.interop.UnsupportedTypeException;
import com.oracle.truffle.polyglot.EngineAccessor;
import com.oracle.truffle.polyglot.PolyglotContextImpl;
import com.oracle.truffle.polyglot.PolyglotEngineException;
import com.oracle.truffle.polyglot.PolyglotLanguageContext;
import com.oracle.truffle.polyglot.PolyglotValueDispatch;
import java.lang.reflect.Type;
import java.util.Arrays;

final class PolyglotInteropErrors {
    private PolyglotInteropErrors() {
    }

    @CompilerDirectives.TruffleBoundary
    static RuntimeException cannotConvertPrimitive(PolyglotLanguageContext context, Object value2, Class<?> targetType) {
        String reason = EngineAccessor.HOST.isPrimitiveTarget(targetType) ? "Invalid or lossy primitive coercion." : "Unsupported target type.";
        return PolyglotEngineException.classCast(String.format("Cannot convert %s to Java type '%s': %s", PolyglotInteropErrors.getValueInfo(context, value2), targetType.getTypeName(), reason));
    }

    @CompilerDirectives.TruffleBoundary
    static RuntimeException invalidListIndex(PolyglotLanguageContext context, Object receiver, Type componentType, long index) {
        String message = String.format("Invalid index %s for List<%s> %s.", index, PolyglotInteropErrors.formatComponentType(componentType), PolyglotInteropErrors.getValueInfo(context, receiver));
        throw PolyglotEngineException.arrayIndexOutOfBounds(message);
    }

    private static Object formatComponentType(Type componentType) {
        return componentType == null || componentType == Object.class ? "Object" : componentType.getTypeName();
    }

    @CompilerDirectives.TruffleBoundary
    static RuntimeException listUnsupported(PolyglotLanguageContext context, Object receiver, Type componentType, String operation) {
        String message = String.format("Unsupported operation %s for List<%s> %s.", operation, PolyglotInteropErrors.formatComponentType(componentType), PolyglotInteropErrors.getValueInfo(context, receiver));
        throw PolyglotEngineException.unsupported(message);
    }

    @CompilerDirectives.TruffleBoundary
    static RuntimeException iterableUnsupported(PolyglotLanguageContext context, Object receiver, Type componentType, String operation) {
        String message = String.format("Unsupported operation %s for Iterable<%s> %s.", operation, PolyglotInteropErrors.formatComponentType(componentType), PolyglotInteropErrors.getValueInfo(context, receiver));
        throw PolyglotEngineException.unsupported(message);
    }

    @CompilerDirectives.TruffleBoundary
    static RuntimeException iteratorUnsupported(PolyglotLanguageContext context, Object receiver, Type componentType, String operation) {
        String message = String.format("Unsupported operation %s for Iterator<%s> %s.", operation, PolyglotInteropErrors.formatComponentType(componentType), PolyglotInteropErrors.getValueInfo(context, receiver));
        throw PolyglotEngineException.unsupported(message);
    }

    @CompilerDirectives.TruffleBoundary
    static RuntimeException stopIteration(PolyglotLanguageContext context, Object receiver, Type componentType) {
        String message = String.format("Iteration was stopped for Iterator<%s> %s.", PolyglotInteropErrors.formatComponentType(componentType), PolyglotInteropErrors.getValueInfo(context, receiver));
        throw PolyglotEngineException.noSuchElement(message);
    }

    @CompilerDirectives.TruffleBoundary
    static RuntimeException iteratorConcurrentlyModified(PolyglotLanguageContext context, Object receiver, Type componentType) {
        String message = String.format("Content was modified during iteration of Iterator<%s> %s.", PolyglotInteropErrors.formatComponentType(componentType), PolyglotInteropErrors.getValueInfo(context, receiver));
        throw PolyglotEngineException.concurrentModificationException(message);
    }

    @CompilerDirectives.TruffleBoundary
    static RuntimeException iteratorElementUnreadable(PolyglotLanguageContext context, Object receiver, Type componentType) {
        String message = String.format("Element is not readable for Iterator<%s> %s.", PolyglotInteropErrors.formatComponentType(componentType), PolyglotInteropErrors.getValueInfo(context, receiver));
        throw PolyglotEngineException.unsupported(message);
    }

    @CompilerDirectives.TruffleBoundary
    static RuntimeException mapUnsupported(PolyglotLanguageContext context, Object receiver, Type keyType, Type valueType, String operation) {
        String message = String.format("Unsupported operation %s for Map<%s, %s> %s.", operation, PolyglotInteropErrors.formatComponentType(keyType), PolyglotInteropErrors.formatComponentType(valueType), PolyglotInteropErrors.getValueInfo(context, receiver));
        throw PolyglotEngineException.unsupported(message);
    }

    @CompilerDirectives.TruffleBoundary
    static RuntimeException invalidMapValue(PolyglotLanguageContext context, Object receiver, Type keyType, Type valueType, Object identifier, Object value2) {
        throw PolyglotEngineException.classCast(String.format("Invalid value %s for Map<%s, %s> %s and identifier '%s'.", PolyglotInteropErrors.getValueInfo(context, value2), PolyglotInteropErrors.formatComponentType(keyType), PolyglotInteropErrors.formatComponentType(valueType), PolyglotInteropErrors.getValueInfo(context, receiver), identifier));
    }

    @CompilerDirectives.TruffleBoundary
    static RuntimeException invalidMapIdentifier(PolyglotLanguageContext context, Object receiver, Type keyType, Type valueType, Object identifier) {
        if (identifier instanceof Number || identifier instanceof String) {
            throw PolyglotEngineException.illegalArgument(String.format("Invalid or unmodifiable value for identifier '%s' for Map<%s, %s> %s.", identifier, PolyglotInteropErrors.formatComponentType(keyType), PolyglotInteropErrors.formatComponentType(valueType), PolyglotInteropErrors.getValueInfo(context, receiver)));
        }
        throw PolyglotEngineException.illegalArgument(String.format("Illegal identifier type '%s' for Map<%s, %s> %s.", identifier == null ? "null" : identifier.getClass().getTypeName(), PolyglotInteropErrors.formatComponentType(keyType), PolyglotInteropErrors.formatComponentType(valueType), PolyglotInteropErrors.getValueInfo(context, receiver)));
    }

    @CompilerDirectives.TruffleBoundary
    static RuntimeException mapEntryUnsupported(PolyglotLanguageContext context, Object receiver, Type keyType, Type valueType, String operation) {
        String message = String.format("Unsupported operation %s for Map.Entry<%s, %s> %s.", operation, PolyglotInteropErrors.formatComponentType(keyType), PolyglotInteropErrors.formatComponentType(valueType), PolyglotInteropErrors.getValueInfo(context, receiver));
        throw PolyglotEngineException.unsupported(message);
    }

    @CompilerDirectives.TruffleBoundary
    static RuntimeException invalidMapEntryArrayIndex(PolyglotLanguageContext context, Object receiver, Type keyType, Type valueType, long index) {
        throw PolyglotEngineException.classCast(String.format("Invalid index %d for Map.Entry<%s, %s> %s.", index, PolyglotInteropErrors.formatComponentType(keyType), PolyglotInteropErrors.formatComponentType(valueType), PolyglotInteropErrors.getValueInfo(context, receiver)));
    }

    @CompilerDirectives.TruffleBoundary
    static RuntimeException invalidListValue(PolyglotLanguageContext context, Object receiver, Type componentType, long identifier, Object value2) {
        throw PolyglotEngineException.classCast(String.format("Invalid value %s for List<%s> %s and index %s.", PolyglotInteropErrors.getValueInfo(context, value2), PolyglotInteropErrors.formatComponentType(componentType), PolyglotInteropErrors.getValueInfo(context, receiver), identifier));
    }

    @CompilerDirectives.TruffleBoundary
    static RuntimeException invalidExecuteArgumentType(PolyglotLanguageContext context, Object receiver, Object[] arguments) {
        String[] formattedArgs = PolyglotInteropErrors.formatArgs(context, arguments);
        String message = String.format("Invalid argument when executing %s with arguments %s.", PolyglotInteropErrors.getValueInfo(context, receiver), Arrays.asList(formattedArgs));
        throw PolyglotEngineException.illegalArgument(message);
    }

    @CompilerDirectives.TruffleBoundary
    static RuntimeException invalidInstantiateArgumentType(PolyglotLanguageContext context, Object receiver, Object[] arguments) {
        String[] formattedArgs = PolyglotInteropErrors.formatArgs(context, arguments);
        String message = String.format("Invalid argument when instantiating %s with arguments %s.", PolyglotInteropErrors.getValueInfo(context, receiver), Arrays.asList(formattedArgs));
        throw PolyglotEngineException.illegalArgument(message);
    }

    @CompilerDirectives.TruffleBoundary
    static RuntimeException invalidInstantiateArity(PolyglotLanguageContext context, Object receiver, Object[] arguments, int minArity, int maxArity, int actual) {
        String[] formattedArgs = PolyglotInteropErrors.formatArgs(context, arguments);
        String message = String.format("Invalid argument count when instantiating %s with arguments %s. %s", PolyglotInteropErrors.getValueInfo(context, receiver), Arrays.asList(formattedArgs), PolyglotValueDispatch.formatExpectedArguments(minArity, maxArity, actual));
        throw PolyglotEngineException.illegalArgument(message);
    }

    @CompilerDirectives.TruffleBoundary
    static RuntimeException invalidExecuteArity(PolyglotLanguageContext context, Object receiver, Object[] arguments, int minArity, int maxArity, int actual) {
        String[] formattedArgs = PolyglotInteropErrors.formatArgs(context, arguments);
        String message = String.format("Invalid argument count when executing %s with arguments %s. %s", PolyglotInteropErrors.getValueInfo(context, receiver), Arrays.asList(formattedArgs), PolyglotValueDispatch.formatExpectedArguments(minArity, maxArity, actual));
        throw PolyglotEngineException.illegalArgument(message);
    }

    @CompilerDirectives.TruffleBoundary
    static RuntimeException invokeUnsupported(PolyglotLanguageContext context, Object receiver, String identifier) {
        String message = String.format("Unsupported operation identifier '%s' and  object %s. Identifier is not executable or instantiable.", identifier, PolyglotInteropErrors.getValueInfo(context, receiver));
        throw PolyglotEngineException.unsupported(message);
    }

    @CompilerDirectives.TruffleBoundary
    static RuntimeException executeUnsupported(PolyglotLanguageContext context, Object receiver) {
        String message = String.format("Unsupported operation for object %s. Object is not executable or instantiable.", PolyglotInteropErrors.getValueInfo(context, receiver));
        throw PolyglotEngineException.unsupported(message);
    }

    private static String[] formatArgs(PolyglotLanguageContext context, Object[] arguments) {
        return PolyglotInteropErrors.formatArgs(context.context, arguments);
    }

    private static String[] formatArgs(PolyglotContextImpl context, Object[] arguments) {
        String[] formattedArgs = new String[arguments.length];
        for (int i = 0; i < arguments.length; ++i) {
            formattedArgs[i] = PolyglotInteropErrors.getValueInfo(context, arguments[i]);
        }
        return formattedArgs;
    }

    static String getValueInfo(PolyglotLanguageContext context, Object value2) {
        return PolyglotValueDispatch.getValueInfo(context != null ? context.context : null, value2);
    }

    static String getValueInfo(PolyglotContextImpl context, Object value2) {
        return PolyglotValueDispatch.getValueInfo(context, value2);
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

