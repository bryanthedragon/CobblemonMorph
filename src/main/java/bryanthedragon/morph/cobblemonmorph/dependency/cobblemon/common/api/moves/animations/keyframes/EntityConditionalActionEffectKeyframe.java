package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes

import com.bedrockk.molang.runtime.MoLangEnvironment
import com.bedrockk.molang.runtime.MoParams
import com.bedrockk.molang.runtime.struct.QueryStruct
import com.bedrockk.molang.runtime.value.DoubleValue
import com.bedrockk.molang.runtime.value.StringValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ExpressionLike
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.ActionEffectContext
import java.util.HashMap
import net.minecraft.world.entity.Entity

public interface EntityConditionalActionEffectKeyframe {
   public val entityCondition: ExpressionLike

   public open fun test(context: ActionEffectContext, entity: Entity, isUser: Boolean): Boolean {
   }

   // $VF: Class flags could not be determined
   internal class DefaultImpls {
      @JvmStatic
      fun test(`$this`: EntityConditionalActionEffectKeyframe, context: ActionEffectContext, entity: Entity, isUser: Boolean): Boolean {
         val var10000: MoLangFunctions = MoLangFunctions.INSTANCE;
         val var10001: MoLangEnvironment = context.getRuntime().getEnvironment();
         MoLangFunctions.getQueryStruct$default(var10000, var10001, null, 1, null)
            .addFunction("entity", EntityConditionalActionEffectKeyframe.DefaultImpls::test$lambda$2);
         return `$this`.getEntityCondition().resolveBoolean(context.getRuntime());
      }

      @JvmStatic
      fun `test$lambda$2$lambda$0`(`$entity`: Entity, it: MoParams): Any {
         return new StringValue(`$entity`.m_20149_());
      }

      @JvmStatic
      fun `test$lambda$2$lambda$1`(`$isUser`: Boolean, it: MoParams): Any {
         return new DoubleValue(`$isUser`);
      }

      @JvmStatic
      fun `test$lambda$2`(`$entity`: Entity, `$isUser`: Boolean, it: MoParams): Any {
         return new QueryStruct(new HashMap<>())
            .addFunction("uuid", EntityConditionalActionEffectKeyframe.DefaultImpls::test$lambda$2$lambda$0)
            .addFunction("is_user", EntityConditionalActionEffectKeyframe.DefaultImpls::test$lambda$2$lambda$1);
      }
   }
}
