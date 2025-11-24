/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.battle.subscreen;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.gui.ParentWidget;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.SingleActionRequest;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.battle.BattleGUI;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\b&\u0018\u00002\u00020\u0001B?\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\f\u001a\u00020\u000b\u0012\u0006\u0010\u0011\u001a\u00020\u0010\u0012\u0006\u0010\u0012\u001a\u00020\u0010\u0012\u0006\u0010\u0013\u001a\u00020\u0010\u0012\u0006\u0010\u0014\u001a\u00020\u0010\u0012\u0006\u0010\u0016\u001a\u00020\u0015\u00a2\u0006\u0004\b\u0017\u0010\u0018R\u0017\u0010\u0003\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\n\u001a\u00020\u00078F\u00a2\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0017\u0010\f\u001a\u00020\u000b8\u0006\u00a2\u0006\f\n\u0004\b\f\u0010\r\u001a\u0004\b\u000e\u0010\u000f\u00a8\u0006\u0019"}, d2={"Lcom/cobblemon/mod/common/client/gui/battle/subscreen/BattleActionSelection;", "Lcom/cobblemon/mod/common/api/gui/ParentWidget;", "Lcom/cobblemon/mod/common/client/gui/battle/BattleGUI;", "battleGUI", "Lcom/cobblemon/mod/common/client/gui/battle/BattleGUI;", "getBattleGUI", "()Lcom/cobblemon/mod/common/client/gui/battle/BattleGUI;", "", "getOpacity", "()F", "opacity", "Lcom/cobblemon/mod/common/client/battle/SingleActionRequest;", "request", "Lcom/cobblemon/mod/common/client/battle/SingleActionRequest;", "getRequest", "()Lcom/cobblemon/mod/common/client/battle/SingleActionRequest;", "", "x", "y", "width", "height", "Lnet/minecraft/network/chat/MutableComponent;", "name", "<init>", "(Lcom/cobblemon/mod/common/client/gui/battle/BattleGUI;Lcom/cobblemon/mod/common/client/battle/SingleActionRequest;IIIILnet/minecraft/network/chat/MutableComponent;)V", "common"})
public abstract class BattleActionSelection
extends ParentWidget {
    @NotNull
    private final BattleGUI battleGUI;
    @NotNull
    private final SingleActionRequest request;

    public BattleActionSelection(@NotNull BattleGUI battleGUI, @NotNull SingleActionRequest request, int x, int y, int width, int height, @NotNull MutableComponent name) {
        Intrinsics.checkNotNullParameter((Object)((Object)battleGUI), (String)"battleGUI");
        Intrinsics.checkNotNullParameter((Object)request, (String)"request");
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        super(x, y, width, height, (Component)name);
        this.battleGUI = battleGUI;
        this.request = request;
    }

    @NotNull
    public final BattleGUI getBattleGUI() {
        return this.battleGUI;
    }

    @NotNull
    public final SingleActionRequest getRequest() {
        return this.request;
    }

    public final float getOpacity() {
        return this.battleGUI.getOpacity();
    }
}

