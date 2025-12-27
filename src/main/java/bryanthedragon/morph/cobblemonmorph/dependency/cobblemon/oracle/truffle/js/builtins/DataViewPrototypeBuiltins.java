package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ValueProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.cast.JSToBigIntNode;
import com.oracle.truffle.js.nodes.cast.JSToBooleanNode;
import com.oracle.truffle.js.nodes.cast.JSToIndexNode;
import com.oracle.truffle.js.nodes.cast.JSToNumberNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.array.TypedArray;
import com.oracle.truffle.js.runtime.array.TypedArrayFactory;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.JSArrayBuffer;
import com.oracle.truffle.js.runtime.builtins.JSArrayBufferObject;
import com.oracle.truffle.js.runtime.builtins.JSDataView;
import com.oracle.truffle.js.runtime.builtins.JSDataViewObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.Undefined;

public final class DataViewPrototypeBuiltins extends JSBuiltinsContainer.SwitchEnum<DataViewPrototypeBuiltins.DataViewPrototype> {
   public static final JSBuiltinsContainer BUILTINS = new DataViewPrototypeBuiltins();

   protected DataViewPrototypeBuiltins() {
      super(JSDataView.PROTOTYPE_NAME, DataViewPrototypeBuiltins.DataViewPrototype.class);
   }

   protected Object createNode(
      JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, DataViewPrototypeBuiltins.DataViewPrototype builtinEnum
   ) {
      switch (builtinEnum) {
         case getBigInt64:
         case getBigUint64:
         case getFloat32:
         case getFloat64:
         case getInt16:
         case getInt32:
         case getInt8:
         case getUint16:
         case getUint32:
         case getUint8:
            return DataViewPrototypeBuiltinsFactory.DataViewGetNodeGen.create(context, builtin, args().withThis().fixedArgs(2).createArgumentNodes(context));
         case setBigInt64:
         case setBigUint64:
         case setFloat32:
         case setFloat64:
         case setInt16:
         case setInt32:
         case setInt8:
         case setUint16:
         case setUint32:
         case setUint8:
            return DataViewPrototypeBuiltinsFactory.DataViewSetNodeGen.create(context, builtin, args().withThis().fixedArgs(3).createArgumentNodes(context));
         default:
            return null;
      }
   }

   @ImportStatic(JSDataView.class)
   public abstract static class DataViewAccessNode extends JSBuiltinNode {
      protected final TypedArrayFactory factory;
      @Node.Child
      protected JSToBooleanNode toBooleanNode;
      @Node.Child
      private InteropLibrary interopLibrary;
      protected final BranchProfile errorBranch = BranchProfile.create();

      public DataViewAccessNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
         this.factory = typedArrayFactoryFromType(Strings.lazySubstring(builtin.getName(), 3));
         this.toBooleanNode = this.factory.getBytesPerElement() == 1 ? null : JSToBooleanNode.create();
      }

      private static TypedArrayFactory typedArrayFactoryFromType(TruffleString type) {
         for (TypedArrayFactory factory : TypedArray.factories()) {
            if (Strings.startsWith(factory.getName(), type)) {
               return factory;
            }
         }

         throw new IllegalArgumentException(Strings.toJavaString(type));
      }

      protected final InteropLibrary getInterop() {
         InteropLibrary lib = this.interopLibrary;
         if (lib == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.interopLibrary = lib = this.insert(InteropLibrary.getFactory().createDispatched(5));
         }

         return lib;
      }

      protected final void ensureNotDetached(JSArrayBufferObject buffer) {
         if (!this.getContext().getTypedArrayNotDetachedAssumption().isValid() && JSArrayBuffer.isDetachedBuffer(buffer)) {
            this.errorBranch.enter();
            throw Errors.createTypeErrorDetachedBuffer();
         }
      }

