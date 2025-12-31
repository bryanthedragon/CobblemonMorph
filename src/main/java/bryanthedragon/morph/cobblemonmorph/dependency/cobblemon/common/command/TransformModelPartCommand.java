/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission.CobblemonPermissions
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.text
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.createTransformation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.argument.ModelPartArgumentType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.argument.TransformTypeArgumentType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.argument.TransformTypeArgumentType.Companion.TransformType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.permission
import com.mojang.brigadier.Command
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.FloatArgumentType
import com.mojang.brigadier.context.CommandContext
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
public final class TransformModelPartCommand {

    fun register(dispatcher : CommandDispatcher<CommandSourceStack>) {
        val command = Commands.literal("transformmodelpart")
            .permission(CobblemonPermissions.TRANSFORM_MODEL_PART)
            .then(Commands.argument("transform", TransformTypeArgumentType.transformType())
                .then(Commands.argument("part", ModelPartArgumentType.modelPart())
                    .then(Commands.argument("x", FloatArgumentType.floatArg())
                        .then(Commands.argument("y", FloatArgumentType.floatArg())
                            .then(Commands.argument("z", FloatArgumentType.floatArg())
                                .executes(::execute)
                            )
                        )
                    )
                    .executes(::execute))
                .executes(::execute))
        dispatcher.register(command)
    }

    private fun execute(context: CommandContext<CommandSourceStack>) : Int {
        val transformType = TransformTypeArgumentType.getTransform(context, "transform")
        val targetModelPart = ModelPartArgumentType.getModelPart(context, "part")?: return 0
        val x = FloatArgumentType.getFloat(context, "x")
        val y = FloatArgumentType.getFloat(context, "y")
        val z = FloatArgumentType.getFloat(context, "z")

        val model = ModelPartArgumentType.getModel(context)?: return 0

        val existing = model.transformedParts.find { it.modelPart == targetModelPart }
        when (transformType) {
            TransformType.POSITION -> {
                if (existing != null) existing.withPosition(x, y, z)
                else model.transformedParts += targetModelPart.createTransformation().withPosition(x, y, z)
            }
            TransformType.ROTATION -> {
                if (existing != null) existing.withRotation(x, y, z)
                else model.transformedParts += targetModelPart.createTransformation().withRotation(x, y, z)
            }
            TransformType.SCALE -> {
                if (existing != null) existing.withScale(x, y, z)
                else model.transformedParts += targetModelPart.createTransformation().withScale(x, y, z)
            }
        }

        context.source.playerOrException.sendSystemMessage("Changed ${transformType.name.lowercase()} of ${context.getArgument("part", String.class )} to $x, $y, $z".text())
        return Command.SINGLE_SUCCESS
    }
}