package com.oracle.truffle.js.runtime.builtins.intl;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.relocations.ibm.icu.text.Collator;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.relocations.ibm.icu.text.RuleBasedCollator;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.relocations.ibm.icu.util.ULocale;
import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.object.HiddenKey;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.intl.CollatorFunctionBuiltins;
import com.oracle.truffle.js.builtins.intl.CollatorPrototypeBuiltins;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.access.PropertySetNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JavaScriptRootNode;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.JSConstructor;
import com.oracle.truffle.js.runtime.builtins.JSConstructorFactory;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.builtins.JSFunctionData;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.builtins.JSNonProxy;
import com.oracle.truffle.js.runtime.builtins.JSObjectFactory;
import com.oracle.truffle.js.runtime.builtins.JSOrdinary;
import com.oracle.truffle.js.runtime.builtins.PrototypeSupplier;
import com.oracle.truffle.js.runtime.objects.JSAttributes;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.IntlUtil;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.Arrays;
import java.util.Locale;
import java.util.Locale.Builder;

public final class JSCollator extends JSNonProxy implements JSConstructorFactory.WithFunctions, PrototypeSupplier {
   public static final TruffleString CLASS_NAME = Strings.constant("Collator");
   public static final TruffleString PROTOTYPE_NAME = Strings.constant("Collator.prototype");
   public static final TruffleString TO_STRING_TAG = Strings.constant("Intl.Collator");
   public static final TruffleString GET_COMPARE_NAME = Strings.constant("get compare");
   static final HiddenKey BOUND_OBJECT_KEY = new HiddenKey(Strings.toJavaString(CLASS_NAME));
   public static final JSCollator INSTANCE = new JSCollator();

   private JSCollator() {
   }

   public static boolean isJSCollator(Object obj) {
      return obj instanceof JSCollatorObject;
   }

   @Override
   public TruffleString getClassName() {
      return CLASS_NAME;
   }

   @Override
   public TruffleString getClassName(JSDynamicObject object) {
      return this.getClassName();
   }

   @Override
   public JSDynamicObject createPrototype(JSRealm realm, JSFunctionObject ctor) {
      JSContext ctx = realm.getContext();
      JSObject collatorPrototype = JSObjectUtil.createOrdinaryPrototypeObject(realm);
      JSObjectUtil.putConstructorProperty(ctx, collatorPrototype, ctor);
      JSObjectUtil.putFunctionsFromContainer(realm, collatorPrototype, CollatorPrototypeBuiltins.BUILTINS);
      JSObjectUtil.putBuiltinAccessorProperty(collatorPrototype, Strings.COMPARE, createCompareFunctionGetter(realm, ctx), Undefined.instance);
      JSObjectUtil.putToStringTag(collatorPrototype, TO_STRING_TAG);
      return collatorPrototype;
   }

