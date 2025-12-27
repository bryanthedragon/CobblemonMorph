package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.WorldSlice;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.calculators.AreaSpawningContextCalculator;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.calculators.AreaSpawningInput;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.influence.SpawningInfluence;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.spawner.Spawner;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.BlockPosExtensionsKt;

import java.util.ArrayList;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Position;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.world.phys.Vec3;

public interface AreaContextResolver {
   public open fun resolve(spawner: Spawner, contextCalculators: List<AreaSpawningContextCalculator<*>>, slice: WorldSlice): List<AreaSpawningContext> {
   }

   // $VF: Class flags could not be determined
   internal class DefaultImpls {
      @JvmStatic
      fun resolve(`$this`: AreaContextResolver, spawner: Spawner, contextCalculators: MutableList<AreaSpawningContextCalculator<?>>, slice: WorldSlice): MutableList<AreaSpawningContext> {
         var pos: MutableBlockPos = new MutableBlockPos(1, 2, 3);
         val input: AreaSpawningInput = new AreaSpawningInput(spawner, pos as BlockPos, slice);
         val contexts: java.util.List = new ArrayList();
         var x: Int = slice.getBaseX();
         var y: Int = slice.getBaseY();

         for (int z = slice.getBaseZ(); x < slice.getBaseX() + slice.getLength(); z = slice.getBaseZ()) {
            while (y < slice.getBaseY() + slice.getHeight()) {
               for (; z < slice.getBaseZ() + slice.getWidth(); z++) {
                  pos.m_122178_(x, y, z);
                  val vec: Vec3 = BlockPosExtensionsKt.toVec3d(pos as BlockPos);
                  val fittedContextCalculator: java.lang.Iterable = slice.getNearbyEntityPositions();
                  var var10000: Boolean;
                  if (fittedContextCalculator is java.util.Collection && (fittedContextCalculator as java.util.Collection).isEmpty()) {
                     var10000 = true;
                  } else {
                     val `$i$f$firstOrNull`: java.util.Iterator = fittedContextCalculator.iterator();

                     while (true) {
                        if (!`$i$f$firstOrNull`.hasNext()) {
                           var10000 = true;
                           break;
                        }

                        val `element$iv`: Vec3 = `$i$f$firstOrNull`.next() as Vec3;
                        if (`element$iv`.m_82509_(vec as Position, Cobblemon.INSTANCE.getConfig().getMinimumDistanceBetweenEntities())
                           && !(`element$iv` == slice.getCause().getEntity())) {
                           var10000 = false;
                           break;
                        }
                     }
                  }

                  if (var10000) {
                     val var28: java.util.Iterator = contextCalculators.iterator();

                     while (true) {
                        if (!var28.hasNext()) {
                           var33 = null;
                           break;
                        }

                        var var29: Any;
                        label96: {
                           var29 = var28.next();
                           val var30: AreaSpawningContextCalculator = var29 as AreaSpawningContextCalculator;
                           if ((var29 as AreaSpawningContextCalculator).fits(input)) {
                              val `$this$none$ivx`: java.lang.Iterable = input.getSpawner().getInfluences();
                              if (`$this$none$ivx` is java.util.Collection && (`$this$none$ivx` as java.util.Collection).isEmpty()) {
                                 var10000 = true;
                              } else {
                                 label124: {
                                    for (Object element$ivx : $this$none$ivx) {
                                       if (!(`element$ivx` as SpawningInfluence).isAllowedPosition(input.getWorld(), input.getPosition(), var30)) {
                                          var10000 = false;
                                          break label124;
                                       }
                                    }

                                    var10000 = true;
                                 }
                              }

                              if (var10000) {
                                 var10000 = true;
                                 break label96;
                              }
                           }

                           var10000 = false;
                        }

                        if (var10000) {
                           var33 = var29;
                           break;
                        }
                     }

                     val var24: AreaSpawningContextCalculator = var33 as AreaSpawningContextCalculator;
                     if (var33 as AreaSpawningContextCalculator != null) {
                        val var26: AreaSpawningContext = var24.calculate(input) as AreaSpawningContext;
                        if (var26 != null) {
                           contexts.add(var26);
                           pos = new MutableBlockPos(1, 2, 3);
                           input.setPosition(pos as BlockPos);
                        }
                     }
                  }
               }

               y++;
               z = slice.getBaseZ();
            }

            x++;
            y = slice.getBaseY();
         }

         return contexts;
      }
   }
}
