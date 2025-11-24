
package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.builtins.JSObjectFactory;
import com.oracle.truffle.js.runtime.builtins.JSRegExp;
import com.oracle.truffle.js.runtime.objects.JSCopyableObject;
import com.oracle.truffle.js.runtime.objects.JSNonProxyObject;
import com.oracle.truffle.js.runtime.objects.JSObject;

public final class JSRegExpObject
extends JSNonProxyObject
implements JSCopyableObject {
    private Object compiledRegex;
    private JSObjectFactory groupsFactory;
    private final JSRealm realm;
    private final boolean legacyFeaturesEnabled;

    protected JSRegExpObject(Shape shape, Object compiledRegex, JSObjectFactory groupsFactory, JSRealm realm, boolean legacyFeaturesEnabled) {
        super(shape);
        this.compiledRegex = compiledRegex;
        this.groupsFactory = groupsFactory;
        this.realm = realm;
        this.legacyFeaturesEnabled = legacyFeaturesEnabled;
    }

    public Object getCompiledRegex() {
        return this.compiledRegex;
    }

    public void setCompiledRegex(Object compiledRegex) {
        this.compiledRegex = compiledRegex;
    }

    public JSObjectFactory getGroupsFactory() {
        return this.groupsFactory;
    }

    public void setGroupsFactory(JSObjectFactory groupsFactory) {
        this.groupsFactory = groupsFactory;
    }

    public JSRealm getRealm() {
        return this.realm;
    }

    public boolean getLegacyFeaturesEnabled() {
        return this.legacyFeaturesEnabled;
    }

    @Override
    public TruffleString getClassName() {
        return JSRegExp.CLASS_NAME;
    }

    public static JSRegExpObject create(JSRealm realm, JSObjectFactory factory, Object compiledRegex, JSObjectFactory groupsFactory, boolean legacyFeaturesEnabled) {
        return factory.initProto(new JSRegExpObject(factory.getShape(realm), compiledRegex, groupsFactory, realm, legacyFeaturesEnabled), realm);
    }

    public static JSRegExpObject create(Shape shape, Object compiledRegex, JSRealm realm) {
        return new JSRegExpObject(shape, compiledRegex, null, realm, false);
    }

    @Override
    protected JSObject copyWithoutProperties(Shape shape) {
        return new JSRegExpObject(shape, this.compiledRegex, this.groupsFactory, this.realm, this.legacyFeaturesEnabled);
    }
}

