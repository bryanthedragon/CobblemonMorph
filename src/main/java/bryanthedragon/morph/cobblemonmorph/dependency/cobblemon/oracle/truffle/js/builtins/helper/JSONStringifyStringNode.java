package com.oracle.truffle.js.builtins.helper;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.InvalidArrayIndexException;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.api.strings.TruffleStringBuilder;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.builtins.JSArray;
import com.oracle.truffle.js.runtime.builtins.JSBigInt;
import com.oracle.truffle.js.runtime.builtins.JSBoolean;
import com.oracle.truffle.js.runtime.builtins.JSClass;
import com.oracle.truffle.js.runtime.builtins.JSNumber;
import com.oracle.truffle.js.runtime.builtins.JSString;
import com.oracle.truffle.js.runtime.interop.JSInteropUtil;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.Null;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.StringBuilderProfile;
import java.util.List;

public abstract class JSONStringifyStringNode extends JavaScriptBaseNode {
   private final JSContext context;
   @Node.Child
   private PropertyGetNode getToJSONProperty;
   @Node.Child
   private JSFunctionCallNode callToJSONFunction;
   @Node.Child
   private TruffleStringBuilder.AppendCharUTF16Node appendCharNode;
   @Node.Child
   private TruffleStringBuilder.AppendIntNumberNode appendIntNode;
   @Node.Child
   private TruffleStringBuilder.AppendLongNumberNode appendLongNode;
   @Node.Child
   private TruffleStringBuilder.AppendStringNode appendStringNode;
   @Node.Child
   private TruffleStringBuilder.ToStringNode builderToStringNode;
   private final StringBuilderProfile stringBuilderProfile;

   protected JSONStringifyStringNode(JSContext context) {
      this.context = context;
      this.stringBuilderProfile = StringBuilderProfile.create(context.getStringLengthLimit());
   }

   public abstract Object execute(Object data, Object keyStr, JSDynamicObject holder);

   public static JSONStringifyStringNode create(JSContext context) {
      return JSONStringifyStringNodeGen.create(context);
   }

   @Specialization
   public Object jsonStrMain(Object jsonData, TruffleString keyStr, JSDynamicObject holder) {
      try {
         assert jsonData instanceof JSONData;

         JSONData data = (JSONData)jsonData;
         Object value = this.jsonStrPrepare(data, keyStr, holder);
         if (!isStringifyable(value)) {
            return Undefined.instance;
         } else {
            TruffleStringBuilder sb = this.stringBuilderProfile.newStringBuilder();
            this.jsonStrExecute(sb, data, value);
            return this.builderToString(sb);
         }
      } catch (StackOverflowError var7) {
         throwStackError();
         return null;
      }
   }

   private static boolean isStringifyable(Object value) {
      return value != Undefined.instance;
   }

   @CompilerDirectives.TruffleBoundary
   private void jsonStrExecute(TruffleStringBuilder builder, JSONData data, Object value) {
      assert isStringifyable(value);

      if (value == Null.instance) {
         this.append(builder, Null.NAME);
      } else if (value instanceof Boolean) {
         this.append(builder, (Boolean)value ? JSBoolean.TRUE_NAME : JSBoolean.FALSE_NAME);
      } else if (Strings.isTString(value)) {
         this.jsonQuote(builder, (TruffleString)value);
      } else if (JSRuntime.isNumber(value)) {
         this.appendNumber(builder, (Number)value);
      } else {
         if (JSRuntime.isBigInt(value)) {
            throw Errors.createTypeError("Do not know how to serialize a BigInt");
         }

         if (JSDynamicObject.isJSDynamicObject(value) && !JSRuntime.isCallableIsJSObject((JSDynamicObject)value)) {
            JSDynamicObject valueObj = (JSDynamicObject)value;
            if (JSRuntime.isArray(valueObj)) {
               this.jsonJA(builder, data, valueObj);
            } else {
               this.jsonJO(builder, data, valueObj);
            }
         } else if (value instanceof TruffleObject) {
            assert JSGuards.isForeignObject(value);

            this.jsonForeignObject(builder, data, value);
         } else {
            if (!JSRuntime.isJavaPrimitive(value)) {
               throw new RuntimeException("JSON.stringify: should never reach here, unknown type: " + value + " " + value.getClass());
            }

            this.jsonQuote(builder, Strings.fromJavaString(value.toString()));
         }
      }
   }

