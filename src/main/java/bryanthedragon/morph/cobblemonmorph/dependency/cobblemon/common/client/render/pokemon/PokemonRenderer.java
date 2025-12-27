package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.pokemon

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.entity.EntitySideDelegate
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.entity.PokemonSideDelegate
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ClientBallDisplay
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.entity.PokemonClientDelegate
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.keybind.CurrentKeyAccessorKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.keybind.keybinds.PartySendBinding
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.MatrixWrapper
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.RenderHelperKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokeball.PokeBallModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.PokemonPoseableModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository.PokeBallModelRepository
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository.PokemonModelRepository
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.pokeball.PokeBallPoseableState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.settings.ServerSettings
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokeball.EmptyPokeBallEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokeball.PokeBall
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerExtensionsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math.DoubleRange
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math.SimpleMathExtensionsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math.geometry.AngleExtensionsKt
import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import com.mojang.math.Axis
import java.util.UUID
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.Font.DisplayMode
import net.minecraft.client.model.EntityModel
import net.minecraft.client.player.LocalPlayer
import net.minecraft.client.render.entity.EntityRendererFactory.Context
import net.minecraft.client.renderer.LightTexture
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.entity.ItemRenderer
import net.minecraft.client.renderer.entity.MobRenderer
import net.minecraft.client.renderer.texture.OverlayTexture
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.FormattedText
import net.minecraft.network.chat.MutableComponent
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.Mth
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.Mob
import net.minecraft.world.entity.player.Player
import net.minecraft.world.phys.Vec3
import org.joml.Matrix4f
import org.joml.Quaternionf
import org.joml.Vector3f
import org.joml.Vector3fc
import org.joml.Vector4f

@SourceDebugExtension(["SMAP\nPokemonRenderer.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PokemonRenderer.kt\ncom/cobblemon/mod/common/client/render/pokemon/PokemonRenderer\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,391:1\n288#2,2:392\n1#3:394\n*S KotlinDebug\n*F\n+ 1 PokemonRenderer.kt\ncom/cobblemon/mod/common/client/render/pokemon/PokemonRenderer\n*L\n198#1:392,2\n*E\n"])
public class PokemonRenderer(context: Context) : MobRenderer(context, null, 0.5F) {
   public open fun getTexture(entity: PokemonEntity): ResourceLocation {
      val var10000: PokemonModelRepository = PokemonModelRepository.INSTANCE;
      val var10001: ResourceLocation = entity.getPokemon().getSpecies().getResourceIdentifier();
      val var10002: java.util.Set = entity.getAspects();
      val var10003: PokemonSideDelegate = entity.getDelegate();
      return var10000.getTexture(var10001, var10002, (var10003 as PokemonClientDelegate).getAnimationSeconds());
   }

   public open fun render(entity: PokemonEntity, entityYaw: Float, partialTicks: Float, poseMatrix: PoseStack, buffer: MultiBufferSource, packedLight: Int) {
      val var10001: Float = (float)Math.min(entity.m_20191_().f_82291_ - entity.m_20191_().f_82288_, entity.m_20191_().f_82293_ - entity.m_20191_().f_82290_)
         / 1.5F;
      val var10002: PokemonSideDelegate = entity.getDelegate();
      this.f_114477_ = var10001 * (var10002 as PokemonClientDelegate).getEntityScaleModifier();
      this.f_115290_ = PokemonModelRepository.INSTANCE.getPoser(entity.getPokemon().getSpecies().getResourceIdentifier(), entity.getAspects());
      val var10000: PokemonSideDelegate = entity.getDelegate();
      val clientDelegate: PokemonClientDelegate = var10000 as PokemonClientDelegate;
      val var9: EntityModel = this.f_115290_;
      val modelNow: PoseableEntityModel = var9 as PoseableEntityModel;
      clientDelegate.updatePartialTicks(partialTicks);
      if (entity.getBeamMode() != 0) {
         this.renderTransition(modelNow, entity.getBeamMode(), entity, partialTicks, poseMatrix, buffer, packedLight, clientDelegate);
      }

      modelNow.setLayerContext(
         buffer, clientDelegate, PokemonModelRepository.INSTANCE.getLayers(entity.getPokemon().getSpecies().getResourceIdentifier(), entity.getAspects())
      );
      if (entity.getTicksLived() < 10) {
         val var10: Any = entity.m_20088_().m_135370_(PokemonEntity.Companion.getSPAWN_DIRECTION());
         entity.f_20883_ = (var10 as java.lang.Number).floatValue();
         entity.f_20884_ = entity.f_20883_;
      }

      super.m_7392_(entity as Mob, entityYaw, partialTicks, poseMatrix, buffer, packedLight);
      modelNow.setGreen(1.0F);
      modelNow.setBlue(1.0F);
      modelNow.resetLayerContext();
      if (this.shouldRenderLabel(entity)) {
         val var11: Component = entity.m_5446_();
         this.renderLabelIfPresent(entity, var11, poseMatrix, buffer, packedLight);
      }
   }

