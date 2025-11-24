
package com.oracle.truffle.js.nodes.control;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.nodes.control.TryCatchNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSException;
import com.oracle.truffle.js.runtime.UserScriptException;

@GeneratedBy(value=TryCatchNode.class)
public final class TryCatchNodeFactory {

    @GeneratedBy(value=TryCatchNode.GetErrorObjectNode.class)
    public static final class GetErrorObjectNodeGen
    extends TryCatchNode.GetErrorObjectNode
    implements Introspection.Provider {
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private GetErrorObjectNodeGen(JSContext context) {
            super(context);
        }

        @Override
        public Object execute(Throwable arg0Value) {
            int state_0 = this.state_0_;
            if ((state_0 & 1) != 0 && arg0Value instanceof JSException) {
                JSException arg0Value_ = (JSException)arg0Value;
                return this.doJSException(arg0Value_);
            }
            if ((state_0 & 2) != 0 && arg0Value instanceof UserScriptException) {
                UserScriptException arg0Value_ = (UserScriptException)arg0Value;
                return TryCatchNode.GetErrorObjectNode.doUserScriptException(arg0Value_);
            }
            if ((state_0 & 4) != 0 && arg0Value instanceof StackOverflowError) {
                StackOverflowError arg0Value_ = (StackOverflowError)arg0Value;
                return this.doStackOverflowError(arg0Value_);
            }
            if ((state_0 & 8) != 0 && GetErrorObjectNodeGen.fallbackGuard_(state_0, arg0Value)) {
                return TryCatchNode.GetErrorObjectNode.doOther(arg0Value);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value);
        }

        private Object executeAndSpecialize(Throwable arg0Value) {
            int state_0 = this.state_0_;
            if (arg0Value instanceof JSException) {
                JSException arg0Value_ = (JSException)arg0Value;
                this.state_0_ = state_0 |= 1;
                return this.doJSException(arg0Value_);
            }
            if (arg0Value instanceof UserScriptException) {
                UserScriptException arg0Value_ = (UserScriptException)arg0Value;
                this.state_0_ = state_0 |= 2;
                return TryCatchNode.GetErrorObjectNode.doUserScriptException(arg0Value_);
            }
            if (arg0Value instanceof StackOverflowError) {
                StackOverflowError arg0Value_ = (StackOverflowError)arg0Value;
                this.state_0_ = state_0 |= 4;
                return this.doStackOverflowError(arg0Value_);
            }
            this.state_0_ = state_0 |= 8;
            return TryCatchNode.GetErrorObjectNode.doOther(arg0Value);
        }

        @Override
        public NodeCost getCost() {
            int state_0 = this.state_0_;
            if (state_0 == 0) {
                return NodeCost.UNINITIALIZED;
            }
            if ((state_0 & state_0 - 1) == 0) {
                return NodeCost.MONOMORPHIC;
            }
            return NodeCost.POLYMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            Object[] data = new Object[5];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "doJSException";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "doUserScriptException";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            s = new Object[3];
            s[0] = "doStackOverflowError";
            s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[3] = s;
            s = new Object[3];
            s[0] = "doOther";
            s[1] = (state_0 & 8) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[4] = s;
            return Introspection.Provider.create(data);
        }

        private static boolean fallbackGuard_(int state_0, Throwable arg0Value) {
            if ((state_0 & 1) == 0 && arg0Value instanceof JSException) {
                return false;
            }
            if ((state_0 & 2) == 0 && arg0Value instanceof UserScriptException) {
                return false;
            }
            return (state_0 & 4) != 0 || !(arg0Value instanceof StackOverflowError);
        }

        public static TryCatchNode.GetErrorObjectNode create(JSContext context) {
            return new GetErrorObjectNodeGen(context);
        }
    }
}

