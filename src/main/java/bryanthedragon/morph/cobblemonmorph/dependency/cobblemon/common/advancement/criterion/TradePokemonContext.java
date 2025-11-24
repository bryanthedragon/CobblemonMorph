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
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\t\b\u0016\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0007\u001a\u00020\u0002\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\t\u0010\nR\u0017\u0010\u0003\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006R\u0017\u0010\u0007\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0007\u0010\u0004\u001a\u0004\b\b\u0010\u0006\u00a8\u0006\u000b"}, d2={"Lcom/cobblemon/mod/common/advancement/criterion/TradePokemonContext;", "", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "received", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "getReceived", "()Lcom/cobblemon/mod/common/pokemon/Pokemon;", "traded", "getTraded", "<init>", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;Lcom/cobblemon/mod/common/pokemon/Pokemon;)V", "common"})
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

