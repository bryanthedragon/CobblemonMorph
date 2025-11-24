/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.JvmStatic
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerPlayer
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonSounds;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.PlayerAdvancementData;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.PlayerDataExtension;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.starter.SetClientPlayerDataPacket;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000R\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b!\b\u0086\b\u0018\u0000 M2\u00020\u0001:\u0001MB]\u0012\u0006\u0010\u0016\u001a\u00020\u0002\u0012\u0006\u0010\u0017\u001a\u00020\u0005\u0012\u0006\u0010\u0018\u001a\u00020\u0005\u0012\u0006\u0010\u0019\u001a\u00020\u0005\u0012\b\u0010\u001a\u001a\u0004\u0018\u00010\u0002\u0012\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\f0\u000b\u0012\b\u0010\u001c\u001a\u0004\u0018\u00010\f\u0012\u0012\u0010\u001d\u001a\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u00130\u0011\u00a2\u0006\u0004\bK\u0010LJ\u0010\u0010\u0003\u001a\u00020\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u0010\u0010\u0006\u001a\u00020\u0005H\u00c6\u0003\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u0010\u0010\b\u001a\u00020\u0005H\u00c6\u0003\u00a2\u0006\u0004\b\b\u0010\u0007J\u0010\u0010\t\u001a\u00020\u0005H\u00c6\u0003\u00a2\u0006\u0004\b\t\u0010\u0007J\u0012\u0010\n\u001a\u0004\u0018\u00010\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\n\u0010\u0004J\u0016\u0010\r\u001a\b\u0012\u0004\u0012\u00020\f0\u000bH\u00c6\u0003\u00a2\u0006\u0004\b\r\u0010\u000eJ\u0012\u0010\u000f\u001a\u0004\u0018\u00010\fH\u00c6\u0003\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u001c\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u00130\u0011H\u00c6\u0003\u00a2\u0006\u0004\b\u0014\u0010\u0015Jv\u0010\u001e\u001a\u00020\u00002\b\b\u0002\u0010\u0016\u001a\u00020\u00022\b\b\u0002\u0010\u0017\u001a\u00020\u00052\b\b\u0002\u0010\u0018\u001a\u00020\u00052\b\b\u0002\u0010\u0019\u001a\u00020\u00052\n\b\u0002\u0010\u001a\u001a\u0004\u0018\u00010\u00022\u000e\b\u0002\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\n\b\u0002\u0010\u001c\u001a\u0004\u0018\u00010\f2\u0014\b\u0002\u0010\u001d\u001a\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u00130\u0011H\u00c6\u0001\u00a2\u0006\u0004\b\u001e\u0010\u001fJ\u001a\u0010!\u001a\u00020\u00052\b\u0010 \u001a\u0004\u0018\u00010\u0001H\u00d6\u0003\u00a2\u0006\u0004\b!\u0010\"J\u0010\u0010$\u001a\u00020#H\u00d6\u0001\u00a2\u0006\u0004\b$\u0010%J\u0015\u0010)\u001a\u00020(2\u0006\u0010'\u001a\u00020&\u00a2\u0006\u0004\b)\u0010*J\u0010\u0010+\u001a\u00020\u0012H\u00d6\u0001\u00a2\u0006\u0004\b+\u0010,R\"\u0010.\u001a\u00020-8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b.\u0010/\u001a\u0004\b0\u00101\"\u0004\b2\u00103R$\u0010\u001c\u001a\u0004\u0018\u00010\f8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u001c\u00104\u001a\u0004\b5\u0010\u0010\"\u0004\b6\u00107R#\u0010\u001d\u001a\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u00130\u00118\u0006\u00a2\u0006\f\n\u0004\b\u001d\u00108\u001a\u0004\b9\u0010\u0015R(\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\f0\u000b8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u001b\u0010:\u001a\u0004\b;\u0010\u000e\"\u0004\b<\u0010=R\"\u0010\u0018\u001a\u00020\u00058\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0018\u0010>\u001a\u0004\b?\u0010\u0007\"\u0004\b@\u0010AR\"\u0010\u0017\u001a\u00020\u00058\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0017\u0010>\u001a\u0004\bB\u0010\u0007\"\u0004\bC\u0010AR\"\u0010\u0019\u001a\u00020\u00058\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0019\u0010>\u001a\u0004\bD\u0010\u0007\"\u0004\bE\u0010AR$\u0010\u001a\u001a\u0004\u0018\u00010\u00028\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u001a\u0010F\u001a\u0004\bG\u0010\u0004\"\u0004\bH\u0010IR\u0017\u0010\u0016\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0016\u0010F\u001a\u0004\bJ\u0010\u0004\u00a8\u0006N"}, d2={"Lcom/cobblemon/mod/common/api/storage/player/PlayerData;", "", "Ljava/util/UUID;", "component1", "()Ljava/util/UUID;", "", "component2", "()Z", "component3", "component4", "component5", "", "Lnet/minecraft/resources/ResourceLocation;", "component6", "()Ljava/util/Set;", "component7", "()Lnet/minecraft/resources/ResourceLocation;", "", "", "Lcom/cobblemon/mod/common/api/storage/player/PlayerDataExtension;", "component8", "()Ljava/util/Map;", "uuid", "starterPrompted", "starterLocked", "starterSelected", "starterUUID", "keyItems", "battleTheme", "extraData", "copy", "(Ljava/util/UUID;ZZZLjava/util/UUID;Ljava/util/Set;Lnet/minecraft/resources/ResourceLocation;Ljava/util/Map;)Lcom/cobblemon/mod/common/api/storage/player/PlayerData;", "other", "equals", "(Ljava/lang/Object;)Z", "", "hashCode", "()I", "Lnet/minecraft/server/level/ServerPlayer;", "player", "", "sendToPlayer", "(Lnet/minecraft/server/level/ServerPlayer;)V", "toString", "()Ljava/lang/String;", "Lcom/cobblemon/mod/common/api/storage/player/PlayerAdvancementData;", "advancementData", "Lcom/cobblemon/mod/common/api/storage/player/PlayerAdvancementData;", "getAdvancementData", "()Lcom/cobblemon/mod/common/api/storage/player/PlayerAdvancementData;", "setAdvancementData", "(Lcom/cobblemon/mod/common/api/storage/player/PlayerAdvancementData;)V", "Lnet/minecraft/resources/ResourceLocation;", "getBattleTheme", "setBattleTheme", "(Lnet/minecraft/resources/ResourceLocation;)V", "Ljava/util/Map;", "getExtraData", "Ljava/util/Set;", "getKeyItems", "setKeyItems", "(Ljava/util/Set;)V", "Z", "getStarterLocked", "setStarterLocked", "(Z)V", "getStarterPrompted", "setStarterPrompted", "getStarterSelected", "setStarterSelected", "Ljava/util/UUID;", "getStarterUUID", "setStarterUUID", "(Ljava/util/UUID;)V", "getUuid", "<init>", "(Ljava/util/UUID;ZZZLjava/util/UUID;Ljava/util/Set;Lnet/minecraft/resources/ResourceLocation;Ljava/util/Map;)V", "Companion", "common"})
public final class PlayerData {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final UUID uuid;
    private boolean starterPrompted;
    private boolean starterLocked;
    private boolean starterSelected;
    @Nullable
    private UUID starterUUID;
    @NotNull
    private Set<ResourceLocation> keyItems;
    @Nullable
    private ResourceLocation battleTheme;
    @NotNull
    private final Map<String, PlayerDataExtension> extraData;
    @NotNull
    private PlayerAdvancementData advancementData;

