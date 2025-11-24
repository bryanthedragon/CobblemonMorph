
package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.builtins.ArrayFunctionBuiltins;
import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.builtins.TypedArrayFunctionBuiltinsFactory;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.JSArrayBufferView;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.builtins.JSTypedArrayObject;
import com.oracle.truffle.js.runtime.objects.IteratorRecord;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.util.SimpleArrayList;

public final class TypedArrayFunctionBuiltins
extends JSBuiltinsContainer.SwitchEnum<TypedArrayFunction> {
    public static final JSBuiltinsContainer BUILTINS = new TypedArrayFunctionBuiltins();

    protected TypedArrayFunctionBuiltins() {
        super(JSArrayBufferView.CLASS_NAME, TypedArrayFunction.class);
    }

    @Override
    protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, TypedArrayFunction builtinEnum) {
        switch (builtinEnum) {
            case of: {
                return TypedArrayFunctionBuiltinsFactory.TypedArrayOfNodeGen.create(context, builtin, TypedArrayFunctionBuiltins.args().withThis().varArgs().createArgumentNodes(context));
            }
            case from: {
                return TypedArrayFunctionBuiltinsFactory.TypedArrayFromNodeGen.create(context, builtin, TypedArrayFunctionBuiltins.args().withThis().varArgs().createArgumentNodes(context));
            }
        }
        return null;
    }

    public static abstract class TypedArrayFromNode
    extends ArrayFunctionBuiltins.JSArrayFromNode {
        private final BranchProfile growProfile = BranchProfile.create();

        public TypedArrayFromNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin, true);
        }

        @Override
        @Specialization
        protected JSDynamicObject arrayFrom(Object thisObj, Object[] args) {
            Object source = JSRuntime.getArgOrUndefined(args, 0);
            Object mapFn = JSRuntime.getArgOrUndefined(args, 1);
            Object thisArg = JSRuntime.getArgOrUndefined(args, 2);
            if (!JSFunction.isConstructor(thisObj)) {
                throw Errors.createTypeErrorNotAConstructor(thisObj, this.getContext());
            }
            return this.arrayFromIntl(thisObj, source, mapFn, thisArg, false);
        }

        @Override
        protected JSDynamicObject arrayFromIterable(Object thisObj, Object items, Object usingIterator, Object mapFn, Object thisArg, boolean mapping) {
            Object next;
            SimpleArrayList<Object> values = new SimpleArrayList<Object>();
            IteratorRecord iteratorRecord = this.getIterator(items, usingIterator);
            while ((next = this.iteratorStep(iteratorRecord)) != Boolean.FALSE) {
                Object nextValue = this.getIteratorValue((JSDynamicObject)next);
                values.add(nextValue, this.growProfile);
            }
            int len = values.size();
            JSTypedArrayObject obj = this.getArraySpeciesConstructorNode().typedArrayCreate((JSDynamicObject)thisObj, len);
            for (int k = 0; k < len; ++k) {
                Object mapped = values.get(k);
                if (mapping) {
                    mapped = this.callMapFn(thisArg, (JSDynamicObject)mapFn, mapped, k);
                }
                this.writeOwn((Object)obj, k, mapped);
            }
            return obj;
        }
    }

    public static abstract class TypedArrayOfNode
    extends ArrayFunctionBuiltins.JSArrayFunctionOperation {
        public TypedArrayOfNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin, true);
        }

        @Specialization
        protected JSDynamicObject arrayOf(Object thisObj, Object ... args) {
            if (!this.isTypedArrayConstructor(thisObj)) {
                throw Errors.createTypeErrorNotAConstructor(thisObj, this.getContext());
            }
            int len = args.length;
            JSTypedArrayObject newObj = this.getArraySpeciesConstructorNode().typedArrayCreate((JSDynamicObject)thisObj, len);
            for (int k = 0; k < len; ++k) {
                Object kValue = args[k];
                this.write((Object)newObj, k, kValue);
            }
            return newObj;
        }
    }

    public static enum TypedArrayFunction implements BuiltinEnum<TypedArrayFunction>
    {
        of(0),
        from(1);

        private final int length;

        private TypedArrayFunction(int length) {
            this.length = length;
        }

        @Override
        public int getLength() {
            return this.length;
        }
    }
}

