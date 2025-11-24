
package com.oracle.js.parser.ir;

import com.oracle.js.parser.ParserStrings;
import com.oracle.js.parser.ir.ClassElement;
import com.oracle.js.parser.ir.Expression;
import com.oracle.js.parser.ir.IdentNode;
import com.oracle.js.parser.ir.LexicalContext;
import com.oracle.js.parser.ir.LexicalContextExpression;
import com.oracle.js.parser.ir.LexicalContextScope;
import com.oracle.js.parser.ir.Node;
import com.oracle.js.parser.ir.Scope;
import com.oracle.js.parser.ir.visitor.NodeVisitor;
import com.oracle.js.parser.ir.visitor.TranslatorNodeVisitor;
import com.oracle.truffle.api.strings.TruffleString;
import java.util.List;

public class ClassNode
extends LexicalContextExpression
implements LexicalContextScope {
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

    public ClassNode(long token, int finish, IdentNode ident, Expression classHeritage, ClassElement constructor, List<ClassElement> classElements, List<Expression> classDecorators, Scope scope, int instanceFieldCount, int staticElementCount, boolean hasPrivateMethods, boolean hasPrivateInstanceMethods) {
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
        assert (instanceFieldCount == ClassNode.elementCount(classElements, false));
        assert (staticElementCount == ClassNode.elementCount(classElements, true));
    }

    private ClassNode(ClassNode classNode, IdentNode ident, Expression classHeritage, ClassElement constructor, List<ClassElement> classElements, List<Expression> classDecorators) {
        super(classNode);
        this.ident = ident;
        this.classHeritage = classHeritage;
        this.constructor = constructor;
        this.classElements = List.copyOf(classElements);
        this.scope = classNode.scope;
        this.instanceFieldCount = ClassNode.elementCount(classElements, false);
        this.staticElementCount = ClassNode.elementCount(classElements, true);
        this.hasPrivateMethods = classNode.hasPrivateMethods;
        this.hasPrivateInstanceMethods = classNode.hasPrivateInstanceMethods;
        this.classDecorators = classDecorators;
    }

    private static int elementCount(List<ClassElement> classElements, boolean isStatic) {
        int count = 0;
        for (ClassElement classElement : classElements) {
            if (classElement.isStatic() != isStatic || !classElement.isClassField() && !classElement.isClassStaticBlock()) continue;
            ++count;
        }
        return count;
    }

    public IdentNode getIdent() {
        return this.ident;
    }

    private ClassNode setIdent(IdentNode ident) {
        if (this.ident == ident) {
            return this;
        }
        return new ClassNode(this, ident, this.classHeritage, this.constructor, this.classElements, this.classDecorators);
    }

    public Expression getClassHeritage() {
        return this.classHeritage;
    }

    private ClassNode setClassHeritage(Expression classHeritage) {
        if (this.classHeritage == classHeritage) {
            return this;
        }
        return new ClassNode(this, this.ident, classHeritage, this.constructor, this.classElements, this.classDecorators);
    }

    public ClassElement getConstructor() {
        return this.constructor;
    }

    public ClassNode setConstructor(ClassElement constructor) {
        if (this.constructor == constructor) {
            return this;
        }
        return new ClassNode(this, this.ident, this.classHeritage, constructor, this.classElements, this.classDecorators);
    }

    public List<ClassElement> getClassElements() {
        return this.classElements;
    }

    public ClassNode setClassElements(List<ClassElement> classElements) {
        if (this.classElements == classElements) {
            return this;
        }
        return new ClassNode(this, this.ident, this.classHeritage, this.constructor, classElements, this.classDecorators);
    }

    public List<Expression> getDecorators() {
        return this.classDecorators;
    }

    public ClassNode setDecorators(List<Expression> decorators) {
        if (this.classDecorators == decorators) {
            return this;
        }
        return new ClassNode(this, this.ident, this.classHeritage, this.constructor, this.classElements, this.classDecorators);
    }

    @Override
    public Node accept(LexicalContext lc, NodeVisitor<? extends LexicalContext> visitor) {
        if (visitor.enterClassNode(this)) {
            IdentNode newIdent = this.ident == null ? null : (IdentNode)this.ident.accept(visitor);
            Expression newClassHeritage = this.classHeritage == null ? null : (Expression)this.classHeritage.accept(visitor);
            ClassElement newConstructor = this.constructor == null ? null : (ClassElement)this.constructor.accept(visitor);
            List<ClassElement> newClassElements = Node.accept(visitor, this.classElements);
            List<Expression> newDecorators = this.classDecorators == null ? null : Node.accept(visitor, this.classDecorators);
            return visitor.leaveClassNode(this.setIdent(newIdent).setClassHeritage(newClassHeritage).setConstructor(newConstructor).setClassElements(newClassElements).setDecorators(newDecorators));
        }
        return this;
    }

    @Override
    public <R> R accept(LexicalContext lc, TranslatorNodeVisitor<? extends LexicalContext, R> visitor) {
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

