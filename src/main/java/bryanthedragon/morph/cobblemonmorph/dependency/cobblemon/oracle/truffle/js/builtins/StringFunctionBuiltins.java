package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.api.strings.TruffleStringBuilder;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.access.ReadElementNode;
import com.oracle.truffle.js.nodes.array.JSGetLengthNode;
import com.oracle.truffle.js.nodes.cast.JSToNumberNode;
import com.oracle.truffle.js.nodes.cast.JSToObjectNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.nodes.cast.JSToUInt16Node;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.JSString;

public final class StringFunctionBuiltins extends JSBuiltinsContainer.SwitchEnum<StringFunctionBuiltins.StringFunction> {
   public static final JSBuiltinsContainer BUILTINS = new StringFunctionBuiltins();

   protected StringFunctionBuiltins() {
      super(JSString.CLASS_NAME, StringFunctionBuiltins.StringFunction.class);
   }

   protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, StringFunctionBuiltins.StringFunction builtinEnum) {
      switch (builtinEnum) {
         case fromCharCode:
            return StringFunctionBuiltinsFactory.JSFromCharCodeNodeGen.create(context, builtin, args().varArgs().createArgumentNodes(context));
         case fromCodePoint:
            return StringFunctionBuiltinsFactory.JSFromCodePointNodeGen.create(context, builtin, args().varArgs().createArgumentNodes(context));
         case raw:
            return StringFunctionBuiltinsFactory.StringRawNodeGen.create(context, builtin, args().fixedArgs(1).varArgs().createArgumentNodes(context));
         default:
            return null;
      }
   }

   public abstract static class JSFromCharCodeNode extends NumberPrototypeBuiltins.JSNumberOperation {
      @Node.Child
      private JSToUInt16Node toUInt16Node;

      public JSFromCharCodeNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      private char toChar(Object target) {
         if (this.toUInt16Node == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.toUInt16Node = this.insert(JSToUInt16Node.create());
         }

         return (char)this.toUInt16Node.executeInt(target);
      }

      @Specialization(guards = "args.length == 0")
      protected Object fromCharCode(Object[] args) {
         return Strings.EMPTY_STRING;
      }

      @Specialization(guards = "args.length == 1")
      protected Object fromCharCodeOneArg(Object[] args, @Cached TruffleString.FromCodePointNode fromCodePointNode) {
         return Strings.fromCodePoint(fromCodePointNode, this.toChar(args[0]));
      }

      @Specialization(guards = "args.length >= 2")
      protected Object fromCharCodeTwoOrMore(Object[] args, @Cached TruffleString.FromCharArrayUTF16Node fromCharArrayNode) {
         char[] chars = new char[args.length];

         for (int i = 0; i < args.length; i++) {
            chars[i] = this.toChar(args[i]);
         }

         return Strings.fromCharArray(fromCharArrayNode, chars);
      }
   }

   public abstract static class JSFromCodePointNode extends JSBuiltinNode {
      public JSFromCodePointNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      protected Object fromCodePoint(
         Object[] args,
         @Cached JSToNumberNode toNumberNode,
         @Cached TruffleString.FromCodePointNode fromCodePointNode,
         @Cached TruffleString.ConcatNode concatNode
      ) {
         TruffleString st = Strings.EMPTY_STRING;

         for (Object arg : args) {
            Number value = toNumberNode.executeNumber(arg);
            double valueDouble = JSRuntime.doubleValue(value);
            int valueInt = JSRuntime.intValue(value);
            if (JSRuntime.isNegativeZero(valueDouble)) {
               valueInt = 0;
            } else if (!JSRuntime.doubleIsRepresentableAsInt(valueDouble) || valueInt < 0 || 1114111 < valueInt) {
               throwRangeError(value);
            }

            st = Strings.concat(concatNode, st, Strings.fromCodePoint(fromCodePointNode, valueInt));
         }

         return st;
      }

      @CompilerDirectives.TruffleBoundary
      private static void throwRangeError(Number value) {
         throw Errors.createRangeError("Invalid code point " + value);
      }
   }

   public static enum StringFunction implements BuiltinEnum<StringFunctionBuiltins.StringFunction> {
      fromCharCode(1),
      fromCodePoint(1),
      raw(1);

      private final int length;

      private StringFunction(int length) {
         this.length = length;
      }

      @Override
      public int getLength() {
         return this.length;
      }

      @Override
      public int getECMAScriptVersion() {
         return this == fromCodePoint ? 6 : BuiltinEnum.super.getECMAScriptVersion();
      }
   }

   public abstract static class StringRawNode extends JSBuiltinNode {
      @Node.Child
      private JSToObjectNode templateToObjectNode;
      @Node.Child
      private JSToObjectNode rawToObjectNode;
      @Node.Child
      private PropertyGetNode getRawNode;
      @Node.Child
      private JSGetLengthNode getRawLengthNode;
      @Node.Child
      private JSToStringNode segToStringNode;
      @Node.Child
      private JSToStringNode subToStringNode;
      @Node.Child
      private ReadElementNode readRawElementNode;
      @Node.Child
      private TruffleStringBuilder.AppendStringNode appendStringNode;
      @Node.Child
      private TruffleStringBuilder.ToStringNode builderToStringNode;
      private final ConditionProfile emptyProf = ConditionProfile.createBinaryProfile();

      public StringRawNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
         this.templateToObjectNode = JSToObjectNode.createToObject(context);
         this.rawToObjectNode = JSToObjectNode.createToObject(context);
         this.getRawNode = PropertyGetNode.create(Strings.RAW, false, context);
         this.getRawLengthNode = JSGetLengthNode.create(context);
         this.segToStringNode = JSToStringNode.create();
         this.subToStringNode = JSToStringNode.create();
         this.readRawElementNode = ReadElementNode.create(context);
         this.appendStringNode = TruffleStringBuilder.AppendStringNode.create();
         this.builderToStringNode = TruffleStringBuilder.ToStringNode.create();
      }

      @Specialization
      protected Object raw(Object template, Object[] substitutions) {
         int numberOfSubstitutions = substitutions.length;
         Object cooked = this.templateToObjectNode.execute(template);
         Object raw = this.rawToObjectNode.execute(this.getRawNode.getValue(cooked));
         int literalSegments = this.getRawLength(raw);
         if (this.emptyProf.profile(literalSegments <= 0)) {
            return Strings.EMPTY_STRING;
         } else {
            TruffleStringBuilder result = Strings.builderCreate();
            int i = 0;

            while (true) {
               Object rawElement = this.readRawElementNode.executeWithTargetAndIndex(raw, i);
               TruffleString nextSeg = this.segToStringNode.executeString(rawElement);
               this.appendChecked(result, nextSeg);
               if (i + 1 == literalSegments) {
                  return Strings.builderToString(this.builderToStringNode, result);
               }

               if (i < numberOfSubstitutions) {
                  TruffleString nextSub = this.subToStringNode.executeString(substitutions[i]);
                  this.appendChecked(result, nextSub);
               }

               i++;
            }
         }
      }

      private int getRawLength(Object raw) {
         long length = this.getRawLengthNode.executeLong(raw);

         try {
            return Math.toIntExact(length);
         } catch (ArithmeticException var5) {
            return 0;
         }
      }

      private void appendChecked(TruffleStringBuilder result, TruffleString str) {
         if (Strings.builderLength(result) + Strings.length(str) > this.getContext().getStringLengthLimit()) {
            CompilerDirectives.transferToInterpreter();
            throw Errors.createRangeErrorInvalidStringLength();
         } else {
            Strings.builderAppend(this.appendStringNode, result, str);
         }
      }
   }
}
