package com.oracle.js.parser.ir;

import com.oracle.js.parser.ir.visitor.NodeVisitor;
import com.oracle.js.parser.ir.visitor.TranslatorNodeVisitor;
import java.util.List;

public class Block extends Node implements BreakableNode, Terminal, Flags<Block>, LexicalContextScope {
   protected final List<Statement> statements;
   protected final Scope scope;
   protected final int flags;
   public static final int NEEDS_SCOPE = 1;
   public static final int IS_TERMINAL = 4;
   public static final int IS_GLOBAL_SCOPE = 8;
   public static final int IS_SYNTHETIC = 16;
   public static final int IS_BODY = 32;
   public static final int IS_PARAMETER_BLOCK = 64;
   public static final int IS_SWITCH_BLOCK = 128;
   public static final int IS_EXPRESSION_BLOCK = 256;
   public static final int IS_MODULE_BODY = 512;

   public Block(final long token, final int finish, final int flags, final Scope scope, final List<Statement> statements) {
      super(token, finish);

      assert this.start <= finish;

      this.statements = List.copyOf(statements);
      this.scope = scope;
      int len = statements.size();
      int terminalFlags = len > 0 && statements.get(len - 1).hasTerminalFlags() ? 4 : 0;
      this.flags = terminalFlags | flags;
   }

   private Block(final Block block, final int finish, final List<Statement> statements, final int flags) {
      super(block, finish);
      this.statements = statements;
      this.flags = flags;
      this.scope = block.scope;
   }

   public boolean isGlobalScope() {
      return this.getFlag(8);
   }

   @Override
   public Node accept(final LexicalContext lc, final NodeVisitor<? extends LexicalContext> visitor) {
      return (Node)(visitor.enterBlock(this) ? visitor.leaveBlock(this.setStatements(lc, Node.accept(visitor, this.statements))) : this);
   }

   @Override
   public <R> R accept(LexicalContext lc, TranslatorNodeVisitor<? extends LexicalContext, R> visitor) {
      return visitor.enterBlock(this);
   }

   public Iterable<Symbol> getSymbols() {
      return this.scope.getSymbols();
   }

   public Symbol getExistingSymbol(final String name) {
      return this.scope.getExistingSymbol(name);
   }

   public boolean hasSymbol(final String name) {
      return this.scope.hasSymbol(name);
   }

   public int getSymbolCount() {
      return this.scope.getSymbolCount();
   }

   public boolean isCatchBlock() {
      return this.getLastStatement() instanceof CatchNode;
   }

   @Override
   public void toString(final StringBuilder sb, final boolean printType) {
      for (Node statement : this.statements) {
         statement.toString(sb, printType);
         sb.append(';');
      }
   }

   @Override
   public int getFlags() {
      return this.flags;
   }

   @Override
   public boolean isTerminal() {
      return this.getFlag(4);
   }

   public List<Statement> getStatements() {
      return this.statements;
   }

   public int getStatementCount() {
      return this.statements.size();
   }

   public int getFirstStatementLineNumber() {
      return this.statements.isEmpty() ? -1 : this.statements.get(0).getLineNumber();
   }

   public Statement getFirstStatement() {
      return this.statements.isEmpty() ? null : this.statements.get(0);
   }

   public Statement getLastStatement() {
      return this.statements.isEmpty() ? null : this.statements.get(this.statements.size() - 1);
   }

   public Block setStatements(final LexicalContext lc, final List<Statement> statements) {
      if (this.statements == statements) {
         return this;
      } else {
         int lastFinish = 0;
         if (!statements.isEmpty()) {
            lastFinish = statements.get(statements.size() - 1).getFinish();
         }

         return Node.replaceInLexicalContext(lc, this, new Block(this, Math.max(this.finish, lastFinish), statements, this.flags));
      }
   }

   public boolean needsScope() {
      return (this.flags & 1) == 1;
   }

   public boolean isSynthetic() {
      return (this.flags & 16) == 16;
   }

   public Block setFlags(final LexicalContext lc, final int flags) {
      return this.flags == flags ? this : Node.replaceInLexicalContext(lc, this, new Block(this, this.finish, this.statements, flags));
   }

   public Block setFlag(final LexicalContext lc, final int flag) {
      return this.setFlags(lc, this.flags | flag);
   }

   @Override
   public boolean getFlag(final int flag) {
      return (this.flags & flag) == flag;
   }

   @Override
   public boolean isBreakableWithoutLabel() {
      return false;
   }

   @Override
   public Node accept(final NodeVisitor<? extends LexicalContext> visitor) {
      return BreakableNode.super.accept(visitor);
   }

   @Override
   public <R> R accept(TranslatorNodeVisitor<? extends LexicalContext, R> visitor) {
      return BreakableNode.super.accept(visitor);
   }

   @Override
   public Scope getScope() {
      return this.scope;
   }

   public boolean isFunctionBody() {
      return this.getFlag(32);
   }

   public boolean isParameterBlock() {
      return this.getFlag(64);
   }

   public boolean isSwitchBlock() {
      return this.getFlag(128);
   }

   public boolean isExpressionBlock() {
      return this.getFlag(256);
   }

   public boolean isModuleBody() {
      return this.getFlag(512);
   }
}