   public fun renderTransition(
      modelNow: PoseableEntityModel<PokemonEntity>,
      beamMode: Int,
      entity: PokemonEntity,
      partialTicks: Float,
      poseMatrix: PoseStack,
      buffer: MultiBufferSource,
      packedLight: Int,
      clientDelegate: PokemonClientDelegate
   ) {
      val s: Float = clientDelegate.getSecondsSinceBeamEffectStarted();
      if (modelNow is PokemonPoseableModel && beamMode == 3 && s > 0.2F) {
         val beamSourcePosition: Float = 1.0F - Math.min(0.6F, (s - 0.2F) / 0.4F);
         modelNow.setGreen(beamSourcePosition);
         modelNow.setBlue(beamSourcePosition);
      }

      val var10000: Entity = clientDelegate.getPhaseTarget();
      if (var10000 != null) {
         poseMatrix.m_85836_();
         label83:
         if (var10000 is EmptyPokeBallEntity) {
            val var43: EntitySideDelegate = (var10000 as EmptyPokeBallEntity).getDelegate();
            val var44: MatrixWrapper = (var43 as PokeBallPoseableState).getLocatorStates().get("beam");
            if (var44 != null) {
               var45 = var44.getOrigin();
               if (var45 != null) {
                  break label83;
               }
            }

            var45 = (var10000 as EmptyPokeBallEntity).m_20182_();
         } else {
            val var46: UUID = var10000.m_20148_();
            val var10001: LocalPlayer = Minecraft.m_91087_().f_91074_;
            var45 = if (var46 == (if (var10001 != null) var10001.m_20148_() else null))
               var10000.m_20299_(partialTicks)
                  .m_82492_(0.0, 0.4, 0.0)
                  .m_82546_(var10000.m_20154_().m_82524_((float) (Math.PI / 2)).m_82542_(1.0, 0.0, 1.0).m_82541_().m_82490_(0.3))
               else
               var10000.m_20299_(partialTicks)
                  .m_82492_(0.0, 0.7, 0.0)
                  .m_82546_(
                     var10000.m_20154_()
                        .m_82524_((float) (Math.PI / 2) - AngleExtensionsKt.toRadians(var10000.m_213816_() - var10000.m_146909_()))
                        .m_82542_(1.0, 0.0, 1.0)
                        .m_82541_()
                        .m_82490_(0.4)
                  );
         }

         var var35: Vec3 = var45;
         if (clientDelegate.getSendOutPosition() == null && beamMode == 1) {
            clientDelegate.setSendOutPosition(var45);
         } else if (beamMode == 1) {
            val var51: Vec3 = clientDelegate.getSendOutPosition();
            clientDelegate.setSendOutPosition(var51.m_82520_(0.0, 0.04, 0.0));
            val var47: Vec3 = clientDelegate.getSendOutPosition();
            var35 = var47;
         }

         val var37: Vec3 = var35.m_82546_(entity.m_20182_()).m_82541_().m_82490_(-((double)clientDelegate.getBallOffset()));
         val angle: Vec3 = var35.m_82546_(entity.m_20182_());
         val var40: Vec3 = var37.m_82490_(2.0).m_82490_(var35.m_82554_(entity.m_20182_()) / 10.0 * (double)5);
         val var48: Vec3 = var40.m_82541_();
         clientDelegate.setSendOutOffset(var40);
         poseMatrix.m_85837_(angle.f_82479_ + var40.f_82479_, angle.f_82480_ + var40.f_82480_, angle.f_82481_ + var40.f_82481_);
         val dir: Vec3 = var35.m_82546_(entity.m_20182_()).m_82541_();
         poseMatrix.m_252781_(Axis.f_252436_.m_252961_(-((float)(Mth.m_14136_(dir.f_82481_, dir.f_82479_) - (double)(float) (Math.PI / 2))) + (float) Math.PI));
         if (beamMode == 1 && !clientDelegate.getBallDone()) {
            val var49: java.lang.String = entity.getPokemon().getCaughtBall().getName().toString();
            if (StringsKt.contains$default(var49, "beast", false, 2, null)) {
               poseMatrix.m_252781_(
                  Axis.f_252529_
                     .m_252961_(-((float)Mth.m_14136_(var48.f_82480_, Math.sqrt(var48.f_82479_ * var48.f_82479_ + var48.f_82481_ * var48.f_82481_))))
               );
            }

            label68: {
               val var42: java.lang.Iterable;
               for (Object element$iv : var42) {
                  if ((if (`element$iv` as Pokemon != null) (`element$iv` as Pokemon).getUuid() else null) == entity.getPokemon().getUuid()) {
                     var50 = `element$iv`;
                     break label68;
                  }
               }

               var50 = null;
            }

            var var53: PokeBall;
            label59: {
               val var10008: Pokemon = var50 as Pokemon;
               if (var50 as Pokemon != null) {
                  var53 = var10008.getCaughtBall();
                  if (var53 != null) {
                     break label59;
                  }
               }

               var53 = clientDelegate.getCurrentEntity().getPokemon().getCaughtBall();
            }

            drawPokeBall$default(
               this,
               new ClientBallDisplay(entity.getPokemon().getCaughtBall(), SetsKt.emptySet()),
               poseMatrix,
               clientDelegate.getBallOffset(),
               partialTicks,
               false,
               buffer,
               packedLight,
               var53,
               (int)Math.ceil(var35.m_82554_(entity.m_20182_()) / (double)4.0F),
               16,
               null
            );
         }

         poseMatrix.m_85849_();
         if (beamMode == 3) {
            this.renderBeam(poseMatrix, partialTicks, entity, var10000, buffer, var37);
         }
      }
   }

