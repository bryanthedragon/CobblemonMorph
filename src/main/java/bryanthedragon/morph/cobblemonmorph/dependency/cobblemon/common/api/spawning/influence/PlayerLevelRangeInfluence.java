package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.influence

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnBucket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SpawningContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.calculators.SpawningContextCalculator
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.PokemonSpawnAction
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnAction
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnDetail
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.PokemonStoreManager
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PlayerPartyStore
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math.SimpleMathExtensionsKt
import java.util.NoSuchElementException
import java.util.UUID
import kotlin.jvm.internal.SourceDebugExtension
import kotlin.random.Random
import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.Entity

@SourceDebugExtension(["SMAP\nPlayerLevelRangeInfluence.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PlayerLevelRangeInfluence.kt\ncom/cobblemon/mod/common/api/spawning/influence/PlayerLevelRangeInfluence\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,74:1\n1#2:75\n*E\n"])
public open class PlayerLevelRangeInfluence(player: ServerPlayer,
      variation: Int,
      noPokemonRange: IntRange = new IntRange(1, Cobblemon.INSTANCE.getConfig().getMinimumLevelRangeMax()),
      recalculationMillis: Long = 5000L
   ) :
   SpawningInfluence {
   public final var lastCalculatedTime: Long
   public final val noPokemonRange: IntRange
   public final var previousRange: IntRange
   public final val recalculationMillis: Long
   public final val uuid: UUID
   public final val variation: Int

   init {
      this.variation = variation;
      this.noPokemonRange = noPokemonRange;
      this.recalculationMillis = recalculationMillis;
      this.uuid = player.m_20148_();
      this.previousRange = this.noPokemonRange;
   }

   public fun getPlayerLevelRange(): IntRange {
      val var14: IntRange;
      if (System.currentTimeMillis() - this.lastCalculatedTime > this.recalculationMillis) {
         this.lastCalculatedTime = System.currentTimeMillis();
         val var10000: PokemonStoreManager = Cobblemon.INSTANCE.getStorage();
         val var10001: UUID = this.uuid;
         val party: PlayerPartyStore = var10000.getParty(var10001);
         var var13: PlayerLevelRangeInfluence = this;
         val var15: IntRange;
         if (!CollectionsKt.any(party)) {
            var15 = this.noPokemonRange;
         } else {
            val var4: java.util.Iterator = party.iterator();
            if (!var4.hasNext()) {
               throw new NoSuchElementException();
            }

            var var10: Int = (var4.next() as Pokemon).getLevel();

            while (var4.hasNext()) {
               val var12: Int = (var4.next() as Pokemon).getLevel();
               if (var10 < var12) {
                  var10 = var12;
               }
            }

            var13 = this;
            var15 = new IntRange(
               Math.max(var10 - this.variation, 1),
               Math.min(
                  Cobblemon.INSTANCE.getConfig().getMaxPokemonLevel(),
                  Math.max(var10 + this.variation, Cobblemon.INSTANCE.getConfig().getMinimumLevelRangeMax())
               )
            );
         }

         var13.previousRange = var15;
         var14 = this.previousRange;
      } else {
         var14 = this.previousRange;
      }

      return var14;
   }

   public override fun affectAction(action: SpawnAction<*>) {
      if (action is PokemonSpawnAction && (action as PokemonSpawnAction).getProps().getLevel() == null) {
         val playerLevelRange: IntRange = this.getPlayerLevelRange();
         val derivedLevelRange: IntRange = (action as PokemonSpawnAction).getDetail().getDerivedLevelRange();
         var spawnLevelRange: IntRange = SimpleMathExtensionsKt.intersection(playerLevelRange, derivedLevelRange);
         val pokemonRangeWidth: Int = derivedLevelRange.getLast() - derivedLevelRange.getFirst();
         if (spawnLevelRange.isEmpty()) {
            spawnLevelRange = if (derivedLevelRange.getFirst() > playerLevelRange.getLast())
               new IntRange(derivedLevelRange.getFirst(), (int)(derivedLevelRange.getFirst() + pokemonRangeWidth / 4.0F))
               else
               new IntRange((int)(derivedLevelRange.getFirst() + 3 * pokemonRangeWidth / 4.0F), derivedLevelRange.getLast());
         }

         (action as PokemonSpawnAction).getProps().setLevel(RangesKt.random(spawnLevelRange, Random.Default as Random));
      }
   }

   override fun isExpired(): Boolean {
      return SpawningInfluence.DefaultImpls.isExpired(this);
   }

   override fun affectSpawnable(detail: SpawnDetail, ctx: SpawningContext): Boolean {
      return SpawningInfluence.DefaultImpls.affectSpawnable(this, detail, ctx);
   }

   override fun affectWeight(detail: SpawnDetail, ctx: SpawningContext, weight: Float): Float {
      return SpawningInfluence.DefaultImpls.affectWeight(this, detail, ctx, weight);
   }

   override fun affectSpawn(entity: Entity) {
      SpawningInfluence.DefaultImpls.affectSpawn(this, entity);
   }

   override fun affectBucketWeight(bucket: SpawnBucket, weight: Float): Float {
      return SpawningInfluence.DefaultImpls.affectBucketWeight(this, bucket, weight);
   }

   override fun isAllowedPosition(world: ServerLevel, pos: BlockPos, contextCalculator: SpawningContextCalculator<?, ?>): Boolean {
      return SpawningInfluence.DefaultImpls.isAllowedPosition(this, world, pos, contextCalculator);
   }
}
