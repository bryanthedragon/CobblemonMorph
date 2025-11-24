/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ActiveClientBattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ClientBattle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ClientBattleActor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u00002\u00020\u0001B\u0007\u00a2\u0006\u0004\b\u0014\u0010\u0015R\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00030\u00028F\u00a2\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u001d\u0010\t\u001a\b\u0012\u0004\u0012\u00020\b0\u00078\u0006\u00a2\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u000b\u0010\fR\"\u0010\u000e\u001a\u00020\r8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013\u00a8\u0006\u0016"}, d2={"Lcom/cobblemon/mod/common/client/battle/ClientBattleSide;", "", "", "Lcom/cobblemon/mod/common/client/battle/ActiveClientBattlePokemon;", "getActiveClientBattlePokemon", "()Ljava/lang/Iterable;", "activeClientBattlePokemon", "", "Lcom/cobblemon/mod/common/client/battle/ClientBattleActor;", "actors", "Ljava/util/List;", "getActors", "()Ljava/util/List;", "Lcom/cobblemon/mod/common/client/battle/ClientBattle;", "battle", "Lcom/cobblemon/mod/common/client/battle/ClientBattle;", "getBattle", "()Lcom/cobblemon/mod/common/client/battle/ClientBattle;", "setBattle", "(Lcom/cobblemon/mod/common/client/battle/ClientBattle;)V", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nClientBattleSide.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ClientBattleSide.kt\ncom/cobblemon/mod/common/client/battle/ClientBattleSide\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,15:1\n1360#2:16\n1446#2,5:17\n*S KotlinDebug\n*F\n+ 1 ClientBattleSide.kt\ncom/cobblemon/mod/common/client/battle/ClientBattleSide\n*L\n14#1:16\n14#1:17,5\n*E\n"})
public final class ClientBattleSide {
    public ClientBattle battle;
    @NotNull
    private final List<ClientBattleActor> actors = new ArrayList();

    @NotNull
    public final ClientBattle getBattle() {
        ClientBattle clientBattle = this.battle;
        if (clientBattle != null) {
            return clientBattle;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"battle");
        return null;
    }

    public final void setBattle(@NotNull ClientBattle clientBattle) {
        Intrinsics.checkNotNullParameter((Object)clientBattle, (String)"<set-?>");
        this.battle = clientBattle;
    }

    @NotNull
    public final List<ClientBattleActor> getActors() {
        return this.actors;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public final Iterable<ActiveClientBattlePokemon> getActiveClientBattlePokemon() {
        void $this$flatMapTo$iv$iv;
        Iterable $this$flatMap$iv = this.actors;
        boolean $i$f$flatMap = false;
        Iterable iterable = $this$flatMap$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$flatMapTo = false;
        for (Object element$iv$iv : $this$flatMapTo$iv$iv) {
            ClientBattleActor it = (ClientBattleActor)element$iv$iv;
            boolean bl = false;
            Iterable list$iv$iv = it.getActivePokemon();
            CollectionsKt.addAll((Collection)destination$iv$iv, (Iterable)list$iv$iv);
        }
        return (List)destination$iv$iv;
    }
}

