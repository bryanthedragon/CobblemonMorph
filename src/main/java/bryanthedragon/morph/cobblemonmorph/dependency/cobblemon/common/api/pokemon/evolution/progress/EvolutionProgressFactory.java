package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.progress

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.progress.DamageTakenEvolutionProgress
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.progress.DefeatEvolutionProgress
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.progress.LastBattleCriticalHitsEvolutionProgress
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.progress.RecoilEvolutionProgress
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.progress.UseMoveEvolutionProgress
import java.util.HashMap
import kotlin.jvm.functions.Function0

public object EvolutionProgressFactory {
   private final val variants: HashMap<String, () -> EvolutionProgress<*>> = new HashMap()

   public fun registerVariant(variant: String, factory: () -> EvolutionProgress<*>) {
      variants.put(variant, factory);
   }

   public fun create(variant: String): EvolutionProgress<*>? {
      val var10000: Function0 = variants.get(variant);
      return if (var10000 == null) null else var10000.invoke() as EvolutionProgress;
   }

   @JvmStatic
   fun {
      var var10000: EvolutionProgressFactory = INSTANCE;
      var var10001: java.lang.String = DamageTakenEvolutionProgress.Companion.getID().toString();
      var10000.registerVariant(var10001, <unrepresentable>.INSTANCE);
      var10000 = INSTANCE;
      var10001 = DefeatEvolutionProgress.Companion.getID().toString();
      var10000.registerVariant(var10001, <unrepresentable>.INSTANCE);
      var10000 = INSTANCE;
      var10001 = LastBattleCriticalHitsEvolutionProgress.Companion.getID().toString();
      var10000.registerVariant(var10001, <unrepresentable>.INSTANCE);
      var10000 = INSTANCE;
      var10001 = RecoilEvolutionProgress.Companion.getID().toString();
      var10000.registerVariant(var10001, <unrepresentable>.INSTANCE);
      var10000 = INSTANCE;
      var10001 = UseMoveEvolutionProgress.Companion.getID().toString();
      var10000.registerVariant(var10001, <unrepresentable>.INSTANCE);
   }
}