   @CompilerDirectives.TruffleBoundary
   public static void initializeCollator(
      JSContext ctx,
      JSCollator.InternalState state,
      String[] locales,
      String usage,
      String localeMatcher,
      String optco,
      Boolean optkn,
      String optkf,
      String sensitivity,
      Boolean ignorePunctuation
   ) {
      state.initializedCollator = true;
      state.usage = usage;
      Locale selectedLocale = IntlUtil.selectedLocale(ctx, locales);
      Locale strippedLocale = selectedLocale.stripExtensions();
      Builder builder = new Builder().setLocale(strippedLocale);
      Boolean kn = optkn;
      if (optkn == null) {
         String knType = selectedLocale.getUnicodeLocaleType("kn");
         if ("".equals(knType) || "true".equals(knType)) {
            kn = true;
         } else if ("false".equals(knType)) {
            kn = false;
         }

         if (kn != null) {
            String value = kn ? "" : "false";
            builder.setUnicodeLocaleKeyword("kn", value);
         }
      }

      if (kn != null) {
         state.numeric = kn;
      }

      String kf = optkf;
      if (optkf == null) {
         String kfType = selectedLocale.getUnicodeLocaleType("kf");
         if ("upper".equals(kfType) || "lower".equals(kfType) || "false".equals(kfType)) {
            kf = kfType;
            builder.setUnicodeLocaleKeyword("kf", kfType);
         }
      }

      if (kf != null) {
         state.caseFirst = kf;
      }

      String collation = optco == null ? selectedLocale.getUnicodeLocaleType("co") : optco;
      if (collation != null) {
         String[] validCollations = IntlUtil.availableCollations(ULocale.forLocale(strippedLocale), false);
         if (!Arrays.asList(validCollations).contains(collation)) {
            collation = null;
         }
      }

      boolean searchUsage = "search".equals(usage);
      if (!searchUsage && collation != null) {
         state.collation = collation;
         builder.setUnicodeLocaleKeyword("co", collation);
      }

      if (sensitivity != null) {
         state.sensitivity = sensitivity;
      }

      state.ignorePunctuation = ignorePunctuation;
      Locale collatorLocale = builder.build();
      state.locale = collatorLocale.toLanguageTag();
      if (searchUsage) {
         collatorLocale = builder.setUnicodeLocaleKeyword("co", "search").build();
      }

      state.collator = Collator.getInstance(collatorLocale);
      state.collator.setDecomposition(17);
      String var18 = state.sensitivity;
      switch (var18) {
         case "base":
            state.collator.setStrength(0);
            break;
         case "accent":
            state.collator.setStrength(1);
            break;
         case "case":
            state.collator.setStrength(0);
            if (state.collator instanceof RuleBasedCollator) {
               ((RuleBasedCollator)state.collator).setCaseLevel(true);
            }
            break;
         case "variant":
            state.collator.setStrength(2);
      }

      if (state.ignorePunctuation && state.collator instanceof RuleBasedCollator) {
         ((RuleBasedCollator)state.collator).setAlternateHandlingShifted(true);
      }
   }

   @Override
   public Shape makeInitialShape(JSContext ctx, JSDynamicObject prototype) {
      return JSObjectUtil.getProtoChildShape(prototype, INSTANCE, ctx);
   }

   public static JSConstructor createConstructor(JSRealm realm) {
      return INSTANCE.createConstructorAndPrototype(realm, CollatorFunctionBuiltins.BUILTINS);
   }

   public static JSCollatorObject create(JSContext context, JSRealm realm) {
      JSCollator.InternalState state = new JSCollator.InternalState();
      JSObjectFactory factory = context.getCollatorFactory();
      JSCollatorObject obj = new JSCollatorObject(factory.getShape(realm), state);
      factory.initProto(obj, realm);
      return context.trackAllocation(obj);
   }

   public static Collator getCollatorProperty(JSDynamicObject obj) {
      return getInternalState(obj).collator;
   }

   @CompilerDirectives.TruffleBoundary
   public static int compare(JSDynamicObject collatorObj, String one, String two) {
      Collator collator = getCollatorProperty(collatorObj);
      return collator.compare(normalize(one), normalize(two));
   }

   private static String normalize(String s) {
      return Normalizer.normalize(s, Form.NFD);
   }

   @CompilerDirectives.TruffleBoundary
   public static JSDynamicObject resolvedOptions(JSContext context, JSRealm realm, JSDynamicObject collatorObj) {
      JSCollator.InternalState state = getInternalState(collatorObj);
      return state.toResolvedOptionsObject(context, realm);
   }

   public static JSCollator.InternalState getInternalState(JSDynamicObject collatorObj) {
      assert isJSCollator(collatorObj);

      return ((JSCollatorObject)collatorObj).getInternalState();
   }

   private static CallTarget createGetCompareCallTarget(JSContext context) {
      return (new JavaScriptRootNode(context.getLanguage(), null, null) {
            @Node.Child
            private PropertySetNode setBoundObjectNode = PropertySetNode.createSetHidden(JSCollator.BOUND_OBJECT_KEY, context);
            private final BranchProfile errorBranch = BranchProfile.create();

            @Override
            public Object execute(VirtualFrame frame) {
               Object[] frameArgs = frame.getArguments();
               Object collatorObj = JSArguments.getThisObject(frameArgs);
               if (JSCollator.isJSCollator(collatorObj)) {
                  JSCollator.InternalState state = JSCollator.getInternalState((JSDynamicObject)collatorObj);
                  if (state != null && state.initializedCollator) {
                     if (state.boundCompareFunction == null) {
                        JSFunctionData compareFunctionData = context.getOrCreateBuiltinFunctionData(
                           JSContext.BuiltinFunctionKey.CollatorCompare, c -> JSCollator.createCompareFunctionData(c)
                        );
                        JSDynamicObject compareFn = JSFunction.create(this.getRealm(), compareFunctionData);
                        this.setBoundObjectNode.setValue(compareFn, collatorObj);
                        state.boundCompareFunction = compareFn;
                     }

                     return state.boundCompareFunction;
                  } else {
                     this.errorBranch.enter();
                     throw Errors.createTypeErrorMethodCalledOnNonObjectOrWrongType("compare");
                  }
               } else {
                  this.errorBranch.enter();
                  throw Errors.createTypeErrorTypeXExpected(JSCollator.CLASS_NAME);
               }
            }
         })
         .getCallTarget();
   }

