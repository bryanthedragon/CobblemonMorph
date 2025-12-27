package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.entity.Despawner;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition.AreaSpawningCondition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition.BasicSpawningCondition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition.GroundedSpawningCondition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition.SpawningCondition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition.SubmergedSpawningCondition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition.SurfaceSpawningCondition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.GroundedSpawningContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.LavafloorSpawningContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SeafloorSpawningContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SpawningContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SubmergedSpawningContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SurfaceSpawningContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.calculators.GroundedSpawningContextCalculator;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.calculators.LavafloorSpawningContextCalculator;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.calculators.SeafloorSpawningContextCalculator;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.calculators.SpawningContextCalculator;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.calculators.SubmergedSpawningContextCalculator;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.calculators.SurfaceSpawningContextCalculator;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.PokemonSpawnDetail;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnDetail;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.preset.BasicSpawnDetailPreset;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.preset.BestSpawnerConfig;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.preset.PokemonSpawnDetailPreset;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.CobblemonAgingDespawner;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;

public object BestSpawner {
   public final var config: BestSpawnerConfig = new BestSpawnerConfig()
   public final var defaultPokemonDespawner: Despawner<PokemonEntity> =
      (new CobblemonAgingDespawner(0.0F, 0.0F, 0, 0, <unrepresentable>.INSTANCE, 15, null)) as Despawner
      public final val spawnerManagers: MutableList<SpawnerManager> = CollectionsKt.mutableListOf(new SpawnerManager[]{CobblemonWorldSpawnerManager.INSTANCE})

   public fun loadConfig() {
      Cobblemon.INSTANCE.getLOGGER().info("Starting the Best Spawner...");
      SpawningCondition.Companion.register("basic", BasicSpawningCondition::class.java);
      SpawningCondition.Companion.register("area", AreaSpawningCondition::class.java);
      SpawningCondition.Companion.register("submerged", SubmergedSpawningCondition::class.java);
      SpawningCondition.Companion.register("grounded", GroundedSpawningCondition::class.java);
      SpawningCondition.Companion.register("surface", SurfaceSpawningCondition::class.java);
      Cobblemon.INSTANCE.getLOGGER().info("Loaded ${SpawningCondition.Companion.getConditionTypes().size()} spawning condition types.");
      SpawningContextCalculator.Companion.register$default(SpawningContextCalculator.Companion, GroundedSpawningContextCalculator.INSTANCE, null, 2, null);
      SpawningContextCalculator.Companion.register$default(SpawningContextCalculator.Companion, SeafloorSpawningContextCalculator.INSTANCE, null, 2, null);
      SpawningContextCalculator.Companion.register$default(SpawningContextCalculator.Companion, LavafloorSpawningContextCalculator.INSTANCE, null, 2, null);
      SpawningContextCalculator.Companion.register$default(SpawningContextCalculator.Companion, SubmergedSpawningContextCalculator.INSTANCE, null, 2, null);
      SpawningContextCalculator.Companion.register$default(SpawningContextCalculator.Companion, SurfaceSpawningContextCalculator.INSTANCE, null, 2, null);
      SpawningContext.Companion.register("grounded", GroundedSpawningContext::class.java, "grounded");
      SpawningContext.Companion.register("seafloor", SeafloorSpawningContext::class.java, "grounded");
      SpawningContext.Companion.register("lavafloor", LavafloorSpawningContext::class.java, "grounded");
      SpawningContext.Companion.register("submerged", SubmergedSpawningContext::class.java, "submerged");
      SpawningContext.Companion.register("surface", SurfaceSpawningContext::class.java, "surface");
      Cobblemon.INSTANCE.getLOGGER().info("Loaded ${SpawningContext.Companion.getContexts().size()} spawning context types.");
      SpawnDetail.Companion.registerSpawnType(PokemonSpawnDetail.Companion.getTYPE(), PokemonSpawnDetail::class.java);
      Cobblemon.INSTANCE.getLOGGER().info("Loaded ${SpawnDetail.Companion.getSpawnDetailTypes().size()} spawn detail types.");
      config = BestSpawnerConfig.Companion.load();
      SpawnDetailPresets.INSTANCE.registerPresetType("basic", BasicSpawnDetailPreset::class.java);
      SpawnDetailPresets.INSTANCE.registerPresetType("pokemon", PokemonSpawnDetailPreset::class.java);
   }

   public fun onServerStarted() {
      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         (`element$iv` as SpawnerManager).onServerStarted();
      }
   }
}
