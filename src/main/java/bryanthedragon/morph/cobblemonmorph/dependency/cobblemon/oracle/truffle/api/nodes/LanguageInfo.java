package com.oracle.truffle.api.nodes;

import java.util.Set;

public final class LanguageInfo {
   private final Object languageCache;
   private final String id;
   private final String name;
   private final String version;
   private final Set<String> mimeTypes;
   private final String defaultMimeType;
   private final boolean internal;
   private final boolean interactive;

   LanguageInfo(
      Object languageCache, String id, String name, String version, String defaultMimeType, Set<String> mimeTypes, boolean internal, boolean interactive
   ) {
      this.languageCache = languageCache;
      this.id = id;
      this.name = name;
      this.version = version;
      this.defaultMimeType = defaultMimeType;
      this.mimeTypes = mimeTypes;
      this.internal = internal;
      this.interactive = interactive;
   }

   public String getId() {
      return this.id;
   }

   public String getName() {
      return this.name;
   }

   public String getVersion() {
      return this.version;
   }

   public String getDefaultMimeType() {
      return this.defaultMimeType;
   }

   public Set<String> getMimeTypes() {
      return this.mimeTypes;
   }

   public boolean isInternal() {
      return this.internal;
   }

   public boolean isInteractive() {
      return this.interactive;
   }

   Object getLanguageCache() {
      return this.languageCache;
   }
}