   private void jsonForeignObject(TruffleStringBuilder sb, JSONData data, Object obj) {
      InteropLibrary interop = InteropLibrary.getFactory().getUncached(obj);
      if (interop.isNull(obj)) {
         this.append(sb, Null.NAME);
      } else if (JSInteropUtil.isBoxedPrimitive(obj, interop)) {
         Object unboxed = JSInteropUtil.toPrimitiveOrDefault(obj, Null.instance, interop, this);

         assert !JSGuards.isForeignObject(unboxed);

         this.jsonStrExecute(sb, data, unboxed);
      } else if (interop.hasArrayElements(obj)) {
         this.jsonJA(sb, data, obj);
      } else {
         this.jsonJO(sb, data, obj);
      }
   }

   private void appendNumber(TruffleStringBuilder builder, Number n) {
      double d = JSRuntime.doubleValue(n);
      if (Double.isNaN(d) || Double.isInfinite(d)) {
         this.append(builder, Null.NAME);
      } else if (n instanceof Integer) {
         this.append(builder, (Integer)n);
      } else if (n instanceof Long) {
         this.append(builder, (Long)n);
      } else {
         this.append(builder, JSRuntime.doubleToString(d));
      }
   }

   @CompilerDirectives.TruffleBoundary
   private Object jsonStrPrepare(JSONData data, TruffleString keyStr, Object holder) {
      Object value;
      if (JSDynamicObject.isJSDynamicObject(holder)) {
         value = JSObject.get((JSDynamicObject)holder, keyStr);
      } else {
         value = this.truffleRead(holder, keyStr);
      }

      return this.jsonStrPreparePart2(data, keyStr, holder, value);
   }

   @CompilerDirectives.TruffleBoundary
   private Object jsonStrPrepareArray(JSONData data, int key, JSDynamicObject holder) {
      Object value = JSObject.get(holder, key);
      return this.jsonStrPreparePart2(data, Strings.fromInt(key), holder, value);
   }

   @CompilerDirectives.TruffleBoundary
   private Object jsonStrPrepareForeign(JSONData data, int key, Object holder) {
      assert JSGuards.isForeignObject(holder);

      Object value = this.truffleRead(holder, key);
      return this.jsonStrPreparePart2(data, Strings.fromInt(key), holder, value);
   }

   private Object jsonStrPreparePart2(JSONData data, Object key, Object holder, Object valueArg) {
      Object value = valueArg;
      boolean tryToJSON = false;
      if (JSRuntime.isObject(valueArg) || JSRuntime.isBigInt(valueArg)) {
         tryToJSON = true;
      } else if (JSRuntime.isForeignObject(valueArg)) {
         InteropLibrary interop = InteropLibrary.getUncached(valueArg);
         tryToJSON = interop.hasMembers(valueArg) && !interop.isNull(valueArg) && !JSInteropUtil.isBoxedPrimitive(valueArg, interop);
      }

      if (tryToJSON) {
         value = this.jsonStrPrepareObject(key, valueArg);
      }

      if (data.getReplacerFnObj() != null) {
         value = JSRuntime.call(data.getReplacerFnObj(), holder, new Object[]{key, value});
      }

      if (JSDynamicObject.isJSDynamicObject(value)) {
         return jsonStrPrepareJSObject((JSDynamicObject)value);
      } else if (value instanceof Symbol) {
         return Undefined.instance;
      } else {
         return JSRuntime.isCallableForeign(value) ? Undefined.instance : value;
      }
   }

