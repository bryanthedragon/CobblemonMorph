/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.TuplesKt
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleFormat;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownActionResponse;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ActiveClientBattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ClientBattleActor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ClientBattleMessageQueue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ClientBattleSide;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.SingleActionRequest;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.battle.BattleSelectActionsPacket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000h\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0010!\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0011\n\u0002\b\t\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0018\u001a\u00020\b\u0012\u0006\u0010\u0014\u001a\u00020\u0013\u00a2\u0006\u0004\b@\u0010AJ\r\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u000f\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u0017\u0010\u000b\u001a\u0004\u0018\u00010\n2\u0006\u0010\t\u001a\u00020\b\u00a2\u0006\u0004\b\u000b\u0010\fJ!\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00100\u000f2\u0006\u0010\u000e\u001a\u00020\r\u00a2\u0006\u0004\b\u0011\u0010\u0012R\u0017\u0010\u0014\u001a\u00020\u00138\u0006\u00a2\u0006\f\n\u0004\b\u0014\u0010\u0015\u001a\u0004\b\u0016\u0010\u0017R\u0017\u0010\u0018\u001a\u00020\b8\u0006\u00a2\u0006\f\n\u0004\b\u0018\u0010\u0019\u001a\u0004\b\u001a\u0010\u001bR\u0017\u0010\u001d\u001a\u00020\u001c8\u0006\u00a2\u0006\f\n\u0004\b\u001d\u0010\u001e\u001a\u0004\b\u001f\u0010 R\"\u0010\"\u001a\u00020!8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\"\u0010#\u001a\u0004\b$\u0010%\"\u0004\b&\u0010'R\"\u0010(\u001a\u00020!8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b(\u0010#\u001a\u0004\b)\u0010%\"\u0004\b*\u0010'R(\u0010,\u001a\b\u0012\u0004\u0012\u00020\u00050+8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b,\u0010-\u001a\u0004\b.\u0010/\"\u0004\b0\u00101R\u0017\u00103\u001a\u0002028\u0006\u00a2\u0006\f\n\u0004\b3\u00104\u001a\u0004\b5\u00106R\u0017\u00107\u001a\u0002028\u0006\u00a2\u0006\f\n\u0004\b7\u00104\u001a\u0004\b8\u00106R\u0017\u0010<\u001a\b\u0012\u0004\u0012\u000202098F\u00a2\u0006\u0006\u001a\u0004\b:\u0010;R\"\u0010=\u001a\u00020!8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b=\u0010#\u001a\u0004\b>\u0010%\"\u0004\b?\u0010'\u00a8\u0006B"}, d2={"Lcom/cobblemon/mod/common/client/battle/ClientBattle;", "", "", "checkForFinishedChoosing", "()V", "Lcom/cobblemon/mod/common/client/battle/SingleActionRequest;", "getFirstUnansweredRequest", "()Lcom/cobblemon/mod/common/client/battle/SingleActionRequest;", "Ljava/util/UUID;", "uuid", "Lcom/cobblemon/mod/common/client/battle/ClientBattleActor;", "getParticipatingActor", "(Ljava/util/UUID;)Lcom/cobblemon/mod/common/client/battle/ClientBattleActor;", "", "pnx", "Lkotlin/Pair;", "Lcom/cobblemon/mod/common/client/battle/ActiveClientBattlePokemon;", "getPokemonFromPNX", "(Ljava/lang/String;)Lkotlin/Pair;", "Lcom/cobblemon/mod/common/battles/BattleFormat;", "battleFormat", "Lcom/cobblemon/mod/common/battles/BattleFormat;", "getBattleFormat", "()Lcom/cobblemon/mod/common/battles/BattleFormat;", "battleId", "Ljava/util/UUID;", "getBattleId", "()Ljava/util/UUID;", "Lcom/cobblemon/mod/common/client/battle/ClientBattleMessageQueue;", "messages", "Lcom/cobblemon/mod/common/client/battle/ClientBattleMessageQueue;", "getMessages", "()Lcom/cobblemon/mod/common/client/battle/ClientBattleMessageQueue;", "", "minimised", "Z", "getMinimised", "()Z", "setMinimised", "(Z)V", "mustChoose", "getMustChoose", "setMustChoose", "", "pendingActionRequests", "Ljava/util/List;", "getPendingActionRequests", "()Ljava/util/List;", "setPendingActionRequests", "(Ljava/util/List;)V", "Lcom/cobblemon/mod/common/client/battle/ClientBattleSide;", "side1", "Lcom/cobblemon/mod/common/client/battle/ClientBattleSide;", "getSide1", "()Lcom/cobblemon/mod/common/client/battle/ClientBattleSide;", "side2", "getSide2", "", "getSides", "()[Lcom/cobblemon/mod/common/client/battle/ClientBattleSide;", "sides", "spectating", "getSpectating", "setSpectating", "<init>", "(Ljava/util/UUID;Lcom/cobblemon/mod/common/battles/BattleFormat;)V", "common"})
@SourceDebugExtension(value={"SMAP\nClientBattle.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ClientBattle.kt\ncom/cobblemon/mod/common/client/battle/ClientBattle\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 4 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,58:1\n288#2,2:59\n1549#2:61\n1620#2,3:62\n10242#3:65\n10664#3,5:66\n10242#3:72\n10664#3,5:73\n1#4:71\n*S KotlinDebug\n*F\n+ 1 ClientBattle.kt\ncom/cobblemon/mod/common/client/battle/ClientBattle\n*L\n33#1:59,2\n39#1:61\n39#1:62,3\n47#1:65\n47#1:66,5\n56#1:72\n56#1:73,5\n*E\n"})
public final class ClientBattle {
    @NotNull
    private final UUID battleId;
    @NotNull
    private final BattleFormat battleFormat;
    private boolean minimised;
    private boolean spectating;
    @NotNull
    private final ClientBattleSide side1;
    @NotNull
    private final ClientBattleSide side2;
    @NotNull
    private List<SingleActionRequest> pendingActionRequests;
    @NotNull
    private final ClientBattleMessageQueue messages;
    private boolean mustChoose;

