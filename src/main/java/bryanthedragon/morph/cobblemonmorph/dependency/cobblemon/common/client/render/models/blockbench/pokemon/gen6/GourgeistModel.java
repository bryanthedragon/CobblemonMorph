/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen6

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.HeadedFrame
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.CryProvider
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.PokemonPosableModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Pose
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.PoseType
import net.minecraft.client.model.geom.ModelPart
import net.minecraft.world.phys.Vec3

public class GourgeistModel (root: ModelPart) : PokemonPosableModel(root), HeadedFrame {
    override val rootPart = root.registerChildWithAllChildren("gourgeist")
    override val head = getPart("head")

    override var portraitScale = 4.0F
    override var portraitTranslation = Vec3(-0.1, 0.6, 0.0)

    override var profileScale = 0.8F
    override var profileTranslation = Vec3(0.0, 0.55, 0.0)

    lateinit var standing: Pose
    lateinit var walk: Pose
    lateinit var hovering: Pose
    lateinit var fly: Pose
    lateinit var sleep: Pose

    override val cryAnimation = CryProvider { bedrockStateful("gourgeist", "cry") }

    override fun registerPoses() {
        val blink = quirk { bedrockStateful("gourgeist", "blink") }
        standing = registerPose(
            poseName = "standing",
            poseTypes = PoseType.UI_POSES + PoseType.STAND,
            quirks = arrayOf(blink),
            animations = arrayOf(
                singleBoneLook(),
                bedrock("gourgeist", "ground_idle")
            )
        )

        walk = registerPose(
            poseName = "walk",
            poseType = PoseType.WALK,
            quirks = arrayOf(blink),
            animations = arrayOf(
                bedrock("gourgeist", "ground_walk")
            )
        )

        hovering = registerPose(
            poseName = "hovering",
            poseType = PoseType.HOVER,
            quirks = arrayOf(blink),
            animations = arrayOf(
                bedrock("gourgeist", "air_idle")
            )
        )

        fly = registerPose(
            poseName = "fly",
            poseType = PoseType.FLY,
            quirks = arrayOf(blink),
            animations = arrayOf(
                bedrock("gourgeist", "air_fly")
            )
        )

        sleep = registerPose(
            poseName = "sleep",
            poseType = PoseType.SLEEP,
            quirks = arrayOf(blink),
            animations = arrayOf(
                bedrock("gourgeist", "sleep")
            )
        )
    }
}