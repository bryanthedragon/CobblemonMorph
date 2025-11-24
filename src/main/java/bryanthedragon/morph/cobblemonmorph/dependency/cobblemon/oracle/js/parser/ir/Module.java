
package com.oracle.js.parser.ir;

import com.oracle.js.parser.ParserStrings;
import com.oracle.js.parser.ir.ExportNode;
import com.oracle.js.parser.ir.ImportNode;
import com.oracle.truffle.api.strings.TruffleString;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public final class Module {
    public static final TruffleString DEFAULT_EXPORT_BINDING_NAME = ParserStrings.constant("*default*");
    public static final TruffleString DEFAULT_NAME = ParserStrings.constant("default");
    public static final TruffleString STAR_NAME = ParserStrings.constant("*");
    public static final TruffleString NAMESPACE_EXPORT_BINDING_NAME = ParserStrings.constant("*namespace*");
    private final List<ModuleRequest> requestedModules;
    private final List<ImportEntry> importEntries;
    private final List<ExportEntry> localExportEntries;
    private final List<ExportEntry> indirectExportEntries;
    private final List<ExportEntry> starExportEntries;
    private final List<ImportNode> imports;
    private final List<ExportNode> exports;

    public Module(List<ModuleRequest> requestedModules, List<ImportEntry> importEntries, List<ExportEntry> localExportEntries, List<ExportEntry> indirectExportEntries, List<ExportEntry> starExportEntries, List<ImportNode> imports, List<ExportNode> exports) {
        this.requestedModules = List.copyOf(requestedModules);
        this.importEntries = List.copyOf(importEntries);
        this.localExportEntries = List.copyOf(localExportEntries);
        this.indirectExportEntries = List.copyOf(indirectExportEntries);
        this.starExportEntries = List.copyOf(starExportEntries);
        this.imports = imports == null ? null : List.copyOf(imports);
        this.exports = exports == null ? null : List.copyOf(exports);
    }

    public List<ModuleRequest> getRequestedModules() {
        return this.requestedModules;
    }

    public List<ImportEntry> getImportEntries() {
        return this.importEntries;
    }

    public List<ExportEntry> getLocalExportEntries() {
        return this.localExportEntries;
    }

    public List<ExportEntry> getIndirectExportEntries() {
        return this.indirectExportEntries;
    }

    public List<ExportEntry> getStarExportEntries() {
        return this.starExportEntries;
    }

    public List<ImportNode> getImports() {
        return this.imports;
    }

    public List<ExportNode> getExports() {
        return this.exports;
    }

    public String toString() {
        return "Module [requestedModules=" + this.requestedModules + ", importEntries=" + this.importEntries + ", localExportEntries=" + this.localExportEntries + ", indirectExportEntries=" + this.indirectExportEntries + ", starExportEntries=" + this.starExportEntries + ", imports=" + this.imports + ", exports=" + this.exports + "]";
    }

    public static final class ModuleRequest {
        private final TruffleString specifier;
        private Map<TruffleString, TruffleString> assertions;

        private ModuleRequest(TruffleString specifier, Map<TruffleString, TruffleString> assertions) {
            this.specifier = specifier;
            this.assertions = assertions;
        }

        public static ModuleRequest create(TruffleString specifier) {
            return new ModuleRequest(specifier, Collections.emptyMap());
        }

        public static ModuleRequest create(TruffleString specifier, Map<TruffleString, TruffleString> assertions) {
            return new ModuleRequest(specifier, Map.copyOf(assertions));
        }

        public static ModuleRequest create(TruffleString specifier, Map.Entry<TruffleString, TruffleString>[] assertions) {
            return new ModuleRequest(specifier, Map.ofEntries((Map.Entry[])assertions));
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

    public static final class ImportEntry {
        private final ModuleRequest moduleRequest;
        private final TruffleString importName;
        private final TruffleString localName;

        private ImportEntry(ModuleRequest moduleRequest, TruffleString importName, TruffleString localName) {
            this.moduleRequest = moduleRequest;
            this.importName = importName;
            this.localName = localName;
        }

        public static ImportEntry importDefault(TruffleString localName) {
            return new ImportEntry(null, DEFAULT_NAME, localName);
        }

        public static ImportEntry importStarAsNameSpaceFrom(TruffleString localNameSpace) {
            return new ImportEntry(null, STAR_NAME, localNameSpace);
        }

        public static ImportEntry importSpecifier(TruffleString importName, TruffleString localName) {
            return new ImportEntry(null, importName, localName);
        }

        public static ImportEntry importSpecifier(TruffleString importName) {
            return ImportEntry.importSpecifier(importName, importName);
        }

        public ImportEntry withFrom(ModuleRequest moduleRequest) {
            return new ImportEntry(moduleRequest, this.importName, this.localName);
        }

        public ModuleRequest getModuleRequest() {
            return this.moduleRequest;
        }

        public TruffleString getImportName() {
            return this.importName;
        }

        public TruffleString getLocalName() {
            return this.localName;
        }

        public String toString() {
            return "ImportEntry [moduleRequest=" + this.moduleRequest + ", importName=" + this.importName + ", localName=" + this.localName + "]";
        }
    }

    public static final class ExportEntry {
        private final TruffleString exportName;
        private final ModuleRequest moduleRequest;
        private final TruffleString importName;
        private final TruffleString localName;

        private ExportEntry(TruffleString exportName, ModuleRequest moduleRequest, TruffleString importName, TruffleString localName) {
            this.exportName = exportName;
            this.moduleRequest = moduleRequest;
            this.importName = importName;
            this.localName = localName;
        }

        public static ExportEntry exportStarFrom(ModuleRequest moduleRequest) {
            return new ExportEntry(null, moduleRequest, STAR_NAME, null);
        }

        public static ExportEntry exportStarAsNamespaceFrom(TruffleString exportName, ModuleRequest moduleRequest) {
            return new ExportEntry(exportName, moduleRequest, STAR_NAME, null);
        }

        public static ExportEntry exportDefault(TruffleString localName) {
            return new ExportEntry(DEFAULT_NAME, null, null, localName);
        }

        public static ExportEntry exportSpecifier(TruffleString exportName, TruffleString localName) {
            return new ExportEntry(exportName, null, null, localName);
        }

        public static ExportEntry exportSpecifier(TruffleString exportName) {
            return ExportEntry.exportSpecifier(exportName, exportName);
        }

        public static ExportEntry exportIndirect(TruffleString exportName, ModuleRequest moduleRequest, TruffleString importName) {
            return new ExportEntry(exportName, moduleRequest, importName, null);
        }

        public ExportEntry withFrom(ModuleRequest moduleRequest) {
            return new ExportEntry(this.exportName, moduleRequest, this.localName, null);
        }

        public TruffleString getExportName() {
            return this.exportName;
        }

        public ModuleRequest getModuleRequest() {
            return this.moduleRequest;
        }

        public TruffleString getImportName() {
            return this.importName;
        }

        public TruffleString getLocalName() {
            return this.localName;
        }

        public String toString() {
            return "ExportEntry [exportName=" + this.exportName + ", moduleRequest=" + this.moduleRequest + ", importName=" + this.importName + ", localName=" + this.localName + "]";
        }
    }
}

