package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mechanics

import com.bedrockk.molang.Expression
import com.bedrockk.molang.runtime.MoLangRuntime
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt
import java.util.LinkedHashMap
import kotlin.jvm.internal.SourceDebugExtension

@SourceDebugExtension(["SMAP\nRemediesMechanic.kt\nKotlin\n*S Kotlin\n*F\n+ 1 RemediesMechanic.kt\ncom/cobblemon/mod/common/mechanics/RemediesMechanic\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,22:1\n1#2:23\n*E\n"])
public class RemediesMechanic {
   public final val friendshipDrop: Expression = MoLangExtensionsKt.asExpression("10")
   public final val healingAmounts: MutableMap<String, Expression> = (new LinkedHashMap()) as java.util.Map

   public fun getHealingAmount(type: String, runtime: MoLangRuntime, default: Int = 20): Int {
      val var10000: Expression = this.healingAmounts.get(type);
      return if (var10000 != null) MoLangExtensionsKt.resolveInt(runtime, var10000) else default;
   }

   public fun getFriendshipDrop(runtime: MoLangRuntime): Int {
      val var10001: Expression = this.friendshipDrop;
      return MoLangExtensionsKt.resolveInt(runtime, var10001);
   }
}
