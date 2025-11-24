/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.server.level.ServerPlayer
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\t\b\u0086\b\u0018\u00002\u00020\u0001B\u0019\u0012\u0006\u0010\b\u001a\u00020\u0002\u0012\b\u0010\t\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\u0004\b\u001a\u0010\u001bJ\u0010\u0010\u0003\u001a\u00020\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u0012\u0010\u0006\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003\u00a2\u0006\u0004\b\u0006\u0010\u0007J&\u0010\n\u001a\u00020\u00002\b\b\u0002\u0010\b\u001a\u00020\u00022\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0005H\u00c6\u0001\u00a2\u0006\u0004\b\n\u0010\u000bJ\u001a\u0010\u000e\u001a\u00020\r2\b\u0010\f\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u0010\u0010\u0011\u001a\u00020\u0010H\u00d6\u0001\u00a2\u0006\u0004\b\u0011\u0010\u0012J\u0010\u0010\u0014\u001a\u00020\u0013H\u00d6\u0001\u00a2\u0006\u0004\b\u0014\u0010\u0015R\u0019\u0010\t\u001a\u0004\u0018\u00010\u00058\u0006\u00a2\u0006\f\n\u0004\b\t\u0010\u0016\u001a\u0004\b\u0017\u0010\u0007R\u0017\u0010\b\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\u0018\u001a\u0004\b\u0019\u0010\u0004\u00a8\u0006\u001c"}, d2={"Lcom/cobblemon/mod/common/api/events/pokemon/FossilRevivedEvent;", "", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "component1", "()Lcom/cobblemon/mod/common/pokemon/Pokemon;", "Lnet/minecraft/server/level/ServerPlayer;", "component2", "()Lnet/minecraft/server/level/ServerPlayer;", "pokemon", "player", "copy", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;Lnet/minecraft/server/level/ServerPlayer;)Lcom/cobblemon/mod/common/api/events/pokemon/FossilRevivedEvent;", "other", "", "equals", "(Ljava/lang/Object;)Z", "", "hashCode", "()I", "", "toString", "()Ljava/lang/String;", "Lnet/minecraft/server/level/ServerPlayer;", "getPlayer", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "getPokemon", "<init>", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;Lnet/minecraft/server/level/ServerPlayer;)V", "common"})
public final class FossilRevivedEvent {
    @NotNull
    private final Pokemon pokemon;
    @Nullable
    private final ServerPlayer player;

    public FossilRevivedEvent(@NotNull Pokemon pokemon, @Nullable ServerPlayer player) {
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        this.pokemon = pokemon;
        this.player = player;
    }

    @NotNull
    public final Pokemon getPokemon() {
        return this.pokemon;
    }

    @Nullable
    public final ServerPlayer getPlayer() {
        return this.player;
    }

    @NotNull
    public final Pokemon component1() {
        return this.pokemon;
    }

    @Nullable
    public final ServerPlayer component2() {
        return this.player;
    }

    @NotNull
    public final FossilRevivedEvent copy(@NotNull Pokemon pokemon, @Nullable ServerPlayer player) {
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        return new FossilRevivedEvent(pokemon, player);
    }

    public static /* synthetic */ FossilRevivedEvent copy$default(FossilRevivedEvent fossilRevivedEvent, Pokemon pokemon, ServerPlayer serverPlayer, int n, Object object) {
        if ((n & 1) != 0) {
            pokemon = fossilRevivedEvent.pokemon;
        }
        if ((n & 2) != 0) {
            serverPlayer = fossilRevivedEvent.player;
        }
        return fossilRevivedEvent.copy(pokemon, serverPlayer);
    }

    @NotNull
    public String toString() {
        return "FossilRevivedEvent(pokemon=" + this.pokemon + ", player=" + this.player + ")";
    }

    public int hashCode() {
        int result = this.pokemon.hashCode();
        result = result * 31 + (this.player == null ? 0 : this.player.hashCode());
        return result;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof FossilRevivedEvent)) {
            return false;
        }
        FossilRevivedEvent fossilRevivedEvent = (FossilRevivedEvent)other;
        if (!Intrinsics.areEqual((Object)this.pokemon, (Object)fossilRevivedEvent.pokemon)) {
            return false;
        }
        return Intrinsics.areEqual((Object)this.player, (Object)fossilRevivedEvent.player);
    }
}

