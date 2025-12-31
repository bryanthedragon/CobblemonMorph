/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.trade

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SettableObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon
import java.util.UUID

public class ClientTrade {
    var myOffer = SettableObservable<Pokemon?>(null)
    var oppositeOffer = SettableObservable<Pokemon?>(null)
    var tradeProcessing = SettableObservable(Pair(false, false))
    var oppositeAcceptedMyOffer = SettableObservable(false)
    var acceptedOppositeOffer = false

    var completedEmitter = SimpleObservable<Pair<UUID, UUID>>()
    var cancelEmitter = SimpleObservable<Unit>()
}