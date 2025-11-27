/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;

import kotlin.jvm.internal.Intrinsics;

import org.jetbrains.annotations.NotNull;

public class TradePokemonContext {
    @NotNull
    private final Pokemon traded;
    @NotNull
    private final Pokemon received;

    public TradePokemonContext(@NotNull Pokemon traded, @NotNull Pokemon received) {
        Intrinsics.checkNotNullParameter((Object)traded, (String)"traded");
        Intrinsics.checkNotNullParameter((Object)received, (String)"received");
        this.traded = traded;
        this.received = received;
    }

    @NotNull
    public final Pokemon getTraded() {
        return this.traded;
    }

    @NotNull
    public final Pokemon getReceived() {
        return this.received;
    }
}

