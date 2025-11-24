/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.blaze3d.vertex.VertexConsumer
 *  com.mojang.math.Axis
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.JvmStatic
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.client.model.PlayerModel
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.entity.LivingEntityRenderer
 *  net.minecraft.client.renderer.entity.RenderLayerParent
 *  net.minecraft.client.renderer.entity.layers.RenderLayer
 *  net.minecraft.client.renderer.texture.OverlayTexture
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.Tuple
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.layer;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonSpecies;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.PokemonFloatingState;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.PokemonPoseableModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Pose;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository.PokemonModelRepository;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.PoseType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.FormData;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Species;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.CompoundTagExtensionsKt;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u0000 &*\b\b\u0000\u0010\u0002*\u00020\u00012\u0014\u0012\u0004\u0012\u00028\u0000\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00040\u0003:\u0003&'(B!\u0012\u0018\u0010#\u001a\u0014\u0012\u0004\u0012\u00028\u0000\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00040\"\u00a2\u0006\u0004\b$\u0010%J!\u0010\n\u001a\u0004\u0018\u00010\t2\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0007H\u0002\u00a2\u0006\u0004\b\n\u0010\u000bJ\u0017\u0010\f\u001a\u00020\u00072\u0006\u0010\u0006\u001a\u00020\u0005H\u0002\u00a2\u0006\u0004\b\f\u0010\rJ_\u0010\u001d\u001a\u00020\u001c2\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0014\u001a\u00028\u00002\u0006\u0010\u0016\u001a\u00020\u00152\u0006\u0010\u0017\u001a\u00020\u00152\u0006\u0010\u0018\u001a\u00020\u00152\u0006\u0010\u0019\u001a\u00020\u00152\u0006\u0010\u001a\u001a\u00020\u00152\u0006\u0010\u001b\u001a\u00020\u0015H\u0016\u00a2\u0006\u0004\b\u001d\u0010\u001eJg\u0010\u001d\u001a\u00020\u001c2\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0014\u001a\u00028\u00002\u0006\u0010\u0016\u001a\u00020\u00152\u0006\u0010\u0017\u001a\u00020\u00152\u0006\u0010\u0018\u001a\u00020\u00152\u0006\u0010\u0019\u001a\u00020\u00152\u0006\u0010\u001a\u001a\u00020\u00152\u0006\u0010\u001b\u001a\u00020\u00152\u0006\u0010 \u001a\u00020\u001fH\u0002\u00a2\u0006\u0004\b\u001d\u0010!\u00a8\u0006)"}, d2={"Lcom/cobblemon/mod/common/client/render/layer/PokemonOnShoulderRenderer;", "Lnet/minecraft/world/entity/player/Player;", "T", "Lnet/minecraft/client/renderer/entity/layers/RenderLayer;", "Lnet/minecraft/client/model/PlayerModel;", "Lnet/minecraft/nbt/CompoundTag;", "shoulderNbt", "Ljava/util/UUID;", "pokemonUUID", "Lcom/cobblemon/mod/common/client/render/layer/PokemonOnShoulderRenderer$ShoulderData;", "extractData", "(Lnet/minecraft/nbt/CompoundTag;Ljava/util/UUID;)Lcom/cobblemon/mod/common/client/render/layer/PokemonOnShoulderRenderer$ShoulderData;", "extractUuid", "(Lnet/minecraft/nbt/CompoundTag;)Ljava/util/UUID;", "Lcom/mojang/blaze3d/vertex/PoseStack;", "matrixStack", "Lnet/minecraft/client/renderer/MultiBufferSource;", "buffer", "", "packedLight", "livingEntity", "", "limbSwing", "limbSwingAmount", "partialTicks", "ageInTicks", "netHeadYaw", "headPitch", "", "render", "(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/player/Player;FFFFFF)V", "", "pLeftShoulder", "(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/player/Player;FFFFFFZ)V", "Lnet/minecraft/client/renderer/entity/RenderLayerParent;", "renderLayerParent", "<init>", "(Lnet/minecraft/client/renderer/entity/RenderLayerParent;)V", "Companion", "ShoulderCache", "ShoulderData", "common"})
@SourceDebugExtension(value={"SMAP\nPokemonOnShoulderRenderer.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PokemonOnShoulderRenderer.kt\ncom/cobblemon/mod/common/client/render/layer/PokemonOnShoulderRenderer\n+ 2 Maps.kt\nkotlin/collections/MapsKt__MapsKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 4 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,186:1\n361#2,7:187\n288#3,2:194\n288#3,2:197\n1549#3:199\n1620#3,3:200\n1#4:196\n*S KotlinDebug\n*F\n+ 1 PokemonOnShoulderRenderer.kt\ncom/cobblemon/mod/common/client/render/layer/PokemonOnShoulderRenderer\n*L\n70#1:187,7\n110#1:194,2\n149#1:197,2\n150#1:199\n150#1:200,3\n*E\n"})
public final class PokemonOnShoulderRenderer<T extends Player>
extends RenderLayer<T, PlayerModel<T>> {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private static final HashMap<UUID, ShoulderCache> playerCache = new HashMap();

    public PokemonOnShoulderRenderer(@NotNull RenderLayerParent<T, PlayerModel<T>> renderLayerParent) {
        Intrinsics.checkNotNullParameter(renderLayerParent, (String)"renderLayerParent");
        super(renderLayerParent);
    }

    public void render(@NotNull PoseStack matrixStack, @NotNull MultiBufferSource buffer, int packedLight, @NotNull T livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        Intrinsics.checkNotNullParameter((Object)matrixStack, (String)"matrixStack");
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        Intrinsics.checkNotNullParameter(livingEntity, (String)"livingEntity");
        this.render(matrixStack, buffer, packedLight, livingEntity, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, true);
        this.render(matrixStack, buffer, packedLight, livingEntity, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, false);
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    private final void render(PoseStack matrixStack, MultiBufferSource buffer, int packedLight, T livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, boolean pLeftShoulder) {
        block12: {
            block11: {
                compoundTag = pLeftShoulder != false ? livingEntity.m_36331_() : livingEntity.m_36332_();
                Intrinsics.checkNotNullExpressionValue((Object)compoundTag, (String)"compoundTag");
                if (!CompoundTagExtensionsKt.isPokemonEntity(compoundTag)) break block12;
                matrixStack.m_85836_();
                uuid = this.extractUuid(compoundTag);
                var15_14 = PokemonOnShoulderRenderer.playerCache;
                v0 = livingEntity.m_20148_();
                Intrinsics.checkNotNullExpressionValue((Object)v0, (String)"livingEntity.uuid");
                key$iv = v0;
                $i$f$getOrPut = false;
                value$iv = $this$getOrPut$iv.get(key$iv);
                if (value$iv == null) {
                    $i$a$-getOrPut-PokemonOnShoulderRenderer$render$cache$1 = false;
                    answer$iv = new ShoulderCache(null, null, 3, null);
                    $this$getOrPut$iv.put(key$iv, answer$iv);
                    v1 /* !! */  = answer$iv;
                } else {
                    v1 /* !! */  = value$iv;
                }
                cache = (ShoulderCache)v1 /* !! */ ;
                shoulderData = null;
                if (!pLeftShoulder) ** GOTO lbl-1000
                v2 = cache.getLastKnownLeft();
                if (!Intrinsics.areEqual((Object)(v2 != null ? v2.getUuid() : null), (Object)uuid)) {
                    shoulderData = this.extractData(compoundTag, uuid);
                    cache.setLastKnownLeft(shoulderData);
                } else if (!pLeftShoulder) {
                    v3 = cache.getLastKnownRight();
                    if (!Intrinsics.areEqual((Object)(v3 != null ? v3.getUuid() : null), (Object)uuid)) {
                        shoulderData = this.extractData(compoundTag, uuid);
                        cache.setLastKnownRight(shoulderData);
                    }
                }
                if (shoulderData == null) {
                    v4 = pLeftShoulder != false ? cache.getLastKnownLeft() : cache.getLastKnownRight();
                    if (v4 == null) {
                        return;
                    }
                    shoulderData = v4;
                }
                scale = shoulderData.getForm().getBaseScale() * shoulderData.getScaleModifier();
                width = shoulderData.getForm().getHitbox().f_20377_;
                offset = (double)(width / (float)2) - 0.7;
                if (livingEntity.m_6144_()) {
                    matrixStack.m_252781_(Axis.f_252529_.m_252961_(0.5f));
                    matrixStack.m_252880_(0.0f, 0.0f, -0.15f);
                }
                matrixStack.m_85837_(pLeftShoulder != false ? -offset : offset, (livingEntity.m_6144_() != false ? -1.3 : -1.5) * (double)scale, 0.0);
                matrixStack.m_85841_(scale, scale, scale);
                model = (PokemonPoseableModel)PokemonModelRepository.INSTANCE.getPoser(shoulderData.getSpecies().getResourceIdentifier(), shoulderData.getAspects());
                state = new PokemonFloatingState();
                state.updatePartialTicks(ageInTicks + partialTicks);
                vertexConsumer = buffer.m_6299_(model.m_103119_(PokemonModelRepository.INSTANCE.getTexture(shoulderData.getSpecies().getResourceIdentifier(), shoulderData.getAspects(), state.getAnimationSeconds())));
                i = LivingEntityRenderer.m_115338_((LivingEntity)((LivingEntity)livingEntity), (float)0.0f);
                var26_28 = model.getPoses().values();
                $i$f$firstOrNull = false;
                for (T element$iv : $this$firstOrNull$iv) {
                    it = (Pose)element$iv;
                    $i$a$-firstOrNull-PokemonOnShoulderRenderer$render$pose$1 = false;
                    if (!it.getPoseTypes().contains((Object)(pLeftShoulder != false ? PoseType.SHOULDER_LEFT : PoseType.SHOULDER_RIGHT))) continue;
                    v5 = element$iv;
                    break block11;
                }
                v5 = null;
            }
            if ((v6 = (Pose)v5) == null) {
                v6 = (Pose)CollectionsKt.first((Iterable)model.getPoses().values());
            }
            pose = v6;
            state.setPose(pose.getPoseName());
            state.setTimeEnteredPose(0.0f);
            var25_35 = livingEntity.f_19797_;
            model.setupAnimStateful(null, state, limbSwing, limbSwingAmount, var25_35, netHeadYaw, headPitch);
            Intrinsics.checkNotNullExpressionValue((Object)vertexConsumer, (String)"vertexConsumer");
            model.m_7695_(matrixStack, vertexConsumer, packedLight, i, 1.0f, 1.0f, 1.0f, 1.0f);
            model.withLayerContext(buffer, state, PokemonModelRepository.INSTANCE.getLayers(shoulderData.getSpecies().getResourceIdentifier(), shoulderData.getAspects()), (Function0<Unit>)((Function0)new Function0<Unit>(model, matrixStack, vertexConsumer, packedLight){
                final /* synthetic */ PokemonPoseableModel $model;
                final /* synthetic */ PoseStack $matrixStack;
                final /* synthetic */ VertexConsumer $vertexConsumer;
                final /* synthetic */ int $packedLight;
                {
                    this.$model = $model;
                    this.$matrixStack = $matrixStack;
                    this.$vertexConsumer = $vertexConsumer;
                    this.$packedLight = $packedLight;
                    super(0);
                }

                public final void invoke() {
                    VertexConsumer vertexConsumer = this.$vertexConsumer;
                    Intrinsics.checkNotNullExpressionValue((Object)vertexConsumer, (String)"vertexConsumer");
                    this.$model.m_7695_(this.$matrixStack, vertexConsumer, this.$packedLight, OverlayTexture.f_118083_, 1.0f, 1.0f, 1.0f, 1.0f);
                }
            }));
            model.setDefault();
            matrixStack.m_85849_();
        }
    }

    private final UUID extractUuid(CompoundTag shoulderNbt) {
        if (!shoulderNbt.m_128441_("shoulder_uuid")) {
            UUID uUID = shoulderNbt.m_128469_("Pokemon").m_128342_("UUID");
            Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"shoulderNbt.getCompound(\u2026id(DataKeys.POKEMON_UUID)");
            return uUID;
        }
        UUID uUID = shoulderNbt.m_128342_("shoulder_uuid");
        Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"shoulderNbt.getUuid(DataKeys.SHOULDER_UUID)");
        return uUID;
    }

    /*
     * WARNING - void declaration
     */
    private final ShoulderData extractData(CompoundTag shoulderNbt, UUID pokemonUUID) {
        void $this$mapTo$iv$iv;
        FormData formData;
        Object v2;
        Species species;
        block5: {
            if (!shoulderNbt.m_128441_("shoulder_species")) {
                Pokemon pokemon;
                Pokemon $this$extractData_u24lambda_u242 = pokemon = new Pokemon();
                boolean bl = false;
                $this$extractData_u24lambda_u242.setClient$common(true);
                CompoundTag compoundTag = shoulderNbt.m_128469_("Pokemon");
                Intrinsics.checkNotNullExpressionValue((Object)compoundTag, (String)"shoulderNbt.getCompound(DataKeys.POKEMON)");
                Pokemon pokemon2 = pokemon.loadFromNBT(compoundTag);
                return new ShoulderData(pokemonUUID, pokemon2.getSpecies(), pokemon2.getForm(), pokemon2.getAspects(), pokemon2.getScaleModifier());
            }
            Species species2 = PokemonSpecies.INSTANCE.getByIdentifier(new ResourceLocation(shoulderNbt.m_128461_("shoulder_species")));
            if (species2 == null) {
                return null;
            }
            species = species2;
            String formName = shoulderNbt.m_128461_("shoulder_form");
            Iterable $this$firstOrNull$iv = species.getForms();
            boolean $i$f$firstOrNull = false;
            for (Object element$iv : $this$firstOrNull$iv) {
                FormData it = (FormData)element$iv;
                boolean bl = false;
                if (!Intrinsics.areEqual((Object)it.getName(), (Object)formName)) continue;
                v2 = element$iv;
                break block5;
            }
            v2 = null;
        }
        if ((formData = (FormData)v2) == null) {
            formData = species.getStandardForm();
        }
        FormData form2 = formData;
        ListTag listTag = shoulderNbt.m_128437_("shoulder_aspects", 8);
        Intrinsics.checkNotNullExpressionValue((Object)listTag, (String)"shoulderNbt.getList(Data\u2026ment.STRING_TYPE.toInt())");
        Iterable $this$map$iv = (Iterable)listTag;
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it;
            Tag tag = (Tag)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl = false;
            collection.add(it.m_7916_());
        }
        Set aspects = CollectionsKt.toSet((Iterable)((List)destination$iv$iv));
        float scaleModifier = shoulderNbt.m_128457_("shoulder_scale");
        return new ShoulderData(pokemonUUID, species, form2, aspects, scaleModifier);
    }

    @JvmStatic
    @NotNull
    public static final Tuple<ShoulderData, ShoulderData> shoulderDataOf(@NotNull Player player) {
        return Companion.shoulderDataOf(player);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u000e\u0010\u000fJ'\u0010\u0006\u001a\u0012\u0012\u0006\u0012\u0004\u0018\u00010\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0007\u00a2\u0006\u0004\b\u0006\u0010\u0007R0\u0010\f\u001a\u001e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\bj\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n`\u000b8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\f\u0010\r\u00a8\u0006\u0010"}, d2={"Lcom/cobblemon/mod/common/client/render/layer/PokemonOnShoulderRenderer$Companion;", "", "Lnet/minecraft/world/entity/player/Player;", "player", "Lnet/minecraft/util/Tuple;", "Lcom/cobblemon/mod/common/client/render/layer/PokemonOnShoulderRenderer$ShoulderData;", "shoulderDataOf", "(Lnet/minecraft/world/entity/player/Player;)Lnet/minecraft/util/Tuple;", "Ljava/util/HashMap;", "Ljava/util/UUID;", "Lcom/cobblemon/mod/common/client/render/layer/PokemonOnShoulderRenderer$ShoulderCache;", "Lkotlin/collections/HashMap;", "playerCache", "Ljava/util/HashMap;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @JvmStatic
        @NotNull
        public final Tuple<ShoulderData, ShoulderData> shoulderDataOf(@NotNull Player player) {
            Intrinsics.checkNotNullParameter((Object)player, (String)"player");
            ShoulderCache shoulderCache = (ShoulderCache)playerCache.get(player.m_20148_());
            if (shoulderCache == null) {
                return new Tuple(null, null);
            }
            ShoulderCache cache = shoulderCache;
            return new Tuple((Object)cache.getLastKnownLeft(), (Object)cache.getLastKnownRight());
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u000b\b\u0082\b\u0018\u00002\u00020\u0001B\u001f\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0002\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0002\u00a2\u0006\u0004\b\u001a\u0010\u001bJ\u0012\u0010\u0003\u001a\u0004\u0018\u00010\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u0012\u0010\u0005\u001a\u0004\u0018\u00010\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0005\u0010\u0004J(\u0010\b\u001a\u00020\u00002\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00022\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0002H\u00c6\u0001\u00a2\u0006\u0004\b\b\u0010\tJ\u001a\u0010\f\u001a\u00020\u000b2\b\u0010\n\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003\u00a2\u0006\u0004\b\f\u0010\rJ\u0010\u0010\u000f\u001a\u00020\u000eH\u00d6\u0001\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u0010\u0010\u0012\u001a\u00020\u0011H\u00d6\u0001\u00a2\u0006\u0004\b\u0012\u0010\u0013R$\u0010\u0006\u001a\u0004\u0018\u00010\u00028\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0006\u0010\u0014\u001a\u0004\b\u0015\u0010\u0004\"\u0004\b\u0016\u0010\u0017R$\u0010\u0007\u001a\u0004\u0018\u00010\u00028\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0007\u0010\u0014\u001a\u0004\b\u0018\u0010\u0004\"\u0004\b\u0019\u0010\u0017\u00a8\u0006\u001c"}, d2={"Lcom/cobblemon/mod/common/client/render/layer/PokemonOnShoulderRenderer$ShoulderCache;", "", "Lcom/cobblemon/mod/common/client/render/layer/PokemonOnShoulderRenderer$ShoulderData;", "component1", "()Lcom/cobblemon/mod/common/client/render/layer/PokemonOnShoulderRenderer$ShoulderData;", "component2", "lastKnownLeft", "lastKnownRight", "copy", "(Lcom/cobblemon/mod/common/client/render/layer/PokemonOnShoulderRenderer$ShoulderData;Lcom/cobblemon/mod/common/client/render/layer/PokemonOnShoulderRenderer$ShoulderData;)Lcom/cobblemon/mod/common/client/render/layer/PokemonOnShoulderRenderer$ShoulderCache;", "other", "", "equals", "(Ljava/lang/Object;)Z", "", "hashCode", "()I", "", "toString", "()Ljava/lang/String;", "Lcom/cobblemon/mod/common/client/render/layer/PokemonOnShoulderRenderer$ShoulderData;", "getLastKnownLeft", "setLastKnownLeft", "(Lcom/cobblemon/mod/common/client/render/layer/PokemonOnShoulderRenderer$ShoulderData;)V", "getLastKnownRight", "setLastKnownRight", "<init>", "(Lcom/cobblemon/mod/common/client/render/layer/PokemonOnShoulderRenderer$ShoulderData;Lcom/cobblemon/mod/common/client/render/layer/PokemonOnShoulderRenderer$ShoulderData;)V", "common"})
    private static final class ShoulderCache {
        @Nullable
        private ShoulderData lastKnownLeft;
        @Nullable
        private ShoulderData lastKnownRight;

        public ShoulderCache(@Nullable ShoulderData lastKnownLeft, @Nullable ShoulderData lastKnownRight) {
            this.lastKnownLeft = lastKnownLeft;
            this.lastKnownRight = lastKnownRight;
        }

        public /* synthetic */ ShoulderCache(ShoulderData shoulderData, ShoulderData shoulderData2, int n, DefaultConstructorMarker defaultConstructorMarker) {
            if ((n & 1) != 0) {
                shoulderData = null;
            }
            if ((n & 2) != 0) {
                shoulderData2 = null;
            }
            this(shoulderData, shoulderData2);
        }

        @Nullable
        public final ShoulderData getLastKnownLeft() {
            return this.lastKnownLeft;
        }

        public final void setLastKnownLeft(@Nullable ShoulderData shoulderData) {
            this.lastKnownLeft = shoulderData;
        }

        @Nullable
        public final ShoulderData getLastKnownRight() {
            return this.lastKnownRight;
        }

        public final void setLastKnownRight(@Nullable ShoulderData shoulderData) {
            this.lastKnownRight = shoulderData;
        }

        @Nullable
        public final ShoulderData component1() {
            return this.lastKnownLeft;
        }

        @Nullable
        public final ShoulderData component2() {
            return this.lastKnownRight;
        }

        @NotNull
        public final ShoulderCache copy(@Nullable ShoulderData lastKnownLeft, @Nullable ShoulderData lastKnownRight) {
            return new ShoulderCache(lastKnownLeft, lastKnownRight);
        }

        public static /* synthetic */ ShoulderCache copy$default(ShoulderCache shoulderCache, ShoulderData shoulderData, ShoulderData shoulderData2, int n, Object object) {
            if ((n & 1) != 0) {
                shoulderData = shoulderCache.lastKnownLeft;
            }
            if ((n & 2) != 0) {
                shoulderData2 = shoulderCache.lastKnownRight;
            }
            return shoulderCache.copy(shoulderData, shoulderData2);
        }

        @NotNull
        public String toString() {
            return "ShoulderCache(lastKnownLeft=" + this.lastKnownLeft + ", lastKnownRight=" + this.lastKnownRight + ")";
        }

        public int hashCode() {
            int result = this.lastKnownLeft == null ? 0 : this.lastKnownLeft.hashCode();
            result = result * 31 + (this.lastKnownRight == null ? 0 : this.lastKnownRight.hashCode());
            return result;
        }

        public boolean equals(@Nullable Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof ShoulderCache)) {
                return false;
            }
            ShoulderCache shoulderCache = (ShoulderCache)other;
            if (!Intrinsics.areEqual((Object)this.lastKnownLeft, (Object)shoulderCache.lastKnownLeft)) {
                return false;
            }
            return Intrinsics.areEqual((Object)this.lastKnownRight, (Object)shoulderCache.lastKnownRight);
        }

        public ShoulderCache() {
            this(null, null, 3, null);
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0011\b\u0086\b\u0018\u00002\u00020\u0001B5\u0012\u0006\u0010\u0012\u001a\u00020\u0002\u0012\u0006\u0010\u0013\u001a\u00020\u0005\u0012\u0006\u0010\u0014\u001a\u00020\b\u0012\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\f0\u000b\u0012\u0006\u0010\u0016\u001a\u00020\u000f\u00a2\u0006\u0004\b,\u0010-J\u0010\u0010\u0003\u001a\u00020\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u0010\u0010\u0006\u001a\u00020\u0005H\u00c6\u0003\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u0010\u0010\t\u001a\u00020\bH\u00c6\u0003\u00a2\u0006\u0004\b\t\u0010\nJ\u0016\u0010\r\u001a\b\u0012\u0004\u0012\u00020\f0\u000bH\u00c6\u0003\u00a2\u0006\u0004\b\r\u0010\u000eJ\u0010\u0010\u0010\u001a\u00020\u000fH\u00c6\u0003\u00a2\u0006\u0004\b\u0010\u0010\u0011JH\u0010\u0017\u001a\u00020\u00002\b\b\u0002\u0010\u0012\u001a\u00020\u00022\b\b\u0002\u0010\u0013\u001a\u00020\u00052\b\b\u0002\u0010\u0014\u001a\u00020\b2\u000e\b\u0002\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\b\b\u0002\u0010\u0016\u001a\u00020\u000fH\u00c6\u0001\u00a2\u0006\u0004\b\u0017\u0010\u0018J\u001a\u0010\u001b\u001a\u00020\u001a2\b\u0010\u0019\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003\u00a2\u0006\u0004\b\u001b\u0010\u001cJ\u0010\u0010\u001e\u001a\u00020\u001dH\u00d6\u0001\u00a2\u0006\u0004\b\u001e\u0010\u001fJ\u0010\u0010 \u001a\u00020\fH\u00d6\u0001\u00a2\u0006\u0004\b \u0010!R\u001d\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\f0\u000b8\u0006\u00a2\u0006\f\n\u0004\b\u0015\u0010\"\u001a\u0004\b#\u0010\u000eR\u0017\u0010\u0014\u001a\u00020\b8\u0006\u00a2\u0006\f\n\u0004\b\u0014\u0010$\u001a\u0004\b%\u0010\nR\u0017\u0010\u0016\u001a\u00020\u000f8\u0006\u00a2\u0006\f\n\u0004\b\u0016\u0010&\u001a\u0004\b'\u0010\u0011R\u0017\u0010\u0013\u001a\u00020\u00058\u0006\u00a2\u0006\f\n\u0004\b\u0013\u0010(\u001a\u0004\b)\u0010\u0007R\u0017\u0010\u0012\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0012\u0010*\u001a\u0004\b+\u0010\u0004\u00a8\u0006."}, d2={"Lcom/cobblemon/mod/common/client/render/layer/PokemonOnShoulderRenderer$ShoulderData;", "", "Ljava/util/UUID;", "component1", "()Ljava/util/UUID;", "Lcom/cobblemon/mod/common/pokemon/Species;", "component2", "()Lcom/cobblemon/mod/common/pokemon/Species;", "Lcom/cobblemon/mod/common/pokemon/FormData;", "component3", "()Lcom/cobblemon/mod/common/pokemon/FormData;", "", "", "component4", "()Ljava/util/Set;", "", "component5", "()F", "uuid", "species", "form", "aspects", "scaleModifier", "copy", "(Ljava/util/UUID;Lcom/cobblemon/mod/common/pokemon/Species;Lcom/cobblemon/mod/common/pokemon/FormData;Ljava/util/Set;F)Lcom/cobblemon/mod/common/client/render/layer/PokemonOnShoulderRenderer$ShoulderData;", "other", "", "equals", "(Ljava/lang/Object;)Z", "", "hashCode", "()I", "toString", "()Ljava/lang/String;", "Ljava/util/Set;", "getAspects", "Lcom/cobblemon/mod/common/pokemon/FormData;", "getForm", "F", "getScaleModifier", "Lcom/cobblemon/mod/common/pokemon/Species;", "getSpecies", "Ljava/util/UUID;", "getUuid", "<init>", "(Ljava/util/UUID;Lcom/cobblemon/mod/common/pokemon/Species;Lcom/cobblemon/mod/common/pokemon/FormData;Ljava/util/Set;F)V", "common"})
    public static final class ShoulderData {
        @NotNull
        private final UUID uuid;
        @NotNull
        private final Species species;
        @NotNull
        private final FormData form;
        @NotNull
        private final Set<String> aspects;
        private final float scaleModifier;

        public ShoulderData(@NotNull UUID uuid2, @NotNull Species species, @NotNull FormData form2, @NotNull Set<String> aspects, float scaleModifier) {
            Intrinsics.checkNotNullParameter((Object)uuid2, (String)"uuid");
            Intrinsics.checkNotNullParameter((Object)species, (String)"species");
            Intrinsics.checkNotNullParameter((Object)form2, (String)"form");
            Intrinsics.checkNotNullParameter(aspects, (String)"aspects");
            this.uuid = uuid2;
            this.species = species;
            this.form = form2;
            this.aspects = aspects;
            this.scaleModifier = scaleModifier;
        }

        @NotNull
        public final UUID getUuid() {
            return this.uuid;
        }

        @NotNull
        public final Species getSpecies() {
            return this.species;
        }

        @NotNull
        public final FormData getForm() {
            return this.form;
        }

        @NotNull
        public final Set<String> getAspects() {
            return this.aspects;
        }

        public final float getScaleModifier() {
            return this.scaleModifier;
        }

        @NotNull
        public final UUID component1() {
            return this.uuid;
        }

        @NotNull
        public final Species component2() {
            return this.species;
        }

        @NotNull
        public final FormData component3() {
            return this.form;
        }

        @NotNull
        public final Set<String> component4() {
            return this.aspects;
        }

        public final float component5() {
            return this.scaleModifier;
        }

        @NotNull
        public final ShoulderData copy(@NotNull UUID uuid2, @NotNull Species species, @NotNull FormData form2, @NotNull Set<String> aspects, float scaleModifier) {
            Intrinsics.checkNotNullParameter((Object)uuid2, (String)"uuid");
            Intrinsics.checkNotNullParameter((Object)species, (String)"species");
            Intrinsics.checkNotNullParameter((Object)form2, (String)"form");
            Intrinsics.checkNotNullParameter(aspects, (String)"aspects");
            return new ShoulderData(uuid2, species, form2, aspects, scaleModifier);
        }

        public static /* synthetic */ ShoulderData copy$default(ShoulderData shoulderData, UUID uUID, Species species, FormData formData, Set set2, float f, int n, Object object) {
            if ((n & 1) != 0) {
                uUID = shoulderData.uuid;
            }
            if ((n & 2) != 0) {
                species = shoulderData.species;
            }
            if ((n & 4) != 0) {
                formData = shoulderData.form;
            }
            if ((n & 8) != 0) {
                set2 = shoulderData.aspects;
            }
            if ((n & 0x10) != 0) {
                f = shoulderData.scaleModifier;
            }
            return shoulderData.copy(uUID, species, formData, set2, f);
        }

        @NotNull
        public String toString() {
            return "ShoulderData(uuid=" + this.uuid + ", species=" + this.species + ", form=" + this.form + ", aspects=" + this.aspects + ", scaleModifier=" + this.scaleModifier + ")";
        }

        public int hashCode() {
            int result = this.uuid.hashCode();
            result = result * 31 + this.species.hashCode();
            result = result * 31 + this.form.hashCode();
            result = result * 31 + ((Object)this.aspects).hashCode();
            result = result * 31 + Float.hashCode(this.scaleModifier);
            return result;
        }

        public boolean equals(@Nullable Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof ShoulderData)) {
                return false;
            }
            ShoulderData shoulderData = (ShoulderData)other;
            if (!Intrinsics.areEqual((Object)this.uuid, (Object)shoulderData.uuid)) {
                return false;
            }
            if (!Intrinsics.areEqual((Object)this.species, (Object)shoulderData.species)) {
                return false;
            }
            if (!Intrinsics.areEqual((Object)this.form, (Object)shoulderData.form)) {
                return false;
            }
            if (!Intrinsics.areEqual(this.aspects, shoulderData.aspects)) {
                return false;
            }
            return Float.compare(this.scaleModifier, shoulderData.scaleModifier) == 0;
        }
    }
}

