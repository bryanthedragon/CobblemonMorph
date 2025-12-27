package com.oracle.js.parser;

import com.oracle.js.parser.ir.Statement;
import java.util.ArrayList;
import java.util.List;

abstract class ParserContextBaseNode implements ParserContextNode {
   protected int flags;
   private List<Statement> statements;

   ParserContextBaseNode() {
      this(0);
   }

   ParserContextBaseNode(int flags) {
      this.flags = flags;
      this.statements = new ArrayList<>();
   }

   @Override
   public int getFlags() {
      return this.flags;
   }

   @Override
   public int getFlag(final int flag) {
      return this.flags & flag;
   }

   @Override
   public int setFlag(final int flag) {
      this.flags |= flag;
      return this.flags;
   }

   public int clearFlag(final int flag) {
      this.flags &= ~flag;
      return this.flags;
   }

   @Override
   public List<Statement> getStatements() {
      return this.statements;
   }

   @Override
   public void setStatements(final List<Statement> statements) {
      this.statements = statements;
   }

   @Override
   public void appendStatement(final Statement statement) {
      this.statements.add(statement);
   }

   @Override
   public void prependStatement(final Statement statement) {
      this.statements.add(0, statement);
   }
}
