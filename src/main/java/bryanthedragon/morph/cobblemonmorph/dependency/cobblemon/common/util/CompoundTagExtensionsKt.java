/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.nbt.CompoundTag
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonEntities;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=2, xi=48, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\u001a\u0011\u0010\u0002\u001a\u00020\u0001*\u00020\u0000\u00a2\u0006\u0004\b\u0002\u0010\u0003\u00a8\u0006\u0004"}, d2={"Lnet/minecraft/nbt/CompoundTag;", "", "isPokemonEntity", "(Lnet/minecraft/nbt/CompoundTag;)Z", "common"})
public final class CompoundTagExtensionsKt {
    public static final boolean isPokemonEntity(@NotNull CompoundTag $this$isPokemonEntity) {
        Intrinsics.checkNotNullParameter((Object)$this$isPokemonEntity, (String)"<this>");
        return $this$isPokemonEntity.m_128461_("id").equals(CobblemonEntities.POKEMON_KEY.toString());
    }
}

