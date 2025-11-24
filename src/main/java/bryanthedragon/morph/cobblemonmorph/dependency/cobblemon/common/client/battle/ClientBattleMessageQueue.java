/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.util.FormattedCharSequence
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.util.FormattedCharSequence;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\b\t\u0018\u00002\u00020\u0001B\u0007\u00a2\u0006\u0004\b\u0013\u0010\u0014J\u001b\u0010\u0006\u001a\u00020\u00052\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002\u00a2\u0006\u0004\b\u0006\u0010\u0007J!\u0010\n\u001a\u00020\u00052\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00050\b\u00a2\u0006\u0004\b\n\u0010\u000bR4\u0010\r\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00050\b0\f8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u001a\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\f8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0004\u0010\u000e\u00a8\u0006\u0015"}, d2={"Lcom/cobblemon/mod/common/client/battle/ClientBattleMessageQueue;", "", "", "Lnet/minecraft/util/FormattedCharSequence;", "messages", "", "add", "(Ljava/lang/Iterable;)V", "Lkotlin/Function1;", "listener", "subscribe", "(Lkotlin/jvm/functions/Function1;)V", "", "listeners", "Ljava/util/List;", "getListeners", "()Ljava/util/List;", "setListeners", "(Ljava/util/List;)V", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nClientBattleMessageQueue.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ClientBattleMessageQueue.kt\ncom/cobblemon/mod/common/client/battle/ClientBattleMessageQueue\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,34:1\n1855#2,2:35\n1855#2,2:37\n*S KotlinDebug\n*F\n+ 1 ClientBattleMessageQueue.kt\ncom/cobblemon/mod/common/client/battle/ClientBattleMessageQueue\n*L\n27#1:35,2\n32#1:37,2\n*E\n"})
public final class ClientBattleMessageQueue {
    @NotNull
    private List<Function1<FormattedCharSequence, Unit>> listeners = new ArrayList();
    @NotNull
    private final List<FormattedCharSequence> messages = new ArrayList();

    @NotNull
    public final List<Function1<FormattedCharSequence, Unit>> getListeners() {
        return this.listeners;
    }

    public final void setListeners(@NotNull List<Function1<FormattedCharSequence, Unit>> list) {
        Intrinsics.checkNotNullParameter(list, (String)"<set-?>");
        this.listeners = list;
    }

    public final void add(@NotNull Iterable<? extends FormattedCharSequence> messages) {
        Intrinsics.checkNotNullParameter(messages, (String)"messages");
        CollectionsKt.addAll((Collection)this.messages, messages);
        Iterable $this$forEach$iv = this.listeners;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            Function1 listener = (Function1)element$iv;
            boolean bl = false;
            Iterable<? extends FormattedCharSequence> $this$forEach$iv2 = messages;
            boolean $i$f$forEach2 = false;
            for (FormattedCharSequence formattedCharSequence : $this$forEach$iv2) {
                listener.invoke((Object)formattedCharSequence);
            }
        }
    }

    public final void subscribe(@NotNull Function1<? super FormattedCharSequence, Unit> listener) {
        Intrinsics.checkNotNullParameter(listener, (String)"listener");
        this.listeners.add(listener);
        Iterable $this$forEach$iv = this.messages;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            listener.invoke(element$iv);
        }
    }
}

