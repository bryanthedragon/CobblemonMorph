package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.layer

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonSpecies
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.PokemonFloatingState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.PokemonPoseableModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Pose
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository.PokemonModelRepository
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.PoseType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.FormData
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Species
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.CompoundTagExtensionsKt
import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import com.mojang.math.Axis
import java.util.ArrayList;
import java.util.HashMap
import java.util.UUID
import kotlin.jvm.functions.Function0
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.client.model.PlayerModel
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.entity.LivingEntityRenderer
import net.minecraft.client.renderer.entity.RenderLayerParent
import net.minecraft.client.renderer.entity.layers.RenderLayer
import net.minecraft.client.renderer.texture.OverlayTexture
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.ListTag
import net.minecraft.nbt.Tag
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.Tuple
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player

@SourceDebugExtension(["SMAP\nPokemonOnShoulderRenderer.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PokemonOnShoulderRenderer.kt\ncom/cobblemon/mod/common/client/render/layer/PokemonOnShoulderRenderer\n+ 2 Maps.kt\nkotlin/collections/MapsKt__MapsKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 4 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,186:1\n361#2,7:187\n288#3,2:194\n288#3,2:197\n1549#3:199\n1620#3,3:200\n1#4:196\n*S KotlinDebug\n*F\n+ 1 PokemonOnShoulderRenderer.kt\ncom/cobblemon/mod/common/client/render/layer/PokemonOnShoulderRenderer\n*L\n70#1:187,7\n110#1:194,2\n149#1:197,2\n150#1:199\n150#1:200,3\n*E\n"])
public class PokemonOnShoulderRenderer<T extends Player>(renderLayerParent: RenderLayerParent<Any, PlayerModel<Any>>) : RenderLayer(renderLayerParent) {
   public open fun render(
      matrixStack: PoseStack,
      buffer: MultiBufferSource,
      packedLight: Int,
      livingEntity: Any,
      limbSwing: Float,
      limbSwingAmount: Float,
      partialTicks: Float,
      ageInTicks: Float,
      netHeadYaw: Float,
      headPitch: Float
   ) {
      this.render(matrixStack, buffer, packedLight, (T)livingEntity, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, true);
      this.render(matrixStack, buffer, packedLight, (T)livingEntity, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, false);
   }

