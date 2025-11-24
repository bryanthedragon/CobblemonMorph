
package com.oracle.truffle.js.nodes.function;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.object.HiddenKey;
import com.oracle.truffle.js.runtime.JSContext;
import java.util.ArrayList;
import java.util.List;

public class ClassElementDefinitionRecord {
    private static final Object[] EMPTY = new Object[0];
    protected final JSContext context;
    private final Kind kind;
    private final Object key;
    private final boolean anonymousFunctionDefinition;
    private final boolean isPrivate;
    Object value;
    private Object[] decorators;
    private Object getter;
    private Object setter;
    private List<Object> appendedInitializers;
    private Object[] initializers;

    public static ClassElementDefinitionRecord createField(JSContext context, Object key, Object value2, boolean isPrivate, boolean anonymousFunctionDefinition, Object[] decorators) {
        return new ClassElementDefinitionRecord(Kind.Field, context, key, value2, isPrivate, anonymousFunctionDefinition, decorators);
    }

    public static ClassElementDefinitionRecord createPublicMethod(JSContext context, Object key, Object value2, boolean anonymousFunctionDefinition, Object[] decorators) {
        return new ClassElementDefinitionRecord(Kind.Method, context, key, value2, false, anonymousFunctionDefinition, decorators);
    }

    public static ClassElementDefinitionRecord createPublicGetter(JSContext context, Object key, Object getter, boolean anonymousFunctionDefinition, Object[] decorators) {
        return new ClassElementDefinitionRecord(Kind.Getter, context, key, getter, false, anonymousFunctionDefinition, decorators);
    }

    public static ClassElementDefinitionRecord createPublicSetter(JSContext context, Object key, Object setter, boolean anonymousFunctionDefinition, Object[] decorators) {
        return new ClassElementDefinitionRecord(Kind.Setter, context, key, setter, false, anonymousFunctionDefinition, decorators);
    }

    public static ClassElementDefinitionRecord createPrivateMethod(JSContext context, Object key, int frameSlot, int brandSlot, int blockSlot, Object value2, boolean anonymousFunctionDefinition, Object[] decorators) {
        return new PrivateFrameBasedElementDefinitionRecord(Kind.Method, context, key, frameSlot, brandSlot, blockSlot, value2, anonymousFunctionDefinition, decorators);
    }

    public static ClassElementDefinitionRecord createPrivateGetter(JSContext context, Object key, int frameSlot, int brandSlot, int blockSlot, Object value2, boolean anonymousFunctionDefinition, Object[] decorators) {
        return new PrivateFrameBasedElementDefinitionRecord(Kind.Getter, context, key, frameSlot, brandSlot, blockSlot, value2, anonymousFunctionDefinition, decorators);
    }

    public static ClassElementDefinitionRecord createPrivateSetter(JSContext context, Object key, int frameSlot, int brandSlot, int blockSlot, Object value2, boolean anonymousFunctionDefinition, Object[] decorators) {
        return new PrivateFrameBasedElementDefinitionRecord(Kind.Getter, context, key, frameSlot, brandSlot, blockSlot, value2, anonymousFunctionDefinition, decorators);
    }

    public static ClassElementDefinitionRecord createAutoAccessor(JSContext context, Object key, HiddenKey backingStorageKey, Object value2, boolean isPrivate, boolean anonymousFunctionDefinition, Object[] decorators) {
        return new AutoAccessor(context, key, backingStorageKey, value2, isPrivate, anonymousFunctionDefinition, decorators);
    }

    protected ClassElementDefinitionRecord(Kind kind, JSContext context, Object key, Object value2, boolean isPrivate, boolean anonymousFunctionDefinition, Object[] decorators) {
        this.kind = kind;
        this.key = key;
        this.value = value2;
        this.anonymousFunctionDefinition = anonymousFunctionDefinition;
        this.decorators = decorators;
        this.isPrivate = isPrivate;
        this.context = context;
    }

    public final boolean isMethod() {
        return this.kind == Kind.Method;
    }

    public final boolean isGetter() {
        return this.kind == Kind.Getter;
    }

    public final boolean isSetter() {
        return this.kind == Kind.Setter;
    }

    public final boolean isAutoAccessor() {
        return this.kind == Kind.AutoAccessor;
    }

    public final boolean isField() {
        return this.kind == Kind.Field;
    }

    public final Kind getKind() {
        return this.kind;
    }

    public final boolean isPrivate() {
        return this.isPrivate;
    }

    public Object[] getDecorators() {
        return this.decorators;
    }

    public Object getKey() {
        return this.key;
    }

    public Object getValue() {
        return this.value;
    }

    public Object[] getInitializers() {
        return this.initializers == null ? EMPTY : this.initializers;
    }

    @CompilerDirectives.TruffleBoundary
    public void appendInitializer(Object initializer) {
        if (this.appendedInitializers == null) {
            this.appendedInitializers = new ArrayList<Object>();
        }
        this.appendedInitializers.add(initializer);
    }

    public void setValue(Object newValue) {
        this.value = newValue;
    }

    @CompilerDirectives.TruffleBoundary
    public void cleanDecorator() {
        this.decorators = EMPTY;
        this.initializers = this.appendedInitializers == null ? EMPTY : this.appendedInitializers.toArray(EMPTY);
    }

    public Object isAnonymousFunction() {
        return this.anonymousFunctionDefinition;
    }

    public void setGetter(Object newGetter) {
        this.getter = newGetter;
    }

    public void setSetter(Object newSetter) {
        this.setter = newSetter;
    }

    public Object getGetter() {
        return this.getter;
    }

    public Object getSetter() {
        return this.setter;
    }

    public static class AutoAccessor
    extends ClassElementDefinitionRecord {
        private final HiddenKey backingStorageKey;

        protected AutoAccessor(JSContext context, Object key, HiddenKey backingStorageKey, Object value2, boolean isPrivate, boolean anonymousFunctionDefinition, Object[] decorators) {
            super(Kind.AutoAccessor, context, key, value2, isPrivate, anonymousFunctionDefinition, decorators);
            this.backingStorageKey = backingStorageKey;
        }

        public HiddenKey getBackingStorageKey() {
            return this.backingStorageKey;
        }
    }

    public static final class PrivateFrameBasedElementDefinitionRecord
    extends ClassElementDefinitionRecord {
        private final int keySlot;
        private final int brandSlot;
        private final int blockScopeSlot;

        private PrivateFrameBasedElementDefinitionRecord(Kind kind, JSContext context, Object key, int keySlot, int brandSlot, int blockScopeSlot, Object value2, boolean anonymousFunctionDefinition, Object[] decorators) {
            super(kind, context, key, value2, true, anonymousFunctionDefinition, decorators);
            this.keySlot = keySlot;
            this.brandSlot = brandSlot;
            this.blockScopeSlot = blockScopeSlot;
        }

        public int getKeySlot() {
            return this.keySlot;
        }

        public int getBrandSlot() {
            return this.brandSlot;
        }

        public int getBlockScopeSlot() {
            return this.blockScopeSlot;
        }
    }

    public static enum Kind {
        Method,
        Field,
        Getter,
        Setter,
        AutoAccessor;

    }
}

