/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen7

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.BipedWalkAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.BipedFrame
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.PokemonPosableModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Pose
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.PoseType.Companion.MOVING_POSES
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.PoseType.Companion.STATIONARY_POSES
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.PoseType.Companion.UI_POSES
import net.minecraft.client.model.geom.ModelPart
import net.minecraft.world.phys.Vec3

public class BounsweetModel(root: ModelPart) : PokemonPosableModel(root), BipedFrame {
    override val rootPart = root.registerChildWithAllChildren("bounsweet")

    override val leftLeg = getPart("left_foot")
    override val rightLeg = getPart("right_foot")

    override var portraitScale = 3.0F
    override var portraitTranslation = Vec3(-0.1, -2.5, 0.0)

    override var profileScale = 1.2F
    override var profileTranslation = Vec3(0.0, 0.0, 0.0)

    lateinit var standing: Pose
    lateinit var walk: Pose

    override fun registerPoses() {
        standing = registerPose(
            poseName = "standing",
            poseTypes = STATIONARY_POSES + UI_POSES,
            animations = arrayOf(
                //bedrock("bounsweet", "ground_idle")
            )
        )

        walk = registerPose(
            poseName = "walk",
            poseTypes = MOVING_POSES,
            animations = arrayOf(
                BipedWalkAnimation(this, periodMultiplier = 1F, amplitudeMultiplier = 0.5F)
                //bedrock("bounsweet", "ground_walk")
            )
        )
    }

//    override fun getFaintAnimation(
//        pokemonEntity: PokemonEntity,
//        state: PosableState<PokemonEntity>
//    ) = if (state.isPosedIn(standing, walk)) bedrockStateful("bounsweet", "faint") else null
}