/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon;

import com.bedrockk.molang.runtime.value.DoubleValue;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.Cancelable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions.asMoLangValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions.moLangFunctionMap;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.trade.TradeParticipant;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.getPlayer;

/**
 * Event fired when a trade is initiated or completed.
 *
 * @author MeAlam
 * @since August 23th, 2025
 */
public interface TradeEvent {

    /**
     * The first [TradeParticipant].
     */
    public final TradeParticipant tradeParticipant1;

    /**
     * The [Pokemon] being traded by the first participant.
     */
    public final Pokemon tradeParticipant1Pokemon;

    /**
     * The second [TradeParticipant].
     */
    public final TradeParticipant tradeParticipant2;

    /**
     * The [Pokemon] being traded by the second participant.
     */
    public final Pokemon tradeParticipant2Pokemon;

    /**
     * Event fired when a trade is about to happen. Cancelling this event prevents the trade from occurring.
     *
     * @author MeAlam
     * @since August 23th, 2025
     */
    public record Pre(public final TradeParticipant tradeParticipant1, public final  Pokemon tradeParticipant1Pokemon, public final TradeParticipant tradeParticipant2, public final Pokemon tradeParticipant2Pokemon) : TradeEvent, Cancelable() {
        public final context = mutableMapOf("trade_participant_1" to (tradeParticipant1.uuid.getPlayer()?.asMoLangValue() ?: DoubleValue.ZERO), "trade_participant_2" to (tradeParticipant2.uuid.getPlayer()?.asMoLangValue() ?: DoubleValue.ZERO), "trade_participant_1_pokemon" to tradeParticipant1Pokemon.struct, "trade_participant_2_pokemon" to tradeParticipant2Pokemon.struct)

        public final functions = moLangFunctionMap(
            cancelFunc;
        )
    }

    /**
     * Event fired after a trade has occurred.
     *
     * @author MeAlam
     * @since August 23th, 2025
     */
    record Post(public final TradeParticipant tradeParticipant1, public final  Pokemon tradeParticipant1Pokemon, public final TradeParticipant tradeParticipant2, public final Pokemon tradeParticipant2Pokemon) : TradeEvent {
        public final context = mutableMapOf("trade_participant_1" to (tradeParticipant1.uuid.getPlayer()?.asMoLangValue() ?: DoubleValue.ZERO), "trade_participant_2" to (tradeParticipant2.uuid.getPlayer()?.asMoLangValue() ?: DoubleValue.ZERO), "trade_participant_1_pokemon" to tradeParticipant1Pokemon.struct, "trade_participant_2_pokemon" to tradeParticipant2Pokemon.struct);
    }
}
