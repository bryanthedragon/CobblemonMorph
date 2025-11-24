
package com.oracle.js.parser.ir;

import com.oracle.js.parser.ir.Expression;
import com.oracle.js.parser.ir.FunctionNode;
import com.oracle.js.parser.ir.IdentNode;
import com.oracle.js.parser.ir.LexicalContext;
import com.oracle.js.parser.ir.Node;
import com.oracle.js.parser.ir.PropertyNode;
import com.oracle.js.parser.ir.visitor.NodeVisitor;
import com.oracle.js.parser.ir.visitor.TranslatorNodeVisitor;
import java.util.ArrayList;
import java.util.List;

public final class ClassElement
extends PropertyNode {
    private static final int KIND_METHOD = 1;
    private static final int KIND_ACCESSOR = 2;
    private static final int KIND_FIELD = 4;
    private static final int KIND_STATIC_INIT = 8;
    private final int kind;
    private final List<Expression> decorators;
    private final boolean isAutoAccessor;

    private ClassElement(long token, int finish, int kind, Expression key, Expression value2, FunctionNode get, FunctionNode set2, List<Expression> decorators, boolean hasComputedKey, boolean isAnonymousFunctionDefinition, boolean isStatic, boolean isAutoAccessor) {
        super(token, finish, key, value2, get, set2, isStatic, hasComputedKey, isAnonymousFunctionDefinition);
        this.kind = kind;
        this.decorators = decorators;
        this.isAutoAccessor = isAutoAccessor;
    }

    private ClassElement(ClassElement element, int kind, Expression key, Expression value2, FunctionNode get, FunctionNode set2, List<Expression> decorators, boolean hasComputedKey, boolean isAnonymousFunctionDefinition, boolean isStatic, boolean isAutoAccessor) {
        super(element.getToken(), element.finish, key, value2, get, set2, isStatic, hasComputedKey, isAnonymousFunctionDefinition);
        this.kind = kind;
        this.decorators = decorators;
        this.isAutoAccessor = isAutoAccessor;
    }

    public static ClassElement createMethod(long token, int finish, Expression key, Expression value2, List<Expression> decorators, boolean isStatic, boolean hasComputedKey) {
        return new ClassElement(token, finish, 1, key, value2, null, null, decorators, hasComputedKey, false, isStatic, false);
    }

    public static ClassElement createAccessor(long token, int finish, Expression key, FunctionNode get, FunctionNode set2, List<Expression> decorators, boolean isPrivate, boolean isStatic, boolean hasComputedKey) {
        return new ClassElement(token, finish, 2, key, null, get, set2, decorators, hasComputedKey, false, isStatic, false);
    }

    public static ClassElement createField(long token, int finish, Expression key, Expression initialize2, List<Expression> decorators, boolean isStatic, boolean hasComputedKey, boolean anonymousFunctionDefinition) {
        return new ClassElement(token, finish, 4, key, initialize2, null, null, decorators, hasComputedKey, anonymousFunctionDefinition, isStatic, false);
    }

    public static ClassElement createDefaultConstructor(long token, int finish, Expression key, Expression value2) {
        return new ClassElement(token, finish, 1, key, value2, null, null, null, false, false, false, false);
    }

    public static ClassElement createStaticInitializer(long token, int finish, FunctionNode functionNode) {
        return new ClassElement(token, finish, 8, null, (Expression)functionNode, null, null, null, false, false, true, false);
    }

    public static ClassElement createAutoAccessor(long token, int finish, Expression key, FunctionNode initializer, List<Expression> classElementDecorators, boolean isStatic, boolean hasComputedKey, boolean anonymousFunctionDefinition) {
        return new ClassElement(token, finish, 4, key, (Expression)initializer, null, null, classElementDecorators, hasComputedKey, anonymousFunctionDefinition, isStatic, true);
    }

    @Override
    public Node accept(NodeVisitor<? extends LexicalContext> visitor) {
        if (visitor.enterClassElement(this)) {
            ClassElement element = this.setKey(this.key == null ? null : (Expression)this.key.accept(visitor)).setValue(this.value == null ? null : (Expression)this.value.accept(visitor)).setGetter(this.getter == null ? null : (FunctionNode)this.getter.accept(visitor)).setSetter(this.setter == null ? null : (FunctionNode)this.setter.accept(visitor));
            element = this.decorators != null ? element.setDecorators(Node.accept(visitor, new ArrayList())) : element.setDecorators(null);
            return element;
        }
        return this;
    }

    @Override
    public <R> R accept(TranslatorNodeVisitor<? extends LexicalContext, R> visitor) {
        return visitor.enterClassElement(this);
    }

    public List<Expression> getDecorators() {
        return this.decorators;
    }

    public ClassElement setDecorators(List<Expression> decorators) {
        if (this.decorators == decorators) {
            return this;
        }
        return new ClassElement(this, this.kind, this.key, this.value, this.getter, this.setter, decorators, this.computed, this.isAnonymousFunctionDefinition, this.isStatic, this.isAutoAccessor);
    }

    @Override
    public ClassElement setGetter(FunctionNode get) {
        if (this.getter == get) {
            return this;
        }
        return new ClassElement(this, this.kind, this.key, this.value, get, this.setter, this.decorators, this.computed, this.isAnonymousFunctionDefinition, this.isStatic, this.isAutoAccessor);
    }

    public ClassElement setKey(Expression key) {
        if (this.key == key) {
            return this;
        }
        return new ClassElement(this, this.kind, key, this.value, this.getter, this.setter, this.decorators, this.computed, this.isAnonymousFunctionDefinition, this.isStatic, this.isAutoAccessor);
    }

    @Override
    public ClassElement setSetter(FunctionNode set2) {
        if (this.setter == set2) {
            return this;
        }
        return new ClassElement(this, this.kind, this.key, this.value, this.getter, set2, this.decorators, this.computed, this.isAnonymousFunctionDefinition, this.isStatic, this.isAutoAccessor);
    }

    @Override
    public ClassElement setValue(Expression value2) {
        if (this.value == value2) {
            return this;
        }
        return new ClassElement(this, this.kind, this.key, value2, this.getter, this.setter, this.decorators, this.computed, this.isAnonymousFunctionDefinition, this.isStatic, this.isAutoAccessor);
    }

    @Override
    public boolean isAccessor() {
        return (this.kind & 2) != 0;
    }

    public boolean isAutoAccessor() {
        return this.isAutoAccessor;
    }

    @Override
    public boolean isClassField() {
        return (this.kind & 4) != 0;
    }

    @Override
    public boolean isClassStaticBlock() {
        return (this.kind & 8) != 0;
    }

    public boolean isMethod() {
        return (this.kind & 1) != 0;
    }

    @Override
    public boolean isPrivate() {
        return this.key instanceof IdentNode && ((IdentNode)this.key).isPrivate();
    }

    @Override
    public boolean isStatic() {
        return this.isStatic;
    }

    @Override
    public void toString(StringBuilder sb, boolean printType) {
        if (this.decorators != null) {
            for (Expression decorator : this.decorators) {
                sb.append("@");
                decorator.toString(sb, printType);
                sb.append(" ");
            }
        }
        if (this.isStatic()) {
            sb.append("static ");
        }
        if (this.isAutoAccessor()) {
            sb.append("accessor ");
        }
        if (this.isMethod()) {
            this.toStringKey(sb, printType);
            ((FunctionNode)this.value).toStringTail(sb, printType);
        }
        if (this.isAccessor()) {
            if (this.getter != null) {
                sb.append("get ");
                this.toStringKey(sb, printType);
                this.getter.toStringTail(sb, printType);
            }
            if (this.setter != null) {
                sb.append("set ");
                this.toStringKey(sb, printType);
                this.setter.toStringTail(sb, printType);
            }
        }
        if (this.isClassField()) {
            this.toStringKey(sb, printType);
            if (this.value != null) {
                this.value.toString(sb, printType);
            }
        }
    }

    private void toStringKey(StringBuilder sb, boolean printType) {
        if (this.computed) {
            sb.append('[');
        }
        this.key.toString(sb, printType);
        if (this.computed) {
            sb.append(']');
        }
    }
}

