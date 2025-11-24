
package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.nodes.interop.JSInteropGetIteratorNode;
import com.oracle.truffle.js.nodes.interop.KeyInfoNode;
import com.oracle.truffle.js.runtime.builtins.JSDateObject;
import com.oracle.truffle.js.runtime.objects.JSNonProxyObjectGen;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;

@GeneratedBy(value=JSDateObject.class)
final class JSDateObjectGen {
    private JSDateObjectGen() {
    }

    static {
        LibraryExport.register(JSDateObject.class, new InteropLibraryExports());
    }

    @GeneratedBy(value=JSDateObject.class)
    private static final class InteropLibraryExports
    extends LibraryExport<InteropLibrary> {
        private InteropLibraryExports() {
            super(InteropLibrary.class, JSDateObject.class, false, false, 0);
        }

        @Override
        protected InteropLibrary createUncached(Object receiver) {
            assert (receiver instanceof JSDateObject);
            Uncached uncached = new Uncached(receiver);
            return uncached;
        }

        @Override
        protected InteropLibrary createCached(Object receiver) {
            assert (receiver instanceof JSDateObject);
            return new Cached(receiver);
        }

        @GeneratedBy(value=JSDateObject.class)
        @DenyReplace
        private static final class Uncached
        extends JSNonProxyObjectGen.InteropLibraryExports.Uncached {
            protected Uncached(Object receiver) {
                super(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean accepts(Object receiver) {
                return super.accepts(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isDate(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((JSDateObject)receiver).isDate();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isTime(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((JSDateObject)receiver).isDate();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isTimeZone(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((JSDateObject)receiver).isDate();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public LocalDate asDate(Object arg0Value_) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                JSDateObject arg0Value = (JSDateObject)arg0Value_;
                return arg0Value.asDate(this);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public LocalTime asTime(Object arg0Value_) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                JSDateObject arg0Value = (JSDateObject)arg0Value_;
                return arg0Value.asTime(this);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public ZoneId asTimeZone(Object arg0Value_) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                JSDateObject arg0Value = (JSDateObject)arg0Value_;
                return arg0Value.asTimeZone(this);
            }
        }

        @GeneratedBy(value=JSDateObject.class)
        private static final class Cached
        extends JSNonProxyObjectGen.InteropLibraryExports.Cached {
            @Node.Child
            private KeyInfoNode keyInfo;
            @Node.Child
            private JSInteropGetIteratorNode getIterator;

            protected Cached(Object receiver) {
                super(receiver);
            }

            @Override
            public boolean isDate(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((JSDateObject)receiver).isDate();
            }

            @Override
            public boolean isTime(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((JSDateObject)receiver).isDate();
            }

            @Override
            public boolean isTimeZone(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((JSDateObject)receiver).isDate();
            }

            @Override
            public LocalDate asDate(Object arg0Value_) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                JSDateObject arg0Value = (JSDateObject)arg0Value_;
                Cached asDateNode__asDate_self__ = this;
                return arg0Value.asDate(asDateNode__asDate_self__);
            }

            @Override
            public NodeCost getCost() {
                return NodeCost.MONOMORPHIC;
            }

            @Override
            public LocalTime asTime(Object arg0Value_) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                JSDateObject arg0Value = (JSDateObject)arg0Value_;
                Cached asTimeNode__asTime_self__ = this;
                return arg0Value.asTime(asTimeNode__asTime_self__);
            }

            @Override
            public ZoneId asTimeZone(Object arg0Value_) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                JSDateObject arg0Value = (JSDateObject)arg0Value_;
                Cached asTimeZoneNode__asTimeZone_self__ = this;
                return arg0Value.asTimeZone(asTimeZoneNode__asTimeZone_self__);
            }
        }
    }
}

