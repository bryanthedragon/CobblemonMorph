
package org.graalvm.polyglot.proxy;

import java.time.ZoneId;
import java.util.Objects;
import org.graalvm.polyglot.proxy.Proxy;

public interface ProxyTimeZone
extends Proxy {
    public ZoneId asTimeZone();

    public static ProxyTimeZone from(final ZoneId timeZone) {
        Objects.requireNonNull(timeZone);
        return new ProxyTimeZone(){

            @Override
            public ZoneId asTimeZone() {
                return timeZone;
            }
        };
    }
}

