package com.oracle.js.parser.ir;

import com.oracle.js.parser.ir.visitor.NodeVisitor;
import com.oracle.js.parser.ir.visitor.TranslatorNodeVisitor;
import com.oracle.truffle.api.strings.TruffleString;

public final class AccessNode extends BaseNode {
   private final TruffleString property;
   private final boolean isPrivate;

   public AccessNode(
      final long token,
      final int finish,
      final Expression base,
      final TruffleString property,
      boolean isSuper,
      boolean isPrivate,
      boolean optional,
      boolean optionalChain
   ) {
      super(token, finish, base, isSuper, optional, optionalChain);
      this.property = property;
      this.isPrivate = isPrivate;

      assert !isSuper || !isPrivate;
   }

   public AccessNode(final long token, final int finish, final Expression base, final TruffleString property) {
      this(token, finish, base, property, false, false, false, false);
   }

   private AccessNode(final AccessNode accessNode, final Expression base, final TruffleString property, boolean isSuper) {
      super(accessNode, base, isSuper, accessNode.isOptional(), accessNode.isOptionalChain());
      this.property = property;
      this.isPrivate = accessNode.isPrivate;
   }

   @Override
   public Node accept(final NodeVisitor<? extends LexicalContext> visitor) {
      return (Node)(visitor.enterAccessNode(this) ? visitor.leaveAccessNode(this.setBase((Expression)this.base.accept(visitor))) : this);
   }

   @Override
   public <R> R accept(TranslatorNodeVisitor<? extends LexicalContext, R> visitor) {
      return visitor.enterAccessNode(this);
   }

   @Override
   public void toString(final StringBuilder sb, final boolean printType) {
      boolean needsParen = this.tokenType().needsParens(this.getBase().tokenType(), true);
      if (needsParen) {
         sb.append('(');
      }

      this.base.toString(sb, printType);
      if (needsParen) {
         sb.append(')');
      }

      if (this.isOptional()) {
         sb.append('?');
      }

      sb.append('.');
      sb.append(this.property);
   }

   public String getProperty() {
      return this.property.toJavaStringUncached();
   }

   public TruffleString getPropertyTS() {
      return this.property;
   }

   public boolean isPrivate() {
      return this.isPrivate;
   }

   public String getPrivateName() {
      assert this.isPrivate();

      return this.property.toJavaStringUncached();
   }

   public TruffleString getPrivateNameTS() {
      assert this.isPrivate();

      return this.property;
   }

   private AccessNode setBase(final Expression base) {
      return this.base == base ? this : new AccessNode(this, base, this.property, this.isSuper());
   }

   public AccessNode setIsSuper() {
      return this.isSuper() ? this : new AccessNode(this, this.base, this.property, true);
   }
}
