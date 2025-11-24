/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.JvmOverloads
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.functions.Function3
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.server.level.ServerPlayer
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.callback;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.callback.MoveSelectCallback;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.callback.MoveSelectCallbacks;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.callback.MoveSelectDTO;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.Move;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.InBattleMove;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.callback.OpenMoveCallbackPacket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000`\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b'\u0010(Je\u0010\r\u001a\u00020\n2\u0006\u0010\u0003\u001a\u00020\u00022\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0014\b\u0002\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\b0\u00072\u0014\b\u0002\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\n0\u00072\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\n0\u0007H\u0007\u00a2\u0006\u0004\b\r\u0010\u000eJt\u0010\r\u001a\u00020\n2\u0006\u0010\u0003\u001a\u00020\u00022\b\b\u0002\u0010\u0010\u001a\u00020\u000f2\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00110\u00042\u0014\b\u0002\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\n0\u00072-\u0010\f\u001a)\u0012\u0004\u0012\u00020\u0002\u0012\u0013\u0012\u00110\u0014\u00a2\u0006\f\b\u0015\u0012\b\b\u0016\u0012\u0004\b\b(\u0017\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\n0\u0013H\u0007\u00a2\u0006\u0004\b\r\u0010\u0018Je\u0010\u001a\u001a\u00020\n2\u0006\u0010\u0003\u001a\u00020\u00022\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00190\u00042\u0014\b\u0002\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u0019\u0012\u0004\u0012\u00020\b0\u00072\u0014\b\u0002\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\n0\u00072\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u0019\u0012\u0004\u0012\u00020\n0\u0007H\u0007\u00a2\u0006\u0004\b\u001a\u0010\u000eJ%\u0010\u001d\u001a\u00020\n2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u001c\u001a\u00020\u001b2\u0006\u0010\u0017\u001a\u00020\u0014\u00a2\u0006\u0004\b\u001d\u0010\u001eJ\u001d\u0010\u001f\u001a\u00020\n2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u001c\u001a\u00020\u001b\u00a2\u0006\u0004\b\u001f\u0010 R#\u0010#\u001a\u000e\u0012\u0004\u0012\u00020\u001b\u0012\u0004\u0012\u00020\"0!8\u0006\u00a2\u0006\f\n\u0004\b#\u0010$\u001a\u0004\b%\u0010&\u00a8\u0006)"}, d2={"Lcom/cobblemon/mod/common/api/callback/MoveSelectCallbacks;", "", "Lnet/minecraft/server/level/ServerPlayer;", "player", "", "Lcom/cobblemon/mod/common/api/moves/Move;", "moves", "Lkotlin/Function1;", "", "canSelect", "", "cancel", "handler", "create", "(Lnet/minecraft/server/level/ServerPlayer;Ljava/util/List;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)V", "Lnet/minecraft/network/chat/Component;", "title", "Lcom/cobblemon/mod/common/api/callback/MoveSelectDTO;", "possibleMoves", "Lkotlin/Function3;", "", "Lkotlin/ParameterName;", "name", "index", "(Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/network/chat/Component;Ljava/util/List;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function3;)V", "Lcom/cobblemon/mod/common/battles/InBattleMove;", "createBattleSelect", "Ljava/util/UUID;", "uuid", "handleCallback", "(Lnet/minecraft/server/level/ServerPlayer;Ljava/util/UUID;I)V", "handleCancelled", "(Lnet/minecraft/server/level/ServerPlayer;Ljava/util/UUID;)V", "", "Lcom/cobblemon/mod/common/api/callback/MoveSelectCallback;", "callbacks", "Ljava/util/Map;", "getCallbacks", "()Ljava/util/Map;", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nMoveSelectCallback.kt\nKotlin\n*S Kotlin\n*F\n+ 1 MoveSelectCallback.kt\ncom/cobblemon/mod/common/api/callback/MoveSelectCallbacks\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,133:1\n1549#2:134\n1620#2,2:135\n1622#2:138\n1549#2:139\n1620#2,3:140\n1#3:137\n*S KotlinDebug\n*F\n+ 1 MoveSelectCallback.kt\ncom/cobblemon/mod/common/api/callback/MoveSelectCallbacks\n*L\n65#1:134\n65#1:135,2\n65#1:138\n79#1:139\n79#1:140,3\n*E\n"})
public final class MoveSelectCallbacks {
    @NotNull
    public static final MoveSelectCallbacks INSTANCE = new MoveSelectCallbacks();
    @NotNull
    private static final Map<UUID, MoveSelectCallback> callbacks = new LinkedHashMap();

    private MoveSelectCallbacks() {
    }

    @NotNull
    public final Map<UUID, MoveSelectCallback> getCallbacks() {
        return callbacks;
    }

    @JvmOverloads
    public final void create(@NotNull ServerPlayer player, @NotNull Component title, @NotNull List<MoveSelectDTO> possibleMoves, @NotNull Function1<? super ServerPlayer, Unit> cancel2, @NotNull Function3<? super ServerPlayer, ? super Integer, ? super MoveSelectDTO, Unit> handler) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter((Object)title, (String)"title");
        Intrinsics.checkNotNullParameter(possibleMoves, (String)"possibleMoves");
        Intrinsics.checkNotNullParameter(cancel2, (String)"cancel");
        Intrinsics.checkNotNullParameter(handler, (String)"handler");
        MoveSelectCallback callback = new MoveSelectCallback(null, possibleMoves, cancel2, handler, 1, null);
        Map<UUID, MoveSelectCallback> map = callbacks;
        UUID uUID = player.m_20148_();
        Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"player.uuid");
        map.put(uUID, callback);
        UUID uUID2 = callback.getUuid();
        MutableComponent mutableComponent = title.m_6881_();
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"title.copy()");
        CobblemonNetwork.INSTANCE.sendPacket(player, new OpenMoveCallbackPacket(uUID2, mutableComponent, possibleMoves));
    }

    public static /* synthetic */ void create$default(MoveSelectCallbacks moveSelectCallbacks, ServerPlayer serverPlayer, Component component, List list, Function1 function1, Function3 function3, int n, Object object) {
        if ((n & 2) != 0) {
            component = (Component)TextKt.text("");
        }
        if ((n & 8) != 0) {
            function1 = create.1.INSTANCE;
        }
        moveSelectCallbacks.create(serverPlayer, component, list, (Function1<? super ServerPlayer, Unit>)function1, (Function3<? super ServerPlayer, ? super Integer, ? super MoveSelectDTO, Unit>)function3);
    }

    /*
     * WARNING - void declaration
     */
    @JvmOverloads
    public final void create(@NotNull ServerPlayer player, @NotNull List<? extends Move> moves, @NotNull Function1<? super Move, Boolean> canSelect, @NotNull Function1<? super ServerPlayer, Unit> cancel2, @NotNull Function1<? super Move, Unit> handler) {
        Collection<MoveSelectDTO> collection;
        void $this$mapTo$iv$iv;
        void $this$map$iv;
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter(moves, (String)"moves");
        Intrinsics.checkNotNullParameter(canSelect, (String)"canSelect");
        Intrinsics.checkNotNullParameter(cancel2, (String)"cancel");
        Intrinsics.checkNotNullParameter(handler, (String)"handler");
        Iterable iterable = moves;
        Component component = null;
        ServerPlayer serverPlayer = player;
        MoveSelectCallbacks moveSelectCallbacks = this;
        boolean $i$f$map = false;
        void var8_11 = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void battleMove;
            MoveSelectDTO moveSelectDTO;
            Move move = (Move)item$iv$iv;
            collection = destination$iv$iv;
            boolean bl = false;
            MoveSelectDTO it = moveSelectDTO = new MoveSelectDTO((Move)battleMove, false, 2, null);
            boolean bl2 = false;
            it.setEnabled((Boolean)canSelect.invoke((Object)battleMove));
            collection.add(moveSelectDTO);
        }
        collection = (List)destination$iv$iv;
        MoveSelectCallbacks.create$default(moveSelectCallbacks, serverPlayer, component, (List)collection, cancel2, (Function3)new Function3<ServerPlayer, Integer, MoveSelectDTO, Unit>(handler, moves){
            final /* synthetic */ Function1<Move, Unit> $handler;
            final /* synthetic */ List<Move> $moves;
            {
                this.$handler = $handler;
                this.$moves = $moves;
                super(3);
            }

            public final void invoke(@NotNull ServerPlayer serverPlayer, int index, @NotNull MoveSelectDTO moveSelectDTO) {
                Intrinsics.checkNotNullParameter((Object)serverPlayer, (String)"<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter((Object)moveSelectDTO, (String)"<anonymous parameter 2>");
                this.$handler.invoke((Object)this.$moves.get(index));
            }
        }, 2, null);
    }

    public static /* synthetic */ void create$default(MoveSelectCallbacks moveSelectCallbacks, ServerPlayer serverPlayer, List list, Function1 function1, Function1 function12, Function1 function13, int n, Object object) {
        if ((n & 4) != 0) {
            function1 = create.2.INSTANCE;
        }
        if ((n & 8) != 0) {
            function12 = create.3.INSTANCE;
        }
        moveSelectCallbacks.create(serverPlayer, list, (Function1<? super Move, Boolean>)function1, (Function1<? super ServerPlayer, Unit>)function12, (Function1<? super Move, Unit>)function13);
    }

    /*
     * WARNING - void declaration
     */
    @JvmOverloads
    public final void createBattleSelect(@NotNull ServerPlayer player, @NotNull List<InBattleMove> moves, @NotNull Function1<? super InBattleMove, Boolean> canSelect, @NotNull Function1<? super ServerPlayer, Unit> cancel2, @NotNull Function1<? super InBattleMove, Unit> handler) {
        Collection<MoveSelectDTO> collection;
        void $this$mapTo$iv$iv;
        void $this$map$iv;
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter(moves, (String)"moves");
        Intrinsics.checkNotNullParameter(canSelect, (String)"canSelect");
        Intrinsics.checkNotNullParameter(cancel2, (String)"cancel");
        Intrinsics.checkNotNullParameter(handler, (String)"handler");
        Iterable iterable = moves;
        Component component = null;
        ServerPlayer serverPlayer = player;
        MoveSelectCallbacks moveSelectCallbacks = this;
        boolean $i$f$map = false;
        void var8_11 = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void battleMove;
            MoveSelectDTO moveSelectDTO;
            InBattleMove inBattleMove = (InBattleMove)item$iv$iv;
            collection = destination$iv$iv;
            boolean bl = false;
            MoveSelectDTO it = moveSelectDTO = new MoveSelectDTO((InBattleMove)battleMove, false, 2, null);
            boolean bl2 = false;
            it.setEnabled((Boolean)canSelect.invoke((Object)battleMove));
            collection.add(moveSelectDTO);
        }
        collection = (List)destination$iv$iv;
        MoveSelectCallbacks.create$default(moveSelectCallbacks, serverPlayer, component, (List)collection, cancel2, (Function3)new Function3<ServerPlayer, Integer, MoveSelectDTO, Unit>(handler, moves){
            final /* synthetic */ Function1<InBattleMove, Unit> $handler;
            final /* synthetic */ List<InBattleMove> $moves;
            {
                this.$handler = $handler;
                this.$moves = $moves;
                super(3);
            }

            public final void invoke(@NotNull ServerPlayer serverPlayer, int index, @NotNull MoveSelectDTO moveSelectDTO) {
                Intrinsics.checkNotNullParameter((Object)serverPlayer, (String)"<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter((Object)moveSelectDTO, (String)"<anonymous parameter 2>");
                this.$handler.invoke((Object)this.$moves.get(index));
            }
        }, 2, null);
    }

    public static /* synthetic */ void createBattleSelect$default(MoveSelectCallbacks moveSelectCallbacks, ServerPlayer serverPlayer, List list, Function1 function1, Function1 function12, Function1 function13, int n, Object object) {
        if ((n & 4) != 0) {
            function1 = createBattleSelect.1.INSTANCE;
        }
        if ((n & 8) != 0) {
            function12 = createBattleSelect.2.INSTANCE;
        }
        moveSelectCallbacks.createBattleSelect(serverPlayer, list, (Function1<? super InBattleMove, Boolean>)function1, (Function1<? super ServerPlayer, Unit>)function12, (Function1<? super InBattleMove, Unit>)function13);
    }

    public final void handleCancelled(@NotNull ServerPlayer player, @NotNull UUID uuid2) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter((Object)uuid2, (String)"uuid");
        MoveSelectCallback moveSelectCallback = callbacks.get(player.m_20148_());
        if (moveSelectCallback == null) {
            return;
        }
        MoveSelectCallback callback = moveSelectCallback;
        if (!Intrinsics.areEqual((Object)callback.getUuid(), (Object)uuid2)) {
            return;
        }
        callbacks.remove(player.m_20148_());
        callback.getCancel().invoke((Object)player);
    }

    public final void handleCallback(@NotNull ServerPlayer player, @NotNull UUID uuid2, int index) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter((Object)uuid2, (String)"uuid");
        MoveSelectCallback moveSelectCallback = callbacks.get(player.m_20148_());
        if (moveSelectCallback == null) {
            return;
        }
        MoveSelectCallback callback = moveSelectCallback;
        callbacks.remove(player.m_20148_());
        if (!Intrinsics.areEqual((Object)callback.getUuid(), (Object)uuid2)) {
            Cobblemon.INSTANCE.getLOGGER().warn("A move select callback ran but with a mismatching UUID from " + player.m_36316_().getName() + ". Hacking attempts?");
        } else if (index >= callback.getShownMoves().size()) {
            Cobblemon.INSTANCE.getLOGGER().warn(player.m_36316_().getName() + " used move select callback with an index that was out of bounds. Hacking attempts? Tried " + index + ", possible size was " + callback.getShownMoves().size());
        } else if (!callback.getShownMoves().get(index).getEnabled()) {
            Cobblemon.INSTANCE.getLOGGER().warn(player.m_36316_().getName() + " used move select callback with a move that is not enabled. Hacking attempts?");
        } else {
            callback.getHandler().invoke((Object)player, (Object)index, (Object)callback.getShownMoves().get(index));
        }
    }

    @JvmOverloads
    public final void create(@NotNull ServerPlayer player, @NotNull Component title, @NotNull List<MoveSelectDTO> possibleMoves, @NotNull Function3<? super ServerPlayer, ? super Integer, ? super MoveSelectDTO, Unit> handler) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter((Object)title, (String)"title");
        Intrinsics.checkNotNullParameter(possibleMoves, (String)"possibleMoves");
        Intrinsics.checkNotNullParameter(handler, (String)"handler");
        MoveSelectCallbacks.create$default(this, player, title, possibleMoves, null, handler, 8, null);
    }

    @JvmOverloads
    public final void create(@NotNull ServerPlayer player, @NotNull List<MoveSelectDTO> possibleMoves, @NotNull Function3<? super ServerPlayer, ? super Integer, ? super MoveSelectDTO, Unit> handler) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter(possibleMoves, (String)"possibleMoves");
        Intrinsics.checkNotNullParameter(handler, (String)"handler");
        MoveSelectCallbacks.create$default(this, player, null, possibleMoves, null, handler, 10, null);
    }

    @JvmOverloads
    public final void create(@NotNull ServerPlayer player, @NotNull List<? extends Move> moves, @NotNull Function1<? super Move, Boolean> canSelect, @NotNull Function1<? super Move, Unit> handler) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter(moves, (String)"moves");
        Intrinsics.checkNotNullParameter(canSelect, (String)"canSelect");
        Intrinsics.checkNotNullParameter(handler, (String)"handler");
        MoveSelectCallbacks.create$default(this, player, moves, canSelect, null, handler, 8, null);
    }

    @JvmOverloads
    public final void create(@NotNull ServerPlayer player, @NotNull List<? extends Move> moves, @NotNull Function1<? super Move, Unit> handler) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter(moves, (String)"moves");
        Intrinsics.checkNotNullParameter(handler, (String)"handler");
        MoveSelectCallbacks.create$default(this, player, moves, null, null, handler, 12, null);
    }

    @JvmOverloads
    public final void createBattleSelect(@NotNull ServerPlayer player, @NotNull List<InBattleMove> moves, @NotNull Function1<? super InBattleMove, Boolean> canSelect, @NotNull Function1<? super InBattleMove, Unit> handler) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter(moves, (String)"moves");
        Intrinsics.checkNotNullParameter(canSelect, (String)"canSelect");
        Intrinsics.checkNotNullParameter(handler, (String)"handler");
        MoveSelectCallbacks.createBattleSelect$default(this, player, moves, canSelect, null, handler, 8, null);
    }

    @JvmOverloads
    public final void createBattleSelect(@NotNull ServerPlayer player, @NotNull List<InBattleMove> moves, @NotNull Function1<? super InBattleMove, Unit> handler) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter(moves, (String)"moves");
        Intrinsics.checkNotNullParameter(handler, (String)"handler");
        MoveSelectCallbacks.createBattleSelect$default(this, player, moves, null, null, handler, 12, null);
    }
}

