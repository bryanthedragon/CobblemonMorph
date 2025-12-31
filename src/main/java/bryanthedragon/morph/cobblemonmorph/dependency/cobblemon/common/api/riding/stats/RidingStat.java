/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.riding.stats;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.cooking.Flavour;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.utill;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

/**
 * A stat that is used for riding Pokémon. The exact implementation of each of these depends on the [bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.riding.controller.RideController].
 *
 * @author Hiroku
 * @since February 17th, 2025
 */
public enum RidingStat {
    ACCELERATION(Flavour.SPICY),
    SKILL(Flavour.DRY),
    SPEED(Flavour.SWEET),
    STAMINA(Flavour.SOUR),
    JUMP(Flavour.BITTER);

    private final Flavour flavour;

    RidingStat(Flavour flavour) {
        this.flavour = flavour;
    }

    public Flavour getFlavour() {
        return flavour;
    }

    final class Companion {
        Companion getByName( String name) = RidingStat.entries.firstOrNull { it.name.equals(name, ignoreCase = true) }
        public static RidingStat getByFlavour(Flavour flavour) {
            for (RidingStat stat : RidingStat.values()) {
                if (stat.getFlavour() == flavour) { return stat; }
            }
    return null;
}

    }

    public final MutableComponent displayName()
    {
        return lang("ui.stats.ride.${name.lowercase()}");
    }
}