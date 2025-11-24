
package com.oracle.truffle.regex;

import com.oracle.truffle.api.TruffleFile;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.instrumentation.ProvidedTags;
import com.oracle.truffle.api.instrumentation.StandardTags;
import com.oracle.truffle.regex.RegexLanguage;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@GeneratedBy(value=RegexLanguage.class)
@TruffleLanguage.Registration(characterMimeTypes={"application/tregex"}, contextPolicy=TruffleLanguage.ContextPolicy.SHARED, id="regex", interactive=false, internal=true, name="REGEX", version="0.1", website="https://github.com/oracle/graal/tree/master/regex")
@ProvidedTags(value={StandardTags.RootTag.class})
public final class RegexLanguageProvider
implements TruffleLanguage.Provider {
    @Override
    public String getLanguageClassName() {
        return "com.oracle.truffle.regex.RegexLanguage";
    }

    @Override
    public TruffleLanguage<?> create() {
        return new RegexLanguage();
    }

    @Override
    public List<TruffleFile.FileTypeDetector> createFileTypeDetectors() {
        return Collections.emptyList();
    }

    @Override
    public Collection<String> getServicesClassNames() {
        return Collections.emptySet();
    }
}

