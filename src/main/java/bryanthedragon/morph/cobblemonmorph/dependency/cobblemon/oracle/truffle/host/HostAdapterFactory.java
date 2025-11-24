
package com.oracle.truffle.host;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.host.HostAdapterBytecodeGenerator;
import com.oracle.truffle.host.HostAdapterClassLoader;
import com.oracle.truffle.host.HostAdapterSuperMembers;
import com.oracle.truffle.host.HostClassCache;
import com.oracle.truffle.host.HostClassDesc;
import com.oracle.truffle.host.HostClassLoader;
import com.oracle.truffle.host.HostContext;
import com.oracle.truffle.host.HostEngineException;
import com.oracle.truffle.host.HostInteropReflect;
import com.oracle.truffle.host.HostMethodDesc;
import com.oracle.truffle.host.HostObject;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.impl.AbstractPolyglotImpl;

final class HostAdapterFactory {
    HostAdapterFactory() {
    }

    @CompilerDirectives.TruffleBoundary
    static AdapterResult getAdapterClassFor(HostContext hostContext, Class<?>[] types, Object classOverrides) {
        assert (types.length > 0);
        HostClassCache hostClassCache = hostContext.getHostClassCache();
        HostClassLoader hostClassLoader = hostContext.getClassloader();
        if (classOverrides == null) {
            AdapterResult prev;
            List<Class<?>> cacheKey;
            if (types.length == 1) {
                HostClassDesc classDesc = HostClassDesc.forClass(hostClassCache, types[0]);
                return classDesc.getAdapter(hostContext);
            }
            Map<List<Class<?>>, AdapterResult> map = hostContext.adapterCache.get(HostAdapterFactory.getTypeForCache(types));
            AdapterResult result = map.get(cacheKey = Arrays.asList(types));
            if (result == null && (result = HostAdapterFactory.makeAdapterClassFor(hostClassCache, types, hostClassLoader, classOverrides)).isSuccess() && (prev = map.putIfAbsent(cacheKey, result)) != null) {
                result = prev;
            }
            return result;
        }
        return HostAdapterFactory.makeAdapterClassFor(hostClassCache, types, hostClassLoader, classOverrides);
    }

    @CompilerDirectives.TruffleBoundary
    static AdapterResult makeAdapterClassFor(HostClassCache hostClassCache, Class<?>[] types, ClassLoader classLoader, Object classOverrides) {
        return HostAdapterFactory.makeAdapterClassForCommon(hostClassCache, types, classLoader, classOverrides);
    }

    @CompilerDirectives.TruffleBoundary
    static AdapterResult makeAdapterClassFor(HostClassCache hostClassCache, Class<?> type, ClassLoader classLoader) {
        return HostAdapterFactory.makeAdapterClassForCommon(hostClassCache, new Class[]{type}, classLoader, null);
    }

    private static AdapterResult makeAdapterClassForCommon(HostClassCache hostClassCache, Class<?>[] types, ClassLoader classLoader, Object classOverrides) {
        Class<?> adapterClass;
        assert (types.length > 0);
        CompilerAsserts.neverPartOfCompilation();
        AbstractPolyglotImpl.AbstractHostAccess polyglotAccess = hostClassCache.polyglotHostAccess;
        Class superClass = null;
        ArrayList interfaces = new ArrayList();
        for (Class<?> t : types) {
            if (!t.isInterface()) {
                if (superClass != null) {
                    throw HostEngineException.illegalArgument(polyglotAccess, String.format("Can not extend multiple classes %s and %s. At most one of the specified types can be a class, the rest must all be interfaces.", t.getCanonicalName(), superClass.getCanonicalName()));
                }
                if (Modifier.isFinal(t.getModifiers())) {
                    throw HostEngineException.illegalArgument(polyglotAccess, String.format("Can not extend final class %s.", t.getCanonicalName()));
                }
                superClass = t;
            } else {
                if (interfaces.size() >= 65535) {
                    throw HostEngineException.illegalArgument(polyglotAccess, "interface limit exceeded");
                }
                interfaces.add(t);
            }
            if (!Modifier.isPublic(t.getModifiers())) {
                throw HostEngineException.illegalArgument(polyglotAccess, String.format("Class not public: %s.", t.getCanonicalName()));
            }
            if (HostInteropReflect.isExtensibleType(t) && hostClassCache.allowsImplementation(t)) continue;
            throw HostEngineException.illegalArgument(polyglotAccess, "Implementation not allowed for " + t);
        }
        ClassLoader commonLoader = HostAdapterFactory.getCommonClassLoader(classLoader, superClass = superClass != null ? superClass : Object.class);
        if (!HostAdapterFactory.classLoaderCanSee(commonLoader, types)) {
            throw HostEngineException.illegalArgument(polyglotAccess, "Could not determine a class loader that can see all types: " + Arrays.toString(types));
        }
        try {
            adapterClass = HostAdapterFactory.generateAdapterClassFor(superClass, interfaces, commonLoader, hostClassCache, classOverrides);
        }
        catch (IllegalArgumentException ex) {
            return new AdapterResult(HostEngineException.illegalArgument(polyglotAccess, ex));
        }
        catch (RuntimeException ex) {
            if (polyglotAccess.isEngineException(ex)) {
                return new AdapterResult(ex);
            }
            throw ex;
        }
        HostClassDesc classDesc = hostClassCache.forClass(adapterClass);
        HostMethodDesc constructor = classDesc.lookupConstructor();
        HostMethodDesc.SingleMethod valueConstructor = null;
        if (constructor != null) {
            for (HostMethodDesc.SingleMethod overload : constructor.getOverloads()) {
                if (overload.getParameterCount() != 1 || overload.getParameterTypes()[0] != Value.class) continue;
                valueConstructor = overload;
                break;
            }
            return new AdapterResult(adapterClass, constructor, valueConstructor);
        }
        return new AdapterResult(HostEngineException.illegalArgument(polyglotAccess, "No accessible constructor: " + superClass.getCanonicalName()));
    }

