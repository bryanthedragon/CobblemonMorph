/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch

/**
 * A dispatch that holds the battle until the condition is met.
 *
 * @author Hiroku
 * @since July 31st, 2022
 */
public class UntilDispatch(val condition: () -> Boolean) : DispatchResult {
    var afterAction: (() -> Unit) = {}
    override fun canProceed(): Boolean {
        val shouldContinue = condition()
        if (shouldContinue) {
            afterAction()
        }
        return shouldContinue
    }

    fun andThen(action: () -> Unit): UntilDispatch {
        val old = afterAction
        afterAction = {
            old()
            action()
        }
        return this
    }
}