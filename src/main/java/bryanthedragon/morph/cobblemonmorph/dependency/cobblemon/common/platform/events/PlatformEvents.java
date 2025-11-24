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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.ChangeDimensionEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.ClientPlayerEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.ClientTickEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.ItemTooltipEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.ServerEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.ServerPlayerEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.ServerTickEvent;
import kotlin.Metadata;
import kotlin.jvm.JvmField;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000|\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b(\u0010)R\u001a\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00028\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u0004\u0010\u0005R\u001a\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\u00028\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u0007\u0010\u0005R\u001a\u0010\t\u001a\b\u0012\u0004\u0012\u00020\b0\u00028\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\t\u0010\u0005R\u001a\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\n0\u00028\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u000b\u0010\u0005R\u001a\u0010\r\u001a\b\u0012\u0004\u0012\u00020\f0\u00028\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\r\u0010\u0005R\u001a\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u000e0\u00028\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u000f\u0010\u0005R\u001a\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00110\u00108\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u0012\u0010\u0013R\u001a\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00140\u00108\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u0015\u0010\u0013R\u001a\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00160\u00108\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u0017\u0010\u0013R\u001a\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00180\u00028\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u0019\u0010\u0005R\u001a\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001a0\u00028\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u001b\u0010\u0005R\u001a\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001c0\u00028\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u001d\u0010\u0005R\u001a\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u001e0\u00028\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u001f\u0010\u0005R\u001a\u0010!\u001a\b\u0012\u0004\u0012\u00020 0\u00028\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b!\u0010\u0005R\u001a\u0010#\u001a\b\u0012\u0004\u0012\u00020\"0\u00028\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b#\u0010\u0005R\u001a\u0010%\u001a\b\u0012\u0004\u0012\u00020$0\u00028\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b%\u0010\u0005R\u001a\u0010'\u001a\b\u0012\u0004\u0012\u00020&0\u00028\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b'\u0010\u0005\u00a8\u0006*"}, d2={"Lcom/cobblemon/mod/common/platform/events/PlatformEvents;", "", "Lcom/cobblemon/mod/common/api/reactive/EventObservable;", "Lcom/cobblemon/mod/common/platform/events/ChangeDimensionEvent;", "CHANGE_DIMENSION", "Lcom/cobblemon/mod/common/api/reactive/EventObservable;", "Lcom/cobblemon/mod/common/platform/events/ItemTooltipEvent;", "CLIENT_ITEM_TOOLTIP", "Lcom/cobblemon/mod/common/platform/events/ClientPlayerEvent$Login;", "CLIENT_PLAYER_LOGIN", "Lcom/cobblemon/mod/common/platform/events/ClientPlayerEvent$Logout;", "CLIENT_PLAYER_LOGOUT", "Lcom/cobblemon/mod/common/platform/events/ClientTickEvent$Post;", "CLIENT_TICK_POST", "Lcom/cobblemon/mod/common/platform/events/ClientTickEvent$Pre;", "CLIENT_TICK_PRE", "Lcom/cobblemon/mod/common/api/reactive/CancelableObservable;", "Lcom/cobblemon/mod/common/platform/events/ServerPlayerEvent$Death;", "PLAYER_DEATH", "Lcom/cobblemon/mod/common/api/reactive/CancelableObservable;", "Lcom/cobblemon/mod/common/platform/events/ServerPlayerEvent$RightClickBlock;", "RIGHT_CLICK_BLOCK", "Lcom/cobblemon/mod/common/platform/events/ServerPlayerEvent$RightClickEntity;", "RIGHT_CLICK_ENTITY", "Lcom/cobblemon/mod/common/platform/events/ServerPlayerEvent$Login;", "SERVER_PLAYER_LOGIN", "Lcom/cobblemon/mod/common/platform/events/ServerPlayerEvent$Logout;", "SERVER_PLAYER_LOGOUT", "Lcom/cobblemon/mod/common/platform/events/ServerEvent$Started;", "SERVER_STARTED", "Lcom/cobblemon/mod/common/platform/events/ServerEvent$Starting;", "SERVER_STARTING", "Lcom/cobblemon/mod/common/platform/events/ServerEvent$Stopped;", "SERVER_STOPPED", "Lcom/cobblemon/mod/common/platform/events/ServerEvent$Stopping;", "SERVER_STOPPING", "Lcom/cobblemon/mod/common/platform/events/ServerTickEvent$Post;", "SERVER_TICK_POST", "Lcom/cobblemon/mod/common/platform/events/ServerTickEvent$Pre;", "SERVER_TICK_PRE", "<init>", "()V", "common"})
public final class PlatformEvents {
    @NotNull
    public static final PlatformEvents INSTANCE = new PlatformEvents();
    @JvmField
    @NotNull
    public static final EventObservable<ServerEvent.Starting> SERVER_STARTING = new EventObservable();
    @JvmField
    @NotNull
    public static final EventObservable<ServerEvent.Started> SERVER_STARTED = new EventObservable();
    @JvmField
    @NotNull
    public static final EventObservable<ServerEvent.Stopping> SERVER_STOPPING = new EventObservable();
    @JvmField
    @NotNull
    public static final EventObservable<ServerEvent.Stopped> SERVER_STOPPED = new EventObservable();
    @JvmField
    @NotNull
    public static final EventObservable<ServerTickEvent.Pre> SERVER_TICK_PRE = new EventObservable();
    @JvmField
    @NotNull
    public static final EventObservable<ServerTickEvent.Post> SERVER_TICK_POST = new EventObservable();
    @JvmField
    @NotNull
    public static final EventObservable<ClientTickEvent.Pre> CLIENT_TICK_PRE = new EventObservable();
    @JvmField
    @NotNull
    public static final EventObservable<ClientTickEvent.Post> CLIENT_TICK_POST = new EventObservable();
    @JvmField
    @NotNull
    public static final EventObservable<ServerPlayerEvent.Login> SERVER_PLAYER_LOGIN = new EventObservable();
    @JvmField
    @NotNull
    public static final EventObservable<ServerPlayerEvent.Logout> SERVER_PLAYER_LOGOUT = new EventObservable();
    @JvmField
    @NotNull
    public static final EventObservable<ClientPlayerEvent.Login> CLIENT_PLAYER_LOGIN = new EventObservable();
    @JvmField
    @NotNull
    public static final EventObservable<ClientPlayerEvent.Logout> CLIENT_PLAYER_LOGOUT = new EventObservable();
    @JvmField
    @NotNull
    public static final CancelableObservable<ServerPlayerEvent.Death> PLAYER_DEATH = new CancelableObservable();
    @JvmField
    @NotNull
    public static final CancelableObservable<ServerPlayerEvent.RightClickBlock> RIGHT_CLICK_BLOCK = new CancelableObservable();
    @JvmField
    @NotNull
    public static final CancelableObservable<ServerPlayerEvent.RightClickEntity> RIGHT_CLICK_ENTITY = new CancelableObservable();
    @JvmField
    @NotNull
    public static final EventObservable<ChangeDimensionEvent> CHANGE_DIMENSION = new EventObservable();
    @JvmField
    @NotNull
    public static final EventObservable<ItemTooltipEvent> CLIENT_ITEM_TOOLTIP = new EventObservable();

    private PlatformEvents() {
    }
}

