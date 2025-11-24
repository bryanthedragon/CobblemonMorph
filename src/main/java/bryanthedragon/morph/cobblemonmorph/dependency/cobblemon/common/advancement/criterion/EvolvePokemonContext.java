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

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.CountableContext;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0004\b\u0016\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0007\u001a\u00020\u0002\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\n\u001a\u00020\t\u00a2\u0006\u0004\b\u000b\u0010\fR\u0017\u0010\u0003\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006R\u0017\u0010\u0007\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0007\u0010\u0004\u001a\u0004\b\b\u0010\u0006\u00a8\u0006\r"}, d2={"Lcom/cobblemon/mod/common/advancement/criterion/EvolvePokemonContext;", "Lcom/cobblemon/mod/common/advancement/criterion/CountableContext;", "Lnet/minecraft/resources/ResourceLocation;", "evolution", "Lnet/minecraft/resources/ResourceLocation;", "getEvolution", "()Lnet/minecraft/resources/ResourceLocation;", "species", "getSpecies", "", "times", "<init>", "(Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/resources/ResourceLocation;I)V", "common"})
public class EvolvePokemonContext
extends CountableContext {
    @NotNull
    private final ResourceLocation species;
    @NotNull
    private final ResourceLocation evolution;

    public EvolvePokemonContext(@NotNull ResourceLocation species, @NotNull ResourceLocation evolution, int times2) {
        Intrinsics.checkNotNullParameter((Object)species, (String)"species");
        Intrinsics.checkNotNullParameter((Object)evolution, (String)"evolution");
        super(times2);
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

