package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.entity.Despawner
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.player.Player

@SourceDebugExtension(["SMAP\nCobblemonAgingDespawner.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CobblemonAgingDespawner.kt\ncom/cobblemon/mod/common/entity/pokemon/CobblemonAgingDespawner\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,61:1\n1#2:62\n*E\n"])
public class CobblemonAgingDespawner<T extends Entity>(nearDistance: Float = 32.0F,
      farDistance: Float = 96.0F,
      minAgeTicks: Int = 600,
      maxAgeTicks: Int = 3600,
      getAgeTicks: (Any) -> Int
   ) :
   Despawner<T> {
   public final val farDistance: Float
   public final val getAgeTicks: (Any) -> Int
   public final val maxAgeTicks: Int
   public final val minAgeTicks: Int
   public final val nearDistance: Float
   public final val nearToFar: Float
   public final val youngToOld: Int

   init {
      this.nearDistance = nearDistance;
      this.farDistance = farDistance;
      this.minAgeTicks = minAgeTicks;
      this.maxAgeTicks = maxAgeTicks;
      this.getAgeTicks = getAgeTicks;
      this.nearToFar = this.farDistance - this.nearDistance;
      this.youngToOld = this.maxAgeTicks - this.minAgeTicks;
   }

   public override fun beginTracking(entity: Any) {
   }

   public override fun shouldDespawn(entity: Any): Boolean {
      val age: Int = (this.getAgeTicks.invoke(entity) as java.lang.Number).intValue();
      if (age >= this.minAgeTicks && (entity !is PokemonEntity || !(entity as PokemonEntity).isBusy()) && !entity.m_20159_()) {
         val var10000: java.util.List = entity.m_9236_().m_6907_();
         val var6: java.util.Iterator = var10000.iterator();
         val var13: java.lang.Float;
         if (!var6.hasNext()) {
            var13 = null;
         } else {
            var var10: Float = (var6.next() as Player).m_20270_(entity);

            while (var6.hasNext()) {
               var10 = Math.min(var10, (var6.next() as Player).m_20270_(entity));
            }

            var13 = var10;
         }

         val closestDistance: Float = var13 ?: java.lang.Float.MAX_VALUE;
         return !(closestDistance < this.nearDistance)
            && (
               age > this.maxAgeTicks
                  || closestDistance > this.farDistance
                  || age > (1 - (closestDistance - this.nearDistance) / this.nearToFar) * this.youngToOld
            );
      } else {
         return false;
      }
   }
}
