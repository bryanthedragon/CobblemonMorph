package com.oracle.truffle.js.nodes.function;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.object.HiddenKey;
import com.oracle.truffle.js.runtime.JSContext;
import java.util.ArrayList;
import java.util.List;

public class ClassElementDefinitionRecord {
   private static final Object[] EMPTY = new Object[0];
   protected final JSContext context;
   private final ClassElementDefinitionRecord.Kind kind;
   private final Object key;
   private final boolean anonymousFunctionDefinition;
   private final boolean isPrivate;
   Object value;
   private Object[] decorators;
   private Object getter;
   private Object setter;
   private List<Object> appendedInitializers;
   private Object[] initializers;

   public static ClassElementDefinitionRecord createField(
      JSContext context, Object key, Object value, boolean isPrivate, boolean anonymousFunctionDefinition, Object[] decorators
   ) {
      return new ClassElementDefinitionRecord(ClassElementDefinitionRecord.Kind.Field, context, key, value, isPrivate, anonymousFunctionDefinition, decorators);
   }

   public static ClassElementDefinitionRecord createPublicMethod(
      JSContext context, Object key, Object value, boolean anonymousFunctionDefinition, Object[] decorators
   ) {
      return new ClassElementDefinitionRecord(ClassElementDefinitionRecord.Kind.Method, context, key, value, false, anonymousFunctionDefinition, decorators);
   }

   public static ClassElementDefinitionRecord createPublicGetter(
      JSContext context, Object key, Object getter, boolean anonymousFunctionDefinition, Object[] decorators
   ) {
      return new ClassElementDefinitionRecord(ClassElementDefinitionRecord.Kind.Getter, context, key, getter, false, anonymousFunctionDefinition, decorators);
   }

   public static ClassElementDefinitionRecord createPublicSetter(
      JSContext context, Object key, Object setter, boolean anonymousFunctionDefinition, Object[] decorators
   ) {
      return new ClassElementDefinitionRecord(ClassElementDefinitionRecord.Kind.Setter, context, key, setter, false, anonymousFunctionDefinition, decorators);
   }

   public static ClassElementDefinitionRecord createPrivateMethod(
      JSContext context, Object key, int frameSlot, int brandSlot, int blockSlot, Object value, boolean anonymousFunctionDefinition, Object[] decorators
   ) {
      return new ClassElementDefinitionRecord.PrivateFrameBasedElementDefinitionRecord(
         ClassElementDefinitionRecord.Kind.Method, context, key, frameSlot, brandSlot, blockSlot, value, anonymousFunctionDefinition, decorators
      );
   }

   public static ClassElementDefinitionRecord createPrivateGetter(
      JSContext context, Object key, int frameSlot, int brandSlot, int blockSlot, Object value, boolean anonymousFunctionDefinition, Object[] decorators
   ) {
      return new ClassElementDefinitionRecord.PrivateFrameBasedElementDefinitionRecord(
         ClassElementDefinitionRecord.Kind.Getter, context, key, frameSlot, brandSlot, blockSlot, value, anonymousFunctionDefinition, decorators
      );
   }

   public static ClassElementDefinitionRecord createPrivateSetter(
      JSContext context, Object key, int frameSlot, int brandSlot, int blockSlot, Object value, boolean anonymousFunctionDefinition, Object[] decorators
   ) {
      return new ClassElementDefinitionRecord.PrivateFrameBasedElementDefinitionRecord(
         ClassElementDefinitionRecord.Kind.Getter, context, key, frameSlot, brandSlot, blockSlot, value, anonymousFunctionDefinition, decorators
      );
   }

   public static ClassElementDefinitionRecord createAutoAccessor(
      JSContext context, Object key, HiddenKey backingStorageKey, Object value, boolean isPrivate, boolean anonymousFunctionDefinition, Object[] decorators
   ) {
      return new ClassElementDefinitionRecord.AutoAccessor(context, key, backingStorageKey, value, isPrivate, anonymousFunctionDefinition, decorators);
   }

   protected ClassElementDefinitionRecord(
      ClassElementDefinitionRecord.Kind kind,
      JSContext context,
      Object key,
      Object value,
      boolean isPrivate,
      boolean anonymousFunctionDefinition,
      Object[] decorators
   ) {
      this.kind = kind;
      this.key = key;
      this.value = value;
      this.anonymousFunctionDefinition = anonymousFunctionDefinition;
      this.decorators = decorators;
      this.isPrivate = isPrivate;
      this.context = context;
   }

   public final boolean isMethod() {
      return this.kind == ClassElementDefinitionRecord.Kind.Method;
   }

   public final boolean isGetter() {
      return this.kind == ClassElementDefinitionRecord.Kind.Getter;
   }

   public final boolean isSetter() {
      return this.kind == ClassElementDefinitionRecord.Kind.Setter;
   }

   public final boolean isAutoAccessor() {
      return this.kind == ClassElementDefinitionRecord.Kind.AutoAccessor;
   }

   public final boolean isField() {
      return this.kind == ClassElementDefinitionRecord.Kind.Field;
   }

   public final ClassElementDefinitionRecord.Kind getKind() {
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
         this.appendedInitializers = new ArrayList<>();
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

   public static class AutoAccessor extends ClassElementDefinitionRecord {
      private final HiddenKey backingStorageKey;

      protected AutoAccessor(
         JSContext context, Object key, HiddenKey backingStorageKey, Object value, boolean isPrivate, boolean anonymousFunctionDefinition, Object[] decorators
      ) {
         super(ClassElementDefinitionRecord.Kind.AutoAccessor, context, key, value, isPrivate, anonymousFunctionDefinition, decorators);
         this.backingStorageKey = backingStorageKey;
      }

      public HiddenKey getBackingStorageKey() {
         return this.backingStorageKey;
      }
   }

   public static enum Kind {
      Method,
      Field,
      Getter,
      Setter,
      AutoAccessor;
   }

   public static final class PrivateFrameBasedElementDefinitionRecord extends ClassElementDefinitionRecord {
      private final int keySlot;
      private final int brandSlot;
      private final int blockScopeSlot;

      private PrivateFrameBasedElementDefinitionRecord(
         ClassElementDefinitionRecord.Kind kind,
         JSContext context,
         Object key,
         int keySlot,
         int brandSlot,
         int blockScopeSlot,
         Object value,
         boolean anonymousFunctionDefinition,
         Object[] decorators
      ) {
         super(kind, context, key, value, true, anonymousFunctionDefinition, decorators);
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
}
