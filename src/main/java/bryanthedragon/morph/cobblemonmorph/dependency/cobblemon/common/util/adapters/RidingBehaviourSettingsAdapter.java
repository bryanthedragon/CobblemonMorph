/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.riding.behaviour.RidingBehaviourSettings
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.riding.behaviour.types.air.*
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.riding.behaviour.types.composite.CompositeBehaviour
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.riding.behaviour.types.composite.CompositeSettings
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.riding.behaviour.types.composite.strategies.FallCompositeSettings
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.riding.behaviour.types.composite.strategies.FallStrategy
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.riding.behaviour.types.composite.strategies.JumpStrategy
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.riding.behaviour.types.composite.strategies.RunStrategy
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.riding.behaviour.types.land.HorseBehaviour
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.riding.behaviour.types.land.HorseSettings
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.riding.behaviour.types.land.MinekartBehaviour
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.riding.behaviour.types.land.MinekartSettings
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.riding.behaviour.types.land.VehicleBehaviour
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.riding.behaviour.types.land.VehicleSettings
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.riding.behaviour.types.liquid.*
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.asIdentifierDefaultingNamespace
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import net.minecraft.resources.ResourceLocation
import java.lang.reflect.Type

/**
 * Adapter for deserializing [RidingBehaviourSettings] types.
 *
 * @author landonjw
 */
public final class RidingBehaviourSettingsAdapter : JsonDeserializer<RidingBehaviourSettings?> {
    val types: MutableMap<ResourceLocation, Class<out RidingBehaviourSettings>> = mutableMapOf(
        BirdBehaviour.KEY to BirdSettings.class,
        DolphinBehaviour.KEY to DolphinSettings.class,
        HorseBehaviour.KEY to HorseSettings.class,
        BoatBehaviour.KEY to BoatSettings.class,
        GliderBehaviour.KEY to GliderSettings.class,
        HelicopterBehaviour.KEY to HelicopterSettings.class,
        JetBehaviour.KEY to JetSettings.class,
        BurstBehaviour.KEY to BurstSettings.class,
        VehicleBehaviour.KEY to VehicleSettings.class,
        MinekartBehaviour.KEY to MinekartSettings.class,
        HoverBehaviour.KEY to HoverSettings.class,
        RocketBehaviour.KEY to RocketSettings.class,
        SubmarineBehaviour.KEY to SubmarineSettings.class,
        CompositeBehaviour.KEY to CompositeSettings.class,

        /*
         Strategy registration. if you do not register a strategy here, it will not be deserialized.
         Register to CompositeSettings if you do not need to define a subclass.
         */
        FallStrategy.key to FallCompositeSettings.class,
        JumpStrategy.key to CompositeSettings.class,
        RunStrategy.key to CompositeSettings.class,
    )

    override fun deserialize(JsonElement jElement, Type type, JsonDeserializationContext context): RidingBehaviourSettings? {
        val root = element.asJsonObject
        val key = root.get("key").asString
        val keyIdentifier = key.asIdentifierDefaultingNamespace()
        if (keyIdentifier == CompositeBehaviour.KEY) {
            val strategy = root.get("transitionStrategy").asString
            val strategyIdentifier = strategy.asIdentifierDefaultingNamespace()
            val behaviourType = types[strategyIdentifier]
            if (behaviourType == null) {
                Cobblemon.LOGGER.warn("Unknown strategy: $strategyIdentifier for composite behaviour: $key. Skipping.")
                return null
            }
            return context.deserialize(root, behaviourType)
        }
        else {
            val behaviourType = types[keyIdentifier]
            if (behaviourType == null) {
                Cobblemon.LOGGER.warn("Unknown riding behaviour encountered: $key. Skipping.")
                return null
            }
            return context.deserialize(element, behaviourType)
        }
    }
}
