
package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.exception.AbstractTruffleException;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.interop.ArityException;
import com.oracle.truffle.api.interop.InteropException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.InvalidArrayIndexException;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.interop.UnsupportedTypeException;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.builtins.ObjectFunctionBuiltinsFactory;
import com.oracle.truffle.js.builtins.ObjectPrototypeBuiltins;
import com.oracle.truffle.js.builtins.helper.ListGetNode;
import com.oracle.truffle.js.builtins.helper.ListSizeNode;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.CreateObjectNode;
import com.oracle.truffle.js.nodes.access.EnumerableOwnPropertyNamesNode;
import com.oracle.truffle.js.nodes.access.FromPropertyDescriptorNode;
import com.oracle.truffle.js.nodes.access.GetIteratorBaseNode;
import com.oracle.truffle.js.nodes.access.GetPrototypeNode;
import com.oracle.truffle.js.nodes.access.IsExtensibleNode;
import com.oracle.truffle.js.nodes.access.IsObjectNode;
import com.oracle.truffle.js.nodes.access.IteratorCloseNode;
import com.oracle.truffle.js.nodes.access.IteratorStepNode;
import com.oracle.truffle.js.nodes.access.IteratorValueNode;
import com.oracle.truffle.js.nodes.access.JSGetOwnPropertyNode;
import com.oracle.truffle.js.nodes.access.JSHasPropertyNode;
import com.oracle.truffle.js.nodes.access.ReadElementNode;
import com.oracle.truffle.js.nodes.access.RequireObjectCoercibleNode;
import com.oracle.truffle.js.nodes.access.ToPropertyDescriptorNode;
import com.oracle.truffle.js.nodes.access.WriteElementNode;
import com.oracle.truffle.js.nodes.binary.JSIdenticalNode;
import com.oracle.truffle.js.nodes.cast.JSToObjectNode;
import com.oracle.truffle.js.nodes.cast.JSToPropertyKeyNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.nodes.interop.ForeignObjectPrototypeNode;
import com.oracle.truffle.js.nodes.interop.ImportValueNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.Boundaries;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Properties;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.JSArray;
import com.oracle.truffle.js.runtime.builtins.JSClass;
import com.oracle.truffle.js.runtime.builtins.JSOrdinary;
import com.oracle.truffle.js.runtime.interop.JSInteropUtil;
import com.oracle.truffle.js.runtime.objects.IteratorRecord;
import com.oracle.truffle.js.runtime.objects.JSAttributes;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;
import com.oracle.truffle.js.runtime.objects.Null;
import com.oracle.truffle.js.runtime.objects.PropertyDescriptor;
import com.oracle.truffle.js.runtime.objects.PropertyProxy;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.JSClassProfile;
import com.oracle.truffle.js.runtime.util.Pair;
import com.oracle.truffle.js.runtime.util.UnmodifiableArrayList;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public final class ObjectFunctionBuiltins
extends JSBuiltinsContainer.SwitchEnum<ObjectFunction> {
    public static final JSBuiltinsContainer BUILTINS = new ObjectFunctionBuiltins();
    public static final JSBuiltinsContainer BUILTINS_NASHORN_COMPAT = new ObjectFunctionNashornCompatBuiltins();

    protected ObjectFunctionBuiltins() {
        super(JSOrdinary.CLASS_NAME, ObjectFunction.class);
    }

    @Override
    protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, ObjectFunction builtinEnum) {
        switch (builtinEnum) {
            case create: {
                return ObjectFunctionBuiltinsFactory.ObjectCreateNodeGen.create(context, builtin, ObjectFunctionBuiltins.args().fixedArgs(2).createArgumentNodes(context));
            }
            case defineProperties: {
                return ObjectFunctionBuiltinsFactory.ObjectDefinePropertiesNodeGen.create(context, builtin, ObjectFunctionBuiltins.args().fixedArgs(2).createArgumentNodes(context));
            }
            case defineProperty: {
                return ObjectFunctionBuiltinsFactory.ObjectDefinePropertyNodeGen.create(context, builtin, ObjectFunctionBuiltins.args().fixedArgs(3).createArgumentNodes(context));
            }
            case freeze: {
                return ObjectFunctionBuiltinsFactory.ObjectSetIntegrityLevelNodeGen.create(context, builtin, true, ObjectFunctionBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
            case getOwnPropertyDescriptor: {
                return ObjectFunctionBuiltinsFactory.ObjectGetOwnPropertyDescriptorNodeGen.create(context, builtin, ObjectFunctionBuiltins.args().fixedArgs(2).createArgumentNodes(context));
            }
            case getOwnPropertyDescriptors: {
                return ObjectFunctionBuiltinsFactory.ObjectGetOwnPropertyDescriptorsNodeGen.create(context, builtin, ObjectFunctionBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
            case getOwnPropertyNames: {
                return ObjectFunctionBuiltinsFactory.ObjectGetOwnPropertyNamesOrSymbolsNodeGen.create(context, builtin, false, ObjectFunctionBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
            case getPrototypeOf: {
                return ObjectFunctionBuiltinsFactory.ObjectGetPrototypeOfNodeGen.create(context, builtin, ObjectFunctionBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
            case isExtensible: {
                return ObjectFunctionBuiltinsFactory.ObjectIsExtensibleNodeGen.create(context, builtin, ObjectFunctionBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
            case isFrozen: {
                return ObjectFunctionBuiltinsFactory.ObjectTestIntegrityLevelNodeGen.create(context, builtin, true, ObjectFunctionBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
            case isSealed: {
                return ObjectFunctionBuiltinsFactory.ObjectTestIntegrityLevelNodeGen.create(context, builtin, false, ObjectFunctionBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
            case keys: {
                return ObjectFunctionBuiltinsFactory.ObjectKeysNodeGen.create(context, builtin, ObjectFunctionBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
            case preventExtensions: {
                return ObjectFunctionBuiltinsFactory.ObjectPreventExtensionsNodeGen.create(context, builtin, ObjectFunctionBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
            case seal: {
                return ObjectFunctionBuiltinsFactory.ObjectSetIntegrityLevelNodeGen.create(context, builtin, false, ObjectFunctionBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
            case setPrototypeOf: {
                return ObjectFunctionBuiltinsFactory.ObjectSetPrototypeOfNodeGen.create(context, builtin, ObjectFunctionBuiltins.args().fixedArgs(2).createArgumentNodes(context));
            }
            case is: {
                return ObjectFunctionBuiltinsFactory.ObjectIsNodeGen.create(context, builtin, ObjectFunctionBuiltins.args().fixedArgs(2).createArgumentNodes(context));
            }
            case getOwnPropertySymbols: {
                return ObjectFunctionBuiltinsFactory.ObjectGetOwnPropertyNamesOrSymbolsNodeGen.create(context, builtin, true, ObjectFunctionBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
            case assign: {
                return ObjectFunctionBuiltinsFactory.ObjectAssignNodeGen.create(context, builtin, ObjectFunctionBuiltins.args().fixedArgs(1).varArgs().createArgumentNodes(context));
            }
            case values: {
                return ObjectFunctionBuiltinsFactory.ObjectValuesOrEntriesNodeGen.create(context, builtin, false, ObjectFunctionBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
            case entries: {
                return ObjectFunctionBuiltinsFactory.ObjectValuesOrEntriesNodeGen.create(context, builtin, true, ObjectFunctionBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
            case fromEntries: {
                return ObjectFunctionBuiltinsFactory.ObjectFromEntriesNodeGen.create(context, builtin, ObjectFunctionBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
            case hasOwn: {
                return ObjectFunctionBuiltinsFactory.ObjectHasOwnNodeGen.create(context, builtin, ObjectFunctionBuiltins.args().fixedArgs(2).createArgumentNodes(context));
            }
        }
        return null;
    }

    public static abstract class ObjectHasOwnNode
    extends ObjectPrototypeBuiltins.ObjectOperation {
        @Node.Child
        JSToPropertyKeyNode toPropertyKeyNode = JSToPropertyKeyNode.create();
        @Node.Child
        JSHasPropertyNode hasOwnPropertyNode = JSHasPropertyNode.create(true);

        public ObjectHasOwnNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected boolean hasOwn(Object o, Object p) {
            Object obj = this.toObject(o);
            Object key = this.toPropertyKeyNode.execute(p);
            return this.hasOwnPropertyNode.executeBoolean(obj, key);
        }
    }

    @ImportStatic(value={JSConfig.class})
    public static abstract class ObjectBindPropertiesNode
    extends ObjectPrototypeBuiltins.ObjectOperation {
        @Node.Child
        private EnumerableOwnPropertyNamesNode enumerableOwnPropertyNamesNode;
        private final JSClassProfile sourceProfile = JSClassProfile.create();
        private final JSClassProfile targetProfile = JSClassProfile.create();

        public ObjectBindPropertiesNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization(guards={"!isJSObject(target)"})
        protected JSDynamicObject bindPropertiesInvalidTarget(Object target, Object source) {
            throw Errors.createTypeErrorNotAnObject(target, this);
        }

        @Specialization(guards={"isJSObject(target)", "isJSDynamicObject(source)"})
        protected JSDynamicObject bindPropertiesDynamicObject(JSDynamicObject target, JSDynamicObject source) {
            JSDynamicObject sourceObject = this.toJSObject(source);
            boolean extensible = JSObject.isExtensible(target, this.targetProfile);
            JSClass sourceClass = this.sourceProfile.getJSClass(sourceObject);
            UnmodifiableArrayList<? extends Object> keys = this.enumerableOwnPropertyNames(sourceObject);
            int length = keys.size();
            for (int i = 0; i < length; ++i) {
                Object key = keys.get(i);
                if (JSObject.hasOwnProperty(target, key, this.targetProfile)) continue;
                if (!extensible) {
                    throw Errors.createTypeErrorNotExtensible(target, key);
                }
                PropertyDescriptor desc = JSObject.getOwnProperty(sourceObject, key, this.sourceProfile);
                if (desc.isAccessorDescriptor()) {
                    JSObject.defineOwnProperty(target, key, desc);
                    continue;
                }
                JSObjectUtil.defineProxyProperty(target, key, new BoundProperty(source, key, sourceClass), desc.getFlags());
            }
            return target;
        }

        @Specialization(guards={"isJSObject(target)"})
        protected JSDynamicObject bindProperties(JSDynamicObject target, Symbol source) {
            return this.bindPropertiesDynamicObject(target, this.toJSObject(source));
        }

        @Specialization(guards={"isJSObject(target)"})
        protected JSDynamicObject bindProperties(JSDynamicObject target, TruffleString source) {
            return this.bindPropertiesDynamicObject(target, this.toJSObject(source));
        }

        @Specialization(guards={"isJSObject(target)"})
        protected JSDynamicObject bindProperties(JSDynamicObject target, SafeInteger source) {
            return this.bindPropertiesDynamicObject(target, this.toJSObject(source));
        }

        @Specialization(guards={"isJSObject(target)"})
        protected JSDynamicObject bindProperties(JSDynamicObject target, BigInt source) {
            return this.bindPropertiesDynamicObject(target, this.toJSObject(source));
        }

        @Specialization(guards={"isJSObject(target)", "!isTruffleObject(source)"})
        protected JSDynamicObject bindProperties(JSDynamicObject target, Object source) {
            return this.bindPropertiesDynamicObject(target, this.toJSObject(source));
        }

        @Specialization(guards={"isJSObject(target)", "isForeignObject(source)"}, limit="InteropLibraryLimit")
        protected JSDynamicObject bindProperties(JSDynamicObject target, Object source, @CachedLibrary(value="source") InteropLibrary interop, @CachedLibrary(limit="InteropLibraryLimit") InteropLibrary members) {
            block13: {
                block9: {
                    if (!interop.hasMembers(source)) break block9;
                    try {
                        boolean extensible = JSObject.isExtensible(target, this.targetProfile);
                        boolean hostObject = this.getRealm().getEnv().isHostObject(source);
                        Object keysObj = interop.getMembers(source);
                        long size = members.getArraySize(keysObj);
                        int i = 0;
                        while ((long)i < size) {
                            block10: {
                                String beanProperty;
                                block12: {
                                    String stringKey;
                                    block11: {
                                        Object key = members.readArrayElement(keysObj, i);
                                        stringKey = Strings.interopAsString(key);
                                        TruffleString tStringKey = Strings.interopAsTruffleString(key);
                                        if (!JSObject.hasOwnProperty(target, tStringKey, this.targetProfile)) {
                                            if (!extensible) {
                                                throw Errors.createTypeErrorNotExtensible(target, key);
                                            }
                                            JSObjectUtil.defineProxyProperty(target, tStringKey, new ForeignBoundProperty(source, stringKey), JSAttributes.getDefault());
                                        }
                                        if (!hostObject) break block10;
                                        if (stringKey.length() <= 3 || stringKey.charAt(0) != 's' && stringKey.charAt(0) != 'g' || stringKey.charAt(1) != 'e' || stringKey.charAt(2) != 't' || !Boundaries.characterIsUpperCase(stringKey.charAt(3))) break block11;
                                        beanProperty = ObjectBindPropertiesNode.beanProperty(stringKey, 3);
                                        break block12;
                                    }
                                    if (stringKey.length() <= 2 || stringKey.charAt(0) != 'i' || stringKey.charAt(1) != 's' || !Boundaries.characterIsUpperCase(stringKey.charAt(2))) break block10;
                                    beanProperty = ObjectBindPropertiesNode.beanProperty(stringKey, 2);
                                }
                                TruffleString tStringBeanProperty = Strings.fromJavaString(beanProperty);
                                if (!JSObject.hasOwnProperty(target, tStringBeanProperty, this.targetProfile) && !interop.isMemberExisting(source, beanProperty)) {
                                    String isKey;
                                    String getKey = ObjectBindPropertiesNode.beanAccessor("get", beanProperty);
                                    String getter = interop.isMemberExisting(source, getKey) ? getKey : (interop.isMemberExisting(source, isKey = ObjectBindPropertiesNode.beanAccessor("is", beanProperty)) ? isKey : null);
                                    String setKey = ObjectBindPropertiesNode.beanAccessor("set", beanProperty);
                                    String setter = interop.isMemberExisting(source, setKey) ? setKey : null;
                                    JSObjectUtil.defineProxyProperty(target, tStringBeanProperty, new ForeignBoundBeanProperty(source, getter, setter), JSAttributes.getDefault());
                                }
                            }
                            ++i;
                        }
                    }
                    catch (InvalidArrayIndexException | UnsupportedMessageException interopException) {}
                    break block13;
                }
                throw Errors.createTypeErrorNotAnObject(target, this);
            }
            return target;
        }

        private UnmodifiableArrayList<? extends Object> enumerableOwnPropertyNames(JSDynamicObject obj) {
            if (this.enumerableOwnPropertyNamesNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.enumerableOwnPropertyNamesNode = this.insert(EnumerableOwnPropertyNamesNode.createKeys(this.getContext()));
            }
            return this.enumerableOwnPropertyNamesNode.execute(obj);
        }

        @CompilerDirectives.TruffleBoundary
        private static String beanProperty(String accessor, int prefixLength) {
            char c = accessor.charAt(prefixLength);
            return Character.toLowerCase(c) + accessor.substring(prefixLength + 1);
        }

        @CompilerDirectives.TruffleBoundary
        private static String beanAccessor(String prefix, String beanProperty) {
            return prefix + Character.toUpperCase(beanProperty.charAt(0)) + beanProperty.substring(1);
        }

        static final class ForeignBoundBeanProperty
        extends PropertyProxy {
            private final Object source;
            private final String getKey;
            private final String setKey;

            ForeignBoundBeanProperty(Object source, String getKey, String setKey) {
                assert (getKey != null || setKey != null);
                this.source = source;
                this.getKey = getKey;
                this.setKey = setKey;
            }

            @Override
            public Object get(JSDynamicObject store) {
                InteropLibrary library;
                if (this.getKey != null && (library = InteropLibrary.getFactory().getUncached(this.source)).isMemberInvocable(this.source, this.getKey)) {
                    try {
                        return JSRuntime.importValue(library.invokeMember(this.source, this.getKey, new Object[0]));
                    }
                    catch (ArityException | UnknownIdentifierException | UnsupportedMessageException | UnsupportedTypeException interopException) {
                        // empty catch block
                    }
                }
                return Undefined.instance;
            }

            @Override
            public boolean set(JSDynamicObject store, Object value2) {
                InteropLibrary library;
                if (this.setKey != null && (library = InteropLibrary.getFactory().getUncached(this.source)).isMemberInvocable(this.source, this.setKey)) {
                    try {
                        library.invokeMember(this.source, this.setKey, JSRuntime.exportValue(value2));
                        return true;
                    }
                    catch (ArityException | UnknownIdentifierException | UnsupportedMessageException | UnsupportedTypeException interopException) {
                        // empty catch block
                    }
                }
                return false;
            }
        }

        static final class ForeignBoundProperty
        extends PropertyProxy {
            private final Object source;
            private final String key;

            ForeignBoundProperty(Object source, String key) {
                this.source = source;
                this.key = key;
            }

            @Override
            public Object get(JSDynamicObject store) {
                InteropLibrary library = InteropLibrary.getFactory().getUncached(this.source);
                if (library.isMemberReadable(this.source, this.key)) {
                    try {
                        return JSRuntime.importValue(library.readMember(this.source, this.key));
                    }
                    catch (UnknownIdentifierException | UnsupportedMessageException interopException) {
                        // empty catch block
                    }
                }
                return Undefined.instance;
            }

            @Override
            public boolean set(JSDynamicObject store, Object value2) {
                InteropLibrary library = InteropLibrary.getFactory().getUncached(this.source);
                if (library.isMemberWritable(this.source, this.key)) {
                    try {
                        library.writeMember(this.source, this.key, JSRuntime.exportValue(value2));
                        return true;
                    }
                    catch (UnknownIdentifierException | UnsupportedMessageException | UnsupportedTypeException interopException) {
                        // empty catch block
                    }
                }
                return false;
            }
        }

        static final class BoundProperty
        extends PropertyProxy {
            private final JSDynamicObject source;
            private final Object key;
            private final JSClass sourceClass;

            BoundProperty(JSDynamicObject source, Object key, JSClass sourceClass) {
                this.source = source;
                this.key = key;
                this.sourceClass = sourceClass;
            }

            @Override
            public Object get(JSDynamicObject store) {
                return this.sourceClass.get(this.source, this.key);
            }

            @Override
            public boolean set(JSDynamicObject store, Object value2) {
                return this.sourceClass.set(this.source, this.key, value2, (Object)this.source, false, null);
            }
        }
    }

    public static abstract class ObjectFromEntriesNode
    extends ObjectPrototypeBuiltins.ObjectOperation {
        @Node.Child
        private RequireObjectCoercibleNode requireObjectCoercibleNode = RequireObjectCoercibleNode.create();
        @Node.Child
        private GetIteratorBaseNode getIteratorNode;
        @Node.Child
        private IteratorStepNode iteratorStepNode;
        @Node.Child
        private IteratorValueNode iteratorValueNode;
        @Node.Child
        private IsObjectNode isObjectNode = IsObjectNode.create();
        @Node.Child
        private IteratorCloseNode iteratorCloseNode;
        @Node.Child
        private JSToPropertyKeyNode toPropertyKeyNode = JSToPropertyKeyNode.create();
        @Node.Child
        private ReadElementNode readElementNode;
        private final BranchProfile errorBranch = BranchProfile.create();

        public ObjectFromEntriesNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
            this.getIteratorNode = GetIteratorBaseNode.create();
            this.iteratorStepNode = IteratorStepNode.create();
            this.iteratorValueNode = IteratorValueNode.create();
            this.readElementNode = ReadElementNode.create(context);
        }

        @Specialization
        protected JSDynamicObject entries(Object iterable) {
            this.requireObjectCoercibleNode.executeVoid(iterable);
            JSObject obj = JSOrdinary.create(this.getContext(), this.getRealm());
            return this.addEntriesFromIterable(obj, iterable);
        }

        private JSDynamicObject addEntriesFromIterable(JSDynamicObject target, Object iterable) {
            assert (!JSRuntime.isNullOrUndefined(target));
            IteratorRecord iteratorRecord = this.getIteratorNode.execute(iterable);
            try {
                while (true) {
                    Object next;
                    if ((next = this.iteratorStepNode.execute(iteratorRecord)) == Boolean.FALSE) {
                        return target;
                    }
                    Object nextItem = this.iteratorValueNode.execute(next);
                    if (!this.isObjectNode.executeBoolean(nextItem)) {
                        this.errorBranch.enter();
                        throw Errors.createTypeErrorIteratorResultNotObject(nextItem, this);
                    }
                    Object k = this.readElementNode.executeWithTargetAndIndex(nextItem, 0);
                    Object v = this.readElementNode.executeWithTargetAndIndex(nextItem, 1);
                    this.createDataPropertyOnObject(target, k, v);
                }
            }
            catch (AbstractTruffleException ex) {
                this.errorBranch.enter();
                this.iteratorCloseAbrupt(iteratorRecord.getIterator());
                throw ex;
            }
        }

        private void createDataPropertyOnObject(JSDynamicObject thisObject, Object key, Object value2) {
            assert (JSRuntime.isObject(thisObject));
            Object propertyKey = this.toPropertyKeyNode.execute(key);
            JSRuntime.createDataPropertyOrThrow(thisObject, propertyKey, value2);
        }

        private void iteratorCloseAbrupt(JSDynamicObject iterator) {
            if (this.iteratorCloseNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.iteratorCloseNode = this.insert(IteratorCloseNode.create(this.getContext()));
            }
            this.iteratorCloseNode.executeAbrupt(iterator);
        }
    }

    @ImportStatic(value={JSConfig.class})
    public static abstract class ObjectValuesOrEntriesNode
    extends ObjectPrototypeBuiltins.ObjectOperation {
        protected final boolean entries;
        @Node.Child
        private EnumerableOwnPropertyNamesNode enumerableOwnPropertyNamesNode;
        private final ConditionProfile hasElements = ConditionProfile.createBinaryProfile();

        public ObjectValuesOrEntriesNode(JSContext context, JSBuiltin builtin, boolean entries) {
            super(context, builtin);
            this.entries = entries;
        }

        protected abstract JSDynamicObject executeEvaluated(Object var1);

        @Specialization(guards={"isJSObject(obj)"})
        protected JSDynamicObject valuesOrEntriesJSObject(JSDynamicObject obj) {
            return this.valuesOrEntries(obj);
        }

        private JSDynamicObject valuesOrEntries(Object obj) {
            assert (JSObject.isJSObject(obj) || JSRuntime.isForeignObject(obj));
            UnmodifiableArrayList<? extends Object> list = this.enumerableOwnPropertyNames(obj);
            int len = list.size();
            JSRealm realm = this.getRealm();
            if (this.hasElements.profile(len > 0)) {
                return JSArray.createConstant(this.getContext(), realm, list.toArray());
            }
            return JSArray.createEmptyChecked(this.getContext(), realm, 0L);
        }

        protected UnmodifiableArrayList<? extends Object> enumerableOwnPropertyNames(Object obj) {
            if (this.enumerableOwnPropertyNamesNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.enumerableOwnPropertyNamesNode = this.insert(this.entries ? EnumerableOwnPropertyNamesNode.createKeysValues(this.getContext()) : EnumerableOwnPropertyNamesNode.createValues(this.getContext()));
            }
            return this.enumerableOwnPropertyNamesNode.execute(obj);
        }

        @Specialization(guards={"isForeignObject(thisObj)"})
        protected JSDynamicObject valuesOrEntriesForeign(Object thisObj) {
            return this.valuesOrEntries(thisObj);
        }

        @Specialization(guards={"!isJSObject(obj)", "!isForeignObject(obj)"})
        protected JSDynamicObject valuesOrEntriesGeneric(Object obj, @Cached(value="createRecursive()") ObjectValuesOrEntriesNode recursive) {
            Object thisObj = this.toObject(obj);
            return recursive.executeEvaluated(thisObj);
        }

        ObjectValuesOrEntriesNode createRecursive() {
            return ObjectFunctionBuiltinsFactory.ObjectValuesOrEntriesNodeGen.create(this.getContext(), this.getBuiltin(), this.entries, new JavaScriptNode[0]);
        }
    }

    @ImportStatic(value={JSConfig.class})
    static abstract class AssignPropertiesNode
    extends JavaScriptBaseNode {
        protected final JSContext context;

        protected AssignPropertiesNode(JSContext context) {
            this.context = context;
        }

        abstract void executeVoid(Object var1, Object var2, WriteElementNode var3);

        @Specialization(guards={"isJSObject(from)"})
        protected static void copyPropertiesFromJSObject(Object to, JSDynamicObject from, WriteElementNode write, @Cached(value="create(context)") ReadElementNode read2, @Cached(value="create(false)") JSGetOwnPropertyNode getOwnProperty, @Cached ListSizeNode listSize, @Cached ListGetNode listGet, @Cached JSClassProfile classProfile) {
            List<Object> ownPropertyKeys = JSObject.ownPropertyKeys(from, classProfile);
            int size = listSize.execute(ownPropertyKeys);
            for (int i = 0; i < size; ++i) {
                Object nextKey = listGet.execute(ownPropertyKeys, i);
                assert (JSRuntime.isPropertyKey(nextKey));
                PropertyDescriptor desc = getOwnProperty.execute(from, nextKey);
                if (desc == null || !desc.getEnumerable()) continue;
                Object propValue = read2.executeWithTargetAndIndex((Object)from, nextKey);
                write.executeWithTargetAndIndexAndValue(to, nextKey, propValue);
            }
        }

        @Specialization(guards={"!isJSObject(from)"}, limit="InteropLibraryLimit")
        protected final void doObject(Object to, Object from, WriteElementNode write, @CachedLibrary(value="from") InteropLibrary fromInterop, @CachedLibrary(limit="InteropLibraryLimit") InteropLibrary keysInterop, @CachedLibrary(limit="InteropLibraryLimit") InteropLibrary stringInterop) {
            if (fromInterop.isNull(from)) {
                return;
            }
            try {
                Object members = fromInterop.getMembers(from);
                long length = JSInteropUtil.getArraySize(members, keysInterop, this);
                for (long i = 0L; i < length; ++i) {
                    Object key = keysInterop.readArrayElement(members, i);
                    String stringKey = Strings.interopAsString(stringInterop, key);
                    Object value2 = fromInterop.readMember(from, stringKey);
                    write.executeWithTargetAndIndexAndValue(to, Strings.fromJavaString(stringKey), value2);
                }
            }
            catch (InvalidArrayIndexException | UnknownIdentifierException | UnsupportedMessageException e) {
                throw Errors.createTypeErrorInteropException(from, e, "CopyDataProperties", this);
            }
        }
    }

    public static abstract class ObjectAssignNode
    extends JSBuiltinNode {
        protected static final boolean STRICT = true;

        public ObjectAssignNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected Object assign(Object target, Object[] sources, @Cached(value="createToObject(getContext())") JSToObjectNode toObjectNode, @Cached(value="create(getContext(), STRICT)") WriteElementNode write, @Cached(value="create(getContext())") AssignPropertiesNode assignProperties) {
            Object to = toObjectNode.execute(target);
            if (sources.length == 0) {
                return to;
            }
            for (Object o : sources) {
                if (JSRuntime.isNullOrUndefined(o)) continue;
                Object from = toObjectNode.execute(o);
                assignProperties.executeVoid(to, from, write);
            }
            return to;
        }
    }

    public static abstract class ObjectIsNode
    extends ObjectPrototypeBuiltins.ObjectOperation {
        public ObjectIsNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected boolean isInt(int a, int b) {
            return a == b;
        }

        @Specialization
        protected boolean isDouble(double a, double b) {
            if (a == 0.0 && b == 0.0) {
                return JSRuntime.isNegativeZero(a) == JSRuntime.isNegativeZero(b);
            }
            if (Double.isNaN(a)) {
                return Double.isNaN(b);
            }
            return a == b;
        }

        @Specialization(guards={"isNumberNumber(a,b)"})
        protected boolean isNumberNumber(Number a, Number b, @Cached(value="createSameValue()") JSIdenticalNode doIdenticalNode) {
            return doIdenticalNode.executeBoolean(JSRuntime.doubleValue(a), JSRuntime.doubleValue(b));
        }

        @Specialization(guards={"!isNumberNumber(a, b)"})
        protected boolean isObject(Object a, Object b, @Cached(value="createSameValue()") JSIdenticalNode doIdenticalNode) {
            return doIdenticalNode.executeBoolean(a, b);
        }

        protected boolean isNumberNumber(Object a, Object b) {
            return a instanceof Number && b instanceof Number;
        }
    }

    public static abstract class ObjectSetPrototypeOfNode
    extends ObjectPrototypeBuiltins.ObjectOperation {
        private final BranchProfile errorBranch = BranchProfile.create();
        private final JSClassProfile classProfile = JSClassProfile.create();

        public ObjectSetPrototypeOfNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization(guards={"isValidPrototype(newProto)"})
        final Object setPrototypeOfJSObject(JSObject object, JSDynamicObject newProto) {
            if (!JSObject.setPrototype(object, newProto, this.classProfile)) {
                this.errorBranch.enter();
                throw Errors.createTypeError("setPrototype failed");
            }
            return object;
        }

        @Specialization(guards={"!isValidPrototype(newProto)"})
        static Object setPrototypeOfJSObjectToInvalidNewProto(JSObject object, Object newProto) {
            throw Errors.createTypeErrorInvalidPrototype(newProto);
        }

        @Specialization(guards={"isNullOrUndefined(object)"})
        final Object setPrototypeOfNonObjectCoercible(Object object, Object newProto) {
            throw this.createTypeErrorCalledOnNonObject(object);
        }

        @Specialization(guards={"!isJSObject(object)", "!isNullOrUndefined(object)", "!isForeignObject(object)"})
        static Object setPrototypeOfValue(Object object, Object newProto) {
            return object;
        }

        @Specialization(guards={"isForeignObject(object)"})
        final Object setPrototypeOfForeignObject(Object object, Object newProto) {
            throw this.createTypeErrorCalledOnNonObject(object);
        }
    }

    @ImportStatic(value={JSConfig.class})
    public static abstract class ObjectKeysNode
    extends ObjectPrototypeBuiltins.ObjectOperation {
        @Node.Child
        private EnumerableOwnPropertyNamesNode enumerableOwnPropertyNamesNode;
        private final ConditionProfile hasElements = ConditionProfile.createBinaryProfile();

        public ObjectKeysNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        private JSDynamicObject keys(Object obj) {
            assert (JSObject.isJSObject(obj) || JSRuntime.isForeignObject(obj));
            UnmodifiableArrayList<? extends Object> keyList = this.enumerableOwnPropertyNames(obj);
            int len = keyList.size();
            JSRealm realm = this.getRealm();
            if (this.hasElements.profile(len > 0)) {
                assert (keyList.stream().allMatch(Strings::isTString));
                return JSArray.createConstant(this.getContext(), realm, keyList.toArray());
            }
            return JSArray.createEmptyChecked(this.getContext(), realm, 0L);
        }

        @Specialization(guards={"isJSDynamicObject(thisObj)"})
        protected JSDynamicObject keysDynamicObject(JSDynamicObject thisObj) {
            return this.keys(this.toOrAsJSObject(thisObj));
        }

        @Specialization
        protected JSDynamicObject keysSymbol(Symbol symbol) {
            return this.keys(this.toOrAsJSObject(symbol));
        }

        @Specialization
        protected JSDynamicObject keysString(TruffleString string) {
            return this.keys(this.toOrAsJSObject(string));
        }

        @Specialization
        protected JSDynamicObject keysSafeInt(SafeInteger largeInteger) {
            return this.keys(this.toOrAsJSObject(largeInteger));
        }

        @Specialization
        protected JSDynamicObject keysBigInt(BigInt bigInt) {
            return this.keys(this.toOrAsJSObject(bigInt));
        }

        @Specialization(guards={"!isTruffleObject(thisObj)"})
        protected JSDynamicObject keysOther(Object thisObj) {
            return this.keys(this.toOrAsJSObject(thisObj));
        }

        @Specialization(guards={"isForeignObject(obj)"})
        protected JSDynamicObject keysForeign(Object obj) {
            return this.keys(obj);
        }

        private UnmodifiableArrayList<? extends Object> enumerableOwnPropertyNames(Object obj) {
            if (this.enumerableOwnPropertyNamesNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.enumerableOwnPropertyNamesNode = this.insert(EnumerableOwnPropertyNamesNode.createKeys(this.getContext()));
            }
            return this.enumerableOwnPropertyNamesNode.execute(obj);
        }
    }

    public static abstract class ObjectSetIntegrityLevelNode
    extends ObjectPrototypeBuiltins.ObjectOperation {
        private final boolean freeze;
        private final ConditionProfile isObject = ConditionProfile.createBinaryProfile();

        public ObjectSetIntegrityLevelNode(JSContext context, JSBuiltin builtin, boolean freeze) {
            super(context, builtin);
            this.freeze = freeze;
        }

        @Specialization
        protected Object setIntegrityLevel(Object thisObj) {
            if (this.isObject.profile(JSRuntime.isObject(thisObj))) {
                JSObject.setIntegrityLevel((JSDynamicObject)thisObj, this.freeze, true);
            } else if (this.getContext().getEcmaScriptVersion() < 6) {
                throw this.createTypeErrorCalledOnNonObject(thisObj);
            }
            return thisObj;
        }
    }

    public static abstract class ObjectTestIntegrityLevelNode
    extends ObjectPrototypeBuiltins.ObjectOperation {
        private final boolean frozen;
        private final ConditionProfile isObject = ConditionProfile.createBinaryProfile();

        public ObjectTestIntegrityLevelNode(JSContext context, JSBuiltin builtin, boolean frozen) {
            super(context, builtin);
            this.frozen = frozen;
        }

        @Specialization
        protected boolean testIntegrityLevel(Object thisObj) {
            if (this.isObject.profile(JSRuntime.isObject(thisObj))) {
                return JSObject.testIntegrityLevel((JSDynamicObject)thisObj, this.frozen);
            }
            if (this.getContext().getEcmaScriptVersion() < 6) {
                throw this.createTypeErrorCalledOnNonObject(thisObj);
            }
            return true;
        }
    }

    public static abstract class ObjectPreventExtensionsNode
    extends ObjectPrototypeBuiltins.ObjectOperation {
        public ObjectPreventExtensionsNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization(guards={"isJSObject(thisObj)"})
        protected JSDynamicObject preventExtensionsObject(JSDynamicObject thisObj) {
            JSObject.preventExtensions(thisObj, true);
            return thisObj;
        }

        @Specialization(guards={"!isJSObject(thisObj)"})
        protected Object preventExtensionsNonObject(Object thisObj) {
            if (this.getContext().getEcmaScriptVersion() < 6) {
                throw this.createTypeErrorCalledOnNonObject(thisObj);
            }
            return thisObj;
        }
    }

    public static abstract class ObjectIsExtensibleNode
    extends ObjectPrototypeBuiltins.ObjectOperation {
        public ObjectIsExtensibleNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization(guards={"isJSObject(thisObj)"})
        protected boolean isExtensibleObject(JSDynamicObject thisObj, @Cached IsExtensibleNode isExtensibleNode) {
            return isExtensibleNode.executeBoolean(thisObj);
        }

        @Specialization(guards={"!isJSObject(thisObj)"})
        protected boolean isExtensibleNonObject(Object thisObj) {
            if (this.getContext().getEcmaScriptVersion() < 6) {
                throw this.createTypeErrorCalledOnNonObject(thisObj);
            }
            return false;
        }
    }

    public static abstract class ObjectDefinePropertiesNode
    extends ObjectDefineOperation {
        public ObjectDefinePropertiesNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization(guards={"isJSObject(thisObj)", "isJSObject(properties)"})
        protected JSDynamicObject definePropertiesObjectObject(JSDynamicObject thisObj, JSDynamicObject properties2) {
            return this.intlDefineProperties(thisObj, properties2);
        }

        @Specialization(replaces={"definePropertiesObjectObject"})
        protected JSDynamicObject definePropertiesGeneric(Object thisObj, Object properties2) {
            JSDynamicObject object = this.asJSObject(thisObj);
            return this.intlDefineProperties(object, this.toJSObject(properties2));
        }
    }

    public static abstract class ObjectDefinePropertyNode
    extends ObjectDefineOperation {
        @Node.Child
        private JSToPropertyKeyNode toPropertyKeyNode = JSToPropertyKeyNode.create();

        public ObjectDefinePropertyNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization(guards={"isJSObject(thisObj)"})
        protected JSDynamicObject definePropertyJSObjectTString(JSDynamicObject thisObj, TruffleString property, Object attributes) {
            PropertyDescriptor desc = this.toPropertyDescriptor(attributes);
            JSRuntime.definePropertyOrThrow(thisObj, property, desc);
            return thisObj;
        }

        @Specialization(replaces={"definePropertyJSObjectTString"})
        protected JSDynamicObject definePropertyGeneric(Object thisObj, Object property, Object attributes) {
            JSDynamicObject object = this.asJSObject(thisObj);
            PropertyDescriptor desc = this.toPropertyDescriptor(attributes);
            Object propertyKey = this.toPropertyKeyNode.execute(property);
            JSRuntime.definePropertyOrThrow(object, propertyKey, desc);
            return object;
        }

        @Override
        protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
            return ObjectFunctionBuiltinsFactory.ObjectDefinePropertyNodeGen.create(this.getContext(), this.getBuiltin(), ObjectDefinePropertyNode.cloneUninitialized(this.getArguments(), materializedTags));
        }
    }

    @ImportStatic(value={JSConfig.class})
    public static abstract class ObjectCreateNode
    extends ObjectDefineOperation {
        @Node.Child
        private CreateObjectNode.CreateObjectWithPrototypeNode objectCreateNode;
        private final BranchProfile needDefineProperties = BranchProfile.create();

        public ObjectCreateNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization(guards={"isJSNull(prototype)"})
        protected JSDynamicObject createPrototypeNull(Object prototype, Object properties2) {
            JSObject ret = JSOrdinary.createWithNullPrototype(this.getContext());
            return this.objectDefineProperties(ret, properties2);
        }

        @Specialization(guards={"!isJSNull(prototype)", "!isJSObject(prototype)"}, limit="InteropLibraryLimit")
        protected JSDynamicObject createForeignNullOrInvalidPrototype(Object prototype, Object properties2, @CachedLibrary(value="prototype") InteropLibrary interop, @Cached(value="createBinaryProfile()") ConditionProfile isNull) {
            assert (prototype != null);
            if (isNull.profile(prototype != Undefined.instance && interop.isNull(prototype))) {
                return this.createPrototypeNull(Null.instance, properties2);
            }
            throw Errors.createTypeErrorInvalidPrototype(prototype);
        }

        @Specialization(guards={"isJSObject(prototype)", "isJSObject(properties)"})
        protected JSDynamicObject createObjectObject(JSDynamicObject prototype, JSDynamicObject properties2) {
            JSDynamicObject ret = this.createObjectWithPrototype(prototype);
            this.intlDefineProperties(ret, properties2);
            return ret;
        }

        @Specialization(guards={"isJSObject(prototype)", "!isJSNull(properties)"})
        protected JSDynamicObject createObjectNotNull(JSDynamicObject prototype, Object properties2) {
            JSDynamicObject ret = this.createObjectWithPrototype(prototype);
            return this.objectDefineProperties(ret, properties2);
        }

        @Specialization(guards={"isJSObject(prototype)", "isJSNull(properties)"})
        protected JSDynamicObject createObjectNull(JSDynamicObject prototype, Object properties2) {
            throw Errors.createTypeErrorNotObjectCoercible(properties2, null, this.getContext());
        }

        private JSDynamicObject objectDefineProperties(JSDynamicObject ret, Object properties2) {
            if (properties2 != Undefined.instance) {
                this.needDefineProperties.enter();
                this.intlDefineProperties(ret, this.toJSObject(properties2));
            }
            return ret;
        }

        private JSDynamicObject createObjectWithPrototype(JSDynamicObject prototype) {
            if (this.objectCreateNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.objectCreateNode = this.insert(CreateObjectNode.createOrdinaryWithPrototype(this.getContext()));
            }
            return this.objectCreateNode.execute(prototype);
        }
    }

    protected static abstract class ObjectDefineOperation
    extends ObjectPrototypeBuiltins.ObjectOperation {
        @Node.Child
        private ToPropertyDescriptorNode toPropertyDescriptorNode;

        public ObjectDefineOperation(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        protected PropertyDescriptor toPropertyDescriptor(Object target) {
            if (this.toPropertyDescriptorNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.toPropertyDescriptorNode = this.insert(ToPropertyDescriptorNode.create(this.getContext()));
            }
            return (PropertyDescriptor)this.toPropertyDescriptorNode.execute(target);
        }

        @CompilerDirectives.TruffleBoundary
        protected JSDynamicObject intlDefineProperties(JSDynamicObject obj, JSDynamicObject descs) {
            ArrayList descriptors = new ArrayList();
            JSClass descsClass = JSObject.getJSClass(descs);
            for (Object key : descsClass.ownPropertyKeys(descs)) {
                PropertyDescriptor keyDesc = descsClass.getOwnProperty(descs, key);
                if (keyDesc == null || !keyDesc.getEnumerable()) continue;
                PropertyDescriptor desc = this.toPropertyDescriptor(descsClass.get(descs, key));
                Boundaries.listAdd(descriptors, new Pair<Object, PropertyDescriptor>(key, desc));
            }
            for (Pair descPair : descriptors) {
                JSRuntime.definePropertyOrThrow(obj, descPair.getFirst(), (PropertyDescriptor)descPair.getSecond());
            }
            return obj;
        }
    }

    @ImportStatic(value={JSConfig.class})
    public static abstract class ObjectGetOwnPropertyNamesOrSymbolsNode
    extends ObjectPrototypeBuiltins.ObjectOperation {
        protected final boolean symbols;

        public ObjectGetOwnPropertyNamesOrSymbolsNode(JSContext context, JSBuiltin builtin, boolean symbols) {
            super(context, builtin);
            this.symbols = symbols;
        }

        @Specialization(guards={"isJSObject(thisObj)"})
        protected JSDynamicObject getJSObject(JSDynamicObject thisObj, @Cached @Cached.Shared(value="jsclassProfile") JSClassProfile jsclassProfile, @Cached @Cached.Shared(value="listSize") ListSizeNode listSize) {
            List<Object> ownPropertyKeys = jsclassProfile.getJSClass(thisObj).getOwnPropertyKeys(thisObj, !this.symbols, this.symbols);
            return JSArray.createLazyArray(this.getContext(), this.getRealm(), ownPropertyKeys, listSize.execute(ownPropertyKeys));
        }

        @Specialization(guards={"!isJSObject(thisObj)", "!isForeignObject(thisObj)"})
        protected JSDynamicObject getDefault(Object thisObj, @Cached @Cached.Shared(value="jsclassProfile") JSClassProfile jsclassProfile, @Cached @Cached.Shared(value="listSize") ListSizeNode listSize) {
            JSDynamicObject object = this.toOrAsJSObject(thisObj);
            return this.getJSObject(object, jsclassProfile, listSize);
        }

        @Specialization(guards={"isForeignObject(thisObj)", "symbols"})
        protected JSDynamicObject getForeignObjectSymbols(Object thisObj) {
            return JSArray.createConstantEmptyArray(this.getContext(), this.getRealm());
        }

        @Specialization(guards={"isForeignObject(thisObj)", "!symbols"})
        protected JSDynamicObject getForeignObjectNames(Object thisObj, @Cached(value="createKeys(getContext())") EnumerableOwnPropertyNamesNode enumerableOwnPropertyNamesNode, @Cached ConditionProfile hasElements) {
            UnmodifiableArrayList<? extends Object> keyList = enumerableOwnPropertyNamesNode.execute(thisObj);
            int len = keyList.size();
            JSRealm realm = this.getRealm();
            if (hasElements.profile(len > 0)) {
                assert (keyList.stream().allMatch(Strings::isTString));
                return JSArray.createConstant(this.getContext(), realm, keyList.toArray());
            }
            return JSArray.createEmptyChecked(this.getContext(), realm, 0L);
        }
    }

    @ImportStatic(value={JSConfig.class})
    public static abstract class ObjectGetOwnPropertyDescriptorsNode
    extends ObjectPrototypeBuiltins.ObjectOperation {
        @Node.Child
        private FromPropertyDescriptorNode fromPropertyDescriptorNode = FromPropertyDescriptorNode.create();
        @Node.Child
        private DynamicObjectLibrary putPropDescNode = DynamicObjectLibrary.getFactory().createDispatched(5);

        public ObjectGetOwnPropertyDescriptorsNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        protected abstract JSDynamicObject executeEvaluated(Object var1);

        @Specialization(guards={"isJSObject(thisObj)"})
        protected JSDynamicObject getJSObject(JSDynamicObject thisObj, @Cached JSGetOwnPropertyNode getOwnPropertyNode, @Cached ListSizeNode listSize, @Cached ListGetNode listGet, @Cached JSClassProfile classProfile) {
            JSObject retObj = JSOrdinary.create(this.getContext(), this.getRealm());
            List<Object> ownPropertyKeys = JSObject.ownPropertyKeys(thisObj, classProfile);
            int size = listSize.execute(ownPropertyKeys);
            for (int i = 0; i < size; ++i) {
                Object key = listGet.execute(ownPropertyKeys, i);
                assert (JSRuntime.isPropertyKey(key));
                PropertyDescriptor desc = getOwnPropertyNode.execute(thisObj, key);
                if (desc == null) continue;
                JSDynamicObject propDesc = this.fromPropertyDescriptorNode.execute(desc, this.getContext());
                Properties.putWithFlags(this.putPropDescNode, retObj, key, propDesc, JSAttributes.configurableEnumerableWritable());
            }
            return retObj;
        }

        @Specialization(guards={"isForeignObject(thisObj)"}, limit="InteropLibraryLimit")
        protected JSDynamicObject getForeignObject(Object thisObj, @CachedLibrary(value="thisObj") InteropLibrary interop, @CachedLibrary(limit="InteropLibraryLimit") InteropLibrary members, @Cached(value="create()") ImportValueNode toJSType, @Cached BranchProfile errorBranch) {
            JSObject result = JSOrdinary.create(this.getContext(), this.getRealm());
            try {
                if (interop.hasMembers(thisObj)) {
                    Object keysObj = interop.getMembers(thisObj);
                    long size = members.getArraySize(keysObj);
                    if (size < 0L || size >= Integer.MAX_VALUE) {
                        errorBranch.enter();
                        throw Errors.createRangeErrorInvalidArrayLength();
                    }
                    int i = 0;
                    while ((long)i < size) {
                        String member = (String)members.readArrayElement(keysObj, i);
                        PropertyDescriptor desc = JSInteropUtil.getExistingMemberProperty(thisObj, member, interop, toJSType);
                        if (desc != null) {
                            JSDynamicObject propDesc = this.fromPropertyDescriptorNode.execute(desc, this.getContext());
                            Properties.putWithFlags(this.putPropDescNode, result, Strings.fromJavaString(member), propDesc, JSAttributes.configurableEnumerableWritable());
                        }
                        ++i;
                    }
                }
                if (interop.hasArrayElements(thisObj)) {
                    long size = interop.getArraySize(thisObj);
                    if (size < 0L || size >= Integer.MAX_VALUE) {
                        errorBranch.enter();
                        throw Errors.createRangeErrorInvalidArrayLength();
                    }
                    for (long i = 0L; i < size; ++i) {
                        PropertyDescriptor desc = JSInteropUtil.getArrayElementProperty(thisObj, i, interop, toJSType);
                        if (desc == null) continue;
                        JSDynamicObject propDesc = this.fromPropertyDescriptorNode.execute(desc, this.getContext());
                        Properties.putWithFlags(this.putPropDescNode, result, Strings.fromLong(i), propDesc, JSAttributes.configurableEnumerableWritable());
                    }
                }
            }
            catch (InteropException interopException) {
                // empty catch block
            }
            return result;
        }

        @Specialization(guards={"!isJSObject(thisObj)", "!isForeignObject(thisObj)"})
        protected JSDynamicObject getDefault(Object thisObj, @Cached(value="createRecursive()") ObjectGetOwnPropertyDescriptorsNode recursive) {
            Object object = this.toObject(thisObj);
            return recursive.executeEvaluated(object);
        }

        ObjectGetOwnPropertyDescriptorsNode createRecursive() {
            return ObjectFunctionBuiltinsFactory.ObjectGetOwnPropertyDescriptorsNodeGen.create(this.getContext(), this.getBuiltin(), new JavaScriptNode[0]);
        }
    }

    @ImportStatic(value={JSConfig.class})
    public static abstract class ObjectGetOwnPropertyDescriptorNode
    extends ObjectPrototypeBuiltins.ObjectOperation {
        @Node.Child
        private JSToPropertyKeyNode toPropertyKeyNode = JSToPropertyKeyNode.create();
        @Node.Child
        private JSGetOwnPropertyNode getOwnPropertyNode = JSGetOwnPropertyNode.create();
        @Node.Child
        private FromPropertyDescriptorNode fromPropertyDescriptorNode = FromPropertyDescriptorNode.create();

        public ObjectGetOwnPropertyDescriptorNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization(guards={"isJSObject(thisObj)"})
        protected JSDynamicObject getJSObject(JSDynamicObject thisObj, Object property) {
            Object propertyKey = this.toPropertyKeyNode.execute(property);
            PropertyDescriptor desc = this.getOwnPropertyNode.execute(thisObj, propertyKey);
            return this.fromPropertyDescriptorNode.execute(desc, this.getContext());
        }

        @Specialization(guards={"isForeignObject(thisObj)"}, limit="InteropLibraryLimit")
        protected JSDynamicObject getForeignObject(Object thisObj, Object property, @CachedLibrary(value="thisObj") InteropLibrary interop, @Cached(value="create()") ImportValueNode toJSType, @Cached TruffleString.ReadCharUTF16Node charAtNode) {
            PropertyDescriptor desc;
            Object propertyKey = this.toPropertyKeyNode.execute(property);
            if (Strings.isTString(propertyKey) && (desc = JSInteropUtil.getOwnProperty(thisObj, (TruffleString)propertyKey, interop, toJSType, charAtNode)) != null) {
                return this.fromPropertyDescriptorNode.execute(desc, this.getContext());
            }
            return Undefined.instance;
        }

        @Specialization(guards={"!isJSObject(thisObj)", "!isForeignObject(thisObj)"})
        protected JSDynamicObject getDefault(Object thisObj, Object property) {
            Object object = this.toObject(thisObj);
            assert (JSDynamicObject.isJSDynamicObject(object));
            return this.getJSObject((JSDynamicObject)object, property);
        }
    }

    public static abstract class ObjectGetPrototypeOfNode
    extends ObjectPrototypeBuiltins.ObjectOperation {
        @Node.Child
        private ForeignObjectPrototypeNode foreignObjectPrototypeNode;

        public ObjectGetPrototypeOfNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization(guards={"!isJSObject(object)"})
        protected JSDynamicObject getPrototypeOfNonObject(Object object, @Cached(value="createBinaryProfile()") ConditionProfile isForeignProfile) {
            if (this.getContext().getEcmaScriptVersion() < 6) {
                if (JSRuntime.isJSPrimitive(object)) {
                    throw Errors.createTypeErrorNotAnObject(object);
                }
                return Null.instance;
            }
            if (isForeignProfile.profile(JSRuntime.isForeignObject(object))) {
                if (InteropLibrary.getUncached(object).isNull(object)) {
                    throw Errors.createTypeErrorNotAnObject(object);
                }
                if (this.getContext().getContextOptions().hasForeignObjectPrototype()) {
                    return this.getForeignObjectPrototype(object);
                }
                return Null.instance;
            }
            assert (JSRuntime.isJSPrimitive(object));
            Object tobject = this.toObject(object);
            return JSObject.getPrototype((JSDynamicObject)tobject);
        }

        private JSDynamicObject getForeignObjectPrototype(Object truffleObject) {
            assert (JSRuntime.isForeignObject(truffleObject));
            if (this.foreignObjectPrototypeNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.foreignObjectPrototypeNode = this.insert(ForeignObjectPrototypeNode.create());
            }
            return this.foreignObjectPrototypeNode.execute(truffleObject);
        }

        @Specialization(guards={"isJSObject(object)"})
        protected JSDynamicObject getPrototypeOfJSObject(JSDynamicObject object, @Cached(value="create()") GetPrototypeNode getPrototypeNode) {
            return getPrototypeNode.execute(object);
        }
    }

    public static final class ObjectFunctionNashornCompatBuiltins
    extends JSBuiltinsContainer.SwitchEnum<ObjectNashornCompat> {
        protected ObjectFunctionNashornCompatBuiltins() {
            super(ObjectNashornCompat.class);
        }

        @Override
        protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, ObjectNashornCompat builtinEnum) {
            switch (builtinEnum) {
                case bindProperties: {
                    return ObjectFunctionBuiltinsFactory.ObjectBindPropertiesNodeGen.create(context, builtin, ObjectFunctionNashornCompatBuiltins.args().fixedArgs(2).createArgumentNodes(context));
                }
            }
            return null;
        }

        public static enum ObjectNashornCompat implements BuiltinEnum<ObjectNashornCompat>
        {
            bindProperties(2);

            private final int length;

            private ObjectNashornCompat(int length) {
                this.length = length;
            }

            @Override
            public int getLength() {
                return this.length;
            }
        }
    }

    public static enum ObjectFunction implements BuiltinEnum<ObjectFunction>
    {
        create(2),
        defineProperties(2),
        defineProperty(3),
        freeze(1),
        getOwnPropertyDescriptor(2),
        getOwnPropertyNames(1),
        getPrototypeOf(1),
        isExtensible(1),
        isFrozen(1),
        isSealed(1),
        keys(1),
        preventExtensions(1),
        seal(1),
        setPrototypeOf(2),
        is(2),
        getOwnPropertySymbols(1),
        assign(2),
        getOwnPropertyDescriptors(1),
        values(1),
        entries(1),
        fromEntries(1),
        hasOwn(2);

        private final int length;

        private ObjectFunction(int length) {
            this.length = length;
        }

        @Override
        public int getLength() {
            return this.length;
        }

        @Override
        public int getECMAScriptVersion() {
            if (EnumSet.of(is, getOwnPropertySymbols, assign).contains(this)) {
                return 6;
            }
            if (EnumSet.of(getOwnPropertyDescriptors, values, entries).contains(this)) {
                return 8;
            }
            if (this == fromEntries) {
                return 10;
            }
            if (this == hasOwn) {
                return 13;
            }
            return BuiltinEnum.super.getECMAScriptVersion();
        }
    }
}

