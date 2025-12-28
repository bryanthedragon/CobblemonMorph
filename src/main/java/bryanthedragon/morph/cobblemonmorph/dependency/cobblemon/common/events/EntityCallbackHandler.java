/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.events

import com.bedrockk.molang.runtime.MoParams
import com.bedrockk.molang.runtime.value.MoValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.Priority
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.CobblemonEvents.THROWN_POKEBALL_HIT
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokeball.ThrownPokeballHitEvent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.Observable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.EntityCallbacks
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.EntityCallbacks.Companion.HIT_BY_POKEBALL
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.MoLangScriptingEntity
import net.minecraft.resources.ResourceLocation

/**
 * Handles the registration of entity callbacks for events related to entities. You can add your own
 * as well, all this actually does is subscribe to the event and pass it through the [EntityCallbacks] of
 * the entity the event is about. In general, entity callbacks occur on the normal priority so that
 * plugins can jump in ahead but on parity with the general MoLang callbacks.
 *
 * @author Hiroku
 * @since July 26th, 2025
 */final class EntityCallbackHandler {
    fun setup() {
        bindCallback(THROWN_POKEBALL_HIT, HIT_BY_POKEBALL, ThrownPokeballHitEvent::pokemon, ThrownPokeballHitEvent::functions)
    }

    fun <T> bindCallback(
        observable: Observable<T>,
        type: ResourceLocation,
        entity: (T) -> MoLangScriptingEntity,
        functions: (T) -> Map<String, (MoParams) -> MoValue>
    ) {
        observable.subscribe(priority = Priority.NORMAL) { entity(it).callbacks.process(type, functions(it)) }
    }
}