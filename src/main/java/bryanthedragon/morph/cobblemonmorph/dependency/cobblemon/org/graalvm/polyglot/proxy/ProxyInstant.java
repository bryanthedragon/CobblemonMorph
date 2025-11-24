
package org.graalvm.polyglot.proxy;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Objects;
import org.graalvm.polyglot.proxy.ProxyDate;
import org.graalvm.polyglot.proxy.ProxyInstantConstants;
import org.graalvm.polyglot.proxy.ProxyTime;
import org.graalvm.polyglot.proxy.ProxyTimeZone;

public interface ProxyInstant
extends ProxyDate,
ProxyTime,
ProxyTimeZone {
    public Instant asInstant();

    @Override
    default public LocalDate asDate() {
        return this.asInstant().atZone(ProxyInstantConstants.UTC).toLocalDate();
    }

    @Override
    default public LocalTime asTime() {
        return this.asInstant().atZone(ProxyInstantConstants.UTC).toLocalTime();
    }

    @Override
    default public ZoneId asTimeZone() {
        return ProxyInstantConstants.UTC;
    }

    public static ProxyInstant from(final Instant instant) {
        Objects.requireNonNull(instant);
        return new ProxyInstant(){

            @Override
            public Instant asInstant() {
                return instant;
            }
        };
    }
}

