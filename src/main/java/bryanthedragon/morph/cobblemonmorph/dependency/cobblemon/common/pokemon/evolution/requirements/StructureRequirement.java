package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.conditional.RegistryLikeCondition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements.template.EntityQueryRequirement
import java.util.Map.Entry
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.core.Registry
import net.minecraft.core.registries.Registries
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.level.levelgen.structure.Structure

@SourceDebugExtension(["SMAP\nStructureRequirement.kt\nKotlin\n*S Kotlin\n*F\n+ 1 StructureRequirement.kt\ncom/cobblemon/mod/common/pokemon/evolution/requirements/StructureRequirement\n+ 2 _Maps.kt\nkotlin/collections/MapsKt___MapsKt\n*L\n1#1,38:1\n187#2,3:39\n*S KotlinDebug\n*F\n+ 1 StructureRequirement.kt\ncom/cobblemon/mod/common/pokemon/evolution/requirements/StructureRequirement\n*L\n31#1:39,3\n*E\n"])
public class StructureRequirement : EntityQueryRequirement {
   public final val structureAnticondition: RegistryLikeCondition<Structure>?
   public final val structureCondition: RegistryLikeCondition<Structure>?

   public override fun check(pokemon: Pokemon, queriedEntity: LivingEntity): Boolean {
      val structures: java.util.Map = queriedEntity.m_9236_().m_46865_(queriedEntity.m_20183_()).m_62769_();
      val registry: Registry = queriedEntity.m_9236_().m_9598_().m_175515_(Registries.f_256944_);
      if (this.structureCondition != null) {
         var var15: Boolean;
         if (structures.isEmpty()) {
            var15 = false;
         } else {
            val var7: java.util.Iterator = structures.entrySet().iterator();

            while (true) {
               if (!var7.hasNext()) {
                  var15 = false;
                  break;
               }

               val `element$iv`: Entry = var7.next() as Entry;
               val var10000: RegistryLikeCondition = this.structureCondition;
               val var10001: Any = `element$iv`.getKey();
               if (var10000.fits(var10001, registry)) {
                  var15 = true;
                  break;
               }
            }
         }

         if (!var15) {
            return false;
         }
      }

      if (this.structureAnticondition == null) {
         return true;
      } else {
         var var16: Boolean;
         if (structures.isEmpty()) {
            var16 = false;
         } else {
            val var12: java.util.Iterator = structures.entrySet().iterator();

            while (true) {
               if (!var12.hasNext()) {
                  var16 = false;
                  break;
               }

               val var13: Entry = var12.next() as Entry;
               val var17: RegistryLikeCondition = this.structureAnticondition;
               val var19: Any = var13.getKey();
               if (var17.fits(var19, registry)) {
                  var16 = true;
                  break;
               }
            }
         }

         return !var16;
      }
   }

   override fun check(pokemon: Pokemon): Boolean {
      return EntityQueryRequirement.DefaultImpls.check(this, pokemon);
   }

   public companion object {
      public const val ADAPTER_VARIANT: String
   }
}
