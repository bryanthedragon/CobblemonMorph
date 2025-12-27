package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.api.strings.TruffleStringBuilder;
import com.oracle.truffle.js.builtins.helper.JSONData;
import com.oracle.truffle.js.builtins.helper.JSONStringifyStringNode;
import com.oracle.truffle.js.builtins.helper.TruffleJSONParser;
import com.oracle.truffle.js.nodes.access.CreateDataPropertyNode;
import com.oracle.truffle.js.nodes.cast.JSToIntegerAsIntNode;
import com.oracle.truffle.js.nodes.cast.JSToNumberNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.nodes.unary.IsCallableNode;
import com.oracle.truffle.js.nodes.unary.JSIsArrayNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.JSArray;
import com.oracle.truffle.js.runtime.builtins.JSNumber;
import com.oracle.truffle.js.runtime.builtins.JSOrdinary;
import com.oracle.truffle.js.runtime.builtins.JSString;
import com.oracle.truffle.js.runtime.objects.JSAttributes;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.StringBuilderProfile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class JSONBuiltins extends JSBuiltinsContainer.SwitchEnum<JSONBuiltins.JSON> {
   public static final JSBuiltinsContainer BUILTINS = new JSONBuiltins();

   protected JSONBuiltins() {
      super(com.oracle.truffle.js.runtime.builtins.JSON.CLASS_NAME, JSONBuiltins.JSON.class);
   }

   protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, JSONBuiltins.JSON builtinEnum) {
      switch (builtinEnum) {
         case parse:
            return JSONBuiltinsFactory.JSONParseNodeGen.create(context, builtin, args().fixedArgs(2).createArgumentNodes(context));
         case stringify:
            return JSONBuiltinsFactory.JSONStringifyNodeGen.create(context, builtin, args().fixedArgs(3).createArgumentNodes(context));
         default:
            return null;
      }
   }

   public static enum JSON implements BuiltinEnum<JSONBuiltins.JSON> {
      parse(2),
      stringify(3);

      private final int length;

      private JSON(int length) {
         this.length = length;
      }

      @Override
      public int getLength() {
         return this.length;
      }
   }

   public abstract static class JSONOperation extends JSBuiltinNode {
      @Node.Child
      private JSToStringNode toStringNode;

      public JSONOperation(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      protected TruffleString toString(Object target) {
         if (this.toStringNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.toStringNode = this.insert(JSToStringNode.create());
         }

         return this.toStringNode.executeString(target);
      }

      protected boolean isArray(Object replacer) {
         return JSRuntime.isArray(replacer);
      }
   }

   public abstract static class JSONParseNode extends JSONBuiltins.JSONOperation {
      public JSONParseNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization(guards = "isCallable.executeBoolean(reviver)", limit = "1")
      protected Object parse(Object text, Object reviver, @Cached @Cached.Shared("isCallable") IsCallableNode isCallable) {
         Object unfiltered = this.parseIntl(this.toString(text));
         JSObject root = JSOrdinary.create(this.getContext(), this.getRealm());
         JSObjectUtil.putDataProperty(this.getContext(), root, Strings.EMPTY_STRING, unfiltered, JSAttributes.getDefault());
         return this.walk(reviver, root, Strings.EMPTY_STRING);
      }

      @Specialization(guards = "!isCallable.executeBoolean(reviver)", limit = "1")
      protected Object parseUnfiltered(Object text, Object reviver, @Cached @Cached.Shared("isCallable") IsCallableNode isCallable) {
         return this.parseIntl(this.toString(text));
      }

      @CompilerDirectives.TruffleBoundary(transferToInterpreterOnException = false)
      private Object parseIntl(TruffleString jsonString) {
         return new TruffleJSONParser(this.getContext()).parse(jsonString, this.getRealm());
      }

      @CompilerDirectives.TruffleBoundary
      private Object walk(Object reviverFn, JSObject holder, Object property) {
         Object value = JSObject.get(holder, property);
         if (JSRuntime.isObject(value)) {
            JSObject object = (JSObject)value;
            if (this.isArray(object)) {
               int len = (int)JSRuntime.toLength(JSObject.get(object, JSArray.LENGTH));

               for (int i = 0; i < len; i++) {
                  Object stringIndex = Strings.fromInt(i);
                  Object newElement = this.walk(reviverFn, object, stringIndex);
                  if (newElement == Undefined.instance) {
                     JSObject.delete(object, i);
                  } else {
                     JSRuntime.createDataProperty(object, stringIndex, newElement);
                  }
               }
            } else {
               for (Object p : JSObject.enumerableOwnNames(object)) {
                  Object newElement = this.walk(reviverFn, object, p);
                  if (newElement == Undefined.instance) {
                     JSObject.delete(object, p);
                  } else {
                     JSRuntime.createDataProperty(object, p, newElement);
                  }
               }
            }
         }

         return JSRuntime.call(reviverFn, holder, new Object[]{property, value});
      }
   }

   public abstract static class JSONStringifyNode extends JSONBuiltins.JSONOperation {
      @Node.Child
      private JSONStringifyStringNode jsonStringifyStringNode;
      @Node.Child
      private CreateDataPropertyNode createWrapperPropertyNode;
      @Node.Child
      private JSToIntegerAsIntNode toIntegerNode;
      @Node.Child
      private JSToNumberNode toNumberNode;
      @Node.Child
      private JSIsArrayNode isArrayNode;
      @Node.Child
      private IsCallableNode isCallableNode;
      private final BranchProfile spaceIsStringBranch = BranchProfile.create();
      private final ConditionProfile spaceIsUndefinedProfile = ConditionProfile.createBinaryProfile();

      public JSONStringifyNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      protected Object jsonStr(Object jsonData, Object keyStr, JSDynamicObject holder) {
         if (this.jsonStringifyStringNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.jsonStringifyStringNode = this.insert(JSONStringifyStringNode.create(this.getContext()));
         }

         return this.jsonStringifyStringNode.execute(jsonData, keyStr, holder);
      }

      @Override
      protected boolean isArray(Object replacer) {
         if (this.isArrayNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.isArrayNode = this.insert(JSIsArrayNode.createIsArrayLike());
         }

         return this.isArrayNode.execute(replacer);
      }

      protected boolean isCallable(Object obj) {
         if (this.isCallableNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.isCallableNode = this.insert(IsCallableNode.create());
         }

         return this.isCallableNode.executeBoolean(obj);
      }

      @Specialization(guards = "isCallable(replacerFn)")
      protected Object stringify(Object value, Object replacerFn, Object spaceParam) {
         assert JSRuntime.isCallable(replacerFn);

         return this.stringifyIntl(value, spaceParam, replacerFn, null);
      }

      @Specialization(guards = "isArray(replacerObj)")
      protected Object stringifyReplacerArray(Object value, JSDynamicObject replacerObj, Object spaceParam) {
         int len = (int)JSRuntime.toLength(JSObject.get(replacerObj, JSArray.LENGTH));
         List<Object> replacerList = new ArrayList<>();

         for (int i = 0; i < len; i++) {
            Object v = JSObject.get(replacerObj, JSRuntime.toString(i));
            Object item = null;
            if (Strings.isTString(v)) {
               item = JSRuntime.toStringIsString(v);
            } else if (JSRuntime.isNumber(v) || JSNumber.isJSNumber(v) || JSString.isJSString(v)) {
               item = this.toString(v);
            }

            if (item != null) {
               addToReplacer(replacerList, item);
            }
         }

         return this.stringifyIntl(value, spaceParam, null, replacerList);
      }

      @CompilerDirectives.TruffleBoundary
      private static void addToReplacer(List<Object> replacerList, Object item) {
         if (!replacerList.contains(item)) {
            replacerList.add(item);
         }
      }

      @Specialization(guards = {"!isCallable(replacer)", "!isArray(replacer)"})
      protected Object stringifyAStringNoReplacer(
         TruffleString str,
         Object replacer,
         Object spaceParam,
         @Cached("createStringBuilderProfile()") StringBuilderProfile stringBuilderProfile,
         @Cached TruffleStringBuilder.AppendCharUTF16Node appendRawValueNode,
         @Cached TruffleStringBuilder.AppendStringNode appendStringNode,
         @Cached TruffleStringBuilder.ToStringNode builderToStringNode
      ) {
         TruffleStringBuilder builder = Strings.builderCreate(Strings.length(str) + 8);
         JSONStringifyStringNode.jsonQuote(stringBuilderProfile, builder, str, appendRawValueNode, appendStringNode);
         return StringBuilderProfile.toString(builderToStringNode, builder);
      }

      protected StringBuilderProfile createStringBuilderProfile() {
         return StringBuilderProfile.create(this.getContext().getStringLengthLimit());
      }

      @Specialization(guards = {"!isString(value)", "!isCallable(replacer)", "!isArray(replacer)"})
      protected Object stringifyNoReplacer(Object value, Object replacer, Object spaceParam) {
         return this.stringifyIntl(value, spaceParam, null, null);
      }

      private Object stringifyIntl(Object value, Object spaceParam, Object replacerFnObj, List<Object> replacerList) {
         TruffleString gap = this.spaceIsUndefinedProfile.profile(spaceParam == Undefined.instance) ? Strings.EMPTY_STRING : this.getGap(spaceParam);
         JSDynamicObject wrapper = JSOrdinary.create(this.getContext(), this.getRealm());
         if (this.createWrapperPropertyNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.createWrapperPropertyNode = this.insert(CreateDataPropertyNode.create(this.getContext(), Strings.EMPTY_STRING));
         }

         this.createWrapperPropertyNode.executeVoid(wrapper, value);
         return this.jsonStr(new JSONData(gap, replacerFnObj, replacerList), Strings.EMPTY_STRING, wrapper);
      }

      private TruffleString getGap(Object spaceParam) {
         Object space = spaceParam;
         if (JSDynamicObject.isJSDynamicObject(spaceParam)) {
            if (JSNumber.isJSNumber(spaceParam)) {
               space = this.toNumber(spaceParam);
            } else if (JSString.isJSString(spaceParam)) {
               space = this.toString(spaceParam);
            }
         }

         if (JSRuntime.isNumber(space)) {
            if (this.toIntegerNode == null) {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               this.toIntegerNode = this.insert(JSToIntegerAsIntNode.create());
            }

            int newSpace = Math.max(0, Math.min(10, this.toIntegerNode.executeInt(space)));
            return makeGap(newSpace);
         } else if (Strings.isTString(space)) {
            this.spaceIsStringBranch.enter();
            return this.makeGap(JSRuntime.toStringIsString(space));
         } else {
            return Strings.EMPTY_STRING;
         }
      }

      @CompilerDirectives.TruffleBoundary
      private TruffleString makeGap(TruffleString spaceStr) {
         return Strings.length(spaceStr) <= 10 ? spaceStr : Strings.substring(this.getContext(), spaceStr, 0, 10);
      }

      @CompilerDirectives.TruffleBoundary
      private static TruffleString makeGap(int spaceValue) {
         char[] ar = new char[spaceValue];
         Arrays.fill(ar, ' ');
         return Strings.fromCharArray(ar);
      }

      protected Number toNumber(Object target) {
         if (this.toNumberNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.toNumberNode = this.insert(JSToNumberNode.create());
         }

         return this.toNumberNode.executeNumber(target);
      }
   }
}
