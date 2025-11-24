
package com.oracle.truffle.api.source;

import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.api.source.SourceImpl;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.concurrent.ConcurrentHashMap;

final class InternedSources {
    private final ConcurrentHashMap<SourceImpl.Key, WeakSourceRef> table = new ConcurrentHashMap();
    private final ReferenceQueue<SourceImpl> deadReferences = new ReferenceQueue();

    InternedSources() {
    }

    void add(SourceImpl source) {
        if (!source.isCached()) {
            return;
        }
        this.table.put(source.key, new WeakSourceRef(source, this.deadReferences));
    }

    Source intern(SourceImpl.Key key) {
        SourceImpl source;
        this.cleanupStaleEntries();
        if (!key.cached) {
            return key.toSourceNotInterned();
        }
        WeakSourceRef sourceRef = this.table.get(key);
        SourceImpl sourceImpl = source = sourceRef != null ? (SourceImpl)sourceRef.get() : null;
        if (source == null) {
            WeakSourceRef oldSourceRef;
            while ((oldSourceRef = this.table.putIfAbsent(key, sourceRef = new WeakSourceRef(source = key.toSourceInterned(), this.deadReferences))) != null) {
                SourceImpl otherSource = (SourceImpl)oldSourceRef.get();
                if (otherSource == null) {
                    boolean replaced = this.table.replace(key, oldSourceRef, sourceRef);
                    if (!replaced) continue;
                    return source;
                }
                assert (otherSource != source);
                return otherSource;
            }
            assert (source != null);
        }
        return source;
    }

    void resetNativeImageState() {
        this.table.clear();
    }

    private void cleanupStaleEntries() {
        WeakSourceRef sourceRef = null;
        while ((sourceRef = (WeakSourceRef)this.deadReferences.poll()) != null) {
            this.table.remove(sourceRef.key, sourceRef);
        }
    }

    private static class WeakSourceRef
    extends WeakReference<SourceImpl> {
        final SourceImpl.Key key;

        WeakSourceRef(SourceImpl referent, ReferenceQueue<SourceImpl> q) {
            super(referent, q);
            this.key = referent.toKey();
        }
    }
}

