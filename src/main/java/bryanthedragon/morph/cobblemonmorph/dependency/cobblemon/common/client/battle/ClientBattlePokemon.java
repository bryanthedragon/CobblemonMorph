package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonSpecies
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stat
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.PokemonFloatingState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Gender
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Species
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.PersistentStatus
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.ResourceLocationExtensionsKt
import java.util.UUID
import net.minecraft.network.chat.MutableComponent

public class ClientBattlePokemon(uuid: UUID,
   displayName: MutableComponent,
   properties: PokemonProperties,
   aspects: Set<String>,
   hpValue: Float,
   maxHp: Float,
   isHpFlat: Boolean,
   status: PersistentStatus?,
   statChanges: MutableMap<Stat, Int>
) {
   public final lateinit var actor: ClientBattleActor
   public final var aspects: Set<String>
   public final var displayName: MutableComponent

   public final val gender: Gender
      public final get() {
         var var10000: Gender = this.properties.getGender();
         if (var10000 == null) {
            var10000 = Gender.GENDERLESS;
         }

         return var10000;
      }


   public final var hpValue: Float
   public final var isHpFlat: Boolean

   public final val level: Int
      public final get() {
         val var10000: Int = this.properties.getLevel();
         return var10000 ?: 0;
      }


   public final var maxHp: Float
   public final var properties: PokemonProperties

   public final val species: Species
      public final get() {
         val var10000: PokemonSpecies = PokemonSpecies.INSTANCE;
         val var10001: java.lang.String = this.properties.getSpecies();
         val var1: Species = var10000.getByIdentifier(ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default(var10001, null, 1, null));
         return var1;
      }


   public final var statChanges: MutableMap<Stat, Int>
   public final var state: PokemonFloatingState
   public final var status: PersistentStatus?
   public final val uuid: UUID

   init {
      this.uuid = uuid;
      this.displayName = displayName;
      this.properties = properties;
      this.aspects = aspects;
      this.hpValue = hpValue;
      this.maxHp = maxHp;
      this.isHpFlat = isHpFlat;
      this.status = status;
      this.statChanges = statChanges;
      this.state = new PokemonFloatingState();
   }
}
