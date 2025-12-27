package org.graalvm.polyglot;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class TypeLiteral<T> {
   private final Type type = extractLiteralType((Class<? extends TypeLiteral>)this.getClass());
   private final Class<T> rawType = (Class<T>)extractRawType(this.type);

   protected TypeLiteral() {
   }

   private static Type extractLiteralType(Class<? extends TypeLiteral> literalClass) {
      Type superType = literalClass.getGenericSuperclass();
      Type typeArgument = null;

      while (true) {
         if (superType instanceof ParameterizedType) {
            ParameterizedType parametrizedType = (ParameterizedType)superType;
            if (parametrizedType.getRawType() != TypeLiteral.class) {
               throw new AssertionError("Unsupported type hierarchy for type literal.");
            }

            typeArgument = parametrizedType.getActualTypeArguments()[0];
            break;
         }

         if (!(superType instanceof Class)) {
            throw new AssertionError("Unsupported type hierarchy for type literal.");
         }

         if (superType == TypeLiteral.class) {
            typeArgument = Object.class;
            break;
         }

         superType = ((Class)superType).getGenericSuperclass();
      }

      return typeArgument;
   }

   private static Class<?> extractRawType(Type type) {
      Class<?> rawType;
      if (type instanceof Class) {
         rawType = (Class<?>)type;
      } else if (type instanceof ParameterizedType) {
         rawType = (Class<?>)((ParameterizedType)type).getRawType();
      } else {
         if (!(type instanceof GenericArrayType)) {
            throw new IllegalArgumentException("Unsupported type: " + type);
         }

         rawType = arrayTypeFromComponentType(extractRawType(((GenericArrayType)type).getGenericComponentType()));
      }

      return rawType;
   }

   private static Class<?> arrayTypeFromComponentType(Class<?> componentType) {
      return Array.newInstance(componentType, 0).getClass();
   }

   public final Type getType() {
      return this.type;
   }

   public final Class<T> getRawType() {
      return this.rawType;
   }

   @Override
   public final boolean equals(Object obj) {
      return super.equals(obj);
   }

   @Override
   public final int hashCode() {
      return super.hashCode();
   }

   @Override
   public final String toString() {
      return "TypeLiteral<" + this.type + ">";
   }
}
