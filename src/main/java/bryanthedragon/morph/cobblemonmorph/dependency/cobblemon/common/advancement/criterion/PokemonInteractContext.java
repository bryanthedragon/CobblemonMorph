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

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\f\b\u0016\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\t\u001a\u00020\u0002\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\f\u0010\rR\"\u0010\u0003\u001a\u00020\u00028\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\"\u0010\t\u001a\u00020\u00028\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\t\u0010\u0004\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\b\u00a8\u0006\u000e"}, d2={"Lcom/cobblemon/mod/common/advancement/criterion/PokemonInteractContext;", "", "Lnet/minecraft/resources/ResourceLocation;", "item", "Lnet/minecraft/resources/ResourceLocation;", "getItem", "()Lnet/minecraft/resources/ResourceLocation;", "setItem", "(Lnet/minecraft/resources/ResourceLocation;)V", "type", "getType", "setType", "<init>", "(Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/resources/ResourceLocation;)V", "common"})
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

