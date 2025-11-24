
package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import com.oracle.truffle.api.object.Property;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.access.InitErrorObjectNodeFactory;
import com.oracle.truffle.js.nodes.access.InstallErrorCauseNode;
import com.oracle.truffle.js.nodes.access.PropertySetNode;
import com.oracle.truffle.js.nodes.function.CreateMethodPropertyNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.GraalJSException;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.Properties;
import com.oracle.truffle.js.runtime.builtins.JSError;
import com.oracle.truffle.js.runtime.objects.JSAttributes;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;
import com.oracle.truffle.js.runtime.objects.JSProperty;
import com.oracle.truffle.js.runtime.objects.Undefined;

public final class InitErrorObjectNode
extends JavaScriptBaseNode {
    private final JSContext context;
    @Node.Child
    private PropertySetNode setException;
    @Node.Child
    private PropertySetNode setFormattedStack;
    @Node.Child
    private DynamicObjectLibrary setMessage;
    @Node.Child
    private DynamicObjectLibrary setErrors;
    @Node.Child
    private DefineStackPropertyNode defineStackProperty;
    private final boolean defaultColumnNumber;
    @Node.Child
    private CreateMethodPropertyNode setLineNumber;
    @Node.Child
    private CreateMethodPropertyNode setColumnNumber;
    @Node.Child
    private InstallErrorCauseNode installErrorCauseNode;

    private InitErrorObjectNode(JSContext context, boolean defaultColumnNumber) {
        this.context = context;
        this.setException = PropertySetNode.createSetHidden(JSError.EXCEPTION_PROPERTY_NAME, context);
        this.setFormattedStack = PropertySetNode.createSetHidden(JSError.FORMATTED_STACK_NAME, context);
        this.setMessage = JSObjectUtil.createDispatched(JSError.MESSAGE);
        this.defineStackProperty = DefineStackPropertyNode.create();
        this.defaultColumnNumber = defaultColumnNumber;
        if (context.isOptionNashornCompatibilityMode()) {
            this.setLineNumber = CreateMethodPropertyNode.create(context, JSError.LINE_NUMBER_PROPERTY_NAME);
            this.setColumnNumber = CreateMethodPropertyNode.create(context, JSError.COLUMN_NUMBER_PROPERTY_NAME);
        }
    }

    public static InitErrorObjectNode create(JSContext context) {
        return new InitErrorObjectNode(context, false);
    }

    public static InitErrorObjectNode create(JSContext context, boolean defaultColumnNumber) {
        return new InitErrorObjectNode(context, defaultColumnNumber);
    }

    public JSDynamicObject execute(JSDynamicObject errorObj, GraalJSException exception, TruffleString messageOpt) {
        return this.execute(errorObj, exception, messageOpt, null);
    }

    public JSDynamicObject execute(JSDynamicObject errorObj, GraalJSException exception, TruffleString messageOpt, JSDynamicObject errorsOpt) {
        return this.execute(errorObj, exception, messageOpt, errorsOpt, Undefined.instance);
    }

    public JSDynamicObject execute(JSDynamicObject errorObj, GraalJSException exception, TruffleString messageOpt, JSDynamicObject errorsOpt, Object options) {
        if (messageOpt != null) {
            Properties.putWithFlags(this.setMessage, errorObj, JSError.MESSAGE, messageOpt, JSError.MESSAGE_ATTRIBUTES);
        }
        if (errorsOpt != null) {
            Properties.putWithFlags(this.setErrorsNode(), errorObj, JSError.ERRORS_NAME, errorsOpt, JSError.ERRORS_ATTRIBUTES);
        }
        if (this.context.getContextOptions().isErrorCauseEnabled() && options != Undefined.instance) {
            this.installErrorCause(errorObj, options);
        }
        this.setException.setValue(errorObj, exception);
        this.setFormattedStack.setValue(errorObj, null);
        this.defineStackProperty.execute(errorObj);
        if (this.setLineNumber != null && exception.getJSStackTrace().length > 0) {
            GraalJSException.JSStackTraceElement topStackTraceElement = exception.getJSStackTrace()[0];
            this.setLineNumber.executeVoid(errorObj, topStackTraceElement.getLineNumber());
            this.setColumnNumber.executeVoid(errorObj, this.defaultColumnNumber ? -1 : topStackTraceElement.getColumnNumber());
        }
        return errorObj;
    }

    private void installErrorCause(JSDynamicObject errorObj, Object options) {
        if (this.installErrorCauseNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.installErrorCauseNode = this.insert(new InstallErrorCauseNode(this.context));
        }
        this.installErrorCauseNode.executeVoid(errorObj, options);
    }

    private DynamicObjectLibrary setErrorsNode() {
        DynamicObjectLibrary errorsLib = this.setErrors;
        if (errorsLib == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.setErrors = errorsLib = this.insert(JSObjectUtil.createDispatched(JSError.ERRORS_NAME));
        }
        return errorsLib;
    }

    public static abstract class DefineStackPropertyNode
    extends JavaScriptBaseNode {
        static DefineStackPropertyNode create() {
            return InitErrorObjectNodeFactory.DefineStackPropertyNodeGen.create();
        }

        abstract void execute(JSDynamicObject var1);

        @Specialization(limit="3")
        void doCached(JSDynamicObject errorObj, @CachedLibrary(value="errorObj") DynamicObjectLibrary objectLibrary) {
            Property stackProperty = Properties.getProperty(objectLibrary, errorObj, JSError.STACK_NAME);
            int attrs = JSAttributes.getDefaultNotEnumerable();
            if (stackProperty != null) {
                if (!JSProperty.isConfigurable(stackProperty)) {
                    throw Errors.createTypeErrorCannotRedefineProperty(JSError.STACK_NAME);
                }
                if (JSProperty.isEnumerable(stackProperty)) {
                    attrs = JSAttributes.getDefault();
                }
            }
            Properties.putConstant(objectLibrary, errorObj, JSError.STACK_NAME, JSError.STACK_PROXY, attrs | 0x10);
        }
    }
}

