/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.platform.InputConstants$Type
 *  kotlin.Metadata
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.keybind.keybinds;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.Summary;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.keybind.CobblemonPartyLockedKeyBinding;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import com.mojang.blaze3d.platform.InputConstants;
import java.util.Collection;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0004J\u000f\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0003\u0010\u0004\u00a8\u0006\u0006"}, d2={"Lcom/cobblemon/mod/common/client/keybind/keybinds/PokeNavigatorBinding;", "Lcom/cobblemon/mod/common/client/keybind/CobblemonPartyLockedKeyBinding;", "", "onPress", "()V", "<init>", "common"})
public final class PokeNavigatorBinding
extends CobblemonPartyLockedKeyBinding {
    @NotNull
    public static final PokeNavigatorBinding INSTANCE = new PokeNavigatorBinding();

    private PokeNavigatorBinding() {
        super("key.cobblemon.pokenavigator", InputConstants.Type.KEYSYM, 78, "key.cobblemon.categories.cobblemon");
    }

    @Override
    public void onPress() {
        try {
            Summary.Companion.open((Collection<? extends Pokemon>)CobblemonClient.INSTANCE.getStorage().getMyParty().getSlots(), true, CobblemonClient.INSTANCE.getStorage().getSelectedSlot());
        }
        catch (Exception e) {
            Cobblemon.INSTANCE.getLOGGER().debug("Failed to open the summary from the PokeNav keybind", (Throwable)e);
        }
    }
}

