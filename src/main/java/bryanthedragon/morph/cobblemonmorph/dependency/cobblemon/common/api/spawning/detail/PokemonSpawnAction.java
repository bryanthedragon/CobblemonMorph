package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SpawningContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.feature.SeasonFeatureHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.CollectionUtilsKt
import java.util.ArrayList;
import kotlin.jvm.internal.SourceDebugExtension
import kotlin.random.Random
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelAccessor

@SourceDebugExtension(["SMAP\nPokemonSpawnAction.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PokemonSpawnAction.kt\ncom/cobblemon/mod/common/api/spawning/detail/PokemonSpawnAction\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,58:1\n1#2:59\n*E\n"])
public class PokemonSpawnAction(ctx: SpawningContext, detail: PokemonSpawnDetail, props: PokemonProperties = detail.getPokemon().copy()) : SingleEntitySpawnAction(
      ctx, detail
   ) {
   public open val detail: PokemonSpawnDetail
   public final var props: PokemonProperties

   init {
      this.detail = detail;
      this.props = props;
   }

   public open fun createEntity(): PokemonEntity {
      if (this.props.getSpecies() == null) {
         Cobblemon.INSTANCE.getLOGGER().error("PokemonSpawnAction run with null species - Spawn detail: ${this.getDetail().getId()}");
      }

      if (this.props.getLevel() == null) {
         this.props.setLevel(RangesKt.random(this.getDetail().getDerivedLevelRange(), Random.Default as Random));
      }

      var var23: java.util.List;
      label63: {
         var23 = this.getDetail().getHeldItems();
         if (var23 != null) {
            var23 = if (!var23.isEmpty()) var23 else null;
            if (var23 != null) {
               var23 = CollectionsKt.toMutableList(var23);
               if (var23 != null) {
                  break label63;
               }
            }
         }

         var23 = new ArrayList();
      }

      val var25: PossibleHeldItem;
      if (!var23.isEmpty()) {
         val var24: Double = 1;
         val var21: java.lang.Iterable = var23;
         var var7: Double = 0.0;

         for (Object var10 : var21) {
            var7 += (var10 as PossibleHeldItem).getPercentage() / 100;
         }

         var25 = if (var24 - var7 > 0.0 && this.getCtx().getWorld().f_46441_.m_188500_() < var24 - var7)
            null
            else
            CollectionUtilsKt.weightedSelection(var23, <unrepresentable>.INSTANCE);
      } else {
         var25 = null;
      }

      val heldItem: ItemStack = if (var25 != null) var25.createStack(this.getCtx()) else null;
      val var19: PokemonEntity = this.props.createEntity(this.getCtx().getWorld() as Level);
      SeasonFeatureHandler.INSTANCE
         .updateSeason(
            var19.getPokemon(), Cobblemon.INSTANCE.getSeasonResolver().invoke(this.getCtx().getWorld() as LevelAccessor, this.getCtx().getPosition())
         );
      if (heldItem != null) {
         Pokemon.swapHeldItem$default(var19.getPokemon(), heldItem, false, 2, null);
      }

      var19.setDrops(this.getDetail().getDrops());
      return var19;
   }
}
