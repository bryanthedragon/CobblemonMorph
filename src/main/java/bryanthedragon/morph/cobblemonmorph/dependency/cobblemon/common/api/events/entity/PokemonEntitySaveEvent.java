/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.nbt.CompoundTag
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.entity;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\t\b\u0086\b\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\b\u001a\u00020\u0002\u0012\u0006\u0010\t\u001a\u00020\u0005\u00a2\u0006\u0004\b\u001a\u0010\u001bJ\u0010\u0010\u0003\u001a\u00020\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u0010\u0010\u0006\u001a\u00020\u0005H\u00c6\u0003\u00a2\u0006\u0004\b\u0006\u0010\u0007J$\u0010\n\u001a\u00020\u00002\b\b\u0002\u0010\b\u001a\u00020\u00022\b\b\u0002\u0010\t\u001a\u00020\u0005H\u00c6\u0001\u00a2\u0006\u0004\b\n\u0010\u000bJ\u001a\u0010\u000e\u001a\u00020\r2\b\u0010\f\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u0010\u0010\u0011\u001a\u00020\u0010H\u00d6\u0001\u00a2\u0006\u0004\b\u0011\u0010\u0012J\u0010\u0010\u0014\u001a\u00020\u0013H\u00d6\u0001\u00a2\u0006\u0004\b\u0014\u0010\u0015R\u0017\u0010\t\u001a\u00020\u00058\u0006\u00a2\u0006\f\n\u0004\b\t\u0010\u0016\u001a\u0004\b\u0017\u0010\u0007R\u0017\u0010\b\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\u0018\u001a\u0004\b\u0019\u0010\u0004\u00a8\u0006\u001c"}, d2={"Lcom/cobblemon/mod/common/api/events/entity/PokemonEntitySaveEvent;", "", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "component1", "()Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "Lnet/minecraft/nbt/CompoundTag;", "component2", "()Lnet/minecraft/nbt/CompoundTag;", "pokemonEntity", "nbt", "copy", "(Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;Lnet/minecraft/nbt/CompoundTag;)Lcom/cobblemon/mod/common/api/events/entity/PokemonEntitySaveEvent;", "other", "", "equals", "(Ljava/lang/Object;)Z", "", "hashCode", "()I", "", "toString", "()Ljava/lang/String;", "Lnet/minecraft/nbt/CompoundTag;", "getNbt", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "getPokemonEntity", "<init>", "(Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;Lnet/minecraft/nbt/CompoundTag;)V", "common"})
public final class PokemonEntitySaveEvent {
    @NotNull
    private final PokemonEntity pokemonEntity;
    @NotNull
    private final CompoundTag nbt;

    public PokemonEntitySaveEvent(@NotNull PokemonEntity pokemonEntity, @NotNull CompoundTag nbt) {
        Intrinsics.checkNotNullParameter((Object)pokemonEntity, (String)"pokemonEntity");
        Intrinsics.checkNotNullParameter((Object)nbt, (String)"nbt");
        this.pokemonEntity = pokemonEntity;
        this.nbt = nbt;
    }

    @NotNull
    public final PokemonEntity getPokemonEntity() {
        return this.pokemonEntity;
    }

    @NotNull
    public final CompoundTag getNbt() {
        return this.nbt;
    }

    @NotNull
    public final PokemonEntity component1() {
        return this.pokemonEntity;
    }

    @NotNull
    public final CompoundTag component2() {
        return this.nbt;
    }

    @NotNull
    public final PokemonEntitySaveEvent copy(@NotNull PokemonEntity pokemonEntity, @NotNull CompoundTag nbt) {
        Intrinsics.checkNotNullParameter((Object)pokemonEntity, (String)"pokemonEntity");
        Intrinsics.checkNotNullParameter((Object)nbt, (String)"nbt");
        return new PokemonEntitySaveEvent(pokemonEntity, nbt);
    }

    public static /* synthetic */ PokemonEntitySaveEvent copy$default(PokemonEntitySaveEvent pokemonEntitySaveEvent, PokemonEntity pokemonEntity, CompoundTag compoundTag, int n, Object object) {
        if ((n & 1) != 0) {
            pokemonEntity = pokemonEntitySaveEvent.pokemonEntity;
        }
        if ((n & 2) != 0) {
            compoundTag = pokemonEntitySaveEvent.nbt;
        }
        return pokemonEntitySaveEvent.copy(pokemonEntity, compoundTag);
    }

    @NotNull
    public String toString() {
        return "PokemonEntitySaveEvent(pokemonEntity=" + this.pokemonEntity + ", nbt=" + this.nbt + ")";
    }

    public int hashCode() {
        int result = this.pokemonEntity.hashCode();
        result = result * 31 + this.nbt.hashCode();
        return result;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof PokemonEntitySaveEvent)) {
            return false;
        }
        PokemonEntitySaveEvent pokemonEntitySaveEvent = (PokemonEntitySaveEvent)other;
        if (!Intrinsics.areEqual((Object)this.pokemonEntity, (Object)pokemonEntitySaveEvent.pokemonEntity)) {
            return false;
        }
        return Intrinsics.areEqual((Object)this.nbt, (Object)pokemonEntitySaveEvent.nbt);
    }
}

