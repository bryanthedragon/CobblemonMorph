/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.annotations.SerializedName
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.ModelAssetVariation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B+\u0012\b\b\u0002\u0010\u0003\u001a\u00020\u0002\u0012\b\b\u0002\u0010\b\u001a\u00020\u0007\u0012\u000e\b\u0002\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\r0\f\u00a2\u0006\u0004\b\u0012\u0010\u0013R\u001a\u0010\u0003\u001a\u00020\u00028\u0006X\u0087\u0004\u00a2\u0006\f\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006R\u0017\u0010\b\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000bR\u001d\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\r0\f8\u0006\u00a2\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\u0010\u0010\u0011\u00a8\u0006\u0014"}, d2={"Lcom/cobblemon/mod/common/client/render/ModelVariationSet;", "", "Lnet/minecraft/resources/ResourceLocation;", "name", "Lnet/minecraft/resources/ResourceLocation;", "getName", "()Lnet/minecraft/resources/ResourceLocation;", "", "order", "I", "getOrder", "()I", "", "Lcom/cobblemon/mod/common/client/render/ModelAssetVariation;", "variations", "Ljava/util/List;", "getVariations", "()Ljava/util/List;", "<init>", "(Lnet/minecraft/resources/ResourceLocation;ILjava/util/List;)V", "common"})
public final class ModelVariationSet {
    @SerializedName(value="name", alternate={"species", "pokeball"})
    @NotNull
    private final ResourceLocation name;
    private final int order;
    @NotNull
    private final List<ModelAssetVariation> variations;

    public ModelVariationSet(@NotNull ResourceLocation name, int order, @NotNull List<ModelAssetVariation> variations) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        Intrinsics.checkNotNullParameter(variations, (String)"variations");
        this.name = name;
        this.order = order;
        this.variations = variations;
    }

    public /* synthetic */ ModelVariationSet(ResourceLocation resourceLocation, int n, List list, int n2, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n2 & 1) != 0) {
            resourceLocation = MiscUtilsKt.cobblemonResource("thing");
        }
        if ((n2 & 2) != 0) {
            n = 0;
        }
        if ((n2 & 4) != 0) {
            list = new ArrayList();
        }
        this(resourceLocation, n, list);
    }

    @NotNull
    public final ResourceLocation getName() {
        return this.name;
    }

    public final int getOrder() {
        return this.order;
    }

    @NotNull
    public final List<ModelAssetVariation> getVariations() {
        return this.variations;
    }

    public ModelVariationSet() {
        this(null, 0, null, 7, null);
    }
}

