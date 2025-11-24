/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.server.level.ServerPlayer
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.Cancelable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B!\u0012\u0006\u0010\u000e\u001a\u00020\r\u0012\u0006\u0010\u0013\u001a\u00020\u0012\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0002\u00a2\u0006\u0004\b\u0017\u0010\u0018R$\u0010\u0003\u001a\u0004\u0018\u00010\u00028\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u0013\u0010\f\u001a\u0004\u0018\u00010\t8F\u00a2\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0017\u0010\u000e\u001a\u00020\r8\u0006\u00a2\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\u0010\u0010\u0011R\u0017\u0010\u0013\u001a\u00020\u00128\u0006\u00a2\u0006\f\n\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0015\u0010\u0016\u00a8\u0006\u0019"}, d2={"Lcom/cobblemon/mod/common/api/events/pokemon/PokemonNicknamedEvent;", "Lcom/cobblemon/mod/common/api/events/Cancelable;", "Lnet/minecraft/network/chat/MutableComponent;", "nickname", "Lnet/minecraft/network/chat/MutableComponent;", "getNickname", "()Lnet/minecraft/network/chat/MutableComponent;", "setNickname", "(Lnet/minecraft/network/chat/MutableComponent;)V", "", "getNicknameString", "()Ljava/lang/String;", "nicknameString", "Lnet/minecraft/server/level/ServerPlayer;", "player", "Lnet/minecraft/server/level/ServerPlayer;", "getPlayer", "()Lnet/minecraft/server/level/ServerPlayer;", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "getPokemon", "()Lcom/cobblemon/mod/common/pokemon/Pokemon;", "<init>", "(Lnet/minecraft/server/level/ServerPlayer;Lcom/cobblemon/mod/common/pokemon/Pokemon;Lnet/minecraft/network/chat/MutableComponent;)V", "common"})
public final class PokemonNicknamedEvent
extends Cancelable {
    @NotNull
    private final ServerPlayer player;
    @NotNull
    private final Pokemon pokemon;
    @Nullable
    private MutableComponent nickname;

    public PokemonNicknamedEvent(@NotNull ServerPlayer player, @NotNull Pokemon pokemon, @Nullable MutableComponent nickname) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        this.player = player;
        this.pokemon = pokemon;
        this.nickname = nickname;
    }

    @NotNull
    public final ServerPlayer getPlayer() {
        return this.player;
    }

    @NotNull
    public final Pokemon getPokemon() {
        return this.pokemon;
    }

    @Nullable
    public final MutableComponent getNickname() {
        return this.nickname;
    }

    public final void setNickname(@Nullable MutableComponent mutableComponent) {
        this.nickname = mutableComponent;
    }

    @Nullable
    public final String getNicknameString() {
        MutableComponent mutableComponent = this.nickname;
        return mutableComponent != null ? mutableComponent.getString() : null;
    }
}

