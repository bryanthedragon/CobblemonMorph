package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.ConstructorBuiltins;
import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;

public interface JSConstructorFactory {
   TruffleString getClassName();

   JSDynamicObject createPrototype(JSRealm realm, JSFunctionObject constructor);

   default JSFunctionObject createConstructorObject(JSRealm realm) {
      return realm.lookupFunction(ConstructorBuiltins.BUILTINS, this.getClassName());
   }

   default void fillConstructor(JSRealm realm, JSDynamicObject constructor) {
   }

   public interface Default extends JSConstructorFactory {
      default JSConstructor createConstructorAndPrototype(JSRealm realm) {
         JSContext ctx = realm.getContext();
         JSFunctionObject constructor = this.createConstructorObject(realm);
         JSDynamicObject prototype = this.createPrototype(realm, constructor);
         JSObjectUtil.putPrototypeData(prototype);
         JSObjectUtil.putConstructorPrototypeProperty(ctx, constructor, prototype);
         this.fillConstructor(realm, constructor);
         return new JSConstructor(constructor, prototype);
      }

      public interface WithSpecies extends JSConstructorFactory.Default {
         @Override
         default void fillConstructor(JSRealm realm, JSDynamicObject constructor) {
            JSNonProxy.putConstructorSpeciesGetter(realm, constructor);
         }
      }
   }

   public interface WithFunctions extends JSConstructorFactory {
      default JSConstructor createConstructorAndPrototype(JSRealm realm, JSBuiltinsContainer functionBuiltins) {
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

   public interface WithFunctionsAndSpecies extends JSConstructorFactory.WithFunctions {
      @Override
      default void fillConstructor(JSRealm realm, JSDynamicObject constructor) {
         JSNonProxy.putConstructorSpeciesGetter(realm, constructor);
      }
   }
}