   private static Object jsonStrPrepareJSObject(JSDynamicObject valueObj) {
      JSClass builtinClass = JSObject.getJSClass(valueObj);
      if (builtinClass == JSNumber.INSTANCE) {
         return JSRuntime.toNumber(valueObj);
      } else if (builtinClass == JSBigInt.INSTANCE) {
         return JSBigInt.valueOf(valueObj);
      } else if (builtinClass == JSString.INSTANCE) {
         return JSRuntime.toString(valueObj);
      } else if (builtinClass == JSBoolean.INSTANCE) {
         return JSBoolean.valueOf(valueObj);
      } else {
         return JSRuntime.isCallableIsJSObject(valueObj) ? Undefined.instance : valueObj;
      }
   }

   private Object jsonStrPrepareObject(Object key, Object value) {
      assert JSRuntime.isPropertyKey(key);

      if (this.getToJSONProperty == null) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.getToJSONProperty = this.insert(PropertyGetNode.create(Strings.TO_JSON, false, this.context));
      }

      Object toJSON = this.getToJSONProperty.getValue(value);
      return JSRuntime.isCallable(toJSON) ? this.jsonStrPrepareObjectFunction(key, value, toJSON) : value;
   }

   private Object jsonStrPrepareObjectFunction(Object key, Object value, Object toJSON) {
      if (this.callToJSONFunction == null) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.callToJSONFunction = this.insert(JSFunctionCallNode.createCall());
      }

      return this.callToJSONFunction.executeCall(JSArguments.createOneArg(value, toJSON, key));
   }

   @CompilerDirectives.TruffleBoundary
   private TruffleStringBuilder jsonJO(TruffleStringBuilder sb, JSONData data, Object value) {
      checkCycle(data, value);
      data.pushStack(value);
      checkStackDepth(data);
      int stepback = data.getIndent();
      int indent = data.getIndent() + 1;
      data.setIndent(indent);
      this.concatStart(sb, '{');
      int lengthBefore = StringBuilderProfile.length(sb);
      if (data.getPropertyList() == null) {
         if (JSDynamicObject.isJSDynamicObject(value)) {
            this.serializeJSONObjectProperties(sb, data, value, indent, JSObject.enumerableOwnNames((JSDynamicObject)value));
         } else {
            this.serializeForeignObjectProperties(sb, data, value, indent);
         }
      } else {
         this.serializeJSONObjectProperties(sb, data, value, indent, data.getPropertyList());
      }

      this.concatEnd(sb, data, stepback, '}', lengthBefore != StringBuilderProfile.length(sb));
      data.popStack();
      data.setIndent(stepback);
      return sb;
   }

   private TruffleStringBuilder serializeJSONObjectProperties(TruffleStringBuilder sb, JSONData data, Object value, int indent, List<? extends Object> keys) {
      boolean isFirst = true;

      for (Object key : keys) {
         TruffleString name = (TruffleString)key;
         Object strPPrepared = this.jsonStrPrepare(data, name, value);
         if (isStringifyable(strPPrepared)) {
            if (isFirst) {
               this.concatFirstStep(sb, data);
               isFirst = false;
            } else {
               this.appendSeparator(sb, data, indent);
            }

            this.jsonQuote(sb, name);
            this.appendColon(sb, data);
            this.jsonStrExecute(sb, data, strPPrepared);
         }
      }

      return sb;
   }

   private void appendColon(TruffleStringBuilder sb, JSONData data) {
      this.append(sb, ':');
      if (Strings.length(data.getGap()) > 0) {
         this.append(sb, ' ');
      }
   }

   private void serializeForeignObjectProperties(TruffleStringBuilder sb, JSONData data, Object obj, int indent) {
      try {
         InteropLibrary objInterop = InteropLibrary.getFactory().getUncached(obj);
         if (objInterop.hasMembers(obj)) {
            Object keysObj = objInterop.getMembers(obj);
            InteropLibrary keysInterop = InteropLibrary.getFactory().getUncached(keysObj);
            long size = keysInterop.getArraySize(keysObj);
            boolean isFirst = true;

            for (long i = 0L; i < size; i++) {
               Object key = keysInterop.readArrayElement(keysObj, i);

               assert InteropLibrary.getUncached().isString(key);

               TruffleString stringKey = key instanceof TruffleString ? (TruffleString)key : InteropLibrary.getUncached().asTruffleString(key);
               if (objInterop.isMemberReadable(obj, Strings.toJavaString(stringKey))) {
                  Object memberValue = this.truffleRead(obj, stringKey);
                  Object strPPrepared = this.jsonStrPreparePart2(data, stringKey, obj, memberValue);
                  if (isStringifyable(strPPrepared)) {
                     if (isFirst) {
                        this.concatFirstStep(sb, data);
                        isFirst = false;
                     } else {
                        this.appendSeparator(sb, data, indent);
                     }

                     this.jsonQuote(sb, stringKey);
                     this.appendColon(sb, data);
                     this.jsonStrExecute(sb, data, strPPrepared);
                  }
               }
            }
         }
      } catch (InvalidArrayIndexException | UnsupportedMessageException var17) {
         throw Errors.createTypeErrorInteropException(obj, var17, "SerializeJSONObject", this);
      }
   }

   @CompilerDirectives.TruffleBoundary
   private TruffleStringBuilder jsonJA(TruffleStringBuilder sb, JSONData data, Object value) {
      checkCycle(data, value);

      assert JSRuntime.isArray(value) || InteropLibrary.getUncached().hasArrayElements(value);

      data.pushStack(value);
      checkStackDepth(data);
      int stepback = data.getIndent();
      int indent = data.getIndent() + 1;
      data.setIndent(indent);
      boolean isForeign = false;
      boolean isArray = false;
      Object lenObject;
      if (JSDynamicObject.isJSDynamicObject(value)) {
         lenObject = JSObject.get((JSDynamicObject)value, JSArray.LENGTH);
         if (JSArray.isJSArray(value)) {
            isArray = true;
         }
      } else {
         lenObject = this.truffleGetSize(value);
         isForeign = true;
      }

      long length = JSRuntime.toLength(lenObject);
      if (length > this.context.getStringLengthLimit()) {
         throw Errors.createRangeErrorInvalidStringLength();
      } else {
         int len = (int)length;
         this.concatStart(sb, '[');

         for (int index = 0; index < len; index++) {
            if (index == 0) {
               this.concatFirstStep(sb, data);
            } else {
               this.appendSeparator(sb, data, indent);
            }

            Object strPPrepared;
            if (isArray) {
               strPPrepared = this.jsonStrPrepareArray(data, index, (JSDynamicObject)value);
            } else if (isForeign) {
               strPPrepared = this.jsonStrPrepareForeign(data, index, value);
            } else {
               strPPrepared = this.jsonStrPrepare(data, Strings.fromInt(index), value);
            }

            if (isStringifyable(strPPrepared)) {
               this.jsonStrExecute(sb, data, strPPrepared);
            } else {
               this.append(sb, Null.NAME);
            }
         }

         this.concatEnd(sb, data, stepback, ']', len > 0);
         data.popStack();
         data.setIndent(stepback);
         return sb;
      }
   }

   private static void checkStackDepth(JSONData data) {
      if (data.stackTooDeep()) {
         throwStackError();
      }
   }

   private static void throwStackError() {
      throw Errors.createRangeError("cannot stringify objects nested that deep");
   }

   private void concatStart(TruffleStringBuilder builder, char c) {
      this.append(builder, c);
   }

   private void concatFirstStep(TruffleStringBuilder sb, JSONData data) {
      if (Strings.length(data.getGap()) > 0) {
         this.append(sb, '\n');

         for (int i = 0; i < data.getIndent(); i++) {
            this.append(sb, data.getGap());
         }
      }
   }

   private void concatEnd(TruffleStringBuilder sb, JSONData data, int stepback, char close, boolean hasContent) {
      if (Strings.length(data.getGap()) > 0 && hasContent) {
         this.append(sb, '\n');

         for (int i = 0; i < stepback; i++) {
            this.append(sb, data.getGap());
         }
      }

      this.append(sb, close);
   }

   @CompilerDirectives.TruffleBoundary
   private void appendSeparator(TruffleStringBuilder sb, JSONData data, int indent) {
      if (Strings.length(data.getGap()) <= 0) {
         this.append(sb, ',');
      } else {
         this.append(sb, Strings.COMMA_NEWLINE);

         for (int i = 0; i < indent; i++) {
            this.append(sb, data.getGap());
         }
      }
   }

   private static void checkCycle(JSONData data, Object value) {
      if (data.stack.contains(value)) {
         throw Errors.createTypeError("Converting circular structure to JSON");
      }
   }

   private TruffleStringBuilder jsonQuote(TruffleStringBuilder builder, TruffleString valueStr) {
      return jsonQuote(this.stringBuilderProfile, builder, valueStr, this.getAppendCharNode(), this.getAppendStringNode());
   }

   public static TruffleStringBuilder jsonQuote(
      StringBuilderProfile stringBuilderProfile,
      TruffleStringBuilder sb,
      TruffleString valueStr,
      TruffleStringBuilder.AppendCharUTF16Node appendCharNode,
      TruffleStringBuilder.AppendStringNode appendStringNode
   ) {
      stringBuilderProfile.append(appendCharNode, sb, '"');

      for (int i = 0; i < Strings.length(valueStr); i++) {
         char ch = Strings.charAt(valueStr, i);
         if (ch < ' ') {
            if (ch == '\b') {
               stringBuilderProfile.append(appendStringNode, sb, Strings.BACKSLASH_B);
            } else if (ch == '\f') {
               stringBuilderProfile.append(appendStringNode, sb, Strings.BACKSLASH_F);
            } else if (ch == '\n') {
               stringBuilderProfile.append(appendStringNode, sb, Strings.BACKSLASH_N);
            } else if (ch == '\r') {
               stringBuilderProfile.append(appendStringNode, sb, Strings.BACKSLASH_R);
            } else if (ch == '\t') {
               stringBuilderProfile.append(appendStringNode, sb, Strings.BACKSLASH_T);
            } else {
               jsonQuoteUnicode(stringBuilderProfile, sb, ch, appendCharNode, appendStringNode);
            }
         } else if (ch == '\\') {
            stringBuilderProfile.append(appendStringNode, sb, Strings.BACKSLASH_BACKSLASH);
         } else if (ch == '"') {
            stringBuilderProfile.append(appendStringNode, sb, Strings.BACKSLASH_DOUBLE_QUOTE);
         } else if (Character.isSurrogate(ch)) {
            if (Character.isHighSurrogate(ch)) {
               char nextCh;
               if (i + 1 < Strings.length(valueStr) && Character.isLowSurrogate(nextCh = Strings.charAt(valueStr, i + 1))) {
                  stringBuilderProfile.append(appendCharNode, sb, ch);
                  stringBuilderProfile.append(appendCharNode, sb, nextCh);
                  i++;
               } else {
                  jsonQuoteSurrogate(stringBuilderProfile, sb, ch, appendCharNode, appendStringNode);
               }
            } else {
               jsonQuoteSurrogate(stringBuilderProfile, sb, ch, appendCharNode, appendStringNode);
            }
         } else {
            stringBuilderProfile.append(appendCharNode, sb, ch);
         }
      }

      stringBuilderProfile.append(appendCharNode, sb, '"');
      return sb;
   }

   private static void jsonQuoteUnicode(
      StringBuilderProfile stringBuilderProfile,
      TruffleStringBuilder sb,
      char c,
      TruffleStringBuilder.AppendCharUTF16Node appendCharNode,
      TruffleStringBuilder.AppendStringNode appendStringNode
   ) {
      stringBuilderProfile.append(appendStringNode, sb, Strings.BACKSLASH_U00);
      stringBuilderProfile.append(appendCharNode, sb, Character.forDigit(c >> 4 & 15, 16));
      stringBuilderProfile.append(appendCharNode, sb, Character.forDigit(c & 15, 16));
   }

   private static void jsonQuoteSurrogate(
      StringBuilderProfile stringBuilderProfile,
      TruffleStringBuilder sb,
      char c,
      TruffleStringBuilder.AppendCharUTF16Node appendCharNode,
      TruffleStringBuilder.AppendStringNode appendStringNode
   ) {
      stringBuilderProfile.append(appendStringNode, sb, Strings.BACKSLASH_UD);
      stringBuilderProfile.append(appendCharNode, sb, Character.forDigit(c >> '\b' & 15, 16));
      stringBuilderProfile.append(appendCharNode, sb, Character.forDigit(c >> 4 & 15, 16));
      stringBuilderProfile.append(appendCharNode, sb, Character.forDigit(c & 15, 16));
   }

   private Object truffleGetSize(Object obj) {
      return JSInteropUtil.getArraySize(obj, InteropLibrary.getUncached(), this);
   }

   private Object truffleRead(Object obj, TruffleString keyStr) {
      try {
         return JSRuntime.importValue(InteropLibrary.getUncached().readMember(obj, Strings.toJavaString(keyStr)));
      } catch (UnknownIdentifierException | UnsupportedMessageException var4) {
         throw Errors.createTypeErrorInteropException(obj, var4, "readMember", keyStr, this);
      }
   }

   private Object truffleRead(Object obj, int index) {
      try {
         return JSRuntime.importValue(InteropLibrary.getUncached().readArrayElement(obj, index));
      } catch (InvalidArrayIndexException | UnsupportedMessageException var4) {
         throw Errors.createTypeErrorInteropException(obj, var4, "readArrayElement", index, this);
      }
   }

   private TruffleStringBuilder.AppendStringNode getAppendStringNode() {
      if (this.appendStringNode == null) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.appendStringNode = this.insert(TruffleStringBuilder.AppendStringNode.create());
      }

      return this.appendStringNode;
   }

   private void append(TruffleStringBuilder sb, TruffleString s) {
      this.stringBuilderProfile.append(this.getAppendStringNode(), sb, s);
   }

   private TruffleStringBuilder.AppendCharUTF16Node getAppendCharNode() {
      if (this.appendCharNode == null) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.appendCharNode = this.insert(TruffleStringBuilder.AppendCharUTF16Node.create());
      }

      return this.appendCharNode;
   }

   private void append(TruffleStringBuilder sb, char value) {
      this.stringBuilderProfile.append(this.getAppendCharNode(), sb, value);
   }

   private void append(TruffleStringBuilder sb, int value) {
      if (this.appendIntNode == null) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.appendIntNode = this.insert(TruffleStringBuilder.AppendIntNumberNode.create());
      }

      this.stringBuilderProfile.append(this.appendIntNode, sb, value);
   }

   private void append(TruffleStringBuilder sb, long value) {
      if (this.appendLongNode == null) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.appendLongNode = this.insert(TruffleStringBuilder.AppendLongNumberNode.create());
      }

      this.stringBuilderProfile.append(this.appendLongNode, sb, value);
   }

   private TruffleString builderToString(TruffleStringBuilder sb) {
      if (this.builderToStringNode == null) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.builderToStringNode = this.insert(TruffleStringBuilder.ToStringNode.create());
      }

      return StringBuilderProfile.toString(this.builderToStringNode, sb);
   }
}
