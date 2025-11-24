/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.blaze3d.vertex.VertexConsumer
 *  com.mojang.math.Axis
 *  kotlin.Lazy
 *  kotlin.LazyKt
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.blockentity.BlockEntityRenderer
 *  net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider$Context
 *  net.minecraft.client.renderer.texture.OverlayTexture
 *  net.minecraft.core.Direction
 *  net.minecraft.world.item.BannerItem
 *  net.minecraft.world.item.BedItem
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemDisplayContext
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 *  org.jetbrains.annotations.NotNull
 *  org.joml.Vector4f
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.block;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonBlocks;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonItems;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.DisplayCaseBlock;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.DisplayCaseBlockEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.block.DisplayCaseRenderer;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.PokemonPoseableModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository.PokemonModelRepository;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.PoseType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.PokeBallItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.PokemonItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.item.BedItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector4f;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u0000 \u001e2\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0002\u001e\u001fB\u000f\u0012\u0006\u0010\u001b\u001a\u00020\u001a\u00a2\u0006\u0004\b\u001c\u0010\u001dJ?\u0010\u000e\u001a\u00020\r2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\nH\u0016\u00a2\u0006\u0004\b\u000e\u0010\u000fJ7\u0010\u0013\u001a\u00020\r2\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\u0004H\u0002\u00a2\u0006\u0004\b\u0013\u0010\u0014R\u001b\u0010\u0019\u001a\u00020\u00108FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0015\u0010\u0016\u001a\u0004\b\u0017\u0010\u0018\u00a8\u0006 "}, d2={"Lcom/cobblemon/mod/common/client/render/block/DisplayCaseRenderer;", "Lnet/minecraft/client/renderer/blockentity/BlockEntityRenderer;", "Lcom/cobblemon/mod/common/block/entity/DisplayCaseBlockEntity;", "entity", "", "tickDelta", "Lcom/mojang/blaze3d/vertex/PoseStack;", "matrices", "Lnet/minecraft/client/renderer/MultiBufferSource;", "vertexConsumers", "", "light", "overlay", "", "render", "(Lcom/cobblemon/mod/common/block/entity/DisplayCaseBlockEntity;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II)V", "Lnet/minecraft/world/item/ItemStack;", "stack", "yRot", "renderPokemon", "(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/item/ItemStack;F)V", "coinPouchStack$delegate", "Lkotlin/Lazy;", "getCoinPouchStack", "()Lnet/minecraft/world/item/ItemStack;", "coinPouchStack", "Lnet/minecraft/client/render/block/entity/BlockEntityRendererFactory$Context;", "ctx", "<init>", "(Lnet/minecraft/client/renderer/blockentity/BlockEntityRendererProvider$Context;)V", "Companion", "PositioningType", "common"})
public final class DisplayCaseRenderer
implements BlockEntityRenderer<DisplayCaseBlockEntity> {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final Lazy coinPouchStack$delegate;
    @NotNull
    private static final List<Item> mobHeads;

    public DisplayCaseRenderer(@NotNull BlockEntityRendererProvider.Context ctx) {
        Intrinsics.checkNotNullParameter((Object)ctx, (String)"ctx");
        this.coinPouchStack$delegate = LazyKt.lazy((Function0)coinPouchStack.2.INSTANCE);
    }

    @NotNull
    public final ItemStack getCoinPouchStack() {
        Lazy lazy = this.coinPouchStack$delegate;
        return (ItemStack)lazy.getValue();
    }

    public void render(@NotNull DisplayCaseBlockEntity entity2, float tickDelta, @NotNull PoseStack matrices, @NotNull MultiBufferSource vertexConsumers, int light, int overlay2) {
        float yRot;
        Intrinsics.checkNotNullParameter((Object)((Object)entity2), (String)"entity");
        Intrinsics.checkNotNullParameter((Object)matrices, (String)"matrices");
        Intrinsics.checkNotNullParameter((Object)vertexConsumers, (String)"vertexConsumers");
        ItemStack stack = entity2.getStack().m_150930_((Item)CobblemonItems.RELIC_COIN_POUCH) ? this.getCoinPouchStack() : entity2.getStack();
        Level level = entity2.m_58904_();
        if (level == null) {
            return;
        }
        Level world = level;
        PositioningType posType = DisplayCaseRenderer.Companion.getPositioningType(stack, world);
        BlockState blockState = entity2.m_58904_() != null ? entity2.m_58900_() : (BlockState)CobblemonBlocks.DISPLAY_CASE.m_49966_().m_61124_((Property)DisplayCaseBlock.Companion.getITEM_DIRECTION(), (Comparable)Direction.NORTH);
        float f = yRot = posType == PositioningType.ITEM_MODEL ? ((Direction)blockState.m_61143_((Property)DisplayCaseBlock.Companion.getITEM_DIRECTION())).m_122424_().m_122435_() : ((Direction)blockState.m_61143_((Property)DisplayCaseBlock.Companion.getITEM_DIRECTION())).m_122435_();
        if (stack.m_41720_() instanceof PokemonItem) {
            this.renderPokemon(matrices, vertexConsumers, light, stack, yRot);
            return;
        }
        matrices.m_85836_();
        matrices.m_252880_(0.5f, 0.4f, 0.5f);
        matrices.m_85841_(posType.getScaleX(), posType.getScaleY(), posType.getScaleZ());
        matrices.m_252880_(posType.getTransX(), posType.getTransY(), posType.getTransZ());
        matrices.m_252781_(Axis.f_252436_.m_252977_(-yRot));
        matrices.m_252781_(Axis.f_252436_.m_252977_(posType.getRotY()));
        Minecraft.m_91087_().m_91291_().m_269128_(stack, ItemDisplayContext.GROUND, light, overlay2, matrices, vertexConsumers, entity2.m_58904_(), 0);
        matrices.m_85849_();
    }

    private final void renderPokemon(PoseStack matrices, MultiBufferSource vertexConsumers, int light, ItemStack stack, float yRot) {
        Item item = stack.m_41720_();
        PokemonItem pokemonItem = item instanceof PokemonItem ? (PokemonItem)item : null;
        if (pokemonItem == null) {
            return;
        }
        PokemonItem item2 = pokemonItem;
        Pokemon pokemon = item2.asPokemon(stack);
        if (pokemon == null) {
            return;
        }
        Pokemon pokemon2 = pokemon;
        PokemonPoseableModel model = (PokemonPoseableModel)PokemonModelRepository.INSTANCE.getPoser(pokemon2.getSpecies().getResourceIdentifier(), pokemon2.getAspects());
        RenderType renderLayer = model.m_103119_(PokemonModelRepository.INSTANCE.getTexture(pokemon2.getSpecies().getResourceIdentifier(), pokemon2.getAspects(), 0.0f));
        Vector4f tint = item2.tint(stack);
        VertexConsumer vertexConsumer = vertexConsumers.m_6299_(renderLayer);
        Intrinsics.checkNotNullExpressionValue((Object)vertexConsumer, (String)"vertexConsumers.getBuffer(renderLayer)");
        VertexConsumer vertexConsumer2 = vertexConsumer;
        float scale = 0.25f;
        matrices.m_85836_();
        matrices.m_85841_(1.0f, -1.0f, -1.0f);
        matrices.m_252880_(0.5f, -0.69f, -0.5f);
        matrices.m_85841_(scale, scale, scale);
        matrices.m_252781_(Axis.f_252436_.m_252977_(yRot));
        PoseableEntityModel.setupAnimStateless$default((PoseableEntityModel)model, PoseType.PROFILE, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 62, null);
        model.withLayerContext(vertexConsumers, null, PokemonModelRepository.INSTANCE.getLayers(pokemon2.getSpecies().getResourceIdentifier(), pokemon2.getAspects()), (Function0<Unit>)((Function0)new Function0<Unit>(model, matrices, vertexConsumer2, light, tint){
            final /* synthetic */ PokemonPoseableModel $model;
            final /* synthetic */ PoseStack $matrices;
            final /* synthetic */ VertexConsumer $vertexConsumer;
            final /* synthetic */ int $light;
            final /* synthetic */ Vector4f $tint;
            {
                this.$model = $model;
                this.$matrices = $matrices;
                this.$vertexConsumer = $vertexConsumer;
                this.$light = $light;
                this.$tint = $tint;
                super(0);
            }

            public final void invoke() {
                this.$model.m_7695_(this.$matrices, this.$vertexConsumer, this.$light, OverlayTexture.f_118083_, this.$tint.x, this.$tint.y, this.$tint.z, this.$tint.w);
            }
        }));
        matrices.m_85849_();
    }

    static {
        Object[] objectArray = new Item[7];
        Intrinsics.checkNotNullExpressionValue((Object)Items.f_42678_, (String)"SKELETON_SKULL");
        Intrinsics.checkNotNullExpressionValue((Object)Items.f_42679_, (String)"WITHER_SKELETON_SKULL");
        Intrinsics.checkNotNullExpressionValue((Object)Items.f_42681_, (String)"ZOMBIE_HEAD");
        Intrinsics.checkNotNullExpressionValue((Object)Items.f_260451_, (String)"PIGLIN_HEAD");
        Intrinsics.checkNotNullExpressionValue((Object)Items.f_42680_, (String)"PLAYER_HEAD");
        Intrinsics.checkNotNullExpressionValue((Object)Items.f_42683_, (String)"DRAGON_HEAD");
        Intrinsics.checkNotNullExpressionValue((Object)Items.f_42682_, (String)"CREEPER_HEAD");
        mobHeads = CollectionsKt.listOf((Object[])objectArray);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\r\u0010\u000eJ\u001f\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0002\u00a2\u0006\u0004\b\u0007\u0010\bR\u001a\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\n0\t8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u000b\u0010\f\u00a8\u0006\u000f"}, d2={"Lcom/cobblemon/mod/common/client/render/block/DisplayCaseRenderer$Companion;", "", "Lnet/minecraft/world/item/ItemStack;", "stack", "Lnet/minecraft/world/level/Level;", "world", "Lcom/cobblemon/mod/common/client/render/block/DisplayCaseRenderer$PositioningType;", "getPositioningType", "(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/level/Level;)Lcom/cobblemon/mod/common/client/render/block/DisplayCaseRenderer$PositioningType;", "", "Lnet/minecraft/world/item/Item;", "mobHeads", "Ljava/util/List;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        private final PositioningType getPositioningType(ItemStack stack, Level world) {
            return mobHeads.contains(stack.m_41720_()) ? PositioningType.MOB_HEAD : (stack.m_41720_() instanceof BedItem ? PositioningType.BED : (stack.m_41720_() instanceof BannerItem ? PositioningType.BANNER : (stack.m_41720_() instanceof PokeBallItem ? PositioningType.POKE_BALL : (Intrinsics.areEqual((Object)stack.m_41720_(), (Object)CobblemonItems.RELIC_COIN_POUCH) ? PositioningType.COIN_POUCH : (Intrinsics.areEqual((Object)stack.m_41720_(), (Object)CobblemonItems.PASTURE) ? PositioningType.PASTURE : (Intrinsics.areEqual((Object)stack.m_41720_(), (Object)((Object)CobblemonItems.POKEMON_MODEL)) ? PositioningType.ITEM_MODEL : (Intrinsics.areEqual((Object)stack.m_41720_(), (Object)Items.f_42740_) ? PositioningType.SHIELD : (Intrinsics.areEqual((Object)stack.m_41720_(), (Object)Items.f_271478_) ? PositioningType.MOB_HEAD : (Minecraft.m_91087_().m_91291_().m_174264_(stack, world, null, 0).m_7539_() ? PositioningType.BLOCK_MODEL : PositioningType.ITEM_MODEL)))))))));
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\u0010\u0007\n\u0002\b\u001c\b\u0082\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001BC\b\u0002\u0012\u0006\u0010\u0007\u001a\u00020\u0002\u0012\u0006\u0010\t\u001a\u00020\u0002\u0012\u0006\u0010\u000b\u001a\u00020\u0002\u0012\u0006\u0010\r\u001a\u00020\u0002\u0012\u0006\u0010\u000f\u001a\u00020\u0002\u0012\u0006\u0010\u0011\u001a\u00020\u0002\u0012\b\b\u0002\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0013\u0010\u0014R\u0017\u0010\u0003\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006R\u0017\u0010\u0007\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0007\u0010\u0004\u001a\u0004\b\b\u0010\u0006R\u0017\u0010\t\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\t\u0010\u0004\u001a\u0004\b\n\u0010\u0006R\u0017\u0010\u000b\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u000b\u0010\u0004\u001a\u0004\b\f\u0010\u0006R\u0017\u0010\r\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\r\u0010\u0004\u001a\u0004\b\u000e\u0010\u0006R\u0017\u0010\u000f\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u000f\u0010\u0004\u001a\u0004\b\u0010\u0010\u0006R\u0017\u0010\u0011\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0011\u0010\u0004\u001a\u0004\b\u0012\u0010\u0006j\u0002\b\u0015j\u0002\b\u0016j\u0002\b\u0017j\u0002\b\u0018j\u0002\b\u0019j\u0002\b\u001aj\u0002\b\u001bj\u0002\b\u001cj\u0002\b\u001d\u00a8\u0006\u001e"}, d2={"Lcom/cobblemon/mod/common/client/render/block/DisplayCaseRenderer$PositioningType;", "", "", "rotY", "F", "getRotY", "()F", "scaleX", "getScaleX", "scaleY", "getScaleY", "scaleZ", "getScaleZ", "transX", "getTransX", "transY", "getTransY", "transZ", "getTransZ", "<init>", "(Ljava/lang/String;IFFFFFFF)V", "POKE_BALL", "BLOCK_MODEL", "ITEM_MODEL", "BED", "BANNER", "MOB_HEAD", "SHIELD", "PASTURE", "COIN_POUCH", "common"})
    private static final class PositioningType
    extends Enum<PositioningType> {
        private final float scaleX;
        private final float scaleY;
        private final float scaleZ;
        private final float transX;
        private final float transY;
        private final float transZ;
        private final float rotY;
        public static final /* enum */ PositioningType POKE_BALL = new PositioningType("POKE_BALL", 0, 1.0f, 1.0f, 1.0f, 0.0f, 0.04f, 0.0f, 0.0f, 64, null);
        public static final /* enum */ PositioningType BLOCK_MODEL = new PositioningType("BLOCK_MODEL", 1, 1.0f, 1.0f, 1.0f, 0.0f, -0.15f, 0.0f, 0.0f, 64, null);
        public static final /* enum */ PositioningType ITEM_MODEL = new PositioningType("ITEM_MODEL", 2, 1.0f, 1.0f, 1.0f, 0.0f, 0.04f, 0.0f, 0.0f, 64, null);
        public static final /* enum */ PositioningType BED = new PositioningType("BED", 3, 1.0f, 1.0f, 1.0f, 0.0f, -0.02f, 0.0f, 0.0f, 64, null);
        public static final /* enum */ PositioningType BANNER = new PositioningType(1.0f, 1.0f, 1.0f, 0.0f, -0.02f, 0.0f, 180.0f);
        public static final /* enum */ PositioningType MOB_HEAD = new PositioningType(1.0f, 1.0f, 1.0f, 0.0f, -0.025f, 0.0f, 180.0f);
        public static final /* enum */ PositioningType SHIELD = new PositioningType(1.0f, 1.0f, 1.0f, 0.0f, -0.045f, 0.0f, 180.0f);
        public static final /* enum */ PositioningType PASTURE = new PositioningType("PASTURE", 7, 1.0f, 1.0f, 1.0f, 0.0f, 0.0375f, 0.0f, 0.0f, 64, null);
        public static final /* enum */ PositioningType COIN_POUCH = new PositioningType("COIN_POUCH", 8, 1.0f, 1.0f, 1.0f, 0.0f, 0.415f, 0.0f, 0.0f, 64, null);
        private static final /* synthetic */ PositioningType[] $VALUES;

        private PositioningType(float scaleX, float scaleY, float scaleZ, float transX, float transY, float transZ, float rotY) {
            this.scaleX = scaleX;
            this.scaleY = scaleY;
            this.scaleZ = scaleZ;
            this.transX = transX;
            this.transY = transY;
            this.transZ = transZ;
            this.rotY = rotY;
        }

        /* synthetic */ PositioningType(String string, int n, float f, float f2, float f3, float f4, float f5, float f6, float f7, int n2, DefaultConstructorMarker defaultConstructorMarker) {
            if ((n2 & 0x40) != 0) {
                f7 = 0.0f;
            }
            this(f, f2, f3, f4, f5, f6, f7);
        }

        public final float getScaleX() {
            return this.scaleX;
        }

        public final float getScaleY() {
            return this.scaleY;
        }

        public final float getScaleZ() {
            return this.scaleZ;
        }

        public final float getTransX() {
            return this.transX;
        }

        public final float getTransY() {
            return this.transY;
        }

        public final float getTransZ() {
            return this.transZ;
        }

        public final float getRotY() {
            return this.rotY;
        }

        public static PositioningType[] values() {
            return (PositioningType[])$VALUES.clone();
        }

        public static PositioningType valueOf(String value2) {
            return Enum.valueOf(PositioningType.class, value2);
        }

        static {
            $VALUES = positioningTypeArray = new PositioningType[]{PositioningType.POKE_BALL, PositioningType.BLOCK_MODEL, PositioningType.ITEM_MODEL, PositioningType.BED, PositioningType.BANNER, PositioningType.MOB_HEAD, PositioningType.SHIELD, PositioningType.PASTURE, PositioningType.COIN_POUCH};
        }
    }
}

