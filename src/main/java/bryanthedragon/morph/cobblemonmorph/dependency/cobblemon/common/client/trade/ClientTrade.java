package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.trade

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SettableObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import java.util.UUID

public class ClientTrade {
   public final var acceptedOppositeOffer: Boolean
   public final var cancelEmitter: SimpleObservable<Unit> = new SimpleObservable()
   public final var completedEmitter: SimpleObservable<Pair<UUID, UUID>> = new SimpleObservable()
   public final var myOffer: SettableObservable<Pokemon?> = new SettableObservable(null)
   public final var oppositeAcceptedMyOffer: SettableObservable<Boolean> = new SettableObservable(false)
   public final var oppositeOffer: SettableObservable<Pokemon?> = new SettableObservable(null)
}
