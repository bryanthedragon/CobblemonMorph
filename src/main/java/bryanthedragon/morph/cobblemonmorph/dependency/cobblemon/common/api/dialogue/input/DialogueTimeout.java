/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.input

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.DialogueAction
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.FunctionDialogueAction

/**
 * A deadline for dialogue input. This is used to have something occur if the player spends
 * too long on one page of dialogue.
 *
 * @author Hiroku
 * @since December 27th, 2023
 */
public class DialogueTimeout(
    var duration: Float = 10F,
    var showTimer: Boolean = true,
    var action: DialogueAction = FunctionDialogueAction { dialogue, _ -> dialogue.close() }
)