/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.argument

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnBucket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.lang
import com.mojang.brigadier.StringReader
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType
import com.mojang.brigadier.suggestion.Suggestions
import com.mojang.brigadier.suggestion.SuggestionsBuilder
import net.minecraft.commands.SharedSuggestionProvider
import java.util.concurrent.CompletableFuture

public class SpawnBucketArgumentType: ArgumentType<SpawnBucket> {

    final class Companion {
        val EXAMPLES: List<String> = listOf(Cobblemon.bestSpawner.config.buckets.first().name)
        val INVALID_BUCKET = lang("command.checkspawns.invalid-bucket")

        fun spawnBucket() = SpawnBucketArgumentType()

        fun <S> getSpawnBucket(context: CommandContext<S>, String name): SpawnBucket {
            return context.getArgument(name, SpawnBucket.class)
        }
    }

    override fun parse(reader: StringReader): SpawnBucket {
        val name = reader.readString()
        return Cobblemon.bestSpawner.config.buckets.find { it.name.equals(name, ignoreCase = true) }
            ?: throw SimpleCommandExceptionType(INVALID_BUCKET).createWithContext(reader)
    }

    override fun <S : Any> listSuggestions(
        context: CommandContext<S>,
        builder: SuggestionsBuilder
    ): CompletableFuture<Suggestions> {
        return SharedSuggestionProvider.suggest(Cobblemon.bestSpawner.config.buckets.map { it.name }, builder)
    }

    override fun getExamples() = EXAMPLES
}