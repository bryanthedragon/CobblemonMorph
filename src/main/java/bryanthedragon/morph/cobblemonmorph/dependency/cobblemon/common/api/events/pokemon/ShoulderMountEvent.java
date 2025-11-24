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

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.Cancelable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\n\b\u0086\b\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u000b\u001a\u00020\u0002\u0012\u0006\u0010\f\u001a\u00020\u0005\u0012\u0006\u0010\r\u001a\u00020\b\u00a2\u0006\u0004\b\u001f\u0010 J\u0010\u0010\u0003\u001a\u00020\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u0010\u0010\u0006\u001a\u00020\u0005H\u00c6\u0003\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u0010\u0010\t\u001a\u00020\bH\u00c6\u0003\u00a2\u0006\u0004\b\t\u0010\nJ.\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u000b\u001a\u00020\u00022\b\b\u0002\u0010\f\u001a\u00020\u00052\b\b\u0002\u0010\r\u001a\u00020\bH\u00c6\u0001\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u001a\u0010\u0012\u001a\u00020\b2\b\u0010\u0011\u001a\u0004\u0018\u00010\u0010H\u00d6\u0003\u00a2\u0006\u0004\b\u0012\u0010\u0013J\u0010\u0010\u0015\u001a\u00020\u0014H\u00d6\u0001\u00a2\u0006\u0004\b\u0015\u0010\u0016J\u0010\u0010\u0018\u001a\u00020\u0017H\u00d6\u0001\u00a2\u0006\u0004\b\u0018\u0010\u0019R\u0017\u0010\r\u001a\u00020\b8\u0006\u00a2\u0006\f\n\u0004\b\r\u0010\u001a\u001a\u0004\b\r\u0010\nR\u0017\u0010\u000b\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u000b\u0010\u001b\u001a\u0004\b\u001c\u0010\u0004R\u0017\u0010\f\u001a\u00020\u00058\u0006\u00a2\u0006\f\n\u0004\b\f\u0010\u001d\u001a\u0004\b\u001e\u0010\u0007\u00a8\u0006!"}, d2={"Lcom/cobblemon/mod/common/api/events/pokemon/ShoulderMountEvent;", "Lcom/cobblemon/mod/common/api/events/Cancelable;", "Lnet/minecraft/server/level/ServerPlayer;", "component1", "()Lnet/minecraft/server/level/ServerPlayer;", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "component2", "()Lcom/cobblemon/mod/common/pokemon/Pokemon;", "", "component3", "()Z", "player", "pokemon", "isLeft", "copy", "(Lnet/minecraft/server/level/ServerPlayer;Lcom/cobblemon/mod/common/pokemon/Pokemon;Z)Lcom/cobblemon/mod/common/api/events/pokemon/ShoulderMountEvent;", "", "other", "equals", "(Ljava/lang/Object;)Z", "", "hashCode", "()I", "", "toString", "()Ljava/lang/String;", "Z", "Lnet/minecraft/server/level/ServerPlayer;", "getPlayer", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "getPokemon", "<init>", "(Lnet/minecraft/server/level/ServerPlayer;Lcom/cobblemon/mod/common/pokemon/Pokemon;Z)V", "common"})
public final class ShoulderMountEvent
extends Cancelable {
    @NotNull
    private final ServerPlayer player;
    @NotNull
    private final Pokemon pokemon;
    private final boolean isLeft;

    public ShoulderMountEvent(@NotNull ServerPlayer player, @NotNull Pokemon pokemon, boolean isLeft) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        this.player = player;
        this.pokemon = pokemon;
        this.isLeft = isLeft;
    }

    @NotNull
    public final ServerPlayer getPlayer() {
        return this.player;
    }

    @NotNull
    public final Pokemon getPokemon() {
        return this.pokemon;
    }

    public final boolean isLeft() {
        return this.isLeft;
    }

    @NotNull
    public final ServerPlayer component1() {
        return this.player;
    }

    @NotNull
    public final Pokemon component2() {
        return this.pokemon;
    }

    public final boolean component3() {
        return this.isLeft;
    }

    @NotNull
    public final ShoulderMountEvent copy(@NotNull ServerPlayer player, @NotNull Pokemon pokemon, boolean isLeft) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        return new ShoulderMountEvent(player, pokemon, isLeft);
    }

    public static /* synthetic */ ShoulderMountEvent copy$default(ShoulderMountEvent shoulderMountEvent, ServerPlayer serverPlayer, Pokemon pokemon, boolean bl, int n, Object object) {
        if ((n & 1) != 0) {
            serverPlayer = shoulderMountEvent.player;
        }
        if ((n & 2) != 0) {
            pokemon = shoulderMountEvent.pokemon;
        }
        if ((n & 4) != 0) {
            bl = shoulderMountEvent.isLeft;
        }
        return shoulderMountEvent.copy(serverPlayer, pokemon, bl);
    }

    @NotNull
    public String toString() {
        return "ShoulderMountEvent(player=" + this.player + ", pokemon=" + this.pokemon + ", isLeft=" + this.isLeft + ")";
    }

    public int hashCode() {
        int result = this.player.hashCode();
        result = result * 31 + this.pokemon.hashCode();
        int n = this.isLeft ? 1 : 0;
        if (n != 0) {
            n = 1;
        }
        result = result * 31 + n;
        return result;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ShoulderMountEvent)) {
            return false;
        }
        ShoulderMountEvent shoulderMountEvent = (ShoulderMountEvent)other;
        if (!Intrinsics.areEqual((Object)this.player, (Object)shoulderMountEvent.player)) {
            return false;
        }
        if (!Intrinsics.areEqual((Object)this.pokemon, (Object)shoulderMountEvent.pokemon)) {
            return false;
        }
        return this.isLeft == shoulderMountEvent.isLeft;
    }
}

