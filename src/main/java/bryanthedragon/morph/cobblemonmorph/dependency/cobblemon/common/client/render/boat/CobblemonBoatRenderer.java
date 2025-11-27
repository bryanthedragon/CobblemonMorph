/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.blaze3d.vertex.VertexConsumer
 *  com.mojang.math.Axis
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.TuplesKt
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.ranges.RangesKt
 *  net.minecraft.client.model.BoatModel
 *  net.minecraft.client.model.ChestBoatModel
 *  net.minecraft.client.model.geom.ModelLayerLocation
 *  net.minecraft.client.model.geom.ModelPart
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.entity.EntityRenderer
 *  net.minecraft.client.renderer.entity.EntityRendererProvider$Context
 *  net.minecraft.client.renderer.texture.OverlayTexture
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.vehicle.Boat
 *  org.jetbrains.annotations.NotNull
 *  org.joml.Quaternionf
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.boat;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.boat.CobblemonBoatEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.boat.CobblemonBoatType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.RangesKt;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.vehicle.Boat;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 !2\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001!B\u0017\u0012\u0006\u0010\u001e\u001a\u00020\u001d\u0012\u0006\u0010\u001b\u001a\u00020\u001a\u00a2\u0006\u0004\b\u001f\u0010 J\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006J?\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u00072\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u000eH\u0016\u00a2\u0006\u0004\b\u0011\u0010\u0012RH\u0010\u0018\u001a6\u0012\u0004\u0012\u00020\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00160\u00150\u0013j\u001a\u0012\u0004\u0012\u00020\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00160\u0015`\u00178\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0018\u0010\u0019R\u0014\u0010\u001b\u001a\u00020\u001a8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u001b\u0010\u001c\u00a8\u0006\""}, d2={"Lcom/cobblemon/mod/common/client/render/boat/CobblemonBoatRenderer;", "Lnet/minecraft/client/renderer/entity/EntityRenderer;", "Lcom/cobblemon/mod/common/entity/boat/CobblemonBoatEntity;", "entity", "Lnet/minecraft/resources/ResourceLocation;", "getTexture", "(Lcom/cobblemon/mod/common/entity/boat/CobblemonBoatEntity;)Lnet/minecraft/resources/ResourceLocation;", "", "yaw", "tickDelta", "Lcom/mojang/blaze3d/vertex/PoseStack;", "matrices", "Lnet/minecraft/client/renderer/MultiBufferSource;", "vertexConsumers", "", "light", "", "render", "(Lcom/cobblemon/mod/common/entity/boat/CobblemonBoatEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", "Ljava/util/HashMap;", "Lcom/cobblemon/mod/common/entity/boat/CobblemonBoatType;", "Lkotlin/Pair;", "Lnet/minecraft/client/model/BoatModel;", "Lkotlin/collections/HashMap;", "boatModels", "Ljava/util/HashMap;", "", "hasChest", "Z", "Lnet/minecraft/client/render/entity/EntityRendererFactory$Context;", "ctx", "<init>", "(Lnet/minecraft/client/renderer/entity/EntityRendererProvider$Context;Z)V", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nCobblemonBoatRenderer.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CobblemonBoatRenderer.kt\ncom/cobblemon/mod/common/client/render/boat/CobblemonBoatRenderer\n+ 2 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n*L\n1#1,90:1\n13579#2,2:91\n*S KotlinDebug\n*F\n+ 1 CobblemonBoatRenderer.kt\ncom/cobblemon/mod/common/client/render/boat/CobblemonBoatRenderer\n*L\n34#1:91,2\n*E\n"})
public final class CobblemonBoatRenderer
extends EntityRenderer<CobblemonBoatEntity> {
    @NotNull
    public static final Companion Companion = new Companion(null);
    private final boolean hasChest;
    @NotNull
    private final HashMap<CobblemonBoatType, Pair<ResourceLocation, BoatModel>> boatModels;

    public CobblemonBoatRenderer(@NotNull EntityRendererProvider.Context ctx, boolean hasChest) {
        Intrinsics.checkNotNullParameter((Object)ctx, (String)"ctx");
        super(ctx);
        this.hasChest = hasChest;
        this.boatModels = new HashMap();
        this.f_114477_ = 0.8f;
        CobblemonBoatType[] $this$forEach$iv = CobblemonBoatType.values();
        boolean $i$f$forEach = false;
        int n = $this$forEach$iv.length;
        for (int i = 0; i < n; ++i) {
            CobblemonBoatType element$iv;
            CobblemonBoatType type = element$iv = $this$forEach$iv[i];
            boolean bl = false;
            ((Map)this.boatModels).put(type, TuplesKt.to((Object)CobblemonBoatRenderer.Companion.generateTextureIdentifier(type, this.hasChest), (Object)CobblemonBoatRenderer.Companion.generateBoatModel(ctx, type, this.hasChest)));
        }
    }

    @NotNull
    public ResourceLocation getTexture(@NotNull CobblemonBoatEntity entity2) {
        Intrinsics.checkNotNullParameter((Object)((Object)entity2), (String)"entity");
        Pair<ResourceLocation, BoatModel> pair = this.boatModels.get((Object)entity2.getBoatType());
        Intrinsics.checkNotNull(pair);
        return (ResourceLocation)pair.getFirst();
    }

    public void render(@NotNull CobblemonBoatEntity entity2, float yaw, float tickDelta, @NotNull PoseStack matrices, @NotNull MultiBufferSource vertexConsumers, int light) {
        float k;
        Intrinsics.checkNotNullParameter((Object)((Object)entity2), (String)"entity");
        Intrinsics.checkNotNullParameter((Object)matrices, (String)"matrices");
        Intrinsics.checkNotNullParameter((Object)vertexConsumers, (String)"vertexConsumers");
        matrices.m_85836_();
        matrices.m_252880_(0.0f, 0.375f, 0.0f);
        matrices.m_252781_(Axis.f_252436_.m_252977_(180.0f - yaw));
        float h = (float)entity2.m_38385_() - tickDelta;
        float j = RangesKt.coerceAtLeast((float)(entity2.m_38384_() - tickDelta), (float)0.0f);
        if (h > 0.0f) {
            matrices.m_252781_(Axis.f_252529_.m_252977_(Mth.m_14031_((float)h) * h * j / 10.0f * (float)entity2.m_38386_()));
        }
        if (!Mth.m_14033_((float)(k = entity2.m_38352_(tickDelta)), (float)0.0f)) {
            matrices.m_252781_(new Quaternionf().setAngleAxis(entity2.m_38352_(tickDelta) * ((float)Math.PI / 180), 1.0f, 0.0f, 1.0f));
        }
        Pair<ResourceLocation, BoatModel> pair = this.boatModels.get((Object)entity2.getBoatType());
        Intrinsics.checkNotNull(pair);
        Pair<ResourceLocation, BoatModel> pair2 = pair;
        ResourceLocation identifier = (ResourceLocation)pair2.component1();
        BoatModel entityModel = (BoatModel)pair2.component2();
        matrices.m_85841_(-1.0f, -1.0f, 1.0f);
        matrices.m_252781_(Axis.f_252436_.m_252977_(90.0f));
        entityModel.m_6973_((Boat)entity2, tickDelta, 0.0f, -0.1f, 0.0f, 0.0f);
        VertexConsumer vertexConsumer = vertexConsumers.m_6299_(entityModel.m_103119_(identifier));
        entityModel.m_7695_(matrices, vertexConsumer, light, OverlayTexture.f_118083_, 1.0f, 1.0f, 1.0f, 1.0f);
        if (!entity2.m_5842_()) {
            VertexConsumer vertexConsumer2 = vertexConsumers.m_6299_(RenderType.m_110478_());
            entityModel.m_102282_().m_104301_(matrices, vertexConsumer2, light, OverlayTexture.f_118083_);
        }
        matrices.m_85849_();
        super.m_7392_((Entity)entity2, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0012\u0010\u0013J\u001f\u0010\t\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0000\u00a2\u0006\u0004\b\u0007\u0010\bJ'\u0010\r\u001a\u00020\f2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0002\u00a2\u0006\u0004\b\r\u0010\u000eJ\u001f\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0002\u00a2\u0006\u0004\b\u0010\u0010\u0011\u00a8\u0006\u0014"}, d2={"Lcom/cobblemon/mod/common/client/render/boat/CobblemonBoatRenderer$Companion;", "", "Lcom/cobblemon/mod/common/entity/boat/CobblemonBoatType;", "type", "", "hasChest", "Lnet/minecraft/client/model/geom/ModelLayerLocation;", "createBoatModelLayer$common", "(Lcom/cobblemon/mod/common/entity/boat/CobblemonBoatType;Z)Lnet/minecraft/client/model/geom/ModelLayerLocation;", "createBoatModelLayer", "Lnet/minecraft/client/render/entity/EntityRendererFactory$Context;", "ctx", "Lnet/minecraft/client/model/BoatModel;", "generateBoatModel", "(Lnet/minecraft/client/renderer/entity/EntityRendererProvider$Context;Lcom/cobblemon/mod/common/entity/boat/CobblemonBoatType;Z)Lnet/minecraft/client/model/BoatModel;", "Lnet/minecraft/resources/ResourceLocation;", "generateTextureIdentifier", "(Lcom/cobblemon/mod/common/entity/boat/CobblemonBoatType;Z)Lnet/minecraft/resources/ResourceLocation;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        private final ResourceLocation generateTextureIdentifier(CobblemonBoatType type, boolean hasChest) {
            String boatSubPath = hasChest ? "chest_boat" : "boat";
            String string = type.name().toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue((Object)string, (String)"this as java.lang.String).toLowerCase(Locale.ROOT)");
            String path = "textures/entity/" + boatSubPath + "/" + string + ".png";
            return MiscUtils.cobblemonResource(path);
        }

        private final BoatModel generateBoatModel(EntityRendererProvider.Context ctx, CobblemonBoatType type, boolean hasChest) {
            ModelLayerLocation modelLayer = this.createBoatModelLayer$common(type, hasChest);
            ModelPart modelPart = ctx.m_174023_(modelLayer);
            return hasChest ? (BoatModel)new ChestBoatModel(modelPart) : new BoatModel(modelPart);
        }

        @NotNull
        public final ModelLayerLocation createBoatModelLayer$common(@NotNull CobblemonBoatType type, boolean hasChest) {
            Intrinsics.checkNotNullParameter((Object)((Object)type), (String)"type");
            String boatSubPath = hasChest ? "chest_boat" : "boat";
            String string = type.name().toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue((Object)string, (String)"this as java.lang.String).toLowerCase(Locale.ROOT)");
            String path = boatSubPath + "/" + string;
            return new ModelLayerLocation(MiscUtils.cobblemonResource(path), "main");
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

