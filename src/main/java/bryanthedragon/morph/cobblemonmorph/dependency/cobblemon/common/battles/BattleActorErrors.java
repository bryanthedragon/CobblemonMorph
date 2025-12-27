package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.BattleActor
import java.util.HashMap
import java.util.LinkedHashSet
import kotlin.jvm.internal.SourceDebugExtension

@SourceDebugExtension(["SMAP\nBattleBuilder.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BattleBuilder.kt\ncom/cobblemon/mod/common/battles/BattleActorErrors\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,285:1\n1#2:286\n*E\n"])
public open class BattleActorErrors : HashMap<BattleActor, java.util.Set<BattleStartError>> {
   public open operator fun get(key: BattleActor): MutableSet<BattleStartError> {
      val var2: java.util.Set = super.get(key) as java.util.Set;
      val var10000: java.util.Set;
      if (var2 == null) {
         val var3: java.util.Set = new LinkedHashSet();
         (this as java.util.Map).put(key, var3);
         var10000 = var3;
      } else {
         var10000 = var2;
      }

      return var10000;
   }
}
