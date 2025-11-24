
package com.oracle.truffle.object;

import com.oracle.truffle.api.Assumption;
import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.GenerateUncached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.object.DynamicObject;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import com.oracle.truffle.api.object.HiddenKey;
import com.oracle.truffle.api.object.Location;
import com.oracle.truffle.api.object.Property;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.object.Flags;
import com.oracle.truffle.object.LayoutImpl;
import com.oracle.truffle.object.LayoutStrategy;
import com.oracle.truffle.object.LocationImpl;
import com.oracle.truffle.object.PropertyImpl;
import com.oracle.truffle.object.ShapeImpl;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.locks.Lock;
import org.graalvm.collections.EconomicSet;

@ExportLibrary(value=DynamicObjectLibrary.class, receiverType=DynamicObject.class, priority=10, transitionLimit="5")
abstract class DynamicObjectLibraryImpl {
    static final int KEY_LIMIT = 3;

    DynamicObjectLibraryImpl() {
    }

    static boolean keyEquals(Object cachedKey, Object key) {
        if (cachedKey instanceof String) {
            return cachedKey == key || key instanceof String && ((String)cachedKey).equals(key);
        }
        if (cachedKey instanceof HiddenKey) {
            return key == cachedKey;
        }
        if (cachedKey instanceof Long) {
            return key instanceof Long && ((Long)cachedKey).equals(key);
        }
        return cachedKey == key || DynamicObjectLibraryImpl.keyEqualsBoundary(cachedKey, key);
    }

    @CompilerDirectives.TruffleBoundary(allowInlining=true)
    static boolean keyEqualsBoundary(Object cachedKey, Object key) {
        return Objects.equals(cachedKey, key);
    }

    @ExportMessage
    static boolean accepts(DynamicObject object, @Cached.Shared(value="cachedShape") @Cached(value="object.getShape()", allowUncached=true) Shape cachedShape) {
        return object.getShape() == cachedShape;
    }

    @ExportMessage
    static Shape getShape(DynamicObject object, @Cached.Shared(value="cachedShape") @Cached(value="object.getShape()", allowUncached=true) Shape cachedShape) {
        return cachedShape;
    }

    @ExportMessage
    static Object getOrDefault(DynamicObject object, Object key, Object defaultValue, @Cached.Shared(value="cachedShape") @Cached(value="object.getShape()", allowUncached=true) Shape cachedShape, @Cached.Shared(value="keyCache") @Cached(value="create(object.getShape(), key)") KeyCacheNode keyCache) {
        return keyCache.getOrDefault(object, cachedShape, key, defaultValue);
    }

    @ExportMessage
    static int getIntOrDefault(DynamicObject object, Object key, Object defaultValue, @Cached.Shared(value="cachedShape") @Cached(value="object.getShape()", allowUncached=true) Shape cachedShape, @Cached.Shared(value="keyCache") @Cached(value="create(object.getShape(), key)") KeyCacheNode keyCache) throws UnexpectedResultException {
        return keyCache.getIntOrDefault(object, cachedShape, key, defaultValue);
    }

    @ExportMessage
    static double getDoubleOrDefault(DynamicObject object, Object key, Object defaultValue, @Cached.Shared(value="cachedShape") @Cached(value="object.getShape()", allowUncached=true) Shape cachedShape, @Cached.Shared(value="keyCache") @Cached(value="create(object.getShape(), key)") KeyCacheNode keyCache) throws UnexpectedResultException {
        return keyCache.getDoubleOrDefault(object, cachedShape, key, defaultValue);
    }

    @ExportMessage
    static long getLongOrDefault(DynamicObject object, Object key, Object defaultValue, @Cached.Shared(value="cachedShape") @Cached(value="object.getShape()", allowUncached=true) Shape cachedShape, @Cached.Shared(value="keyCache") @Cached(value="create(object.getShape(), key)") KeyCacheNode keyCache) throws UnexpectedResultException {
        return keyCache.getLongOrDefault(object, cachedShape, key, defaultValue);
    }

    @ExportMessage
    static boolean containsKey(DynamicObject object, Object key, @Cached.Shared(value="cachedShape") @Cached(value="object.getShape()", allowUncached=true) Shape cachedShape, @Cached.Shared(value="keyCache") @Cached(value="create(object.getShape(), key)") KeyCacheNode keyCache) {
        return keyCache.containsKey(object, cachedShape, key);
    }

    @ExportMessage
    static void put(DynamicObject object, Object key, Object value2, @Cached.Shared(value="cachedShape") @Cached(value="object.getShape()", allowUncached=true) Shape cachedShape, @Cached.Shared(value="keyCache") @Cached(value="create(object.getShape(), key)") KeyCacheNode keyCache) {
        keyCache.put(object, cachedShape, key, value2, 0L);
    }

    @ExportMessage
    static void putInt(DynamicObject object, Object key, int value2, @Cached.Shared(value="cachedShape") @Cached(value="object.getShape()", allowUncached=true) Shape cachedShape, @Cached.Shared(value="keyCache") @Cached(value="create(object.getShape(), key)") KeyCacheNode keyCache) {
        keyCache.putInt(object, cachedShape, key, value2, 0L);
    }

    @ExportMessage
    static void putLong(DynamicObject object, Object key, long value2, @Cached.Shared(value="cachedShape") @Cached(value="object.getShape()", allowUncached=true) Shape cachedShape, @Cached.Shared(value="keyCache") @Cached(value="create(object.getShape(), key)") KeyCacheNode keyCache) {
        keyCache.putLong(object, cachedShape, key, value2, 0L);
    }

    @ExportMessage
    static void putDouble(DynamicObject object, Object key, double value2, @Cached.Shared(value="cachedShape") @Cached(value="object.getShape()", allowUncached=true) Shape cachedShape, @Cached.Shared(value="keyCache") @Cached(value="create(object.getShape(), key)") KeyCacheNode keyCache) {
        keyCache.putDouble(object, cachedShape, key, value2, 0L);
    }

    @ExportMessage
    static boolean putIfPresent(DynamicObject object, Object key, Object value2, @Cached.Shared(value="cachedShape") @Cached(value="object.getShape()", allowUncached=true) Shape cachedShape, @Cached.Shared(value="keyCache") @Cached(value="create(object.getShape(), key)") KeyCacheNode keyCache) {
        return keyCache.put(object, cachedShape, key, value2, 0x400000000L);
    }

    @ExportMessage
    static void putWithFlags(DynamicObject object, Object key, Object value2, int flags, @Cached.Shared(value="cachedShape") @Cached(value="object.getShape()", allowUncached=true) Shape cachedShape, @Cached.Shared(value="keyCache") @Cached(value="create(object.getShape(), key)") KeyCacheNode keyCache) {
        keyCache.put(object, cachedShape, key, value2, Flags.propertyFlagsToPutFlags(flags) | 0x800000000L);
    }

    @ExportMessage
    static void putConstant(DynamicObject object, Object key, Object value2, int flags, @Cached.Shared(value="cachedShape") @Cached(value="object.getShape()", allowUncached=true) Shape cachedShape, @Cached.Shared(value="keyCache") @Cached(value="create(object.getShape(), key)") KeyCacheNode keyCache) {
        keyCache.put(object, cachedShape, key, value2, Flags.propertyFlagsToPutFlags(flags) | 0x800000000L | 0x1000000000L);
    }

    @ExportMessage
    public static Property getProperty(DynamicObject object, Object key, @Cached.Shared(value="cachedShape") @Cached(value="object.getShape()", allowUncached=true) Shape cachedShape, @Cached.Shared(value="keyCache") @Cached(value="create(object.getShape(), key)") KeyCacheNode keyCache) {
        return keyCache.getProperty(object, cachedShape, key);
    }

    @ExportMessage
    public static boolean setPropertyFlags(DynamicObject object, Object key, int propertyFlags, @Cached.Shared(value="cachedShape") @Cached(value="object.getShape()", allowUncached=true) Shape cachedShape, @Cached.Shared(value="keyCache") @Cached(value="create(object.getShape(), key)") KeyCacheNode keyCache) {
        return keyCache.setPropertyFlags(object, cachedShape, key, propertyFlags);
    }

    @CompilerDirectives.TruffleBoundary
    static ShapeImpl changePropertyFlags(ShapeImpl shape, PropertyImpl cachedProperty, int propertyFlags) {
        return shape.replaceProperty(cachedProperty, cachedProperty.copyWithFlags(propertyFlags));
    }

    @ExportMessage
    public static boolean removeKey(DynamicObject object, Object key, @Cached.Shared(value="cachedShape") @Cached(value="object.getShape()", allowUncached=true) Shape cachedShape, @Cached.Shared(value="keyCache") @Cached(value="create(object.getShape(), key)") KeyCacheNode keyCache) {
        return keyCache.removeKey(object, cachedShape, key);
    }

    @ExportMessage
    public static Object getDynamicType(DynamicObject object, @Cached.Shared(value="cachedShape") @Cached(value="object.getShape()", allowUncached=true) Shape cachedShape) {
        return cachedShape.getDynamicType();
    }

