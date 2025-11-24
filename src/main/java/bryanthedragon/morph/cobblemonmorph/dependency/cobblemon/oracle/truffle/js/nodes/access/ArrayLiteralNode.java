
package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.GenerateWrapper;
import com.oracle.truffle.api.instrumentation.InstrumentableNode;
import com.oracle.truffle.api.instrumentation.ProbeNode;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.ArrayLiteralNodeWrapper;
import com.oracle.truffle.js.nodes.access.GetIteratorNode;
import com.oracle.truffle.js.nodes.access.IteratorGetNextValueNode;
import com.oracle.truffle.js.nodes.access.JSConstantNode;
import com.oracle.truffle.js.nodes.control.EmptyNode;
import com.oracle.truffle.js.nodes.instrumentation.JSTags;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.array.dyn.AbstractConstantArray;
import com.oracle.truffle.js.runtime.array.dyn.ConstantByteArray;
import com.oracle.truffle.js.runtime.array.dyn.ConstantDoubleArray;
import com.oracle.truffle.js.runtime.array.dyn.ConstantIntArray;
import com.oracle.truffle.js.runtime.array.dyn.ConstantObjectArray;
import com.oracle.truffle.js.runtime.builtins.JSArray;
import com.oracle.truffle.js.runtime.builtins.JSArrayObject;
import com.oracle.truffle.js.runtime.objects.IteratorRecord;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.util.SimpleArrayList;
import java.util.Set;

