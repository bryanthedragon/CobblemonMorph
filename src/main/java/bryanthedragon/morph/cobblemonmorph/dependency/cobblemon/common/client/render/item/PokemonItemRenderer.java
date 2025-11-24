/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.platform.Lighting
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.blaze3d.vertex.VertexConsumer
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.Unit
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.client.renderer.LightTexture
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.texture.OverlayTexture
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemDisplayContext
 *  net.minecraft.world.item.ItemStack
 *  org.jetbrains.annotations.NotNull
 *  org.joml.Quaternionf
 *  org.joml.Vector3f
 *  org.joml.Vector4f
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.item;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.item.CobblemonBuiltinItemRenderer;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.PokemonPoseableModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository.PokemonModelRepository;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.PoseType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.PokemonItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Species;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math.QuaternionUtilsKt;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.joml.Vector4f;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\b\u0018\u0000 \u00122\u00020\u0001:\u0003\u0012\u0013\u0014B\u0007\u00a2\u0006\u0004\b\u0010\u0010\u0011J?\u0010\u000e\u001a\u00020\r2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\nH\u0016\u00a2\u0006\u0004\b\u000e\u0010\u000f\u00a8\u0006\u0015"}, d2={"Lcom/cobblemon/mod/common/client/render/item/PokemonItemRenderer;", "Lcom/cobblemon/mod/common/client/render/item/CobblemonBuiltinItemRenderer;", "Lnet/minecraft/world/item/ItemStack;", "stack", "Lnet/minecraft/world/item/ItemDisplayContext;", "mode", "Lcom/mojang/blaze3d/vertex/PoseStack;", "matrices", "Lnet/minecraft/client/renderer/MultiBufferSource;", "vertexConsumers", "", "light", "overlay", "", "render", "(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II)V", "<init>", "()V", "Companion", "Transformation", "Transformations", "common"})
public final class PokemonItemRenderer
implements CobblemonBuiltinItemRenderer {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private static final Map<ItemDisplayContext, Transformations> positions = new LinkedHashMap();

    @Override
    public void render(@NotNull ItemStack stack, @NotNull ItemDisplayContext mode, @NotNull PoseStack matrices, @NotNull MultiBufferSource vertexConsumers, int light, int overlay2) {
        Intrinsics.checkNotNullParameter((Object)stack, (String)"stack");
        Intrinsics.checkNotNullParameter((Object)mode, (String)"mode");
        Intrinsics.checkNotNullParameter((Object)matrices, (String)"matrices");
        Intrinsics.checkNotNullParameter((Object)vertexConsumers, (String)"vertexConsumers");
        Item item = stack.m_41720_();
        PokemonItem pokemonItem = item instanceof PokemonItem ? (PokemonItem)item : null;
        if (pokemonItem == null) {
            return;
        }
        PokemonItem pokemonItem2 = pokemonItem;
        Pair<Species, Set<String>> pair = pokemonItem2.getSpeciesAndAspects(stack);
        if (pair == null) {
            return;
        }
        Pair<Species, Set<String>> pair2 = pair;
        Species species = (Species)pair2.component1();
        Set aspects = (Set)pair2.component2();
        matrices.m_85836_();
        PokemonPoseableModel model = (PokemonPoseableModel)PokemonModelRepository.INSTANCE.getPoser(species.getResourceIdentifier(), aspects);
        RenderType renderLayer = model.m_103119_(PokemonModelRepository.INSTANCE.getTexture(species.getResourceIdentifier(), aspects, 0.0f));
        Transformations transformations = positions.get(mode);
        Intrinsics.checkNotNull((Object)transformations);
        Transformations transformations2 = transformations;
        Lighting.m_84931_();
        matrices.m_85841_(((Number)transformations2.getScale().getX()).floatValue(), ((Number)transformations2.getScale().getY()).floatValue(), ((Number)transformations2.getScale().getZ()).floatValue());
        matrices.m_85837_(((Number)transformations2.getTranslation().getX()).doubleValue(), ((Number)transformations2.getTranslation().getY()).doubleValue(), ((Number)transformations2.getTranslation().getZ()).doubleValue());
        PoseableEntityModel.setupAnimStateless$default((PoseableEntityModel)model, PoseType.PROFILE, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 62, null);
        matrices.m_85837_(model.getProfileTranslation().f_82479_, model.getProfileTranslation().f_82480_, model.getProfileTranslation().f_82481_ - 4.0);
        matrices.m_85841_(model.getProfileScale(), model.getProfileScale(), 0.15f);
        Quaternionf rotation = QuaternionUtilsKt.fromEulerXYZDegrees(new Quaternionf(), new Vector3f(((Number)transformations2.getRotation().getX()).floatValue(), ((Number)transformations2.getRotation().getY()).floatValue(), ((Number)transformations2.getRotation().getZ()).floatValue()));
        matrices.m_252781_(rotation);
        rotation.conjugate();
        VertexConsumer vertexConsumer = vertexConsumers.m_6299_(renderLayer);
        Intrinsics.checkNotNullExpressionValue((Object)vertexConsumer, (String)"vertexConsumers.getBuffer(renderLayer)");
        VertexConsumer vertexConsumer2 = vertexConsumer;
        matrices.m_85836_();
        int packedLight = mode == ItemDisplayContext.GUI ? LightTexture.m_109885_((int)13, (int)13) : light;
        Vector4f tint = pokemonItem2.tint(stack);
        model.withLayerContext(vertexConsumers, null, PokemonModelRepository.INSTANCE.getLayers(species.getResourceIdentifier(), aspects), (Function0<Unit>)((Function0)new Function0<Unit>(model, matrices, vertexConsumer2, packedLight, tint){
            final /* synthetic */ PokemonPoseableModel $model;
            final /* synthetic */ PoseStack $matrices;
            final /* synthetic */ VertexConsumer $vertexConsumer;
            final /* synthetic */ int $packedLight;
            final /* synthetic */ Vector4f $tint;
            {
                this.$model = $model;
                this.$matrices = $matrices;
                this.$vertexConsumer = $vertexConsumer;
                this.$packedLight = $packedLight;
                this.$tint = $tint;
                super(0);
            }

            public final void invoke() {
                this.$model.m_7695_(this.$matrices, this.$vertexConsumer, this.$packedLight, OverlayTexture.f_118083_, this.$tint.x, this.$tint.y, this.$tint.z, this.$tint.w);
            }
        }));
        model.setDefault();
        matrices.m_85849_();
        matrices.m_85849_();
        Lighting.m_84930_();
    }

    static {
        positions.put(ItemDisplayContext.GUI, new PokemonItemRenderer().new Transformations(new PokemonItemRenderer().new Transformation<Double>(1.0, -1.9, -0.5), new PokemonItemRenderer().new Transformation<Float>(Float.valueOf(0.5f), Float.valueOf(-0.5f), Float.valueOf(-0.5f)), new PokemonItemRenderer().new Transformation<Float>(Float.valueOf(0.0f), Float.valueOf(35.0f), Float.valueOf(0.0f))));
        positions.put(ItemDisplayContext.FIXED, new PokemonItemRenderer().new Transformations(new PokemonItemRenderer().new Transformation<Double>(1.0, -2.0, 3.0), new PokemonItemRenderer().new Transformation<Float>(Float.valueOf(0.5f), Float.valueOf(-0.5f), Float.valueOf(-0.5f)), new PokemonItemRenderer().new Transformation<Float>(Float.valueOf(0.0f), Float.valueOf(-145.0f), Float.valueOf(0.0f))));
        positions.put(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND, new PokemonItemRenderer().new Transformations(new PokemonItemRenderer().new Transformation<Double>(2.75, -1.2, 5.0), new PokemonItemRenderer().new Transformation<Float>(Float.valueOf(0.5f), Float.valueOf(-0.5f), Float.valueOf(-0.5f)), new PokemonItemRenderer().new Transformation<Float>(Float.valueOf(0.0f), Float.valueOf(35.0f), Float.valueOf(0.0f))));
        positions.put(ItemDisplayContext.FIRST_PERSON_LEFT_HAND, new PokemonItemRenderer().new Transformations(new PokemonItemRenderer().new Transformation<Double>(-0.75, -1.2, 5.0), new PokemonItemRenderer().new Transformation<Float>(Float.valueOf(0.5f), Float.valueOf(-0.5f), Float.valueOf(-0.5f)), new PokemonItemRenderer().new Transformation<Float>(Float.valueOf(0.0f), Float.valueOf(-35.0f), Float.valueOf(0.0f))));
        positions.put(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, new PokemonItemRenderer().new Transformations(new PokemonItemRenderer().new Transformation<Double>(1.0, -2.6, 2.75), new PokemonItemRenderer().new Transformation<Float>(Float.valueOf(0.5f), Float.valueOf(-0.5f), Float.valueOf(-0.5f)), new PokemonItemRenderer().new Transformation<Float>(Float.valueOf(0.0f), Float.valueOf(35.0f), Float.valueOf(0.0f))));
        positions.put(ItemDisplayContext.THIRD_PERSON_LEFT_HAND, new PokemonItemRenderer().new Transformations(new PokemonItemRenderer().new Transformation<Double>(1.0, -2.6, 2.75), new PokemonItemRenderer().new Transformation<Float>(Float.valueOf(0.5f), Float.valueOf(-0.5f), Float.valueOf(-0.5f)), new PokemonItemRenderer().new Transformation<Float>(Float.valueOf(0.0f), Float.valueOf(-35.0f), Float.valueOf(0.0f))));
        positions.put(ItemDisplayContext.GROUND, new PokemonItemRenderer().new Transformations(new PokemonItemRenderer().new Transformation<Double>(1.0, -2.6, 3.0), new PokemonItemRenderer().new Transformation<Float>(Float.valueOf(0.5f), Float.valueOf(-0.5f), Float.valueOf(-0.5f)), new PokemonItemRenderer().new Transformation<Float>(Float.valueOf(0.0f), Float.valueOf(35.0f), Float.valueOf(0.0f))));
        positions.put(ItemDisplayContext.HEAD, new PokemonItemRenderer().new Transformations(new PokemonItemRenderer().new Transformation<Double>(1.0, -3.5, 3.0), new PokemonItemRenderer().new Transformation<Float>(Float.valueOf(0.5f), Float.valueOf(-0.5f), Float.valueOf(-0.5f)), new PokemonItemRenderer().new Transformation<Float>(Float.valueOf(0.0f), Float.valueOf(215.0f), Float.valueOf(0.0f))));
        positions.put(ItemDisplayContext.NONE, new PokemonItemRenderer().new Transformations(new PokemonItemRenderer().new Transformation<Double>(0.0, 0.0, 0.0), new PokemonItemRenderer().new Transformation<Float>(Float.valueOf(0.5f), Float.valueOf(-0.5f), Float.valueOf(-0.5f)), new PokemonItemRenderer().new Transformation<Float>(Float.valueOf(0.0f), Float.valueOf(0.0f), Float.valueOf(0.0f))));
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\n\u0010\u000bR'\u0010\u0006\u001a\u0012\u0012\u0004\u0012\u00020\u0003\u0012\b\u0012\u00060\u0004R\u00020\u00050\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0006\u0010\u0007\u001a\u0004\b\b\u0010\t\u00a8\u0006\f"}, d2={"Lcom/cobblemon/mod/common/client/render/item/PokemonItemRenderer$Companion;", "", "", "Lnet/minecraft/world/item/ItemDisplayContext;", "Lcom/cobblemon/mod/common/client/render/item/PokemonItemRenderer$Transformations;", "Lcom/cobblemon/mod/common/client/render/item/PokemonItemRenderer;", "positions", "Ljava/util/Map;", "getPositions", "()Ljava/util/Map;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final Map<ItemDisplayContext, Transformations> getPositions() {
            return positions;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u000b\b\u0086\u0004\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B\u001f\u0012\u0006\u0010\u0003\u001a\u00028\u0000\u0012\u0006\u0010\u0007\u001a\u00028\u0000\u0012\u0006\u0010\t\u001a\u00028\u0000\u00a2\u0006\u0004\b\u000b\u0010\fR\u0017\u0010\u0003\u001a\u00028\u00008\u0006\u00a2\u0006\f\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006R\u0017\u0010\u0007\u001a\u00028\u00008\u0006\u00a2\u0006\f\n\u0004\b\u0007\u0010\u0004\u001a\u0004\b\b\u0010\u0006R\u0017\u0010\t\u001a\u00028\u00008\u0006\u00a2\u0006\f\n\u0004\b\t\u0010\u0004\u001a\u0004\b\n\u0010\u0006\u00a8\u0006\r"}, d2={"Lcom/cobblemon/mod/common/client/render/item/PokemonItemRenderer$Transformation;", "T", "", "x", "Ljava/lang/Object;", "getX", "()Ljava/lang/Object;", "y", "getY", "z", "getZ", "<init>", "(Lcom/cobblemon/mod/common/client/render/item/PokemonItemRenderer;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V", "common"})
    public final class Transformation<T> {
        private final T x;
        private final T y;
        private final T z;

        public Transformation(T x, T y, T z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public final T getX() {
            return this.x;
        }

        public final T getY() {
            return this.y;
        }

        public final T getZ() {
            return this.z;
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0006\n\u0002\b\u0005\b\u0086\u0004\u0018\u00002\u00020\u0001B=\u0012\u0010\u0010\f\u001a\f\u0012\u0004\u0012\u00020\u000b0\u0002R\u00020\u0004\u0012\u0010\u0010\t\u001a\f\u0012\u0004\u0012\u00020\u00030\u0002R\u00020\u0004\u0012\u0010\u0010\u0005\u001a\f\u0012\u0004\u0012\u00020\u00030\u0002R\u00020\u0004\u00a2\u0006\u0004\b\u000e\u0010\u000fR!\u0010\u0005\u001a\f\u0012\u0004\u0012\u00020\u00030\u0002R\u00020\u00048\u0006\u00a2\u0006\f\n\u0004\b\u0005\u0010\u0006\u001a\u0004\b\u0007\u0010\bR!\u0010\t\u001a\f\u0012\u0004\u0012\u00020\u00030\u0002R\u00020\u00048\u0006\u00a2\u0006\f\n\u0004\b\t\u0010\u0006\u001a\u0004\b\n\u0010\bR!\u0010\f\u001a\f\u0012\u0004\u0012\u00020\u000b0\u0002R\u00020\u00048\u0006\u00a2\u0006\f\n\u0004\b\f\u0010\u0006\u001a\u0004\b\r\u0010\b\u00a8\u0006\u0010"}, d2={"Lcom/cobblemon/mod/common/client/render/item/PokemonItemRenderer$Transformations;", "", "Lcom/cobblemon/mod/common/client/render/item/PokemonItemRenderer$Transformation;", "", "Lcom/cobblemon/mod/common/client/render/item/PokemonItemRenderer;", "rotation", "Lcom/cobblemon/mod/common/client/render/item/PokemonItemRenderer$Transformation;", "getRotation", "()Lcom/cobblemon/mod/common/client/render/item/PokemonItemRenderer$Transformation;", "scale", "getScale", "", "translation", "getTranslation", "<init>", "(Lcom/cobblemon/mod/common/client/render/item/PokemonItemRenderer;Lcom/cobblemon/mod/common/client/render/item/PokemonItemRenderer$Transformation;Lcom/cobblemon/mod/common/client/render/item/PokemonItemRenderer$Transformation;Lcom/cobblemon/mod/common/client/render/item/PokemonItemRenderer$Transformation;)V", "common"})
    public final class Transformations {
        @NotNull
        private final Transformation<Double> translation;
        @NotNull
        private final Transformation<Float> scale;
        @NotNull
        private final Transformation<Float> rotation;

        public Transformations(@NotNull Transformation<Double> translation, @NotNull Transformation<Float> scale, Transformation<Float> rotation) {
            Intrinsics.checkNotNullParameter(translation, (String)"translation");
            Intrinsics.checkNotNullParameter(scale, (String)"scale");
            Intrinsics.checkNotNullParameter(rotation, (String)"rotation");
            this.translation = translation;
            this.scale = scale;
            this.rotation = rotation;
        }

        @NotNull
        public final Transformation<Double> getTranslation() {
            return this.translation;
        }

        @NotNull
        public final Transformation<Float> getScale() {
            return this.scale;
        }

        @NotNull
        public final Transformation<Float> getRotation() {
            return this.rotation;
        }
    }
}

