package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.loot

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt
import java.util.HashSet
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.loot.LootPool.Builder
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.storage.loot.BuiltInLootTables
import net.minecraft.world.level.storage.loot.LootPool
import net.minecraft.world.level.storage.loot.entries.LootTableReference
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator
import org.jetbrains.annotations.ApiStatus.Internal

@Internal
@SourceDebugExtension(["SMAP\nLootInjector.kt\nKotlin\n*S Kotlin\n*F\n+ 1 LootInjector.kt\ncom/cobblemon/mod/common/loot/LootInjector\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,103:1\n1#2:104\n*E\n"])
public object LootInjector {
   private const val PREFIX: String = "injection/"
   private final val VILLAGE_HOUSE: ResourceLocation = MiscUtilsKt.cobblemonResource("injection/chests/village_house")
   private final val injections: HashSet<ResourceLocation>
   private final val villageHouseLootTables: HashSet<ResourceLocation> =
      SetsKt.hashSetOf(
         new ResourceLocation[]{
            BuiltInLootTables.f_78754_, BuiltInLootTables.f_78755_, BuiltInLootTables.f_78758_, BuiltInLootTables.f_78757_, BuiltInLootTables.f_78756_
         }
      )

   public fun attemptInjection(id: ResourceLocation, provider: (Builder) -> Unit): Boolean {
      if (!injections.contains(id)) {
         return false;
      } else {
         val resulting: ResourceLocation = this.convertToPotentialInjected(id);
         Cobblemon.INSTANCE.getLOGGER().debug("{}: Injected {} to {}", (this.getClass()::class).getSimpleName(), resulting, id);
         provider.invoke(this.injectLootPool(resulting));
         return true;
      }
   }

   private fun convertToPotentialInjected(source: ResourceLocation): ResourceLocation {
      return if (villageHouseLootTables.contains(source)) VILLAGE_HOUSE else MiscUtilsKt.cobblemonResource("injection/${source.m_135815_()}");
   }

   private fun injectLootPool(resulting: ResourceLocation): Builder {
      val var10000: net.minecraft.world.level.storage.loot.LootPool.Builder = LootPool.m_79043_()
         .m_79076_(LootTableReference.m_79776_(resulting).m_79707_(1) as net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer.Builder)
         .m_165135_(UniformGenerator.m_165780_(0.0F, 1.0F) as NumberProvider);
      return var10000;
   }

   @JvmStatic
   fun {
      val var4: HashSet = SetsKt.hashSetOf(
         new ResourceLocation[]{
            BuiltInLootTables.f_78759_,
            BuiltInLootTables.f_230876_,
            BuiltInLootTables.f_78699_,
            BuiltInLootTables.f_78700_,
            BuiltInLootTables.f_78698_,
            BuiltInLootTables.f_78697_,
            BuiltInLootTables.f_78741_,
            BuiltInLootTables.f_78688_,
            BuiltInLootTables.f_78686_,
            BuiltInLootTables.f_78760_,
            BuiltInLootTables.f_78696_,
            BuiltInLootTables.f_78694_,
            BuiltInLootTables.f_78742_,
            BuiltInLootTables.f_78740_,
            BuiltInLootTables.f_78763_,
            BuiltInLootTables.f_78689_
         }
      );
      var4.addAll(villageHouseLootTables);
      injections = var4;
   }
}
