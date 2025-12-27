package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInterface;
import com.oracle.truffle.js.nodes.JavaScriptNode;

public interface WriteNode extends NodeInterface {
   void executeWrite(VirtualFrame frame, Object value);

   JavaScriptNode getRhs();
}