    public PlayerData(@NotNull UUID uuid2, boolean starterPrompted, boolean starterLocked, boolean starterSelected, @Nullable UUID starterUUID, @NotNull Set<ResourceLocation> keyItems, @Nullable ResourceLocation battleTheme, @NotNull Map<String, PlayerDataExtension> extraData) {
        Intrinsics.checkNotNullParameter((Object)uuid2, (String)"uuid");
        Intrinsics.checkNotNullParameter(keyItems, (String)"keyItems");
        Intrinsics.checkNotNullParameter(extraData, (String)"extraData");
        this.uuid = uuid2;
        this.starterPrompted = starterPrompted;
        this.starterLocked = starterLocked;
        this.starterSelected = starterSelected;
        this.starterUUID = starterUUID;
        this.keyItems = keyItems;
        this.battleTheme = battleTheme;
        this.extraData = extraData;
        this.advancementData = new PlayerAdvancementData();
    }

    @NotNull
    public final UUID getUuid() {
        return this.uuid;
    }

    public final boolean getStarterPrompted() {
        return this.starterPrompted;
    }

    public final void setStarterPrompted(boolean bl) {
        this.starterPrompted = bl;
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

    @NotNull
    public final Set<ResourceLocation> getKeyItems() {
        return this.keyItems;
    }

    public final void setKeyItems(@NotNull Set<ResourceLocation> set2) {
        Intrinsics.checkNotNullParameter(set2, (String)"<set-?>");
        this.keyItems = set2;
    }

    @Nullable
    public final ResourceLocation getBattleTheme() {
        return this.battleTheme;
    }

    public final void setBattleTheme(@Nullable ResourceLocation resourceLocation) {
        this.battleTheme = resourceLocation;
    }

    @NotNull
    public final Map<String, PlayerDataExtension> getExtraData() {
        return this.extraData;
    }

    @NotNull
    public final PlayerAdvancementData getAdvancementData() {
        return this.advancementData;
    }

    public final void setAdvancementData(@NotNull PlayerAdvancementData playerAdvancementData) {
        Intrinsics.checkNotNullParameter((Object)playerAdvancementData, (String)"<set-?>");
        this.advancementData = playerAdvancementData;
    }

    public final void sendToPlayer(@NotNull ServerPlayer player) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        CobblemonNetwork.INSTANCE.sendPacket(player, new SetClientPlayerDataPacket(this, null, 2, null));
    }

