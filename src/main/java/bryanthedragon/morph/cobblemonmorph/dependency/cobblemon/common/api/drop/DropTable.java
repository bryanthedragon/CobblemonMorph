package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.drop

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.Cancelable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.CobblemonEvents
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.drops.LootDroppedEvent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.CancelableObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.EventObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import java.util.ArrayList;
import java.util.Arrays
import kotlin.jvm.functions.Function1
import kotlin.jvm.internal.SourceDebugExtension
import kotlin.random.Random
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.ItemLike
import net.minecraft.world.phys.Vec3

@SourceDebugExtension(["SMAP\nDropTable.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DropTable.kt\ncom/cobblemon/mod/common/api/drop/DropTable\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/CancelableObservable\n+ 4 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/EventObservable\n+ 5 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 6 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/CancelableObservable$postThen$1\n*L\n1#1,99:1\n766#2:100\n857#2,2:101\n288#2,2:103\n1855#2,2:114\n39#3,2:105\n41#3,2:110\n44#3:113\n46#3:116\n47#3:119\n17#4,2:107\n19#4:118\n13579#5:109\n13580#5:117\n39#6:112\n*S KotlinDebug\n*F\n+ 1 DropTable.kt\ncom/cobblemon/mod/common/api/drop/DropTable\n*L\n49#1:100\n49#1:101,2\n59#1:103,2\n96#1:114,2\n94#1:105,2\n94#1:110,2\n94#1:113\n94#1:116\n94#1:119\n94#1:107,2\n94#1:118\n94#1:109\n94#1:117\n94#1:112\n*E\n"])
public class DropTable {
   public final val amount: IntRange = new IntRange(1, 1)
   public final val entries: MutableList<DropEntry> = (new ArrayList()) as java.util.List

   public fun getDrops(amount: IntRange = this.amount): List<DropEntry> {
      val chosenAmount: Int = RangesKt.random(amount, Random.Default as Random);
      val drops: java.lang.Iterable = this.entries;
      val remaining: java.util.Collection = new ArrayList();

      for (Object element$iv$iv : $this$filter$iv) {
         if ((`element$iv` as DropEntry).getQuantity() <= chosenAmount) {
            remaining.add(`element$iv`);
         }
      }

      val possibleDrops: java.util.List = CollectionsKt.toMutableList(remaining as java.util.List);
      if (possibleDrops.isEmpty()) {
         return CollectionsKt.emptyList();
      } else {
         val var13: java.util.List = new ArrayList();
         var var14: Int = 0;

         do {
            var var10000: Any;
            label46: {
               for (Object element$iv : var15) {
                  if (Random.Default.nextFloat() * 100.0F < (var19 as DropEntry).getPercentage()) {
                     var10000 = var19;
                     break label46;
                  }
               }

               var10000 = null;
            }

            val drop: DropEntry = var10000 as DropEntry;
            if (var10000 as DropEntry == null) {
               var14++;
            } else {
               var13.add(drop);
               var14 += drop.getQuantity();
               val var16: Int = chosenAmount - var14;
               possibleDrops.removeIf(DropTable::getDrops$lambda$2);
            }
         } while (dropCount < chosenAmount && !possibleDrops.isEmpty());

         return var13;
      }
   }

   public fun drop(entity: LivingEntity?, world: ServerLevel, pos: Vec3, player: ServerPlayer?, amount: IntRange = this.amount) {
      val drops: java.util.List = CollectionsKt.toMutableList(this.getDrops(amount));
      val heldItem: ItemStack = (entity as PokemonEntity).getPokemon().heldItemNoCopy$common();
      if (!heldItem.m_41619_()) {
         (entity as PokemonEntity).m_19998_(heldItem.m_41720_() as ItemLike);
      }

      val `$this$iv`: CancelableObservable = CobblemonEvents.LOOT_DROPPED;
      val `event$iv`: Cancelable = new LootDroppedEvent(this, player, entity, drops);
      val `this_$iv$iv`: EventObservable = `$this$iv`;
      val `events$iv$iv`: Array<Cancelable> = new Cancelable[]{`event$iv`};
      `this_$iv$iv`.emit(Arrays.copyOf(`events$iv$iv`, `events$iv$iv`.length));

      for (Object element$iv$iv$iv : events$iv$iv) {
         if (!((Cancelable)`element$iv$iv$iv`).isCanceled()) {
            val `$this$forEach$iv`: java.lang.Iterable;
            for (Object element$iv : $this$forEach$iv) {
               (`element$iv` as DropEntry).drop(entity, world, pos, player);
            }
         }
      }
   }

   @JvmStatic
   fun `getDrops$lambda$2`(`$tmp0`: Function1, p0: Any): Boolean {
      return `$tmp0`.invoke(p0) as java.lang.Boolean;
   }
}
