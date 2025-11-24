
package org.graalvm.polyglot;

import java.util.Set;
import org.graalvm.options.OptionDescriptors;
import org.graalvm.polyglot.impl.AbstractPolyglotImpl;

public final class Language {
    final AbstractPolyglotImpl.AbstractLanguageDispatch dispatch;
    final Object receiver;

    Language(AbstractPolyglotImpl.AbstractLanguageDispatch dispatch, Object receiver) {
        this.dispatch = dispatch;
        this.receiver = receiver;
    }

    public String getId() {
        return this.dispatch.getId(this.receiver);
    }

    public String getName() {
        return this.dispatch.getName(this.receiver);
    }

    public String getImplementationName() {
        return this.dispatch.getImplementationName(this.receiver);
    }

    public String getVersion() {
        return this.dispatch.getVersion(this.receiver);
    }

    public boolean isInteractive() {
        return this.dispatch.isInteractive(this.receiver);
    }

    public OptionDescriptors getOptions() {
        return this.dispatch.getOptions(this.receiver);
    }

    public String getDefaultMimeType() {
        return this.dispatch.getDefaultMimeType(this.receiver);
    }

    public Set<String> getMimeTypes() {
        return this.dispatch.getMimeTypes(this.receiver);
    }

    public String getWebsite() {
        return this.dispatch.getWebsite(this.receiver);
    }
}

