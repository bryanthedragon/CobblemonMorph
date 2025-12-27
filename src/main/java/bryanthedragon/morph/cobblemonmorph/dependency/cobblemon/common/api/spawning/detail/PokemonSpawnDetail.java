package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail

import com.bedrockk.molang.runtime.struct.VariableStruct
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.drop.DropTable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonSpecies
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SpawningContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.ElementalType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Species
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.ResourceLocationExtensionsKt
import java.util.Locale
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.network.chat.MutableComponent

@SourceDebugExtension(["SMAP\nPokemonSpawnDetail.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PokemonSpawnDetail.kt\ncom/cobblemon/mod/common/api/spawning/detail/PokemonSpawnDetail\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,115:1\n1#2:116\n*E\n"])
public class PokemonSpawnDetail : SpawnDetail {
   public final val drops: DropTable?
   public final val heldItems: MutableList<PossibleHeldItem>?
   public final var levelRange: IntRange?
   public final var pokemon: PokemonProperties

   private final val pokemonExample: Pokemon
      private final get() {
         return this.pokemonExample$delegate.getValue() as Pokemon;
      }


   public open val type: String

   public override fun getName(): MutableComponent {
      var speciesString: java.lang.String = this.getDisplayName();
      if (speciesString != null) {
         val var9: MutableComponent = MiscUtilsKt.asTranslated(speciesString);
         return var9;
      } else {
         speciesString = this.pokemon.getSpecies();
         if (speciesString != null) {
            val var6: java.lang.String = speciesString.toLowerCase(Locale.ROOT);
            if (var6 == "random") {
               val var8: MutableComponent = LocalizationUtilsKt.lang("species.random");
               return var8;
            } else {
               val species: Species = PokemonSpecies.INSTANCE
                  .getByIdentifier(ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default(speciesString, null, 1, null));
               val var7: MutableComponent;
               if (species == null) {
                  var7 = LocalizationUtilsKt.lang("species.unknown");
               } else {
                  var7 = species.getTranslatedName();
               }

               return var7;
            }
         } else {
            val var10000: MutableComponent = LocalizationUtilsKt.lang("a_pokemon");
            return var10000;
         }
      }
   }

   public override fun autoLabel() {
      val pokemonStruct: VariableStruct = this.pokemon.asStruct();
      if (this.pokemon.getSpecies() != null) {
         val var10000: PokemonSpecies = PokemonSpecies.INSTANCE;
         var var10001: java.lang.String = this.pokemon.getSpecies();
         val species: Species = var10000.getByIdentifier(ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default(var10001, null, 1, null));
         if (species != null) {
            label20: {
               var7 = this.getLabels();
               val var8: ElementalType = species.getSecondaryType();
               if (var8 != null) {
                  val var5: Array<java.lang.String> = new java.lang.String[2];
                  var var10002: java.lang.String = species.getPrimaryType().getName().toLowerCase(Locale.ROOT);
                  var5[0] = var10002;
                  var10002 = var8.getName().toLowerCase(Locale.ROOT);
                  var5[1] = var10002;
                  var9 = CollectionsKt.listOf(var5);
                  if (var9 != null) {
                     break label20;
                  }
               }

               var10001 = species.getPrimaryType().getName().toLowerCase(Locale.ROOT);
               var9 = CollectionsKt.listOf(var10001);
            }

            var7.addAll(var9);
            if (this.getHeight() == -1) {
               this.setHeight(
                  (int)((float)Math.ceil((double)(this.getPokemonExample().getForm().getHitbox().f_20378_ * this.getPokemonExample().getForm().getBaseScale())))
               );
            }

            if (this.getWidth() == -1) {
               this.setWidth(
                  (int)((float)Math.ceil((double)(this.getPokemonExample().getForm().getHitbox().f_20377_ * this.getPokemonExample().getForm().getBaseScale())))
               );
            }
         }
      }

      this.getStruct().setDirectly("pokemon", pokemonStruct);
      super.autoLabel();
   }

   public fun getDerivedLevelRange(): IntRange {
      val var10000: IntRange;
      if (this.levelRange == null && this.pokemon.getLevel() == null) {
         var10000 = new IntRange(1, Cobblemon.INSTANCE.getConfig().getMaxPokemonLevel());
      } else if (this.levelRange == null) {
         val var10002: Int = this.pokemon.getLevel();
         val var3: Int = var10002;
         val var10003: Int = this.pokemon.getLevel();
         var10000 = new IntRange(var3, var10003);
      } else {
         var10000 = this.levelRange;
      }

      return var10000;
   }

   public override fun isValid(): Boolean {
      val isValidSpecies: Boolean = this.pokemon.getSpecies() != null;
      if (!isValidSpecies) {
         Cobblemon.INSTANCE.getLOGGER().error("Invalid species for spawn detail: ${this.getId()}");
      }

      return super.isValid() && isValidSpecies;
   }

   public open fun doSpawn(ctx: SpawningContext): SingleEntitySpawnAction<PokemonEntity> {
      return new PokemonSpawnAction(ctx, this, null, 4, null);
   }

   public companion object {
      public final val TYPE: String
   }
}
