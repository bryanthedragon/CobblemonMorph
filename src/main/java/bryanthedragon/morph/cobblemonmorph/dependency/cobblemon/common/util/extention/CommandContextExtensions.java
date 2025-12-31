/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.extention;

import com.mojang.brigadier.context.CommandContext;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ResourceLocationArgument;
public class CommandContextExtensions {
    CommandContext<CommandSourceStack> player(String argumentName = "player") {
        EntityArgument.getPlayer(this, argumentName);
    }
    CommandContext<CommandSourceStack> string(String argumentName) {
        this.getArgument(argumentName, String.class);
    }
    CommandContext<CommandSourceStack> uuid(String argumentName) {
        this.getArgument(argumentName, String.class).asUUID;
    }
    CommandContext<CommandSourceStack> resourceLocation(String argumentName) {
        ResourceLocationArgument.getId(this, argumentName);
    }
    CommandContext<CommandSourceStack> entity(String argumentName); {
        EntityArgument.getEntity(this, argumentName);
    }
}