/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.invoke.VarHandle
 */
package com.oracle.truffle.js.decorators;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.decorators.CreateDecoratorContextObjectNode;
import com.oracle.truffle.js.nodes.access.PropertySetNode;
import com.oracle.truffle.js.nodes.function.ClassElementDefinitionRecord;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.JSFunctionData;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=CreateDecoratorContextObjectNode.class)
public final class CreateDecoratorContextObjectNodeGen
extends CreateDecoratorContextObjectNode
implements Introspection.Provider {
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @CompilerDirectives.CompilationFinal
    private volatile int exclude_;
    @Node.Child
    private PrivateMethodCachedData privateMethodCached_cache;
    @Node.Child
    private PublicMethodCachedData publicMethodCached_cache;
    @Node.Child
    private PropertySetNode methodGeneric_setMagic_;
    @Node.Child
    private FieldCachedData fieldCached_cache;
    @Node.Child
    private PropertySetNode fieldUncached_setMagic_;
    @Node.Child
    private AutoAccessorCachedData autoAccessorCached_cache;
    @Node.Child
    private PropertySetNode autoAccessor_setMagic_;
    @Node.Child
    private GetterCachedData getterCached_cache;
    @Node.Child
    private PropertySetNode getter_setMagic_;
    @Node.Child
    private SetterCachedData setterCached_cache;
    @Node.Child
    private PropertySetNode setter_setMagic_;

    private CreateDecoratorContextObjectNodeGen(JSContext context, boolean isStatic) {
        super(context, isStatic);
    }

    @Override
    @ExplodeLoop
    public JSDynamicObject executeContext(VirtualFrame frameValue, ClassElementDefinitionRecord arg0Value, Object arg1Value, CreateDecoratorContextObjectNode.Record arg2Value) {
        int state_0 = this.state_0_;
        if (state_0 != 0) {
            ClassElementDefinitionRecord arg0Value_;
            if ((state_0 & 1) != 0 && arg0Value instanceof ClassElementDefinitionRecord.PrivateFrameBasedElementDefinitionRecord && (arg0Value_ = (ClassElementDefinitionRecord.PrivateFrameBasedElementDefinitionRecord)arg0Value).isMethod()) {
                PrivateMethodCachedData s0_ = this.privateMethodCached_cache;
                while (s0_ != null) {
                    if (CreateDecoratorContextObjectNode.nameEquals(s0_.strEq_, arg0Value_, s0_.cachedName_)) {
                        assert (s0_.privateName_);
                        return this.doPrivateMethodCached(frameValue, (ClassElementDefinitionRecord.PrivateFrameBasedElementDefinitionRecord)arg0Value_, arg1Value, arg2Value, s0_.cachedName_, s0_.description_, s0_.strEq_, s0_.valueGetterFunctionData_, s0_.privateName_);
                    }
                    s0_ = s0_.next_;
                }
            }
            if ((state_0 & 0x1E) != 0) {
                if ((state_0 & 2) != 0 && arg0Value.isMethod()) {
                    PublicMethodCachedData s1_ = this.publicMethodCached_cache;
                    while (s1_ != null) {
                        if (CreateDecoratorContextObjectNode.nameEquals(s1_.strEq_, arg0Value, s1_.cachedName_)) {
                            assert (!s1_.privateName_);
                            return this.doPublicMethodCached(frameValue, arg0Value, arg1Value, arg2Value, s1_.cachedName_, s1_.description_, s1_.strEq_, s1_.privateName_, s1_.valueGetterFunctionData_);
                        }
                        s1_ = s1_.next_;
                    }
                }
                if ((state_0 & 4) != 0 && arg0Value.isMethod()) {
                    return this.doMethodGeneric(frameValue, arg0Value, arg1Value, arg2Value, this.methodGeneric_setMagic_);
                }
                if ((state_0 & 8) != 0 && arg0Value.isField()) {
                    FieldCachedData s3_ = this.fieldCached_cache;
                    while (s3_ != null) {
                        if (CreateDecoratorContextObjectNode.nameEquals(s3_.strEq_, arg0Value, s3_.cachedName_)) {
                            return this.doFieldCached(frameValue, arg0Value, arg1Value, arg2Value, s3_.cachedName_, s3_.description_, s3_.strEq_, s3_.privateName_, s3_.valueGetterFunctionData_, s3_.valueSetterFunctionData_);
                        }
                        s3_ = s3_.next_;
                    }
                }
                if ((state_0 & 0x10) != 0 && arg0Value.isField()) {
                    return this.doFieldUncached(frameValue, arg0Value, arg1Value, arg2Value, this.fieldUncached_setMagic_);
                }
            }
            if ((state_0 & 0x60) != 0 && arg0Value instanceof ClassElementDefinitionRecord.AutoAccessor) {
                arg0Value_ = (ClassElementDefinitionRecord.AutoAccessor)arg0Value;
                if ((state_0 & 0x20) != 0 && arg0Value_.isAutoAccessor()) {
                    AutoAccessorCachedData s5_ = this.autoAccessorCached_cache;
                    while (s5_ != null) {
                        if (CreateDecoratorContextObjectNode.nameEquals(s5_.strEq_, arg0Value_, s5_.cachedName_)) {
                            return this.doAutoAccessorCached(frameValue, (ClassElementDefinitionRecord.AutoAccessor)arg0Value_, arg1Value, arg2Value, s5_.cachedName_, s5_.description_, s5_.strEq_, s5_.privateName_, s5_.valueGetterFunctionData_, s5_.valueSetterFunctionData_);
                        }
                        s5_ = s5_.next_;
                    }
                }
                if ((state_0 & 0x40) != 0 && arg0Value_.isAutoAccessor()) {
                    return this.doAutoAccessor(frameValue, (ClassElementDefinitionRecord.AutoAccessor)arg0Value_, arg1Value, arg2Value, this.autoAccessor_setMagic_);
                }
            }
            if ((state_0 & 0x780) != 0) {
                if ((state_0 & 0x80) != 0 && arg0Value.isGetter()) {
                    GetterCachedData s7_ = this.getterCached_cache;
                    while (s7_ != null) {
                        if (CreateDecoratorContextObjectNode.nameEquals(s7_.strEq_, arg0Value, s7_.cachedName_)) {
                            assert (!s7_.privateName_);
                            return this.doGetterCached(frameValue, arg0Value, arg1Value, arg2Value, s7_.cachedName_, s7_.description_, s7_.strEq_, s7_.privateName_, s7_.valueGetterFunctionData_);
                        }
                        s7_ = s7_.next_;
                    }
                }
                if ((state_0 & 0x100) != 0 && arg0Value.isGetter()) {
                    return this.doGetter(frameValue, arg0Value, arg1Value, arg2Value, this.getter_setMagic_);
                }
                if ((state_0 & 0x200) != 0 && arg0Value.isSetter()) {
                    SetterCachedData s9_ = this.setterCached_cache;
                    while (s9_ != null) {
                        if (CreateDecoratorContextObjectNode.nameEquals(s9_.strEq_, arg0Value, s9_.cachedName_)) {
                            assert (!s9_.privateName_);
                            return this.doSetterCached(frameValue, arg0Value, arg1Value, arg2Value, s9_.cachedName_, s9_.description_, s9_.strEq_, s9_.privateName_, s9_.valueSetterFunctionData_);
                        }
                        s9_ = s9_.next_;
                    }
                }
                if ((state_0 & 0x400) != 0 && arg0Value.isSetter()) {
                    return this.doSetter(frameValue, arg0Value, arg1Value, arg2Value, this.setter_setMagic_);
                }
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(frameValue, arg0Value, arg1Value, arg2Value);
    }

    private JSDynamicObject executeAndSpecialize(VirtualFrame frameValue, ClassElementDefinitionRecord arg0Value, Object arg1Value, CreateDecoratorContextObjectNode.Record arg2Value) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            ClassElementDefinitionRecord.PrivateFrameBasedElementDefinitionRecord arg0Value_;
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if ((exclude & 1) == 0 && arg0Value instanceof ClassElementDefinitionRecord.PrivateFrameBasedElementDefinitionRecord && (arg0Value_ = (ClassElementDefinitionRecord.PrivateFrameBasedElementDefinitionRecord)arg0Value).isMethod()) {
                Object cachedName__;
                int count0_ = 0;
                PrivateMethodCachedData s0_ = this.privateMethodCached_cache;
                if ((state_0 & 1) != 0) {
                    while (s0_ != null) {
                        if (CreateDecoratorContextObjectNode.nameEquals(s0_.strEq_, arg0Value_, s0_.cachedName_)) {
                            assert (s0_.privateName_);
                            break;
                        }
                        s0_ = s0_.next_;
                        ++count0_;
                    }
                }
                if (s0_ == null) {
                    boolean privateName__;
                    cachedName__ = arg0Value_.getKey();
                    TruffleString.EqualNode strEq__ = super.insert(TruffleString.EqualNode.create());
                    if (CreateDecoratorContextObjectNode.nameEquals(strEq__, arg0Value_, cachedName__) && (privateName__ = arg0Value_.isPrivate()) && count0_ < 3) {
                        s0_ = super.insert(new PrivateMethodCachedData(this.privateMethodCached_cache));
                        s0_.cachedName_ = cachedName__;
                        s0_.description_ = this.getName(cachedName__);
                        s0_.strEq_ = s0_.insertAccessor(strEq__);
                        s0_.valueGetterFunctionData_ = this.createMethodGetterFromFrameCached(arg0Value_);
                        s0_.privateName_ = privateName__;
                        VarHandle.storeStoreFence();
                        this.privateMethodCached_cache = s0_;
                        this.state_0_ = state_0 |= 1;
                    }
                }
                if (s0_ != null) {
                    lock.unlock();
                    hasLock = false;
                    cachedName__ = this.doPrivateMethodCached(frameValue, arg0Value_, arg1Value, arg2Value, s0_.cachedName_, s0_.description_, s0_.strEq_, s0_.valueGetterFunctionData_, s0_.privateName_);
                    return cachedName__;
                }
            }
            if ((exclude & 2) == 0 && arg0Value.isMethod()) {
                Object cachedName__1;
                int count1_ = 0;
                PublicMethodCachedData s1_ = this.publicMethodCached_cache;
                if ((state_0 & 2) != 0) {
                    while (s1_ != null) {
                        if (CreateDecoratorContextObjectNode.nameEquals(s1_.strEq_, arg0Value, s1_.cachedName_)) {
                            assert (!s1_.privateName_);
                            break;
                        }
                        s1_ = s1_.next_;
                        ++count1_;
                    }
                }
                if (s1_ == null) {
                    boolean privateName__1;
                    cachedName__1 = arg0Value.getKey();
                    TruffleString.EqualNode strEq__1 = super.insert(TruffleString.EqualNode.create());
                    if (CreateDecoratorContextObjectNode.nameEquals(strEq__1, arg0Value, cachedName__1) && !(privateName__1 = arg0Value.isPrivate()) && count1_ < 3) {
                        s1_ = super.insert(new PublicMethodCachedData(this.publicMethodCached_cache));
                        s1_.cachedName_ = cachedName__1;
                        s1_.description_ = this.getName(cachedName__1);
                        s1_.strEq_ = s1_.insertAccessor(strEq__1);
                        s1_.privateName_ = privateName__1;
                        s1_.valueGetterFunctionData_ = this.createValueGetterCached(cachedName__1, false);
                        VarHandle.storeStoreFence();
                        this.publicMethodCached_cache = s1_;
                        this.state_0_ = state_0 |= 2;
                    }
                }
                if (s1_ != null) {
                    lock.unlock();
                    hasLock = false;
                    cachedName__1 = this.doPublicMethodCached(frameValue, arg0Value, arg1Value, arg2Value, s1_.cachedName_, s1_.description_, s1_.strEq_, s1_.privateName_, s1_.valueGetterFunctionData_);
                    return cachedName__1;
                }
            }
            if (arg0Value.isMethod()) {
                this.methodGeneric_setMagic_ = super.insert(PropertySetNode.createSetHidden(CreateDecoratorContextObjectNode.MAGIC_KEY, this.context));
                this.exclude_ = exclude |= 3;
                this.privateMethodCached_cache = null;
                this.publicMethodCached_cache = null;
                state_0 &= 0xFFFFFFFC;
                this.state_0_ = state_0 |= 4;
                lock.unlock();
                hasLock = false;
                JSDynamicObject count1_ = this.doMethodGeneric(frameValue, arg0Value, arg1Value, arg2Value, this.methodGeneric_setMagic_);
                return count1_;
            }
            if ((exclude & 4) == 0 && arg0Value.isField()) {
                Object cachedName__2;
                int count3_ = 0;
                FieldCachedData s3_ = this.fieldCached_cache;
                if ((state_0 & 8) != 0) {
                    while (s3_ != null && !CreateDecoratorContextObjectNode.nameEquals(s3_.strEq_, arg0Value, s3_.cachedName_)) {
                        s3_ = s3_.next_;
                        ++count3_;
                    }
                }
                if (s3_ == null) {
                    cachedName__2 = arg0Value.getKey();
                    TruffleString.EqualNode strEq__2 = super.insert(TruffleString.EqualNode.create());
                    if (CreateDecoratorContextObjectNode.nameEquals(strEq__2, arg0Value, cachedName__2) && count3_ < 3) {
                        s3_ = super.insert(new FieldCachedData(this.fieldCached_cache));
                        s3_.cachedName_ = cachedName__2;
                        s3_.description_ = this.getName(cachedName__2);
                        s3_.strEq_ = s3_.insertAccessor(strEq__2);
                        s3_.privateName_ = arg0Value.isPrivate();
                        s3_.valueGetterFunctionData_ = this.createValueGetterCached(cachedName__2, s3_.privateName_);
                        s3_.valueSetterFunctionData_ = this.createValueSetterCached(cachedName__2, s3_.privateName_);
                        VarHandle.storeStoreFence();
                        this.fieldCached_cache = s3_;
                        this.state_0_ = state_0 |= 8;
                    }
                }
                if (s3_ != null) {
                    lock.unlock();
                    hasLock = false;
                    cachedName__2 = this.doFieldCached(frameValue, arg0Value, arg1Value, arg2Value, s3_.cachedName_, s3_.description_, s3_.strEq_, s3_.privateName_, s3_.valueGetterFunctionData_, s3_.valueSetterFunctionData_);
                    return cachedName__2;
                }
            }
            if (arg0Value.isField()) {
                this.fieldUncached_setMagic_ = super.insert(PropertySetNode.createSetHidden(CreateDecoratorContextObjectNode.MAGIC_KEY, this.context));
                this.exclude_ = exclude |= 4;
                this.fieldCached_cache = null;
                state_0 &= 0xFFFFFFF7;
                this.state_0_ = state_0 |= 0x10;
                lock.unlock();
                hasLock = false;
                JSDynamicObject count3_ = this.doFieldUncached(frameValue, arg0Value, arg1Value, arg2Value, this.fieldUncached_setMagic_);
                return count3_;
            }
            if (arg0Value instanceof ClassElementDefinitionRecord.AutoAccessor) {
                ClassElementDefinitionRecord.AutoAccessor arg0Value_2 = (ClassElementDefinitionRecord.AutoAccessor)arg0Value;
                if ((exclude & 8) == 0 && arg0Value_2.isAutoAccessor()) {
                    Object cachedName__3;
                    int count5_ = 0;
                    AutoAccessorCachedData s5_ = this.autoAccessorCached_cache;
                    if ((state_0 & 0x20) != 0) {
                        while (s5_ != null && !CreateDecoratorContextObjectNode.nameEquals(s5_.strEq_, arg0Value_2, s5_.cachedName_)) {
                            s5_ = s5_.next_;
                            ++count5_;
                        }
                    }
                    if (s5_ == null) {
                        cachedName__3 = arg0Value_2.getKey();
                        TruffleString.EqualNode strEq__3 = super.insert(TruffleString.EqualNode.create());
                        if (CreateDecoratorContextObjectNode.nameEquals(strEq__3, arg0Value_2, cachedName__3) && count5_ < 3) {
                            s5_ = super.insert(new AutoAccessorCachedData(this.autoAccessorCached_cache));
                            s5_.cachedName_ = cachedName__3;
                            s5_.description_ = this.getName(cachedName__3);
                            s5_.strEq_ = s5_.insertAccessor(strEq__3);
                            s5_.privateName_ = arg0Value_2.isPrivate();
                            s5_.valueGetterFunctionData_ = this.createValueGetterCached(cachedName__3, s5_.privateName_);
                            s5_.valueSetterFunctionData_ = this.createValueSetterCached(cachedName__3, s5_.privateName_);
                            VarHandle.storeStoreFence();
                            this.autoAccessorCached_cache = s5_;
                            this.state_0_ = state_0 |= 0x20;
                        }
                    }
                    if (s5_ != null) {
                        lock.unlock();
                        hasLock = false;
                        cachedName__3 = this.doAutoAccessorCached(frameValue, arg0Value_2, arg1Value, arg2Value, s5_.cachedName_, s5_.description_, s5_.strEq_, s5_.privateName_, s5_.valueGetterFunctionData_, s5_.valueSetterFunctionData_);
                        return cachedName__3;
                    }
                }
                if (arg0Value_2.isAutoAccessor()) {
                    this.autoAccessor_setMagic_ = super.insert(PropertySetNode.createSetHidden(CreateDecoratorContextObjectNode.MAGIC_KEY, this.context));
                    this.exclude_ = exclude |= 8;
                    this.autoAccessorCached_cache = null;
                    state_0 &= 0xFFFFFFDF;
                    this.state_0_ = state_0 |= 0x40;
                    lock.unlock();
                    hasLock = false;
                    JSDynamicObject count5_ = this.doAutoAccessor(frameValue, arg0Value_2, arg1Value, arg2Value, this.autoAccessor_setMagic_);
                    return count5_;
                }
            }
            if ((exclude & 0x10) == 0 && arg0Value.isGetter()) {
                Object cachedName__4;
                int count7_ = 0;
                GetterCachedData s7_ = this.getterCached_cache;
                if ((state_0 & 0x80) != 0) {
                    while (s7_ != null) {
                        if (CreateDecoratorContextObjectNode.nameEquals(s7_.strEq_, arg0Value, s7_.cachedName_)) {
                            assert (!s7_.privateName_);
                            break;
                        }
                        s7_ = s7_.next_;
                        ++count7_;
                    }
                }
                if (s7_ == null) {
                    boolean privateName__2;
                    cachedName__4 = arg0Value.getKey();
                    TruffleString.EqualNode strEq__4 = super.insert(TruffleString.EqualNode.create());
                    if (CreateDecoratorContextObjectNode.nameEquals(strEq__4, arg0Value, cachedName__4) && !(privateName__2 = arg0Value.isPrivate()) && count7_ < 3) {
                        s7_ = super.insert(new GetterCachedData(this.getterCached_cache));
                        s7_.cachedName_ = cachedName__4;
                        s7_.description_ = this.getName(cachedName__4);
                        s7_.strEq_ = s7_.insertAccessor(strEq__4);
                        s7_.privateName_ = privateName__2;
                        s7_.valueGetterFunctionData_ = this.createValueGetterCached(cachedName__4, privateName__2);
                        VarHandle.storeStoreFence();
                        this.getterCached_cache = s7_;
                        this.state_0_ = state_0 |= 0x80;
                    }
                }
                if (s7_ != null) {
                    lock.unlock();
                    hasLock = false;
                    cachedName__4 = this.doGetterCached(frameValue, arg0Value, arg1Value, arg2Value, s7_.cachedName_, s7_.description_, s7_.strEq_, s7_.privateName_, s7_.valueGetterFunctionData_);
                    return cachedName__4;
                }
            }
            if (arg0Value.isGetter()) {
                this.getter_setMagic_ = super.insert(PropertySetNode.createSetHidden(CreateDecoratorContextObjectNode.MAGIC_KEY, this.context));
                this.exclude_ = exclude |= 0x10;
                this.getterCached_cache = null;
                state_0 &= 0xFFFFFF7F;
                this.state_0_ = state_0 |= 0x100;
                lock.unlock();
                hasLock = false;
                JSDynamicObject count7_ = this.doGetter(frameValue, arg0Value, arg1Value, arg2Value, this.getter_setMagic_);
                return count7_;
            }
            if ((exclude & 0x20) == 0 && arg0Value.isSetter()) {
                int count9_ = 0;
                SetterCachedData s9_ = this.setterCached_cache;
                if ((state_0 & 0x200) != 0) {
                    while (s9_ != null) {
                        if (CreateDecoratorContextObjectNode.nameEquals(s9_.strEq_, arg0Value, s9_.cachedName_)) {
                            assert (!s9_.privateName_);
                            break;
                        }
                        s9_ = s9_.next_;
                        ++count9_;
                    }
                }
                if (s9_ == null) {
                    boolean privateName__3;
                    Object cachedName__5 = arg0Value.getKey();
                    TruffleString.EqualNode strEq__5 = super.insert(TruffleString.EqualNode.create());
                    if (CreateDecoratorContextObjectNode.nameEquals(strEq__5, arg0Value, cachedName__5) && !(privateName__3 = arg0Value.isPrivate()) && count9_ < 3) {
                        s9_ = super.insert(new SetterCachedData(this.setterCached_cache));
                        s9_.cachedName_ = cachedName__5;
                        s9_.description_ = this.getName(cachedName__5);
                        s9_.strEq_ = s9_.insertAccessor(strEq__5);
                        s9_.privateName_ = privateName__3;
                        s9_.valueSetterFunctionData_ = this.createValueSetterCached(cachedName__5, privateName__3);
                        VarHandle.storeStoreFence();
                        this.setterCached_cache = s9_;
                        this.state_0_ = state_0 |= 0x200;
                    }
                }
                if (s9_ != null) {
                    lock.unlock();
                    hasLock = false;
                    JSDynamicObject jSDynamicObject = this.doSetterCached(frameValue, arg0Value, arg1Value, arg2Value, s9_.cachedName_, s9_.description_, s9_.strEq_, s9_.privateName_, s9_.valueSetterFunctionData_);
                    return jSDynamicObject;
                }
            }
            if (arg0Value.isSetter()) {
                this.setter_setMagic_ = super.insert(PropertySetNode.createSetHidden(CreateDecoratorContextObjectNode.MAGIC_KEY, this.context));
                this.exclude_ = exclude |= 0x20;
                this.setterCached_cache = null;
                state_0 &= 0xFFFFFDFF;
                this.state_0_ = state_0 |= 0x400;
                lock.unlock();
                hasLock = false;
                JSDynamicObject jSDynamicObject = this.doSetter(frameValue, arg0Value, arg1Value, arg2Value, this.setter_setMagic_);
                return jSDynamicObject;
            }
            throw new UnsupportedSpecializationException(this, new Node[]{null, null, null}, arg0Value, arg1Value, arg2Value);
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
            PrivateMethodCachedData s0_ = this.privateMethodCached_cache;
            PublicMethodCachedData s1_ = this.publicMethodCached_cache;
            FieldCachedData s3_ = this.fieldCached_cache;
            AutoAccessorCachedData s5_ = this.autoAccessorCached_cache;
            GetterCachedData s7_ = this.getterCached_cache;
            SetterCachedData s9_ = this.setterCached_cache;
            if (!(s0_ != null && s0_.next_ != null || s1_ != null && s1_.next_ != null || s3_ != null && s3_.next_ != null || s5_ != null && s5_.next_ != null || s7_ != null && s7_.next_ != null || s9_ != null && s9_.next_ != null)) {
                return NodeCost.MONOMORPHIC;
            }
        }
        return NodeCost.POLYMORPHIC;
    }

    @Override
    public Introspection getIntrospectionData() {
        ArrayList<List<Object>> cached;
        Object[] data = new Object[12];
        data[0] = 0;
        int state_0 = this.state_0_;
        int exclude = this.exclude_;
        Object[] s = new Object[3];
        s[0] = "doPrivateMethodCached";
        if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList<List<Object>>();
            PrivateMethodCachedData s0_ = this.privateMethodCached_cache;
            while (s0_ != null) {
                cached.add(Arrays.asList(s0_.cachedName_, s0_.description_, s0_.strEq_, s0_.valueGetterFunctionData_, s0_.privateName_));
                s0_ = s0_.next_;
            }
            s[2] = cached;
        } else {
            s[1] = (exclude & 1) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
        }
        data[1] = s;
        s = new Object[3];
        s[0] = "doPublicMethodCached";
        if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            PublicMethodCachedData s1_ = this.publicMethodCached_cache;
            while (s1_ != null) {
                cached.add(Arrays.asList(s1_.cachedName_, s1_.description_, s1_.strEq_, s1_.privateName_, s1_.valueGetterFunctionData_));
                s1_ = s1_.next_;
            }
            s[2] = cached;
        } else {
            s[1] = (exclude & 2) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
        }
        data[2] = s;
        s = new Object[3];
        s[0] = "doMethodGeneric";
        if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.methodGeneric_setMagic_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[3] = s;
        s = new Object[3];
        s[0] = "doFieldCached";
        if ((state_0 & 8) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            FieldCachedData s3_ = this.fieldCached_cache;
            while (s3_ != null) {
                cached.add(Arrays.asList(s3_.cachedName_, s3_.description_, s3_.strEq_, s3_.privateName_, s3_.valueGetterFunctionData_, s3_.valueSetterFunctionData_));
                s3_ = s3_.next_;
            }
            s[2] = cached;
        } else {
            s[1] = (exclude & 4) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
        }
        data[4] = s;
        s = new Object[3];
        s[0] = "doFieldUncached";
        if ((state_0 & 0x10) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.fieldUncached_setMagic_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[5] = s;
        s = new Object[3];
        s[0] = "doAutoAccessorCached";
        if ((state_0 & 0x20) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            AutoAccessorCachedData s5_ = this.autoAccessorCached_cache;
            while (s5_ != null) {
                cached.add(Arrays.asList(s5_.cachedName_, s5_.description_, s5_.strEq_, s5_.privateName_, s5_.valueGetterFunctionData_, s5_.valueSetterFunctionData_));
                s5_ = s5_.next_;
            }
            s[2] = cached;
        } else {
            s[1] = (exclude & 8) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
        }
        data[6] = s;
        s = new Object[3];
        s[0] = "doAutoAccessor";
        if ((state_0 & 0x40) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.autoAccessor_setMagic_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[7] = s;
        s = new Object[3];
        s[0] = "doGetterCached";
        if ((state_0 & 0x80) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            GetterCachedData s7_ = this.getterCached_cache;
            while (s7_ != null) {
                cached.add(Arrays.asList(s7_.cachedName_, s7_.description_, s7_.strEq_, s7_.privateName_, s7_.valueGetterFunctionData_));
                s7_ = s7_.next_;
            }
            s[2] = cached;
        } else {
            s[1] = (exclude & 0x10) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
        }
        data[8] = s;
        s = new Object[3];
        s[0] = "doGetter";
        if ((state_0 & 0x100) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.getter_setMagic_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[9] = s;
        s = new Object[3];
        s[0] = "doSetterCached";
        if ((state_0 & 0x200) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            SetterCachedData s9_ = this.setterCached_cache;
            while (s9_ != null) {
                cached.add(Arrays.asList(s9_.cachedName_, s9_.description_, s9_.strEq_, s9_.privateName_, s9_.valueSetterFunctionData_));
                s9_ = s9_.next_;
            }
            s[2] = cached;
        } else {
            s[1] = (exclude & 0x20) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
        }
        data[10] = s;
        s = new Object[3];
        s[0] = "doSetter";
        if ((state_0 & 0x400) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.setter_setMagic_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[11] = s;
        return Introspection.Provider.create(data);
    }

    public static CreateDecoratorContextObjectNode create(JSContext context, boolean isStatic) {
        return new CreateDecoratorContextObjectNodeGen(context, isStatic);
    }

    @GeneratedBy(value=CreateDecoratorContextObjectNode.class)
    private static final class SetterCachedData
    extends Node {
        @Node.Child
        SetterCachedData next_;
        @CompilerDirectives.CompilationFinal
        Object cachedName_;
        @CompilerDirectives.CompilationFinal
        Object description_;
        @Node.Child
        TruffleString.EqualNode strEq_;
        @CompilerDirectives.CompilationFinal
        boolean privateName_;
        @CompilerDirectives.CompilationFinal
        JSFunctionData valueSetterFunctionData_;

        SetterCachedData(SetterCachedData next_) {
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

    @GeneratedBy(value=CreateDecoratorContextObjectNode.class)
    private static final class GetterCachedData
    extends Node {
        @Node.Child
        GetterCachedData next_;
        @CompilerDirectives.CompilationFinal
        Object cachedName_;
        @CompilerDirectives.CompilationFinal
        Object description_;
        @Node.Child
        TruffleString.EqualNode strEq_;
        @CompilerDirectives.CompilationFinal
        boolean privateName_;
        @CompilerDirectives.CompilationFinal
        JSFunctionData valueGetterFunctionData_;

        GetterCachedData(GetterCachedData next_) {
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

    @GeneratedBy(value=CreateDecoratorContextObjectNode.class)
    private static final class AutoAccessorCachedData
    extends Node {
        @Node.Child
        AutoAccessorCachedData next_;
        @CompilerDirectives.CompilationFinal
        Object cachedName_;
        @CompilerDirectives.CompilationFinal
        Object description_;
        @Node.Child
        TruffleString.EqualNode strEq_;
        @CompilerDirectives.CompilationFinal
        boolean privateName_;
        @CompilerDirectives.CompilationFinal
        JSFunctionData valueGetterFunctionData_;
        @CompilerDirectives.CompilationFinal
        JSFunctionData valueSetterFunctionData_;

        AutoAccessorCachedData(AutoAccessorCachedData next_) {
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

    @GeneratedBy(value=CreateDecoratorContextObjectNode.class)
    private static final class FieldCachedData
    extends Node {
        @Node.Child
        FieldCachedData next_;
        @CompilerDirectives.CompilationFinal
        Object cachedName_;
        @CompilerDirectives.CompilationFinal
        Object description_;
        @Node.Child
        TruffleString.EqualNode strEq_;
        @CompilerDirectives.CompilationFinal
        boolean privateName_;
        @CompilerDirectives.CompilationFinal
        JSFunctionData valueGetterFunctionData_;
        @CompilerDirectives.CompilationFinal
        JSFunctionData valueSetterFunctionData_;

        FieldCachedData(FieldCachedData next_) {
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

    @GeneratedBy(value=CreateDecoratorContextObjectNode.class)
    private static final class PublicMethodCachedData
    extends Node {
        @Node.Child
        PublicMethodCachedData next_;
        @CompilerDirectives.CompilationFinal
        Object cachedName_;
        @CompilerDirectives.CompilationFinal
        Object description_;
        @Node.Child
        TruffleString.EqualNode strEq_;
        @CompilerDirectives.CompilationFinal
        boolean privateName_;
        @CompilerDirectives.CompilationFinal
        JSFunctionData valueGetterFunctionData_;

        PublicMethodCachedData(PublicMethodCachedData next_) {
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

    @GeneratedBy(value=CreateDecoratorContextObjectNode.class)
    private static final class PrivateMethodCachedData
    extends Node {
        @Node.Child
        PrivateMethodCachedData next_;
        @CompilerDirectives.CompilationFinal
        Object cachedName_;
        @CompilerDirectives.CompilationFinal
        Object description_;
        @Node.Child
        TruffleString.EqualNode strEq_;
        @CompilerDirectives.CompilationFinal
        JSFunctionData valueGetterFunctionData_;
        @CompilerDirectives.CompilationFinal
        boolean privateName_;

        PrivateMethodCachedData(PrivateMethodCachedData next_) {
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
}

