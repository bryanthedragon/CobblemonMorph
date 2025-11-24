
package com.oracle.truffle.api.debug;

import com.oracle.truffle.api.instrumentation.StandardTags;
import com.oracle.truffle.api.instrumentation.Tag;

public enum SourceElement {
    STATEMENT(StandardTags.StatementTag.class),
    EXPRESSION(StandardTags.ExpressionTag.class),
    ROOT(StandardTags.RootTag.class);

    private final Class<? extends Tag> tag;

    private SourceElement(Class<? extends Tag> tag) {
        this.tag = tag;
    }

    Class<? extends Tag> getTag() {
        return this.tag;
    }
}

