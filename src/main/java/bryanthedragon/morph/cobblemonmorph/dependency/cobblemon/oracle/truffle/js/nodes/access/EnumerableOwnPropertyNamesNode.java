
package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.InvalidArrayIndexException;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.helper.ListGetNode;
import com.oracle.truffle.js.builtins.helper.ListSizeNode;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.access.EnumerableOwnPropertyNamesNodeGen;
import com.oracle.truffle.js.nodes.access.HasOnlyShapePropertiesNode;
import com.oracle.truffle.js.nodes.access.JSGetOwnPropertyNode;
import com.oracle.truffle.js.nodes.interop.ImportValueNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.builtins.JSArray;
import com.oracle.truffle.js.runtime.builtins.JSClass;
import com.oracle.truffle.js.runtime.builtins.JSProxy;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSShape;
import com.oracle.truffle.js.runtime.objects.PropertyDescriptor;
import com.oracle.truffle.js.runtime.util.JSClassProfile;
import com.oracle.truffle.js.runtime.util.SimpleArrayList;
import com.oracle.truffle.js.runtime.util.UnmodifiableArrayList;
import java.util.List;

@ImportStatic(value={JSConfig.class})
public abstract class EnumerableOwnPropertyNamesNode
extends JavaScriptBaseNode {
    private final boolean keys;
    private final boolean values;
    private final JSContext context;
    @Node.Child
    private JSGetOwnPropertyNode getOwnPropertyNode;
    private final ConditionProfile hasFastShapesProfile = ConditionProfile.createBinaryProfile();
    private final BranchProfile growProfile = BranchProfile.create();

    protected EnumerableOwnPropertyNamesNode(JSContext context, boolean keys, boolean values) {
        this.context = context;
        this.keys = keys;
        this.values = values;
    }

    public static EnumerableOwnPropertyNamesNode createKeys(JSContext context) {
        return EnumerableOwnPropertyNamesNodeGen.create(context, true, false);
    }

    public static EnumerableOwnPropertyNamesNode createValues(JSContext context) {
        return EnumerableOwnPropertyNamesNodeGen.create(context, false, true);
    }

    public static EnumerableOwnPropertyNamesNode createKeysValues(JSContext context) {
        return EnumerableOwnPropertyNamesNodeGen.create(context, true, true);
    }

    public abstract UnmodifiableArrayList<? extends Object> execute(Object var1);

    @Specialization
    protected UnmodifiableArrayList<? extends Object> enumerableOwnPropertyNames(JSDynamicObject thisObj, @Cached JSClassProfile jsclassProfile, @Cached ListSizeNode listSize, @Cached ListGetNode listGet, @Cached HasOnlyShapePropertiesNode hasOnlyShapeProperties) {
        JSClass jsclass = jsclassProfile.getJSClass(thisObj);
        if (this.hasFastShapesProfile.profile(this.keys && !this.values && hasOnlyShapeProperties.execute(thisObj, jsclass))) {
            return JSShape.getEnumerablePropertyNames(thisObj.getShape());
        }
        boolean isProxy = JSProxy.isJSProxy(thisObj);
        List<Object> ownKeys = jsclass.ownPropertyKeys(thisObj);
        int ownKeysSize = listSize.execute(ownKeys);
        SimpleArrayList<Object> properties2 = new SimpleArrayList<Object>();
        for (int i = 0; i < ownKeysSize; ++i) {
            Object element;
            PropertyDescriptor desc;
            Object key = listGet.execute(ownKeys, i);
            if (!Strings.isTString(key) || (desc = this.getOwnProperty(thisObj, key)) == null || !desc.getEnumerable()) continue;
            if (this.keys && !this.values) {
                element = key;
            } else {
                Object value2;
                Object object = value2 = desc.isAccessorDescriptor() || isProxy ? jsclass.get(thisObj, key) : desc.getValue();
                if (!this.keys && this.values) {
                    element = value2;
                } else {
                    assert (this.keys && this.values);
                    element = this.createKeyValuePair(key, value2);
                }
            }
            properties2.add(element, this.growProfile);
        }
        return new UnmodifiableArrayList<Object>(properties2.toArray());
    }

    private Object createKeyValuePair(Object key, Object value2) {
        return JSArray.createConstant(this.context, this.getRealm(), new Object[]{key, value2});
    }

    protected PropertyDescriptor getOwnProperty(JSDynamicObject thisObj, Object key) {
        if (this.getOwnPropertyNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.getOwnPropertyNode = this.insert(JSGetOwnPropertyNode.create(this.values, true, false, false, false));
        }
        return this.getOwnPropertyNode.execute(thisObj, key);
    }

    @Specialization(guards={"isForeignObject(obj)"}, limit="InteropLibraryLimit")
    protected UnmodifiableArrayList<? extends Object> enumerableOwnPropertyNamesForeign(Object obj, @CachedLibrary(value="obj") InteropLibrary interop, @CachedLibrary(limit="InteropLibraryLimit") InteropLibrary members, @CachedLibrary(limit="InteropLibraryLimit") InteropLibrary asString, @Cached ImportValueNode importValue, @Cached BranchProfile errorBranch) {
        try {
            long arraySize = 0L;
            if (interop.hasArrayElements(obj)) {
                arraySize = interop.getArraySize(obj);
            }
            Object keysObj = null;
            long memberCount = 0L;
            if (interop.hasMembers(obj)) {
                keysObj = interop.getMembers(obj);
                memberCount = members.getArraySize(keysObj);
            }
            long size = arraySize + memberCount;
            if (arraySize < 0L || memberCount < 0L || size < 0L || size >= Integer.MAX_VALUE) {
                errorBranch.enter();
                throw Errors.createRangeErrorInvalidArrayLength();
            }
            if (size > 0L) {
                Object element;
                TruffleString key;
                SimpleArrayList<Object> list = new SimpleArrayList<Object>((int)size);
                for (long i = 0L; i < arraySize; ++i) {
                    key = Strings.fromLong(i);
                    if (this.values) {
                        Object value2 = importValue.executeWithTarget(interop.readArrayElement(obj, i));
                        element = this.keys ? this.createKeyValuePair(key, value2) : value2;
                    } else {
                        element = key;
                    }
                    list.addUnchecked(element);
                }
                int i = 0;
                while ((long)i < memberCount) {
                    Object objectKey = members.readArrayElement(keysObj, i);
                    assert (InteropLibrary.getUncached().isString(objectKey));
                    key = Strings.interopAsTruffleString(asString, objectKey);
                    if (this.values) {
                        String javaStringKey = Strings.toJavaString(key);
                        Object value3 = importValue.executeWithTarget(interop.readMember(obj, javaStringKey));
                        element = this.keys ? this.createKeyValuePair(key, value3) : value3;
                    } else {
                        element = key;
                    }
                    list.addUnchecked(element);
                    ++i;
                }
                return new UnmodifiableArrayList<Object>(list.toArray());
            }
        }
        catch (InvalidArrayIndexException | UnknownIdentifierException | UnsupportedMessageException interopException) {
            // empty catch block
        }
        return new UnmodifiableArrayList<Object>(ScriptArray.EMPTY_OBJECT_ARRAY);
    }
}

