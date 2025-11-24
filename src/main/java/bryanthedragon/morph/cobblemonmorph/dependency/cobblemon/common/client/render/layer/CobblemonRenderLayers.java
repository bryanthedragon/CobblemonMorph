/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.DefaultVertexFormat
 *  com.mojang.blaze3d.vertex.VertexFormat
 *  com.mojang.blaze3d.vertex.VertexFormat$Mode
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.Util
 *  net.minecraft.client.renderer.RenderStateShard
 *  net.minecraft.client.renderer.RenderStateShard$EmptyTextureStateShard
 *  net.minecraft.client.renderer.RenderStateShard$TextureStateShard
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.RenderType$CompositeRenderType
 *  net.minecraft.client.renderer.RenderType$CompositeState
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.layer;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import java.util.function.BiFunction;
import java.util.function.Function;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.Util;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0007\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0015\u0010\u0016R\u001f\u0010\u0004\u001a\n \u0003*\u0004\u0018\u00010\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007R#\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\b8\u0006\u00a2\u0006\f\n\u0004\b\u000b\u0010\f\u001a\u0004\b\r\u0010\u000eR)\u0010\u0011\u001a\u0014\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\n0\u000f8\u0006\u00a2\u0006\f\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u0013\u0010\u0014\u00a8\u0006\u0017"}, d2={"Lcom/cobblemon/mod/common/client/render/layer/CobblemonRenderLayers;", "", "Lnet/minecraft/client/render/RenderLayer$MultiPhase;", "kotlin.jvm.PlatformType", "BERRY_LAYER", "Lnet/minecraft/client/renderer/RenderType$CompositeRenderType;", "getBERRY_LAYER", "()Lnet/minecraft/client/renderer/RenderType$CompositeRenderType;", "Ljava/util/function/Function;", "Lnet/minecraft/resources/ResourceLocation;", "Lnet/minecraft/client/renderer/RenderType;", "ENTITY_CUTOUT", "Ljava/util/function/Function;", "getENTITY_CUTOUT", "()Ljava/util/function/Function;", "Ljava/util/function/BiFunction;", "", "ENTITY_TRANSLUCENT", "Ljava/util/function/BiFunction;", "getENTITY_TRANSLUCENT", "()Ljava/util/function/BiFunction;", "<init>", "()V", "common"})
public final class CobblemonRenderLayers {
    @NotNull
    public static final CobblemonRenderLayers INSTANCE;
    private static final RenderType.CompositeRenderType BERRY_LAYER;
    @NotNull
    private static final BiFunction<ResourceLocation, Boolean, RenderType> ENTITY_TRANSLUCENT;
    @NotNull
    private static final Function<ResourceLocation, RenderType> ENTITY_CUTOUT;

    private CobblemonRenderLayers() {
    }

    public final RenderType.CompositeRenderType getBERRY_LAYER() {
        return BERRY_LAYER;
    }

    @NotNull
    public final BiFunction<ResourceLocation, Boolean, RenderType> getENTITY_TRANSLUCENT() {
        return ENTITY_TRANSLUCENT;
    }

    @NotNull
    public final Function<ResourceLocation, RenderType> getENTITY_CUTOUT() {
        return ENTITY_CUTOUT;
    }

    private static final RenderType ENTITY_TRANSLUCENT$lambda$1(ResourceLocation texture, boolean affectsOutline) {
        Intrinsics.checkNotNullParameter((Object)texture, (String)"texture");
        RenderType.CompositeState compositeState = RenderType.CompositeState.m_110628_().m_173292_(RenderStateShard.f_173066_).m_173290_((RenderStateShard.EmptyTextureStateShard)new RenderStateShard.TextureStateShard(texture, false, false)).m_110685_(RenderStateShard.f_110139_).m_110661_(RenderStateShard.f_110110_).m_110671_(RenderStateShard.f_110152_).m_110677_(RenderStateShard.f_110154_).m_110691_(affectsOutline);
        Intrinsics.checkNotNullExpressionValue((Object)compositeState, (String)"builder()\n              \u2026OR).build(affectsOutline)");
        RenderType.CompositeState multiPhaseParameters = compositeState;
        return (RenderType)RenderType.m_173215_((String)"entity_translucent", (VertexFormat)DefaultVertexFormat.f_85812_, (VertexFormat.Mode)VertexFormat.Mode.QUADS, (int)256, (boolean)true, (boolean)true, (RenderType.CompositeState)multiPhaseParameters);
    }

    private static final RenderType ENTITY_CUTOUT$lambda$2(ResourceLocation texture) {
        Intrinsics.checkNotNullParameter((Object)texture, (String)"texture");
        RenderType.CompositeState multiPhaseParameters = RenderType.CompositeState.m_110628_().m_173292_(RenderStateShard.f_173113_).m_173290_((RenderStateShard.EmptyTextureStateShard)new RenderStateShard.TextureStateShard(texture, false, false)).m_110685_(RenderStateShard.f_110134_).m_110671_(RenderStateShard.f_110152_).m_110677_(RenderStateShard.f_110154_).m_110691_(true);
        return (RenderType)RenderType.m_173215_((String)"entity_cutout", (VertexFormat)DefaultVertexFormat.f_85812_, (VertexFormat.Mode)VertexFormat.Mode.QUADS, (int)256, (boolean)true, (boolean)false, (RenderType.CompositeState)multiPhaseParameters);
    }

    static {
        CobblemonRenderLayers $this$BERRY_LAYER_u24lambda_u240 = INSTANCE = new CobblemonRenderLayers();
        boolean bl = false;
        RenderType.CompositeState multiPhaseParameters = RenderType.CompositeState.m_110628_().m_110671_(RenderStateShard.f_110152_).m_173292_(RenderStateShard.f_173107_).m_173290_((RenderStateShard.EmptyTextureStateShard)new RenderStateShard.TextureStateShard(MiscUtilsKt.cobblemonResource("textures/atlas/berries.png"), false, true)).m_110661_(RenderStateShard.f_110110_).m_110691_(true);
        BERRY_LAYER = RenderType.m_173215_((String)"berries", (VertexFormat)DefaultVertexFormat.f_85811_, (VertexFormat.Mode)VertexFormat.Mode.QUADS, (int)512, (boolean)true, (boolean)false, (RenderType.CompositeState)multiPhaseParameters);
        BiFunction biFunction = Util.m_143821_(CobblemonRenderLayers::ENTITY_TRANSLUCENT$lambda$1);
        Intrinsics.checkNotNullExpressionValue((Object)biFunction, (String)"memoize { texture: Ident\u2026arameters\n        )\n    }");
        ENTITY_TRANSLUCENT = biFunction;
        Function function = Util.m_143827_(CobblemonRenderLayers::ENTITY_CUTOUT$lambda$2);
        Intrinsics.checkNotNullExpressionValue((Object)function, (String)"memoize { texture: Ident\u2026arameters\n        )\n    }");
        ENTITY_CUTOUT = function;
    }
}

