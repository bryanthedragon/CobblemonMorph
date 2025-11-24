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
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.activestate;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.activestate.PokemonState;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0001\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0007\u00a2\u0006\u0004\b\u0007\u0010\bJ\u0019\u0010\u0005\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006\u00a8\u0006\t"}, d2={"Lcom/cobblemon/mod/common/pokemon/activestate/InactivePokemonState;", "Lcom/cobblemon/mod/common/pokemon/activestate/PokemonState;", "Lnet/minecraft/nbt/CompoundTag;", "nbt", "", "writeToNBT", "(Lnet/minecraft/nbt/CompoundTag;)Ljava/lang/Void;", "<init>", "()V", "common"})
public final class InactivePokemonState
extends PokemonState {
    public InactivePokemonState() {
        super(null);
    }

    @Nullable
    public Void writeToNBT(@NotNull CompoundTag nbt) {
        Intrinsics.checkNotNullParameter((Object)nbt, (String)"nbt");
        return null;
    }
}

