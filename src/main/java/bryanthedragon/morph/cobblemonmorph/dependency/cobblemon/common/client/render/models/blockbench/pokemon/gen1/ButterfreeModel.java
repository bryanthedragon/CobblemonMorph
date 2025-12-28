/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen1

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.createTransformation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.BiWingedFrame
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.HeadedFrame
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.CryProvider
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.PokemonPosableModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.ModelPartTransformation.Companion.Y_AXIS
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Pose
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.PoseType
import net.minecraft.client.model.geom.ModelPart
import net.minecraft.world.phys.Vec3

class ButterfreeModel(root: ModelPart) : PokemonPosableModel(root), HeadedFrame, BiWingedFrame {
    override val rootPart = root.registerChildWithAllChildren("butterfree")
    override val head = getPart("head")
    override val leftWing = getPart("wing_left")
    override val rightWing = getPart("wing_right")

    override var portraitScale = 2.4F
    override var portraitTranslation = Vec3(-0.1, 0.2, 0.0)

    override var profileScale = 0.7F
    override var profileTranslation = Vec3(0.1, 0.8, 0.0)

    lateinit var sleep: Pose

    override val cryAnimation = CryProvider { bedrockStateful("butterfree", "cry") }

    override fun registerPoses() {
        sleep = registerPose(
            poseType = PoseType.SLEEP,
            animations = arrayOf(bedrock("butterfree", "sleep"))
        )

        registerPose(
            poseName = "standing",
            poseTypes = setOf(PoseType.NONE, PoseType.PROFILE, PoseType.PORTRAIT, PoseType.STAND, PoseType.HOVER, PoseType.FLOAT),
            animations = arrayOf(
                singleBoneLook(),
                bedrock("butterfree", "air_idle")
            ),
            transformedParts = arrayOf(rootPart.createTransformation().addPosition(Y_AXIS, -5F))
        )

        registerPose(
            poseName = "walking",
            poseTypes = PoseType.MOVING_POSES,
            animations = arrayOf(
                singleBoneLook(),
                bedrock("butterfree", "air_fly")
            ),
            transformedParts = arrayOf(rootPart.createTransformation().addPosition(Y_AXIS, -5F))
        )
    }
}