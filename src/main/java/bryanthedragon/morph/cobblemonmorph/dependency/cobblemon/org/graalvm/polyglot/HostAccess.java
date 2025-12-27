package org.graalvm.polyglot;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import org.graalvm.collections.EconomicMap;
import org.graalvm.collections.EconomicSet;
import org.graalvm.collections.Equivalence;
import org.graalvm.collections.MapCursor;

public final class HostAccess {
   private final String name;
   private final EconomicSet<Class<? extends Annotation>> accessAnnotations;
   private final EconomicSet<Class<? extends Annotation>> implementableAnnotations;
   private final EconomicMap<Class<?>, Boolean> excludeTypes;
   private final EconomicSet<AnnotatedElement> members;
   private final EconomicSet<Class<?>> implementableTypes;
   private final List<Object> targetMappings;
   final boolean allowPublic;
   private final boolean allowAllInterfaceImplementations;
   private final boolean allowAllClassImplementations;
   final boolean allowArrayAccess;
   final boolean allowListAccess;
   final boolean allowBufferAccess;
   final boolean allowIterableAccess;
   final boolean allowIteratorAccess;
   final boolean allowMapAccess;
   final boolean allowAccessInheritance;
   private final boolean methodScopingDefault;
   private final EconomicSet<Class<? extends Annotation>> disableMethodScopingAnnotations;
   private final EconomicSet<Executable> disableMethodScoping;
   volatile Object impl;
   private static final HostAccess EMPTY = new HostAccess(
      null, null, null, null, null, null, null, false, false, false, false, false, false, false, false, false, false, false, null, null
   );
   public static final HostAccess EXPLICIT = newBuilder()
      .allowAccessAnnotatedBy(HostAccess.Export.class)
      .allowImplementationsAnnotatedBy(HostAccess.Implementable.class)
      .allowImplementationsAnnotatedBy(FunctionalInterface.class)
      .name("HostAccess.EXPLICIT")
      .build();
   public static final HostAccess SCOPED = newBuilder()
      .allowAccessAnnotatedBy(HostAccess.Export.class)
      .allowImplementationsAnnotatedBy(HostAccess.Implementable.class)
      .allowImplementationsAnnotatedBy(FunctionalInterface.class)
      .methodScoping(true)
      .disableMethodScopingAnnotatedBy(HostAccess.DisableMethodScoping.class)
      .name("HostAccess.SCOPED")
      .build();
   public static final HostAccess ALL = newBuilder()
      .allowPublicAccess(true)
      .allowAllImplementations(true)
      .allowAllClassImplementations(true)
      .allowArrayAccess(true)
      .allowListAccess(true)
      .allowBufferAccess(true)
      .allowIterableAccess(true)
      .allowIteratorAccess(true)
      .allowMapAccess(true)
      .allowAccessInheritance(true)
      .name("HostAccess.ALL")
      .build();
   public static final HostAccess NONE = newBuilder().name("HostAccess.NONE").build();

   HostAccess(
      EconomicSet<Class<? extends Annotation>> annotations,
      EconomicMap<Class<?>, Boolean> excludeTypes,
      EconomicSet<AnnotatedElement> members,
      EconomicSet<Class<? extends Annotation>> implementableAnnotations,
      EconomicSet<Class<?>> implementableTypes,
      List<Object> targetMappings,
      String name,
      boolean allowPublic,
      boolean allowAllImplementations,
      boolean allowAllClassImplementations,
      boolean allowArrayAccess,
      boolean allowListAccess,
      boolean allowBufferAccess,
      boolean allowIterableAccess,
      boolean allowIteratorAccess,
      boolean allowMapAccess,
      boolean allowAccessInheritance,
      boolean methodScopingDefault,
      EconomicSet<Class<? extends Annotation>> disableMethodScopingAnnotations,
      EconomicSet<Executable> disableMethodScoping
   ) {
      this.accessAnnotations = copySet(annotations, Equivalence.IDENTITY);
      this.excludeTypes = copyMap(excludeTypes, Equivalence.IDENTITY);
      this.members = copySet(members, Equivalence.DEFAULT);
      this.implementableAnnotations = copySet(implementableAnnotations, Equivalence.IDENTITY);
      this.implementableTypes = copySet(implementableTypes, Equivalence.IDENTITY);
      this.targetMappings = targetMappings != null ? new ArrayList<>(targetMappings) : null;
      this.name = name;
      this.allowPublic = allowPublic;
      this.allowAllInterfaceImplementations = allowAllImplementations;
      this.allowAllClassImplementations = allowAllClassImplementations;
      this.allowArrayAccess = allowArrayAccess;
      this.allowListAccess = allowListAccess;
      this.allowBufferAccess = allowBufferAccess;
      this.allowIterableAccess = allowListAccess || allowIterableAccess;
      this.allowMapAccess = allowMapAccess;
      this.allowIteratorAccess = allowListAccess || allowIterableAccess || allowMapAccess || allowIteratorAccess;
      this.allowAccessInheritance = allowAccessInheritance;
      this.methodScopingDefault = methodScopingDefault;
      this.disableMethodScopingAnnotations = disableMethodScopingAnnotations;
      this.disableMethodScoping = disableMethodScoping;
   }

