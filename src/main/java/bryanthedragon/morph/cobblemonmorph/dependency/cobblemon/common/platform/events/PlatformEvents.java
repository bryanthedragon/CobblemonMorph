/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.CancelableObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.EventObservable;

/**
 * A class for our converted events from platform specific to a trigger on common.
 * If you're using this class as API it is recommended that you use the platform specific events instead.
 *
 * @author Licious
 * @since February 13th, 20230
 */
public final class PlatformEvents {
    val EventObservable<ServerEvent.Starting> SERVER_STARTING;
    val EventObservable<ServerEvent.Started> SERVER_STARTED;
    val EventObservable<ServerEvent.Stopping> SERVER_STOPPING;
    val EventObservable<ServerEvent.Stopped> SERVER_STOPPED;

    val EventObservable<ServerPlayerTickEvent.Pre> SERVER_PLAYER_TICK_PRE;
    val EventObservable<ServerPlayerTickEvent.Post> SERVER_PLAYER_TICK_POST;

    val EventObservable<ServerPlayerEvent.AdvancementEarned> SERVER_PLAYER_ADVANCEMENT_EARNED;

    val EventObservable<ServerTickEvent.Pre> SERVER_TICK_PRE;
    val EventObservable<ServerTickEvent.Post> SERVER_TICK_POST;

    val EventObservable<ClientTickEvent.Pre> CLIENT_TICK_PRE ;
    val EventObservable<ClientTickEvent.Post> CLIENT_TICK_POST;

    val EventObservable<ServerPlayerEvent.Login> SERVER_PLAYER_LOGIN;
    val EventObservable<ServerPlayerEvent.Logout> SERVER_PLAYER_LOGOUT;
    val EventObservable<ClientPlayerEvent.Login> CLIENT_PLAYER_LOGIN;
    val EventObservable<ClientPlayerEvent.Logout> CLIENT_PLAYER_LOGOUT;
    val CancelableObservable<ServerPlayerEvent.Death> PLAYER_DEATH;
    val CancelableObservable<ServerPlayerEvent.RightClickBlock> RIGHT_CLICK_BLOCK;
    val CancelableObservable<ServerPlayerEvent.RightClickEntity> RIGHT_CLICK_ENTITY;

    val EventObservable<ChangeDimensionEvent> CHANGE_DIMENSION;

    val EventObservable<ItemTooltipEvent> CLIENT_ITEM_TOOLTIP;

    val EventObservable<ClientEntityEvent.Load> CLIENT_ENTITY_LOAD;
    val EventObservable<ClientEntityEvent.Unload> CLIENT_ENTITY_UNLOAD;

    val RENDER = EventObservable<RenderEvent>;
}
