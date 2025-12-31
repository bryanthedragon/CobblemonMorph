/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.molang

import com.bedrockk.molang.runtime.struct.VariableStruct
import java.util.UUID

public interface MoLangDataStoreFactory {
    fun markDirty(UUID uuid)
    fun load(UUID uuid, fileString path? = null) : VariableStruct
}