/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ExpressionLike
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import java.util.UUID
import net.minecraft.resources.ResourceLocation

/**
 * Something that produces a dialogue's renderable face. This is sealed because the client has very particular handling for this.
 *
 * @author Hiroku
 * @since January 1st, 2024
 */
sealed interface DialogueFaceProvider {
    val isLeftSide: Boolean

    final class Companion {
        val types = mutableMapOf(
            "artificial" to ArtificialDialogueFaceProvider.class,
            "player" to PlayerDialogueFaceProvider.class,
            "standard" to ArtificialDialogueFaceProvider.class,
            "expression" to ExpressionLikeDialogueFaceProvider.class
        )
    }
}

public class ArtificialDialogueFaceProvider(
    val modelType: String = "",
    val ResourceLocation identifier = cobblemonResource("bulbasaur"),
    val aspects: Set<String> = setOf(),
    override val isLeftSide: Boolean = true
) : DialogueFaceProvider

public class ReferenceDialogueFaceProvider(
    val entityId: Int,
    override val isLeftSide: Boolean = true
): DialogueFaceProvider

/**
 * A face provider that uses the player's skin as the face, as long as there is a player with this UUID online.
 *
 * What's interesting is that this works for fake players, which is what Taterzens' NPCs use.
 */
public class PlayerDialogueFaceProvider(val playerId: UUID = UUID.randomUUID(), override val isLeftSide: Boolean = true) : DialogueFaceProvider

public class ExpressionLikeDialogueFaceProvider(
    val providerExpression: ExpressionLike
): DialogueFaceProvider {
    override val isLeftSide: Boolean = false // Doesn't get used
}