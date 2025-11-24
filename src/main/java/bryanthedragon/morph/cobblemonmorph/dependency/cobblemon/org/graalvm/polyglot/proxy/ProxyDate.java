
package org.graalvm.polyglot.proxy;

import java.time.LocalDate;
import org.graalvm.polyglot.proxy.Proxy;

public interface ProxyDate
extends Proxy {
    public LocalDate asDate();

    public static ProxyDate from(final LocalDate date) {
        return new ProxyDate(){

            @Override
            public LocalDate asDate() {
                return date;
            }
        };
    }
}

