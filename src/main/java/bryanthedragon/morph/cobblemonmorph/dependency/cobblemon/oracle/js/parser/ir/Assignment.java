package com.oracle.js.parser.ir;

public interface Assignment<D extends Expression> {
   D getAssignmentDest();

   Expression getAssignmentSource();
}
