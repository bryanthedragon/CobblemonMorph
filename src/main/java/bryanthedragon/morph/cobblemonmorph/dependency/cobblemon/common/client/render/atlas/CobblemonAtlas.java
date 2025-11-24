/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.client.renderer.texture.TextureManager
 *  net.minecraft.client.resources.TextureAtlasHolder
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.atlas;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.TextureAtlasHolder;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0004\u0012\u0006\u0010\u0006\u001a\u00020\u0004\u00a2\u0006\u0004\b\u0007\u0010\b\u00a8\u0006\t"}, d2={"Lcom/cobblemon/mod/common/client/render/atlas/CobblemonAtlas;", "Lnet/minecraft/client/resources/TextureAtlasHolder;", "Lnet/minecraft/client/renderer/texture/TextureManager;", "textureManager", "Lnet/minecraft/resources/ResourceLocation;", "atlasId", "sourcePath", "<init>", "(Lnet/minecraft/client/renderer/texture/TextureManager;Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/resources/ResourceLocation;)V", "common"})
public final class CobblemonAtlas
extends TextureAtlasHolder {
    public CobblemonAtlas(@NotNull TextureManager textureManager, @NotNull ResourceLocation atlasId, @NotNull ResourceLocation sourcePath) {
        Intrinsics.checkNotNullParameter((Object)textureManager, (String)"textureManager");
        Intrinsics.checkNotNullParameter((Object)atlasId, (String)"atlasId");
        Intrinsics.checkNotNullParameter((Object)sourcePath, (String)"sourcePath");
        super(textureManager, atlasId, sourcePath);
    }
}

