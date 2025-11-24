
package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.ConsoleBuiltinsFactory;
import com.oracle.truffle.js.builtins.GlobalBuiltins;
import com.oracle.truffle.js.builtins.GlobalBuiltinsFactory;
import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.nodes.cast.JSToBooleanNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.runtime.JSConsoleUtil;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.io.PrintWriter;
import java.util.Map;

public final class ConsoleBuiltins
extends JSBuiltinsContainer.SwitchEnum<Console> {
    public static final JSBuiltinsContainer BUILTINS = new ConsoleBuiltins();

    protected ConsoleBuiltins() {
        super(JSRealm.CONSOLE_CLASS_NAME, Console.class);
    }

    @Override
    protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, Console builtinEnum) {
        switch (builtinEnum) {
            case log: 
            case info: 
            case debug: 
            case dir: {
                return GlobalBuiltinsFactory.JSGlobalPrintNodeGen.create(context, builtin, false, false, ConsoleBuiltins.args().varArgs().createArgumentNodes(context));
            }
            case error: 
            case warn: {
                return GlobalBuiltinsFactory.JSGlobalPrintNodeGen.create(context, builtin, true, false, ConsoleBuiltins.args().varArgs().createArgumentNodes(context));
            }
            case assert_: {
                return ConsoleBuiltinsFactory.JSConsoleAssertNodeGen.create(context, builtin, ConsoleBuiltins.args().varArgs().createArgumentNodes(context));
            }
            case clear: {
                return ConsoleBuiltinsFactory.JSConsoleClearNodeGen.create(context, builtin, ConsoleBuiltins.args().varArgs().createArgumentNodes(context));
            }
            case count: {
                return ConsoleBuiltinsFactory.JSConsoleCountNodeGen.create(context, builtin, ConsoleBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
            case countReset: {
                return ConsoleBuiltinsFactory.JSConsoleCountResetNodeGen.create(context, builtin, ConsoleBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
            case group: 
            case groupCollapsed: {
                return ConsoleBuiltinsFactory.JSConsoleGroupNodeGen.create(context, builtin, ConsoleBuiltins.args().varArgs().createArgumentNodes(context));
            }
            case groupEnd: {
                return ConsoleBuiltinsFactory.JSConsoleGroupEndNodeGen.create(context, builtin, ConsoleBuiltins.args().fixedArgs(0).createArgumentNodes(context));
            }
            case time: {
                return ConsoleBuiltinsFactory.JSConsoleTimeNodeGen.create(context, builtin, ConsoleBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
            case timeEnd: {
                return ConsoleBuiltinsFactory.JSConsoleTimeEndNodeGen.create(context, builtin, ConsoleBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
            case timeLog: {
                return ConsoleBuiltinsFactory.JSConsoleTimeLogNodeGen.create(context, builtin, ConsoleBuiltins.args().varArgs().createArgumentNodes(context));
            }
        }
        return null;
    }

    public static abstract class JSConsoleTimeLogNode
    extends JSConsoleOperation {
        @Node.Child
        private GlobalBuiltins.JSGlobalPrintNode printNode;
        @Node.Child
        private JSToStringNode toStringNode;

        public JSConsoleTimeLogNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
            this.printNode = GlobalBuiltinsFactory.JSGlobalPrintNodeGen.create(context, null, false, false, null);
            this.toStringNode = JSToStringNode.create();
        }

        @Specialization
        @CompilerDirectives.TruffleBoundary
        protected JSDynamicObject timeLog(Object ... data) {
            TruffleString key = data.length == 0 || data[0] == Undefined.instance ? Strings.DEFAULT : this.toStringNode.executeString(data[0]);
            Map<TruffleString, Long> timeMap = this.getConsoleUtil().getTimeMap();
            if (timeMap.containsKey(key)) {
                long start2 = timeMap.get(key);
                long end2 = this.getRealm().currentTimeMillis();
                long delta = end2 - start2;
                Object[] arr = new Object[Math.max(2, data.length + 1)];
                if (data.length > 1) {
                    System.arraycopy(data, 1, arr, 2, data.length - 1);
                }
                arr[0] = Strings.concat(key, Strings.COLON);
                arr[1] = Strings.concat(Strings.fromLong(delta), Strings.MS);
                this.printNode.executeObjectArray(arr);
            }
            return Undefined.instance;
        }
    }

    public static abstract class JSConsoleTimeEndNode
    extends JSConsoleOperation {
        @Node.Child
        private JSToStringNode toStringNode = JSToStringNode.create();
        @Node.Child
        private GlobalBuiltins.JSGlobalPrintNode printNode;

        public JSConsoleTimeEndNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
            this.printNode = GlobalBuiltinsFactory.JSGlobalPrintNodeGen.create(context, null, false, false, null);
        }

        @Specialization
        @CompilerDirectives.TruffleBoundary
        protected JSDynamicObject timeEnd(Object label) {
            TruffleString key = label == Undefined.instance ? Strings.DEFAULT : this.toStringNode.executeString(label);
            Map<TruffleString, Long> timeMap = this.getConsoleUtil().getTimeMap();
            if (timeMap.containsKey(key)) {
                long start2 = timeMap.remove(key);
                long end2 = this.getRealm().currentTimeMillis();
                long delta = end2 - start2;
                this.printNode.executeObjectArray(new Object[]{Strings.concat(key, Strings.COLON), Strings.concat(Strings.fromLong(delta), Strings.MS)});
            }
            return Undefined.instance;
        }
    }

    public static abstract class JSConsoleTimeNode
    extends JSConsoleOperation {
        @Node.Child
        private JSToStringNode toStringNode = JSToStringNode.create();

        public JSConsoleTimeNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        @CompilerDirectives.TruffleBoundary
        protected JSDynamicObject time(Object label) {
            TruffleString key = label == Undefined.instance ? Strings.DEFAULT : this.toStringNode.executeString(label);
            this.getConsoleUtil().getTimeMap().put(key, this.getRealm().currentTimeMillis());
            return Undefined.instance;
        }
    }

    public static abstract class JSConsoleGroupEndNode
    extends JSConsoleOperation {
        public JSConsoleGroupEndNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        @CompilerDirectives.TruffleBoundary
        protected JSDynamicObject groupEnd() {
            this.getConsoleUtil().decConsoleIndentation();
            return Undefined.instance;
        }
    }

    public static abstract class JSConsoleGroupNode
    extends JSConsoleOperation {
        @Node.Child
        private GlobalBuiltins.JSGlobalPrintNode printNode;
        @Node.Child
        private JSToStringNode toStringNode = JSToStringNode.create();

        public JSConsoleGroupNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
            this.printNode = GlobalBuiltinsFactory.JSGlobalPrintNodeGen.create(context, null, false, false, null);
        }

        @Specialization
        @CompilerDirectives.TruffleBoundary
        protected JSDynamicObject group(Object[] label) {
            if (label.length > 0) {
                this.printNode.executeObjectArray(label);
            }
            this.getConsoleUtil().incConsoleIndentation();
            return Undefined.instance;
        }
    }

    public static abstract class JSConsoleCountResetNode
    extends JSConsoleOperation {
        @Node.Child
        private JSToStringNode toStringNode = JSToStringNode.create();

        public JSConsoleCountResetNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        @CompilerDirectives.TruffleBoundary
        protected JSDynamicObject count(Object label) {
            TruffleString key = label == Undefined.instance ? Strings.DEFAULT : this.toStringNode.executeString(label);
            this.getConsoleUtil().getCountMap().remove(key);
            return Undefined.instance;
        }
    }

    public static abstract class JSConsoleCountNode
    extends JSConsoleOperation {
        @Node.Child
        private JSToStringNode toStringNode = JSToStringNode.create();

        public JSConsoleCountNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        @CompilerDirectives.TruffleBoundary
        protected JSDynamicObject count(Object label) {
            TruffleString key = label == Undefined.instance ? Strings.DEFAULT : this.toStringNode.executeString(label);
            int count = 0;
            JSConsoleUtil console = this.getConsoleUtil();
            Map<TruffleString, Integer> countMap = console.getCountMap();
            if (countMap.containsKey(key)) {
                count = countMap.get(key);
            }
            countMap.put(key, ++count);
            PrintWriter writer = this.getRealm().getOutputWriter();
            writer.append(console.getConsoleIndentationString());
            writer.append(Strings.toJavaString(key));
            writer.append(": ");
            writer.append(String.valueOf(count));
            writer.append(Strings.LINE_SEPARATOR_JLS);
            writer.flush();
            return Undefined.instance;
        }
    }

    public static abstract class JSConsoleClearNode
    extends JSConsoleOperation {
        public JSConsoleClearNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        @CompilerDirectives.TruffleBoundary
        protected JSDynamicObject clear() {
            PrintWriter writer = this.getRealm().getOutputWriter();
            writer.append("\u001b[H\u001b[2J");
            writer.flush();
            return Undefined.instance;
        }
    }

    public static abstract class JSConsoleAssertNode
    extends JSConsoleOperation {
        public static final TruffleString ASSERTION_FAILED_COLON = Strings.constant("Assertion failed:");
        public static final TruffleString ASSERTION_FAILED = Strings.constant("Assertion failed");
        @Node.Child
        private GlobalBuiltins.JSGlobalPrintNode printNode;
        @Node.Child
        private JSToBooleanNode toBooleanNode;

        public JSConsoleAssertNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
            this.printNode = GlobalBuiltinsFactory.JSGlobalPrintNodeGen.create(context, null, false, false, null);
            this.toBooleanNode = JSToBooleanNode.create();
        }

        @Specialization
        protected JSDynamicObject assertImpl(Object ... data) {
            boolean result;
            boolean bl = result = data.length > 0 ? this.toBooleanNode.executeBoolean(data[0]) : false;
            if (!result) {
                Object[] arr = new Object[data.length > 0 ? data.length : 1];
                if (data.length > 1) {
                    System.arraycopy(data, 1, arr, 1, data.length - 1);
                }
                arr[0] = data.length > 1 ? ASSERTION_FAILED_COLON : ASSERTION_FAILED;
                this.printNode.executeObjectArray(arr);
            }
            return Undefined.instance;
        }
    }

    public static abstract class JSConsoleOperation
    extends JSBuiltinNode {
        public JSConsoleOperation(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        public JSConsoleUtil getConsoleUtil() {
            return this.getRealm().getConsoleUtil();
        }
    }

    public static enum Console implements BuiltinEnum<Console>
    {
        log(0),
        info(0),
        debug(0),
        dir(0),
        error(0),
        warn(0),
        assert_(0),
        clear(0),
        count(0),
        countReset(0),
        group(0),
        groupCollapsed(0),
        groupEnd(0),
        time(0),
        timeEnd(0),
        timeLog(0);

        private final int length;

        private Console(int length) {
            this.length = length;
        }

        @Override
        public int getLength() {
            return this.length;
        }
    }
}