   protected open fun scale(pEntity: PokemonEntity, pMatrixStack: PoseStack, pPartialTickTime: Float) {
      val var10000: Float = pEntity.getPokemon().getForm().getBaseScale() * pEntity.getPokemon().getScaleModifier();
      val var10001: PokemonSideDelegate = pEntity.getDelegate();
      val scale: Float = var10000 * (var10001 as PokemonClientDelegate).getEntityScaleModifier();
      pMatrixStack.m_85841_(scale, scale, scale);
   }

   public fun renderBeam(matrixStack: PoseStack, partialTicks: Float, entity: PokemonEntity, beamTarget: Entity, buffer: MultiBufferSource, offset: Vec3) {
      var clientDelegate: PokemonClientDelegate;
      var pokemonPosition: Vec3;
      var var31: Vec3;
      val var10000: PokemonSideDelegate = entity.getDelegate();
      clientDelegate = var10000 as PokemonClientDelegate;
      pokemonPosition = entity.m_20182_()
         .m_82520_(0.0, (double)entity.m_20206_() / 2.0 * (double)(var10000 as PokemonClientDelegate).getEntityScaleModifier(), 0.0);
      label38:
      if (beamTarget is EmptyPokeBallEntity) {
         val var29: EntitySideDelegate = (beamTarget as EmptyPokeBallEntity).getDelegate();
         val var30: MatrixWrapper = (var29 as PokeBallPoseableState).getLocatorStates().get("beam");
         if (var30 != null) {
            var31 = var30.getOrigin();
            if (var31 != null) {
               break label38;
            }
         }

         var31 = (beamTarget as EmptyPokeBallEntity).m_20182_();
      } else {
         val var32: UUID = beamTarget.m_20148_();
         val var10001: LocalPlayer = Minecraft.m_91087_().f_91074_;
         var31 = if (var32 == (if (var10001 != null) var10001.m_20148_() else null))
            beamTarget.m_20299_(partialTicks)
               .m_82492_(0.0, 0.4, 0.0)
               .m_82546_(beamTarget.m_20154_().m_82524_((float) (Math.PI / 2)).m_82542_(1.0, 0.0, 1.0).m_82541_().m_82490_(0.3))
            else
            beamTarget.m_20299_(partialTicks)
               .m_82492_(0.0, 0.7, 0.0)
               .m_82546_(
                  beamTarget.m_20154_()
                     .m_82524_((float) (Math.PI / 2) - AngleExtensionsKt.toRadians(beamTarget.m_213816_() - beamTarget.m_146909_()))
                     .m_82542_(1.0, 0.0, 1.0)
                     .m_82541_()
                     .m_82490_(0.4)
               );
      }

      var beamSourcePosition: Vec3 = var31;
      if (clientDelegate.getSendOutPosition() != null) {
         var31 = clientDelegate.getSendOutPosition();
         beamSourcePosition = var31;
      }

      if (!(beamSourcePosition.m_82554_(pokemonPosition) > 20.0)) {
         val var24: Vec3 = offset.m_82490_(2.0)
            .m_82490_(beamSourcePosition.m_82554_(entity.m_20182_()) / 10.0 * (double)5)
            .m_82542_(0.0, (double)1 + Companion.ease((double)clientDelegate.getBallOffset()), 0.0);
         var ratio: Vec3 = pokemonPosition.m_82546_(beamSourcePosition.m_82549_(var24));
         val direction: Vector3f = new Vector3f((float)ratio.f_82479_, (float)ratio.f_82480_, (float)ratio.f_82481_);
         matrixStack.m_85836_();
         ratio = beamSourcePosition.m_82546_(entity.m_20182_());
         matrixStack.m_85837_(ratio.f_82479_ + var24.f_82479_, ratio.f_82480_ + var24.f_82480_, ratio.f_82481_ + var24.f_82481_);
         val s: Float = clientDelegate.getSecondsSinceBeamEffectStarted();
         val var26: Float = if (s < 0.2F) s / 0.2F else (if (s > 0.6F) 1 - Math.min((s - 0.2F - 0.4F) / 0.2F, 1.0F) else 1.0F);
         direction.normalize();
         val var28: Vector3f = new Vector3f(0.0F, 1.0F, 0.0F);
         val dot: Float = direction.dot(var28 as Vector3fc);
         val cross: Vector3f = var28.cross(direction as Vector3fc);
         matrixStack.m_252781_(new Quaternionf(cross.x, cross.y, cross.z, (float)1 + dot).normalize());
         RenderHelperKt.renderBeaconBeam$default(
            matrixStack,
            buffer,
            null,
            partialTicks,
            entity.m_9236_().m_46467_(),
            0.0F,
            (float)pokemonPosition.m_82554_(beamSourcePosition.m_82549_(offset)) * var26,
            recallBeamColour.x,
            recallBeamColour.y,
            recallBeamColour.z,
            recallBeamColour.w,
            0.03F,
            0.07F,
            0.4F,
            36,
            null
         );
         matrixStack.m_85849_();
      }
   }

