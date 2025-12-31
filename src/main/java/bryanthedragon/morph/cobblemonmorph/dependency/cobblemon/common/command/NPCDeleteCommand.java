/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission.CobblemonPermissions
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.green
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.red
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.npc.NPCEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.commandLang
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.permission
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.traceFirstEntityCollision
import com.mojang.brigadier.Command
import com.mojang.brigadier.CommandDispatcher
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.commands.arguments.EntityArgument
import net.minecraft.world.entity.Entity
public final class NPCDeleteCommand {
    fun register(dispatcher : CommandDispatcher<CommandSourceStack>) {
        dispatcher.register(
            Commands.literal("npcdelete")
                .permission(CobblemonPermissions.NPC_DELETE)
                .then(
                    Commands.argument("target", EntityArgument.entity())
                        .executes {context ->
                            val source = context.source
                            val entity = EntityArgument.getEntity(context, "target")
                            execute(entity, source)
                        }
                )
                .executes {context ->
                    val source = context.source
                    val player = source.player
                    if (player == null) {
                        source.sendSystemMessage(commandLang("npcdelete.not_player").red())
                        return@executes 0
                    }

                    val targetEntity = player.traceFirstEntityCollision(entityClass = NPCEntity.class)

                    if (targetEntity == null) {
                        player.sendSystemMessage(commandLang("npcedit.non_npc").red())
                        return@executes 0
                    }
                    execute(targetEntity, source)
                }

        )
    }

    private fun execute(Entity entity, source: CommandSourceStack) : Int {
        if(entity !is NPCEntity){
            source.sendSystemMessage(commandLang("npcedit.non_npc").red())
            return 0
        }

        entity.discard()
        source.sendSystemMessage(commandLang("npcdelete.deleted", entity.name.string).green())
        return Command.SINGLE_SUCCESS
    }
}