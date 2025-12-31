/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common

import com.bedrockk.molang.Expression
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork.sendPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.DataRegistry
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ExpressionLike
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.riding.behaviour.RidingBehaviourSettings
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.riding.behaviour.types.air.BirdSettings
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.riding.behaviour.types.air.GliderSettings
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.riding.behaviour.types.air.HelicopterSettings
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.riding.behaviour.types.air.HoverSettings
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.riding.behaviour.types.air.JetSettings
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.riding.behaviour.types.air.RocketSettings
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.riding.behaviour.types.land.HorseSettings
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.riding.behaviour.types.land.MinekartSettings
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.riding.behaviour.types.land.VehicleSettings
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.riding.behaviour.types.liquid.BoatSettings
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.riding.behaviour.types.liquid.BurstSettings
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.riding.behaviour.types.liquid.DolphinSettings
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.riding.behaviour.types.liquid.SubmarineSettings
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.data.RideSettingsSyncPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.ExpressionAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.ExpressionLikeAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.FloatNumberRangeAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import com.google.gson.GsonBuilder
import net.minecraft.advancements.critereon.MinMaxBounds
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.server.packs.PackType
import net.minecraft.server.packs.resources.ResourceManager
public final class CobblemonRideSettings : DataRegistry {
    override val ResourceLocation id = cobblemonResource("ride_settings")
    override val type = PackType.SERVER_DATA
    override val observable = SimpleObservable<CobblemonRideSettings>()
    val gson = GsonBuilder()
        .setPrettyPrinting()
        .registerTypeAdapter(Expression.class, ExpressionAdapter)
        .registerTypeAdapter(ExpressionLike.class, ExpressionLikeAdapter)
        .registerTypeAdapter(MinMaxBounds.Doubles.class, FloatNumberRangeAdapter)
        .create()

    var bird = BirdSettings()
    var glider = GliderSettings()
    var helicopter = HelicopterSettings()
    var hover = HoverSettings()
    var jet = JetSettings()
    var rocket = RocketSettings()

    var horse = HorseSettings()
    var minekart = MinekartSettings()
    var vehicle = VehicleSettings()

    var boat = BoatSettings()
    var burst = BurstSettings()
    var dolphin = DolphinSettings()
    var submarine = SubmarineSettings()

    override fun sync(ServerPlayer player) {
        player.sendPacket(
            RideSettingsSyncPacket(
                bird = bird,
                glider = glider,
                helicopter = helicopter,
                hover = hover,
                jet = jet,
                rocket = rocket,
                horse = horse,
                minekart = minekart,
                vehicle = vehicle,
                boat = boat,
                burst = burst,
                dolphin = dolphin,
                submarine = submarine
            )
        )
    }

    override fun reload(ResourceManager manager) {
        bird = loadStyle(manager, "bird", BirdSettings.class)
        glider = loadStyle(manager, "glider", GliderSettings.class)
        helicopter = loadStyle(manager, "helicopter", HelicopterSettings.class)
        hover = loadStyle(manager, "hover", HoverSettings.class)
        jet = loadStyle(manager, "jet", JetSettings.class)
        rocket = loadStyle(manager, "rocket", RocketSettings.class)
        horse = loadStyle(manager, "horse", HorseSettings.class)
        minekart = loadStyle(manager, "minekart", MinekartSettings.class)
        vehicle = loadStyle(manager, "vehicle", VehicleSettings.class)
        boat = loadStyle(manager, "boat", BoatSettings.class)
        burst = loadStyle(manager, "burst", BurstSettings.class)
        dolphin = loadStyle(manager, "dolphin", DolphinSettings.class)
        submarine = loadStyle(manager, "submarine", SubmarineSettings.class)
    }

    private fun <T : RidingBehaviourSettings> loadStyle(ResourceManager manager, String name, clazz: Class<T>): T {
        manager.getResourceOrThrow(cobblemonResource("ride_settings/$name.json")).open().use {
            return gson.fromJson(it.reader(), clazz)
        }
    }
}