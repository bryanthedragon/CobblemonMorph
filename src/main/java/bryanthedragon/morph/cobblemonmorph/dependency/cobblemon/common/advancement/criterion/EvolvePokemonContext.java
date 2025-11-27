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

public class EvolvePokemonContext extends CountableContext {
    @NotNull
    private final ResourceLocation species;
    @NotNull
    private final ResourceLocation evolution;

    public EvolvePokemonContext(@NotNull ResourceLocation species, @NotNull ResourceLocation evolution, int times2) {
        super(times2);
        Intrinsics.checkNotNullParameter((Object)species, (String)"species");
        Intrinsics.checkNotNullParameter((Object)evolution, (String)"evolution");
        this.species = species;
        this.evolution = evolution;
    }

    @NotNull
    public final ResourceLocation getSpecies() {
        return this.species;
    }

    @NotNull
    public final ResourceLocation getEvolution() {
        return this.evolution;
    }
}

