package com.oracle.truffle.api.frame;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;
import org.graalvm.collections.EconomicMap;
import org.graalvm.collections.MapCursor;

public final class FrameDescriptor implements Cloneable {
   static final int NO_STATIC_MODE = 1;
   static final int ALL_STATIC_MODE = 2;
   static final int MIXED_STATIC_MODE = 3;
   private final Object defaultValue;
   @CompilerDirectives.CompilationFinal(dimensions = 1)
   private final byte[] indexedSlotTags;
   @CompilerDirectives.CompilationFinal(dimensions = 1)
   private final Object[] indexedSlotNames;
   @CompilerDirectives.CompilationFinal(dimensions = 1)
   private final Object[] indexedSlotInfos;
   private volatile EconomicMap<Object, Integer> auxiliarySlotMap;
   private volatile BitSet disabledAuxiliarySlots;
   private final Object descriptorInfo;
   @CompilerDirectives.CompilationFinal
   private volatile int activeAuxiliarySlotCount;
   private volatile int auxiliarySlotCount;
   boolean materializeCalled;
   final int staticMode;
   private static final String NEVER_PART_OF_COMPILATION_MESSAGE = "interpreter-only. includes hashmap operations.";
   private static final byte[] EMPTY_BYTE_ARRAY = new byte[0];

   public FrameDescriptor() {
      this(null);
   }

   public FrameDescriptor(Object defaultValue) {
      CompilerAsserts.neverPartOfCompilation("do not create a FrameDescriptor from compiled code");
      this.indexedSlotTags = EMPTY_BYTE_ARRAY;
      this.indexedSlotNames = null;
      this.indexedSlotInfos = null;
      this.descriptorInfo = null;
      this.staticMode = 1;
      this.defaultValue = defaultValue;
   }

   private FrameDescriptor(Object defaultValue, byte[] indexedSlotTags, Object[] indexedSlotNames, Object[] indexedSlotInfos, Object info, int staticMode) {
      CompilerAsserts.neverPartOfCompilation("do not create a FrameDescriptor from compiled code");
      this.indexedSlotTags = indexedSlotTags;
      this.indexedSlotNames = indexedSlotNames;
      this.indexedSlotInfos = indexedSlotInfos;
      this.descriptorInfo = info;
      this.staticMode = staticMode;
      this.defaultValue = defaultValue;
   }

   public FrameDescriptor copy() {
      CompilerAsserts.neverPartOfCompilation("interpreter-only. includes hashmap operations.");
      synchronized (this) {
         FrameDescriptor clonedFrameDescriptor = new FrameDescriptor(
            this.defaultValue,
            this.indexedSlotTags == null ? null : (byte[])this.indexedSlotTags.clone(),
            this.indexedSlotNames == null ? null : (Object[])this.indexedSlotNames.clone(),
            this.indexedSlotInfos == null ? null : (Object[])this.indexedSlotInfos.clone(),
            this.descriptorInfo,
            this.staticMode
         );
         clonedFrameDescriptor.auxiliarySlotCount = this.auxiliarySlotCount;
         clonedFrameDescriptor.activeAuxiliarySlotCount = this.activeAuxiliarySlotCount;
         if (this.auxiliarySlotMap != null) {
            clonedFrameDescriptor.auxiliarySlotMap = EconomicMap.create(this.auxiliarySlotMap);
         }

         if (this.disabledAuxiliarySlots != null) {
            clonedFrameDescriptor.disabledAuxiliarySlots = new BitSet();
            clonedFrameDescriptor.disabledAuxiliarySlots.or(this.disabledAuxiliarySlots);
         }

         return clonedFrameDescriptor;
      }
   }

   public Object getDefaultValue() {
      return this.defaultValue;
   }

   @Override
   public String toString() {
      CompilerAsserts.neverPartOfCompilation("interpreter-only. includes hashmap operations.");
      synchronized (this) {
         StringBuilder sb = new StringBuilder();
         sb.append("FrameDescriptor@").append(Integer.toHexString(this.hashCode()));
         sb.append("{");
         boolean comma = false;

         for (int slot = 0; slot < this.indexedSlotTags.length; slot++) {
            if (comma) {
               sb.append(", ");
            } else {
               comma = true;
            }

            sb.append('#').append(slot);
            if (this.getSlotName(slot) != null) {
               sb.append(":").append(this.getSlotName(slot));
            }
         }

         EconomicMap<Object, Integer> map = this.auxiliarySlotMap;
         if (map != null) {
            for (MapCursor<Object, Integer> entries = map.getEntries();
               entries.advance();
               sb.append('@').append(entries.getKey()).append(":").append(entries.getValue())
            ) {
               if (comma) {
                  sb.append(", ");
               } else {
                  comma = true;
               }
            }
         }

         sb.append("}");
         return sb.toString();
      }
   }

   public int getNumberOfSlots() {
      return this.indexedSlotTags.length;
   }

   public FrameSlotKind getSlotKind(int slot) {
      return FrameSlotKind.fromTag(this.indexedSlotTags[slot]);
   }

