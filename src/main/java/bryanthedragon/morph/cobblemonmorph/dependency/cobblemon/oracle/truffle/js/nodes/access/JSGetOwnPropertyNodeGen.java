/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.invoke.VarHandle
 */
package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.Assumption;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.object.Property;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.profiles.ValueProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.access.JSGetOwnPropertyNode;
import com.oracle.truffle.js.nodes.cast.ToArrayIndexNode;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.builtins.JSClass;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.PropertyDescriptor;
import com.oracle.truffle.js.runtime.util.JSClassProfile;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=JSGetOwnPropertyNode.class)
public final class JSGetOwnPropertyNodeGen
extends JSGetOwnPropertyNode
implements Introspection.Provider {
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @CompilerDirectives.CompilationFinal
    private volatile int exclude_;
    @Node.Child
    private JSGetOwnPropertyNode.UsesOrdinaryGetOwnPropertyNode usesOrdinaryGetOwnProperty;
    @Node.Child
    private ArrayData array_cache;
    @CompilerDirectives.CompilationFinal
    private ConditionProfile getOwnPropertyString_stringCaseProfile_;
    @Node.Child
    private CachedOrdinaryData cachedOrdinary_cache;
    @CompilerDirectives.CompilationFinal
    private JSClassProfile generic_jsclassProfile_;

    private JSGetOwnPropertyNodeGen(boolean needValue, boolean needEnumerability, boolean needConfigurability, boolean needWritability, boolean allowCaching) {
        super(needValue, needEnumerability, needConfigurability, needWritability, allowCaching);
    }

    @Override
    @ExplodeLoop
    public PropertyDescriptor execute(JSDynamicObject arg0Value, Object arg1Value) {
        int state_0 = this.state_0_;
        if (state_0 != 0) {
            ArrayData s0_;
            if ((state_0 & 1) != 0 && (s0_ = this.array_cache) != null && JSGuards.isJSArray(arg0Value)) {
                return this.array(arg0Value, arg1Value, s0_.toArrayIndexNode_, s0_.noSuchElementBranch_, s0_.typeProfile_);
            }
            if ((state_0 & 2) != 0 && JSGuards.isJSString(arg0Value)) {
                return this.getOwnPropertyString(arg0Value, arg1Value, this.getOwnPropertyString_stringCaseProfile_);
            }
            if ((state_0 & 4) != 0) {
                assert (this.allowCaching);
                CachedOrdinaryData s2_ = this.cachedOrdinary_cache;
                while (s2_ != null) {
                    if (!Assumption.isValidAssumption(s2_.assumption0_)) {
                        CompilerDirectives.transferToInterpreterAndInvalidate();
                        this.removeCachedOrdinary_(s2_);
                        return this.executeAndSpecialize(arg0Value, arg1Value);
                    }
                    assert (s2_.cachedJSClass_ != null);
                    if (JSRuntime.propertyKeyEquals(s2_.equalsNode_, s2_.cachedPropertyKey_, arg1Value) && s2_.cachedShape_ == arg0Value.getShape()) {
                        return this.cachedOrdinary(arg0Value, arg1Value, s2_.cachedJSClass_, s2_.cachedShape_, s2_.cachedPropertyKey_, s2_.cachedProperty_, s2_.equalsNode_);
                    }
                    s2_ = s2_.next_;
                }
            }
            if ((state_0 & 8) != 0 && this.usesOrdinaryGetOwnProperty.execute(arg0Value)) {
                return this.uncachedOrdinary(arg0Value, arg1Value, this.usesOrdinaryGetOwnProperty);
            }
            if (!((state_0 & 0x10) == 0 || this.usesOrdinaryGetOwnProperty.execute(arg0Value) || JSGuards.isJSArray(arg0Value) || JSGuards.isJSString(arg0Value))) {
                return JSGetOwnPropertyNode.generic(arg0Value, arg1Value, this.generic_jsclassProfile_, this.usesOrdinaryGetOwnProperty);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arg0Value, arg1Value);
    }

    private PropertyDescriptor executeAndSpecialize(JSDynamicObject arg0Value, Object arg1Value) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            JSGetOwnPropertyNode.UsesOrdinaryGetOwnPropertyNode generic_usesOrdinaryGetOwnProperty__;
            Object uncachedOrdinary_usesOrdinaryGetOwnProperty__;
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (JSGuards.isJSArray(arg0Value)) {
                ArrayData s0_ = super.insert(new ArrayData());
                s0_.toArrayIndexNode_ = s0_.insertAccessor(ToArrayIndexNode.create());
                s0_.noSuchElementBranch_ = BranchProfile.create();
                s0_.typeProfile_ = ValueProfile.createIdentityProfile();
                VarHandle.storeStoreFence();
                this.array_cache = s0_;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                PropertyDescriptor propertyDescriptor = this.array(arg0Value, arg1Value, s0_.toArrayIndexNode_, s0_.noSuchElementBranch_, s0_.typeProfile_);
                return propertyDescriptor;
            }
            if (JSGuards.isJSString(arg0Value)) {
                this.getOwnPropertyString_stringCaseProfile_ = ConditionProfile.createBinaryProfile();
                this.state_0_ = state_0 |= 2;
                lock.unlock();
                hasLock = false;
                PropertyDescriptor s0_ = this.getOwnPropertyString(arg0Value, arg1Value, this.getOwnPropertyString_stringCaseProfile_);
                return s0_;
            }
            if (exclude == 0 && this.allowCaching) {
                Object cachedJSClass__;
                int count2_ = 0;
                CachedOrdinaryData s2_ = this.cachedOrdinary_cache;
                if ((state_0 & 4) != 0) {
                    while (s2_ != null) {
                        assert (s2_.cachedJSClass_ != null);
                        if (JSRuntime.propertyKeyEquals(s2_.equalsNode_, s2_.cachedPropertyKey_, arg1Value) && s2_.cachedShape_ == arg0Value.getShape() && Assumption.isValidAssumption(s2_.assumption0_)) break;
                        s2_ = s2_.next_;
                        ++count2_;
                    }
                }
                if (s2_ == null && (cachedJSClass__ = JSGetOwnPropertyNode.getJSClassIfOrdinary(arg0Value)) != null) {
                    Assumption assumption0;
                    Shape cachedShape__;
                    Object cachedPropertyKey__ = arg1Value;
                    TruffleString.EqualNode equalsNode__ = super.insert(TruffleString.EqualNode.create());
                    if (JSRuntime.propertyKeyEquals(equalsNode__, cachedPropertyKey__, arg1Value) && (cachedShape__ = arg0Value.getShape()) == arg0Value.getShape() && Assumption.isValidAssumption(assumption0 = cachedShape__.getValidAssumption()) && count2_ < 3) {
                        s2_ = super.insert(new CachedOrdinaryData(this.cachedOrdinary_cache));
                        s2_.cachedJSClass_ = cachedJSClass__;
                        s2_.cachedShape_ = cachedShape__;
                        s2_.cachedPropertyKey_ = cachedPropertyKey__;
                        s2_.cachedProperty_ = cachedShape__.getProperty(arg1Value);
                        s2_.equalsNode_ = s2_.insertAccessor(equalsNode__);
                        s2_.assumption0_ = assumption0;
                        VarHandle.storeStoreFence();
                        this.cachedOrdinary_cache = s2_;
                        this.state_0_ = state_0 |= 4;
                    }
                }
                if (s2_ != null) {
                    lock.unlock();
                    hasLock = false;
                    cachedJSClass__ = this.cachedOrdinary(arg0Value, arg1Value, s2_.cachedJSClass_, s2_.cachedShape_, s2_.cachedPropertyKey_, s2_.cachedProperty_, s2_.equalsNode_);
                    return cachedJSClass__;
                }
            }
            boolean UncachedOrdinary_duplicateFound_ = false;
            if ((state_0 & 8) != 0 && this.usesOrdinaryGetOwnProperty.execute(arg0Value)) {
                UncachedOrdinary_duplicateFound_ = true;
            }
            if (!UncachedOrdinary_duplicateFound_ && ((JSGetOwnPropertyNode.UsesOrdinaryGetOwnPropertyNode)(uncachedOrdinary_usesOrdinaryGetOwnProperty__ = super.insert(this.usesOrdinaryGetOwnProperty == null ? JSGetOwnPropertyNode.UsesOrdinaryGetOwnPropertyNode.create() : this.usesOrdinaryGetOwnProperty))).execute(arg0Value) && (state_0 & 8) == 0) {
                if (this.usesOrdinaryGetOwnProperty == null) {
                    JSGetOwnPropertyNode.UsesOrdinaryGetOwnPropertyNode uncachedOrdinary_usesOrdinaryGetOwnProperty___check = (JSGetOwnPropertyNode.UsesOrdinaryGetOwnPropertyNode)super.insert(uncachedOrdinary_usesOrdinaryGetOwnProperty__);
                    if (uncachedOrdinary_usesOrdinaryGetOwnProperty___check == null) {
                        throw new AssertionError((Object)"Specialization 'uncachedOrdinary(JSDynamicObject, Object, UsesOrdinaryGetOwnPropertyNode)' contains a shared cache with name 'usesOrdinaryGetOwnProperty' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                    }
                    this.usesOrdinaryGetOwnProperty = uncachedOrdinary_usesOrdinaryGetOwnProperty___check;
                }
                this.exclude_ = exclude |= 1;
                this.cachedOrdinary_cache = null;
                state_0 &= 0xFFFFFFFB;
                this.state_0_ = state_0 |= 8;
                UncachedOrdinary_duplicateFound_ = true;
            }
            if (UncachedOrdinary_duplicateFound_) {
                lock.unlock();
                hasLock = false;
                uncachedOrdinary_usesOrdinaryGetOwnProperty__ = this.uncachedOrdinary(arg0Value, arg1Value, this.usesOrdinaryGetOwnProperty);
                return uncachedOrdinary_usesOrdinaryGetOwnProperty__;
            }
            boolean Generic_duplicateFound_ = false;
            if (!((state_0 & 0x10) == 0 || this.usesOrdinaryGetOwnProperty.execute(arg0Value) || JSGuards.isJSArray(arg0Value) || JSGuards.isJSString(arg0Value))) {
                Generic_duplicateFound_ = true;
            }
            if (!(Generic_duplicateFound_ || (generic_usesOrdinaryGetOwnProperty__ = super.insert(this.usesOrdinaryGetOwnProperty == null ? JSGetOwnPropertyNode.UsesOrdinaryGetOwnPropertyNode.create() : this.usesOrdinaryGetOwnProperty)).execute(arg0Value) || JSGuards.isJSArray(arg0Value) || JSGuards.isJSString(arg0Value) || (state_0 & 0x10) != 0)) {
                this.generic_jsclassProfile_ = JSClassProfile.create();
                if (this.usesOrdinaryGetOwnProperty == null) {
                    JSGetOwnPropertyNode.UsesOrdinaryGetOwnPropertyNode generic_usesOrdinaryGetOwnProperty___check = super.insert(generic_usesOrdinaryGetOwnProperty__);
                    if (generic_usesOrdinaryGetOwnProperty___check == null) {
                        throw new AssertionError((Object)"Specialization 'generic(JSDynamicObject, Object, JSClassProfile, UsesOrdinaryGetOwnPropertyNode)' contains a shared cache with name 'usesOrdinaryGetOwnProperty' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                    }
                    this.usesOrdinaryGetOwnProperty = generic_usesOrdinaryGetOwnProperty___check;
                }
                this.state_0_ = state_0 |= 0x10;
                Generic_duplicateFound_ = true;
            }
            if (Generic_duplicateFound_) {
                lock.unlock();
                hasLock = false;
                PropertyDescriptor propertyDescriptor = JSGetOwnPropertyNode.generic(arg0Value, arg1Value, this.generic_jsclassProfile_, this.usesOrdinaryGetOwnProperty);
                return propertyDescriptor;
            }
            throw new UnsupportedSpecializationException(this, new Node[]{null, null}, arg0Value, arg1Value);
        }
        finally {
            if (hasLock) {
                lock.unlock();
            }
        }
    }

    @Override
    public NodeCost getCost() {
        CachedOrdinaryData s2_;
        int state_0 = this.state_0_;
        if (state_0 == 0) {
            return NodeCost.UNINITIALIZED;
        }
        if ((state_0 & state_0 - 1) == 0 && ((s2_ = this.cachedOrdinary_cache) == null || s2_.next_ == null)) {
            return NodeCost.MONOMORPHIC;
        }
        return NodeCost.POLYMORPHIC;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void removeCachedOrdinary_(Object s2_) {
        Lock lock = this.getLock();
        lock.lock();
        try {
            CachedOrdinaryData prev = null;
            CachedOrdinaryData cur = this.cachedOrdinary_cache;
            while (cur != null) {
                if (cur == s2_) {
                    if (prev == null) {
                        this.cachedOrdinary_cache = cur.next_;
                        this.adoptChildren();
                        break;
                    }
                    prev.next_ = cur.next_;
                    prev.adoptChildren();
                    break;
                }
                prev = cur;
                cur = cur.next_;
            }
            if (this.cachedOrdinary_cache == null) {
                this.state_0_ &= 0xFFFFFFFB;
            }
        }
        finally {
            lock.unlock();
        }
    }

    @Override
    public Introspection getIntrospectionData() {
        ArrayList<List<Object>> cached;
        Object[] data = new Object[6];
        data[0] = 0;
        int state_0 = this.state_0_;
        int exclude = this.exclude_;
        Object[] s = new Object[3];
        s[0] = "array";
        if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList<List<Object>>();
            ArrayData s0_ = this.array_cache;
            if (s0_ != null) {
                cached.add(Arrays.asList(s0_.toArrayIndexNode_, s0_.noSuchElementBranch_, s0_.typeProfile_));
            }
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[1] = s;
        s = new Object[3];
        s[0] = "getOwnPropertyString";
        if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.getOwnPropertyString_stringCaseProfile_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[2] = s;
        s = new Object[3];
        s[0] = "cachedOrdinary";
        if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            CachedOrdinaryData s2_ = this.cachedOrdinary_cache;
            while (s2_ != null) {
                cached.add(Arrays.asList(s2_.cachedJSClass_, s2_.cachedShape_, s2_.cachedPropertyKey_, s2_.cachedProperty_, s2_.equalsNode_));
                s2_ = s2_.next_;
            }
            s[2] = cached;
        } else {
            s[1] = exclude != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
        }
        data[3] = s;
        s = new Object[3];
        s[0] = "uncachedOrdinary";
        if ((state_0 & 8) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.usesOrdinaryGetOwnProperty));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[4] = s;
        s = new Object[3];
        s[0] = "generic";
        if ((state_0 & 0x10) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.generic_jsclassProfile_, this.usesOrdinaryGetOwnProperty));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[5] = s;
        return Introspection.Provider.create(data);
    }

    public static JSGetOwnPropertyNode create(boolean needValue, boolean needEnumerability, boolean needConfigurability, boolean needWritability, boolean allowCaching) {
        return new JSGetOwnPropertyNodeGen(needValue, needEnumerability, needConfigurability, needWritability, allowCaching);
    }

    @GeneratedBy(value=JSGetOwnPropertyNode.GetPropertyProxyValueNode.class)
    public static final class GetPropertyProxyValueNodeGen
    extends JSGetOwnPropertyNode.GetPropertyProxyValueNode
    implements Introspection.Provider {
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private volatile int exclude_;
        @CompilerDirectives.CompilationFinal
        private CachedData cached_cache;

        private GetPropertyProxyValueNodeGen() {
        }

        @Override
        @ExplodeLoop
        public Object execute(JSDynamicObject arg0Value, Object arg1Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
                if ((state_0 & 1) != 0) {
                    CachedData s0_ = this.cached_cache;
                    while (s0_ != null) {
                        if (arg1Value.getClass() == s0_.cachedClass_) {
                            return JSGetOwnPropertyNode.GetPropertyProxyValueNode.doCached(arg0Value, arg1Value, s0_.cachedClass_);
                        }
                        s0_ = s0_.next_;
                    }
                }
                if ((state_0 & 2) != 0) {
                    return JSGetOwnPropertyNode.GetPropertyProxyValueNode.doUncached(arg0Value, arg1Value);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private Object executeAndSpecialize(JSDynamicObject arg0Value, Object arg1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                int exclude = this.exclude_;
                if (exclude == 0) {
                    int count0_ = 0;
                    CachedData s0_ = this.cached_cache;
                    if ((state_0 & 1) != 0) {
                        while (s0_ != null && arg1Value.getClass() != s0_.cachedClass_) {
                            s0_ = s0_.next_;
                            ++count0_;
                        }
                    }
                    if (s0_ == null) {
                        Class<?> cachedClass__ = arg1Value.getClass();
                        if (arg1Value.getClass() == cachedClass__ && count0_ < 5) {
                            s0_ = new CachedData(this.cached_cache);
                            s0_.cachedClass_ = cachedClass__;
                            VarHandle.storeStoreFence();
                            this.cached_cache = s0_;
                            this.state_0_ = state_0 |= 1;
                        }
                    }
                    if (s0_ != null) {
                        lock.unlock();
                        hasLock = false;
                        Object object = JSGetOwnPropertyNode.GetPropertyProxyValueNode.doCached(arg0Value, arg1Value, s0_.cachedClass_);
                        return object;
                    }
                }
                this.exclude_ = exclude |= 1;
                this.cached_cache = null;
                state_0 &= 0xFFFFFFFE;
                this.state_0_ = state_0 |= 2;
                lock.unlock();
                hasLock = false;
                Object object = JSGetOwnPropertyNode.GetPropertyProxyValueNode.doUncached(arg0Value, arg1Value);
                return object;
            }
            finally {
                if (hasLock) {
                    lock.unlock();
                }
            }
        }

        @Override
        public NodeCost getCost() {
            CachedData s0_;
            int state_0 = this.state_0_;
            if (state_0 == 0) {
                return NodeCost.UNINITIALIZED;
            }
            if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.cached_cache) == null || s0_.next_ == null)) {
                return NodeCost.MONOMORPHIC;
            }
            return NodeCost.POLYMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            Object[] data = new Object[3];
            data[0] = 0;
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            Object[] s = new Object[3];
            s[0] = "doCached";
            if ((state_0 & 1) != 0) {
                s[1] = (byte)1;
                ArrayList<List<Class>> cached = new ArrayList<List<Class>>();
                CachedData s0_ = this.cached_cache;
                while (s0_ != null) {
                    cached.add(Arrays.asList(s0_.cachedClass_));
                    s0_ = s0_.next_;
                }
                s[2] = cached;
            } else {
                s[1] = exclude != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
            }
            data[1] = s;
            s = new Object[3];
            s[0] = "doUncached";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        public static JSGetOwnPropertyNode.GetPropertyProxyValueNode create() {
            return new GetPropertyProxyValueNodeGen();
        }

        @GeneratedBy(value=JSGetOwnPropertyNode.GetPropertyProxyValueNode.class)
        private static final class CachedData {
            @CompilerDirectives.CompilationFinal
            CachedData next_;
            @CompilerDirectives.CompilationFinal
            Class<?> cachedClass_;

            CachedData(CachedData next_) {
                this.next_ = next_;
            }
        }
    }

    @GeneratedBy(value=JSGetOwnPropertyNode.UsesOrdinaryGetOwnPropertyNode.class)
    public static final class UsesOrdinaryGetOwnPropertyNodeGen
    extends JSGetOwnPropertyNode.UsesOrdinaryGetOwnPropertyNode
    implements Introspection.Provider {
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private volatile int exclude_;
        @CompilerDirectives.CompilationFinal
        private CachedData cached_cache;

        private UsesOrdinaryGetOwnPropertyNodeGen() {
        }

        @Override
        @ExplodeLoop
        public boolean execute(Object arg0Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
                if ((state_0 & 1) != 0) {
                    CachedData s0_ = this.cached_cache;
                    while (s0_ != null) {
                        if (JSGuards.isReferenceEquals(arg0Value, s0_.cachedJSClass_)) {
                            return JSGetOwnPropertyNode.UsesOrdinaryGetOwnPropertyNode.doCached(arg0Value, s0_.cachedJSClass_);
                        }
                        s0_ = s0_.next_;
                    }
                }
                if ((state_0 & 2) != 0) {
                    return JSGetOwnPropertyNode.UsesOrdinaryGetOwnPropertyNode.doObjectPrototype(arg0Value);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private boolean executeAndSpecialize(Object arg0Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                int exclude = this.exclude_;
                if (exclude == 0) {
                    JSClass cachedJSClass__;
                    int count0_ = 0;
                    CachedData s0_ = this.cached_cache;
                    if ((state_0 & 1) != 0) {
                        while (s0_ != null && !JSGuards.isReferenceEquals(arg0Value, s0_.cachedJSClass_)) {
                            s0_ = s0_.next_;
                            ++count0_;
                        }
                    }
                    if (s0_ == null && JSGuards.isReferenceEquals(arg0Value, cachedJSClass__ = JSGetOwnPropertyNode.UsesOrdinaryGetOwnPropertyNode.asJSClass(arg0Value)) && count0_ < 7) {
                        s0_ = new CachedData(this.cached_cache);
                        s0_.cachedJSClass_ = cachedJSClass__;
                        VarHandle.storeStoreFence();
                        this.cached_cache = s0_;
                        this.state_0_ = state_0 |= 1;
                    }
                    if (s0_ != null) {
                        lock.unlock();
                        hasLock = false;
                        boolean bl = JSGetOwnPropertyNode.UsesOrdinaryGetOwnPropertyNode.doCached(arg0Value, s0_.cachedJSClass_);
                        return bl;
                    }
                }
                this.exclude_ = exclude |= 1;
                this.cached_cache = null;
                state_0 &= 0xFFFFFFFE;
                this.state_0_ = state_0 |= 2;
                lock.unlock();
                hasLock = false;
                boolean bl = JSGetOwnPropertyNode.UsesOrdinaryGetOwnPropertyNode.doObjectPrototype(arg0Value);
                return bl;
            }
            finally {
                if (hasLock) {
                    lock.unlock();
                }
            }
        }

        @Override
        public NodeCost getCost() {
            CachedData s0_;
            int state_0 = this.state_0_;
            if (state_0 == 0) {
                return NodeCost.UNINITIALIZED;
            }
            if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.cached_cache) == null || s0_.next_ == null)) {
                return NodeCost.MONOMORPHIC;
            }
            return NodeCost.POLYMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            Object[] data = new Object[3];
            data[0] = 0;
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            Object[] s = new Object[3];
            s[0] = "doCached";
            if ((state_0 & 1) != 0) {
                s[1] = (byte)1;
                ArrayList<List<JSClass>> cached = new ArrayList<List<JSClass>>();
                CachedData s0_ = this.cached_cache;
                while (s0_ != null) {
                    cached.add(Arrays.asList(s0_.cachedJSClass_));
                    s0_ = s0_.next_;
                }
                s[2] = cached;
            } else {
                s[1] = exclude != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
            }
            data[1] = s;
            s = new Object[3];
            s[0] = "doObjectPrototype";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        public static JSGetOwnPropertyNode.UsesOrdinaryGetOwnPropertyNode create() {
            return new UsesOrdinaryGetOwnPropertyNodeGen();
        }

        @GeneratedBy(value=JSGetOwnPropertyNode.UsesOrdinaryGetOwnPropertyNode.class)
        private static final class CachedData {
            @CompilerDirectives.CompilationFinal
            CachedData next_;
            @CompilerDirectives.CompilationFinal
            JSClass cachedJSClass_;

            CachedData(CachedData next_) {
                this.next_ = next_;
            }
        }
    }

    @GeneratedBy(value=JSGetOwnPropertyNode.class)
    private static final class CachedOrdinaryData
    extends Node {
        @Node.Child
        CachedOrdinaryData next_;
        @CompilerDirectives.CompilationFinal
        JSClass cachedJSClass_;
        @CompilerDirectives.CompilationFinal
        Shape cachedShape_;
        @CompilerDirectives.CompilationFinal
        Object cachedPropertyKey_;
        @CompilerDirectives.CompilationFinal
        Property cachedProperty_;
        @Node.Child
        TruffleString.EqualNode equalsNode_;
        @CompilerDirectives.CompilationFinal
        Assumption assumption0_;

        CachedOrdinaryData(CachedOrdinaryData next_) {
            this.next_ = next_;
        }

        @Override
        public NodeCost getCost() {
            return NodeCost.NONE;
        }

        <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
        }
    }

    @GeneratedBy(value=JSGetOwnPropertyNode.class)
    private static final class ArrayData
    extends Node {
        @Node.Child
        ToArrayIndexNode toArrayIndexNode_;
        @CompilerDirectives.CompilationFinal
        BranchProfile noSuchElementBranch_;
        @CompilerDirectives.CompilationFinal
        ValueProfile typeProfile_;

        ArrayData() {
        }

        @Override
        public NodeCost getCost() {
            return NodeCost.NONE;
        }

        <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
        }
    }
}

