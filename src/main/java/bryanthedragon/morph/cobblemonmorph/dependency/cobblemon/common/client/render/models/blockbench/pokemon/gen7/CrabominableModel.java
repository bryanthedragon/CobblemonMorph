/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen7

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.HeadedFrame
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.PokemonPosableModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Pose
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.PoseType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.PoseType.Companion.UI_POSES
import net.minecraft.client.model.geom.ModelPart
import net.minecraft.world.phys.Vec3

public class CrabominableModel (root: ModelPart) : PokemonPosableModel(root), HeadedFrame {
    override val rootPart = root.registerChildWithAllChildren("crabominable")
    override val head = getPart("head")

    override var portraitScale = 1.0F
    override var portraitTranslation = Vec3(-0.55, 1.65, 0.0)

    override var profileScale = 0.3F
    override var profileTranslation = Vec3(0.0, 1.2, 0.0)

    lateinit var standing: Pose
    lateinit var walk: Pose

    override fun registerPoses() {
        standing = registerPose(
            poseName = "standing",
            poseTypes = PoseType.STATIONARY_POSES + UI_POSES,
            animations = arrayOf(
                singleBoneLook(),
                bedrock("crabominable", "ground_idle")
            )
        )

        walk = registerPose(
            poseName = "walk",
            poseTypes = PoseType.MOVING_POSES,
            animations = arrayOf(
                singleBoneLook(),
                bedrock("crabominable", "ground_idle"),
                bedrock("crabominable", "ground_walk")
            )
        )
    }

//    override fun getFaintAnimation(
//        pokemonEntity: PokemonEntity,
//        state: PosableState<PokemonEntity>
//    ) = if (state.isPosedIn(standing, walk)) bedrockStateful("crabominable", "faint") else null
}