   public void setSlotKind(int slot, FrameSlotKind kind) {
      assert this.indexedSlotTags[slot] == FrameSlotKind.Static.tag && kind == FrameSlotKind.Static
         || this.indexedSlotTags[slot] != FrameSlotKind.Static.tag && kind != FrameSlotKind.Static : "Cannot switch between static and non-static slot kind";

      if (this.indexedSlotTags[slot] != kind.tag) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.indexedSlotTags[slot] = kind.tag;
      }
   }

   public Object getSlotName(int slot) {
      return this.indexedSlotNames == null ? null : this.indexedSlotNames[slot];
   }

   public Object getSlotInfo(int slot) {
      return this.indexedSlotInfos == null ? null : this.indexedSlotInfos[slot];
   }

   public int findOrAddAuxiliarySlot(Object key) {
      CompilerAsserts.neverPartOfCompilation("interpreter-only. includes hashmap operations.");
      synchronized (this) {
         EconomicMap<Object, Integer> map = this.auxiliarySlotMap;
         if (map == null) {
            this.auxiliarySlotMap = map = EconomicMap.create();
         }

         Integer index = map.get(key);
         if (index == null) {
            map.put(key, index = this.auxiliarySlotCount++);
            this.activeAuxiliarySlotCount = this.auxiliarySlotCount;
         } else if (this.disabledAuxiliarySlots != null) {
            this.disabledAuxiliarySlots.clear(index);
            this.recalculateAuxiliarySlotSize();
         }

         return index;
      }
   }

   public void disableAuxiliarySlot(Object key) {
      CompilerAsserts.neverPartOfCompilation("interpreter-only. includes hashmap operations.");
      synchronized (this) {
         EconomicMap<Object, Integer> map = this.auxiliarySlotMap;
         if (map != null) {
            Integer index = map.get(key);
            if (index != null) {
               BitSet set = this.disabledAuxiliarySlots;
               if (set == null) {
                  this.disabledAuxiliarySlots = set = new BitSet();
               }

               set.set(index);
               this.recalculateAuxiliarySlotSize();
            }
         }
      }
   }

   public Map<Object, Integer> getAuxiliarySlots() {
      CompilerAsserts.neverPartOfCompilation("interpreter-only. includes hashmap operations.");
      synchronized (this) {
         Map<Object, Integer> result = new HashMap<>();
         EconomicMap<Object, Integer> map = this.auxiliarySlotMap;
         BitSet disabled = this.disabledAuxiliarySlots;
         if (map != null) {
            MapCursor<Object, Integer> cursor = map.getEntries();

            while (cursor.advance()) {
               Object identifier = cursor.getKey();
               int index = cursor.getValue();
               if (disabled == null || !disabled.get(index)) {
                  result.put(identifier, index);
               }
            }
         }

         return result;
      }
   }

   private void recalculateAuxiliarySlotSize() {
      BitSet set = this.disabledAuxiliarySlots;
      int i = this.auxiliarySlotCount;

      while (i > 0 && set.get(i - 1)) {
         i--;
      }

      this.activeAuxiliarySlotCount = i;
   }

   public int getNumberOfAuxiliarySlots() {
      return this.activeAuxiliarySlotCount;
   }

   public Object getInfo() {
      return this.descriptorInfo;
   }

   public static FrameDescriptor.Builder newBuilder() {
      return new FrameDescriptor.Builder(8);
   }

   public static FrameDescriptor.Builder newBuilder(int capacity) {
      return new FrameDescriptor.Builder(capacity);
   }

   public static final class Builder {
      private static final int DEFAULT_CAPACITY = 8;
      private Object defaultValue;
      private byte[] tags;
      private Object[] names;
      private Object[] infos;
      private int size;
      private Object descriptorInfo;
      private int staticMode;

      private Builder(int capacity) {
         this.tags = new byte[capacity];
      }

      private void ensureCapacity(int count) {
         if (this.tags.length < this.size + count) {
            int newLength = Math.max(this.size + count, this.size * 2);
            this.tags = Arrays.copyOf(this.tags, newLength);
            if (this.names != null) {
               this.names = Arrays.copyOf(this.names, newLength);
            }

            if (this.infos != null) {
               this.infos = Arrays.copyOf(this.infos, newLength);
            }
         }
      }

      public FrameDescriptor.Builder defaultValue(Object newDefaultValue) {
         this.defaultValue = newDefaultValue;
         return this;
      }

      public int addSlots(int count, FrameSlotKind kind) {
         if (count >= 0 && this.size + count >= 0) {
            this.ensureCapacity(count);
            Arrays.fill(this.tags, this.size, this.size + count, kind.tag);
            int newIndex = this.size;
            this.size += count;
            this.staticMode = this.staticMode | (kind == FrameSlotKind.Static ? 2 : 1);
            return newIndex;
         } else {
            throw new IllegalArgumentException("invalid slot count: " + count);
         }
      }

      public int addSlot(FrameSlotKind kind, Object name, Object info) {
         this.ensureCapacity(1);
         this.tags[this.size] = kind.tag;
         if (name != null) {
            if (this.names == null) {
               this.names = new Object[this.tags.length];
            }

            this.names[this.size] = name;
         }

         if (info != null) {
            if (this.infos == null) {
               this.infos = new Object[this.tags.length];
            }

            this.infos[this.size] = info;
         }

         this.tags[this.size] = kind.tag;
         int newIndex = this.size++;
         this.staticMode = this.staticMode | (kind == FrameSlotKind.Static ? 2 : 1);
         return newIndex;
      }

      public FrameDescriptor.Builder info(Object info) {
         this.descriptorInfo = info;
         return this;
      }

      public FrameDescriptor build() {
         return new FrameDescriptor(
            this.defaultValue,
            Arrays.copyOf(this.tags, this.size),
            this.names == null ? null : Arrays.copyOf(this.names, this.size),
            this.infos == null ? null : Arrays.copyOf(this.infos, this.size),
            this.descriptorInfo,
            this.staticMode != 0 ? this.staticMode : 1
         );
      }
   }
}
