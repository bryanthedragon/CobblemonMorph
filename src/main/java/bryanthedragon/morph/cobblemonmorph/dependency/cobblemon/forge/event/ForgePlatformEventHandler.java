/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.event.TickEvent$Phase
 *  net.minecraftforge.event.TickEvent$ServerTickEvent
 *  net.minecraftforge.event.entity.living.LivingDeathEvent
 *  net.minecraftforge.event.entity.player.PlayerEvent$PlayerChangedDimensionEvent
 *  net.minecraftforge.event.entity.player.PlayerEvent$PlayerLoggedInEvent
 *  net.minecraftforge.event.entity.player.PlayerEvent$PlayerLoggedOutEvent
 *  net.minecraftforge.event.entity.player.PlayerInteractEvent$EntityInteract
 *  net.minecraftforge.event.entity.player.PlayerInteractEvent$RightClickBlock
 *  net.minecraftforge.event.server.ServerAboutToStartEvent
 *  net.minecraftforge.event.server.ServerStartedEvent
 *  net.minecraftforge.event.server.ServerStoppedEvent
 *  net.minecraftforge.event.server.ServerStoppingEvent
 *  net.minecraftforge.eventbus.api.SubscribeEvent
 *  net.minecraftforge.server.ServerLifecycleHooks
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.mod.forge.event;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.Cancelable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.CancelableObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.EventObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.ChangeDimensionEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.PlatformEvents;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.ServerEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.ServerPlayerEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.ServerTickEvent;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.event.server.ServerStoppedEvent;
import net.minecraftforge.event.server.ServerStoppingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.server.ServerLifecycleHooks;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b'\u0010\u001aJ\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0007\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u0017\u0010\b\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0007H\u0007\u00a2\u0006\u0004\b\b\u0010\tJ\u0017\u0010\u000b\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\nH\u0007\u00a2\u0006\u0004\b\u000b\u0010\fJ\u0017\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\rH\u0007\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u0017\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0010H\u0007\u00a2\u0006\u0004\b\u0011\u0010\u0012J\u0017\u0010\u0014\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0013H\u0007\u00a2\u0006\u0004\b\u0014\u0010\u0015J\u0017\u0010\u0017\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0016H\u0007\u00a2\u0006\u0004\b\u0017\u0010\u0018J\r\u0010\u0019\u001a\u00020\u0004\u00a2\u0006\u0004\b\u0019\u0010\u001aJ\u0017\u0010\u001c\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u001bH\u0007\u00a2\u0006\u0004\b\u001c\u0010\u001dJ\u0017\u0010\u001f\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u001eH\u0007\u00a2\u0006\u0004\b\u001f\u0010 J\u0017\u0010\"\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020!H\u0007\u00a2\u0006\u0004\b\"\u0010#J\u0017\u0010%\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020$H\u0007\u00a2\u0006\u0004\b%\u0010&\u00a8\u0006("}, d2={"Lcom/cobblemon/mod/forge/event/ForgePlatformEventHandler;", "", "Lnet/minecraftforge/event/entity/player/PlayerEvent$PlayerChangedDimensionEvent;", "e", "", "onChangeDimension", "(Lnet/minecraftforge/event/entity/player/PlayerEvent$PlayerChangedDimensionEvent;)V", "Lnet/minecraftforge/event/entity/living/LivingDeathEvent;", "onDeath", "(Lnet/minecraftforge/event/entity/living/LivingDeathEvent;)V", "Lnet/minecraftforge/event/entity/player/PlayerEvent$PlayerLoggedInEvent;", "onLogin", "(Lnet/minecraftforge/event/entity/player/PlayerEvent$PlayerLoggedInEvent;)V", "Lnet/minecraftforge/event/entity/player/PlayerEvent$PlayerLoggedOutEvent;", "onLogout", "(Lnet/minecraftforge/event/entity/player/PlayerEvent$PlayerLoggedOutEvent;)V", "Lnet/minecraftforge/event/entity/player/PlayerInteractEvent$RightClickBlock;", "onRightClickBlock", "(Lnet/minecraftforge/event/entity/player/PlayerInteractEvent$RightClickBlock;)V", "Lnet/minecraftforge/event/entity/player/PlayerInteractEvent$EntityInteract;", "onRightClickEntity", "(Lnet/minecraftforge/event/entity/player/PlayerInteractEvent$EntityInteract;)V", "Lnet/minecraftforge/event/TickEvent$ServerTickEvent;", "onTick", "(Lnet/minecraftforge/event/TickEvent$ServerTickEvent;)V", "register", "()V", "Lnet/minecraftforge/event/server/ServerStartedEvent;", "serverStarted", "(Lnet/minecraftforge/event/server/ServerStartedEvent;)V", "Lnet/minecraftforge/event/server/ServerAboutToStartEvent;", "serverStarting", "(Lnet/minecraftforge/event/server/ServerAboutToStartEvent;)V", "Lnet/minecraftforge/event/server/ServerStoppedEvent;", "serverStopped", "(Lnet/minecraftforge/event/server/ServerStoppedEvent;)V", "Lnet/minecraftforge/event/server/ServerStoppingEvent;", "serverStopping", "(Lnet/minecraftforge/event/server/ServerStoppingEvent;)V", "<init>", "forge"})
@SourceDebugExtension(value={"SMAP\nForgePlatformEventHandler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ForgePlatformEventHandler.kt\ncom/cobblemon/mod/forge/event/ForgePlatformEventHandler\n+ 2 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/EventObservable\n+ 3 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 4 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/EventObservable$post$1\n+ 5 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/CancelableObservable\n*L\n1#1,123:1\n14#2,5:124\n19#2:132\n14#2,5:133\n19#2:141\n14#2,5:142\n19#2:150\n14#2,5:151\n19#2:159\n14#2,5:160\n19#2:168\n14#2,5:169\n19#2:177\n14#2,5:178\n19#2:186\n14#2,5:187\n19#2:195\n17#2,2:197\n19#2:207\n17#2,2:210\n19#2:220\n17#2,2:223\n19#2:233\n14#2,5:235\n19#2:243\n13579#3:129\n13580#3:131\n13579#3:138\n13580#3:140\n13579#3:147\n13580#3:149\n13579#3:156\n13580#3:158\n13579#3:165\n13580#3:167\n13579#3:174\n13580#3:176\n13579#3:183\n13580#3:185\n13579#3:192\n13580#3:194\n13579#3:199\n13580#3:206\n13579#3:212\n13580#3:219\n13579#3:225\n13580#3:232\n13579#3:240\n13580#3:242\n14#4:130\n14#4:139\n14#4:148\n14#4:157\n14#4:166\n14#4:175\n14#4:184\n14#4:193\n14#4:241\n40#5:196\n41#5,6:200\n47#5:208\n40#5:209\n41#5,6:213\n47#5:221\n40#5:222\n41#5,6:226\n47#5:234\n*S KotlinDebug\n*F\n+ 1 ForgePlatformEventHandler.kt\ncom/cobblemon/mod/forge/event/ForgePlatformEventHandler\n*L\n39#1:124,5\n39#1:132\n44#1:133,5\n44#1:141\n50#1:142,5\n50#1:150\n55#1:151,5\n55#1:159\n61#1:160,5\n61#1:168\n64#1:169,5\n64#1:177\n71#1:178,5\n71#1:186\n77#1:187,5\n77#1:195\n83#1:197,2\n83#1:207\n96#1:210,2\n96#1:220\n109#1:223,2\n109#1:233\n120#1:235,5\n120#1:243\n39#1:129\n39#1:131\n44#1:138\n44#1:140\n50#1:147\n50#1:149\n55#1:156\n55#1:158\n61#1:165\n61#1:167\n64#1:174\n64#1:176\n71#1:183\n71#1:185\n77#1:192\n77#1:194\n83#1:199\n83#1:206\n96#1:212\n96#1:219\n109#1:225\n109#1:232\n120#1:240\n120#1:242\n39#1:130\n44#1:139\n50#1:148\n55#1:157\n61#1:166\n64#1:175\n71#1:184\n77#1:193\n120#1:241\n83#1:196\n83#1:200,6\n83#1:208\n96#1:209\n96#1:213,6\n96#1:221\n109#1:222\n109#1:226,6\n109#1:234\n*E\n"})
public final class ForgePlatformEventHandler {
    @NotNull
    public static final ForgePlatformEventHandler INSTANCE = new ForgePlatformEventHandler();

