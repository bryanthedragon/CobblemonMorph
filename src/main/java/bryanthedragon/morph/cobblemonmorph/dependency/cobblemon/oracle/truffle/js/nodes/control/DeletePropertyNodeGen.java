
package com.oracle.truffle.js.nodes.control;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.IsArrayNode;
import com.oracle.truffle.js.nodes.array.JSArrayDeleteIndexNode;
import com.oracle.truffle.js.nodes.cast.JSToPropertyKeyNode;
import com.oracle.truffle.js.nodes.cast.ToArrayIndexNode;
import com.oracle.truffle.js.nodes.control.DeletePropertyNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.util.JSClassProfile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=DeletePropertyNode.class)
public final class DeletePropertyNodeGen
extends DeletePropertyNode
implements Introspection.Provider {
    private static final LibraryFactory<DynamicObjectLibrary> DYNAMIC_OBJECT_LIBRARY_ = LibraryFactory.resolve(DynamicObjectLibrary.class);
    private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @CompilerDirectives.CompilationFinal
    private volatile int exclude_;
    @Node.Child
    private JSToPropertyKeyNode toPropertyKey;
    @Node.Child
    private ToArrayIndexNode toArrayIndex;
    @Node.Child
    private InteropLibrary interop;
    @Node.Child
    private DynamicObjectLibrary jSOrdinaryObject_dynamicObjectLib_;
    @Node.Child
    private IsArrayNode jSObject_isArrayNode_;
    @CompilerDirectives.CompilationFinal
    private ConditionProfile jSObject_arrayProfile_;
    @Node.Child
    private ToArrayIndexNode jSObject_toArrayIndexNode_;
    @CompilerDirectives.CompilationFinal
    private ConditionProfile jSObject_arrayIndexProfile_;
    @Node.Child
    private JSArrayDeleteIndexNode jSObject_deleteArrayIndexNode_;
    @CompilerDirectives.CompilationFinal
    private JSClassProfile jSObject_jsclassProfile_;
    @Node.Child
    private TruffleString.EqualNode string_equalsNode_;

    private DeletePropertyNodeGen(boolean strict, JSContext context, JavaScriptNode targetNode, JavaScriptNode propertyNode) {
        super(strict, context, targetNode, propertyNode);
    }

    @Override
    public boolean executeEvaluated(Object targetNodeValue, Object propertyNodeValue) {
        int state_0 = this.state_0_;
        if ((state_0 & 0x3F) != 0) {
            Object targetNodeValue_;
            if ((state_0 & 3) != 0 && targetNodeValue instanceof JSDynamicObject) {
                targetNodeValue_ = (JSDynamicObject)targetNodeValue;
                if ((state_0 & 1) != 0 && JSGuards.isJSOrdinaryObject(targetNodeValue_)) {
                    return this.doJSOrdinaryObject((JSDynamicObject)targetNodeValue_, propertyNodeValue, this.toPropertyKey, this.jSOrdinaryObject_dynamicObjectLib_);
                }
                if ((state_0 & 2) != 0 && !JSGuards.isJSOrdinaryObject(targetNodeValue_)) {
                    return this.doJSObject((JSDynamicObject)targetNodeValue_, propertyNodeValue, this.jSObject_isArrayNode_, this.jSObject_arrayProfile_, this.jSObject_toArrayIndexNode_, this.jSObject_arrayIndexProfile_, this.jSObject_deleteArrayIndexNode_, this.jSObject_jsclassProfile_, this.toPropertyKey);
                }
            }
            if ((state_0 & 4) != 0 && targetNodeValue instanceof Symbol) {
                targetNodeValue_ = (Symbol)targetNodeValue;
                return DeletePropertyNode.doSymbol((Symbol)targetNodeValue_, propertyNodeValue, this.toPropertyKey);
            }
            if ((state_0 & 8) != 0 && targetNodeValue instanceof SafeInteger) {
                targetNodeValue_ = (SafeInteger)targetNodeValue;
                return DeletePropertyNode.doSafeInteger((SafeInteger)targetNodeValue_, propertyNodeValue, this.toPropertyKey);
            }
            if ((state_0 & 0x10) != 0 && targetNodeValue instanceof BigInt) {
                targetNodeValue_ = (BigInt)targetNodeValue;
                return DeletePropertyNode.doBigInt((BigInt)targetNodeValue_, propertyNodeValue, this.toPropertyKey);
            }
            if ((state_0 & 0x20) != 0 && targetNodeValue instanceof TruffleString) {
                targetNodeValue_ = (TruffleString)targetNodeValue;
                return this.doString((TruffleString)targetNodeValue_, propertyNodeValue, this.toArrayIndex, this.string_equalsNode_);
            }
        }
        if ((state_0 & 0x3C0) != 0) {
            if ((state_0 & 0x40) != 0 && propertyNodeValue instanceof TruffleString) {
                TruffleString propertyNodeValue_ = (TruffleString)propertyNodeValue;
                if (JSGuards.isForeignObject(targetNodeValue) && !this.interop.hasArrayElements(targetNodeValue)) {
                    return this.member(targetNodeValue, propertyNodeValue_, this.interop);
                }
            }
            if ((state_0 & 0x80) != 0 && propertyNodeValue instanceof Integer) {
                int propertyNodeValue_ = (Integer)propertyNodeValue;
                if (JSGuards.isForeignObject(targetNodeValue) && this.interop.hasArrayElements(targetNodeValue)) {
                    return this.arrayElementInt(targetNodeValue, propertyNodeValue_, this.interop);
                }
            }
            if ((state_0 & 0x300) != 0) {
                if ((state_0 & 0x100) != 0 && JSGuards.isForeignObject(targetNodeValue)) {
                    return this.foreignObject(targetNodeValue, propertyNodeValue, this.interop, this.toArrayIndex, this.toPropertyKey);
                }
                if ((state_0 & 0x200) != 0 && !JSGuards.isTruffleObject(targetNodeValue) && !JSGuards.isString(targetNodeValue)) {
                    return this.doOther(targetNodeValue, propertyNodeValue, this.toPropertyKey);
                }
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(targetNodeValue, propertyNodeValue);
    }

    @Override
    public Object executeWithTarget(VirtualFrame frameValue, Object targetNodeValue) {
        int state_0 = this.state_0_;
        if ((state_0 & 0x37F) == 0 && state_0 != 0) {
            return this.executeWithTarget_int0(state_0, frameValue, targetNodeValue);
        }
        return this.executeWithTarget_generic1(state_0, frameValue, targetNodeValue);
    }

    private Object executeWithTarget_int0(int state_0, VirtualFrame frameValue, Object targetNodeValue) {
        int propertyNodeValue_;
        try {
            propertyNodeValue_ = this.propertyNode.executeInt(frameValue);
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(targetNodeValue, ex.getResult());
        }
        assert ((state_0 & 0x80) != 0);
        if (JSGuards.isForeignObject(targetNodeValue) && this.interop.hasArrayElements(targetNodeValue)) {
            return this.arrayElementInt(targetNodeValue, propertyNodeValue_, this.interop);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(targetNodeValue, propertyNodeValue_);
    }

    private Object executeWithTarget_generic1(int state_0, VirtualFrame frameValue, Object targetNodeValue) {
        Object propertyNodeValue_ = this.propertyNode.execute(frameValue);
        if ((state_0 & 0x3F) != 0) {
            Object targetNodeValue_;
            if ((state_0 & 3) != 0 && targetNodeValue instanceof JSDynamicObject) {
                targetNodeValue_ = (JSDynamicObject)targetNodeValue;
                if ((state_0 & 1) != 0 && JSGuards.isJSOrdinaryObject(targetNodeValue_)) {
                    return this.doJSOrdinaryObject((JSDynamicObject)targetNodeValue_, propertyNodeValue_, this.toPropertyKey, this.jSOrdinaryObject_dynamicObjectLib_);
                }
                if ((state_0 & 2) != 0 && !JSGuards.isJSOrdinaryObject(targetNodeValue_)) {
                    return this.doJSObject((JSDynamicObject)targetNodeValue_, propertyNodeValue_, this.jSObject_isArrayNode_, this.jSObject_arrayProfile_, this.jSObject_toArrayIndexNode_, this.jSObject_arrayIndexProfile_, this.jSObject_deleteArrayIndexNode_, this.jSObject_jsclassProfile_, this.toPropertyKey);
                }
            }
            if ((state_0 & 4) != 0 && targetNodeValue instanceof Symbol) {
                targetNodeValue_ = (Symbol)targetNodeValue;
                return DeletePropertyNode.doSymbol((Symbol)targetNodeValue_, propertyNodeValue_, this.toPropertyKey);
            }
            if ((state_0 & 8) != 0 && targetNodeValue instanceof SafeInteger) {
                targetNodeValue_ = (SafeInteger)targetNodeValue;
                return DeletePropertyNode.doSafeInteger((SafeInteger)targetNodeValue_, propertyNodeValue_, this.toPropertyKey);
            }
            if ((state_0 & 0x10) != 0 && targetNodeValue instanceof BigInt) {
                targetNodeValue_ = (BigInt)targetNodeValue;
                return DeletePropertyNode.doBigInt((BigInt)targetNodeValue_, propertyNodeValue_, this.toPropertyKey);
            }
            if ((state_0 & 0x20) != 0 && targetNodeValue instanceof TruffleString) {
                targetNodeValue_ = (TruffleString)targetNodeValue;
                return this.doString((TruffleString)targetNodeValue_, propertyNodeValue_, this.toArrayIndex, this.string_equalsNode_);
            }
        }
        if ((state_0 & 0x3C0) != 0) {
            if ((state_0 & 0x40) != 0 && propertyNodeValue_ instanceof TruffleString) {
                TruffleString propertyNodeValue__ = (TruffleString)propertyNodeValue_;
                if (JSGuards.isForeignObject(targetNodeValue) && !this.interop.hasArrayElements(targetNodeValue)) {
                    return this.member(targetNodeValue, propertyNodeValue__, this.interop);
                }
            }
            if ((state_0 & 0x80) != 0 && propertyNodeValue_ instanceof Integer) {
                int propertyNodeValue__ = (Integer)propertyNodeValue_;
                if (JSGuards.isForeignObject(targetNodeValue) && this.interop.hasArrayElements(targetNodeValue)) {
                    return this.arrayElementInt(targetNodeValue, propertyNodeValue__, this.interop);
                }
            }
            if ((state_0 & 0x300) != 0) {
                if ((state_0 & 0x100) != 0 && JSGuards.isForeignObject(targetNodeValue)) {
                    return this.foreignObject(targetNodeValue, propertyNodeValue_, this.interop, this.toArrayIndex, this.toPropertyKey);
                }
                if ((state_0 & 0x200) != 0 && !JSGuards.isTruffleObject(targetNodeValue) && !JSGuards.isString(targetNodeValue)) {
                    return this.doOther(targetNodeValue, propertyNodeValue_, this.toPropertyKey);
                }
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(targetNodeValue, propertyNodeValue_);
    }

    @Override
    public boolean executeBoolean(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        if ((state_0 & 0x37F) == 0 && state_0 != 0) {
            return this.executeBoolean_int2(state_0, frameValue);
        }
        return this.executeBoolean_generic3(state_0, frameValue);
    }

    private boolean executeBoolean_int2(int state_0, VirtualFrame frameValue) {
        int propertyNodeValue_;
        Object targetNodeValue_ = this.targetNode.execute(frameValue);
        try {
            propertyNodeValue_ = this.propertyNode.executeInt(frameValue);
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(targetNodeValue_, ex.getResult());
        }
        assert ((state_0 & 0x80) != 0);
        if (JSGuards.isForeignObject(targetNodeValue_) && this.interop.hasArrayElements(targetNodeValue_)) {
            return this.arrayElementInt(targetNodeValue_, propertyNodeValue_, this.interop);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(targetNodeValue_, propertyNodeValue_);
    }

    private boolean executeBoolean_generic3(int state_0, VirtualFrame frameValue) {
        Object targetNodeValue_ = this.targetNode.execute(frameValue);
        Object propertyNodeValue_ = this.propertyNode.execute(frameValue);
        if ((state_0 & 0x3F) != 0) {
            Object targetNodeValue__;
            if ((state_0 & 3) != 0 && targetNodeValue_ instanceof JSDynamicObject) {
                targetNodeValue__ = (JSDynamicObject)targetNodeValue_;
                if ((state_0 & 1) != 0 && JSGuards.isJSOrdinaryObject(targetNodeValue__)) {
                    return this.doJSOrdinaryObject((JSDynamicObject)targetNodeValue__, propertyNodeValue_, this.toPropertyKey, this.jSOrdinaryObject_dynamicObjectLib_);
                }
                if ((state_0 & 2) != 0 && !JSGuards.isJSOrdinaryObject(targetNodeValue__)) {
                    return this.doJSObject((JSDynamicObject)targetNodeValue__, propertyNodeValue_, this.jSObject_isArrayNode_, this.jSObject_arrayProfile_, this.jSObject_toArrayIndexNode_, this.jSObject_arrayIndexProfile_, this.jSObject_deleteArrayIndexNode_, this.jSObject_jsclassProfile_, this.toPropertyKey);
                }
            }
            if ((state_0 & 4) != 0 && targetNodeValue_ instanceof Symbol) {
                targetNodeValue__ = (Symbol)targetNodeValue_;
                return DeletePropertyNode.doSymbol((Symbol)targetNodeValue__, propertyNodeValue_, this.toPropertyKey);
            }
            if ((state_0 & 8) != 0 && targetNodeValue_ instanceof SafeInteger) {
                targetNodeValue__ = (SafeInteger)targetNodeValue_;
                return DeletePropertyNode.doSafeInteger((SafeInteger)targetNodeValue__, propertyNodeValue_, this.toPropertyKey);
            }
            if ((state_0 & 0x10) != 0 && targetNodeValue_ instanceof BigInt) {
                targetNodeValue__ = (BigInt)targetNodeValue_;
                return DeletePropertyNode.doBigInt((BigInt)targetNodeValue__, propertyNodeValue_, this.toPropertyKey);
            }
            if ((state_0 & 0x20) != 0 && targetNodeValue_ instanceof TruffleString) {
                targetNodeValue__ = (TruffleString)targetNodeValue_;
                return this.doString((TruffleString)targetNodeValue__, propertyNodeValue_, this.toArrayIndex, this.string_equalsNode_);
            }
        }
        if ((state_0 & 0x3C0) != 0) {
            if ((state_0 & 0x40) != 0 && propertyNodeValue_ instanceof TruffleString) {
                TruffleString propertyNodeValue__ = (TruffleString)propertyNodeValue_;
                if (JSGuards.isForeignObject(targetNodeValue_) && !this.interop.hasArrayElements(targetNodeValue_)) {
                    return this.member(targetNodeValue_, propertyNodeValue__, this.interop);
                }
            }
            if ((state_0 & 0x80) != 0 && propertyNodeValue_ instanceof Integer) {
                int propertyNodeValue__ = (Integer)propertyNodeValue_;
                if (JSGuards.isForeignObject(targetNodeValue_) && this.interop.hasArrayElements(targetNodeValue_)) {
                    return this.arrayElementInt(targetNodeValue_, propertyNodeValue__, this.interop);
                }
            }
            if ((state_0 & 0x300) != 0) {
                if ((state_0 & 0x100) != 0 && JSGuards.isForeignObject(targetNodeValue_)) {
                    return this.foreignObject(targetNodeValue_, propertyNodeValue_, this.interop, this.toArrayIndex, this.toPropertyKey);
                }
                if ((state_0 & 0x200) != 0 && !JSGuards.isTruffleObject(targetNodeValue_) && !JSGuards.isString(targetNodeValue_)) {
                    return this.doOther(targetNodeValue_, propertyNodeValue_, this.toPropertyKey);
                }
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(targetNodeValue_, propertyNodeValue_);
    }

    @Override
    public void executeVoid(VirtualFrame frameValue) {
        this.executeBoolean(frameValue);
    }

    private boolean executeAndSpecialize(Object targetNodeValue, Object propertyNodeValue) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            Object targetNodeValue_;
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (targetNodeValue instanceof JSDynamicObject) {
                targetNodeValue_ = (JSDynamicObject)targetNodeValue;
                if (JSGuards.isJSOrdinaryObject(targetNodeValue_)) {
                    this.toPropertyKey = super.insert(this.toPropertyKey == null ? JSToPropertyKeyNode.create() : this.toPropertyKey);
                    this.jSOrdinaryObject_dynamicObjectLib_ = super.insert(DYNAMIC_OBJECT_LIBRARY_.createDispatched(5));
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = this.doJSOrdinaryObject((JSDynamicObject)targetNodeValue_, propertyNodeValue, this.toPropertyKey, this.jSOrdinaryObject_dynamicObjectLib_);
                    return bl;
                }
                if (!JSGuards.isJSOrdinaryObject(targetNodeValue_)) {
                    this.jSObject_isArrayNode_ = super.insert(IsArrayNode.createIsFastArray());
                    this.jSObject_arrayProfile_ = ConditionProfile.createBinaryProfile();
                    this.jSObject_toArrayIndexNode_ = super.insert(ToArrayIndexNode.create());
                    this.jSObject_arrayIndexProfile_ = ConditionProfile.createBinaryProfile();
                    this.jSObject_deleteArrayIndexNode_ = super.insert(JSArrayDeleteIndexNode.create(this.context, this.strict));
                    this.jSObject_jsclassProfile_ = JSClassProfile.create();
                    this.toPropertyKey = super.insert(this.toPropertyKey == null ? JSToPropertyKeyNode.create() : this.toPropertyKey);
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = this.doJSObject((JSDynamicObject)targetNodeValue_, propertyNodeValue, this.jSObject_isArrayNode_, this.jSObject_arrayProfile_, this.jSObject_toArrayIndexNode_, this.jSObject_arrayIndexProfile_, this.jSObject_deleteArrayIndexNode_, this.jSObject_jsclassProfile_, this.toPropertyKey);
                    return bl;
                }
            }
            if (targetNodeValue instanceof Symbol) {
                targetNodeValue_ = (Symbol)targetNodeValue;
                this.toPropertyKey = super.insert(this.toPropertyKey == null ? JSToPropertyKeyNode.create() : this.toPropertyKey);
                this.state_0_ = state_0 |= 4;
                lock.unlock();
                hasLock = false;
                boolean bl = DeletePropertyNode.doSymbol((Symbol)targetNodeValue_, propertyNodeValue, this.toPropertyKey);
                return bl;
            }
            if (targetNodeValue instanceof SafeInteger) {
                targetNodeValue_ = (SafeInteger)targetNodeValue;
                this.toPropertyKey = super.insert(this.toPropertyKey == null ? JSToPropertyKeyNode.create() : this.toPropertyKey);
                this.state_0_ = state_0 |= 8;
                lock.unlock();
                hasLock = false;
                boolean bl = DeletePropertyNode.doSafeInteger((SafeInteger)targetNodeValue_, propertyNodeValue, this.toPropertyKey);
                return bl;
            }
            if (targetNodeValue instanceof BigInt) {
                targetNodeValue_ = (BigInt)targetNodeValue;
                this.toPropertyKey = super.insert(this.toPropertyKey == null ? JSToPropertyKeyNode.create() : this.toPropertyKey);
                this.state_0_ = state_0 |= 0x10;
                lock.unlock();
                hasLock = false;
                boolean bl = DeletePropertyNode.doBigInt((BigInt)targetNodeValue_, propertyNodeValue, this.toPropertyKey);
                return bl;
            }
            if (targetNodeValue instanceof TruffleString) {
                targetNodeValue_ = (TruffleString)targetNodeValue;
                this.toArrayIndex = super.insert(this.toArrayIndex == null ? ToArrayIndexNode.create() : this.toArrayIndex);
                this.string_equalsNode_ = super.insert(TruffleString.EqualNode.create());
                this.state_0_ = state_0 |= 0x20;
                lock.unlock();
                hasLock = false;
                boolean bl = this.doString((TruffleString)targetNodeValue_, propertyNodeValue, this.toArrayIndex, this.string_equalsNode_);
                return bl;
            }
            if ((exclude & 1) == 0 && propertyNodeValue instanceof TruffleString) {
                InteropLibrary member_interop__;
                TruffleString propertyNodeValue_ = (TruffleString)propertyNodeValue;
                if (JSGuards.isForeignObject(targetNodeValue) && !(member_interop__ = super.insert(this.interop == null ? INTEROP_LIBRARY_.createDispatched(5) : this.interop)).hasArrayElements(targetNodeValue)) {
                    if (this.interop == null) {
                        InteropLibrary member_interop___check = super.insert(member_interop__);
                        if (member_interop___check == null) {
                            throw new AssertionError((Object)"Specialization 'member(Object, TruffleString, InteropLibrary)' contains a shared cache with name 'interop' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                        }
                        this.interop = member_interop___check;
                    }
                    this.state_0_ = state_0 |= 0x40;
                    lock.unlock();
                    hasLock = false;
                    boolean member_interop___check = this.member(targetNodeValue, propertyNodeValue_, member_interop__);
                    return member_interop___check;
                }
            }
            if ((exclude & 2) == 0 && propertyNodeValue instanceof Integer) {
                InteropLibrary arrayElementInt_interop__;
                int propertyNodeValue_ = (Integer)propertyNodeValue;
                if (JSGuards.isForeignObject(targetNodeValue) && (arrayElementInt_interop__ = super.insert(this.interop == null ? INTEROP_LIBRARY_.createDispatched(5) : this.interop)).hasArrayElements(targetNodeValue)) {
                    if (this.interop == null) {
                        InteropLibrary arrayElementInt_interop___check = super.insert(arrayElementInt_interop__);
                        if (arrayElementInt_interop___check == null) {
                            throw new AssertionError((Object)"Specialization 'arrayElementInt(Object, int, InteropLibrary)' contains a shared cache with name 'interop' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                        }
                        this.interop = arrayElementInt_interop___check;
                    }
                    this.state_0_ = state_0 |= 0x80;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = this.arrayElementInt(targetNodeValue, propertyNodeValue_, arrayElementInt_interop__);
                    return bl;
                }
            }
            if (JSGuards.isForeignObject(targetNodeValue)) {
                this.interop = super.insert(this.interop == null ? INTEROP_LIBRARY_.createDispatched(5) : this.interop);
                this.toArrayIndex = super.insert(this.toArrayIndex == null ? ToArrayIndexNode.create() : this.toArrayIndex);
                this.toPropertyKey = super.insert(this.toPropertyKey == null ? JSToPropertyKeyNode.create() : this.toPropertyKey);
                this.exclude_ = exclude |= 3;
                state_0 &= 0xFFFFFF3F;
                this.state_0_ = state_0 |= 0x100;
                lock.unlock();
                hasLock = false;
                boolean bl = this.foreignObject(targetNodeValue, propertyNodeValue, this.interop, this.toArrayIndex, this.toPropertyKey);
                return bl;
            }
            if (!JSGuards.isTruffleObject(targetNodeValue) && !JSGuards.isString(targetNodeValue)) {
                this.toPropertyKey = super.insert(this.toPropertyKey == null ? JSToPropertyKeyNode.create() : this.toPropertyKey);
                this.state_0_ = state_0 |= 0x200;
                lock.unlock();
                hasLock = false;
                boolean bl = this.doOther(targetNodeValue, propertyNodeValue, this.toPropertyKey);
                return bl;
            }
            throw new UnsupportedSpecializationException(this, new Node[]{this.targetNode, this.propertyNode}, targetNodeValue, propertyNodeValue);
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
        ArrayList<List<Cloneable>> cached;
        Object[] data = new Object[11];
        data[0] = 0;
        int state_0 = this.state_0_;
        int exclude = this.exclude_;
        Object[] s = new Object[3];
        s[0] = "doJSOrdinaryObject";
        if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList<List<Cloneable>>();
            cached.add(Arrays.asList(this.toPropertyKey, this.jSOrdinaryObject_dynamicObjectLib_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[1] = s;
        s = new Object[3];
        s[0] = "doJSObject";
        if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.jSObject_isArrayNode_, this.jSObject_arrayProfile_, this.jSObject_toArrayIndexNode_, this.jSObject_arrayIndexProfile_, this.jSObject_deleteArrayIndexNode_, this.jSObject_jsclassProfile_, this.toPropertyKey));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[2] = s;
        s = new Object[3];
        s[0] = "doSymbol";
        if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.toPropertyKey));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[3] = s;
        s = new Object[3];
        s[0] = "doSafeInteger";
        if ((state_0 & 8) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.toPropertyKey));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[4] = s;
        s = new Object[3];
        s[0] = "doBigInt";
        if ((state_0 & 0x10) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.toPropertyKey));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[5] = s;
        s = new Object[3];
        s[0] = "doString";
        if ((state_0 & 0x20) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.toArrayIndex, this.string_equalsNode_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[6] = s;
        s = new Object[3];
        s[0] = "member";
        if ((state_0 & 0x40) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.interop));
            s[2] = cached;
        } else {
            s[1] = (exclude & 1) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
        }
        data[7] = s;
        s = new Object[3];
        s[0] = "arrayElementInt";
        if ((state_0 & 0x80) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.interop));
            s[2] = cached;
        } else {
            s[1] = (exclude & 2) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
        }
        data[8] = s;
        s = new Object[3];
        s[0] = "foreignObject";
        if ((state_0 & 0x100) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.interop, this.toArrayIndex, this.toPropertyKey));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[9] = s;
        s = new Object[3];
        s[0] = "doOther";
        if ((state_0 & 0x200) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.toPropertyKey));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[10] = s;
        return Introspection.Provider.create(data);
    }

    public static DeletePropertyNode create(boolean strict, JSContext context, JavaScriptNode targetNode, JavaScriptNode propertyNode) {
        return new DeletePropertyNodeGen(strict, context, targetNode, propertyNode);
    }
}

