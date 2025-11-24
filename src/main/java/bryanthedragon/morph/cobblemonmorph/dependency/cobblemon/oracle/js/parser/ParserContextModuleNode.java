
package com.oracle.js.parser;

import com.oracle.js.parser.AbstractParser;
import com.oracle.js.parser.ParserContextBaseNode;
import com.oracle.js.parser.ir.ExportNode;
import com.oracle.js.parser.ir.ExportSpecifierNode;
import com.oracle.js.parser.ir.ImportNode;
import com.oracle.js.parser.ir.Module;
import com.oracle.js.parser.ir.Scope;
import com.oracle.truffle.api.strings.TruffleString;
import java.util.ArrayList;
import java.util.List;
import org.graalvm.collections.EconomicMap;
import org.graalvm.collections.EconomicSet;

class ParserContextModuleNode
extends ParserContextBaseNode {
    private static final String MSG_DUPLICATE_EXPORT = "duplicate.export";
    private static final String MSG_EXPORT_NOT_DEFINED = "export.not.defined";
    private final String name;
    private final Scope moduleScope;
    private final AbstractParser parser;
    private List<Module.ModuleRequest> requestedModules = new ArrayList<Module.ModuleRequest>();
    private List<Module.ImportEntry> importEntries = new ArrayList<Module.ImportEntry>();
    private List<Module.ExportEntry> localExportEntries = new ArrayList<Module.ExportEntry>();
    private List<Module.ExportEntry> indirectExportEntries = new ArrayList<Module.ExportEntry>();
    private List<Module.ExportEntry> starExportEntries = new ArrayList<Module.ExportEntry>();
    private List<ImportNode> imports = new ArrayList<ImportNode>();
    private List<ExportNode> exports = new ArrayList<ExportNode>();
    private EconomicMap<String, Module.ImportEntry> importedLocalNames = EconomicMap.create();
    private EconomicSet<String> exportedNames = EconomicSet.create();

    ParserContextModuleNode(String name, Scope moduleScope, AbstractParser parser) {
        this.name = name;
        this.moduleScope = moduleScope;
        this.parser = parser;
    }

    public String getModuleName() {
        return this.name;
    }

    public void addImport(ImportNode importNode) {
        this.imports.add(importNode);
    }

    public void addExport(ExportNode exportNode) {
        this.exports.add(exportNode);
    }

    public void addModuleRequest(Module.ModuleRequest moduleRequest) {
        this.requestedModules.add(moduleRequest);
    }

    public void addImportEntry(Module.ImportEntry importEntry) {
        this.importEntries.add(importEntry);
        this.importedLocalNames.put(importEntry.getLocalName().toJavaStringUncached(), importEntry);
    }

    public void addLocalExportEntry(long exportToken, Module.ExportEntry exportEntry) {
        this.localExportEntries.add(exportEntry);
        this.addExportedName(exportToken, exportEntry);
        if (!this.moduleScope.hasSymbol(exportEntry.getLocalName().toJavaStringUncached())) {
            throw this.parser.error(AbstractParser.message(MSG_EXPORT_NOT_DEFINED, exportEntry.getLocalName().toJavaStringUncached()), exportToken);
        }
    }

    public void addIndirectExportEntry(long exportToken, Module.ExportEntry exportEntry) {
        this.indirectExportEntries.add(exportEntry);
        this.addExportedName(exportToken, exportEntry);
    }

    public void addStarExportEntry(Module.ExportEntry exportEntry) {
        this.starExportEntries.add(exportEntry);
    }

    private void addExportedName(long exportToken, Module.ExportEntry exportEntry) {
        if (!this.exportedNames.add(exportEntry.getExportName().toJavaStringUncached())) {
            throw this.parser.error(AbstractParser.message(MSG_DUPLICATE_EXPORT, exportEntry.getExportName().toJavaStringUncached()), exportToken);
        }
    }

    private void resolveExports() {
        for (ExportNode export : this.exports) {
            long exportToken = export.getToken();
            if (export.getNamedExports() != null) {
                assert (export.getExportIdentifier() == null);
                for (ExportSpecifierNode s : export.getNamedExports().getExportSpecifiers()) {
                    TruffleString localName = s.getIdentifier().getNameTS();
                    Module.ExportEntry ee = s.getExportIdentifier() != null ? Module.ExportEntry.exportSpecifier(s.getExportIdentifier().getNameTS(), localName) : Module.ExportEntry.exportSpecifier(localName);
                    if (export.getFrom() == null) {
                        Module.ImportEntry ie = (Module.ImportEntry)this.importedLocalNames.get(localName.toJavaStringUncached());
                        if (ie == null) {
                            this.addLocalExportEntry(exportToken, ee);
                            continue;
                        }
                        if (ie.getImportName().equals(Module.STAR_NAME)) {
                            this.addLocalExportEntry(exportToken, ee);
                            continue;
                        }
                        this.addIndirectExportEntry(exportToken, Module.ExportEntry.exportIndirect(ee.getExportName(), ie.getModuleRequest(), ie.getImportName()));
                        continue;
                    }
                    this.addIndirectExportEntry(exportToken, ee.withFrom(Module.ModuleRequest.create(export.getFrom().getModuleSpecifier().getValue())));
                }
                continue;
            }
            if (export.getFrom() != null) {
                TruffleString specifier = export.getFrom().getModuleSpecifier().getValue();
                Module.ModuleRequest moduleRequest = Module.ModuleRequest.create(specifier, export.getAssertions());
                if (export.getExportIdentifier() == null) {
                    this.addStarExportEntry(Module.ExportEntry.exportStarFrom(moduleRequest));
                    continue;
                }
                this.addIndirectExportEntry(exportToken, Module.ExportEntry.exportStarAsNamespaceFrom(export.getExportIdentifier().getNameTS(), moduleRequest));
                continue;
            }
            if (export.isDefault()) {
                this.addLocalExportEntry(exportToken, Module.ExportEntry.exportDefault(export.getExportIdentifier().getNameTS()));
                continue;
            }
            this.addLocalExportEntry(exportToken, Module.ExportEntry.exportSpecifier(export.getExportIdentifier().getNameTS()));
        }
    }

    public Module createModule() {
        this.resolveExports();
        return new Module(this.requestedModules, this.importEntries, this.localExportEntries, this.indirectExportEntries, this.starExportEntries, this.imports, this.exports);
    }
}

