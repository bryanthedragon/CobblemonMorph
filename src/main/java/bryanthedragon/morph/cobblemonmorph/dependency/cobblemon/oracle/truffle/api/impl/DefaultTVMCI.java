
package com.oracle.truffle.api.impl;

import com.oracle.truffle.api.impl.Accessor;
import com.oracle.truffle.api.impl.DefaultRuntimeAccessor;
import com.oracle.truffle.api.impl.TVMCI;

final class DefaultTVMCI
extends TVMCI {
    DefaultTVMCI() {
    }

    @Override
    protected Accessor.RuntimeSupport createRuntimeSupport(Object permission2) {
        return new DefaultRuntimeAccessor.DefaultRuntimeSupport(permission2);
    }
}

