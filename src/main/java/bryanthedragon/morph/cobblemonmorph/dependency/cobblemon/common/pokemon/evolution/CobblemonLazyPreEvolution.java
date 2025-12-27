package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonSpecies
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.PreEvolution
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.FormData
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Species
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.ResourceLocationExtensionsKt
import kotlin.jvm.functions.Function0
import net.minecraft.resources.ResourceLocation
import org.jetbrains.annotations.NotNull

internal class CobblemonLazyPreEvolution(rawData: String) : PreEvolution {
   public open val form: FormData
      public open get() {
         return this.getLazyForm();
      }


   private final val lazyForm: FormData
      private final get() {
         return this.lazyForm$delegate.getValue() as FormData;
      }


   private final val lazySpecies: Species
      private final get() {
         return this.lazySpecies$delegate.getValue() as Species;
      }


   private final val properties: PokemonProperties
      private final get() {
         return PokemonProperties.Companion.parse$default(PokemonProperties.Companion, this.rawData, null, null, 6, null);
      }


   private final val rawData: String

   public open val species: Species
      public open get() {
         return this.getLazySpecies();
      }


   init {
      this.rawData = rawData;
      this.lazySpecies$delegate = LazyKt.lazy((new Function0<Species>(this) {
         {
            super(0);
            this.this$0 = `$receiver`;
         }

         @NotNull
         public final Species invoke() {
            val var10000: java.lang.String = CobblemonLazyPreEvolution.access$getProperties(this.this$0).getSpecies();
            if (var10000 != null) {
               val var3: ResourceLocation = ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default(var10000, null, 1, null);
               if (var3 != null) {
                  val var4: Species = PokemonSpecies.INSTANCE.getByIdentifier(var3);
                  if (var4 != null) {
                     return var4;
                  }
               }
            }

            throw new IllegalArgumentException("A PreEvolution needs a valid species");
         }
      }) as Function0);
      this.lazyForm$delegate = LazyKt.lazy((new Function0<FormData>(this) {
         {
            super(0);
            this.this$0 = `$receiver`;
         }

         @NotNull
         public final FormData invoke() {
            var var10000: java.lang.String = CobblemonLazyPreEvolution.access$getProperties(this.this$0).getForm();
            if (var10000 != null) {
               val formId: java.lang.String = var10000;
               val var7: java.util.Iterator = this.this$0.getSpecies().getForms().iterator();

               while (true) {
                  if (!var7.hasNext()) {
                     var10000 = null;
                     break;
                  }

                  val `element$iv`: Any = var7.next();
                  if (StringsKt.equals((`element$iv` as FormData).formOnlyShowdownId(), formId, true)) {
                     var10000 = (java.lang.String)`element$iv`;
                     break;
                  }
               }

               val var12: FormData = var10000 as FormData;
               if (var10000 as FormData != null) {
                  return var12;
               }
            }

            return this.this$0.getSpecies().getStandardForm();
         }
      }) as Function0);
   }
}
