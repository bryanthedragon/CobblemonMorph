package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.Assumption;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.utilities.TruffleWeakReference;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

final class WeakAssumedValue<T> {
   private static final AtomicReferenceFieldUpdater<WeakAssumedValue, WeakAssumedValue.Profile> PROFILE_UPDATER = AtomicReferenceFieldUpdater.newUpdater(
      WeakAssumedValue.class, WeakAssumedValue.Profile.class, "profile"
   );
   private static final Assumption INVALID_ASSUMPTION;
   @CompilerDirectives.CompilationFinal
   private volatile WeakAssumedValue.Profile<T> profile;
   private final String name;

   WeakAssumedValue(String name) {
      this.name = name;
   }

   public void invalidate() {
      this.invalidateImpl(this.profile);
   }

   private void invalidateImpl(WeakAssumedValue.Profile<T> currentProfile) {
      if (currentProfile != null) {
         currentProfile.assumption.invalidate();
      }

      WeakAssumedValue.Profile<?> previous = PROFILE_UPDATER.getAndSet(this, WeakAssumedValue.Profile.INVALID);

      assert previous == currentProfile || previous == WeakAssumedValue.Profile.INVALID;
   }

   public boolean isValid() {
      WeakAssumedValue.Profile<T> p = this.profile;
      return p == null ? false : p.assumption.isValid();
   }

   public void reset() {
      this.invalidateImpl(this.profile);
      this.profile = null;
   }

   public T getConstant() {
      if (CompilerDirectives.inCompiledCode() && CompilerDirectives.isPartialEvaluationConstant(this)) {
         WeakAssumedValue.Profile<T> p = this.profile;
         if (p == null) {
            return null;
         } else {
            return p.assumption.isValid() ? p.get() : null;
         }
      } else {
         return null;
      }
   }

   @CompilerDirectives.TruffleBoundary
   public void update(T newValue) {
      assert newValue != null;

      WeakAssumedValue.Profile<T> currentProfile = this.profile;
      if (currentProfile != WeakAssumedValue.Profile.INVALID) {
         if (currentProfile == null) {
            WeakAssumedValue.Profile<T> newProfile = new WeakAssumedValue.Profile<>(newValue, this.name);
            if (!PROFILE_UPDATER.compareAndSet(this, currentProfile, newProfile)) {
               this.update(newValue);
            }
         } else {
            if (currentProfile.get() == newValue) {
               return;
            }

            this.invalidateImpl(currentProfile);
         }
      }
   }

   static {
      Assumption assumption = Truffle.getRuntime().createAssumption();
      assumption.invalidate();
      INVALID_ASSUMPTION = assumption;
   }

   static final class Profile<V> {
      private static final WeakAssumedValue.Profile<?> INVALID = new WeakAssumedValue.Profile();
      final Assumption assumption;
      final TruffleWeakReference<V> reference;

      private Profile() {
         this.assumption = WeakAssumedValue.INVALID_ASSUMPTION;
         this.reference = null;
      }

      private Profile(V value, String name) {
         assert value != null;

         this.assumption = Truffle.getRuntime().createAssumption(name);
         this.reference = new TruffleWeakReference<>(value);
      }

      V get() {
         TruffleWeakReference<V> ref = this.reference;
         return ref == null ? null : ref.get();
      }
   }
}
