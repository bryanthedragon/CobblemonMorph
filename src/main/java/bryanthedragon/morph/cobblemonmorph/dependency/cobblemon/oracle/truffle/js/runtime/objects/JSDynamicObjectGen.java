
package com.oracle.truffle.js.runtime.objects;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.DynamicDispatchLibrary;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.utilities.TriState;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

@GeneratedBy(value=JSDynamicObject.class)
public final class JSDynamicObjectGen {
    private static final LibraryFactory<DynamicDispatchLibrary> DYNAMIC_DISPATCH_LIBRARY_ = LibraryFactory.resolve(DynamicDispatchLibrary.class);

    private JSDynamicObjectGen() {
    }

    static {
        LibraryExport.register(JSDynamicObject.class, new InteropLibraryExports());
    }

    @GeneratedBy(value=JSDynamicObject.class)
    public static class InteropLibraryExports
    extends LibraryExport<InteropLibrary> {
        private InteropLibraryExports() {
            super(InteropLibrary.class, JSDynamicObject.class, false, false, 0);
        }

        @Override
        protected InteropLibrary createUncached(Object receiver) {
            assert (receiver instanceof JSDynamicObject);
            Uncached uncached = new Uncached(receiver);
            return uncached;
        }

        @Override
        protected InteropLibrary createCached(Object receiver) {
            assert (receiver instanceof JSDynamicObject);
            return new Cached(receiver);
        }

        @GeneratedBy(value=JSDynamicObject.class)
        public static class Uncached
        extends InteropLibrary {
            private final Class<? extends JSDynamicObject> receiverClass_;

            protected Uncached(Object receiver) {
                this.receiverClass_ = ((JSDynamicObject)receiver).getClass();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean accepts(Object receiver) {
                assert (receiver.getClass() != this.receiverClass_ || DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null) : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";
                return CompilerDirectives.isExact(receiver, this.receiverClass_);
            }

            @Override
            public final boolean isAdoptable() {
                return false;
            }

            @Override
            public final NodeCost getCost() {
                return NodeCost.MEGAMORPHIC;
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public TriState isIdenticalOrUndefined(Object arg0Value_, Object arg1Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                JSDynamicObject arg0Value = (JSDynamicObject)arg0Value_;
                if (arg1Value instanceof JSDynamicObject) {
                    JSDynamicObject arg1Value_ = (JSDynamicObject)arg1Value;
                    return JSDynamicObject.IsIdenticalOrUndefined.doHostObject(arg0Value, arg1Value_);
                }
                return JSDynamicObject.IsIdenticalOrUndefined.doOther(arg0Value, arg1Value);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public int identityHashCode(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((JSDynamicObject)receiver).identityHashCode();
            }
        }

        @GeneratedBy(value=JSDynamicObject.class)
        public static class Cached
        extends InteropLibrary {
            private final Class<? extends JSDynamicObject> receiverClass_;
            @CompilerDirectives.CompilationFinal
            private int state_0_;

            protected Cached(Object receiver) {
                JSDynamicObject castReceiver = (JSDynamicObject)receiver;
                this.receiverClass_ = castReceiver.getClass();
            }

            @Override
            public boolean accepts(Object receiver) {
                assert (receiver.getClass() != this.receiverClass_ || DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null) : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";
                return CompilerDirectives.isExact(receiver, this.receiverClass_);
            }

            @Override
            protected TriState isIdenticalOrUndefined(Object arg0Value_, Object arg1Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                JSDynamicObject arg0Value = CompilerDirectives.castExact(arg0Value_, this.receiverClass_);
                int state_0 = this.state_0_;
                if (state_0 != 0) {
                    if ((state_0 & 1) != 0 && arg1Value instanceof JSDynamicObject) {
                        JSDynamicObject arg1Value_ = (JSDynamicObject)arg1Value;
                        return JSDynamicObject.IsIdenticalOrUndefined.doHostObject(arg0Value, arg1Value_);
                    }
                    if ((state_0 & 2) != 0 && Cached.fallbackGuard_(state_0, arg0Value, arg1Value)) {
                        return JSDynamicObject.IsIdenticalOrUndefined.doOther(arg0Value, arg1Value);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(arg0Value, arg1Value);
            }

            private TriState executeAndSpecialize(JSDynamicObject arg0Value, Object arg1Value) {
                int state_0 = this.state_0_;
                if (arg1Value instanceof JSDynamicObject) {
                    JSDynamicObject arg1Value_ = (JSDynamicObject)arg1Value;
                    this.state_0_ = state_0 |= 1;
                    return JSDynamicObject.IsIdenticalOrUndefined.doHostObject(arg0Value, arg1Value_);
                }
                this.state_0_ = state_0 |= 2;
                return JSDynamicObject.IsIdenticalOrUndefined.doOther(arg0Value, arg1Value);
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
            public int identityHashCode(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return CompilerDirectives.castExact(receiver, this.receiverClass_).identityHashCode();
            }

            private static boolean fallbackGuard_(int state_0, JSDynamicObject arg0Value, Object arg1Value) {
                return (state_0 & 1) != 0 || !(arg1Value instanceof JSDynamicObject);
            }
        }
    }
}

