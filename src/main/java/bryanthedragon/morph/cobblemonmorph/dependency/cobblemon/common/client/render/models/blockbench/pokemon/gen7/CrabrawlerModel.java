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
import net.minecraft.client.model.geom.ModelPart
import net.minecraft.world.phys.Vec3

public class CrabrawlerModel (root: ModelPart) : PokemonPosableModel(root), HeadedFrame{
    override val rootPart = root.registerChildWithAllChildren("crabrawler")
    override val head = getPart("head")

    override var portraitScale = 2.2F
    override var portraitTranslation = Vec3(-0.25, -0.5, 0.0)

    override var profileScale = 0.65F
    override var profileTranslation = Vec3(0.0, 0.7, 0.0)

    lateinit var standing: Pose
    lateinit var walk: Pose
    lateinit var portrait: Pose

    override fun registerPoses() {
        portrait = registerPose(
            poseName = "portrait",
            poseType = PoseType.PORTRAIT,
            animations = arrayOf(
                singleBoneLook(),
                bedrock("crabrawler", "portrait")
            )
        )

        standing = registerPose(
            poseName = "standing",
            poseTypes = PoseType.STATIONARY_POSES + PoseType.PROFILE,
            animations = arrayOf(
                singleBoneLook(),
                bedrock("crabrawler", "ground_idle")
            )
        )

        walk = registerPose(
            poseName = "walk",
            poseTypes = PoseType.MOVING_POSES,
            animations = arrayOf(
                singleBoneLook(),
                bedrock("crabrawler", "ground_idle"),
                bedrock("crabrawler", "ground_walk")
            )
        )
    }

//    override fun getFaintAnimation(
//        pokemonEntity: PokemonEntity,
//        state: PosableState<PokemonEntity>
//    ) = if (state.isPosedIn(standing, walk)) bedrockStateful("crabrawler", "faint") else null
}