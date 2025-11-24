
package com.oracle.truffle.api.frame;

import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.impl.Accessor;

final class FrameAccessor
extends Accessor {
    static final FrameAccessor ACCESSOR = new FrameAccessor();

    FrameAccessor() {
    }

    static final class FramesImpl
    extends Accessor.FrameSupport {
        FramesImpl() {
        }

        @Override
        public void markMaterializeCalled(FrameDescriptor descriptor) {
            descriptor.materializeCalled = true;
        }

        @Override
        public boolean getMaterializeCalled(FrameDescriptor descriptor) {
            return descriptor.materializeCalled;
        }

        @Override
        public boolean usesAllStaticMode(FrameDescriptor descriptor) {
            return descriptor.staticMode == 2;
        }

        @Override
        public boolean usesMixedStaticMode(FrameDescriptor descriptor) {
            return descriptor.staticMode == 3;
        }
    }
}

