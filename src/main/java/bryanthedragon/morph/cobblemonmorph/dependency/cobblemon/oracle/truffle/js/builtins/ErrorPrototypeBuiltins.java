
package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.ErrorPrototypeBuiltinsFactory;
import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.GraalJSException;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.JSArray;
import com.oracle.truffle.js.runtime.builtins.JSError;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.Undefined;

public final class ErrorPrototypeBuiltins
extends JSBuiltinsContainer.Switch {
    public static final JSBuiltinsContainer BUILTINS = new ErrorPrototypeBuiltins();

    protected ErrorPrototypeBuiltins() {
        super(JSError.PROTOTYPE_NAME);
        this.defineFunction(Strings.TO_STRING, 0);
    }

    @Override
    protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget) {
        if (Strings.equals(Strings.TO_STRING, builtin.getName())) {
            return ErrorPrototypeBuiltinsFactory.ErrorPrototypeToStringNodeGen.create(context, builtin, ErrorPrototypeBuiltins.args().withThis().createArgumentNodes(context));
        }
        return null;
    }

    public static abstract class ErrorPrototypeGetStackTraceNode
    extends JSBuiltinNode {
        public ErrorPrototypeGetStackTraceNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization(guards={"!isJSObject(thisObj)"})
        protected JSDynamicObject getStackTrace(Object thisObj) {
            throw Errors.createTypeErrorNotAnObject(thisObj);
        }

        @Specialization(guards={"isJSObject(thisObj)"})
        protected JSDynamicObject getStackTrace(JSDynamicObject thisObj) {
            Object exception = JSDynamicObject.getOrNull(thisObj, JSError.EXCEPTION_PROPERTY_NAME);
            Object[] stackTrace = ErrorPrototypeGetStackTraceNode.getStackTraceFromThrowable(exception);
            return JSArray.createConstant(this.getContext(), this.getRealm(), stackTrace);
        }

        @CompilerDirectives.TruffleBoundary
        private static Object[] getStackTraceFromThrowable(Object exception) {
            if (exception instanceof GraalJSException) {
                return ((GraalJSException)exception).getJSStackTrace();
            }
            return new StackTraceElement[0];
        }
    }

    public static abstract class ErrorPrototypeToStringNode
    extends JSBuiltinNode {
        @Node.Child
        private PropertyGetNode getNameNode;
        @Node.Child
        private PropertyGetNode getMessageNode;
        @Node.Child
        private JSToStringNode toStringNode;

        public ErrorPrototypeToStringNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization(guards={"!isJSObject(thisObj)"})
        protected Object toStringNonObject(Object thisObj, @Cached TruffleString.ConcatNode concatNode) {
            TruffleString name = this.toStringConv(thisObj);
            String message = Strings.toJavaString(Strings.concat(concatNode, Strings.METHOD_ERROR_PROTOTYPE_TO_STRING_CALLED_ON_INCOMPATIBLE_RECEIVER, name));
            throw Errors.createTypeError(message, (Node)this);
        }

        @Specialization(guards={"isJSObject(errorObj)"})
        protected Object toStringObject(JSDynamicObject errorObj) {
            TruffleString strMessage;
            Object objName = this.getName(errorObj);
            TruffleString strName = objName == Undefined.instance ? Strings.UC_ERROR : this.toStringConv(objName);
            Object objMessage = this.getMessage(errorObj);
            TruffleString truffleString = strMessage = objMessage == Undefined.instance ? Strings.EMPTY_STRING : this.toStringConv(objMessage);
            if (Strings.length(strName) == 0) {
                return strMessage;
            }
            if (Strings.length(strMessage) == 0) {
                return strName;
            }
            return ErrorPrototypeToStringNode.toStringIntl(strName, strMessage);
        }

        private TruffleString toStringConv(Object value2) {
            if (this.toStringNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.toStringNode = this.insert(JSToStringNode.create());
            }
            return this.toStringNode.executeString(value2);
        }

        @CompilerDirectives.TruffleBoundary
        private static Object toStringIntl(TruffleString strName, TruffleString strMessage) {
            return Strings.concatAll(strName, Strings.COLON_SPACE, strMessage);
        }

        protected Object getName(JSDynamicObject errObj) {
            if (this.getNameNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.getNameNode = this.insert(PropertyGetNode.create(JSError.NAME, false, this.getContext()));
            }
            return this.getNameNode.getValue(errObj);
        }

        protected Object getMessage(JSDynamicObject errObj) {
            if (this.getMessageNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.getMessageNode = this.insert(PropertyGetNode.create(JSError.MESSAGE, false, this.getContext()));
            }
            return this.getMessageNode.getValue(errObj);
        }
    }

    public static final class ErrorPrototypeNashornCompatBuiltins
    extends JSBuiltinsContainer.SwitchEnum<ErrorNashornCompat> {
        public static final JSBuiltinsContainer BUILTINS = new ErrorPrototypeNashornCompatBuiltins();

        protected ErrorPrototypeNashornCompatBuiltins() {
            super(JSError.PROTOTYPE_NAME, ErrorNashornCompat.class);
        }

        @Override
        protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, ErrorNashornCompat builtinEnum) {
            switch (builtinEnum) {
                case getStackTrace: {
                    return ErrorPrototypeBuiltinsFactory.ErrorPrototypeGetStackTraceNodeGen.create(context, builtin, ErrorPrototypeNashornCompatBuiltins.args().withThis().createArgumentNodes(context));
                }
            }
            return null;
        }

        public static enum ErrorNashornCompat implements BuiltinEnum<ErrorNashornCompat>
        {
            getStackTrace(0);

            private final int length;

            private ErrorNashornCompat(int length) {
                this.length = length;
            }

            @Override
            public int getLength() {
                return this.length;
            }
        }
    }
}