    @NotNull
    public final UUID component1() {
        return this.uuid;
    }

    public final boolean component2() {
        return this.starterPrompted;
    }

    public final boolean component3() {
        return this.starterLocked;
    }

    public final boolean component4() {
        return this.starterSelected;
    }

    @Nullable
    public final UUID component5() {
        return this.starterUUID;
    }

    @NotNull
    public final Set<ResourceLocation> component6() {
        return this.keyItems;
    }

    @Nullable
    public final ResourceLocation component7() {
        return this.battleTheme;
    }

    @NotNull
    public final Map<String, PlayerDataExtension> component8() {
        return this.extraData;
    }

    @NotNull
    public final PlayerData copy(@NotNull UUID uuid2, boolean starterPrompted, boolean starterLocked, boolean starterSelected, @Nullable UUID starterUUID, @NotNull Set<ResourceLocation> keyItems, @Nullable ResourceLocation battleTheme, @NotNull Map<String, PlayerDataExtension> extraData) {
        Intrinsics.checkNotNullParameter((Object)uuid2, (String)"uuid");
        Intrinsics.checkNotNullParameter(keyItems, (String)"keyItems");
        Intrinsics.checkNotNullParameter(extraData, (String)"extraData");
        return new PlayerData(uuid2, starterPrompted, starterLocked, starterSelected, starterUUID, keyItems, battleTheme, extraData);
    }

