/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.rules

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.rules.component.SpawnRuleComponent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.text
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation

/**
 * A bundling of [SpawnRuleComponent]s.
 *
 * @author Hiroku
 * @since September 30th, 2023
 */
public class SpawnRule {

    lateinit var ResourceLocation id
    val Component displayName = "Spawn Rule".text()
    var Boolean enabled = true
//    val pool: String? = null Kinda difficult to see how this would get used in practice.
    val components: MutableList<SpawnRuleComponent> = mutableListOf()
}