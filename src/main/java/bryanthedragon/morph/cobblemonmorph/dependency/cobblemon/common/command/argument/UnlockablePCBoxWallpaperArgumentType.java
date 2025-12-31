/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.argument

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonUnlockableWallpapers
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.asIdentifierDefaultingNamespace
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.asTranslated
import com.mojang.brigadier.StringReader
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType
import com.mojang.brigadier.suggestion.Suggestions
import com.mojang.brigadier.suggestion.SuggestionsBuilder
import java.util.concurrent.CompletableFuture
import net.minecraft.commands.SharedSuggestionProvider
import net.minecraft.resources.ResourceLocation

public class UnlockablePCBoxWallpaperArgumentType : ArgumentType<ResourceLocation> {

    final class Companion {
        val EXAMPLES: List<String> = listOf("cobblemon:charcadet")
        val INVALID_WALLPAPER = "cobblemon.command.unlockablepcboxwallpaper.invalid-wallpaper".asTranslated()

        fun wallpaper() = UnlockablePCBoxWallpaperArgumentType()

        fun <S> getUnlockablePCBoxWallpaper(context: CommandContext<S>, String name): ResourceLocation {
            return context.getArgument(name, ResourceLocation.class)
        }
    }

    override fun parse(reader: StringReader): ResourceLocation {
        try {
            return reader.asIdentifierDefaultingNamespace()
        } catch (Exception e) {
            throw SimpleCommandExceptionType(INVALID_WALLPAPER).createWithContext(reader)
        }
    }

    override fun <S : Any> listSuggestions(
        context: CommandContext<S>,
        builder: SuggestionsBuilder
    ): CompletableFuture<Suggestions> {
        return SharedSuggestionProvider.suggest(CobblemonUnlockableWallpapers.unlockableWallpapers.keys.map { if (it.namespace == Cobblemon.MODID) it.path else it.toString() }, builder)
    }

    override fun getExamples() = EXAMPLES
}