    private static Class<?> generateAdapterClassFor(Class<?> superClass, List<Class<?>> interfaces, ClassLoader commonLoader, HostClassCache hostClassCache, Object classOverrides) {
        boolean classOverride = classOverrides != null;
        HostAdapterBytecodeGenerator bytecodeGenerator = new HostAdapterBytecodeGenerator(superClass, interfaces, commonLoader, hostClassCache, classOverride);
        HostAdapterClassLoader generatedClassLoader = bytecodeGenerator.createAdapterClassLoader();
        return generatedClassLoader.generateClass(commonLoader, classOverrides);
    }

    @CompilerDirectives.TruffleBoundary
    static Object getSuperAdapter(HostObject adapter2) {
        assert (HostAdapterFactory.isAdapterInstance(adapter2.obj));
        return new HostAdapterSuperMembers(adapter2);
    }

    @CompilerDirectives.TruffleBoundary
    static String getSuperMethodName(String methodName) {
        assert (!methodName.startsWith("super$"));
        return "super$".concat(methodName);
    }

    @CompilerDirectives.TruffleBoundary
    static boolean isAdapterInstance(Object adapter2) {
        return HostAdapterClassLoader.isAdapterInstance(adapter2);
    }

    private static boolean classLoaderCanSee(ClassLoader loader, Class<?> clazz) {
        if (clazz.getClassLoader() == loader) {
            return true;
        }
        try {
            return Class.forName(clazz.getName(), false, loader) == clazz;
        }
        catch (ClassNotFoundException e) {
            return false;
        }
    }

    private static boolean classLoaderCanSee(ClassLoader loader, Class<?>[] classes) {
        for (Class<?> c : classes) {
            if (HostAdapterFactory.classLoaderCanSee(loader, c)) continue;
            return false;
        }
        return true;
    }

    private static ClassLoader getCommonClassLoader(ClassLoader classLoader, Class<?> superclass) {
        if (superclass != Object.class && HostAdapterClassLoader.isGeneratedClass(superclass)) {
            return superclass.getClassLoader();
        }
        return classLoader;
    }

    private static Class<?> getTypeForCache(Class<?>[] types) {
        return types[0];
    }

    static final class AdapterResult {
        private final Class<?> adapterClass;
        private final HostMethodDesc constructor;
        private final HostMethodDesc.SingleMethod valueConstructor;
        private final RuntimeException exception;

        AdapterResult(Class<?> adapterClass, HostMethodDesc constructor, HostMethodDesc.SingleMethod valueConstructor) {
            this.adapterClass = Objects.requireNonNull(adapterClass);
            this.constructor = constructor;
            this.valueConstructor = valueConstructor;
            this.exception = null;
        }

        AdapterResult(RuntimeException exception) {
            this.adapterClass = null;
            this.constructor = null;
            this.valueConstructor = null;
            this.exception = exception;
        }

        Class<?> getAdapterClass() {
            return this.adapterClass;
        }

        HostMethodDesc getConstructor() {
            return this.constructor;
        }

        HostMethodDesc.SingleMethod getValueConstructor() {
            return this.valueConstructor;
        }

        boolean isSuccess() {
            return this.constructor != null;
        }

        boolean isAutoConvertible() {
            return this.valueConstructor != null;
        }

        RuntimeException throwException() {
            throw this.exception;
        }
    }
}

