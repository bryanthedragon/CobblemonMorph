package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.entity.pokemon

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonSpecies
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.FormData
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Species
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.ResourceLocationExtensionsKt
import kotlin.jvm.internal.SourceDebugExtension

public interface MocKEffect : PhysicalEffect {
   public open val exposedForm: FormData?
      public open get() {
      }


   public open val exposedSpecies: Species?
      public open get() {
      }


   public val mock: PokemonProperties

   // $VF: Class flags could not be determined
   @SourceDebugExtension(["SMAP\nEntityEffect.kt\nKotlin\n*S Kotlin\n*F\n+ 1 EntityEffect.kt\ncom/cobblemon/mod/common/api/entity/pokemon/MocKEffect$DefaultImpls\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,98:1\n1#2:99\n288#3,2:100\n*S KotlinDebug\n*F\n+ 1 EntityEffect.kt\ncom/cobblemon/mod/common/api/entity/pokemon/MocKEffect$DefaultImpls\n*L\n95#1:100,2\n*E\n"])
   internal class DefaultImpls {
      @JvmStatic
      fun getExposedSpecies(`$this`: MocKEffect): Species? {
         val var10000: java.lang.String = `$this`.getMock().getSpecies();
         return if (var10000 != null)
            PokemonSpecies.INSTANCE.getByIdentifier(ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default(var10000, null, 1, null))
            else
            null;
      }

      @JvmStatic
      fun getExposedForm(`$this`: MocKEffect): FormData? {
         var var10000: java.lang.String = `$this`.getMock().getForm();
         if (var10000 != null) {
            label33: {
               val formID: java.lang.String = var10000;
               val var9: Species = `$this`.getExposedSpecies();
               if (var9 != null) {
                  val var10: java.util.List = var9.getForms();
                  if (var10 != null) {
                     val var5: java.util.Iterator = var10.iterator();

                     while (true) {
                        if (var5.hasNext()) {
                           val `element$iv`: Any = var5.next();
                           if (!StringsKt.equals((`element$iv` as FormData).formOnlyShowdownId(), formID, true)) {
                              continue;
                           }

                           var10000 = (java.lang.String)`element$iv`;
                           break;
                        }

                        var10000 = null;
                        break;
                     }

                     var11 = var10000 as FormData;
                     break label33;
                  }
               }

               var11 = null;
            }

            if (var11 != null) {
               return var11;
            }
         }

         val var13: Species = `$this`.getExposedSpecies();
         return if (var13 != null) var13.getStandardForm() else null;
      }
   }
}
