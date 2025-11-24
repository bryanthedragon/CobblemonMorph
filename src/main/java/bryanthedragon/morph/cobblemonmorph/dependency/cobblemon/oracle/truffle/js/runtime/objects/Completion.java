
package com.oracle.truffle.js.runtime.objects;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;

@CompilerDirectives.ValueType
public final class Completion {
    final Type type;
    final Object value;

    Completion(Type completionType, Object completionValue) {
        this.type = completionType;
        this.value = completionValue;
    }

    public Object getValue() {
        return this.value;
    }

    public Type getType() {
        return this.type;
    }

    public boolean isNormal() {
        return this.type == Type.Normal;
    }

    public boolean isAbrupt() {
        return this.type != Type.Normal;
    }

    public boolean isReturn() {
        return this.type == Type.Return;
    }

    public boolean isThrow() {
        return this.type == Type.Throw;
    }

    public static Completion forNormal(Object value2) {
        return new Completion(Type.Normal, value2);
    }

    public static Completion forReturn(Object value2) {
        return new Completion(Type.Return, value2);
    }

    public static Completion forThrow(Object value2) {
        return new Completion(Type.Throw, value2);
    }

    public static Completion create(Type type, Object value2) {
        return new Completion(type, value2);
    }

    public String toString() {
        CompilerAsserts.neverPartOfCompilation();
        return "Completion[type=" + this.type + ", value=" + this.value + "]";
    }

    public static enum Type {
        Normal,
        Return,
        Throw;

    }
}