    @ExportMessage
    public static boolean setDynamicType(DynamicObject object, Object objectType, @Cached.Shared(value="cachedShape") @Cached(value="object.getShape()", allowUncached=true) Shape cachedShape, @Cached SetDynamicTypeNode setCache) {
        return setCache.execute(object, cachedShape, objectType);
    }

    @ExportMessage
    public static int getShapeFlags(DynamicObject object, @Cached.Shared(value="cachedShape") @Cached(value="object.getShape()", allowUncached=true) Shape cachedShape) {
        return cachedShape.getFlags();
    }

    @ExportMessage
    public static boolean setShapeFlags(DynamicObject object, int flags, @Cached.Shared(value="cachedShape") @Cached(value="object.getShape()", allowUncached=true) Shape cachedShape, @Cached SetFlagsNode setCache) {
        return setCache.execute(object, cachedShape, flags);
    }

    @ExportMessage
    public static boolean isShared(DynamicObject object, @Cached.Shared(value="cachedShape") @Cached(value="object.getShape()", allowUncached=true) Shape cachedShape) {
        return cachedShape.isShared();
    }

    @ExportMessage
    public static void markShared(DynamicObject object, @Cached.Shared(value="cachedShape") @Cached(value="object.getShape()", allowUncached=true) Shape cachedShape, @Cached MakeSharedNode setCache) {
        setCache.execute(object, cachedShape);
    }

    @ExportMessage
    public static boolean updateShape(DynamicObject object, @Cached.Shared(value="cachedShape") @Cached(value="object.getShape()", allowUncached=true) Shape cachedShape) {
        if (cachedShape.isValid()) {
            return false;
        }
        return DynamicObjectLibraryImpl.updateShapeImpl(object);
    }

    @CompilerDirectives.TruffleBoundary
    static boolean updateShapeImpl(DynamicObject object) {
        return ((ShapeImpl)object.getShape()).getLayoutStrategy().updateShape(object);
    }

    @ExportMessage
    public static boolean resetShape(DynamicObject object, Shape otherShape, @Cached.Shared(value="cachedShape") @Cached(value="object.getShape()", allowUncached=true) Shape cachedShape, @Cached ResetShapeNode setCache) {
        return setCache.execute(object, cachedShape, otherShape);
    }

    @ExportMessage
    public static Object[] getKeyArray(DynamicObject object, @Cached.Shared(value="cachedShape") @Cached(value="object.getShape()", allowUncached=true) Shape cachedShape) {
        return ((ShapeImpl)cachedShape).getKeyArray();
    }

    @ExportMessage
    public static Property[] getPropertyArray(DynamicObject object, @Cached.Shared(value="cachedShape") @Cached(value="object.getShape()", allowUncached=true) Shape cachedShape) {
        return ((ShapeImpl)cachedShape).getPropertyArray();
    }

    static LocationImpl getLocation(Property existing) {
        return (LocationImpl)existing.getLocation();
    }

    @CompilerDirectives.TruffleBoundary
    protected static boolean putUncached(DynamicObject object, Object key, Object value2, long putFlags) {
        Shape s = LayoutImpl.ACCESS.getShape(object);
        Property existingProperty = s.getProperty(key);
        if (existingProperty == null && Flags.isSetExisting(putFlags)) {
            return false;
        }
        if (existingProperty != null && !Flags.isUpdateFlags(putFlags) && existingProperty.getLocation().canStore(value2)) {
            DynamicObjectLibraryImpl.getLocation(existingProperty).setSafe(object, value2, false, false);
            return true;
        }
        return DynamicObjectLibraryImpl.putUncachedSlow(object, key, value2, putFlags);
    }

    private static boolean putUncachedSlow(DynamicObject object, Object key, Object value2, long putFlags) {
        Property property;
        ShapeImpl newShape;
        ShapeImpl oldShape;
        CompilerAsserts.neverPartOfCompilation();
        DynamicObjectLibraryImpl.updateShapeImpl(object);
        do {
            LayoutStrategy strategy;
            Property existingProperty;
            if ((existingProperty = (oldShape = (ShapeImpl)LayoutImpl.ACCESS.getShape(object)).getProperty(key)) == null) {
                if (Flags.isSetExisting(putFlags)) {
                    return false;
                }
                strategy = oldShape.getLayoutStrategy();
                newShape = strategy.defineProperty(oldShape, key, value2, Flags.getPropertyFlags(putFlags), null, existingProperty, putFlags);
                property = ((Shape)newShape).getProperty(key);
                continue;
            }
            if (Flags.isUpdateFlags(putFlags) && Flags.getPropertyFlags(putFlags) != existingProperty.getFlags()) {
                strategy = oldShape.getLayoutStrategy();
                newShape = strategy.defineProperty(oldShape, key, value2, Flags.getPropertyFlags(putFlags), null, existingProperty, putFlags);
                property = ((Shape)newShape).getProperty(key);
                continue;
            }
            if (existingProperty.getLocation().canStore(value2)) {
                newShape = oldShape;
                property = existingProperty;
                continue;
            }
            strategy = oldShape.getLayoutStrategy();
            newShape = strategy.defineProperty(oldShape, key, value2, existingProperty.getFlags(), null, existingProperty, putFlags);
            property = ((Shape)newShape).getProperty(key);
        } while (DynamicObjectLibraryImpl.updateShapeImpl(object));
        assert (LayoutImpl.ACCESS.getShape(object) == oldShape);
        LocationImpl location = DynamicObjectLibraryImpl.getLocation(property);
        if (oldShape != newShape) {
            LayoutImpl.ACCESS.grow(object, oldShape, newShape);
            location.setSafe(object, value2, false, true);
            LayoutImpl.ACCESS.setShapeWithStoreFence(object, newShape);
            DynamicObjectLibraryImpl.updateShapeImpl(object);
        } else {
            location.setSafe(object, value2, false, false);
        }
        return true;
    }

    static RemovePlan prepareRemove(ShapeImpl shapeBefore, ShapeImpl shapeAfter) {
        assert (!shapeBefore.isShared());
        LayoutStrategy strategy = shapeBefore.getLayoutStrategy();
        ArrayList<Move> moves = new ArrayList<Move>();
        boolean canMoveInPlace = shapeAfter.getObjectArrayCapacity() <= shapeBefore.getObjectArrayCapacity() && shapeAfter.getPrimitiveArrayCapacity() <= shapeBefore.getPrimitiveArrayCapacity();
        ListIterator<Property> iterator = shapeAfter.getPropertyListInternal(false).listIterator();
        while (iterator.hasNext()) {
            Property to = iterator.next();
            Property from = shapeBefore.getProperty(to.getKey());
            LocationImpl fromLoc = DynamicObjectLibraryImpl.getLocation(from);
            LocationImpl toLoc = DynamicObjectLibraryImpl.getLocation(to);
            if (LocationImpl.isSameLocation(toLoc, fromLoc)) continue;
            assert (!toLoc.isValue());
            int fromOrd = strategy.getLocationOrdinal(fromLoc);
            int toOrd = strategy.getLocationOrdinal(toLoc);
            Move move = new Move(fromLoc, toLoc, fromOrd, toOrd);
            canMoveInPlace = canMoveInPlace && fromOrd > toOrd;
            moves.add(move);
        }
        if (canMoveInPlace && !DynamicObjectLibraryImpl.isSorted(moves)) {
            Collections.sort(moves);
        }
        return new RemovePlan(moves.toArray(new Move[0]), canMoveInPlace, shapeBefore, shapeAfter);
    }

    private static boolean isSorted(List<Move> moves) {
        for (int i = 1; i < moves.size(); ++i) {
            Move m2;
            Move m1 = moves.get(i - 1);
            if (m1.compareTo(m2 = moves.get(i)) <= 0) continue;
            return false;
        }
        return true;
    }

    static <T extends CacheData<T>> T filterValid(T cache) {
        if (cache == null) {
            return null;
        }
        Object filteredNext = DynamicObjectLibraryImpl.filterValid(((CacheData)cache).next);
        if (((CacheData)cache).isValid()) {
            if (filteredNext == ((CacheData)cache).next) {
                return (T)cache;
            }
            return ((CacheData)cache).withNext(filteredNext);
        }
        return filteredNext;
    }

