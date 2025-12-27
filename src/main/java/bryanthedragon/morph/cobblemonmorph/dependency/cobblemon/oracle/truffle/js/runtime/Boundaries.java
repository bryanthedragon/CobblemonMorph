package com.oracle.truffle.js.runtime;

import com.oracle.truffle.api.CompilerDirectives;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import org.graalvm.collections.EconomicMap;
import org.graalvm.collections.EconomicSet;

public final class Boundaries {
   private Boundaries() {
   }

   @CompilerDirectives.TruffleBoundary
   public static String javaToString(Object value) {
      return value.toString();
   }

   @CompilerDirectives.TruffleBoundary
   public static String stringValueOf(Object o) {
      return String.valueOf(o);
   }

   @CompilerDirectives.TruffleBoundary
   public static String stringFormat(String format, Object... params) {
      return String.format(format, params);
   }

   @CompilerDirectives.TruffleBoundary
   public static boolean characterIsDigit(char ch) {
      return Character.isDigit(ch);
   }

   @CompilerDirectives.TruffleBoundary
   public static boolean characterIsUpperCase(char ch) {
      return Character.isUpperCase(ch);
   }

   @CompilerDirectives.TruffleBoundary
   public static boolean equals(Object a, Object b) {
      return a.equals(b);
   }

   @CompilerDirectives.TruffleBoundary
   public static <K, V> Set<Entry<K, V>> mapEntrySet(Map<K, V> map) {
      return map.entrySet();
   }

   @CompilerDirectives.TruffleBoundary(allowInlining = true)
   public static <K, V> Entry<K, V> mapEntry(K key, V value) {
      return Map.entry(key, value);
   }

   @CompilerDirectives.TruffleBoundary
   public static <K, V> V mapPut(Map<K, V> map, K key, V value) {
      return map.put(key, value);
   }

   @CompilerDirectives.TruffleBoundary
   public static <K, V> V mapPutIfAbsent(Map<K, V> map, K key, V value) {
      return map.putIfAbsent(key, value);
   }

   @CompilerDirectives.TruffleBoundary
   public static <K, V> boolean mapContainsKey(Map<K, V> map, K key) {
      return map.containsKey(key);
   }

   @CompilerDirectives.TruffleBoundary
   public static <K, V> V mapGet(Map<K, V> map, K key) {
      return map.get(key);
   }

   @CompilerDirectives.TruffleBoundary
   public static <K, V> V mapRemove(Map<K, V> map, K key) {
      return map.remove(key);
   }

   @CompilerDirectives.TruffleBoundary
   public static <T> T listGet(List<T> list, int index) {
      return list.get(index);
   }

   @CompilerDirectives.TruffleBoundary
   public static <T> void listSet(List<T> list, int index, T value) {
      list.set(index, value);
   }

   @CompilerDirectives.TruffleBoundary
   public static <T> int listSize(List<T> list) {
      return list.size();
   }

   @CompilerDirectives.TruffleBoundary
   public static <T> int listIndexOf(List<T> list, T element) {
      return list.indexOf(element);
   }

   @CompilerDirectives.TruffleBoundary
   public static <T> void listAdd(List<T> list, T element) {
      list.add(element);
   }

   @CompilerDirectives.TruffleBoundary
   public static <T> void listAddAll(List<T> list, List<T> addList) {
      list.addAll(addList);
   }

   @CompilerDirectives.TruffleBoundary
   public static <T> boolean listContains(List<T> list, T element) {
      return list.contains(element);
   }

   @CompilerDirectives.TruffleBoundary
   public static boolean listContainsUnchecked(List<?> list, Object element) {
      return list.contains(element);
   }

   @CompilerDirectives.TruffleBoundary
   public static <T> Object[] listToArray(List<T> list) {
      return list.toArray();
   }

   @CompilerDirectives.TruffleBoundary
   public static <T> boolean iteratorHasNext(Iterator<T> it) {
      return it.hasNext();
   }

   @CompilerDirectives.TruffleBoundary
   public static <T> T iteratorNext(Iterator<T> it) {
      return it.next();
   }

   @CompilerDirectives.TruffleBoundary
   public static <T> Iterator<T> iterator(Iterable<T> iterable) {
      return iterable.iterator();
   }

   @CompilerDirectives.TruffleBoundary
   public static <T> EconomicSet<T> economicSetCreate() {
      return EconomicSet.create();
   }

   @CompilerDirectives.TruffleBoundary
   public static <T> boolean economicSetAdd(EconomicSet<T> economicSet, T element) {
      return economicSet.add(element);
   }

   @CompilerDirectives.TruffleBoundary
   public static <T> boolean economicSetContains(EconomicSet<T> economicSet, T element) {
      return economicSet.contains(element);
   }

   @CompilerDirectives.TruffleBoundary
   public static <K, V> EconomicMap<K, V> economicMapCreate() {
      return EconomicMap.create();
   }

   @CompilerDirectives.TruffleBoundary
   public static <K, V> V economicMapPut(EconomicMap<K, V> map, K key, V value) {
      return map.put(key, value);
   }

   @CompilerDirectives.TruffleBoundary
   public static <K, V> boolean economicMapContainsKey(EconomicMap<K, V> map, K key) {
      return map.containsKey(key);
   }

   @CompilerDirectives.TruffleBoundary
   public static <K, V> V economicMapGet(EconomicMap<K, V> map, K key) {
      return map.get(key);
   }

   @CompilerDirectives.TruffleBoundary(allowInlining = true)
   public static byte[] byteBufferArray(ByteBuffer buffer) {
      return buffer.array();
   }

   @CompilerDirectives.TruffleBoundary(allowInlining = true)
   public static void byteBufferPutSlice(ByteBuffer dst, int dstPos, ByteBuffer src, int srcPos, int srcLimit) {
      ByteBuffer slice = byteBufferSlice(src, srcPos, srcLimit);
      ByteBuffer dstDup = dst.duplicate();
      dstDup.position(dstPos);
      dstDup.put(slice);
   }

   @CompilerDirectives.TruffleBoundary(allowInlining = true)
   public static ByteBuffer byteBufferSlice(ByteBuffer buf, int pos, int limit) {
      ByteBuffer dup = buf.duplicate();
      dup.position(pos).limit(limit);
      return dup.slice();
   }

   @CompilerDirectives.TruffleBoundary(allowInlining = true)
   public static ByteBuffer byteBufferDuplicate(ByteBuffer buffer) {
      return buffer.duplicate();
   }

   @CompilerDirectives.TruffleBoundary
   public static boolean setContains(Set<?> set, Object element) {
      return set.contains(element);
   }

   @CompilerDirectives.TruffleBoundary
   public static BigInteger bigIntegerValueOf(long l) {
      return BigInteger.valueOf(l);
   }

   @CompilerDirectives.TruffleBoundary
   public static BigDecimal bigDecimalValueOf(long l) {
      return BigDecimal.valueOf(l);
   }

   @CompilerDirectives.TruffleBoundary
   public static BigInteger bigIntegerMultiply(BigInteger a, BigInteger b) {
      return a.multiply(b);
   }
}