      protected final int getBufferIndex(JSDataViewObject dataView, long getIndex) {
         int viewLength = JSDataView.typedArrayGetLength(dataView);
         int elementSize = this.factory.getBytesPerElement();
         if (getIndex + elementSize > viewLength) {
            this.errorBranch.enter();
            throw Errors.createRangeError("index + elementSize > viewLength");
         } else {
            int viewOffset = JSDataView.typedArrayGetOffset(dataView);

            assert getIndex + (long)viewOffset <= 2147483647L;

            return (int)(getIndex + viewOffset);
         }
      }
   }

   public abstract static class DataViewGetNode extends DataViewPrototypeBuiltins.DataViewAccessNode {
      public DataViewGetNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      protected final Object doDataView(
         JSDataViewObject dataView,
         Object byteOffset,
         Object littleEndian,
         @Cached("create()") JSToIndexNode toIndexNode,
         @Cached("createClassProfile()") ValueProfile bufferTypeProfile,
         @Cached("createClassProfile()") ValueProfile arrayTypeProfile
      ) {
         long getIndex = toIndexNode.executeLong(byteOffset);
         boolean isLittleEndian = this.factory.getBytesPerElement() == 1 || this.toBooleanNode.executeBoolean(littleEndian);
         JSArrayBufferObject buffer = bufferTypeProfile.profile(JSDataView.getArrayBuffer(dataView));
         this.ensureNotDetached(buffer);
         int bufferIndex = this.getBufferIndex(dataView, getIndex);
         boolean isInteropBuffer = JSArrayBuffer.isJSInteropArrayBuffer(buffer);
         TypedArray strategy = arrayTypeProfile.profile(
            this.factory.createArrayType(JSArrayBuffer.isJSDirectOrSharedArrayBuffer(buffer), true, isInteropBuffer)
         );
         return strategy.getBufferElement(buffer, bufferIndex, isLittleEndian, isInteropBuffer ? this.getInterop() : null);
      }

      @Specialization(guards = "!isJSDataView(thisObj)")
      protected JSDynamicObject doIncompatibleReceiver(Object thisObj, Object byteOffset, Object littleEndian) {
         throw Errors.createTypeErrorNotADataView();
      }
   }

   public static enum DataViewPrototype implements BuiltinEnum<DataViewPrototypeBuiltins.DataViewPrototype> {
      getBigInt64(1),
      getBigUint64(1),
      getFloat32(1),
      getFloat64(1),
      getInt8(1),
      getInt16(1),
      getInt32(1),
      getUint8(1),
      getUint16(1),
      getUint32(1),
      setBigInt64(2),
      setBigUint64(2),
      setFloat32(2),
      setFloat64(2),
      setInt8(2),
      setInt16(2),
      setInt32(2),
      setUint8(2),
      setUint16(2),
      setUint32(2);

      private final int length;

      private DataViewPrototype(int length) {
         this.length = length;
      }

      @Override
      public int getLength() {
         return this.length;
      }
   }

   public abstract static class DataViewSetNode extends DataViewPrototypeBuiltins.DataViewAccessNode {
      @Node.Child
      private JSToNumberNode toNumberNode;
      @Node.Child
      private JSToBigIntNode toBigIntNode;

      public DataViewSetNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
         if (JSRuntime.isTypedArrayBigIntFactory(this.factory)) {
            this.toBigIntNode = JSToBigIntNode.create();
         } else {
            this.toNumberNode = JSToNumberNode.create();
         }
      }

      @Specialization
      protected Object doDataView(
         JSDataViewObject dataView,
         Object byteOffset,
         Object value,
         Object littleEndian,
         @Cached("create()") JSToIndexNode toIndexNode,
         @Cached("createClassProfile()") ValueProfile bufferTypeProfile,
         @Cached("createClassProfile()") ValueProfile arrayTypeProfile
      ) {
         long getIndex = toIndexNode.executeLong(byteOffset);
         Object numberValue = JSRuntime.isTypedArrayBigIntFactory(this.factory)
            ? this.toBigIntNode.executeBigInteger(value)
            : this.toNumberNode.executeNumber(value);
         boolean isLittleEndian = this.factory.getBytesPerElement() == 1 || this.toBooleanNode.executeBoolean(littleEndian);
         JSArrayBufferObject buffer = bufferTypeProfile.profile(JSDataView.getArrayBuffer(dataView));
         this.ensureNotDetached(buffer);
         int bufferIndex = this.getBufferIndex(dataView, getIndex);
         boolean isInteropBuffer = JSArrayBuffer.isJSInteropArrayBuffer(buffer);
         TypedArray strategy = arrayTypeProfile.profile(
            this.factory.createArrayType(JSArrayBuffer.isJSDirectOrSharedArrayBuffer(buffer), true, isInteropBuffer)
         );
         strategy.setBufferElement(buffer, bufferIndex, isLittleEndian, numberValue, isInteropBuffer ? this.getInterop() : null);
         return Undefined.instance;
      }

      @Specialization(guards = "!isJSDataView(thisObj)")
      protected JSDynamicObject doIncompatibleReceiver(Object thisObj, Object byteOffset, Object value, Object littleEndian) {
         throw Errors.createTypeErrorNotADataView();
      }
   }
}
