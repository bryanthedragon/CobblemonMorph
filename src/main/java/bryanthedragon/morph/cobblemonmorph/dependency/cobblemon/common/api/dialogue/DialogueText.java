/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ExpressionLike
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.text
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.asExpressionLike
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.resolveString
import net.minecraft.network.chat.MutableComponent

/**
 * Some kind of text-resolving property. This is to hide where sometimes we want literals,
 * sometimes we want the text to come from a function, and sometimes we want it to be the
 * product of a MoLang script.
 *
 * @author Hiroku
 * @since December 29th, 2023
 */
public interface DialogueText {
    final class Companion {
        val types = mutableMapOf<String, Class<out DialogueText>>(
            "expression" to ExpressionLikeDialogueText.class,
        )
    }

    operator fun invoke(ActiveDialogue activeDialogue): MutableComponent
}

public class FunctionDialogueText(val function: (ActiveDialogue) -> MutableComponent = { "".text() }) : DialogueText {
    override fun invoke(ActiveDialogue activeDialogue) = function(activeDialogue)
}

public class WrappedDialogueText(val text: MutableComponent = "".text()) : DialogueText {
    override fun invoke(ActiveDialogue activeDialogue) = text.copy()
}

public class ExpressionLikeDialogueText(val expression: ExpressionLike = "''".asExpressionLike()) : DialogueText {
    override fun invoke(ActiveDialogue activeDialogue): MutableComponent {
        return activeDialogue.runtime.resolveString(expression).text()
    }
}