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

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.asExpressionLike;

import com.mojang.serialization.Codec;

/**
 * An object that can be given a [MoLangRuntime] to produce a single [MoValue]. This abstracts
 * the use of simple and complex [com.bedrockk.molang.Expression]s as MoLang can be a single line or multiple or
 * a script reference etc.
 *
 * @author Hiroku
 * @since October 22nd, 2023
 */
public interface ExpressionLike {
    public String toString();

    /** Produces a [MoValue] for a [MoLangRuntime] to supply an environment. */
    public MoValue resolve(MoLangRuntime runtime, Map<String, MoValue> context = runtime.environment.context?.map ?: hashMapOf());
    String getString() = toString();

    fun resolveDouble(MoLangRuntime runtime) = resolve(runtime).asDouble();
    fun resolveFloat(MoLangRuntime runtime) = resolveDouble(runtime).toFloat();
    fun resolveString(MoLangRuntime runtime) = resolve(runtime).asString();
    fun resolveInt(MoLangRuntime runtime) = resolveDouble(runtime).toInt();
    fun resolveBoolean(MoLangRuntime runtime) = resolveDouble(runtime) == 1.0;
    fun resolveObject(MoLangRuntime runtime) = resolve(runtime) as ObjectValue<*>;

    final class Companion {
        public final Codec<ExpressionLike> CODEC = Codec.STRING.xmap({it.asExpressionLike();}) { it.getString(); }
    }
}