    private ForgePlatformEventHandler() {
    }

    public final void register() {
        MinecraftForge.EVENT_BUS.register((Object)this);
    }

    /*
     * WARNING - void declaration
     */
    @SubscribeEvent
    public final void serverStarting(@NotNull ServerAboutToStartEvent e) {
        void events$iv;
        void $this$iv;
        Intrinsics.checkNotNullParameter((Object)e, (String)"e");
        EventObservable<ServerEvent.Starting> eventObservable = PlatformEvents.SERVER_STARTING;
        ServerEvent.Starting[] startingArray = new ServerEvent.Starting[1];
        MinecraftServer minecraftServer = e.getServer();
        Intrinsics.checkNotNullExpressionValue((Object)minecraftServer, (String)"e.server");
        startingArray[0] = new ServerEvent.Starting(minecraftServer);
        boolean $i$f$post = false;
        $this$iv.emit(Arrays.copyOf(events$iv, ((void)events$iv).length));
        void $this$forEach$iv$iv = events$iv;
        boolean $i$f$forEach = false;
        int n = ((void)$this$forEach$iv$iv).length;
        for (int i = 0; i < n; ++i) {
            void element$iv$iv;
            void var10_10 = element$iv$iv = $this$forEach$iv$iv[i];
            boolean bl = false;
            void it = var10_10;
        }
    }

