
package com.oracle.truffle.js.nodes.control;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.debug.DebuggerTags;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.control.StatementNode;
import java.text.MessageFormat;
import java.util.Set;

@NodeInfo(shortName="debugger")
public class DebuggerNode
extends StatementNode {
    private static long time;
    private static boolean timingEnabled;

    DebuggerNode() {
    }

    public static DebuggerNode create() {
        return new DebuggerNode();
    }

    @Override
    public Object execute(VirtualFrame frame) {
        DebuggerNode.doTiming();
        return EMPTY;
    }

    public static void timingStart() {
        time = System.nanoTime();
        timingEnabled = true;
    }

    public static void timingStop() {
        timingEnabled = false;
    }

    @CompilerDirectives.TruffleBoundary
    private static void doTiming() {
        if (timingEnabled) {
            long now = System.nanoTime();
            if (time != 0L) {
                System.out.println(MessageFormat.format("run time: {0,number,#,###.##} ms", (double)(now - time) / 1000.0 / 1000.0));
            }
            time = now;
        }
    }

    @Override
    protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
        return DebuggerNode.create();
    }

    @Override
    public boolean hasTag(Class<? extends Tag> tag) {
        if (tag == DebuggerTags.AlwaysHalt.class) {
            return true;
        }
        return super.hasTag(tag);
    }
}

