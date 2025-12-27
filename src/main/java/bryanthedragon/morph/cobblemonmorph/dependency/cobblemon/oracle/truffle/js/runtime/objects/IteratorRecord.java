package com.oracle.truffle.js.runtime.objects;

import com.oracle.truffle.api.CompilerDirectives;

@CompilerDirectives.ValueType
public final class IteratorRecord {
   private final JSDynamicObject iterator;
   private final Object nextMethod;
   private boolean done;

   private IteratorRecord(JSDynamicObject iterator, Object nextMethod, boolean done) {
      this.iterator = iterator;
      this.nextMethod = nextMethod;
      this.done = done;
   }

   public static IteratorRecord create(JSDynamicObject iterator, Object nextMethod, boolean done) {
      return new IteratorRecord(iterator, nextMethod, done);
   }

   public static IteratorRecord create(JSDynamicObject iterator, Object nextMethod) {
      return create(iterator, nextMethod, false);
   }

   public JSDynamicObject getIterator() {
      return this.iterator;
   }

   public Object getNextMethod() {
      return this.nextMethod;
   }

   public boolean isDone() {
      return this.done;
   }

   public void setDone(boolean done) {
      this.done = done;
   }
}
