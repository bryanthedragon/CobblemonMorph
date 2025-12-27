package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.StopIterationException;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.interop.UnknownKeyException;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.js.builtins.helper.JSCollectionsNormalizeNode;
import com.oracle.truffle.js.nodes.interop.ExportValueNode;
import com.oracle.truffle.js.nodes.interop.ImportValueNode;
import com.oracle.truffle.js.runtime.interop.InteropArray;
import com.oracle.truffle.js.runtime.objects.JSNonProxyObject;
import com.oracle.truffle.js.runtime.util.JSHashMap;

@ExportLibrary(InteropLibrary.class)
public final class JSMapObject extends JSNonProxyObject {
   private final JSHashMap map;

   protected JSMapObject(Shape shape, JSHashMap map) {
      super(shape);
      this.map = map;
   }

   public JSHashMap getMap() {
      return this.map;
   }

   @ExportMessage
   public boolean hasHashEntries() {
      return true;
   }

   @ExportMessage
   long getHashSize() {
      return this.getMap().size();
   }

   @ExportMessage
   Object getHashEntriesIterator() {
      return new JSMapObject.EntriesIterator(this.getMap().getEntries());
   }

   @ExportMessage
   boolean isHashEntryReadable(
      Object key,
      @Cached @Cached.Shared("importKeyNode") ImportValueNode importKeyNode,
      @Cached @Cached.Shared("normalizeKeyNode") JSCollectionsNormalizeNode normalizeKeyNode
   ) {
      Object normalizedKey = normalizeKeyNode.execute(importKeyNode.executeWithTarget(key));
      return this.getMap().has(normalizedKey);
   }

   @ExportMessage
   Object readHashValue(
      Object key,
      @Cached @Cached.Shared("exportValueNode") ExportValueNode exportValueNode,
      @Cached @Cached.Shared("importKeyNode") ImportValueNode importKeyNode,
      @Cached @Cached.Shared("normalizeKeyNode") JSCollectionsNormalizeNode normalizeKeyNode
   ) throws UnknownKeyException {
      Object normalizedKey = normalizeKeyNode.execute(importKeyNode.executeWithTarget(key));
      Object value = this.getMap().get(normalizedKey);
      if (value == null) {
         throw UnknownKeyException.create(key);
      } else {
         return exportValueNode.execute(value);
      }
   }

   @ExportMessage
   Object readHashValueOrDefault(
      Object key,
      Object defaultValue,
      @Cached @Cached.Shared("exportValueNode") ExportValueNode exportValueNode,
      @Cached @Cached.Shared("importKeyNode") ImportValueNode importKeyNode,
      @Cached @Cached.Shared("normalizeKeyNode") JSCollectionsNormalizeNode normalizeKeyNode
   ) {
      Object normalizedKey = normalizeKeyNode.execute(importKeyNode.executeWithTarget(key));
      Object value = this.getMap().get(normalizedKey);
      return value == null ? defaultValue : exportValueNode.execute(value);
   }

   @ExportMessage.Repeat({@ExportMessage, @ExportMessage(name = "isHashEntryRemovable")})
   boolean isHashEntryModifiable(
      Object key,
      @Cached @Cached.Shared("importKeyNode") ImportValueNode importKeyNode,
      @Cached @Cached.Shared("normalizeKeyNode") JSCollectionsNormalizeNode normalizeKeyNode
   ) {
      Object normalizedKey = normalizeKeyNode.execute(importKeyNode.executeWithTarget(key));
      return this.getMap().has(normalizedKey);
   }

   @ExportMessage
   boolean isHashEntryInsertable(Object key, @CachedLibrary("this") InteropLibrary thisLibrary) {
      return !thisLibrary.isHashEntryModifiable(this, key);
   }

   @ExportMessage
   void writeHashEntry(
      Object key,
      Object value,
      @Cached @Cached.Shared("importKeyNode") ImportValueNode importKeyNode,
      @Cached @Cached.Exclusive ImportValueNode importValueNode,
      @Cached @Cached.Shared("normalizeKeyNode") JSCollectionsNormalizeNode normalizeKeyNode
   ) {
      Object normalizedKey = normalizeKeyNode.execute(importKeyNode.executeWithTarget(key));
      this.getMap().put(normalizedKey, importValueNode.executeWithTarget(value));
   }

   @ExportMessage
   void removeHashEntry(
      Object key,
      @Cached @Cached.Shared("importKeyNode") ImportValueNode importKeyNode,
      @Cached @Cached.Shared("normalizeKeyNode") JSCollectionsNormalizeNode normalizeKeyNode
   ) throws UnknownKeyException {
      Object normalizedKey = normalizeKeyNode.execute(importKeyNode.executeWithTarget(key));
      if (!this.getMap().remove(normalizedKey)) {
         throw UnknownKeyException.create(key);
      }
   }

   @ExportLibrary(InteropLibrary.class)
   static final class EntriesIterator implements TruffleObject {
      private JSHashMap.Cursor cursor;
      private Boolean hasNext;

      private EntriesIterator(JSHashMap.Cursor cursor) {
         this.cursor = cursor;
      }

      @ExportMessage
      boolean isIterator() {
         return true;
      }

      @ExportMessage
      boolean hasIteratorNextElement() {
         if (this.hasNext == null) {
            this.hasNext = this.cursor.advance();
         }

         return this.hasNext;
      }

      @ExportMessage
      Object getIteratorNextElement() throws StopIterationException {
         if (this.hasIteratorNextElement()) {
            Object entryTuple = InteropArray.create(new Object[]{this.cursor.getKey(), this.cursor.getValue()});
            this.hasNext = null;
            return entryTuple;
         } else {
            throw StopIterationException.create();
         }
      }
   }
}
