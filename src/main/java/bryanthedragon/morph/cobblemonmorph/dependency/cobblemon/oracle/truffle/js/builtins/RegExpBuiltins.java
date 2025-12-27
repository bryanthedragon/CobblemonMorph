package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.Assumption;
import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSFrameUtil;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.JSRegExp;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.TRegexUtil;

public final class RegExpBuiltins extends JSBuiltinsContainer.SwitchEnum<RegExpBuiltins.RegExpBuiltin> {
   public static final TruffleString INPUT = Strings.constant("input");
   public static final JSBuiltinsContainer BUILTINS = new RegExpBuiltins();

   protected RegExpBuiltins() {
      super(JSRegExp.CLASS_NAME, RegExpBuiltins.RegExpBuiltin.class);
   }

   protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, RegExpBuiltins.RegExpBuiltin builtinEnum) {
      switch (builtinEnum) {
         case input:
            return RegExpBuiltinsFactory.JSRegExpStaticResultGetInputNodeGen.create(context, builtin, args().createArgumentNodes(context));
         case set_input:
            return RegExpBuiltinsFactory.JSRegExpStaticResultSetInputNodeGen.create(context, builtin, args().fixedArgs(1).createArgumentNodes(context));
         case lastMatch:
            return RegExpBuiltinsFactory.JSRegExpStaticResultGetGroupNodeGen.create(context, builtin, 0, args().createArgumentNodes(context));
         case lastParen:
            return RegExpBuiltinsFactory.JSRegExpStaticResultLastParenNodeGen.create(context, builtin, args().createArgumentNodes(context));
         case leftContext:
            return RegExpBuiltinsFactory.JSRegExpStaticResultLeftContextNodeGen.create(context, builtin, args().createArgumentNodes(context));
         case rightContext:
            return RegExpBuiltinsFactory.JSRegExpStaticResultRightContextNodeGen.create(context, builtin, args().createArgumentNodes(context));
         case multiline:
            return RegExpBuiltinsFactory.JSRegExpStaticResultMultilineNodeGen.create(context, builtin, args().createArgumentNodes(context));
         case $1:
         case $2:
         case $3:
         case $4:
         case $5:
         case $6:
         case $7:
         case $8:
         case $9:
            return RegExpBuiltinsFactory.JSRegExpStaticResultGetGroupNodeGen.create(
               context, builtin, builtinEnum.name().charAt(1) - '0', args().createArgumentNodes(context)
            );
         default:
            return null;
      }
   }

   static void checkStaticRegexResultPropertyGet(JSContext context, JSRealm realm, Object thisValue) {
      CompilerAsserts.partialEvaluationConstant(context);
      if (!context.isOptionV8CompatibilityMode() && (thisValue != realm.getRegExpConstructor() || realm.isRegexResultInvalidated())) {
         throw Errors.createTypeError("Static RegExp result properties cannot be used with subclasses of RegExp.");
      }
   }

   static void checkStaticRegexResultPropertySet(JSContext context, JSRealm realm, Object thisValue) {
      CompilerAsserts.partialEvaluationConstant(context);
      if (!context.isOptionV8CompatibilityMode() && thisValue != realm.getRegExpConstructor()) {
         throw Errors.createTypeError("Static RegExp result properties cannot be used with subclasses of RegExp.");
      }
   }

   abstract static class GetStaticRegExpResultNode extends JavaScriptBaseNode {
      private final JSContext context;
      @Node.Child
      private TRegexUtil.TRegexCompiledRegexAccessor compiledRegexAccessor = TRegexUtil.TRegexCompiledRegexAccessor.create();

      GetStaticRegExpResultNode(JSContext context) {
         this.context = context;
      }

      static RegExpBuiltins.GetStaticRegExpResultNode create(JSContext context) {
         return RegExpBuiltinsFactory.GetStaticRegExpResultNodeGen.create(context);
      }

      abstract Object execute();

      @Specialization
      Object get() {
         return this.getRealm().getStaticRegexResult(this.context, this.compiledRegexAccessor);
      }
   }

   abstract static class JSRegExpStaticResultGetGroupNode extends RegExpBuiltins.JSRegExpStaticResultPropertyNode {
      private final int groupNumber;

      JSRegExpStaticResultGetGroupNode(JSContext context, JSBuiltin builtin, int groupNumber) {
         super(context, builtin);

         assert groupNumber >= 0;

         this.groupNumber = groupNumber;
      }

      @Specialization
      TruffleString getGroup(VirtualFrame frame, @Cached TruffleString.SubstringByteIndexNode substringNode) {
         JSRealm realm = this.getRealm();
         RegExpBuiltins.checkStaticRegexResultPropertyGet(this.getContext(), realm, JSFrameUtil.getThisObj(frame));
         Object result = this.getResultNode.execute();
         if (this.resultAccessor.isMatch(result) && this.compiledRegexAccessor.groupCount(realm.getStaticRegexResultCompiledRegex()) > this.groupNumber) {
            int start = this.resultAccessor.captureGroupStart(result, this.groupNumber);
            if (start >= 0) {
               return Strings.substring(
                  this.getContext(), substringNode, this.getInput(), start, this.resultAccessor.captureGroupEnd(result, this.groupNumber) - start
               );
            }
         }

         return Strings.EMPTY_STRING;
      }
   }

   abstract static class JSRegExpStaticResultGetInputNode extends JSBuiltinNode {
      JSRegExpStaticResultGetInputNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      TruffleString getInputProp(VirtualFrame frame) {
         RegExpBuiltins.checkStaticRegexResultPropertyGet(this.getContext(), this.getRealm(), JSFrameUtil.getThisObj(frame));
         return this.getRealm().getStaticRegexResultInputString();
      }
   }

   abstract static class JSRegExpStaticResultLastParenNode extends RegExpBuiltins.JSRegExpStaticResultPropertyNode {
      JSRegExpStaticResultLastParenNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      TruffleString lastParen(VirtualFrame frame, @Cached TruffleString.SubstringByteIndexNode substringNode) {
         JSRealm realm = this.getRealm();
         RegExpBuiltins.checkStaticRegexResultPropertyGet(this.getContext(), realm, JSFrameUtil.getThisObj(frame));
         Object result = this.getResultNode.execute();
         if (this.resultAccessor.isMatch(result)) {
            int groupNumber = this.compiledRegexAccessor.groupCount(realm.getStaticRegexResultCompiledRegex()) - 1;
            if (groupNumber > 0) {
               int start = this.resultAccessor.captureGroupStart(result, groupNumber);
               if (start >= 0) {
                  return Strings.substring(
                     this.getContext(), substringNode, this.getInput(), start, this.resultAccessor.captureGroupEnd(result, groupNumber) - start
                  );
               }
            }
         }

         return Strings.EMPTY_STRING;
      }
   }

   abstract static class JSRegExpStaticResultLeftContextNode extends RegExpBuiltins.JSRegExpStaticResultPropertyNode {
      JSRegExpStaticResultLeftContextNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      TruffleString leftContext(VirtualFrame frame, @Cached TruffleString.SubstringByteIndexNode substringNode) {
         RegExpBuiltins.checkStaticRegexResultPropertyGet(this.getContext(), this.getRealm(), JSFrameUtil.getThisObj(frame));
         Object result = this.getResultNode.execute();
         if (this.resultAccessor.isMatch(result)) {
            int start = this.resultAccessor.captureGroupStart(result, 0);
            return Strings.substring(this.getContext(), substringNode, this.getInput(), 0, start);
         } else {
            return Strings.EMPTY_STRING;
         }
      }
   }

   abstract static class JSRegExpStaticResultMultilineNode extends JSBuiltinNode {
      @Node.Child
      private TRegexUtil.TRegexCompiledRegexSingleFlagAccessor multilineAccessor = TRegexUtil.TRegexCompiledRegexSingleFlagAccessor.create("multiline");

      JSRegExpStaticResultMultilineNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization(guards = "getContext().isOptionNashornCompatibilityMode()")
      boolean getMultilineLazyNashorn() {
         return false;
      }

      @Specialization(assumptions = "getStaticResultUnusedAssumption()", guards = "!getContext().isOptionNashornCompatibilityMode()")
      boolean getMultilineLazy() {
         Object compiledRegex = this.getRealm().getStaticRegexResultCompiledRegex();
         return compiledRegex != null ? this.multilineAccessor.get(compiledRegex) : false;
      }

      @Specialization
      boolean getMultilineEager(
         @Cached("createGetResultNode()") RegExpBuiltins.GetStaticRegExpResultNode getResultNode,
         @Cached("create()") TRegexUtil.TRegexResultAccessor resultAccessor
      ) {
         Object compiledRegex = this.getRealm().getStaticRegexResultCompiledRegex();
         Object result = getResultNode.execute();
         return !this.getContext().isOptionNashornCompatibilityMode() && resultAccessor.isMatch(result) ? this.multilineAccessor.get(compiledRegex) : false;
      }

      Assumption getStaticResultUnusedAssumption() {
         return this.getContext().getRegExpStaticResultUnusedAssumption();
      }

      RegExpBuiltins.GetStaticRegExpResultNode createGetResultNode() {
         return RegExpBuiltins.GetStaticRegExpResultNode.create(this.getContext());
      }
   }

   abstract static class JSRegExpStaticResultPropertyNode extends JSBuiltinNode {
      @Node.Child
      RegExpBuiltins.GetStaticRegExpResultNode getResultNode;
      @Node.Child
      TRegexUtil.TRegexCompiledRegexAccessor compiledRegexAccessor;
      @Node.Child
      TRegexUtil.TRegexResultAccessor resultAccessor;

      JSRegExpStaticResultPropertyNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
         this.getResultNode = RegExpBuiltins.GetStaticRegExpResultNode.create(context);
         this.compiledRegexAccessor = TRegexUtil.TRegexCompiledRegexAccessor.create();
         this.resultAccessor = TRegexUtil.TRegexResultAccessor.create();
      }

      TruffleString getInput() {
         return this.getRealm().getStaticRegexResultOriginalInputString();
      }
   }

   abstract static class JSRegExpStaticResultRightContextNode extends RegExpBuiltins.JSRegExpStaticResultPropertyNode {
      JSRegExpStaticResultRightContextNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      TruffleString rightContext(VirtualFrame frame, @Cached TruffleString.SubstringByteIndexNode substringNode) {
         RegExpBuiltins.checkStaticRegexResultPropertyGet(this.getContext(), this.getRealm(), JSFrameUtil.getThisObj(frame));
         Object result = this.getResultNode.execute();
         if (this.resultAccessor.isMatch(result)) {
            int end = this.resultAccessor.captureGroupEnd(result, 0);
            return Strings.substring(this.getContext(), substringNode, this.getInput(), end);
         } else {
            return Strings.EMPTY_STRING;
         }
      }
   }

   abstract static class JSRegExpStaticResultSetInputNode extends JSBuiltinNode {
      @Node.Child
      private JSToStringNode toStringNode = JSToStringNode.create();

      JSRegExpStaticResultSetInputNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      Object setInputProp(VirtualFrame frame, Object val) {
         RegExpBuiltins.checkStaticRegexResultPropertySet(this.getContext(), this.getRealm(), JSFrameUtil.getThisObj(frame));
         this.getRealm().setStaticRegexResultInputString(this.toStringNode.executeString(val));
         return Undefined.instance;
      }
   }

   public static enum RegExpBuiltin implements BuiltinEnum<RegExpBuiltins.RegExpBuiltin> {
      input(0),
      set_input(1, RegExpBuiltins.INPUT),
      lastMatch(0),
      lastParen(0),
      leftContext(0),
      rightContext(0),
      multiline(0),
      $1(0),
      $2(0),
      $3(0),
      $4(0),
      $5(0),
      $6(0),
      $7(0),
      $8(0),
      $9(0);

      private final TruffleString key;
      private final int length;

      private RegExpBuiltin(int length) {
         this.key = Strings.fromJavaString(this.name());
         this.length = length;
      }

      private RegExpBuiltin(int length, TruffleString key) {
         this.key = key;
         this.length = length;
      }

      @Override
      public TruffleString getName() {
         return this.prependAccessorPrefix(this.key);
      }

      public TruffleString getKey() {
         return this.key;
      }

      @Override
      public int getLength() {
         return this.length;
      }

      @Override
      public boolean isGetter() {
         return this.getLength() == 0;
      }

      @Override
      public boolean isSetter() {
         return this.getLength() == 1;
      }
   }
}
