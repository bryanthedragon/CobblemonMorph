package com.oracle.truffle.js.builtins.intl;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.nodes.intl.JSToCanonicalizedLocaleListNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.JSArray;
import com.oracle.truffle.js.runtime.builtins.intl.JSIntl;
import com.oracle.truffle.js.runtime.util.IntlUtil;

public final class IntlBuiltins extends JSBuiltinsContainer.SwitchEnum<IntlBuiltins.Intl> {
   public static final JSBuiltinsContainer BUILTINS = new IntlBuiltins();

   protected IntlBuiltins() {
      super(JSIntl.CLASS_NAME, IntlBuiltins.Intl.class);
   }

   protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, IntlBuiltins.Intl builtinEnum) {
      switch (builtinEnum) {
         case getCanonicalLocales:
            return IntlBuiltinsFactory.GetCanonicalLocalesNodeGen.create(context, builtin, args().fixedArgs(1).createArgumentNodes(context));
         case supportedValuesOf:
            return IntlBuiltinsFactory.SupportedValuesOfNodeGen.create(context, builtin, args().fixedArgs(1).createArgumentNodes(context));
         default:
            return null;
      }
   }

   public abstract static class GetCanonicalLocalesNode extends JSBuiltinNode {
      @Node.Child
      JSToCanonicalizedLocaleListNode canonicalizeLocaleListNode;

      public GetCanonicalLocalesNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      @CompilerDirectives.TruffleBoundary
      protected Object getCanonicalLocales(Object locales) {
         if (this.canonicalizeLocaleListNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.canonicalizeLocaleListNode = this.insert(JSToCanonicalizedLocaleListNode.create(this.getContext()));
         }

         String[] languageTags = this.canonicalizeLocaleListNode.executeLanguageTags(locales);
         return JSArray.createConstant(this.getContext(), this.getRealm(), Strings.convertJavaStringArray(languageTags));
      }
   }

   public static enum Intl implements BuiltinEnum<IntlBuiltins.Intl> {
      getCanonicalLocales(1),
      supportedValuesOf(1);

      private final int length;

      private Intl(int length) {
         this.length = length;
      }

      @Override
      public int getLength() {
         return this.length;
      }

      @Override
      public int getECMAScriptVersion() {
         switch (this) {
            case getCanonicalLocales:
               return 7;
            case supportedValuesOf:
               return 14;
            default:
               return BuiltinEnum.super.getECMAScriptVersion();
         }
      }
   }

   public abstract static class SupportedValuesOfNode extends JSBuiltinNode {
      public SupportedValuesOfNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      protected Object supportedValuesOf(Object keyArg, @Cached JSToStringNode toStringNode, @Cached BranchProfile errorBranch) {
         TruffleString key = toStringNode.executeString(keyArg);
         String[] list;
         if (Strings.equals(IntlUtil.KEY_CALENDAR, key)) {
            list = IntlUtil.availableCalendars();
         } else if (Strings.equals(IntlUtil.KEY_COLLATION, key)) {
            list = IntlUtil.availableCollations();
         } else if (Strings.equals(IntlUtil.KEY_CURRENCY, key)) {
            list = IntlUtil.availableCurrencies();
         } else if (Strings.equals(IntlUtil.KEY_NUMBERING_SYSTEM, key)) {
            list = IntlUtil.availableNumberingSystems(this.getContext());
         } else if (Strings.equals(IntlUtil.KEY_TIME_ZONE, key)) {
            list = IntlUtil.availableTimeZones();
         } else {
            if (!Strings.equals(IntlUtil.KEY_UNIT, key)) {
               errorBranch.enter();
               throw Errors.createRangeErrorFormat("Invalid key : %s", this, key);
            }

            list = IntlUtil.availableUnits();
         }

         return JSArray.createConstant(this.getContext(), this.getRealm(), Strings.convertJavaStringArray(list));
      }
   }
}
