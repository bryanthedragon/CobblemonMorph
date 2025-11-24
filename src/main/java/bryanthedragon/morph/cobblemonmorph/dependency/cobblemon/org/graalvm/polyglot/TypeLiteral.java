
package org.graalvm.polyglot;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class TypeLiteral<T> {
    private final Type type = TypeLiteral.extractLiteralType(this.getClass());
    private final Class<T> rawType = TypeLiteral.extractRawType(this.type);

    protected TypeLiteral() {
    }

    private static Type extractLiteralType(Class<? extends TypeLiteral> literalClass) {
        Object typeArgument;
        block4: {
            Type superType = literalClass.getGenericSuperclass();
            typeArgument = null;
            while (true) {
                if (superType instanceof ParameterizedType) {
                    ParameterizedType parametrizedType = (ParameterizedType)superType;
                    if (parametrizedType.getRawType() == TypeLiteral.class) {
                        typeArgument = parametrizedType.getActualTypeArguments()[0];
                        break block4;
                    }
                    throw new AssertionError((Object)"Unsupported type hierarchy for type literal.");
                }
                if (!(superType instanceof Class)) break;
                if (superType == TypeLiteral.class) {
                    typeArgument = Object.class;
                    break block4;
                }
                superType = ((Class)superType).getGenericSuperclass();
            }
            throw new AssertionError((Object)"Unsupported type hierarchy for type literal.");
        }
        return typeArgument;
    }

    private static Class<?> extractRawType(Type type) {
        Class<?> rawType;
        if (type instanceof Class) {
            rawType = (Class<?>)type;
        } else if (type instanceof ParameterizedType) {
            rawType = (Class)((ParameterizedType)type).getRawType();
        } else if (type instanceof GenericArrayType) {
            rawType = TypeLiteral.arrayTypeFromComponentType(TypeLiteral.extractRawType(((GenericArrayType)type).getGenericComponentType()));
        } else {
            throw new IllegalArgumentException("Unsupported type: " + type);
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

    public final boolean equals(Object obj) {
        return super.equals(obj);
    }

    public final int hashCode() {
        return super.hashCode();
    }

    public final String toString() {
        return "TypeLiteral<" + this.type + ">";
    }
}

