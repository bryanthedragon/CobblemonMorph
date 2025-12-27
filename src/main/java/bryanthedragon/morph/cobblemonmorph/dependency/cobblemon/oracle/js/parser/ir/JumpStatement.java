package com.oracle.js.parser.ir;

public abstract class JumpStatement extends Statement {
   private final String labelName;

   JumpStatement(final int lineNumber, final long token, final int finish, final String labelName) {
      super(lineNumber, token, finish);
      this.labelName = labelName;
   }

   JumpStatement(final JumpStatement jumpStatement) {
      super(jumpStatement);
      this.labelName = jumpStatement.labelName;
   }

   @Override
   public boolean hasGoto() {
      return true;
   }

   public String getLabelName() {
      return this.labelName;
   }

   @Override
   public void toString(final StringBuilder sb, final boolean printType) {
      sb.append(this.getStatementName());
      if (this.labelName != null) {
         sb.append(' ').append(this.labelName);
      }
   }

   abstract String getStatementName();
}
