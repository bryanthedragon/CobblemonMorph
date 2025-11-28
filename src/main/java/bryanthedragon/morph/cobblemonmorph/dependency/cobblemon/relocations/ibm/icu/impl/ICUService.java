package com.cobblemon.mod.relocations.ibm.icu.impl;

import com.cobblemon.mod.relocations.ibm.icu.util.ULocale;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.EventListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class ICUService extends ICUNotifier {
   protected final String name;
   private static final boolean DEBUG = ICUDebug.enabled("service");
   private final ICURWLock factoryLock = new ICURWLock();
   private final List<ICUService.Factory> factories = new ArrayList<>();
   private int defaultSize = 0;
   private Map<String, ICUService.CacheEntry> cache;
   private Map<String, ICUService.Factory> idcache;
   private ICUService.LocaleRef dnref;

   public ICUService() {
      this.name = "";
   }

   public ICUService(String name) {
      this.name = name;
   }

   public Object get(String descriptor) {
      return this.getKey(this.createKey(descriptor), null);
   }

   public Object get(String descriptor, String[] actualReturn) {
      if (descriptor == null) {
         throw new NullPointerException("descriptor must not be null");
      } else {
         return this.getKey(this.createKey(descriptor), actualReturn);
      }
   }

   public Object getKey(ICUService.Key key) {
      return this.getKey(key, null);
   }

   public Object getKey(ICUService.Key key, String[] actualReturn) {
      return this.getKey(key, actualReturn, null);
   }

   public Object getKey(ICUService.Key key, String[] actualReturn, ICUService.Factory factory) {
      if (this.factories.size() == 0) {
         return this.handleDefault(key, actualReturn);
      } else {
         if (DEBUG) {
            System.out.println("Service: " + this.name + " key: " + key.canonicalID());
         }

         ICUService.CacheEntry result = null;
         if (key != null) {
            try {
               this.factoryLock.acquireRead();
               Map<String, ICUService.CacheEntry> cache = this.cache;
               if (cache == null) {
                  if (DEBUG) {
                     System.out.println("Service " + this.name + " cache was empty");
                  }

                  cache = new ConcurrentHashMap<>();
               }

               String currentDescriptor = null;
               ArrayList<String> cacheDescriptorList = null;
               boolean putInCache = false;
               int NDebug = 0;
               int startIndex = 0;
               int limit = this.factories.size();
               boolean cacheResult = true;
               if (factory != null) {
                  for (int i = 0; i < limit; i++) {
                     if (factory == this.factories.get(i)) {
                        startIndex = i + 1;
                        break;
                     }
                  }

                  if (startIndex == 0) {
                     throw new IllegalStateException("Factory " + factory + "not registered with service: " + this);
                  }

                  cacheResult = false;
               }

               label222:
               do {
                  currentDescriptor = key.currentDescriptor();
                  if (DEBUG) {
                     System.out.println(this.name + "[" + NDebug++ + "] looking for: " + currentDescriptor);
                  }

                  result = cache.get(currentDescriptor);
                  if (result != null) {
                     if (DEBUG) {
                        System.out.println(this.name + " found with descriptor: " + currentDescriptor);
                     }
                     break;
                  }

                  if (DEBUG) {
                     System.out.println("did not find: " + currentDescriptor + " in cache");
                  }

                  putInCache = cacheResult;
                  int index = startIndex;

                  while (index < limit) {
                     ICUService.Factory f = this.factories.get(index++);
                     if (DEBUG) {
                        System.out.println("trying factory[" + (index - 1) + "] " + f.toString());
                     }

                     Object service = f.create(key, this);
                     if (service != null) {
                        result = new ICUService.CacheEntry(currentDescriptor, service);
                        if (DEBUG) {
                           System.out.println(this.name + " factory supported: " + currentDescriptor + ", caching");
                        }
                        break label222;
                     }

                     if (DEBUG) {
                        System.out.println("factory did not support: " + currentDescriptor);
                     }
                  }

                  if (cacheDescriptorList == null) {
                     cacheDescriptorList = new ArrayList<>(5);
                  }

                  cacheDescriptorList.add(currentDescriptor);
               } while (key.fallback());

               if (result != null) {
                  if (putInCache) {
                     if (DEBUG) {
                        System.out.println("caching '" + result.actualDescriptor + "'");
                     }

                     cache.put(result.actualDescriptor, result);
                     if (cacheDescriptorList != null) {
                        for (String desc : cacheDescriptorList) {
                           if (DEBUG) {
                              System.out.println(this.name + " adding descriptor: '" + desc + "' for actual: '" + result.actualDescriptor + "'");
                           }

                           cache.put(desc, result);
                        }
                     }

                     this.cache = cache;
                  }

                  if (actualReturn != null) {
                     if (result.actualDescriptor.indexOf("/") == 0) {
                        actualReturn[0] = result.actualDescriptor.substring(1);
                     } else {
                        actualReturn[0] = result.actualDescriptor;
                     }
                  }

                  if (DEBUG) {
                     System.out.println("found in service: " + this.name);
                  }

                  return result.service;
               }
            } finally {
               this.factoryLock.releaseRead();
            }
         }

         if (DEBUG) {
            System.out.println("not found in service: " + this.name);
         }

         return this.handleDefault(key, actualReturn);
      }
   }

   protected Object handleDefault(ICUService.Key key, String[] actualIDReturn) {
      return null;
   }

   public Set<String> getVisibleIDs() {
      return this.getVisibleIDs(null);
   }

   public Set<String> getVisibleIDs(String matchID) {
      Set<String> result = this.getVisibleIDMap().keySet();
      ICUService.Key fallbackKey = this.createKey(matchID);
      if (fallbackKey != null) {
         Set<String> temp = new HashSet<>(result.size());

         for (String id : result) {
            if (fallbackKey.isFallbackOf(id)) {
               temp.add(id);
            }
         }

         result = temp;
      }

      return result;
   }

   private Map<String, ICUService.Factory> getVisibleIDMap() {
      synchronized (this) {
         if (this.idcache == null) {
            try {
               this.factoryLock.acquireRead();
               Map<String, ICUService.Factory> mutableMap = new HashMap<>();
               ListIterator<ICUService.Factory> lIter = this.factories.listIterator(this.factories.size());

               while (lIter.hasPrevious()) {
                  ICUService.Factory f = lIter.previous();
                  f.updateVisibleIDs(mutableMap);
               }

               this.idcache = Collections.unmodifiableMap(mutableMap);
            } finally {
               this.factoryLock.releaseRead();
            }
         }
      }

      return this.idcache;
   }

   public String getDisplayName(String id) {
      return this.getDisplayName(id, ULocale.getDefault(ULocale.Category.DISPLAY));
   }

   public String getDisplayName(String id, ULocale locale) {
      Map<String, ICUService.Factory> m = this.getVisibleIDMap();
      ICUService.Factory f = m.get(id);
      if (f != null) {
         return f.getDisplayName(id, locale);
      } else {
         ICUService.Key key = this.createKey(id);

         while (key.fallback()) {
            f = m.get(key.currentID());
            if (f != null) {
               return f.getDisplayName(id, locale);
            }
         }

         return null;
      }
   }

   public SortedMap<String, String> getDisplayNames() {
      ULocale locale = ULocale.getDefault(ULocale.Category.DISPLAY);
      return this.getDisplayNames(locale, null, null);
   }

   public SortedMap<String, String> getDisplayNames(ULocale locale) {
      return this.getDisplayNames(locale, null, null);
   }

   public SortedMap<String, String> getDisplayNames(ULocale locale, Comparator<Object> com) {
      return this.getDisplayNames(locale, com, null);
   }

   public SortedMap<String, String> getDisplayNames(ULocale locale, String matchID) {
      return this.getDisplayNames(locale, null, matchID);
   }

   public SortedMap<String, String> getDisplayNames(ULocale locale, Comparator<Object> com, String matchID) {
      SortedMap<String, String> dncache = null;
      ICUService.LocaleRef ref = this.dnref;
      if (ref != null) {
         dncache = ref.get(locale, com);
      }

      while (dncache == null) {
         synchronized (this) {
            if (ref != this.dnref && this.dnref != null) {
               ref = this.dnref;
               dncache = ref.get(locale, com);
            } else {
               SortedMap<String, String> var14 = new TreeMap<>(com);
               Map<String, ICUService.Factory> m = this.getVisibleIDMap();

               for (Entry<String, ICUService.Factory> e : m.entrySet()) {
                  String id = e.getKey();
                  ICUService.Factory f = e.getValue();
                  var14.put(f.getDisplayName(id, locale), id);
               }

               dncache = Collections.unmodifiableSortedMap(var14);
               this.dnref = new ICUService.LocaleRef(dncache, locale, com);
            }
         }
      }

      ICUService.Key matchKey = this.createKey(matchID);
      if (matchKey == null) {
         return dncache;
      } else {
         SortedMap<String, String> result = new TreeMap<>(dncache);
         Iterator<Entry<String, String>> iter = result.entrySet().iterator();

         while (iter.hasNext()) {
            Entry<String, String> e = iter.next();
            if (!matchKey.isFallbackOf(e.getValue())) {
               iter.remove();
            }
         }

         return result;
      }
   }

   public final List<ICUService.Factory> factories() {
      ArrayList var1;
      try {
         this.factoryLock.acquireRead();
         var1 = new ArrayList<>(this.factories);
      } finally {
         this.factoryLock.releaseRead();
      }

      return var1;
   }

   public ICUService.Factory registerObject(Object obj, String id) {
      return this.registerObject(obj, id, true);
   }

   public ICUService.Factory registerObject(Object obj, String id, boolean visible) {
      String canonicalID = this.createKey(id).canonicalID();
      return this.registerFactory(new ICUService.SimpleFactory(obj, canonicalID, visible));
   }

   public final ICUService.Factory registerFactory(ICUService.Factory factory) {
      if (factory == null) {
         throw new NullPointerException();
      } else {
         try {
            this.factoryLock.acquireWrite();
            this.factories.add(0, factory);
            this.clearCaches();
         } finally {
            this.factoryLock.releaseWrite();
         }

         this.notifyChanged();
         return factory;
      }
   }

   public final boolean unregisterFactory(ICUService.Factory factory) {
      if (factory == null) {
         throw new NullPointerException();
      } else {
         boolean result = false;

         try {
            this.factoryLock.acquireWrite();
            if (this.factories.remove(factory)) {
               result = true;
               this.clearCaches();
            }
         } finally {
            this.factoryLock.releaseWrite();
         }

         if (result) {
            this.notifyChanged();
         }

         return result;
      }
   }

   public final void reset() {
      try {
         this.factoryLock.acquireWrite();
         this.reInitializeFactories();
         this.clearCaches();
      } finally {
         this.factoryLock.releaseWrite();
      }

      this.notifyChanged();
   }

   protected void reInitializeFactories() {
      this.factories.clear();
   }

   public boolean isDefault() {
      return this.factories.size() == this.defaultSize;
   }

   protected void markDefault() {
      this.defaultSize = this.factories.size();
   }

   public ICUService.Key createKey(String id) {
      return id == null ? null : new ICUService.Key(id);
   }

   protected void clearCaches() {
      this.cache = null;
      this.idcache = null;
      this.dnref = null;
   }

   protected void clearServiceCache() {
      this.cache = null;
   }

   @Override
   protected boolean acceptsListener(EventListener l) {
      return l instanceof ICUService.ServiceListener;
   }

   @Override
   protected void notifyListener(EventListener l) {
      ((ICUService.ServiceListener)l).serviceChanged(this);
   }

   public String stats() {
      ICURWLock.Stats stats = this.factoryLock.resetStats();
      return stats != null ? stats.toString() : "no stats";
   }

   public String getName() {
      return this.name;
   }

   @Override
   public String toString() {
      return super.toString() + "{" + this.name + "}";
   }

   private static final class CacheEntry {
      final String actualDescriptor;
      final Object service;

      CacheEntry(String actualDescriptor, Object service) {
         this.actualDescriptor = actualDescriptor;
         this.service = service;
      }
   }

   public interface Factory {
      Object create(ICUService.Key var1, ICUService var2);

      void updateVisibleIDs(Map<String, ICUService.Factory> var1);

      String getDisplayName(String var1, ULocale var2);
   }

   public static class Key {
      private final String id;

      public Key(String id) {
         this.id = id;
      }

      public final String id() {
         return this.id;
      }

      public String canonicalID() {
         return this.id;
      }

      public String currentID() {
         return this.canonicalID();
      }

      public String currentDescriptor() {
         return "/" + this.currentID();
      }

      public boolean fallback() {
         return false;
      }

      public boolean isFallbackOf(String idToCheck) {
         return this.canonicalID().equals(idToCheck);
      }
   }

   private static class LocaleRef {
      private final ULocale locale;
      private SortedMap<String, String> dnCache;
      private Comparator<Object> com;

      LocaleRef(SortedMap<String, String> dnCache, ULocale locale, Comparator<Object> com) {
         this.locale = locale;
         this.com = com;
         this.dnCache = dnCache;
      }

      SortedMap<String, String> get(ULocale loc, Comparator<Object> comp) {
         SortedMap<String, String> m = this.dnCache;
         return m == null || !this.locale.equals(loc) || this.com != comp && (this.com == null || !this.com.equals(comp)) ? null : m;
      }
   }

   public interface ServiceListener extends EventListener {
      void serviceChanged(ICUService var1);
   }

   public static class SimpleFactory implements ICUService.Factory {
      protected Object instance;
      protected String id;
      protected boolean visible;

      public SimpleFactory(Object instance, String id) {
         this(instance, id, true);
      }

      public SimpleFactory(Object instance, String id, boolean visible) {
         if (instance != null && id != null) {
            this.instance = instance;
            this.id = id;
            this.visible = visible;
         } else {
            throw new IllegalArgumentException("Instance or id is null");
         }
      }

      @Override
      public Object create(ICUService.Key key, ICUService service) {
         return this.id.equals(key.currentID()) ? this.instance : null;
      }

      @Override
      public void updateVisibleIDs(Map<String, ICUService.Factory> result) {
         if (this.visible) {
            result.put(this.id, this);
         } else {
            result.remove(this.id);
         }
      }

      @Override
      public String getDisplayName(String identifier, ULocale locale) {
         return this.visible && this.id.equals(identifier) ? identifier : null;
      }

      @Override
      public String toString() {
         StringBuilder buf = new StringBuilder(super.toString());
         buf.append(", id: ");
         buf.append(this.id);
         buf.append(", visible: ");
         buf.append(this.visible);
         return buf.toString();
      }
   }
}
