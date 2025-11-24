
package com.oracle.js.parser.ir;

import com.oracle.js.parser.ir.ExportSpecifierNode;
import com.oracle.js.parser.ir.LexicalContext;
import com.oracle.js.parser.ir.Node;
import com.oracle.js.parser.ir.visitor.NodeVisitor;
import com.oracle.js.parser.ir.visitor.TranslatorNodeVisitor;
import java.util.List;

public class NamedExportsNode
extends Node {
    private final List<ExportSpecifierNode> exportSpecifiers;

    public NamedExportsNode(long token, int start2, int finish, List<ExportSpecifierNode> exportSpecifiers) {
        super(token, start2, finish);
        this.exportSpecifiers = List.copyOf(exportSpecifiers);
    }

    private NamedExportsNode(NamedExportsNode node, List<ExportSpecifierNode> exportSpecifiers) {
        super(node);
        this.exportSpecifiers = List.copyOf(exportSpecifiers);
    }

    public List<ExportSpecifierNode> getExportSpecifiers() {
        return this.exportSpecifiers;
    }

    public NamedExportsNode setExportSpecifiers(List<ExportSpecifierNode> exportSpecifiers) {
        if (this.exportSpecifiers == exportSpecifiers) {
            return this;
        }
        return new NamedExportsNode(this, exportSpecifiers);
    }

    @Override
    public Node accept(NodeVisitor<? extends LexicalContext> visitor) {
        if (visitor.enterNamedExportsNode(this)) {
            return visitor.leaveNamedExportsNode(this.setExportSpecifiers(Node.accept(visitor, this.exportSpecifiers)));
        }
        return this;
    }

    @Override
    public <R> R accept(TranslatorNodeVisitor<? extends LexicalContext, R> visitor) {
        return visitor.enterNamedExportsNode(this);
    }

    @Override
    public void toString(StringBuilder sb, boolean printType) {
        sb.append('{');
        for (int i = 0; i < this.exportSpecifiers.size(); ++i) {
            this.exportSpecifiers.get(i).toString(sb, printType);
            if (i >= this.exportSpecifiers.size() - 1) continue;
            sb.append(", ");
        }
        sb.append('}');
    }
}

