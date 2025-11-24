
package com.oracle.truffle.api.dsl;

import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.nodes.RootNode;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value=RetentionPolicy.CLASS)
@Target(value={ElementType.TYPE})
public @interface GenerateAOT {

    public static interface Provider {
        public void prepareForAOT(TruffleLanguage<?> var1, RootNode var2);
    }

    @Retention(value=RetentionPolicy.CLASS)
    @Target(value={ElementType.METHOD})
    public static @interface Exclude {
    }
}

