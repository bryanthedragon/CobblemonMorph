package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleSafepoint;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.api.strings.TruffleStringBuilder;
import com.oracle.truffle.js.builtins.helper.JSRegExpExecIntlNode;
import com.oracle.truffle.js.builtins.helper.ReplaceStringParser;
import com.oracle.truffle.js.nodes.CompileRegexNode;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.access.CreateObjectNode;
import com.oracle.truffle.js.nodes.access.GetMethodNode;
import com.oracle.truffle.js.nodes.access.IsRegExpNode;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.access.PropertySetNode;
import com.oracle.truffle.js.nodes.access.RequireObjectCoercibleNode;
import com.oracle.truffle.js.nodes.cast.JSToIntegerAsIntNode;
import com.oracle.truffle.js.nodes.cast.JSToNumberNode;
import com.oracle.truffle.js.nodes.cast.JSToRegExpNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.nodes.cast.JSToUInt32Node;
import com.oracle.truffle.js.nodes.cast.JSTrimWhitespaceNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.nodes.intl.CreateRegExpNode;
import com.oracle.truffle.js.nodes.intl.InitializeCollatorNode;
import com.oracle.truffle.js.nodes.intl.JSToCanonicalizedLocaleListNode;
import com.oracle.truffle.js.nodes.unary.IsCallableNode;
import com.oracle.truffle.js.runtime.Boundaries;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.JSArray;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.builtins.JSRegExp;
import com.oracle.truffle.js.runtime.builtins.JSRegExpObject;
import com.oracle.truffle.js.runtime.builtins.JSString;
import com.oracle.truffle.js.runtime.builtins.intl.JSCollator;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.Null;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.IntlUtil;
import com.oracle.truffle.js.runtime.util.SimpleArrayList;
import com.oracle.truffle.js.runtime.util.StringBuilderProfile;
import com.oracle.truffle.js.runtime.util.TRegexUtil;
import java.text.Collator;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Locale;

public final class StringPrototypeBuiltins extends JSBuiltinsContainer.SwitchEnum<StringPrototypeBuiltins.StringPrototype> {
   public static final JSBuiltinsContainer BUILTINS = new StringPrototypeBuiltins();
   public static final JSBuiltinsContainer EXTENSION_BUILTINS = new StringPrototypeBuiltins.StringPrototypeExtensionBuiltins();

   protected StringPrototypeBuiltins() {
      super(JSString.PROTOTYPE_NAME, StringPrototypeBuiltins.StringPrototype.class);
   }

   protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, StringPrototypeBuiltins.StringPrototype builtinEnum) {
      switch (builtinEnum) {
         case charAt:
            return StringPrototypeBuiltinsFactory.JSStringCharAtNodeGen.create(context, builtin, args().withThis().fixedArgs(1).createArgumentNodes(context));
         case charCodeAt:
            return StringPrototypeBuiltinsFactory.JSStringCharCodeAtNodeGen.create(
               context, builtin, args().withThis().fixedArgs(1).createArgumentNodes(context)
            );
         case concat:
            return StringPrototypeBuiltinsFactory.JSStringConcatNodeGen.create(context, builtin, args().withThis().varArgs().createArgumentNodes(context));
         case indexOf:
            return StringPrototypeBuiltinsFactory.JSStringIndexOfNodeGen.create(context, builtin, args().withThis().fixedArgs(2).createArgumentNodes(context));
         case lastIndexOf:
            return StringPrototypeBuiltinsFactory.JSStringLastIndexOfNodeGen.create(
               context, builtin, args().withThis().fixedArgs(2).varArgs().createArgumentNodes(context)
            );
         case localeCompare:
            if (context.isOptionIntl402()) {
               return StringPrototypeBuiltinsFactory.JSStringLocaleCompareIntlNodeGen.create(
                  context, builtin, args().withThis().fixedArgs(3).createArgumentNodes(context)
               );
            }

            return StringPrototypeBuiltinsFactory.JSStringLocaleCompareNodeGen.create(
               context, builtin, args().withThis().fixedArgs(1).createArgumentNodes(context)
            );
         case match:
            if (context.getEcmaScriptVersion() >= 6) {
               return StringPrototypeBuiltinsFactory.JSStringMatchNodeGen.create(
                  context, builtin, false, args().withThis().fixedArgs(1).createArgumentNodes(context)
               );
            }

            return StringPrototypeBuiltinsFactory.JSStringMatchES5NodeGen.create(context, builtin, args().withThis().fixedArgs(1).createArgumentNodes(context));
         case replace:
            if (context.getEcmaScriptVersion() >= 6) {
               return StringPrototypeBuiltinsFactory.JSStringReplaceNodeGen.create(
                  context, builtin, args().withThis().fixedArgs(2).createArgumentNodes(context)
               );
            }

            return StringPrototypeBuiltinsFactory.JSStringReplaceES5NodeGen.create(
               context, builtin, args().withThis().fixedArgs(2).createArgumentNodes(context)
            );
         case replaceAll:
            return StringPrototypeBuiltinsFactory.JSStringReplaceAllNodeGen.create(
               context, builtin, args().withThis().fixedArgs(2).createArgumentNodes(context)
            );
         case search:
            if (context.getEcmaScriptVersion() >= 6) {
               return StringPrototypeBuiltinsFactory.JSStringSearchNodeGen.create(context, builtin, args().withThis().fixedArgs(1).createArgumentNodes(context));
            }

            return StringPrototypeBuiltinsFactory.JSStringSearchES5NodeGen.create(context, builtin, args().withThis().varArgs().createArgumentNodes(context));
         case slice:
            return StringPrototypeBuiltinsFactory.JSStringSliceNodeGen.create(context, builtin, args().withThis().fixedArgs(2).createArgumentNodes(context));
         case split:
            return StringPrototypeBuiltinsFactory.JSStringSplitNodeGen.create(context, builtin, args().withThis().fixedArgs(2).createArgumentNodes(context));
         case substr:
            return StringPrototypeBuiltinsFactory.JSStringSubstrNodeGen.create(context, builtin, args().withThis().fixedArgs(2).createArgumentNodes(context));
         case substring:
            return StringPrototypeBuiltinsFactory.JSStringSubstringNodeGen.create(context, builtin, args().withThis().fixedArgs(2).createArgumentNodes(context));
         case toLowerCase:
            return StringPrototypeBuiltinsFactory.JSStringToLowerCaseNodeGen.create(context, builtin, false, args().withThis().createArgumentNodes(context));
         case toLocaleLowerCase:
            if (context.isOptionIntl402()) {
               return StringPrototypeBuiltinsFactory.JSStringToLocaleLowerCaseIntlNodeGen.create(
                  context, builtin, args().withThis().fixedArgs(1).createArgumentNodes(context)
               );
            }

            return StringPrototypeBuiltinsFactory.JSStringToLowerCaseNodeGen.create(context, builtin, true, args().withThis().createArgumentNodes(context));
         case toUpperCase:
            return StringPrototypeBuiltinsFactory.JSStringToUpperCaseNodeGen.create(context, builtin, false, args().withThis().createArgumentNodes(context));
         case toLocaleUpperCase:
            if (context.isOptionIntl402()) {
               return StringPrototypeBuiltinsFactory.JSStringToLocaleUpperCaseIntlNodeGen.create(
                  context, builtin, args().withThis().fixedArgs(1).createArgumentNodes(context)
               );
            }

            return StringPrototypeBuiltinsFactory.JSStringToUpperCaseNodeGen.create(context, builtin, true, args().withThis().createArgumentNodes(context));
         case toString:
            return StringPrototypeBuiltinsFactory.JSStringToStringNodeGen.create(context, builtin, args().withThis().createArgumentNodes(context));
         case valueOf:
            return StringPrototypeBuiltinsFactory.JSStringToStringNodeGen.create(context, builtin, args().withThis().createArgumentNodes(context));
         case trim:
            return StringPrototypeBuiltinsFactory.JSStringTrimNodeGen.create(context, builtin, args().withThis().createArgumentNodes(context));
         case startsWith:
            return StringPrototypeBuiltinsFactory.JSStringStartsWithNodeGen.create(
               context, builtin, args().withThis().fixedArgs(2).createArgumentNodes(context)
            );
         case endsWith:
            return StringPrototypeBuiltinsFactory.JSStringEndsWithNodeGen.create(context, builtin, args().withThis().fixedArgs(2).createArgumentNodes(context));
         case includes:
            return StringPrototypeBuiltinsFactory.JSStringIncludesNodeGen.create(context, builtin, args().withThis().fixedArgs(2).createArgumentNodes(context));
         case repeat:
            return StringPrototypeBuiltinsFactory.JSStringRepeatNodeGen.create(context, builtin, args().withThis().fixedArgs(1).createArgumentNodes(context));
         case codePointAt:
            return StringPrototypeBuiltinsFactory.JSStringCodePointAtNodeGen.create(
               context, builtin, args().withThis().fixedArgs(1).createArgumentNodes(context)
            );
         case _iterator:
            return StringPrototypeBuiltinsFactory.CreateStringIteratorNodeGen.create(context, builtin, args().withThis().createArgumentNodes(context));
         case normalize:
            return StringPrototypeBuiltinsFactory.JSStringNormalizeNodeGen.create(context, builtin, args().withThis().fixedArgs(1).createArgumentNodes(context));
         case matchAll:
            return StringPrototypeBuiltinsFactory.JSStringMatchNodeGen.create(
               context, builtin, true, args().withThis().fixedArgs(1).createArgumentNodes(context)
            );
         case padStart:
            return StringPrototypeBuiltinsFactory.JSStringPadNodeGen.create(context, builtin, true, args().withThis().varArgs().createArgumentNodes(context));
         case padEnd:
            return StringPrototypeBuiltinsFactory.JSStringPadNodeGen.create(context, builtin, false, args().withThis().varArgs().createArgumentNodes(context));
         case anchor:
            return createHTMLNode(context, builtin, Strings.A, Strings.NAME);
         case big:
            return createHTMLNode(context, builtin, Strings.BIG, Strings.EMPTY_STRING);
         case blink:
            return createHTMLNode(context, builtin, Strings.BLINK, Strings.EMPTY_STRING);
         case bold:
            return createHTMLNode(context, builtin, Strings.B, Strings.EMPTY_STRING);
         case fixed:
            return createHTMLNode(context, builtin, Strings.TT, Strings.EMPTY_STRING);
         case fontcolor:
            return createHTMLNode(context, builtin, Strings.FONT, Strings.COLOR);
         case fontsize:
            return createHTMLNode(context, builtin, Strings.FONT, Strings.SIZE);
         case italics:
            return createHTMLNode(context, builtin, Strings.I, Strings.EMPTY_STRING);
         case link:
            return createHTMLNode(context, builtin, Strings.A, Strings.HREF);
         case small:
            return createHTMLNode(context, builtin, Strings.SMALL, Strings.EMPTY_STRING);
         case strike:
            return createHTMLNode(context, builtin, Strings.STRIKE, Strings.EMPTY_STRING);
         case sub:
            return createHTMLNode(context, builtin, Strings.SUB, Strings.EMPTY_STRING);
         case sup:
            return createHTMLNode(context, builtin, Strings.SUP, Strings.EMPTY_STRING);
         case at:
            return StringPrototypeBuiltinsFactory.JSStringAtNodeGen.create(context, builtin, args().withThis().fixedArgs(1).createArgumentNodes(context));
         default:
            return null;
      }
   }

   static StringPrototypeBuiltins.CreateHTMLNode createHTMLNode(JSContext context, JSBuiltin builtin, TruffleString tag, TruffleString attribute) {
      return StringPrototypeBuiltinsFactory.CreateHTMLNodeGen.create(
         context, builtin, tag, attribute, args().withThis().fixedArgs(1).createArgumentNodes(context)
      );
   }

   abstract static class CreateHTMLNode extends JSBuiltinNode {
      private final TruffleString tag;
      private final TruffleString attribute;
      private final boolean emptyAttr;

      CreateHTMLNode(JSContext context, JSBuiltin builtin, TruffleString tag, TruffleString attribute) {
         super(context, builtin);
         this.tag = tag;
         this.attribute = attribute;
         this.emptyAttr = Strings.isEmpty(attribute);
      }

      @Specialization
      protected Object createHTML(
         Object thisObj, Object value, @Cached RequireObjectCoercibleNode requireObjectCoercibleNode, @Cached JSToStringNode toStringNode
      ) {
         TruffleString string = toStringNode.executeString(requireObjectCoercibleNode.execute(thisObj));
         if (!this.emptyAttr) {
            TruffleString attrVal = toStringNode.executeString(value);
            return this.wrapInTagWithAttribute(string, attrVal);
         } else {
            return this.wrapInTag(string);
         }
      }

      @CompilerDirectives.TruffleBoundary
      private Object wrapInTag(TruffleString string) {
         return Strings.concatAll(
            Strings.ANGLE_BRACKET_OPEN, this.tag, Strings.ANGLE_BRACKET_CLOSE, string, Strings.ANGLE_BRACKET_OPEN_SLASH, this.tag, Strings.ANGLE_BRACKET_CLOSE
         );
      }

      @CompilerDirectives.TruffleBoundary
      private Object wrapInTagWithAttribute(TruffleString string, TruffleString attrVal) {
         TruffleString escapedVal = Strings.replace(attrVal, Strings.DOUBLE_QUOTE, Strings.HTML_QUOT);
         return Strings.concatAll(
            Strings.ANGLE_BRACKET_OPEN,
            this.tag,
            Strings.SPACE,
            this.attribute,
            Strings.EQUALS_DOUBLE_QUOTE,
            escapedVal,
            Strings.DOUBLE_QUOTE,
            Strings.ANGLE_BRACKET_CLOSE,
            string,
            Strings.ANGLE_BRACKET_OPEN_SLASH,
            this.tag,
            Strings.ANGLE_BRACKET_CLOSE
         );
      }
   }

   public static class CreateRegExpStringIteratorNode extends JavaScriptBaseNode {
      @Node.Child
      private CreateObjectNode.CreateObjectWithPrototypeNode createObjectNode;
      @Node.Child
      private PropertySetNode setIteratingRegExpNode;
      @Node.Child
      private PropertySetNode setIteratedStringNode;
      @Node.Child
      private PropertySetNode setGlobalNode;
      @Node.Child
      private PropertySetNode setUnicodeNode;
      @Node.Child
      private PropertySetNode setDoneNode;

      public CreateRegExpStringIteratorNode(JSContext context) {
         this.createObjectNode = CreateObjectNode.createOrdinaryWithPrototype(context);
         this.setIteratingRegExpNode = PropertySetNode.createSetHidden(JSString.REGEXP_ITERATOR_ITERATING_REGEXP_ID, context);
         this.setIteratedStringNode = PropertySetNode.createSetHidden(JSString.REGEXP_ITERATOR_ITERATED_STRING_ID, context);
         this.setGlobalNode = PropertySetNode.createSetHidden(JSString.REGEXP_ITERATOR_GLOBAL_ID, context);
         this.setUnicodeNode = PropertySetNode.createSetHidden(JSString.REGEXP_ITERATOR_UNICODE_ID, context);
         this.setDoneNode = PropertySetNode.createSetHidden(JSString.REGEXP_ITERATOR_DONE_ID, context);
      }

      public JSDynamicObject createIterator(Object regex, Object string, Boolean global, Boolean fullUnicode) {
         JSDynamicObject regExpStringIteratorPrototype = this.getRealm().getRegExpStringIteratorPrototype();
         JSDynamicObject iterator = this.createObjectNode.execute(regExpStringIteratorPrototype);
         this.setIteratingRegExpNode.setValue(iterator, regex);
         this.setIteratedStringNode.setValue(iterator, string);
         this.setGlobalNode.setValueBoolean(iterator, global);
         this.setUnicodeNode.setValueBoolean(iterator, fullUnicode);
         this.setDoneNode.setValueBoolean(iterator, false);
         return iterator;
      }
   }

   public abstract static class CreateStringIteratorNode extends JSBuiltinNode {
      @Node.Child
      private CreateObjectNode.CreateObjectWithPrototypeNode createObjectNode;
      @Node.Child
      private PropertySetNode setNextIndexNode;
      @Node.Child
      private PropertySetNode setIteratedObjectNode;

      public CreateStringIteratorNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
         this.createObjectNode = CreateObjectNode.createOrdinaryWithPrototype(context);
         this.setIteratedObjectNode = PropertySetNode.createSetHidden(JSString.ITERATED_STRING_ID, context);
         this.setNextIndexNode = PropertySetNode.createSetHidden(JSString.STRING_ITERATOR_NEXT_INDEX_ID, context);
      }

      @Specialization
      protected JSDynamicObject doString(TruffleString thisObj) {
         JSDynamicObject iterator = this.createObjectNode.execute(this.getRealm().getStringIteratorPrototype());
         this.setIteratedObjectNode.setValue(iterator, thisObj);
         this.setNextIndexNode.setValueInt(iterator, 0);
         return iterator;
      }

      @Specialization(guards = "!isString(thisObj)")
      protected JSDynamicObject doCoerce(Object thisObj, @Cached RequireObjectCoercibleNode requireObjectCoercibleNode, @Cached JSToStringNode toStringNode) {
         return this.doString(toStringNode.executeString(requireObjectCoercibleNode.execute(thisObj)));
      }
   }

   public abstract static class JSStringAtNode extends StringPrototypeBuiltins.JSStringOperation {
      public JSStringAtNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      protected Object at(Object thisObj, Object index, @Cached TruffleString.SubstringByteIndexNode substringNode) {
         this.requireObjectCoercible(thisObj);
         TruffleString thisStr = this.toString(thisObj);
         int relativeIndex = this.toIntegerAsInt(index);
         int k = relativeIndex >= 0 ? relativeIndex : Strings.length(thisStr) + relativeIndex;
         return k >= 0 && k < Strings.length(thisStr) ? Strings.substring(this.getContext(), substringNode, thisStr, k, 1) : Undefined.instance;
      }
   }

   public abstract static class JSStringCharAtNode extends StringPrototypeBuiltins.JSStringOperation implements JSBuiltinNode.Inlineable {
      private final ConditionProfile indexOutOfBounds = ConditionProfile.createBinaryProfile();
      @Node.Child
      private TruffleString.SubstringByteIndexNode substringNode = TruffleString.SubstringByteIndexNode.create();

      public JSStringCharAtNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      protected TruffleString stringCharAt(TruffleString thisObj, int pos) {
         return this.indexOutOfBounds.profile(pos < 0 || pos >= Strings.length(thisObj))
            ? Strings.EMPTY_STRING
            : Strings.substring(this.getContext(), this.substringNode, thisObj, pos, 1);
      }

      @Specialization
      protected TruffleString charAt(Object thisObj, Object index) {
         this.requireObjectCoercible(thisObj);
         return this.stringCharAt(this.toString(thisObj), this.toIntegerAsInt(index));
      }

      public StringPrototypeBuiltins.JSStringCharAtNode.Inlined createInlined() {
         return StringPrototypeBuiltinsFactory.JSStringCharAtNodeGen.InlinedNodeGen.create(this.getContext(), this.getBuiltin(), this.getArguments());
      }

      public abstract static class Inlined extends StringPrototypeBuiltins.JSStringCharAtNode implements JSBuiltinNode.Inlined {
         public Inlined(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
         }

         @Specialization
         @Override
         protected TruffleString charAt(Object thisObj, Object indexObj) {
            throw this.rewriteToCall();
         }

         protected abstract Object executeWithArguments(Object arg0, Object arg1);

         @Override
         public Object callInlined(Object[] arguments) {
            if (JSArguments.getUserArgumentCount(arguments) < 1) {
               throw this.rewriteToCall();
            } else {
               return this.executeWithArguments(JSArguments.getThisObject(arguments), JSArguments.getUserArgument(arguments, 0));
            }
         }
      }
   }

   public abstract static class JSStringCharCodeAtNode extends StringPrototypeBuiltins.JSStringOperation implements JSBuiltinNode.Inlineable {
      private final ConditionProfile indexOutOfBounds = ConditionProfile.createBinaryProfile();

      public JSStringCharCodeAtNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      protected static boolean posInBounds(TruffleString thisStr, int pos) {
         return pos >= 0 && pos < Strings.length(thisStr);
      }

      @Specialization(guards = "posInBounds(thisStr, pos)")
      protected int charCodeAtInBounds(TruffleString thisStr, int pos) {
         return this.charAt(thisStr, pos);
      }

      @Specialization(guards = "!posInBounds(thisStr, pos)")
      protected double charCodeAtOutOfBounds(TruffleString thisStr, int pos) {
         return Double.NaN;
      }

      @Specialization(replaces = {"charCodeAtInBounds", "charCodeAtOutOfBounds"})
      protected Object charCodeAtGeneric(Object thisObj, Object indexObj, @Cached JSToNumberNode toNumberNode) {
         this.requireObjectCoercible(thisObj);
         TruffleString s = this.toString(thisObj);
         Number index = toNumberNode.executeNumber(indexObj);
         long lIndex = JSRuntime.toInteger(index);
         return this.indexOutOfBounds.profile(0L > lIndex || lIndex >= Strings.length(s)) ? Double.NaN : Integer.valueOf(this.charAt(s, (int)lIndex));
      }

      public StringPrototypeBuiltins.JSStringCharCodeAtNode.Inlined createInlined() {
         return StringPrototypeBuiltinsFactory.JSStringCharCodeAtNodeGen.InlinedNodeGen.create(this.getContext(), this.getBuiltin(), this.getArguments());
      }

      public abstract static class Inlined extends StringPrototypeBuiltins.JSStringCharCodeAtNode implements JSBuiltinNode.Inlined {
         public Inlined(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
         }

         @Specialization
         @Override
         protected Object charCodeAtGeneric(Object thisObj, Object indexObj, @Cached JSToNumberNode toNumberNode) {
            throw this.rewriteToCall();
         }

         protected abstract Object executeWithArguments(Object arg0, Object arg1);

         @Override
         public Object callInlined(Object[] arguments) {
            if (JSArguments.getUserArgumentCount(arguments) < 1) {
               throw this.rewriteToCall();
            } else {
               return this.executeWithArguments(JSArguments.getThisObject(arguments), JSArguments.getUserArgument(arguments, 0));
            }
         }
      }
   }

   public abstract static class JSStringCodePointAtNode extends StringPrototypeBuiltins.JSStringOperation {
      private final BranchProfile undefinedBranch = BranchProfile.create();
      private final BranchProfile needSecondBranch = BranchProfile.create();
      private final BranchProfile needCalculationBranch = BranchProfile.create();

      public JSStringCodePointAtNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      protected Object codePointAt(Object thisObj, Object position, @Cached TruffleString.CodePointAtByteIndexNode codePointAtRawNode) {
         this.requireObjectCoercible(thisObj);
         TruffleString thisStr = this.toString(thisObj);
         int pos = this.toIntegerAsInt(position);
         if (pos >= 0 && Strings.length(thisStr) > pos) {
            int first = Strings.codePointAt(codePointAtRawNode, thisStr, pos);
            boolean isEnd = pos + 1 == Strings.length(thisStr);
            if (!isEnd && first >= 55296 && first <= 56319) {
               this.needSecondBranch.enter();
               int second = Strings.codePointAt(codePointAtRawNode, thisStr, pos + 1);
               if (second >= 56320 && second <= 57343) {
                  this.needCalculationBranch.enter();
                  return (first - 55296) * 1024 + (second - 56320) + 65536;
               } else {
                  return first;
               }
            } else {
               return first;
            }
         } else {
            this.undefinedBranch.enter();
            return Undefined.instance;
         }
      }
   }

   public abstract static class JSStringConcatNode extends StringPrototypeBuiltins.JSStringOperation {
      private final StringBuilderProfile stringBuilderProfile;

      public JSStringConcatNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
         this.stringBuilderProfile = StringBuilderProfile.create(context.getStringLengthLimit());
      }

      @Specialization
      protected Object concat(
         Object thisObj,
         Object[] args,
         @Cached JSToStringNode toString2Node,
         @Cached TruffleStringBuilder.AppendStringNode appendStringNode,
         @Cached TruffleStringBuilder.ToStringNode sbToStringNode
      ) {
         this.requireObjectCoercible(thisObj);
         TruffleStringBuilder sb = this.stringBuilderProfile.newStringBuilder();
         this.stringBuilderProfile.append(appendStringNode, sb, this.toString(thisObj));

         for (Object o : args) {
            this.stringBuilderProfile.append(appendStringNode, sb, toString2Node.executeString(o));
            TruffleSafepoint.poll(this);
         }

         return StringBuilderProfile.toString(sbToStringNode, sb);
      }
   }

   public abstract static class JSStringEndsWithNode extends StringPrototypeBuiltins.JSStringOperation {
      private final BranchProfile noStringBranch = BranchProfile.create();

      public JSStringEndsWithNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization(guards = "isUndefined(position)")
      protected boolean endsWithStringUndefined(
         TruffleString thisStr,
         TruffleString searchStr,
         Object position,
         @Cached @Cached.Shared("regionEqualsNode") TruffleString.RegionEqualByteIndexNode regionEqualsNode
      ) {
         int fromIndex = Strings.length(thisStr);
         if (Strings.length(searchStr) <= 0) {
            return true;
         } else {
            if (fromIndex >= Strings.length(thisStr)) {
               fromIndex = Strings.length(thisStr);
            } else if (fromIndex < 0) {
               return false;
            }

            return endsWithIntl(regionEqualsNode, thisStr, searchStr, fromIndex);
         }
      }

      @Specialization(guards = "!isStringString(thisObj, searchString) || !isUndefined(position)")
      protected boolean endsWithGeneric(
         Object thisObj,
         Object searchString,
         Object position,
         @Cached JSToStringNode toString2Node,
         @Cached("create(getContext())") IsRegExpNode isRegExpNode,
         @Cached @Cached.Shared("regionEqualsNode") TruffleString.RegionEqualByteIndexNode regionEqualsNode
      ) {
         this.requireObjectCoercible(thisObj);
         TruffleString thisStr = this.toString(thisObj);
         if (isRegExpNode.executeBoolean(searchString)) {
            this.noStringBranch.enter();
            throw Errors.createTypeError("First argument to String.prototype.endsWith must not be a regular expression");
         } else {
            TruffleString searchStr = toString2Node.executeString(searchString);
            int fromIndex = this.toIntegerAsInt(position);
            if (Strings.length(searchStr) <= 0) {
               return true;
            } else {
               if (fromIndex < Strings.length(thisStr) && position != Undefined.instance) {
                  if (fromIndex < 0) {
                     return false;
                  }
               } else {
                  fromIndex = Strings.length(thisStr);
               }

               return endsWithIntl(regionEqualsNode, thisStr, searchStr, fromIndex);
            }
         }
      }

      private static boolean endsWithIntl(
         TruffleString.RegionEqualByteIndexNode regionEqualsNode, TruffleString thisStr, TruffleString searchStr, int fromIndex
      ) {
         int searchStrLength = Strings.length(searchStr);
         int offset1 = fromIndex - searchStrLength;
         return offset1 >= 0 && Strings.regionEquals(regionEqualsNode, thisStr, offset1, searchStr, 0, searchStrLength);
      }
   }

   public abstract static class JSStringIncludesNode extends StringPrototypeBuiltins.JSStringOperation {
      private final BranchProfile noStringBranch = BranchProfile.create();

      public JSStringIncludesNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization(guards = "isUndefined(position)")
      protected boolean includesString(
         TruffleString thisStr,
         TruffleString searchStr,
         Object position,
         @Cached @Cached.Shared("indexOfStringNode") TruffleString.ByteIndexOfStringNode indexOfStringNode
      ) {
         return Strings.indexOf(indexOfStringNode, thisStr, searchStr) != -1;
      }

      @Specialization(guards = "!isStringString(thisObj, searchString) || !isUndefined(position)")
      protected boolean includesGeneric(
         Object thisObj,
         Object searchString,
         Object position,
         @Cached JSToStringNode toString2Node,
         @Cached("create(getContext())") IsRegExpNode isRegExpNode,
         @Cached @Cached.Shared("indexOfStringNode") TruffleString.ByteIndexOfStringNode indexOfStringNode
      ) {
         this.requireObjectCoercible(thisObj);
         TruffleString thisStr = this.toString(thisObj);
         if (isRegExpNode.executeBoolean(searchString)) {
            this.noStringBranch.enter();
            throw Errors.createTypeError("First argument to String.prototype.includes must not be a regular expression");
         } else {
            TruffleString searchStr = toString2Node.executeString(searchString);
            int fromIndex = this.toIntegerAsInt(position);
            return Strings.indexOf(indexOfStringNode, thisStr, searchStr, fromIndex) != -1;
         }
      }
   }

   public abstract static class JSStringIndexOfNode extends StringPrototypeBuiltins.JSStringOperation {
      private final ConditionProfile hasPos = ConditionProfile.createBinaryProfile();

      public JSStringIndexOfNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization(guards = "isUndefined(position)")
      protected int indexOfStringUndefined(
         TruffleString thisStr,
         TruffleString searchStr,
         Object position,
         @Cached @Cached.Shared("indexOfStringNode") TruffleString.ByteIndexOfStringNode indexOfStringNode
      ) {
         return Strings.indexOf(indexOfStringNode, thisStr, searchStr);
      }

      @Specialization
      protected int indexOfStringInt(
         TruffleString thisStr,
         TruffleString searchStr,
         int position,
         @Cached @Cached.Shared("indexOfStringNode") TruffleString.ByteIndexOfStringNode indexOfStringNode
      ) {
         return this.indexOfIntl(thisStr, searchStr, position, indexOfStringNode);
      }

      @Specialization(guards = "!isStringString(thisObj, searchObj) || !isUndefined(position)", replaces = "indexOfStringInt")
      protected int indexOfGeneric(
         Object thisObj,
         Object searchObj,
         Object position,
         @Cached JSToStringNode toString2Node,
         @Cached @Cached.Shared("indexOfStringNode") TruffleString.ByteIndexOfStringNode indexOfStringNode
      ) {
         this.requireObjectCoercible(thisObj);
         TruffleString thisStr = this.toString(thisObj);
         TruffleString searchStr = toString2Node.executeString(searchObj);
         return this.indexOfIntl(thisStr, searchStr, position, indexOfStringNode);
      }

      private int indexOfIntl(TruffleString thisStr, TruffleString searchStr, Object position, TruffleString.ByteIndexOfStringNode indexOfStringNode) {
         int startPos;
         if (this.hasPos.profile(position != Undefined.instance)) {
            startPos = Math.min(this.toIntegerAsInt(position), Strings.length(thisStr));
         } else {
            startPos = 0;
         }

         return Strings.indexOf(indexOfStringNode, thisStr, searchStr, startPos);
      }
   }

   public abstract static class JSStringLastIndexOfNode extends StringPrototypeBuiltins.JSStringOperation {
      public JSStringLastIndexOfNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization(guards = "isUndefined(position)")
      protected int lastIndexOfString(
         TruffleString thisObj,
         TruffleString searchString,
         Object position,
         @Cached @Cached.Shared("lastIndexOfNode") TruffleString.LastByteIndexOfStringNode lastIndexOfNode
      ) {
         return Strings.lastIndexOf(lastIndexOfNode, thisObj, searchString, Strings.length(thisObj));
      }

      @Specialization
      protected int lastIndexOfString(
         TruffleString thisObj,
         TruffleString searchString,
         int position,
         @Cached @Cached.Shared("lastIndexOfNode") TruffleString.LastByteIndexOfStringNode lastIndexOfNode
      ) {
         int len = Strings.length(thisObj);
         int pos = within(position, 0, len);
         return Strings.lastIndexOf(lastIndexOfNode, thisObj, searchString, pos);
      }

      @Specialization(guards = "!isStringString(thisObj, searchString) || !isUndefined(position)")
      protected int lastIndexOf(
         Object thisObj,
         Object searchString,
         Object position,
         @Cached JSToStringNode toString2Node,
         @Cached JSToNumberNode toNumberNode,
         @Cached ConditionProfile posNaN,
         @Cached @Cached.Shared("lastIndexOfNode") TruffleString.LastByteIndexOfStringNode lastIndexOfNode
      ) {
         this.requireObjectCoercible(thisObj);
         TruffleString thisStr = this.toString(thisObj);
         TruffleString searchStr = toString2Node.executeString(searchString);
         Number numPos = toNumberNode.executeNumber(position);
         int lastPos = Strings.length(thisStr);
         double dVal = JSRuntime.doubleValue(numPos);
         int pos;
         if (posNaN.profile(Double.isNaN(dVal))) {
            pos = lastPos;
         } else {
            pos = within((int)dVal, 0, lastPos);
         }

         return Strings.lastIndexOf(lastIndexOfNode, thisStr, searchStr, pos);
      }
   }

   public abstract static class JSStringLocaleCompareIntlNode extends StringPrototypeBuiltins.JSStringOperation {
      @Node.Child
      InitializeCollatorNode initCollatorNode;

      public JSStringLocaleCompareIntlNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
         this.initCollatorNode = InitializeCollatorNode.createInitalizeCollatorNode(context);
      }

      @CompilerDirectives.TruffleBoundary
      private JSDynamicObject createCollator(Object locales, Object options) {
         JSDynamicObject collatorObj = JSCollator.create(this.getContext(), this.getRealm());
         this.initCollatorNode.executeInit(collatorObj, locales, options);
         return collatorObj;
      }

      @Specialization
      protected int localeCompare(Object thisObj, Object thatObj, Object locales, Object options, @Cached JSToStringNode toString2Node) {
         this.requireObjectCoercible(thisObj);
         TruffleString thisStr = this.toString(thisObj);
         TruffleString thatStr = toString2Node.executeString(thatObj);
         JSDynamicObject collator = this.createCollator(locales, options);
         return JSCollator.compare(collator, Strings.toJavaString(thisStr), Strings.toJavaString(thatStr));
      }
   }

   public abstract static class JSStringLocaleCompareNode extends StringPrototypeBuiltins.JSStringOperation {
      private static Collator collator;

      public JSStringLocaleCompareNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @CompilerDirectives.TruffleBoundary
      private static Collator getCollator() {
         if (collator == null) {
            collator = Collator.getInstance(Locale.ROOT);
            collator.setStrength(2);
            collator.setDecomposition(2);
         }

         return collator;
      }

      @Specialization
      protected int localeCompare(Object thisObj, Object thatObj, @Cached JSToStringNode toString2Node) {
         this.requireObjectCoercible(thisObj);
         TruffleString thisStr = this.toString(thisObj);
         TruffleString thatStr = toString2Node.executeString(thatObj);
         return doLocaleCompare(thisStr, thatStr);
      }

      @CompilerDirectives.TruffleBoundary
      private static int doLocaleCompare(TruffleString thisStr, TruffleString thatStr) {
         return getCollator().compare(Strings.toJavaString(thisStr), Strings.toJavaString(thatStr));
      }
   }

   public abstract static class JSStringMatchES5Node extends StringPrototypeBuiltins.JSStringOperationWithRegExpArgument {
      @Node.Child
      private PropertySetNode setLastIndexNode;
      @Node.Child
      private JSToRegExpNode toRegExpNode;
      @Node.Child
      private RegExpPrototypeBuiltins.JSRegExpExecES5Node regExpExecNode;
      @Node.Child
      private TRegexUtil.TRegexCompiledRegexSingleFlagAccessor globalFlagAccessor = TRegexUtil.TRegexCompiledRegexSingleFlagAccessor.create("global");
      @Node.Child
      private TRegexUtil.TRegexResultAccessor resultAccessor = TRegexUtil.TRegexResultAccessor.create();
      @Node.Child
      private TRegexUtil.TRegexMaterializeResultNode resultMaterializer = TRegexUtil.TRegexMaterializeResultNode.create();
      private final ConditionProfile match = ConditionProfile.createCountingProfile();
      private final ConditionProfile isGlobalRegExp = ConditionProfile.createCountingProfile();

      public JSStringMatchES5Node(JSContext context, JSBuiltin builtin) {
         super(context, builtin);

         assert context.getEcmaScriptVersion() < 6;

         this.toRegExpNode = JSToRegExpNode.create(context);
         this.regExpExecNode = RegExpPrototypeBuiltinsFactory.JSRegExpExecES5NodeGen.create(context, null, null);
      }

      private void setLastIndex(JSDynamicObject regExp, int value) {
         if (this.setLastIndexNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.setLastIndexNode = this.insert(PropertySetNode.create(JSRegExp.LAST_INDEX, false, this.getContext(), true));
         }

         this.setLastIndexNode.setValue(regExp, value);
      }

      @Specialization
      protected JSDynamicObject matchRegExpNotGlobal(Object thisObj, Object searchObj) {
         this.requireObjectCoercible(thisObj);
         if (this.isGlobalRegExp.profile(JSRegExp.isJSRegExp(searchObj) && this.globalFlagAccessor.get(JSRegExp.getCompiledRegex((JSDynamicObject)searchObj)))) {
            TruffleString thisStr = this.toString(thisObj);
            return this.matchAll((JSDynamicObject)searchObj, thisStr);
         } else {
            return this.matchNotRegExpIntl(thisObj, searchObj);
         }
      }

      private JSDynamicObject matchNotRegExpIntl(Object thisObj, Object searchObj) {
         Object thisStr = this.toString(thisObj);
         JSRegExpObject regExp = this.toRegExpNode.execute(searchObj);
         return this.regExpExecNode.exec(regExp, thisStr);
      }

      private JSDynamicObject matchAll(JSDynamicObject regExp, TruffleString input) {
         this.setLastIndex(regExp, 0);
         Object result = this.matchIgnoreLastIndex(regExp, input, 0);
         if (this.match.profile(!this.resultAccessor.isMatch(result))) {
            return Null.instance;
         } else {
            List<Object> matches = new ArrayList<>();

            for (int lastIndex = 0; this.resultAccessor.isMatch(result); result = this.matchIgnoreLastIndex(regExp, input, lastIndex)) {
               Boundaries.listAdd(matches, this.resultMaterializer.materializeGroup(this.getContext(), result, 0, input));
               int thisIndex = this.resultAccessor.captureGroupEnd(result, 0);
               lastIndex = thisIndex + (thisIndex == lastIndex ? 1 : 0);
            }

            return JSArray.createConstant(this.getContext(), this.getRealm(), Boundaries.listToArray(matches));
         }
      }
   }

   public abstract static class JSStringMatchNode extends StringPrototypeBuiltins.JSStringOperationWithRegExpArgument {
      @Node.Child
      private CompileRegexNode compileRegexNode;
      @Node.Child
      private CreateRegExpNode createRegExpNode;
      @Node.Child
      private IsRegExpNode isRegExpNode;
      @Node.Child
      private PropertyGetNode getFlagsNode;
      @Node.Child
      private TruffleString.ByteIndexOfCodePointNode stringIndexOfNode;
      private final BranchProfile errorBranch;
      private final boolean matchAll;

      protected JSStringMatchNode(JSContext context, JSBuiltin builtin, boolean matchAll) {
         super(context, builtin);
         this.matchAll = matchAll;
         this.errorBranch = matchAll ? BranchProfile.create() : null;
      }

      @Specialization
      protected Object match(Object thisObj, Object regex) {
         this.requireObjectCoercible(thisObj);
         if (this.isSpecialProfile.profile(regex != Undefined.instance && regex != Null.instance)) {
            if (this.matchAll && this.getIsRegExpNode().executeBoolean(regex)) {
               Object flags = this.getFlags(regex);
               this.requireObjectCoercible(flags);
               if (this.indexOf(this.toString(flags), 103) == -1) {
                  this.errorBranch.enter();
                  throw Errors.createTypeError("Regular expression passed to matchAll() is missing 'g' flag.");
               }
            }

            Object matcher = this.getMethod(regex, this.matchSymbol());
            if (this.callSpecialProfile.profile(matcher != Undefined.instance)) {
               return this.call(matcher, regex, new Object[]{thisObj});
            }
         }

         return this.builtinMatch(thisObj, regex);
      }

      private Symbol matchSymbol() {
         return this.matchAll ? Symbol.SYMBOL_MATCH_ALL : Symbol.SYMBOL_MATCH;
      }

      private Object builtinMatch(Object thisObj, Object regex) {
         TruffleString thisStr = this.toString(thisObj);
         Object cRe = this.getCompileRegexNode()
            .compile(regex == Undefined.instance ? Strings.EMPTY_STRING : this.toString(regex), this.matchAll ? Strings.G : Strings.EMPTY_STRING);
         JSDynamicObject regExp = this.getCreateRegExpNode().createRegExp(cRe);
         return this.invoke(regExp, this.matchSymbol(), thisStr);
      }

      private CompileRegexNode getCompileRegexNode() {
         if (this.compileRegexNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.compileRegexNode = this.insert(CompileRegexNode.create(this.getContext()));
         }

         return this.compileRegexNode;
      }

      private CreateRegExpNode getCreateRegExpNode() {
         if (this.createRegExpNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.createRegExpNode = this.insert(CreateRegExpNode.create(this.getContext()));
         }

         return this.createRegExpNode;
      }

      private IsRegExpNode getIsRegExpNode() {
         if (this.isRegExpNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.isRegExpNode = this.insert(IsRegExpNode.create(this.getContext()));
         }

         return this.isRegExpNode;
      }

      private Object getFlags(Object regexp) {
         if (this.getFlagsNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.getFlagsNode = this.insert(PropertyGetNode.create(JSRegExp.FLAGS, this.getContext()));
         }

         return this.getFlagsNode.getValue(regexp);
      }

      private int indexOf(TruffleString a, int codepoint) {
         if (this.stringIndexOfNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.stringIndexOfNode = this.insert(TruffleString.ByteIndexOfCodePointNode.create());
         }

         return Strings.indexOf(this.stringIndexOfNode, a, codepoint);
      }
   }

   public abstract static class JSStringNormalizeNode extends StringPrototypeBuiltins.JSStringOperation {
      public JSStringNormalizeNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      protected TruffleString normalize(
         Object thisObj,
         Object form,
         @Cached TruffleString.EqualNode stringEqualsNode,
         @Cached TruffleString.ToJavaStringNode toJavaStringNode,
         @Cached TruffleString.FromJavaStringNode fromJavaStringNode
      ) {
         this.requireObjectCoercible(thisObj);
         TruffleString thisStr = this.toString(thisObj);
         TruffleString formStr = this.toString(form);
         Form useForm = null;
         if (form == Undefined.instance || Strings.length(formStr) <= 0 || Strings.equals(stringEqualsNode, formStr, Strings.NFC)) {
            useForm = Form.NFC;
         } else if (Strings.equals(stringEqualsNode, formStr, Strings.NFD)) {
            useForm = Form.NFD;
         } else if (Strings.equals(stringEqualsNode, formStr, Strings.NFKC)) {
            useForm = Form.NFKC;
         } else {
            if (!Strings.equals(stringEqualsNode, formStr, Strings.NFKD)) {
               throw Errors.createRangeError("invalid form string");
            }

            useForm = Form.NFKD;
         }

         return Strings.fromJavaString(fromJavaStringNode, doNormalize(Strings.toJavaString(toJavaStringNode, thisStr), useForm));
      }

      @CompilerDirectives.TruffleBoundary
      private static String doNormalize(String thisStr, Form form) {
         return Normalizer.normalize(thisStr, form);
      }
   }

   abstract static class JSStringOperation extends JSBuiltinNode {
      @Node.Child
      private RequireObjectCoercibleNode requireObjectCoercibleNode;
      @Node.Child
      private JSToStringNode toStringNode;
      @Node.Child
      private JSToIntegerAsIntNode toIntegerNode;
      @Node.Child
      private TruffleString.ReadCharUTF16Node stringReadNode;

      JSStringOperation(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      protected static int within(int value, int min, int max) {
         assert min <= max;

         if (value >= max) {
            return max;
         } else {
            return value <= min ? min : value;
         }
      }

      protected static int withinNumber(Number value, int min, int max) {
         assert min <= max;

         double dValue = JSRuntime.doubleValue(value);
         if (Double.isInfinite(dValue)) {
            return dValue < 0.0 ? min : max;
         } else {
            long lValue = JSRuntime.intValue(value);
            if (lValue >= max) {
               return max;
            } else {
               return lValue <= min ? min : (int)lValue;
            }
         }
      }

      protected final void requireObjectCoercible(Object target) {
         if (this.requireObjectCoercibleNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.requireObjectCoercibleNode = this.insert(RequireObjectCoercibleNode.create());
         }

         this.requireObjectCoercibleNode.executeVoid(target);
      }

      protected TruffleString toString(Object target) {
         if (this.toStringNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.toStringNode = this.insert(JSToStringNode.create());
         }

         return this.toStringNode.executeString(target);
      }

      protected char charAt(TruffleString s, int i) {
         if (this.stringReadNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.stringReadNode = this.insert(TruffleString.ReadCharUTF16Node.create());
         }

         return Strings.charAt(this.stringReadNode, s, i);
      }

      protected int toIntegerAsInt(Object target) {
         if (this.toIntegerNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.toIntegerNode = this.insert(JSToIntegerAsIntNode.create());
         }

         return this.toIntegerNode.executeInt(target);
      }
   }

   public abstract static class JSStringOperationWithRegExpArgument extends StringPrototypeBuiltins.JSStringOperation {
      @Node.Child
      protected JSRegExpExecIntlNode regExpNode;
      @Node.Child
      protected JSRegExpExecIntlNode.JSRegExpExecIntlIgnoreLastIndexNode regExpIgnoreLastIndexNode;
      @Node.Child
      private JSFunctionCallNode callNode;
      @Node.Child
      private PropertyGetNode getSymbolNode;
      @Node.Child
      private GetMethodNode getMethodNode;
      protected final ConditionProfile isSpecialProfile = ConditionProfile.createBinaryProfile();
      protected final ConditionProfile callSpecialProfile = ConditionProfile.createBinaryProfile();

      public JSStringOperationWithRegExpArgument(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      protected final Object matchIgnoreLastIndex(JSDynamicObject regExp, TruffleString input, int fromIndex) {
         assert this.getContext().getEcmaScriptVersion() <= 5;

         return this.getRegExpIgnoreLastIndexNode().execute(regExp, input, fromIndex);
      }

      protected JSRegExpExecIntlNode getRegExpNode() {
         if (this.regExpNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.regExpNode = this.insert(JSRegExpExecIntlNode.create(this.getContext()));
         }

         return this.regExpNode;
      }

      protected JSRegExpExecIntlNode.JSRegExpExecIntlIgnoreLastIndexNode getRegExpIgnoreLastIndexNode() {
         if (this.regExpIgnoreLastIndexNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.regExpIgnoreLastIndexNode = this.insert(JSRegExpExecIntlNode.JSRegExpExecIntlIgnoreLastIndexNode.create(this.getContext(), true));
         }

         return this.regExpIgnoreLastIndexNode;
      }

      protected final Object call(Object function, Object target, Object[] args) {
         if (this.callNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.callNode = this.insert(JSFunctionCallNode.createCall());
         }

         return this.callNode.executeCall(JSArguments.create(target, function, args));
      }

      protected final Object invoke(JSDynamicObject regExp, Symbol symbol, TruffleString thisStr) {
         assert JSRuntime.isPropertyKey(symbol);

         if (this.getSymbolNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.getSymbolNode = this.insert(PropertyGetNode.create(symbol, false, this.getContext()));
         }

         Object func = this.getSymbolNode.getValue(regExp);
         return this.call(func, regExp, new Object[]{thisStr});
      }

      protected final Object getMethod(Object target, Object key) {
         if (this.getMethodNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.getMethodNode = this.insert(GetMethodNode.create(this.getContext(), key));
         }

         return this.getMethodNode.executeWithTarget(target);
      }
   }

   public abstract static class JSStringPadNode extends StringPrototypeBuiltins.JSStringOperation {
      private final boolean atStart;

      public JSStringPadNode(JSContext context, JSBuiltin builtin, boolean atStart) {
         super(context, builtin);
         this.atStart = atStart;
      }

      @Specialization
      protected Object pad(
         Object thisObj,
         Object[] args,
         @Cached JSToStringNode toString2Node,
         @Cached TruffleStringBuilder.AppendStringNode appendStringNode,
         @Cached TruffleStringBuilder.AppendSubstringByteIndexNode appendSubStringNode,
         @Cached TruffleStringBuilder.ToStringNode builderToStringNode
      ) {
         this.requireObjectCoercible(thisObj);
         TruffleString thisStr = this.toString(thisObj);
         if (args.length == 0) {
            return thisStr;
         } else {
            int len = this.toIntegerAsInt(args[0]);
            if (len <= Strings.length(thisStr)) {
               return thisStr;
            } else {
               TruffleString fillStr;
               if (args.length > 1 && args[1] != Undefined.instance) {
                  fillStr = toString2Node.executeString(args[1]);
                  if (Strings.isEmpty(fillStr)) {
                     return thisStr;
                  }
               } else {
                  fillStr = Strings.SPACE;
               }

               if (len > this.getContext().getStringLengthLimit()) {
                  CompilerDirectives.transferToInterpreter();
                  throw Errors.createRangeErrorInvalidStringLength();
               } else {
                  assert !Strings.isEmpty(fillStr);

                  int pos = len - Strings.length(thisStr);
                  int fillLen = Strings.length(fillStr);
                  TruffleStringBuilder sb = Strings.builderCreate(len);
                  if (!this.atStart) {
                     Strings.builderAppend(appendStringNode, sb, thisStr);
                  }

                  while (pos >= fillLen) {
                     Strings.builderAppend(appendStringNode, sb, fillStr);
                     pos -= fillLen;
                  }

                  if (pos > 0) {
                     Strings.builderAppend(appendSubStringNode, sb, fillStr, 0, pos);
                  }

                  if (this.atStart) {
                     Strings.builderAppend(appendStringNode, sb, thisStr);
                  }

                  return Strings.builderToString(builderToStringNode, sb);
               }
            }
         }
      }
   }

   public abstract static class JSStringRepeatNode extends StringPrototypeBuiltins.JSStringOperation {
      private final BranchProfile errorBranch = BranchProfile.create();

      public JSStringRepeatNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      protected Object repeat(Object thisObj, Object count, @Cached JSToNumberNode toNumberNode, @Cached TruffleString.RepeatNode repeatNode) {
         this.requireObjectCoercible(thisObj);
         TruffleString thisStr = this.toString(thisObj);
         Number repeatCountN = toNumberNode.executeNumber(count);
         long repeatCount = JSRuntime.toInteger(repeatCountN);
         if (repeatCount >= 0L && (!(repeatCountN instanceof Double) || !Double.isInfinite(repeatCountN.doubleValue()))) {
            if (repeatCount == 1L) {
               return thisStr;
            } else if (repeatCount != 0L && Strings.length(thisStr) != 0) {
               int repeatCountInt = (int)repeatCount;
               if (repeatCountInt == repeatCount && repeatCount * Strings.length(thisStr) <= this.getContext().getStringLengthLimit()) {
                  return repeatNode.execute(thisStr, repeatCountInt, TruffleString.Encoding.UTF_16);
               } else {
                  this.errorBranch.enter();
                  throw Errors.createRangeErrorInvalidStringLength();
               }
            } else {
               return Strings.EMPTY_STRING;
            }
         } else {
            this.errorBranch.enter();
            throw Errors.createRangeError("illegal repeat count");
         }
      }
   }

   public abstract static class JSStringReplaceAllNode extends StringPrototypeBuiltins.JSStringReplaceBaseNode {
      private final ConditionProfile isSearchValueEmpty = ConditionProfile.createBinaryProfile();
      private final ConditionProfile isRegExp = ConditionProfile.createBinaryProfile();
      private final BranchProfile errorBranch = BranchProfile.create();
      @Node.Child
      private IsRegExpNode isRegExpNode;
      @Node.Child
      private PropertyGetNode getFlagsNode;
      @Node.Child
      private TruffleString.ByteIndexOfCodePointNode stringIndexOfNode;
      @Node.Child
      private TruffleString.ByteIndexOfStringNode stringIndexOfStringNode;

      public JSStringReplaceAllNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization(guards = "stringEquals(equalsNode, cachedReplaceValue, replaceValue)")
      protected Object replaceStringCached(
         Object thisObj,
         TruffleString searchValue,
         TruffleString replaceValue,
         @Cached("replaceValue") TruffleString cachedReplaceValue,
         @Cached(value = "parseReplaceValue(replaceValue)", dimensions = 1) ReplaceStringParser.Token[] cachedParsedReplaceValue,
         @Cached TruffleString.EqualNode equalsNode
      ) {
         this.requireObjectCoercible(thisObj);
         return this.performReplaceAll(searchValue, cachedReplaceValue, thisObj, cachedParsedReplaceValue);
      }

      @Specialization(replaces = "replaceStringCached")
      protected Object replaceString(Object thisObj, TruffleString searchValue, TruffleString replaceValue) {
         this.requireObjectCoercible(thisObj);
         return this.performReplaceAll(searchValue, replaceValue, thisObj, null);
      }

      protected Object performReplaceAll(TruffleString searchValue, TruffleString replaceValue, Object thisObj, ReplaceStringParser.Token[] parsedReplaceParam) {
         TruffleString thisStr = this.toString(thisObj);
         if (this.isSearchValueEmpty.profile(Strings.isEmpty(searchValue))) {
            int len = Strings.length(thisStr);
            TruffleStringBuilder sb = Strings.builderCreate((len + 1) * Strings.length(replaceValue) + len);
            this.append(sb, replaceValue);

            for (int i = 0; i < len; i++) {
               this.appendLen(sb, thisStr, i, 1);
               this.append(sb, replaceValue);
            }

            return this.builderToString(sb);
         } else {
            TruffleStringBuilder sb = Strings.builderCreate();
            int position = 0;

            while (position < Strings.length(thisStr)) {
               int nextPosition = this.indexOf(thisStr, searchValue, position);
               this.builtinReplaceString(searchValue, replaceValue, thisStr, parsedReplaceParam, position, nextPosition, sb);
               if (nextPosition < 0) {
                  break;
               }

               position = nextPosition + Strings.length(searchValue);
            }

            return this.builderToString(sb);
         }
      }

      @Specialization(guards = "!isStringString(searchValue, replaceValue)")
      protected Object replaceGeneric(Object thisObj, Object searchValue, Object replaceValue) {
         this.requireObjectCoercible(thisObj);
         if (this.isSpecialProfile.profile(searchValue != Undefined.instance && searchValue != Null.instance)) {
            if (this.isRegExp.profile(this.getIsRegExpNode().executeBoolean(searchValue))) {
               Object flags = this.getFlags(searchValue);
               this.requireObjectCoercible(flags);
               if (this.indexOf(this.toString(flags), 103) == -1) {
                  this.errorBranch.enter();
                  throw Errors.createTypeError("Only global regexps allowed");
               }
            }

            Object replacer = this.getMethod(searchValue, Symbol.SYMBOL_REPLACE);
            if (this.callSpecialProfile.profile(replacer != Undefined.instance)) {
               return this.call(replacer, searchValue, new Object[]{thisObj, replaceValue});
            }
         }

         if (this.toString2Node == null || this.toString3Node == null || this.isCallableNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.toString2Node = this.insert(JSToStringNode.create());
            this.toString3Node = this.insert(JSToStringNode.create());
            this.isCallableNode = this.insert(IsCallableNode.create());
         }

         return this.performReplaceAllGeneric(searchValue, replaceValue, thisObj);
      }

      protected Object performReplaceAllGeneric(Object searchValue, Object replParam, Object thisObj) {
         TruffleString thisStr = this.toString(thisObj);
         TruffleString searchString = this.toString2Node.executeString(searchValue);
         TruffleStringBuilder sb = Strings.builderCreate();
         int position = 0;
         boolean functionalReplace = this.isCallableNode.executeBoolean(replParam);
         Object replaceValue;
         if (this.functionalReplaceProfile.profile(functionalReplace)) {
            replaceValue = replParam;
         } else {
            replaceValue = this.toString3Node.executeString(replParam);
         }

         if (this.isSearchValueEmpty.profile(Strings.isEmpty(searchString))) {
            for (; position <= Strings.length(thisStr); position++) {
               this.builtinReplace(searchString, functionalReplace, replaceValue, thisStr, position, position, sb);
               if (position < Strings.length(thisStr)) {
                  this.appendLen(sb, thisStr, position, 1);
               }
            }

            return this.builderToString(sb);
         } else {
            while (position < Strings.length(thisStr)) {
               int nextPosition = this.indexOf(thisStr, searchString, position);
               this.builtinReplace(searchString, functionalReplace, replaceValue, thisStr, position, nextPosition, sb);
               if (nextPosition < 0) {
                  break;
               }

               position = nextPosition + Strings.length(searchString);
            }

            return this.builderToString(sb);
         }
      }

      private void builtinReplace(
         TruffleString searchString,
         boolean functionalReplace,
         Object replParam,
         TruffleString input,
         int lastPosition,
         int curPosition,
         TruffleStringBuilder sb
      ) {
         if (this.replaceNecessaryProfile.profile(curPosition < 0)) {
            this.append(sb, input, lastPosition, Strings.length(input));
         } else {
            this.append(sb, input, lastPosition, curPosition);
            if (this.functionalReplaceProfile.profile(functionalReplace)) {
               Object replValue = this.functionReplaceCall(replParam, Undefined.instance, new Object[]{searchString, curPosition, input});
               this.append(sb, this.toString3Node.executeString(replValue));
            } else {
               appendSubstitution(sb, input, (TruffleString)replParam, searchString, curPosition, this.dollarProfile, this);
            }
         }
      }

      private void builtinReplaceString(
         TruffleString searchString,
         TruffleString replaceString,
         TruffleString input,
         ReplaceStringParser.Token[] parsedReplaceParam,
         int lastPosition,
         int curPosition,
         TruffleStringBuilder sb
      ) {
         if (this.replaceNecessaryProfile.profile(curPosition < 0)) {
            this.append(sb, input, lastPosition, Strings.length(input));
         } else {
            this.append(sb, input, lastPosition, curPosition);
            if (parsedReplaceParam == null) {
               appendSubstitution(sb, input, replaceString, searchString, curPosition, this.dollarProfile, this);
            } else {
               ReplaceStringParser.processParsed(
                  parsedReplaceParam,
                  new StringPrototypeBuiltins.JSStringReplaceBaseNode.ReplaceStringConsumer(sb, input, replaceString, searchString, curPosition),
                  this
               );
            }
         }
      }

      private IsRegExpNode getIsRegExpNode() {
         if (this.isRegExpNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.isRegExpNode = this.insert(IsRegExpNode.create(this.getContext()));
         }

         return this.isRegExpNode;
      }

      private Object getFlags(Object regexp) {
         if (this.getFlagsNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.getFlagsNode = this.insert(PropertyGetNode.create(JSRegExp.FLAGS, this.getContext()));
         }

         return this.getFlagsNode.getValue(regexp);
      }

      private int indexOf(TruffleString a, int codepoint) {
         if (this.stringIndexOfNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.stringIndexOfNode = this.insert(TruffleString.ByteIndexOfCodePointNode.create());
         }

         return Strings.indexOf(this.stringIndexOfNode, a, codepoint);
      }

      private int indexOf(TruffleString s1, TruffleString s2, int fromIndex) {
         if (this.stringIndexOfStringNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.stringIndexOfStringNode = this.insert(TruffleString.ByteIndexOfStringNode.create());
         }

         return Strings.indexOf(this.stringIndexOfStringNode, s1, s2, fromIndex);
      }
   }

   public abstract static class JSStringReplaceBaseNode extends StringPrototypeBuiltins.JSStringOperationWithRegExpArgument {
      @Node.Child
      protected JSFunctionCallNode functionReplaceCallNode;
      @Node.Child
      protected JSToStringNode toString2Node;
      @Node.Child
      protected JSToStringNode toString3Node;
      @Node.Child
      protected IsCallableNode isCallableNode;
      protected final ConditionProfile functionalReplaceProfile = ConditionProfile.createBinaryProfile();
      protected final ConditionProfile replaceNecessaryProfile = ConditionProfile.createBinaryProfile();
      protected final BranchProfile dollarProfile = BranchProfile.create();
      @Node.Child
      private TruffleStringBuilder.AppendStringNode appendStringNode;
      @Node.Child
      private TruffleStringBuilder.AppendSubstringByteIndexNode appendSubStringNode;
      @Node.Child
      private TruffleStringBuilder.ToStringNode builderToStringNode;

      public JSStringReplaceBaseNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      protected ReplaceStringParser.Token[] parseReplaceValue(TruffleString replaceValue) {
         return ReplaceStringParser.parse(this.getContext(), replaceValue, 0, false);
      }

      protected static void appendSubstitution(
         TruffleStringBuilder sb,
         TruffleString input,
         TruffleString replaceStr,
         TruffleString searchStr,
         int pos,
         BranchProfile dollarProfile,
         StringPrototypeBuiltins.JSStringReplaceBaseNode node
      ) {
         ReplaceStringParser.process(
            node.getContext(),
            replaceStr,
            0,
            false,
            dollarProfile,
            new StringPrototypeBuiltins.JSStringReplaceBaseNode.ReplaceStringConsumer(sb, input, replaceStr, searchStr, pos),
            node
         );
      }

      protected final Object functionReplaceCall(Object splitter, Object separator, Object[] args) {
         if (this.functionReplaceCallNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.functionReplaceCallNode = this.insert(JSFunctionCallNode.createCall());
         }

         return this.functionReplaceCallNode.executeCall(JSArguments.create(separator, splitter, args));
      }

      void append(TruffleStringBuilder sb, TruffleString s) {
         if (this.appendStringNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.appendStringNode = this.insert(TruffleStringBuilder.AppendStringNode.create());
         }

         Strings.builderAppend(this.appendStringNode, sb, s);
      }

      void append(TruffleStringBuilder sb, TruffleString s, int fromIndex, int toIndex) {
         if (this.appendSubStringNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.appendSubStringNode = this.insert(TruffleStringBuilder.AppendSubstringByteIndexNode.create());
         }

         Strings.builderAppend(this.appendSubStringNode, sb, s, fromIndex, toIndex);
      }

      void appendLen(TruffleStringBuilder sb, TruffleString s, int fromIndex, int length) {
         if (this.appendSubStringNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.appendSubStringNode = this.insert(TruffleStringBuilder.AppendSubstringByteIndexNode.create());
         }

         Strings.builderAppendLen(this.appendSubStringNode, sb, s, fromIndex, length);
      }

      TruffleString builderToString(TruffleStringBuilder sb) {
         if (this.builderToStringNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.builderToStringNode = this.insert(TruffleStringBuilder.ToStringNode.create());
         }

         return Strings.builderToString(this.builderToStringNode, sb);
      }

      protected static final class ReplaceStringConsumer
         implements ReplaceStringParser.Consumer<StringPrototypeBuiltins.JSStringReplaceBaseNode, TruffleStringBuilder> {
         private final TruffleStringBuilder sb;
         private final TruffleString input;
         private final TruffleString searchStr;
         private final TruffleString replaceStr;
         private final int matchedPos;

         private ReplaceStringConsumer(TruffleStringBuilder sb, TruffleString input, TruffleString replaceStr, TruffleString searchStr, int matchedPos) {
            this.sb = sb;
            this.input = input;
            this.replaceStr = replaceStr;
            this.searchStr = searchStr;
            this.matchedPos = matchedPos;
         }

         public void literal(StringPrototypeBuiltins.JSStringReplaceBaseNode node, int start, int end) {
            node.append(this.sb, this.replaceStr, start, end);
         }

         public void match(StringPrototypeBuiltins.JSStringReplaceBaseNode node) {
            node.append(this.sb, this.searchStr);
         }

         public void matchHead(StringPrototypeBuiltins.JSStringReplaceBaseNode node) {
            node.append(this.sb, this.input, 0, this.matchedPos);
         }

         public void matchTail(StringPrototypeBuiltins.JSStringReplaceBaseNode node) {
            node.append(this.sb, this.input, this.matchedPos + Strings.length(this.searchStr), Strings.length(this.input));
         }

         public void captureGroup(StringPrototypeBuiltins.JSStringReplaceBaseNode node, int groupNumber, int literalStart, int literalEnd) {
            throw Errors.shouldNotReachHere();
         }

         public void namedCaptureGroup(StringPrototypeBuiltins.JSStringReplaceBaseNode node, TruffleString groupName) {
            throw Errors.shouldNotReachHere();
         }

         public TruffleStringBuilder getResult() {
            return this.sb;
         }
      }
   }

   public abstract static class JSStringReplaceES5Node extends StringPrototypeBuiltins.JSStringReplaceBaseNode {
      @Node.Child
      private PropertySetNode setLastIndexNode;
      @Node.Child
      private StringPrototypeBuiltins.JSStringReplaceES5Node.StringReplacer stringReplacerNode;
      @Node.Child
      private StringPrototypeBuiltins.JSStringReplaceES5Node.FunctionReplacer functionReplacerNode;
      @Node.Child
      private TRegexUtil.TRegexCompiledRegexSingleFlagAccessor globalFlagAccessor = TRegexUtil.TRegexCompiledRegexSingleFlagAccessor.create("global");
      @Node.Child
      private TRegexUtil.TRegexCompiledRegexAccessor compiledRegexAccessor = TRegexUtil.TRegexCompiledRegexAccessor.create();
      @Node.Child
      private TRegexUtil.TRegexResultAccessor resultAccessor = TRegexUtil.TRegexResultAccessor.create();
      @Node.Child
      private TruffleString.ByteIndexOfStringNode stringIndexOfNode;
      private final ConditionProfile match = ConditionProfile.createCountingProfile();
      private final ConditionProfile isRegExp = ConditionProfile.createCountingProfile();
      private final ConditionProfile isFnRepl = ConditionProfile.createCountingProfile();

      public JSStringReplaceES5Node(JSContext context, JSBuiltin builtin) {
         super(context, builtin);

         assert context.getEcmaScriptVersion() < 6;
      }

      @Specialization
      protected Object replace(Object thisObj, Object searchValue, Object replaceValue) {
         this.requireObjectCoercible(thisObj);
         TruffleString thisStr = this.toString(thisObj);
         if (Strings.length(thisStr) > this.getContext().getStringLengthLimit()) {
            CompilerDirectives.transferToInterpreter();
            throw Errors.createRangeErrorInvalidStringLength();
         } else if (this.isRegExp.profile(JSRegExp.isJSRegExp(searchValue))) {
            JSRegExpObject searchRegExp = (JSRegExpObject)searchValue;
            Object tRegexCompiledRegex = JSRegExp.getCompiledRegex(searchRegExp);
            int groupCount = this.compiledRegexAccessor.groupCount(tRegexCompiledRegex);
            if (this.isFnRepl.profile(JSFunction.isJSFunction(replaceValue))) {
               JSDynamicObject replaceFunc = (JSDynamicObject)replaceValue;
               return this.globalFlagAccessor.get(tRegexCompiledRegex)
                  ? this.replaceAll(searchRegExp, thisStr, groupCount, this.getFunctionReplacerNode(), replaceFunc, tRegexCompiledRegex)
                  : this.replaceFirst(thisStr, searchRegExp, this.getFunctionReplacerNode(), replaceFunc, tRegexCompiledRegex);
            } else {
               TruffleString replaceStr = this.toString3(replaceValue);
               return this.globalFlagAccessor.get(tRegexCompiledRegex)
                  ? this.replaceAll(searchRegExp, thisStr, groupCount, this.getStringReplacerNode(), replaceStr, tRegexCompiledRegex)
                  : this.replaceFirst(thisStr, searchRegExp, this.getStringReplacerNode(), replaceStr, tRegexCompiledRegex);
            }
         } else {
            TruffleString searchStr = this.toString2(searchValue);
            if (this.isFnRepl.profile(JSFunction.isJSFunction(replaceValue))) {
               return this.replaceFirst(thisStr, searchStr, this.getFunctionReplacerNode(), (JSDynamicObject)replaceValue, null);
            } else {
               TruffleString replaceStr = this.toString3(replaceValue);
               return this.replaceFirst(thisStr, searchStr, this.getStringReplacerNode(), replaceStr, null);
            }
         }
      }

      private TruffleString toString2(Object obj) {
         if (this.toString2Node == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.toString2Node = this.insert(JSToStringNode.create());
         }

         return this.toString2Node.executeString(obj);
      }

      private TruffleString toString3(Object obj) {
         if (this.toString3Node == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.toString3Node = this.insert(JSToStringNode.create());
         }

         return this.toString3Node.executeString(obj);
      }

      private void setLastIndex(JSDynamicObject regExp, int value) {
         if (this.setLastIndexNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.setLastIndexNode = this.insert(PropertySetNode.create(JSRegExp.LAST_INDEX, false, this.getContext(), true));
         }

         this.setLastIndexNode.setValueInt(regExp, value);
      }

      private StringPrototypeBuiltins.JSStringReplaceES5Node.StringReplacer getStringReplacerNode() {
         if (this.stringReplacerNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.stringReplacerNode = this.insert(StringPrototypeBuiltins.JSStringReplaceES5Node.StringReplacer.create(this));
         }

         return this.stringReplacerNode;
      }

      private StringPrototypeBuiltins.JSStringReplaceES5Node.FunctionReplacer getFunctionReplacerNode() {
         if (this.functionReplacerNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.functionReplacerNode = this.insert(StringPrototypeBuiltins.JSStringReplaceES5Node.FunctionReplacer.create(this));
         }

         return this.functionReplacerNode;
      }

      private int indexOf(TruffleString s1, TruffleString s2) {
         if (this.stringIndexOfNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.stringIndexOfNode = this.insert(TruffleString.ByteIndexOfStringNode.create());
         }

         return Strings.indexOf(this.stringIndexOfNode, s1, s2);
      }

      private <T> Object replaceFirst(
         TruffleString thisStr,
         TruffleString searchStr,
         StringPrototypeBuiltins.JSStringReplaceES5Node.Replacer<T> replacer,
         T replaceValue,
         Object tRegexCompiledRegex
      ) {
         int start = this.indexOf(thisStr, searchStr);
         if (this.match.profile(start < 0)) {
            return thisStr;
         } else {
            int end = start + Strings.length(searchStr);
            TruffleStringBuilder sb = Strings.builderCreate();
            this.append(sb, thisStr, 0, start);
            replacer.appendReplacementString(sb, thisStr, searchStr, start, replaceValue, this, tRegexCompiledRegex);
            this.append(sb, thisStr, end, Strings.length(thisStr));
            return this.builderToString(sb);
         }
      }

      private <T> TruffleString replaceFirst(
         TruffleString thisStr,
         JSRegExpObject regExp,
         StringPrototypeBuiltins.JSStringReplaceES5Node.Replacer<T> replacer,
         T replaceValue,
         Object tRegexCompiledRegex
      ) {
         Object result = this.match(regExp, thisStr);
         return this.match.profile(!this.resultAccessor.isMatch(result))
            ? thisStr
            : this.replace(
               thisStr, result, this.compiledRegexAccessor.groupCount(JSRegExp.getCompiledRegex(regExp)), replacer, replaceValue, tRegexCompiledRegex
            );
      }

      protected final Object match(JSRegExpObject regExp, TruffleString input) {
         assert this.getContext().getEcmaScriptVersion() <= 5;

         return this.getRegExpNode().execute(regExp, input);
      }

      private <T> TruffleString replace(
         TruffleString thisStr,
         Object result,
         int groupCount,
         StringPrototypeBuiltins.JSStringReplaceES5Node.Replacer<T> replacer,
         T replaceValue,
         Object tRegexCompiledRegex
      ) {
         TruffleStringBuilder sb = Strings.builderCreate();
         this.append(sb, thisStr, 0, this.resultAccessor.captureGroupStart(result, 0));
         replacer.appendReplacementRegex(sb, thisStr, result, groupCount, replaceValue, this, tRegexCompiledRegex);
         this.append(sb, thisStr, this.resultAccessor.captureGroupEnd(result, 0), Strings.length(thisStr));
         return this.builderToString(sb);
      }

      private <T> TruffleString replaceAll(
         JSDynamicObject regExp,
         TruffleString input,
         int groupCount,
         StringPrototypeBuiltins.JSStringReplaceES5Node.Replacer<T> replacer,
         T replaceValue,
         Object tRegexCompiledRegex
      ) {
         this.setLastIndex(regExp, 0);
         Object result = this.matchIgnoreLastIndex(regExp, input, 0);
         if (this.match.profile(!this.resultAccessor.isMatch(result))) {
            return input;
         } else {
            TruffleStringBuilder sb = Strings.builderCreate();
            int thisIndex = 0;

            for (int lastIndex = 0; this.resultAccessor.isMatch(result); result = this.matchIgnoreLastIndex(regExp, input, lastIndex)) {
               this.append(sb, input, thisIndex, this.resultAccessor.captureGroupStart(result, 0));
               replacer.appendReplacementRegex(sb, input, result, groupCount, replaceValue, this, tRegexCompiledRegex);
               if (Strings.builderLength(sb) > this.getContext().getStringLengthLimit()) {
                  CompilerDirectives.transferToInterpreter();
                  throw Errors.createRangeErrorInvalidStringLength();
               }

               thisIndex = this.resultAccessor.captureGroupEnd(result, 0);
               if (thisIndex == Strings.length(input) && this.resultAccessor.captureGroupLength(result, 0) == 0) {
                  break;
               }

               lastIndex = thisIndex + (thisIndex == lastIndex ? 1 : 0);
            }

            this.append(sb, input, thisIndex, Strings.length(input));
            return this.builderToString(sb);
         }
      }

      protected static final class FunctionReplacer extends StringPrototypeBuiltins.JSStringReplaceES5Node.Replacer<JSDynamicObject> {
         @Node.Child
         private JSFunctionCallNode functionCallNode = JSFunctionCallNode.createCall();
         @Node.Child
         private JSToStringNode toStringNode = JSToStringNode.create();

         private FunctionReplacer(StringPrototypeBuiltins.JSStringReplaceES5Node parent) {
            super(parent);
         }

         public static StringPrototypeBuiltins.JSStringReplaceES5Node.FunctionReplacer create(StringPrototypeBuiltins.JSStringReplaceES5Node parent) {
            return new StringPrototypeBuiltins.JSStringReplaceES5Node.FunctionReplacer(parent);
         }

         void appendReplacementRegex(
            TruffleStringBuilder sb,
            TruffleString input,
            Object result,
            int groupCount,
            JSDynamicObject replaceFunc,
            StringPrototypeBuiltins.JSStringReplaceES5Node parent,
            Object tRegexCompiledRegex
         ) {
            parent.append(sb, this.callReplaceValueFunc(parent.getContext(), result, input, groupCount, replaceFunc));
         }

         void appendReplacementString(
            TruffleStringBuilder sb,
            TruffleString input,
            TruffleString matchedString,
            int pos,
            JSDynamicObject replaceFunc,
            StringPrototypeBuiltins.JSStringReplaceES5Node parent,
            Object tRegexCompiledRegex
         ) {
            Object[] arguments = createArguments(new Object[]{matchedString}, pos, input, replaceFunc);
            Object replaceValue = this.functionCallNode.executeCall(arguments);
            TruffleString replaceStr = this.toStringNode.executeString(replaceValue);
            parent.append(sb, replaceStr);
         }

         private TruffleString callReplaceValueFunc(JSContext context, Object result, TruffleString input, int groupCount, JSDynamicObject replaceFunc) {
            Object[] matches = this.resultMaterializer.materializeFull(context, result, groupCount, input);
            Object[] arguments = createArguments(matches, this.parentNode.resultAccessor.captureGroupStart(result, 0), input, replaceFunc);
            Object replaceValue = this.functionCallNode.executeCall(arguments);
            return this.toStringNode.executeString(replaceValue);
         }

         private static Object[] createArguments(Object[] matches, int matchIndex, Object input, JSDynamicObject replaceFunc) {
            JSDynamicObject target = Undefined.instance;
            Object[] arguments = JSArguments.createInitial(target, replaceFunc, matches.length + 2);
            JSArguments.setUserArguments(arguments, 0, matches);
            JSArguments.setUserArgument(arguments, matches.length, matchIndex);
            JSArguments.setUserArgument(arguments, matches.length + 1, input);
            return arguments;
         }
      }

      private abstract static class Replacer<T> extends JavaScriptBaseNode {
         final StringPrototypeBuiltins.JSStringReplaceES5Node parentNode;
         @Node.Child
         TRegexUtil.TRegexMaterializeResultNode resultMaterializer = TRegexUtil.TRegexMaterializeResultNode.create();
         protected final ConditionProfile emptyReplace = ConditionProfile.createBinaryProfile();

         protected Replacer(StringPrototypeBuiltins.JSStringReplaceES5Node parent) {
            this.parentNode = parent;
         }

         abstract void appendReplacementRegex(
            TruffleStringBuilder sb,
            TruffleString input,
            Object result,
            int groupCount,
            T replaceValue,
            StringPrototypeBuiltins.JSStringReplaceES5Node parent,
            Object tRegexCompiledRegex
         );

         abstract void appendReplacementString(
            TruffleStringBuilder sb,
            TruffleString input,
            TruffleString matchedString,
            int pos,
            T replaceValue,
            StringPrototypeBuiltins.JSStringReplaceES5Node parent,
            Object tRegexCompiledRegex
         );
      }

      protected static final class StringReplacer
         extends StringPrototypeBuiltins.JSStringReplaceES5Node.Replacer<TruffleString>
         implements RegExpPrototypeBuiltins.ReplaceStringConsumerTRegex.ParentNode {
         private final BranchProfile dollarProfile = BranchProfile.create();
         private final BranchProfile invalidGroupNumberProfile = BranchProfile.create();

         private StringReplacer(StringPrototypeBuiltins.JSStringReplaceES5Node parent) {
            super(parent);
         }

         public static StringPrototypeBuiltins.JSStringReplaceES5Node.StringReplacer create(StringPrototypeBuiltins.JSStringReplaceES5Node parent) {
            return new StringPrototypeBuiltins.JSStringReplaceES5Node.StringReplacer(parent);
         }

         void appendReplacementRegex(
            TruffleStringBuilder sb,
            TruffleString input,
            Object result,
            int groupCount,
            TruffleString replaceStr,
            StringPrototypeBuiltins.JSStringReplaceES5Node parent,
            Object tRegexCompiledRegex
         ) {
            if (this.emptyReplace.profile(!Strings.isEmpty(replaceStr))) {
               ReplaceStringParser.process(
                  parent.getContext(),
                  replaceStr,
                  groupCount,
                  false,
                  this.dollarProfile,
                  new RegExpPrototypeBuiltins.ReplaceStringConsumerTRegex(
                     sb,
                     input,
                     replaceStr,
                     parent.resultAccessor.captureGroupStart(result, 0),
                     parent.resultAccessor.captureGroupEnd(result, 0),
                     result,
                     tRegexCompiledRegex
                  ),
                  this
               );
            }
         }

         void appendReplacementString(
            TruffleStringBuilder sb,
            TruffleString input,
            TruffleString matchedString,
            int pos,
            TruffleString replaceValue,
            StringPrototypeBuiltins.JSStringReplaceES5Node parent,
            Object tRegexCompiledRegex
         ) {
            StringPrototypeBuiltins.JSStringReplaceNode.appendSubstitution(sb, input, replaceValue, matchedString, pos, this.dollarProfile, parent);
         }

         @Override
         public TRegexUtil.TRegexCompiledRegexAccessor getCompiledRegexAccessor() {
            return this.parentNode.compiledRegexAccessor;
         }

         @Override
         public TRegexUtil.TRegexResultAccessor getResultAccessor() {
            return this.parentNode.resultAccessor;
         }

         @Override
         public TRegexUtil.TRegexNamedCaptureGroupsAccessor getNamedCaptureGroupsAccessor() {
            throw Errors.shouldNotReachHere();
         }

         @Override
         public void append(TruffleStringBuilder sb, TruffleString s) {
            this.parentNode.append(sb, s);
         }

         @Override
         public void append(TruffleStringBuilder sb, TruffleString s, int fromIndex, int toIndex) {
            this.parentNode.append(sb, s, fromIndex, toIndex);
         }

         @Override
         public BranchProfile getInvalidGroupNumberProfile() {
            return this.invalidGroupNumberProfile;
         }
      }
   }

   public abstract static class JSStringReplaceNode extends StringPrototypeBuiltins.JSStringReplaceBaseNode {
      @Node.Child
      private TruffleString.ByteIndexOfStringNode stringIndexOfNode;

      public JSStringReplaceNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization(guards = "stringEquals(equalsNode, cachedReplaceValue, replaceValue)")
      protected Object replaceStringCached(
         TruffleString thisObj,
         TruffleString searchValue,
         TruffleString replaceValue,
         @Cached("replaceValue") TruffleString cachedReplaceValue,
         @Cached(value = "parseReplaceValue(replaceValue)", dimensions = 1) ReplaceStringParser.Token[] cachedParsedReplaceValue,
         @Cached TruffleString.EqualNode equalsNode
      ) {
         this.requireObjectCoercible(thisObj);
         return this.builtinReplaceString(searchValue, cachedReplaceValue, thisObj, cachedParsedReplaceValue);
      }

      @Specialization(replaces = "replaceStringCached")
      protected Object replaceString(Object thisObj, TruffleString searchValue, TruffleString replaceValue) {
         this.requireObjectCoercible(thisObj);
         return this.builtinReplaceString(searchValue, replaceValue, thisObj, null);
      }

      @Specialization(guards = "!isStringString(searchValue, replaceValue)")
      protected Object replaceGeneric(Object thisObj, Object searchValue, Object replaceValue) {
         this.requireObjectCoercible(thisObj);
         if (this.isSpecialProfile.profile(searchValue != Undefined.instance && searchValue != Null.instance)) {
            Object replacer = this.getMethod(searchValue, Symbol.SYMBOL_REPLACE);
            if (this.callSpecialProfile.profile(replacer != Undefined.instance)) {
               return this.call(replacer, searchValue, new Object[]{thisObj, replaceValue});
            }
         }

         if (this.toString2Node == null || this.toString3Node == null || this.isCallableNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.toString2Node = this.insert(JSToStringNode.create());
            this.toString3Node = this.insert(JSToStringNode.create());
            this.isCallableNode = this.insert(IsCallableNode.create());
         }

         return this.builtinReplace(searchValue, replaceValue, thisObj);
      }

      private Object builtinReplace(Object searchValue, Object replParam, Object o) {
         TruffleString input = this.toString(o);
         TruffleString searchString = this.toString2Node.executeString(searchValue);
         boolean functionalReplace = this.isCallableNode.executeBoolean(replParam);
         TruffleString replaceString = null;
         if (!this.functionalReplaceProfile.profile(functionalReplace)) {
            replaceString = this.toString3Node.executeString(replParam);
         }

         int pos = this.indexOf(input, searchString);
         if (this.replaceNecessaryProfile.profile(pos < 0)) {
            return input;
         } else {
            TruffleStringBuilder sb = Strings.builderCreate();
            this.append(sb, input, 0, pos);
            if (this.functionalReplaceProfile.profile(functionalReplace)) {
               Object replValue = this.functionReplaceCall(replParam, Undefined.instance, new Object[]{searchString, pos, input});
               this.append(sb, this.toString3Node.executeString(replValue));
            } else {
               appendSubstitution(sb, input, replaceString, searchString, pos, this.dollarProfile, this);
            }

            this.append(sb, input, pos + Strings.length(searchString), Strings.length(input));
            return this.builderToString(sb);
         }
      }

      private Object builtinReplaceString(TruffleString searchString, TruffleString replaceString, Object o, ReplaceStringParser.Token[] parsedReplaceParam) {
         TruffleString input = this.toString(o);
         int pos = this.indexOf(input, searchString);
         if (this.replaceNecessaryProfile.profile(pos < 0)) {
            return input;
         } else {
            TruffleStringBuilder sb = Strings.builderCreate();
            this.append(sb, input, 0, pos);
            if (parsedReplaceParam == null) {
               appendSubstitution(sb, input, replaceString, searchString, pos, this.dollarProfile, this);
            } else {
               ReplaceStringParser.processParsed(
                  parsedReplaceParam,
                  new StringPrototypeBuiltins.JSStringReplaceBaseNode.ReplaceStringConsumer(sb, input, replaceString, searchString, pos),
                  this
               );
            }

            this.append(sb, input, pos + Strings.length(searchString), Strings.length(input));
            return this.builderToString(sb);
         }
      }

      private int indexOf(TruffleString s1, TruffleString s2) {
         if (this.stringIndexOfNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.stringIndexOfNode = this.insert(TruffleString.ByteIndexOfStringNode.create());
         }

         return Strings.indexOf(this.stringIndexOfNode, s1, s2);
      }
   }

   public abstract static class JSStringSearchES5Node extends StringPrototypeBuiltins.JSStringOperationWithRegExpArgument {
      @Node.Child
      private TRegexUtil.TRegexResultAccessor resultAccessor = TRegexUtil.TRegexResultAccessor.create();

      public JSStringSearchES5Node(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      protected int search(Object thisObj, Object[] args, @Cached("create(getContext())") JSToRegExpNode toRegExpNode) {
         assert this.getContext().getEcmaScriptVersion() < 6;

         Object searchObj = JSRuntime.getArgOrUndefined(args, 0);
         this.requireObjectCoercible(thisObj);
         TruffleString thisStr = this.toString(thisObj);
         JSDynamicObject regExp = toRegExpNode.execute(searchObj);
         Object result = this.matchIgnoreLastIndex(regExp, thisStr, 0);
         return this.resultAccessor.isMatch(result) ? this.resultAccessor.captureGroupStart(result, 0) : -1;
      }
   }

   public abstract static class JSStringSearchNode extends StringPrototypeBuiltins.JSStringOperationWithRegExpArgument {
      @Node.Child
      private CompileRegexNode compileRegexNode;
      @Node.Child
      private CreateRegExpNode createRegExpNode;

      public JSStringSearchNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      protected Object search(Object thisObj, Object regex) {
         assert this.getContext().getEcmaScriptVersion() >= 6;

         this.requireObjectCoercible(thisObj);
         if (this.isSpecialProfile.profile(regex != Undefined.instance && regex != Null.instance)) {
            Object searcher = this.getMethod(regex, Symbol.SYMBOL_SEARCH);
            if (this.callSpecialProfile.profile(searcher != Undefined.instance)) {
               return this.call(searcher, regex, new Object[]{thisObj});
            }
         }

         return this.builtinSearch(thisObj, regex);
      }

      private Object builtinSearch(Object thisObj, Object regex) {
         TruffleString thisStr = this.toString(thisObj);
         Object cRe = this.getCompileRegexNode().compile(regex == Undefined.instance ? Strings.EMPTY_STRING : this.toString(regex));
         JSDynamicObject regExp = this.getCreateRegExpNode().createRegExp(cRe);
         return this.invoke(regExp, Symbol.SYMBOL_SEARCH, thisStr);
      }

      private CompileRegexNode getCompileRegexNode() {
         if (this.compileRegexNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.compileRegexNode = this.insert(CompileRegexNode.create(this.getContext()));
         }

         return this.compileRegexNode;
      }

      private CreateRegExpNode getCreateRegExpNode() {
         if (this.createRegExpNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.createRegExpNode = this.insert(CreateRegExpNode.create(this.getContext()));
         }

         return this.createRegExpNode;
      }
   }

   public abstract static class JSStringSliceNode extends StringPrototypeBuiltins.JSStringOperation {
      private final ConditionProfile canReturnEmpty = ConditionProfile.createBinaryProfile();
      private final ConditionProfile offsetProfile1 = ConditionProfile.createBinaryProfile();
      private final ConditionProfile offsetProfile2 = ConditionProfile.createBinaryProfile();
      @Node.Child
      private TruffleString.SubstringByteIndexNode substringNode = TruffleString.SubstringByteIndexNode.create();

      public JSStringSliceNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      protected Object sliceStringIntInt(TruffleString thisObj, int start, int end) {
         int len = Strings.length(thisObj);
         int istart = JSRuntime.getOffset(start, len, this.offsetProfile1);
         int iend = JSRuntime.getOffset(end, len, this.offsetProfile2);
         return this.canReturnEmpty.profile(iend > istart)
            ? Strings.substring(this.getContext(), this.substringNode, thisObj, istart, iend - istart)
            : Strings.EMPTY_STRING;
      }

      @Specialization(guards = "!isString(thisObj)", replaces = "sliceStringIntInt")
      protected Object sliceObjectIntInt(Object thisObj, int start, int end) {
         this.requireObjectCoercible(thisObj);
         return this.sliceStringIntInt(this.toString(thisObj), start, end);
      }

      @Specialization(guards = "isUndefined(end)")
      protected Object sliceStringIntUndefined(TruffleString str, int start, Object end) {
         int len = Strings.length(str);
         int istart = JSRuntime.getOffset(start, len, this.offsetProfile1);
         return this.canReturnEmpty.profile(len > istart)
            ? Strings.substring(this.getContext(), this.substringNode, str, istart, len - istart)
            : Strings.EMPTY_STRING;
      }

      @Specialization(replaces = {"sliceStringIntInt", "sliceObjectIntInt", "sliceStringIntUndefined"})
      protected Object sliceGeneric(Object thisObj, Object start, Object end, @Cached("createBinaryProfile()") ConditionProfile isUndefined) {
         this.requireObjectCoercible(thisObj);
         TruffleString s = this.toString(thisObj);
         long len = Strings.length(s);
         long istart = JSRuntime.getOffset((long)this.toIntegerAsInt(start), len, this.offsetProfile1);
         long iend = isUndefined.profile(end == Undefined.instance) ? len : JSRuntime.getOffset((long)this.toIntegerAsInt(end), len, this.offsetProfile2);
         if (this.canReturnEmpty.profile(iend > istart)) {
            int begin = (int)istart;
            return Strings.substring(this.getContext(), this.substringNode, s, begin, (int)iend - begin);
         } else {
            return Strings.EMPTY_STRING;
         }
      }
   }

   public abstract static class JSStringSplitNode extends StringPrototypeBuiltins.JSStringOperationWithRegExpArgument {
      private final ConditionProfile emptyInput = ConditionProfile.createBinaryProfile();
      private final ConditionProfile emptySeparator = ConditionProfile.createBinaryProfile();
      private final ConditionProfile zeroLimit = ConditionProfile.createBinaryProfile();
      private final ConditionProfile matchProfile = ConditionProfile.createCountingProfile();
      private final BranchProfile isUndefinedBranch = BranchProfile.create();
      private final BranchProfile isStringBranch = BranchProfile.create();
      private final BranchProfile isRegexpBranch = BranchProfile.create();
      private final BranchProfile growProfile = BranchProfile.create();
      @Node.Child
      private JSToUInt32Node toUInt32Node;
      @Node.Child
      private JSToStringNode toString2Node;
      @Node.Child
      private TRegexUtil.TRegexCompiledRegexAccessor compiledRegexAccessor;
      @Node.Child
      private TRegexUtil.TRegexResultAccessor resultAccessor;
      @Node.Child
      private TruffleString.SubstringByteIndexNode substringNode;
      @Node.Child
      private TruffleString.ByteIndexOfStringNode stringIndexOfNode;
      private static final StringPrototypeBuiltins.JSStringSplitNode.Splitter<Void> NOP_SPLITTER = (input, limit, separator, parent) -> new Object[]{input};
      private static final StringPrototypeBuiltins.JSStringSplitNode.Splitter<TruffleString> STRING_SPLITTER = new StringPrototypeBuiltins.JSStringSplitNode.StringSplitter();
      private static final StringPrototypeBuiltins.JSStringSplitNode.Splitter<JSDynamicObject> REGEXP_SPLITTER = new StringPrototypeBuiltins.JSStringSplitNode.RegExpSplitter();

      public JSStringSplitNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      private int toUInt32(Object target) {
         if (this.toUInt32Node == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.toUInt32Node = this.insert(JSToUInt32Node.create());
         }

         return (int)Math.min(2147483647L, JSRuntime.toInteger((Number)this.toUInt32Node.execute(target)));
      }

      private TruffleString toString2(Object obj) {
         if (this.toString2Node == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.toString2Node = this.insert(JSToStringNode.create());
         }

         return this.toString2Node.executeString(obj);
      }

      private TruffleString substring(TruffleString a, int fromIndex) {
         if (this.substringNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.substringNode = this.insert(TruffleString.SubstringByteIndexNode.create());
         }

         return Strings.substring(this.getContext(), this.substringNode, a, fromIndex);
      }

      private TruffleString substring(TruffleString a, int fromIndex, int length) {
         if (this.substringNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.substringNode = this.insert(TruffleString.SubstringByteIndexNode.create());
         }

         return Strings.substring(this.getContext(), this.substringNode, a, fromIndex, length);
      }

      private int indexOf(TruffleString s1, TruffleString s2, int fromIndex) {
         if (this.stringIndexOfNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.stringIndexOfNode = this.insert(TruffleString.ByteIndexOfStringNode.create());
         }

         return Strings.indexOf(this.stringIndexOfNode, s1, s2, fromIndex);
      }

      protected boolean isES6OrNewer() {
         return this.getContext().getEcmaScriptVersion() >= 6;
      }

      @Specialization(guards = "!isES6OrNewer()")
      protected Object splitES5(Object thisObj, Object separator, Object limitObj) {
         this.requireObjectCoercible(thisObj);
         TruffleString thisStr = this.toString(thisObj);
         int limit = this.getLimit(limitObj);
         if (separator == Undefined.instance) {
            this.isUndefinedBranch.enter();
            return this.split(thisStr, limit, NOP_SPLITTER, null);
         } else if (JSRegExp.isJSRegExp(separator)) {
            this.isRegexpBranch.enter();
            return this.split(thisStr, limit, REGEXP_SPLITTER, (JSDynamicObject)separator);
         } else {
            this.isStringBranch.enter();
            TruffleString separatorStr = this.toString2(separator);
            return this.split(thisStr, limit, STRING_SPLITTER, separatorStr);
         }
      }

      protected boolean isFastPath(Object thisObj, Object separator, Object limit) {
         return Strings.isTString(thisObj) && Strings.isTString(separator) && limit == Undefined.instance;
      }

      @Specialization(guards = {"isES6OrNewer()", "isUndefined(limit)"})
      protected Object splitES6StrStrUndefined(TruffleString thisStr, TruffleString sepStr, JSDynamicObject limit) {
         return this.split(thisStr, Integer.MAX_VALUE, STRING_SPLITTER, sepStr);
      }

      @Specialization(guards = {"isES6OrNewer()", "!isFastPath(thisObj, separator, limit)"})
      protected Object splitES6Generic(Object thisObj, Object separator, Object limit) {
         this.requireObjectCoercible(thisObj);
         if (this.isSpecialProfile.profile(separator != Undefined.instance && separator != Null.instance)) {
            Object splitter = this.getMethod(separator, Symbol.SYMBOL_SPLIT);
            if (this.callSpecialProfile.profile(splitter != Undefined.instance)) {
               return this.call(splitter, separator, new Object[]{thisObj, limit});
            }
         }

         return this.builtinSplit(thisObj, separator, limit);
      }

      private Object builtinSplit(Object thisObj, Object separator, Object limit) {
         TruffleString thisStr = this.toString(thisObj);
         int lim = this.getLimit(limit);
         TruffleString sepStr = this.toString2(separator);
         return separator == Undefined.instance ? this.split(thisStr, lim, NOP_SPLITTER, null) : this.split(thisStr, lim, STRING_SPLITTER, sepStr);
      }

      private int getLimit(Object limit) {
         return limit == Undefined.instance ? Integer.MAX_VALUE : this.toUInt32(limit);
      }

      private <T> JSDynamicObject split(TruffleString thisStr, int limit, StringPrototypeBuiltins.JSStringSplitNode.Splitter<T> splitter, T separator) {
         JSRealm realm = this.getRealm();
         if (this.zeroLimit.profile(limit == 0)) {
            return JSArray.createEmptyZeroLength(this.getContext(), realm);
         } else {
            Object[] splits = splitter.split(thisStr, limit, separator, this);
            return JSArray.createConstant(this.getContext(), realm, splits);
         }
      }

      public TRegexUtil.TRegexCompiledRegexAccessor getCompiledRegexAccessor() {
         if (this.compiledRegexAccessor == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.compiledRegexAccessor = this.insert(TRegexUtil.TRegexCompiledRegexAccessor.create());
         }

         return this.compiledRegexAccessor;
      }

      public TRegexUtil.TRegexResultAccessor getResultAccessor() {
         if (this.resultAccessor == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.resultAccessor = this.insert(TRegexUtil.TRegexResultAccessor.create());
         }

         return this.resultAccessor;
      }

      private static final class RegExpSplitter implements StringPrototypeBuiltins.JSStringSplitNode.Splitter<JSDynamicObject> {
         private static final Object[] EMPTY_SPLITS = new Object[0];
         private static final Object[] SINGLE_ZERO_LENGTH_SPLIT = new Object[]{Strings.EMPTY_STRING};

         public Object[] split(TruffleString input, int limit, JSDynamicObject regExp, StringPrototypeBuiltins.JSStringSplitNode parent) {
            return parent.emptyInput.profile(Strings.isEmpty(input)) ? splitEmptyString(regExp, parent) : splitNonEmptyString(input, limit, regExp, parent);
         }

         private static Object[] splitEmptyString(JSDynamicObject regExp, StringPrototypeBuiltins.JSStringSplitNode parent) {
            Object result = parent.matchIgnoreLastIndex(regExp, Strings.EMPTY_STRING, 0);
            return parent.matchProfile.profile(parent.getResultAccessor().isMatch(result)) ? EMPTY_SPLITS : SINGLE_ZERO_LENGTH_SPLIT;
         }

         private static Object[] splitNonEmptyString(TruffleString input, int limit, JSDynamicObject regExp, StringPrototypeBuiltins.JSStringSplitNode parent) {
            Object result = parent.matchIgnoreLastIndex(regExp, input, 0);
            if (parent.matchProfile.profile(!parent.getResultAccessor().isMatch(result))) {
               return new Object[]{input};
            } else {
               SimpleArrayList<Object> splits = new SimpleArrayList<>();
               int start = 0;

               while (parent.getResultAccessor().isMatch(result)) {
                  int matchStart = parent.getResultAccessor().captureGroupStart(result, 0);
                  int matchEnd = parent.getResultAccessor().captureGroupEnd(result, 0);
                  if (matchEnd - matchStart == 0 && matchStart == start) {
                     if (matchStart == Strings.length(input) - 1) {
                        break;
                     }

                     result = parent.matchIgnoreLastIndex(regExp, input, start + 1);
                  } else {
                     TruffleString split = parent.substring(input, start, matchStart - start);
                     splits.add(split, parent.growProfile);
                     int count = Math.min(parent.getCompiledRegexAccessor().groupCount(JSRegExp.getCompiledRegex(regExp)) - 1, limit - splits.size());

                     for (int i = 1; i <= count; i++) {
                        int groupStart = parent.getResultAccessor().captureGroupStart(result, i);
                        if (groupStart == -1) {
                           splits.add(Undefined.instance, parent.growProfile);
                        } else {
                           splits.add(
                              parent.substring(input, groupStart, parent.getResultAccessor().captureGroupEnd(result, i) - groupStart), parent.growProfile
                           );
                        }
                     }

                     if (splits.size() == limit) {
                        return splits.toArray();
                     }

                     start = matchEnd + (matchEnd == start ? 1 : 0);
                     result = parent.matchIgnoreLastIndex(regExp, input, start);
                  }
               }

               splits.add(parent.substring(input, start), parent.growProfile);
               return splits.toArray();
            }
         }
      }

      private interface Splitter<T> {
         Object[] split(TruffleString input, int limit, T separator, StringPrototypeBuiltins.JSStringSplitNode parent);
      }

      private static final class StringSplitter implements StringPrototypeBuiltins.JSStringSplitNode.Splitter<TruffleString> {
         public Object[] split(TruffleString input, int limit, TruffleString separator, StringPrototypeBuiltins.JSStringSplitNode parent) {
            return parent.emptySeparator.profile(Strings.isEmpty(separator))
               ? individualCharSplit(input, limit, parent)
               : regularSplit(input, limit, separator, parent);
         }

         private static Object[] regularSplit(TruffleString input, int limit, TruffleString separator, StringPrototypeBuiltins.JSStringSplitNode parent) {
            int end = parent.indexOf(input, separator, 0);
            return parent.matchProfile.profile(end == -1) ? new Object[]{input} : regularSplitIntl(input, limit, separator, end, parent);
         }

         private static Object[] regularSplitIntl(
            TruffleString input, int limit, TruffleString separator, int endParam, StringPrototypeBuiltins.JSStringSplitNode parent
         ) {
            SimpleArrayList<Object> splits = SimpleArrayList.create(limit);
            int start = 0;
            int end = endParam;

            while (end != -1) {
               splits.add(parent.substring(input, start, end - start), parent.growProfile);
               if (splits.size() == limit) {
                  return splits.toArray();
               }

               start = end + Strings.length(separator);
               end = parent.indexOf(input, separator, start);
            }

            splits.add(parent.substring(input, start), parent.growProfile);
            return splits.toArray();
         }

         private static Object[] individualCharSplit(TruffleString input, int limit, StringPrototypeBuiltins.JSStringSplitNode parent) {
            int len = Math.min(Strings.length(input), limit);
            Object[] array = new Object[len];

            for (int i = 0; i < len; i++) {
               array[i] = parent.substring(input, i, 1);
            }

            return array;
         }
      }
   }

   public abstract static class JSStringStartsWithNode extends StringPrototypeBuiltins.JSStringOperation {
      private final BranchProfile noStringBranch = BranchProfile.create();

      public JSStringStartsWithNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization(guards = "isUndefined(position)")
      protected boolean startsWithString(
         TruffleString thisObj,
         TruffleString searchStr,
         JSDynamicObject position,
         @Cached @Cached.Shared("regionEqualsNode") TruffleString.RegionEqualByteIndexNode regionEqualsNode
      ) {
         if (Strings.length(searchStr) <= 0) {
            return true;
         } else {
            return Strings.length(thisObj) < Strings.length(searchStr) ? false : Strings.startsWith(regionEqualsNode, thisObj, searchStr);
         }
      }

      @Specialization(guards = "!isStringString(thisObj, searchString) || !isUndefined(position)")
      protected boolean startsWithGeneric(
         Object thisObj,
         Object searchString,
         Object position,
         @Cached JSToStringNode toString2Node,
         @Cached("create(getContext())") IsRegExpNode isRegExpNode,
         @Cached @Cached.Shared("regionEqualsNode") TruffleString.RegionEqualByteIndexNode regionEqualsNode
      ) {
         this.requireObjectCoercible(thisObj);
         TruffleString thisStr = this.toString(thisObj);
         if (isRegExpNode.executeBoolean(searchString)) {
            this.noStringBranch.enter();
            throw Errors.createTypeError("First argument to String.prototype.startsWith must not be a regular expression");
         } else {
            TruffleString searchStr = toString2Node.executeString(searchString);
            int fromIndex = this.toIntegerAsInt(position);
            return Strings.length(searchStr) <= 0 ? true : Strings.startsWith(regionEqualsNode, thisStr, searchStr, Math.max(0, fromIndex));
         }
      }
   }

   public abstract static class JSStringSubstrNode extends StringPrototypeBuiltins.JSStringOperation {
      private final BranchProfile startNegativeBranch = BranchProfile.create();
      private final BranchProfile finalLenEmptyBranch = BranchProfile.create();
      @Node.Child
      private TruffleString.SubstringByteIndexNode substringNode = TruffleString.SubstringByteIndexNode.create();

      public JSStringSubstrNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      protected Object substrInt(TruffleString thisStr, int start, int length) {
         return this.substrIntl(thisStr, start, length);
      }

      @Specialization(guards = "isUndefined(length)")
      protected Object substrLenUndef(TruffleString thisStr, int start, Object length) {
         return this.substrIntl(thisStr, start, Strings.length(thisStr));
      }

      @Specialization
      protected Object substrGeneric(Object thisObj, Object start, Object length) {
         this.requireObjectCoercible(thisObj);
         TruffleString thisStr = this.toString(thisObj);
         int startInt = this.toIntegerAsInt(start);
         int len = length == Undefined.instance ? Strings.length(thisStr) : this.toIntegerAsInt(length);
         return this.substrIntl(thisStr, startInt, len);
      }

      private Object substrIntl(TruffleString thisStr, int start, int length) {
         int startInt = start;
         if (start < 0) {
            this.startNegativeBranch.enter();
            startInt = Math.max(start + Strings.length(thisStr), 0);
         }

         int finalLen = within(length, 0, Math.max(0, Strings.length(thisStr) - startInt));
         if (finalLen <= 0) {
            this.finalLenEmptyBranch.enter();
            return Strings.EMPTY_STRING;
         } else {
            return Strings.substring(this.getContext(), this.substringNode, thisStr, startInt, startInt + finalLen - startInt);
         }
      }
   }

   public abstract static class JSStringSubstringNode extends StringPrototypeBuiltins.JSStringOperation implements JSBuiltinNode.Inlineable {
      private final ConditionProfile startLowerEnd = ConditionProfile.createBinaryProfile();
      @Node.Child
      private TruffleString.SubstringByteIndexNode substringNode = TruffleString.SubstringByteIndexNode.create();

      public JSStringSubstringNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      protected TruffleString substring(TruffleString thisStr, int start, int end) {
         int len = Strings.length(thisStr);
         int finalStart = within(start, 0, len);
         int finalEnd = within(end, 0, len);
         return this.substringIntl(thisStr, finalStart, finalEnd);
      }

      @Specialization(guards = "isUndefined(end)")
      protected TruffleString substringStart(TruffleString thisStr, int start, Object end) {
         int len = Strings.length(thisStr);
         int finalStart = within(start, 0, len);
         return this.substringIntl(thisStr, finalStart, len);
      }

      private TruffleString substringIntl(TruffleString thisStr, int start, int end) {
         int fromIndex;
         int length;
         if (this.startLowerEnd.profile(start <= end)) {
            fromIndex = start;
            length = end - start;
         } else {
            fromIndex = end;
            length = start - end;
         }

         return Strings.substring(this.getContext(), this.substringNode, thisStr, fromIndex, length);
      }

      @Specialization(replaces = {"substring", "substringStart"})
      protected TruffleString substringGeneric(
         Object thisObj,
         Object start,
         Object end,
         @Cached JSToNumberNode toNumberNode,
         @Cached JSToNumberNode toNumber2Node,
         @Cached("createBinaryProfile()") ConditionProfile startUndefined,
         @Cached("createBinaryProfile()") ConditionProfile endUndefined
      ) {
         this.requireObjectCoercible(thisObj);
         TruffleString thisStr = this.toString(thisObj);
         int len = Strings.length(thisStr);
         int intStart;
         if (startUndefined.profile(start == Undefined.instance)) {
            intStart = 0;
         } else {
            intStart = withinNumber(toNumberNode.executeNumber(start), 0, len);
         }

         int intEnd;
         if (endUndefined.profile(end == Undefined.instance)) {
            intEnd = len;
         } else {
            intEnd = withinNumber(toNumber2Node.executeNumber(end), 0, len);
         }

         return this.substringIntl(thisStr, intStart, intEnd);
      }

      public StringPrototypeBuiltins.JSStringSubstringNode.Inlined createInlined() {
         return StringPrototypeBuiltinsFactory.JSStringSubstringNodeGen.InlinedNodeGen.create(this.getContext(), this.getBuiltin(), this.getArguments());
      }

      public abstract static class Inlined extends StringPrototypeBuiltins.JSStringSubstringNode implements JSBuiltinNode.Inlined {
         public Inlined(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
         }

         @Specialization
         @Override
         protected TruffleString substringGeneric(
            Object thisObj,
            Object start,
            Object end,
            @Cached JSToNumberNode toNumberNode,
            @Cached JSToNumberNode toNumber2Node,
            @Cached("createBinaryProfile()") ConditionProfile startUndefined,
            @Cached("createBinaryProfile()") ConditionProfile endUndefined
         ) {
            throw this.rewriteToCall();
         }

         protected abstract Object executeWithArguments(Object arg0, Object arg1, Object arg2);

         @Override
         public Object callInlined(Object[] arguments) {
            if (JSArguments.getUserArgumentCount(arguments) < 1) {
               throw this.rewriteToCall();
            } else {
               Object thisObj = JSArguments.getThisObject(arguments);
               Object start = JSArguments.getUserArgument(arguments, 0);
               Object end = JSArguments.getUserArgumentCount(arguments) >= 2 ? JSArguments.getUserArgument(arguments, 1) : Undefined.instance;
               return this.executeWithArguments(thisObj, start, end);
            }
         }
      }
   }

   public abstract static class JSStringToLocaleLowerCaseIntlNode extends StringPrototypeBuiltins.JSStringToLocaleXCaseIntl {
      public JSStringToLocaleLowerCaseIntlNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Override
      protected TruffleString toXCase(String thisStr, String[] locales) {
         return IntlUtil.toLowerCase(this.getContext(), thisStr, locales);
      }
   }

   public abstract static class JSStringToLocaleUpperCaseIntlNode extends StringPrototypeBuiltins.JSStringToLocaleXCaseIntl {
      public JSStringToLocaleUpperCaseIntlNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Override
      protected TruffleString toXCase(String thisStr, String[] locales) {
         return IntlUtil.toUpperCase(this.getContext(), thisStr, locales);
      }
   }

   public abstract static class JSStringToLocaleXCaseIntl extends StringPrototypeBuiltins.JSStringOperation {
      @Node.Child
      JSToCanonicalizedLocaleListNode toCanonicalizedLocaleListNode;

      public JSStringToLocaleXCaseIntl(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
         this.toCanonicalizedLocaleListNode = JSToCanonicalizedLocaleListNode.create(context);
      }

      @Specialization
      protected Object toDesiredCase(Object thisObj, Object locale) {
         this.requireObjectCoercible(thisObj);
         TruffleString thisStr = this.toString(thisObj);
         if (thisStr != null && !Strings.isEmpty(thisStr)) {
            String[] locales = this.toCanonicalizedLocaleListNode.executeLanguageTags(locale);
            return this.toXCase(Strings.toJavaString(thisStr), locales);
         } else {
            return thisStr;
         }
      }

      protected TruffleString toXCase(String thisStr, String[] locales) {
         throw new UnsupportedOperationException();
      }
   }

   public abstract static class JSStringToLowerCaseNode extends StringPrototypeBuiltins.JSStringOperation {
      private final boolean locale;

      public JSStringToLowerCaseNode(JSContext context, JSBuiltin builtin, boolean locale) {
         super(context, builtin);
         this.locale = locale;
      }

      @Specialization
      protected Object toLowerCaseString(TruffleString thisStr) {
         return this.toLowerCaseIntl(thisStr);
      }

      @Specialization(guards = "!isString(thisObj)")
      protected Object toLowerCase(Object thisObj) {
         this.requireObjectCoercible(thisObj);
         TruffleString thisStr = this.toString(thisObj);
         return this.toLowerCaseIntl(thisStr);
      }

      private Object toLowerCaseIntl(TruffleString str) {
         return Strings.toLowerCase(str, this.locale ? this.getContext().getLocale() : Locale.US);
      }
   }

   @ImportStatic(JSConfig.class)
   public abstract static class JSStringToStringNode extends JSBuiltinNode {
      public JSStringToStringNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      public static StringPrototypeBuiltins.JSStringToStringNode createStringToString(JSContext context) {
         return StringPrototypeBuiltinsFactory.JSStringToStringNodeGen.create(context, null, null);
      }

      @Specialization
      protected TruffleString toStringTString(TruffleString thisStr) {
         return thisStr;
      }

      @Specialization(guards = "isJSString(thisStr)")
      protected TruffleString toStringString(JSDynamicObject thisStr) {
         return JSString.getString(thisStr);
      }

      @Specialization(guards = "isForeignObject(thisObj)", limit = "InteropLibraryLimit")
      protected TruffleString toStringForeignObject(Object thisObj, @CachedLibrary("thisObj") InteropLibrary interop) {
         if (interop.isString(thisObj)) {
            try {
               return interop.asTruffleString(thisObj);
            } catch (UnsupportedMessageException var4) {
               throw Errors.createTypeErrorUnboxException(thisObj, var4, this);
            }
         } else {
            return this.toStringOther(thisObj);
         }
      }

      @Fallback
      protected TruffleString toStringOther(Object thisObj) {
         throw Errors.createTypeError("string object expected");
      }
   }

   public abstract static class JSStringToUpperCaseNode extends StringPrototypeBuiltins.JSStringOperation {
      private final boolean locale;

      public JSStringToUpperCaseNode(JSContext context, JSBuiltin builtin, boolean locale) {
         super(context, builtin);
         this.locale = locale;
      }

      @Specialization
      protected Object toUpperCaseString(TruffleString thisStr) {
         return this.toUpperCaseIntl(thisStr);
      }

      @Specialization(guards = "!isString(thisObj)")
      protected Object toUpperCaseGeneric(Object thisObj) {
         this.requireObjectCoercible(thisObj);
         TruffleString thisStr = this.toString(thisObj);
         return this.toUpperCaseIntl(thisStr);
      }

      private Object toUpperCaseIntl(TruffleString str) {
         return Strings.toUpperCase(str, this.locale ? this.getContext().getLocale() : Locale.US);
      }
   }

   public abstract static class JSStringTrimLeftNode extends StringPrototypeBuiltins.JSStringOperation {
      private final ConditionProfile lengthExceeded = ConditionProfile.createBinaryProfile();
      private final ConditionProfile lengthZero = ConditionProfile.createBinaryProfile();

      public JSStringTrimLeftNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      protected Object trimLeft(Object thisObj, @Cached TruffleString.SubstringByteIndexNode substringNode, @Cached TruffleString.ReadCharUTF16Node readRawNode) {
         this.requireObjectCoercible(thisObj);
         TruffleString string = this.toString(thisObj);
         int firstIdx = JSRuntime.firstNonWhitespaceIndex(string, true, readRawNode);
         if (this.lengthZero.profile(firstIdx == 0)) {
            return string;
         } else {
            return this.lengthExceeded.profile(firstIdx >= Strings.length(string))
               ? Strings.EMPTY_STRING
               : Strings.substring(this.getContext(), substringNode, string, firstIdx);
         }
      }
   }

   public abstract static class JSStringTrimNode extends StringPrototypeBuiltins.JSStringOperation {
      public JSStringTrimNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      protected Object trimString(TruffleString thisStr, @Cached.Shared("trimWhitespace") @Cached JSTrimWhitespaceNode trimWhitespaceNode) {
         return trimWhitespaceNode.executeString(thisStr);
      }

      @Specialization(guards = "!isString(thisObj)")
      protected Object trimObject(Object thisObj, @Cached.Shared("trimWhitespace") @Cached JSTrimWhitespaceNode trimWhitespaceNode) {
         this.requireObjectCoercible(thisObj);
         return trimWhitespaceNode.executeString(this.toString(thisObj));
      }
   }

   public abstract static class JSStringTrimRightNode extends StringPrototypeBuiltins.JSStringOperation {
      private final ConditionProfile lengthExceeded = ConditionProfile.createBinaryProfile();

      public JSStringTrimRightNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      protected Object trimRight(
         Object thisObj, @Cached TruffleString.ReadCharUTF16Node readRawNode, @Cached TruffleString.SubstringByteIndexNode substringNode
      ) {
         this.requireObjectCoercible(thisObj);
         TruffleString string = this.toString(thisObj);
         int lastIdx = JSRuntime.lastNonWhitespaceIndex(string, true, readRawNode);
         return this.lengthExceeded.profile(lastIdx >= Strings.length(string))
            ? string
            : Strings.substring(this.getContext(), substringNode, string, 0, lastIdx + 1);
      }
   }

   public static enum StringPrototype implements BuiltinEnum<StringPrototypeBuiltins.StringPrototype> {
      charAt(1),
      charCodeAt(1),
      concat(1),
      indexOf(1),
      lastIndexOf(1),
      localeCompare(1),
      match(1),
      replace(2),
      search(1),
      slice(2),
      split(2),
      substring(2),
      toLowerCase(0),
      toLocaleLowerCase(0),
      toUpperCase(0),
      toLocaleUpperCase(0),
      toString(0),
      valueOf(0),
      trim(0),
      substr(2),
      anchor(1),
      big(0),
      blink(0),
      bold(0),
      fixed(0),
      fontcolor(1),
      fontsize(1),
      italics(0),
      link(1),
      small(0),
      strike(0),
      sub(0),
      sup(0),
      startsWith(1),
      endsWith(1),
      includes(1),
      repeat(1),
      codePointAt(1),
      _iterator(0),
      normalize(0),
      padStart(1),
      padEnd(1),
      matchAll(1),
      replaceAll(2),
      at(1);

      private final int length;

      private StringPrototype(int length) {
         this.length = length;
      }

      @Override
      public int getLength() {
         return this.length;
      }

      @Override
      public boolean isAnnexB() {
         return EnumSet.range(substr, sup).contains(this);
      }

      @Override
      public int getECMAScriptVersion() {
         if (EnumSet.range(startsWith, normalize).contains(this)) {
            return 6;
         } else if (EnumSet.range(padStart, padEnd).contains(this)) {
            return 8;
         } else if (matchAll == this) {
            return 11;
         } else if (replaceAll == this) {
            return 12;
         } else {
            return at == this ? 13 : BuiltinEnum.super.getECMAScriptVersion();
         }
      }

      @Override
      public Object getKey() {
         return this == _iterator ? Symbol.SYMBOL_ITERATOR : BuiltinEnum.super.getKey();
      }
   }

   public static final class StringPrototypeExtensionBuiltins
      extends JSBuiltinsContainer.SwitchEnum<StringPrototypeBuiltins.StringPrototypeExtensionBuiltins.StringExtensionBuiltins> {
      protected StringPrototypeExtensionBuiltins() {
         super(JSString.CLASS_NAME_EXTENSIONS, StringPrototypeBuiltins.StringPrototypeExtensionBuiltins.StringExtensionBuiltins.class);
      }

      protected Object createNode(
         JSContext context,
         JSBuiltin builtin,
         boolean construct,
         boolean newTarget,
         StringPrototypeBuiltins.StringPrototypeExtensionBuiltins.StringExtensionBuiltins builtinEnum
      ) {
         switch (builtinEnum) {
            case trimStart:
               return StringPrototypeBuiltinsFactory.JSStringTrimLeftNodeGen.create(context, builtin, args().withThis().createArgumentNodes(context));
            case trimEnd:
               return StringPrototypeBuiltinsFactory.JSStringTrimRightNodeGen.create(context, builtin, args().withThis().createArgumentNodes(context));
            default:
               return null;
         }
      }

      public static enum StringExtensionBuiltins implements BuiltinEnum<StringPrototypeBuiltins.StringPrototypeExtensionBuiltins.StringExtensionBuiltins> {
         trimStart(0),
         trimEnd(0);

         private final int length;

         private StringExtensionBuiltins(int length) {
            this.length = length;
         }

         @Override
         public int getLength() {
            return this.length;
         }
      }
   }
}
