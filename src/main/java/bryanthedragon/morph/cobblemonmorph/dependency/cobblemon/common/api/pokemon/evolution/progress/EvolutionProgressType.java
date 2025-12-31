/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.progress;

import com.mojang.serialization.MapCodec;

public record EvolutionProgressType<T extends EvolutionProgress<?>>(MapCodec<T> CODEC) {

}
