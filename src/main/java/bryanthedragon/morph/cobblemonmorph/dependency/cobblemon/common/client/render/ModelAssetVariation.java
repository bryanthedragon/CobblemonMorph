/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.ModelLayer;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.ModelTextureSupplier;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010#\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001BM\u0012\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002\u0012\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u000e\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u000e\u0012\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\u0015\u0012\u0010\b\u0002\u0010\n\u001a\n\u0012\u0004\u0012\u00020\t\u0018\u00010\b\u00a2\u0006\u0004\b\u001a\u0010\u001bR\u001d\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007R\u001f\u0010\n\u001a\n\u0012\u0004\u0012\u00020\t\u0018\u00010\b8\u0006\u00a2\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\f\u0010\rR\u0019\u0010\u000f\u001a\u0004\u0018\u00010\u000e8\u0006\u00a2\u0006\f\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\u0011\u0010\u0012R\u0019\u0010\u0013\u001a\u0004\u0018\u00010\u000e8\u0006\u00a2\u0006\f\n\u0004\b\u0013\u0010\u0010\u001a\u0004\b\u0014\u0010\u0012R\u0019\u0010\u0016\u001a\u0004\u0018\u00010\u00158\u0006\u00a2\u0006\f\n\u0004\b\u0016\u0010\u0017\u001a\u0004\b\u0018\u0010\u0019\u00a8\u0006\u001c"}, d2={"Lcom/cobblemon/mod/common/client/render/ModelAssetVariation;", "", "", "", "aspects", "Ljava/util/Set;", "getAspects", "()Ljava/util/Set;", "", "Lcom/cobblemon/mod/common/client/render/ModelLayer;", "layers", "Ljava/util/List;", "getLayers", "()Ljava/util/List;", "Lnet/minecraft/resources/ResourceLocation;", "model", "Lnet/minecraft/resources/ResourceLocation;", "getModel", "()Lnet/minecraft/resources/ResourceLocation;", "poser", "getPoser", "Lcom/cobblemon/mod/common/client/render/ModelTextureSupplier;", "texture", "Lcom/cobblemon/mod/common/client/render/ModelTextureSupplier;", "getTexture", "()Lcom/cobblemon/mod/common/client/render/ModelTextureSupplier;", "<init>", "(Ljava/util/Set;Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/resources/ResourceLocation;Lcom/cobblemon/mod/common/client/render/ModelTextureSupplier;Ljava/util/List;)V", "common"})
public final class ModelAssetVariation {
    @NotNull
    private final Set<String> aspects;
    @Nullable
    private final ResourceLocation poser;
    @Nullable
    private final ResourceLocation model;
    @Nullable
    private final ModelTextureSupplier texture;
    @Nullable
    private final List<ModelLayer> layers;

    public ModelAssetVariation(@NotNull Set<String> aspects, @Nullable ResourceLocation poser, @Nullable ResourceLocation model, @Nullable ModelTextureSupplier texture, @Nullable List<ModelLayer> layers) {
        Intrinsics.checkNotNullParameter(aspects, (String)"aspects");
        this.aspects = aspects;
        this.poser = poser;
        this.model = model;
        this.texture = texture;
        this.layers = layers;
    }

    public /* synthetic */ ModelAssetVariation(Set set2, ResourceLocation resourceLocation, ResourceLocation resourceLocation2, ModelTextureSupplier modelTextureSupplier, List list, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 1) != 0) {
            set2 = new LinkedHashSet();
        }
        if ((n & 2) != 0) {
            resourceLocation = null;
        }
        if ((n & 4) != 0) {
            resourceLocation2 = null;
        }
        if ((n & 8) != 0) {
            modelTextureSupplier = null;
        }
        if ((n & 0x10) != 0) {
            list = null;
        }
        this(set2, resourceLocation, resourceLocation2, modelTextureSupplier, list);
    }

    @NotNull
    public final Set<String> getAspects() {
        return this.aspects;
    }

    @Nullable
    public final ResourceLocation getPoser() {
        return this.poser;
    }

    @Nullable
    public final ResourceLocation getModel() {
        return this.model;
    }

    @Nullable
    public final ModelTextureSupplier getTexture() {
        return this.texture;
    }

    @Nullable
    public final List<ModelLayer> getLayers() {
        return this.layers;
    }

    public ModelAssetVariation() {
        this(null, null, null, null, null, 31, null);
    }
}

