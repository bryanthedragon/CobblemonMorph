/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.blaze3d.vertex.VertexConsumer
 *  com.mojang.math.Axis
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.Unit
 *  kotlin.collections.CollectionsKt
 *  kotlin.collections.SetsKt
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.ranges.RangesKt
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.block.model.BakedQuad
 *  net.minecraft.client.renderer.blockentity.BlockEntityRenderer
 *  net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider$Context
 *  net.minecraft.client.renderer.texture.OverlayTexture
 *  net.minecraft.client.resources.model.BakedModel
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Direction$Axis
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.HorizontalDirectionalBlock
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.block;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonBlocks;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.fossil.Fossil;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.multiblock.MultiblockStructure;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.RestorationTankBlockEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.multiblock.FossilMultiblockStructure;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonBakingOverrides;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.block.RestorationTankRenderer;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.fossil.FossilModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.fossil.FossilState;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Pose;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository.FossilModelRepository;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.wavefunction.WaveFunctionKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.RangesKt;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 \u00152\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u0015B\u000f\u0012\u0006\u0010\u0012\u001a\u00020\u0011\u00a2\u0006\u0004\b\u0013\u0010\u0014J?\u0010\u000e\u001a\u00020\r2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\nH\u0016\u00a2\u0006\u0004\b\u000e\u0010\u000fJ?\u0010\u0010\u001a\u00020\r2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\nH\u0002\u00a2\u0006\u0004\b\u0010\u0010\u000f\u00a8\u0006\u0016"}, d2={"Lcom/cobblemon/mod/common/client/render/block/RestorationTankRenderer;", "Lnet/minecraft/client/renderer/blockentity/BlockEntityRenderer;", "Lcom/cobblemon/mod/common/block/entity/RestorationTankBlockEntity;", "entity", "", "tickDelta", "Lcom/mojang/blaze3d/vertex/PoseStack;", "matrices", "Lnet/minecraft/client/renderer/MultiBufferSource;", "vertexConsumers", "", "light", "overlay", "", "render", "(Lcom/cobblemon/mod/common/block/entity/RestorationTankBlockEntity;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II)V", "renderFetus", "Lnet/minecraft/client/render/block/entity/BlockEntityRendererFactory$Context;", "ctx", "<init>", "(Lnet/minecraft/client/renderer/blockentity/BlockEntityRendererProvider$Context;)V", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nRestorationTankRenderer.kt\nKotlin\n*S Kotlin\n*F\n+ 1 RestorationTankRenderer.kt\ncom/cobblemon/mod/common/client/render/block/RestorationTankRenderer\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,202:1\n1855#2,2:203\n1855#2,2:205\n1855#2,2:207\n*S KotlinDebug\n*F\n+ 1 RestorationTankRenderer.kt\ncom/cobblemon/mod/common/client/render/block/RestorationTankRenderer\n*L\n58#1:203,2\n76#1:205,2\n123#1:207,2\n*E\n"})
public final class RestorationTankRenderer
implements BlockEntityRenderer<RestorationTankBlockEntity> {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private static final List<BakedModel> FLUID_MODELS;
    @NotNull
    private static final BakedModel CONNECTOR_MODEL;
    @NotNull
    private static final List<ResourceLocation> EMBRYO_IDENTIFIERS;
    @NotNull
    private static final Function1<Float, Float> EMBRYO_CURVE_1;
    @NotNull
    private static final Function1<Float, Float> EMBRYO_CURVE_2;
    @NotNull
    private static final Function1<Float, Float> EMBRYO_CURVE_3;
    @NotNull
    private static final Function1<Float, Float> FOSSIL_CURVE;

    public RestorationTankRenderer(@NotNull BlockEntityRendererProvider.Context ctx) {
        Intrinsics.checkNotNullParameter((Object)ctx, (String)"ctx");
    }

    public void render(@NotNull RestorationTankBlockEntity entity2, float tickDelta, @NotNull PoseStack matrices, @NotNull MultiBufferSource vertexConsumers, int light, int overlay2) {
        int fillLevel;
        Direction connectionDir;
        Intrinsics.checkNotNullParameter((Object)((Object)entity2), (String)"entity");
        Intrinsics.checkNotNullParameter((Object)matrices, (String)"matrices");
        Intrinsics.checkNotNullParameter((Object)vertexConsumers, (String)"vertexConsumers");
        if (entity2.getMultiblockStructure() == null) {
            return;
        }
        MultiblockStructure multiblockStructure = entity2.getMultiblockStructure();
        Intrinsics.checkNotNull((Object)multiblockStructure, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.multiblock.FossilMultiblockStructure");
        FossilMultiblockStructure struct2 = (FossilMultiblockStructure)multiblockStructure;
        Direction direction = connectionDir = struct2.getTankConnectorDirection();
        switch (direction == null ? -1 : WhenMappings.$EnumSwitchMapping$0[direction.ordinal()]) {
            case 1: {
                matrices.m_272245_(Axis.f_252436_.m_252977_(0.0f), 0.5f, 0.0f, 0.5f);
                break;
            }
            case 2: {
                matrices.m_272245_(Axis.f_252436_.m_252977_(270.0f), 0.5f, 0.0f, 0.5f);
                break;
            }
            case 3: {
                matrices.m_272245_(Axis.f_252436_.m_252977_(180.0f), 0.5f, 0.0f, 0.5f);
                break;
            }
            case 4: {
                matrices.m_272245_(Axis.f_252436_.m_252977_(90.0f), 0.5f, 0.0f, 0.5f);
            }
        }
        VertexConsumer cutoutBuffer = vertexConsumers.m_6299_(RenderType.m_110463_());
        if (connectionDir != null) {
            matrices.m_85836_();
            Level level = entity2.m_58904_();
            List list = CONNECTOR_MODEL.m_213637_(entity2.m_58900_(), null, (RandomSource)(level != null ? level.f_46441_ : null));
            Intrinsics.checkNotNullExpressionValue((Object)list, (String)"CONNECTOR_MODEL.getQuads\u2026ll, entity.world?.random)");
            Iterable $this$forEach$iv = list;
            boolean $i$f$forEach = false;
            for (Object element$iv : $this$forEach$iv) {
                BakedQuad quad = (BakedQuad)element$iv;
                boolean bl = false;
                cutoutBuffer.m_85987_(matrices.m_85850_(), quad, 0.75f, 0.75f, 0.75f, light, OverlayTexture.f_118083_);
            }
            matrices.m_85849_();
        }
        if ((fillLevel = struct2.getFillLevel()) == 0 && !struct2.getHasCreatedPokemon()) {
            return;
        }
        if (struct2.isRunning() | struct2.getHasCreatedPokemon()) {
            this.renderFetus(entity2, tickDelta, matrices, vertexConsumers, light, overlay2);
        }
        matrices.m_85836_();
        VertexConsumer transparentBuffer = vertexConsumers.m_6299_(RenderType.m_110466_());
        BakedModel fluidModel = struct2.isRunning() ? FLUID_MODELS.get(8) : (struct2.getHasCreatedPokemon() ? FLUID_MODELS.get(7) : FLUID_MODELS.get(RangesKt.coerceAtMost((int)fillLevel, (int)(FLUID_MODELS.size() - 1)) - 1));
        Level level = entity2.m_58904_();
        List list = fluidModel.m_213637_(entity2.m_58900_(), null, (RandomSource)(level != null ? level.f_46441_ : null));
        Intrinsics.checkNotNullExpressionValue((Object)list, (String)"fluidModel.getQuads(enti\u2026ll, entity.world?.random)");
        Iterable $this$forEach$iv = list;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            BakedQuad quad = (BakedQuad)element$iv;
            boolean bl = false;
            VertexConsumer vertexConsumer = transparentBuffer;
            if (vertexConsumer == null) continue;
            vertexConsumer.m_85987_(matrices.m_85850_(), quad, 0.75f, 0.75f, 0.75f, light, OverlayTexture.f_118083_);
        }
        matrices.m_85849_();
    }

    private final void renderFetus(RestorationTankBlockEntity entity2, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay2) {
        MultiblockStructure multiblockStructure = entity2.getMultiblockStructure();
        FossilMultiblockStructure fossilMultiblockStructure = multiblockStructure instanceof FossilMultiblockStructure ? (FossilMultiblockStructure)multiblockStructure : null;
        if (fossilMultiblockStructure == null) {
            return;
        }
        FossilMultiblockStructure struc = fossilMultiblockStructure;
        Fossil fossil = struc.getResultingFossil();
        if (fossil == null) {
            return;
        }
        Fossil fossil2 = fossil;
        int timeRemaining = struc.getTimeRemaining();
        Level level = entity2.m_58904_();
        BlockState blockState = level != null ? level.m_8055_(entity2.m_58899_()) : null;
        if (blockState == null) {
            return;
        }
        BlockState tankBlockState = blockState;
        if (!Intrinsics.areEqual((Object)tankBlockState.m_60734_(), (Object)((Object)CobblemonBlocks.RESTORATION_TANK))) {
            return;
        }
        Direction tankDirection = (Direction)tankBlockState.m_61143_((Property)HorizontalDirectionalBlock.f_54117_);
        MultiblockStructure multiblockStructure2 = entity2.getMultiblockStructure();
        Intrinsics.checkNotNull((Object)multiblockStructure2, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.multiblock.FossilMultiblockStructure");
        FossilMultiblockStructure struct2 = (FossilMultiblockStructure)multiblockStructure2;
        Direction connectionDir = struct2.getTankConnectorDirection();
        Set aspects = SetsKt.emptySet();
        FossilState state = struc.getFossilState();
        state.updatePartialTicks(tickDelta);
        float completionPercentage = RangesKt.coerceIn((float)(1.0f - (float)timeRemaining / 14400.0f), (float)0.0f, (float)1.0f);
        FossilModel fossilFetusModel = (FossilModel)FossilModelRepository.INSTANCE.getPoser(fossil2.getIdentifier(), aspects);
        float embryo1Scale = ((Number)EMBRYO_CURVE_1.invoke((Object)Float.valueOf(completionPercentage))).floatValue();
        float embryo2Scale = ((Number)EMBRYO_CURVE_2.invoke((Object)Float.valueOf(completionPercentage))).floatValue();
        float embryo3Scale = ((Number)EMBRYO_CURVE_3.invoke((Object)Float.valueOf(completionPercentage))).floatValue();
        float fossilScale = ((Number)FOSSIL_CURVE.invoke((Object)Float.valueOf(completionPercentage))).floatValue();
        Object[] objectArray = new Pair[]{new Pair((Object)EMBRYO_IDENTIFIERS.get(0), (Object)Float.valueOf(embryo1Scale)), new Pair((Object)EMBRYO_IDENTIFIERS.get(1), (Object)Float.valueOf(embryo2Scale)), new Pair((Object)EMBRYO_IDENTIFIERS.get(2), (Object)Float.valueOf(embryo3Scale)), new Pair((Object)fossil2.getIdentifier(), (Object)Float.valueOf(fossilScale))};
        List identifiersAndScales = CollectionsKt.listOf((Object[])objectArray);
        Iterable $this$forEach$iv = identifiersAndScales;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            Pair pair = (Pair)element$iv;
            boolean bl = false;
            ResourceLocation identifier = (ResourceLocation)pair.component1();
            float scale = ((Number)pair.component2()).floatValue();
            FossilModel model = (FossilModel)FossilModelRepository.INSTANCE.getPoser(identifier, aspects);
            ResourceLocation texture = FossilModelRepository.INSTANCE.getTexture(identifier, aspects, state.getAnimationSeconds());
            if (!(scale > 0.0f)) continue;
            VertexConsumer vertexConsumer = vertexConsumers.m_6299_(model.m_103119_(texture));
            Pose pose = (Pose)CollectionsKt.first((Iterable)model.getPoses().values());
            state.setCurrentModel(model);
            state.setPose(pose.getPoseName());
            state.setTimeEnteredPose(0.0f);
            float scale2 = timeRemaining == 0 ? model.getMaxScale() : scale * model.getMaxScale();
            matrices.m_85836_();
            matrices.m_85837_(0.5, 1.0 + (double)fossilFetusModel.getYTranslation(), 0.5);
            matrices.m_252781_(Axis.f_252403_.m_252977_(180.0f));
            if (tankDirection.m_175364_(Direction.Axis.Y) == connectionDir) {
                matrices.m_252781_(Axis.f_252436_.m_252977_(-90.0f));
            } else if (tankDirection == connectionDir) {
                matrices.m_252781_(Axis.f_252436_.m_252977_(180.0f));
            } else if (tankDirection.m_122424_() != connectionDir) {
                matrices.m_252781_(Axis.f_252436_.m_252977_(90.0f));
            }
            matrices.m_85836_();
            matrices.m_85841_(scale2, scale2, scale2);
            matrices.m_85837_(0.0, (double)model.getYGrowthPoint(), 0.0);
            float f = state.getAnimationSeconds() * (float)20;
            model.setupAnimStateful(null, state, 0.0f, 0.0f, f, 0.0f, 0.0f);
            Intrinsics.checkNotNullExpressionValue((Object)vertexConsumer, (String)"vertexConsumer");
            model.m_7695_(matrices, vertexConsumer, light, overlay2, 1.0f, 1.0f, 1.0f, 1.0f);
            model.withLayerContext(vertexConsumers, state, FossilModelRepository.INSTANCE.getLayers(fossil2.getIdentifier(), aspects), (Function0<Unit>)((Function0)new Function0<Unit>(model, matrices, vertexConsumer, light){
                final /* synthetic */ FossilModel $model;
                final /* synthetic */ PoseStack $matrices;
                final /* synthetic */ VertexConsumer $vertexConsumer;
                final /* synthetic */ int $light;
                {
                    this.$model = $model;
                    this.$matrices = $matrices;
                    this.$vertexConsumer = $vertexConsumer;
                    this.$light = $light;
                    super(0);
                }

                public final void invoke() {
                    VertexConsumer vertexConsumer = this.$vertexConsumer;
                    Intrinsics.checkNotNullExpressionValue((Object)vertexConsumer, (String)"vertexConsumer");
                    this.$model.m_7695_(this.$matrices, vertexConsumer, this.$light, OverlayTexture.f_118083_, 1.0f, 1.0f, 1.0f, 1.0f);
                }
            }));
            model.setDefault();
            matrices.m_85849_();
            matrices.m_85849_();
        }
    }

    static {
        Object[] objectArray = new BakedModel[]{CobblemonBakingOverrides.INSTANCE.getRESTORATION_TANK_FLUID_CHUNKED_1().getModel(), CobblemonBakingOverrides.INSTANCE.getRESTORATION_TANK_FLUID_CHUNKED_2().getModel(), CobblemonBakingOverrides.INSTANCE.getRESTORATION_TANK_FLUID_CHUNKED_3().getModel(), CobblemonBakingOverrides.INSTANCE.getRESTORATION_TANK_FLUID_CHUNKED_4().getModel(), CobblemonBakingOverrides.INSTANCE.getRESTORATION_TANK_FLUID_CHUNKED_5().getModel(), CobblemonBakingOverrides.INSTANCE.getRESTORATION_TANK_FLUID_CHUNKED_6().getModel(), CobblemonBakingOverrides.INSTANCE.getRESTORATION_TANK_FLUID_CHUNKED_7().getModel(), CobblemonBakingOverrides.INSTANCE.getRESTORATION_TANK_FLUID_CHUNKED_8().getModel(), CobblemonBakingOverrides.INSTANCE.getRESTORATION_TANK_FLUID_BUBBLING().getModel()};
        FLUID_MODELS = CollectionsKt.listOf((Object[])objectArray);
        CONNECTOR_MODEL = CobblemonBakingOverrides.INSTANCE.getRESTORATION_TANK_CONNECTOR().getModel();
        objectArray = new ResourceLocation[]{MiscUtilsKt.cobblemonResource("embryo_stage1"), MiscUtilsKt.cobblemonResource("embryo_stage2"), MiscUtilsKt.cobblemonResource("embryo_stage3")};
        EMBRYO_IDENTIFIERS = CollectionsKt.listOf((Object[])objectArray);
        EMBRYO_CURVE_1 = WaveFunctionKt.timeDilate(WaveFunctionKt.rerange(WaveFunctionKt.parabolaFunction(0.5f, 1.0f), 0.0f, 0.8f), 2.5f);
        EMBRYO_CURVE_2 = WaveFunctionKt.timeDilate(WaveFunctionKt.rerange(WaveFunctionKt.parabolaFunction(0.9f, 1.0f), 0.2f, 1.2f), 2.5f);
        EMBRYO_CURVE_3 = WaveFunctionKt.timeDilate(WaveFunctionKt.rerange(WaveFunctionKt.parabolaFunction(1.0f, 1.0f), 0.6f, 1.4f), 2.5f);
        FOSSIL_CURVE = WaveFunctionKt.timeDilate((Function1<? super Float, Float>)((Function1)Companion.FOSSIL_CURVE.1.INSTANCE), 2.5f);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u000b\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u001c\u0010\u001dR\u0017\u0010\u0003\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006R'\u0010\n\u001a\u0012\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b0\u0007j\u0002`\t8\u0006\u00a2\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\f\u0010\rR'\u0010\u000e\u001a\u0012\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b0\u0007j\u0002`\t8\u0006\u00a2\u0006\f\n\u0004\b\u000e\u0010\u000b\u001a\u0004\b\u000f\u0010\rR'\u0010\u0010\u001a\u0012\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b0\u0007j\u0002`\t8\u0006\u00a2\u0006\f\n\u0004\b\u0010\u0010\u000b\u001a\u0004\b\u0011\u0010\rR\u001d\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00130\u00128\u0006\u00a2\u0006\f\n\u0004\b\u0014\u0010\u0015\u001a\u0004\b\u0016\u0010\u0017R\u001d\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00020\u00128\u0006\u00a2\u0006\f\n\u0004\b\u0018\u0010\u0015\u001a\u0004\b\u0019\u0010\u0017R'\u0010\u001a\u001a\u0012\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b0\u0007j\u0002`\t8\u0006\u00a2\u0006\f\n\u0004\b\u001a\u0010\u000b\u001a\u0004\b\u001b\u0010\r\u00a8\u0006\u001e"}, d2={"Lcom/cobblemon/mod/common/client/render/block/RestorationTankRenderer$Companion;", "", "Lnet/minecraft/client/resources/model/BakedModel;", "CONNECTOR_MODEL", "Lnet/minecraft/client/resources/model/BakedModel;", "getCONNECTOR_MODEL", "()Lnet/minecraft/client/resources/model/BakedModel;", "Lkotlin/Function1;", "", "Lcom/cobblemon/mod/common/client/render/models/blockbench/wavefunction/WaveFunction;", "EMBRYO_CURVE_1", "Lkotlin/jvm/functions/Function1;", "getEMBRYO_CURVE_1", "()Lkotlin/jvm/functions/Function1;", "EMBRYO_CURVE_2", "getEMBRYO_CURVE_2", "EMBRYO_CURVE_3", "getEMBRYO_CURVE_3", "", "Lnet/minecraft/resources/ResourceLocation;", "EMBRYO_IDENTIFIERS", "Ljava/util/List;", "getEMBRYO_IDENTIFIERS", "()Ljava/util/List;", "FLUID_MODELS", "getFLUID_MODELS", "FOSSIL_CURVE", "getFOSSIL_CURVE", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final List<BakedModel> getFLUID_MODELS() {
            return FLUID_MODELS;
        }

        @NotNull
        public final BakedModel getCONNECTOR_MODEL() {
            return CONNECTOR_MODEL;
        }

        @NotNull
        public final List<ResourceLocation> getEMBRYO_IDENTIFIERS() {
            return EMBRYO_IDENTIFIERS;
        }

        @NotNull
        public final Function1<Float, Float> getEMBRYO_CURVE_1() {
            return EMBRYO_CURVE_1;
        }

        @NotNull
        public final Function1<Float, Float> getEMBRYO_CURVE_2() {
            return EMBRYO_CURVE_2;
        }

        @NotNull
        public final Function1<Float, Float> getEMBRYO_CURVE_3() {
            return EMBRYO_CURVE_3;
        }

        @NotNull
        public final Function1<Float, Float> getFOSSIL_CURVE() {
            return FOSSIL_CURVE;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }

    @Metadata(mv={1, 8, 0}, k=3, xi=48)
    public final class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] nArray = new int[Direction.values().length];
            try {
                nArray[Direction.NORTH.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[Direction.EAST.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[Direction.SOUTH.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[Direction.WEST.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            $EnumSwitchMapping$0 = nArray;
        }
    }
}

