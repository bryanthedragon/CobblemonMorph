
package com.oracle.truffle.api.staticobject;

import com.oracle.truffle.api.staticobject.StaticProperty;

public final class DefaultStaticProperty
extends StaticProperty {
    private final String id;

    public DefaultStaticProperty(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }
}

