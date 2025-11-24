
package com.oracle.truffle.js.nodes;

import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.nodes.NodeInterface;

public interface FrameDescriptorProvider
extends NodeInterface {
    public FrameDescriptor getFrameDescriptor();
}

