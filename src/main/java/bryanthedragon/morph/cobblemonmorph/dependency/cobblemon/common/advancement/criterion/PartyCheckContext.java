package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PlayerPartyStore

public open class PartyCheckContext(party: PlayerPartyStore) {
   public final val party: PlayerPartyStore

   init {
      this.party = party;
   }
}
