
package org.graalvm.polyglot.proxy;

import java.time.LocalTime;
import org.graalvm.polyglot.proxy.Proxy;

public interface ProxyTime
extends Proxy {
    public LocalTime asTime();

    public static ProxyTime from(final LocalTime time) {
        return new ProxyTime(){

            @Override
            public LocalTime asTime() {
                return time;
            }
        };
    }
}

