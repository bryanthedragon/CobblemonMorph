package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.Abilities
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.AbilityTemplate
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.PokemonUpdatePacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import kotlin.jvm.functions.Function0
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation

public class AbilityUpdatePacket(pokemon: () -> Pokemon, ability: AbilityTemplate) : SingleUpdatePacket(pokemon, ability) {
   public open val id: ResourceLocation

   init {
      this.id = ID;
   }

   public override fun encodeValue(buffer: FriendlyByteBuf) {
      buffer.m_130070_(this.getValue().getName());
   }

   public open fun set(pokemon: Pokemon, value: AbilityTemplate) {
      pokemon.setAbility$common(AbilityTemplate.create$default(value, false, 1, null));
   }

   public companion object {
      public final val ID: ResourceLocation

      public fun decode(buffer: FriendlyByteBuf): AbilityUpdatePacket {
         val pokemon: Function0 = PokemonUpdatePacket.Companion.decodePokemon(buffer);
         val var10000: Abilities = Abilities.INSTANCE;
         val var10001: java.lang.String = buffer.m_130277_();
         val var4: AbilityTemplate = var10000.get(var10001);
         return new AbilityUpdatePacket(pokemon, var4);
      }
   }
}
