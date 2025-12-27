package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ClientBattleChallenge
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.trade.ClientTradeOffer
import java.util.ArrayList;

public class ClientPlayerActionRequests {
   public final val battleChallenges: MutableList<ClientBattleChallenge> = (new ArrayList()) as java.util.List
   public final val tradeOffers: MutableList<ClientTradeOffer> = (new ArrayList()) as java.util.List
}
