/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.collections.CollectionsKt
 *  kotlin.collections.SetsKt
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.Entity
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.BattleActor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleActorErrors;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleStartError;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleStartResult;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ErroredBattleStart;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerExtensionsKt;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010#\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\t\b\u0016\u0018\u00002\u00020\u0001B!\u0012\u000e\b\u0002\u0010#\u001a\b\u0012\u0004\u0012\u00020\u00020\"\u0012\b\b\u0002\u0010)\u001a\u00020(\u00a2\u0006\u0004\b/\u00100J3\u0010\u0007\u001a\u00020\u0000\"\n\b\u0000\u0010\u0003\u0018\u0001*\u00020\u00022\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u00050\u0004H\u0086\b\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0007\u0010\bJ#\u0010\t\u001a\u00020\u00012\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020\u00050\u0004H\u0016\u00a2\u0006\u0004\b\t\u0010\nJ-\u0010\f\u001a\u00020\u0000\"\n\b\u0000\u0010\u0003\u0018\u0001*\u00020\u00022\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u000bH\u0086\b\u00f8\u0001\u0000\u00a2\u0006\u0004\b\f\u0010\rJ\u0015\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u000f\u001a\u00020\u000e\u00a2\u0006\u0004\b\u0011\u0010\u0012J\r\u0010\u0013\u001a\u00020\u0010\u00a2\u0006\u0004\b\u0013\u0010\u0014J+\u0010\u0019\u001a\u00020\u00052\u0006\u0010\u0016\u001a\u00020\u00152\u0014\b\u0002\u0010\u0018\u001a\u000e\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u00170\u0004\u00a2\u0006\u0004\b\u0019\u0010\u001aR\u0017\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u001c0\u001b8F\u00a2\u0006\u0006\u001a\u0004\b\u001d\u0010\u001eR\u0017\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00020\u001b8F\u00a2\u0006\u0006\u001a\u0004\b \u0010\u001eR\u001d\u0010#\u001a\b\u0012\u0004\u0012\u00020\u00020\"8\u0006\u00a2\u0006\f\n\u0004\b#\u0010$\u001a\u0004\b%\u0010&R\u0011\u0010'\u001a\u00020\u00108F\u00a2\u0006\u0006\u001a\u0004\b'\u0010\u0014R\u0017\u0010)\u001a\u00020(8\u0006\u00a2\u0006\f\n\u0004\b)\u0010*\u001a\u0004\b+\u0010,R\u0017\u0010.\u001a\b\u0012\u0004\u0012\u00020\u000e0\u001b8F\u00a2\u0006\u0006\u001a\u0004\b-\u0010\u001e\u0082\u0002\u0007\n\u0005\b\u009920\u0001\u00a8\u00061"}, d2={"Lcom/cobblemon/mod/common/battles/ErroredBattleStart;", "Lcom/cobblemon/mod/common/battles/BattleStartResult;", "Lcom/cobblemon/mod/common/battles/BattleStartError;", "T", "Lkotlin/Function1;", "", "action", "forError", "(Lkotlin/jvm/functions/Function1;)Lcom/cobblemon/mod/common/battles/ErroredBattleStart;", "ifErrored", "(Lkotlin/jvm/functions/Function1;)Lcom/cobblemon/mod/common/battles/BattleStartResult;", "Lkotlin/Function0;", "ifHasError", "(Lkotlin/jvm/functions/Function0;)Lcom/cobblemon/mod/common/battles/ErroredBattleStart;", "Lnet/minecraft/server/level/ServerPlayer;", "player", "", "isPlayerToBlame", "(Lnet/minecraft/server/level/ServerPlayer;)Z", "isSomePlayerToBlame", "()Z", "Lnet/minecraft/world/entity/Entity;", "entity", "Lnet/minecraft/network/chat/MutableComponent;", "transformer", "sendTo", "(Lnet/minecraft/world/entity/Entity;Lkotlin/jvm/functions/Function1;)V", "", "Lcom/cobblemon/mod/common/api/battles/model/actor/BattleActor;", "getActorsToBlame", "()Ljava/lang/Iterable;", "actorsToBlame", "getErrors", "errors", "", "generalErrors", "Ljava/util/Set;", "getGeneralErrors", "()Ljava/util/Set;", "isEmpty", "Lcom/cobblemon/mod/common/battles/BattleActorErrors;", "participantErrors", "Lcom/cobblemon/mod/common/battles/BattleActorErrors;", "getParticipantErrors", "()Lcom/cobblemon/mod/common/battles/BattleActorErrors;", "getPlayersToBlame", "playersToBlame", "<init>", "(Ljava/util/Set;Lcom/cobblemon/mod/common/battles/BattleActorErrors;)V", "common"})
@SourceDebugExtension(value={"SMAP\nBattleBuilder.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BattleBuilder.kt\ncom/cobblemon/mod/common/battles/ErroredBattleStart\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 4 _Maps.kt\nkotlin/collections/MapsKt___MapsKt\n*L\n1#1,285:1\n800#2,11:286\n1855#2,2:297\n1855#2,2:299\n800#2,11:301\n1726#2,3:312\n1603#2,9:316\n1855#2:325\n1856#2:327\n1612#2:328\n1#3:315\n1#3:326\n76#4:329\n96#4,5:330\n*S KotlinDebug\n*F\n+ 1 BattleBuilder.kt\ncom/cobblemon/mod/common/battles/ErroredBattleStart\n*L\n253#1:286,11\n253#1:297,2\n258#1:299,2\n262#1:301,11\n269#1:312,3\n278#1:316,9\n278#1:325\n278#1:327\n278#1:328\n278#1:326\n284#1:329\n284#1:330,5\n*E\n"})
public class ErroredBattleStart
extends BattleStartResult {
    @NotNull
    private final Set<BattleStartError> generalErrors;
    @NotNull
    private final BattleActorErrors participantErrors;

    public ErroredBattleStart(@NotNull Set<BattleStartError> generalErrors, @NotNull BattleActorErrors participantErrors) {
        Intrinsics.checkNotNullParameter(generalErrors, (String)"generalErrors");
        Intrinsics.checkNotNullParameter((Object)participantErrors, (String)"participantErrors");
        this.generalErrors = generalErrors;
        this.participantErrors = participantErrors;
    }

    public /* synthetic */ ErroredBattleStart(Set set2, BattleActorErrors battleActorErrors, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 1) != 0) {
            set2 = new LinkedHashSet();
        }
        if ((n & 2) != 0) {
            battleActorErrors = new BattleActorErrors();
        }
        this(set2, battleActorErrors);
    }

    @NotNull
    public final Set<BattleStartError> getGeneralErrors() {
        return this.generalErrors;
    }

    @NotNull
    public final BattleActorErrors getParticipantErrors() {
        return this.participantErrors;
    }

    @Override
    @NotNull
    public BattleStartResult ifErrored(@NotNull Function1<? super ErroredBattleStart, Unit> action2) {
        Intrinsics.checkNotNullParameter(action2, (String)"action");
        action2.invoke((Object)this);
        return this;
    }

    /*
     * WARNING - void declaration
     */
    public final /* synthetic */ <T extends BattleStartError> ErroredBattleStart forError(Function1<? super T, Unit> action2) {
        void $this$filterIsInstanceTo$iv$iv;
        Intrinsics.checkNotNullParameter(action2, (String)"action");
        boolean $i$f$forError = false;
        Iterable<BattleStartError> $this$filterIsInstance$iv = this.getErrors();
        boolean $i$f$filterIsInstance = false;
        Iterable<BattleStartError> iterable = $this$filterIsInstance$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$filterIsInstanceTo = false;
        for (Object element$iv$iv : $this$filterIsInstanceTo$iv$iv) {
            Intrinsics.reifiedOperationMarker((int)3, (String)"T");
            if (!(element$iv$iv instanceof Object)) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        Iterable $this$forEach$iv = (List)destination$iv$iv;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            BattleStartError it = (BattleStartError)element$iv;
            boolean bl = false;
            action2.invoke((Object)it);
        }
        return this;
    }

    public final void sendTo(@NotNull Entity entity2, @NotNull Function1<? super MutableComponent, ? extends MutableComponent> transformer) {
        Intrinsics.checkNotNullParameter((Object)entity2, (String)"entity");
        Intrinsics.checkNotNullParameter(transformer, (String)"transformer");
        Iterable<BattleStartError> $this$forEach$iv = this.getErrors();
        boolean $i$f$forEach = false;
        Iterator<BattleStartError> iterator = $this$forEach$iv.iterator();
        while (iterator.hasNext()) {
            BattleStartError element$iv;
            BattleStartError it = element$iv = iterator.next();
            boolean bl = false;
            entity2.m_213846_((Component)transformer.invoke((Object)it.getMessageFor(entity2)));
        }
    }

    public static /* synthetic */ void sendTo$default(ErroredBattleStart erroredBattleStart, Entity entity2, Function1 function1, int n, Object object) {
        if (object != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: sendTo");
        }
        if ((n & 2) != 0) {
            function1 = sendTo.1.INSTANCE;
        }
        erroredBattleStart.sendTo(entity2, (Function1<? super MutableComponent, ? extends MutableComponent>)function1);
    }

    /*
     * WARNING - void declaration
     */
    public final /* synthetic */ <T extends BattleStartError> ErroredBattleStart ifHasError(Function0<Unit> action2) {
        void $this$filterIsInstanceTo$iv$iv;
        Intrinsics.checkNotNullParameter(action2, (String)"action");
        boolean $i$f$ifHasError = false;
        Iterable<BattleStartError> $this$filterIsInstance$iv = this.getErrors();
        boolean $i$f$filterIsInstance = false;
        Iterable<BattleStartError> iterable = $this$filterIsInstance$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$filterIsInstanceTo = false;
        for (Object element$iv$iv : $this$filterIsInstanceTo$iv$iv) {
            Intrinsics.reifiedOperationMarker((int)3, (String)"T");
            if (!(element$iv$iv instanceof Object)) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        if (!((Collection)((List)destination$iv$iv)).isEmpty()) {
            action2.invoke();
        }
        return this;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final boolean isEmpty() {
        Set it;
        if (!this.generalErrors.isEmpty()) return false;
        Collection<Set<BattleStartError>> collection = this.participantErrors.values();
        Intrinsics.checkNotNullExpressionValue(collection, (String)"participantErrors.values");
        Iterable $this$all$iv = collection;
        boolean $i$f$all = false;
        if (((Collection)$this$all$iv).isEmpty()) {
            return true;
        }
        Iterator iterator = $this$all$iv.iterator();
        do {
            if (!iterator.hasNext()) return true;
            Object element$iv = iterator.next();
            it = (Set)element$iv;
            boolean bl = false;
        } while (it.isEmpty());
        return false;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final boolean isPlayerToBlame(@NotNull ServerPlayer player) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        if (!this.generalErrors.isEmpty()) return false;
        if (this.participantErrors.size() != 1) return false;
        Set<Map.Entry<BattleActor, Set<BattleStartError>>> set2 = this.participantErrors.entrySet();
        Intrinsics.checkNotNullExpressionValue(set2, (String)"participantErrors.entries");
        Map.Entry it = (Map.Entry)CollectionsKt.first((Iterable)set2);
        boolean bl = false;
        if (!Intrinsics.areEqual((Object)((BattleActor)it.getKey()).getUuid(), (Object)player.m_20148_())) return false;
        return true;
    }

    public final boolean isSomePlayerToBlame() {
        return this.generalErrors.isEmpty() && !((Map)this.participantErrors).isEmpty();
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public final Iterable<ServerPlayer> getPlayersToBlame() {
        void $this$mapNotNullTo$iv$iv;
        Set<BattleActor> set2 = this.participantErrors.keySet();
        Intrinsics.checkNotNullExpressionValue(set2, (String)"participantErrors.keys");
        Iterable $this$mapNotNull$iv = set2;
        boolean $i$f$mapNotNull = false;
        Iterable iterable = $this$mapNotNull$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$mapNotNullTo = false;
        void $this$forEach$iv$iv$iv = $this$mapNotNullTo$iv$iv;
        boolean $i$f$forEach = false;
        Iterator iterator = $this$forEach$iv$iv$iv.iterator();
        while (iterator.hasNext()) {
            ServerPlayer it$iv$iv;
            Object element$iv$iv$iv;
            Object element$iv$iv = element$iv$iv$iv = iterator.next();
            boolean bl = false;
            BattleActor it = (BattleActor)element$iv$iv;
            boolean bl2 = false;
            if (PlayerExtensionsKt.getPlayer(it.getUuid()) == null) continue;
            boolean bl3 = false;
            destination$iv$iv.add(it$iv$iv);
        }
        return (List)destination$iv$iv;
    }

    @NotNull
    public final Iterable<BattleActor> getActorsToBlame() {
        Set<BattleActor> set2 = this.participantErrors.keySet();
        Intrinsics.checkNotNullExpressionValue(set2, (String)"participantErrors.keys");
        return set2;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public final Iterable<BattleStartError> getErrors() {
        void $this$flatMapTo$iv$iv;
        void $this$flatMap$iv;
        Map map = this.participantErrors;
        Set<BattleStartError> set2 = this.generalErrors;
        boolean $i$f$flatMap = false;
        void var3_4 = $this$flatMap$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$flatMapTo = false;
        Iterator iterator = $this$flatMapTo$iv$iv.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry element$iv$iv;
            Map.Entry it = element$iv$iv = iterator.next();
            boolean bl = false;
            Iterable list$iv$iv = (Set)it.getValue();
            CollectionsKt.addAll((Collection)destination$iv$iv, (Iterable)list$iv$iv);
        }
        return SetsKt.plus(set2, (Iterable)((List)destination$iv$iv));
    }

    public ErroredBattleStart() {
        this(null, null, 3, null);
    }
}

