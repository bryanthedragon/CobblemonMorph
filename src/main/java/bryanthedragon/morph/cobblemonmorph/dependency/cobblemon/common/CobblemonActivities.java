/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common;

import net.minecraft.world.entity.schedule.Activity;

import java.util.ArrayList;
import java.util.List;

public class CobblemonActivities {
  public static final List<Activity> activities = new ArrayList<>();

    public static final Activity BATTLING =
            register(new Activity("battling"));

    public static final Activity ACTION_EFFECT =
            register(new Activity("action_effect"));

    public static final Activity NPC_CHATTING =
            register(new Activity("npc_chatting"));

    public static final Activity POKEMON_SLEEPING_ACTIVITY =
            register(new Activity("pokemon_sleeping"));

    public static final Activity POKEMON_GROW_CROP =
            register(new Activity("pokemon_grow_crop"));

    public static final Activity POKEMON_SLEEP_ON_TRAINER_BED =
            register(new Activity("pokemon_sleep_on_trainer_bed"));

    public static final Activity POKEMON_HERD =
            register(new Activity("pokemon_herd"));

    public static final Activity POKEMON_POLLINATION =
            register(new Activity("pokemon_pollination"));

    private CobblemonActivities() {
        // prevent instantiation
    }

    public static Activity register(Activity activity) {
        activities.add(activity);
        return activity;
    }
}