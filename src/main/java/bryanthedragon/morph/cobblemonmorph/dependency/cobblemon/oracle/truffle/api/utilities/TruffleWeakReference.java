package com.oracle.truffle.api.utilities;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

public final class TruffleWeakReference<T> extends WeakReference<T> {
   public TruffleWeakReference(T t) {
      super(t);
   }

   public TruffleWeakReference(T referent, ReferenceQueue<? super T> q) {
      super(referent, q);
   }
}