    /*
     * WARNING - void declaration
     */
    @SubscribeEvent
    public final void serverStarted(@NotNull ServerStartedEvent e) {
        void events$iv;
        void $this$iv;
        Intrinsics.checkNotNullParameter((Object)e, (String)"e");
        EventObservable<ServerEvent.Started> eventObservable = PlatformEvents.SERVER_STARTED;
        ServerEvent.Started[] startedArray = new ServerEvent.Started[1];
        MinecraftServer minecraftServer = e.getServer();
        Intrinsics.checkNotNullExpressionValue((Object)minecraftServer, (String)"e.server");
        startedArray[0] = new ServerEvent.Started(minecraftServer);
        boolean $i$f$post = false;
        $this$iv.emit(Arrays.copyOf(events$iv, ((void)events$iv).length));
        void $this$forEach$iv$iv = events$iv;
        boolean $i$f$forEach = false;
        int n = ((void)$this$forEach$iv$iv).length;
        for (int i = 0; i < n; ++i) {
            void element$iv$iv;
            void var10_10 = element$iv$iv = $this$forEach$iv$iv[i];
            boolean bl = false;
            void it = var10_10;
        }
    }

    /*
     * WARNING - void declaration
     */
    @SubscribeEvent
    public final void serverStopping(@NotNull ServerStoppingEvent e) {
        void events$iv;
        void $this$iv;
        Intrinsics.checkNotNullParameter((Object)e, (String)"e");
        EventObservable<ServerEvent.Stopping> eventObservable = PlatformEvents.SERVER_STOPPING;
        ServerEvent.Stopping[] stoppingArray = new ServerEvent.Stopping[1];
        MinecraftServer minecraftServer = e.getServer();
        Intrinsics.checkNotNullExpressionValue((Object)minecraftServer, (String)"e.server");
        stoppingArray[0] = new ServerEvent.Stopping(minecraftServer);
        boolean $i$f$post = false;
        $this$iv.emit(Arrays.copyOf(events$iv, ((void)events$iv).length));
        void $this$forEach$iv$iv = events$iv;
        boolean $i$f$forEach = false;
        int n = ((void)$this$forEach$iv$iv).length;
        for (int i = 0; i < n; ++i) {
            void element$iv$iv;
            void var10_10 = element$iv$iv = $this$forEach$iv$iv[i];
            boolean bl = false;
            void it = var10_10;
        }
    }

