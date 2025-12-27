package com.oracle.truffle.api.profiles;

import com.oracle.truffle.api.CompilerDirectives;
import java.util.Objects;

public final class PrimitiveValueProfile extends ValueProfile {
   private static final PrimitiveValueProfile DISABLED;
   private static final Object UNINITIALIZED = new Object();
   private static final Object GENERIC = new Object();
   @CompilerDirectives.CompilationFinal
   private Object cachedValue = UNINITIALIZED;

   PrimitiveValueProfile() {
   }

   @Override
   public <T> T profile(T v) {
      Object snapshot = this.cachedValue;
      if (snapshot != GENERIC) {
         if (snapshot instanceof Byte) {
            if (v instanceof Byte && (Byte)snapshot == (Byte)v) {
               return (T)snapshot;
            }
         } else if (snapshot instanceof Short) {
            if (v instanceof Short && (Short)snapshot == (Short)v) {
               return (T)snapshot;
            }
         } else if (snapshot instanceof Integer) {
            if (v instanceof Integer && (Integer)snapshot == (Integer)v) {
               return (T)snapshot;
            }
         } else if (snapshot instanceof Long) {
            if (v instanceof Long && (Long)snapshot == (Long)v) {
               return (T)snapshot;
            }
         } else if (snapshot instanceof Float) {
            if (v instanceof Float && Float.floatToRawIntBits((Float)snapshot) == Float.floatToRawIntBits((Float)v)) {
               return (T)snapshot;
            }
         } else if (snapshot instanceof Double) {
            if (v instanceof Double && Double.doubleToRawLongBits((Double)snapshot) == Double.doubleToRawLongBits((Double)v)) {
               return (T)snapshot;
            }
         } else if (snapshot instanceof Boolean) {
            if (v instanceof Boolean && (Boolean)snapshot == (Boolean)v) {
               return (T)snapshot;
            }
         } else if (snapshot instanceof Character) {
            if (v instanceof Character && (Character)snapshot == (Character)v) {
               return (T)snapshot;
            }
         } else if (snapshot == v) {
            return (T)snapshot;
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.slowPath(v);
      }

      return v;
   }

   public byte profile(byte value) {
      Object snapshot = this.cachedValue;
      if (snapshot != GENERIC) {
         if (snapshot instanceof Byte && (Byte)snapshot == value) {
            return (Byte)snapshot;
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.slowPath(value);
      }

      return value;
   }

   public short profile(short value) {
      Object snapshot = this.cachedValue;
      if (snapshot != GENERIC) {
         if (snapshot instanceof Short && (Short)snapshot == value) {
            return (Short)snapshot;
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.slowPath(value);
      }

      return value;
   }

   public int profile(int value) {
      Object snapshot = this.cachedValue;
      if (snapshot != GENERIC) {
         if (snapshot instanceof Integer && (Integer)snapshot == value) {
            return (Integer)snapshot;
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.slowPath(value);
      }

      return value;
   }

   public long profile(long value) {
      Object snapshot = this.cachedValue;
      if (snapshot != GENERIC) {
         if (snapshot instanceof Long && (Long)snapshot == value) {
            return (Long)snapshot;
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.slowPath(value);
      }

      return value;
   }

   public float profile(float value) {
      Object snapshot = this.cachedValue;
      if (snapshot != GENERIC) {
         if (snapshot instanceof Float && Float.floatToRawIntBits((Float)snapshot) == Float.floatToRawIntBits(value)) {
            return (Float)snapshot;
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.slowPath(value);
      }

      return value;
   }

   public double profile(double value) {
      Object snapshot = this.cachedValue;
      if (snapshot != GENERIC) {
         if (snapshot instanceof Double && Double.doubleToRawLongBits((Double)snapshot) == Double.doubleToRawLongBits(value)) {
            return (Double)snapshot;
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.slowPath(value);
      }

      return value;
   }

   public boolean profile(boolean value) {
      Object snapshot = this.cachedValue;
      if (snapshot != GENERIC) {
         if (snapshot instanceof Boolean && (Boolean)snapshot == value) {
            return (Boolean)snapshot;
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.slowPath(value);
      }

      return value;
   }

   public char profile(char value) {
      Object snapshot = this.cachedValue;
      if (snapshot != GENERIC) {
         if (snapshot instanceof Character && (Character)snapshot == value) {
            return (Character)snapshot;
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.slowPath(value);
      }

      return value;
   }

   @Override
   public void disable() {
      this.cachedValue = GENERIC;
   }

   @Override
   public void reset() {
      if (this != DISABLED) {
         this.cachedValue = UNINITIALIZED;
      }
   }

   private void slowPath(Object value) {
      if (this.cachedValue == UNINITIALIZED) {
         this.cachedValue = value;
      } else {
         this.cachedValue = GENERIC;
      }
   }

   boolean isGeneric() {
      return this.cachedValue == GENERIC;
   }

   boolean isUninitialized() {
      return this.cachedValue == UNINITIALIZED;
   }

   Object getCachedValue() {
      return this.cachedValue;
   }

   @Override
   public String toString() {
      return this == DISABLED
         ? this.toStringDisabled(PrimitiveValueProfile.class)
         : this.toString(PrimitiveValueProfile.class, this.isUninitialized(), this.isGeneric(), this.formatSpecialization());
   }

   private String formatSpecialization() {
      if (!this.isUninitialized() && !this.isGeneric()) {
         Object snapshot = this.cachedValue;
         if (snapshot == null) {
            return String.format("value == null");
         } else if (!(snapshot instanceof Byte)
            && !(snapshot instanceof Short)
            && !(snapshot instanceof Integer)
            && !(snapshot instanceof Long)
            && !(snapshot instanceof Float)
            && !(snapshot instanceof Double)
            && !(snapshot instanceof Boolean)
            && !(snapshot instanceof Character)) {
            String simpleName = snapshot.getClass().getSimpleName();
            return String.format("value == %s@%x", simpleName, Objects.hash(snapshot));
         } else {
            return String.format("value == (%s)%s", snapshot.getClass().getSimpleName(), snapshot);
         }
      } else {
         return null;
      }
   }

   public static PrimitiveValueProfile createEqualityProfile() {
      return create();
   }

   public static PrimitiveValueProfile create() {
      return Profile.isProfilingEnabled() ? new PrimitiveValueProfile() : DISABLED;
   }

   public static PrimitiveValueProfile getUncached() {
      return DISABLED;
   }

   static {
      PrimitiveValueProfile profile = new PrimitiveValueProfile();
      profile.disable();
      DISABLED = profile;
   }
}
