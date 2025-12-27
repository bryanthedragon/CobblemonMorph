package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.InvalidArrayIndexException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.access.ReadElementNode;
import com.oracle.truffle.js.nodes.access.WriteElementNode;
import com.oracle.truffle.js.nodes.interop.ArrayElementInfoNode;
import com.oracle.truffle.js.nodes.interop.ExportValueNode;
import com.oracle.truffle.js.nodes.interop.ImportValueNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.interop.InteropArray;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.HashMap;
import java.util.Map;

@ExportLibrary(InteropLibrary.class)
public class JSArgumentsObject extends JSArrayBase {
   protected JSArgumentsObject(Shape shape, ScriptArray arrayType, Object array, int length) {
      super(shape, arrayType, array, null, length, 0, 0, 0, 0);
   }

   @Override
   public final TruffleString getClassName() {
      return JSArgumentsArray.CLASS_NAME;
   }

   @ExportMessage
   public final Object getMembers(boolean includeInternal) {
      assert JSObject.getJSClass(this) == JSArgumentsArray.INSTANCE;

      return InteropArray.create(filterEnumerableNames(this, JSObject.ownPropertyKeys(this), JSArgumentsArray.INSTANCE));
   }

   @ExportMessage
   public final boolean hasArrayElements() {
      return true;
   }

   @ExportMessage
   public final long getArraySize() {
      return JSRuntime.toInteger(JSObject.get(this, JSAbstractArray.LENGTH));
   }

   @ExportMessage
   public final Object readArrayElement(
      long index,
      @CachedLibrary("this") InteropLibrary self,
      @Cached(value = "create(language(self).getJSContext())", uncached = "getUncachedRead()") ReadElementNode readNode,
      @Cached ExportValueNode exportNode
   ) throws InvalidArrayIndexException, UnsupportedMessageException {
      if (index >= 0L && index < self.getArraySize(this)) {
         Object result;
         if (readNode == null) {
            result = JSObject.getOrDefault(this, index, this, Undefined.instance);
         } else {
            result = readNode.executeWithTargetAndIndexOrDefault(this, index, Undefined.instance);
         }

         return exportNode.execute(result);
      } else {
         throw InvalidArrayIndexException.create(index);
      }
   }

   @ExportMessage
   public final boolean isArrayElementReadable(long index, @CachedLibrary("this") InteropLibrary thisLibrary) {
      try {
         return index >= 0L && index < thisLibrary.getArraySize(this);
      } catch (UnsupportedMessageException var5) {
         throw Errors.shouldNotReachHere(var5);
      }
   }

   @ExportMessage
   public final void writeArrayElement(
      long index,
      Object value,
      @Cached.Shared("elementInfo") @Cached ArrayElementInfoNode elements,
      @Cached ImportValueNode castValueNode,
      @Cached(value = "createCachedInterop()", uncached = "getUncachedWrite()") WriteElementNode writeNode
   ) throws InvalidArrayIndexException, UnsupportedMessageException {
      elements.executeCheck(this, index, 6);
      Object importedValue = castValueNode.executeWithTarget(value);
      if (writeNode == null) {
         JSObject.set(this, index, importedValue, true, null);
      } else {
         writeNode.executeWithTargetAndIndexAndValue(this, index, importedValue);
      }
   }

   @ExportMessage
   public final boolean isArrayElementModifiable(long index, @Cached.Shared("elementInfo") @Cached ArrayElementInfoNode elements) {
      return elements.executeBoolean(this, index, 2);
   }

   @ExportMessage
   public final boolean isArrayElementInsertable(long index, @Cached.Shared("elementInfo") @Cached ArrayElementInfoNode elements) {
      return elements.executeBoolean(this, index, 4);
   }

   public static final class Mapped extends JSArgumentsObject {
      protected int connectedArgumentCount;
      protected Map<Long, Object> disconnectedIndices;

      protected Mapped(Shape shape, ScriptArray arrayType, Object array, int length) {
         super(shape, arrayType, array, length);
         this.connectedArgumentCount = length;
      }

      public int getConnectedArgumentCount() {
         return this.connectedArgumentCount;
      }

      public Map<Long, Object> getDisconnectedIndices() {
         assert JSAbstractArgumentsArray.hasDisconnectedIndices(this);

         return this.disconnectedIndices;
      }

      @CompilerDirectives.TruffleBoundary
      public void initDisconnectedIndices() {
         assert JSAbstractArgumentsArray.hasDisconnectedIndices(this);

         this.disconnectedIndices = new HashMap<>();
      }
   }

   public static final class Unmapped extends JSArgumentsObject {
      protected Unmapped(Shape shape, ScriptArray arrayType, Object array, int length) {
         super(shape, arrayType, array, length);
      }
   }
}
