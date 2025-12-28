/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common

import com.bedrockk.molang.Expression
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.DataRegistry
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ExpressionLike
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mechanics.AprijuicesMechanic
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mechanics.BerriesMechanic
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mechanics.PotionsMechanic
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mechanics.RemediesMechanic
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mechanics.SlowpokeTailsMechanic
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.data.CobblemonMechanicsSyncPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.ExpressionAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.ExpressionLikeAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import com.google.gson.GsonBuilder
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.server.packs.PackType
import net.minecraft.server.packs.resources.ResourceManager
final class CobblemonMechanics : DataRegistry {
    override val id: ResourceLocation = cobblemonResource("mechanics")
    override val type = PackType.SERVER_DATA
    override val observable = SimpleObservable<CobblemonMechanics>()
    val gson = GsonBuilder()
        .setPrettyPrinting()
        .registerTypeAdapter(Expression::class.java, ExpressionAdapter)
        .registerTypeAdapter(ExpressionLike::class.java, ExpressionLikeAdapter)
        .create()

    var remedies = RemediesMechanic()
    var berries = BerriesMechanic()
    var potions = PotionsMechanic()
    var aprijuices = AprijuicesMechanic()
    var slowpokeTails = SlowpokeTailsMechanic()

    override fun sync(player: ServerPlayer) {
        CobblemonMechanicsSyncPacket(
            this.remedies,
            this.berries,
            this.potions,
            this.aprijuices,
            this.slowpokeTails
        ).sendToPlayer(player)
    }

    override fun reload(manager: ResourceManager) {
        remedies = loadMechanic(manager, "remedies", RemediesMechanic::class.java)
        berries = loadMechanic(manager, "berries", BerriesMechanic::class.java)
        potions = loadMechanic(manager, "potions", PotionsMechanic::class.java)
        aprijuices = loadMechanic(manager, "aprijuices", AprijuicesMechanic::class.java)
        slowpokeTails = loadMechanic(manager, "slowpoke_tails", SlowpokeTailsMechanic::class.java)
    }

    private fun <T> loadMechanic(manager: ResourceManager, name: String, clazz: Class<T>): T {
        manager.getResourceOrThrow(cobblemonResource("mechanics/$name.json")).open().use {
            return gson.fromJson(it.reader(), clazz)
        }
    }
}