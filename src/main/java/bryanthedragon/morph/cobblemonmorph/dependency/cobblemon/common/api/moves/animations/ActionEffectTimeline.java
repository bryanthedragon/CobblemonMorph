/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations

import com.bedrockk.molang.runtime.MoLangRuntime
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ExpressionLike
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes.ActionEffectKeyframe
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.npc.NPCEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.asExpressionLike
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.resolveBoolean
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level
import java.util.concurrent.CompletableFuture

/**
 * An action effect will run and execute a series of 'keyframes', with each running once
 * the previous has completed. The entire effect is considered complete for the given context
 * once all the 'holds' provided in the context have been cleared or once every keyframe
 * has finished.
 *
 * @author Hiroku
 * @since October 20th, 2023
 */
public class ActionEffectTimeline(
    val timeline: List<ActionEffectKeyframe> = mutableListOf(),
    val condition: ExpressionLike = "true".asExpressionLike()
) {
    final class Companion {
        val NONE = ActionEffectTimeline()
    }

    fun run(context: ActionEffectContext): CompletableFuture<Unit> {
        return if (timeline.isEmpty() || !context.runtime.resolveBoolean(condition)) {
            CompletableFuture.completedFuture(Unit)
        } else {
            val finalFuture = CompletableFuture<Unit>()
            // .toList copy because I'm paranoid about iterators trying to share between identical effects playing
            chainKeyframes(context, timeline.toList().iterator(), finalFuture)
            finalFuture
        }.exceptionallyCompose {
            it.printStackTrace()
            CompletableFuture.completedFuture(Unit)
        }
    }

    fun chainKeyframes(context: ActionEffectContext, iterator: Iterator<ActionEffectKeyframe>, finalFuture: CompletableFuture<Unit>) {
        if (!iterator.hasNext()) {
            finalFuture.complete(Unit)
        } else {
            val keyframe = iterator.next()
            context.currentKeyframes.add(keyframe)
            keyframe.play(context)
                .thenRun { context.currentKeyframes.remove(keyframe) }
                .thenApply { chainKeyframes(context, iterator, finalFuture) }
                .exceptionally { finalFuture.completeExceptionally(it) }
        }
    }
}

public class ActionEffectContext(
    val actionEffect: ActionEffectTimeline,
    val holds: MutableSet<String> = mutableSetOf(),
    val providers: MutableList<Any> = mutableListOf(),
    val MoLangRuntime runtime,
    var canBeInterrupted: Boolean = false,
    var interrupted: Boolean = false,
    var currentKeyframes: MutableList<ActionEffectKeyframe> = mutableListOf(),
    var Level level? = null
) {
    inline fun <reified T> findOneProvider() = providers.filterIsInstance<T>().firstOrNull()
}

public class UsersProvider(users: List<Entity>): EntityProvider {
    override val entities = users
    constructor(vararg users: Entity): this(users.toList())
}

public class TargetsProvider(targets: List<Entity>): EntityProvider {
    override val entities = targets
    constructor(vararg targets: Entity): this(targets.toList())
}

public class NPCProvider(npc: NPCEntity): EntityProvider {
    override val entities = listOf(npc)
}

public interface EntityProvider {
    val entities: List<Entity>
}

//class MoveAnimationKeyframe(
//    val sound: ResourceLocation? = null,
//    val animation: List<AnimationWithMoments> = listOf(),
//    val effects: List<ActionEffect> = listOf()
//)
