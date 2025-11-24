
package com.oracle.truffle.js.runtime.objects;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.JSOverloadedOperatorsObject;
import java.util.Arrays;
import java.util.List;
import org.graalvm.collections.EconomicMap;
import org.graalvm.collections.EconomicSet;

public class OperatorSet {
    public static final EconomicSet<TruffleString> BINARY_OPERATORS;
    public static final EconomicSet<TruffleString> UNARY_OPERATORS;
    public static final EconomicSet<TruffleString> ALL_OPERATORS;
    private static final EconomicSet<TruffleString> STRING_OPEN_OPERATORS;
    public static final OperatorSet NUMBER_OPERATOR_SET;
    public static final OperatorSet BIGINT_OPERATOR_SET;
    public static final OperatorSet STRING_OPERATOR_SET;
    private final int operatorCounter;
    private final EconomicMap<TruffleString, Object> selfOperatorDefinitions;
    private final EconomicMap<TruffleString, Object[]> leftOperatorDefinitions;
    private final EconomicMap<TruffleString, Object[]> rightOperatorDefinitions;
    private final EconomicSet<TruffleString> openOperators;

    public OperatorSet(int operatorCounter, EconomicSet<TruffleString> openOperators) {
        this(operatorCounter, null, null, null, openOperators);
    }

    public OperatorSet(int operatorCounter, EconomicMap<TruffleString, Object> selfOperatorDefinitions, EconomicMap<TruffleString, Object[]> leftOperatorDefinitions, EconomicMap<TruffleString, Object[]> rightOperatorDefinitions, EconomicSet<TruffleString> openOperators) {
        this.operatorCounter = operatorCounter;
        this.selfOperatorDefinitions = selfOperatorDefinitions;
        this.leftOperatorDefinitions = leftOperatorDefinitions;
        this.rightOperatorDefinitions = rightOperatorDefinitions;
        this.openOperators = openOperators;
    }

    @CompilerDirectives.TruffleBoundary
    public boolean isOperatorOpen(TruffleString operator) {
        return this.openOperators.contains(operator);
    }

    public int getOperatorCounter() {
        return this.operatorCounter;
    }

    public static OperatorSet getOperatorSet(Object object) {
        if (JSRuntime.isNumber(object)) {
            return NUMBER_OPERATOR_SET;
        }
        if (JSRuntime.isBigInt(object)) {
            return BIGINT_OPERATOR_SET;
        }
        if (Strings.isTString(object)) {
            return STRING_OPERATOR_SET;
        }
        assert (object instanceof JSOverloadedOperatorsObject);
        return ((JSOverloadedOperatorsObject)object).getOperatorSet();
    }

    @CompilerDirectives.TruffleBoundary
    public static Object getOperatorImplementation(JSOverloadedOperatorsObject operand, TruffleString operatorName) {
        return operand.getOperatorSet().selfOperatorDefinitions.get(operatorName);
    }

    @CompilerDirectives.TruffleBoundary
    public static Object getOperatorImplementation(Object left, Object right, TruffleString operatorName) {
        OperatorSet rightOperatorSet;
        if (JSRuntime.isNullOrUndefined(left) || JSRuntime.isNullOrUndefined(right)) {
            return null;
        }
        OperatorSet leftOperatorSet = OperatorSet.getOperatorSet(left);
        if (leftOperatorSet == (rightOperatorSet = OperatorSet.getOperatorSet(right))) {
            return leftOperatorSet.selfOperatorDefinitions.get(operatorName);
        }
        if (leftOperatorSet.operatorCounter < rightOperatorSet.operatorCounter) {
            Object[] rightOperatorDefinitions = (Object[])rightOperatorSet.rightOperatorDefinitions.get(operatorName);
            if (rightOperatorDefinitions != null) {
                return rightOperatorDefinitions[leftOperatorSet.operatorCounter];
            }
            return null;
        }
        assert (leftOperatorSet.operatorCounter > rightOperatorSet.operatorCounter);
        Object[] leftOperatorDefinitions = (Object[])leftOperatorSet.leftOperatorDefinitions.get(operatorName);
        if (leftOperatorDefinitions != null) {
            return leftOperatorDefinitions[rightOperatorSet.operatorCounter];
        }
        return null;
    }

    static {
        List<TruffleString> binaryOperators = Arrays.asList(Strings.SYMBOL_MINUS, Strings.SYMBOL_STAR, Strings.SLASH, Strings.SYMBOL_PERCENT, Strings.SYMBOL_STAR_STAR, Strings.SYMBOL_AMPERSAND, Strings.SYMBOL_CARET, Strings.SYMBOL_PIPE, Strings.ANGLE_BRACKET_OPEN_2, Strings.ANGLE_BRACKET_CLOSE_2, Strings.ANGLE_BRACKET_CLOSE_3, Strings.SYMBOL_EQUALS_EQUALS, Strings.SYMBOL_PLUS, Strings.ANGLE_BRACKET_OPEN);
        BINARY_OPERATORS = EconomicSet.create(binaryOperators.size());
        BINARY_OPERATORS.addAll(binaryOperators);
        List<TruffleString> unaryOperators = Arrays.asList(Strings.POS, Strings.NEG, Strings.SYMBOL_PLUS_PLUS, Strings.SYMBOL_MINUS_MINUS, Strings.SYMBOL_TILDE);
        UNARY_OPERATORS = EconomicSet.create(unaryOperators.size());
        UNARY_OPERATORS.addAll(unaryOperators);
        ALL_OPERATORS = EconomicSet.create(BINARY_OPERATORS.size() + UNARY_OPERATORS.size());
        ALL_OPERATORS.addAll(BINARY_OPERATORS);
        ALL_OPERATORS.addAll(UNARY_OPERATORS);
        STRING_OPEN_OPERATORS = EconomicSet.create(3);
        STRING_OPEN_OPERATORS.addAll(Arrays.asList(Strings.SYMBOL_PLUS, Strings.SYMBOL_EQUALS_EQUALS, Strings.ANGLE_BRACKET_OPEN));
        NUMBER_OPERATOR_SET = new OperatorSet(0, BINARY_OPERATORS);
        BIGINT_OPERATOR_SET = new OperatorSet(1, BINARY_OPERATORS);
        STRING_OPERATOR_SET = new OperatorSet(2, STRING_OPEN_OPERATORS);
    }
}

