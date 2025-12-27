package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.rules.component

import com.bedrockk.molang.Expression
import com.bedrockk.molang.runtime.MoLangEnvironment
import com.bedrockk.molang.runtime.MoLangRuntime
import com.bedrockk.molang.runtime.value.DoubleValue
import com.bedrockk.molang.runtime.value.StringValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ObjectValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnBucket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SpawningContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.calculators.SpawningContextCalculator
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnAction
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnDetail
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt
import kotlin.jvm.internal.Intrinsics
import net.minecraft.core.BlockPos
import net.minecraft.core.Holder
import net.minecraft.core.registries.Registries
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level
import net.minecraft.world.level.dimension.DimensionType

public class LocationRuleCalculator : SpawnRuleComponent {
   public final val allow: Expression = MoLangExtensionsKt.asExpression("true")
   private final val reusableContext: StringValue = new StringValue("")
   private final lateinit var reusableDimensionTypeValue: ObjectValue<Holder<DimensionType>>
   private final lateinit var reusableWorldValue: ObjectValue<Holder<Level>>
   private final val reusableX: DoubleValue = new DoubleValue(0.0)
   private final val reusableY: DoubleValue = new DoubleValue(0.0)
   private final val reusableZ: DoubleValue = new DoubleValue(0.0)
   public final val runtime: MoLangRuntime = MoLangFunctions.INSTANCE.setup(new MoLangRuntime())

   public override fun isAllowedPosition(world: ServerLevel, pos: BlockPos, contextCalculator: SpawningContextCalculator<*, *>): Boolean {
      this.reusableX.value = pos.m_123341_();
      this.reusableY.value = pos.m_123342_();
      this.reusableZ.value = pos.m_123343_();
      this.reusableContext.value = contextCalculator.getName();
      if (this.reusableWorldValue == null) {
         val var10001: MoLangFunctions = MoLangFunctions.INSTANCE;
         val var10002: Any = world.m_9598_().m_175515_(Registries.f_256858_).m_203636_(world.m_46472_()).get();
         this.reusableWorldValue = var10001.asWorldMoLangValue(var10002 as Holder<Level>);
      } else {
         var var10000: ObjectValue = this.reusableWorldValue;
         if (this.reusableWorldValue == null) {
            Intrinsics.throwUninitializedPropertyAccessException("reusableWorldValue");
            var10000 = null;
         }

         val var8: Any = world.m_9598_().m_175515_(Registries.f_256858_).m_203636_(world.m_46472_()).get();
         var10000.setObj(var8);
      }

      if (this.reusableDimensionTypeValue == null) {
         val var9: MoLangFunctions = MoLangFunctions.INSTANCE;
         val var12: Holder = world.m_204156_();
         this.reusableDimensionTypeValue = var9.asDimensionTypeMoLangValue(var12);
      } else {
         var var4: ObjectValue = this.reusableDimensionTypeValue;
         if (this.reusableDimensionTypeValue == null) {
            Intrinsics.throwUninitializedPropertyAccessException("reusableDimensionTypeValue");
            var4 = null;
         }

         val var10: Holder = world.m_204156_();
         var4.setObj(var10);
      }

      this.runtime.getEnvironment().setSimpleVariable("x", this.reusableX);
      this.runtime.getEnvironment().setSimpleVariable("y", this.reusableY);
      this.runtime.getEnvironment().setSimpleVariable("z", this.reusableZ);
      this.runtime.getEnvironment().setSimpleVariable("context", this.reusableContext);
      var var5: MoLangEnvironment = this.runtime.getEnvironment();
      var var13: ObjectValue = this.reusableWorldValue;
      if (this.reusableWorldValue == null) {
         Intrinsics.throwUninitializedPropertyAccessException("reusableWorldValue");
         var13 = null;
      }

      var5.setSimpleVariable("world", var13);
      var5 = this.runtime.getEnvironment();
      var13 = this.reusableDimensionTypeValue;
      if (this.reusableDimensionTypeValue == null) {
         Intrinsics.throwUninitializedPropertyAccessException("reusableDimensionTypeValue");
         var13 = null;
      }

      var5.setSimpleVariable("dimension_type", var13);
      val var7: MoLangRuntime = this.runtime;
      val var11: Expression = this.allow;
      return MoLangExtensionsKt.resolveBoolean(var7, var11);
   }

   override fun isExpired(): Boolean {
      return SpawnRuleComponent.DefaultImpls.isExpired(this);
   }

   override fun affectSpawnable(detail: SpawnDetail, ctx: SpawningContext): Boolean {
      return SpawnRuleComponent.DefaultImpls.affectSpawnable(this, detail, ctx);
   }

   override fun affectWeight(detail: SpawnDetail, ctx: SpawningContext, weight: Float): Float {
      return SpawnRuleComponent.DefaultImpls.affectWeight(this, detail, ctx, weight);
   }

   override fun affectAction(action: SpawnAction<?>) {
      SpawnRuleComponent.DefaultImpls.affectAction(this, action);
   }

   override fun affectSpawn(entity: Entity) {
      SpawnRuleComponent.DefaultImpls.affectSpawn(this, entity);
   }

   override fun affectBucketWeight(bucket: SpawnBucket, weight: Float): Float {
      return SpawnRuleComponent.DefaultImpls.affectBucketWeight(this, bucket, weight);
   }
}
