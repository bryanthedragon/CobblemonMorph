/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang;
import com.bedrockk.molang.runtime.MoLangRuntime;
import com.bedrockk.molang.runtime.value.MoValue;
import net.minecraft.resources.ResourceLocation;

/**
 * Used to fill into most MoLang sites with Kotlin code.
 *
 * @author Hiroku
 * @since August 23rd, 2025
 */
public class KotlinExpression(val ResourceLocation id, val ExpressionLike function (MoLangRuntime runtime, Map<String, MoValue> context) -> MoValue)
{
    override fun toString() = id.toString()
    override fun resolve(MoLangRuntime runtime, Map<String, MoValue> context) = function(runtime, context)
}