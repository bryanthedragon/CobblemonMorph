package com.cobblemon.mod.relocations.ibm.icu.impl.locale;

import com.cobblemon.mod.relocations.ibm.icu.util.ICUException;
import com.cobblemon.mod.relocations.ibm.icu.util.ICUUncheckedIOException;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XCldrStub {
   public static <T> String join(T[] source, String separator) {
      StringBuilder result = new StringBuilder();

      for (int i = 0; i < source.length; i++) {
         if (i != 0) {
            result.append(separator);
         }

         result.append(source[i]);
      }

      return result.toString();
   }

   public static <T> String join(Iterable<T> source, String separator) {
      StringBuilder result = new StringBuilder();
      boolean first = true;

      for (T item : source) {
         if (!first) {
            result.append(separator);
         } else {
            first = false;
         }

         result.append(item.toString());
      }

      return result.toString();
   }

   public static class CollectionUtilities {
      public static <T, U extends Iterable<T>> String join(U source, String separator) {
         return XCldrStub.join(source, separator);
      }
   }

   public static class FileUtilities {
      public static final Charset UTF8 = Charset.forName("utf-8");

      public static BufferedReader openFile(Class<?> class1, String file) {
         return openFile(class1, file, UTF8);
      }

      public static BufferedReader openFile(Class<?> class1, String file, Charset charset) {
         try {
            InputStream resourceAsStream = class1.getResourceAsStream(file);
            if (charset == null) {
               charset = UTF8;
            }

            InputStreamReader reader = new InputStreamReader(resourceAsStream, charset);
            return new BufferedReader(reader, 65536);
         } catch (Exception var8) {
            String className = class1 == null ? null : class1.getCanonicalName();
            String canonicalName = null;

            try {
               String relativeFileName = getRelativeFileName(class1, "../util/");
               canonicalName = new File(relativeFileName).getCanonicalPath();
            } catch (Exception var7) {
               throw new ICUUncheckedIOException("Couldn't open file: " + file + "; relative to class: " + className, var8);
            }

            throw new ICUUncheckedIOException("Couldn't open file " + file + "; in path " + canonicalName + "; relative to class: " + className, var8);
         }
      }

      public static String getRelativeFileName(Class<?> class1, String filename) {
         URL resource = class1 == null ? XCldrStub.FileUtilities.class.getResource(filename) : class1.getResource(filename);
         String resourceString = resource.toString();
         if (resourceString.startsWith("file:")) {
            return resourceString.substring(5);
         } else if (resourceString.startsWith("jar:file:")) {
            return resourceString.substring(9);
         } else {
            throw new ICUUncheckedIOException("File not found: " + resourceString);
         }
      }
   }

   public static class HashMultimap<K, V> extends XCldrStub.Multimap<K, V> {
      private HashMultimap() {
         super(new HashMap(), HashSet.class);
      }

      public static <K, V> XCldrStub.HashMultimap<K, V> create() {
         return new XCldrStub.HashMultimap<>();
      }
   }

   public static class ImmutableMap {
      public static <K, V> Map<K, V> copyOf(Map<K, V> values) {
         return Collections.unmodifiableMap(new LinkedHashMap<>(values));
      }
   }

   public static class ImmutableMultimap {
      public static <K, V> XCldrStub.Multimap<K, V> copyOf(XCldrStub.Multimap<K, V> values) {
         LinkedHashMap<K, Set<V>> temp = new LinkedHashMap<>();

         for (Entry<K, Set<V>> entry : values.asMap().entrySet()) {
            Set<V> value = entry.getValue();
            temp.put(
               entry.getKey(), value.size() == 1 ? Collections.singleton(value.iterator().next()) : Collections.unmodifiableSet(new LinkedHashSet<>(value))
            );
         }

         return new XCldrStub.Multimap<>(Collections.unmodifiableMap(temp), null);
      }
   }

   public static class ImmutableSet {
      public static <T> Set<T> copyOf(Set<T> values) {
         return Collections.unmodifiableSet(new LinkedHashSet<>(values));
      }
   }

   public static class Joiner {
      private final String separator;

      private Joiner(String separator) {
         this.separator = separator;
      }

      public static final XCldrStub.Joiner on(String separator) {
         return new XCldrStub.Joiner(separator);
      }

      public <T> String join(T[] source) {
         return XCldrStub.join(source, this.separator);
      }

      public <T> String join(Iterable<T> source) {
         return XCldrStub.join(source, this.separator);
      }
   }

   public static class LinkedHashMultimap<K, V> extends XCldrStub.Multimap<K, V> {
      private LinkedHashMultimap() {
         super(new LinkedHashMap(), LinkedHashSet.class);
      }

      public static <K, V> XCldrStub.LinkedHashMultimap<K, V> create() {
         return new XCldrStub.LinkedHashMultimap<>();
      }
   }

   public static class Multimap<K, V> {
      private final Map<K, Set<V>> map;
      private final Class<Set<V>> setClass;

      private Multimap(Map<K, Set<V>> map, Class<?> setClass) {
         this.map = map;
         this.setClass = (Class<Set<V>>)(setClass != null ? setClass : HashSet.class);
      }

      @SafeVarargs
      public final XCldrStub.Multimap<K, V> putAll(K key, V... values) {
         if (values.length != 0) {
            this.createSetIfMissing(key).addAll(Arrays.asList(values));
         }

         return this;
      }

      public void putAll(K key, Collection<V> values) {
         if (!values.isEmpty()) {
            this.createSetIfMissing(key).addAll(values);
         }
      }

      public void putAll(Collection<K> keys, V value) {
         for (K key : keys) {
            this.put(key, value);
         }
      }

      public void putAll(XCldrStub.Multimap<K, V> source) {
         for (Entry<K, Set<V>> entry : source.map.entrySet()) {
            this.putAll(entry.getKey(), entry.getValue());
         }
      }

      public void put(K key, V value) {
         this.createSetIfMissing(key).add(value);
      }

      private Set<V> createSetIfMissing(K key) {
         Set<V> old = this.map.get(key);
         if (old == null) {
            this.map.put(key, old = this.getInstance());
         }

         return old;
      }

      private Set<V> getInstance() {
         try {
            return this.setClass.newInstance();
         } catch (Exception var2) {
            throw new ICUException(var2);
         }
      }

      public Set<V> get(K key) {
         return this.map.get(key);
      }

      public Set<K> keySet() {
         return this.map.keySet();
      }

      public Map<K, Set<V>> asMap() {
         return this.map;
      }

      public Set<V> values() {
         Collection<Set<V>> values = this.map.values();
         if (values.size() == 0) {
            return Collections.emptySet();
         } else {
            Set<V> result = this.getInstance();

            for (Set<V> valueSet : values) {
               result.addAll(valueSet);
            }

            return result;
         }
      }

      public int size() {
         return this.map.size();
      }

      public Iterable<Entry<K, V>> entries() {
         return new XCldrStub.MultimapIterator<>(this.map);
      }

      @Override
      public boolean equals(Object obj) {
         return this == obj || obj != null && obj.getClass() == this.getClass() && this.map.equals(((XCldrStub.Multimap)obj).map);
      }

      @Override
      public int hashCode() {
         return this.map.hashCode();
      }
   }

   private static class MultimapIterator<K, V> implements Iterator<Entry<K, V>>, Iterable<Entry<K, V>> {
      private final Iterator<Entry<K, Set<V>>> it1;
      private Iterator<V> it2 = null;
      private final XCldrStub.ReusableEntry<K, V> entry = new XCldrStub.ReusableEntry<>();

      private MultimapIterator(Map<K, Set<V>> map) {
         this.it1 = map.entrySet().iterator();
      }

      @Override
      public boolean hasNext() {
         return this.it1.hasNext() || this.it2 != null && this.it2.hasNext();
      }

      public Entry<K, V> next() {
         if (this.it2 != null && this.it2.hasNext()) {
            this.entry.value = this.it2.next();
         } else {
            Entry<K, Set<V>> e = this.it1.next();
            this.entry.key = e.getKey();
            this.it2 = e.getValue().iterator();
         }

         return this.entry;
      }

      @Override
      public Iterator<Entry<K, V>> iterator() {
         return this;
      }

      @Override
      public void remove() {
         throw new UnsupportedOperationException();
      }
   }

   public static class Multimaps {
      public static <K, V, R extends XCldrStub.Multimap<K, V>> R invertFrom(XCldrStub.Multimap<V, K> source, R target) {
         for (Entry<V, Set<K>> entry : source.asMap().entrySet()) {
            target.putAll(entry.getValue(), entry.getKey());
         }

         return target;
      }

      public static <K, V, R extends XCldrStub.Multimap<K, V>> R invertFrom(Map<V, K> source, R target) {
         for (Entry<V, K> entry : source.entrySet()) {
            target.put(entry.getValue(), entry.getKey());
         }

         return target;
      }

      public static <K, V> Map<K, V> forMap(Map<K, V> map) {
         return map;
      }
   }

   public interface Predicate<T> {
      boolean test(T var1);
   }

   public static class RegexUtilities {
      public static int findMismatch(Matcher m, CharSequence s) {
         int i;
         for (i = 1; i < s.length(); i++) {
            boolean matches = m.reset(s.subSequence(0, i)).matches();
            if (!matches && !m.hitEnd()) {
               break;
            }
         }

         return i - 1;
      }

      public static String showMismatch(Matcher m, CharSequence s) {
         int failPoint = findMismatch(m, s);
         return s.subSequence(0, failPoint) + "☹" + s.subSequence(failPoint, s.length());
      }
   }

   private static class ReusableEntry<K, V> implements Entry<K, V> {
      K key;
      V value;

      private ReusableEntry() {
      }

      @Override
      public K getKey() {
         return this.key;
      }

      @Override
      public V getValue() {
         return this.value;
      }

      @Override
      public V setValue(V value) {
         throw new UnsupportedOperationException();
      }
   }

   public static class Splitter {
      Pattern pattern;
      boolean trimResults = false;

      public Splitter(char c) {
         this(Pattern.compile("\\Q" + c + "\\E"));
      }

      public Splitter(Pattern p) {
         this.pattern = p;
      }

      public static XCldrStub.Splitter on(char c) {
         return new XCldrStub.Splitter(c);
      }

      public static XCldrStub.Splitter on(Pattern p) {
         return new XCldrStub.Splitter(p);
      }

      public List<String> splitToList(String input) {
         String[] items = this.pattern.split(input);
         if (this.trimResults) {
            for (int i = 0; i < items.length; i++) {
               items[i] = items[i].trim();
            }
         }

         return Arrays.asList(items);
      }

      public XCldrStub.Splitter trimResults() {
         this.trimResults = true;
         return this;
      }

      public Iterable<String> split(String input) {
         return this.splitToList(input);
      }
   }

   public static class TreeMultimap<K, V> extends XCldrStub.Multimap<K, V> {
      private TreeMultimap() {
         super(new TreeMap(), TreeSet.class);
      }

      public static <K, V> XCldrStub.TreeMultimap<K, V> create() {
         return new XCldrStub.TreeMultimap<>();
      }
   }
}
