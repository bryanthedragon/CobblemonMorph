/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.riding.behaviour

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.riding.behaviour.types.air.*
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.riding.behaviour.types.composite.CompositeBehaviour
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.riding.behaviour.types.land.HorseBehaviour
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.riding.behaviour.types.land.VehicleBehaviour
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.riding.behaviour.types.land.MinekartBehaviour
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.riding.behaviour.types.liquid.BoatBehaviour
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.riding.behaviour.types.liquid.BurstBehaviour
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.riding.behaviour.types.liquid.DolphinBehaviour
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.riding.behaviour.types.liquid.SubmarineBehaviour
import net.minecraft.resources.ResourceLocation
final class RidingBehaviours {
    val behaviours = mutableMapOf<ResourceLocation, RidingBehaviour<RidingBehaviourSettings, RidingBehaviourState>>()

    init {
        register(BirdBehaviour.KEY, BirdBehaviour())
        register(DolphinBehaviour.KEY, DolphinBehaviour())
        register(HorseBehaviour.KEY, HorseBehaviour())
        register(BoatBehaviour.KEY, BoatBehaviour())
        register(GliderBehaviour.KEY, GliderBehaviour())
        register(HelicopterBehaviour.KEY, HelicopterBehaviour())
        register(JetBehaviour.KEY, JetBehaviour())
        register(BurstBehaviour.KEY, BurstBehaviour())
        register(VehicleBehaviour.KEY, VehicleBehaviour())
        register(MinekartBehaviour.KEY, MinekartBehaviour())
        register(HoverBehaviour.KEY, HoverBehaviour())
        register(RocketBehaviour.KEY, RocketBehaviour())
        register(SubmarineBehaviour.KEY, SubmarineBehaviour())
        register(CompositeBehaviour.KEY, CompositeBehaviour())
    }

    @JvmStatic
    fun register(key: ResourceLocation, behaviour: RidingBehaviour<out RidingBehaviourSettings, out RidingBehaviourState>) {
        if (behaviours.contains(key)) error("Behaviour already registered to key $key")
        behaviours[key] = behaviour as RidingBehaviour<RidingBehaviourSettings, RidingBehaviourState>
    }

    @JvmStatic
    fun get(key: ResourceLocation): RidingBehaviour<RidingBehaviourSettings, RidingBehaviourState> {
        if (!behaviours.contains(key)) error("Behaviour not registered to key $key")
        return behaviours[key]!!
    }
}