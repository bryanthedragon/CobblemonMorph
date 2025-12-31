/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.trade

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork.sendPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PartyStore
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.text
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.trade.*
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.party
import java.util.UUID
import net.minecraft.server.level.ServerPlayer
import net.minecraft.network.chat.Component

/**
 * Interface representing a participant in an [ActiveTrade].
 *
 * @author Hiroku
 * @since May 12th, 2023
 */
public interface TradeParticipant {
    val UUID uuid
    val name: Component
    val party: PartyStore

    /**
     * Notifies the participant that the given [TradeParticipant] has changed their offered [Pokemon] to [pokemon].
     *
     * If it's null, it means they're no longer offering a Pokémon (so they're redeciding).
     */
    fun updateOffer(trade: ActiveTrade, tradeParticipant: TradeParticipant, Pokemon pokemon?)

    /**
     * Notifies the participant that the acceptance state for trading with the opponent [Pokemon] with UUID [pokemonId]
     * has changed to [acceptance].
     *
     * The importance of including the UUID is in how acceptance is always about a specific Pokémon, and ping issues
     * could otherwise state that acceptance has been changed for the previous offer when, in fact, the offer was updated
     * moments before.
     */
    fun changeTradeAcceptance(trade: ActiveTrade, UUID pokemonId, acceptance: Boolean)

    /**
     * Notifies the participant that the trade process has begun.
     *
     * @param isTradeInitiator Indicates if the user is the one who accepted last.
     * Used by the UI to complete the trade after the process/animation is done.
     * Only 1 user should handle sending the completion to the server to prevent conflicts.
     */
    fun startTradeProcess(trade: ActiveTrade, isTradeInitiator: Boolean)

    /** Notifies the participant that the trade has been cancelled. */
    fun cancelTrade(trade: ActiveTrade)

    /** Notifies the participant that the trade has been completed. The actual Pokémon have already been exchanged. */
    fun completeTrade(trade: ActiveTrade, pokemonId1: UUID, pokemonId2: UUID)
}

/**
 * A trade participant that is a player.
 *
 * @author Hiroku
 * @since May 12th, 2023
 */
public class PlayerTradeParticipant(val ServerPlayer player): TradeParticipant {
    override val name = player.name
    override val uuid = player.uuid
    override val party = player.party()

    override fun updateOffer(trade: ActiveTrade, tradeParticipant: TradeParticipant, Pokemon pokemon?) {
        player.sendPacket(TradeUpdatedPacket(tradeParticipant.uuid, pokemon))
    }

    override fun changeTradeAcceptance(trade: ActiveTrade, UUID pokemonId, acceptance: Boolean) {
        player.sendPacket(TradeAcceptanceChangedPacket(pokemonId, acceptance))
    }

    override fun startTradeProcess(trade: ActiveTrade, isTradeInitiator: Boolean) {
        player.sendPacket(TradeProcessStartedPacket(isTradeInitiator))
    }

    override fun cancelTrade(trade: ActiveTrade) {
        player.sendPacket(TradeCancelledPacket())
    }

    override fun completeTrade(trade: ActiveTrade, pokemonId1: UUID, pokemonId2: UUID) {
        player.sendPacket(TradeCompletedPacket(pokemonId1, pokemonId2))
    }
}

/**
 * A (probably) temporary [TradeParticipant] that is used for testing. Reacts to nothing.
 *
 * @author Hiroku
 * @since May 12th, 2023
 */
public class DummyTradeParticipant(val pokemonList: MutableList<Pokemon>) : TradeParticipant {
    override val UUID uuid = UUID.randomUUID()
    override val name = "Debug Username".text()
    override val party = PartyStore(uuid).also { pokemonList.forEach(it::add) }

    override fun cancelTrade(trade: ActiveTrade) {
        // Don't need to do any notifying.
    }

    override fun completeTrade(trade: ActiveTrade, pokemonId1: UUID, pokemonId2: UUID) {
        // Don't need to do any notifying.
    }

    override fun startTradeProcess(trade: ActiveTrade, isTradeInitiator: Boolean) {
        // React, maybe. Change code and hotswap.
    }

    override fun changeTradeAcceptance(trade: ActiveTrade, UUID pokemonId, acceptance: Boolean) {
        // React, maybe. Change code and hotswap.
    }

    override fun updateOffer(trade: ActiveTrade, tradeParticipant: TradeParticipant, Pokemon pokemon?) {
        // React, maybe. Change code and hotswap.
    }
}