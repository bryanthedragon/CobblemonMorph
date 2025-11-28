package com.cobblemon.mod.relocations.ibm.icu.impl;

import com.cobblemon.mod.relocations.ibm.icu.util.ULocale;
import com.cobblemon.mod.relocations.ibm.icu.util.UResourceBundle;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public final class ResourceBundleWrapper extends UResourceBundle {
   private ResourceBundle bundle = null;
   private String localeID = null;
   private String baseName = null;
   private List<String> keys = null;
   private static CacheBase<String, ResourceBundleWrapper, ResourceBundleWrapper.Loader> BUNDLE_CACHE = new SoftCache<String, ResourceBundleWrapper, ResourceBundleWrapper.Loader>() {
      protected ResourceBundleWrapper createInstance(String unusedKey, ResourceBundleWrapper.Loader loader) {
         return loader.load();
      }
   };
   private static final boolean DEBUG = ICUDebug.enabled("resourceBundleWrapper");

   private ResourceBundleWrapper(ResourceBundle bundle) {
      this.bundle = bundle;
   }

   @Override
   protected Object handleGetObject(String aKey) {
      ResourceBundleWrapper current = this;

      Object obj;
      for (obj = null; current != null; current = (ResourceBundleWrapper)current.getParent()) {
         try {
            obj = current.bundle.getObject(aKey);
            break;
         } catch (MissingResourceException var5) {
         }
      }

      if (obj == null) {
         throw new MissingResourceException("Can't find resource for bundle " + this.baseName + ", key " + aKey, this.getClass().getName(), aKey);
      } else {
         return obj;
      }
   }

   @Override
   public Enumeration<String> getKeys() {
      return Collections.enumeration(this.keys);
   }

   private void initKeysVector() {
      ResourceBundleWrapper current = this;

      for (this.keys = new ArrayList<>(); current != null; current = (ResourceBundleWrapper)current.getParent()) {
         Enumeration<String> e = current.bundle.getKeys();

         while (e.hasMoreElements()) {
            String elem = e.nextElement();
            if (!this.keys.contains(elem)) {
               this.keys.add(elem);
            }
         }
      }
   }

   @Override
   protected String getLocaleID() {
      return this.localeID;
   }

   @Override
   protected String getBaseName() {
      return this.bundle.getClass().getName().replace('.', '/');
   }

   @Override
   public ULocale getULocale() {
      return new ULocale(this.localeID);
   }

   @Override
   public UResourceBundle getParent() {
      return (UResourceBundle)this.parent;
   }

   public static ResourceBundleWrapper getBundleInstance(String baseName, String localeID, ClassLoader root, boolean disableFallback) {
      if (root == null) {
         root = ClassLoaderUtil.getClassLoader();
      }

      ResourceBundleWrapper b;
      if (disableFallback) {
         b = instantiateBundle(baseName, localeID, null, root, disableFallback);
      } else {
         b = instantiateBundle(baseName, localeID, ULocale.getDefault().getBaseName(), root, disableFallback);
      }

      if (b == null) {
         String separator = "_";
         if (baseName.indexOf(47) >= 0) {
            separator = "/";
         }

         throw new MissingResourceException("Could not find the bundle " + baseName + separator + localeID, "", "");
      } else {
         return b;
      }
   }

   private static boolean localeIDStartsWithLangSubtag(String localeID, String lang) {
      return localeID.startsWith(lang) && (localeID.length() == lang.length() || localeID.charAt(lang.length()) == '_');
   }

   private static ResourceBundleWrapper instantiateBundle(String baseName, String localeID, String defaultID, ClassLoader root, boolean disableFallback) {
      final String name = localeID.isEmpty() ? baseName : baseName + '_' + localeID;
      String cacheKey = disableFallback ? name : name + '#' + defaultID;
      return BUNDLE_CACHE.getInstance(
         cacheKey,
         new ResourceBundleWrapper.Loader() {
            @Override
            public ResourceBundleWrapper load() {
               ResourceBundleWrapper parent = null;
               int i = localeID.lastIndexOf(95);
               boolean loadFromProperties = false;
               boolean parentIsRoot = false;
               if (i != -1) {
                  String locName = localeID.substring(0, i);
                  parent = ResourceBundleWrapper.instantiateBundle(baseName, locName, defaultID, root, disableFallback);
               } else if (!localeID.isEmpty()) {
                  parent = ResourceBundleWrapper.instantiateBundle(baseName, "", defaultID, root, disableFallback);
                  parentIsRoot = true;
               }

               ResourceBundleWrapper b = null;

               try {
                  Class<? extends ResourceBundle> cls = root.loadClass(name).asSubclass(ResourceBundle.class);
                  ResourceBundle bx = cls.newInstance();
                  b = new ResourceBundleWrapper(bx);
                  if (parent != null) {
                     b.setParent(parent);
                  }

                  b.baseName = baseName;
                  b.localeID = localeID;
               } catch (ClassNotFoundException var24) {
                  loadFromProperties = true;
               } catch (NoClassDefFoundError var25) {
                  loadFromProperties = true;
               } catch (Exception var26) {
                  if (ResourceBundleWrapper.DEBUG) {
                     System.out.println("failure");
                  }

                  if (ResourceBundleWrapper.DEBUG) {
                     System.out.println(var26);
                  }
               }

               if (loadFromProperties) {
                  try {
                     final String resName = name.replace('.', '/') + ".properties";
                     InputStream stream = AccessController.doPrivileged(new PrivilegedAction<InputStream>() {
                        public InputStream run() {
                           return root.getResourceAsStream(resName);
                        }
                     });
                     if (stream != null) {
                        InputStream var30 = new BufferedInputStream(stream);

                        try {
                           b = new ResourceBundleWrapper(new PropertyResourceBundle(var30));
                           if (parent != null) {
                              b.setParent(parent);
                           }

                           b.baseName = baseName;
                           b.localeID = localeID;
                        } catch (Exception var21) {
                        } finally {
                           try {
                              var30.close();
                           } catch (Exception var20) {
                           }
                        }
                     }

                     if (b == null
                        && !disableFallback
                        && !localeID.isEmpty()
                        && localeID.indexOf(95) < 0
                        && !ResourceBundleWrapper.localeIDStartsWithLangSubtag(defaultID, localeID)) {
                        b = ResourceBundleWrapper.instantiateBundle(baseName, defaultID, defaultID, root, disableFallback);
                     }

                     if (b == null && (!parentIsRoot || !disableFallback)) {
                        b = parent;
                     }
                  } catch (Exception var23) {
                     if (ResourceBundleWrapper.DEBUG) {
                        System.out.println("failure");
                     }

                     if (ResourceBundleWrapper.DEBUG) {
                        System.out.println(var23);
                     }
                  }
               }

               if (b != null) {
                  b.initKeysVector();
               } else if (ResourceBundleWrapper.DEBUG) {
                  System.out.println("Returning null for " + baseName + "_" + localeID);
               }

               return b;
            }
         }
      );
   }

   private abstract static class Loader {
      private Loader() {
      }

      abstract ResourceBundleWrapper load();
   }
}
