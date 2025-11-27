/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion;

import kotlin.jvm.internal.Intrinsics;

import net.minecraft.resources.ResourceLocation;

import org.jetbrains.annotations.NotNull;

public class PokemonInteractContext {
    @NotNull
    private ResourceLocation type;
    @NotNull
    private ResourceLocation item;

    public PokemonInteractContext(@NotNull ResourceLocation type, @NotNull ResourceLocation item) {
        Intrinsics.checkNotNullParameter((Object)type, (String)"type");
        Intrinsics.checkNotNullParameter((Object)item, (String)"item");
        this.type = type;
        this.item = item;
    }

    @NotNull
    public final ResourceLocation getType() {
        return this.type;
    }

    public final void setType(@NotNull ResourceLocation resourceLocation) {
        Intrinsics.checkNotNullParameter((Object)resourceLocation, (String)"<set-?>");
        this.type = resourceLocation;
    }

    @NotNull
    public final ResourceLocation getItem() {
        return this.item;
    }

    public final void setItem(@NotNull ResourceLocation resourceLocation) {
        Intrinsics.checkNotNullParameter((Object)resourceLocation, (String)"<set-?>");
        this.item = resourceLocation;
    }
}

