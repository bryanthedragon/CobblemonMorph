/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.client.sounds.SoundManager
 *  net.minecraft.network.chat.Component
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.widgets;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.gui.ParentWidget;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\b&\u0018\u00002\u00020\u0001B/\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\u0006\u0010\t\u001a\u00020\u0007\u0012\u0006\u0010\n\u001a\u00020\u0007\u0012\u0006\u0010\u000b\u001a\u00020\u0007\u0012\u0006\u0010\r\u001a\u00020\f\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0010"}, d2={"Lcom/cobblemon/mod/common/client/gui/summary/widgets/SoundlessWidget;", "Lcom/cobblemon/mod/common/api/gui/ParentWidget;", "Lnet/minecraft/client/sounds/SoundManager;", "pHandler", "", "playDownSound", "(Lnet/minecraft/client/sounds/SoundManager;)V", "", "pX", "pY", "pWidth", "pHeight", "Lnet/minecraft/network/chat/Component;", "component", "<init>", "(IIIILnet/minecraft/network/chat/Component;)V", "common"})
public abstract class SoundlessWidget
extends ParentWidget {
    public SoundlessWidget(int pX, int pY, int pWidth, int pHeight, @NotNull Component component) {
        Intrinsics.checkNotNullParameter((Object)component, (String)"component");
        super(pX, pY, pWidth, pHeight, component);
    }

    public void m_7435_(@NotNull SoundManager pHandler) {
        Intrinsics.checkNotNullParameter((Object)pHandler, (String)"pHandler");
    }
}

