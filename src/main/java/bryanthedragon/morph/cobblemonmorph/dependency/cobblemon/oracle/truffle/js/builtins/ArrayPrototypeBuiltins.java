
package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleSafepoint;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.exception.AbstractTruffleException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.interop.ExceptionType;
import com.oracle.truffle.api.interop.InteropException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.InvalidArrayIndexException;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.interop.UnsupportedTypeException;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.nodes.DirectCallNode;
import com.oracle.truffle.api.nodes.LoopNode;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.SlowPathException;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.profiles.ValueProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.api.strings.TruffleStringBuilder;
import com.oracle.truffle.js.builtins.ArrayPrototypeBuiltinsFactory;
import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.builtins.helper.JSCollectionsNormalizeNode;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JSNodeUtil;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.access.CreateObjectNode;
import com.oracle.truffle.js.nodes.access.ForEachIndexCallNode;
import com.oracle.truffle.js.nodes.access.GetPrototypeNode;
import com.oracle.truffle.js.nodes.access.IsArrayNode;
import com.oracle.truffle.js.nodes.access.JSHasPropertyNode;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.access.PropertyNode;
import com.oracle.truffle.js.nodes.access.PropertySetNode;
import com.oracle.truffle.js.nodes.access.ReadElementNode;
import com.oracle.truffle.js.nodes.access.WriteElementNode;
import com.oracle.truffle.js.nodes.access.WritePropertyNode;
import com.oracle.truffle.js.nodes.array.ArrayCreateNode;
import com.oracle.truffle.js.nodes.array.ArrayLengthNode;
import com.oracle.truffle.js.nodes.array.JSArrayDeleteRangeNode;
import com.oracle.truffle.js.nodes.array.JSArrayFirstElementIndexNode;
import com.oracle.truffle.js.nodes.array.JSArrayLastElementIndexNode;
import com.oracle.truffle.js.nodes.array.JSArrayNextElementIndexNode;
import com.oracle.truffle.js.nodes.array.JSArrayPreviousElementIndexNode;
import com.oracle.truffle.js.nodes.array.JSArrayToDenseObjectArrayNode;
import com.oracle.truffle.js.nodes.array.JSGetLengthNode;
import com.oracle.truffle.js.nodes.array.JSSetLengthNode;
import com.oracle.truffle.js.nodes.array.TestArrayNode;
import com.oracle.truffle.js.nodes.binary.JSIdenticalNode;
import com.oracle.truffle.js.nodes.cast.JSToBooleanNode;
import com.oracle.truffle.js.nodes.cast.JSToIntegerAsIntNode;
import com.oracle.truffle.js.nodes.cast.JSToIntegerAsLongNode;
import com.oracle.truffle.js.nodes.cast.JSToObjectNode;
import com.oracle.truffle.js.nodes.cast.JSToPropertyKeyNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.nodes.control.DeletePropertyNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.nodes.interop.ForeignObjectPrototypeNode;
import com.oracle.truffle.js.nodes.interop.ImportValueNode;
import com.oracle.truffle.js.nodes.unary.IsCallableNode;
import com.oracle.truffle.js.nodes.unary.IsConstructorNode;
import com.oracle.truffle.js.nodes.unary.JSIsArrayNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.JavaScriptRootNode;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.array.SparseArray;
import com.oracle.truffle.js.runtime.array.TypedArray;
import com.oracle.truffle.js.runtime.array.dyn.AbstractDoubleArray;
import com.oracle.truffle.js.runtime.array.dyn.AbstractIntArray;
import com.oracle.truffle.js.runtime.array.dyn.ConstantByteArray;
import com.oracle.truffle.js.runtime.array.dyn.ConstantDoubleArray;
import com.oracle.truffle.js.runtime.array.dyn.ConstantIntArray;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.JSAbstractArray;
import com.oracle.truffle.js.runtime.builtins.JSArray;
import com.oracle.truffle.js.runtime.builtins.JSArrayBufferView;
import com.oracle.truffle.js.runtime.builtins.JSArrayObject;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.builtins.JSFunctionData;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.builtins.JSMap;
import com.oracle.truffle.js.runtime.builtins.JSMapObject;
import com.oracle.truffle.js.runtime.builtins.JSOrdinary;
import com.oracle.truffle.js.runtime.builtins.JSProxy;
import com.oracle.truffle.js.runtime.builtins.JSSlowArray;
import com.oracle.truffle.js.runtime.builtins.JSTypedArrayObject;
import com.oracle.truffle.js.runtime.interop.JSInteropUtil;
import com.oracle.truffle.js.runtime.objects.JSAttributes;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;
import com.oracle.truffle.js.runtime.objects.Null;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.JSHashMap;
import com.oracle.truffle.js.runtime.util.Pair;
import com.oracle.truffle.js.runtime.util.SimpleArrayList;
import com.oracle.truffle.js.runtime.util.StringBuilderProfile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class ArrayPrototypeBuiltins
extends JSBuiltinsContainer.SwitchEnum<ArrayPrototype> {
    public static final JSBuiltinsContainer BUILTINS = new ArrayPrototypeBuiltins();

    protected ArrayPrototypeBuiltins() {
        super(JSArray.PROTOTYPE_NAME, ArrayPrototype.class);
    }

    @Override
    protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, ArrayPrototype builtinEnum) {
        switch (builtinEnum) {
            case push: {
                return ArrayPrototypeBuiltinsFactory.JSArrayPushNodeGen.create(context, builtin, ArrayPrototypeBuiltins.args().withThis().varArgs().createArgumentNodes(context));
            }
            case pop: {
                return ArrayPrototypeBuiltinsFactory.JSArrayPopNodeGen.create(context, builtin, ArrayPrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
            case slice: {
                return ArrayPrototypeBuiltinsFactory.JSArraySliceNodeGen.create(context, builtin, false, ArrayPrototypeBuiltins.args().withThis().fixedArgs(2).createArgumentNodes(context));
            }
            case shift: {
                return ArrayPrototypeBuiltinsFactory.JSArrayShiftNodeGen.create(context, builtin, ArrayPrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
            case unshift: {
                return ArrayPrototypeBuiltinsFactory.JSArrayUnshiftNodeGen.create(context, builtin, ArrayPrototypeBuiltins.args().withThis().varArgs().createArgumentNodes(context));
            }
            case toString: {
                return ArrayPrototypeBuiltinsFactory.JSArrayToStringNodeGen.create(context, builtin, ArrayPrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
            case concat: {
                return ArrayPrototypeBuiltinsFactory.JSArrayConcatNodeGen.create(context, builtin, ArrayPrototypeBuiltins.args().withThis().varArgs().createArgumentNodes(context));
            }
            case indexOf: {
                return ArrayPrototypeBuiltinsFactory.JSArrayIndexOfNodeGen.create(context, builtin, false, true, ArrayPrototypeBuiltins.args().withThis().varArgs().createArgumentNodes(context));
            }
            case lastIndexOf: {
                return ArrayPrototypeBuiltinsFactory.JSArrayIndexOfNodeGen.create(context, builtin, false, false, ArrayPrototypeBuiltins.args().withThis().varArgs().createArgumentNodes(context));
            }
            case join: {
                return ArrayPrototypeBuiltinsFactory.JSArrayJoinNodeGen.create(context, builtin, false, ArrayPrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
            case toLocaleString: {
                return ArrayPrototypeBuiltinsFactory.JSArrayToLocaleStringNodeGen.create(context, builtin, false, ArrayPrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
            case splice: {
                return ArrayPrototypeBuiltinsFactory.JSArraySpliceNodeGen.create(context, builtin, ArrayPrototypeBuiltins.args().withThis().varArgs().createArgumentNodes(context));
            }
            case every: {
                return ArrayPrototypeBuiltinsFactory.JSArrayEveryNodeGen.create(context, builtin, false, ArrayPrototypeBuiltins.args().withThis().fixedArgs(2).createArgumentNodes(context));
            }
            case filter: {
                return ArrayPrototypeBuiltinsFactory.JSArrayFilterNodeGen.create(context, builtin, false, ArrayPrototypeBuiltins.args().withThis().fixedArgs(2).createArgumentNodes(context));
            }
            case forEach: {
                return ArrayPrototypeBuiltinsFactory.JSArrayForEachNodeGen.create(context, builtin, ArrayPrototypeBuiltins.args().withThis().fixedArgs(2).createArgumentNodes(context));
            }
            case some: {
                return ArrayPrototypeBuiltinsFactory.JSArraySomeNodeGen.create(context, builtin, false, ArrayPrototypeBuiltins.args().withThis().fixedArgs(2).createArgumentNodes(context));
            }
            case map: {
                return ArrayPrototypeBuiltinsFactory.JSArrayMapNodeGen.create(context, builtin, false, ArrayPrototypeBuiltins.args().withThis().fixedArgs(2).createArgumentNodes(context));
            }
            case sort: {
                return ArrayPrototypeBuiltinsFactory.JSArraySortNodeGen.create(context, builtin, false, ArrayPrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
            case reduce: {
                return ArrayPrototypeBuiltinsFactory.JSArrayReduceNodeGen.create(context, builtin, false, true, ArrayPrototypeBuiltins.args().withThis().fixedArgs(1).varArgs().createArgumentNodes(context));
            }
            case reduceRight: {
                return ArrayPrototypeBuiltinsFactory.JSArrayReduceNodeGen.create(context, builtin, false, false, ArrayPrototypeBuiltins.args().withThis().fixedArgs(1).varArgs().createArgumentNodes(context));
            }
            case reverse: {
                return ArrayPrototypeBuiltinsFactory.JSArrayReverseNodeGen.create(context, builtin, ArrayPrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
            case find: {
                return ArrayPrototypeBuiltinsFactory.JSArrayFindNodeGen.create(context, builtin, false, false, ArrayPrototypeBuiltins.args().withThis().fixedArgs(2).createArgumentNodes(context));
            }
            case findIndex: {
                return ArrayPrototypeBuiltinsFactory.JSArrayFindIndexNodeGen.create(context, builtin, false, false, ArrayPrototypeBuiltins.args().withThis().fixedArgs(2).createArgumentNodes(context));
            }
            case findLast: {
                return ArrayPrototypeBuiltinsFactory.JSArrayFindNodeGen.create(context, builtin, false, true, ArrayPrototypeBuiltins.args().withThis().fixedArgs(2).createArgumentNodes(context));
            }
            case findLastIndex: {
                return ArrayPrototypeBuiltinsFactory.JSArrayFindIndexNodeGen.create(context, builtin, false, true, ArrayPrototypeBuiltins.args().withThis().fixedArgs(2).createArgumentNodes(context));
            }
            case fill: {
                return ArrayPrototypeBuiltinsFactory.JSArrayFillNodeGen.create(context, builtin, false, ArrayPrototypeBuiltins.args().withThis().fixedArgs(3).createArgumentNodes(context));
            }
            case copyWithin: {
                return ArrayPrototypeBuiltinsFactory.JSArrayCopyWithinNodeGen.create(context, builtin, false, ArrayPrototypeBuiltins.args().withThis().fixedArgs(3).createArgumentNodes(context));
            }
            case keys: {
                return ArrayPrototypeBuiltinsFactory.JSArrayIteratorNodeGen.create(context, builtin, 1, ArrayPrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
            case values: {
                return ArrayPrototypeBuiltinsFactory.JSArrayIteratorNodeGen.create(context, builtin, 2, ArrayPrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
            case entries: {
                return ArrayPrototypeBuiltinsFactory.JSArrayIteratorNodeGen.create(context, builtin, 3, ArrayPrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
            case includes: {
                return ArrayPrototypeBuiltinsFactory.JSArrayIncludesNodeGen.create(context, builtin, false, ArrayPrototypeBuiltins.args().withThis().fixedArgs(2).createArgumentNodes(context));
            }
            case flatMap: {
                return ArrayPrototypeBuiltinsFactory.JSArrayFlatMapNodeGen.create(context, builtin, ArrayPrototypeBuiltins.args().withThis().fixedArgs(2).createArgumentNodes(context));
            }
            case flat: {
                return ArrayPrototypeBuiltinsFactory.JSArrayFlatNodeGen.create(context, builtin, ArrayPrototypeBuiltins.args().withThis().fixedArgs(3).createArgumentNodes(context));
            }
            case at: {
                return ArrayPrototypeBuiltinsFactory.JSArrayAtNodeGen.create(context, builtin, false, ArrayPrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
            case group: {
                return ArrayPrototypeBuiltinsFactory.JSArrayGroupNodeGen.create(context, builtin, ArrayPrototypeBuiltins.args().withThis().fixedArgs(2).createArgumentNodes(context));
            }
            case groupToMap: {
                return ArrayPrototypeBuiltinsFactory.JSArrayGroupToMapNodeGen.create(context, builtin, ArrayPrototypeBuiltins.args().withThis().fixedArgs(2).createArgumentNodes(context));
            }
        }
        return null;
    }

    public static abstract class JSArrayGroupToMapNode
    extends JSArrayGroupBaseNode {
        @Node.Child
        private JSCollectionsNormalizeNode normalizeKeyNode = JSCollectionsNormalizeNode.create();

        public JSArrayGroupToMapNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected Object groupToMap(Object thisObj, Object callback, Object thisArg) {
            Map<Object, List<Object>> groups = this.collectGroupResults(thisObj, callback, thisArg);
            JSMapObject map = JSMap.create(this.getContext(), this.getRealm());
            return this.createGroupResult(map, groups);
        }

        @Override
        protected final Object toKey(Object callbackResult) {
            return this.normalizeKeyNode.execute(callbackResult);
        }

        @CompilerDirectives.TruffleBoundary
        protected Object createGroupResult(JSMapObject map, Map<Object, List<Object>> groups) {
            JSHashMap internalMap = JSMap.getInternalMap(map);
            JSRealm realm = this.getRealm();
            for (Map.Entry<Object, List<Object>> entry : groups.entrySet()) {
                JSArrayObject elements2 = JSArray.createConstant(this.getContext(), realm, entry.getValue().toArray());
                internalMap.put(entry.getKey(), elements2);
            }
            return map;
        }
    }

    public static abstract class JSArrayGroupNode
    extends JSArrayGroupBaseNode {
        @Node.Child
        private JSToPropertyKeyNode toPropertyKeyNode = JSToPropertyKeyNode.create();

        public JSArrayGroupNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected Object group(Object thisObj, Object callback, Object thisArg) {
            Map<Object, List<Object>> groups = this.collectGroupResults(thisObj, callback, thisArg);
            JSObject obj = JSOrdinary.createWithNullPrototype(this.getContext());
            return this.createGroupResult(obj, groups);
        }

        @Override
        protected final Object toKey(Object callbackResult) {
            return this.toPropertyKeyNode.execute(callbackResult);
        }

        @CompilerDirectives.TruffleBoundary
        protected Object createGroupResult(JSDynamicObject obj, Map<Object, List<Object>> groups) {
            for (Map.Entry<Object, List<Object>> entry : groups.entrySet()) {
                JSArrayObject elements2 = JSArray.createConstant(this.getContext(), this.getRealm(), entry.getValue().toArray());
                JSObjectUtil.defineDataProperty(this.getContext(), obj, entry.getKey(), elements2, JSAttributes.getDefault());
            }
            return obj;
        }
    }

    public static abstract class JSArrayGroupBaseNode
    extends JSArrayOperation {
        @Node.Child
        private JSFunctionCallNode callNode = JSFunctionCallNode.createCall();

        protected JSArrayGroupBaseNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin, false);
        }

        protected Map<Object, List<Object>> collectGroupResults(Object thisObj, Object callback, Object thisArg) {
            Object thisJSObj = this.toObject(thisObj);
            long length = this.getLength(thisJSObj);
            Object callbackFn = this.checkCallbackIsFunction(callback);
            Map<Object, List<Object>> groups = JSArrayGroupBaseNode.initGroups();
            for (long k = 0L; k < length; ++k) {
                Object kValue = this.read(thisObj, k);
                Object key = this.toKey(this.callNode.executeCall(JSArguments.create(thisArg, callbackFn, kValue, k, thisJSObj)));
                JSArrayGroupBaseNode.addValueToKeyedGroup(groups, key, kValue);
            }
            return groups;
        }

        @CompilerDirectives.TruffleBoundary
        private static Map<Object, List<Object>> initGroups() {
            return new LinkedHashMap<Object, List<Object>>();
        }

        @CompilerDirectives.TruffleBoundary
        private static void addValueToKeyedGroup(Map<Object, List<Object>> groups, Object key, Object value2) {
            List<Object> group = groups.get(key);
            if (group == null) {
                group = new ArrayList<Object>();
                groups.put(key, group);
            }
            group.add(value2);
        }

        protected abstract Object toKey(Object var1);
    }

    public static abstract class JSArrayAtNode
    extends JSArrayOperationWithToInt {
        public JSArrayAtNode(JSContext context, JSBuiltin builtin, boolean isTypedArrayImplementation) {
            super(context, builtin, isTypedArrayImplementation);
        }

        @Specialization
        protected Object at(Object thisObj, Object index) {
            Object o = this.toObjectOrValidateTypedArray(thisObj);
            long length = this.getLength(o);
            long relativeIndex = this.toIntegerAsLong(index);
            long k = relativeIndex >= 0L ? relativeIndex : length + relativeIndex;
            if (k < 0L || k >= length) {
                return Undefined.instance;
            }
            return this.read(o, k);
        }
    }

    public static abstract class JSArrayIteratorNode
    extends JSBuiltinNode {
        @Node.Child
        private CreateArrayIteratorNode createArrayIteratorNode;

        public JSArrayIteratorNode(JSContext context, JSBuiltin builtin, int iterationKind) {
            super(context, builtin);
            this.createArrayIteratorNode = CreateArrayIteratorNode.create(context, iterationKind);
        }

        @Specialization(guards={"isJSObject(thisObj)"})
        protected JSDynamicObject doJSObject(JSDynamicObject thisObj) {
            return this.createArrayIteratorNode.execute(thisObj);
        }

        @Specialization(guards={"!isJSObject(thisObj)"})
        protected JSDynamicObject doNotJSObject(Object thisObj, @Cached(value="createToObject(getContext())") JSToObjectNode toObjectNode) {
            return this.createArrayIteratorNode.execute(toObjectNode.execute(thisObj));
        }
    }

    public static class CreateArrayIteratorNode
    extends JavaScriptBaseNode {
        private final int iterationKind;
        @Node.Child
        private CreateObjectNode.CreateObjectWithPrototypeNode createObjectNode;
        @Node.Child
        private PropertySetNode setNextIndexNode;
        @Node.Child
        private PropertySetNode setIteratedObjectNode;
        @Node.Child
        private PropertySetNode setIterationKindNode;

        protected CreateArrayIteratorNode(JSContext context, int iterationKind) {
            this.iterationKind = iterationKind;
            this.createObjectNode = CreateObjectNode.createOrdinaryWithPrototype(context);
            this.setIteratedObjectNode = PropertySetNode.createSetHidden(JSRuntime.ITERATED_OBJECT_ID, context);
            this.setNextIndexNode = PropertySetNode.createSetHidden(JSRuntime.ITERATOR_NEXT_INDEX, context);
            this.setIterationKindNode = PropertySetNode.createSetHidden(JSArray.ARRAY_ITERATION_KIND_ID, context);
        }

        public static CreateArrayIteratorNode create(JSContext context, int iterationKind) {
            return new CreateArrayIteratorNode(context, iterationKind);
        }

        public JSDynamicObject execute(Object array) {
            assert (JSGuards.isJSObject(array) || JSGuards.isForeignObject(array));
            JSDynamicObject iterator = this.createObjectNode.execute(this.getRealm().getArrayIteratorPrototype());
            this.setIteratedObjectNode.setValue(iterator, array);
            this.setNextIndexNode.setValue(iterator, 0L);
            this.setIterationKindNode.setValueInt(iterator, this.iterationKind);
            return iterator;
        }
    }

    public static abstract class JSArrayReverseNode
    extends JSArrayOperation {
        @Node.Child
        private TestArrayNode hasHolesNode;
        @Node.Child
        private DeletePropertyNode deletePropertyNode;
        private final ConditionProfile bothExistProfile = ConditionProfile.createBinaryProfile();
        private final ConditionProfile onlyUpperExistsProfile = ConditionProfile.createBinaryProfile();
        private final ConditionProfile onlyLowerExistsProfile = ConditionProfile.createBinaryProfile();

        public JSArrayReverseNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
            this.hasHolesNode = TestArrayNode.createHasHoles();
        }

        private boolean deleteProperty(Object array, long index) {
            if (this.deletePropertyNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.deletePropertyNode = this.insert(DeletePropertyNode.create(true, this.getContext()));
            }
            return this.deletePropertyNode.executeEvaluated(array, index);
        }

        @Specialization
        protected Object reverseJSArray(JSArrayObject thisObj) {
            return this.reverse(thisObj, true);
        }

        @Specialization(replaces={"reverseJSArray"})
        protected Object reverseGeneric(Object thisObj) {
            Object array = this.toObject(thisObj);
            return this.reverse(array, JSArray.isJSArray(array));
        }

        private Object reverse(Object array, boolean isArray) {
            boolean hasHoles;
            long length = this.getLength(array);
            long lower = 0L;
            long upper = length - 1L;
            boolean bl = hasHoles = isArray && this.hasHolesNode.executeBoolean((JSDynamicObject)array);
            while (lower < upper) {
                boolean upperExists;
                boolean lowerExists;
                Object lowerValue = null;
                Object upperValue = null;
                if (this.getContext().getEcmaScriptVersion() < 6) {
                    lowerValue = this.read(array, lower);
                    upperValue = this.read(array, upper);
                    lowerExists = lowerValue != Undefined.instance || this.hasProperty(array, lower);
                    upperExists = upperValue != Undefined.instance || this.hasProperty(array, upper);
                } else {
                    lowerExists = this.hasProperty(array, lower);
                    if (lowerExists) {
                        lowerValue = this.read(array, lower);
                    }
                    if (upperExists = this.hasProperty(array, upper)) {
                        upperValue = this.read(array, upper);
                    }
                }
                if (this.bothExistProfile.profile(lowerExists && upperExists)) {
                    this.write(array, lower, upperValue);
                    this.write(array, upper, lowerValue);
                } else if (this.onlyUpperExistsProfile.profile(!lowerExists && upperExists)) {
                    this.write(array, lower, upperValue);
                    this.deleteProperty(array, upper);
                } else if (this.onlyLowerExistsProfile.profile(lowerExists && !upperExists)) {
                    this.deleteProperty(array, lower);
                    this.write(array, upper, lowerValue);
                } else assert (!lowerExists && !upperExists);
                if (hasHoles) {
                    long nextUpper;
                    long nextLower = this.nextElementIndex(array, lower, length);
                    if (length - nextLower - 1L >= (nextUpper = this.previousElementIndex(array, upper))) {
                        lower = nextLower;
                        upper = length - lower - 1L;
                    } else {
                        lower = length - nextUpper - 1L;
                        upper = nextUpper;
                    }
                } else {
                    ++lower;
                    --upper;
                }
                TruffleSafepoint.poll(this);
            }
            this.reportLoopCount(lower);
            return array;
        }
    }

    public static abstract class JSArrayIncludesNode
    extends JSArrayOperationWithToInt {
        public JSArrayIncludesNode(JSContext context, JSBuiltin builtin, boolean isTypedArrayImplementation) {
            super(context, builtin, isTypedArrayImplementation);
        }

        @Specialization
        protected boolean includes(Object thisValue, Object searchElement, Object fromIndex, @Cached(value="createSameValueZero()") JSIdenticalNode identicalNode) {
            long k;
            Object thisObj = this.toObjectOrValidateTypedArray(thisValue);
            long len = this.getLength(thisObj);
            if (len == 0L) {
                return false;
            }
            long n = this.toIntegerAsLong(fromIndex);
            if (n >= 0L) {
                k = n;
            } else {
                k = len + n;
                if (k < 0L) {
                    k = 0L;
                }
            }
            if (!identicalNode.executeBoolean(searchElement, searchElement)) {
                return true;
            }
            long startIdx = k;
            while (k < len) {
                Object currentElement = this.read(thisObj, k);
                if (identicalNode.executeBoolean(searchElement, currentElement)) {
                    this.reportLoopCount(k - startIdx);
                    return true;
                }
                ++k;
                TruffleSafepoint.poll(this);
            }
            this.reportLoopCount(len - startIdx);
            return false;
        }
    }

    public static abstract class JSArrayCopyWithinNode
    extends JSArrayOperationWithToInt {
        @Node.Child
        private DeletePropertyNode deletePropertyNode;
        private final ConditionProfile offsetProfile1 = ConditionProfile.createBinaryProfile();
        private final ConditionProfile offsetProfile2 = ConditionProfile.createBinaryProfile();
        private final ConditionProfile offsetProfile3 = ConditionProfile.createBinaryProfile();

        public JSArrayCopyWithinNode(JSContext context, JSBuiltin builtin, boolean isTypedArrayImplementation) {
            super(context, builtin, isTypedArrayImplementation);
            this.deletePropertyNode = DeletePropertyNode.create(true, context);
        }

        @Specialization
        protected Object copyWithin(Object thisObj, Object target, Object start2, Object end2) {
            long count;
            Object obj = this.toObjectOrValidateTypedArray(thisObj);
            long len = this.getLength(obj);
            long to = JSRuntime.getOffset(this.toIntegerAsLong(target), len, this.offsetProfile1);
            long from = JSRuntime.getOffset(this.toIntegerAsLong(start2), len, this.offsetProfile2);
            long finalIdx = end2 == Undefined.instance ? len : JSRuntime.getOffset(this.toIntegerAsLong(end2), len, this.offsetProfile3);
            long expectedCount = count = Math.min(finalIdx - from, len - to);
            if (count > 0L) {
                long direction;
                if (this.isTypedArrayImplementation) {
                    this.checkHasDetachedBuffer((JSDynamicObject)thisObj);
                }
                if (from < to && to < from + count) {
                    direction = -1L;
                    from = from + count - 1L;
                    to = to + count - 1L;
                } else {
                    direction = 1L;
                }
                while (count > 0L) {
                    if (this.isTypedArrayImplementation || this.hasProperty(obj, from)) {
                        Object fromVal = this.read(obj, from);
                        this.write(obj, to, fromVal);
                    } else {
                        this.deletePropertyNode.executeEvaluated(obj, to);
                    }
                    from += direction;
                    to += direction;
                    --count;
                    TruffleSafepoint.poll(this);
                }
                this.reportLoopCount(expectedCount);
            }
            return obj;
        }
    }

    public static abstract class JSArrayFillNode
    extends JSArrayOperationWithToInt {
        private final ConditionProfile offsetProfile1 = ConditionProfile.createBinaryProfile();
        private final ConditionProfile offsetProfile2 = ConditionProfile.createBinaryProfile();

        public JSArrayFillNode(JSContext context, JSBuiltin builtin, boolean isTypedArrayImplementation) {
            super(context, builtin, isTypedArrayImplementation);
        }

        @Specialization
        protected Object fill(Object thisObj, Object value2, Object start2, Object end2) {
            Object thisJSObj = this.toObjectOrValidateTypedArray(thisObj);
            long len = this.getLength(thisJSObj);
            long lStart = JSRuntime.getOffset(this.toIntegerAsLong(start2), len, this.offsetProfile1);
            long lEnd = end2 == Undefined.instance ? len : JSRuntime.getOffset(this.toIntegerAsLong(end2), len, this.offsetProfile2);
            for (long idx = lStart; idx < lEnd; ++idx) {
                this.write(thisJSObj, idx, value2);
                TruffleSafepoint.poll(this);
            }
            this.reportLoopCount(lEnd - lStart);
            return thisJSObj;
        }
    }

    public static abstract class JSArrayReduceNode
    extends ArrayForEachIndexCallOperation {
        private final boolean isForward;
        private final BranchProfile findInitialValueBranch = BranchProfile.create();
        @Node.Child
        private ForEachIndexCallNode forEachIndexFindInitialNode;

        public JSArrayReduceNode(JSContext context, JSBuiltin builtin, boolean isTypedArrayImplementation, boolean isForward) {
            super(context, builtin, isTypedArrayImplementation);
            this.isForward = isForward;
        }

        @Specialization
        protected Object reduce(Object thisObj, Object callback, Object ... initialValueOpt) {
            long currentIndex;
            Object thisJSObj = this.toObjectOrValidateTypedArray(thisObj);
            long length = this.getLength(thisJSObj);
            Object callbackFn = this.checkCallbackIsFunction(callback);
            Object currentValue = initialValueOpt.length > 0 ? initialValueOpt[0] : null;
            long l = currentIndex = this.isForward() ? 0L : length - 1L;
            if (currentValue == null) {
                this.findInitialValueBranch.enter();
                Pair<Long, Object> res = this.findInitialValue(thisJSObj, currentIndex, length);
                currentIndex = res.getFirst() + (long)(this.isForward() ? 1 : -1);
                currentValue = res.getSecond();
            }
            return this.forEachIndexCall(thisJSObj, callbackFn, Undefined.instance, currentIndex, length, currentValue);
        }

        @Override
        protected boolean isForward() {
            return this.isForward;
        }

        protected final Pair<Long, Object> findInitialValue(Object arrayObj, long fromIndex, long length) {
            Pair res;
            if (length >= 0L && (res = (Pair)this.getForEachIndexFindInitialNode().executeForEachIndex(arrayObj, null, null, fromIndex, length, null)) != null) {
                return res;
            }
            this.errorBranch.enter();
            throw JSArrayReduceNode.reduceNoInitialValueError();
        }

        private ForEachIndexCallNode getForEachIndexFindInitialNode() {
            if (this.forEachIndexFindInitialNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.forEachIndexFindInitialNode = this.insert(ForEachIndexCallNode.create(this.getContext(), null, new ForEachIndexCallNode.MaybeResultNode(){

                    @Override
                    public ForEachIndexCallNode.MaybeResult<Object> apply(long index, Object value2, Object callbackResult, Object currentResult) {
                        return ForEachIndexCallNode.MaybeResult.returnResult(new Pair<Long, Object>(index, value2));
                    }
                }, this.isForward(), this.shouldCheckHasProperty()));
            }
            return this.forEachIndexFindInitialNode;
        }

        @Override
        protected ForEachIndexCallNode.CallbackNode makeCallbackNode() {
            return new ArrayForEachIndexCallOperation.DefaultCallbackNode(){

                @Override
                public Object apply(long index, Object value2, Object target, Object callback, Object callbackThisArg, Object currentResult) {
                    return this.callNode.executeCall(JSArguments.create(callbackThisArg, callback, currentResult, value2, JSRuntime.boxIndex(index, this.indexInIntRangeCondition), target));
                }
            };
        }

        @Override
        protected ForEachIndexCallNode.MaybeResultNode makeMaybeResultNode() {
            return new ForEachIndexCallNode.MaybeResultNode(){

                @Override
                public ForEachIndexCallNode.MaybeResult<Object> apply(long index, Object value2, Object callbackResult, Object currentResult) {
                    return ForEachIndexCallNode.MaybeResult.continueResult(callbackResult);
                }
            };
        }

        @CompilerDirectives.TruffleBoundary
        protected static RuntimeException reduceNoInitialValueError() {
            throw Errors.createTypeError("Reduce of empty array with no initial value");
        }
    }

    public static abstract class JSArraySortNode
    extends JSArrayOperation {
        @Node.Child
        private DeletePropertyNode deletePropertyNode;
        private final ConditionProfile isSparse = ConditionProfile.create();
        private final BranchProfile hasCompareFnBranch = BranchProfile.create();
        private final BranchProfile noCompareFnBranch = BranchProfile.create();
        private final BranchProfile growProfile = BranchProfile.create();
        @Node.Child
        private InteropLibrary interopNode;
        @Node.Child
        private ImportValueNode importValueNode;

        public JSArraySortNode(JSContext context, JSBuiltin builtin, boolean isTypedArrayImplementation) {
            super(context, builtin, isTypedArrayImplementation);
        }

        @Specialization(guards={"!isTypedArrayImplementation", "isJSFastArray(thisObj)"}, assumptions={"getContext().getArrayPrototypeNoElementsAssumption()"})
        protected JSDynamicObject sortArray(JSDynamicObject thisObj, Object compare, @Cached(value="create(getContext())") JSArrayToDenseObjectArrayNode arrayToObjectArrayNode, @Cached(value="create(getContext(), true)") JSArrayDeleteRangeNode arrayDeleteRangeNode) {
            this.checkCompareFunction(compare);
            long len = this.getLength(thisObj);
            if (len < 2L) {
                return thisObj;
            }
            ScriptArray scriptArray = JSAbstractArray.arrayGetArrayType(thisObj);
            Object[] array = arrayToObjectArrayNode.executeObjectArray(thisObj, scriptArray, len);
            JSArraySortNode.sortIntl(this.getComparator(thisObj, compare), array);
            this.reportLoopCount(len);
            for (int i = 0; i < array.length; ++i) {
                this.write((Object)thisObj, i, array[i]);
            }
            if (this.isSparse.profile((long)array.length < len)) {
                arrayDeleteRangeNode.execute(thisObj, scriptArray, array.length, len);
            }
            return thisObj;
        }

        private void delete(Object obj, Object i) {
            if (this.deletePropertyNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                JSContext context = this.getContext();
                this.deletePropertyNode = this.insert(DeletePropertyNode.create(true, context));
            }
            this.deletePropertyNode.executeEvaluated(obj, i);
        }

        @Specialization
        protected Object sort(Object thisObj, Object comparefn, @Cached(value="createBinaryProfile()") ConditionProfile isJSObject) {
            this.checkCompareFunction(comparefn);
            Object thisJSObj = this.toObjectOrValidateTypedArray(thisObj);
            if (isJSObject.profile(JSDynamicObject.isJSDynamicObject(thisJSObj))) {
                return this.sortJSObject(comparefn, (JSDynamicObject)thisJSObj);
            }
            return this.sortForeignObject(comparefn, thisJSObj);
        }

        private JSDynamicObject sortJSObject(Object comparefn, JSDynamicObject thisJSObj) {
            long len = this.getLength(thisJSObj);
            if (len == 0L) {
                return thisJSObj;
            }
            Object[] array = this.jsobjectToArray(thisJSObj, len);
            Comparator<Object> comparator = this.getComparator(thisJSObj, comparefn);
            if (this.isTypedArrayImplementation && comparefn == Undefined.instance) {
                assert (comparator == null);
                JSArraySortNode.prepareForDefaultComparator(array);
            }
            JSArraySortNode.sortIntl(comparator, array);
            this.reportLoopCount(len);
            for (int i = 0; i < array.length; ++i) {
                this.write((Object)thisJSObj, i, array[i]);
            }
            if (this.isSparse.profile((long)array.length < len)) {
                this.deleteGenericElements(thisJSObj, array.length, len);
            }
            return thisJSObj;
        }

        public Object sortForeignObject(Object comparefn, Object thisObj) {
            assert (JSGuards.isForeignObject(thisObj));
            long len = this.getLength(thisObj);
            if (len < 2L) {
                return thisObj;
            }
            if (len >= Integer.MAX_VALUE) {
                this.errorBranch.enter();
                throw Errors.createRangeErrorInvalidArrayLength();
            }
            Object[] array = this.foreignArrayToObjectArray(thisObj, (int)len);
            Comparator<Object> comparator = this.getComparator(thisObj, comparefn);
            JSArraySortNode.sortIntl(comparator, array);
            this.reportLoopCount(len);
            for (int i = 0; i < array.length; ++i) {
                this.write(thisObj, i, array[i]);
            }
            return thisObj;
        }

        private void checkCompareFunction(Object compare) {
            if (compare != Undefined.instance && !this.isCallable(compare)) {
                this.errorBranch.enter();
                throw Errors.createTypeError("The comparison function must be either a function or undefined");
            }
        }

        private Comparator<Object> getComparator(Object thisObj, Object compare) {
            if (compare == Undefined.instance) {
                this.noCompareFnBranch.enter();
                return this.getDefaultComparator(thisObj);
            }
            assert (this.isCallable(compare));
            this.hasCompareFnBranch.enter();
            return new SortComparator(compare);
        }

        private Comparator<Object> getDefaultComparator(Object thisObj) {
            if (this.isTypedArrayImplementation) {
                return null;
            }
            if (JSArray.isJSArray(thisObj)) {
                ScriptArray array = JSAbstractArray.arrayGetArrayType((JSDynamicObject)thisObj);
                if (array instanceof AbstractIntArray || array instanceof ConstantByteArray || array instanceof ConstantIntArray) {
                    return JSArray.DEFAULT_JSARRAY_INTEGER_COMPARATOR;
                }
                if (array instanceof AbstractDoubleArray || array instanceof ConstantDoubleArray) {
                    return JSArray.DEFAULT_JSARRAY_DOUBLE_COMPARATOR;
                }
            }
            return JSArray.DEFAULT_JSARRAY_COMPARATOR;
        }

        private void deleteGenericElements(Object obj, long fromIndex, long toIndex) {
            for (long index = fromIndex; index < toIndex; ++index) {
                this.delete(obj, index);
            }
        }

        @CompilerDirectives.TruffleBoundary
        private static void sortIntl(Comparator<Object> comparator, Object[] array) {
            try {
                Arrays.sort(array, comparator);
            }
            catch (IllegalArgumentException illegalArgumentException) {
                // empty catch block
            }
        }

        private static void prepareForDefaultComparator(Object[] array) {
            boolean needsConversion = false;
            Class<?> clazz = array[0].getClass();
            for (Object element : array) {
                Class<?> c = element.getClass();
                if (clazz == c) continue;
                needsConversion = true;
                break;
            }
            if (needsConversion) {
                for (int i = 0; i < array.length; ++i) {
                    array[i] = JSRuntime.toDouble(array[i]);
                }
            }
        }

        @CompilerDirectives.TruffleBoundary
        private Object[] jsobjectToArray(JSDynamicObject thisObj, long len) {
            SimpleArrayList<Object> list = SimpleArrayList.create(len);
            for (long k = 0L; k < len; ++k) {
                if (!JSObject.hasProperty(thisObj, k)) continue;
                list.add(JSObject.get(thisObj, k), this.growProfile);
            }
            return list.toArray();
        }

        private Object[] foreignArrayToObjectArray(Object thisObj, int len) {
            InteropLibrary interop = this.interopNode;
            ImportValueNode importValue = this.importValueNode;
            if (interop == null || importValue == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.interopNode = interop = this.insert(InteropLibrary.getFactory().createDispatched(5));
                this.importValueNode = importValue = this.insert(ImportValueNode.create());
            }
            Object[] array = new Object[len];
            for (int index = 0; index < len; ++index) {
                array[index] = JSInteropUtil.readArrayElementOrDefault(thisObj, index, Undefined.instance, interop, importValue, this);
            }
            return array;
        }

        private class SortComparator
        implements Comparator<Object> {
            private final Object compFnObj;
            private final boolean isFunction;

            SortComparator(Object compFnObj) {
                this.compFnObj = compFnObj;
                this.isFunction = JSFunction.isJSFunction(compFnObj);
            }

            @Override
            public int compare(Object arg0, Object arg1) {
                if (arg0 == Undefined.instance) {
                    if (arg1 == Undefined.instance) {
                        return 0;
                    }
                    return 1;
                }
                if (arg1 == Undefined.instance) {
                    return -1;
                }
                Object retObj = this.isFunction ? JSFunction.call((JSFunctionObject)this.compFnObj, Undefined.instance, new Object[]{arg0, arg1}) : JSRuntime.call(this.compFnObj, Undefined.instance, new Object[]{arg0, arg1});
                return this.convertResult(retObj);
            }

            private int convertResult(Object retObj) {
                if (retObj instanceof Integer) {
                    return (Integer)retObj;
                }
                double d = JSRuntime.toDouble(retObj);
                if (d < 0.0) {
                    return -1;
                }
                if (d > 0.0) {
                    return 1;
                }
                return 0;
            }
        }
    }

    public static abstract class JSArrayFindIndexNode
    extends JSArrayOperation {
        @Node.Child
        private JSToBooleanNode toBooleanNode = JSToBooleanNode.create();
        @Node.Child
        private JSFunctionCallNode callNode = JSFunctionCallNode.createCall();
        private final boolean isLast;

        public JSArrayFindIndexNode(JSContext context, JSBuiltin builtin, boolean isTypedArrayImplementation, boolean isLast) {
            super(context, builtin, isTypedArrayImplementation);
            this.isLast = isLast;
        }

        private Object callPredicate(Object function, Object target, Object value2, double index, Object thisObj) {
            return this.callNode.executeCall(JSArguments.create(target, function, value2, index, thisObj));
        }

        @Specialization
        protected Object findIndex(Object thisObj, Object callback, Object thisArg) {
            long idx;
            Object thisJSObj = this.toObjectOrValidateTypedArray(thisObj);
            long length = this.getLength(thisJSObj);
            Object callbackFn = this.checkCallbackIsFunction(callback);
            long l = idx = this.isLast ? length - 1L : 0L;
            while (this.isLast ? idx >= 0L : idx < length) {
                Object value2 = this.read(thisObj, idx);
                Object callbackResult = this.callPredicate(callbackFn, thisArg, value2, idx, thisJSObj);
                boolean testResult = this.toBooleanNode.executeBoolean(callbackResult);
                if (testResult) {
                    this.reportLoopCount(this.isLast ? length - idx - 1L : idx);
                    return JSRuntime.positiveLongToIntOrDouble(idx);
                }
                idx = this.isLast ? idx - 1L : idx + 1L;
            }
            this.reportLoopCount(length);
            return -1;
        }
    }

    public static abstract class JSArrayFindNode
    extends JSArrayOperation {
        @Node.Child
        private JSToBooleanNode toBooleanNode = JSToBooleanNode.create();
        @Node.Child
        private JSFunctionCallNode callNode = JSFunctionCallNode.createCall();
        private final boolean isLast;

        public JSArrayFindNode(JSContext context, JSBuiltin builtin, boolean isTypedArrayImplementation, boolean isLast) {
            super(context, builtin, isTypedArrayImplementation);
            this.isLast = isLast;
        }

        private Object callPredicate(Object function, Object target, Object value2, double index, Object thisObj) {
            return this.callNode.executeCall(JSArguments.create(target, function, value2, index, thisObj));
        }

        @Specialization
        protected Object find(Object thisObj, Object callback, Object thisArg) {
            long idx;
            Object thisJSObj = this.toObjectOrValidateTypedArray(thisObj);
            long length = this.getLength(thisJSObj);
            Object callbackFn = this.checkCallbackIsFunction(callback);
            long l = idx = this.isLast ? length - 1L : 0L;
            while (this.isLast ? idx >= 0L : idx < length) {
                Object value2 = this.read(thisObj, idx);
                Object callbackResult = this.callPredicate(callbackFn, thisArg, value2, idx, thisJSObj);
                boolean testResult = this.toBooleanNode.executeBoolean(callbackResult);
                if (testResult) {
                    this.reportLoopCount(this.isLast ? length - idx - 1L : idx);
                    return value2;
                }
                idx = this.isLast ? idx - 1L : idx + 1L;
            }
            this.reportLoopCount(length);
            return Undefined.instance;
        }
    }

    public static abstract class JSArrayFlatNode
    extends JSArrayOperation {
        @Node.Child
        private JSToIntegerAsIntNode toIntegerNode;

        public JSArrayFlatNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin, false);
        }

        @Specialization
        protected Object flat(Object thisObj, Object depth, @Cached(value="createFlattenIntoArrayNode(getContext())") FlattenIntoArrayNode flattenIntoArrayNode) {
            Object thisJSObj = this.toObject(thisObj);
            long length = this.getLength(thisJSObj);
            long depthNum = depth == Undefined.instance ? 1L : (long)this.toIntegerAsInt(depth);
            Object resultArray = this.getArraySpeciesConstructorNode().createEmptyContainer(thisJSObj, 0L);
            flattenIntoArrayNode.flatten((JSDynamicObject)resultArray, thisJSObj, length, 0L, depthNum, null, null);
            return resultArray;
        }

        private int toIntegerAsInt(Object depth) {
            if (this.toIntegerNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.toIntegerNode = this.insert(JSToIntegerAsIntNode.create());
            }
            return this.toIntegerNode.executeInt(depth);
        }

        protected static final FlattenIntoArrayNode createFlattenIntoArrayNode(JSContext context) {
            return ArrayPrototypeBuiltinsFactory.FlattenIntoArrayNodeGen.create(context, false);
        }
    }

    public static abstract class JSArrayFlatMapNode
    extends JSArrayOperation {
        public JSArrayFlatMapNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin, false);
        }

        @Specialization
        protected Object flatMap(Object thisObj, Object callback, Object thisArg, @Cached(value="createFlattenIntoArrayNode(getContext())") FlattenIntoArrayNode flattenIntoArrayNode) {
            Object thisJSObj = this.toObject(thisObj);
            long length = this.getLength(thisJSObj);
            Object callbackFn = this.checkCallbackIsFunction(callback);
            Object resultArray = this.getArraySpeciesConstructorNode().createEmptyContainer(thisJSObj, 0L);
            flattenIntoArrayNode.flatten((JSDynamicObject)resultArray, thisJSObj, length, 0L, 1L, callbackFn, thisArg);
            return resultArray;
        }

        protected static final FlattenIntoArrayNode createFlattenIntoArrayNode(JSContext context) {
            return ArrayPrototypeBuiltinsFactory.FlattenIntoArrayNodeGen.create(context, true);
        }
    }

    public static abstract class FlattenIntoArrayNode
    extends JavaScriptBaseNode {
        protected final JSContext context;
        protected final boolean withMapCallback;
        @Node.Child
        private ForEachIndexCallNode forEachIndexNode;
        @Node.Child
        private JSToObjectNode toObjectNode;
        @Node.Child
        private JSGetLengthNode getLengthNode;

        protected FlattenIntoArrayNode(JSContext context, boolean withMapCallback) {
            this.context = context;
            this.withMapCallback = withMapCallback;
        }

        public static FlattenIntoArrayNode create(JSContext context, boolean withCallback) {
            return ArrayPrototypeBuiltinsFactory.FlattenIntoArrayNodeGen.create(context, withCallback);
        }

        protected abstract long executeLong(JSDynamicObject var1, Object var2, long var3, long var5, long var7, Object var9, Object var10);

        @Specialization
        protected long flatten(JSDynamicObject target, Object source, long sourceLen, long start2, long depth, Object callback, Object thisArg) {
            boolean callbackUndefined = callback == null;
            FlattenState flattenState = new FlattenState(target, start2, depth, callbackUndefined);
            Object thisJSObj = this.toObject(source);
            this.forEachIndexCall(thisJSObj, callback, thisArg, 0L, sourceLen, flattenState);
            return flattenState.targetIndex;
        }

        protected final Object forEachIndexCall(Object arrayObj, Object callbackObj, Object thisArg, long fromIndex, long length, Object initialResult) {
            if (this.forEachIndexNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.forEachIndexNode = this.insert(this.makeForEachIndexCallNode());
            }
            return this.forEachIndexNode.executeForEachIndex(arrayObj, callbackObj, thisArg, fromIndex, length, initialResult);
        }

        private ForEachIndexCallNode makeForEachIndexCallNode() {
            return ForEachIndexCallNode.create(this.context, this.makeCallbackNode(), this.makeMaybeResultNode(), true, true);
        }

        protected ForEachIndexCallNode.CallbackNode makeCallbackNode() {
            return this.withMapCallback ? new ArrayForEachIndexCallOperation.DefaultCallbackNode() : null;
        }

        protected final Object toObject(Object target) {
            if (this.toObjectNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.toObjectNode = this.insert(JSToObjectNode.createToObject(this.context));
            }
            return this.toObjectNode.execute(target);
        }

        protected long getLength(Object thisObject) {
            if (this.getLengthNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.getLengthNode = this.insert(JSGetLengthNode.create(this.context));
            }
            return this.getLengthNode.executeLong(thisObject);
        }

        protected ForEachIndexCallNode.MaybeResultNode makeMaybeResultNode() {
            return new ForEachIndexCallNode.MaybeResultNode(){
                protected final BranchProfile errorBranch = BranchProfile.create();
                @Node.Child
                private WriteElementNode writeOwnNode;
                @Node.Child
                private DirectCallNode innerFlattenCall;
                {
                    this.writeOwnNode = WriteElementNode.create(context, true, true);
                }

                @Override
                public ForEachIndexCallNode.MaybeResult<Object> apply(long index, Object originalValue, Object callbackResult, Object resultState) {
                    Object value2;
                    boolean shouldFlatten = false;
                    FlattenState state = (FlattenState)resultState;
                    Object object = value2 = state.callbackUndefined ? originalValue : callbackResult;
                    if (state.depth > 0L) {
                        shouldFlatten = JSRuntime.isArray(value2);
                    }
                    if (shouldFlatten) {
                        long elementLen = this.getLength(this.toObject(value2));
                        state.targetIndex = this.makeFlattenCall(state.resultArray, value2, elementLen, state.targetIndex, state.depth - 1L);
                    } else {
                        if (state.targetIndex >= JSRuntime.MAX_SAFE_INTEGER_LONG) {
                            this.errorBranch.enter();
                            throw Errors.createTypeError("Index out of bounds in flatten into array");
                        }
                        this.writeOwnNode.executeWithTargetAndIndexAndValue((Object)state.resultArray, state.targetIndex++, value2);
                    }
                    return ForEachIndexCallNode.MaybeResult.continueResult(resultState);
                }

                private long makeFlattenCall(JSDynamicObject targetArray, Object element, long elementLength, long targetIndex, long depth) {
                    if (this.innerFlattenCall == null) {
                        CompilerDirectives.transferToInterpreterAndInvalidate();
                        JSFunctionData flattenFunctionData = context.getOrCreateBuiltinFunctionData(JSContext.BuiltinFunctionKey.ArrayFlattenIntoArray, c -> FlattenIntoArrayNode.createOrGetFlattenCallFunctionData(c));
                        this.innerFlattenCall = this.insert(DirectCallNode.create(flattenFunctionData.getCallTarget()));
                    }
                    return (Long)this.innerFlattenCall.call(targetArray, element, elementLength, targetIndex, depth);
                }
            };
        }

        private static JSFunctionData createOrGetFlattenCallFunctionData(JSContext context) {
            return JSFunctionData.createCallOnly(context, new InnerFlattenCallNode(context, FlattenIntoArrayNode.create(context, false)).getCallTarget(), 0, Strings.EMPTY_STRING);
        }

        static final class FlattenState {
            final JSDynamicObject resultArray;
            final boolean callbackUndefined;
            final long depth;
            long targetIndex;

            FlattenState(JSDynamicObject result, long toIndex, long depth, boolean callbackUndefined) {
                this.resultArray = result;
                this.callbackUndefined = callbackUndefined;
                this.targetIndex = toIndex;
                this.depth = depth;
            }
        }

        private static final class InnerFlattenCallNode
        extends JavaScriptRootNode {
            @Node.Child
            private FlattenIntoArrayNode flattenNode;

            InnerFlattenCallNode(JSContext context, FlattenIntoArrayNode flattenNode) {
                super(context.getLanguage(), null, null);
                this.flattenNode = flattenNode;
            }

            @Override
            public Object execute(VirtualFrame frame) {
                Object[] arguments = frame.getArguments();
                JSDynamicObject resultArray = (JSDynamicObject)arguments[0];
                Object element = arguments[1];
                long elementLen = (Long)arguments[2];
                long targetIndex = (Long)arguments[3];
                long depth = (Long)arguments[4];
                return this.flattenNode.flatten(resultArray, element, elementLen, targetIndex, depth, null, null);
            }
        }
    }

    public static abstract class JSArrayMapNode
    extends ArrayForEachIndexCallOperation {
        public JSArrayMapNode(JSContext context, JSBuiltin builtin, boolean isTypedArrayImplementation) {
            super(context, builtin, isTypedArrayImplementation);
        }

        @Specialization
        protected Object map(Object thisObj, Object callback, Object thisArg) {
            Object thisJSObj = this.toObjectOrValidateTypedArray(thisObj);
            long length = this.getLength(thisJSObj);
            Object callbackFn = this.checkCallbackIsFunction(callback);
            Object resultArray = this.getArraySpeciesConstructorNode().createEmptyContainer(thisJSObj, length);
            return this.forEachIndexCall(thisJSObj, callbackFn, thisArg, 0L, length, resultArray);
        }

        @Override
        protected ForEachIndexCallNode.MaybeResultNode makeMaybeResultNode() {
            return new ForEachIndexCallNode.MaybeResultNode(){
                @Node.Child
                private WriteElementNode writeOwnNode;
                {
                    this.writeOwnNode = WriteElementNode.create(this.getContext(), true, true);
                }

                @Override
                public ForEachIndexCallNode.MaybeResult<Object> apply(long index, Object value2, Object callbackResult, Object currentResult) {
                    this.writeOwnNode.executeWithTargetAndIndexAndValue(currentResult, index, callbackResult);
                    return ForEachIndexCallNode.MaybeResult.continueResult(currentResult);
                }
            };
        }
    }

    public static abstract class JSArraySomeNode
    extends ArrayForEachIndexCallOperation {
        public JSArraySomeNode(JSContext context, JSBuiltin builtin, boolean isTypedArrayImplementation) {
            super(context, builtin, isTypedArrayImplementation);
        }

        @Specialization
        protected boolean some(Object thisObj, Object callback, Object thisArg) {
            Object thisJSObj = this.toObjectOrValidateTypedArray(thisObj);
            long length = this.getLength(thisJSObj);
            Object callbackFn = this.checkCallbackIsFunction(callback);
            return (Boolean)this.forEachIndexCall(thisJSObj, callbackFn, thisArg, 0L, length, false);
        }

        @Override
        protected ForEachIndexCallNode.MaybeResultNode makeMaybeResultNode() {
            return new ForEachIndexCallNode.MaybeResultNode(){
                @Node.Child
                private JSToBooleanNode toBooleanNode = JSToBooleanNode.create();

                @Override
                public ForEachIndexCallNode.MaybeResult<Object> apply(long index, Object value2, Object callbackResult, Object currentResult) {
                    return this.toBooleanNode.executeBoolean(callbackResult) ? ForEachIndexCallNode.MaybeResult.returnResult(true) : ForEachIndexCallNode.MaybeResult.continueResult(currentResult);
                }
            };
        }
    }

    public static abstract class JSArrayForEachNode
    extends ArrayForEachIndexCallOperation {
        public JSArrayForEachNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected Object forEach(Object thisObj, Object callback, Object thisArg) {
            Object thisJSObj = this.toObject(thisObj);
            long length = this.getLength(thisJSObj);
            Object callbackFn = this.checkCallbackIsFunction(callback);
            return this.forEachIndexCall(thisJSObj, callbackFn, thisArg, 0L, length, Undefined.instance);
        }

        @Override
        protected ForEachIndexCallNode.MaybeResultNode makeMaybeResultNode() {
            return new ForEachIndexCallNode.MaybeResultNode(){

                @Override
                public ForEachIndexCallNode.MaybeResult<Object> apply(long index, Object value2, Object callbackResult, Object currentResult) {
                    return ForEachIndexCallNode.MaybeResult.continueResult(currentResult);
                }
            };
        }
    }

    public static abstract class JSArrayFilterNode
    extends ArrayForEachIndexCallOperation {
        private final ValueProfile arrayTypeProfile = ValueProfile.createClassProfile();
        private final ValueProfile resultArrayTypeProfile = ValueProfile.createClassProfile();

        public JSArrayFilterNode(JSContext context, JSBuiltin builtin, boolean isTypedArrayImplementation) {
            super(context, builtin, isTypedArrayImplementation);
        }

        @Specialization
        protected JSDynamicObject filter(Object thisObj, Object callback, Object thisArg) {
            Object thisJSObj = this.toObjectOrValidateTypedArray(thisObj);
            long length = this.getLength(thisJSObj);
            Object callbackFn = this.checkCallbackIsFunction(callback);
            JSDynamicObject resultArray = this.isTypedArrayImplementation ? JSArray.createEmpty(this.getContext(), this.getRealm(), 0) : (JSDynamicObject)this.getArraySpeciesConstructorNode().arraySpeciesCreate(thisJSObj, 0L);
            this.forEachIndexCall(thisJSObj, callbackFn, thisArg, 0L, length, new FilterState(resultArray, 0L));
            if (this.isTypedArrayImplementation) {
                return this.getTypedResult((JSDynamicObject)thisJSObj, resultArray);
            }
            return resultArray;
        }

        private JSTypedArrayObject getTypedResult(JSDynamicObject thisJSObj, JSDynamicObject resultArray) {
            long resultLen = JSAbstractArray.arrayGetLength(resultArray);
            JSTypedArrayObject typedResult = this.getArraySpeciesConstructorNode().typedArraySpeciesCreate(thisJSObj, JSRuntime.longToIntOrDouble(resultLen));
            TypedArray typedArray = this.arrayTypeProfile.profile(JSArrayBufferView.typedArrayGetArrayType(typedResult));
            ScriptArray array = this.resultArrayTypeProfile.profile(JSAbstractArray.arrayGetArrayType(resultArray));
            for (long i = 0L; i < resultLen; ++i) {
                typedArray.setElement(typedResult, i, array.getElement(resultArray, i), true);
                TruffleSafepoint.poll(this);
            }
            return typedResult;
        }

        @Override
        protected ForEachIndexCallNode.MaybeResultNode makeMaybeResultNode() {
            return new ForEachIndexCallNode.MaybeResultNode(){
                @Node.Child
                private JSToBooleanNode toBooleanNode = JSToBooleanNode.create();
                @Node.Child
                private WriteElementNode writeOwnNode = WriteElementNode.create(this.getContext(), true, true);

                @Override
                public ForEachIndexCallNode.MaybeResult<Object> apply(long index, Object value2, Object callbackResult, Object currentResult) {
                    if (this.toBooleanNode.executeBoolean(callbackResult)) {
                        FilterState filterState = (FilterState)currentResult;
                        this.writeOwnNode.executeWithTargetAndIndexAndValue((Object)filterState.resultArray, filterState.toIndex++, value2);
                    }
                    return ForEachIndexCallNode.MaybeResult.continueResult(currentResult);
                }
            };
        }

        static final class FilterState {
            final JSDynamicObject resultArray;
            long toIndex;

            FilterState(JSDynamicObject resultArray, long toIndex) {
                this.resultArray = resultArray;
                this.toIndex = toIndex;
            }
        }
    }

    public static abstract class JSArrayEveryNode
    extends ArrayForEachIndexCallOperation {
        public JSArrayEveryNode(JSContext context, JSBuiltin builtin, boolean isTypedArrayImplementation) {
            super(context, builtin, isTypedArrayImplementation);
        }

        @Specialization
        protected boolean every(Object thisObj, Object callback, Object thisArg) {
            Object thisJSObj = this.toObjectOrValidateTypedArray(thisObj);
            long length = this.getLength(thisJSObj);
            Object callbackFn = this.checkCallbackIsFunction(callback);
            return (Boolean)this.forEachIndexCall(thisJSObj, callbackFn, thisArg, 0L, length, true);
        }

        @Override
        protected ForEachIndexCallNode.MaybeResultNode makeMaybeResultNode() {
            return new ForEachIndexCallNode.MaybeResultNode(){
                @Node.Child
                private JSToBooleanNode toBooleanNode = JSToBooleanNode.create();

                @Override
                public ForEachIndexCallNode.MaybeResult<Object> apply(long index, Object value2, Object callbackResult, Object currentResult) {
                    return this.toBooleanNode.executeBoolean(callbackResult) ? ForEachIndexCallNode.MaybeResult.continueResult(currentResult) : ForEachIndexCallNode.MaybeResult.returnResult(false);
                }
            };
        }
    }

    public static abstract class ArrayForEachIndexCallOperation
    extends JSArrayOperation {
        @Node.Child
        private ForEachIndexCallNode forEachIndexNode;

        public ArrayForEachIndexCallOperation(JSContext context, JSBuiltin builtin, boolean isTypedArrayImplementation) {
            super(context, builtin, isTypedArrayImplementation);
        }

        public ArrayForEachIndexCallOperation(JSContext context, JSBuiltin builtin) {
            this(context, builtin, false);
        }

        protected final Object forEachIndexCall(Object arrayObj, Object callbackObj, Object thisArg, long fromIndex, long length, Object initialResult) {
            if (this.forEachIndexNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.forEachIndexNode = this.insert(this.makeForEachIndexCallNode());
            }
            return this.forEachIndexNode.executeForEachIndex(arrayObj, callbackObj, thisArg, fromIndex, length, initialResult);
        }

        private ForEachIndexCallNode makeForEachIndexCallNode() {
            return ForEachIndexCallNode.create(this.getContext(), this.makeCallbackNode(), this.makeMaybeResultNode(), this.isForward(), this.shouldCheckHasProperty());
        }

        protected boolean isForward() {
            return true;
        }

        protected boolean shouldCheckHasProperty() {
            return !this.isTypedArrayImplementation;
        }

        protected ForEachIndexCallNode.CallbackNode makeCallbackNode() {
            return new DefaultCallbackNode();
        }

        protected abstract ForEachIndexCallNode.MaybeResultNode makeMaybeResultNode();

        protected static class DefaultCallbackNode
        extends ForEachIndexCallNode.CallbackNode {
            @Node.Child
            protected JSFunctionCallNode callNode = JSFunctionCallNode.createCall();
            protected final ConditionProfile indexInIntRangeCondition = ConditionProfile.createBinaryProfile();

            protected DefaultCallbackNode() {
            }

            @Override
            public Object apply(long index, Object value2, Object target, Object callback, Object callbackThisArg, Object currentResult) {
                return this.callNode.executeCall(JSArguments.create(callbackThisArg, callback, value2, JSRuntime.boxIndex(index, this.indexInIntRangeCondition), target));
            }
        }
    }

    public static abstract class JSArraySpliceNode
    extends JSArrayOperationWithToInt {
        @Node.Child
        private DeletePropertyNode deletePropertyNode;
        private final BranchProfile branchA = BranchProfile.create();
        private final BranchProfile branchB = BranchProfile.create();
        private final BranchProfile branchDelete = BranchProfile.create();
        private final BranchProfile objectBranch = BranchProfile.create();
        private final ConditionProfile argsLength0Profile = ConditionProfile.createBinaryProfile();
        private final ConditionProfile argsLength1Profile = ConditionProfile.createBinaryProfile();
        private final ConditionProfile offsetProfile = ConditionProfile.createBinaryProfile();
        private final BranchProfile needMoveDeleteBranch = BranchProfile.create();
        private final BranchProfile needInsertBranch = BranchProfile.create();
        @Node.Child
        private InteropLibrary arrayInterop;

        public JSArraySpliceNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
            this.deletePropertyNode = DeletePropertyNode.create(true, context);
        }

        @Specialization
        protected JSDynamicObject splice(Object thisArg, Object[] args, @Cached(value="create(getContext())") SpliceJSArrayNode spliceJSArray) {
            long actualDeleteCount;
            long insertCount;
            Object thisObj = this.toObject(thisArg);
            long len = this.getLength(thisObj);
            long actualStart = JSRuntime.getOffset(this.toIntegerAsLong(JSRuntime.getArgOrUndefined(args, 0)), len, this.offsetProfile);
            if (this.argsLength0Profile.profile(args.length == 0)) {
                insertCount = 0L;
                actualDeleteCount = 0L;
            } else if (this.argsLength1Profile.profile(args.length == 1)) {
                insertCount = 0L;
                actualDeleteCount = len - actualStart;
            } else {
                assert (args.length >= 2);
                insertCount = args.length - 2;
                long deleteCount = this.toIntegerAsLong(JSRuntime.getArgOrUndefined(args, 1));
                actualDeleteCount = Math.min(Math.max(deleteCount, 0L), len - actualStart);
            }
            if (len + insertCount - actualDeleteCount > JSRuntime.MAX_SAFE_INTEGER_LONG) {
                this.errorBranch.enter();
                JSArraySpliceNode.throwLengthError();
            }
            JSDynamicObject aObj = (JSDynamicObject)this.getArraySpeciesConstructorNode().createEmptyContainer(thisObj, actualDeleteCount);
            if (actualDeleteCount > 0L) {
                this.branchDelete.enter();
                this.spliceRead(thisObj, actualStart, actualDeleteCount, aObj, len);
            }
            this.setLength((Object)aObj, actualDeleteCount);
            long itemCount = insertCount;
            boolean isJSArray = JSArray.isJSArray(thisObj);
            if (isJSArray) {
                JSArrayObject dynObj = (JSArrayObject)thisObj;
                ScriptArray arrayType = JSAbstractArray.arrayGetArrayType(dynObj);
                spliceJSArray.execute(dynObj, len, actualStart, actualDeleteCount, itemCount, arrayType, this);
            } else if (JSDynamicObject.isJSDynamicObject(thisObj)) {
                this.objectBranch.enter();
                this.spliceJSObject(thisObj, len, actualStart, actualDeleteCount, itemCount);
            } else {
                this.spliceForeignArray(thisObj, len, actualStart, actualDeleteCount, itemCount);
            }
            if (itemCount > 0L) {
                this.needInsertBranch.enter();
                this.spliceInsert(thisObj, actualStart, args);
            }
            long newLength = len - actualDeleteCount + itemCount;
            this.setLength(thisObj, newLength);
            this.reportLoopCount(len);
            return aObj;
        }

        final boolean mustUseElementwise(JSDynamicObject obj, long expectedLength, ScriptArray array, GetPrototypeNode getPrototypeNode) {
            return array instanceof SparseArray || array.isLengthNotWritable() || getPrototypeNode.execute(obj) != this.getRealm().getArrayPrototype() || !this.getContext().getArrayPrototypeNoElementsAssumption().isValid() || !this.getContext().getFastArrayAssumption().isValid() && JSSlowArray.isJSSlowArray(obj) || array.length(obj) != expectedLength;
        }

        private void spliceRead(Object thisObj, long actualStart, long actualDeleteCount, JSDynamicObject aObj, long length) {
            long kPlusStart = actualStart;
            if (!this.hasProperty(thisObj, kPlusStart)) {
                kPlusStart = this.nextElementIndex(thisObj, kPlusStart, length);
            }
            while (kPlusStart < actualDeleteCount + actualStart) {
                Object fromValue = this.read(thisObj, kPlusStart);
                this.writeOwn((Object)aObj, kPlusStart - actualStart, fromValue);
                kPlusStart = this.nextElementIndex(thisObj, kPlusStart, length);
            }
        }

        private void spliceInsert(Object thisObj, long actualStart, Object[] args) {
            int itemOffset = 2;
            for (int i = 2; i < args.length; ++i) {
                this.write(thisObj, actualStart + (long)i - 2L, args[i]);
            }
        }

        private void spliceJSObject(Object thisObj, long len, long actualStart, long actualDeleteCount, long itemCount) {
            if (itemCount < actualDeleteCount) {
                this.branchA.enter();
                this.spliceJSObjectShrink(thisObj, len, actualStart, actualDeleteCount, itemCount);
            } else if (itemCount > actualDeleteCount) {
                this.branchB.enter();
                this.spliceJSObjectMove(thisObj, len, actualStart, actualDeleteCount, itemCount);
            }
        }

        private void spliceJSObjectMove(Object thisObj, long len, long actualStart, long actualDeleteCount, long itemCount) {
            for (long k = len - actualDeleteCount; k > actualStart; --k) {
                this.spliceMoveValue(thisObj, k + actualDeleteCount - 1L, k + itemCount - 1L);
            }
        }

        private void spliceJSObjectShrink(Object thisObj, long len, long actualStart, long actualDeleteCount, long itemCount) {
            long k;
            for (k = actualStart; k < len - actualDeleteCount; ++k) {
                this.spliceMoveValue(thisObj, k + actualDeleteCount, k + itemCount);
            }
            for (k = len; k > len - actualDeleteCount + itemCount; --k) {
                this.deletePropertyNode.executeEvaluated(thisObj, k - 1L);
            }
        }

        private void spliceMoveValue(Object thisObj, long fromIndex, long toIndex) {
            if (this.hasProperty(thisObj, fromIndex)) {
                Object val = this.read(thisObj, fromIndex);
                this.write(thisObj, toIndex, val);
            } else {
                this.needMoveDeleteBranch.enter();
                this.deletePropertyNode.executeEvaluated(thisObj, toIndex);
            }
        }

        final void spliceJSArrayElementwise(JSDynamicObject thisObj, long len, long actualStart, long actualDeleteCount, long itemCount) {
            assert (JSArray.isJSArray(thisObj));
            if (itemCount < actualDeleteCount) {
                this.branchA.enter();
                this.spliceJSArrayElementwiseWalkUp(thisObj, len, actualStart, actualDeleteCount, itemCount);
            } else if (itemCount > actualDeleteCount) {
                this.branchB.enter();
                this.spliceJSArrayElementwiseWalkDown(thisObj, len, actualStart, actualDeleteCount, itemCount);
            }
        }

        private void spliceJSArrayElementwiseWalkDown(JSDynamicObject thisObj, long len, long actualStart, long actualDeleteCount, long itemCount) {
            long k = len - 1L;
            long delta = itemCount - actualDeleteCount;
            while (k > actualStart + actualDeleteCount - 1L) {
                this.spliceMoveValue(thisObj, k, k + delta);
                if (k - delta > actualStart + actualDeleteCount - 1L && !this.hasProperty((Object)thisObj, k - delta)) {
                    this.deletePropertyNode.executeEvaluated(thisObj, k);
                }
                k = this.previousElementIndex(thisObj, k);
            }
        }

        private void spliceJSArrayElementwiseWalkUp(JSDynamicObject thisObj, long len, long actualStart, long actualDeleteCount, long itemCount) {
            long k = actualStart + actualDeleteCount;
            long delta = itemCount - actualDeleteCount;
            while (k < len) {
                this.spliceMoveValue(thisObj, k, k + delta);
                if (k - delta < len && !this.hasProperty((Object)thisObj, k - delta)) {
                    this.deletePropertyNode.executeEvaluated(thisObj, k);
                }
                k = this.nextElementIndex(thisObj, k, len);
            }
            k = len - 1L;
            while (k >= len + delta) {
                this.deletePropertyNode.executeEvaluated(thisObj, k);
                k = this.previousElementIndex(thisObj, k);
            }
        }

        final void spliceJSArrayBlockwise(JSDynamicObject thisObj, long actualStart, long actualDeleteCount, long itemCount, ScriptArray array) {
            assert (JSArray.isJSArray(thisObj));
            if (itemCount < actualDeleteCount) {
                this.branchA.enter();
                JSAbstractArray.arraySetArrayType(thisObj, array.removeRange(thisObj, actualStart + itemCount, actualStart + actualDeleteCount, this.errorBranch));
            } else if (itemCount > actualDeleteCount) {
                this.branchB.enter();
                JSAbstractArray.arraySetArrayType(thisObj, array.addRange(thisObj, actualStart, (int)(itemCount - actualDeleteCount)));
            }
        }

        private void spliceForeignArray(Object thisObj, long len, long actualStart, long actualDeleteCount, long itemCount) {
            InteropLibrary arrays = this.arrayInterop;
            if (arrays == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.arrayInterop = arrays = this.insert(InteropLibrary.getFactory().createDispatched(5));
            }
            try {
                if (itemCount < actualDeleteCount) {
                    this.branchA.enter();
                    JSArraySpliceNode.spliceForeignArrayShrink(thisObj, len, actualStart, actualDeleteCount, itemCount, arrays);
                } else if (itemCount > actualDeleteCount) {
                    this.branchB.enter();
                    JSArraySpliceNode.spliceForeignArrayMove(thisObj, len, actualStart, actualDeleteCount, itemCount, arrays);
                }
            }
            catch (InvalidArrayIndexException | UnsupportedMessageException | UnsupportedTypeException e) {
                throw Errors.createTypeErrorInteropException(thisObj, e, "splice", this);
            }
        }

        private static void spliceForeignArrayMove(Object thisObj, long len, long actualStart, long actualDeleteCount, long itemCount, InteropLibrary arrays) throws UnsupportedMessageException, InvalidArrayIndexException, UnsupportedTypeException {
            for (long k = len - actualDeleteCount; k > actualStart; --k) {
                JSArraySpliceNode.spliceForeignMoveValue(thisObj, k + actualDeleteCount - 1L, k + itemCount - 1L, arrays);
            }
        }

        private static void spliceForeignArrayShrink(Object thisObj, long len, long actualStart, long actualDeleteCount, long itemCount, InteropLibrary arrays) throws UnsupportedMessageException, InvalidArrayIndexException, UnsupportedTypeException {
            long k;
            for (k = actualStart; k < len - actualDeleteCount; ++k) {
                JSArraySpliceNode.spliceForeignMoveValue(thisObj, k + actualDeleteCount, k + itemCount, arrays);
            }
            for (k = len; k > len - actualDeleteCount + itemCount; --k) {
                arrays.removeArrayElement(thisObj, k - 1L);
            }
        }

        private static void spliceForeignMoveValue(Object thisObj, long fromIndex, long toIndex, InteropLibrary arrays) throws UnsupportedMessageException, InvalidArrayIndexException, UnsupportedTypeException {
            Object val = arrays.readArrayElement(thisObj, fromIndex);
            arrays.writeArrayElement(thisObj, toIndex, val);
        }

        static abstract class SpliceJSArrayNode
        extends JavaScriptBaseNode {
            final JSContext context;

            SpliceJSArrayNode(JSContext context) {
                this.context = context;
            }

            abstract void execute(JSDynamicObject var1, long var2, long var4, long var6, long var8, ScriptArray var10, JSArraySpliceNode var11);

            @Specialization(guards={"cachedArrayType.isInstance(arrayType)"}, limit="5")
            static void doCached(JSDynamicObject array, long len, long actualStart, long actualDeleteCount, long itemCount, ScriptArray arrayType, JSArraySpliceNode parent, @Cached(value="arrayType") ScriptArray cachedArrayType, @Cached GetPrototypeNode getPrototypeNode, @Cached ConditionProfile arrayElementwise) {
                if (arrayElementwise.profile(parent.mustUseElementwise(array, len, cachedArrayType.cast(arrayType), getPrototypeNode))) {
                    parent.spliceJSArrayElementwise(array, len, actualStart, actualDeleteCount, itemCount);
                } else {
                    parent.spliceJSArrayBlockwise(array, actualStart, actualDeleteCount, itemCount, cachedArrayType.cast(arrayType));
                }
            }

            @Specialization(replaces={"doCached"})
            static void doUncached(JSDynamicObject array, long len, long actualStart, long actualDeleteCount, long itemCount, ScriptArray arrayType, JSArraySpliceNode parent, @Cached GetPrototypeNode getPrototypeNode, @Cached ConditionProfile arrayElementwise) {
                if (arrayElementwise.profile(parent.mustUseElementwise(array, len, arrayType, getPrototypeNode))) {
                    parent.spliceJSArrayElementwise(array, len, actualStart, actualDeleteCount, itemCount);
                } else {
                    parent.spliceJSArrayBlockwise(array, actualStart, actualDeleteCount, itemCount, arrayType);
                }
            }
        }
    }

    public static abstract class JSArrayToLocaleStringNode
    extends JSArrayOperation {
        private final StringBuilderProfile stringBuilderProfile;
        private final BranchProfile stackGrowProfile = BranchProfile.create();
        @Node.Child
        private PropertyGetNode getToLocaleStringNode;
        @Node.Child
        private JSFunctionCallNode callToLocaleStringNode;
        @Node.Child
        private TruffleStringBuilder.AppendCharUTF16Node appendCharNode;
        @Node.Child
        private TruffleStringBuilder.AppendStringNode appendStringNode;
        @Node.Child
        private TruffleStringBuilder.ToStringNode builderToStringNode;

        public JSArrayToLocaleStringNode(JSContext context, JSBuiltin builtin, boolean isTypedArrayImplementation) {
            super(context, builtin, isTypedArrayImplementation);
            this.stringBuilderProfile = StringBuilderProfile.create(context.getStringLengthLimit());
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Specialization
        protected TruffleString toLocaleString(VirtualFrame frame, Object thisObj, @Cached JSToStringNode toStringNode) {
            Object arrayObj = this.toObjectOrValidateTypedArray(thisObj);
            long len = this.getLength(arrayObj);
            if (len == 0L) {
                return Strings.EMPTY_STRING;
            }
            JSRealm realm = this.getRealm();
            if (!realm.joinStackPush(thisObj, this.stackGrowProfile)) {
                return Strings.EMPTY_STRING;
            }
            try {
                Object[] userArguments = JSArguments.extractUserArguments(frame.getArguments());
                TruffleStringBuilder sb = this.stringBuilderProfile.newStringBuilder();
                for (long k = 0L; k < len; ++k) {
                    Object nextElement;
                    if (k > 0L) {
                        this.append(sb, ',');
                    }
                    if ((nextElement = this.read(arrayObj, k)) == Null.instance || nextElement == Undefined.instance) continue;
                    Object result = this.callToLocaleString(nextElement, userArguments);
                    TruffleString resultString = toStringNode.executeString(result);
                    this.append(sb, resultString);
                }
                TruffleString truffleString = this.builderToString(sb);
                return truffleString;
            }
            finally {
                realm.joinStackPop();
            }
        }

        private Object callToLocaleString(Object nextElement, Object[] userArguments) {
            if (this.getToLocaleStringNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.getToLocaleStringNode = this.insert(PropertyGetNode.create(Strings.TO_LOCALE_STRING, false, this.getContext()));
                this.callToLocaleStringNode = this.insert(JSFunctionCallNode.createCall());
            }
            Object toLocaleString = this.getToLocaleStringNode.getValue(nextElement);
            return this.callToLocaleStringNode.executeCall(JSArguments.create(nextElement, toLocaleString, userArguments));
        }

        private void append(TruffleStringBuilder sb, char c) {
            if (this.appendCharNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.appendCharNode = this.insert(TruffleStringBuilder.AppendCharUTF16Node.create());
            }
            this.stringBuilderProfile.append(this.appendCharNode, sb, c);
        }

        private void append(TruffleStringBuilder sb, TruffleString s) {
            if (this.appendStringNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.appendStringNode = this.insert(TruffleStringBuilder.AppendStringNode.create());
            }
            this.stringBuilderProfile.append(this.appendStringNode, sb, s);
        }

        private TruffleString builderToString(TruffleStringBuilder sb) {
            if (this.builderToStringNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.builderToStringNode = this.insert(TruffleStringBuilder.ToStringNode.create());
            }
            return StringBuilderProfile.toString(this.builderToStringNode, sb);
        }
    }

    public static abstract class JSArrayJoinNode
    extends JSArrayOperation {
        @Node.Child
        private JSToStringNode separatorToStringNode;
        @Node.Child
        private JSToStringNode elementToStringNode;
        @Node.Child
        private TruffleString.ConcatNode stringConcatNode;
        @Node.Child
        private InteropLibrary interopLibrary;
        private final ConditionProfile separatorNotEmpty = ConditionProfile.createBinaryProfile();
        private final ConditionProfile isZero = ConditionProfile.createBinaryProfile();
        private final ConditionProfile isOne = ConditionProfile.createBinaryProfile();
        private final ConditionProfile isTwo = ConditionProfile.createBinaryProfile();
        private final ConditionProfile isSparse = ConditionProfile.createBinaryProfile();
        private final BranchProfile growProfile = BranchProfile.create();
        private final BranchProfile stackGrowProfile = BranchProfile.create();
        private final StringBuilderProfile stringBuilderProfile;
        @Node.Child
        private TruffleStringBuilder.AppendStringNode appendStringNode;
        @Node.Child
        private TruffleStringBuilder.ToStringNode builderToStringNode;

        public JSArrayJoinNode(JSContext context, JSBuiltin builtin, boolean isTypedArrayImplementation) {
            super(context, builtin, isTypedArrayImplementation);
            this.elementToStringNode = JSToStringNode.create();
            this.stringBuilderProfile = StringBuilderProfile.create(context.getStringLengthLimit());
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Specialization
        protected TruffleString join(Object thisObj, Object joinStr) {
            Object thisJSObject = this.toObjectOrValidateTypedArray(thisObj);
            long length = this.getLength(thisJSObject);
            TruffleString joinSeparator = joinStr == Undefined.instance ? Strings.COMMA : this.getSeparatorToString().executeString(joinStr);
            JSRealm realm = this.getRealm();
            if (!realm.joinStackPush(thisObj, this.stackGrowProfile)) {
                return Strings.EMPTY_STRING;
            }
            try {
                if (this.isZero.profile(length == 0L)) {
                    TruffleString truffleString = Strings.EMPTY_STRING;
                    return truffleString;
                }
                if (this.isOne.profile(length == 1L)) {
                    TruffleString truffleString = this.joinOne(thisJSObject);
                    return truffleString;
                }
                boolean appendSep = this.separatorNotEmpty.profile(Strings.length(joinSeparator) > 0);
                if (this.isTwo.profile(length == 2L)) {
                    TruffleString truffleString = this.joinTwo(thisJSObject, joinSeparator, appendSep);
                    return truffleString;
                }
                if (this.isSparse.profile(JSArray.isJSArray(thisJSObject) && JSAbstractArray.arrayGetArrayType((JSDynamicObject)thisJSObject) instanceof SparseArray)) {
                    TruffleString truffleString = this.joinSparse(thisJSObject, length, joinSeparator, appendSep);
                    return truffleString;
                }
                TruffleString truffleString = this.joinLoop(thisJSObject, length, joinSeparator, appendSep);
                return truffleString;
            }
            finally {
                realm.joinStackPop();
            }
        }

        private JSToStringNode getSeparatorToString() {
            if (this.separatorToStringNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.separatorToStringNode = this.insert(JSToStringNode.create());
            }
            return this.separatorToStringNode;
        }

        private TruffleString concat(TruffleString a, TruffleString b) {
            if (this.stringConcatNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.stringConcatNode = this.insert(TruffleString.ConcatNode.create());
            }
            return Strings.concat(this.stringConcatNode, a, b);
        }

        private void append(TruffleStringBuilder sb, TruffleString s) {
            if (this.appendStringNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.appendStringNode = this.insert(TruffleStringBuilder.AppendStringNode.create());
            }
            this.stringBuilderProfile.append(this.appendStringNode, sb, s);
        }

        private TruffleString builderToString(TruffleStringBuilder sb) {
            if (this.builderToStringNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.builderToStringNode = this.insert(TruffleStringBuilder.ToStringNode.create());
            }
            return StringBuilderProfile.toString(this.builderToStringNode, sb);
        }

        private TruffleString joinOne(Object thisObject) {
            Object value2 = this.read(thisObject, 0);
            return this.toStringOrEmpty(value2);
        }

        private TruffleString joinTwo(Object thisObject, TruffleString joinSeparator, boolean appendSep) {
            TruffleString first = this.toStringOrEmpty(this.read(thisObject, 0));
            TruffleString second = this.toStringOrEmpty(this.read(thisObject, 1));
            long resultLength = (long)Strings.length(first) + (appendSep ? (long)Strings.length(joinSeparator) : 0L) + (long)Strings.length(second);
            if (resultLength > (long)this.getContext().getStringLengthLimit()) {
                CompilerDirectives.transferToInterpreter();
                throw Errors.createRangeErrorInvalidStringLength();
            }
            TruffleString res = first;
            if (appendSep) {
                res = this.concat(res, joinSeparator);
            }
            return this.concat(res, second);
        }

        private TruffleString joinLoop(Object thisJSObject, long length, TruffleString joinSeparator, boolean appendSep) {
            TruffleStringBuilder sb = this.stringBuilderProfile.newStringBuilder();
            long i = 0L;
            while (i < length) {
                if (appendSep && i != 0L) {
                    this.append(sb, joinSeparator);
                }
                Object value2 = this.read(thisJSObject, i);
                TruffleString str = this.toStringOrEmpty(value2);
                this.append(sb, str);
                if (appendSep) {
                    ++i;
                    continue;
                }
                i = this.nextElementIndex(thisJSObject, i, length);
            }
            return this.builderToString(sb);
        }

        private TruffleString toStringOrEmpty(Object value2) {
            if (this.isValidEntry(value2)) {
                return this.elementToStringNode.executeString(value2);
            }
            return Strings.EMPTY_STRING;
        }

        private boolean isValidEntry(Object value2) {
            return value2 != Undefined.instance && value2 != Null.instance && !this.isForeignNull(value2);
        }

        private boolean isForeignNull(Object value2) {
            if (value2 instanceof JSDynamicObject) {
                return false;
            }
            if (value2 instanceof TruffleObject) {
                if (this.interopLibrary == null) {
                    CompilerDirectives.transferToInterpreterAndInvalidate();
                    this.interopLibrary = this.insert(InteropLibrary.getFactory().createDispatched(5));
                }
                return this.interopLibrary.isNull(value2);
            }
            return false;
        }

        private TruffleString joinSparse(Object thisObject, long length, TruffleString joinSeparator, boolean appendSep) {
            SimpleArrayList<Object> converted = SimpleArrayList.create(length);
            long calculatedLength = 0L;
            long i = 0L;
            while (i < length) {
                TruffleString string;
                int stringLength;
                Object value2 = this.read(thisObject, i);
                if (this.isValidEntry(value2) && (stringLength = Strings.length(string = this.elementToStringNode.executeString(value2))) > 0) {
                    calculatedLength += (long)stringLength;
                    converted.add(i, this.growProfile);
                    converted.add(string, this.growProfile);
                }
                i = this.nextElementIndex(thisObject, i, length);
            }
            if (appendSep) {
                calculatedLength += (length - 1L) * (long)Strings.length(joinSeparator);
            }
            if (calculatedLength > (long)this.getContext().getStringLengthLimit()) {
                CompilerDirectives.transferToInterpreter();
                throw Errors.createRangeErrorInvalidStringLength();
            }
            assert (calculatedLength <= Integer.MAX_VALUE);
            TruffleStringBuilder sb = this.stringBuilderProfile.newStringBuilder((int)calculatedLength);
            long lastIndex = 0L;
            for (int j = 0; j < converted.size(); j += 2) {
                long index = (Long)converted.get(j);
                Object value3 = converted.get(j + 1);
                if (appendSep) {
                    for (long k = lastIndex; k < index; ++k) {
                        this.append(sb, joinSeparator);
                    }
                }
                this.append(sb, (TruffleString)value3);
                lastIndex = index;
            }
            if (appendSep) {
                for (long k = lastIndex; k < length - 1L; ++k) {
                    this.append(sb, joinSeparator);
                }
            }
            assert ((long)StringBuilderProfile.length(sb) == calculatedLength);
            return this.builderToString(sb);
        }
    }

    public static abstract class JSArrayIndexOfNode
    extends ArrayForEachIndexCallOperation {
        private final boolean isForward;
        @Node.Child
        private JSToIntegerAsLongNode toIntegerNode;
        private final BranchProfile arrayWithContentBranch = BranchProfile.create();
        private final BranchProfile fromConversionBranch = BranchProfile.create();

        public JSArrayIndexOfNode(JSContext context, JSBuiltin builtin, boolean isTypedArrayImplementation, boolean isForward) {
            super(context, builtin, isTypedArrayImplementation);
            this.isForward = isForward;
        }

        @Specialization
        protected Object indexOf(Object thisObj, Object[] args) {
            long fromIndexValue;
            Object thisJSObject = this.toObjectOrValidateTypedArray(thisObj);
            long len = this.getLength(thisJSObject);
            if (len == 0L) {
                return -1;
            }
            this.arrayWithContentBranch.enter();
            Object searchElement = JSRuntime.getArgOrUndefined(args, 0);
            Object fromIndex = JSRuntime.getArgOrUndefined(args, 1);
            long l = fromIndexValue = this.isForward() ? this.calcFromIndexForward(args, len, fromIndex) : this.calcFromIndexBackward(args, len, fromIndex);
            if (fromIndexValue < 0L) {
                return -1;
            }
            return this.forEachIndexCall(thisJSObject, Undefined.instance, searchElement, fromIndexValue, len, -1);
        }

        private long calcFromIndexForward(Object[] args, long len, Object fromIndex) {
            if (args.length <= 1) {
                return 0L;
            }
            this.fromConversionBranch.enter();
            long fromIndexValue = this.toInteger(fromIndex);
            if (fromIndexValue > len) {
                return -1L;
            }
            if (fromIndexValue < 0L) {
                fromIndexValue = (fromIndexValue += len) < 0L ? 0L : fromIndexValue;
            }
            return fromIndexValue;
        }

        private long calcFromIndexBackward(Object[] args, long len, Object fromIndex) {
            if (args.length <= 1) {
                return len - 1L;
            }
            this.fromConversionBranch.enter();
            long fromIndexInt = this.toInteger(fromIndex);
            if (fromIndexInt >= 0L) {
                return Math.min(fromIndexInt, len - 1L);
            }
            return fromIndexInt + len;
        }

        private long toInteger(Object operand) {
            if (this.toIntegerNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.toIntegerNode = this.insert(JSToIntegerAsLongNode.create());
            }
            return this.toIntegerNode.executeLong(operand);
        }

        @Override
        protected boolean isForward() {
            return this.isForward;
        }

        @Override
        protected boolean shouldCheckHasProperty() {
            return true;
        }

        @Override
        protected final ForEachIndexCallNode.MaybeResultNode makeMaybeResultNode() {
            return new ForEachIndexCallNode.MaybeResultNode(){
                @Node.Child
                private JSIdenticalNode doIdenticalNode = JSIdenticalNode.createStrictEqualityComparison();
                private final ConditionProfile indexInIntRangeCondition = ConditionProfile.createBinaryProfile();

                @Override
                public ForEachIndexCallNode.MaybeResult<Object> apply(long index, Object value2, Object callbackResult, Object currentResult) {
                    return this.doIdenticalNode.executeBoolean(value2, callbackResult) ? ForEachIndexCallNode.MaybeResult.returnResult(JSRuntime.boxIndex(index, this.indexInIntRangeCondition)) : ForEachIndexCallNode.MaybeResult.continueResult(currentResult);
                }
            };
        }

        @Override
        protected final ForEachIndexCallNode.CallbackNode makeCallbackNode() {
            return null;
        }
    }

    public static abstract class JSArrayConcatNode
    extends JSArrayOperation {
        @Node.Child
        private JSToBooleanNode toBooleanNode;
        @Node.Child
        private JSArrayFirstElementIndexNode firstElementIndexNode;
        @Node.Child
        private JSArrayLastElementIndexNode lastElementIndexNode;
        @Node.Child
        private PropertyGetNode getSpreadableNode;
        @Node.Child
        private JSIsArrayNode isArrayNode;
        private final ConditionProfile isFirstSpreadable = ConditionProfile.createBinaryProfile();
        private final ConditionProfile hasFirstElements = ConditionProfile.createBinaryProfile();
        private final ConditionProfile isSecondSpreadable = ConditionProfile.createBinaryProfile();
        private final ConditionProfile hasSecondElements = ConditionProfile.createBinaryProfile();
        private final ConditionProfile lengthErrorProfile = ConditionProfile.createBinaryProfile();
        private final ConditionProfile hasMultipleArgs = ConditionProfile.createBinaryProfile();
        private final ConditionProfile hasOneArg = ConditionProfile.createBinaryProfile();
        private final ConditionProfile optimizationsObservable = ConditionProfile.createBinaryProfile();
        private final ConditionProfile hasFirstOneElement = ConditionProfile.createBinaryProfile();
        private final ConditionProfile hasSecondOneElement = ConditionProfile.createBinaryProfile();

        public JSArrayConcatNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        protected boolean toBoolean(Object target) {
            if (this.toBooleanNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.toBooleanNode = this.insert(JSToBooleanNode.create());
            }
            return this.toBooleanNode.executeBoolean(target);
        }

        @Specialization
        protected JSDynamicObject concat(Object thisObj, Object[] args) {
            Object thisJSObj = this.toObject(thisObj);
            JSDynamicObject retObj = (JSDynamicObject)this.getArraySpeciesConstructorNode().createEmptyContainer(thisJSObj, 0L);
            long n = this.concatElementIntl(retObj, thisJSObj, 0L, this.isFirstSpreadable, this.hasFirstElements, this.hasFirstOneElement);
            long resultLen = this.concatIntl(retObj, n, args);
            this.setLength((Object)retObj, resultLen);
            return retObj;
        }

        private long concatIntl(JSDynamicObject retObj, long initialLength, Object[] args) {
            long n = initialLength;
            if (this.hasOneArg.profile(args.length == 1)) {
                n = this.concatElementIntl(retObj, args[0], n, this.isSecondSpreadable, this.hasSecondElements, this.hasSecondOneElement);
            } else if (this.hasMultipleArgs.profile(args.length > 1)) {
                for (int i = 0; i < args.length; ++i) {
                    n = this.concatElementIntl(retObj, args[i], n, this.isSecondSpreadable, this.hasSecondElements, this.hasSecondOneElement);
                }
            }
            return n;
        }

        private long concatElementIntl(JSDynamicObject retObj, Object el, long n, ConditionProfile isSpreadable, ConditionProfile hasElements, ConditionProfile hasOneElement) {
            if (isSpreadable.profile(this.isConcatSpreadable(el))) {
                long len2 = this.getLength(el);
                if (hasElements.profile(len2 > 0L)) {
                    return this.concatSpreadable(retObj, n, el, len2, hasOneElement);
                }
            } else {
                if (this.lengthErrorProfile.profile((double)n > JSRuntime.MAX_SAFE_INTEGER)) {
                    this.errorBranch.enter();
                    JSArrayConcatNode.throwLengthError();
                }
                this.writeOwn((Object)retObj, n, el);
                return n + 1L;
            }
            return n;
        }

        private long concatSpreadable(JSDynamicObject retObj, long n, Object elObj, long len2, ConditionProfile hasOneElement) {
            block4: {
                block5: {
                    block3: {
                        if (this.lengthErrorProfile.profile((double)(n + len2) > JSRuntime.MAX_SAFE_INTEGER)) {
                            this.errorBranch.enter();
                            JSArrayConcatNode.throwLengthError();
                        }
                        if (!this.optimizationsObservable.profile(JSProxy.isJSProxy(elObj) || !JSDynamicObject.isJSDynamicObject(elObj))) break block3;
                        for (long k = 0L; k < len2; ++k) {
                            if (!this.hasProperty(elObj, k)) continue;
                            this.writeOwn((Object)retObj, n + k, this.read(elObj, k));
                        }
                        break block4;
                    }
                    if (!hasOneElement.profile(len2 == 1L)) break block5;
                    if (!this.hasProperty(elObj, 0L)) break block4;
                    this.writeOwn((Object)retObj, n, this.read(elObj, 0));
                    break block4;
                }
                long k = this.firstElementIndex((JSDynamicObject)elObj, len2);
                long lastI = this.lastElementIndex((JSDynamicObject)elObj, len2);
                while (k <= lastI) {
                    this.writeOwn((Object)retObj, n + k, this.read(elObj, k));
                    k = this.nextElementIndex(elObj, k, len2);
                }
            }
            return n + len2;
        }

        private boolean isConcatSpreadable(Object el) {
            JSObject obj;
            Object spreadable;
            if (el instanceof JSObject && (spreadable = this.getSpreadableProperty(obj = (JSObject)el)) != Undefined.instance) {
                return this.toBoolean(spreadable);
            }
            return this.isArray(el);
        }

        private boolean isArray(Object object) {
            if (this.isArrayNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.isArrayNode = this.insert(JSIsArrayNode.createIsArrayLike());
            }
            return this.isArrayNode.execute(object);
        }

        private Object getSpreadableProperty(Object obj) {
            if (this.getSpreadableNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.getSpreadableNode = this.insert(PropertyGetNode.create(Symbol.SYMBOL_IS_CONCAT_SPREADABLE, false, this.getContext()));
            }
            return this.getSpreadableNode.getValue(obj);
        }

        private long firstElementIndex(JSDynamicObject target, long length) {
            if (this.firstElementIndexNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.firstElementIndexNode = this.insert(JSArrayFirstElementIndexNode.create(this.getContext()));
            }
            return this.firstElementIndexNode.executeLong(target, length);
        }

        private long lastElementIndex(JSDynamicObject target, long length) {
            if (this.lastElementIndexNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.lastElementIndexNode = this.insert(JSArrayLastElementIndexNode.create(this.getContext()));
            }
            return this.lastElementIndexNode.executeLong(target, length);
        }
    }

    public static abstract class JSArrayToStringNode
    extends BasicArrayOperation {
        @Node.Child
        private PropertyNode joinPropertyNode;
        @Node.Child
        private PropertyNode toStringPropertyNode;
        @Node.Child
        private JSFunctionCallNode callJoinNode;
        @Node.Child
        private JSFunctionCallNode callToStringNode;
        @Node.Child
        private ForeignObjectPrototypeNode foreignObjectPrototypeNode;
        @Node.Child
        private InteropLibrary interopLibrary;
        @Node.Child
        private ImportValueNode importValueNode;
        private final ConditionProfile isJSObjectProfile = ConditionProfile.createBinaryProfile();
        @CompilerDirectives.CompilationFinal
        private Class<?> hostLanguageClass;

        public JSArrayToStringNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
            this.joinPropertyNode = PropertyNode.createProperty(context, null, Strings.JOIN);
        }

        private Object getJoinProperty(Object target) {
            return this.joinPropertyNode.executeWithTarget(target);
        }

        private Object getToStringProperty(Object target) {
            if (this.toStringPropertyNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.toStringPropertyNode = this.insert(PropertyNode.createProperty(this.getContext(), null, Strings.TO_STRING));
            }
            return this.toStringPropertyNode.executeWithTarget(target);
        }

        private Object callJoin(Object target, Object function) {
            if (this.callJoinNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.callJoinNode = this.insert(JSFunctionCallNode.createCall());
            }
            return this.callJoinNode.executeCall(JSArguments.createZeroArg(target, function));
        }

        private Object callToString(Object target, Object function) {
            if (this.callToStringNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.callToStringNode = this.insert(JSFunctionCallNode.createCall());
            }
            return this.callToStringNode.executeCall(JSArguments.createZeroArg(target, function));
        }

        private JSDynamicObject getForeignObjectPrototype(Object truffleObject) {
            assert (JSRuntime.isForeignObject(truffleObject));
            if (this.foreignObjectPrototypeNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.foreignObjectPrototypeNode = this.insert(ForeignObjectPrototypeNode.create());
            }
            return this.foreignObjectPrototypeNode.execute(truffleObject);
        }

        private InteropLibrary getInterop() {
            if (this.interopLibrary == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.interopLibrary = this.insert(InteropLibrary.getFactory().createDispatched(5));
            }
            return this.interopLibrary;
        }

        private Object importValue(Object value2) {
            if (this.importValueNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.importValueNode = this.insert(ImportValueNode.create());
            }
            return this.importValueNode.executeWithTarget(value2);
        }

        private Object toStringForeign(Object arrayObj) {
            Object join;
            InteropLibrary interop = this.getInterop();
            if (this.shouldTryOwnJoin(arrayObj) && interop.isMemberInvocable(arrayObj, Strings.JOIN_JLS)) {
                Object result;
                block8: {
                    try {
                        try {
                            result = interop.invokeMember(arrayObj, Strings.JOIN_JLS, new Object[0]);
                        }
                        catch (AbstractTruffleException e) {
                            if (InteropLibrary.getUncached(e).getExceptionType(e) == ExceptionType.RUNTIME_ERROR) {
                                result = null;
                                break block8;
                            }
                            throw e;
                        }
                    }
                    catch (InteropException e) {
                        result = null;
                    }
                }
                if (result != null) {
                    return this.importValue(result);
                }
            }
            if (this.isCallable(join = this.getJoinProperty(this.getForeignObjectPrototype(arrayObj)))) {
                return this.callJoin(arrayObj, join);
            }
            Object toString = this.getToStringProperty(this.getRealm().getObjectPrototype());
            return this.callToString(arrayObj, toString);
        }

        private boolean shouldTryOwnJoin(Object arrayObj) {
            InteropLibrary interop = this.getInterop();
            try {
                return !interop.hasLanguage(arrayObj) || interop.getLanguage(arrayObj) == this.getHostLanguageClass();
            }
            catch (UnsupportedMessageException umex) {
                throw CompilerDirectives.shouldNotReachHere();
            }
        }

        private Class<?> getHostLanguageClass() {
            if (this.hostLanguageClass == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                try {
                    this.hostLanguageClass = InteropLibrary.getUncached().getLanguage(this.getRealm().getEnv().asGuestValue(new Object()));
                }
                catch (UnsupportedMessageException umex) {
                    throw CompilerDirectives.shouldNotReachHere();
                }
                catch (UnsupportedOperationException uoex) {
                    this.hostLanguageClass = Object.class;
                }
            }
            return this.hostLanguageClass;
        }

        @Specialization
        protected Object toString(Object thisObj) {
            Object arrayObj = this.toObject(thisObj);
            if (this.isJSObjectProfile.profile(JSDynamicObject.isJSDynamicObject(arrayObj))) {
                Object join = this.getJoinProperty(arrayObj);
                if (this.isCallable(join)) {
                    return this.callJoin(arrayObj, join);
                }
                return JSObject.defaultToString((JSDynamicObject)arrayObj);
            }
            return this.toStringForeign(arrayObj);
        }
    }

    public static abstract class JSArrayUnshiftNode
    extends JSArrayOperation {
        @Node.Child
        protected IsArrayNode isArrayNode = IsArrayNode.createIsArray();
        @Node.Child
        protected TestArrayNode hasHolesNode = TestArrayNode.createHasHoles();

        public JSArrayUnshiftNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        protected boolean isFastPath(Object thisObj) {
            boolean isArray = this.isArrayNode.execute(thisObj);
            return isArray && !this.hasHolesNode.executeBoolean((JSDynamicObject)thisObj);
        }

        private long unshiftHoleless(JSDynamicObject thisObj, Object[] args) {
            long len = this.getLength(thisObj);
            if (this.getContext().getEcmaScriptVersion() <= 5 || args.length > 0) {
                for (long l = len - 1L; l >= 0L; --l) {
                    this.write((Object)thisObj, l + (long)args.length, this.read((Object)thisObj, l));
                }
                for (int i = 0; i < args.length; ++i) {
                    this.write((Object)thisObj, i, args[i]);
                }
                this.reportLoopCount(len + (long)args.length);
            }
            long newLen = len + (long)args.length;
            this.setLength((Object)thisObj, newLen);
            return newLen;
        }

        @Specialization(guards={"isFastPath(thisObj)"}, rewriteOn={UnexpectedResultException.class})
        protected int unshiftInt(JSDynamicObject thisObj, Object[] args) throws UnexpectedResultException {
            long newLen = this.unshiftHoleless(thisObj, args);
            if (JSRuntime.longIsRepresentableAsInt(newLen)) {
                return (int)newLen;
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            throw new UnexpectedResultException(newLen);
        }

        @Specialization(guards={"isFastPath(thisObj)"}, replaces={"unshiftInt"})
        protected double unshiftDouble(JSDynamicObject thisObj, Object[] args) {
            return this.unshiftHoleless(thisObj, args);
        }

        @Specialization(guards={"!isFastPath(thisObjParam)"})
        protected double unshiftHoles(Object thisObjParam, Object[] args, @Cached(value="create(THROW_ERROR, getContext())") DeletePropertyNode deletePropertyNode, @Cached(value="create(getContext())") JSArrayLastElementIndexNode lastElementIndexNode, @Cached(value="create(getContext())") JSArrayFirstElementIndexNode firstElementIndexNode) {
            Object thisObj = this.toObject(thisObjParam);
            long len = this.getLength(thisObj);
            if (this.getContext().getEcmaScriptVersion() <= 5 || args.length > 0) {
                if ((long)args.length + len > JSRuntime.MAX_SAFE_INTEGER_LONG) {
                    this.errorBranch.enter();
                    JSArrayUnshiftNode.throwLengthError();
                }
                long lastIdx = lastElementIndexNode.executeLong(thisObj, len);
                long firstIdx = firstElementIndexNode.executeLong(thisObj, len);
                long count = 0L;
                long i = lastIdx;
                while (i >= firstIdx) {
                    ++count;
                    if (this.hasProperty(thisObj, i)) {
                        this.write(thisObj, i + (long)args.length, this.read(thisObj, i));
                        if (args.length > 0 && i >= (long)args.length && !this.hasProperty(thisObj, i - (long)args.length)) {
                            deletePropertyNode.executeEvaluated(thisObj, i);
                        }
                    }
                    i = this.previousElementIndex(thisObj, i);
                }
                for (int i2 = 0; i2 < args.length; ++i2) {
                    this.write(thisObj, i2, args[i2]);
                }
                this.reportLoopCount(count + (long)args.length);
            }
            long newLen = len + (long)args.length;
            this.setLength(thisObj, newLen);
            return newLen;
        }
    }

    @ImportStatic(value={JSConfig.class})
    public static abstract class JSArrayShiftNode
    extends JSArrayOperation {
        public JSArrayShiftNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        protected static boolean isSparseArray(JSDynamicObject thisObj) {
            return JSAbstractArray.arrayGetArrayType(thisObj) instanceof SparseArray;
        }

        protected static boolean isArrayWithoutHolesAndNotSealed(JSDynamicObject thisObj, IsArrayNode isArrayNode, TestArrayNode hasHolesNode, TestArrayNode isSealedNode) {
            boolean isArray = isArrayNode.execute(thisObj);
            return isArray && !hasHolesNode.executeBoolean(thisObj) && !isSealedNode.executeBoolean(thisObj);
        }

        @Specialization(guards={"isArrayWithoutHolesAndNotSealed(thisObj, isArrayNode, hasHolesNode, isSealedNode)"}, limit="1")
        protected Object shiftWithoutHoles(JSDynamicObject thisObj, @Cached.Shared(value="isArray") @Cached(value="createIsArray()") IsArrayNode isArrayNode, @Cached.Shared(value="hasHoles") @Cached(value="createHasHoles()") TestArrayNode hasHolesNode, @Cached.Shared(value="isSealed") @Cached(value="createIsSealed()") TestArrayNode isSealedNode, @Cached(value="createClassProfile()") ValueProfile arrayTypeProfile, @Cached.Shared(value="lengthIsZero") @Cached(value="createBinaryProfile()") ConditionProfile lengthIsZero, @Cached(value="createBinaryProfile()") ConditionProfile lengthLargerOne) {
            long len = this.getLength(thisObj);
            if (lengthIsZero.profile(len == 0L)) {
                this.setLength((Object)thisObj, 0);
                return Undefined.instance;
            }
            Object firstElement = this.read((Object)thisObj, 0);
            if (lengthLargerOne.profile(len > 1L)) {
                ScriptArray array = arrayTypeProfile.profile(JSAbstractArray.arrayGetArrayType(thisObj));
                JSAbstractArray.arraySetArrayType(thisObj, array.shiftRange(thisObj, 1L));
            }
            this.setLength((Object)thisObj, len - 1L);
            return firstElement;
        }

        protected static boolean isArrayWithHolesOrSealed(JSDynamicObject thisObj, IsArrayNode isArrayNode, TestArrayNode hasHolesNode, TestArrayNode isSealedNode) {
            boolean isArray = isArrayNode.execute(thisObj);
            return isArray && (hasHolesNode.executeBoolean(thisObj) || isSealedNode.executeBoolean(thisObj)) && !JSArrayShiftNode.isSparseArray(thisObj);
        }

        @Specialization(guards={"isArrayWithHolesOrSealed(thisObj, isArrayNode, hasHolesNode, isSealedNode)"}, limit="1")
        protected Object shiftWithHoles(JSDynamicObject thisObj, @Cached.Shared(value="isArray") @Cached(value="createIsArray()") IsArrayNode isArrayNode, @Cached.Shared(value="hasHoles") @Cached(value="createHasHoles()") TestArrayNode hasHolesNode, @Cached.Shared(value="isSealed") @Cached(value="createIsSealed()") TestArrayNode isSealedNode, @Cached.Shared(value="deleteProperty") @Cached(value="create(THROW_ERROR, getContext())") DeletePropertyNode deletePropertyNode, @Cached.Shared(value="lengthIsZero") @Cached(value="createBinaryProfile()") ConditionProfile lengthIsZero) {
            long len = this.getLength(thisObj);
            if (lengthIsZero.profile(len > 0L)) {
                Object firstElement = this.read((Object)thisObj, 0);
                for (long i = 0L; i < len - 1L; ++i) {
                    if (this.hasProperty((Object)thisObj, i + 1L)) {
                        this.write((Object)thisObj, i, this.read((Object)thisObj, i + 1L));
                        continue;
                    }
                    deletePropertyNode.executeEvaluated(thisObj, i);
                }
                deletePropertyNode.executeEvaluated(thisObj, len - 1L);
                this.setLength((Object)thisObj, len - 1L);
                this.reportLoopCount(len - 1L);
                return firstElement;
            }
            this.setLength((Object)thisObj, 0);
            return Undefined.instance;
        }

        @Specialization(guards={"isArrayNode.execute(thisObj)", "isSparseArray(thisObj)"}, limit="1")
        protected Object shiftSparse(JSDynamicObject thisObj, @Cached.Shared(value="isArray") @Cached(value="createIsArray()") IsArrayNode isArrayNode, @Cached.Shared(value="deleteProperty") @Cached(value="create(THROW_ERROR, getContext())") DeletePropertyNode deletePropertyNode, @Cached.Shared(value="lengthIsZero") @Cached(value="createBinaryProfile()") ConditionProfile lengthIsZero, @Cached(value="create(getContext())") JSArrayFirstElementIndexNode firstElementIndexNode, @Cached(value="create(getContext())") JSArrayLastElementIndexNode lastElementIndexNode) {
            long len = this.getLength(thisObj);
            if (lengthIsZero.profile(len > 0L)) {
                Object firstElement = this.read((Object)thisObj, 0);
                long count = 0L;
                long i = firstElementIndexNode.executeLong(thisObj, len);
                while (i <= lastElementIndexNode.executeLong(thisObj, len)) {
                    if (i > 0L) {
                        this.write((Object)thisObj, i - 1L, this.read((Object)thisObj, i));
                    }
                    if (!this.hasProperty((Object)thisObj, i + 1L)) {
                        deletePropertyNode.executeEvaluated(thisObj, i);
                    }
                    ++count;
                    i = this.nextElementIndex(thisObj, i, len);
                }
                this.setLength((Object)thisObj, len - 1L);
                this.reportLoopCount(count);
                return firstElement;
            }
            this.setLength((Object)thisObj, 0);
            return Undefined.instance;
        }

        @Specialization(guards={"!isJSArray(thisObj)", "!isForeignObject(thisObj)"})
        protected Object shiftGeneric(Object thisObj, @Cached.Shared(value="deleteProperty") @Cached(value="create(THROW_ERROR, getContext())") DeletePropertyNode deleteNode, @Cached.Shared(value="lengthIsZero") @Cached(value="createBinaryProfile()") ConditionProfile lengthIsZero) {
            Object thisJSObj = this.toObject(thisObj);
            long len = this.getLength(thisJSObj);
            if (lengthIsZero.profile(len == 0L)) {
                this.setLength(thisJSObj, 0);
                return Undefined.instance;
            }
            Object firstObj = this.read(thisJSObj, 0);
            for (long i = 1L; i < len; ++i) {
                if (this.hasProperty(thisJSObj, i)) {
                    this.write(thisJSObj, i - 1L, this.read(thisObj, i));
                    continue;
                }
                deleteNode.executeEvaluated(thisJSObj, i - 1L);
            }
            deleteNode.executeEvaluated(thisJSObj, len - 1L);
            this.setLength(thisJSObj, len - 1L);
            this.reportLoopCount(len);
            return firstObj;
        }

        @Specialization(guards={"isForeignObject(thisObj)"})
        protected Object shiftForeign(Object thisObj, @CachedLibrary(limit="InteropLibraryLimit") InteropLibrary arrays, @Cached.Shared(value="lengthIsZero") @Cached(value="createBinaryProfile()") ConditionProfile lengthIsZero) {
            long len = JSInteropUtil.getArraySize(thisObj, arrays, this);
            if (lengthIsZero.profile(len == 0L)) {
                return Undefined.instance;
            }
            try {
                Object firstObj = arrays.readArrayElement(thisObj, 0L);
                for (long i = 1L; i < len; ++i) {
                    Object val = arrays.readArrayElement(thisObj, i);
                    arrays.writeArrayElement(thisObj, i - 1L, val);
                }
                arrays.removeArrayElement(thisObj, len - 1L);
                this.reportLoopCount(len);
                return firstObj;
            }
            catch (InvalidArrayIndexException | UnsupportedMessageException | UnsupportedTypeException e) {
                throw Errors.createTypeErrorInteropException(thisObj, e, "shift", this);
            }
        }
    }

    public static abstract class JSArraySliceNode
    extends ArrayForEachIndexCallOperation {
        private final ConditionProfile sizeIsZero = ConditionProfile.createBinaryProfile();
        private final ConditionProfile offsetProfile1 = ConditionProfile.createBinaryProfile();
        private final ConditionProfile offsetProfile2 = ConditionProfile.createBinaryProfile();

        public JSArraySliceNode(JSContext context, JSBuiltin builtin, boolean isTypedArrayImplementation) {
            super(context, builtin, isTypedArrayImplementation);
        }

        @Specialization
        protected Object sliceGeneric(Object thisObj, Object begin, Object end2, @Cached(value="create()") JSToIntegerAsLongNode toIntegerAsLong) {
            Object thisArrayObj = this.toObjectOrValidateTypedArray(thisObj);
            long len = this.getLength(thisArrayObj);
            long startPos = begin != Undefined.instance ? JSRuntime.getOffset(toIntegerAsLong.executeLong(begin), len, this.offsetProfile1) : 0L;
            long endPos = end2 == Undefined.instance ? len : JSRuntime.getOffset(toIntegerAsLong.executeLong(end2), len, this.offsetProfile2);
            long size = startPos <= endPos ? endPos - startPos : 0L;
            Object resultArray = this.getArraySpeciesConstructorNode().createEmptyContainer(thisArrayObj, size);
            if (this.sizeIsZero.profile(size > 0L)) {
                if (this.isTypedArrayImplementation) {
                    this.checkHasDetachedBuffer((JSDynamicObject)thisObj);
                }
                this.forEachIndexCall(thisArrayObj, null, startPos, startPos, endPos, resultArray);
            }
            if (!this.isTypedArrayImplementation) {
                this.setLength(resultArray, size);
            }
            return resultArray;
        }

        @Override
        protected ForEachIndexCallNode.MaybeResultNode makeMaybeResultNode() {
            return new ForEachIndexCallNode.MaybeResultNode(){
                @Node.Child
                private WriteElementNode writeOwnNode;
                {
                    this.writeOwnNode = WriteElementNode.create(this.getContext(), true, true);
                }

                @Override
                public ForEachIndexCallNode.MaybeResult<Object> apply(long index, Object value2, Object callbackResult, Object currentResult) {
                    long startIndex = (Long)callbackResult;
                    this.writeOwnNode.executeWithTargetAndIndexAndValue(currentResult, index - startIndex, value2);
                    return ForEachIndexCallNode.MaybeResult.continueResult(currentResult);
                }
            };
        }

        @Override
        protected ForEachIndexCallNode.CallbackNode makeCallbackNode() {
            return null;
        }
    }

    @ImportStatic(value={JSRuntime.class, JSConfig.class})
    protected static abstract class DeleteAndSetLengthNode
    extends JavaScriptBaseNode {
        protected static final boolean THROW_ERROR = true;
        protected final JSContext context;

        protected DeleteAndSetLengthNode(JSContext context) {
            this.context = context;
        }

        public static DeleteAndSetLengthNode create(JSContext context) {
            return ArrayPrototypeBuiltinsFactory.DeleteAndSetLengthNodeGen.create(context);
        }

        public abstract void executeVoid(Object var1, long var2);

        protected final WritePropertyNode createWritePropertyNode() {
            return WritePropertyNode.create(null, JSArray.LENGTH, null, this.context, true);
        }

        protected static boolean isArray(JSDynamicObject object) {
            return JSArray.isJSFastArray(object);
        }

        @Specialization(guards={"isArray(object)", "longIsRepresentableAsInt(longLength)"})
        protected static void setArrayLength(JSDynamicObject object, long longLength, @Cached(value="createArrayLengthWriteNode()") ArrayLengthNode.ArrayLengthWriteNode arrayLengthWriteNode) {
            arrayLengthWriteNode.executeVoid(object, (int)longLength);
        }

        protected static final ArrayLengthNode.ArrayLengthWriteNode createArrayLengthWriteNode() {
            return ArrayLengthNode.ArrayLengthWriteNode.createSetOrDelete(true);
        }

        @Specialization(guards={"isJSObject(object)", "longIsRepresentableAsInt(longLength)"})
        protected static void setIntLength(JSDynamicObject object, long longLength, @Cached(value="create(THROW_ERROR, context)") DeletePropertyNode deletePropertyNode, @Cached(value="createWritePropertyNode()") WritePropertyNode setLengthProperty) {
            int intLength = (int)longLength;
            deletePropertyNode.executeEvaluated(object, intLength);
            setLengthProperty.executeIntWithValue(object, intLength);
        }

        @Specialization(guards={"isJSObject(object)"}, replaces={"setIntLength"})
        protected static void setLength(JSDynamicObject object, long longLength, @Cached(value="create(THROW_ERROR, context)") DeletePropertyNode deletePropertyNode, @Cached(value="createWritePropertyNode()") WritePropertyNode setLengthProperty, @Cached(value="createBinaryProfile()") ConditionProfile indexInIntRangeCondition) {
            Object boxedLength = JSRuntime.boxIndex(longLength, indexInIntRangeCondition);
            deletePropertyNode.executeEvaluated(object, boxedLength);
            setLengthProperty.executeWithValue(object, boxedLength);
        }

        @Specialization(guards={"!isJSObject(object)"})
        protected static void foreignArray(Object object, long newLength, @CachedLibrary(limit="InteropLibraryLimit") InteropLibrary arrays) {
            try {
                arrays.removeArrayElement(object, newLength);
            }
            catch (InvalidArrayIndexException | UnsupportedMessageException e) {
                throw Errors.createTypeErrorInteropException(object, e, "removeArrayElement", null);
            }
        }
    }

    public static abstract class JSArrayPopNode
    extends JSArrayOperation {
        public JSArrayPopNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected Object popGeneric(Object thisObj, @Cached(value="create(getContext())") DeleteAndSetLengthNode deleteAndSetLength, @Cached(value="createBinaryProfile()") ConditionProfile lengthIsZero) {
            Object thisObject = this.toObject(thisObj);
            long length = this.getLength(thisObject);
            if (lengthIsZero.profile(length > 0L)) {
                long newLength = length - 1L;
                Object result = this.read(thisObject, newLength);
                deleteAndSetLength.executeVoid(thisObject, newLength);
                return result;
            }
            assert (length == 0L);
            this.setLength(thisObject, 0);
            return Undefined.instance;
        }
    }

    public static abstract class JSArrayPushNode
    extends JSArrayOperation {
        public JSArrayPushNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization(guards={"isJSArray(thisObject)", "args.length == 0"})
        protected Object pushArrayNone(JSDynamicObject thisObject, Object[] args) {
            long len = this.getLength(thisObject);
            this.setLength((Object)thisObject, len);
            if (len >= Integer.MAX_VALUE) {
                return (double)len;
            }
            return (int)len;
        }

        @Specialization(guards={"isJSArray(thisObject)", "args.length == 1"}, rewriteOn={SlowPathException.class})
        protected int pushArraySingle(JSDynamicObject thisObject, Object[] args) throws SlowPathException {
            long len = this.getLength(thisObject);
            if (len >= Integer.MAX_VALUE) {
                throw JSNodeUtil.slowPathException();
            }
            int iLen = (int)len;
            this.write((Object)thisObject, iLen, args[0]);
            int newLength = iLen + 1;
            this.setLength((Object)thisObject, newLength);
            return newLength;
        }

        @Specialization(guards={"isJSArray(thisObject)", "args.length == 1"})
        protected double pushArraySingleLong(JSDynamicObject thisObject, Object[] args) {
            long len = this.getLength(thisObject);
            this.checkLength(args, len);
            this.write((Object)thisObject, len, args[0]);
            long newLength = len + 1L;
            this.setLength((Object)thisObject, newLength);
            return newLength;
        }

        @Specialization(guards={"isJSArray(thisObject)", "args.length >= 2"}, rewriteOn={SlowPathException.class})
        protected int pushArrayAll(JSDynamicObject thisObject, Object[] args) throws SlowPathException {
            long len = this.getLength(thisObject);
            if (len + (long)args.length >= Integer.MAX_VALUE) {
                throw JSNodeUtil.slowPathException();
            }
            int ilen = (int)len;
            for (int i = 0; i < args.length; ++i) {
                this.write((Object)thisObject, ilen + i, args[i]);
            }
            this.setLength((Object)thisObject, ilen + args.length);
            return ilen + args.length;
        }

        @Specialization(guards={"isJSArray(thisObject)", "args.length >= 2"})
        protected double pushArrayAllLong(JSDynamicObject thisObject, Object[] args) {
            long len = this.getLength(thisObject);
            this.checkLength(args, len);
            for (int i = 0; i < args.length; ++i) {
                this.write((Object)thisObject, len + (long)i, args[i]);
            }
            this.setLength((Object)thisObject, len + (long)args.length);
            return (double)len + (double)args.length;
        }

        @Specialization(guards={"!isJSArray(thisObject)"})
        protected double pushProperty(Object thisObject, Object[] args) {
            Object thisObj = this.toObject(thisObject);
            long len = this.getLength(thisObj);
            this.checkLength(args, len);
            for (int i = 0; i < args.length; ++i) {
                this.write(thisObj, len + (long)i, args[i]);
            }
            long newLength = len + (long)args.length;
            this.setLength(thisObj, newLength);
            return newLength;
        }

        private void checkLength(Object[] args, long len) {
            if ((double)(len + (long)args.length) > JSRuntime.MAX_SAFE_INTEGER) {
                this.errorBranch.enter();
                JSArrayPushNode.throwLengthError();
            }
        }
    }

    public static abstract class JSArrayOperationWithToInt
    extends JSArrayOperation {
        @Node.Child
        private JSToIntegerAsLongNode toIntegerAsLongNode;

        public JSArrayOperationWithToInt(JSContext context, JSBuiltin builtin, boolean isTypedArrayImplementation) {
            super(context, builtin, isTypedArrayImplementation);
        }

        public JSArrayOperationWithToInt(JSContext context, JSBuiltin builtin) {
            this(context, builtin, false);
        }

        protected long toIntegerAsLong(Object target) {
            if (this.toIntegerAsLongNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.toIntegerAsLongNode = this.insert(JSToIntegerAsLongNode.create());
            }
            return this.toIntegerAsLongNode.executeLong(target);
        }
    }

    public static abstract class JSArrayOperation
    extends BasicArrayOperation {
        protected static final boolean THROW_ERROR = true;
        @Node.Child
        private JSSetLengthNode setLengthNode;
        @Node.Child
        private WriteElementNode writeNode;
        @Node.Child
        private WriteElementNode writeOwnNode;
        @Node.Child
        private ReadElementNode readNode;
        @Node.Child
        private JSHasPropertyNode hasPropertyNode;
        @Node.Child
        private JSArrayNextElementIndexNode nextElementIndexNode;
        @Node.Child
        private JSArrayPreviousElementIndexNode previousElementIndexNode;

        public JSArrayOperation(JSContext context, JSBuiltin builtin, boolean isTypedArrayImplementation) {
            super(context, builtin, isTypedArrayImplementation);
        }

        public JSArrayOperation(JSContext context, JSBuiltin builtin) {
            super(context, builtin, false);
        }

        protected void setLength(Object thisObject, int length) {
            this.setLengthIntl(thisObject, length);
        }

        protected void setLength(Object thisObject, long length) {
            this.setLengthIntl(thisObject, JSRuntime.longToIntOrDouble(length));
        }

        protected void setLength(Object thisObject, double length) {
            this.setLengthIntl(thisObject, length);
        }

        private void setLengthIntl(Object thisObject, Object length) {
            assert (!(length instanceof Long));
            if (this.setLengthNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.setLengthNode = this.insert(JSSetLengthNode.create(this.getContext(), true));
            }
            this.setLengthNode.execute(thisObject, length);
        }

        private ReadElementNode getOrCreateReadNode() {
            if (this.readNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.readNode = this.insert(ReadElementNode.create(this.getContext()));
            }
            return this.readNode;
        }

        protected Object read(Object target, int index) {
            return this.getOrCreateReadNode().executeWithTargetAndIndex(target, index);
        }

        protected Object read(Object target, long index) {
            ReadElementNode read2 = this.getOrCreateReadNode();
            return read2.executeWithTargetAndIndex(target, index);
        }

        private WriteElementNode getOrCreateWriteNode() {
            if (this.writeNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.writeNode = this.insert(WriteElementNode.create(this.getContext(), true));
            }
            return this.writeNode;
        }

        protected void write(Object target, int index, Object value2) {
            this.getOrCreateWriteNode().executeWithTargetAndIndexAndValue(target, index, value2);
        }

        protected void write(Object target, long index, Object value2) {
            WriteElementNode write = this.getOrCreateWriteNode();
            write.executeWithTargetAndIndexAndValue(target, index, value2);
        }

        private WriteElementNode getOrCreateWriteOwnNode() {
            if (this.writeOwnNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.writeOwnNode = this.insert(WriteElementNode.create(this.getContext(), true, true));
            }
            return this.writeOwnNode;
        }

        protected void writeOwn(Object target, int index, Object value2) {
            this.getOrCreateWriteOwnNode().executeWithTargetAndIndexAndValue(target, index, value2);
        }

        protected void writeOwn(Object target, long index, Object value2) {
            WriteElementNode write = this.getOrCreateWriteOwnNode();
            write.executeWithTargetAndIndexAndValue(target, index, value2);
        }

        private JSHasPropertyNode getOrCreateHasPropertyNode() {
            if (this.hasPropertyNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.hasPropertyNode = this.insert(JSHasPropertyNode.create());
            }
            return this.hasPropertyNode;
        }

        protected boolean hasProperty(Object target, long propertyIdx) {
            return this.getOrCreateHasPropertyNode().executeBoolean(target, propertyIdx);
        }

        protected boolean hasProperty(Object target, Object propertyName) {
            return this.getOrCreateHasPropertyNode().executeBoolean(target, propertyName);
        }

        protected long nextElementIndex(Object target, long currentIndex, long length) {
            if (this.nextElementIndexNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.nextElementIndexNode = this.insert(JSArrayNextElementIndexNode.create(this.getContext()));
            }
            return this.nextElementIndexNode.executeLong(target, currentIndex, length);
        }

        protected long previousElementIndex(Object target, long currentIndex) {
            if (this.previousElementIndexNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.previousElementIndexNode = this.insert(JSArrayPreviousElementIndexNode.create(this.getContext()));
            }
            return this.previousElementIndexNode.executeLong(target, currentIndex);
        }

        protected static final void throwLengthError() {
            throw Errors.createTypeError("length too big");
        }
    }

    protected static class ArraySpeciesConstructorNode
    extends JavaScriptBaseNode {
        private final boolean isTypedArrayImplementation;
        @Node.Child
        private JSFunctionCallNode constructorCall;
        @Node.Child
        private PropertyGetNode getConstructorNode;
        @Node.Child
        private PropertyGetNode getSpeciesNode;
        @Node.Child
        private JSIsArrayNode isArrayNode;
        @Node.Child
        private IsConstructorNode isConstructorNode = IsConstructorNode.create();
        @Node.Child
        private ArrayCreateNode arrayCreateNode;
        private final BranchProfile errorBranch = BranchProfile.create();
        private final BranchProfile arraySpeciesIsArray = BranchProfile.create();
        private final BranchProfile arraySpeciesGetSymbol = BranchProfile.create();
        private final BranchProfile differentRealm = BranchProfile.create();
        private final BranchProfile defaultConstructorBranch = BranchProfile.create();
        private final ConditionProfile arraySpeciesEmpty = ConditionProfile.createBinaryProfile();
        private final BranchProfile notAJSObjectBranch = BranchProfile.create();
        private final JSContext context;

        protected ArraySpeciesConstructorNode(JSContext context, boolean isTypedArrayImplementation) {
            this.context = context;
            this.isTypedArrayImplementation = isTypedArrayImplementation;
            this.isArrayNode = JSIsArrayNode.createIsArray();
            this.constructorCall = JSFunctionCallNode.createNew();
        }

        protected static ArraySpeciesConstructorNode create(JSContext context, boolean isTypedArrayImplementation) {
            return new ArraySpeciesConstructorNode(context, isTypedArrayImplementation);
        }

        protected final Object createEmptyContainer(Object thisObj, long size) {
            if (this.isTypedArrayImplementation) {
                return this.typedArraySpeciesCreate(JSRuntime.expectJSObject(thisObj, this.notAJSObjectBranch), JSRuntime.longToIntOrDouble(size));
            }
            return this.arraySpeciesCreate(thisObj, size);
        }

        protected final JSTypedArrayObject typedArraySpeciesCreate(JSDynamicObject thisObj, Object ... args) {
            JSDynamicObject constr = this.speciesConstructor(thisObj, this.getDefaultConstructor(thisObj));
            return this.typedArrayCreate(constr, args);
        }

        public final JSTypedArrayObject typedArrayCreate(JSDynamicObject constr, Object ... args) {
            Object newObject = this.construct(constr, args);
            if (!JSArrayBufferView.isJSArrayBufferView(newObject)) {
                this.errorBranch.enter();
                throw Errors.createTypeErrorArrayBufferViewExpected();
            }
            JSTypedArrayObject newTypedArray = (JSTypedArrayObject)newObject;
            if (JSArrayBufferView.hasDetachedBuffer(newTypedArray, this.context)) {
                this.errorBranch.enter();
                throw Errors.createTypeErrorDetachedBuffer();
            }
            if (args.length == 1 && JSRuntime.isNumber(args[0]) && (double)JSArrayBufferView.typedArrayGetLength(newTypedArray) < JSRuntime.doubleValue((Number)args[0])) {
                this.errorBranch.enter();
                throw Errors.createTypeError("invalid TypedArray created");
            }
            return newTypedArray;
        }

        protected final Object arraySpeciesCreate(Object originalArray, long length) {
            Object ctor = Undefined.instance;
            if (this.isArray(originalArray)) {
                this.arraySpeciesIsArray.enter();
                ctor = this.getConstructorProperty(originalArray);
                if (ctor instanceof JSObject) {
                    JSRealm ctorRealm;
                    JSRealm thisRealm;
                    JSObject ctorObj = (JSObject)ctor;
                    if (JSFunction.isJSFunction(ctorObj) && JSFunction.isConstructor(ctorObj) && (thisRealm = this.getRealm()) != (ctorRealm = JSFunction.getRealm((JSFunctionObject)ctorObj))) {
                        this.differentRealm.enter();
                        if (ctorRealm.getArrayConstructor() == ctor) {
                            return this.arrayCreate(length);
                        }
                    }
                    this.arraySpeciesGetSymbol.enter();
                    ctor = this.getSpeciesProperty(ctor);
                    ctor = ctor == Null.instance ? Undefined.instance : ctor;
                }
            }
            if (this.arraySpeciesEmpty.profile(ctor == Undefined.instance)) {
                return this.arrayCreate(length);
            }
            if (!this.isConstructorNode.executeBoolean(ctor)) {
                this.errorBranch.enter();
                throw Errors.createTypeErrorNotAConstructor(ctor, this.context);
            }
            return this.construct((JSDynamicObject)ctor, JSRuntime.longToIntOrDouble(length));
        }

        protected final boolean isArray(Object thisObj) {
            return this.isArrayNode.execute(thisObj);
        }

        private Object arrayCreate(long length) {
            if (this.arrayCreateNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.arrayCreateNode = this.insert(ArrayCreateNode.create(this.context));
            }
            return this.arrayCreateNode.execute(length);
        }

        protected Object construct(JSDynamicObject constructor, Object ... userArgs) {
            Object[] args = JSArguments.createInitial(JSFunction.CONSTRUCT, constructor, userArgs.length);
            System.arraycopy(userArgs, 0, args, 2, userArgs.length);
            return this.constructorCall.executeCall(args);
        }

        protected final JSDynamicObject getDefaultConstructor(JSDynamicObject thisObj) {
            assert (JSArrayBufferView.isJSArrayBufferView(thisObj));
            TypedArray arrayType = JSArrayBufferView.typedArrayGetArrayType(thisObj);
            return this.getRealm().getArrayBufferViewConstructor(arrayType.getFactory());
        }

        protected final JSDynamicObject speciesConstructor(JSDynamicObject thisObj, JSDynamicObject defaultConstructor) {
            Object c = this.getConstructorProperty(thisObj);
            if (c == Undefined.instance) {
                this.defaultConstructorBranch.enter();
                return defaultConstructor;
            }
            if (!(c instanceof JSObject)) {
                this.errorBranch.enter();
                throw Errors.createTypeErrorNotAnObject(c);
            }
            Object speciesConstructor = this.getSpeciesProperty(c);
            if (speciesConstructor == Undefined.instance || speciesConstructor == Null.instance) {
                this.defaultConstructorBranch.enter();
                return defaultConstructor;
            }
            if (!this.isConstructorNode.executeBoolean(speciesConstructor)) {
                this.errorBranch.enter();
                throw Errors.createTypeErrorNotAConstructor(speciesConstructor, this.context);
            }
            return (JSDynamicObject)speciesConstructor;
        }

        private Object getConstructorProperty(Object obj) {
            if (this.getConstructorNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.getConstructorNode = this.insert(PropertyGetNode.create(JSObject.CONSTRUCTOR, false, this.context));
            }
            return this.getConstructorNode.getValue(obj);
        }

        private Object getSpeciesProperty(Object obj) {
            if (this.getSpeciesNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.getSpeciesNode = this.insert(PropertyGetNode.create(Symbol.SYMBOL_SPECIES, false, this.context));
            }
            return this.getSpeciesNode.getValue(obj);
        }
    }

    public static abstract class BasicArrayOperation
    extends JSBuiltinNode {
        protected final boolean isTypedArrayImplementation;
        @Node.Child
        private JSToObjectNode toObjectNode;
        @Node.Child
        private JSGetLengthNode getLengthNode;
        @Node.Child
        private ArraySpeciesConstructorNode arraySpeciesCreateNode;
        @Node.Child
        private IsCallableNode isCallableNode;
        protected final BranchProfile errorBranch = BranchProfile.create();

        public BasicArrayOperation(JSContext context, JSBuiltin builtin, boolean isTypedArrayImplementation) {
            super(context, builtin);
            this.isTypedArrayImplementation = isTypedArrayImplementation;
        }

        public BasicArrayOperation(JSContext context, JSBuiltin builtin) {
            this(context, builtin, false);
        }

        protected final Object toObject(Object target) {
            if (this.toObjectNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.toObjectNode = this.insert(JSToObjectNode.createToObject(this.getContext()));
            }
            return this.toObjectNode.execute(target);
        }

        protected long getLength(Object thisObject) {
            if (this.isTypedArrayImplementation) {
                return JSArrayBufferView.typedArrayGetLength((JSTypedArrayObject)thisObject);
            }
            if (this.getLengthNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.getLengthNode = this.insert(JSGetLengthNode.create(this.getContext()));
            }
            return this.getLengthNode.executeLong(thisObject);
        }

        protected final Object toObjectOrValidateTypedArray(Object thisObj) {
            return this.isTypedArrayImplementation ? this.validateTypedArray(thisObj) : this.toObject(thisObj);
        }

        protected final boolean isCallable(Object callback) {
            if (this.isCallableNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.isCallableNode = this.insert(IsCallableNode.create());
            }
            return this.isCallableNode.executeBoolean(callback);
        }

        protected final Object checkCallbackIsFunction(Object callback) {
            if (!this.isCallable(callback)) {
                this.errorBranch.enter();
                throw Errors.createTypeErrorNotAFunction(callback, this);
            }
            return callback;
        }

        protected final ArraySpeciesConstructorNode getArraySpeciesConstructorNode() {
            if (this.arraySpeciesCreateNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.arraySpeciesCreateNode = this.insert(ArraySpeciesConstructorNode.create(this.getContext(), this.isTypedArrayImplementation));
            }
            return this.arraySpeciesCreateNode;
        }

        protected final void checkHasDetachedBuffer(JSDynamicObject view) {
            if (JSArrayBufferView.hasDetachedBuffer(view, this.getContext())) {
                this.errorBranch.enter();
                throw Errors.createTypeErrorDetachedBuffer();
            }
        }

        protected final JSTypedArrayObject validateTypedArray(Object obj) {
            if (!JSArrayBufferView.isJSArrayBufferView(obj)) {
                this.errorBranch.enter();
                throw Errors.createTypeErrorArrayBufferViewExpected();
            }
            JSTypedArrayObject typedArrayObject = (JSTypedArrayObject)obj;
            if (JSArrayBufferView.hasDetachedBuffer(typedArrayObject, this.getContext())) {
                this.errorBranch.enter();
                throw Errors.createTypeErrorDetachedBuffer();
            }
            return typedArrayObject;
        }

        protected void reportLoopCount(long count) {
            BasicArrayOperation.reportLoopCount(this, count);
        }

        public static void reportLoopCount(Node node, long count) {
            if (count > 0L) {
                LoopNode.reportLoopCount(node, count > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int)count);
            }
        }
    }

    public static enum ArrayPrototype implements BuiltinEnum<ArrayPrototype>
    {
        push(1),
        pop(0),
        slice(2),
        shift(0),
        unshift(1),
        toString(0),
        concat(1),
        indexOf(1),
        lastIndexOf(1),
        join(1),
        toLocaleString(0),
        splice(2),
        every(1),
        filter(1),
        forEach(1),
        some(1),
        map(1),
        sort(1),
        reduce(1),
        reduceRight(1),
        reverse(0),
        find(1),
        findIndex(1),
        fill(1),
        copyWithin(2),
        keys(0),
        values(0),
        entries(0),
        includes(1),
        flat(0),
        flatMap(1),
        at(1),
        group(1),
        groupToMap(1),
        findLast(1),
        findLastIndex(1);

        private final int length;

        private ArrayPrototype(int length) {
            this.length = length;
        }

        @Override
        public int getLength() {
            return this.length;
        }

        @Override
        public int getECMAScriptVersion() {
            if (EnumSet.of(find, new ArrayPrototype[]{findIndex, fill, copyWithin, keys, values, entries}).contains(this)) {
                return 6;
            }
            if (this == includes) {
                return 7;
            }
            if (EnumSet.of(flat, flatMap).contains(this)) {
                return 10;
            }
            if (this == at) {
                return 13;
            }
            if (EnumSet.of(group, groupToMap, findLast, findLastIndex).contains(this)) {
                return 14;
            }
            return BuiltinEnum.super.getECMAScriptVersion();
        }
    }
}

