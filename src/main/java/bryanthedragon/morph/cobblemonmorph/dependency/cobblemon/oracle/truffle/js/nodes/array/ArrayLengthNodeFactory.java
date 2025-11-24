/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.invoke.VarHandle
 */
package com.oracle.truffle.js.nodes.array;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.array.ArrayLengthNode;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.builtins.JSArrayBase;
import com.oracle.truffle.js.runtime.builtins.JSTypedArrayObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=ArrayLengthNode.class)
public final class ArrayLengthNodeFactory {

    @GeneratedBy(value=ArrayLengthNode.SetArrayLengthOrDeleteNode.class)
    public static final class SetArrayLengthOrDeleteNodeGen
    extends ArrayLengthNode.SetArrayLengthOrDeleteNode
    implements Introspection.Provider {
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private volatile int exclude_;
        @CompilerDirectives.CompilationFinal
        private CachedData cached_cache;
        @CompilerDirectives.CompilationFinal
        private ConditionProfile generic_mustDeleteProfile_;
        @CompilerDirectives.CompilationFinal
        private ScriptArray.ProfileHolder generic_setLengthProfile_;

        private SetArrayLengthOrDeleteNodeGen(boolean strict) {
            super(strict);
        }

        @Override
        @ExplodeLoop
        public void executeVoid(JSDynamicObject arg0Value, int arg1Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
                if ((state_0 & 1) != 0) {
                    CachedData s0_ = this.cached_cache;
                    while (s0_ != null) {
                        if (s0_.arrayType_.isInstance(ArrayLengthNode.getArrayType(arg0Value))) {
                            this.doCached(arg0Value, arg1Value, s0_.arrayType_, s0_.setLengthProfile_);
                            return;
                        }
                        s0_ = s0_.next_;
                    }
                }
                if ((state_0 & 2) != 0) {
                    this.doGeneric(arg0Value, arg1Value, this.generic_mustDeleteProfile_, this.generic_setLengthProfile_);
                    return;
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.executeAndSpecialize(arg0Value, arg1Value);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private void executeAndSpecialize(JSDynamicObject arg0Value, int arg1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                int exclude = this.exclude_;
                if (exclude == 0) {
                    ScriptArray arrayType__;
                    int count0_ = 0;
                    CachedData s0_ = this.cached_cache;
                    if ((state_0 & 1) != 0) {
                        while (s0_ != null && !s0_.arrayType_.isInstance(ArrayLengthNode.getArrayType(arg0Value))) {
                            s0_ = s0_.next_;
                            ++count0_;
                        }
                    }
                    if (s0_ == null && (arrayType__ = ArrayLengthNode.getArrayType(arg0Value)).isInstance(ArrayLengthNode.getArrayType(arg0Value)) && count0_ < 4) {
                        s0_ = new CachedData(this.cached_cache);
                        s0_.arrayType_ = arrayType__;
                        s0_.setLengthProfile_ = ScriptArray.createSetLengthProfile();
                        VarHandle.storeStoreFence();
                        this.cached_cache = s0_;
                        this.state_0_ = state_0 |= 1;
                    }
                    if (s0_ != null) {
                        lock.unlock();
                        hasLock = false;
                        this.doCached(arg0Value, arg1Value, s0_.arrayType_, s0_.setLengthProfile_);
                        return;
                    }
                }
                this.generic_mustDeleteProfile_ = ConditionProfile.createBinaryProfile();
                this.generic_setLengthProfile_ = ScriptArray.createSetLengthProfile();
                this.exclude_ = exclude |= 1;
                this.cached_cache = null;
                state_0 &= 0xFFFFFFFE;
                this.state_0_ = state_0 |= 2;
                lock.unlock();
                hasLock = false;
                this.doGeneric(arg0Value, arg1Value, this.generic_mustDeleteProfile_, this.generic_setLengthProfile_);
                return;
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
            ArrayList<List<Object>> cached;
            Object[] data = new Object[3];
            data[0] = 0;
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            Object[] s = new Object[3];
            s[0] = "doCached";
            if ((state_0 & 1) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList<List<Object>>();
                CachedData s0_ = this.cached_cache;
                while (s0_ != null) {
                    cached.add(Arrays.asList(s0_.arrayType_, s0_.setLengthProfile_));
                    s0_ = s0_.next_;
                }
                s[2] = cached;
            } else {
                s[1] = exclude != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
            }
            data[1] = s;
            s = new Object[3];
            s[0] = "doGeneric";
            if ((state_0 & 2) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(this.generic_mustDeleteProfile_, this.generic_setLengthProfile_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        public static ArrayLengthNode.SetArrayLengthOrDeleteNode create(boolean strict) {
            return new SetArrayLengthOrDeleteNodeGen(strict);
        }

        @GeneratedBy(value=ArrayLengthNode.SetArrayLengthOrDeleteNode.class)
        private static final class CachedData {
            @CompilerDirectives.CompilationFinal
            CachedData next_;
            @CompilerDirectives.CompilationFinal
            ScriptArray arrayType_;
            @CompilerDirectives.CompilationFinal
            ScriptArray.ProfileHolder setLengthProfile_;

            CachedData(CachedData next_) {
                this.next_ = next_;
            }
        }
    }

    @GeneratedBy(value=ArrayLengthNode.SetArrayLengthNode.class)
    public static final class SetArrayLengthNodeGen
    extends ArrayLengthNode.SetArrayLengthNode
    implements Introspection.Provider {
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private volatile int exclude_;
        @CompilerDirectives.CompilationFinal
        private CachedData cached_cache;
        @CompilerDirectives.CompilationFinal
        private ConditionProfile generic_sealedProfile_;
        @CompilerDirectives.CompilationFinal
        private ScriptArray.ProfileHolder generic_setLengthProfile_;

        private SetArrayLengthNodeGen(boolean strict) {
            super(strict);
        }

        @Override
        @ExplodeLoop
        public void executeVoid(JSDynamicObject arg0Value, int arg1Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
                if ((state_0 & 1) != 0) {
                    CachedData s0_ = this.cached_cache;
                    while (s0_ != null) {
                        if (s0_.arrayType_.isInstance(ArrayLengthNode.getArrayType(arg0Value))) {
                            this.doCached(arg0Value, arg1Value, s0_.arrayType_, s0_.setLengthProfile_);
                            return;
                        }
                        s0_ = s0_.next_;
                    }
                }
                if ((state_0 & 2) != 0) {
                    this.doGeneric(arg0Value, arg1Value, this.generic_sealedProfile_, this.generic_setLengthProfile_);
                    return;
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.executeAndSpecialize(arg0Value, arg1Value);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private void executeAndSpecialize(JSDynamicObject arg0Value, int arg1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                int exclude = this.exclude_;
                if (exclude == 0) {
                    ScriptArray arrayType__;
                    int count0_ = 0;
                    CachedData s0_ = this.cached_cache;
                    if ((state_0 & 1) != 0) {
                        while (s0_ != null && !s0_.arrayType_.isInstance(ArrayLengthNode.getArrayType(arg0Value))) {
                            s0_ = s0_.next_;
                            ++count0_;
                        }
                    }
                    if (s0_ == null && (arrayType__ = ArrayLengthNode.getArrayType(arg0Value)).isInstance(ArrayLengthNode.getArrayType(arg0Value)) && count0_ < 4) {
                        s0_ = new CachedData(this.cached_cache);
                        s0_.arrayType_ = arrayType__;
                        s0_.setLengthProfile_ = ScriptArray.createSetLengthProfile();
                        VarHandle.storeStoreFence();
                        this.cached_cache = s0_;
                        this.state_0_ = state_0 |= 1;
                    }
                    if (s0_ != null) {
                        lock.unlock();
                        hasLock = false;
                        this.doCached(arg0Value, arg1Value, s0_.arrayType_, s0_.setLengthProfile_);
                        return;
                    }
                }
                this.generic_sealedProfile_ = ConditionProfile.createBinaryProfile();
                this.generic_setLengthProfile_ = ScriptArray.createSetLengthProfile();
                this.exclude_ = exclude |= 1;
                this.cached_cache = null;
                state_0 &= 0xFFFFFFFE;
                this.state_0_ = state_0 |= 2;
                lock.unlock();
                hasLock = false;
                this.doGeneric(arg0Value, arg1Value, this.generic_sealedProfile_, this.generic_setLengthProfile_);
                return;
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
            ArrayList<List<Object>> cached;
            Object[] data = new Object[3];
            data[0] = 0;
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            Object[] s = new Object[3];
            s[0] = "doCached";
            if ((state_0 & 1) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList<List<Object>>();
                CachedData s0_ = this.cached_cache;
                while (s0_ != null) {
                    cached.add(Arrays.asList(s0_.arrayType_, s0_.setLengthProfile_));
                    s0_ = s0_.next_;
                }
                s[2] = cached;
            } else {
                s[1] = exclude != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
            }
            data[1] = s;
            s = new Object[3];
            s[0] = "doGeneric";
            if ((state_0 & 2) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(this.generic_sealedProfile_, this.generic_setLengthProfile_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        public static ArrayLengthNode.SetArrayLengthNode create(boolean strict) {
            return new SetArrayLengthNodeGen(strict);
        }

        @GeneratedBy(value=ArrayLengthNode.SetArrayLengthNode.class)
        private static final class CachedData {
            @CompilerDirectives.CompilationFinal
            CachedData next_;
            @CompilerDirectives.CompilationFinal
            ScriptArray arrayType_;
            @CompilerDirectives.CompilationFinal
            ScriptArray.ProfileHolder setLengthProfile_;

            CachedData(CachedData next_) {
                this.next_ = next_;
            }
        }
    }

    @GeneratedBy(value=ArrayLengthNode.ArrayLengthReadNode.class)
    public static final class ArrayLengthReadNodeGen
    extends ArrayLengthNode.ArrayLengthReadNode
    implements Introspection.Provider {
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private volatile int exclude_;
        @CompilerDirectives.CompilationFinal
        private ScriptArray intLength_arrayType_;

        private ArrayLengthReadNodeGen() {
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Object executeObject(JSDynamicObject arg0Value) {
            int state_0 = this.state_0_;
            if ((state_0 & 1) != 0 && arg0Value instanceof JSTypedArrayObject) {
                JSTypedArrayObject arg0Value_ = (JSTypedArrayObject)arg0Value;
                return ArrayLengthNode.ArrayLengthReadNode.doTypedArray(arg0Value_);
            }
            if ((state_0 & 0xE) != 0 && arg0Value instanceof JSArrayBase) {
                JSArrayBase arg0Value_ = (JSArrayBase)arg0Value;
                if ((state_0 & 2) != 0 && this.intLength_arrayType_.isInstance(arg0Value_.getArrayType())) {
                    assert (ArrayLengthNode.ArrayLengthReadNode.isLengthAlwaysInt(this.intLength_arrayType_));
                    return ArrayLengthNode.ArrayLengthReadNode.doIntLength(arg0Value_, this.intLength_arrayType_);
                }
                if ((state_0 & 4) != 0) {
                    try {
                        return ArrayLengthNode.ArrayLengthReadNode.doUncachedIntLength(arg0Value_);
                    }
                    catch (UnexpectedResultException ex) {
                        CompilerDirectives.transferToInterpreterAndInvalidate();
                        Lock lock = this.getLock();
                        lock.lock();
                        try {
                            this.exclude_ |= 2;
                            this.state_0_ &= 0xFFFFFFFB;
                        }
                        finally {
                            lock.unlock();
                        }
                        return ex.getResult();
                    }
                }
                if ((state_0 & 8) != 0) {
                    return ArrayLengthNode.ArrayLengthReadNode.doUncachedLongLength(arg0Value_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int executeInt(JSDynamicObject arg0Value) throws UnexpectedResultException {
            int state_0 = this.state_0_;
            if ((state_0 & 1) != 0 && arg0Value instanceof JSTypedArrayObject) {
                JSTypedArrayObject arg0Value_ = (JSTypedArrayObject)arg0Value;
                return ArrayLengthNode.ArrayLengthReadNode.doTypedArray(arg0Value_);
            }
            if ((state_0 & 6) != 0 && arg0Value instanceof JSArrayBase) {
                JSArrayBase arg0Value_ = (JSArrayBase)arg0Value;
                if ((state_0 & 2) != 0 && this.intLength_arrayType_.isInstance(arg0Value_.getArrayType())) {
                    assert (ArrayLengthNode.ArrayLengthReadNode.isLengthAlwaysInt(this.intLength_arrayType_));
                    return ArrayLengthNode.ArrayLengthReadNode.doIntLength(arg0Value_, this.intLength_arrayType_);
                }
                if ((state_0 & 4) != 0) {
                    try {
                        return ArrayLengthNode.ArrayLengthReadNode.doUncachedIntLength(arg0Value_);
                    }
                    catch (UnexpectedResultException ex) {
                        CompilerDirectives.transferToInterpreterAndInvalidate();
                        Lock lock = this.getLock();
                        lock.lock();
                        try {
                            this.exclude_ |= 2;
                            this.state_0_ &= 0xFFFFFFFB;
                        }
                        finally {
                            lock.unlock();
                        }
                        return JSTypesGen.expectInteger(ex.getResult());
                    }
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectInteger(this.executeAndSpecialize(arg0Value));
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         * Loose catch block
         */
        private Object executeAndSpecialize(JSDynamicObject arg0Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                int exclude = this.exclude_;
                if (arg0Value instanceof JSTypedArrayObject) {
                    JSTypedArrayObject arg0Value_ = (JSTypedArrayObject)arg0Value;
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    Integer n = ArrayLengthNode.ArrayLengthReadNode.doTypedArray(arg0Value_);
                    return n;
                }
                if (arg0Value instanceof JSArrayBase) {
                    Object object;
                    JSArrayBase arg0Value_ = (JSArrayBase)arg0Value;
                    if ((exclude & 1) == 0) {
                        ScriptArray intLength_arrayType__;
                        boolean IntLength_duplicateFound_ = false;
                        if ((state_0 & 2) != 0 && this.intLength_arrayType_.isInstance(arg0Value_.getArrayType())) {
                            assert (ArrayLengthNode.ArrayLengthReadNode.isLengthAlwaysInt(this.intLength_arrayType_));
                            IntLength_duplicateFound_ = true;
                        }
                        if (!IntLength_duplicateFound_ && (intLength_arrayType__ = ArrayLengthNode.getArrayType(arg0Value_)).isInstance(arg0Value_.getArrayType()) && ArrayLengthNode.ArrayLengthReadNode.isLengthAlwaysInt(intLength_arrayType__) && (state_0 & 2) == 0) {
                            this.intLength_arrayType_ = intLength_arrayType__;
                            this.state_0_ = state_0 |= 2;
                            IntLength_duplicateFound_ = true;
                        }
                        if (IntLength_duplicateFound_) {
                            lock.unlock();
                            hasLock = false;
                            object = ArrayLengthNode.ArrayLengthReadNode.doIntLength(arg0Value_, this.intLength_arrayType_);
                            return object;
                        }
                    }
                    if ((exclude & 2) == 0) {
                        this.exclude_ = exclude |= 1;
                        state_0 &= 0xFFFFFFFD;
                        this.state_0_ = state_0 |= 4;
                        try {
                            lock.unlock();
                            hasLock = false;
                            Integer IntLength_duplicateFound_ = ArrayLengthNode.ArrayLengthReadNode.doUncachedIntLength(arg0Value_);
                            return IntLength_duplicateFound_;
                        }
                        catch (UnexpectedResultException ex) {
                            CompilerDirectives.transferToInterpreterAndInvalidate();
                            lock.lock();
                            try {
                                this.exclude_ |= 2;
                                this.state_0_ &= 0xFFFFFFFB;
                            }
                            finally {
                                lock.unlock();
                            }
                            object = ex.getResult();
                            if (hasLock) {
                                lock.unlock();
                            }
                            return object;
                        }
                    }
                    this.exclude_ = exclude |= 3;
                    state_0 &= 0xFFFFFFF9;
                    this.state_0_ = state_0 |= 8;
                    lock.unlock();
                    hasLock = false;
                    Double d = ArrayLengthNode.ArrayLengthReadNode.doUncachedLongLength(arg0Value_);
                    return d;
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
                {
                    catch (Throwable throwable) {
                        throw throwable;
                    }
                }
            }
            finally {
                if (hasLock) {
                    lock.unlock();
                }
            }
        }

        @Override
        public NodeCost getCost() {
            int state_0 = this.state_0_;
            if (state_0 == 0) {
                return NodeCost.UNINITIALIZED;
            }
            if ((state_0 & state_0 - 1) == 0) {
                return NodeCost.MONOMORPHIC;
            }
            return NodeCost.POLYMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            Object[] data = new Object[5];
            data[0] = 0;
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            Object[] s = new Object[3];
            s[0] = "doTypedArray";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "doIntLength";
            if ((state_0 & 2) != 0) {
                s[1] = (byte)1;
                ArrayList<List<ScriptArray>> cached = new ArrayList<List<ScriptArray>>();
                cached.add(Arrays.asList(this.intLength_arrayType_));
                s[2] = cached;
            } else {
                s[1] = (exclude & 1) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
            }
            data[2] = s;
            s = new Object[3];
            s[0] = "doUncachedIntLength";
            s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : ((exclude & 2) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
            data[3] = s;
            s = new Object[3];
            s[0] = "doUncachedLongLength";
            s[1] = (state_0 & 8) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[4] = s;
            return Introspection.Provider.create(data);
        }

        public static ArrayLengthNode.ArrayLengthReadNode create() {
            return new ArrayLengthReadNodeGen();
        }
    }
}

