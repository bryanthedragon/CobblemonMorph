package com.oracle.truffle.host;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
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
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.BiFunction;
import org.graalvm.collections.EconomicMap;
import org.graalvm.collections.UnmodifiableEconomicMap;

final class HostClassDesc {
   private final Class<?> type;
   private final Reference<HostClassCache> cache;
   private volatile HostClassDesc.Members members;
   private volatile HostClassDesc.JNIMembers jniMembers;
   private volatile HostClassDesc.MethodsBySignature methodsBySignature;
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
      this.allowedTargetType = this.allowsImplementation && HostInteropReflect.isAbstractType(type) && hasDefaultConstructor(type);
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
      assert !type.isPrimitive();

      if (type.isInterface()) {
         return true;
      } else {
         for (Constructor<?> ctor : type.getConstructors()) {
            if (ctor.getParameterCount() == 0) {
               return true;
            }
         }

         return false;
      }
   }

   static boolean isObjectMethodOverride(Method m) {
      return m.getParameterCount() == 0 && (m.getName().equals("hashCode") || m.getName().equals("toString"))
         || m.getParameterCount() == 1 && m.getName().equals("equals") && m.getParameterTypes()[0] == Object.class;
   }

   private HostClassDesc.Members getMembers() {
      HostClassDesc.Members m = this.members;
      if (m == null) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         synchronized (this) {
            m = this.members;
            if (m == null) {
               HostClassCache localCache = this.getCache();
               this.members = m = new HostClassDesc.Members(localCache, this.type);
            }
         }
      }

      return m;
   }

   private HostClassCache getCache() {
      HostClassCache localCache = this.cache.get();

      assert localCache != null : "cache was collected but should no longer be accessible";

      return localCache;
   }

   private HostClassDesc.JNIMembers getJNIMembers() {
      HostClassDesc.JNIMembers m = this.jniMembers;
      if (m == null) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         synchronized (this) {
            m = this.jniMembers;
            if (m == null) {
               this.jniMembers = m = new HostClassDesc.JNIMembers(this.getMembers());
            }
         }
      }

      return m;
   }

   private HostClassDesc.MethodsBySignature getMethodsBySignature() {
      HostClassDesc.MethodsBySignature m = this.methodsBySignature;
      if (m == null) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         synchronized (this) {
            m = this.methodsBySignature;
            if (m == null) {
               this.methodsBySignature = m = new HostClassDesc.MethodsBySignature(this.getMembers());
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
      HostClassDesc.MethodsBySignature m = this.getMethodsBySignature();
      return onlyStatic ? m.staticMethods.get(nameAndSignature) : m.methods.get(nameAndSignature);
   }

   public HostMethodDesc lookupMethodByJNIName(String jniName, boolean onlyStatic) {
      HostClassDesc.JNIMembers m = this.getJNIMembers();
      return onlyStatic ? m.staticMethods.get(jniName) : m.methods.get(jniName);
   }

   public Collection<String> getMethodNames(boolean onlyStatic, boolean includeInternal) {
      Map<String, HostMethodDesc> methods = onlyStatic ? this.getMembers().staticMethods : this.getMembers().methods;
      if (!includeInternal && !onlyStatic) {
         Collection<String> methodNames = new ArrayList<>(methods.size());

         for (Entry<String, HostMethodDesc> entry : methods.entrySet()) {
            if (!entry.getValue().isInternal()) {
               methodNames.add(entry.getKey());
            }
         }

         return methodNames;
      } else {
         return Collections.unmodifiableCollection(methods.keySet());
      }
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

   private HostAdapterFactory.AdapterResult getOrSetAdapter(HostContext hostContext) {
      CompilerAsserts.neverPartOfCompilation();
      synchronized (this) {
         HostAdapterFactory.AdapterResult result = this.adapter;
         if (result == null) {
            this.adapter = result = HostAdapterFactory.makeAdapterClassFor(this.getCache(), this.type, hostContext.getClassloader());
         }

         return result;
      }
   }

   @Override
   public String toString() {
      return "JavaClass[" + this.type.getCanonicalName() + "]";
   }

   private static final class JNIMembers {
      final UnmodifiableEconomicMap<String, HostMethodDesc> methods;
      final UnmodifiableEconomicMap<String, HostMethodDesc> staticMethods;

      JNIMembers(HostClassDesc.Members members) {
         this.methods = collectJNINamedMethods(members.methods);
         this.staticMethods = collectJNINamedMethods(members.staticMethods);
      }

      private static UnmodifiableEconomicMap<String, HostMethodDesc> collectJNINamedMethods(Map<String, HostMethodDesc> methods) {
         EconomicMap<String, HostMethodDesc> jniMethods = EconomicMap.create();

         for (HostMethodDesc method : methods.values()) {
            if (!method.isConstructor()) {
               for (HostMethodDesc.SingleMethod m : method.getOverloads()) {
                  assert m.isMethod();

                  jniMethods.put(HostInteropReflect.jniName((Method)m.getReflectionMethod()), m);
               }
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
      private static final BiFunction<HostMethodDesc, HostMethodDesc, HostMethodDesc> MERGE = new BiFunction<HostMethodDesc, HostMethodDesc, HostMethodDesc>() {
         public HostMethodDesc apply(HostMethodDesc m1, HostMethodDesc m2) {
            return HostClassDesc.Members.merge(m1, m2);
         }
      };

      Members(HostClassCache hostAccess, Class<?> type) {
         Map<String, HostMethodDesc> methodMap = new LinkedHashMap<>();
         Map<String, HostMethodDesc> staticMethodMap = new LinkedHashMap<>();
         Map<String, HostFieldDesc> fieldMap = new LinkedHashMap<>();
         Map<String, HostFieldDesc> staticFieldMap = new LinkedHashMap<>();
         HostMethodDesc functionalInterfaceMethodImpl = null;
         collectPublicMethods(hostAccess, type, methodMap, staticMethodMap);
         collectPublicFields(hostAccess, type, fieldMap, staticFieldMap);
         HostMethodDesc ctor = collectPublicConstructors(hostAccess, type);
         if (!Modifier.isInterface(type.getModifiers()) && !Modifier.isAbstract(type.getModifiers())) {
            Method implementableAbstractMethod = findFunctionalInterfaceMethod(hostAccess, type);
            if (implementableAbstractMethod != null) {
               functionalInterfaceMethodImpl = lookupAbstractMethodImplementation(implementableAbstractMethod, methodMap);
            }
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
         HostMethodDesc ctor = null;
         if (isClassAccessible(type, hostAccess) && !Modifier.isAbstract(type.getModifiers())) {
            for (Constructor<?> c : type.getConstructors()) {
               if (hostAccess.allowsAccess(c)) {
                  boolean scoped = hostAccess.methodScoped(c);
                  HostMethodDesc.SingleMethod overload = HostMethodDesc.SingleMethod.unreflect(c, scoped);
                  ctor = (HostMethodDesc)(ctor == null ? overload : merge(ctor, overload));
               }
            }
         }

         return ctor;
      }

      private static void collectPublicMethods(
         HostClassCache hostAccess, Class<?> type, Map<String, HostMethodDesc> methodMap, Map<String, HostMethodDesc> staticMethodMap
      ) {
         collectPublicMethods(hostAccess, type, methodMap, staticMethodMap, new HashMap<>(), type);
      }

      private static void collectPublicMethods(
         HostClassCache hostAccess,
         Class<?> type,
         Map<String, HostMethodDesc> methodMap,
         Map<String, HostMethodDesc> staticMethodMap,
         Map<Object, Object> visited,
         Class<?> startType
      ) {
         boolean isPublicType = isClassAccessible(type, hostAccess) && !Proxy.isProxyClass(type);
         boolean includeInherited = hostAccess.allowsPublicAccess || hostAccess.allowsAccessInheritance;
         List<Method> bridgeMethods = null;
         if (isPublicType || !includeInherited) {
            for (Method m : type.getMethods()) {
               Class<?> declaringClass = m.getDeclaringClass();
               if ((!Modifier.isStatic(m.getModifiers()) || declaringClass == startType || !Modifier.isInterface(declaringClass.getModifiers()))
                  && (isClassAccessible(declaringClass, hostAccess) || Proxy.isProxyClass(declaringClass))) {
                  if (m.isBridge() && hostAccess.allowsAccess(m)) {
                     if (bridgeMethods == null) {
                        bridgeMethods = new ArrayList<>();
                     }

                     bridgeMethods.add(m);
                  } else if (hostAccess.allowsAccess(m)) {
                     collectPublicMethod(hostAccess, methodMap, staticMethodMap, visited, m);
                  }
               }
            }

            if (hostAccess.isArrayAccess() && type.isArray()) {
               HostMethodDesc.SingleMethod arrayCloneMethod = HostMethodDesc.SingleMethod.SyntheticArrayCloneMethod.SINGLETON;
               methodMap.put(arrayCloneMethod.getName(), arrayCloneMethod);
            }
         }

         if (includeInherited) {
            if (type.getSuperclass() != null) {
               collectPublicMethods(hostAccess, type.getSuperclass(), methodMap, staticMethodMap, visited, startType);
            }

            for (Class<?> intf : type.getInterfaces()) {
               if (visited.put(intf, intf) == null) {
                  collectPublicMethods(hostAccess, intf, methodMap, staticMethodMap, visited, startType);
               }
            }
         }

         if (bridgeMethods != null && !bridgeMethods.isEmpty()) {
            for (Method mx : bridgeMethods) {
               assert hostAccess.allowsAccess(mx);

               collectPublicMethod(hostAccess, methodMap, staticMethodMap, visited, mx);
            }
         }
      }

      private static void collectPublicMethod(
         HostClassCache hostAccess, Map<String, HostMethodDesc> methodMap, Map<String, HostMethodDesc> staticMethodMap, Map<Object, Object> visited, Method m
      ) {
         HostClassDesc.Members.MethodInfo methodInfo = methodInfo(m);
         if (!visited.containsKey(methodInfo)) {
            visited.put(methodInfo, methodInfo);
            putMethod(hostAccess, m, methodMap, staticMethodMap, false);
         } else {
            HostClassDesc.Members.MethodInfo info = (HostClassDesc.Members.MethodInfo)visited.get(methodInfo);
            if (info.returnType != methodInfo.returnType) {
               putMethod(hostAccess, m, methodMap, staticMethodMap, true);
            }
         }
      }

      private static HostClassDesc.Members.MethodInfo methodInfo(Method m) {
         return new HostClassDesc.Members.MethodInfo(m);
      }

      private static void putMethod(
         HostClassCache hostAccess,
         Method m,
         Map<String, HostMethodDesc> methodMap,
         Map<String, HostMethodDesc> staticMethodMap,
         boolean onlyVisibleFromJniName
      ) {
         assert hostAccess.allowsAccess(m);

         boolean scoped = hostAccess.methodScoped(m);
         HostMethodDesc.SingleMethod method = HostMethodDesc.SingleMethod.unreflect(m, scoped, onlyVisibleFromJniName);
         Map<String, HostMethodDesc> map = Modifier.isStatic(m.getModifiers()) ? staticMethodMap : methodMap;
         map.merge(m.getName(), method, MERGE);
      }

      static HostMethodDesc merge(HostMethodDesc existing, HostMethodDesc other) {
         assert other instanceof HostMethodDesc.SingleMethod;

         if (existing instanceof HostMethodDesc.SingleMethod) {
            return new HostMethodDesc.OverloadedMethod(
               new HostMethodDesc.SingleMethod[]{(HostMethodDesc.SingleMethod)existing, (HostMethodDesc.SingleMethod)other}
            );
         } else {
            HostMethodDesc.SingleMethod[] oldOverloads = existing.getOverloads();
            HostMethodDesc.SingleMethod[] newOverloads = Arrays.copyOf(oldOverloads, oldOverloads.length + 1);
            newOverloads[oldOverloads.length] = (HostMethodDesc.SingleMethod)other;
            return new HostMethodDesc.OverloadedMethod(newOverloads);
         }
      }

      private static void collectPublicFields(
         HostClassCache hostAccess, Class<?> type, Map<String, HostFieldDesc> fieldMap, Map<String, HostFieldDesc> staticFieldMap
      ) {
         if (isClassAccessible(type, hostAccess)) {
            boolean inheritedPublicInstanceFields = false;
            boolean inheritedPublicInaccessibleFields = false;

            for (Field f : type.getFields()) {
               if (!Modifier.isStatic(f.getModifiers())) {
                  if (f.getDeclaringClass() == type) {
                     assert !fieldMap.containsKey(f.getName());

                     if (hostAccess.allowsAccess(f)) {
                        fieldMap.put(f.getName(), HostFieldDesc.unreflect(f));
                     }
                  } else if (isClassAccessible(f.getDeclaringClass(), hostAccess)) {
                     inheritedPublicInstanceFields = true;
                  } else {
                     inheritedPublicInaccessibleFields = true;
                  }
               } else if (f.getDeclaringClass() == type && hostAccess.allowsAccess(f)) {
                  staticFieldMap.put(f.getName(), HostFieldDesc.unreflect(f));
               }
            }

            if (inheritedPublicInstanceFields) {
               collectPublicInstanceFields(hostAccess, type, fieldMap, inheritedPublicInaccessibleFields);
            }

            if (hostAccess.isArrayAccess() && type.isArray()) {
               HostFieldDesc arrayLengthField = HostFieldDesc.SyntheticArrayLengthField.SINGLETON;
               fieldMap.put(arrayLengthField.getName(), arrayLengthField);
            }
         } else if (!Modifier.isInterface(type.getModifiers())) {
            collectPublicInstanceFields(hostAccess, type, fieldMap, true);
         }
      }

      private static void collectPublicInstanceFields(
         HostClassCache hostAccess, Class<?> type, Map<String, HostFieldDesc> fieldMap, boolean mayHaveInaccessibleFields
      ) {
         Set<String> fieldNames = new HashSet<>();

         for (Class<?> superclass = type; superclass != null && superclass != Object.class; superclass = superclass.getSuperclass()) {
            boolean inheritedPublicInstanceFields = false;

            for (Field f : superclass.getFields()) {
               if (!Modifier.isStatic(f.getModifiers())) {
                  if (f.getDeclaringClass() != superclass) {
                     if (Modifier.isPublic(f.getDeclaringClass().getModifiers())) {
                        inheritedPublicInstanceFields = true;
                     }
                  } else if (!mayHaveInaccessibleFields || fieldNames.add(f.getName())) {
                     if (isClassAccessible(f.getDeclaringClass(), hostAccess)) {
                        if (hostAccess.allowsAccess(f)) {
                           fieldMap.putIfAbsent(f.getName(), HostFieldDesc.unreflect(f));
                        }
                     } else {
                        assert mayHaveInaccessibleFields;
                     }
                  }
               }
            }

            if (!inheritedPublicInstanceFields) {
               break;
            }
         }
      }

      private static Method findFunctionalInterfaceMethod(HostClassCache hostAccess, Class<?> clazz) {
         for (Class<?> iface : clazz.getInterfaces()) {
            if (isClassAccessible(iface, hostAccess) && iface.isAnnotationPresent(FunctionalInterface.class)) {
               for (Method m : iface.getMethods()) {
                  if (Modifier.isAbstract(m.getModifiers()) && !HostClassDesc.isObjectMethodOverride(m)) {
                     return m;
                  }
               }
            }
         }

         Class<?> superclass = clazz.getSuperclass();
         return superclass != null && superclass != Object.class ? findFunctionalInterfaceMethod(hostAccess, superclass) : null;
      }

      private static HostMethodDesc lookupAbstractMethodImplementation(Method abstractMethod, Map<String, HostMethodDesc> methodMap) {
         HostMethodDesc accessibleMethodDesc = methodMap.get(abstractMethod.getName());
         if (accessibleMethodDesc != null) {
            Class<?>[] searchTypes = abstractMethod.getParameterTypes();
            HostMethodDesc.SingleMethod[] available = accessibleMethodDesc.getOverloads();
            List<HostMethodDesc.SingleMethod> candidates = new ArrayList<>(available.length);

            label42:
            for (HostMethodDesc.SingleMethod candidate : available) {
               Class<?>[] candidateTypes = candidate.getParameterTypes();
               if (searchTypes.length == candidateTypes.length) {
                  for (int i = 0; i < searchTypes.length; i++) {
                     if (!candidateTypes[i].isAssignableFrom(searchTypes[i]) && !searchTypes[i].isAssignableFrom(candidateTypes[i])) {
                        continue label42;
                     }
                  }

                  candidates.add(candidate);
               }
            }

            if (candidates.size() == available.length) {
               return accessibleMethodDesc;
            }

            if (candidates.size() == 1) {
               return candidates.get(0);
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

         @Override
         public boolean equals(Object obj) {
            if (!(obj instanceof HostClassDesc.Members.MethodInfo)) {
               return false;
            } else {
               HostClassDesc.Members.MethodInfo other = (HostClassDesc.Members.MethodInfo)obj;
               return this.isStatic == other.isStatic
                  && this.name.equals(other.name)
                  && Arrays.equals((Object[])this.parameterTypes, (Object[])other.parameterTypes);
            }
         }

         @Override
         public int hashCode() {
            int prime = 31;
            int result = 1;
            result = 31 * result + (this.isStatic ? 1 : 0);
            result = 31 * result + this.name.hashCode();
            return 31 * result + Arrays.hashCode((Object[])this.parameterTypes);
         }
      }
   }

   private static final class MethodsBySignature {
      final UnmodifiableEconomicMap<String, HostMethodDesc> methods;
      final UnmodifiableEconomicMap<String, HostMethodDesc> staticMethods;

      MethodsBySignature(HostClassDesc.Members members) {
         this.methods = collectMethodsBySignature(members.methods);
         this.staticMethods = collectMethodsBySignature(members.staticMethods);
      }

      private static UnmodifiableEconomicMap<String, HostMethodDesc> collectMethodsBySignature(Map<String, HostMethodDesc> methods) {
         EconomicMap<String, HostMethodDesc> methodMap = EconomicMap.create();

         for (HostMethodDesc method : methods.values()) {
            if (!method.isConstructor()) {
               for (HostMethodDesc.SingleMethod m : method.getOverloads()) {
                  assert m.isMethod();

                  if (!m.isOnlyVisibleFromJniName()) {
                     methodMap.put(HostInteropReflect.toNameAndSignature((Method)m.getReflectionMethod()), m);
                  }
               }
            }
         }

         return methodMap;
      }
   }
}
