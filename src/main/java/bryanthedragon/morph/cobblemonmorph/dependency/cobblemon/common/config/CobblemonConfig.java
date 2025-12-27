package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.config

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.drop.ItemDropMethod
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.calculators.CaptureCalculator
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status.Statuses
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.config.constraint.IntConstraint
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokeball.catching.calculators.CobblemonCaptureCalculator
import com.google.gson.Gson

public class CobblemonConfig {
   public final var allowExperienceFromPvP: Boolean = true
   public final var allowSpectating: Boolean = true
   public final var ambientPokemonCryTicks: Int = 1080
   public final var announceDropItems: Boolean = true
   public final var appleLeftoversChance: Double = 0.025
   public final var autoUpdateShowdown: Boolean = true
   public final var baseApricornTreeGenerationChance: Float = 0.1F
   public final var bigRootPropagationChance: Double = 0.1
   public final var captureCalculator: CaptureCalculator = CobblemonCaptureCalculator.INSTANCE as CaptureCalculator
   public final var chargeGainedPerTick: Float = 3.33333E-4F

   @IntConstraint(min = 1, max = 1000)
   public final var defaultBoxCount: Int = 30

   public final var defaultDropItemMethod: ItemDropMethod = ItemDropMethod.ON_ENTITY
   public final var defaultFaintTimer: Int = 300
   public final var defaultFleeDistance: Float = 32.0F
   public final var defaultPasturedPokemonLimit: Int = 16
   public final var displayEntityLevelLabel: Boolean = true
   public final var enableDebugKeys: Boolean
   public final var enableSpawning: Boolean = true
   public final var energyRootChance: Double = 0.25
   public final var experienceMultiplier: Float = 2.0F
   public final var experienceShareMultiplier: Double = 0.5
   public final var exportSpawnConfig: Boolean
   public final var exportStarterConfig: Boolean
   public final var faintAwakenHealthPercent: Float = 0.2F
   public final var healPercent: Double = 0.05
   public final var healTimer: Int = 60
   public final var infiniteHealerCharge: Boolean
   public final var lastSavedVersion: String = "0.0.1"
   public final var luckyEggMultiplier: Double = 1.5

   @IntConstraint(min = 0, max = 10)
   public final var maxDynamaxLevel: Int = 10

   public final var maxHealerCharge: Float = 6.0F
   public final var maxInsertedFossilItems: Int = 2
   public final var maxNearbyBlocksHorizontalRange: Int = 4
   public final var maxNearbyBlocksVerticalRange: Int = 2

   @IntConstraint(min = 0, max = 1000)
   public final var maxPokemonFriendship: Int = 255

   @IntConstraint(min = 1, max = 1000)
   public final var maxPokemonLevel: Int = 100

   public final var maxRootsInArea: Int = 5

   @IntConstraint(min = 1, max = 200)
   public final var maxVerticalCorrectionBlocks: Int = 64

   public final var maxVerticalSpace: Int = 8
   public final var maximumSliceDistanceFromPlayer: Float = 64.0F
   public final var minimumDistanceBetweenEntities: Double = 8.0

   @IntConstraint(min = 1, max = 1000)
   public final var minimumLevelRangeMax: Int = 10

   public final var minimumSliceDistanceFromPlayer: Float = 16.0F
   public final var mongoDBConnectionString: String = "mongodb://localhost:27017"
   public final var mongoDBDatabaseName: String = "cobblemon"
   public final var ninjaskCreatesShedinja: Boolean = true
   public final var passiveStatuses: MutableMap<String, IntRange> =
      MapsKt.mutableMapOf(
         new Pair[]{
            Statuses.INSTANCE.getPOISON().configEntry(),
            Statuses.INSTANCE.getPOISON_BADLY().configEntry(),
            Statuses.INSTANCE.getPARALYSIS().configEntry(),
            Statuses.INSTANCE.getFROZEN().configEntry(),
            Statuses.INSTANCE.getSLEEP().configEntry(),
            Statuses.INSTANCE.getBURN().configEntry()
         }
      )
      public final var pastureBlockUpdateTicks: Int = 40
   public final var pastureMaxPerChunk: Float = 4.0F
   public final var pastureMaxWanderDistance: Int = 64
   public final var playerDamagePokemon: Boolean = true
   public final var pokemonPerChunk: Float = 1.0F

   @IntConstraint(min = 1, max = 120)
   public final var pokemonSaveIntervalSeconds: Int = 30

   public final var preventCompletePartyDeposit: Boolean
   public final var savePokemonToWorld: Boolean = true
   public final var shinyRate: Float = 8192.0F
   public final var storageFormat: String = "nbt"
   public final var teraTypeRate: Float = 20.0F
   public final var ticksBetweenSpawnAttempts: Float = 20.0F
   public final var walkingInBattleAnimations: Boolean
   public final var worldSliceDiameter: Int = 8
   public final var worldSliceHeight: Int = 16

   public companion object {
      public final val GSON: Gson
   }
}