   protected open fun getLyingAngle(entity: PokemonEntity?): Float {
      return 0.0F;
   }

   protected open fun hasLabel(entity: PokemonEntity): Boolean {
      return false;
   }

   private fun shouldRenderLabel(entity: PokemonEntity): Boolean {
      if (!super.m_6512_(entity as Mob)) {
         return false;
      } else {
         var var10000: Any = entity.m_20088_().m_135370_(PokemonEntity.Companion.getHIDE_LABEL());
         if (var10000 as java.lang.Boolean) {
            return false;
         } else {
            var10000 = Minecraft.m_91087_().f_91074_;
            if (var10000 == null) {
               return false;
            } else {
               val var4: PokemonSideDelegate = entity.getDelegate();
               var10000 = var4 as? PokemonClientDelegate;
               if ((var4 as? PokemonClientDelegate) == null) {
                  return false;
               } else {
                  return PlayerExtensionsKt.isLookingAt$default(var10000 as Entity, entity as Entity, 0.0F, 0.0F, 6, null)
                     && ((PokemonClientDelegate)var10000).getPhaseTarget() == null;
               }
            }
         }
      }
   }

   protected open fun renderLabelIfPresent(entity: PokemonEntity, text: Component, matrices: PoseStack, vertexConsumers: MultiBufferSource, light: Int) {
      if (!entity.m_20145_()) {
         val var10000: LocalPlayer = Minecraft.m_91087_().f_91074_;
         if (var10000 != null) {
            val d: Double = this.f_114476_.m_114471_(entity as Entity);
            if (d <= 4096.0) {
               val scale: Double = Math.min(1.5, Math.max(0.65, SimpleMathExtensionsKt.remap(d, new DoubleRange(-16.0, 96.0), new DoubleRange(0.0, 1.0))));
               val sizeScale: Double = Mth.m_14139_(SimpleMathExtensionsKt.remap(scale, new DoubleRange(0.65, 1.5), new DoubleRange(0.0, 1.0)), 0.5, 1.0);
               val offsetScale: Double = Mth.m_14139_(SimpleMathExtensionsKt.remap(scale, new DoubleRange(0.65, 1.5), new DoubleRange(0.0, 1.0)), 0.0, 1.0);
               val entityHeight: Double = entity.m_20191_().m_82376_() + 0.5F;
               matrices.m_85836_();
               matrices.m_85837_(0.0, entityHeight, 0.0);
               matrices.m_252781_(this.f_114476_.m_253208_());
               matrices.m_85837_(0.0, 0.0 + offsetScale / (double)2, -(scale + offsetScale));
               matrices.m_85841_((float)(-0.025 * sizeScale), (float)(-0.025 * sizeScale), (float)1 * (float)sizeScale);
               val matrix4f: Matrix4f = matrices.m_85850_().m_252922_();
               val opacity: Int = (int)(Minecraft.m_91087_().f_91066_.m_92141_(0.25F) * 255.0F) shl 24;
               var label: MutableComponent = entity.m_7755_().m_6881_();
               if (ServerSettings.INSTANCE.getDisplayEntityLevelLabel()) {
                  val var29: Int = entity.labelLevel();
                  if (var29.intValue() > 0) {
                     val y: Array<Any> = new Object[1];
                     val var10003: Int = entity.labelLevel();
                     y[0] = var10003;
                     val h: MutableComponent = LocalizationUtilsKt.lang("label.lv", y);
                     label = TextKt.add(label, " ").m_7220_(h as Component);
                  }
               }

               var var26: Float = -this.m_114481_().m_92852_(label as FormattedText) / 2;
               val packedLight: Int = LightTexture.m_109885_(15, 15);
               this.m_114481_()
                  .m_272077_(label as Component, var26, 0.0F, 553648127, false, matrix4f, vertexConsumers, DisplayMode.SEE_THROUGH, opacity, packedLight);
               this.m_114481_().m_272077_(label as Component, var26, 0.0F, -1, false, matrix4f, vertexConsumers, DisplayMode.NORMAL, 0, packedLight);
               if (entity.canBattle(var10000 as Player)) {
                  val sendOutBinding: Component = CurrentKeyAccessorKt.boundKey(PartySendBinding.INSTANCE).m_84875_();
                  val var25: Array<Any> = new Object[1];
                  var25[0] = sendOutBinding;
                  val battlePrompt: MutableComponent = LocalizationUtilsKt.lang("challenge_label", var25);
                  var26 = -this.m_114481_().m_92852_(battlePrompt as FormattedText) / 2;
                  this.m_114481_()
                     .m_272077_(
                        battlePrompt as Component,
                        var26,
                        0.0F + (float)10,
                        553648127,
                        false,
                        matrix4f,
                        vertexConsumers,
                        DisplayMode.SEE_THROUGH,
                        opacity,
                        packedLight
                     );
                  this.m_114481_()
                     .m_272077_(battlePrompt as Component, var26, 0.0F + (float)10, -1, false, matrix4f, vertexConsumers, DisplayMode.NORMAL, 0, packedLight);
               }

               matrices.m_85849_();
            }
         }
      }
   }

