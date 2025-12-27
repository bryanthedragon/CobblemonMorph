package com.oracle.js.parser.ir;

import com.oracle.js.parser.ParserStrings;
import com.oracle.js.parser.ir.visitor.NodeVisitor;
import com.oracle.js.parser.ir.visitor.TranslatorNodeVisitor;
import com.oracle.truffle.api.strings.TruffleString;
import java.util.List;

public class ClassNode extends LexicalContextExpression implements LexicalContextScope {
   private final IdentNode ident;
   private final Expression classHeritage;
   private final ClassElement constructor;
   private final List<ClassElement> classElements;
   private final List<Expression> classDecorators;
   private final Scope scope;
   private final int instanceFieldCount;
   private final int staticElementCount;
   private final boolean hasPrivateMethods;
   private final boolean hasPrivateInstanceMethods;
   public static final TruffleString PRIVATE_CONSTRUCTOR_BINDING_NAME = ParserStrings.constant("#constructor");

   public ClassNode(
      final long token,
      final int finish,
      final IdentNode ident,
      final Expression classHeritage,
      final ClassElement constructor,
      final List<ClassElement> classElements,
      final List<Expression> classDecorators,
      final Scope scope,
      final int instanceFieldCount,
      final int staticElementCount,
      final boolean hasPrivateMethods,
      final boolean hasPrivateInstanceMethods
   ) {
      super(token, finish);
      this.ident = ident;
      this.classHeritage = classHeritage;
      this.constructor = constructor;
      this.classElements = List.copyOf(classElements);
      this.scope = scope;
      this.instanceFieldCount = instanceFieldCount;
      this.staticElementCount = staticElementCount;
      this.hasPrivateMethods = hasPrivateMethods;
      this.hasPrivateInstanceMethods = hasPrivateInstanceMethods;
      this.classDecorators = classDecorators;

      assert instanceFieldCount == elementCount(classElements, false);

      assert staticElementCount == elementCount(classElements, true);
   }

   private ClassNode(
      final ClassNode classNode,
      final IdentNode ident,
      final Expression classHeritage,
      final ClassElement constructor,
      final List<ClassElement> classElements,
      final List<Expression> classDecorators
   ) {
      super(classNode);
      this.ident = ident;
      this.classHeritage = classHeritage;
      this.constructor = constructor;
      this.classElements = List.copyOf(classElements);
      this.scope = classNode.scope;
      this.instanceFieldCount = elementCount(classElements, false);
      this.staticElementCount = elementCount(classElements, true);
      this.hasPrivateMethods = classNode.hasPrivateMethods;
      this.hasPrivateInstanceMethods = classNode.hasPrivateInstanceMethods;
      this.classDecorators = classDecorators;
   }

   private static int elementCount(List<ClassElement> classElements, boolean isStatic) {
      int count = 0;

      for (ClassElement classElement : classElements) {
         if (classElement.isStatic() == isStatic && (classElement.isClassField() || classElement.isClassStaticBlock())) {
            count++;
         }
      }

      return count;
   }

   public IdentNode getIdent() {
      return this.ident;
   }

   private ClassNode setIdent(final IdentNode ident) {
      return this.ident == ident ? this : new ClassNode(this, ident, this.classHeritage, this.constructor, this.classElements, this.classDecorators);
   }

   public Expression getClassHeritage() {
      return this.classHeritage;
   }

   private ClassNode setClassHeritage(final Expression classHeritage) {
      return this.classHeritage == classHeritage
         ? this
         : new ClassNode(this, this.ident, classHeritage, this.constructor, this.classElements, this.classDecorators);
   }

   public ClassElement getConstructor() {
      return this.constructor;
   }

   public ClassNode setConstructor(final ClassElement constructor) {
      return this.constructor == constructor
         ? this
         : new ClassNode(this, this.ident, this.classHeritage, constructor, this.classElements, this.classDecorators);
   }

   public List<ClassElement> getClassElements() {
      return this.classElements;
   }

   public ClassNode setClassElements(final List<ClassElement> classElements) {
      return this.classElements == classElements
         ? this
         : new ClassNode(this, this.ident, this.classHeritage, this.constructor, classElements, this.classDecorators);
   }

   public List<Expression> getDecorators() {
      return this.classDecorators;
   }

   public ClassNode setDecorators(final List<Expression> decorators) {
      return this.classDecorators == decorators
         ? this
         : new ClassNode(this, this.ident, this.classHeritage, this.constructor, this.classElements, this.classDecorators);
   }

   @Override
   public Node accept(final LexicalContext lc, final NodeVisitor<? extends LexicalContext> visitor) {
      if (visitor.enterClassNode(this)) {
         IdentNode newIdent = this.ident == null ? null : (IdentNode)this.ident.accept(visitor);
         Expression newClassHeritage = this.classHeritage == null ? null : (Expression)this.classHeritage.accept(visitor);
         ClassElement newConstructor = this.constructor == null ? null : (ClassElement)this.constructor.accept(visitor);
         List<ClassElement> newClassElements = Node.accept(visitor, this.classElements);
         List<Expression> newDecorators = this.classDecorators == null ? null : Node.accept(visitor, this.classDecorators);
         return visitor.leaveClassNode(
            this.setIdent(newIdent)
               .setClassHeritage(newClassHeritage)
               .setConstructor(newConstructor)
               .setClassElements(newClassElements)
               .setDecorators(newDecorators)
         );
      } else {
         return this;
      }
   }

   @Override
   public <R> R accept(final LexicalContext lc, TranslatorNodeVisitor<? extends LexicalContext, R> visitor) {
      return visitor.enterClassNode(this);
   }

   @Override
   public Scope getScope() {
      return this.scope;
   }

   public Scope getClassHeadScope() {
      return this.scope.isClassBodyScope() ? this.scope.getParent() : this.scope;
   }

   public boolean hasInstanceFields() {
      return this.instanceFieldCount != 0;
   }

   public int getInstanceFieldCount() {
      return this.instanceFieldCount;
   }

   public boolean hasStaticElements() {
      return this.staticElementCount != 0;
   }

   public int getStaticElementCount() {
      return this.staticElementCount;
   }

   public boolean hasPrivateMethods() {
      return this.hasPrivateMethods;
   }

   public boolean hasPrivateInstanceMethods() {
      return this.hasPrivateInstanceMethods;
   }

   public boolean isAnonymous() {
      return this.getIdent() == null;
   }

   @Override
   public void toString(StringBuilder sb, boolean printType) {
      if (this.classDecorators != null) {
         for (Expression decorator : this.classDecorators) {
            sb.append("@");
            decorator.toString(sb, printType);
            sb.append(" ");
         }
      }

      sb.append("class");
      if (this.ident != null) {
         sb.append(' ');
         this.ident.toString(sb, printType);
      }

      if (this.classHeritage != null) {
         sb.append(" extends ");
         this.classHeritage.toString(sb, printType);
      }

      sb.append(" {");
      if (this.constructor != null) {
         this.constructor.toString(sb, printType);
      }

      for (ClassElement classElement : this.getClassElements()) {
         sb.append(", ");
         classElement.toString(sb, printType);
      }

      sb.append("}");
   }
}
