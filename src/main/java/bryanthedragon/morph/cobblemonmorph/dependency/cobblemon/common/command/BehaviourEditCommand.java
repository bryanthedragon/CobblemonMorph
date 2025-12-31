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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.red
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.BehaviourEditingTracker
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.MoLangScriptingEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.npc.NPCEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.OpenBehaviourEditorPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.commandLang
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.requiresWithPermission
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.traceFirstEntityCollision
import com.mojang.brigadier.Command
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.context.CommandContext
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.LivingEntity
public final class BehaviourEditCommand {
    fun register(dispatcher : CommandDispatcher<CommandSourceStack>) {
        dispatcher.register(
            Commands.literal("behaviouredit")
                .requiresWithPermission(CobblemonPermissions.BEHAVIOUR_EDIT) { it.player != null }
                .executes { execute(it, it.source.playerOrException) })
    }

    private fun execute(context: CommandContext<CommandSourceStack>, ServerPlayer player) : Int {
        val targetEntity = player.traceFirstEntityCollision(entityClass = LivingEntity.class, ignoreEntity = player)?.takeIf { it is MoLangScriptingEntity }
        if (targetEntity == null) {
            player.sendSystemMessage(commandLang("behaviouredit.non_scriptable").red())
            return 0
        }

        if (targetEntity is NPCEntity) {
            targetEntity.edit(player)
        } else {
            BehaviourEditingTracker.startEditing(player, targetEntity)
            player.sendPacket(OpenBehaviourEditorPacket(targetEntity.id, (targetEntity as MoLangScriptingEntity).behaviours.toSet()))
        }

        return Command.SINGLE_SUCCESS
    }
}