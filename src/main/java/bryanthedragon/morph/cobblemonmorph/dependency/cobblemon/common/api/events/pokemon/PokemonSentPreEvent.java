/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.Cancelable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u000b\b\u0086\b\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u000b\u001a\u00020\u0002\u0012\u0006\u0010\f\u001a\u00020\u0005\u0012\u0006\u0010\r\u001a\u00020\b\u00a2\u0006\u0004\b!\u0010\"J\u0010\u0010\u0003\u001a\u00020\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u0010\u0010\u0006\u001a\u00020\u0005H\u00c6\u0003\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u0010\u0010\t\u001a\u00020\bH\u00c6\u0003\u00a2\u0006\u0004\b\t\u0010\nJ.\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u000b\u001a\u00020\u00022\b\b\u0002\u0010\f\u001a\u00020\u00052\b\b\u0002\u0010\r\u001a\u00020\bH\u00c6\u0001\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u001a\u0010\u0013\u001a\u00020\u00122\b\u0010\u0011\u001a\u0004\u0018\u00010\u0010H\u00d6\u0003\u00a2\u0006\u0004\b\u0013\u0010\u0014J\u0010\u0010\u0016\u001a\u00020\u0015H\u00d6\u0001\u00a2\u0006\u0004\b\u0016\u0010\u0017J\u0010\u0010\u0019\u001a\u00020\u0018H\u00d6\u0001\u00a2\u0006\u0004\b\u0019\u0010\u001aR\u0017\u0010\f\u001a\u00020\u00058\u0006\u00a2\u0006\f\n\u0004\b\f\u0010\u001b\u001a\u0004\b\u001c\u0010\u0007R\u0017\u0010\u000b\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u000b\u0010\u001d\u001a\u0004\b\u001e\u0010\u0004R\u0017\u0010\r\u001a\u00020\b8\u0006\u00a2\u0006\f\n\u0004\b\r\u0010\u001f\u001a\u0004\b \u0010\n\u00a8\u0006#"}, d2={"Lcom/cobblemon/mod/common/api/events/pokemon/PokemonSentPreEvent;", "Lcom/cobblemon/mod/common/api/events/Cancelable;", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "component1", "()Lcom/cobblemon/mod/common/pokemon/Pokemon;", "Lnet/minecraft/server/level/ServerLevel;", "component2", "()Lnet/minecraft/server/level/ServerLevel;", "Lnet/minecraft/world/phys/Vec3;", "component3", "()Lnet/minecraft/world/phys/Vec3;", "pokemon", "level", "position", "copy", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/phys/Vec3;)Lcom/cobblemon/mod/common/api/events/pokemon/PokemonSentPreEvent;", "", "other", "", "equals", "(Ljava/lang/Object;)Z", "", "hashCode", "()I", "", "toString", "()Ljava/lang/String;", "Lnet/minecraft/server/level/ServerLevel;", "getLevel", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "getPokemon", "Lnet/minecraft/world/phys/Vec3;", "getPosition", "<init>", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/phys/Vec3;)V", "common"})
public final class PokemonSentPreEvent
extends Cancelable {
    @NotNull
    private final Pokemon pokemon;
    @NotNull
    private final ServerLevel level;
    @NotNull
    private final Vec3 position;

    public PokemonSentPreEvent(@NotNull Pokemon pokemon, @NotNull ServerLevel level, @NotNull Vec3 position) {
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        Intrinsics.checkNotNullParameter((Object)level, (String)"level");
        Intrinsics.checkNotNullParameter((Object)position, (String)"position");
        this.pokemon = pokemon;
        this.level = level;
        this.position = position;
    }

    @NotNull
    public final Pokemon getPokemon() {
        return this.pokemon;
    }

    @NotNull
    public final ServerLevel getLevel() {
        return this.level;
    }

    @NotNull
    public final Vec3 getPosition() {
        return this.position;
    }

    @NotNull
    public final Pokemon component1() {
        return this.pokemon;
    }

    @NotNull
    public final ServerLevel component2() {
        return this.level;
    }

    @NotNull
    public final Vec3 component3() {
        return this.position;
    }

    @NotNull
    public final PokemonSentPreEvent copy(@NotNull Pokemon pokemon, @NotNull ServerLevel level, @NotNull Vec3 position) {
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        Intrinsics.checkNotNullParameter((Object)level, (String)"level");
        Intrinsics.checkNotNullParameter((Object)position, (String)"position");
        return new PokemonSentPreEvent(pokemon, level, position);
    }

    public static /* synthetic */ PokemonSentPreEvent copy$default(PokemonSentPreEvent pokemonSentPreEvent, Pokemon pokemon, ServerLevel serverLevel, Vec3 vec3, int n, Object object) {
        if ((n & 1) != 0) {
            pokemon = pokemonSentPreEvent.pokemon;
        }
        if ((n & 2) != 0) {
            serverLevel = pokemonSentPreEvent.level;
        }
        if ((n & 4) != 0) {
            vec3 = pokemonSentPreEvent.position;
        }
        return pokemonSentPreEvent.copy(pokemon, serverLevel, vec3);
    }

    @NotNull
    public String toString() {
        return "PokemonSentPreEvent(pokemon=" + this.pokemon + ", level=" + this.level + ", position=" + this.position + ")";
    }

    public int hashCode() {
        int result = this.pokemon.hashCode();
        result = result * 31 + this.level.hashCode();
        result = result * 31 + this.position.hashCode();
        return result;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof PokemonSentPreEvent)) {
            return false;
        }
        PokemonSentPreEvent pokemonSentPreEvent = (PokemonSentPreEvent)other;
        if (!Intrinsics.areEqual((Object)this.pokemon, (Object)pokemonSentPreEvent.pokemon)) {
            return false;
        }
        if (!Intrinsics.areEqual((Object)this.level, (Object)pokemonSentPreEvent.level)) {
            return false;
        }
        return Intrinsics.areEqual((Object)this.position, (Object)pokemonSentPreEvent.position);
    }
}

