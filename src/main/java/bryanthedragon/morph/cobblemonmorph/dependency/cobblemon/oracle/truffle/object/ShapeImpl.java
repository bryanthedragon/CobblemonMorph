
package com.oracle.truffle.object;

import com.oracle.truffle.api.Assumption;
import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.object.DynamicObject;
import com.oracle.truffle.api.object.DynamicObjectFactory;
import com.oracle.truffle.api.object.HiddenKey;
import com.oracle.truffle.api.object.Layout;
import com.oracle.truffle.api.object.Location;
import com.oracle.truffle.api.object.LocationFactory;
import com.oracle.truffle.api.object.ObjectType;
import com.oracle.truffle.api.object.Property;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.object.Debug;
import com.oracle.truffle.object.DebugCounter;
import com.oracle.truffle.object.DefaultLayout;
import com.oracle.truffle.object.LayoutImpl;
import com.oracle.truffle.object.LayoutStrategy;
import com.oracle.truffle.object.LocationImpl;
import com.oracle.truffle.object.ObjectStorageOptions;
import com.oracle.truffle.object.PropertyMap;
import com.oracle.truffle.object.StrongKeyWeakValueEntry;
import com.oracle.truffle.object.Transition;
import com.oracle.truffle.object.TransitionMap;
import com.oracle.truffle.object.WeakKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import org.graalvm.collections.EconomicMap;