    public static /* synthetic */ PlayerData copy$default(PlayerData playerData, UUID uUID, boolean bl, boolean bl2, boolean bl3, UUID uUID2, Set set2, ResourceLocation resourceLocation, Map map, int n, Object object) {
        if ((n & 1) != 0) {
            uUID = playerData.uuid;
        }
        if ((n & 2) != 0) {
            bl = playerData.starterPrompted;
        }
        if ((n & 4) != 0) {
            bl2 = playerData.starterLocked;
        }
        if ((n & 8) != 0) {
            bl3 = playerData.starterSelected;
        }
        if ((n & 0x10) != 0) {
            uUID2 = playerData.starterUUID;
        }
        if ((n & 0x20) != 0) {
            set2 = playerData.keyItems;
        }
        if ((n & 0x40) != 0) {
            resourceLocation = playerData.battleTheme;
        }
        if ((n & 0x80) != 0) {
            map = playerData.extraData;
        }
        return playerData.copy(uUID, bl, bl2, bl3, uUID2, set2, resourceLocation, map);
    }

    @NotNull
    public String toString() {
        return "PlayerData(uuid=" + this.uuid + ", starterPrompted=" + this.starterPrompted + ", starterLocked=" + this.starterLocked + ", starterSelected=" + this.starterSelected + ", starterUUID=" + this.starterUUID + ", keyItems=" + this.keyItems + ", battleTheme=" + this.battleTheme + ", extraData=" + this.extraData + ")";
    }

    public int hashCode() {
        int result = this.uuid.hashCode();
        int n = this.starterPrompted ? 1 : 0;
        if (n != 0) {
            n = 1;
        }
        result = result * 31 + n;
        int n2 = this.starterLocked ? 1 : 0;
        if (n2 != 0) {
            n2 = 1;
        }
        result = result * 31 + n2;
        int n3 = this.starterSelected ? 1 : 0;
        if (n3 != 0) {
            n3 = 1;
        }
        result = result * 31 + n3;
        result = result * 31 + (this.starterUUID == null ? 0 : this.starterUUID.hashCode());
        result = result * 31 + ((Object)this.keyItems).hashCode();
        result = result * 31 + (this.battleTheme == null ? 0 : this.battleTheme.hashCode());
        result = result * 31 + ((Object)this.extraData).hashCode();
        return result;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof PlayerData)) {
            return false;
        }
        PlayerData playerData = (PlayerData)other;
        if (!Intrinsics.areEqual((Object)this.uuid, (Object)playerData.uuid)) {
            return false;
        }
        if (this.starterPrompted != playerData.starterPrompted) {
            return false;
        }
        if (this.starterLocked != playerData.starterLocked) {
            return false;
        }
        if (this.starterSelected != playerData.starterSelected) {
            return false;
        }
        if (!Intrinsics.areEqual((Object)this.starterUUID, (Object)playerData.starterUUID)) {
            return false;
        }
        if (!Intrinsics.areEqual(this.keyItems, playerData.keyItems)) {
            return false;
        }
        if (!Intrinsics.areEqual((Object)this.battleTheme, (Object)playerData.battleTheme)) {
            return false;
        }
        return Intrinsics.areEqual(this.extraData, playerData.extraData);
    }

    @JvmStatic
    @NotNull
    public static final PlayerData defaultData(@NotNull UUID forPlayer) {
        return Companion.defaultData(forPlayer);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0007\u0010\bJ\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0007\u00a2\u0006\u0004\b\u0005\u0010\u0006\u00a8\u0006\t"}, d2={"Lcom/cobblemon/mod/common/api/storage/player/PlayerData$Companion;", "", "Ljava/util/UUID;", "forPlayer", "Lcom/cobblemon/mod/common/api/storage/player/PlayerData;", "defaultData", "(Ljava/util/UUID;)Lcom/cobblemon/mod/common/api/storage/player/PlayerData;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @JvmStatic
        @NotNull
        public final PlayerData defaultData(@NotNull UUID forPlayer) {
            Intrinsics.checkNotNullParameter((Object)forPlayer, (String)"forPlayer");
            return new PlayerData(forPlayer, false, !Cobblemon.INSTANCE.getStarterConfig().getAllowStarterOnJoin(), false, null, new LinkedHashSet(), CobblemonSounds.PVP_BATTLE.m_11660_(), new LinkedHashMap());
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

