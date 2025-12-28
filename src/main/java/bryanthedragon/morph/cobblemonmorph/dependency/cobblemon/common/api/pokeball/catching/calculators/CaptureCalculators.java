/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.calculators

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokeball.catching.calculators.CobblemonCaptureCalculator
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokeball.catching.calculators.DebugCaptureCalculator
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokeball.catching.calculators.Gen1CaptureCalculator
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokeball.catching.calculators.Gen2CaptureCalculator
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokeball.catching.calculators.Gen3And4CaptureCalculator
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokeball.catching.calculators.Gen5CaptureCalculator
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokeball.catching.calculators.Gen6CaptureCalculator
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokeball.catching.calculators.Gen7CaptureCalculator
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokeball.catching.calculators.Gen8CaptureCalculator
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokeball.catching.calculators.Gen9CaptureCalculator
final class CaptureCalculators {

    private val calculators = linkedMapOf<String, CaptureCalculator>()

    fun register(calculator: CaptureCalculator) {
        val id = calculator.id().lowercase()
        val existing = this.calculators.put(id, calculator)
        if (existing != null) {
            Cobblemon.LOGGER.debug("The capture calculator {} with ID {} was replaced by {}", existing::class.qualifiedName, id, calculator::class.qualifiedName)
        }
    }

    fun fromId(id: String): CaptureCalculator? = this.calculators[id.lowercase()]

    internal fun registerDefaults() {
        this.register(Gen1CaptureCalculator)
        this.register(Gen2CaptureCalculator(false))
        this.register(Gen2CaptureCalculator(true))
        this.register(Gen3And4CaptureCalculator)
        this.register(Gen5CaptureCalculator)
        this.register(Gen6CaptureCalculator)
        this.register(Gen7CaptureCalculator)
        this.register(Gen8CaptureCalculator)
        this.register(Gen9CaptureCalculator)
        this.register(CobblemonCaptureCalculator)
        this.register(DebugCaptureCalculator)
    }

}