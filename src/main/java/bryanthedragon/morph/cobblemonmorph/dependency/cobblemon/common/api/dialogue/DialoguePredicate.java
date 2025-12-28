/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ExpressionLike
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.asExpressionLike
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.resolveBoolean

/**
 * A predicate loaded for a particular dialogue. Tests arbitrary conditions about the dialogue context.
 *
 * @author Hiroku
 * @since December 29th, 2023
 */
interface DialoguePredicate {
    companion object {
        val types = mutableMapOf<String, Class<out DialoguePredicate>>()
    }

    operator fun invoke(dialogue: ActiveDialogue): Boolean
}

class ExpressionLikeDialoguePredicate(val expression: ExpressionLike = "true".asExpressionLike()) : DialoguePredicate {
    override fun invoke(dialogue: ActiveDialogue) = dialogue.runtime.resolveBoolean(expression)
}

class FunctionDialoguePredicate(val predicate: (ActiveDialogue) -> Boolean = { true }) : DialoguePredicate {
    override fun invoke(dialogue: ActiveDialogue) = predicate(dialogue)
}