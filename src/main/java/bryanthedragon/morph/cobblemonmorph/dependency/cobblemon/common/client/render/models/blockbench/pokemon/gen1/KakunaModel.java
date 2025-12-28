/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen1

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PosableState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.HeadedFrame
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.CryProvider
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.PokemonPosableModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Pose
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.PoseType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.PoseType.Companion.ALL_POSES
import net.minecraft.client.model.geom.ModelPart
import net.minecraft.world.phys.Vec3

class KakunaModel(root: ModelPart) : PokemonPosableModel(root), HeadedFrame {
    override val rootPart = root.registerChildWithAllChildren("kakuna")
    override val head = getPart("head")

    override var portraitScale = 1.7F
    override var portraitTranslation = Vec3(0.1, -0.4, 0.0)
    override var profileScale = 0.96F
    override var profileTranslation = Vec3(0.0, 0.35, 0.0)

    lateinit var sleep: Pose
    lateinit var standing: Pose

    override val cryAnimation = CryProvider { bedrockStateful("kakuna", "cry") }

    override fun registerPoses() {
        sleep = registerPose(
            poseType = PoseType.SLEEP,
            animations = arrayOf(bedrock("kakuna", "sleep"))
        )

        standing = registerPose(
            poseName = "standing",
            poseTypes = ALL_POSES - PoseType.SLEEP,
            animations = arrayOf(
                singleBoneLook(),
                bedrock("kakuna", "ground_idle")
            )
        )
    }

    override fun getFaintAnimation(state: PosableState) = bedrockStateful("kakuna", "faint")
}