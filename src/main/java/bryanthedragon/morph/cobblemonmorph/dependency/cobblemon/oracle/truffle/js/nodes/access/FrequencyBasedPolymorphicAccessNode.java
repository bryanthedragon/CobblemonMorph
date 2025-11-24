
package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.access.PropertyCacheNode;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.access.PropertySetNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.objects.JSAttributes;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;

abstract class FrequencyBasedPolymorphicAccessNode<T extends PropertyCacheNode<?>>
extends JavaScriptBaseNode {
    private static final int MIN_CACHING_PERC = 10;
    private static final int MAX_DISTRIBUTION_MAP_SIZE = 1024;
    private static final int MIN_KEYS_ACCESSES = 100;
    private static final int SAMPLE_EVERY = 10;
    private static final int IC_GET_MAX_SIZE = 5;
    private static final int IC_SET_MAX_SIZE = 3;
    protected final JSContext context;
    private int totalHits;
    private final int[] topHits;
    private Map<Object, HitsCount> hitsDistributionMap = new HashMap<Object, HitsCount>();

    public static FrequencyBasedPropertyGetNode createFrequencyBasedPropertyGet(JSContext context) {
        return FrequencyBasedPropertyGetNode.create(context);
    }

    public static FrequencyBasedPropertySetNode createFrequencyBasedPropertySet(JSContext context, boolean setOwn, boolean strict, boolean superProperty) {
        return FrequencyBasedPropertySetNode.create(context, setOwn, strict, superProperty);
    }

    private FrequencyBasedPolymorphicAccessNode(JSContext context, int size) {
        this.context = context;
        this.topHits = new int[size];
    }

    protected abstract T[] getHighFrequencyNodes();

    protected abstract void setHighFrequencyNode(int var1, Object var2);

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void interpreterSample(Object key) {
        Lock lock = this.getLock();
        lock.lock();
        try {
            int i;
            int hits;
            CompilerAsserts.neverPartOfCompilation();
            assert (JSRuntime.isPropertyKey(key));
            if (this.hitsDistributionMap == null) {
                return;
            }
            if (this.hitsDistributionMap.size() >= 1024) {
                this.hitsDistributionMap = null;
                return;
            }
            ++this.totalHits;
            HitsCount hitsCounter = this.hitsDistributionMap.get(key);
            if (hitsCounter == null) {
                hitsCounter = new HitsCount();
                this.hitsDistributionMap.put(key, hitsCounter);
            }
            if ((hits = hitsCounter.incrementAndGet()) % 10 != 0 || this.totalHits < 100) {
                return;
            }
            for (i = 0; i < this.topHits.length; ++i) {
                PropertyCacheNode[] highFrequencyNodes = this.getHighFrequencyNodes();
                if (hits <= this.topHits[i]) continue;
                if (highFrequencyNodes[i] == null) {
                    this.setHighFrequencyNode(i, key);
                    this.topHits[i] = hits;
                    break;
                }
                if (highFrequencyNodes[i].getKey().equals(key)) {
                    this.topHits[i] = hits;
                    break;
                }
                for (int j = this.topHits.length - 1; j > i; --j) {
                    highFrequencyNodes[j] = highFrequencyNodes[j - 1];
                    this.topHits[j] = this.topHits[j - 1];
                }
                this.setHighFrequencyNode(i, key);
                this.topHits[i] = hits;
                break;
            }
            for (i = 0; i < this.topHits.length; ++i) {
                if (this.topHits[i] == 0) {
                    break;
                }
                int perc = this.percentage(this.topHits[i]);
                if (perc >= 10) continue;
                this.topHits[i] = 0;
                this.getHighFrequencyNodes()[i] = null;
            }
        }
        finally {
            lock.unlock();
        }
    }

    private int percentage(int hits) {
        return (int)((float)hits / (float)this.totalHits * 100.0f);
    }

    public static final class FrequencyBasedPropertyGetNode
    extends FrequencyBasedPolymorphicAccessNode<PropertyGetNode> {
        @Node.Children
        private PropertyGetNode[] highFrequencyKeys = new PropertyGetNode[5];

        public static FrequencyBasedPropertyGetNode create(JSContext context) {
            return new FrequencyBasedPropertyGetNode(context);
        }

        private FrequencyBasedPropertyGetNode(JSContext context) {
            super(context, 5);
        }

        protected PropertyGetNode[] getHighFrequencyNodes() {
            return this.highFrequencyKeys;
        }

        @Override
        protected void setHighFrequencyNode(int position, Object key) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            assert (JSRuntime.isPropertyKey(key));
            this.highFrequencyKeys[position] = this.insert(PropertyGetNode.create(key, this.context));
        }

        public Object executeFastGet(Object key, Object target, TruffleString.EqualNode equalsNode) {
            if (CompilerDirectives.inInterpreter()) {
                this.interpreterSample(key);
            }
            return this.readFromCaches(key, target, equalsNode);
        }

        @ExplodeLoop(kind=ExplodeLoop.LoopExplosionKind.FULL_UNROLL_UNTIL_RETURN)
        private Object readFromCaches(Object key, Object target, TruffleString.EqualNode equalsNode) {
            for (PropertyGetNode highFrequencyKey : this.highFrequencyKeys) {
                if (highFrequencyKey == null || !JSRuntime.propertyKeyEquals(equalsNode, highFrequencyKey.getKey(), key)) continue;
                return highFrequencyKey.getValueOrDefault(target, null);
            }
            return null;
        }
    }

    public static final class FrequencyBasedPropertySetNode
    extends FrequencyBasedPolymorphicAccessNode<PropertySetNode> {
        @Node.Children
        private PropertySetNode[] highFrequencyKeys;
        protected final boolean setOwn;
        protected final boolean strict;
        protected final boolean superProperty;

        public static FrequencyBasedPropertySetNode create(JSContext context, boolean setOwn, boolean isStrict, boolean superProperty) {
            return new FrequencyBasedPropertySetNode(context, setOwn, isStrict, superProperty);
        }

        private FrequencyBasedPropertySetNode(JSContext context, boolean setOwn, boolean isStrict, boolean superProperty) {
            super(context, 3);
            this.setOwn = setOwn;
            this.strict = isStrict;
            this.superProperty = superProperty;
            this.highFrequencyKeys = new PropertySetNode[3];
        }

        protected PropertySetNode[] getHighFrequencyNodes() {
            return this.highFrequencyKeys;
        }

        @Override
        protected void setHighFrequencyNode(int position, Object key) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            assert (JSRuntime.isPropertyKey(key));
            this.highFrequencyKeys[position] = this.insert(PropertySetNode.createImpl(key, false, this.context, this.strict, this.setOwn, JSAttributes.getDefault(), false, this.superProperty));
        }

        public boolean executeFastSet(JSDynamicObject target, Object key, Object value2, Object receiver, TruffleString.EqualNode equalsNode) {
            if (this.setOwn) {
                return false;
            }
            assert (JSRuntime.isPropertyKey(key));
            if (CompilerDirectives.inInterpreter()) {
                this.interpreterSample(key);
            }
            return this.compiledSet(target, key, value2, receiver, equalsNode);
        }

        @ExplodeLoop(kind=ExplodeLoop.LoopExplosionKind.FULL_UNROLL_UNTIL_RETURN)
        private boolean compiledSet(JSDynamicObject target, Object key, Object value2, Object receiver, TruffleString.EqualNode equalsNode) {
            for (PropertySetNode highFrequencyKey : this.highFrequencyKeys) {
                if (highFrequencyKey == null || !JSRuntime.propertyKeyEquals(equalsNode, highFrequencyKey.getKey(), key)) continue;
                highFrequencyKey.setValue(target, value2, receiver);
                return true;
            }
            return false;
        }
    }

    private static class HitsCount {
        private int hits = 0;

        HitsCount() {
        }

        public int incrementAndGet() {
            return ++this.hits;
        }
    }
}

