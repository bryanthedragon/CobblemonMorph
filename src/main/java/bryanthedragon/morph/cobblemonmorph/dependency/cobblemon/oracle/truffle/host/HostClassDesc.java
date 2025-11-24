
package com.oracle.truffle.host;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.host.HostAdapterFactory;
import com.oracle.truffle.host.HostClassCache;
import com.oracle.truffle.host.HostContext;
import com.oracle.truffle.host.HostFieldDesc;
import com.oracle.truffle.host.HostInteropReflect;
import com.oracle.truffle.host.HostMethodDesc;
import java.lang.ref.Reference;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiFunction;
import org.graalvm.collections.EconomicMap;
import org.graalvm.collections.UnmodifiableEconomicMap;

final class HostClassDesc {
    private final Class<?> type;
    private final Reference<HostClassCache> cache;
    private volatile Members members;
    private volatile JNIMembers jniMembers;
    private volatile MethodsBySignature methodsBySignature;
    private volatile HostAdapterFactory.AdapterResult adapter;
    private final boolean allowsImplementation;
    private final boolean allowedTargetType;

    @CompilerDirectives.TruffleBoundary
    static HostClassDesc forClass(HostContext context, Class<?> clazz) {
        return context.getHostClassCache().forClass(clazz);
    }

    @CompilerDirectives.TruffleBoundary
    static HostClassDesc forClass(HostClassCache cache, Class<?> clazz) {
        return cache.forClass(clazz);
    }

    HostClassDesc(Reference<HostClassCache> cacheRef, Class<?> type) {
        this.type = type;
        this.cache = cacheRef;
        this.allowsImplementation = HostInteropReflect.isExtensibleType(type) && this.getCache().allowsImplementation(type);
        this.allowedTargetType = this.allowsImplementation && HostInteropReflect.isAbstractType(type) && HostClassDesc.hasDefaultConstructor(type);
    }

    public boolean isAllowsImplementation() {
        return this.allowsImplementation;
    }

    public boolean isAllowedTargetType() {
        return this.allowedTargetType;
    }

    public Class<?> getType() {
        return this.type;
    }

    private static boolean hasDefaultConstructor(Class<?> type) {
        assert (!type.isPrimitive());
        if (type.isInterface()) {
            return true;
        }
        for (Constructor<?> ctor : type.getConstructors()) {
            if (ctor.getParameterCount() != 0) continue;
            return true;
        }
        return false;
    }

