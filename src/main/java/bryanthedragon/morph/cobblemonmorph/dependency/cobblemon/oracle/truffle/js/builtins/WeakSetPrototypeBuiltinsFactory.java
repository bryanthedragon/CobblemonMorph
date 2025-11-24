
package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.builtins.WeakSetPrototypeBuiltins;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

@GeneratedBy(value=WeakSetPrototypeBuiltins.class)
public final class WeakSetPrototypeBuiltinsFactory {

    @GeneratedBy(value=WeakSetPrototypeBuiltins.JSWeakSetHasNode.class)
    public static final class JSWeakSetHasNodeGen
    extends WeakSetPrototypeBuiltins.JSWeakSetHasNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private JSWeakSetHasNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSDynamicObject) {
                JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
                if (arguments1Value_ instanceof JSDynamicObject) {
                    JSDynamicObject arguments1Value__ = (JSDynamicObject)arguments1Value_;
                    if (JSGuards.isJSWeakSet(arguments0Value__) && JSGuards.isJSObject(arguments1Value__)) {
                        return WeakSetPrototypeBuiltins.JSWeakSetHasNode.has(arguments0Value__, arguments1Value__);
                    }
                }
            }
            if ((state_0 & 6) != 0) {
                if ((state_0 & 2) != 0 && JSGuards.isJSWeakSet(arguments0Value_) && !JSGuards.isJSObject(arguments1Value_)) {
                    return WeakSetPrototypeBuiltins.JSWeakSetHasNode.hasNonObjectKey(arguments0Value_, arguments1Value_);
                }
                if ((state_0 & 4) != 0 && !JSGuards.isJSWeakSet(arguments0Value_)) {
                    return WeakSetPrototypeBuiltins.JSWeakSetHasNode.notWeakSet(arguments0Value_, arguments1Value_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        @Override
        public boolean executeBoolean(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSDynamicObject) {
                JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
                if (arguments1Value_ instanceof JSDynamicObject) {
                    JSDynamicObject arguments1Value__ = (JSDynamicObject)arguments1Value_;
                    if (JSGuards.isJSWeakSet(arguments0Value__) && JSGuards.isJSObject(arguments1Value__)) {
                        return WeakSetPrototypeBuiltins.JSWeakSetHasNode.has(arguments0Value__, arguments1Value__);
                    }
                }
            }
            if ((state_0 & 6) != 0) {
                if ((state_0 & 2) != 0 && JSGuards.isJSWeakSet(arguments0Value_) && !JSGuards.isJSObject(arguments1Value_)) {
                    return WeakSetPrototypeBuiltins.JSWeakSetHasNode.hasNonObjectKey(arguments0Value_, arguments1Value_);
                }
                if ((state_0 & 4) != 0 && !JSGuards.isJSWeakSet(arguments0Value_)) {
                    return WeakSetPrototypeBuiltins.JSWeakSetHasNode.notWeakSet(arguments0Value_, arguments1Value_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.executeBoolean(frameValue);
        }

        private boolean executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof JSDynamicObject) {
                JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
                if (arguments1Value instanceof JSDynamicObject) {
                    JSDynamicObject arguments1Value_ = (JSDynamicObject)arguments1Value;
                    if (JSGuards.isJSWeakSet(arguments0Value_) && JSGuards.isJSObject(arguments1Value_)) {
                        this.state_0_ = state_0 |= 1;
                        return WeakSetPrototypeBuiltins.JSWeakSetHasNode.has(arguments0Value_, arguments1Value_);
                    }
                }
            }
            if (JSGuards.isJSWeakSet(arguments0Value) && !JSGuards.isJSObject(arguments1Value)) {
                this.state_0_ = state_0 |= 2;
                return WeakSetPrototypeBuiltins.JSWeakSetHasNode.hasNonObjectKey(arguments0Value, arguments1Value);
            }
            if (!JSGuards.isJSWeakSet(arguments0Value)) {
                this.state_0_ = state_0 |= 4;
                return WeakSetPrototypeBuiltins.JSWeakSetHasNode.notWeakSet(arguments0Value, arguments1Value);
            }
            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_}, arguments0Value, arguments1Value);
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
            Object[] data = new Object[4];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "has";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "hasNonObjectKey";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            s = new Object[3];
            s[0] = "notWeakSet";
            s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[3] = s;
            return Introspection.Provider.create(data);
        }

        public static WeakSetPrototypeBuiltins.JSWeakSetHasNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSWeakSetHasNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=WeakSetPrototypeBuiltins.JSWeakSetAddNode.class)
    public static final class JSWeakSetAddNodeGen
    extends WeakSetPrototypeBuiltins.JSWeakSetAddNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private JSWeakSetAddNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSDynamicObject) {
                JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
                if (arguments1Value_ instanceof JSDynamicObject) {
                    JSDynamicObject arguments1Value__ = (JSDynamicObject)arguments1Value_;
                    if (JSGuards.isJSWeakSet(arguments0Value__) && JSGuards.isJSObject(arguments1Value__)) {
                        return WeakSetPrototypeBuiltins.JSWeakSetAddNode.add(arguments0Value__, arguments1Value__);
                    }
                }
            }
            if ((state_0 & 6) != 0) {
                if ((state_0 & 2) != 0 && JSGuards.isJSWeakSet(arguments0Value_) && !JSGuards.isJSObject(arguments1Value_)) {
                    return WeakSetPrototypeBuiltins.JSWeakSetAddNode.addNonObjectKey(arguments0Value_, arguments1Value_);
                }
                if ((state_0 & 4) != 0 && !JSGuards.isJSWeakSet(arguments0Value_)) {
                    return WeakSetPrototypeBuiltins.JSWeakSetAddNode.notWeakSet(arguments0Value_, arguments1Value_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof JSDynamicObject) {
                JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
                if (arguments1Value instanceof JSDynamicObject) {
                    JSDynamicObject arguments1Value_ = (JSDynamicObject)arguments1Value;
                    if (JSGuards.isJSWeakSet(arguments0Value_) && JSGuards.isJSObject(arguments1Value_)) {
                        this.state_0_ = state_0 |= 1;
                        return WeakSetPrototypeBuiltins.JSWeakSetAddNode.add(arguments0Value_, arguments1Value_);
                    }
                }
            }
            if (JSGuards.isJSWeakSet(arguments0Value) && !JSGuards.isJSObject(arguments1Value)) {
                this.state_0_ = state_0 |= 2;
                return WeakSetPrototypeBuiltins.JSWeakSetAddNode.addNonObjectKey(arguments0Value, arguments1Value);
            }
            if (!JSGuards.isJSWeakSet(arguments0Value)) {
                this.state_0_ = state_0 |= 4;
                return WeakSetPrototypeBuiltins.JSWeakSetAddNode.notWeakSet(arguments0Value, arguments1Value);
            }
            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_}, arguments0Value, arguments1Value);
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
            Object[] data = new Object[4];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "add";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "addNonObjectKey";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            s = new Object[3];
            s[0] = "notWeakSet";
            s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[3] = s;
            return Introspection.Provider.create(data);
        }

        public static WeakSetPrototypeBuiltins.JSWeakSetAddNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSWeakSetAddNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=WeakSetPrototypeBuiltins.JSWeakSetDeleteNode.class)
    public static final class JSWeakSetDeleteNodeGen
    extends WeakSetPrototypeBuiltins.JSWeakSetDeleteNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private JSWeakSetDeleteNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSDynamicObject) {
                JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
                if (arguments1Value_ instanceof JSDynamicObject) {
                    JSDynamicObject arguments1Value__ = (JSDynamicObject)arguments1Value_;
                    if (JSGuards.isJSWeakSet(arguments0Value__) && JSGuards.isJSObject(arguments1Value__)) {
                        return WeakSetPrototypeBuiltins.JSWeakSetDeleteNode.delete(arguments0Value__, arguments1Value__);
                    }
                }
            }
            if ((state_0 & 6) != 0) {
                if ((state_0 & 2) != 0 && JSGuards.isJSWeakSet(arguments0Value_) && !JSGuards.isJSObject(arguments1Value_)) {
                    return WeakSetPrototypeBuiltins.JSWeakSetDeleteNode.deleteNonObjectKey(arguments0Value_, arguments1Value_);
                }
                if ((state_0 & 4) != 0 && !JSGuards.isJSWeakSet(arguments0Value_)) {
                    return WeakSetPrototypeBuiltins.JSWeakSetDeleteNode.notWeakSet(arguments0Value_, arguments1Value_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        @Override
        public boolean executeBoolean(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSDynamicObject) {
                JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
                if (arguments1Value_ instanceof JSDynamicObject) {
                    JSDynamicObject arguments1Value__ = (JSDynamicObject)arguments1Value_;
                    if (JSGuards.isJSWeakSet(arguments0Value__) && JSGuards.isJSObject(arguments1Value__)) {
                        return WeakSetPrototypeBuiltins.JSWeakSetDeleteNode.delete(arguments0Value__, arguments1Value__);
                    }
                }
            }
            if ((state_0 & 6) != 0) {
                if ((state_0 & 2) != 0 && JSGuards.isJSWeakSet(arguments0Value_) && !JSGuards.isJSObject(arguments1Value_)) {
                    return WeakSetPrototypeBuiltins.JSWeakSetDeleteNode.deleteNonObjectKey(arguments0Value_, arguments1Value_);
                }
                if ((state_0 & 4) != 0 && !JSGuards.isJSWeakSet(arguments0Value_)) {
                    return WeakSetPrototypeBuiltins.JSWeakSetDeleteNode.notWeakSet(arguments0Value_, arguments1Value_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.executeBoolean(frameValue);
        }

        private boolean executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof JSDynamicObject) {
                JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
                if (arguments1Value instanceof JSDynamicObject) {
                    JSDynamicObject arguments1Value_ = (JSDynamicObject)arguments1Value;
                    if (JSGuards.isJSWeakSet(arguments0Value_) && JSGuards.isJSObject(arguments1Value_)) {
                        this.state_0_ = state_0 |= 1;
                        return WeakSetPrototypeBuiltins.JSWeakSetDeleteNode.delete(arguments0Value_, arguments1Value_);
                    }
                }
            }
            if (JSGuards.isJSWeakSet(arguments0Value) && !JSGuards.isJSObject(arguments1Value)) {
                this.state_0_ = state_0 |= 2;
                return WeakSetPrototypeBuiltins.JSWeakSetDeleteNode.deleteNonObjectKey(arguments0Value, arguments1Value);
            }
            if (!JSGuards.isJSWeakSet(arguments0Value)) {
                this.state_0_ = state_0 |= 4;
                return WeakSetPrototypeBuiltins.JSWeakSetDeleteNode.notWeakSet(arguments0Value, arguments1Value);
            }
            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_}, arguments0Value, arguments1Value);
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
            Object[] data = new Object[4];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "delete";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "deleteNonObjectKey";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            s = new Object[3];
            s[0] = "notWeakSet";
            s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[3] = s;
            return Introspection.Provider.create(data);
        }

        public static WeakSetPrototypeBuiltins.JSWeakSetDeleteNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSWeakSetDeleteNodeGen(context, builtin, arguments);
        }
    }
}

