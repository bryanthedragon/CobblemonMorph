/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.JvmField
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.CancelableObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.EventObservable;

import kotlin.jvm.JvmField;

import org.jetbrains.annotations.NotNull;

public final class PlatformEvents {
    @NotNull
    public static final PlatformEvents INSTANCE = new PlatformEvents();

    @JvmField
    @NotNull
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static final EventObservable<ServerEvent.Starting> SERVER_STARTING = new EventObservable();

    @JvmField
    @NotNull
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static final EventObservable<ServerEvent.Started> SERVER_STARTED = new EventObservable();

    @JvmField
    @NotNull
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static final EventObservable<ServerEvent.Stopping> SERVER_STOPPING = new EventObservable();

    @JvmField
    @NotNull
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static final EventObservable<ServerEvent.Stopped> SERVER_STOPPED = new EventObservable();

    @JvmField
    @NotNull
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static final EventObservable<ServerTickEvent.Pre> SERVER_TICK_PRE = new EventObservable();

    @JvmField
    @NotNull
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static final EventObservable<ServerTickEvent.Post> SERVER_TICK_POST = new EventObservable();

    @JvmField
    @NotNull
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static final EventObservable<ClientTickEvent.Pre> CLIENT_TICK_PRE = new EventObservable();

    @JvmField
    @NotNull
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static final EventObservable<ClientTickEvent.Post> CLIENT_TICK_POST = new EventObservable();

    @JvmField
    @NotNull
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static final EventObservable<ServerPlayerEvent.Login> SERVER_PLAYER_LOGIN = new EventObservable();

    @JvmField
    @NotNull
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static final EventObservable<ServerPlayerEvent.Logout> SERVER_PLAYER_LOGOUT = new EventObservable();

    @JvmField
    @NotNull
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static final EventObservable<ClientPlayerEvent.Login> CLIENT_PLAYER_LOGIN = new EventObservable();

    @JvmField
    @NotNull
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static final EventObservable<ClientPlayerEvent.Logout> CLIENT_PLAYER_LOGOUT = new EventObservable();

    @JvmField
    @NotNull
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static final CancelableObservable<ServerPlayerEvent.Death> PLAYER_DEATH = new CancelableObservable();

    @JvmField
    @NotNull
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static final CancelableObservable<ServerPlayerEvent.RightClickBlock> RIGHT_CLICK_BLOCK = new CancelableObservable();

    @JvmField
    @NotNull
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static final CancelableObservable<ServerPlayerEvent.RightClickEntity> RIGHT_CLICK_ENTITY = new CancelableObservable();
    
    @JvmField
    @NotNull
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static final EventObservable<ChangeDimensionEvent> CHANGE_DIMENSION = new EventObservable();
    
    @JvmField
    @NotNull
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static final EventObservable<ItemTooltipEvent> CLIENT_ITEM_TOOLTIP = new EventObservable();

    private PlatformEvents() {
    }
}

