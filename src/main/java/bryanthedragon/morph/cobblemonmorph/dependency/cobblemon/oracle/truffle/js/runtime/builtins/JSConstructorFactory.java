
package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.ConstructorBuiltins;
import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.builtins.JSConstructor;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.builtins.JSNonProxy;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;

public interface JSConstructorFactory {
    public TruffleString getClassName();

    public JSDynamicObject createPrototype(JSRealm var1, JSFunctionObject var2);

    default public JSFunctionObject createConstructorObject(JSRealm realm) {
        return realm.lookupFunction(ConstructorBuiltins.BUILTINS, this.getClassName());
    }

    default public void fillConstructor(JSRealm realm, JSDynamicObject constructor) {
    }

    public static interface WithFunctionsAndSpecies
    extends WithFunctions {
        @Override
        default public void fillConstructor(JSRealm realm, JSDynamicObject constructor) {
            JSNonProxy.putConstructorSpeciesGetter(realm, constructor);
        }
    }

    public static interface WithFunctions
    extends JSConstructorFactory {
        default public JSConstructor createConstructorAndPrototype(JSRealm realm, JSBuiltinsContainer functionBuiltins) {
            JSContext ctx = realm.getContext();
            JSFunctionObject constructor = this.createConstructorObject(realm);
            JSDynamicObject prototype = this.createPrototype(realm, constructor);
            JSObjectUtil.putPrototypeData(prototype);
            JSObjectUtil.putConstructorPrototypeProperty(ctx, constructor, prototype);
            JSObjectUtil.putFunctionsFromContainer(realm, constructor, functionBuiltins);
            this.fillConstructor(realm, constructor);
            return new JSConstructor(constructor, prototype);
        }
    }

    public static interface Default
    extends JSConstructorFactory {
        default public JSConstructor createConstructorAndPrototype(JSRealm realm) {
            JSContext ctx = realm.getContext();
            JSFunctionObject constructor = this.createConstructorObject(realm);
            JSDynamicObject prototype = this.createPrototype(realm, constructor);
            JSObjectUtil.putPrototypeData(prototype);
            JSObjectUtil.putConstructorPrototypeProperty(ctx, constructor, prototype);
            this.fillConstructor(realm, constructor);
            return new JSConstructor(constructor, prototype);
        }

        public static interface WithSpecies
        extends Default {
            @Override
            default public void fillConstructor(JSRealm realm, JSDynamicObject constructor) {
                JSNonProxy.putConstructorSpeciesGetter(realm, constructor);
            }
        }
    }
}