    @GenerateUncached
    static abstract class ResetShapeNode
    extends Node {
        ResetShapeNode() {
        }

        abstract boolean execute(DynamicObject var1, Shape var2, Shape var3);

        @Specialization(guards={"otherShape == cachedOtherShape"})
        static boolean doCached(DynamicObject object, Shape cachedShape, Shape otherShape, @Cached(value="verifyResetShape(cachedShape, otherShape)", allowUncached=true) Shape cachedOtherShape) {
            if (cachedShape == cachedOtherShape) {
                return false;
            }
            LayoutImpl.ACCESS.resize(object, cachedShape, cachedOtherShape);
            LayoutImpl.ACCESS.setShape(object, cachedOtherShape);
            return true;
        }

        static Shape verifyResetShape(Shape currentShape, Shape otherShape) {
            if (((ShapeImpl)otherShape).hasInstanceProperties()) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                throw new IllegalArgumentException("Shape must not contain any instance properties.");
            }
            if (currentShape != otherShape) {
                LayoutImpl.ACCESS.invalidateAllPropertyAssumptions(currentShape);
            }
            return otherShape;
        }
    }

    @GenerateUncached
    static abstract class MakeSharedNode
    extends Node {
        MakeSharedNode() {
        }

        abstract void execute(DynamicObject var1, Shape var2);

        @Specialization
        static void doCached(DynamicObject object, Shape cachedShape, @Cached(value="makeSharedShape(cachedShape)", allowUncached=true) Shape newShape) {
            assert (newShape != cachedShape && ((ShapeImpl)cachedShape).getObjectArrayCapacity() == ((ShapeImpl)newShape).getObjectArrayCapacity() && ((ShapeImpl)cachedShape).getPrimitiveArrayCapacity() == ((ShapeImpl)newShape).getPrimitiveArrayCapacity());
            LayoutImpl.ACCESS.setShape(object, newShape);
        }

        static Shape makeSharedShape(Shape inputShape) {
            return ((ShapeImpl)inputShape).makeSharedShape();
        }
    }

    @GenerateUncached
    static abstract class SetDynamicTypeNode
    extends Node {
        SetDynamicTypeNode() {
        }

        abstract boolean execute(DynamicObject var1, Shape var2, Object var3);

        @Specialization(guards={"objectType == newObjectType"}, limit="3")
        static boolean doCached(DynamicObject object, Shape cachedShape, Object objectType, @Cached(value="objectType", allowUncached=true) Object newObjectType, @Cached(value="shapeSetDynamicType(cachedShape, newObjectType)", allowUncached=true) Shape newShape) {
            if (newShape != cachedShape) {
                LayoutImpl.ACCESS.setShape(object, newShape);
                return true;
            }
            return false;
        }

        @Specialization(replaces={"doCached"})
        static boolean doUncached(DynamicObject object, Shape cachedShape, Object objectType) {
            Shape newShape = SetDynamicTypeNode.shapeSetDynamicType(cachedShape, objectType);
            if (newShape != cachedShape) {
                LayoutImpl.ACCESS.setShape(object, newShape);
                return true;
            }
            return false;
        }

        static Shape shapeSetDynamicType(Shape shape, Object newType) {
            return ((ShapeImpl)shape).setDynamicType(newType);
        }
    }

    @GenerateUncached
    static abstract class SetFlagsNode
    extends Node {
        SetFlagsNode() {
        }

        abstract boolean execute(DynamicObject var1, Shape var2, int var3);

        @Specialization(guards={"flags == newFlags"}, limit="3")
        static boolean doCached(DynamicObject object, Shape cachedShape, int flags, @Cached(value="flags", allowUncached=true) int newFlags, @Cached(value="shapeSetFlags(cachedShape, newFlags)", allowUncached=true) Shape newShape) {
            if (newShape != cachedShape) {
                LayoutImpl.ACCESS.setShape(object, newShape);
                return true;
            }
            return false;
        }

        @Specialization(replaces={"doCached"})
        static boolean doUncached(DynamicObject object, Shape cachedShape, int flags) {
            Shape newShape = SetFlagsNode.shapeSetFlags(cachedShape, flags);
            if (newShape != cachedShape) {
                LayoutImpl.ACCESS.setShape(object, newShape);
                return true;
            }
            return false;
        }

        static Shape shapeSetFlags(Shape shape, int newFlags) {
            return ((ShapeImpl)shape).setFlags(newFlags);
        }
    }

    static class RemovePropertyCacheData
    extends MutateCacheData {
        final RemovePlan removePlan;

        RemovePropertyCacheData(Shape newShape, Assumption newShapeValidAssumption, RemovePlan removePlan, MutateCacheData next) {
            super(next, newShape, newShapeValidAssumption);
            this.removePlan = removePlan;
        }

        @Override
        protected MutateCacheData withNext(MutateCacheData newNext) {
            return new RemovePropertyCacheData(this.newShape, this.newShapeValidAssumption, this.removePlan, newNext);
        }
    }

    static class SetPropertyFlagsCacheData
    extends MutateCacheData {
        final Property property;

        SetPropertyFlagsCacheData(Shape newShape, Assumption newShapeValidAssumption, Property property, MutateCacheData next) {
            super(next, newShape, newShapeValidAssumption);
            this.property = property;
        }

        @Override
        protected MutateCacheData withNext(MutateCacheData newNext) {
            return new SetPropertyFlagsCacheData(this.newShape, this.newShapeValidAssumption, this.property, newNext);
        }
    }

    static class PutCacheData
    extends MutateCacheData {
        final long putFlags;
        final Property property;

        PutCacheData(long putFlags, Shape newShape, Assumption newShapeValidAssumption, Property property, MutateCacheData next) {
            super(next, newShape, newShapeValidAssumption);
            this.putFlags = putFlags;
            this.property = property;
        }

        @Override
        protected MutateCacheData withNext(MutateCacheData newNext) {
            return new PutCacheData(this.putFlags, this.newShape, this.newShapeValidAssumption, this.property, newNext);
        }
    }

    static class MutateCacheData
    extends CacheData<MutateCacheData> {
        static final MutateCacheData GENERIC = new MutateCacheData(null, null, null);
        final Shape newShape;
        final Assumption newShapeValidAssumption;

        MutateCacheData(MutateCacheData next, Shape newShape, Assumption newShapeValidAssumption) {
            super(next);
            this.newShape = newShape;
            this.newShapeValidAssumption = newShapeValidAssumption;
        }

        @Override
        protected boolean isValid() {
            Assumption newShapeValid = this.newShapeValidAssumption;
            return newShapeValid == Assumption.NEVER_VALID || newShapeValid == Assumption.ALWAYS_VALID || newShapeValid.isValid();
        }

        protected void maybeUpdateShape(DynamicObject store) {
            if (this.newShapeValidAssumption == Assumption.NEVER_VALID) {
                DynamicObjectLibraryImpl.updateShapeImpl(store);
            }
        }

        @Override
        protected MutateCacheData withNext(MutateCacheData newNext) {
            return new MutateCacheData((MutateCacheData)this.next, this.newShape, this.newShapeValidAssumption);
        }
    }

    static abstract class CacheData<T extends CacheData<T>> {
        final T next;

        CacheData(T next) {
            this.next = next;
        }

        protected boolean isValid() {
            return true;
        }

        protected abstract T withNext(T var1);
    }

    static abstract class SpecificKey
    extends KeyCacheEntry {
        final Object cachedKey;
        @CompilerDirectives.CompilationFinal
        MutateCacheData cache;

        SpecificKey(Object key, KeyCacheEntry next) {
            super(next);
            this.cachedKey = key;
        }

        static SpecificKey create(Object key, Shape shape, KeyCacheEntry next, boolean useIdentity) {
            Property property;
            if (key != null && (property = shape.getProperty(key)) != null) {
                return useIdentity ? new ExistingKeyIdentity(key, property, next) : new ExistingKey(key, property, next);
            }
            return useIdentity ? new MissingKeyIdentity(key, next) : new MissingKey(key, next);
        }

        protected final boolean assertCachedKeyAndShapeForRead(DynamicObject object, Shape cachedShape, Object key) {
            assert (object.getShape() == cachedShape || cachedShape.isShared());
            assert (DynamicObjectLibraryImpl.keyEquals(this.cachedKey, key));
            return true;
        }

        protected final boolean assertCachedKeyAndShapeForWrite(DynamicObject object, Shape cachedShape, Object key) {
            assert (object.getShape() == cachedShape);
            assert (DynamicObjectLibraryImpl.keyEquals(this.cachedKey, key));
            return true;
        }

        @Override
        public boolean acceptsKey(Object key) {
            return DynamicObjectLibraryImpl.keyEquals(this.cachedKey, key);
        }

        @ExplodeLoop
        protected boolean putImpl(DynamicObject object, Shape cachedShape, Object key, Object value2, long putFlags, Property oldProperty) {
            Shape oldShape = cachedShape;
            MutateCacheData start2 = this.cache;
            if (start2 == MutateCacheData.GENERIC || !cachedShape.isValid()) {
                return DynamicObjectLibraryImpl.putUncached(object, key, value2, putFlags);
            }
            MutateCacheData c = start2;
            while (c != null && c.isValid()) {
                block7: {
                    block9: {
                        boolean guardCondition;
                        LocationImpl location;
                        block8: {
                            if (!(c instanceof PutCacheData) || ((PutCacheData)c).putFlags != putFlags) break block7;
                            Property newProperty = ((PutCacheData)c).property;
                            if (newProperty == null) {
                                assert (Flags.isSetExisting(putFlags));
                                return false;
                            }
                            location = DynamicObjectLibraryImpl.getLocation(newProperty);
                            boolean bl = guardCondition = object.getShape() == oldShape;
                            if (!location.canStore(value2)) break block7;
                            Shape newShape = c.newShape;
                            if (newShape == oldShape) break block8;
                            LayoutImpl.ACCESS.grow(object, oldShape, newShape);
                            location.setSafe(object, value2, guardCondition, true);
                            LayoutImpl.ACCESS.setShapeWithStoreFence(object, newShape);
                            break block9;
                        }
                        if (location.isFinal()) break block7;
                        location.setSafe(object, value2, guardCondition, false);
                    }
                    c.maybeUpdateShape(object);
                    return true;
                }
                c = (MutateCacheData)c.next;
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            KeyCacheNode impl = this.insertIntoPutCache(object, cachedShape, value2, putFlags, oldProperty);
            return impl.put(object, cachedShape, key, value2, putFlags);
        }

        @ExplodeLoop
        protected boolean putIntImpl(DynamicObject object, Shape cachedShape, Object key, int value2, long putFlags, Property oldProperty) {
            Shape oldShape = cachedShape;
            MutateCacheData start2 = this.cache;
            if (start2 == MutateCacheData.GENERIC || !cachedShape.isValid()) {
                return DynamicObjectLibraryImpl.putUncached(object, key, value2, putFlags);
            }
            MutateCacheData c = start2;
            while (c != null && c.isValid()) {
                block14: {
                    block25: {
                        boolean guardCondition;
                        LocationImpl location;
                        block24: {
                            Shape newShape;
                            block21: {
                                block23: {
                                    block22: {
                                        block18: {
                                            block20: {
                                                block19: {
                                                    block15: {
                                                        block17: {
                                                            block16: {
                                                                if (!(c instanceof PutCacheData) || ((PutCacheData)c).putFlags != putFlags) break block14;
                                                                Property newProperty = ((PutCacheData)c).property;
                                                                if (newProperty == null) {
                                                                    assert (Flags.isSetExisting(putFlags));
                                                                    return false;
                                                                }
                                                                location = DynamicObjectLibraryImpl.getLocation(newProperty);
                                                                newShape = c.newShape;
                                                                boolean bl = guardCondition = object.getShape() == oldShape;
                                                                if (!location.isIntLocation()) break block15;
                                                                if (newShape == oldShape) break block16;
                                                                LayoutImpl.ACCESS.grow(object, oldShape, newShape);
                                                                location.setIntSafe(object, value2, guardCondition, true);
                                                                LayoutImpl.ACCESS.setShapeWithStoreFence(object, newShape);
                                                                break block17;
                                                            }
                                                            if (location.isFinal()) break block14;
                                                            location.setIntSafe(object, value2, guardCondition, false);
                                                        }
                                                        c.maybeUpdateShape(object);
                                                        return true;
                                                    }
                                                    if (!location.isImplicitCastIntToLong()) break block18;
                                                    if (newShape == oldShape) break block19;
                                                    LayoutImpl.ACCESS.grow(object, oldShape, newShape);
                                                    location.setLongSafe(object, value2, guardCondition, true);
                                                    LayoutImpl.ACCESS.setShapeWithStoreFence(object, newShape);
                                                    break block20;
                                                }
                                                if (location.isFinal()) break block14;
                                                location.setLongSafe(object, value2, guardCondition, false);
                                            }
                                            c.maybeUpdateShape(object);
                                            return true;
                                        }
                                        if (!location.isImplicitCastIntToDouble()) break block21;
                                        if (newShape == oldShape) break block22;
                                        LayoutImpl.ACCESS.grow(object, oldShape, newShape);
                                        location.setDoubleSafe(object, value2, guardCondition, true);
                                        LayoutImpl.ACCESS.setShapeWithStoreFence(object, newShape);
                                        break block23;
                                    }
                                    if (location.isFinal()) break block14;
                                    location.setDoubleSafe(object, value2, guardCondition, false);
                                }
                                c.maybeUpdateShape(object);
                                return true;
                            }
                            if (!location.canStore(value2)) break block14;
                            if (newShape == oldShape) break block24;
                            LayoutImpl.ACCESS.grow(object, oldShape, newShape);
                            location.setSafe(object, value2, guardCondition, true);
                            LayoutImpl.ACCESS.setShapeWithStoreFence(object, newShape);
                            break block25;
                        }
                        if (location.isFinal()) break block14;
                        location.setSafe(object, value2, guardCondition, false);
                    }
                    c.maybeUpdateShape(object);
                    return true;
                }
                c = (MutateCacheData)c.next;
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            KeyCacheNode impl = this.insertIntoPutCache(object, cachedShape, value2, putFlags, oldProperty);
            return impl.putInt(object, cachedShape, key, value2, putFlags);
        }

        @ExplodeLoop
        protected boolean putLongImpl(DynamicObject object, Shape cachedShape, Object key, long value2, long putFlags, Property oldProperty) {
            Shape oldShape = cachedShape;
            MutateCacheData start2 = this.cache;
            if (start2 == MutateCacheData.GENERIC) {
                return DynamicObjectLibraryImpl.putUncached(object, key, value2, putFlags);
            }
            MutateCacheData c = start2;
            while (c != null) {
                block10: {
                    block15: {
                        boolean guardCondition;
                        LocationImpl location;
                        block14: {
                            Shape newShape;
                            block11: {
                                block13: {
                                    block12: {
                                        if (!(c instanceof PutCacheData) || ((PutCacheData)c).putFlags != putFlags) break block10;
                                        Property newProperty = ((PutCacheData)c).property;
                                        if (newProperty == null) {
                                            assert (Flags.isSetExisting(putFlags));
                                            return false;
                                        }
                                        location = DynamicObjectLibraryImpl.getLocation(newProperty);
                                        boolean bl = guardCondition = object.getShape() == oldShape;
                                        if (!location.isLongLocation()) break block11;
                                        newShape = c.newShape;
                                        if (newShape == oldShape) break block12;
                                        LayoutImpl.ACCESS.grow(object, oldShape, newShape);
                                        location.setLongSafe(object, value2, guardCondition, true);
                                        LayoutImpl.ACCESS.setShapeWithStoreFence(object, newShape);
                                        break block13;
                                    }
                                    if (location.isFinal()) break block10;
                                    location.setLongSafe(object, value2, guardCondition, false);
                                }
                                c.maybeUpdateShape(object);
                                return true;
                            }
                            if (!location.canStore(value2)) break block10;
                            newShape = c.newShape;
                            if (newShape == oldShape) break block14;
                            LayoutImpl.ACCESS.grow(object, oldShape, newShape);
                            location.setSafe(object, value2, guardCondition, true);
                            LayoutImpl.ACCESS.setShapeWithStoreFence(object, newShape);
                            break block15;
                        }
                        if (location.isFinal()) break block10;
                        location.setSafe(object, value2, guardCondition, false);
                    }
                    c.maybeUpdateShape(object);
                    return true;
                }
                c = (MutateCacheData)c.next;
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            KeyCacheNode impl = this.insertIntoPutCache(object, cachedShape, value2, putFlags, oldProperty);
            return impl.putLong(object, cachedShape, key, value2, putFlags);
        }

        @ExplodeLoop
        protected boolean putDoubleImpl(DynamicObject object, Shape cachedShape, Object key, double value2, long putFlags, Property oldProperty) {
            Shape oldShape = cachedShape;
            MutateCacheData start2 = this.cache;
            if (start2 == MutateCacheData.GENERIC) {
                return DynamicObjectLibraryImpl.putUncached(object, key, value2, putFlags);
            }
            MutateCacheData c = start2;
            while (c != null) {
                block10: {
                    block15: {
                        boolean guardCondition;
                        LocationImpl location;
                        block14: {
                            Shape newShape;
                            Property newProperty;
                            block11: {
                                block13: {
                                    block12: {
                                        if (!(c instanceof PutCacheData) || ((PutCacheData)c).putFlags != putFlags) break block10;
                                        newProperty = ((PutCacheData)c).property;
                                        if (newProperty == null) {
                                            assert (Flags.isSetExisting(putFlags));
                                            return false;
                                        }
                                        location = DynamicObjectLibraryImpl.getLocation(newProperty);
                                        boolean bl = guardCondition = object.getShape() == oldShape;
                                        if (!location.isDoubleLocation()) break block11;
                                        newShape = c.newShape;
                                        if (newShape == oldShape) break block12;
                                        LayoutImpl.ACCESS.grow(object, oldShape, newShape);
                                        location.setDoubleSafe(object, value2, guardCondition, true);
                                        LayoutImpl.ACCESS.setShapeWithStoreFence(object, newShape);
                                        break block13;
                                    }
                                    if (location.isFinal()) break block10;
                                    location.setDoubleSafe(object, value2, guardCondition, false);
                                }
                                c.maybeUpdateShape(object);
                                return true;
                            }
                            if (!newProperty.getLocation().canStore(value2)) break block10;
                            newShape = c.newShape;
                            if (newShape == oldShape) break block14;
                            LayoutImpl.ACCESS.grow(object, oldShape, newShape);
                            location.setSafe(object, value2, guardCondition, true);
                            LayoutImpl.ACCESS.setShapeWithStoreFence(object, newShape);
                            break block15;
                        }
                        if (location.isFinal()) break block10;
                        location.setSafe(object, value2, guardCondition, false);
                    }
                    c.maybeUpdateShape(object);
                    return true;
                }
                c = (MutateCacheData)c.next;
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            KeyCacheNode impl = this.insertIntoPutCache(object, cachedShape, value2, putFlags, oldProperty);
            return impl.putDouble(object, cachedShape, key, value2, putFlags);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        protected KeyCacheNode insertIntoPutCache(DynamicObject object, Shape cachedShape, Object value2, long putFlags, Property property) {
            CompilerAsserts.neverPartOfCompilation();
            if (!cachedShape.isValid()) {
                return Generic.instance();
            }
            Lock lock = this.getLock();
            lock.lock();
            try {
                Property newProperty;
                MutateCacheData tail = DynamicObjectLibraryImpl.filterValid(this.cache);
                ShapeImpl oldShape = (ShapeImpl)cachedShape;
                ShapeImpl newShape = this.getNewShape(object, value2, putFlags, property, oldShape);
                if (!oldShape.isValid()) {
                    Generic generic = Generic.instance();
                    return generic;
                }
                if (newShape == oldShape) {
                    newProperty = property;
                } else {
                    newProperty = newShape.getProperty(this.cachedKey);
                    assert (newProperty.getLocation().canStore(value2));
                }
                Assumption newShapeValid = SpecificKey.getShapeValidAssumption(oldShape, newShape);
                this.cache = new PutCacheData(putFlags, newShape, newShapeValid, newProperty, tail);
                SpecificKey specificKey = this;
                return specificKey;
            }
            finally {
                lock.unlock();
            }
        }

        private ShapeImpl getNewShape(DynamicObject object, Object value2, long putFlags, Property property, ShapeImpl oldShape) {
            if (property == null) {
                if (Flags.isSetExisting(putFlags)) {
                    return oldShape;
                }
                int propertyFlags = Flags.getPropertyFlags(putFlags);
                LayoutStrategy strategy = oldShape.getLayoutStrategy();
                return strategy.defineProperty(oldShape, this.cachedKey, value2, propertyFlags, null, putFlags);
            }
            if (Flags.isUpdateFlags(putFlags) && Flags.getPropertyFlags(putFlags) != property.getFlags()) {
                int propertyFlags = Flags.getPropertyFlags(putFlags);
                LayoutStrategy strategy = oldShape.getLayoutStrategy();
                return strategy.defineProperty(oldShape, this.cachedKey, value2, propertyFlags, null, putFlags);
            }
            Location location = property.getLocation();
            if (!location.isDeclared() && !location.canStore(value2)) {
                assert (oldShape == LayoutImpl.ACCESS.getShape(object));
                LayoutStrategy strategy = oldShape.getLayoutStrategy();
                ShapeImpl newShape = strategy.definePropertyGeneralize(oldShape, property, value2, null, putFlags);
                assert (newShape != oldShape);
                return newShape;
            }
            if (location.isDeclared()) {
                LayoutStrategy strategy = oldShape.getLayoutStrategy();
                return strategy.defineProperty(oldShape, this.cachedKey, value2, property.getFlags(), null, putFlags);
            }
            assert (location.canStore(value2));
            return oldShape;
        }

        @ExplodeLoop
        protected boolean setPropertyFlagsImpl(DynamicObject object, Shape cachedShape, Object key, int propertyFlags, Property cachedProperty) {
            Shape oldShape = cachedShape;
            MutateCacheData start2 = this.cache;
            if (start2 == MutateCacheData.GENERIC || !cachedShape.isValid()) {
                return Generic.instance().setPropertyFlags(object, cachedShape, key, propertyFlags);
            }
            MutateCacheData c = start2;
            while (c != null && c.isValid()) {
                if (c instanceof SetPropertyFlagsCacheData && ((SetPropertyFlagsCacheData)c).property.getFlags() == propertyFlags) {
                    Shape newShape;
                    if (cachedProperty == null) {
                        return false;
                    }
                    if (cachedProperty.getFlags() != propertyFlags && (newShape = c.newShape) != oldShape) {
                        LayoutImpl.ACCESS.setShape(object, newShape);
                        c.maybeUpdateShape(object);
                    }
                    return true;
                }
                c = (MutateCacheData)c.next;
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            KeyCacheNode impl = this.insertIntoSetPropertyFlagsCache(cachedShape, propertyFlags, cachedProperty);
            return impl.setPropertyFlags(object, cachedShape, key, propertyFlags);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        protected KeyCacheNode insertIntoSetPropertyFlagsCache(Shape cachedShape, int propertyFlags, Property cachedProperty) {
            CompilerAsserts.neverPartOfCompilation();
            if (!cachedShape.isValid()) {
                return Generic.instance();
            }
            Lock lock = this.getLock();
            lock.lock();
            try {
                MutateCacheData tail = DynamicObjectLibraryImpl.filterValid(this.cache);
                ShapeImpl oldShape = (ShapeImpl)cachedShape;
                ShapeImpl newShape = DynamicObjectLibraryImpl.changePropertyFlags(oldShape, (PropertyImpl)cachedProperty, propertyFlags);
                if (!oldShape.isValid()) {
                    Generic generic = Generic.instance();
                    return generic;
                }
                Property newProperty = newShape == oldShape ? cachedProperty : newShape.getProperty(this.cachedKey);
                Assumption newShapeValid = SpecificKey.getShapeValidAssumption(oldShape, newShape);
                this.cache = new SetPropertyFlagsCacheData(newShape, newShapeValid, newProperty, tail);
                SpecificKey specificKey = this;
                return specificKey;
            }
            finally {
                lock.unlock();
            }
        }

        @ExplodeLoop
        protected boolean removeKeyImpl(DynamicObject object, Shape cachedShape, Object key, Property cachedProperty) {
            Shape oldShape = cachedShape;
            MutateCacheData start2 = this.cache;
            if (start2 == MutateCacheData.GENERIC || !cachedShape.isValid()) {
                return Generic.instance().removeKey(object, cachedShape, key);
            }
            MutateCacheData c = start2;
            while (c != null && c.isValid()) {
                if (c instanceof RemovePropertyCacheData) {
                    if (cachedProperty == null) {
                        return false;
                    }
                    Shape newShape = c.newShape;
                    assert (newShape != oldShape);
                    Map<Object, Object> archive = null;
                    assert ((archive = LayoutImpl.ACCESS.archive(object)) != null);
                    if (!oldShape.isShared()) {
                        ((RemovePropertyCacheData)c).removePlan.execute(object);
                    } else {
                        LayoutImpl.ACCESS.setShape(object, newShape);
                    }
                    assert (LayoutImpl.ACCESS.verifyValues(object, archive));
                    c.maybeUpdateShape(object);
                    return true;
                }
                c = (MutateCacheData)c.next;
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            KeyCacheNode impl = this.insertIntoRemoveKeyCache(cachedShape, cachedProperty);
            return impl.removeKey(object, cachedShape, key);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        protected KeyCacheNode insertIntoRemoveKeyCache(Shape cachedShape, Property cachedProperty) {
            CompilerAsserts.neverPartOfCompilation();
            if (!cachedShape.isValid()) {
                return Generic.instance();
            }
            Lock lock = this.getLock();
            lock.lock();
            try {
                MutateCacheData tail = DynamicObjectLibraryImpl.filterValid(this.cache);
                ShapeImpl oldShape = (ShapeImpl)cachedShape;
                ShapeImpl newShape = oldShape.removeProperty(cachedProperty);
                if (!oldShape.isValid()) {
                    Generic generic = Generic.instance();
                    return generic;
                }
                RemovePlan removePlan = null;
                if (!oldShape.isShared()) {
                    removePlan = DynamicObjectLibraryImpl.prepareRemove(oldShape, newShape);
                }
                Assumption newShapeValid = SpecificKey.getShapeValidAssumption(oldShape, newShape);
                this.cache = new RemovePropertyCacheData(newShape, newShapeValid, removePlan, tail);
                SpecificKey specificKey = this;
                return specificKey;
            }
            finally {
                lock.unlock();
            }
        }

        private static Assumption getShapeValidAssumption(Shape oldShape, Shape newShape) {
            if (oldShape == newShape) {
                return Assumption.ALWAYS_VALID;
            }
            return newShape.isValid() ? newShape.getValidAssumption() : Assumption.NEVER_VALID;
        }

        static final class MissingKeyIdentity
        extends MissingKey {
            MissingKeyIdentity(Object key, KeyCacheEntry next) {
                super(key, next);
            }

            @Override
            public boolean acceptsKey(Object key) {
                return this.cachedKey == key;
            }

            @Override
            boolean isIdentity() {
                return true;
            }
        }

        static final class ExistingKeyIdentity
        extends ExistingKey {
            ExistingKeyIdentity(Object key, Property property, KeyCacheEntry next) {
                super(key, property, next);
            }

            @Override
            public boolean acceptsKey(Object key) {
                return this.cachedKey == key;
            }

            @Override
            boolean isIdentity() {
                return true;
            }
        }

        static class MissingKey
        extends SpecificKey {
            MissingKey(Object key, KeyCacheEntry next) {
                super(key, next);
            }

            @Override
            public Object getOrDefault(DynamicObject object, Shape cachedShape, Object key, Object defaultValue) {
                CompilerAsserts.partialEvaluationConstant(cachedShape);
                assert (this.assertCachedKeyAndShapeForRead(object, cachedShape, key));
                return defaultValue;
            }

            @Override
            public boolean put(DynamicObject object, Shape cachedShape, Object key, Object value2, long putFlags) {
                CompilerAsserts.partialEvaluationConstant(cachedShape);
                assert (this.assertCachedKeyAndShapeForWrite(object, cachedShape, key));
                return this.putImpl(object, cachedShape, key, value2, putFlags, null);
            }

            @Override
            public boolean putInt(DynamicObject object, Shape cachedShape, Object key, int value2, long putFlags) {
                CompilerAsserts.partialEvaluationConstant(cachedShape);
                assert (this.assertCachedKeyAndShapeForWrite(object, cachedShape, key));
                return this.putIntImpl(object, cachedShape, key, value2, putFlags, null);
            }

            @Override
            public boolean putLong(DynamicObject object, Shape cachedShape, Object key, long value2, long putFlags) {
                CompilerAsserts.partialEvaluationConstant(cachedShape);
                assert (this.assertCachedKeyAndShapeForWrite(object, cachedShape, key));
                return this.putLongImpl(object, cachedShape, key, value2, putFlags, null);
            }

            @Override
            public boolean putDouble(DynamicObject object, Shape cachedShape, Object key, double value2, long putFlags) {
                CompilerAsserts.partialEvaluationConstant(cachedShape);
                assert (this.assertCachedKeyAndShapeForWrite(object, cachedShape, key));
                return this.putDoubleImpl(object, cachedShape, key, value2, putFlags, null);
            }

            @Override
            public boolean containsKey(DynamicObject object, Shape cachedShape, Object key) {
                CompilerAsserts.partialEvaluationConstant(cachedShape);
                assert (this.assertCachedKeyAndShapeForRead(object, cachedShape, key));
                return false;
            }

            @Override
            public Property getProperty(DynamicObject object, Shape cachedShape, Object key) {
                CompilerAsserts.partialEvaluationConstant(cachedShape);
                assert (this.assertCachedKeyAndShapeForRead(object, cachedShape, key));
                return null;
            }

            @Override
            public int getIntOrDefault(DynamicObject object, Shape cachedShape, Object key, Object defaultValue) throws UnexpectedResultException {
                return LocationImpl.expectInteger(defaultValue);
            }

            @Override
            public long getLongOrDefault(DynamicObject object, Shape cachedShape, Object key, Object defaultValue) throws UnexpectedResultException {
                return LocationImpl.expectLong(defaultValue);
            }

            @Override
            public double getDoubleOrDefault(DynamicObject object, Shape cachedShape, Object key, Object defaultValue) throws UnexpectedResultException {
                return LocationImpl.expectDouble(defaultValue);
            }

            @Override
            public boolean setPropertyFlags(DynamicObject object, Shape cachedShape, Object key, int propertyFlags) {
                CompilerAsserts.partialEvaluationConstant(cachedShape);
                assert (this.assertCachedKeyAndShapeForWrite(object, cachedShape, key));
                return false;
            }

            @Override
            public boolean removeKey(DynamicObject object, Shape cachedShape, Object key) {
                CompilerAsserts.partialEvaluationConstant(cachedShape);
                assert (this.assertCachedKeyAndShapeForWrite(object, cachedShape, key));
                return false;
            }
        }

        static class ExistingKey
        extends SpecificKey {
            final Property cachedProperty;

            ExistingKey(Object key, Property property, KeyCacheEntry next) {
                super(key, next);
                this.cachedProperty = property;
            }

            private static boolean guard(DynamicObject object, Shape cachedShape) {
                return object.getShape() == cachedShape;
            }

            @Override
            public Object getOrDefault(DynamicObject object, Shape cachedShape, Object key, Object defaultValue) {
                CompilerAsserts.partialEvaluationConstant(cachedShape);
                assert (this.assertCachedKeyAndShapeForRead(object, cachedShape, key));
                return DynamicObjectLibraryImpl.getLocation(this.cachedProperty).get(object, ExistingKey.guard(object, cachedShape));
            }

            @Override
            public int getIntOrDefault(DynamicObject object, Shape cachedShape, Object key, Object defaultValue) throws UnexpectedResultException {
                CompilerAsserts.partialEvaluationConstant(cachedShape);
                assert (this.assertCachedKeyAndShapeForRead(object, cachedShape, key));
                return DynamicObjectLibraryImpl.getLocation(this.cachedProperty).getInt(object, ExistingKey.guard(object, cachedShape));
            }

            @Override
            public long getLongOrDefault(DynamicObject object, Shape cachedShape, Object key, Object defaultValue) throws UnexpectedResultException {
                CompilerAsserts.partialEvaluationConstant(cachedShape);
                assert (this.assertCachedKeyAndShapeForRead(object, cachedShape, key));
                return DynamicObjectLibraryImpl.getLocation(this.cachedProperty).getLong(object, ExistingKey.guard(object, cachedShape));
            }

            @Override
            public double getDoubleOrDefault(DynamicObject object, Shape cachedShape, Object key, Object defaultValue) throws UnexpectedResultException {
                CompilerAsserts.partialEvaluationConstant(cachedShape);
                assert (this.assertCachedKeyAndShapeForRead(object, cachedShape, key));
                return DynamicObjectLibraryImpl.getLocation(this.cachedProperty).getDouble(object, ExistingKey.guard(object, cachedShape));
            }

            @Override
            public boolean put(DynamicObject object, Shape cachedShape, Object key, Object value2, long putFlags) {
                CompilerAsserts.partialEvaluationConstant(cachedShape);
                assert (this.assertCachedKeyAndShapeForWrite(object, cachedShape, key));
                return this.putImpl(object, cachedShape, key, value2, putFlags, this.cachedProperty);
            }

            @Override
            public boolean putInt(DynamicObject object, Shape cachedShape, Object key, int value2, long putFlags) {
                CompilerAsserts.partialEvaluationConstant(cachedShape);
                assert (this.assertCachedKeyAndShapeForWrite(object, cachedShape, key));
                return this.putIntImpl(object, cachedShape, key, value2, putFlags, this.cachedProperty);
            }

            @Override
            public boolean putLong(DynamicObject object, Shape cachedShape, Object key, long value2, long putFlags) {
                CompilerAsserts.partialEvaluationConstant(cachedShape);
                assert (this.assertCachedKeyAndShapeForWrite(object, cachedShape, key));
                return this.putLongImpl(object, cachedShape, key, value2, putFlags, this.cachedProperty);
            }

            @Override
            public boolean putDouble(DynamicObject object, Shape cachedShape, Object key, double value2, long putFlags) {
                CompilerAsserts.partialEvaluationConstant(cachedShape);
                assert (this.assertCachedKeyAndShapeForWrite(object, cachedShape, key));
                return this.putDoubleImpl(object, cachedShape, key, value2, putFlags, this.cachedProperty);
            }

            @Override
            public boolean containsKey(DynamicObject object, Shape cachedShape, Object key) {
                CompilerAsserts.partialEvaluationConstant(cachedShape);
                assert (this.assertCachedKeyAndShapeForRead(object, cachedShape, key));
                return true;
            }

            @Override
            public Property getProperty(DynamicObject object, Shape cachedShape, Object key) {
                CompilerAsserts.partialEvaluationConstant(cachedShape);
                assert (this.assertCachedKeyAndShapeForRead(object, cachedShape, key));
                return this.cachedProperty;
            }

            @Override
            public boolean setPropertyFlags(DynamicObject object, Shape cachedShape, Object key, int propertyFlags) {
                CompilerAsserts.partialEvaluationConstant(cachedShape);
                assert (this.assertCachedKeyAndShapeForWrite(object, cachedShape, key));
                return this.setPropertyFlagsImpl(object, cachedShape, key, propertyFlags, this.cachedProperty);
            }

            @Override
            public boolean removeKey(DynamicObject object, Shape cachedShape, Object key) {
                CompilerAsserts.partialEvaluationConstant(cachedShape);
                assert (this.assertCachedKeyAndShapeForWrite(object, cachedShape, key));
                return this.removeKeyImpl(object, cachedShape, key, this.cachedProperty);
            }
        }
    }

    static final class AnyKey
    extends KeyCacheNode {
        @Node.Child
        private KeyCacheEntry keyCache;

        AnyKey(KeyCacheEntry keyCache) {
            this.keyCache = keyCache;
        }

        public static KeyCacheNode create() {
            return new AnyKey(null);
        }

        public static KeyCacheNode create(Object key, Shape cachedShape) {
            return new AnyKey(SpecificKey.create(key, cachedShape, null, true));
        }

        @Override
        @ExplodeLoop
        public Object getOrDefault(DynamicObject object, Shape cachedShape, Object key, Object defaultValue) {
            KeyCacheEntry start2 = this.keyCache;
            if (start2 != KeyCacheNode.getUncached()) {
                KeyCacheEntry c = start2;
                while (c != null) {
                    if (c.acceptsKey(key)) {
                        return c.getOrDefault(object, cachedShape, key, defaultValue);
                    }
                    c = c.next;
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                KeyCacheNode impl = this.insertIntoKeyCache(key, cachedShape);
                if (impl != null) {
                    return impl.getOrDefault(object, cachedShape, key, defaultValue);
                }
            }
            return Generic.instance().getOrDefault(object, cachedShape, key, defaultValue);
        }

        @Override
        @ExplodeLoop
        public int getIntOrDefault(DynamicObject object, Shape cachedShape, Object key, Object defaultValue) throws UnexpectedResultException {
            KeyCacheEntry start2 = this.keyCache;
            if (start2 != KeyCacheNode.getUncached()) {
                KeyCacheEntry c = start2;
                while (c != null) {
                    if (c.acceptsKey(key)) {
                        return c.getIntOrDefault(object, cachedShape, key, defaultValue);
                    }
                    c = c.next;
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                KeyCacheNode impl = this.insertIntoKeyCache(key, cachedShape);
                if (impl != null) {
                    return impl.getIntOrDefault(object, cachedShape, key, defaultValue);
                }
            }
            return Generic.instance().getIntOrDefault(object, cachedShape, key, defaultValue);
        }

        @Override
        @ExplodeLoop
        public long getLongOrDefault(DynamicObject object, Shape cachedShape, Object key, Object defaultValue) throws UnexpectedResultException {
            KeyCacheEntry start2 = this.keyCache;
            if (start2 != KeyCacheNode.getUncached()) {
                KeyCacheEntry c = start2;
                while (c != null) {
                    if (c.acceptsKey(key)) {
                        return c.getLongOrDefault(object, cachedShape, key, defaultValue);
                    }
                    c = c.next;
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                KeyCacheNode impl = this.insertIntoKeyCache(key, cachedShape);
                if (impl != null) {
                    return impl.getLongOrDefault(object, cachedShape, key, defaultValue);
                }
            }
            return Generic.instance().getLongOrDefault(object, cachedShape, key, defaultValue);
        }

        @Override
        @ExplodeLoop
        public double getDoubleOrDefault(DynamicObject object, Shape cachedShape, Object key, Object defaultValue) throws UnexpectedResultException {
            KeyCacheEntry start2 = this.keyCache;
            if (start2 != KeyCacheNode.getUncached()) {
                KeyCacheEntry c = start2;
                while (c != null) {
                    if (c.acceptsKey(key)) {
                        return c.getDoubleOrDefault(object, cachedShape, key, defaultValue);
                    }
                    c = c.next;
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                KeyCacheNode impl = this.insertIntoKeyCache(key, cachedShape);
                if (impl != null) {
                    return impl.getDoubleOrDefault(object, cachedShape, key, defaultValue);
                }
            }
            return Generic.instance().getDoubleOrDefault(object, cachedShape, key, defaultValue);
        }

        @Override
        @ExplodeLoop
        public boolean put(DynamicObject object, Shape cachedShape, Object key, Object value2, long putFlags) {
            KeyCacheEntry start2 = this.keyCache;
            if (start2 != KeyCacheNode.getUncached()) {
                KeyCacheEntry c = start2;
                while (c != null) {
                    if (c.acceptsKey(key)) {
                        return c.put(object, cachedShape, key, value2, putFlags);
                    }
                    c = c.next;
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                KeyCacheNode impl = this.insertIntoKeyCache(key, cachedShape);
                if (impl != null) {
                    return impl.put(object, cachedShape, key, value2, putFlags);
                }
            }
            return Generic.instance().put(object, cachedShape, key, value2, putFlags);
        }

        @Override
        @ExplodeLoop
        public boolean containsKey(DynamicObject object, Shape cachedShape, Object key) {
            KeyCacheEntry start2 = this.keyCache;
            if (start2 != KeyCacheNode.getUncached()) {
                KeyCacheEntry c = start2;
                while (c != null) {
                    if (c.acceptsKey(key)) {
                        return c.containsKey(object, cachedShape, key);
                    }
                    c = c.next;
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                KeyCacheNode impl = this.insertIntoKeyCache(key, cachedShape);
                if (impl != null) {
                    return impl.containsKey(object, cachedShape, key);
                }
            }
            return Generic.instance().containsKey(object, cachedShape, key);
        }

        @Override
        @ExplodeLoop
        public Property getProperty(DynamicObject object, Shape cachedShape, Object key) {
            KeyCacheEntry start2 = this.keyCache;
            if (start2 != KeyCacheNode.getUncached()) {
                KeyCacheEntry c = start2;
                while (c != null) {
                    if (c.acceptsKey(key)) {
                        return c.getProperty(object, cachedShape, key);
                    }
                    c = c.next;
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                KeyCacheNode impl = this.insertIntoKeyCache(key, cachedShape);
                if (impl != null) {
                    return impl.getProperty(object, cachedShape, key);
                }
            }
            return Generic.instance().getProperty(object, cachedShape, key);
        }

        @Override
        @ExplodeLoop
        public boolean setPropertyFlags(DynamicObject object, Shape cachedShape, Object key, int propertyFlags) {
            KeyCacheEntry start2 = this.keyCache;
            if (start2 != KeyCacheNode.getUncached()) {
                KeyCacheEntry c = start2;
                while (c != null) {
                    if (c.acceptsKey(key)) {
                        return c.setPropertyFlags(object, cachedShape, key, propertyFlags);
                    }
                    c = c.next;
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                KeyCacheNode impl = this.insertIntoKeyCache(key, cachedShape);
                if (impl != null) {
                    return impl.setPropertyFlags(object, cachedShape, key, propertyFlags);
                }
            }
            return Generic.instance().setPropertyFlags(object, cachedShape, key, propertyFlags);
        }

        @Override
        @ExplodeLoop
        public boolean removeKey(DynamicObject object, Shape cachedShape, Object key) {
            KeyCacheEntry start2 = this.keyCache;
            if (start2 != KeyCacheNode.getUncached()) {
                KeyCacheEntry c = start2;
                while (c != null) {
                    if (c.acceptsKey(key)) {
                        return c.removeKey(object, cachedShape, key);
                    }
                    c = c.next;
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                KeyCacheNode impl = this.insertIntoKeyCache(key, cachedShape);
                if (impl != null) {
                    return impl.removeKey(object, cachedShape, key);
                }
            }
            return Generic.instance().removeKey(object, cachedShape, key);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private KeyCacheNode insertIntoKeyCache(Object key, Shape cachedShape) {
            CompilerAsserts.neverPartOfCompilation();
            Lock lock = this.getLock();
            lock.lock();
            try {
                KeyCacheEntry tail = this.keyCache;
                int cachedCount = 0;
                boolean generic = false;
                boolean useIdentity = true;
                KeyCacheEntry c = tail;
                while (c != null) {
                    if (c == KeyCacheNode.getUncached()) {
                        generic = true;
                        break;
                    }
                    ++cachedCount;
                    if (c.acceptsKey(key)) {
                        KeyCacheEntry keyCacheEntry = c;
                        return keyCacheEntry;
                    }
                    if (!c.isIdentity()) {
                        useIdentity = false;
                    }
                    c = c.next;
                }
                if (cachedCount > 1 && useIdentity && AnyKey.hasDuplicateCacheKeys(tail, key)) {
                    tail = null;
                    cachedCount = 0;
                    useIdentity = false;
                }
                if (cachedCount >= 3) {
                    generic = true;
                    this.keyCache = KeyCacheNode.getUncached();
                }
                if (generic) {
                    c = null;
                    return c;
                }
                SpecificKey newEntry = SpecificKey.create(key, cachedShape, tail, useIdentity);
                this.insert(newEntry);
                this.keyCache = newEntry;
                AnyKey anyKey = this;
                return anyKey;
            }
            finally {
                lock.unlock();
            }
        }

        private static boolean hasDuplicateCacheKeys(KeyCacheEntry tail, Object key) {
            EconomicSet<Object> keySet = EconomicSet.create();
            KeyCacheEntry c = tail;
            while (c != null) {
                if (c instanceof SpecificKey) {
                    SpecificKey cacheEntry = (SpecificKey)c;
                    if (!keySet.add(cacheEntry.cachedKey)) {
                        return true;
                    }
                }
                c = c.next;
            }
            return !keySet.add(key);
        }
    }

    static final class Generic
    extends KeyCacheEntry {
        private static final Generic INSTANCE = new Generic();

        Generic() {
            super(null);
        }

        static Generic instance() {
            return INSTANCE;
        }

        @Override
        public boolean isAdoptable() {
            return false;
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public Object getOrDefault(DynamicObject object, Shape cachedShape, Object key, Object defaultValue) {
            Property existing = LayoutImpl.ACCESS.getShape(object).getProperty(key);
            if (existing != null) {
                return DynamicObjectLibraryImpl.getLocation(existing).get(object, false);
            }
            return defaultValue;
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public int getIntOrDefault(DynamicObject object, Shape cachedShape, Object key, Object defaultValue) throws UnexpectedResultException {
            Property existing = LayoutImpl.ACCESS.getShape(object).getProperty(key);
            if (existing != null) {
                return DynamicObjectLibraryImpl.getLocation(existing).getInt(object, false);
            }
            return LocationImpl.expectInteger(defaultValue);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public long getLongOrDefault(DynamicObject object, Shape cachedShape, Object key, Object defaultValue) throws UnexpectedResultException {
            Property existing = LayoutImpl.ACCESS.getShape(object).getProperty(key);
            if (existing != null) {
                return DynamicObjectLibraryImpl.getLocation(existing).getLong(object, false);
            }
            return LocationImpl.expectLong(defaultValue);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public double getDoubleOrDefault(DynamicObject object, Shape cachedShape, Object key, Object defaultValue) throws UnexpectedResultException {
            Property existing = LayoutImpl.ACCESS.getShape(object).getProperty(key);
            if (existing != null) {
                return DynamicObjectLibraryImpl.getLocation(existing).getDouble(object, false);
            }
            return LocationImpl.expectDouble(defaultValue);
        }

        @Override
        public boolean put(DynamicObject object, Shape cachedShape, Object key, Object value2, long putFlags) {
            return DynamicObjectLibraryImpl.putUncached(object, key, value2, putFlags);
        }

        @Override
        public boolean containsKey(DynamicObject object, Shape cachedShape, Object key) {
            Property existing = this.getProperty(object, cachedShape, key);
            return existing != null;
        }

        @Override
        public Property getProperty(DynamicObject object, Shape cachedShape, Object key) {
            return LayoutImpl.ACCESS.getShape(object).getProperty(key);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean setPropertyFlags(DynamicObject object, Shape cachedShape, Object key, int propertyFlags) {
            ShapeImpl oldShape = (ShapeImpl)LayoutImpl.ACCESS.getShape(object);
            Property existingProperty = oldShape.getProperty(key);
            if (existingProperty == null) {
                return false;
            }
            if (existingProperty.getFlags() != propertyFlags) {
                DynamicObjectLibraryImpl.updateShapeImpl(object);
                ShapeImpl newShape = DynamicObjectLibraryImpl.changePropertyFlags(oldShape, (PropertyImpl)existingProperty, propertyFlags);
                if (newShape != oldShape) {
                    LayoutImpl.ACCESS.setShape(object, newShape);
                    DynamicObjectLibraryImpl.updateShapeImpl(object);
                }
            }
            return true;
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean removeKey(DynamicObject obj, Shape cachedShape, Object key) {
            ShapeImpl oldShape = (ShapeImpl)cachedShape;
            Property property = oldShape.getProperty(key);
            if (property == null) {
                return false;
            }
            Map<Object, Object> archive = null;
            assert ((archive = LayoutImpl.ACCESS.archive(obj)) != null);
            ShapeImpl newShape = oldShape.removeProperty(property);
            assert (oldShape != newShape);
            assert (LayoutImpl.ACCESS.getShape(obj) == oldShape);
            if (!oldShape.isShared()) {
                RemovePlan plan = DynamicObjectLibraryImpl.prepareRemove(oldShape, newShape);
                plan.execute(obj);
            } else {
                LayoutImpl.ACCESS.setShape(obj, newShape);
            }
            assert (LayoutImpl.ACCESS.verifyValues(obj, archive));
            return true;
        }
    }

    static abstract class KeyCacheEntry
    extends KeyCacheNode {
        @Node.Child
        KeyCacheEntry next;

        KeyCacheEntry(KeyCacheEntry next) {
            this.next = next;
        }

        public boolean acceptsKey(Object key) {
            return true;
        }
    }

    static abstract class KeyCacheNode
    extends Node {
        KeyCacheNode() {
        }

        public abstract Object getOrDefault(DynamicObject var1, Shape var2, Object var3, Object var4);

        public abstract int getIntOrDefault(DynamicObject var1, Shape var2, Object var3, Object var4) throws UnexpectedResultException;

        public abstract long getLongOrDefault(DynamicObject var1, Shape var2, Object var3, Object var4) throws UnexpectedResultException;

        public abstract double getDoubleOrDefault(DynamicObject var1, Shape var2, Object var3, Object var4) throws UnexpectedResultException;

        public abstract boolean put(DynamicObject var1, Shape var2, Object var3, Object var4, long var5);

        public abstract boolean containsKey(DynamicObject var1, Shape var2, Object var3);

        public abstract Property getProperty(DynamicObject var1, Shape var2, Object var3);

        public abstract boolean setPropertyFlags(DynamicObject var1, Shape var2, Object var3, int var4);

        public abstract boolean removeKey(DynamicObject var1, Shape var2, Object var3);

        public boolean putInt(DynamicObject object, Shape cachedShape, Object key, int value2, long putFlags) {
            return this.put(object, cachedShape, key, value2, putFlags);
        }

        public boolean putLong(DynamicObject object, Shape cachedShape, Object key, long value2, long putFlags) {
            return this.put(object, cachedShape, key, value2, putFlags);
        }

        public boolean putDouble(DynamicObject object, Shape cachedShape, Object key, double value2, long putFlags) {
            return this.put(object, cachedShape, key, value2, putFlags);
        }

        boolean isIdentity() {
            return false;
        }

        static KeyCacheNode create(Shape cachedShape, Object key) {
            if (key == null) {
                return KeyCacheNode.getUncached();
            }
            return AnyKey.create(key, cachedShape);
        }

        static KeyCacheEntry getUncached() {
            return Generic.instance();
        }
    }

    private static final class RemovePlan {
        private static final int MAX_UNROLL = 32;
        @CompilerDirectives.CompilationFinal(dimensions=1)
        private final Move[] moves;
        private final boolean canMoveInPlace;
        private final Shape shapeBefore;
        private final Shape shapeAfter;

        RemovePlan(Move[] moves, boolean canMoveInPlace, Shape shapeBefore, Shape shapeAfter) {
            this.moves = moves;
            this.canMoveInPlace = canMoveInPlace;
            this.shapeBefore = shapeBefore;
            this.shapeAfter = shapeAfter;
        }

        void execute(DynamicObject object) {
            CompilerAsserts.partialEvaluationConstant(this.moves.length);
            if (CompilerDirectives.inCompiledCode() && this.moves.length <= 32) {
                this.perform(object);
            } else {
                this.performBoundary(object);
            }
        }

        @ExplodeLoop
        void perform(DynamicObject object) {
            CompilerAsserts.partialEvaluationConstant(this.moves.length);
            if (this.canMoveInPlace) {
                for (int i = this.moves.length - 1; i >= 0; --i) {
                    this.moves[i].perform(object);
                    if (i != 0) continue;
                    this.moves[i].clear(object);
                }
                LayoutImpl.ACCESS.trimToSize(object, this.shapeBefore, this.shapeAfter);
                LayoutImpl.ACCESS.setShape(object, this.shapeAfter);
            } else {
                int i;
                Object[] tempValues = new Object[this.moves.length];
                for (i = this.moves.length - 1; i >= 0; --i) {
                    tempValues[i] = this.moves[i].performGet(object);
                    this.moves[i].clear(object);
                }
                LayoutImpl.ACCESS.resize(object, this.shapeBefore, this.shapeAfter);
                for (i = this.moves.length - 1; i >= 0; --i) {
                    this.moves[i].performSet(object, tempValues[i]);
                }
                LayoutImpl.ACCESS.setShape(object, this.shapeAfter);
            }
        }

        @CompilerDirectives.TruffleBoundary
        void performBoundary(DynamicObject object) {
            this.perform(object);
        }
    }

    private static final class Move
    implements Comparable<Move> {
        private final LocationImpl fromLoc;
        private final LocationImpl toLoc;
        private final int fromOrd;
        private final int toOrd;

        Move(LocationImpl fromLoc, LocationImpl toLoc, int fromOrd, int toOrd) {
            this.fromLoc = fromLoc;
            this.toLoc = toLoc;
            this.fromOrd = fromOrd;
            this.toOrd = toOrd;
        }

        void perform(DynamicObject obj) {
            this.performSet(obj, this.performGet(obj));
        }

        Object performGet(DynamicObject obj) {
            return this.fromLoc.get(obj, false);
        }

        void performSet(DynamicObject obj, Object value2) {
            this.toLoc.setSafe(obj, value2, false, true);
        }

        void clear(DynamicObject obj) {
            this.fromLoc.clear(obj);
        }

        public String toString() {
            CompilerAsserts.neverPartOfCompilation();
            return this.fromLoc + " => " + this.toLoc;
        }

        @Override
        public int compareTo(Move other) {
            int order = Integer.compare(this.fromOrd, other.fromOrd);
            assert (order == Integer.compare(this.toOrd, other.toOrd));
            return -order;
        }
    }
}