    /*
     * WARNING - void declaration
     */
    @SubscribeEvent
    public final void serverStopped(@NotNull ServerStoppedEvent e) {
        void events$iv;
        void $this$iv;
        Intrinsics.checkNotNullParameter((Object)e, (String)"e");
        EventObservable<ServerEvent.Stopped> eventObservable = PlatformEvents.SERVER_STOPPED;
        ServerEvent.Stopped[] stoppedArray = new ServerEvent.Stopped[1];
        MinecraftServer minecraftServer = e.getServer();
        Intrinsics.checkNotNullExpressionValue((Object)minecraftServer, (String)"e.server");
        stoppedArray[0] = new ServerEvent.Stopped(minecraftServer);
        boolean $i$f$post = false;
        $this$iv.emit(Arrays.copyOf(events$iv, ((void)events$iv).length));
        void $this$forEach$iv$iv = events$iv;
        boolean $i$f$forEach = false;
        int n = ((void)$this$forEach$iv$iv).length;
        for (int i = 0; i < n; ++i) {
            void element$iv$iv;
            void var10_10 = element$iv$iv = $this$forEach$iv$iv[i];
            boolean bl = false;
            void it = var10_10;
        }
    }

    /*
     * WARNING - void declaration
     */
    @SubscribeEvent
    public final void onTick(@NotNull TickEvent.ServerTickEvent e) {
        Intrinsics.checkNotNullParameter((Object)e, (String)"e");
        if (e.phase == TickEvent.Phase.START) {
            void events$iv;
            void $this$iv;
            EventObservable<ServerTickEvent.Pre> eventObservable = PlatformEvents.SERVER_TICK_PRE;
            ServerTickEvent.Pre[] preArray = new ServerTickEvent.Pre[1];
            MinecraftServer minecraftServer = ServerLifecycleHooks.getCurrentServer();
            Intrinsics.checkNotNullExpressionValue((Object)minecraftServer, (String)"getCurrentServer()");
            preArray[0] = new ServerTickEvent.Pre(minecraftServer);
            boolean $i$f$post = false;
            $this$iv.emit(Arrays.copyOf(events$iv, ((void)events$iv).length));
            void $this$forEach$iv$iv = events$iv;
            boolean $i$f$forEach = false;
            int n = ((void)$this$forEach$iv$iv).length;
            for (int i = 0; i < n; ++i) {
                void element$iv$iv;
                void var10_18 = element$iv$iv = $this$forEach$iv$iv[i];
                boolean bl = false;
                void it = var10_18;
            }
        } else {
            EventObservable<ServerTickEvent.Post> $this$iv = PlatformEvents.SERVER_TICK_POST;
            ServerTickEvent.Post[] events$iv = new ServerTickEvent.Post[1];
            MinecraftServer minecraftServer = ServerLifecycleHooks.getCurrentServer();
            Intrinsics.checkNotNullExpressionValue((Object)minecraftServer, (String)"getCurrentServer()");
            events$iv[0] = new ServerTickEvent.Post(minecraftServer);
            boolean $i$f$post = false;
            $this$iv.emit((ServerTickEvent.Post[])Arrays.copyOf(events$iv, events$iv.length));
            ServerTickEvent.Post[] $this$forEach$iv$iv = events$iv;
            boolean $i$f$forEach = false;
            int n = $this$forEach$iv$iv.length;
            for (int i = 0; i < n; ++i) {
                ServerTickEvent.Post element$iv$iv;
                ServerTickEvent.Post it = element$iv$iv = $this$forEach$iv$iv[i];
                boolean bl = false;
            }
        }
    }