   @Override
   public boolean equals(Object obj) {
      if (!(obj instanceof HostAccess)) {
         return false;
      } else {
         HostAccess other = (HostAccess)obj;
         return this.allowPublic == other.allowPublic
            && this.allowAllInterfaceImplementations == other.allowAllInterfaceImplementations
            && this.allowAllClassImplementations == other.allowAllClassImplementations
            && this.allowArrayAccess == other.allowArrayAccess
            && this.allowListAccess == other.allowListAccess
            && this.allowIterableAccess == other.allowIterableAccess
            && this.allowIteratorAccess == other.allowIteratorAccess
            && this.allowMapAccess == other.allowMapAccess
            && equalsMap(this.excludeTypes, other.excludeTypes)
            && equalsSet(this.members, other.members)
            && equalsSet(this.implementableAnnotations, other.implementableAnnotations)
            && equalsSet(this.implementableTypes, other.implementableTypes)
            && Objects.equals(this.targetMappings, other.targetMappings)
            && equalsSet(this.accessAnnotations, other.accessAnnotations);
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(
         this.allowPublic,
         this.allowAllInterfaceImplementations,
         this.allowAllClassImplementations,
         this.allowArrayAccess,
         this.allowListAccess,
         this.allowIterableAccess,
         this.allowIteratorAccess,
         this.allowMapAccess,
         hashMap(this.excludeTypes),
         hashSet(this.members),
         hashSet(this.implementableAnnotations),
         hashSet(this.implementableTypes),
         hashSet(this.members),
         this.targetMappings,
         hashSet(this.accessAnnotations)
      );
   }

   private static <T, V> int hashMap(EconomicMap<T, V> map) {
      int h = 0;
      if (map != null) {
         MapCursor<T, V> cursor = map.getEntries();

         while (cursor.advance()) {
            h += Objects.hashCode(cursor.getKey()) ^ Objects.hashCode(cursor.getValue());
         }
      }

      return h;
   }

   private static <V> int hashSet(EconomicSet<V> set) {
      int h = 0;
      if (set != null) {
         for (V v : set) {
            if (v != null) {
               h += v.hashCode();
            }
         }
      }

      return h;
   }

   private static <T, V> boolean equalsMap(EconomicMap<T, V> map0, EconomicMap<T, V> map1) {
      if (Objects.equals(map0, map1)) {
         return true;
      } else if (map0 == null) {
         return false;
      } else if (map0.size() != map1.size()) {
         return false;
      } else {
         MapCursor<T, V> cursor = map0.getEntries();

         while (cursor.advance()) {
            if (!map1.containsKey(cursor.getKey())) {
               return false;
            }

            V v0 = cursor.getValue();
            V v1 = map1.get(cursor.getKey());
            if (!Objects.equals(v0, v1)) {
               return false;
            }
         }

         return true;
      }
   }

   private static <T> boolean equalsSet(EconomicSet<T> set0, EconomicSet<T> set1) {
      if (Objects.equals(set0, set1)) {
         return true;
      } else if (set0 == null) {
         return false;
      } else if (set0.size() != set1.size()) {
         return false;
      } else {
         for (T v : set0) {
            if (!set1.contains(v)) {
               return false;
            }
         }

         return true;
      }
   }

   private static <T> EconomicSet<T> copySet(EconomicSet<T> values, Equivalence equivalence) {
      return values == null ? null : EconomicSet.create(equivalence, values);
   }

   private static <K, T> EconomicMap<K, T> copyMap(EconomicMap<K, T> values, Equivalence equivalence) {
      return values == null ? null : EconomicMap.create(equivalence, values);
   }

   public static HostAccess.Builder newBuilder() {
      return EMPTY.new Builder();
   }

   public static HostAccess.Builder newBuilder(HostAccess conf) {
      Objects.requireNonNull(conf);
      return EMPTY.new Builder(conf);
   }

   List<Object> getTargetMappings() {
      return this.targetMappings;
   }

   boolean allowsImplementation(Class<?> type) {
      if (this.allowAllInterfaceImplementations && type.isInterface()) {
         return true;
      } else if (this.allowAllClassImplementations && !type.isInterface()) {
         return true;
      } else if (this.implementableTypes != null && this.implementableTypes.contains(type)) {
         return true;
      } else {
         if (this.implementableAnnotations != null) {
            for (Class<? extends Annotation> ann : this.implementableAnnotations) {
               if (type.getAnnotation(ann) != null) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   boolean allowsAccess(AnnotatedElement member) {
      if (this.excludeTypes != null) {
         Class<?> owner = getDeclaringClass(member);
         MapCursor<Class<?>, Boolean> cursor = this.excludeTypes.getEntries();

         while (cursor.advance()) {
            Class<?> ban = cursor.getKey();
            if (cursor.getValue()) {
               if (ban.isAssignableFrom(owner)) {
                  return false;
               }
            } else if (ban == owner) {
               return false;
            }
         }
      }

      if (this.allowPublic) {
         return true;
      } else if (this.members != null && this.members.contains(member)) {
         return true;
      } else {
         if (this.accessAnnotations != null) {
            for (Class<? extends Annotation> ann : this.accessAnnotations) {
               if (hasAnnotation(member, ann)) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   boolean isMethodScoped(Executable e) {
      if (!this.isMethodScopingEnabled()) {
         return false;
      } else if (this.disableMethodScoping != null && this.disableMethodScoping.contains(e)) {
         return false;
      } else {
         if (this.disableMethodScopingAnnotations != null) {
            for (Class<? extends Annotation> ann : this.disableMethodScopingAnnotations) {
               if (e.getAnnotation(ann) != null) {
                  return false;
               }
            }
         }

         return true;
      }
   }

   boolean isMethodScopingEnabled() {
      return this.methodScopingDefault;
   }

   @Override
   public String toString() {
      return this.name == null ? super.toString() : this.name;
   }

   private static boolean hasAnnotation(AnnotatedElement member, Class<? extends Annotation> annotationType) {
      if (member instanceof Field) {
         Field f = (Field)member;
         return f.getAnnotation(annotationType) != null;
      } else if (member instanceof Method) {
         Method m = (Method)member;
         return m.getAnnotation(annotationType) != null;
      } else if (member instanceof Constructor) {
         Constructor<?> c = (Constructor<?>)member;
         return c.getAnnotation(annotationType) != null;
      } else {
         return false;
      }
   }

   private static Class<?> getDeclaringClass(AnnotatedElement member) {
      if (member instanceof Field) {
         Field f = (Field)member;
         return f.getDeclaringClass();
      } else if (member instanceof Method) {
         Method m = (Method)member;
         return m.getDeclaringClass();
      } else if (member instanceof Constructor) {
         Constructor<?> c = (Constructor<?>)member;
         return c.getDeclaringClass();
      } else {
         return Object.class;
      }
   }

   public final class Builder {
      private EconomicSet<Class<? extends Annotation>> accessAnnotations;
      private EconomicSet<Class<? extends Annotation>> implementationAnnotations;
      private EconomicMap<Class<?>, Boolean> excludeTypes;
      private EconomicSet<Class<?>> implementableTypes;
      private EconomicSet<AnnotatedElement> members;
      private List<Object> targetMappings;
      private boolean allowPublic;
      private boolean allowArrayAccess;
      private boolean allowListAccess;
      private boolean allowBufferAccess;
      private boolean allowIterableAccess;
      private boolean allowIteratorAccess;
      private boolean allowMapAccess;
      private boolean allowAllImplementations;
      private boolean allowAllClassImplementations;
      private boolean allowAccessInheritance;
      private boolean methodScopingDefault;
      private EconomicSet<Class<? extends Annotation>> disableMethodScopingAnnotations;
      private EconomicSet<Executable> disableMethodScoping;
      private String name;

      Builder() {
      }

      Builder(HostAccess access) {
         this.accessAnnotations = HostAccess.copySet(access.accessAnnotations, Equivalence.IDENTITY);
         this.excludeTypes = HostAccess.copyMap(access.excludeTypes, Equivalence.IDENTITY);
         this.members = HostAccess.copySet(access.members, Equivalence.DEFAULT);
         this.implementationAnnotations = HostAccess.copySet(access.implementableAnnotations, Equivalence.IDENTITY);
         this.implementableTypes = HostAccess.copySet(access.implementableTypes, Equivalence.IDENTITY);
         this.targetMappings = access.targetMappings != null ? new ArrayList<>(access.targetMappings) : null;
         this.excludeTypes = access.excludeTypes;
         this.allowPublic = access.allowPublic;
         this.allowListAccess = access.allowListAccess;
         this.allowArrayAccess = access.allowArrayAccess;
         this.allowBufferAccess = access.allowBufferAccess;
         this.allowIterableAccess = access.allowIterableAccess;
         this.allowIteratorAccess = access.allowIteratorAccess;
         this.allowMapAccess = access.allowMapAccess;
         this.allowAllImplementations = access.allowAllInterfaceImplementations;
         this.allowAllClassImplementations = access.allowAllClassImplementations;
         this.allowAccessInheritance = access.allowAccessInheritance;
         this.methodScopingDefault = access.methodScopingDefault;
         this.disableMethodScopingAnnotations = HostAccess.copySet(access.disableMethodScopingAnnotations, Equivalence.IDENTITY);
         this.disableMethodScoping = HostAccess.copySet(access.disableMethodScoping, Equivalence.IDENTITY);
      }

      public HostAccess.Builder allowAccessAnnotatedBy(Class<? extends Annotation> annotation) {
         Objects.requireNonNull(annotation);
         if (this.accessAnnotations == null) {
            this.accessAnnotations = EconomicSet.create(Equivalence.IDENTITY);
         }

         this.accessAnnotations.add(annotation);
         return this;
      }

      public HostAccess.Builder allowPublicAccess(boolean allow) {
         this.allowPublic = allow;
         return this;
      }

      public HostAccess.Builder allowAccess(Executable element) {
         Objects.requireNonNull(element);
         if (this.members == null) {
            this.members = EconomicSet.create();
         }

         this.members.add(element);
         return this;
      }

      public HostAccess.Builder allowAccess(Field element) {
         Objects.requireNonNull(element);
         if (this.members == null) {
            this.members = EconomicSet.create();
         }

         this.members.add(element);
         return this;
      }

      public HostAccess.Builder denyAccess(Class<?> clazz) {
         return this.denyAccess(clazz, true);
      }

      public HostAccess.Builder denyAccess(Class<?> clazz, boolean includeSubclasses) {
         Objects.requireNonNull(clazz);
         if (this.excludeTypes == null) {
            this.excludeTypes = EconomicMap.create(Equivalence.IDENTITY);
         }

         this.excludeTypes.put(clazz, includeSubclasses);
         return this;
      }

      public HostAccess.Builder allowAllImplementations(boolean allow) {
         this.allowAllImplementations = allow;
         return this;
      }

      public HostAccess.Builder allowAllClassImplementations(boolean allow) {
         this.allowAllClassImplementations = allow;
         return this;
      }

      public HostAccess.Builder allowImplementationsAnnotatedBy(Class<? extends Annotation> annotation) {
         Objects.requireNonNull(annotation);
         if (this.implementationAnnotations == null) {
            this.implementationAnnotations = EconomicSet.create(Equivalence.IDENTITY);
         }

         this.implementationAnnotations.add(annotation);
         return this;
      }

      public HostAccess.Builder allowImplementations(Class<?> type) {
         Objects.requireNonNull(type);
         if (this.implementableTypes == null) {
            this.implementableTypes = EconomicSet.create(Equivalence.IDENTITY);
         }

         this.implementableTypes.add(type);
         return this;
      }

      public HostAccess.Builder allowArrayAccess(boolean arrayAccess) {
         this.allowArrayAccess = arrayAccess;
         return this;
      }

      public HostAccess.Builder allowListAccess(boolean listAccess) {
         this.allowListAccess = listAccess;
         return this;
      }

      public HostAccess.Builder allowIterableAccess(boolean iterableAccess) {
         this.allowIterableAccess = iterableAccess;
         return this;
      }

      public HostAccess.Builder allowIteratorAccess(boolean iteratorAccess) {
         this.allowIteratorAccess = iteratorAccess;
         return this;
      }

      public HostAccess.Builder allowMapAccess(boolean mapAccess) {
         this.allowMapAccess = mapAccess;
         return this;
      }

      public HostAccess.Builder allowBufferAccess(boolean bufferAccess) {
         this.allowBufferAccess = bufferAccess;
         return this;
      }

      public HostAccess.Builder allowAccessInheritance(boolean inheritAccess) {
         this.allowAccessInheritance = inheritAccess;
         return this;
      }

      public <S, T> HostAccess.Builder targetTypeMapping(Class<S> sourceType, Class<T> targetType, Predicate<S> accepts, Function<S, T> converter) {
         return this.targetTypeMapping(
            (Class<T>)sourceType, (Class<S>)targetType, (Predicate<T>)accepts, (Function<T, S>)converter, HostAccess.TargetMappingPrecedence.HIGH
         );
      }

      public <S, T> HostAccess.Builder targetTypeMapping(
         Class<S> sourceType, Class<T> targetType, Predicate<S> accepts, Function<S, T> converter, HostAccess.TargetMappingPrecedence precedence
      ) {
         Objects.requireNonNull(sourceType);
         Objects.requireNonNull(targetType);
         Objects.requireNonNull(converter);
         Objects.requireNonNull(precedence);
         if (targetType.isPrimitive()) {
            throw new IllegalArgumentException("Primitive target type is not supported as target mapping. Use boxed primitives instead.");
         } else {
            if (this.targetMappings == null) {
               this.targetMappings = new ArrayList<>();
            }

            this.targetMappings.add(Engine.getImpl().newTargetTypeMapping(sourceType, targetType, accepts, converter, precedence));
            return this;
         }
      }

      HostAccess.Builder name(String givenName) {
         this.name = givenName;
         return this;
      }

      public HostAccess.Builder methodScoping(boolean scopingDefault) {
         this.methodScopingDefault = scopingDefault;
         return this;
      }

      public HostAccess.Builder disableMethodScopingAnnotatedBy(Class<? extends Annotation> annotation) {
         Objects.requireNonNull(annotation);
         if (this.disableMethodScopingAnnotations == null) {
            this.disableMethodScopingAnnotations = EconomicSet.create(Equivalence.IDENTITY);
         }

         this.disableMethodScopingAnnotations.add(annotation);
         return this;
      }

      public HostAccess.Builder disableMethodScoping(Executable e) {
         Objects.requireNonNull(e);
         if (this.disableMethodScoping == null) {
            this.disableMethodScoping = EconomicSet.create(Equivalence.IDENTITY);
         }

         this.disableMethodScoping.add(e);
         return this;
      }

      public HostAccess build() {
         return new HostAccess(
            this.accessAnnotations,
            this.excludeTypes,
            this.members,
            this.implementationAnnotations,
            this.implementableTypes,
            this.targetMappings,
            this.name,
            this.allowPublic,
            this.allowAllImplementations,
            this.allowAllClassImplementations,
            this.allowArrayAccess,
            this.allowListAccess,
            this.allowBufferAccess,
            this.allowIterableAccess,
            this.allowIteratorAccess,
            this.allowMapAccess,
            this.allowAccessInheritance,
            this.methodScopingDefault,
            this.disableMethodScopingAnnotations,
            this.disableMethodScoping
         );
      }
   }

   @Retention(RetentionPolicy.RUNTIME)
   @Target({ElementType.CONSTRUCTOR, ElementType.METHOD})
   public @interface DisableMethodScoping {
   }

   @Target({ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.METHOD})
   @Retention(RetentionPolicy.RUNTIME)
   public @interface Export {
   }

   @Retention(RetentionPolicy.RUNTIME)
   @Target(ElementType.TYPE)
   public @interface Implementable {
   }

   public static enum TargetMappingPrecedence {
      HIGHEST,
      HIGH,
      LOW,
      LOWEST;
   }
}
