/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.texture.TextureManager
 *  net.minecraft.client.resources.TextureAtlasHolder
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.atlas;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.atlas.CobblemonAtlas;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtils;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.TextureAtlasHolder;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010#\n\u0002\b\u0007\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0011\u0010\u0012J\u001d\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0006\u0010\u0007R\u0017\u0010\b\u001a\u00020\u00058\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000bR\u001d\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00050\f8\u0006\u00a2\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000f\u0010\u0010\u00a8\u0006\u0013"}, d2={"Lcom/cobblemon/mod/common/client/render/atlas/CobblemonAtlases;", "", "", "atlasId", "sourcePath", "Lnet/minecraft/client/resources/TextureAtlasHolder;", "register", "(Ljava/lang/String;Ljava/lang/String;)Lnet/minecraft/client/resources/TextureAtlasHolder;", "BERRY_SPRITE_ATLAS", "Lnet/minecraft/client/resources/TextureAtlasHolder;", "getBERRY_SPRITE_ATLAS", "()Lnet/minecraft/client/resources/TextureAtlasHolder;", "", "atlases", "Ljava/util/Set;", "getAtlases", "()Ljava/util/Set;", "<init>", "()V", "common"})
public final class CobblemonAtlases {
    @NotNull
    public static final CobblemonAtlases INSTANCE = new CobblemonAtlases();
    @NotNull
    private static final Set<TextureAtlasHolder> atlases = new LinkedHashSet();
    @NotNull
    private static final TextureAtlasHolder BERRY_SPRITE_ATLAS = INSTANCE.register("textures/atlas/berries.png", "berries");

    private CobblemonAtlases() {
    }

    @NotNull
    public final Set<TextureAtlasHolder> getAtlases() {
        return atlases;
    }

    @NotNull
    public final TextureAtlasHolder getBERRY_SPRITE_ATLAS() {
        return BERRY_SPRITE_ATLAS;
    }

    @NotNull
    public final TextureAtlasHolder register(@NotNull String atlasId, @NotNull String sourcePath) {
        Intrinsics.checkNotNullParameter((Object)atlasId, (String)"atlasId");
        Intrinsics.checkNotNullParameter((Object)sourcePath, (String)"sourcePath");
        TextureManager textureManager = Minecraft.m_91087_().m_91097_();
        Intrinsics.checkNotNullExpressionValue((Object)textureManager, (String)"getInstance().textureManager");
        CobblemonAtlas atlas = new CobblemonAtlas(textureManager, MiscUtils.cobblemonResource(atlasId), MiscUtils.cobblemonResource(sourcePath));
        atlases.add(atlas);
        return atlas;
    }
}

