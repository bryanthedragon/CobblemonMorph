/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.resources.model.BakedModel
 *  net.minecraft.client.resources.model.ModelResourceLocation
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\t\b\u0086\b\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\b\u001a\u00020\u0002\u0012\u0006\u0010\t\u001a\u00020\u0005\u00a2\u0006\u0004\b\u001d\u0010\u001eJ\u0010\u0010\u0003\u001a\u00020\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u0010\u0010\u0006\u001a\u00020\u0005H\u00c6\u0003\u00a2\u0006\u0004\b\u0006\u0010\u0007J$\u0010\n\u001a\u00020\u00002\b\b\u0002\u0010\b\u001a\u00020\u00022\b\b\u0002\u0010\t\u001a\u00020\u0005H\u00c6\u0001\u00a2\u0006\u0004\b\n\u0010\u000bJ\u001a\u0010\u000e\u001a\u00020\r2\b\u0010\f\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\r\u0010\u0011\u001a\u00020\u0010\u00a2\u0006\u0004\b\u0011\u0010\u0012J\u0010\u0010\u0014\u001a\u00020\u0013H\u00d6\u0001\u00a2\u0006\u0004\b\u0014\u0010\u0015J\u0010\u0010\u0017\u001a\u00020\u0016H\u00d6\u0001\u00a2\u0006\u0004\b\u0017\u0010\u0018R\u0017\u0010\t\u001a\u00020\u00058\u0006\u00a2\u0006\f\n\u0004\b\t\u0010\u0019\u001a\u0004\b\u001a\u0010\u0007R\u0017\u0010\b\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\u001b\u001a\u0004\b\u001c\u0010\u0004\u00a8\u0006\u001f"}, d2={"Lcom/cobblemon/mod/common/BakingOverride;", "", "Lnet/minecraft/resources/ResourceLocation;", "component1", "()Lnet/minecraft/resources/ResourceLocation;", "Lnet/minecraft/client/resources/model/ModelResourceLocation;", "component2", "()Lnet/minecraft/client/resources/model/ModelResourceLocation;", "modelLocation", "modelIdentifier", "copy", "(Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/client/resources/model/ModelResourceLocation;)Lcom/cobblemon/mod/common/BakingOverride;", "other", "", "equals", "(Ljava/lang/Object;)Z", "Lnet/minecraft/client/resources/model/BakedModel;", "getModel", "()Lnet/minecraft/client/resources/model/BakedModel;", "", "hashCode", "()I", "", "toString", "()Ljava/lang/String;", "Lnet/minecraft/client/resources/model/ModelResourceLocation;", "getModelIdentifier", "Lnet/minecraft/resources/ResourceLocation;", "getModelLocation", "<init>", "(Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/client/resources/model/ModelResourceLocation;)V", "common"})
public final class BakingOverride {
    @NotNull
    private final ResourceLocation modelLocation;
    @NotNull
    private final ModelResourceLocation modelIdentifier;

    public BakingOverride(@NotNull ResourceLocation modelLocation, @NotNull ModelResourceLocation modelIdentifier) {
        Intrinsics.checkNotNullParameter((Object)modelLocation, (String)"modelLocation");
        Intrinsics.checkNotNullParameter((Object)modelIdentifier, (String)"modelIdentifier");
        this.modelLocation = modelLocation;
        this.modelIdentifier = modelIdentifier;
    }

    @NotNull
    public final ResourceLocation getModelLocation() {
        return this.modelLocation;
    }

    @NotNull
    public final ModelResourceLocation getModelIdentifier() {
        return this.modelIdentifier;
    }

    @NotNull
    public final BakedModel getModel() {
        BakedModel bakedModel = Minecraft.m_91087_().m_91304_().m_119422_(this.modelIdentifier);
        Intrinsics.checkNotNullExpressionValue((Object)bakedModel, (String)"getInstance().bakedModel\u2026getModel(modelIdentifier)");
        return bakedModel;
    }

    @NotNull
    public final ResourceLocation component1() {
        return this.modelLocation;
    }

    @NotNull
    public final ModelResourceLocation component2() {
        return this.modelIdentifier;
    }

    @NotNull
    public final BakingOverride copy(@NotNull ResourceLocation modelLocation, @NotNull ModelResourceLocation modelIdentifier) {
        Intrinsics.checkNotNullParameter((Object)modelLocation, (String)"modelLocation");
        Intrinsics.checkNotNullParameter((Object)modelIdentifier, (String)"modelIdentifier");
        return new BakingOverride(modelLocation, modelIdentifier);
    }

    public static /* synthetic */ BakingOverride copy$default(BakingOverride bakingOverride, ResourceLocation resourceLocation, ModelResourceLocation modelResourceLocation, int n, Object object) {
        if ((n & 1) != 0) {
            resourceLocation = bakingOverride.modelLocation;
        }
        if ((n & 2) != 0) {
            modelResourceLocation = bakingOverride.modelIdentifier;
        }
        return bakingOverride.copy(resourceLocation, modelResourceLocation);
    }

    @NotNull
    public String toString() {
        return "BakingOverride(modelLocation=" + this.modelLocation + ", modelIdentifier=" + this.modelIdentifier + ")";
    }

    public int hashCode() {
        int result = this.modelLocation.hashCode();
        result = result * 31 + this.modelIdentifier.hashCode();
        return result;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BakingOverride)) {
            return false;
        }
        BakingOverride bakingOverride = (BakingOverride)other;
        if (!Intrinsics.areEqual((Object)this.modelLocation, (Object)bakingOverride.modelLocation)) {
            return false;
        }
        return Intrinsics.areEqual((Object)this.modelIdentifier, (Object)bakingOverride.modelIdentifier);
    }
}

