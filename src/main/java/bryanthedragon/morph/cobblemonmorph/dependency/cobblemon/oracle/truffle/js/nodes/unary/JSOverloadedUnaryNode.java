
package com.oracle.truffle.js.nodes.unary;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.ReportPolymorphism;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.OperatorsBuiltins;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.nodes.unary.JSOverloadedUnaryNodeGen;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.builtins.JSOverloadedOperatorsObject;
import com.oracle.truffle.js.runtime.objects.OperatorSet;
import com.oracle.truffle.js.runtime.objects.Undefined;

@ImportStatic(value={OperatorSet.class})
public abstract class JSOverloadedUnaryNode
extends JavaScriptBaseNode {
    private final TruffleString overloadedOperatorName;

    protected JSOverloadedUnaryNode(TruffleString overloadedOperatorName) {
        this.overloadedOperatorName = overloadedOperatorName;
    }

    public static JSOverloadedUnaryNode create(TruffleString overloadedOperatorName) {
        return JSOverloadedUnaryNodeGen.create(overloadedOperatorName);
    }

    public abstract Object execute(Object var1);

    @Specialization(guards={"operand.matchesOperatorCounter(operatorCounter)"})
    protected Object doCached(JSOverloadedOperatorsObject operand, @Cached(value="operand.getOperatorCounter()") int operatorCounter, @Cached(value="getOperatorImplementation(operand, getOverloadedOperatorName())") Object operatorImplementation, @Cached(value="createCall()") JSFunctionCallNode callNode) {
        OperatorsBuiltins.checkOverloadedOperatorsAllowed(operand, this);
        return this.performOverloaded(callNode, operatorImplementation, operand);
    }

    @Specialization(replaces={"doCached"})
    @ReportPolymorphism.Megamorphic
    protected Object doGeneric(JSOverloadedOperatorsObject operand, @Cached(value="createCall()") JSFunctionCallNode callNode) {
        OperatorsBuiltins.checkOverloadedOperatorsAllowed(operand, this);
        Object operatorImplementation = OperatorSet.getOperatorImplementation(operand, this.getOverloadedOperatorName());
        return this.performOverloaded(callNode, operatorImplementation, operand);
    }

    private Object performOverloaded(JSFunctionCallNode callNode, Object operatorImplementation, Object operand) {
        if (operatorImplementation == null) {
            throw Errors.createTypeErrorNoOverloadFound(this.getOverloadedOperatorName(), this);
        }
        return callNode.executeCall(JSArguments.create(Undefined.instance, operatorImplementation, operand));
    }

    protected TruffleString getOverloadedOperatorName() {
        return this.overloadedOperatorName;
    }
}

