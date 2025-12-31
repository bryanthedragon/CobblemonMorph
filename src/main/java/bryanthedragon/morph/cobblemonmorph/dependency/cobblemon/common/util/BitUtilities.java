/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math.pow;

import kotlin.experimental.and;
import kotlin.experimental.or;
public class BitUtilities {
    public Byte setBitForByte(Byte byte, Int bit, Boolean on) {
        val bitAsByte = 2 pow (bit - 1);
        return if (on) {
            byte or bitAsByte.toByte();
        } 
        else {
            byte and (-bitAsByte - 1).toByte();
        }
    }

    public Boolean getBitForByte(Byte byte, Int bit) {
        val bitAsByte = 2 pow (bit - 1);
        return (byte and bitAsByte.toByte()) != 0.toByte();
    }
}