
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.relocations.ibm.icu.impl;

import com.cobblemon.mod.relocations.ibm.icu.impl.CacheBase;
import com.cobblemon.mod.relocations.ibm.icu.impl.CacheValue;
import java.util.concurrent.ConcurrentHashMap;

public abstract class SoftCache<K, V, D>
extends CacheBase<K, V, D> {
    private ConcurrentHashMap<K, Object> map = new ConcurrentHashMap();

    @Override
    public final V getInstance(K key, D data) {
        CacheValue mapValue = this.map.get(key);
        if (mapValue != null) {
            if (!(mapValue instanceof CacheValue)) {
                return (V)mapValue;
            }
            CacheValue cv = mapValue;
            if (cv.isNull()) {
                return null;
            }
            Object value2 = cv.get();
            if (value2 != null) {
                return value2;
            }
            value2 = this.createInstance(key, data);
            return cv.resetIfCleared(value2);
        }
        Object value3 = this.createInstance(key, data);
        mapValue = value3 != null && CacheValue.futureInstancesWillBeStrong() ? value3 : CacheValue.getInstance(value3);
        if ((mapValue = this.map.putIfAbsent(key, mapValue)) == null) {
            return value3;
        }
        if (!(mapValue instanceof CacheValue)) {
            return (V)mapValue;
        }
        CacheValue cv = mapValue;
        return cv.resetIfCleared(value3);
    }
}

