
package com.oracle.js.parser.ir;

import com.oracle.js.parser.TokenType;
import com.oracle.js.parser.ir.Expression;
import com.oracle.js.parser.ir.FunctionNode;
import com.oracle.js.parser.ir.IdentNode;
import com.oracle.js.parser.ir.LexicalContext;
import com.oracle.js.parser.ir.Node;
import com.oracle.js.parser.ir.PropertyKey;
import com.oracle.js.parser.ir.visitor.NodeVisitor;
import com.oracle.js.parser.ir.visitor.TranslatorNodeVisitor;
import com.oracle.truffle.api.strings.TruffleString;

public class PropertyNode
extends Node {
    protected final Expression key;
    protected final Expression value;
    protected final FunctionNode getter;
    protected final FunctionNode setter;
    protected final boolean isStatic;
    protected final boolean computed;
    private final boolean coverInitializedName;
    private final boolean proto;
    private final boolean classField;
    protected final boolean isAnonymousFunctionDefinition;

    protected PropertyNode(long token, int finish, Expression key, Expression value2, FunctionNode getter, FunctionNode setter, boolean isStatic, boolean computed, boolean isAnonymousFunctionDefinition) {
        this(token, finish, key, value2, getter, setter, isStatic, computed, false, false, false, isAnonymousFunctionDefinition);
    }

    public PropertyNode(long token, int finish, Expression key, Expression value2, FunctionNode getter, FunctionNode setter, boolean isStatic, boolean computed, boolean coverInitializedName, boolean proto) {
        this(token, finish, key, value2, getter, setter, isStatic, computed, coverInitializedName, proto, false, false);
    }

    public PropertyNode(long token, int finish, Expression key, Expression value2, FunctionNode getter, FunctionNode setter, boolean isStatic, boolean computed, boolean coverInitializedName, boolean proto, boolean classField, boolean isAnonymousFunctionDefinition) {
        super(token, finish);
        this.key = key;
        this.value = value2;
        this.getter = getter;
        this.setter = setter;
        this.isStatic = isStatic;
        this.computed = computed;
        this.coverInitializedName = coverInitializedName;
        this.proto = proto;
        this.classField = classField;
        this.isAnonymousFunctionDefinition = isAnonymousFunctionDefinition;
    }

    private PropertyNode(PropertyNode propertyNode, Expression key, Expression value2, FunctionNode getter, FunctionNode setter, boolean isStatic, boolean computed, boolean coverInitializedName, boolean proto) {
        super(propertyNode);
        this.key = key;
        this.value = value2;
        this.getter = getter;
        this.setter = setter;
        this.isStatic = isStatic;
        this.computed = computed;
        this.coverInitializedName = coverInitializedName;
        this.proto = proto;
        this.classField = propertyNode.classField;
        this.isAnonymousFunctionDefinition = propertyNode.isAnonymousFunctionDefinition;
    }

    public String getKeyName() {
        return this.key instanceof PropertyKey ? ((PropertyKey)((Object)this.key)).getPropertyName() : null;
    }

    public TruffleString getKeyNameTS() {
        return this.key instanceof PropertyKey ? ((PropertyKey)((Object)this.key)).getPropertyNameTS() : null;
    }

    @Override
    public Node accept(NodeVisitor<? extends LexicalContext> visitor) {
        if (visitor.enterPropertyNode(this)) {
            return visitor.leavePropertyNode(this.setKey(this.key == null ? null : (Expression)this.key.accept(visitor)).setValue(this.value == null ? null : (Expression)this.value.accept(visitor)).setGetter(this.getter == null ? null : (FunctionNode)this.getter.accept(visitor)).setSetter(this.setter == null ? null : (FunctionNode)this.setter.accept(visitor)));
        }
        return this;
    }

    @Override
    public <R> R accept(TranslatorNodeVisitor<? extends LexicalContext, R> visitor) {
        return visitor.enterPropertyNode(this);
    }

    @Override
    public void toString(StringBuilder sb, boolean printType) {
        if (this.isStatic) {
            sb.append("static ");
        }
        if (this.value != null) {
            if (this.isClassStaticBlock()) {
                sb.append("{}");
            } else if (this.value instanceof FunctionNode && ((FunctionNode)this.value).isMethod()) {
                if (((FunctionNode)this.value).isAsync()) {
                    sb.append("async ");
                }
                if (((FunctionNode)this.value).isGenerator()) {
                    sb.append('*');
                }
                this.toStringKey(sb, printType);
                ((FunctionNode)this.value).toStringTail(sb, printType);
            } else {
                this.toStringKey(sb, printType);
                sb.append(": ");
                this.value.toString(sb, printType);
            }
        } else if (this.isClassField()) {
            this.toStringKey(sb, printType);
        }
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

    private void toStringKey(StringBuilder sb, boolean printType) {
        if (this.computed) {
            sb.append('[');
        }
        this.key.toString(sb, printType);
        if (this.computed) {
            sb.append(']');
        }
    }

    public FunctionNode getGetter() {
        return this.getter;
    }

    public PropertyNode setGetter(FunctionNode getter) {
        if (this.getter == getter) {
            return this;
        }
        return new PropertyNode(this, this.key, this.value, getter, this.setter, this.isStatic, this.computed, this.coverInitializedName, this.proto);
    }

    public Expression getKey() {
        return this.key;
    }

    private PropertyNode setKey(Expression key) {
        if (this.key == key) {
            return this;
        }
        return new PropertyNode(this, key, this.value, this.getter, this.setter, this.isStatic, this.computed, this.coverInitializedName, this.proto);
    }

    public FunctionNode getSetter() {
        return this.setter;
    }

    public PropertyNode setSetter(FunctionNode setter) {
        if (this.setter == setter) {
            return this;
        }
        return new PropertyNode(this, this.key, this.value, this.getter, setter, this.isStatic, this.computed, this.coverInitializedName, this.proto);
    }

    public Expression getValue() {
        return this.value;
    }

    public PropertyNode setValue(Expression value2) {
        if (this.value == value2) {
            return this;
        }
        return new PropertyNode(this, this.key, value2, this.getter, this.setter, this.isStatic, this.computed, this.coverInitializedName, this.proto);
    }

    public boolean isStatic() {
        return this.isStatic;
    }

    public boolean isComputed() {
        return this.computed;
    }

    public boolean isCoverInitializedName() {
        return this.coverInitializedName;
    }

    public boolean isProto() {
        return this.proto;
    }

    public boolean isRest() {
        return this.key != null && this.key.isTokenType(TokenType.SPREAD_OBJECT);
    }

    public boolean isClassField() {
        return this.classField;
    }

    public boolean isAnonymousFunctionDefinition() {
        return this.isAnonymousFunctionDefinition;
    }

    public boolean isPrivate() {
        return this.key instanceof IdentNode && ((IdentNode)this.key).isPrivate();
    }

    public String getPrivateName() {
        assert (this.isPrivate());
        return ((IdentNode)this.key).getName();
    }

    public TruffleString getPrivateNameTS() {
        assert (this.isPrivate());
        return ((IdentNode)this.key).getNameTS();
    }

    public boolean isAccessor() {
        return this.getter != null || this.setter != null;
    }

    public boolean isClassStaticBlock() {
        return this.isStatic() && this.getKey() == null;
    }
}

