package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation

import com.bedrockk.molang.Expression
import com.bedrockk.molang.runtime.MoLangEnvironment
import com.bedrockk.molang.runtime.MoLangRuntime
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.BedrockParticleEffect
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.particle.ParticleStorm
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.MatrixWrapper
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt
import java.util.ArrayList;
import java.util.HashMap
import kotlin.jvm.functions.Function0
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level
import net.minecraft.world.phys.Vec3
import org.jetbrains.annotations.NotNull

@SourceDebugExtension(["SMAP\nBedrockAnimation.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BedrockAnimation.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockParticleKeyframe\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,346:1\n1549#2:347\n1620#2,3:348\n*S KotlinDebug\n*F\n+ 1 BedrockAnimation.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockParticleKeyframe\n*L\n68#1:347\n68#1:348,3\n*E\n"])
public class BedrockParticleKeyframe(seconds: Float, effect: BedrockParticleEffect, locator: String, scripts: List<Expression>) : BedrockEffectKeyframe(seconds) {
   public final val effect: BedrockParticleEffect
   public final val locator: String
   public final val scripts: List<Expression>

   init {
      this.effect = effect;
      this.locator = locator;
      this.scripts = scripts;
   }

   public fun isSameAs(other: BedrockParticleKeyframe): Boolean {
      val var10000: Boolean;
      if (this.getSeconds() != other.getSeconds()) {
         var10000 = false;
      } else if (!(this.effect == other.effect)) {
         var10000 = false;
      } else if (!(this.locator == other.locator)) {
         var10000 = false;
      } else {
         var `$this$map$iv`: java.lang.Iterable = this.scripts;
         var `destination$iv$iv`: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(this.scripts, 10));

         for (Object item$iv$iv : $this$map$iv) {
            `destination$iv$iv`.add(MoLangExtensionsKt.getString(`item$iv$iv` as Expression));
         }

         val var21: java.util.Set = CollectionsKt.toSet(`destination$iv$iv` as java.util.List);
         `$this$map$iv` = other.scripts;
         `destination$iv$iv` = new ArrayList(CollectionsKt.collectionSizeOrDefault(other.scripts, 10));

         for (Object item$iv$iv : $this$map$iv) {
            `destination$iv$iv`.add(MoLangExtensionsKt.getString(var18 as Expression));
         }

         var10000 = var21 == CollectionsKt.toSet(`destination$iv$iv` as java.util.List);
      }

      return var10000;
   }

   public override fun <T : Entity> run(entity: Any, state: PoseableEntityState<Any>) {
      val particleRuntime: Level = entity.m_9236_();
      var var10000: ClientLevel = particleRuntime as? ClientLevel;
      if ((particleRuntime as? ClientLevel) != null) {
         var var12: MatrixWrapper = state.getLocatorStates().get(this.locator);
         if (var12 == null) {
            var10000 = state.getLocatorStates().get("root");
            var12 = var10000 as MatrixWrapper;
         }

         if (!state.getPoseParticles().contains(this)) {
            val var9: MoLangRuntime = new MoLangRuntime();
            val var14: HashMap = var9.getEnvironment().getStructs();
            val storm: java.util.Map = var14;
            val var15: MoLangFunctions = MoLangFunctions.INSTANCE;
            val var10001: MoLangEnvironment = state.getRuntime().getEnvironment();
            storm.put("query", MoLangFunctions.getQueryStruct$default(var15, var10001, null, 1, null));
            val var10: ParticleStorm = new ParticleStorm(this.effect, var12, var10000, (new Function0<Vec3>(entity) {
               {
                  super(0);
                  this.$entity = (T)`$entity`;
               }

               @NotNull
               public final Vec3 invoke() {
                  val var10000: Vec3 = this.$entity.m_20184_();
                  return var10000;
               }
            }) as () -> Vec3, (new Function0<java.lang.Boolean>(entity, state, this) {
               {
                  super(0);
                  this.$entity = (T)`$entity`;
                  this.$state = `$state`;
                  this.this$0 = `$receiver`;
               }

               @NotNull
               public final java.lang.Boolean invoke() {
                  return !this.$entity.m_213877_() && this.$state.getPoseParticles().contains(this.this$0);
               }
            }) as () -> java.lang.Boolean, (new Function0<java.lang.Boolean>(entity) {
               {
                  super(0);
                  this.$entity = (T)`$entity`;
               }

               @NotNull
               public final java.lang.Boolean invoke() {
                  return !this.$entity.m_20145_();
               }
            }) as () -> java.lang.Boolean, (new Function0<Unit>(state, this) {
               {
                  super(0);
                  this.$state = `$state`;
                  this.this$0 = `$receiver`;
               }

               public final void invoke() {
                  this.$state.getPoseParticles().remove(this.this$0);
               }
            }) as () -> Unit, var9, entity);
            state.getPoseParticles().add(this);
            var10.getRuntime().execute(this.scripts);
            var10.spawn();
         }
      }
   }
}
