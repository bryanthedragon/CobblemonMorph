/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.trade

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.interaction.RequestManager
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.CobblemonEvents
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.TradeEvent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.interaction.ServerPlayerActionRequest
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.aqua
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.trade.TradeOfferExpiredPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.trade.TradeOfferNotificationPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.trade.TradeStartedPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.trade.TradeStartedPacket.TradeablePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.variants.TradeEvolution
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.canInteractWith
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.party
import java.util.UUID
import net.minecraft.server.level.ServerPlayer

/**
 * Responsible for managing [ActiveTrade]s and the respective [TradeRequest]s that create them.
 *
 * @author Hiroku
 * @since March 4th, 2023
 */final class TradeManager : RequestManager<TradeManager.TradeRequest>() {

    init {
        register(this)
    }

    /**
     * Represents an interaction request between players to trade.
     *
     * @param sender The player sending this request.
     * @param receiver The player receiving this request.
     * @param expiryTime How long (in seconds) this request is active.
     */
    record TradeRequest(
        override val sender: ServerPlayer,
        override val receiver: ServerPlayer,
        override val expiryTime: Int = 20
    ) : ServerPlayerActionRequest {
        override val key: String = "trade"
        override val requestID: UUID = UUID.randomUUID()
    }

    private val activeTrades = mutableListOf<ActiveTrade>()

    fun removeActiveTrade(trade: ActiveTrade) = activeTrades.remove(trade)

    fun getActiveTrade(playerId: UUID) = activeTrades.find { it.player1.uuid == playerId || it.player2.uuid == playerId }

    override fun expirationPacket(request: TradeRequest): NetworkPacket<*> = TradeOfferExpiredPacket(request)

    override fun notificationPacket(request: TradeRequest): NetworkPacket<*> = TradeOfferNotificationPacket(request)

    override fun onAccept(request: TradeRequest) {
        val trade = ActiveTrade(PlayerTradeParticipant(request.receiver), PlayerTradeParticipant(request.sender))
        activeTrades.add(trade)
        request.sendToSender(TradeStartedPacket(request.sender.uuid, request.receiver.name.plainCopy(), trade.player1.party.mapNullPreserving(::TradeablePokemon)))
        request.sendToReceiver(TradeStartedPacket(request.receiver.uuid, request.sender.name.plainCopy(), trade.player2.party.mapNullPreserving(::TradeablePokemon)))
    }

    override fun canAccept(request: TradeRequest): Boolean {
        if (request.sender.party().none()) {
            request.notifySender(true, "error.insufficient_pokemon.self")
            request.notifyReceiver(true, "error.insufficient_pokemon.other", request.sender.name.copy().aqua())
        }
        else if (request.receiver.party().none()) {
            request.notifySender(true, "error.insufficient_pokemon.other", request.sender.name.copy().aqua())
            request.notifyReceiver(true, "error.insufficient_pokemon.self")
        }
        // isBusy already checks if sender is in an active trade
        else return true
        return false
    }

    override fun isValidInteraction(player: ServerPlayer, target: ServerPlayer): Boolean = player.canInteractWith(target, Cobblemon.config.tradeMaxDistance)

    override fun onLogoff(player: ServerPlayer) {
        super.onLogoff(player)
        val trade = this.getActiveTrade(player.uuid)
        if (trade != null) {
            val tradeParticipant = trade.getTradeParticipant(player.uuid)
            val oppositeParticipant = trade.getOppositePlayer(tradeParticipant)
            oppositeParticipant.cancelTrade(trade)
            this.removeActiveTrade(trade)
        }
    }

    fun performTrade(player1: TradeParticipant, pokemon1: Pokemon, player2: TradeParticipant, pokemon2: Pokemon) {
        CobblemonEvents.TRADE_EVENT_PRE.postThen(TradeEvent.Pre(player1, pokemon2, player2, pokemon1)) {
            val party1 = player1.party
            val party2 = player2.party

            party1.remove(pokemon1)
            party2.remove(pokemon2)

            pokemon1.setFriendship(pokemon1.form.baseFriendship)
            pokemon2.setFriendship(pokemon2.form.baseFriendship)

            party2.add(pokemon1)
            party1.add(pokemon2)

            pokemon1.lockedEvolutions.filterIsInstance<TradeEvolution>().firstOrNull {
                it.attemptEvolution(pokemon1, pokemon2)
            }

            pokemon2.lockedEvolutions.filterIsInstance<TradeEvolution>().firstOrNull {
                it.attemptEvolution(pokemon2, pokemon1)
            }

            CobblemonEvents.TRADE_EVENT_POST.post(TradeEvent.Post(player1, pokemon2, player2, pokemon1))
        }
    }
}