@GenerateWrapper
public abstract class ArrayLiteralNode
extends JavaScriptNode {
    protected final JSContext context;

    public ArrayLiteralNode(ArrayLiteralNode copy) {
        this.context = copy.context;
    }

    protected ArrayLiteralNode(JSContext context) {
        this.context = context;
    }

    @Override
    public abstract JSArrayObject execute(VirtualFrame var1);

    @Override
    public boolean hasTag(Class<? extends Tag> tag) {
        if (tag == JSTags.LiteralTag.class) {
            return true;
        }
        return super.hasTag(tag);
    }

    @Override
    public Object getNodeObject() {
        return JSTags.createNodeObjectDescriptor("literalType", JSTags.LiteralTag.Type.ArrayLiteral.name());
    }

    @Override
    public InstrumentableNode.WrapperNode createWrapper(ProbeNode probe) {
        return new ArrayLiteralNodeWrapper(this, this, probe);
    }

    public static ArrayLiteralNode create(JSContext context, JavaScriptNode[] elements2) {
        if (elements2 == null || elements2.length == 0) {
            return new ConstantEmptyArrayLiteralNode(context);
        }
        Object[] constantValues = ArrayLiteralNode.resolveConstants(elements2);
        if (constantValues != null) {
            return ArrayLiteralNode.createConstantArray(context, elements2, constantValues);
        }
        for (JavaScriptNode element : elements2) {
            if (!(element instanceof EmptyNode)) continue;
            return new DefaultObjectArrayWithEmptyLiteralNode(context, elements2);
        }
        if (elements2.length == 1) {
            return new DefaultArrayLiteralOneElementNode(context, elements2[0]);
        }
        return new DefaultArrayLiteralNode(context, elements2);
    }

    public static ArrayLiteralNode createWithSpread(JSContext context, JavaScriptNode[] elements2) {
        return new DefaultArrayLiteralWithSpreadNode(context, elements2);
    }

    private static ArrayLiteralNode createConstantArray(JSContext context, JavaScriptNode[] elements2, Object[] constantValues) {
        ArrayContentType type = ArrayLiteralNode.identifyPrimitiveContentType(constantValues, true);
        if (type == ArrayContentType.Byte) {
            return new ConstantArrayLiteralNode(context, ConstantByteArray.createConstantByteArray(), ArrayLiteralNode.createByteArray(constantValues), elements2.length);
        }
        if (type == ArrayContentType.Integer) {
            return new ConstantArrayLiteralNode(context, ConstantIntArray.createConstantIntArray(), ArrayLiteralNode.createIntArray(constantValues), elements2.length);
        }
        if (type == ArrayContentType.Double) {
            return new ConstantArrayLiteralNode(context, ConstantDoubleArray.createConstantDoubleArray(), ArrayLiteralNode.createDoubleArray(constantValues), elements2.length);
        }
        return ArrayLiteralNode.createConstantObjectArray(context, elements2, constantValues);
    }

    private static ArrayLiteralNode createConstantObjectArray(JSContext context, JavaScriptNode[] elements2, Object array) {
        boolean hasEmpty = false;
        boolean emptyOnly = true;
        for (Object value2 : (Object[])array) {
            if (value2 == null) {
                hasEmpty = true;
                continue;
            }
            emptyOnly = false;
        }
        if (emptyOnly) {
            return new ConstantEmptyArrayWithCapLiteralNode(context, elements2.length);
        }
        if (hasEmpty) {
            return new ConstantArrayLiteralNode(context, ConstantObjectArray.createConstantHolesObjectArray(), array, elements2.length);
        }
        return new ConstantArrayLiteralNode(context, ConstantObjectArray.createConstantObjectArray(), array, elements2.length);
    }

    private static Object[] resolveConstants(JavaScriptNode[] nodes) {
        Object[] values = new Object[nodes.length];
        for (int i = 0; i < values.length; ++i) {
            JavaScriptNode node = nodes[i];
            if (node instanceof JSConstantNode) {
                values[i] = ((JSConstantNode)node).getValue();
                continue;
            }
            if (node instanceof EmptyNode) {
                values[i] = null;
                continue;
            }
            return null;
        }
        return values;
    }

    public static ArrayContentType identifyPrimitiveContentType(Object[] values, boolean createBytes) {
        boolean bytes = createBytes;
        boolean integers = true;
        boolean hasHoles = false;
        for (int i = 0; i < values.length; ++i) {
            Object value2 = values[i];
            if (value2 == null) {
                hasHoles = true;
                continue;
            }
            if (integers && value2 instanceof Integer) {
                bytes = bytes && ScriptArray.valueIsByte((Integer)value2);
                continue;
            }
            if (value2 instanceof Double) {
                bytes = false;
                integers = false;
                continue;
            }
            if (value2 instanceof Integer || value2 instanceof Double) continue;
            return ArrayContentType.Object;
        }
        if (bytes) {
            return hasHoles ? ArrayContentType.ByteWithHoles : ArrayContentType.Byte;
        }
        if (integers) {
            return hasHoles ? ArrayContentType.IntegerWithHoles : ArrayContentType.Integer;
        }
        return hasHoles ? ArrayContentType.DoubleWithHoles : ArrayContentType.Double;
    }

    private static Object createPrimitiveArray(Object[] values, boolean createBytes) {
        ArrayContentType type = ArrayLiteralNode.identifyPrimitiveContentType(values, createBytes);
        if (type == ArrayContentType.Byte) {
            return ArrayLiteralNode.createByteArray(values);
        }
        if (type == ArrayContentType.Integer) {
            return ArrayLiteralNode.createIntArray(values);
        }
        if (type == ArrayContentType.Double) {
            return ArrayLiteralNode.createDoubleArray(values);
        }
        return values;
    }

    public static double[] createDoubleArray(Object[] values) {
        double[] doubleArray = new double[values.length];
        for (int i = 0; i < values.length; ++i) {
            Object oValue = values[i];
            if (oValue instanceof Double) {
                doubleArray[i] = (Double)oValue;
                continue;
            }
            if (!(oValue instanceof Integer)) continue;
            doubleArray[i] = ((Integer)oValue).intValue();
        }
        return doubleArray;
    }

    public static int[] createIntArray(Object[] values) {
        int[] intArray = new int[values.length];
        for (int i = 0; i < values.length; ++i) {
            intArray[i] = values[i] == null ? Integer.MIN_VALUE : (Integer)values[i];
        }
        return intArray;
    }

    public static byte[] createByteArray(Object[] values) {
        byte[] byteArray = new byte[values.length];
        for (int i = 0; i < values.length; ++i) {
            byteArray[i] = (byte)((Integer)values[i]).intValue();
        }
        return byteArray;
    }

    @Override
    public boolean isResultAlwaysOfType(Class<?> clazz) {
        return clazz == JSDynamicObject.class;
    }

    public static enum ArrayContentType {
        Byte,
        ByteWithHoles,
        Integer,
        IntegerWithHoles,
        Double,
        DoubleWithHoles,
        Object;

    }

    public static final class SpreadArrayNode
    extends JavaScriptNode {
        @Node.Child
        private GetIteratorNode getIteratorNode;
        @Node.Child
        private IteratorGetNextValueNode iteratorStepNode;

        private SpreadArrayNode(JSContext context, GetIteratorNode getIteratorNode) {
            this.getIteratorNode = getIteratorNode;
            this.iteratorStepNode = IteratorGetNextValueNode.create(context, null, JSConstantNode.create(null), false);
        }

        public static SpreadArrayNode create(JSContext context, GetIteratorNode getIteratorNode) {
            return new SpreadArrayNode(context, getIteratorNode);
        }

        public int executeToList(VirtualFrame frame, SimpleArrayList<Object> toList, BranchProfile growProfile) {
            Object nextArg;
            IteratorRecord iteratorRecord = this.getIteratorNode.execute(frame);
            int count = 0;
            while ((nextArg = this.iteratorStepNode.execute(frame, iteratorRecord)) != null) {
                toList.add(nextArg, growProfile);
                ++count;
            }
            return count;
        }

        @Override
        public Object execute(VirtualFrame frame) {
            throw Errors.shouldNotReachHere("Cannot execute SpreadArrayNode");
        }

        @Override
        protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
            SpreadArrayNode copy = (SpreadArrayNode)this.copy();
            copy.getIteratorNode = SpreadArrayNode.cloneUninitialized(this.getIteratorNode, materializedTags);
            copy.iteratorStepNode = SpreadArrayNode.cloneUninitialized(this.iteratorStepNode, materializedTags);
            return copy;
        }
    }

    private static final class DefaultArrayLiteralWithSpreadNode
    extends DefaultArrayLiteralNode {
        private final BranchProfile growProfile = BranchProfile.create();

        DefaultArrayLiteralWithSpreadNode(JSContext context, JavaScriptNode[] elements2) {
            super(context, elements2);
            assert (elements2.length > 0);
        }

        @Override
        @ExplodeLoop
        public JSArrayObject execute(VirtualFrame frame) {
            SimpleArrayList<Object> evaluatedElements = new SimpleArrayList<Object>(this.elements.length + 3);
            int holeCount = 0;
            int holesBeforeLastNonEmpty = 0;
            int arrayOffset = 0;
            int lastNonEmptyPlusOne = 0;
            for (int i = 0; i < this.elements.length; ++i) {
                Node node = this.elements[i];
                if (this.elements[i] instanceof InstrumentableNode.WrapperNode) {
                    node = ((InstrumentableNode.WrapperNode)((Object)this.elements[i])).getDelegateNode();
                }
                if (node instanceof EmptyNode) {
                    evaluatedElements.add(null, this.growProfile);
                    ++holeCount;
                    if (i != arrayOffset) continue;
                    ++arrayOffset;
                    continue;
                }
                if (node instanceof SpreadArrayNode) {
                    int count = ((SpreadArrayNode)node).executeToList(frame, evaluatedElements, this.growProfile);
                    if (count == 0) continue;
                    lastNonEmptyPlusOne = evaluatedElements.size();
                    holesBeforeLastNonEmpty = holeCount;
                    continue;
                }
                evaluatedElements.add(this.elements[i].execute(frame), this.growProfile);
                lastNonEmptyPlusOne = evaluatedElements.size();
                holesBeforeLastNonEmpty = holeCount;
            }
            int usedLength = lastNonEmptyPlusOne - arrayOffset;
            int holesInUsedLength = holesBeforeLastNonEmpty - arrayOffset;
            return JSArray.createZeroBasedHolesObjectArray(this.context, this.getRealm(), evaluatedElements.toArray(), usedLength, arrayOffset, holesInUsedLength);
        }

        @Override
        protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
            return new DefaultArrayLiteralWithSpreadNode(this.context, DefaultArrayLiteralWithSpreadNode.cloneUninitialized(this.elements, materializedTags));
        }
    }

    private static final class ConstantEmptyArrayLiteralNode
    extends ArrayLiteralNode {
        ConstantEmptyArrayLiteralNode(JSContext context) {
            super(context);
        }

        @Override
        public JSArrayObject execute(VirtualFrame frame) {
            return JSArray.createConstantEmptyArray(this.context, this.getRealm());
        }

        @Override
        protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
            return this.copy();
        }
    }

    private static final class ConstantEmptyArrayWithCapLiteralNode
    extends ArrayLiteralNode {
        private final int capacity;

        ConstantEmptyArrayWithCapLiteralNode(JSContext context, int cap) {
            super(context);
            this.capacity = cap;
        }

        @Override
        public JSArrayObject execute(VirtualFrame frame) {
            return JSArray.createConstantEmptyArray(this.context, this.getRealm(), this.capacity);
        }

        @Override
        protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
            return this.copy();
        }
    }

    private static final class ConstantArrayLiteralNode
    extends ArrayLiteralNode {
        private final AbstractConstantArray arrayType;
        private final Object array;
        private final long length;

        ConstantArrayLiteralNode(JSContext context, AbstractConstantArray arrayType, Object array, long length) {
            super(context);
            this.arrayType = arrayType;
            this.array = array;
            this.length = length;
        }

        @Override
        public JSArrayObject execute(VirtualFrame frame) {
            return JSArray.create(this.context, this.getRealm(), this.arrayType, this.array, this.length);
        }

        @Override
        protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
            return this.copy();
        }
    }

    @NodeInfo(cost=NodeCost.MONOMORPHIC)
    private static final class DefaultObjectArrayWithEmptyLiteralNode
    extends DefaultArrayLiteralNode {
        DefaultObjectArrayWithEmptyLiteralNode(JSContext context, JavaScriptNode[] elements2) {
            super(context, elements2);
            assert (elements2.length > 0);
        }

        @Override
        @ExplodeLoop
        public JSArrayObject execute(VirtualFrame frame) {
            Object[] primitiveArray = new Object[this.elements.length];
            int holeCount = 0;
            int holesBeforeLastNonEmpty = 0;
            int arrayOffset = 0;
            int lastNonEmpty = -1;
            for (int i = 0; i < this.elements.length; ++i) {
                if (this.elements[i] instanceof EmptyNode) {
                    ++holeCount;
                    if (i != arrayOffset) continue;
                    ++arrayOffset;
                    continue;
                }
                primitiveArray[i] = this.elements[i].execute(frame);
                lastNonEmpty = i;
                holesBeforeLastNonEmpty = holeCount;
            }
            int usedLength = lastNonEmpty + 1 - arrayOffset;
            int holesInUsedLength = holesBeforeLastNonEmpty - arrayOffset;
            return JSArray.createZeroBasedHolesObjectArray(this.context, this.getRealm(), primitiveArray, usedLength, arrayOffset, holesInUsedLength);
        }

        @Override
        protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
            return new DefaultObjectArrayWithEmptyLiteralNode(this.context, DefaultObjectArrayWithEmptyLiteralNode.cloneUninitialized(this.elements, materializedTags));
        }
    }

    @NodeInfo(cost=NodeCost.MONOMORPHIC)
    private static class DefaultArrayLiteralOneElementNode
    extends DefaultArrayLiteralBaseNode {
        @Node.Child
        protected JavaScriptNode child;

        DefaultArrayLiteralOneElementNode(JSContext context, JavaScriptNode child) {
            super(context);
            this.child = child;
        }

        @Override
        protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
            return new DefaultArrayLiteralOneElementNode(this.context, DefaultArrayLiteralOneElementNode.cloneUninitialized(this.child, materializedTags));
        }

        @Override
        protected int getLength() {
            return 1;
        }

        @Override
        protected JavaScriptNode getElement(int index) {
            assert (index == 0);
            return this.child;
        }
    }

    @NodeInfo(cost=NodeCost.MONOMORPHIC)
    private static class DefaultArrayLiteralNode
    extends DefaultArrayLiteralBaseNode {
        @Node.Children
        protected final JavaScriptNode[] elements;

        DefaultArrayLiteralNode(JSContext context, JavaScriptNode[] elements2) {
            super(context);
            this.elements = elements2;
        }

        @Override
        protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
            return new DefaultArrayLiteralNode(this.context, DefaultArrayLiteralNode.cloneUninitialized(this.elements, materializedTags));
        }

        @Override
        protected int getLength() {
            return this.elements.length;
        }

        @Override
        protected JavaScriptNode getElement(int index) {
            return this.elements[index];
        }
    }

    private static abstract class DefaultArrayLiteralBaseNode
    extends ArrayLiteralNode {
        @CompilerDirectives.CompilationFinal
        protected byte state;
        protected static final byte INT_ARRAY = 1;
        protected static final byte DOUBLE_ARRAY = 2;
        protected static final byte OBJECT_ARRAY = 3;
        @CompilerDirectives.CompilationFinal
        protected boolean seenUnexpectedInteger;

        DefaultArrayLiteralBaseNode(JSContext context) {
            super(context);
        }

        protected abstract int getLength();

        protected abstract JavaScriptNode getElement(int var1);

        protected final JSArrayObject executeAndSpecialize(Object[] values) {
            CompilerAsserts.neverPartOfCompilation();
            Object primitive = ArrayLiteralNode.createPrimitiveArray(values, false);
            JSRealm realm = this.getRealm();
            if (primitive instanceof int[]) {
                this.state = 1;
                return JSArray.createZeroBasedIntArray(this.context, realm, (int[])primitive);
            }
            if (primitive instanceof double[]) {
                this.state = (byte)2;
                return JSArray.createZeroBasedDoubleArray(this.context, realm, (double[])primitive);
            }
            if (primitive instanceof Object[]) {
                this.state = (byte)3;
                return JSArray.createZeroBasedObjectArray(this.context, realm, values);
            }
            throw Errors.shouldNotReachHere();
        }

        @Override
        public JSArrayObject execute(VirtualFrame frame) {
            if (this.state == 0) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                Object[] values = new Object[this.getLength()];
                for (int i = 0; i < this.getLength(); ++i) {
                    values[i] = this.getElement(i).execute(frame);
                }
                return this.executeAndSpecialize(values);
            }
            if (this.state == 1) {
                return this.executeZeroBasedIntArray(frame);
            }
            if (this.state == 2) {
                return this.executeZeroBasedDoubleArray(frame);
            }
            assert (this.state == 3);
            return this.executeZeroBasedObjectArray(frame);
        }

        @ExplodeLoop
        private JSArrayObject executeZeroBasedIntArray(VirtualFrame frame) {
            int[] primitiveArray = new int[this.getLength()];
            for (int i = 0; i < this.getLength(); ++i) {
                try {
                    primitiveArray[i] = this.getElement(i).executeInt(frame);
                    continue;
                }
                catch (UnexpectedResultException e) {
                    CompilerDirectives.transferToInterpreterAndInvalidate();
                    assert (!(e.getResult() instanceof Integer));
                    return this.executeIntArrayFallback(frame, primitiveArray, i, e.getResult());
                }
            }
            return JSArray.createZeroBasedIntArray(this.context, this.getRealm(), primitiveArray);
        }

        private JSArrayObject executeIntArrayFallback(VirtualFrame frame, int[] primitiveArray, int failIdx, Object failValue) {
            Object[] objectArray = new Object[this.getLength()];
            for (int j = 0; j < failIdx; ++j) {
                objectArray[j] = primitiveArray[j];
            }
            return this.executeFallback(frame, objectArray, failIdx, failValue);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @ExplodeLoop
        private JSArrayObject executeZeroBasedDoubleArray(VirtualFrame frame) {
            double[] primitiveArray = new double[this.getLength()];
            int i = 0;
            while (i < this.getLength()) {
                try {
                    double doubleValue;
                    if (this.seenUnexpectedInteger) {
                        Object objectValue = this.getElement(i).execute(frame);
                        if (objectValue instanceof Double) {
                            doubleValue = (Double)objectValue;
                        } else {
                            if (!(objectValue instanceof Integer)) {
                                CompilerDirectives.transferToInterpreterAndInvalidate();
                                throw new UnexpectedResultException(objectValue);
                            }
                            doubleValue = ((Integer)objectValue).intValue();
                        }
                    } else {
                        doubleValue = this.getElement(i).executeDouble(frame);
                    }
                    primitiveArray[i] = doubleValue;
                }
                catch (UnexpectedResultException e) {
                    CompilerDirectives.transferToInterpreterAndInvalidate();
                    assert (!(e.getResult() instanceof Double));
                    if (!(e.getResult() instanceof Integer)) {
                        return this.executeDoubleArrayFallback(frame, primitiveArray, i, e.getResult());
                    }
                    primitiveArray[i] = ((Integer)e.getResult()).intValue();
                    this.seenUnexpectedInteger = true;
                }
                ++i;
            }
            return JSArray.createZeroBasedDoubleArray(this.context, this.getRealm(), primitiveArray);
        }

        private JSArrayObject executeDoubleArrayFallback(VirtualFrame frame, double[] primitiveArray, int failIdx, Object failValue) {
            Object[] objectArray = new Object[this.getLength()];
            for (int j = 0; j < failIdx; ++j) {
                objectArray[j] = primitiveArray[j];
            }
            return this.executeFallback(frame, objectArray, failIdx, failValue);
        }

        @ExplodeLoop
        private JSArrayObject executeZeroBasedObjectArray(VirtualFrame frame) {
            Object[] primitiveArray = new Object[this.getLength()];
            for (int i = 0; i < this.getLength(); ++i) {
                primitiveArray[i] = this.getElement(i).execute(frame);
            }
            return JSArray.createZeroBasedObjectArray(this.context, this.getRealm(), primitiveArray);
        }

        private JSArrayObject executeFallback(VirtualFrame frame, Object[] objectArray, int failingIndex, Object failingValue) {
            objectArray[failingIndex] = failingValue;
            for (int j = failingIndex + 1; j < this.getLength(); ++j) {
                objectArray[j] = this.getElement(j).execute(frame);
            }
            return this.executeAndSpecialize(objectArray);
        }
    }
}

