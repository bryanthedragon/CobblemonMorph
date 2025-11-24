/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.trade;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import kotlin.Metadata;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\r\u0018\u00002\u00020\u0001B\u0007\u00a2\u0006\u0004\b\u0012\u0010\u0013J\u0017\u0010\u0005\u001a\u00020\u00042\b\u0010\u0003\u001a\u0004\u0018\u00010\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\"\u0010\b\u001a\u00020\u00078\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR$\u0010\u0003\u001a\u0004\u0018\u00010\u00028\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0003\u0010\u000e\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0006\u00a8\u0006\u0014"}, d2={"Lcom/cobblemon/mod/common/trade/TradeOffer;", "", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "", "updateOffer", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;)V", "", "accepted", "Z", "getAccepted", "()Z", "setAccepted", "(Z)V", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "getPokemon", "()Lcom/cobblemon/mod/common/pokemon/Pokemon;", "setPokemon", "<init>", "()V", "common"})
public final class TradeOffer {
    @Nullable
    private Pokemon pokemon;
    private boolean accepted;

    @Nullable
    public final Pokemon getPokemon() {
        return this.pokemon;
    }

    public final void setPokemon(@Nullable Pokemon pokemon) {
        this.pokemon = pokemon;
    }

    public final boolean getAccepted() {
        return this.accepted;
    }

    public final void setAccepted(boolean bl) {
        this.accepted = bl;
    }

    public final void updateOffer(@Nullable Pokemon pokemon) {
        this.pokemon = pokemon;
        this.accepted = false;
    }
}

