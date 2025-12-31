/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command


import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission.CobblemonPermissions
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.TeamManager
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.alias
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.permission
import com.mojang.brigadier.Command.SINGLE_SUCCESS
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.context.CommandContext
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
public final class AbandonMultiTeam {

    private const val ALIAS = "abandonmultibattleteam"

    fun register(dispatcher: CommandDispatcher<CommandSourceStack>) {
        val command = dispatcher.register(
                Commands.literal("abandonmultiteam")
                        .permission(CobblemonPermissions.ABANDON_MULTITEAM)
                        .executes { execute(it) }
        )
        dispatcher.register(command.alias(ALIAS))
    }

    private fun execute(context: CommandContext<CommandSourceStack>) : Int {

        val player = context.source.player
        if (player != null) {
            TeamManager.removeTeamMember(player)
        }
        return SINGLE_SUCCESS
    }

}