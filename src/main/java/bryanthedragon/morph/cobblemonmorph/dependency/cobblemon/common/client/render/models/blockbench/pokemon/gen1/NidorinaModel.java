/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen1

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.QuadrupedWalkAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.HeadedFrame
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.QuadrupedFrame
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.CryProvider
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.PokemonPosableModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Pose
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.PoseType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.PoseType.Companion.MOVING_POSES
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.PoseType.Companion.STATIONARY_POSES
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.PoseType.Companion.UI_POSES
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.isBattling
import net.minecraft.client.model.geom.ModelPart
import net.minecraft.world.phys.Vec3

public class NidorinaModel(root: ModelPart) : PokemonPosableModel(root), HeadedFrame, QuadrupedFrame {
    override val rootPart = root.registerChildWithAllChildren("nidorina")
    override val head = getPart("head")
    override val foreLeftLeg= getPart("left_upper_arm")
    override val foreRightLeg = getPart("right_upper_arm")
    override val hindLeftLeg = getPart("left_leg")
    override val hindRightLeg = getPart("right_leg")

    override var portraitScale = 1.8F
    override var portraitTranslation = Vec3(-0.35, -0.8, 0.0)

    override var profileScale = 1.0F
    override var profileTranslation = Vec3(0.0, 0.21, 0.0)

    lateinit var sleep: Pose
    lateinit var standing: Pose
    lateinit var walk: Pose
    lateinit var battleidle: Pose

    override val cryAnimation = CryProvider { bedrockStateful("nidorina", "cry") }

    override fun registerPoses() {
        val blink = quirk { bedrockStateful("nidorina", "blink")}
        standing = registerPose(
            poseName = "standing",
            poseTypes = STATIONARY_POSES + UI_POSES,
            condition = { !it.isBattling },
            quirks = arrayOf(blink),
            animations = arrayOf(
                singleBoneLook(),
                bedrock("nidorina", "idle3")
            )
        )

        sleep = registerPose(
                poseType = PoseType.SLEEP,
                animations = arrayOf(bedrock("nidorina", "idle2"))
        )

        walk = registerPose(
            poseName = "walk",
            poseTypes = MOVING_POSES,
            quirks = arrayOf(blink),
            animations = arrayOf(
                singleBoneLook(),
                bedrock("nidorina", "idle2"),
                QuadrupedWalkAnimation(this)
            )
        )

        battleidle = registerPose(
            poseName = "battle_idle",
            poseTypes = PoseType.STATIONARY_POSES,
            transformTicks = 10,
            quirks = arrayOf(blink),
            condition = { it.isBattling },
            animations = arrayOf(
                singleBoneLook(),
                bedrock("nidorina", "idle")
            )
        )
    }

//    override fun getFaintAnimation(
//        pokemonEntity: PokemonEntity,
//        state: PosableState<PokemonEntity>
//    ) = if (state.isPosedIn(standing, walk)) bedrockStateful("nidorina", "faint") else null
}