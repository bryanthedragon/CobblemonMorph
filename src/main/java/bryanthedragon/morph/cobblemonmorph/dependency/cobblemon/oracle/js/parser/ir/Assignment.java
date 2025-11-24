
package com.oracle.js.parser.ir;

import com.oracle.js.parser.ir.Expression;

public interface Assignment<D extends Expression> {
    public D getAssignmentDest();

    public Expression getAssignmentSource();
}

