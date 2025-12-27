package com.oracle.truffle.host;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleOptions;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;

abstract class HostFieldDesc {
   private final boolean isFinal;
   private final Class<?> type;
   private final Type genericType;
   private final String name;

   private HostFieldDesc(Class<?> type, Type genericType, String name, boolean isFinal) {
      this.isFinal = isFinal;
      this.type = type;
      this.genericType = genericType;
      this.name = name;
   }

   public final boolean isFinal() {
      return this.isFinal;
   }

   public final Class<?> getType() {
      return this.type;
   }

   public final Type getGenericType() {
      return this.genericType;
   }

   public final String getName() {
      return this.name;
   }

   public abstract Object get(Object receiver);

   public abstract void set(Object receiver, Object value) throws ClassCastException, NullPointerException, IllegalArgumentException;

   static HostFieldDesc unreflect(Field reflectionField) {
      assert isAccessible(reflectionField);

      return (HostFieldDesc)(TruffleOptions.AOT ? new HostFieldDesc.ReflectImpl(reflectionField) : new HostFieldDesc.MHImpl(reflectionField));
   }

   static boolean isAccessible(Field field) {
      return Modifier.isPublic(field.getModifiers()) && Modifier.isPublic(field.getDeclaringClass().getModifiers());
   }

   private static final class MHImpl extends HostFieldDesc {
      private final Field field;
      @CompilerDirectives.CompilationFinal
      private MethodHandle getHandle;
      @CompilerDirectives.CompilationFinal
      private MethodHandle setHandle;

      MHImpl(Field field) {
         super(field.getType(), field.getGenericType(), field.getName(), Modifier.isFinal(field.getModifiers()));
         this.field = field;
      }

      @Override
      public Object get(Object receiver) {
         if (this.getHandle == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.getHandle = this.makeGetMethodHandle();
         }

         try {
            return invokeGetHandle(this.getHandle, receiver);
         } catch (Throwable var3) {
            throw HostInteropReflect.rethrow(var3);
         }
      }

      @Override
      public void set(Object receiver, Object value) {
         if (this.setHandle == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.setHandle = this.makeSetMethodHandle();
         }

         try {
            invokeSetHandle(this.setHandle, receiver, value);
         } catch (Throwable var4) {
            throw HostInteropReflect.rethrow(var4);
         }
      }

      @CompilerDirectives.TruffleBoundary(allowInlining = true)
      private static Object invokeGetHandle(MethodHandle invokeHandle, Object receiver) throws Throwable {
         return (Object)invokeHandle.invokeExact((Object)receiver);
      }

      @CompilerDirectives.TruffleBoundary(allowInlining = true)
      private static void invokeSetHandle(MethodHandle invokeHandle, Object receiver, Object value) throws Throwable {
         invokeHandle.invokeExact((Object)receiver, (Object)value);
      }

      private MethodHandle makeGetMethodHandle() {
         CompilerAsserts.neverPartOfCompilation();

         try {
            MethodHandle getter = MethodHandles.publicLookup().unreflectGetter(this.field);
            return Modifier.isStatic(this.field.getModifiers())
               ? MethodHandles.dropArguments(getter.asType(MethodType.methodType(Object.class)), 0, Object.class)
               : getter.asType(MethodType.methodType(Object.class, Object.class));
         } catch (IllegalAccessException var2) {
            throw new IllegalStateException(var2);
         }
      }

      private MethodHandle makeSetMethodHandle() {
         CompilerAsserts.neverPartOfCompilation();

         try {
            MethodHandle setter = MethodHandles.publicLookup().unreflectSetter(this.field);
            return Modifier.isStatic(this.field.getModifiers())
               ? MethodHandles.dropArguments(setter.asType(MethodType.methodType(void.class, Object.class)), 0, Object.class)
               : setter.asType(MethodType.methodType(void.class, Object.class, Object.class));
         } catch (IllegalAccessException var2) {
            throw new IllegalStateException(var2);
         }
      }

      @Override
      public String toString() {
         return "Field[" + this.field.toString() + "]";
      }
   }

   private static final class ReflectImpl extends HostFieldDesc {
      private final Field field;

      ReflectImpl(Field field) {
         super(field.getType(), field.getGenericType(), field.getName(), Modifier.isFinal(field.getModifiers()));
         this.field = field;
      }

      @Override
      public Object get(Object receiver) {
         try {
            return reflectGet(this.field, receiver);
         } catch (IllegalAccessException var3) {
            throw CompilerDirectives.shouldNotReachHere(var3);
         }
      }

      @Override
      public void set(Object receiver, Object value) {
         try {
            reflectSet(this.field, receiver, value);
         } catch (IllegalAccessException var4) {
            throw CompilerDirectives.shouldNotReachHere(var4);
         }
      }

      @CompilerDirectives.TruffleBoundary
      private static Object reflectGet(Field field, Object receiver) throws IllegalArgumentException, IllegalAccessException {
         return field.get(receiver);
      }

      @CompilerDirectives.TruffleBoundary
      private static void reflectSet(Field field, Object receiver, Object value) throws IllegalArgumentException, IllegalAccessException {
         field.set(receiver, value);
      }

      @Override
      public String toString() {
         return "Field[" + this.field.toString() + "]";
      }
   }

   static final class SyntheticArrayLengthField extends HostFieldDesc {
      static final HostFieldDesc.SyntheticArrayLengthField SINGLETON = new HostFieldDesc.SyntheticArrayLengthField();

      private SyntheticArrayLengthField() {
         super(int.class, int.class, "length", true);
      }

      @Override
      public Object get(Object receiver) {
         try {
            return Array.getLength(receiver);
         } catch (IllegalArgumentException var3) {
            throw CompilerDirectives.shouldNotReachHere(var3);
         }
      }

      @Override
      public void set(Object receiver, Object value) {
         CompilerDirectives.shouldNotReachHere();
      }

      @Override
      public String toString() {
         return "Field[length]";
      }
   }
}
