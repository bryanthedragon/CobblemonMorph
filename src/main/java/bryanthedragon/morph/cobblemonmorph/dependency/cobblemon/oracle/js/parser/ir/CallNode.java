package com.oracle.js.parser.ir;

import com.oracle.js.parser.ir.visitor.NodeVisitor;
import com.oracle.js.parser.ir.visitor.TranslatorNodeVisitor;
import java.util.List;

public final class CallNode extends OptionalExpression {
   private final Expression function;
   private final List<Expression> args;
   private static final int IS_NEW = 1;
   private static final int IS_EVAL = 2;
   private static final int IS_IMPORT = 4;
   private static final int IS_APPLY_ARGUMENTS = 8;
   private static final int IS_OPTIONAL = 16;
   private static final int IS_OPTIONAL_CHAIN = 32;
   private static final int IS_TAGGED_TEMPLATE_LITERAL = 64;
   private static final int IS_DEFAULT_DERIVED_CONSTRUCTOR_SUPER_CALL = 128;
   private final int flags;
   private final int lineNumber;

   public static Expression forNew(int lineNumber, long token, int start, int finish, Expression function, List<Expression> args) {
      return new CallNode(lineNumber, token, start, finish, function, args, 1);
   }

   public static Expression forCall(int lineNumber, long token, int start, int finish, Expression function, List<Expression> args) {
      return forCall(lineNumber, token, start, finish, function, args, false, false, false, false, false);
   }

   public static Expression forCall(
      int lineNumber, long token, int start, int finish, Expression function, List<Expression> args, boolean optional, boolean optionalChain
   ) {
      return forCall(lineNumber, token, start, finish, function, args, optional, optionalChain, false, false, false);
   }

   public static Expression forCall(
      int lineNumber,
      long token,
      int start,
      int finish,
      Expression function,
      List<Expression> args,
      boolean optional,
      boolean optionalChain,
      boolean isEval,
      boolean isApplyArguments,
      boolean isDefaultDerivedConstructorSuperCall
   ) {
      return create(
         lineNumber, token, start, finish, function, args, optional, optionalChain, isEval, isApplyArguments, isDefaultDerivedConstructorSuperCall, false
      );
   }

   public static Expression forTaggedTemplateLiteral(int lineNumber, long token, int start, int finish, Expression function, List<Expression> args) {
      return create(lineNumber, token, start, finish, function, args, false, false, false, false, false, true);
   }

   private static Expression create(
      int lineNumber,
      long token,
      int start,
      int finish,
      Expression function,
      List<Expression> args,
      boolean optional,
      boolean optionalChain,
      boolean isEval,
      boolean isApplyArguments,
      boolean isDefaultDerivedConstructorSuperCall,
      boolean isTaggedTemplateLiteral
   ) {
      return new CallNode(
         lineNumber,
         token,
         start,
         finish,
         setIsFunction(function),
         args,
         (optional ? 16 : 0)
            | (optionalChain ? 32 : 0)
            | (isEval ? 2 : 0)
            | (isApplyArguments ? 8 : 0)
            | (isTaggedTemplateLiteral ? 64 : 0)
            | (isDefaultDerivedConstructorSuperCall ? 128 : 0)
      );
   }

   public static Expression forImport(int lineNumber, long token, int start, int finish, IdentNode importIdent, List<Expression> args) {
      return new CallNode(lineNumber, token, start, finish, importIdent, args, 4);
   }

   private CallNode(int lineNumber, long token, int start, int finish, Expression function, List<Expression> args, int flags) {
      super(token, start, finish);
      this.function = function;
      this.args = List.copyOf(args);
      this.flags = flags;
      this.lineNumber = lineNumber;
   }

   private CallNode(final CallNode callNode, final Expression function, final List<Expression> args, final int flags) {
      super(callNode);
      this.lineNumber = callNode.lineNumber;
      this.function = function;
      this.args = List.copyOf(args);
      this.flags = flags;
   }

   private static Expression setIsFunction(final Expression function) {
      return (Expression)(function instanceof BaseNode ? ((BaseNode)function).setIsFunction() : function);
   }

   public int getLineNumber() {
      return this.lineNumber;
   }

   @Override
   public Node accept(final NodeVisitor<? extends LexicalContext> visitor) {
      return (Node)(visitor.enterCallNode(this)
         ? visitor.leaveCallNode(this.setFunction((Expression)this.function.accept(visitor)).setArgs(Node.accept(visitor, this.args)))
         : this);
   }

   @Override
   public <R> R accept(TranslatorNodeVisitor<? extends LexicalContext, R> visitor) {
      return visitor.enterCallNode(this);
   }

   @Override
   public void toString(final StringBuilder sb, final boolean printType) {
      StringBuilder fsb = new StringBuilder();
      this.function.toString(fsb, printType);
      sb.append((CharSequence)fsb);
      if (this.isOptional()) {
         sb.append('?').append('.');
      }

      sb.append('(');
      boolean first = true;

      for (Node arg : this.args) {
         if (!first) {
            sb.append(", ");
         } else {
            first = false;
         }

         arg.toString(sb, printType);
      }

      sb.append(')');
   }

   public List<Expression> getArgs() {
      return this.args;
   }

   public CallNode setArgs(final List<Expression> args) {
      return this.args == args ? this : new CallNode(this, this.function, args, this.flags);
   }

   public boolean isEval() {
      return (this.flags & 2) != 0;
   }

   public Expression getFunction() {
      return this.function;
   }

   public CallNode setFunction(final Expression function) {
      return this.function == function ? this : new CallNode(this, function, this.args, this.flags);
   }

   public boolean isNew() {
      return (this.flags & 1) != 0;
   }

   public boolean isImport() {
      return (this.flags & 4) != 0;
   }

   public boolean isApplyArguments() {
      return (this.flags & 8) != 0;
   }

   @Override
   public boolean isOptional() {
      return (this.flags & 16) != 0;
   }

   @Override
   public boolean isOptionalChain() {
      return (this.flags & 32) != 0;
   }

   public boolean isTaggedTemplateLiteral() {
      return (this.flags & 64) != 0;
   }

   public boolean isDefaultDerivedConstructorSuperCall() {
      return (this.flags & 128) != 0;
   }
}
