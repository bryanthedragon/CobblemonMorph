package com.oracle.truffle.api.profiles;

import com.oracle.truffle.api.CompilerDirectives;
import java.util.Objects;

public abstract class ValueProfile extends Profile {
   ValueProfile() {
   }

   public abstract <T> T profile(T value);

   public static ValueProfile createClassProfile() {
      return Profile.isProfilingEnabled() ? ValueProfile.ExactClass.create() : ValueProfile.Disabled.INSTANCE;
   }

   public static ValueProfile createIdentityProfile() {
      return Profile.isProfilingEnabled() ? ValueProfile.Identity.create() : ValueProfile.Disabled.INSTANCE;
   }

   public static ValueProfile getUncached() {
      return ValueProfile.Disabled.INSTANCE;
   }

   static final class Disabled extends ValueProfile {
      static final ValueProfile INSTANCE = new ValueProfile.Disabled();

      @Override
      protected Object clone() {
         return INSTANCE;
      }

      @Override
      public <T> T profile(T value) {
         return value;
      }

      @Override
      public String toString() {
         return this.toStringDisabled(ValueProfile.class);
      }
   }

   static final class ExactClass extends ValueProfile {
      @CompilerDirectives.CompilationFinal
      protected Class<?> cachedClass;

      public static ValueProfile create() {
         return new ValueProfile.ExactClass();
      }

      @Override
      public <T> T profile(T value) {
         Class<?> clazz = this.cachedClass;
         if (clazz != Object.class) {
            if (clazz != null && CompilerDirectives.isExact(value, clazz)) {
               if (CompilerDirectives.inInterpreter()) {
                  return value;
               }

               return CompilerDirectives.castExact(value, (Class<T>)clazz);
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            if (clazz == null && value != null) {
               this.cachedClass = value.getClass();
            } else {
               this.cachedClass = Object.class;
            }
         }

         return value;
      }

      boolean isGeneric() {
         return this.cachedClass == Object.class;
      }

      boolean isUninitialized() {
         return this.cachedClass == null;
      }

      @Override
      public void disable() {
         this.cachedClass = Object.class;
      }

      @Override
      public void reset() {
         this.cachedClass = null;
      }

      Class<?> getCachedClass() {
         return this.cachedClass;
      }

      @Override
      public String toString() {
         return this.toString(
            ValueProfile.class,
            this.cachedClass == null,
            this.cachedClass == Object.class,
            String.format("value.getClass() == %s.class", this.cachedClass != null ? this.cachedClass.getSimpleName() : "null")
         );
      }
   }

   static final class Identity extends ValueProfile {
      private static final Object UNINITIALIZED = new Object();
      private static final Object GENERIC = new Object();
      @CompilerDirectives.CompilationFinal
      protected Object cachedValue = UNINITIALIZED;

      @Override
      public <T> T profile(T newValue) {
         Object cached = this.cachedValue;
         if (cached != GENERIC) {
            if (cached == newValue) {
               return (T)cached;
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            if (this.cachedValue == UNINITIALIZED) {
               this.cachedValue = newValue;
            } else {
               this.cachedValue = GENERIC;
            }
         }

         return newValue;
      }

      public boolean isGeneric() {
         return this.getCachedValue() == GENERIC;
      }

      public boolean isUninitialized() {
         return this.getCachedValue() == UNINITIALIZED;
      }

      public Object getCachedValue() {
         return this.cachedValue;
      }

      @Override
      public void disable() {
         this.cachedValue = GENERIC;
      }

      @Override
      public void reset() {
         this.cachedValue = UNINITIALIZED;
      }

      @Override
      public String toString() {
         return this.toString(
            ValueProfile.class,
            this.isUninitialized(),
            this.isGeneric(),
            String.format("value == %s@%x", this.cachedValue != null ? this.cachedValue.getClass().getSimpleName() : "null", Objects.hash(this.cachedValue))
         );
      }

      static ValueProfile create() {
         return new ValueProfile.Identity();
      }
   }
}
