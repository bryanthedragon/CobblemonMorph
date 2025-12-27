package com.oracle.truffle.js.runtime.objects;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;

@CompilerDirectives.ValueType
public final class Completion {
   final Completion.Type type;
   final Object value;

   Completion(Completion.Type completionType, Object completionValue) {
      this.type = completionType;
      this.value = completionValue;
   }

   public Object getValue() {
      return this.value;
   }

   public Completion.Type getType() {
      return this.type;
   }

   public boolean isNormal() {
      return this.type == Completion.Type.Normal;
   }

   public boolean isAbrupt() {
      return this.type != Completion.Type.Normal;
   }

   public boolean isReturn() {
      return this.type == Completion.Type.Return;
   }

   public boolean isThrow() {
      return this.type == Completion.Type.Throw;
   }

   public static Completion forNormal(Object value) {
      return new Completion(Completion.Type.Normal, value);
   }

   public static Completion forReturn(Object value) {
      return new Completion(Completion.Type.Return, value);
   }

   public static Completion forThrow(Object value) {
      return new Completion(Completion.Type.Throw, value);
   }

   public static Completion create(Completion.Type type, Object value) {
      return new Completion(type, value);
   }

   @Override
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
