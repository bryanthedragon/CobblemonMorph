/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.settings;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000b\n\u0002\b\f\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\f\u0010\rR\"\u0010\u0003\u001a\u00020\u00028\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\"\u0010\t\u001a\u00020\u00028\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\t\u0010\u0004\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\b\u00a8\u0006\u000e"}, d2={"Lcom/cobblemon/mod/common/client/settings/ServerSettings;", "", "", "displayEntityLevelLabel", "Z", "getDisplayEntityLevelLabel", "()Z", "setDisplayEntityLevelLabel", "(Z)V", "preventCompletePartyDeposit", "getPreventCompletePartyDeposit", "setPreventCompletePartyDeposit", "<init>", "()V", "common"})
public final class ServerSettings {
    @NotNull
    public static final ServerSettings INSTANCE = new ServerSettings();
    private static boolean preventCompletePartyDeposit = Cobblemon.INSTANCE.getConfig().getPreventCompletePartyDeposit();
    private static boolean displayEntityLevelLabel = Cobblemon.INSTANCE.getConfig().getDisplayEntityLevelLabel();

    private ServerSettings() {
    }

    public final boolean getPreventCompletePartyDeposit() {
        return preventCompletePartyDeposit;
    }

    public final void setPreventCompletePartyDeposit(boolean bl) {
        preventCompletePartyDeposit = bl;
    }

    public final boolean getDisplayEntityLevelLabel() {
        return displayEntityLevelLabel;
    }

    public final void setDisplayEntityLevelLabel(boolean bl) {
        displayEntityLevelLabel = bl;
    }
}