   private static JSFunctionData createCompareFunctionData(JSContext context) {
      return JSFunctionData.createCallOnly(context, (new JavaScriptRootNode(context.getLanguage(), null, null) {
         @Node.Child
         private PropertyGetNode getBoundObjectNode = PropertyGetNode.createGetHidden(JSCollator.BOUND_OBJECT_KEY, context);
         @Node.Child
         private JSToStringNode toString1Node = JSToStringNode.create();
         @Node.Child
         private JSToStringNode toString2Node = JSToStringNode.create();

         @Override
         public Object execute(VirtualFrame frame) {
            Object[] arguments = frame.getArguments();
            JSDynamicObject thisObj = (JSDynamicObject)this.getBoundObjectNode.getValue(JSArguments.getFunctionObject(arguments));

            assert JSCollator.isJSCollator(thisObj);

            int argumentCount = JSArguments.getUserArgumentCount(arguments);
            String one = Strings.toJavaString(argumentCount > 0 ? this.toString1Node.executeString(JSArguments.getUserArgument(arguments, 0)) : Undefined.NAME);
            String two = Strings.toJavaString(argumentCount > 1 ? this.toString2Node.executeString(JSArguments.getUserArgument(arguments, 1)) : Undefined.NAME);
            return JSCollator.compare(thisObj, one, two);
         }
      }).getCallTarget(), 2, Strings.EMPTY_STRING);
   }

   private static JSDynamicObject createCompareFunctionGetter(JSRealm realm, JSContext context) {
      JSFunctionData fd = realm.getContext().getOrCreateBuiltinFunctionData(JSContext.BuiltinFunctionKey.CollatorGetCompare, c -> {
         CallTarget ct = createGetCompareCallTarget(context);
         return JSFunctionData.create(context, ct, ct, 0, GET_COMPARE_NAME, false, false, false, true);
      });
      return JSFunction.create(realm, fd);
   }

   @Override
   public JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
      return realm.getCollatorPrototype();
   }

   public static class InternalState {
      private boolean initializedCollator = false;
      private Collator collator;
      private JSDynamicObject boundCompareFunction = null;
      private String locale;
      private String usage = "sort";
      private String sensitivity = "variant";
      private String collation = "default";
      private boolean ignorePunctuation = false;
      private boolean numeric = false;
      private String caseFirst = "false";

      JSObject toResolvedOptionsObject(JSContext context, JSRealm realm) {
         JSObject result = JSOrdinary.create(context, realm);
         JSObjectUtil.defineDataProperty(context, result, IntlUtil.KEY_LOCALE, Strings.fromJavaString(this.locale), JSAttributes.getDefault());
         JSObjectUtil.defineDataProperty(context, result, IntlUtil.KEY_USAGE, Strings.fromJavaString(this.usage), JSAttributes.getDefault());
         JSObjectUtil.defineDataProperty(context, result, IntlUtil.KEY_SENSITIVITY, Strings.fromJavaString(this.sensitivity), JSAttributes.getDefault());
         JSObjectUtil.defineDataProperty(context, result, IntlUtil.KEY_IGNORE_PUNCTUATION, this.ignorePunctuation, JSAttributes.getDefault());
         JSObjectUtil.defineDataProperty(context, result, IntlUtil.KEY_COLLATION, Strings.fromJavaString(this.collation), JSAttributes.getDefault());
         JSObjectUtil.defineDataProperty(context, result, IntlUtil.KEY_NUMERIC, this.numeric, JSAttributes.getDefault());
         JSObjectUtil.defineDataProperty(context, result, IntlUtil.KEY_CASE_FIRST, Strings.fromJavaString(this.caseFirst), JSAttributes.getDefault());
         return result;
      }
   }
}
