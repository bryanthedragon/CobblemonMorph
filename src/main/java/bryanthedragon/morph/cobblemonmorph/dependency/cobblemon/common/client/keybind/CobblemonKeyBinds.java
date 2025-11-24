/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.client.KeyMapping
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.keybind;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.Observable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.keybind.CobblemonKeyBinding;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.keybind.keybinds.DebugKeybindings;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.keybind.keybinds.DownShiftPartyBinding;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.keybind.keybinds.HidePartyBinding;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.keybind.keybinds.PartySendBinding;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.keybind.keybinds.SummaryBinding;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.keybind.keybinds.UpShiftPartyBinding;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.PlatformEvents;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.client.KeyMapping;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u001e\u0010\u0004J\u000f\u0010\u0003\u001a\u00020\u0002H\u0002\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u0017\u0010\b\u001a\u00020\u00072\u0006\u0010\u0006\u001a\u00020\u0005H\u0002\u00a2\u0006\u0004\b\b\u0010\tJ!\u0010\f\u001a\u00020\u00022\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00020\n\u00a2\u0006\u0004\b\f\u0010\rR\u0017\u0010\u000e\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\u0010\u0010\u0011R\u0017\u0010\u0012\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\u0012\u0010\u000f\u001a\u0004\b\u0013\u0010\u0011R\u0017\u0010\u0014\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\u0014\u0010\u000f\u001a\u0004\b\u0015\u0010\u0011R\u0017\u0010\u0016\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\u0016\u0010\u000f\u001a\u0004\b\u0017\u0010\u0011R\u0017\u0010\u0018\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\u0018\u0010\u000f\u001a\u0004\b\u0019\u0010\u0011R$\u0010\u001c\u001a\u0012\u0012\u0004\u0012\u00020\u00050\u001aj\b\u0012\u0004\u0012\u00020\u0005`\u001b8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u001c\u0010\u001d\u00a8\u0006\u001f"}, d2={"Lcom/cobblemon/mod/common/client/keybind/CobblemonKeyBinds;", "", "", "onTick", "()V", "Lcom/cobblemon/mod/common/client/keybind/CobblemonKeyBinding;", "keyBinding", "Lnet/minecraft/client/KeyMapping;", "queue", "(Lcom/cobblemon/mod/common/client/keybind/CobblemonKeyBinding;)Lnet/minecraft/client/KeyMapping;", "Lkotlin/Function1;", "registrar", "register", "(Lkotlin/jvm/functions/Function1;)V", "HIDE_PARTY", "Lnet/minecraft/client/KeyMapping;", "getHIDE_PARTY", "()Lnet/minecraft/client/KeyMapping;", "PARTY_OVERLAY_DOWN", "getPARTY_OVERLAY_DOWN", "PARTY_OVERLAY_UP", "getPARTY_OVERLAY_UP", "SEND_OUT_POKEMON", "getSEND_OUT_POKEMON", "SUMMARY", "getSUMMARY", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "keyBinds", "Ljava/util/ArrayList;", "<init>", "common"})
@SourceDebugExtension(value={"SMAP\nCobblemonKeyBinds.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CobblemonKeyBinds.kt\ncom/cobblemon/mod/common/client/keybind/CobblemonKeyBinds\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,62:1\n1855#2,2:63\n1855#2,2:65\n1855#2,2:67\n*S KotlinDebug\n*F\n+ 1 CobblemonKeyBinds.kt\ncom/cobblemon/mod/common/client/keybind/CobblemonKeyBinds\n*L\n49#1:63,2\n54#1:65,2\n34#1:67,2\n*E\n"})
public final class CobblemonKeyBinds {
    @NotNull
    public static final CobblemonKeyBinds INSTANCE = new CobblemonKeyBinds();
    @NotNull
    private static final ArrayList<CobblemonKeyBinding> keyBinds = new ArrayList();
    @NotNull
    private static final KeyMapping HIDE_PARTY;
    @NotNull
    private static final KeyMapping SUMMARY;
    @NotNull
    private static final KeyMapping PARTY_OVERLAY_DOWN;
    @NotNull
    private static final KeyMapping PARTY_OVERLAY_UP;
    @NotNull
    private static final KeyMapping SEND_OUT_POKEMON;

    private CobblemonKeyBinds() {
    }

    @NotNull
    public final KeyMapping getHIDE_PARTY() {
        return HIDE_PARTY;
    }

    @NotNull
    public final KeyMapping getSUMMARY() {
        return SUMMARY;
    }

    @NotNull
    public final KeyMapping getPARTY_OVERLAY_DOWN() {
        return PARTY_OVERLAY_DOWN;
    }

    @NotNull
    public final KeyMapping getPARTY_OVERLAY_UP() {
        return PARTY_OVERLAY_UP;
    }

    @NotNull
    public final KeyMapping getSEND_OUT_POKEMON() {
        return SEND_OUT_POKEMON;
    }

    public final void register(@NotNull Function1<? super KeyMapping, Unit> registrar) {
        Intrinsics.checkNotNullParameter(registrar, (String)"registrar");
        Iterable $this$forEach$iv = keyBinds;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            KeyMapping p0 = (KeyMapping)element$iv;
            boolean bl = false;
            registrar.invoke((Object)p0);
        }
    }

    private final void onTick() {
        Iterable $this$forEach$iv = keyBinds;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            CobblemonKeyBinding p0 = (CobblemonKeyBinding)((Object)element$iv);
            boolean bl = false;
            p0.onTick();
        }
    }

    private final KeyMapping queue(CobblemonKeyBinding keyBinding) {
        keyBinds.add(keyBinding);
        return keyBinding;
    }

    static {
        Observable.DefaultImpls.subscribe$default(PlatformEvents.CLIENT_TICK_POST, null, 1.INSTANCE, 1, null);
        if (Cobblemon.INSTANCE.getConfig().getEnableDebugKeys()) {
            Iterable $this$forEach$iv = DebugKeybindings.INSTANCE.getKeybindings();
            boolean $i$f$forEach = false;
            for (Object element$iv : $this$forEach$iv) {
                CobblemonKeyBinding it = (CobblemonKeyBinding)((Object)element$iv);
                boolean bl = false;
                INSTANCE.queue(it);
            }
        }
        HIDE_PARTY = INSTANCE.queue(HidePartyBinding.INSTANCE);
        SUMMARY = INSTANCE.queue(SummaryBinding.INSTANCE);
        PARTY_OVERLAY_DOWN = INSTANCE.queue(DownShiftPartyBinding.INSTANCE);
        PARTY_OVERLAY_UP = INSTANCE.queue(UpShiftPartyBinding.INSTANCE);
        SEND_OUT_POKEMON = INSTANCE.queue(PartySendBinding.INSTANCE);
    }
}

