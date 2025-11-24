/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.VertexFormat
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.packs.resources.ResourceProvider
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util;

import com.mojang.blaze3d.vertex.VertexFormat;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u000b\b\u0086\b\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u000b\u001a\u00020\u0002\u0012\u0006\u0010\f\u001a\u00020\u0005\u0012\u0006\u0010\r\u001a\u00020\b\u00a2\u0006\u0004\b \u0010!J\u0010\u0010\u0003\u001a\u00020\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u0010\u0010\u0006\u001a\u00020\u0005H\u00c6\u0003\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u0010\u0010\t\u001a\u00020\bH\u00c6\u0003\u00a2\u0006\u0004\b\t\u0010\nJ.\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u000b\u001a\u00020\u00022\b\b\u0002\u0010\f\u001a\u00020\u00052\b\b\u0002\u0010\r\u001a\u00020\bH\u00c6\u0001\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u001a\u0010\u0012\u001a\u00020\u00112\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003\u00a2\u0006\u0004\b\u0012\u0010\u0013J\u0010\u0010\u0015\u001a\u00020\u0014H\u00d6\u0001\u00a2\u0006\u0004\b\u0015\u0010\u0016J\u0010\u0010\u0018\u001a\u00020\u0017H\u00d6\u0001\u00a2\u0006\u0004\b\u0018\u0010\u0019R\u0017\u0010\u000b\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u000b\u0010\u001a\u001a\u0004\b\u001b\u0010\u0004R\u0017\u0010\f\u001a\u00020\u00058\u0006\u00a2\u0006\f\n\u0004\b\f\u0010\u001c\u001a\u0004\b\u001d\u0010\u0007R\u0017\u0010\r\u001a\u00020\b8\u0006\u00a2\u0006\f\n\u0004\b\r\u0010\u001e\u001a\u0004\b\u001f\u0010\n\u00a8\u0006\""}, d2={"Lcom/cobblemon/mod/common/util/ShaderRegistryData;", "", "Lnet/minecraft/server/packs/resources/ResourceProvider;", "component1", "()Lnet/minecraft/server/packs/resources/ResourceProvider;", "Lnet/minecraft/resources/ResourceLocation;", "component2", "()Lnet/minecraft/resources/ResourceLocation;", "Lcom/mojang/blaze3d/vertex/VertexFormat;", "component3", "()Lcom/mojang/blaze3d/vertex/VertexFormat;", "resourceFactory", "shaderName", "vertexFormat", "copy", "(Lnet/minecraft/server/packs/resources/ResourceProvider;Lnet/minecraft/resources/ResourceLocation;Lcom/mojang/blaze3d/vertex/VertexFormat;)Lcom/cobblemon/mod/common/util/ShaderRegistryData;", "other", "", "equals", "(Ljava/lang/Object;)Z", "", "hashCode", "()I", "", "toString", "()Ljava/lang/String;", "Lnet/minecraft/server/packs/resources/ResourceProvider;", "getResourceFactory", "Lnet/minecraft/resources/ResourceLocation;", "getShaderName", "Lcom/mojang/blaze3d/vertex/VertexFormat;", "getVertexFormat", "<init>", "(Lnet/minecraft/server/packs/resources/ResourceProvider;Lnet/minecraft/resources/ResourceLocation;Lcom/mojang/blaze3d/vertex/VertexFormat;)V", "common"})
public final class ShaderRegistryData {
    @NotNull
    private final ResourceProvider resourceFactory;
    @NotNull
    private final ResourceLocation shaderName;
    @NotNull
    private final VertexFormat vertexFormat;

    public ShaderRegistryData(@NotNull ResourceProvider resourceFactory, @NotNull ResourceLocation shaderName, @NotNull VertexFormat vertexFormat) {
        Intrinsics.checkNotNullParameter((Object)resourceFactory, (String)"resourceFactory");
        Intrinsics.checkNotNullParameter((Object)shaderName, (String)"shaderName");
        Intrinsics.checkNotNullParameter((Object)vertexFormat, (String)"vertexFormat");
        this.resourceFactory = resourceFactory;
        this.shaderName = shaderName;
        this.vertexFormat = vertexFormat;
    }

    @NotNull
    public final ResourceProvider getResourceFactory() {
        return this.resourceFactory;
    }

    @NotNull
    public final ResourceLocation getShaderName() {
        return this.shaderName;
    }

    @NotNull
    public final VertexFormat getVertexFormat() {
        return this.vertexFormat;
    }

    @NotNull
    public final ResourceProvider component1() {
        return this.resourceFactory;
    }

    @NotNull
    public final ResourceLocation component2() {
        return this.shaderName;
    }

    @NotNull
    public final VertexFormat component3() {
        return this.vertexFormat;
    }

    @NotNull
    public final ShaderRegistryData copy(@NotNull ResourceProvider resourceFactory, @NotNull ResourceLocation shaderName, @NotNull VertexFormat vertexFormat) {
        Intrinsics.checkNotNullParameter((Object)resourceFactory, (String)"resourceFactory");
        Intrinsics.checkNotNullParameter((Object)shaderName, (String)"shaderName");
        Intrinsics.checkNotNullParameter((Object)vertexFormat, (String)"vertexFormat");
        return new ShaderRegistryData(resourceFactory, shaderName, vertexFormat);
    }

    public static /* synthetic */ ShaderRegistryData copy$default(ShaderRegistryData shaderRegistryData, ResourceProvider resourceProvider, ResourceLocation resourceLocation, VertexFormat vertexFormat, int n, Object object) {
        if ((n & 1) != 0) {
            resourceProvider = shaderRegistryData.resourceFactory;
        }
        if ((n & 2) != 0) {
            resourceLocation = shaderRegistryData.shaderName;
        }
        if ((n & 4) != 0) {
            vertexFormat = shaderRegistryData.vertexFormat;
        }
        return shaderRegistryData.copy(resourceProvider, resourceLocation, vertexFormat);
    }

    @NotNull
    public String toString() {
        return "ShaderRegistryData(resourceFactory=" + this.resourceFactory + ", shaderName=" + this.shaderName + ", vertexFormat=" + this.vertexFormat + ")";
    }

    public int hashCode() {
        int result = this.resourceFactory.hashCode();
        result = result * 31 + this.shaderName.hashCode();
        result = result * 31 + this.vertexFormat.hashCode();
        return result;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ShaderRegistryData)) {
            return false;
        }
        ShaderRegistryData shaderRegistryData = (ShaderRegistryData)other;
        if (!Intrinsics.areEqual((Object)this.resourceFactory, (Object)shaderRegistryData.resourceFactory)) {
            return false;
        }
        if (!Intrinsics.areEqual((Object)this.shaderName, (Object)shaderRegistryData.shaderName)) {
            return false;
        }
        return Intrinsics.areEqual((Object)this.vertexFormat, (Object)shaderRegistryData.vertexFormat);
    }
}

