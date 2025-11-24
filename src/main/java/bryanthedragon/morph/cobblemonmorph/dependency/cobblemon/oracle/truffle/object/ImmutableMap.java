
package com.oracle.truffle.object;

import java.util.Map;

public interface ImmutableMap<K, V>
extends Map<K, V> {
    public ImmutableMap<K, V> copyAndPut(K var1, V var2);

    public ImmutableMap<K, V> copyAndRemove(K var1);
}

