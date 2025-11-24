
package com.oracle.truffle.polyglot;

import com.oracle.truffle.polyglot.PolyglotImpl;
import com.oracle.truffle.polyglot.PolyglotLanguage;
import java.util.Set;
import org.graalvm.options.OptionDescriptors;
import org.graalvm.polyglot.impl.AbstractPolyglotImpl;

final class PolyglotLanguageDispatch
extends AbstractPolyglotImpl.AbstractLanguageDispatch {
    protected PolyglotLanguageDispatch(PolyglotImpl impl) {
        super(impl);
    }

    @Override
    public String getName(Object receiver) {
        return ((PolyglotLanguage)receiver).getName();
    }

    @Override
    public String getImplementationName(Object receiver) {
        return ((PolyglotLanguage)receiver).getImplementationName();
    }

    @Override
    public boolean isInteractive(Object receiver) {
        return ((PolyglotLanguage)receiver).isInteractive();
    }

    @Override
    public String getVersion(Object receiver) {
        return ((PolyglotLanguage)receiver).getVersion();
    }

    @Override
    public String getId(Object receiver) {
        return ((PolyglotLanguage)receiver).getId();
    }

    @Override
    public OptionDescriptors getOptions(Object receiver) {
        return ((PolyglotLanguage)receiver).getOptions();
    }

    @Override
    public Set<String> getMimeTypes(Object receiver) {
        return ((PolyglotLanguage)receiver).getMimeTypes();
    }

    @Override
    public String getDefaultMimeType(Object receiver) {
        return ((PolyglotLanguage)receiver).getDefaultMimeType();
    }

    @Override
    public String getWebsite(Object receiver) {
        return ((PolyglotLanguage)receiver).getWebsite();
    }
}