    /*
     * WARNING - void declaration
     */
    @SubscribeEvent
    public final void onLogin(@NotNull PlayerEvent.PlayerLoggedInEvent e) {
        void $this$iv;
        Intrinsics.checkNotNullParameter((Object)e, (String)"e");
        ServerPlayerEvent.Login[] loginArray = e.getEntity();
        ServerPlayer serverPlayer = loginArray instanceof ServerPlayer ? (ServerPlayer)loginArray : null;
        if (serverPlayer == null) {
            return;
        }
        ServerPlayer player = serverPlayer;
        EventObservable<ServerPlayerEvent.Login> eventObservable = PlatformEvents.SERVER_PLAYER_LOGIN;
        loginArray = new ServerPlayerEvent.Login[]{new ServerPlayerEvent.Login(player)};
        ServerPlayerEvent.Login[] events$iv = loginArray;
        boolean $i$f$post = false;
        $this$iv.emit(Arrays.copyOf(events$iv, events$iv.length));
        ServerPlayerEvent.Login[] $this$forEach$iv$iv = events$iv;
        boolean $i$f$forEach = false;
        int n = $this$forEach$iv$iv.length;
        for (int i = 0; i < n; ++i) {
            ServerPlayerEvent.Login element$iv$iv;
            ServerPlayerEvent.Login login = element$iv$iv = $this$forEach$iv$iv[i];
            boolean bl = false;
            ServerPlayerEvent.Login it = login;
        }
    }

    /*
     * WARNING - void declaration
     */
    @SubscribeEvent
    public final void onLogout(@NotNull PlayerEvent.PlayerLoggedOutEvent e) {
        void $this$iv;
        Intrinsics.checkNotNullParameter((Object)e, (String)"e");
        ServerPlayerEvent.Logout[] logoutArray = e.getEntity();
        ServerPlayer serverPlayer = logoutArray instanceof ServerPlayer ? (ServerPlayer)logoutArray : null;
        if (serverPlayer == null) {
            return;
        }
        ServerPlayer player = serverPlayer;
        EventObservable<ServerPlayerEvent.Logout> eventObservable = PlatformEvents.SERVER_PLAYER_LOGOUT;
        logoutArray = new ServerPlayerEvent.Logout[]{new ServerPlayerEvent.Logout(player)};
        ServerPlayerEvent.Logout[] events$iv = logoutArray;
        boolean $i$f$post = false;
        $this$iv.emit(Arrays.copyOf(events$iv, events$iv.length));
        ServerPlayerEvent.Logout[] $this$forEach$iv$iv = events$iv;
        boolean $i$f$forEach = false;
        int n = $this$forEach$iv$iv.length;
        for (int i = 0; i < n; ++i) {
            ServerPlayerEvent.Logout element$iv$iv;
            ServerPlayerEvent.Logout logout = element$iv$iv = $this$forEach$iv$iv[i];
            boolean bl = false;
            ServerPlayerEvent.Logout it = logout;
        }
    }

