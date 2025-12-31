/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.particle

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.BedrockParticleOptions
import net.minecraft.core.particles.ParticleOptions

public class SnowstormParticleOptions(val effect: BedrockParticleOptions) : ParticleOptions {
    override fun getType() = CobblemonParticles.SNOWSTORM_PARTICLE_TYPE
}