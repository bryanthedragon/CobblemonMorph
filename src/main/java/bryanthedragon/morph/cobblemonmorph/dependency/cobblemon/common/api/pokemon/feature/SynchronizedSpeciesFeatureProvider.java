package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.Decodable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.Encodable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.featurerenderers.SummarySpeciesFeatureRenderer
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import net.minecraft.network.FriendlyByteBuf

public interface SynchronizedSpeciesFeatureProvider<T extends SynchronizedSpeciesFeature> : SpeciesFeatureProvider<T>, Encodable, Decodable {
   public var visible: Boolean

   public abstract operator fun invoke(buffer: FriendlyByteBuf, name: String): Any? {
   }

   public abstract fun get(pokemon: Pokemon): Any? {
   }

   public abstract fun getRenderer(pokemon: Pokemon): SummarySpeciesFeatureRenderer<Any>? {
   }
}