    public ClientBattle(@NotNull UUID battleId, @NotNull BattleFormat battleFormat) {
        Intrinsics.checkNotNullParameter((Object)battleId, (String)"battleId");
        Intrinsics.checkNotNullParameter((Object)battleFormat, (String)"battleFormat");
        this.battleId = battleId;
        this.battleFormat = battleFormat;
        this.minimised = true;
        this.side1 = new ClientBattleSide();
        this.side2 = new ClientBattleSide();
        this.pendingActionRequests = new ArrayList();
        this.messages = new ClientBattleMessageQueue();
    }

    @NotNull
    public final UUID getBattleId() {
        return this.battleId;
    }

    @NotNull
    public final BattleFormat getBattleFormat() {
        return this.battleFormat;
    }

    public final boolean getMinimised() {
        return this.minimised;
    }

    public final void setMinimised(boolean bl) {
        this.minimised = bl;
    }

    public final boolean getSpectating() {
        return this.spectating;
    }

    public final void setSpectating(boolean bl) {
        this.spectating = bl;
    }

    @NotNull
    public final ClientBattleSide getSide1() {
        return this.side1;
    }

    @NotNull
    public final ClientBattleSide getSide2() {
        return this.side2;
    }

    @NotNull
    public final ClientBattleSide[] getSides() {
        ClientBattleSide[] clientBattleSideArray = new ClientBattleSide[]{this.side1, this.side2};
        return clientBattleSideArray;
    }

    @NotNull
    public final List<SingleActionRequest> getPendingActionRequests() {
        return this.pendingActionRequests;
    }

    public final void setPendingActionRequests(@NotNull List<SingleActionRequest> list) {
        Intrinsics.checkNotNullParameter(list, (String)"<set-?>");
        this.pendingActionRequests = list;
    }

    @NotNull
    public final ClientBattleMessageQueue getMessages() {
        return this.messages;
    }

    public final boolean getMustChoose() {
        return this.mustChoose;
    }

    public final void setMustChoose(boolean bl) {
        this.mustChoose = bl;
    }

    @Nullable
    public final SingleActionRequest getFirstUnansweredRequest() {
        Object v0;
        block1: {
            Iterable $this$firstOrNull$iv = this.pendingActionRequests;
            boolean $i$f$firstOrNull = false;
            for (Object element$iv : $this$firstOrNull$iv) {
                SingleActionRequest it = (SingleActionRequest)element$iv;
                boolean bl = false;
                if (!(it.getResponse() == null)) continue;
                v0 = element$iv;
                break block1;
            }
            v0 = null;
        }
        return v0;
    }

