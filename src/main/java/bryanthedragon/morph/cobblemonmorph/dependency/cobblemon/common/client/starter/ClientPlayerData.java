/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.starter;

import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0011\b\u0086\b\u0018\u00002\u00020\u0001B1\u0012\b\b\u0002\u0010\n\u001a\u00020\u0002\u0012\b\b\u0002\u0010\u000b\u001a\u00020\u0002\u0012\b\b\u0002\u0010\f\u001a\u00020\u0002\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\u0004\b%\u0010&J\u0010\u0010\u0003\u001a\u00020\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0005\u0010\u0004J\u0010\u0010\u0006\u001a\u00020\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0006\u0010\u0004J\u0012\u0010\b\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003\u00a2\u0006\u0004\b\b\u0010\tJ:\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\n\u001a\u00020\u00022\b\b\u0002\u0010\u000b\u001a\u00020\u00022\b\b\u0002\u0010\f\u001a\u00020\u00022\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u0007H\u00c6\u0001\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u001a\u0010\u0011\u001a\u00020\u00022\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003\u00a2\u0006\u0004\b\u0011\u0010\u0012J\u0010\u0010\u0014\u001a\u00020\u0013H\u00d6\u0001\u00a2\u0006\u0004\b\u0014\u0010\u0015J\u0010\u0010\u0017\u001a\u00020\u0016H\u00d6\u0001\u00a2\u0006\u0004\b\u0017\u0010\u0018R\"\u0010\n\u001a\u00020\u00028\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\n\u0010\u0019\u001a\u0004\b\u001a\u0010\u0004\"\u0004\b\u001b\u0010\u001cR\"\u0010\u000b\u001a\u00020\u00028\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u000b\u0010\u0019\u001a\u0004\b\u001d\u0010\u0004\"\u0004\b\u001e\u0010\u001cR\"\u0010\f\u001a\u00020\u00028\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\f\u0010\u0019\u001a\u0004\b\u001f\u0010\u0004\"\u0004\b \u0010\u001cR$\u0010\r\u001a\u0004\u0018\u00010\u00078\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\r\u0010!\u001a\u0004\b\"\u0010\t\"\u0004\b#\u0010$\u00a8\u0006'"}, d2={"Lcom/cobblemon/mod/common/client/starter/ClientPlayerData;", "", "", "component1", "()Z", "component2", "component3", "Ljava/util/UUID;", "component4", "()Ljava/util/UUID;", "promptStarter", "starterLocked", "starterSelected", "starterUUID", "copy", "(ZZZLjava/util/UUID;)Lcom/cobblemon/mod/common/client/starter/ClientPlayerData;", "other", "equals", "(Ljava/lang/Object;)Z", "", "hashCode", "()I", "", "toString", "()Ljava/lang/String;", "Z", "getPromptStarter", "setPromptStarter", "(Z)V", "getStarterLocked", "setStarterLocked", "getStarterSelected", "setStarterSelected", "Ljava/util/UUID;", "getStarterUUID", "setStarterUUID", "(Ljava/util/UUID;)V", "<init>", "(ZZZLjava/util/UUID;)V", "common"})
public final class ClientPlayerData {
    private boolean promptStarter;
    private boolean starterLocked;
    private boolean starterSelected;
    @Nullable
    private UUID starterUUID;

    public ClientPlayerData(boolean promptStarter, boolean starterLocked, boolean starterSelected, @Nullable UUID starterUUID) {
        this.promptStarter = promptStarter;
        this.starterLocked = starterLocked;
        this.starterSelected = starterSelected;
        this.starterUUID = starterUUID;
    }

    public /* synthetic */ ClientPlayerData(boolean bl, boolean bl2, boolean bl3, UUID uUID, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 1) != 0) {
            bl = true;
        }
        if ((n & 2) != 0) {
            bl2 = true;
        }
        if ((n & 4) != 0) {
            bl3 = false;
        }
        if ((n & 8) != 0) {
            uUID = null;
        }
        this(bl, bl2, bl3, uUID);
    }

    public final boolean getPromptStarter() {
        return this.promptStarter;
    }

    public final void setPromptStarter(boolean bl) {
        this.promptStarter = bl;
    }

    public final boolean getStarterLocked() {
        return this.starterLocked;
    }

    public final void setStarterLocked(boolean bl) {
        this.starterLocked = bl;
    }

    public final boolean getStarterSelected() {
        return this.starterSelected;
    }

    public final void setStarterSelected(boolean bl) {
        this.starterSelected = bl;
    }

    @Nullable
    public final UUID getStarterUUID() {
        return this.starterUUID;
    }

    public final void setStarterUUID(@Nullable UUID uUID) {
        this.starterUUID = uUID;
    }

    public final boolean component1() {
        return this.promptStarter;
    }

    public final boolean component2() {
        return this.starterLocked;
    }

    public final boolean component3() {
        return this.starterSelected;
    }

    @Nullable
    public final UUID component4() {
        return this.starterUUID;
    }

    @NotNull
    public final ClientPlayerData copy(boolean promptStarter, boolean starterLocked, boolean starterSelected, @Nullable UUID starterUUID) {
        return new ClientPlayerData(promptStarter, starterLocked, starterSelected, starterUUID);
    }

    public static /* synthetic */ ClientPlayerData copy$default(ClientPlayerData clientPlayerData, boolean bl, boolean bl2, boolean bl3, UUID uUID, int n, Object object) {
        if ((n & 1) != 0) {
            bl = clientPlayerData.promptStarter;
        }
        if ((n & 2) != 0) {
            bl2 = clientPlayerData.starterLocked;
        }
        if ((n & 4) != 0) {
            bl3 = clientPlayerData.starterSelected;
        }
        if ((n & 8) != 0) {
            uUID = clientPlayerData.starterUUID;
        }
        return clientPlayerData.copy(bl, bl2, bl3, uUID);
    }

    @NotNull
    public String toString() {
        return "ClientPlayerData(promptStarter=" + this.promptStarter + ", starterLocked=" + this.starterLocked + ", starterSelected=" + this.starterSelected + ", starterUUID=" + this.starterUUID + ")";
    }

    public int hashCode() {
        int n;
        int result;
        int n2 = this.promptStarter ? 1 : 0;
        if (n2 != 0) {
            n2 = result = 1;
        }
        if ((n = this.starterLocked) != 0) {
            n = 1;
        }
        result = result * 31 + n;
        int n3 = this.starterSelected ? 1 : 0;
        if (n3 != 0) {
            n3 = 1;
        }
        result = result * 31 + n3;
        result = result * 31 + (this.starterUUID == null ? 0 : this.starterUUID.hashCode());
        return result;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ClientPlayerData)) {
            return false;
        }
        ClientPlayerData clientPlayerData = (ClientPlayerData)other;
        if (this.promptStarter != clientPlayerData.promptStarter) {
            return false;
        }
        if (this.starterLocked != clientPlayerData.starterLocked) {
            return false;
        }
        if (this.starterSelected != clientPlayerData.starterSelected) {
            return false;
        }
        return Intrinsics.areEqual((Object)this.starterUUID, (Object)clientPlayerData.starterUUID);
    }

    public ClientPlayerData() {
        this(false, false, false, null, 15, null);
    }
}

