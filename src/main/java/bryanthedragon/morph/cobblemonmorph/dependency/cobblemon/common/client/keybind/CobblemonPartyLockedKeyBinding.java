/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.platform.InputConstants$Type
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.keybind;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.keybind.CobblemonKeyBinding;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.keybind.CurrentKeyAccessorKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.keybind.keybinds.SummaryBinding;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.starter.RequestStarterScreenPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import com.mojang.blaze3d.platform.InputConstants;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\b&\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\u000b\u001a\u00020\n\u0012\u0006\u0010\r\u001a\u00020\f\u0012\u0006\u0010\u000f\u001a\u00020\u000e\u0012\u0006\u0010\u0010\u001a\u00020\n\u00a2\u0006\u0004\b\u0011\u0010\u0012J\u000f\u0010\u0003\u001a\u00020\u0002H\u0002\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u000f\u0010\u0006\u001a\u00020\u0005H\u0016\u00a2\u0006\u0004\b\u0006\u0010\u0007R\u0016\u0010\b\u001a\u00020\u00028\u0002@\u0002X\u0082\u000e\u00a2\u0006\u0006\n\u0004\b\b\u0010\t\u00a8\u0006\u0013"}, d2={"Lcom/cobblemon/mod/common/client/keybind/CobblemonPartyLockedKeyBinding;", "Lcom/cobblemon/mod/common/client/keybind/CobblemonKeyBinding;", "", "hasPartyMembers", "()Z", "", "onTick", "()V", "skippedStarterSelectionMessageShown", "Z", "", "name", "Lnet/minecraft/client/util/InputUtil$Type;", "type", "", "key", "category", "<init>", "(Ljava/lang/String;Lcom/mojang/blaze3d/platform/InputConstants$Type;ILjava/lang/String;)V", "common"})
@SourceDebugExtension(value={"SMAP\nCobblemonPartyLockedKeyBinding.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CobblemonPartyLockedKeyBinding.kt\ncom/cobblemon/mod/common/client/keybind/CobblemonPartyLockedKeyBinding\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,71:1\n1747#2,3:72\n*S KotlinDebug\n*F\n+ 1 CobblemonPartyLockedKeyBinding.kt\ncom/cobblemon/mod/common/client/keybind/CobblemonPartyLockedKeyBinding\n*L\n45#1:72,3\n*E\n"})
public abstract class CobblemonPartyLockedKeyBinding
extends CobblemonKeyBinding {
    private boolean skippedStarterSelectionMessageShown;

    public CobblemonPartyLockedKeyBinding(@NotNull String name, @NotNull InputConstants.Type type, int key, @NotNull String category) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        Intrinsics.checkNotNullParameter((Object)type, (String)"type");
        Intrinsics.checkNotNullParameter((Object)category, (String)"category");
        super(name, type, key, category);
    }

    @Override
    public void onTick() {
        if (this.m_90859_() && this.hasPartyMembers()) {
            this.onPress();
        }
    }

    private final boolean hasPartyMembers() {
        boolean bl;
        block10: {
            Iterable $this$any$iv = CobblemonClient.INSTANCE.getStorage().getMyParty().getSlots();
            boolean $i$f$any = false;
            if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                bl = false;
            } else {
                for (Object element$iv : $this$any$iv) {
                    Pokemon it = (Pokemon)element$iv;
                    boolean bl2 = false;
                    if (!(it != null)) continue;
                    bl = true;
                    break block10;
                }
                bl = false;
            }
        }
        boolean havePokemon = bl;
        boolean starterSelected = CobblemonClient.INSTANCE.getClientPlayerData().getStarterSelected();
        boolean startersLocked = CobblemonClient.INSTANCE.getClientPlayerData().getStarterLocked();
        if (!starterSelected && !havePokemon) {
            if (startersLocked) {
                LocalPlayer localPlayer = Minecraft.m_91087_().f_91074_;
                if (localPlayer != null) {
                    MutableComponent mutableComponent = LocalizationUtilsKt.lang("ui.starter.cannotchoose", new Object[0]);
                    Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"lang(\"ui.starter.cannotchoose\")");
                    localPlayer.m_5661_((Component)TextKt.red(mutableComponent), false);
                }
            } else {
                new RequestStarterScreenPacket().sendToServer();
            }
            return false;
        }
        if (!startersLocked && !starterSelected && havePokemon) {
            if (!this.skippedStarterSelectionMessageShown) {
                LocalPlayer localPlayer = Minecraft.m_91087_().f_91074_;
                if (localPlayer != null) {
                    Object[] objectArray = new Object[1];
                    Intrinsics.checkNotNullExpressionValue((Object)CurrentKeyAccessorKt.boundKey(SummaryBinding.INSTANCE).m_84875_(), (String)"SummaryBinding.boundKey().localizedText");
                    MutableComponent mutableComponent = LocalizationUtilsKt.lang("ui.starter.skippedchoosing", objectArray);
                    Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"lang(\n                  \u2026ext\n                    )");
                    localPlayer.m_5661_((Component)TextKt.yellow(mutableComponent), false);
                }
                this.skippedStarterSelectionMessageShown = true;
            }
            return true;
        }
        return true;
    }
}

