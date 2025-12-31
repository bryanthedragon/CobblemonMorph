/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api;

import com.mojang.serialization.Codec;

import net.minecraft.util.StringRepresentable;

public enum Priority implements StringRepresentable {
    HIGHEST,
    HIGH,
    NORMAL,
    LOW,
    LOWEST;

    @Override
    public String getSerializedName() {
        return this.name();
    }


    final class Companion {
        public static final Codec<Priority> CODEC = StringRepresentable.fromEnum(Priority::values);
    }

}