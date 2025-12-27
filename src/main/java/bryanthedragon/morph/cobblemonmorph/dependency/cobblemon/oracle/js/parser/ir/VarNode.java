package com.oracle.js.parser.ir;

import com.oracle.js.parser.ir.visitor.NodeVisitor;
import com.oracle.js.parser.ir.visitor.TranslatorNodeVisitor;

public final class VarNode extends Statement implements Assignment<IdentNode> {
   private final IdentNode name;
   private final Expression init;
   private final int flags;
   private final int sourceOrder;
   public static final int IS_LET = 1;
   public static final int IS_CONST = 2;
   public static final int IS_LAST_FUNCTION_DECLARATION = 4;
   public static final int IS_EXPORT = 8;
   public static final int IS_DESTRUCTURING = 16;
   public static final int IS_ANNEXB_BLOCK_TO_FUNCTION_TRANSFER = 32;

   public VarNode(final int lineNumber, final long token, final int finish, final IdentNode name, final Expression init) {
      this(lineNumber, token, finish, name, init, 0);
   }

   private VarNode(final VarNode varNode, final IdentNode name, final Expression init, final int flags) {
      super(varNode);
      this.sourceOrder = -1;
      this.name = init == null ? name : name.setIsInitializedHere();
      this.init = init;
      this.flags = flags;
   }

   public VarNode(final int lineNumber, final long token, final int finish, final IdentNode name, final Expression init, final int flags) {
      this(lineNumber, token, -1, finish, name, init, flags);
   }

   public VarNode(final int lineNumber, final long token, final int sourceOrder, final int finish, final IdentNode name, final Expression init, final int flags) {
      super(lineNumber, token, finish);
      this.sourceOrder = sourceOrder;
      this.name = init == null ? name : name.setIsInitializedHere();
      this.init = init;
      this.flags = flags;
   }

   public VarNode(
      final int lineNumber,
      final long token,
      final int sourceOrder,
      final int start,
      final int finish,
      final IdentNode name,
      final Expression init,
      final int flags
   ) {
      super(lineNumber, token, start, finish);
      this.sourceOrder = sourceOrder;
      this.name = init == null ? name : name.setIsInitializedHere();
      this.init = init;
      this.flags = flags;
   }

   @Override
   public int getSourceOrder() {
      return this.sourceOrder == -1 ? super.getSourceOrder() : this.sourceOrder;
   }

   @Override
   public boolean isAssignment() {
      return this.hasInit();
   }

   public IdentNode getAssignmentDest() {
      return this.isAssignment() ? this.name : null;
   }

   @Override
   public Expression getAssignmentSource() {
      return this.isAssignment() ? this.getInit() : null;
   }

   public boolean isBlockScoped() {
      return this.getFlag(1) || this.getFlag(2);
   }

   public boolean isLet() {
      return this.getFlag(1);
   }

   public boolean isConst() {
      return this.getFlag(2);
   }

   public int getSymbolFlags() {
      if (this.isLet()) {
         return this.getName().isCatchParameter() ? 33793 : 1;
      } else {
         return this.isConst() ? 2 : 4;
      }
   }

   public boolean hasInit() {
      return this.init != null;
   }

   @Override
   public Node accept(final NodeVisitor<? extends LexicalContext> visitor) {
      if (visitor.enterVarNode(this)) {
         Expression newInit = this.init == null ? null : (Expression)this.init.accept(visitor);
         IdentNode newName = (IdentNode)this.name.accept(visitor);
         VarNode newThis;
         if (this.name == newName && this.init == newInit) {
            newThis = this;
         } else {
            newThis = new VarNode(this, newName, newInit, this.flags);
         }

         return visitor.leaveVarNode(newThis);
      } else {
         return this;
      }
   }

   @Override
   public <R> R accept(TranslatorNodeVisitor<? extends LexicalContext, R> visitor) {
      return visitor.enterVarNode(this);
   }

   @Override
   public void toString(final StringBuilder sb, final boolean printType) {
      sb.append(this.tokenType().getName()).append(' ');
      this.name.toString(sb, printType);
      if (this.init != null) {
         sb.append(" = ");
         this.init.toString(sb, printType);
      }
   }

   public Expression getInit() {
      return this.init;
   }

   public IdentNode getName() {
      return this.name;
   }

   private VarNode setFlags(final int flags) {
      return this.flags == flags ? this : new VarNode(this, this.name, this.init, flags);
   }

   public boolean getFlag(final int flag) {
      return (this.flags & flag) == flag;
   }

   public VarNode setFlag(final int flag) {
      return this.setFlags(this.flags | flag);
   }

   public boolean isHoistableDeclaration() {
      return this.init instanceof FunctionNode && ((FunctionNode)this.init).isDeclared();
   }

   public boolean isFunctionDeclaration() {
      return this.init instanceof FunctionNode && ((FunctionNode)this.init).isFunctionDeclaration();
   }

   public boolean isExport() {
      return this.getFlag(8);
   }

   public boolean isDestructuring() {
      return this.getFlag(16);
   }

   public boolean isClassDeclaration() {
      return this.init instanceof ClassNode;
   }
}
