
package com.oracle.js.parser;

import com.oracle.js.parser.ParserContext;
import com.oracle.js.parser.ParserContextBaseNode;
import com.oracle.js.parser.ParserContextScopableNode;
import com.oracle.js.parser.ir.IdentNode;
import com.oracle.js.parser.ir.Scope;
import java.util.Iterator;
import org.graalvm.collections.EconomicMap;
import org.graalvm.collections.UnmodifiableMapCursor;

class ParserContextClassNode
extends ParserContextBaseNode
implements ParserContextScopableNode {
    private Scope scope;
    protected EconomicMap<String, IdentNode> unresolvedPrivateIdentifiers;

    ParserContextClassNode(Scope scope) {
        this.scope = scope;
        assert (scope.isClassHeadScope() || scope.isClassBodyScope());
    }

    @Override
    public Scope getScope() {
        return this.scope;
    }

    public void setScope(Scope scope) {
        assert (scope.isClassHeadScope() || scope.isClassBodyScope());
        this.scope = scope;
    }

    void usePrivateName(IdentNode ident) {
        String name = ident.getName();
        if (this.scope.findPrivateName(name)) {
            return;
        }
        if (this.unresolvedPrivateIdentifiers == null) {
            this.unresolvedPrivateIdentifiers = EconomicMap.create();
        }
        if (!this.unresolvedPrivateIdentifiers.containsKey(name)) {
            this.unresolvedPrivateIdentifiers.put(name, ident);
        }
    }

    IdentNode verifyAllPrivateIdentifiersValid(ParserContext lc) {
        if (this.unresolvedPrivateIdentifiers != null) {
            UnmodifiableMapCursor entries = this.unresolvedPrivateIdentifiers.getEntries();
            block0: while (entries.advance()) {
                IdentNode unresolved = (IdentNode)entries.getValue();
                String name = (String)entries.getKey();
                if (this.scope.findPrivateName(name)) continue;
                Iterator<ParserContextClassNode> it = lc.getClasses();
                boolean seenThis = false;
                while (it.hasNext()) {
                    ParserContextClassNode outer = it.next();
                    if (!seenThis) {
                        if (outer != this) continue;
                        seenThis = true;
                        continue;
                    }
                    outer.usePrivateName(unresolved);
                    continue block0;
                }
                return unresolved;
            }
            this.unresolvedPrivateIdentifiers = null;
        }
        return null;
    }
}

