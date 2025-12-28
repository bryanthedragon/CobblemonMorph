/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.Dialogues
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission.CobblemonPermissions
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.text
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.argument.DialogueArgumentType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.alias
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.openDialogue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.permission
import com.mojang.brigadier.Command
import com.mojang.brigadier.CommandDispatcher
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.commands.arguments.EntityArgument
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
final class OpenDialogueCommand {
    fun register(dispatcher : CommandDispatcher<CommandSourceStack>) {
        val command = dispatcher.register(Commands.literal("opendialogue")
            .permission(CobblemonPermissions.OPEN_DIALOGUE)
            .then(
                Commands.argument("dialogue", DialogueArgumentType.dialogue())
                    .then(
                        Commands.argument("player", EntityArgument.player())
                            .executes {
                                val dialogueId = DialogueArgumentType.getDialogue(it, "dialogue")
                                if (!Dialogues.dialogues.containsKey(dialogueId)) {
                                    it.source.sendFailure("Invalid dialogue: $dialogueId".text())
                                    return@executes Command.SINGLE_SUCCESS
                                }
                                val player = EntityArgument.getPlayer(it, "player")
                                return@executes execute(it.source, dialogueId, player)
                            }
                    )
            )
        )
        dispatcher.register(command.alias("opendialogue"))
    }

    private fun execute(source: CommandSourceStack, dialogueId: ResourceLocation, player: ServerPlayer): Int {
        val dialogue = Dialogues.dialogues[dialogueId] ?: return run {
            source.sendSystemMessage("Invalid dialogue ID: $dialogueId".text())
            Command.SINGLE_SUCCESS
        }
        try {
            player.openDialogue(dialogue)
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
        return Command.SINGLE_SUCCESS
    }
}