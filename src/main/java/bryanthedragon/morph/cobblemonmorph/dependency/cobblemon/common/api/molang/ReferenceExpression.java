/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang

import com.bedrockk.molang.runtime.MoLangRuntime
import com.bedrockk.molang.runtime.value.MoValue
import com.bedrockk.molang.runtime.value.StringValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scripting.CobblemonScripts
import net.minecraft.resources.ResourceLocation

/**
 * An [ExpressionLike] which is a reference to a datapacked MoLang script. Created when a string is
 * a valid resource location.
 *
 * @author Hiroku
 * @since August 9th, 2025
 */
public class ReferenceExpression(val ResourceLocation identifier) : ExpressionLike {
    override fun resolve(MoLangRuntime runtime, Map<String, MoValue> context): MoValue {
        return getScript()?.resolve(runtime, context) ?: StringValue(identifier.toString())
    }

    override fun toString(): String {
        return getScript()?.getString() ?: identifier.toString()
    }

    fun getScript() = CobblemonScripts.scripts[identifier]
}