    /*
     * WARNING - void declaration
     */
    @SubscribeEvent
    public final void onDeath(@NotNull LivingDeathEvent e) {
        void this_$iv$iv;
        Intrinsics.checkNotNullParameter((Object)e, (String)"e");
        Object object = e.getEntity();
        ServerPlayer serverPlayer = object instanceof ServerPlayer ? (ServerPlayer)object : null;
        if (serverPlayer == null) {
            return;
        }
        ServerPlayer player = serverPlayer;
        CancelableObservable<ServerPlayerEvent.Death> cancelableObservable = PlatformEvents.PLAYER_DEATH;
        object = new ServerPlayerEvent.Death(player);
        CancelableObservable<ServerPlayerEvent.Death> this_$iv = cancelableObservable;
        boolean $i$f$postThen = false;
        EventObservable eventObservable = this_$iv;
        Cancelable[] cancelableArray = new Cancelable[]{object};
        Cancelable[] events$iv$iv = cancelableArray;
        boolean $i$f$post = false;
        this_$iv$iv.emit(Arrays.copyOf(events$iv$iv, events$iv$iv.length));
        Cancelable[] $this$forEach$iv$iv$iv = events$iv$iv;
        boolean $i$f$forEach = false;
        int n = $this$forEach$iv$iv$iv.length;
        for (int i = 0; i < n; ++i) {
            ServerPlayerEvent.Death it;
            Cancelable element$iv$iv$iv;
            Cancelable it$iv = element$iv$iv$iv = $this$forEach$iv$iv$iv[i];
            boolean bl = false;
            if (it$iv.isCanceled()) {
                it = (ServerPlayerEvent.Death)it$iv;
                boolean bl2 = false;
                e.setCanceled(true);
                continue;
            }
            it = (ServerPlayerEvent.Death)it$iv;
            boolean bl3 = false;
        }
    }

    /*
     * WARNING - void declaration
     */
    @SubscribeEvent
    public final void onRightClickBlock(@NotNull PlayerInteractEvent.RightClickBlock e) {
        void this_$iv$iv;
        Intrinsics.checkNotNullParameter((Object)e, (String)"e");
        Player player = e.getEntity();
        ServerPlayer serverPlayer = player instanceof ServerPlayer ? (ServerPlayer)player : null;
        if (serverPlayer == null) {
            return;
        }
        ServerPlayer player2 = serverPlayer;
        InteractionHand interactionHand = e.getHand();
        Intrinsics.checkNotNullExpressionValue((Object)interactionHand, (String)"e.hand");
        InteractionHand hand = interactionHand;
        BlockPos blockPos2 = e.getPos();
        Intrinsics.checkNotNullExpressionValue((Object)blockPos2, (String)"e.pos");
        BlockPos pos = blockPos2;
        Direction face = e.getFace();
        CancelableObservable<ServerPlayerEvent.RightClickBlock> cancelableObservable = PlatformEvents.RIGHT_CLICK_BLOCK;
        ServerPlayerEvent.RightClickBlock rightClickBlock = new ServerPlayerEvent.RightClickBlock(player2, pos, hand, face);
        CancelableObservable<ServerPlayerEvent.RightClickBlock> this_$iv = cancelableObservable;
        boolean $i$f$postThen = false;
        EventObservable eventObservable = this_$iv;
        Cancelable[] cancelableArray = new Cancelable[]{rightClickBlock};
        Cancelable[] events$iv$iv = cancelableArray;
        boolean $i$f$post = false;
        this_$iv$iv.emit(Arrays.copyOf(events$iv$iv, events$iv$iv.length));
        Cancelable[] $this$forEach$iv$iv$iv = events$iv$iv;
        boolean $i$f$forEach = false;
        int n = $this$forEach$iv$iv$iv.length;
        for (int i = 0; i < n; ++i) {
            ServerPlayerEvent.RightClickBlock it;
            Cancelable element$iv$iv$iv;
            Cancelable it$iv = element$iv$iv$iv = $this$forEach$iv$iv$iv[i];
            boolean bl = false;
            if (it$iv.isCanceled()) {
                it = (ServerPlayerEvent.RightClickBlock)it$iv;
                boolean bl2 = false;
                e.setCanceled(true);
                continue;
            }
            it = (ServerPlayerEvent.RightClickBlock)it$iv;
            boolean bl3 = false;
        }
    }