    static boolean isObjectMethodOverride(Method m) {
        return m.getParameterCount() == 0 && (m.getName().equals("hashCode") || m.getName().equals("toString")) || m.getParameterCount() == 1 && m.getName().equals("equals") && m.getParameterTypes()[0] == Object.class;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Members getMembers() {
        Members m = this.members;
        if (m == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            HostClassDesc hostClassDesc = this;
            synchronized (hostClassDesc) {
                m = this.members;
                if (m == null) {
                    HostClassCache localCache = this.getCache();
                    this.members = m = new Members(localCache, this.type);
                }
            }
        }
        return m;
    }

    private HostClassCache getCache() {
        HostClassCache localCache = this.cache.get();
        assert (localCache != null) : "cache was collected but should no longer be accessible";
        return localCache;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private JNIMembers getJNIMembers() {
        JNIMembers m = this.jniMembers;
        if (m == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            HostClassDesc hostClassDesc = this;
            synchronized (hostClassDesc) {
                m = this.jniMembers;
                if (m == null) {
                    this.jniMembers = m = new JNIMembers(this.getMembers());
                }
            }
        }
        return m;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private MethodsBySignature getMethodsBySignature() {
        MethodsBySignature m = this.methodsBySignature;
        if (m == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            HostClassDesc hostClassDesc = this;
            synchronized (hostClassDesc) {
                m = this.methodsBySignature;
                if (m == null) {
                    this.methodsBySignature = m = new MethodsBySignature(this.getMembers());
                }
            }
        }
        return m;
    }

    private HostMethodDesc lookupMethod(String name) {
        return this.getMembers().methods.get(name);
    }

    private HostMethodDesc lookupStaticMethod(String name) {
        return this.getMembers().staticMethods.get(name);
    }

    public HostMethodDesc lookupMethod(String name, boolean onlyStatic) {
        return onlyStatic ? this.lookupStaticMethod(name) : this.lookupMethod(name);
    }

    HostMethodDesc lookupMethodBySignature(String nameAndSignature, boolean onlyStatic) {
        MethodsBySignature m = this.getMethodsBySignature();
        return onlyStatic ? m.staticMethods.get(nameAndSignature) : m.methods.get(nameAndSignature);
    }

    public HostMethodDesc lookupMethodByJNIName(String jniName, boolean onlyStatic) {
        JNIMembers m = this.getJNIMembers();
        return onlyStatic ? m.staticMethods.get(jniName) : m.methods.get(jniName);
    }

    public Collection<String> getMethodNames(boolean onlyStatic, boolean includeInternal) {
        Map<String, HostMethodDesc> methods;
        Map<String, HostMethodDesc> map = methods = onlyStatic ? this.getMembers().staticMethods : this.getMembers().methods;
        if (includeInternal || onlyStatic) {
            return Collections.unmodifiableCollection(methods.keySet());
        }
        ArrayList<String> methodNames = new ArrayList<String>(methods.size());
        for (Map.Entry<String, HostMethodDesc> entry : methods.entrySet()) {
            if (entry.getValue().isInternal()) continue;
            methodNames.add(entry.getKey());
        }
        return methodNames;
    }

    public HostMethodDesc lookupConstructor() {
        return this.getMembers().constructor;
    }

    private HostFieldDesc lookupField(String name) {
        return this.getMembers().fields.get(name);
    }

    private HostFieldDesc lookupStaticField(String name) {
        return this.getMembers().staticFields.get(name);
    }

    public HostFieldDesc lookupField(String name, boolean onlyStatic) {
        return onlyStatic ? this.lookupStaticField(name) : this.lookupField(name);
    }

    public Collection<String> getFieldNames(boolean onlyStatic) {
        return Collections.unmodifiableCollection((onlyStatic ? this.getMembers().staticFields : this.getMembers().fields).keySet());
    }

    public HostMethodDesc getFunctionalMethod() {
        return this.getMembers().functionalMethod;
    }

    public HostAdapterFactory.AdapterResult getAdapter(HostContext hostContext) {
        HostAdapterFactory.AdapterResult result = this.adapter;
        if (result == null) {
            result = this.getOrSetAdapter(hostContext);
        }
        return result;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private HostAdapterFactory.AdapterResult getOrSetAdapter(HostContext hostContext) {
        CompilerAsserts.neverPartOfCompilation();
        HostClassDesc hostClassDesc = this;
        synchronized (hostClassDesc) {
            HostAdapterFactory.AdapterResult result = this.adapter;
            if (result == null) {
                this.adapter = result = HostAdapterFactory.makeAdapterClassFor(this.getCache(), this.type, hostContext.getClassloader());
            }
            return result;
        }
    }

    public String toString() {
        return "JavaClass[" + this.type.getCanonicalName() + "]";
    }

    private static final class MethodsBySignature {
        final UnmodifiableEconomicMap<String, HostMethodDesc> methods;
        final UnmodifiableEconomicMap<String, HostMethodDesc> staticMethods;

        MethodsBySignature(Members members) {
            this.methods = MethodsBySignature.collectMethodsBySignature(members.methods);
            this.staticMethods = MethodsBySignature.collectMethodsBySignature(members.staticMethods);
        }

        private static UnmodifiableEconomicMap<String, HostMethodDesc> collectMethodsBySignature(Map<String, HostMethodDesc> methods) {
            EconomicMap<String, HostMethodDesc> methodMap = EconomicMap.create();
            for (HostMethodDesc method : methods.values()) {
                if (method.isConstructor()) continue;
                for (HostMethodDesc.SingleMethod m : method.getOverloads()) {
                    assert (m.isMethod());
                    if (m.isOnlyVisibleFromJniName()) continue;
                    methodMap.put(HostInteropReflect.toNameAndSignature((Method)m.getReflectionMethod()), m);
                }
            }
            return methodMap;
        }
    }

    private static final class JNIMembers {
        final UnmodifiableEconomicMap<String, HostMethodDesc> methods;
        final UnmodifiableEconomicMap<String, HostMethodDesc> staticMethods;

        JNIMembers(Members members) {
            this.methods = JNIMembers.collectJNINamedMethods(members.methods);
            this.staticMethods = JNIMembers.collectJNINamedMethods(members.staticMethods);
        }

        private static UnmodifiableEconomicMap<String, HostMethodDesc> collectJNINamedMethods(Map<String, HostMethodDesc> methods) {
            EconomicMap<String, HostMethodDesc> jniMethods = EconomicMap.create();
            for (HostMethodDesc method : methods.values()) {
                if (method.isConstructor()) continue;
                for (HostMethodDesc.SingleMethod m : method.getOverloads()) {
                    assert (m.isMethod());
                    jniMethods.put(HostInteropReflect.jniName((Method)m.getReflectionMethod()), m);
                }
            }
            return jniMethods;
        }
    }

    private static class Members {
        final Map<String, HostMethodDesc> methods;
        final Map<String, HostMethodDesc> staticMethods;
        final HostMethodDesc constructor;
        final Map<String, HostFieldDesc> fields;
        final Map<String, HostFieldDesc> staticFields;
        final HostMethodDesc functionalMethod;
        private static final BiFunction<HostMethodDesc, HostMethodDesc, HostMethodDesc> MERGE = new BiFunction<HostMethodDesc, HostMethodDesc, HostMethodDesc>(){

            @Override
            public HostMethodDesc apply(HostMethodDesc m1, HostMethodDesc m2) {
                return Members.merge(m1, m2);
            }
        };

        Members(HostClassCache hostAccess, Class<?> type) {
            Method implementableAbstractMethod;
            LinkedHashMap<String, HostMethodDesc> methodMap = new LinkedHashMap<String, HostMethodDesc>();
            LinkedHashMap<String, HostMethodDesc> staticMethodMap = new LinkedHashMap<String, HostMethodDesc>();
            LinkedHashMap<String, HostFieldDesc> fieldMap = new LinkedHashMap<String, HostFieldDesc>();
            LinkedHashMap<String, HostFieldDesc> staticFieldMap = new LinkedHashMap<String, HostFieldDesc>();
            HostMethodDesc functionalInterfaceMethodImpl = null;
            Members.collectPublicMethods(hostAccess, type, methodMap, staticMethodMap);
            Members.collectPublicFields(hostAccess, type, fieldMap, staticFieldMap);
            HostMethodDesc ctor = Members.collectPublicConstructors(hostAccess, type);
            if (!Modifier.isInterface(type.getModifiers()) && !Modifier.isAbstract(type.getModifiers()) && (implementableAbstractMethod = Members.findFunctionalInterfaceMethod(hostAccess, type)) != null) {
                functionalInterfaceMethodImpl = Members.lookupAbstractMethodImplementation(implementableAbstractMethod, methodMap);
            }
            this.methods = methodMap;
            this.staticMethods = staticMethodMap;
            this.constructor = ctor;
            this.fields = fieldMap;
            this.staticFields = staticFieldMap;
            this.functionalMethod = functionalInterfaceMethodImpl;
        }

        private static boolean isClassAccessible(Class<?> declaringClass, HostClassCache hostAccess) {
            return Modifier.isPublic(declaringClass.getModifiers()) && HostContext.verifyModuleVisibility(hostAccess.getUnnamedModule(), declaringClass);
        }

        private static HostMethodDesc collectPublicConstructors(HostClassCache hostAccess, Class<?> type) {
            HostMethodDesc.SingleMethod ctor = null;
            if (Members.isClassAccessible(type, hostAccess) && !Modifier.isAbstract(type.getModifiers())) {
                for (Constructor<?> c : type.getConstructors()) {
                    if (!hostAccess.allowsAccess(c)) continue;
                    boolean scoped = hostAccess.methodScoped(c);
                    HostMethodDesc.SingleMethod overload = HostMethodDesc.SingleMethod.unreflect(c, scoped);
                    ctor = ctor == null ? overload : Members.merge(ctor, overload);
                }
            }
            return ctor;
        }

        private static void collectPublicMethods(HostClassCache hostAccess, Class<?> type, Map<String, HostMethodDesc> methodMap, Map<String, HostMethodDesc> staticMethodMap) {
            Members.collectPublicMethods(hostAccess, type, methodMap, staticMethodMap, new HashMap<Object, Object>(), type);
        }

        private static void collectPublicMethods(HostClassCache hostAccess, Class<?> type, Map<String, HostMethodDesc> methodMap, Map<String, HostMethodDesc> staticMethodMap, Map<Object, Object> visited, Class<?> startType) {
            boolean isPublicType = Members.isClassAccessible(type, hostAccess) && !Proxy.isProxyClass(type);
            boolean includeInherited = hostAccess.allowsPublicAccess || hostAccess.allowsAccessInheritance;
            ArrayList<Object> bridgeMethods = null;
            if (isPublicType || !includeInherited) {
                for (Method m : type.getMethods()) {
                    Class<?> declaringClass = m.getDeclaringClass();
                    if (Modifier.isStatic(m.getModifiers()) && declaringClass != startType && Modifier.isInterface(declaringClass.getModifiers()) || !Members.isClassAccessible(declaringClass, hostAccess) && !Proxy.isProxyClass(declaringClass)) continue;
                    if (m.isBridge() && hostAccess.allowsAccess(m)) {
                        if (bridgeMethods == null) {
                            bridgeMethods = new ArrayList<Object>();
                        }
                        bridgeMethods.add(m);
                        continue;
                    }
                    if (!hostAccess.allowsAccess(m)) continue;
                    Members.collectPublicMethod(hostAccess, methodMap, staticMethodMap, visited, m);
                }
                if (hostAccess.isArrayAccess() && type.isArray()) {
                    HostMethodDesc.SingleMethod.SyntheticArrayCloneMethod arrayCloneMethod = HostMethodDesc.SingleMethod.SyntheticArrayCloneMethod.SINGLETON;
                    methodMap.put(((HostMethodDesc.SingleMethod)arrayCloneMethod).getName(), arrayCloneMethod);
                }
            }
            if (includeInherited) {
                if (type.getSuperclass() != null) {
                    Members.collectPublicMethods(hostAccess, type.getSuperclass(), methodMap, staticMethodMap, visited, startType);
                }
                for (Class<?> intf : type.getInterfaces()) {
                    if (visited.put(intf, intf) != null) continue;
                    Members.collectPublicMethods(hostAccess, intf, methodMap, staticMethodMap, visited, startType);
                }
            }
            if (bridgeMethods != null && !bridgeMethods.isEmpty()) {
                for (Method method : bridgeMethods) {
                    assert (hostAccess.allowsAccess(method));
                    Members.collectPublicMethod(hostAccess, methodMap, staticMethodMap, visited, method);
                }
            }
        }

        private static void collectPublicMethod(HostClassCache hostAccess, Map<String, HostMethodDesc> methodMap, Map<String, HostMethodDesc> staticMethodMap, Map<Object, Object> visited, Method m) {
            MethodInfo methodInfo = Members.methodInfo(m);
            if (!visited.containsKey(methodInfo)) {
                visited.put(methodInfo, methodInfo);
                Members.putMethod(hostAccess, m, methodMap, staticMethodMap, false);
            } else {
                MethodInfo info = (MethodInfo)visited.get(methodInfo);
                if (info.returnType != methodInfo.returnType) {
                    Members.putMethod(hostAccess, m, methodMap, staticMethodMap, true);
                }
            }
        }

        private static MethodInfo methodInfo(Method m) {
            return new MethodInfo(m);
        }

        private static void putMethod(HostClassCache hostAccess, Method m, Map<String, HostMethodDesc> methodMap, Map<String, HostMethodDesc> staticMethodMap, boolean onlyVisibleFromJniName) {
            assert (hostAccess.allowsAccess(m));
            boolean scoped = hostAccess.methodScoped(m);
            HostMethodDesc.SingleMethod method = HostMethodDesc.SingleMethod.unreflect(m, scoped, onlyVisibleFromJniName);
            Map<String, HostMethodDesc> map = Modifier.isStatic(m.getModifiers()) ? staticMethodMap : methodMap;
            map.merge(m.getName(), method, MERGE);
        }

        static HostMethodDesc merge(HostMethodDesc existing, HostMethodDesc other) {
            assert (other instanceof HostMethodDesc.SingleMethod);
            if (existing instanceof HostMethodDesc.SingleMethod) {
                return new HostMethodDesc.OverloadedMethod(new HostMethodDesc.SingleMethod[]{(HostMethodDesc.SingleMethod)existing, (HostMethodDesc.SingleMethod)other});
            }
            HostMethodDesc.SingleMethod[] oldOverloads = existing.getOverloads();
            HostMethodDesc.SingleMethod[] newOverloads = Arrays.copyOf(oldOverloads, oldOverloads.length + 1);
            newOverloads[oldOverloads.length] = (HostMethodDesc.SingleMethod)other;
            return new HostMethodDesc.OverloadedMethod(newOverloads);
        }

        private static void collectPublicFields(HostClassCache hostAccess, Class<?> type, Map<String, HostFieldDesc> fieldMap, Map<String, HostFieldDesc> staticFieldMap) {
            if (Members.isClassAccessible(type, hostAccess)) {
                boolean inheritedPublicInstanceFields = false;
                boolean inheritedPublicInaccessibleFields = false;
                for (Field f : type.getFields()) {
                    if (!Modifier.isStatic(f.getModifiers())) {
                        if (f.getDeclaringClass() == type) {
                            assert (!fieldMap.containsKey(f.getName()));
                            if (!hostAccess.allowsAccess(f)) continue;
                            fieldMap.put(f.getName(), HostFieldDesc.unreflect(f));
                            continue;
                        }
                        if (Members.isClassAccessible(f.getDeclaringClass(), hostAccess)) {
                            inheritedPublicInstanceFields = true;
                            continue;
                        }
                        inheritedPublicInaccessibleFields = true;
                        continue;
                    }
                    if (f.getDeclaringClass() != type || !hostAccess.allowsAccess(f)) continue;
                    staticFieldMap.put(f.getName(), HostFieldDesc.unreflect(f));
                }
                if (inheritedPublicInstanceFields) {
                    Members.collectPublicInstanceFields(hostAccess, type, fieldMap, inheritedPublicInaccessibleFields);
                }
                if (hostAccess.isArrayAccess() && type.isArray()) {
                    HostFieldDesc.SyntheticArrayLengthField arrayLengthField = HostFieldDesc.SyntheticArrayLengthField.SINGLETON;
                    fieldMap.put(arrayLengthField.getName(), arrayLengthField);
                }
            } else if (!Modifier.isInterface(type.getModifiers())) {
                Members.collectPublicInstanceFields(hostAccess, type, fieldMap, true);
            }
        }

        private static void collectPublicInstanceFields(HostClassCache hostAccess, Class<?> type, Map<String, HostFieldDesc> fieldMap, boolean mayHaveInaccessibleFields) {
            HashSet<String> fieldNames = new HashSet<String>();
            for (Class<?> superclass = type; superclass != null && superclass != Object.class; superclass = superclass.getSuperclass()) {
                boolean inheritedPublicInstanceFields = false;
                for (Field f : superclass.getFields()) {
                    if (Modifier.isStatic(f.getModifiers())) continue;
                    if (f.getDeclaringClass() != superclass) {
                        if (!Modifier.isPublic(f.getDeclaringClass().getModifiers())) continue;
                        inheritedPublicInstanceFields = true;
                        continue;
                    }
                    if (mayHaveInaccessibleFields && !fieldNames.add(f.getName())) continue;
                    if (Members.isClassAccessible(f.getDeclaringClass(), hostAccess)) {
                        if (!hostAccess.allowsAccess(f)) continue;
                        fieldMap.putIfAbsent(f.getName(), HostFieldDesc.unreflect(f));
                        continue;
                    }
                    assert (mayHaveInaccessibleFields);
                }
                if (!inheritedPublicInstanceFields) break;
            }
        }

        private static Method findFunctionalInterfaceMethod(HostClassCache hostAccess, Class<?> clazz) {
            for (Class<?> iface : clazz.getInterfaces()) {
                if (!Members.isClassAccessible(iface, hostAccess) || !iface.isAnnotationPresent(FunctionalInterface.class)) continue;
                for (Method m : iface.getMethods()) {
                    if (!Modifier.isAbstract(m.getModifiers()) || HostClassDesc.isObjectMethodOverride(m)) continue;
                    return m;
                }
            }
            Class<?> superclass = clazz.getSuperclass();
            if (superclass != null && superclass != Object.class) {
                return Members.findFunctionalInterfaceMethod(hostAccess, superclass);
            }
            return null;
        }

        private static HostMethodDesc lookupAbstractMethodImplementation(Method abstractMethod, Map<String, HostMethodDesc> methodMap) {
            HostMethodDesc accessibleMethodDesc = methodMap.get(abstractMethod.getName());
            if (accessibleMethodDesc != null) {
                Class<?>[] searchTypes = abstractMethod.getParameterTypes();
                HostMethodDesc.SingleMethod[] available = accessibleMethodDesc.getOverloads();
                ArrayList<HostMethodDesc.SingleMethod> candidates = new ArrayList<HostMethodDesc.SingleMethod>(available.length);
                block0: for (HostMethodDesc.SingleMethod candidate : available) {
                    Class<?>[] candidateTypes = candidate.getParameterTypes();
                    if (searchTypes.length != candidateTypes.length) continue;
                    for (int i = 0; i < searchTypes.length; ++i) {
                        if (!candidateTypes[i].isAssignableFrom(searchTypes[i]) && !searchTypes[i].isAssignableFrom(candidateTypes[i])) continue block0;
                    }
                    candidates.add(candidate);
                }
                if (candidates.size() == available.length) {
                    return accessibleMethodDesc;
                }
                if (candidates.size() == 1) {
                    return (HostMethodDesc)candidates.get(0);
                }
                if (candidates.size() > 1) {
                    return new HostMethodDesc.OverloadedMethod(candidates.toArray(new HostMethodDesc.SingleMethod[candidates.size()]));
                }
            }
            return null;
        }

        private static class MethodInfo {
            private final boolean isStatic;
            private final String name;
            private final Class<?>[] parameterTypes;
            private final Class<?> returnType;

            MethodInfo(Method m) {
                this.isStatic = Modifier.isStatic(m.getModifiers());
                this.name = m.getName();
                this.parameterTypes = m.getParameterTypes();
                this.returnType = m.getReturnType();
            }

            public boolean equals(Object obj) {
                if (obj instanceof MethodInfo) {
                    MethodInfo other = (MethodInfo)obj;
                    return this.isStatic == other.isStatic && this.name.equals(other.name) && Arrays.equals(this.parameterTypes, other.parameterTypes);
                }
                return false;
            }

            public int hashCode() {
                int prime = 31;
                int result = 1;
                result = 31 * result + (this.isStatic ? 1 : 0);
                result = 31 * result + this.name.hashCode();
                result = 31 * result + Arrays.hashCode(this.parameterTypes);
                return result;
            }
        }
    }
}