   private fun drawPokeBall(
      state: ClientBallDisplay,
      matrixStack: PoseStack,
      scale: Float = 5.0F,
      partialTicks: Float,
      reversed: Boolean = false,
      buff: MultiBufferSource,
      packedLight: Int,
      ball: PokeBall,
      distance: Int
   ) {
      matrixStack.m_85836_();
      matrixStack.m_85841_(0.7F, -0.7F, -0.7F);
      val model: PokeBallModel = PokeBallModelRepository.INSTANCE.getPoser(ball.getName(), state.getAspects());
      val texture: ResourceLocation = PokeBallModelRepository.INSTANCE.getTexture(ball.getName(), state.getAspects(), state.getAnimationSeconds());
      if (scale == 1.0F) {
         model.moveToPose(null, state, model.getOpen());
      } else {
         matrixStack.m_85837_(0.0, -0.2, 0.0);
         val buffer: Float = 360.0F * distance;
         val var10000: java.lang.String = ball.getName().toString();
         if (StringsKt.contains$default(var10000, "beast", false, 2, null)) {
            matrixStack.m_252781_(Axis.f_252393_.m_252977_(org.joml.Math.lerp(0.0F, buffer, scale)));
         } else {
            matrixStack.m_252781_(Axis.f_252495_.m_252977_(org.joml.Math.lerp(0.0F, buffer, scale)));
         }

         matrixStack.m_85837_(0.0, 0.2, 0.0);
      }

      state.setTimeEnteredPose(0.0F);
      state.updatePartialTicks(partialTicks);
      model.setupAnimStateful(null, state, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
      model.m_6839_(null, 0.0F, 0.0F, 0.0F);
      val var13: VertexConsumer = ItemRenderer.m_115222_(buff, model.m_103119_(texture), false, false);
      model.m_7695_(matrixStack, var13, packedLight, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
      model.setGreen(1.0F);
      model.setBlue(1.0F);
      model.setRed(1.0F);
      model.resetLayerContext();
      matrixStack.m_85849_();
   }

   public companion object {
      public final val recallBeamColour: Vector4f

      public fun ease(x: Double): Double {
         return 1 - Math.pow((double)1 - x, (double)3);
      }
   }
}
