package com.oracle.js.parser;

import com.oracle.js.parser.ir.Statement;
import java.util.List;

interface ParserContextNode {
   int getFlags();

   int getFlag(final int flag);

   int setFlag(final int flag);

   List<Statement> getStatements();

   void setStatements(final List<Statement> statements);

   void appendStatement(final Statement statement);

   void prependStatement(final Statement statement);
}
