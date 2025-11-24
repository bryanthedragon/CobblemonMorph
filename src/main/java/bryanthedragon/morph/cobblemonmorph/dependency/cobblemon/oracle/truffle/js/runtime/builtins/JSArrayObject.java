
package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.InvalidArrayIndexException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.access.ReadElementNode;
import com.oracle.truffle.js.nodes.access.WriteElementNode;
import com.oracle.truffle.js.nodes.interop.ArrayElementInfoNode;
import com.oracle.truffle.js.nodes.interop.ExportValueNode;
import com.oracle.truffle.js.nodes.interop.ImportValueNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.array.ArrayAllocationSite;
import com.oracle.truffle.js.runtime.array.DynamicArray;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.array.dyn.AbstractConstantEmptyArray;
import com.oracle.truffle.js.runtime.array.dyn.AbstractObjectArray;
import com.oracle.truffle.js.runtime.array.dyn.ConstantObjectArray;
import com.oracle.truffle.js.runtime.builtins.JSAbstractArray;
import com.oracle.truffle.js.runtime.builtins.JSArray;
import com.oracle.truffle.js.runtime.builtins.JSArrayBase;
import com.oracle.truffle.js.runtime.builtins.JSNonProxy;
import com.oracle.truffle.js.runtime.interop.InteropArray;
import com.oracle.truffle.js.runtime.objects.JSCopyableObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.Undefined;

@ExportLibrary(value=InteropLibrary.class)
public final class JSArrayObject
extends JSArrayBase
implements JSCopyableObject {
    protected JSArrayObject(Shape shape, ScriptArray arrayType, Object array, ArrayAllocationSite site, long length, int usedLength, int indexOffset, int arrayOffset, int holeCount) {
        super(shape, arrayType, array, site, length, usedLength, indexOffset, arrayOffset, holeCount);
    }

    public static JSArrayObject create(Shape shape, ScriptArray arrayType, Object array, ArrayAllocationSite site, long length, int usedLength, int indexOffset, int arrayOffset, int holeCount) {
        return new JSArrayObject(shape, arrayType, array, site, length, usedLength, indexOffset, arrayOffset, holeCount);
    }

    public static JSArrayObject createEmpty(Shape shape, ScriptArray arrayType) {
        assert (arrayType instanceof AbstractConstantEmptyArray || arrayType instanceof ConstantObjectArray || arrayType instanceof AbstractObjectArray);
        return new JSArrayObject(shape, arrayType, ScriptArray.EMPTY_OBJECT_ARRAY, null, 0L, 0, 0, 0, 0);
    }

    @Override
    public JSAbstractArray getJSClass() {
        return (JSAbstractArray)super.getJSClass();
    }

    @Override
    protected JSObject copyWithoutProperties(Shape shape) {
        Object clonedArray = ((DynamicArray)this.getArrayType()).cloneArray(this);
        return new JSArrayObject(shape, this.getArrayType(), clonedArray, null, this.length, this.usedLength, this.indexOffset, this.arrayOffset, this.holeCount);
    }

    @ExportMessage
    public boolean hasArrayElements() {
        return true;
    }

    @ExportMessage
    public long getArraySize() {
        return JSArray.arrayGetLength(this);
    }

    @ExportMessage
    public Object readArrayElement(long index, @CachedLibrary(value="this") InteropLibrary self, @Cached(value="create(language(self).getJSContext())", uncached="getUncachedRead()") ReadElementNode readNode, @Cached ExportValueNode exportNode) throws InvalidArrayIndexException, UnsupportedMessageException {
        if (index < 0L || index >= self.getArraySize(this)) {
            throw InvalidArrayIndexException.create(index);
        }
        Object result = readNode == null ? JSObject.getOrDefault((JSDynamicObject)this, index, (Object)this, (Object)Undefined.instance) : readNode.executeWithTargetAndIndexOrDefault(this, index, Undefined.instance);
        return exportNode.execute(result);
    }

    @ExportMessage
    public boolean isArrayElementReadable(long index, @CachedLibrary(value="this") InteropLibrary thisLibrary) {
        try {
            return index >= 0L && index < thisLibrary.getArraySize(this);
        }
        catch (UnsupportedMessageException e) {
            throw Errors.shouldNotReachHere(e);
        }
    }

    @ExportMessage
    public void writeArrayElement(long index, Object value2, @Cached.Shared(value="elementInfo") @Cached ArrayElementInfoNode elements2, @Cached ImportValueNode castValueNode, @Cached(value="createCachedInterop()", uncached="getUncachedWrite()") WriteElementNode writeNode) throws InvalidArrayIndexException, UnsupportedMessageException {
        elements2.executeCheck(this, index, 6);
        Object importedValue = castValueNode.executeWithTarget(value2);
        if (writeNode == null) {
            JSObject.set((JSDynamicObject)this, index, importedValue, true, null);
        } else {
            writeNode.executeWithTargetAndIndexAndValue((Object)this, index, importedValue);
        }
    }

    @ExportMessage
    public boolean isArrayElementModifiable(long index, @Cached.Shared(value="elementInfo") @Cached ArrayElementInfoNode elements2) {
        return elements2.executeBoolean(this, index, 2);
    }

    @ExportMessage
    public boolean isArrayElementInsertable(long index, @Cached.Shared(value="elementInfo") @Cached ArrayElementInfoNode elements2) {
        return elements2.executeBoolean(this, index, 4);
    }

    @ExportMessage
    public boolean isArrayElementRemovable(long index, @Cached.Shared(value="elementInfo") @Cached ArrayElementInfoNode elements2) {
        return elements2.executeBoolean(this, index, 8);
    }

    @ExportMessage
    public void removeArrayElement(long index, @Cached.Shared(value="elementInfo") @Cached ArrayElementInfoNode elements2) throws UnsupportedMessageException, InvalidArrayIndexException {
        elements2.executeCheck(this, index, 8);
        ScriptArray strategy = this.getArrayType();
        long len = strategy.length(this);
        strategy = strategy.removeRange(this, index, index + 1L);
        JSObject.setArray(this, strategy);
        strategy = strategy.setLength(this, len - 1L, true);
        JSObject.setArray(this, strategy);
    }

    @ExportMessage
    @ImportStatic(value={JSGuards.class, JSObject.class})
    static abstract class GetMembers {
        GetMembers() {
        }

        @Specialization(guards={"isJSFastArray(target)"})
        public static Object fastArray(JSArrayObject target, boolean internal) {
            return InteropArray.create(JSArrayObject.filterEnumerableNames(target, JSNonProxy.ordinaryOwnPropertyKeys(target), JSObject.getJSClass(target)));
        }

        @Specialization(guards={"!isJSFastArray(target)"})
        public static Object slowArray(JSArrayObject target, boolean internal) {
            return InteropArray.create(JSArrayObject.filterEnumerableNames(target, JSObject.ownPropertyKeys(target), JSObject.getJSClass(target)));
        }
    }
}

