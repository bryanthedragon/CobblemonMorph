
package com.oracle.truffle.js.decorators;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.MaterializedFrame;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import com.oracle.truffle.api.object.HiddenKey;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.decorators.CreateDecoratorContextObjectNodeGen;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.access.CreateObjectNode;
import com.oracle.truffle.js.nodes.access.HasPropertyCacheNode;
import com.oracle.truffle.js.nodes.access.JSReadFrameSlotNode;
import com.oracle.truffle.js.nodes.access.PrivateBrandCheckNode;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.access.PropertySetNode;
import com.oracle.truffle.js.nodes.access.ReadElementNode;
import com.oracle.truffle.js.nodes.access.WriteElementNode;
import com.oracle.truffle.js.nodes.function.ClassElementDefinitionRecord;
import com.oracle.truffle.js.runtime.Boundaries;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSErrorType;
import com.oracle.truffle.js.runtime.JSException;
import com.oracle.truffle.js.runtime.JSFrameUtil;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.JavaScriptRootNode;
import com.oracle.truffle.js.runtime.Properties;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.builtins.JSFunctionData;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.objects.Accessor;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.List;

@ImportStatic(value={Strings.class, ClassElementDefinitionRecord.class})
public abstract class CreateDecoratorContextObjectNode
extends JavaScriptBaseNode {
    private static final TruffleString FIELD_KIND = Strings.constant("field");
    private static final TruffleString GETTER_KIND = Strings.constant("getter");
    private static final TruffleString SETTER_KIND = Strings.constant("setter");
    private static final TruffleString METHOD_KIND = Strings.constant("method");
    private static final TruffleString CLASS_KIND = Strings.constant("class");
    private static final TruffleString AUTO_ACCESSOR_KIND = Strings.constant("accessor");
    private static final TruffleString ADD_INITIALIZER = Strings.constant("addInitializer");
    private static final TruffleString NAME = Strings.constant("name");
    private static final TruffleString KIND = Strings.constant("kind");
    private static final TruffleString ACCESS = Strings.constant("access");
    private static final HiddenKey INIT_KEY = new HiddenKey(":initializers");
    protected static final HiddenKey MAGIC_KEY = new HiddenKey(":magic");
    @CompilerDirectives.CompilationFinal
    private int blockScopeSlot = -1;
    @CompilerDirectives.CompilationFinal
    private JSFunctionData valueGetterFrameUncached;
    @CompilerDirectives.CompilationFinal
    private JSFunctionData valueGetterPropertyUncached;
    @CompilerDirectives.CompilationFinal
    private JSFunctionData valueSetterFrameUncached;
    @CompilerDirectives.CompilationFinal
    private JSFunctionData valueSetterPropertyUncached;
    @CompilerDirectives.CompilationFinal
    private JSFunctionData methodGetterFrameUncached;
    @CompilerDirectives.CompilationFinal
    private JSFunctionData methodGetterPropertyUncached;
    @Node.Child
    private PropertySetNode setRecordKey;
    @Node.Child
    private CreateObjectNode createObjectNode;
    @Node.Child
    private PropertySetNode setInitializersKey;
    @Node.Child
    protected TruffleString.RegionEqualByteIndexNode eqNode;
    private final boolean isStatic;
    private final JSFunctionData initializerData;
    protected final JSContext context;

    public static CreateDecoratorContextObjectNode create(JSContext context, boolean isStatic) {
        return CreateDecoratorContextObjectNodeGen.create(context, isStatic);
    }

    public abstract JSDynamicObject executeContext(VirtualFrame var1, ClassElementDefinitionRecord var2, Object var3, Record var4);

    CreateDecoratorContextObjectNode(JSContext context, boolean isStatic) {
        this.initializerData = CreateDecoratorContextObjectNode.createInitializerFunctionData(context);
        this.createObjectNode = CreateObjectNode.create(context);
        this.setInitializersKey = PropertySetNode.createSetHidden(INIT_KEY, context);
        this.setRecordKey = PropertySetNode.createSetHidden(MAGIC_KEY, context);
        this.eqNode = TruffleString.RegionEqualByteIndexNode.create();
        this.isStatic = isStatic;
        this.context = context;
    }

    public final JSDynamicObject evaluateClass(VirtualFrame frame, Object className, Object initializers, Record state) {
        JSDynamicObject contextObj = this.createObjectNode.execute(frame);
        JSRuntime.createDataPropertyOrThrow(contextObj, KIND, CLASS_KIND);
        JSRuntime.createDataPropertyOrThrow(contextObj, NAME, className);
        this.addInitializerFunction(contextObj, state, initializers);
        return contextObj;
    }

    @Specialization(guards={"record.isMethod()", "nameEquals(strEq,record,cachedName)", "privateName"})
    public JSDynamicObject doPrivateMethodCached(VirtualFrame frame, ClassElementDefinitionRecord.PrivateFrameBasedElementDefinitionRecord record, Object initializers, Record state, @Cached(value="record.getKey()") Object cachedName, @Cached(value="getName(cachedName)") Object description, @Cached(value="create()") TruffleString.EqualNode strEq, @Cached(value="createMethodGetterFromFrameCached(record)") JSFunctionData valueGetterFunctionData, @Cached(value="record.isPrivate()") boolean privateName) {
        JSFunctionObject getter = JSFunction.create(this.getRealm(), valueGetterFunctionData, this.getScopeFrame(frame, record));
        return this.createContextObject(frame, cachedName, initializers, state, getter, null, privateName, METHOD_KIND);
    }

    @Specialization(guards={"record.isMethod()", "nameEquals(strEq,record,cachedName)", "!privateName"})
    public JSDynamicObject doPublicMethodCached(VirtualFrame frame, ClassElementDefinitionRecord record, Object initializers, Record state, @Cached(value="record.getKey()") Object cachedName, @Cached(value="getName(cachedName)") Object description, @Cached(value="create()") TruffleString.EqualNode strEq, @Cached(value="record.isPrivate()") boolean privateName, @Cached(value="createValueGetterCached(cachedName,false)") JSFunctionData valueGetterFunctionData) {
        JSFunctionObject getter = JSFunction.create(this.getRealm(), valueGetterFunctionData);
        return this.createContextObject(frame, description, initializers, state, getter, null, privateName, METHOD_KIND);
    }

    @Specialization(guards={"record.isMethod()"}, replaces={"doPublicMethodCached", "doPrivateMethodCached"})
    public JSDynamicObject doMethodGeneric(VirtualFrame frame, ClassElementDefinitionRecord record, Object initializers, Record state, @Cached(value="createSetHidden(MAGIC_KEY,context)") PropertySetNode setMagic) {
        Object description = record.isPrivate() ? record.getKey() : this.getName(record.getKey());
        JSFunctionData valueGetterFunctionData = record.isPrivate() ? this.getMethodGetterFrameUncached() : this.getMethodGetterPropertyUncached();
        JSDynamicObject getter = this.initializeMagicField(frame, record, valueGetterFunctionData, setMagic);
        return this.createContextObject(frame, description, initializers, state, getter, null, record.isPrivate(), METHOD_KIND);
    }

    @Specialization(guards={"record.isField()", "nameEquals(strEq,record,cachedName)"})
    public JSDynamicObject doFieldCached(VirtualFrame frame, ClassElementDefinitionRecord record, Object initializers, Record state, @Cached(value="record.getKey()") Object cachedName, @Cached(value="getName(cachedName)") Object description, @Cached(value="create()") TruffleString.EqualNode strEq, @Cached(value="record.isPrivate()") boolean privateName, @Cached(value="createValueGetterCached(cachedName,privateName)") JSFunctionData valueGetterFunctionData, @Cached(value="createValueSetterCached(cachedName,privateName)") JSFunctionData valueSetterFunctionData) {
        JSFunctionObject getter = JSFunction.create(this.getRealm(), valueGetterFunctionData);
        JSFunctionObject setter = JSFunction.create(this.getRealm(), valueSetterFunctionData);
        return this.createContextObject(frame, description, initializers, state, getter, setter, privateName, FIELD_KIND);
    }

    @Specialization(guards={"record.isField()"}, replaces={"doFieldCached"})
    public JSDynamicObject doFieldUncached(VirtualFrame frame, ClassElementDefinitionRecord record, Object initializers, Record state, @Cached(value="createSetHidden(MAGIC_KEY,context)") PropertySetNode setMagic) {
        return this.createContextGetterSetter(frame, record, initializers, state, setMagic, FIELD_KIND);
    }

    @Specialization(guards={"record.isAutoAccessor()", "nameEquals(strEq,record,cachedName)"})
    public JSDynamicObject doAutoAccessorCached(VirtualFrame frame, ClassElementDefinitionRecord.AutoAccessor record, Object initializers, Record state, @Cached(value="record.getKey()") Object cachedName, @Cached(value="getName(cachedName)") Object description, @Cached(value="create()") TruffleString.EqualNode strEq, @Cached(value="record.isPrivate()") boolean privateName, @Cached(value="createValueGetterCached(cachedName,privateName)") JSFunctionData valueGetterFunctionData, @Cached(value="createValueSetterCached(cachedName,privateName)") JSFunctionData valueSetterFunctionData) {
        JSFunctionObject getter = JSFunction.create(this.getRealm(), valueGetterFunctionData);
        JSFunctionObject setter = JSFunction.create(this.getRealm(), valueSetterFunctionData);
        return this.createContextObject(frame, description, initializers, state, getter, setter, privateName, AUTO_ACCESSOR_KIND);
    }

    @Specialization(guards={"record.isAutoAccessor()"}, replaces={"doAutoAccessorCached"})
    public JSDynamicObject doAutoAccessor(VirtualFrame frame, ClassElementDefinitionRecord.AutoAccessor record, Object initializers, Record state, @Cached(value="createSetHidden(MAGIC_KEY,context)") PropertySetNode setMagic) {
        return this.createContextGetterSetter(frame, record, initializers, state, setMagic, AUTO_ACCESSOR_KIND);
    }

    @Specialization(guards={"record.isGetter()", "nameEquals(strEq,record,cachedName)", "!privateName"})
    public JSDynamicObject doGetterCached(VirtualFrame frame, ClassElementDefinitionRecord record, Object initializers, Record state, @Cached(value="record.getKey()") Object cachedName, @Cached(value="getName(cachedName)") Object description, @Cached(value="create()") TruffleString.EqualNode strEq, @Cached(value="record.isPrivate()") boolean privateName, @Cached(value="createValueGetterCached(cachedName,privateName)") JSFunctionData valueGetterFunctionData) {
        JSFunctionObject getter = JSFunction.create(this.getRealm(), valueGetterFunctionData);
        return this.createContextObject(frame, description, initializers, state, getter, null, privateName, GETTER_KIND);
    }

    @Specialization(guards={"record.isGetter()"}, replaces={"doGetterCached"})
    public JSDynamicObject doGetter(VirtualFrame frame, ClassElementDefinitionRecord record, Object initializers, Record state, @Cached(value="createSetHidden(MAGIC_KEY,context)") PropertySetNode setMagic) {
        JSFunctionData valueGetterFunctionData = record.isPrivate() ? this.getValueGetterFrameUncached() : this.getValueGetterPropertyUncached();
        JSDynamicObject getter = this.initializeMagicField(frame, record, valueGetterFunctionData, setMagic);
        Object name = record.isPrivate() ? record.getKey() : this.getName(record.getKey());
        return this.createContextObject(frame, name, initializers, state, getter, null, record.isPrivate(), GETTER_KIND);
    }

    @Specialization(guards={"record.isSetter()", "nameEquals(strEq,record,cachedName)", "!privateName"})
    public JSDynamicObject doSetterCached(VirtualFrame frame, ClassElementDefinitionRecord record, Object initializers, Record state, @Cached(value="record.getKey()") Object cachedName, @Cached(value="getName(cachedName)") Object description, @Cached(value="create()") TruffleString.EqualNode strEq, @Cached(value="record.isPrivate()") boolean privateName, @Cached(value="createValueSetterCached(cachedName,privateName)") JSFunctionData valueSetterFunctionData) {
        JSFunctionObject setter = JSFunction.create(this.getRealm(), valueSetterFunctionData);
        return this.createContextObject(frame, description, initializers, state, null, setter, privateName, SETTER_KIND);
    }

    @Specialization(guards={"record.isSetter()"}, replaces={"doSetterCached"})
    public JSDynamicObject doSetter(VirtualFrame frame, ClassElementDefinitionRecord record, Object initializers, Record state, @Cached(value="createSetHidden(MAGIC_KEY,context)") PropertySetNode setMagic) {
        JSFunctionData valueSetterFunctionData = record.isPrivate() ? this.getValueSetterFrameUncached() : this.getValueSetterPropertyUncached();
        JSDynamicObject setter = this.initializeMagicField(frame, record, valueSetterFunctionData, setMagic);
        Object name = record.isPrivate() ? record.getKey() : this.getName(record.getKey());
        return this.createContextObject(frame, name, initializers, state, null, setter, record.isPrivate(), SETTER_KIND);
    }

    private JSDynamicObject initializeMagicField(VirtualFrame frame, ClassElementDefinitionRecord record, JSFunctionData functionData, PropertySetNode setMagic) {
        JSFunctionObject function;
        if (record.isPrivate()) {
            ClassElementDefinitionRecord.PrivateFrameBasedElementDefinitionRecord methodRecord = (ClassElementDefinitionRecord.PrivateFrameBasedElementDefinitionRecord)record;
            function = JSFunction.create(this.getRealm(), functionData, this.getScopeFrame(frame, methodRecord));
            int[] slots = new int[]{methodRecord.getKeySlot(), methodRecord.getBrandSlot()};
            setMagic.setValue(function, slots);
        } else {
            function = JSFunction.create(this.getRealm(), functionData);
            setMagic.setValue(function, record);
        }
        return function;
    }

    private MaterializedFrame getScopeFrame(VirtualFrame frame, ClassElementDefinitionRecord.PrivateFrameBasedElementDefinitionRecord record) {
        if (this.blockScopeSlot == -1) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.blockScopeSlot = record.getBlockScopeSlot();
        }
        assert (this.blockScopeSlot == record.getBlockScopeSlot()) : "slot must not change";
        return JSFrameUtil.castMaterializedFrame(frame.getObject(this.blockScopeSlot));
    }

    protected JSFunctionData getMethodGetterFrameUncached() {
        if (this.methodGetterFrameUncached == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.methodGetterFrameUncached = CreateDecoratorContextObjectNode.createGetterFromFrameUncached(this.context, ClassElementDefinitionRecord.Kind.Method);
        }
        return this.methodGetterFrameUncached;
    }

    protected JSFunctionData getMethodGetterPropertyUncached() {
        if (this.methodGetterPropertyUncached == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.methodGetterPropertyUncached = CreateDecoratorContextObjectNode.createGetterFromPropertyUncached(this.context);
        }
        return this.methodGetterPropertyUncached;
    }

    private JSFunctionData getValueGetterPropertyUncached() {
        if (this.valueGetterPropertyUncached == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.valueGetterPropertyUncached = CreateDecoratorContextObjectNode.createGetterFromPropertyUncached(this.context);
        }
        return this.valueGetterPropertyUncached;
    }

    private JSFunctionData getValueGetterFrameUncached() {
        if (this.valueGetterFrameUncached == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.valueGetterFrameUncached = CreateDecoratorContextObjectNode.createGetterFromFrameUncached(this.context, ClassElementDefinitionRecord.Kind.Getter);
        }
        return this.valueGetterFrameUncached;
    }

    private JSFunctionData getValueSetterPropertyUncached() {
        if (this.valueSetterPropertyUncached == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.valueSetterPropertyUncached = CreateDecoratorContextObjectNode.createSetterFromPropertyUncached(this.context);
        }
        return this.valueSetterPropertyUncached;
    }

    private JSFunctionData getValueSetterFrameUncached() {
        if (this.valueSetterFrameUncached == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.valueSetterFrameUncached = CreateDecoratorContextObjectNode.createSetterFromFrameUncached(this.context, ClassElementDefinitionRecord.Kind.Setter);
        }
        return this.valueSetterFrameUncached;
    }

    protected static boolean nameEquals(TruffleString.EqualNode strEq, ClassElementDefinitionRecord record, Object expected) {
        if (record.getKey() instanceof TruffleString && expected instanceof TruffleString) {
            return Strings.equals(strEq, (TruffleString)expected, (TruffleString)record.getKey());
        }
        return false;
    }

    @CompilerDirectives.TruffleBoundary
    protected Object getName(Object key) {
        if (key instanceof HiddenKey) {
            String name = ((HiddenKey)key).getName();
            String description = name.charAt(0) == '#' ? name.substring(1) : name;
            return Strings.fromJavaString(description);
        }
        return key;
    }

    private void addInitializerFunction(JSDynamicObject contextObj, Record state, Object initializers) {
        JSFunctionObject initializerFunction = JSFunction.create(JSRealm.get(this), this.initializerData);
        this.setInitializersKey.setValue(initializerFunction, initializers);
        this.setRecordKey.setValue(initializerFunction, state);
        JSRuntime.createDataPropertyOrThrow(contextObj, ADD_INITIALIZER, initializerFunction);
    }

    public JSDynamicObject createContextGetterSetter(VirtualFrame frame, ClassElementDefinitionRecord record, Object initializers, Record state, PropertySetNode setMagic, TruffleString kind) {
        Object description = this.getName(record.getKey());
        boolean privateName = record.isPrivate();
        JSFunctionData valueGetterFunctionData = record.isPrivate() && record.getKind() != ClassElementDefinitionRecord.Kind.Field ? this.getValueGetterFrameUncached() : this.getValueGetterPropertyUncached();
        JSFunctionData valueSetterFunctionData = record.isPrivate() && record.getKind() != ClassElementDefinitionRecord.Kind.Field ? this.getValueSetterFrameUncached() : this.getValueSetterPropertyUncached();
        JSFunctionObject getter = JSFunction.create(this.getRealm(), valueGetterFunctionData);
        JSFunctionObject setter = JSFunction.create(this.getRealm(), valueSetterFunctionData);
        setMagic.setValue(getter, record);
        setMagic.setValue(setter, record);
        return this.createContextObject(frame, description, initializers, state, getter, setter, privateName, kind);
    }

    public JSDynamicObject createContextObject(VirtualFrame frame, Object name, Object initializers, Record state, JSDynamicObject getter, JSDynamicObject setter, boolean privateName, TruffleString kindName) {
        JSDynamicObject contextObj = this.createObjectNode.execute(frame);
        JSRuntime.createDataPropertyOrThrow(contextObj, KIND, kindName);
        JSDynamicObject accessObject = this.createObjectNode.execute(frame);
        if (getter != null) {
            JSRuntime.createDataPropertyOrThrow(accessObject, Strings.GET, getter);
        }
        if (setter != null) {
            JSRuntime.createDataPropertyOrThrow(accessObject, Strings.SET, setter);
        }
        JSRuntime.createDataPropertyOrThrow(contextObj, ACCESS, accessObject);
        JSRuntime.createDataPropertyOrThrow(contextObj, Strings.STATIC, this.isStatic);
        JSRuntime.createDataPropertyOrThrow(contextObj, Strings.PRIVATE, privateName);
        JSRuntime.createDataPropertyOrThrow(contextObj, NAME, name);
        this.addInitializerFunction(contextObj, state, initializers);
        return contextObj;
    }

    @CompilerDirectives.TruffleBoundary
    protected static JSFunctionData createInitializerFunctionData(final JSContext context) {
        CompilerAsserts.neverPartOfCompilation();
        RootCallTarget callTarget = new JavaScriptRootNode(context.getLanguage(), null, null){
            @Node.Child
            private PropertyGetNode getRecordKey;
            @Node.Child
            private PropertyGetNode getInitializersKey;
            {
                super(lang, sourceSection, frameDescriptor);
                this.getRecordKey = PropertyGetNode.createGetHidden(MAGIC_KEY, context);
                this.getInitializersKey = PropertyGetNode.createGetHidden(INIT_KEY, context);
            }

            @Override
            public Object execute(VirtualFrame frame) {
                JSFunctionObject self = (JSFunctionObject)JSArguments.getFunctionObject(frame.getArguments());
                Record state = (Record)this.getRecordKey.getValue(self);
                List initializers = (List)this.getInitializersKey.getValue(self);
                if (state.finished) {
                    CompilerDirectives.transferToInterpreter();
                    throw JSException.create(JSErrorType.TypeError, "Bad decorator initializer state");
                }
                Object[] args = frame.getArguments();
                JSDynamicObject value2 = JSArguments.getUserArgumentCount(args) > 0 ? JSArguments.getUserArgument(args, 0) : Undefined.instance;
                Boundaries.listAdd(initializers, value2);
                return Undefined.instance;
            }
        }.getCallTarget();
        return JSFunctionData.createCallOnly(context, callTarget, 1, ADD_INITIALIZER);
    }

    @CompilerDirectives.TruffleBoundary
    protected JSFunctionData createMethodGetterFromFrameCached(ClassElementDefinitionRecord.PrivateFrameBasedElementDefinitionRecord record) {
        CompilerAsserts.neverPartOfCompilation();
        assert (record.getKind() == ClassElementDefinitionRecord.Kind.Method);
        final int keySlot = record.getKeySlot();
        final int constructorSlot = record.getBrandSlot();
        RootCallTarget callTarget = new JavaScriptRootNode(this.context.getLanguage(), null, null){
            @Node.Child
            private JSReadFrameSlotNode readMethod;
            @Node.Child
            private PrivateBrandCheckNode brandCheckNode;

            @Override
            public Object execute(VirtualFrame frame) {
                JSFunctionObject functionObject = JSFrameUtil.getFunctionObject(frame);
                Object thiz = JSFrameUtil.getThisObj(frame);
                MaterializedFrame blockScopeFrame = JSFunction.getEnclosingFrame(functionObject);
                if (this.brandCheckNode == null) {
                    CompilerDirectives.transferToInterpreterAndInvalidate();
                    JSReadFrameSlotNode readConstructor = JSReadFrameSlotNode.create(blockScopeFrame.getFrameDescriptor(), constructorSlot);
                    this.readMethod = this.insert(JSReadFrameSlotNode.create(blockScopeFrame.getFrameDescriptor(), keySlot));
                    this.brandCheckNode = this.insert(PrivateBrandCheckNode.create(this.readMethod, readConstructor));
                }
                if (this.brandCheckNode.executeWithTarget(blockScopeFrame, thiz) != Undefined.instance) {
                    return this.readMethod.execute(blockScopeFrame);
                }
                throw Errors.createTypeErrorIllegalAccessorTarget(this);
            }
        }.getCallTarget();
        return JSFunctionData.createCallOnly(this.context, callTarget, 0, Strings.GET);
    }

    @CompilerDirectives.TruffleBoundary
    protected JSFunctionData createValueGetterCached(final Object keyName, final boolean isPrivateFieldGet) {
        CompilerAsserts.neverPartOfCompilation();
        RootCallTarget callTarget = new JavaScriptRootNode(this.context.getLanguage(), null, null){
            @Node.Child
            private PropertyGetNode propertyGetNode;
            @Node.Child
            private HasPropertyCacheNode propertyHasNode;
            {
                super(lang, sourceSection, frameDescriptor);
                this.propertyGetNode = PropertyGetNode.create(keyName, CreateDecoratorContextObjectNode.this.context);
                this.propertyHasNode = HasPropertyCacheNode.create(keyName, CreateDecoratorContextObjectNode.this.context);
            }

            @Override
            public Object execute(VirtualFrame frame) {
                Object thiz = JSFrameUtil.getThisObj(frame);
                if (isPrivateFieldGet && !this.propertyHasNode.hasProperty(thiz)) {
                    throw Errors.createTypeErrorIllegalAccessorTarget(this);
                }
                return this.propertyGetNode.getValue(thiz);
            }
        }.getCallTarget();
        return JSFunctionData.createCallOnly(this.context, callTarget, 0, Strings.GET);
    }

    @CompilerDirectives.TruffleBoundary
    protected JSFunctionData createValueSetterCached(final Object name, final boolean isPrivateFieldGet) {
        CompilerAsserts.neverPartOfCompilation();
        RootCallTarget callTarget = new JavaScriptRootNode(this.context.getLanguage(), null, null){
            @Node.Child
            private HasPropertyCacheNode propertyHasNode;
            @Node.Child
            private PropertySetNode propertySetNode;
            {
                super(lang, sourceSection, frameDescriptor);
                this.propertyHasNode = HasPropertyCacheNode.create(name, CreateDecoratorContextObjectNode.this.context);
                this.propertySetNode = PropertySetNode.create(name, false, CreateDecoratorContextObjectNode.this.context, true);
            }

            @Override
            public Object execute(VirtualFrame frame) {
                Object thiz = JSFrameUtil.getThisObj(frame);
                if (isPrivateFieldGet && !this.propertyHasNode.hasProperty(thiz)) {
                    throw Errors.createTypeErrorIllegalAccessorTarget(this);
                }
                Object[] args = frame.getArguments();
                JSDynamicObject newValue = JSArguments.getUserArgumentCount(args) > 0 ? JSArguments.getUserArgument(args, 0) : Undefined.instance;
                this.propertySetNode.setValue(thiz, newValue);
                return newValue;
            }
        }.getCallTarget();
        return JSFunctionData.createCallOnly(this.context, callTarget, 1, Strings.SET);
    }

    @CompilerDirectives.TruffleBoundary
    private static JSFunctionData createGetterFromFrameUncached(final JSContext context, final ClassElementDefinitionRecord.Kind kind) {
        CompilerAsserts.neverPartOfCompilation();
        RootCallTarget callTarget = new JavaScriptRootNode(context.getLanguage(), null, null){
            @Node.Child
            private PropertyGetNode getMagic;
            @CompilerDirectives.CompilationFinal
            private int keySlot;
            @CompilerDirectives.CompilationFinal
            private int classSlot;
            {
                super(lang, sourceSection, frameDescriptor);
                this.getMagic = PropertyGetNode.createGetHidden(MAGIC_KEY, context);
                this.keySlot = -1;
                this.classSlot = -1;
            }

            @Override
            public Object execute(VirtualFrame frame) {
                JSFunctionObject functionObject = JSFrameUtil.getFunctionObject(frame);
                Object thiz = JSFrameUtil.getThisObj(frame);
                MaterializedFrame blockScopeFrame = JSFunction.getEnclosingFrame(functionObject);
                int[] slots = (int[])this.getMagic.getValue(functionObject);
                if (this.keySlot == -1 || this.classSlot == -1) {
                    CompilerDirectives.transferToInterpreterAndInvalidate();
                    this.keySlot = slots[0];
                    this.classSlot = slots[1];
                }
                assert (slots[0] == this.keySlot && slots[1] == this.classSlot) : "slot must not change";
                if (blockScopeFrame.isObject(this.classSlot) && blockScopeFrame.isObject(this.keySlot)) {
                    Object method = blockScopeFrame.getObject(this.keySlot);
                    Object constructor = blockScopeFrame.getObject(this.classSlot);
                    if (thiz == constructor) {
                        if (kind == ClassElementDefinitionRecord.Kind.Getter) {
                            Accessor accessor = (Accessor)method;
                            Object getter = accessor.getGetter();
                            if (getter != Undefined.instance) {
                                return JSRuntime.call(getter, thiz, JSArguments.EMPTY_ARGUMENTS_ARRAY);
                            }
                            return Undefined.instance;
                        }
                        if (kind == ClassElementDefinitionRecord.Kind.Setter) {
                            Accessor accessor = (Accessor)method;
                            return accessor.getSetter();
                        }
                        assert (kind == ClassElementDefinitionRecord.Kind.Method);
                        return method;
                    }
                }
                throw Errors.createTypeErrorIllegalAccessorTarget(this);
            }
        }.getCallTarget();
        return JSFunctionData.createCallOnly(context, callTarget, 0, Strings.GET);
    }

    @CompilerDirectives.TruffleBoundary
    private static JSFunctionData createSetterFromFrameUncached(final JSContext context, final ClassElementDefinitionRecord.Kind kind) {
        CompilerAsserts.neverPartOfCompilation();
        RootCallTarget callTarget = new JavaScriptRootNode(context.getLanguage(), null, null){
            @Node.Child
            private PropertyGetNode getMagic;
            @CompilerDirectives.CompilationFinal
            private int keySlot;
            @CompilerDirectives.CompilationFinal
            private int classSlot;
            {
                super(lang, sourceSection, frameDescriptor);
                this.getMagic = PropertyGetNode.createGetHidden(MAGIC_KEY, context);
                this.keySlot = -1;
                this.classSlot = -1;
            }

            @Override
            public Object execute(VirtualFrame frame) {
                Object constructor;
                JSFunctionObject functionObject = JSFrameUtil.getFunctionObject(frame);
                Object thiz = JSFrameUtil.getThisObj(frame);
                MaterializedFrame blockScopeFrame = JSFunction.getEnclosingFrame(functionObject);
                int[] slots = (int[])this.getMagic.getValue(functionObject);
                if (this.keySlot == -1 || this.classSlot == -1) {
                    CompilerDirectives.transferToInterpreterAndInvalidate();
                    this.keySlot = slots[0];
                    this.classSlot = slots[1];
                }
                assert (slots[0] == this.keySlot && slots[1] == this.classSlot) : "slot must not change";
                if (blockScopeFrame.isObject(this.classSlot) && blockScopeFrame.isObject(this.keySlot) && thiz == (constructor = blockScopeFrame.getObject(this.classSlot))) {
                    Accessor accessor;
                    JSDynamicObject newValue = Undefined.instance;
                    if (JSArguments.getUserArgumentCount(frame.getArguments()) > 0) {
                        Object maybeNewValue = JSArguments.getUserArgument(frame.getArguments(), 0);
                        if (maybeNewValue instanceof JSDynamicObject) {
                            newValue = (JSDynamicObject)maybeNewValue;
                        } else {
                            throw Errors.createTypeErrorIllegalAccessorTarget(this);
                        }
                    }
                    if (kind == ClassElementDefinitionRecord.Kind.Setter) {
                        accessor = (Accessor)blockScopeFrame.getObject(this.keySlot);
                        Accessor updated = new Accessor(accessor.getGetter(), newValue);
                        blockScopeFrame.setObject(this.keySlot, updated);
                    } else if (kind == ClassElementDefinitionRecord.Kind.Getter) {
                        accessor = (Accessor)blockScopeFrame.getObject(this.keySlot);
                        Accessor updated = new Accessor(newValue, accessor.getSetter());
                        blockScopeFrame.setObject(this.keySlot, updated);
                    } else {
                        assert (kind == ClassElementDefinitionRecord.Kind.Method);
                        blockScopeFrame.setObject(this.keySlot, newValue);
                    }
                }
                throw Errors.createTypeErrorIllegalAccessorTarget(this);
            }
        }.getCallTarget();
        if (kind == ClassElementDefinitionRecord.Kind.Getter) {
            return JSFunctionData.createCallOnly(context, callTarget, 0, Strings.GET);
        }
        return JSFunctionData.createCallOnly(context, callTarget, 1, Strings.SET);
    }

    private static Object checkPrivateAccess(VirtualFrame frame, Object thiz, PropertyGetNode getMagic, DynamicObjectLibrary access, Node self) {
        JSFunctionObject function = JSFrameUtil.getFunctionObject(frame);
        ClassElementDefinitionRecord record = (ClassElementDefinitionRecord)getMagic.getValue(function);
        Object key = record.getKey();
        if (!(!record.isPrivate() || thiz instanceof JSDynamicObject && Properties.containsKey(access, (JSDynamicObject)thiz, key))) {
            throw Errors.createTypeErrorIllegalAccessorTarget(self);
        }
        return key;
    }

    @CompilerDirectives.TruffleBoundary
    protected static JSFunctionData createGetterFromPropertyUncached(final JSContext context) {
        RootCallTarget callTarget = new JavaScriptRootNode(context.getLanguage(), null, null){
            @Node.Child
            private DynamicObjectLibrary access;
            @Node.Child
            private ReadElementNode propertyGetNode;
            @Node.Child
            private PropertyGetNode getMagic;
            {
                super(lang, sourceSection, frameDescriptor);
                this.access = DynamicObjectLibrary.getUncached();
                this.propertyGetNode = ReadElementNode.create(context);
                this.getMagic = PropertyGetNode.createGetHidden(MAGIC_KEY, context);
            }

            @Override
            public Object execute(VirtualFrame frame) {
                Object thiz = JSFrameUtil.getThisObj(frame);
                Object key = CreateDecoratorContextObjectNode.checkPrivateAccess(frame, thiz, this.getMagic, this.access, this);
                return this.propertyGetNode.executeWithTargetAndIndex(thiz, key);
            }
        }.getCallTarget();
        return JSFunctionData.createCallOnly(context, callTarget, 0, Strings.GET);
    }

    @CompilerDirectives.TruffleBoundary
    protected static JSFunctionData createSetterFromPropertyUncached(final JSContext context) {
        RootCallTarget callTarget = new JavaScriptRootNode(context.getLanguage(), null, null){
            @Node.Child
            private DynamicObjectLibrary access;
            @Node.Child
            private PropertyGetNode getMagic;
            @Node.Child
            private WriteElementNode propertySetNode;
            {
                super(lang, sourceSection, frameDescriptor);
                this.access = DynamicObjectLibrary.getUncached();
                this.getMagic = PropertyGetNode.createGetHidden(MAGIC_KEY, context);
                this.propertySetNode = WriteElementNode.create(context, false);
            }

            @Override
            public Object execute(VirtualFrame frame) {
                Object thiz = JSFrameUtil.getThisObj(frame);
                Object key = CreateDecoratorContextObjectNode.checkPrivateAccess(frame, thiz, this.getMagic, this.access, this);
                Object[] args = frame.getArguments();
                JSDynamicObject newValue = JSArguments.getUserArgumentCount(args) > 0 ? JSArguments.getUserArgument(args, 0) : Undefined.instance;
                this.propertySetNode.executeWithTargetAndIndexAndValue(thiz, key, (Object)newValue);
                return newValue;
            }
        }.getCallTarget();
        return JSFunctionData.createCallOnly(context, callTarget, 1, Strings.SET);
    }

    public static class Record {
        boolean finished = false;
    }
}

