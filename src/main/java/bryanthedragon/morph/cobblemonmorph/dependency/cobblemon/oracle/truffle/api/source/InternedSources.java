package com.oracle.truffle.api.source;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.concurrent.ConcurrentHashMap;

final class InternedSources {
   private final ConcurrentHashMap<SourceImpl.Key, InternedSources.WeakSourceRef> table = new ConcurrentHashMap<>();
   private final ReferenceQueue<SourceImpl> deadReferences = new ReferenceQueue<>();

   void add(SourceImpl source) {
      if (source.isCached()) {
         this.table.put(source.key, new InternedSources.WeakSourceRef(source, this.deadReferences));
      }
   }

   Source intern(SourceImpl.Key key) {
      this.cleanupStaleEntries();
      if (!key.cached) {
         return key.toSourceNotInterned();
      } else {
         InternedSources.WeakSourceRef sourceRef = this.table.get(key);
         SourceImpl source = sourceRef != null ? sourceRef.get() : null;
         if (source == null) {
            while (true) {
               source = key.toSourceInterned();
               sourceRef = new InternedSources.WeakSourceRef(source, this.deadReferences);
               InternedSources.WeakSourceRef oldSourceRef = this.table.putIfAbsent(key, sourceRef);
               if (oldSourceRef == null) {
                  assert source != null;
                  break;
               }

               SourceImpl otherSource = oldSourceRef.get();
               if (otherSource != null) {
                  assert otherSource != source;

                  return otherSource;
               }

               boolean replaced = this.table.replace(key, oldSourceRef, sourceRef);
               if (replaced) {
                  return source;
               }
            }
         }

         return source;
      }
   }

   void resetNativeImageState() {
      this.table.clear();
   }

   private void cleanupStaleEntries() {
      InternedSources.WeakSourceRef sourceRef = null;

      while ((sourceRef = (InternedSources.WeakSourceRef)this.deadReferences.poll()) != null) {
         this.table.remove(sourceRef.key, sourceRef);
      }
   }

   private static class WeakSourceRef extends WeakReference<SourceImpl> {
      final SourceImpl.Key key;

      WeakSourceRef(SourceImpl referent, ReferenceQueue<SourceImpl> q) {
         super(referent, q);
         this.key = referent.toKey();
      }
   }
}
