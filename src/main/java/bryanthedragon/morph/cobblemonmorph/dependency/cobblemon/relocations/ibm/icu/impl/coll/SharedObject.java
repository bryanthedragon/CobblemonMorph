package com.cobblemon.mod.relocations.ibm.icu.impl.coll;

import com.cobblemon.mod.relocations.ibm.icu.util.ICUCloneNotSupportedException;
import java.util.concurrent.atomic.AtomicInteger;

public class SharedObject implements Cloneable {
   private AtomicInteger refCount = new AtomicInteger();

   public SharedObject clone() {
      SharedObject c;
      try {
         c = (SharedObject)super.clone();
      } catch (CloneNotSupportedException var3) {
         throw new ICUCloneNotSupportedException(var3);
      }

      c.refCount = new AtomicInteger();
      return c;
   }

   public final void addRef() {
      this.refCount.incrementAndGet();
   }

   public final void removeRef() {
      this.refCount.decrementAndGet();
   }

   public final int getRefCount() {
      return this.refCount.get();
   }

   public final void deleteIfZeroRefCount() {
   }

   public static final class Reference<T extends SharedObject> implements Cloneable {
      private T ref;

      public Reference(T r) {
         this.ref = r;
         if (r != null) {
            r.addRef();
         }
      }

      public SharedObject.Reference<T> clone() {
         SharedObject.Reference<T> c;
         try {
            c = (SharedObject.Reference<T>)super.clone();
         } catch (CloneNotSupportedException var3) {
            throw new ICUCloneNotSupportedException(var3);
         }

         if (this.ref != null) {
            this.ref.addRef();
         }

         return c;
      }

      public T readOnly() {
         return this.ref;
      }

      public T copyOnWrite() {
         T r = this.ref;
         if (r.getRefCount() <= 1) {
            return r;
         } else {
            T r2 = (T)r.clone();
            r.removeRef();
            this.ref = r2;
            r2.addRef();
            return r2;
         }
      }

      public void clear() {
         if (this.ref != null) {
            this.ref.removeRef();
            this.ref = null;
         }
      }

      @Override
      protected void finalize() throws Throwable {
         super.finalize();
         this.clear();
      }
   }
}
