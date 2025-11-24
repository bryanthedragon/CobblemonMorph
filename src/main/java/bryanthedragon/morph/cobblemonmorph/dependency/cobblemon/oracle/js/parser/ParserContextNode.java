
package com.oracle.js.parser;

import com.oracle.js.parser.ir.Statement;
import java.util.List;

interface ParserContextNode {
    public int getFlags();

    public int getFlag(int var1);

    public int setFlag(int var1);

    public List<Statement> getStatements();

    public void setStatements(List<Statement> var1);

    public void appendStatement(Statement var1);

    public void prependStatement(Statement var1);
}

