package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stat.Type
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.EVs
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.FormData
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.IVs
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Species
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation

public interface StatProvider {
   public val typeAdapter: StatTypeAdapter

   public abstract fun all(): Collection<Stat> {
   }

   public abstract fun ofType(type: Type): Collection<Stat> {
   }

   public abstract fun provide(species: Species) {
   }

   public abstract fun provide(form: FormData) {
   }

   public abstract fun createEmptyEVs(): EVs {
   }

   public abstract fun createEmptyIVs(minPerfectIVs: Int): IVs {
   }

   public abstract fun toShowdown(species: Species, form: FormData?): String {
   }

   public abstract fun getStatForPokemon(pokemon: Pokemon, stat: Stat): Int {
   }

   public abstract fun fromIdentifier(identifier: ResourceLocation): Stat? {
   }

   public abstract fun fromIdentifierOrThrow(identifier: ResourceLocation): Stat {
   }

   public abstract fun decode(buffer: FriendlyByteBuf): Stat {
   }

   public abstract fun encode(buffer: FriendlyByteBuf, stat: Stat) {
   }
}
