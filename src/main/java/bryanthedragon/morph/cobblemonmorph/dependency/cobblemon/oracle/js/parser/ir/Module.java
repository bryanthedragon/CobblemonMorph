package com.oracle.js.parser.ir;

import com.oracle.js.parser.ParserStrings;
import com.oracle.truffle.api.strings.TruffleString;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public final class Module {
   public static final TruffleString DEFAULT_EXPORT_BINDING_NAME = ParserStrings.constant("*default*");
   public static final TruffleString DEFAULT_NAME = ParserStrings.constant("default");
   public static final TruffleString STAR_NAME = ParserStrings.constant("*");
   public static final TruffleString NAMESPACE_EXPORT_BINDING_NAME = ParserStrings.constant("*namespace*");
   private final List<Module.ModuleRequest> requestedModules;
   private final List<Module.ImportEntry> importEntries;
   private final List<Module.ExportEntry> localExportEntries;
   private final List<Module.ExportEntry> indirectExportEntries;
   private final List<Module.ExportEntry> starExportEntries;
   private final List<ImportNode> imports;
   private final List<ExportNode> exports;

   public Module(
      List<Module.ModuleRequest> requestedModules,
      List<Module.ImportEntry> importEntries,
      List<Module.ExportEntry> localExportEntries,
      List<Module.ExportEntry> indirectExportEntries,
      List<Module.ExportEntry> starExportEntries,
      List<ImportNode> imports,
      List<ExportNode> exports
   ) {
      this.requestedModules = List.copyOf(requestedModules);
      this.importEntries = List.copyOf(importEntries);
      this.localExportEntries = List.copyOf(localExportEntries);
      this.indirectExportEntries = List.copyOf(indirectExportEntries);
      this.starExportEntries = List.copyOf(starExportEntries);
      this.imports = imports == null ? null : List.copyOf(imports);
      this.exports = exports == null ? null : List.copyOf(exports);
   }

   public List<Module.ModuleRequest> getRequestedModules() {
      return this.requestedModules;
   }

   public List<Module.ImportEntry> getImportEntries() {
      return this.importEntries;
   }

   public List<Module.ExportEntry> getLocalExportEntries() {
      return this.localExportEntries;
   }

   public List<Module.ExportEntry> getIndirectExportEntries() {
      return this.indirectExportEntries;
   }

   public List<Module.ExportEntry> getStarExportEntries() {
      return this.starExportEntries;
   }

   public List<ImportNode> getImports() {
      return this.imports;
   }

   public List<ExportNode> getExports() {
      return this.exports;
   }

   @Override
   public String toString() {
      return "Module [requestedModules="
         + this.requestedModules
         + ", importEntries="
         + this.importEntries
         + ", localExportEntries="
         + this.localExportEntries
         + ", indirectExportEntries="
         + this.indirectExportEntries
         + ", starExportEntries="
         + this.starExportEntries
         + ", imports="
         + this.imports
         + ", exports="
         + this.exports
         + "]";
   }

   public static final class ExportEntry {
      private final TruffleString exportName;
      private final Module.ModuleRequest moduleRequest;
      private final TruffleString importName;
      private final TruffleString localName;

      private ExportEntry(TruffleString exportName, Module.ModuleRequest moduleRequest, TruffleString importName, TruffleString localName) {
         this.exportName = exportName;
         this.moduleRequest = moduleRequest;
         this.importName = importName;
         this.localName = localName;
      }

      public static Module.ExportEntry exportStarFrom(Module.ModuleRequest moduleRequest) {
         return new Module.ExportEntry(null, moduleRequest, Module.STAR_NAME, null);
      }

      public static Module.ExportEntry exportStarAsNamespaceFrom(TruffleString exportName, Module.ModuleRequest moduleRequest) {
         return new Module.ExportEntry(exportName, moduleRequest, Module.STAR_NAME, null);
      }

      public static Module.ExportEntry exportDefault(TruffleString localName) {
         return new Module.ExportEntry(Module.DEFAULT_NAME, null, null, localName);
      }

      public static Module.ExportEntry exportSpecifier(TruffleString exportName, TruffleString localName) {
         return new Module.ExportEntry(exportName, null, null, localName);
      }

      public static Module.ExportEntry exportSpecifier(TruffleString exportName) {
         return exportSpecifier(exportName, exportName);
      }

      public static Module.ExportEntry exportIndirect(TruffleString exportName, Module.ModuleRequest moduleRequest, TruffleString importName) {
         return new Module.ExportEntry(exportName, moduleRequest, importName, null);
      }

      public Module.ExportEntry withFrom(Module.ModuleRequest moduleRequest) {
         return new Module.ExportEntry(this.exportName, moduleRequest, this.localName, null);
      }

      public TruffleString getExportName() {
         return this.exportName;
      }

      public Module.ModuleRequest getModuleRequest() {
         return this.moduleRequest;
      }

      public TruffleString getImportName() {
         return this.importName;
      }

      public TruffleString getLocalName() {
         return this.localName;
      }

      @Override
      public String toString() {
         return "ExportEntry [exportName="
            + this.exportName
            + ", moduleRequest="
            + this.moduleRequest
            + ", importName="
            + this.importName
            + ", localName="
            + this.localName
            + "]";
      }
   }

   public static final class ImportEntry {
      private final Module.ModuleRequest moduleRequest;
      private final TruffleString importName;
      private final TruffleString localName;

      private ImportEntry(Module.ModuleRequest moduleRequest, TruffleString importName, TruffleString localName) {
         this.moduleRequest = moduleRequest;
         this.importName = importName;
         this.localName = localName;
      }

      public static Module.ImportEntry importDefault(TruffleString localName) {
         return new Module.ImportEntry(null, Module.DEFAULT_NAME, localName);
      }

      public static Module.ImportEntry importStarAsNameSpaceFrom(TruffleString localNameSpace) {
         return new Module.ImportEntry(null, Module.STAR_NAME, localNameSpace);
      }

      public static Module.ImportEntry importSpecifier(TruffleString importName, TruffleString localName) {
         return new Module.ImportEntry(null, importName, localName);
      }

      public static Module.ImportEntry importSpecifier(TruffleString importName) {
         return importSpecifier(importName, importName);
      }

      public Module.ImportEntry withFrom(Module.ModuleRequest moduleRequest) {
         return new Module.ImportEntry(moduleRequest, this.importName, this.localName);
      }

      public Module.ModuleRequest getModuleRequest() {
         return this.moduleRequest;
      }

      public TruffleString getImportName() {
         return this.importName;
      }

      public TruffleString getLocalName() {
         return this.localName;
      }

      @Override
      public String toString() {
         return "ImportEntry [moduleRequest=" + this.moduleRequest + ", importName=" + this.importName + ", localName=" + this.localName + "]";
      }
   }

   public static final class ModuleRequest {
      private final TruffleString specifier;
      private Map<TruffleString, TruffleString> assertions;

      private ModuleRequest(TruffleString specifier, Map<TruffleString, TruffleString> assertions) {
         this.specifier = specifier;
         this.assertions = assertions;
      }

      public static Module.ModuleRequest create(TruffleString specifier) {
         return new Module.ModuleRequest(specifier, Collections.emptyMap());
      }

      public static Module.ModuleRequest create(TruffleString specifier, Map<TruffleString, TruffleString> assertions) {
         return new Module.ModuleRequest(specifier, Map.copyOf(assertions));
      }

      public static Module.ModuleRequest create(TruffleString specifier, Entry<TruffleString, TruffleString>[] assertions) {
         return new Module.ModuleRequest(specifier, Map.ofEntries(assertions));
      }

      public TruffleString getSpecifier() {
         return this.specifier;
      }

      public Map<TruffleString, TruffleString> getAssertions() {
         return this.assertions;
      }

      public void setAssertions(Map<TruffleString, TruffleString> assertions) {
         this.assertions = assertions;
      }
   }
}
