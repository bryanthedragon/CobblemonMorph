
package org.graalvm.polyglot.proxy;

import java.time.Duration;
import org.graalvm.polyglot.proxy.Proxy;

public interface ProxyDuration
extends Proxy {
    public Duration asDuration();

    public static ProxyDuration from(final Duration duration) {
        return new ProxyDuration(){

            @Override
            public Duration asDuration() {
                return duration;
            }
        };
    }
}

