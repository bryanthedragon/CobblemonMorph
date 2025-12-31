/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen1

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.CryProvider
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.PokemonPosableModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.CobblemonPose
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.PoseType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.PoseType.Companion.MOVING_POSES
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.PoseType.Companion.STATIONARY_POSES
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.PoseType.Companion.UI_POSES
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.isBattling
import net.minecraft.client.model.geom.ModelPart
import net.minecraft.world.phys.Vec3

public class PinsirModel(root: ModelPart) : PokemonPosableModel(root) {
    override val rootPart = root.registerChildWithAllChildren("pinsir")

    override var portraitScale = 1.6F
    override var portraitTranslation = Vec3(-0.3, 0.3, 0.0)

    override var profileScale = 0.65F
    override var profileTranslation = Vec3(0.0, 0.75, 0.0)

    lateinit var standing: CobblemonPose
    lateinit var walk: CobblemonPose
    lateinit var battleidle: CobblemonPose
    lateinit var sleep: CobblemonPose

    override val cryAnimation = CryProvider { bedrockStateful("pinsir", "cry") }

    override fun registerPoses() {
        val blink = quirk { bedrockStateful("pinsir", "blink")}
        sleep = registerPose(
            poseName = "sleep",
            poseType = PoseType.SLEEP,
            animations = arrayOf(bedrock("pinsir", "sleep"))
        )

        standing = registerPose(
            poseName = "standing",
            poseTypes = STATIONARY_POSES + UI_POSES,
            transformTicks = 10,
            condition = { !it.isBattling },
            quirks = arrayOf(blink),
            animations = arrayOf(
                bedrock("pinsir", "ground_idle")
            )
        )

        walk = registerPose(
            poseName = "walk",
            poseTypes = MOVING_POSES,
            transformTicks = 10,
            quirks = arrayOf(blink),
            animations = arrayOf(
                bedrock("pinsir", "ground_walk")
            )
        )

        battleidle = registerPose(
            poseName = "battleidle",
            poseTypes = PoseType.STATIONARY_POSES,
            condition = { it.isBattling },
            transformTicks = 10,
            quirks = arrayOf(blink),
            animations = arrayOf(bedrock("pinsir", "battle_idle"))
        )
    }

//    override fun getFaintAnimation(
//        pokemonEntity: PokemonEntity,
//        state: PosableState<PokemonEntity>
//    ) = if (state.isPosedIn(standing, walk)) bedrockStateful("pinsir", "faint") else null
}