    /*
     * WARNING - void declaration
     */
    public final void checkForFinishedChoosing() {
        if (this.getFirstUnansweredRequest() == null) {
            Collection<ShowdownActionResponse> collection;
            void $this$mapTo$iv$iv;
            void $this$map$iv;
            Iterable iterable = this.pendingActionRequests;
            UUID uUID = this.battleId;
            CobblemonNetwork cobblemonNetwork = CobblemonNetwork.INSTANCE;
            boolean $i$f$map = false;
            void var3_5 = $this$map$iv;
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
            boolean $i$f$mapTo = false;
            for (Object item$iv$iv : $this$mapTo$iv$iv) {
                void it;
                SingleActionRequest singleActionRequest = (SingleActionRequest)item$iv$iv;
                collection = destination$iv$iv;
                boolean bl = false;
                ShowdownActionResponse showdownActionResponse = it.getResponse();
                Intrinsics.checkNotNull((Object)showdownActionResponse);
                collection.add(showdownActionResponse);
            }
            collection = (List)destination$iv$iv;
            List list = collection;
            UUID uUID2 = uUID;
            cobblemonNetwork.sendPacketToServer(new BattleSelectActionsPacket(uUID2, list));
            this.mustChoose = false;
        }
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public final Pair<ClientBattleActor, ActiveClientBattlePokemon> getPokemonFromPNX(@NotNull String pnx) {
        Object v4;
        ClientBattleActor actor;
        block6: {
            Object object;
            Object object22;
            block5: {
                void $this$flatMapTo$iv$iv;
                Intrinsics.checkNotNullParameter((Object)pnx, (String)"pnx");
                ClientBattleSide[] $this$flatMap$iv = this.getSides();
                boolean $i$f$flatMap = false;
                ClientBattleSide[] clientBattleSideArray = $this$flatMap$iv;
                Collection destination$iv$iv = new ArrayList();
                boolean $i$f$flatMapTo = false;
                int n = ((void)$this$flatMapTo$iv$iv).length;
                for (int i = 0; i < n; ++i) {
                    void element$iv$iv;
                    void it = element$iv$iv = $this$flatMapTo$iv$iv[i];
                    boolean bl = false;
                    Iterable list$iv$iv = it.getActors();
                    CollectionsKt.addAll((Collection)destination$iv$iv, (Iterable)list$iv$iv);
                }
                Iterable iterable = (List)destination$iv$iv;
                for (Object object22 : iterable) {
                    ClientBattleActor it = (ClientBattleActor)object22;
                    boolean bl = false;
                    String string = it.getShowdownId();
                    String string2 = pnx.substring(0, 2);
                    Intrinsics.checkNotNullExpressionValue((Object)string2, (String)"this as java.lang.String\u2026ing(startIndex, endIndex)");
                    if (!Intrinsics.areEqual((Object)string, (Object)string2)) continue;
                    object = object22;
                    break block5;
                }
                object = null;
            }
            ClientBattleActor clientBattleActor = (ClientBattleActor)object;
            if (clientBattleActor == null) {
                throw new IllegalStateException("Invalid pnx: " + pnx + " - unknown actor");
            }
            actor = clientBattleActor;
            char letter = pnx.charAt(2);
            object22 = actor.getSide().getActiveClientBattlePokemon();
            Iterator iterator = object22.iterator();
            while (iterator.hasNext()) {
                Object t = iterator.next();
                ActiveClientBattlePokemon it = (ActiveClientBattlePokemon)t;
                boolean bl = false;
                if (!(it.getLetter() == letter)) continue;
                v4 = t;
                break block6;
            }
            v4 = null;
        }
        ActiveClientBattlePokemon activeClientBattlePokemon = v4;
        if (activeClientBattlePokemon == null) {
            throw new IllegalStateException("Invalid pnx: " + pnx + " - unknown pokemon");
        }
        ActiveClientBattlePokemon pokemon = activeClientBattlePokemon;
        return TuplesKt.to((Object)actor, (Object)pokemon);
    }

    /*
     * WARNING - void declaration
     */
    @Nullable
    public final ClientBattleActor getParticipatingActor(@NotNull UUID uuid2) {
        Object v0;
        block2: {
            void $this$flatMapTo$iv$iv;
            Intrinsics.checkNotNullParameter((Object)uuid2, (String)"uuid");
            ClientBattleSide[] $this$flatMap$iv = this.getSides();
            boolean $i$f$flatMap = false;
            ClientBattleSide[] clientBattleSideArray = $this$flatMap$iv;
            Collection destination$iv$iv = new ArrayList();
            boolean $i$f$flatMapTo = false;
            int n = ((void)$this$flatMapTo$iv$iv).length;
            for (int i = 0; i < n; ++i) {
                void element$iv$iv;
                void it = element$iv$iv = $this$flatMapTo$iv$iv[i];
                boolean bl = false;
                Iterable list$iv$iv = it.getActors();
                CollectionsKt.addAll((Collection)destination$iv$iv, (Iterable)list$iv$iv);
            }
            Iterable iterable = (List)destination$iv$iv;
            for (Object e : iterable) {
                ClientBattleActor it = (ClientBattleActor)e;
                boolean bl = false;
                if (!Intrinsics.areEqual((Object)it.getUuid(), (Object)uuid2)) continue;
                v0 = e;
                break block2;
            }
            v0 = null;
        }
        return v0;
    }
}

