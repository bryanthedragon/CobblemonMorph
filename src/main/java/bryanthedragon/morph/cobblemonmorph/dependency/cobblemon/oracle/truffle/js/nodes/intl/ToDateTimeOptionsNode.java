package com.oracle.truffle.js.nodes.intl;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.access.CreateObjectNode;
import com.oracle.truffle.js.nodes.cast.JSToObjectNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.JSOrdinary;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.IntlUtil;

public abstract class ToDateTimeOptionsNode extends JavaScriptBaseNode {
   private static final String ALL = "all";
   private static final String ANY = "any";
   private static final String DATE = "date";
   private static final String TIME = "time";
   @Node.Child
   JSToObjectNode toObjectNode;
   final JSContext context;

   public JSContext getContext() {
      return this.context;
   }

   public ToDateTimeOptionsNode(JSContext context) {
      this.context = context;
   }

   public abstract JSDynamicObject execute(Object opts, String required, String defaults);

   @Specialization(guards = "isUndefined(opts)")
   public JSDynamicObject fromUndefined(Object opts, String required, String defaults) {
      return setDefaultsIfNeeded(JSOrdinary.createWithNullPrototype(this.getContext()), required, defaults);
   }

   @Specialization(guards = "!isUndefined(opts)")
   public JSDynamicObject fromOtherThenUndefined(
      Object opts,
      String required,
      String defaults,
      @Cached("createOrdinaryWithPrototype(context)") CreateObjectNode.CreateObjectWithPrototypeNode createObjectNode
   ) {
      JSDynamicObject options = createObjectNode.execute(this.toDynamicObject(opts));
      return setDefaultsIfNeeded(options, required, defaults);
   }

   private static JSDynamicObject setDefaultsIfNeeded(JSDynamicObject options, String required, String defaults) {
      boolean needDefaults = true;
      if (required != null) {
         if ("date".equals(required) || "any".equals(required)) {
            needDefaults &= JSGuards.isUndefined(JSObject.get(options, Strings.WEEKDAY));
            needDefaults &= JSGuards.isUndefined(JSObject.get(options, Strings.YEAR));
            needDefaults &= JSGuards.isUndefined(JSObject.get(options, Strings.MONTH));
            needDefaults &= JSGuards.isUndefined(JSObject.get(options, Strings.DAY));
         }

         if ("time".equals(required) || "any".equals(required)) {
            needDefaults &= JSGuards.isUndefined(JSObject.get(options, IntlUtil.KEY_DAY_PERIOD));
            needDefaults &= JSGuards.isUndefined(JSObject.get(options, Strings.HOUR));
            needDefaults &= JSGuards.isUndefined(JSObject.get(options, Strings.MINUTE));
            needDefaults &= JSGuards.isUndefined(JSObject.get(options, Strings.SECOND));
            needDefaults &= JSGuards.isUndefined(JSObject.get(options, IntlUtil.KEY_FRACTIONAL_SECOND_DIGITS));
         }
      }

      Object dateStyle = JSObject.get(options, Strings.DATE_STYLE);
      Object timeStyle = JSObject.get(options, Strings.TIME_STYLE);
      if (dateStyle != Undefined.instance || timeStyle != Undefined.instance) {
         needDefaults = false;
      }

      if ("date".equals(required) && timeStyle != Undefined.instance) {
         throw Errors.createTypeError("timeStyle option is not allowed here");
      } else if ("time".equals(required) && dateStyle != Undefined.instance) {
         throw Errors.createTypeError("dateStyle option is not allowed here");
      } else {
         if (defaults != null) {
            if (needDefaults && ("date".equals(defaults) || "all".equals(defaults))) {
               JSRuntime.createDataPropertyOrThrow(options, Strings.YEAR, Strings.NUMERIC);
               JSRuntime.createDataPropertyOrThrow(options, Strings.MONTH, Strings.NUMERIC);
               JSRuntime.createDataPropertyOrThrow(options, Strings.DAY, Strings.NUMERIC);
            }

            if (needDefaults && ("time".equals(defaults) || "all".equals(defaults))) {
               JSRuntime.createDataPropertyOrThrow(options, Strings.HOUR, Strings.NUMERIC);
               JSRuntime.createDataPropertyOrThrow(options, Strings.MINUTE, Strings.NUMERIC);
               JSRuntime.createDataPropertyOrThrow(options, Strings.SECOND, Strings.NUMERIC);
            }
         }

         return options;
      }
   }

   private JSDynamicObject toDynamicObject(Object o) {
      if (this.toObjectNode == null) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.toObjectNode = this.insert(JSToObjectNode.createToObject(this.getContext()));
      }

      return (JSDynamicObject)this.toObjectNode.execute(o);
   }
}
