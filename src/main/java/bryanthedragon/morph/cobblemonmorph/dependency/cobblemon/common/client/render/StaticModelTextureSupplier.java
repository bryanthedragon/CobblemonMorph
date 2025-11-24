/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.ModelTextureSupplier;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0007\u001a\u00020\u0004\u00a2\u0006\u0004\b\u000b\u0010\fJ\u0018\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0096\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0017\u0010\u0007\u001a\u00020\u00048\u0006\u00a2\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\t\u0010\n\u00a8\u0006\r"}, d2={"Lcom/cobblemon/mod/common/client/render/StaticModelTextureSupplier;", "Lcom/cobblemon/mod/common/client/render/ModelTextureSupplier;", "", "animationSeconds", "Lnet/minecraft/resources/ResourceLocation;", "invoke", "(F)Lnet/minecraft/resources/ResourceLocation;", "texture", "Lnet/minecraft/resources/ResourceLocation;", "getTexture", "()Lnet/minecraft/resources/ResourceLocation;", "<init>", "(Lnet/minecraft/resources/ResourceLocation;)V", "common"})
public final class StaticModelTextureSupplier
implements ModelTextureSupplier {
    @NotNull
    private final ResourceLocation texture;

    public StaticModelTextureSupplier(@NotNull ResourceLocation texture) {
        Intrinsics.checkNotNullParameter((Object)texture, (String)"texture");
        this.texture = texture;
    }

    @NotNull
    public final ResourceLocation getTexture() {
        return this.texture;
    }

    @Override
    @NotNull
    public ResourceLocation invoke(float animationSeconds) {
        return this.texture;
    }
}

