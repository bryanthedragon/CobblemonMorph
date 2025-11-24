
package org.graalvm.polyglot;

import org.graalvm.polyglot.impl.AbstractPolyglotImpl;

abstract class AbstractValue {
    final Object receiver;
    final Object context;
    final AbstractPolyglotImpl.AbstractValueDispatch dispatch;

    AbstractValue(AbstractPolyglotImpl.AbstractValueDispatch dispatch, Object context, Object receiver) {
        this.context = context;
        this.dispatch = dispatch;
        this.receiver = receiver;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof AbstractValue)) {
            return false;
        }
        return this.dispatch.equalsImpl(this.context, this.receiver, ((AbstractValue)obj).receiver);
    }

    public int hashCode() {
        return this.dispatch.hashCodeImpl(this.context, this.receiver);
    }

    public String toString() {
        return this.dispatch.toString(this.context, this.receiver);
    }
}

