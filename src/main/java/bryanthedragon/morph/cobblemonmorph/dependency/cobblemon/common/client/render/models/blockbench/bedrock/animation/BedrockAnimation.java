package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation

import com.bedrockk.molang.runtime.MoLangEnvironment
import com.bedrockk.molang.runtime.MoLangRuntime
import com.bedrockk.molang.runtime.MoParams
import com.bedrockk.molang.runtime.value.DoubleValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Bone
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository.RenderContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math.geometry.AngleExtensionsKt
import java.util.ArrayList;
import java.util.Map.Entry
import kotlin.jvm.functions.Function1
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.CrashReport
import net.minecraft.CrashReportCategory
import net.minecraft.ReportedException
import net.minecraft.client.model.geom.ModelPart
import net.minecraft.world.entity.Entity
import net.minecraft.world.phys.Vec3
import org.jetbrains.annotations.NotNull

@SourceDebugExtension(["SMAP\nBedrockAnimation.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BedrockAnimation.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockAnimation\n+ 2 _Maps.kt\nkotlin/collections/MapsKt___MapsKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,346:1\n215#2,2:347\n766#3:349\n857#3,2:350\n1855#3,2:352\n*S KotlinDebug\n*F\n+ 1 BedrockAnimation.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockAnimation\n*L\n161#1:347,2\n215#1:349\n215#1:350,2\n215#1:352,2\n*E\n"])
public data BedrockAnimation(shouldLoop: Boolean,
   animationLength: Double,
   effects: List<BedrockEffectKeyframe>,
   boneTimelines: Map<String, BedrockBoneTimeline>
) {
   public final val animationLength: Double
   public final val boneTimelines: Map<String, BedrockBoneTimeline>
   public final val effects: List<BedrockEffectKeyframe>
   public final val shouldLoop: Boolean

   init {
      this.shouldLoop = shouldLoop;
      this.animationLength = animationLength;
      this.effects = effects;
      this.boneTimelines = boneTimelines;
   }

   public fun run(model: PoseableEntityModel<*>, state: PoseableEntityState<*>?, animationSeconds: Float, intensity: Float): Boolean {
      var var22: Float = animationSeconds;
      if (this.shouldLoop) {
         var22 = animationSeconds % (float)this.animationLength;
      } else if (animationSeconds > this.animationLength && this.animationLength > 0.0) {
         return false;
      }

      for (Entry element$iv : this.boneTimelines.entrySet()) {
         val boneName: java.lang.String = `element$iv`.getKey() as java.lang.String;
         val timeline: BedrockBoneTimeline = `element$iv`.getValue() as BedrockBoneTimeline;
         var var10000: ModelPart = model.getRelevantPartsByName().get(boneName);
         if (var10000 == null) {
            if (boneName == "root_part") {
               val var29: Bone = model.getRootPart();
               var10000 = var29 as ModelPart;
            } else {
               var10000 = null;
            }
         }

         val part: ModelPart = var10000;
         if (var10000 != null) {
            if (!timeline.getPosition().isEmpty()) {
               var var10001: Double;
               var var10002: MoLangRuntime;
               label74: {
                  var30 = timeline.getPosition();
                  var10001 = var22;
                  if (state != null) {
                     var10002 = state.getRuntime();
                     if (var10002 != null) {
                        break label74;
                     }
                  }

                  var10002 = sharedRuntime;
               }

               val scale: Vec3 = var30.resolve(var10001, var10002).m_82490_((double)intensity);
               var10000.f_104200_ = var10000.f_104200_ + (float)scale.f_82479_;
               var10000.f_104201_ = var10000.f_104201_ + (float)scale.f_82480_;
               var10000.f_104202_ = var10000.f_104202_ + (float)scale.f_82481_;
            }

            if (!timeline.getRotation().isEmpty()) {
               try {
                  var var34: Double;
                  var var38: MoLangRuntime;
                  label64: {
                     var31 = timeline.getRotation();
                     var34 = var22;
                     if (state != null) {
                        var38 = state.getRuntime();
                        if (var38 != null) {
                           break label64;
                        }
                     }

                     var38 = sharedRuntime;
                  }

                  val var24: Vec3 = var31.resolve(var34, var38).m_82490_((double)intensity);
                  part.f_104203_ = part.f_104203_ + AngleExtensionsKt.toRadians((float)var24.f_82479_);
                  part.f_104204_ = part.f_104204_ + AngleExtensionsKt.toRadians((float)var24.f_82480_);
                  part.f_104205_ = part.f_104205_ + AngleExtensionsKt.toRadians((float)var24.f_82481_);
               } catch (var21: Exception) {
                  var var36: Any = model.getContext().request(RenderContext.Companion.getENTITY());
                  val crash: CrashReport = new CrashReport(
                     "Cobblemon encountered an unexpected crash",
                     new IllegalStateException("Bad animation for species: ${(var36 as PokemonEntity).getPokemon().getSpecies().getName()}", var21)
                  );
                  val var27: CrashReportCategory = crash.m_127514_("Animation Details");
                  if (state != null) {
                     var36 = state.getCurrentPose();
                     var27.m_128159_("Pose", var36);
                  }

                  var27.m_128159_("Bone", boneName);
                  throw new ReportedException(crash);
               }
            }

            if (!timeline.getScale().isEmpty()) {
               var var35: Double;
               var var39: MoLangRuntime;
               label56: {
                  var32 = timeline.getScale();
                  var35 = var22;
                  if (state != null) {
                     var39 = state.getRuntime();
                     if (var39 != null) {
                        break label56;
                     }
                  }

                  var39 = sharedRuntime;
               }

               val var33: Vec3 = var32.resolve(var35, var39)
                  .m_82490_(-1.0)
                  .m_82520_(1.0, 1.0, 1.0)
                  .m_82490_((double)intensity)
                  .m_82492_(1.0, 1.0, 1.0)
                  .m_82490_(-1.0);
               var10000.f_233553_ = var10000.f_233553_ * (float)var33.f_82479_;
               var10000.f_233554_ = var10000.f_233554_ * (float)var33.f_82480_;
               var10000.f_233555_ = var10000.f_233555_ * (float)var33.f_82481_;
            }
         }
      }

      return true;
   }

   public fun <T : Entity> applyEffects(entity: Any, state: PoseableEntityState<Any>, previousSeconds: Float, newSeconds: Float) {
      val effectCondition: Function1 = if (previousSeconds > newSeconds)
         (new Function1<BedrockEffectKeyframe, java.lang.Boolean>(previousSeconds, newSeconds) {
            {
               super(1);
               this.$previousSeconds = `$previousSeconds`;
               this.$newSeconds = `$newSeconds`;
            }

            @NotNull
            public final java.lang.Boolean invoke(@NotNull BedrockEffectKeyframe it) {
               return it.getSeconds() >= this.$previousSeconds || it.getSeconds() <= this.$newSeconds;
            }
         }) as Function1
         else
         (new Function1<BedrockEffectKeyframe, java.lang.Boolean>(previousSeconds, newSeconds) {
            {
               super(1);
               this.$previousSeconds = `$previousSeconds`;
               this.$newSeconds = `$newSeconds`;
            }

            @NotNull
            public final java.lang.Boolean invoke(@NotNull BedrockEffectKeyframe it) {
               val var2: Float = it.getSeconds();
               return this.$previousSeconds <= var2 && var2 <= this.$newSeconds;
            }
         }) as Function1;
      val `$this$forEach$iv`: java.lang.Iterable = this.effects;
      val `element$iv`: java.util.Collection = new ArrayList();

      for (Object element$iv$iv : $this$filter$iv) {
         if (effectCondition.invoke(`element$iv$iv`) as java.lang.Boolean) {
            `element$iv`.add(`element$iv$iv`);
         }
      }

      for (Object element$ivx : $this$filter$iv) {
         (`element$ivx` as BedrockEffectKeyframe).run(entity, state);
      }
   }

   public operator fun component1(): Boolean {
      return this.shouldLoop;
   }

   public operator fun component2(): Double {
      return this.animationLength;
   }

   public operator fun component3(): List<BedrockEffectKeyframe> {
      return this.effects;
   }

   public operator fun component4(): Map<String, BedrockBoneTimeline> {
      return this.boneTimelines;
   }

   public fun copy(
      shouldLoop: Boolean = this.shouldLoop,
      animationLength: Double = this.animationLength,
      effects: List<BedrockEffectKeyframe> = this.effects,
      boneTimelines: Map<String, BedrockBoneTimeline> = this.boneTimelines
   ): BedrockAnimation {
      return new BedrockAnimation(shouldLoop, animationLength, effects, boneTimelines);
   }

   public override fun toString(): String {
      return "BedrockAnimation(shouldLoop=${this.shouldLoop}, animationLength=${this.animationLength}, effects=${this.effects}, boneTimelines=${this.boneTimelines})";
   }

   public override fun hashCode(): Int {
      var var10000: Byte = this.shouldLoop;
      if (this.shouldLoop) {
         var10000 = 1;
      }

      return ((var10000 * 31 + java.lang.Double.hashCode(this.animationLength)) * 31 + this.effects.hashCode()) * 31 + this.boneTimelines.hashCode();
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this === other) {
         return true;
      } else if (other !is BedrockAnimation) {
         return false;
      } else {
         val var2: BedrockAnimation = other as BedrockAnimation;
         if (this.shouldLoop != (other as BedrockAnimation).shouldLoop) {
            return false;
         } else if (java.lang.Double.compare(this.animationLength, var2.animationLength) != 0) {
            return false;
         } else if (!(this.effects == var2.effects)) {
            return false;
         } else {
            return this.boneTimelines == var2.boneTimelines;
         }
      }
   }

   @JvmStatic
   fun `sharedRuntime$lambda$6$lambda$5`(`$zero`: DoubleValue, it: MoParams): Any {
      return `$zero`;
   }

   @JvmStatic
   fun {
      val var0: MoLangRuntime = new MoLangRuntime();
      val zero: DoubleValue = new DoubleValue(0.0);
      val var10000: MoLangFunctions = MoLangFunctions.INSTANCE;
      val var10001: MoLangFunctions = MoLangFunctions.INSTANCE;
      val var10002: MoLangEnvironment = var0.getEnvironment();
      var10000.addFunctions(
         MoLangFunctions.getQueryStruct$default(var10001, var10002, null, 1, null),
         MapsKt.mapOf(TuplesKt.to("anim_time", BedrockAnimation::sharedRuntime$lambda$6$lambda$5))
      );
      sharedRuntime = var0;
   }

   public companion object {
      public final val sharedRuntime: MoLangRuntime
   }
}
