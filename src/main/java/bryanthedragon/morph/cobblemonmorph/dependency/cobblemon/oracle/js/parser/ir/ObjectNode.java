package com.oracle.js.parser.ir;

import com.oracle.js.parser.ir.visitor.NodeVisitor;
import com.oracle.js.parser.ir.visitor.TranslatorNodeVisitor;
import java.util.List;

public final class ObjectNode extends Expression {
   private final List<PropertyNode> elements;

   public ObjectNode(final long token, final int finish, final List<PropertyNode> elements) {
      super(token, finish);
      this.elements = List.copyOf(elements);
   }

   private ObjectNode(final ObjectNode objectNode, final List<PropertyNode> elements) {
      super(objectNode);
      this.elements = List.copyOf(elements);
   }

   @Override
   public Node accept(final NodeVisitor<? extends LexicalContext> visitor) {
      return (Node)(visitor.enterObjectNode(this) ? visitor.leaveObjectNode(this.setElements(Node.accept(visitor, this.elements))) : this);
   }

   @Override
   public <R> R accept(TranslatorNodeVisitor<? extends LexicalContext, R> visitor) {
      return visitor.enterObjectNode(this);
   }

   @Override
   public void toString(final StringBuilder sb, final boolean printType) {
      sb.append('{');
      if (!this.elements.isEmpty()) {
         sb.append(' ');
         boolean first = true;

         for (Node element : this.elements) {
            if (!first) {
               sb.append(", ");
            }

            first = false;
            element.toString(sb, printType);
         }

         sb.append(' ');
      }

      sb.append('}');
   }

   public List<PropertyNode> getElements() {
      return this.elements;
   }

   private ObjectNode setElements(final List<PropertyNode> elements) {
      return this.elements == elements ? this : new ObjectNode(this, elements);
   }
}
