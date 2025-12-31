/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork.sendPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission.CobblemonPermissions
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.settings.OpenCobblemonConfigScreenPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.commandLang
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.permission
import com.mojang.brigadier.Command
import com.mojang.brigadier.CommandDispatcher
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.server.level.ServerPlayer
public final class CobblemonConfigCommand {
    fun register(dispatcher: CommandDispatcher<CommandSourceStack>) {
        dispatcher.register(
            Commands.literal("cobblemonconfig")
                .executes {
                    it.source.playerOrException.sendPacket(OpenCobblemonConfigScreenPacket())
                    Command.SINGLE_SUCCESS
                }
                .then(
                    Commands.literal("reload")
                        .permission(CobblemonPermissions.COBBLEMON_CONFIG_RELOAD)
                        .executes {
                            Cobblemon.reloadConfig()
                            it.source.server.playerList.players.forEach { player ->
                                if (player is ServerPlayer) {
                                    Cobblemon.sendServerSettingsPacketToPlayer(player)
                                }
                            }

                            it.source.sendSuccess({ commandLang("cobblemon_config.reload") }, true)
                            Command.SINGLE_SUCCESS
                        }
                )
        )
    }
}
