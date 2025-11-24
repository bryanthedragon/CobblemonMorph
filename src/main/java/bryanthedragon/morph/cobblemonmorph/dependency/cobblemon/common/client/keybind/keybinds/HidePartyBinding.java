/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.platform.InputConstants$Type
 *  kotlin.Metadata
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.keybind.keybinds;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.keybind.CobblemonKeyBinding;
import com.mojang.blaze3d.platform.InputConstants;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\b\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\f\u0010\u0004J\u000f\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0003\u0010\u0004R\"\u0010\u0006\u001a\u00020\u00058\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0006\u0010\u0007\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000b\u00a8\u0006\r"}, d2={"Lcom/cobblemon/mod/common/client/keybind/keybinds/HidePartyBinding;", "Lcom/cobblemon/mod/common/client/keybind/CobblemonKeyBinding;", "", "onPress", "()V", "", "shouldHide", "Z", "getShouldHide", "()Z", "setShouldHide", "(Z)V", "<init>", "common"})
public final class HidePartyBinding
extends CobblemonKeyBinding {
    @NotNull
    public static final HidePartyBinding INSTANCE = new HidePartyBinding();
    private static boolean shouldHide;

    private HidePartyBinding() {
        super("key.cobblemon.hideparty", InputConstants.Type.KEYSYM, 79, "key.cobblemon.categories.cobblemon");
    }

    public final boolean getShouldHide() {
        return shouldHide;
    }

    public final void setShouldHide(boolean bl) {
        shouldHide = bl;
    }

    @Override
    public void onPress() {
        shouldHide = !shouldHide;
    }
}

