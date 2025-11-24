
package com.oracle.truffle.js.nodes.binary;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.ReportPolymorphism;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.binary.JSAddNode;
import com.oracle.truffle.js.nodes.binary.JSOverloadedBinaryNodeGen;
import com.oracle.truffle.js.nodes.cast.JSToNumericNode;
import com.oracle.truffle.js.nodes.cast.JSToOperandNode;
import com.oracle.truffle.js.nodes.cast.JSToPrimitiveNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.JSOverloadedOperatorsObject;
import com.oracle.truffle.js.runtime.objects.OperatorSet;
import com.oracle.truffle.js.runtime.objects.Undefined;

public abstract class JSOverloadedBinaryNode
extends JavaScriptBaseNode {
    private final TruffleString overloadedOperatorName;
    private final boolean numeric;
    private final JSToPrimitiveNode.Hint hint;
    private final boolean leftToRight;

    protected JSOverloadedBinaryNode(TruffleString overloadedOperatorName, boolean numeric, JSToPrimitiveNode.Hint hint, boolean leftToRight) {
        this.overloadedOperatorName = overloadedOperatorName;
        this.numeric = numeric;
        this.hint = hint;
        this.leftToRight = leftToRight;
    }

    public static JSOverloadedBinaryNode create(TruffleString overloadedOperatorName, JSToPrimitiveNode.Hint hint) {
        return JSOverloadedBinaryNodeGen.create(overloadedOperatorName, false, hint, true);
    }

    public static JSOverloadedBinaryNode createHintDefault(TruffleString overloadedOperatorName) {
        return JSOverloadedBinaryNodeGen.create(overloadedOperatorName, false, JSToPrimitiveNode.Hint.Default, true);
    }

    public static JSOverloadedBinaryNode createHintNumber(TruffleString overloadedOperatorName) {
        return JSOverloadedBinaryNodeGen.create(overloadedOperatorName, false, JSToPrimitiveNode.Hint.Number, true);
    }

    public static JSOverloadedBinaryNode createHintNumberLeftToRight(TruffleString overloadedOperatorName) {
        return JSOverloadedBinaryNodeGen.create(overloadedOperatorName, false, JSToPrimitiveNode.Hint.Number, true);
    }

    public static JSOverloadedBinaryNode createHintNumberRightToLeft(TruffleString overloadedOperatorName) {
        return JSOverloadedBinaryNodeGen.create(overloadedOperatorName, false, JSToPrimitiveNode.Hint.Number, false);
    }

    public static JSOverloadedBinaryNode createHintString(TruffleString overloadedOperatorName) {
        return JSOverloadedBinaryNodeGen.create(overloadedOperatorName, false, JSToPrimitiveNode.Hint.String, true);
    }

    public static JSOverloadedBinaryNode createNumeric(TruffleString overloadedOperatorName) {
        return JSOverloadedBinaryNodeGen.create(overloadedOperatorName, true, null, true);
    }

    public abstract Object execute(Object var1, Object var2);

    @Specialization(guards={"!isNumeric()", "!isAddition()"})
    protected Object doToOperandGeneric(Object left, Object right, @Cached(value="create(getHint(), !isEquality())") JSToOperandNode toOperandLeftNode, @Cached(value="create(getHint(), !isEquality())") JSToOperandNode toOperandRightNode, @Cached(value="create(getOverloadedOperatorName())") DispatchBinaryOperatorNode dispatchBinaryOperatorNode) {
        Object rightOperand;
        Object leftOperand;
        if (this.leftToRight) {
            leftOperand = toOperandLeftNode.execute(left);
            rightOperand = toOperandRightNode.execute(right);
        } else {
            rightOperand = toOperandRightNode.execute(right);
            leftOperand = toOperandLeftNode.execute(left);
        }
        return dispatchBinaryOperatorNode.execute(leftOperand, rightOperand);
    }

    @Specialization(guards={"!isNumeric()", "isAddition()"})
    protected Object doToOperandAddition(Object left, Object right, @Cached(value="create(getHint())") JSToOperandNode toOperandLeftNode, @Cached(value="create(getHint())") JSToOperandNode toOperandRightNode, @Cached(value="create(getOverloadedOperatorName())") DispatchBinaryOperatorNode dispatchBinaryOperatorNode, @Cached(value="create()") JSToStringNode toStringLeftNode, @Cached(value="create()") JSToStringNode toStringRightNode, @Cached(value="createBinaryProfile()") ConditionProfile leftStringProfile, @Cached(value="createBinaryProfile()") ConditionProfile rightStringProfile, @Cached(value="createUnoptimized()") JSAddNode addNode) {
        Object rightOperand;
        Object leftOperand;
        if (this.leftToRight) {
            leftOperand = toOperandLeftNode.execute(left);
            rightOperand = toOperandRightNode.execute(right);
        } else {
            rightOperand = toOperandRightNode.execute(right);
            leftOperand = toOperandLeftNode.execute(left);
        }
        if (leftStringProfile.profile(JSGuards.isString(leftOperand))) {
            return addNode.execute(leftOperand, toStringRightNode.executeString(rightOperand));
        }
        if (rightStringProfile.profile(JSGuards.isString(rightOperand))) {
            return addNode.execute(toStringLeftNode.executeString(leftOperand), rightOperand);
        }
        return dispatchBinaryOperatorNode.execute(leftOperand, rightOperand);
    }

    @Specialization(guards={"isNumeric()"})
    protected Object doToNumericOperand(Object left, Object right, @Cached(value="create(true)") JSToNumericNode toNumericOperandLeftNode, @Cached(value="create(true)") JSToNumericNode toNumericOperandRightNode, @Cached(value="create(getOverloadedOperatorName())") DispatchBinaryOperatorNode dispatchBinaryOperatorNode) {
        Object rightOperand;
        Object leftOperand;
        if (this.leftToRight) {
            leftOperand = toNumericOperandLeftNode.execute(left);
            rightOperand = toNumericOperandRightNode.execute(right);
        } else {
            rightOperand = toNumericOperandRightNode.execute(right);
            leftOperand = toNumericOperandLeftNode.execute(left);
        }
        return dispatchBinaryOperatorNode.execute(leftOperand, rightOperand);
    }

    protected TruffleString getOverloadedOperatorName() {
        return this.overloadedOperatorName;
    }

    protected boolean isNumeric() {
        return this.numeric;
    }

    protected JSToPrimitiveNode.Hint getHint() {
        return this.hint;
    }

    protected boolean isAddition() {
        return Strings.equals(Strings.SYMBOL_PLUS, this.overloadedOperatorName);
    }

    protected boolean isEquality() {
        return Strings.equals(Strings.SYMBOL_EQUALS_EQUALS, this.overloadedOperatorName);
    }

    @ImportStatic(value={OperatorSet.class})
    public static abstract class DispatchBinaryOperatorNode
    extends JavaScriptBaseNode {
        private final TruffleString overloadedOperatorName;

        protected DispatchBinaryOperatorNode(TruffleString overloadedOperatorName) {
            this.overloadedOperatorName = overloadedOperatorName;
        }

        public static DispatchBinaryOperatorNode create(TruffleString overloadedOperatorName) {
            return JSOverloadedBinaryNodeGen.DispatchBinaryOperatorNodeGen.create(overloadedOperatorName);
        }

        protected abstract Object execute(Object var1, Object var2);

        @Specialization(guards={"left.matchesOperatorCounter(leftOperatorCounter)", "right.matchesOperatorCounter(rightOperatorCounter)"})
        protected Object doOverloadedOverloaded(JSOverloadedOperatorsObject left, JSOverloadedOperatorsObject right, @Cached(value="left.getOperatorCounter()") int leftOperatorCounter, @Cached(value="right.getOperatorCounter()") int rightOperatorCounter, @Cached(value="getOperatorImplementation(left, right, getOverloadedOperatorName())") Object operatorImplementation, @Cached(value="createCall()") JSFunctionCallNode callNode) {
            return this.performOverloaded(callNode, operatorImplementation, left, right);
        }

        @Specialization(guards={"left.matchesOperatorCounter(leftOperatorCounter)", "isNumber(right)"})
        protected Object doOverloadedNumber(JSOverloadedOperatorsObject left, Object right, @Cached(value="left.getOperatorCounter()") int leftOperatorCounter, @Cached(value="getOperatorImplementation(left, right, getOverloadedOperatorName())") Object operatorImplementation, @Cached(value="createCall()") JSFunctionCallNode callNode) {
            return this.performOverloaded(callNode, operatorImplementation, left, right);
        }

        @Specialization(guards={"left.matchesOperatorCounter(leftOperatorCounter)"})
        protected Object doOverloadedBigInt(JSOverloadedOperatorsObject left, BigInt right, @Cached(value="left.getOperatorCounter()") int leftOperatorCounter, @Cached(value="getOperatorImplementation(left, right, getOverloadedOperatorName())") Object operatorImplementation, @Cached(value="createCall()") JSFunctionCallNode callNode) {
            return this.performOverloaded(callNode, operatorImplementation, left, right);
        }

        @Specialization(guards={"left.matchesOperatorCounter(leftOperatorCounter)", "isString(right)", "!isAddition()"})
        protected Object doOverloadedString(JSOverloadedOperatorsObject left, Object right, @Cached(value="left.getOperatorCounter()") int leftOperatorCounter, @Cached(value="getOperatorImplementation(left, right, getOverloadedOperatorName())") Object operatorImplementation, @Cached(value="createCall()") JSFunctionCallNode callNode) {
            return this.performOverloaded(callNode, operatorImplementation, left, right);
        }

        @Specialization(guards={"isNullOrUndefined(right)"})
        protected Object doOverloadedNullish(JSOverloadedOperatorsObject left, Object right) {
            return this.missingImplementation();
        }

        @Specialization(guards={"right.matchesOperatorCounter(rightOperatorCounter)", "isNumber(left)"})
        protected Object doNumberOverloaded(Object left, JSOverloadedOperatorsObject right, @Cached(value="right.getOperatorCounter()") int rightOperatorCounter, @Cached(value="getOperatorImplementation(left, right, getOverloadedOperatorName())") Object operatorImplementation, @Cached(value="createCall()") JSFunctionCallNode callNode) {
            return this.performOverloaded(callNode, operatorImplementation, left, right);
        }

        @Specialization(guards={"right.matchesOperatorCounter(rightOperatorCounter)"})
        protected Object doBigIntOverloaded(BigInt left, JSOverloadedOperatorsObject right, @Cached(value="right.getOperatorCounter()") int rightOperatorCounter, @Cached(value="getOperatorImplementation(left, right, getOverloadedOperatorName())") Object operatorImplementation, @Cached(value="createCall()") JSFunctionCallNode callNode) {
            return this.performOverloaded(callNode, operatorImplementation, left, right);
        }

        @Specialization(guards={"right.matchesOperatorCounter(rightOperatorCounter)", "isString(left)", "!isAddition()"})
        protected Object doStringOverloaded(Object left, JSOverloadedOperatorsObject right, @Cached(value="right.getOperatorCounter()") int rightOperatorCounter, @Cached(value="getOperatorImplementation(left, right, getOverloadedOperatorName())") Object operatorImplementation, @Cached(value="createCall()") JSFunctionCallNode callNode) {
            return this.performOverloaded(callNode, operatorImplementation, left, right);
        }

        @Specialization(guards={"isNullOrUndefined(left)"})
        protected Object doNullishOverloaded(Object left, JSOverloadedOperatorsObject right) {
            return this.missingImplementation();
        }

        @Specialization(replaces={"doOverloadedOverloaded", "doOverloadedNumber", "doOverloadedBigInt", "doOverloadedString", "doNumberOverloaded", "doBigIntOverloaded", "doStringOverloaded"})
        @ReportPolymorphism.Megamorphic
        protected Object doGeneric(Object left, Object right, @Cached(value="createCall()") JSFunctionCallNode callNode) {
            Object operatorImplementation = OperatorSet.getOperatorImplementation(left, right, this.getOverloadedOperatorName());
            return this.performOverloaded(callNode, operatorImplementation, left, right);
        }

        private boolean missingImplementation() {
            if (this.isEquality()) {
                return false;
            }
            throw Errors.createTypeErrorNoOverloadFound(this.getOverloadedOperatorName(), this);
        }

        private Object performOverloaded(JSFunctionCallNode callNode, Object operatorImplementation, Object left, Object right) {
            if (operatorImplementation == null) {
                return this.missingImplementation();
            }
            return callNode.executeCall(JSArguments.create(Undefined.instance, operatorImplementation, left, right));
        }

        protected TruffleString getOverloadedOperatorName() {
            return this.overloadedOperatorName;
        }

        protected boolean isAddition() {
            return Strings.equals(Strings.SYMBOL_PLUS, this.overloadedOperatorName);
        }

        protected boolean isEquality() {
            return Strings.equals(Strings.SYMBOL_EQUALS_EQUALS, this.overloadedOperatorName);
        }
    }
}

