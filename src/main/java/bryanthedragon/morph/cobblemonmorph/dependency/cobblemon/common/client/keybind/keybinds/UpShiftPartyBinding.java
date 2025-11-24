/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.platform.InputConstants$Type
 *  kotlin.Metadata
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.keybind.keybinds;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.keybind.CobblemonKeyBinding;
import com.mojang.blaze3d.platform.InputConstants;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0004J\u000f\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0003\u0010\u0004\u00a8\u0006\u0006"}, d2={"Lcom/cobblemon/mod/common/client/keybind/keybinds/UpShiftPartyBinding;", "Lcom/cobblemon/mod/common/client/keybind/CobblemonKeyBinding;", "", "onPress", "()V", "<init>", "common"})
public final class UpShiftPartyBinding
extends CobblemonKeyBinding {
    @NotNull
    public static final UpShiftPartyBinding INSTANCE = new UpShiftPartyBinding();

    private UpShiftPartyBinding() {
        super("key.cobblemon.upshiftparty", InputConstants.Type.KEYSYM, 265, "key.cobblemon.categories.cobblemon");
    }

    @Override
    public void onPress() {
        CobblemonClient.INSTANCE.getStorage().shiftSelected(false);
    }
}

