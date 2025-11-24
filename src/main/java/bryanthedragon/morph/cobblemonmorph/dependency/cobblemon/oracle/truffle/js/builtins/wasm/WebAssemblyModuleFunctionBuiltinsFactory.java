
package com.oracle.truffle.js.builtins.wasm;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.builtins.wasm.WebAssemblyModuleFunctionBuiltins;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.wasm.JSWebAssemblyModuleObject;

@GeneratedBy(value=WebAssemblyModuleFunctionBuiltins.class)
public final class WebAssemblyModuleFunctionBuiltinsFactory {

    @GeneratedBy(value=WebAssemblyModuleFunctionBuiltins.WebAssemblyModuleCustomSectionsNode.class)
    public static final class WebAssemblyModuleCustomSectionsNodeGen
    extends WebAssemblyModuleFunctionBuiltins.WebAssemblyModuleCustomSectionsNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private WebAssemblyModuleCustomSectionsNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
            this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_, this.arguments1_};
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            if (state_0 != 0) {
                if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSWebAssemblyModuleObject) {
                    JSWebAssemblyModuleObject arguments0Value__ = (JSWebAssemblyModuleObject)arguments0Value_;
                    return this.customSectionsOfModule(arguments0Value__, arguments1Value_);
                }
                if ((state_0 & 2) != 0 && WebAssemblyModuleCustomSectionsNodeGen.fallbackGuard_(state_0, arguments0Value_, arguments1Value_)) {
                    return this.customSectionsOfOther(arguments0Value_, arguments1Value_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof JSWebAssemblyModuleObject) {
                JSWebAssemblyModuleObject arguments0Value_ = (JSWebAssemblyModuleObject)arguments0Value;
                this.state_0_ = state_0 |= 1;
                return this.customSectionsOfModule(arguments0Value_, arguments1Value);
            }
            this.state_0_ = state_0 |= 2;
            return this.customSectionsOfOther(arguments0Value, arguments1Value);
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
            Object[] data = new Object[3];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "customSectionsOfModule";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "customSectionsOfOther";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        private static boolean fallbackGuard_(int state_0, Object arguments0Value, Object arguments1Value) {
            return (state_0 & 1) != 0 || !(arguments0Value instanceof JSWebAssemblyModuleObject);
        }

        public static WebAssemblyModuleFunctionBuiltins.WebAssemblyModuleCustomSectionsNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new WebAssemblyModuleCustomSectionsNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=WebAssemblyModuleFunctionBuiltins.WebAssemblyModuleImportsNode.class)
    public static final class WebAssemblyModuleImportsNodeGen
    extends WebAssemblyModuleFunctionBuiltins.WebAssemblyModuleImportsNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private WebAssemblyModuleImportsNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_};
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSWebAssemblyModuleObject) {
                JSWebAssemblyModuleObject arguments0Value__ = (JSWebAssemblyModuleObject)arguments0Value_;
                return this.importsOfModule(arguments0Value__);
            }
            if ((state_0 & 2) != 0 && WebAssemblyModuleImportsNodeGen.fallbackGuard_(state_0, arguments0Value_)) {
                return this.importsOfOther(arguments0Value_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        private Object executeAndSpecialize(Object arguments0Value) {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof JSWebAssemblyModuleObject) {
                JSWebAssemblyModuleObject arguments0Value_ = (JSWebAssemblyModuleObject)arguments0Value;
                this.state_0_ = state_0 |= 1;
                return this.importsOfModule(arguments0Value_);
            }
            this.state_0_ = state_0 |= 2;
            return this.importsOfOther(arguments0Value);
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
            Object[] data = new Object[3];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "importsOfModule";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "importsOfOther";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        private static boolean fallbackGuard_(int state_0, Object arguments0Value) {
            return (state_0 & 1) != 0 || !(arguments0Value instanceof JSWebAssemblyModuleObject);
        }

        public static WebAssemblyModuleFunctionBuiltins.WebAssemblyModuleImportsNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new WebAssemblyModuleImportsNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=WebAssemblyModuleFunctionBuiltins.WebAssemblyModuleExportsNode.class)
    public static final class WebAssemblyModuleExportsNodeGen
    extends WebAssemblyModuleFunctionBuiltins.WebAssemblyModuleExportsNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private WebAssemblyModuleExportsNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_};
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSWebAssemblyModuleObject) {
                JSWebAssemblyModuleObject arguments0Value__ = (JSWebAssemblyModuleObject)arguments0Value_;
                return this.exportsOfModule(arguments0Value__);
            }
            if ((state_0 & 2) != 0 && WebAssemblyModuleExportsNodeGen.fallbackGuard_(state_0, arguments0Value_)) {
                return this.exportsOfOther(arguments0Value_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        private Object executeAndSpecialize(Object arguments0Value) {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof JSWebAssemblyModuleObject) {
                JSWebAssemblyModuleObject arguments0Value_ = (JSWebAssemblyModuleObject)arguments0Value;
                this.state_0_ = state_0 |= 1;
                return this.exportsOfModule(arguments0Value_);
            }
            this.state_0_ = state_0 |= 2;
            return this.exportsOfOther(arguments0Value);
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
            Object[] data = new Object[3];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "exportsOfModule";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "exportsOfOther";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        private static boolean fallbackGuard_(int state_0, Object arguments0Value) {
            return (state_0 & 1) != 0 || !(arguments0Value instanceof JSWebAssemblyModuleObject);
        }

        public static WebAssemblyModuleFunctionBuiltins.WebAssemblyModuleExportsNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new WebAssemblyModuleExportsNodeGen(context, builtin, arguments);
        }
    }
}

