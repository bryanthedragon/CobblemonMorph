
package com.oracle.truffle.api.impl.asm.tree;

import com.oracle.truffle.api.impl.asm.ModuleVisitor;
import java.util.List;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class ModuleProvideNode {
    public String service;
    public List<String> providers;

    public ModuleProvideNode(String service2, List<String> providers) {
        this.service = service2;
        this.providers = providers;
    }

    public void accept(ModuleVisitor moduleVisitor) {
        moduleVisitor.visitProvide(this.service, this.providers.toArray(new String[0]));
    }
}

