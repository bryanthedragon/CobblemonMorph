/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission.CobblemonPermissions
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.argument.UnlockablePCBoxWallpaperArgumentType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.commandLang
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.effectiveName
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.pc
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.permission
import com.mojang.brigadier.Command
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.BoolArgumentType
import com.mojang.brigadier.context.CommandContext
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.commands.arguments.EntityArgument
final class UnlockPCBoxWallpaperCommand {
    fun register(dispatcher : CommandDispatcher<CommandSourceStack>) {
        dispatcher.register(Commands.literal("unlockpcboxwallpaper")
            .permission(CobblemonPermissions.UNLOCK_PC_BOX_WALLPAPER)
            .then(
                Commands.argument("player", EntityArgument.player())
                    .then(
                        Commands.argument("wallpaper", UnlockablePCBoxWallpaperArgumentType.wallpaper())
                            .executes { execute(it, playSound = true) }
                            .then(
                                Commands.argument("playsSound", BoolArgumentType.bool())
                                    .executes { execute(it, BoolArgumentType.getBool(it, "playsSound"))}
                            )
                    )
            )
        )
    }

    private fun execute(ctx: CommandContext<CommandSourceStack>, playSound: Boolean): Int {
        val source = ctx.source
        val wallpaperId = UnlockablePCBoxWallpaperArgumentType.getUnlockablePCBoxWallpaper(ctx, "wallpaper")
        val player = EntityArgument.getPlayer(ctx, "player")

        try {
            if (player.pc().unlockWallpaper(wallpaperId, playSound)) {
                source.sendSuccess({ commandLang("unlockboxwallpaper.success", player.effectiveName(), wallpaperId.toString()) }, true)
            } else {
                source.sendFailure(commandLang("unlockboxwallpaper.already", player.effectiveName(), wallpaperId.toString()))
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
        return Command.SINGLE_SUCCESS
    }
}