   private fun render(
      matrixStack: PoseStack,
      buffer: MultiBufferSource,
      packedLight: Int,
      livingEntity: Any,
      limbSwing: Float,
      limbSwingAmount: Float,
      partialTicks: Float,
      ageInTicks: Float,
      netHeadYaw: Float,
      headPitch: Float,
      pLeftShoulder: Boolean
   ) {
      val compoundTag: CompoundTag = if (pLeftShoulder) livingEntity.m_36331_() else livingEntity.m_36332_();
      if (CompoundTagExtensionsKt.isPokemonEntity(compoundTag)) {
         matrixStack.m_85836_();
         val uuid: UUID = this.extractUuid(compoundTag);
         val shoulderData: java.util.Map = playerCache;
         var var10000: UUID = livingEntity.m_20148_();
         val offset: Any = shoulderData.get(var10000);
         if (offset == null) {
            val var36: Any = new PokemonOnShoulderRenderer.ShoulderCache(null, null, 3, null);
            shoulderData.put(var10000, var36);
            var10000 = (UUID)var36;
         } else {
            var10000 = (UUID)offset;
         }

         var cache: PokemonOnShoulderRenderer.ShoulderCache;
         label86: {
            cache = var10000 as PokemonOnShoulderRenderer.ShoulderCache;
            var32 = null;
            if (pLeftShoulder) {
               val var38: PokemonOnShoulderRenderer.ShoulderData = cache.getLastKnownLeft();
               if (!((if (var38 != null) var38.getUuid() else null) == uuid)) {
                  var32 = this.extractData(compoundTag, uuid);
                  cache.setLastKnownLeft(var32);
                  break label86;
               }
            }

            if (!pLeftShoulder) {
               val var39: PokemonOnShoulderRenderer.ShoulderData = cache.getLastKnownRight();
               if (!((if (var39 != null) var39.getUuid() else null) == uuid)) {
                  var32 = this.extractData(compoundTag, uuid);
                  cache.setLastKnownRight(var32);
               }
            }
         }

         if (var32 == null) {
            val var40: PokemonOnShoulderRenderer.ShoulderData = if (pLeftShoulder) cache.getLastKnownLeft() else cache.getLastKnownRight();
            if (var40 == null) {
               return;
            }

            var32 = var40;
         }

         val var33: Float = var32.getForm().getBaseScale() * var32.getScaleModifier();
         val var35: Double = var32.getForm().getHitbox().f_20377_ / 2 - 0.7;
         if (livingEntity.m_6144_()) {
            matrixStack.m_252781_(Axis.f_252529_.m_252961_(0.5F));
            matrixStack.m_252880_(0.0F, 0.0F, -0.15F);
         }

         matrixStack.m_85837_(if (pLeftShoulder) -var35 else var35, (if (livingEntity.m_6144_()) -1.3 else -1.5) * (double)var33, 0.0);
         matrixStack.m_85841_(var33, var33, var33);
         val model: PokemonPoseableModel = PokemonModelRepository.INSTANCE.getPoser(var32.getSpecies().getResourceIdentifier(), var32.getAspects());
         val state: PokemonFloatingState = new PokemonFloatingState();
         state.updatePartialTicks(ageInTicks + partialTicks);
         val vertexConsumer: VertexConsumer = buffer.m_6299_(
            model.m_103119_(
               PokemonModelRepository.INSTANCE.getTexture(var32.getSpecies().getResourceIdentifier(), var32.getAspects(), state.getAnimationSeconds())
            )
         );

         label79: {
            val `$this$firstOrNull$iv`: java.lang.Iterable;
            for (Object element$iv : $this$firstOrNull$iv) {
               if ((`element$iv` as Pose).getPoseTypes().contains(if (pLeftShoulder) PoseType.SHOULDER_LEFT else PoseType.SHOULDER_RIGHT)) {
                  var10000 = (UUID)`element$iv`;
                  break label79;
               }
            }

            var10000 = null;
         }

         var var42: Pose = var10000 as Pose;
         if (var10000 as Pose == null) {
            var42 = CollectionsKt.first(model.getPoses().values()) as Pose;
         }

         state.setPose(var42.getPoseName());
         state.setTimeEnteredPose(0.0F);
         model.setupAnimStateful(null, state, limbSwing, limbSwingAmount, (float)livingEntity.f_19797_, netHeadYaw, headPitch);
         model.m_7695_(matrixStack, vertexConsumer, packedLight, LivingEntityRenderer.m_115338_(livingEntity as LivingEntity, 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);
         model.withLayerContext(
            buffer,
            state,
            PokemonModelRepository.INSTANCE.getLayers(var32.getSpecies().getResourceIdentifier(), var32.getAspects()),
            (new Function0<Unit>(model, matrixStack, vertexConsumer, packedLight) {
               {
                  super(0);
                  this.$model = `$model`;
                  this.$matrixStack = `$matrixStack`;
                  this.$vertexConsumer = `$vertexConsumer`;
                  this.$packedLight = `$packedLight`;
               }

               public final void invoke() {
                  val var10000: PokemonPoseableModel = this.$model;
                  val var10001: PoseStack = this.$matrixStack;
                  val var10002: VertexConsumer = this.$vertexConsumer;
                  var10000.m_7695_(var10001, var10002, this.$packedLight, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
               }
            }) as () -> Unit
         );
         model.setDefault();
         matrixStack.m_85849_();
      }
   }

   private fun extractUuid(shoulderNbt: CompoundTag): UUID {
      if (!shoulderNbt.m_128441_("shoulder_uuid")) {
         val var2: UUID = shoulderNbt.m_128469_("Pokemon").m_128342_("UUID");
         return var2;
      } else {
         val var10000: UUID = shoulderNbt.m_128342_("shoulder_uuid");
         return var10000;
      }
   }

   private fun extractData(shoulderNbt: CompoundTag, pokemonUUID: UUID): bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.layer.PokemonOnShoulderRenderer.ShoulderData? {
      if (!shoulderNbt.m_128441_("shoulder_species")) {
         val var18: Pokemon = new Pokemon();
         var18.setClient$common(true);
         val var10001: CompoundTag = shoulderNbt.m_128469_("Pokemon");
         val var17: Pokemon = var18.loadFromNBT(var10001);
         return new PokemonOnShoulderRenderer.ShoulderData(pokemonUUID, var17.getSpecies(), var17.getForm(), var17.getAspects(), var17.getScaleModifier());
      } else {
         val var10000: Species = PokemonSpecies.INSTANCE.getByIdentifier(new ResourceLocation(shoulderNbt.m_128461_("shoulder_species")));
         if (var10000 == null) {
            return null;
         } else {
            val formName: java.lang.String = shoulderNbt.m_128461_("shoulder_form");
            val `$this$mapTo$iv$iv`: java.util.Iterator = var10000.getForms().iterator();

            while (true) {
               if (`$this$mapTo$iv$iv`.hasNext()) {
                  val `destination$iv$iv`: Any = `$this$mapTo$iv$iv`.next();
                  if (!((`destination$iv$iv` as FormData).getName() == formName)) {
                     continue;
                  }

                  var26 = `destination$iv$iv`;
                  break;
               }

               var26 = null;
               break;
            }

            var var27: FormData = var26 as FormData;
            if (var26 as FormData == null) {
               var27 = var10000.getStandardForm();
            }

            val var28: ListTag = shoulderNbt.m_128437_("shoulder_aspects", 8);
            val var20: java.lang.Iterable = var28 as java.lang.Iterable;
            val var23: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(var28 as java.lang.Iterable, 10));

            for (Object item$iv$iv : $this$map$iv) {
               var23.add((`item$iv$iv` as Tag).m_7916_());
            }

            return new PokemonOnShoulderRenderer.ShoulderData(
               pokemonUUID, var10000, var27, CollectionsKt.toSet(var23 as java.util.List), shoulderNbt.m_128457_("shoulder_scale")
            );
         }
      }
   }

   public companion object {
      private final val playerCache: HashMap<UUID, bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.layer.PokemonOnShoulderRenderer.ShoulderCache>

      public fun shoulderDataOf(player: Player): Tuple<
            bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.layer.PokemonOnShoulderRenderer.ShoulderData?,
            bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.layer.PokemonOnShoulderRenderer.ShoulderData?
         > {
         val var10000: PokemonOnShoulderRenderer.ShoulderCache = PokemonOnShoulderRenderer.access$getPlayerCache$cp().get(player.m_20148_()) as PokemonOnShoulderRenderer.ShoulderCache;
         return if (var10000 == null) new Tuple(null, null) else new Tuple(var10000.getLastKnownLeft(), var10000.getLastKnownRight());
      }
   }

   private data class ShoulderCache(lastKnownLeft: bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.layer.PokemonOnShoulderRenderer.ShoulderData? = null,
      lastKnownRight: bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.layer.PokemonOnShoulderRenderer.ShoulderData? = null
   ) {
      public final var lastKnownLeft: bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.layer.PokemonOnShoulderRenderer.ShoulderData?
      public final var lastKnownRight: bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.layer.PokemonOnShoulderRenderer.ShoulderData?

      init {
         this.lastKnownLeft = lastKnownLeft;
         this.lastKnownRight = lastKnownRight;
      }

      public operator fun component1(): bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.layer.PokemonOnShoulderRenderer.ShoulderData? {
         return this.lastKnownLeft;
      }

      public operator fun component2(): bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.layer.PokemonOnShoulderRenderer.ShoulderData? {
         return this.lastKnownRight;
      }

      public fun copy(
         lastKnownLeft: bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.layer.PokemonOnShoulderRenderer.ShoulderData? = this.lastKnownLeft,
         lastKnownRight: bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.layer.PokemonOnShoulderRenderer.ShoulderData? = this.lastKnownRight
      ): bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.layer.PokemonOnShoulderRenderer.ShoulderCache {
         return new PokemonOnShoulderRenderer.ShoulderCache(lastKnownLeft, lastKnownRight);
      }

      public override fun toString(): String {
         return "ShoulderCache(lastKnownLeft=${this.lastKnownLeft}, lastKnownRight=${this.lastKnownRight})";
      }

      public override fun hashCode(): Int {
         return (if (this.lastKnownLeft == null) 0 else this.lastKnownLeft.hashCode()) * 31
            + (if (this.lastKnownRight == null) 0 else this.lastKnownRight.hashCode());
      }

      public override operator fun equals(other: Any?): Boolean {
         if (this === other) {
            return true;
         } else if (other !is PokemonOnShoulderRenderer.ShoulderCache) {
            return false;
         } else {
            val var2: PokemonOnShoulderRenderer.ShoulderCache = other as PokemonOnShoulderRenderer.ShoulderCache;
            if (!(this.lastKnownLeft == (other as PokemonOnShoulderRenderer.ShoulderCache).lastKnownLeft)) {
               return false;
            } else {
               return this.lastKnownRight == var2.lastKnownRight;
            }
         }
      }

      fun ShoulderCache() {
         this(null, null, 3, null);
      }
   }

   public data ShoulderData(uuid: UUID, species: Species, form: FormData, aspects: Set<String>, scaleModifier: Float) {
      public final val aspects: Set<String>
      public final val form: FormData
      public final val scaleModifier: Float
      public final val species: Species
      public final val uuid: UUID

      init {
         this.uuid = uuid;
         this.species = species;
         this.form = form;
         this.aspects = aspects;
         this.scaleModifier = scaleModifier;
      }

      public operator fun component1(): UUID {
         return this.uuid;
      }

      public operator fun component2(): Species {
         return this.species;
      }

      public operator fun component3(): FormData {
         return this.form;
      }

      public operator fun component4(): Set<String> {
         return this.aspects;
      }

      public operator fun component5(): Float {
         return this.scaleModifier;
      }

      public fun copy(
         uuid: UUID = this.uuid,
         species: Species = this.species,
         form: FormData = this.form,
         aspects: Set<String> = this.aspects,
         scaleModifier: Float = this.scaleModifier
      ): bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.layer.PokemonOnShoulderRenderer.ShoulderData {
         return new PokemonOnShoulderRenderer.ShoulderData(uuid, species, form, aspects, scaleModifier);
      }

      public override fun toString(): String {
         return "ShoulderData(uuid=${this.uuid}, species=${this.species}, form=${this.form}, aspects=${this.aspects}, scaleModifier=${this.scaleModifier})";
      }

      public override fun hashCode(): Int {
         return (((this.uuid.hashCode() * 31 + this.species.hashCode()) * 31 + this.form.hashCode()) * 31 + this.aspects.hashCode()) * 31
            + java.lang.Float.hashCode(this.scaleModifier);
      }

      public override operator fun equals(other: Any?): Boolean {
         if (this === other) {
            return true;
         } else if (other !is PokemonOnShoulderRenderer.ShoulderData) {
            return false;
         } else {
            val var2: PokemonOnShoulderRenderer.ShoulderData = other as PokemonOnShoulderRenderer.ShoulderData;
            if (!(this.uuid == (other as PokemonOnShoulderRenderer.ShoulderData).uuid)) {
               return false;
            } else if (!(this.species == var2.species)) {
               return false;
            } else if (!(this.form == var2.form)) {
               return false;
            } else if (!(this.aspects == var2.aspects)) {
               return false;
            } else {
               return java.lang.Float.compare(this.scaleModifier, var2.scaleModifier) == 0;
            }
         }
      }
   }
}