    /*
     * WARNING - void declaration
     */
    @SubscribeEvent
    public final void onRightClickEntity(@NotNull PlayerInteractEvent.EntityInteract e) {
        void this_$iv$iv;
        Intrinsics.checkNotNullParameter((Object)e, (String)"e");
        Player player = e.getEntity();
        ServerPlayer serverPlayer = player instanceof ServerPlayer ? (ServerPlayer)player : null;
        if (serverPlayer == null) {
            return;
        }
        ServerPlayer player2 = serverPlayer;
        InteractionHand interactionHand = e.getHand();
        Intrinsics.checkNotNullExpressionValue((Object)interactionHand, (String)"e.hand");
        InteractionHand hand = interactionHand;
        ItemStack item = player2.m_21120_(hand);
        Entity entity2 = e.getTarget();
        CancelableObservable<ServerPlayerEvent.RightClickEntity> cancelableObservable = PlatformEvents.RIGHT_CLICK_ENTITY;
        Intrinsics.checkNotNullExpressionValue((Object)item, (String)"item");
        Intrinsics.checkNotNullExpressionValue((Object)entity2, (String)"entity");
        ServerPlayerEvent.RightClickEntity rightClickEntity = new ServerPlayerEvent.RightClickEntity(player2, item, hand, entity2);
        CancelableObservable<ServerPlayerEvent.RightClickEntity> this_$iv = cancelableObservable;
        boolean $i$f$postThen = false;
        EventObservable eventObservable = this_$iv;
        Cancelable[] cancelableArray = new Cancelable[]{rightClickEntity};
        Cancelable[] events$iv$iv = cancelableArray;
        boolean $i$f$post = false;
        this_$iv$iv.emit(Arrays.copyOf(events$iv$iv, events$iv$iv.length));
        Cancelable[] $this$forEach$iv$iv$iv = events$iv$iv;
        boolean $i$f$forEach = false;
        int n = $this$forEach$iv$iv$iv.length;
        for (int i = 0; i < n; ++i) {
            ServerPlayerEvent.RightClickEntity it;
            Cancelable element$iv$iv$iv;
            Cancelable it$iv = element$iv$iv$iv = $this$forEach$iv$iv$iv[i];
            boolean bl = false;
            if (it$iv.isCanceled()) {
                it = (ServerPlayerEvent.RightClickEntity)it$iv;
                boolean bl2 = false;
                e.setCanceled(true);
                continue;
            }
            it = (ServerPlayerEvent.RightClickEntity)it$iv;
            boolean bl3 = false;
        }
    }

    /*
     * WARNING - void declaration
     */
    @SubscribeEvent
    public final void onChangeDimension(@NotNull PlayerEvent.PlayerChangedDimensionEvent e) {
        Intrinsics.checkNotNullParameter((Object)e, (String)"e");
        Player player = e.getEntity();
        if (player instanceof ServerPlayer) {
            void $this$iv;
            EventObservable<ChangeDimensionEvent> eventObservable = PlatformEvents.CHANGE_DIMENSION;
            ChangeDimensionEvent[] changeDimensionEventArray = new ChangeDimensionEvent[]{new ChangeDimensionEvent((ServerPlayer)player)};
            ChangeDimensionEvent[] events$iv = changeDimensionEventArray;
            boolean $i$f$post = false;
            $this$iv.emit(Arrays.copyOf(events$iv, events$iv.length));
            ChangeDimensionEvent[] $this$forEach$iv$iv = events$iv;
            boolean $i$f$forEach = false;
            int n = $this$forEach$iv$iv.length;
            for (int i = 0; i < n; ++i) {
                ChangeDimensionEvent element$iv$iv;
                ChangeDimensionEvent changeDimensionEvent = element$iv$iv = $this$forEach$iv$iv[i];
                boolean bl = false;
                ChangeDimensionEvent it = changeDimensionEvent;
            }
        }
    }
}

