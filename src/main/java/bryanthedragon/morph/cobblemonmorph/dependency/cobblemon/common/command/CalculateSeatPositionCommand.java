/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork.sendPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission.CobblemonPermissions
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.argument.SpeciesArgumentType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.PoseType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.CalculateSeatPositionsPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.permission
import com.mojang.brigadier.Command
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.context.CommandContext
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
public final class CalculateSeatPositionCommand {
    fun register(dispatcher : CommandDispatcher<CommandSourceStack>) {
        dispatcher.register(
            Commands.literal("calculateseatpositions")
                .permission(CobblemonPermissions.CALCULATE_SEAT_POSITIONS)
                .then(
                    Commands.argument("species", SpeciesArgumentType.species())
                        .then(
                            Commands.argument("aspects", StringArgumentType.string())
                                .then(
                                    Commands.argument("pose", StringArgumentType.string()).executes(::run)
                                )
                        )
                )
        )
    }

    private fun run(context: CommandContext<CommandSourceStack>) : Int {
        val player = context.source.playerOrException
        val species = SpeciesArgumentType.getPokemon(context, "species")
        val aspects = StringArgumentType.getString(context, "aspects").split(",").toSet()
        val pose = StringArgumentType.getString(context, "pose")
        val poseType = PoseType.valueOf(pose.uppercase())
        player.sendPacket(CalculateSeatPositionsPacket(species.resourceIdentifier, aspects, poseType))
        return Command.SINGLE_SUCCESS
    }
}