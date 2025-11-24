
package com.oracle.truffle.js.nodes.function;

import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.runtime.Strings;

public interface FunctionNameHolder {
    public TruffleString getFunctionName();

    public void setFunctionName(TruffleString var1);

    default public boolean isAnonymous() {
        return Strings.isEmpty(this.getFunctionName());
    }

    public static interface Delegate
    extends FunctionNameHolder {
        public FunctionNameHolder getFunctionNameHolder();

        @Override
        default public TruffleString getFunctionName() {
            return this.getFunctionNameHolder().getFunctionName();
        }

        @Override
        default public void setFunctionName(TruffleString name) {
            this.getFunctionNameHolder().setFunctionName(name);
        }

        @Override
        default public boolean isAnonymous() {
            return this.getFunctionNameHolder().isAnonymous();
        }
    }
}

