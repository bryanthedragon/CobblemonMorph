
package org.graalvm.polyglot;

import org.graalvm.options.OptionDescriptors;
import org.graalvm.polyglot.impl.AbstractPolyglotImpl;

public final class Instrument {
    final AbstractPolyglotImpl.AbstractInstrumentDispatch dispatch;
    final Object receiver;

    Instrument(AbstractPolyglotImpl.AbstractInstrumentDispatch dispatch, Object receiver) {
        this.dispatch = dispatch;
        this.receiver = receiver;
    }

    public String getId() {
        return this.dispatch.getId(this.receiver);
    }

    public String getName() {
        return this.dispatch.getName(this.receiver);
    }

    public OptionDescriptors getOptions() {
        return this.dispatch.getOptions(this.receiver);
    }

    public String getVersion() {
        return this.dispatch.getVersion(this.receiver);
    }

    public <T> T lookup(Class<T> type) {
        return this.dispatch.lookup(this.receiver, type);
    }

    public String getWebsite() {
        return this.dispatch.getWebsite(this.receiver);
    }
}