public abstract class ShapeImpl
extends Shape {
    protected final int flags;
    protected final LayoutImpl layout;
    protected final Object objectType;
    protected final ShapeImpl parent;
    protected final PropertyMap propertyMap;
    protected final Object sharedData;
    private final ShapeImpl root;
    protected final int objectArraySize;
    protected final int objectArrayCapacity;
    protected final int objectFieldSize;
    protected final int primitiveFieldSize;
    protected final int primitiveArraySize;
    protected final int primitiveArrayCapacity;
    protected final int depth;
    protected final int propertyCount;
    protected final Assumption validAssumption;
    @CompilerDirectives.CompilationFinal
    protected volatile Assumption leafAssumption;
    private volatile Object transitionMap;
    private final Transition transitionFromParent;
    private volatile PropertyAssumptions sharedPropertyAssumptions;
    private static final AtomicReferenceFieldUpdater<ShapeImpl, Object> TRANSITION_MAP_UPDATER = AtomicReferenceFieldUpdater.newUpdater(ShapeImpl.class, Object.class, "transitionMap");
    private static final AtomicReferenceFieldUpdater<ShapeImpl, Assumption> LEAF_ASSUMPTION_UPDATER = AtomicReferenceFieldUpdater.newUpdater(ShapeImpl.class, Assumption.class, "leafAssumption");
    private static final AtomicReferenceFieldUpdater<ShapeImpl, PropertyAssumptions> PROPERTY_ASSUMPTIONS_UPDATER = AtomicReferenceFieldUpdater.newUpdater(ShapeImpl.class, PropertyAssumptions.class, "sharedPropertyAssumptions");
    protected static final int FLAG_SHARED_SHAPE = 65536;
    protected static final int FLAG_ALLOW_PROPERTY_ASSUMPTIONS = 131072;
    protected static final int FLAG_HAS_INSTANCE_PROPERTIES = 262144;
    protected static final int OBJECT_FLAGS_MASK = 255;
    protected static final int OBJECT_FLAGS_SHIFT = 0;
    private static final DebugCounter shapeCount = DebugCounter.create("Shapes allocated total");
    private static final DebugCounter shapeCloneCount = DebugCounter.create("Shapes allocated cloned");
    private static final DebugCounter shapeCacheHitCount = DebugCounter.create("Shape cache hits");
    private static final DebugCounter shapeCacheMissCount = DebugCounter.create("Shape cache misses");
    static final DebugCounter shapeCacheExpunged = DebugCounter.create("Shape cache expunged");
    static final DebugCounter shapeCacheWeakKeys = DebugCounter.create("Shape cache weak keys");
    static final DebugCounter propertyAssumptionsCreated = DebugCounter.create("Property assumptions created");
    static final DebugCounter propertyAssumptionsRemoved = DebugCounter.create("Property assumptions removed");

    private ShapeImpl(Layout layout, ShapeImpl parent, Object objectType, Object sharedData, PropertyMap propertyMap, Transition transitionFromParent, int objectArraySize, int objectFieldSize, int primitiveFieldSize, int primitiveArraySize, int flags, Assumption singleContextAssumption) {
        this.layout = (LayoutImpl)layout;
        this.objectType = Objects.requireNonNull(objectType);
        this.propertyMap = Objects.requireNonNull(propertyMap);
        this.root = parent != null ? parent.getRoot() : this;
        this.parent = parent;
        this.objectArraySize = objectArraySize;
        this.objectArrayCapacity = ShapeImpl.capacityFromSize(objectArraySize);
        this.objectFieldSize = objectFieldSize;
        this.primitiveFieldSize = primitiveFieldSize;
        this.primitiveArraySize = primitiveArraySize;
        this.primitiveArrayCapacity = ShapeImpl.capacityFromSize(primitiveArraySize);
        if (parent != null) {
            this.propertyCount = ShapeImpl.makePropertyCount(parent, propertyMap, transitionFromParent);
            this.depth = parent.depth + 1;
        } else {
            this.propertyCount = 0;
            this.depth = 0;
        }
        this.validAssumption = ShapeImpl.createValidAssumption();
        int allFlags = flags;
        if ((allFlags & 0x40000) == 0 && (objectFieldSize != 0 || objectArraySize != 0 || primitiveFieldSize != 0 || primitiveArraySize != 0)) {
            allFlags |= 0x40000;
        }
        this.flags = allFlags;
        this.transitionFromParent = transitionFromParent;
        this.sharedData = sharedData;
        assert (parent == null || this.sharedData == parent.sharedData);
        this.sharedPropertyAssumptions = parent == null && (flags & 0x20000) != 0 && singleContextAssumption != null ? new PropertyAssumptions(singleContextAssumption) : null;
        shapeCount.inc();
        if (ObjectStorageOptions.DumpShapes) {
            Debug.trackShape(this);
        }
    }

    protected ShapeImpl(Layout layout, ShapeImpl parent, Object objectType, Object sharedData, PropertyMap propertyMap, Transition transition, Shape.Allocator allocator, int flags) {
        this(layout, parent, objectType, sharedData, propertyMap, transition, ((BaseAllocator)allocator).objectArraySize, ((BaseAllocator)allocator).objectFieldSize, ((BaseAllocator)allocator).primitiveFieldSize, ((BaseAllocator)allocator).primitiveArraySize, flags, null);
    }

    protected abstract ShapeImpl createShape(Layout var1, Object var2, ShapeImpl var3, Object var4, PropertyMap var5, Transition var6, Shape.Allocator var7, int var8);

    protected ShapeImpl(Layout layout, Object dynamicType, Object sharedData, int flags, Assumption constantObjectAssumption) {
        this(layout, null, dynamicType, sharedData, PropertyMap.empty(), null, 0, 0, 0, 0, flags, constantObjectAssumption);
    }

    private static int makePropertyCount(ShapeImpl parent, PropertyMap propertyMap, Transition transitionFromParent) {
        int parentSize;
        int thisSize = propertyMap.size();
        if (thisSize > (parentSize = parent.propertyMap.size())) {
            Property lastProperty = propertyMap.getLastProperty();
            if (!lastProperty.isHidden()) {
                return parent.propertyCount + 1;
            }
        } else if (thisSize < parentSize && transitionFromParent instanceof Transition.RemovePropertyTransition && !(((Transition.RemovePropertyTransition)transitionFromParent).getPropertyKey() instanceof HiddenKey)) {
            return parent.propertyCount - 1;
        }
        return parent.propertyCount;
    }

    @Override
    public final Property getLastProperty() {
        return this.propertyMap.getLastProperty();
    }

    @Override
    public final int getFlags() {
        return ShapeImpl.getObjectFlags(this.flags);
    }

    public final int getFlagsInternal() {
        return this.flags;
    }

    private static int capacityFromSize(int size) {
        if (size == 0) {
            return 0;
        }
        if (size <= 4) {
            return 4;
        }
        if (size <= 8) {
            return 8;
        }
        int hi = Integer.highestOneBit(size);
        int cap = hi;
        if (cap < size && (cap = hi + (hi >>> 1)) < size && (cap = hi << 1) < size) {
            cap = size;
        }
        return cap;
    }

    public final int getObjectArraySize() {
        return this.objectArraySize;
    }

    public final int getObjectFieldSize() {
        return this.objectFieldSize;
    }

    public final int getPrimitiveFieldSize() {
        return this.primitiveFieldSize;
    }

    public final int getObjectArrayCapacity() {
        return this.objectArrayCapacity;
    }

    public final int getPrimitiveArrayCapacity() {
        return this.primitiveArrayCapacity;
    }

    public final int getPrimitiveArraySize() {
        return this.primitiveArraySize;
    }

    public final boolean hasPrimitiveArray() {
        return this.getLayout().hasPrimitiveExtensionArray();
    }

    @Override
    protected boolean hasInstanceProperties() {
        return (this.flags & 0x40000) != 0;
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public Property getProperty(Object key) {
        return (Property)this.propertyMap.get(key);
    }

    public final PropertyMap getPropertyMap() {
        return this.propertyMap;
    }

    public final void addDirectTransition(Transition transition, ShapeImpl next) {
        assert (next.getParent() == this && transition.isDirect());
        this.addTransitionInternal(transition, next);
    }

    public final void addIndirectTransition(Transition transition, ShapeImpl next) {
        assert (!this.isShared());
        assert (next.getParent() != this && !transition.isDirect());
        this.addTransitionInternal(transition, next);
    }

    private void addTransitionInternal(Transition transition, ShapeImpl successor) {
        Object next;
        Object prev;
        CompilerAsserts.neverPartOfCompilation();
        do {
            if ((prev = TRANSITION_MAP_UPDATER.get(this)) == null) {
                this.invalidateLeafAssumption();
                next = ShapeImpl.newSingleEntry(transition, successor);
                continue;
            }
            if (ShapeImpl.isSingleEntry(prev)) {
                Transition exTra;
                StrongKeyWeakValueEntry<Object, ShapeImpl> entry = ShapeImpl.asSingleEntry(prev);
                ShapeImpl exSucc = entry.getValue();
                if (exSucc != null && (exTra = ShapeImpl.unwrapKey(entry.getKey())) != null) {
                    next = ShapeImpl.newTransitionMap(exTra, exSucc, transition, successor);
                    continue;
                }
                next = ShapeImpl.newSingleEntry(transition, successor);
                continue;
            }
            next = ShapeImpl.addToTransitionMap(transition, successor, ShapeImpl.asTransitionMap(prev));
        } while (prev != next && !TRANSITION_MAP_UPDATER.compareAndSet(this, prev, next));
    }

    private static Object newTransitionMap(Transition firstTransition, ShapeImpl firstShape, Transition secondTransition, ShapeImpl secondShape) {
        TransitionMap<Transition, ShapeImpl> map = ShapeImpl.newTransitionMap();
        ShapeImpl.addToTransitionMap(firstTransition, firstShape, map);
        ShapeImpl.addToTransitionMap(secondTransition, secondShape, map);
        return map;
    }

    private static Object addToTransitionMap(Transition transition, ShapeImpl successor, TransitionMap<Transition, ShapeImpl> map) {
        if (transition.hasConstantLocation()) {
            map.putWeakKey(transition, successor);
        } else {
            map.put(transition, successor);
        }
        return map;
    }

    private static TransitionMap<Transition, ShapeImpl> newTransitionMap() {
        return new TransitionMap<Transition, ShapeImpl>();
    }

    private static Transition unwrapKey(Object key) {
        if (key instanceof WeakKey) {
            return (Transition)((WeakKey)key).get();
        }
        return (Transition)key;
    }

    private static TransitionMap<Transition, ShapeImpl> asTransitionMap(Object map) {
        return (TransitionMap)map;
    }

    private static boolean isTransitionMap(Object trans) {
        return trans instanceof TransitionMap;
    }

    private static Object newSingleEntry(Transition transition, ShapeImpl successor) {
        Object key = transition;
        if (transition.hasConstantLocation()) {
            key = new WeakKey<Transition>(transition);
        }
        return new StrongKeyWeakValueEntry<Transition, ShapeImpl>((Transition)key, successor);
    }

    private static boolean isSingleEntry(Object trans) {
        return trans instanceof StrongKeyWeakValueEntry;
    }

    private static StrongKeyWeakValueEntry<Object, ShapeImpl> asSingleEntry(Object trans) {
        return (StrongKeyWeakValueEntry)trans;
    }

    @Deprecated
    public final Map<Transition, ShapeImpl> getTransitionMapForRead() {
        final HashMap<Transition, ShapeImpl> snapshot = new HashMap<Transition, ShapeImpl>();
        this.forEachTransition(new BiConsumer<Transition, ShapeImpl>(){

            @Override
            public void accept(Transition t, ShapeImpl s) {
                snapshot.put(t, s);
            }
        });
        return snapshot;
    }

    public final void forEachTransition(BiConsumer<Transition, ShapeImpl> consumer) {
        Object trans = this.transitionMap;
        if (trans == null) {
            return;
        }
        if (ShapeImpl.isSingleEntry(trans)) {
            Transition key;
            StrongKeyWeakValueEntry<Object, ShapeImpl> entry = ShapeImpl.asSingleEntry(trans);
            ShapeImpl shape = entry.getValue();
            if (shape != null && (key = ShapeImpl.unwrapKey(entry.getKey())) != null) {
                consumer.accept(key, shape);
            }
        } else {
            assert (ShapeImpl.isTransitionMap(trans));
            TransitionMap<Transition, ShapeImpl> map = ShapeImpl.asTransitionMap(trans);
            map.forEach(consumer);
        }
    }

    private ShapeImpl queryTransitionImpl(Transition transition) {
        Object trans = this.transitionMap;
        if (trans == null) {
            return null;
        }
        if (ShapeImpl.isSingleEntry(trans)) {
            Transition key;
            StrongKeyWeakValueEntry<Object, ShapeImpl> entry = ShapeImpl.asSingleEntry(trans);
            ShapeImpl shape = entry.getValue();
            if (shape != null && (key = ShapeImpl.unwrapKey(entry.getKey())) != null && transition.equals(key)) {
                return shape;
            }
            return null;
        }
        assert (ShapeImpl.isTransitionMap(trans));
        TransitionMap<Transition, ShapeImpl> map = ShapeImpl.asTransitionMap(trans);
        return map.get(transition);
    }

    public final ShapeImpl queryTransition(Transition transition) {
        ShapeImpl cachedShape = this.queryTransitionImpl(transition);
        if (cachedShape != null) {
            shapeCacheHitCount.inc();
            return cachedShape;
        }
        shapeCacheMissCount.inc();
        return null;
    }

    public final <R> R iterateTransitions(BiFunction<Transition, ShapeImpl, R> consumer) {
        Object trans = this.transitionMap;
        if (trans == null) {
            return null;
        }
        if (ShapeImpl.isSingleEntry(trans)) {
            Transition key;
            StrongKeyWeakValueEntry<Object, ShapeImpl> entry = ShapeImpl.asSingleEntry(trans);
            ShapeImpl shape = entry.getValue();
            if (shape != null && (key = ShapeImpl.unwrapKey(entry.getKey())) != null) {
                return consumer.apply(key, shape);
            }
            return null;
        }
        assert (ShapeImpl.isTransitionMap(trans));
        TransitionMap<Transition, ShapeImpl> map = ShapeImpl.asTransitionMap(trans);
        return map.iterateEntries(consumer);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public ShapeImpl addProperty(Property property) {
        return this.getLayoutStrategy().addProperty(this, property);
    }

    @CompilerDirectives.TruffleBoundary
    protected void onPropertyTransition(Property property) {
        this.onPropertyTransitionWithKey(property.getKey());
    }

    final void onPropertyTransitionWithKey(Object propertyKey) {
        PropertyAssumptions propertyAssumptions;
        if (this.allowPropertyAssumptions() && (propertyAssumptions = this.getPropertyAssumptions()) != null) {
            propertyAssumptions.invalidatePropertyAssumption(propertyKey);
        }
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public ShapeImpl defineProperty(Object key, Object value2, int propertyFlags) {
        return this.getLayoutStrategy().defineProperty(this, key, value2, propertyFlags, null);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public ShapeImpl defineProperty(Object key, Object value2, int propertyFlags, LocationFactory locationFactory) {
        return this.getLayoutStrategy().defineProperty(this, key, value2, propertyFlags, locationFactory);
    }

    protected ShapeImpl cloneRoot(ShapeImpl from, Object newSharedData) {
        return this.createShape(from.layout, newSharedData, null, from.objectType, from.propertyMap, null, from.allocator(), from.flags);
    }

    protected final ShapeImpl cloneOnto(ShapeImpl newParent) {
        ShapeImpl from = this;
        ShapeImpl newShape = this.createShape(newParent.layout, newParent.sharedData, newParent, from.objectType, from.propertyMap, from.transitionFromParent, from.allocator(), newParent.flags);
        shapeCloneCount.inc();
        newParent.addDirectTransition(from.transitionFromParent, newShape);
        return newShape;
    }

    public final Transition getTransitionFromParent() {
        return this.transitionFromParent;
    }

    protected static ShapeImpl makeShapeWithAddedProperty(ShapeImpl parent, Transition.AddPropertyTransition addTransition) {
        Property addend = addTransition.getProperty();
        BaseAllocator allocator = parent.allocator().addLocation(addend.getLocation());
        PropertyMap newPropertyMap = parent.propertyMap.putCopy(addend);
        ShapeImpl newShape = parent.createShape(parent.layout, parent.sharedData, parent, parent.objectType, newPropertyMap, addTransition, allocator, parent.flags);
        assert (newShape.hasPrimitiveArray() || ((LocationImpl)addend.getLocation()).primitiveArrayCount() == 0);
        assert (newShape.depth == allocator.depth);
        return newShape;
    }

    public boolean isRelated(Shape other) {
        if (this == other) {
            return true;
        }
        return this.getRoot() == this.getRoot();
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public final List<Property> getPropertyList() {
        return Arrays.asList(this.getPropertyArray());
    }

    @CompilerDirectives.TruffleBoundary
    public final Property[] getPropertyArray() {
        Property[] props = new Property[this.getPropertyCount()];
        int i = props.length;
        Iterator<Property> it = this.propertyMap.reverseOrderedValueIterator();
        while (it.hasNext()) {
            Property currentProperty = it.next();
            if (currentProperty.isHidden()) continue;
            props[--i] = currentProperty;
        }
        return props;
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public final List<Property> getPropertyListInternal(boolean ascending) {
        Property[] props = new Property[this.propertyMap.size()];
        int i = ascending ? props.length : 0;
        Iterator<Property> it = this.propertyMap.reverseOrderedValueIterator();
        while (it.hasNext()) {
            Property current = it.next();
            if (ascending) {
                props[--i] = current;
                continue;
            }
            props[i++] = current;
        }
        return Arrays.asList(props);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public final List<Object> getKeyList() {
        return Arrays.asList(this.getKeyArray());
    }

    @CompilerDirectives.TruffleBoundary
    public final Object[] getKeyArray() {
        Object[] props = new Object[this.getPropertyCount()];
        int i = props.length;
        Iterator<Property> it = this.propertyMap.reverseOrderedValueIterator();
        while (it.hasNext()) {
            Property currentProperty = it.next();
            if (currentProperty.isHidden()) continue;
            props[--i] = currentProperty.getKey();
        }
        return props;
    }

    @Override
    public Iterable<Object> getKeys() {
        return this.getKeyList();
    }

    @Override
    public final boolean isValid() {
        return this.getValidAssumption().isValid();
    }

    @Override
    public final Assumption getValidAssumption() {
        return this.validAssumption;
    }

    private static Assumption createValidAssumption() {
        return Truffle.getRuntime().createAssumption("valid shape");
    }

    public final void invalidateValidAssumption() {
        this.getValidAssumption().invalidate();
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public final boolean isLeaf() {
        Assumption assumption = this.leafAssumption;
        return assumption == null || assumption.isValid();
    }

    @Override
    public final Assumption getLeafAssumption() {
        boolean isLeafShape;
        Assumption next;
        Assumption prev;
        Assumption assumption = this.leafAssumption;
        if (assumption != null) {
            return assumption;
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        do {
            if ((prev = LEAF_ASSUMPTION_UPDATER.get(this)) == null) continue;
            return prev;
        } while (!LEAF_ASSUMPTION_UPDATER.compareAndSet(this, prev, next = (isLeafShape = this.transitionMap == null) ? ShapeImpl.createLeafAssumption() : Assumption.NEVER_VALID));
        return next;
    }

    private static Assumption createLeafAssumption() {
        return Truffle.getRuntime().createAssumption("leaf shape");
    }

    @CompilerDirectives.TruffleBoundary
    protected void invalidateLeafAssumption() {
        Assumption prev;
        while ((prev = LEAF_ASSUMPTION_UPDATER.get(this)) != Assumption.NEVER_VALID) {
            if (prev != null) {
                prev.invalidate();
            }
            if (!LEAF_ASSUMPTION_UPDATER.compareAndSet(this, prev, Assumption.NEVER_VALID)) continue;
        }
    }

    public String toString() {
        return this.toStringLimit(Integer.MAX_VALUE);
    }

    @CompilerDirectives.TruffleBoundary
    public String toStringLimit(int limit) {
        StringBuilder sb = new StringBuilder();
        sb.append('@');
        sb.append(Integer.toHexString(this.hashCode()));
        if (!this.isValid()) {
            sb.append('!');
        }
        sb.append("{");
        Iterator<Property> iterator = this.propertyMap.reverseOrderedValueIterator();
        while (iterator.hasNext()) {
            Property p = iterator.next();
            sb.append(p);
            if (iterator.hasNext()) {
                sb.append(",");
            }
            if (sb.length() >= limit) {
                sb.append("...");
                break;
            }
            sb.append("\n");
        }
        sb.append("}");
        return sb.toString();
    }

    @Override
    public final ShapeImpl getParent() {
        return this.parent;
    }

    public final int getDepth() {
        return this.depth;
    }

    @Override
    public final boolean hasProperty(Object name) {
        return this.getProperty(name) != null;
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public final ShapeImpl removeProperty(Property prop) {
        this.onPropertyTransition(prop);
        return this.getLayoutStrategy().removeProperty(this, prop);
    }

    @Override
    public final BaseAllocator allocator() {
        return this.getLayoutStrategy().createAllocator(this);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public ShapeImpl replaceProperty(Property oldProperty, Property newProperty) {
        assert (oldProperty.getKey().equals(newProperty.getKey()));
        this.onPropertyTransition(oldProperty);
        return this.getLayoutStrategy().replaceProperty(this, oldProperty, newProperty);
    }

    public static ShapeImpl findCommonAncestor(ShapeImpl left, ShapeImpl right) {
        int leftLength;
        if (!left.isRelated(right)) {
            throw new IllegalArgumentException("shapes must have the same root");
        }
        if (left == right) {
            return left;
        }
        int rightLength = right.depth;
        ShapeImpl leftPtr = left;
        ShapeImpl rightPtr = right;
        for (leftLength = left.depth; leftLength > rightLength; --leftLength) {
            leftPtr = leftPtr.parent;
        }
        while (rightLength > leftLength) {
            rightPtr = rightPtr.parent;
            --rightLength;
        }
        while (leftPtr != rightPtr) {
            leftPtr = leftPtr.parent;
            rightPtr = rightPtr.parent;
        }
        return leftPtr;
    }

    @Override
    public final int getPropertyCount() {
        return this.propertyCount;
    }

    public static List<Property> diff(Shape oldShape, Shape newShape) {
        List<Property> oldList = oldShape.getPropertyListInternal(false);
        List<Property> newList = newShape.getPropertyListInternal(false);
        ArrayList<Property> diff = new ArrayList<Property>(oldList);
        diff.addAll(newList);
        ArrayList<Property> intersection = new ArrayList<Property>(oldList);
        intersection.retainAll(newList);
        diff.removeAll(intersection);
        return diff;
    }

    @Override
    public ObjectType getObjectType() {
        return (ObjectType)this.objectType;
    }

    @Override
    public Object getDynamicType() {
        return this.objectType;
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    protected ShapeImpl setDynamicType(Object newObjectType) {
        Objects.requireNonNull(newObjectType, "dynamicType");
        if (this.getDynamicType() == newObjectType) {
            return this;
        }
        Transition.ObjectTypeTransition transition = new Transition.ObjectTypeTransition(newObjectType);
        ShapeImpl cachedShape = this.queryTransition(transition);
        if (cachedShape != null) {
            return cachedShape;
        }
        ShapeImpl newShape = this.createShape(this.layout, this.sharedData, this, newObjectType, this.propertyMap, transition, this.allocator(), this.flags);
        this.addDirectTransition(transition, newShape);
        return newShape;
    }

    @Override
    public ShapeImpl getRoot() {
        return this.root;
    }

    @Override
    public final boolean check(DynamicObject subject) {
        return subject.getShape() == this;
    }

    @Override
    public final LayoutImpl getLayout() {
        return this.layout;
    }

    public final LayoutStrategy getLayoutStrategy() {
        return this.getLayout().getStrategy();
    }

    @Override
    public final Object getSharedData() {
        return this.sharedData;
    }

    final Object getSharedDataInternal() {
        return this.sharedData;
    }

    final boolean allowPropertyAssumptions() {
        return (this.flags & 0x20000) != 0;
    }

    private PropertyAssumptions getOrCreatePropertyAssumptions() {
        CompilerAsserts.neverPartOfCompilation();
        assert (this.allowPropertyAssumptions());
        PropertyAssumptions ass = this.root.sharedPropertyAssumptions;
        if (ass == null && !PROPERTY_ASSUMPTIONS_UPDATER.compareAndSet(this.root, null, ass = new PropertyAssumptions(null))) {
            ass = this.getPropertyAssumptions();
        }
        assert (ass != null);
        return ass;
    }

    private PropertyAssumptions getPropertyAssumptions() {
        CompilerAsserts.neverPartOfCompilation();
        assert (this.allowPropertyAssumptions());
        return this.root.sharedPropertyAssumptions;
    }

    @CompilerDirectives.TruffleBoundary
    protected void invalidateAllPropertyAssumptions() {
        assert (this.allowPropertyAssumptions());
        PropertyAssumptions propertyAssumptions = this.getPropertyAssumptions();
        if (propertyAssumptions != null) {
            propertyAssumptions.invalidateAllPropertyAssumptions();
        }
    }

    protected Assumption getSingleContextAssumption() {
        PropertyAssumptions propertyAssumptions = this.getPropertyAssumptions();
        if (propertyAssumptions != null) {
            return propertyAssumptions.getSingleContextAssumption();
        }
        return null;
    }

    @CompilerDirectives.TruffleBoundary
    public final ShapeImpl createSeparateShape(Object newSharedData) {
        if (this.parent == null) {
            return this.cloneRoot(this, newSharedData);
        }
        return this.cloneOnto(this.parent.createSeparateShape(newSharedData));
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public final ShapeImpl changeType(ObjectType newObjectType) {
        return this.setDynamicType(newObjectType);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    protected ShapeImpl setFlags(int newShapeFlags) {
        ShapeImpl.checkObjectFlags(newShapeFlags);
        if (this.getFlags() == newShapeFlags) {
            return this;
        }
        Transition.ObjectFlagsTransition transition = new Transition.ObjectFlagsTransition(newShapeFlags);
        ShapeImpl cachedShape = this.queryTransition(transition);
        if (cachedShape != null) {
            return cachedShape;
        }
        int newFlags = newShapeFlags | this.flags & 0xFFFFFF00;
        ShapeImpl newShape = this.createShape(this.layout, this.sharedData, this, this.objectType, this.propertyMap, transition, this.allocator(), newFlags);
        this.addDirectTransition(transition, newShape);
        return newShape;
    }

    @Override
    public final Iterable<Property> getProperties() {
        return this.getPropertyList();
    }

    @Override
    public final DynamicObject newInstance() {
        throw DefaultLayout.unsupported();
    }

    @Override
    public final DynamicObjectFactory createFactory() {
        throw DefaultLayout.unsupported();
    }

    @Override
    public Object getMutex() {
        return this.getRoot();
    }

    @Override
    public Shape tryMerge(Shape other) {
        return null;
    }

    @Override
    public boolean isShared() {
        return (this.flags & 0x10000) != 0;
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public Shape makeSharedShape() {
        if (this.isShared()) {
            throw new UnsupportedOperationException("makeSharedShape() can only be called on non-shared shapes.");
        }
        Transition.ShareShapeTransition transition = new Transition.ShareShapeTransition();
        ShapeImpl cachedShape = this.queryTransition(transition);
        if (cachedShape != null) {
            return cachedShape;
        }
        ShapeImpl newShape = this.createShape(this.layout, this.sharedData, this, this.objectType, this.propertyMap, transition, this.allocator(), this.flags | 0x10000);
        this.addDirectTransition(transition, newShape);
        return newShape;
    }

    protected static int getObjectFlags(int flags) {
        return (flags & 0xFF) >>> 0;
    }

    protected static int checkObjectFlags(int flags) {
        if ((flags & 0xFFFFFF00) != 0) {
            throw new IllegalArgumentException("flags must be in the range [0, 255]");
        }
        return flags;
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public Assumption getPropertyAssumption(Object key) {
        Assumption propertyAssumption;
        if (this.allowPropertyAssumptions() && (propertyAssumption = this.getOrCreatePropertyAssumptions().getPropertyAssumption(key)) != null && propertyAssumption.isValid()) {
            return propertyAssumption;
        }
        return Assumption.NEVER_VALID;
    }

    protected boolean testPropertyFlags(IntPredicate predicate) {
        for (Property p : this.getProperties()) {
            if (predicate.test(p.getFlags())) continue;
            return false;
        }
        return true;
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public boolean allPropertiesMatch(Predicate<Property> predicate) {
        for (Property p : this.getProperties()) {
            if (predicate.test(p)) continue;
            return false;
        }
        return true;
    }

    static final class PropertyAssumptions {
        private final EconomicMap<Object, Assumption> stablePropertyAssumptions;
        private final Assumption singleContextAssumption;

        PropertyAssumptions(Assumption singleContextAssumption) {
            this.singleContextAssumption = singleContextAssumption;
            this.stablePropertyAssumptions = EconomicMap.create();
        }

        synchronized Assumption getPropertyAssumption(Object propertyName) {
            CompilerAsserts.neverPartOfCompilation();
            EconomicMap<Object, Assumption> map = this.stablePropertyAssumptions;
            Assumption assumption = (Assumption)map.get(propertyName);
            if (assumption != null) {
                return assumption;
            }
            assumption = Truffle.getRuntime().createAssumption(propertyName.toString());
            map.put(propertyName, assumption);
            propertyAssumptionsCreated.inc();
            return assumption;
        }

        synchronized void invalidatePropertyAssumption(Object propertyName) {
            CompilerAsserts.neverPartOfCompilation();
            EconomicMap<Object, Assumption> map = this.stablePropertyAssumptions;
            Assumption assumption = (Assumption)map.get(propertyName);
            if (assumption != null && assumption != Assumption.NEVER_VALID) {
                assumption.invalidate("invalidatePropertyAssumption");
                map.put(propertyName, Assumption.NEVER_VALID);
                propertyAssumptionsRemoved.inc();
            }
        }

        synchronized void invalidateAllPropertyAssumptions() {
            CompilerAsserts.neverPartOfCompilation();
            for (Assumption assumption : this.stablePropertyAssumptions.getValues()) {
                assumption.invalidate("invalidateAllPropertyAssumptions");
            }
            this.stablePropertyAssumptions.clear();
        }

        Assumption getSingleContextAssumption() {
            return this.singleContextAssumption;
        }
    }

    public static abstract class BaseAllocator
    extends Shape.Allocator
    implements LocationImpl.LocationVisitor,
    Cloneable {
        protected final LayoutImpl layout;
        protected int objectArraySize;
        protected int objectFieldSize;
        protected int primitiveFieldSize;
        protected int primitiveArraySize;
        protected int depth;
        protected boolean shared;

        protected BaseAllocator(LayoutImpl layout) {
            this.layout = layout;
        }

        protected BaseAllocator(ShapeImpl shape) {
            this(shape.getLayout());
            this.objectArraySize = shape.objectArraySize;
            this.objectFieldSize = shape.objectFieldSize;
            this.primitiveFieldSize = shape.primitiveFieldSize;
            this.primitiveArraySize = shape.primitiveArraySize;
            this.depth = shape.depth;
            this.shared = shape.isShared();
        }

        protected abstract Location moveLocation(Location var1);

        @Deprecated
        protected abstract Location newObjectLocation(boolean var1, boolean var2);

        @Deprecated
        protected abstract Location newTypedObjectLocation(boolean var1, Class<?> var2, boolean var3);

        @Deprecated
        protected abstract Location newIntLocation(boolean var1);

        @Deprecated
        protected abstract Location newDoubleLocation(boolean var1);

        @Deprecated
        protected abstract Location newLongLocation(boolean var1);

        @Deprecated
        protected abstract Location newBooleanLocation(boolean var1);

        @Override
        @Deprecated
        public Location constantLocation(Object value2) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Location declaredLocation(Object value2) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        protected Location locationForValue(Object value2, boolean useFinal, boolean nonNull) {
            throw new UnsupportedOperationException();
        }

        @Deprecated
        protected Location locationForValueUpcast(Object value2, Location oldLocation) {
            return this.locationForValueUpcast(value2, oldLocation, 0L);
        }

        protected Location locationForValueUpcast(Object value2, Location oldLocation, long putFlags) {
            throw new UnsupportedOperationException();
        }

        @Override
        protected Location locationForType(Class<?> type, boolean useFinal, boolean nonNull) {
            throw new UnsupportedOperationException();
        }

        protected <T extends Location> T advance(T location0) {
            if (location0 instanceof LocationImpl) {
                LocationImpl location = (LocationImpl)location0;
                location.accept(this);
                assert (this.layout.hasPrimitiveExtensionArray() || this.primitiveArraySize == 0);
            }
            ++this.depth;
            return location0;
        }

        @Override
        public BaseAllocator addLocation(Location location) {
            this.advance(location);
            return this;
        }

        @Override
        public void visitObjectField(int index, int count) {
            this.objectFieldSize = Math.max(this.objectFieldSize, index + count);
        }

        @Override
        public void visitObjectArray(int index, int count) {
            this.objectArraySize = Math.max(this.objectArraySize, index + count);
        }

        @Override
        public void visitPrimitiveArray(int index, int count) {
            this.primitiveArraySize = Math.max(this.primitiveArraySize, index + count);
        }

        @Override
        public void visitPrimitiveField(int index, int count) {
            this.primitiveFieldSize = Math.max(this.primitiveFieldSize, index + count);
        }

        @Override
        public final BaseAllocator copy() {
            return this.clone();
        }

        protected final BaseAllocator clone() {
            try {
                return (BaseAllocator)super.clone();
            }
            catch (CloneNotSupportedException e) {
                throw CompilerDirectives.shouldNotReachHere(e);
            }
        }

        @Deprecated
        public Location existingLocationForValue(Object value2, Location oldLocation, ShapeImpl oldShape) {
            assert (oldShape.getLayout() == this.layout);
            Location newLocation = oldLocation.canStore(value2) ? oldLocation : oldShape.allocator().locationForValueUpcast(value2, oldLocation);
            return newLocation;
        }
    }
}

