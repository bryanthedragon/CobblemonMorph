/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status.Status;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status.Statuses;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.PersistentStatus;
import com.google.gson.JsonObject;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u0000 \u001e2\u00020\u0001:\u0001\u001eB\u0019\u0012\u0006\u0010\u0018\u001a\u00020\u0017\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u0010\u00a2\u0006\u0004\b\u001c\u0010\u001dJ\r\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u0015\u0010\u0007\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\u0004\b\u0007\u0010\bJ\u0015\u0010\u000b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t\u00a2\u0006\u0004\b\u000b\u0010\fJ\r\u0010\u000e\u001a\u00020\r\u00a2\u0006\u0004\b\u000e\u0010\u000fR\"\u0010\u0011\u001a\u00020\u00108\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u0017\u0010\u0018\u001a\u00020\u00178\u0006\u00a2\u0006\f\n\u0004\b\u0018\u0010\u0019\u001a\u0004\b\u001a\u0010\u001b\u00a8\u0006\u001f"}, d2={"Lcom/cobblemon/mod/common/pokemon/status/PersistentStatusContainer;", "", "", "isExpired", "()Z", "Lcom/google/gson/JsonObject;", "json", "saveToJSON", "(Lcom/google/gson/JsonObject;)Lcom/google/gson/JsonObject;", "Lnet/minecraft/nbt/CompoundTag;", "nbt", "saveToNBT", "(Lnet/minecraft/nbt/CompoundTag;)Lnet/minecraft/nbt/CompoundTag;", "", "tickTimer", "()V", "", "secondsLeft", "I", "getSecondsLeft", "()I", "setSecondsLeft", "(I)V", "Lcom/cobblemon/mod/common/pokemon/status/PersistentStatus;", "status", "Lcom/cobblemon/mod/common/pokemon/status/PersistentStatus;", "getStatus", "()Lcom/cobblemon/mod/common/pokemon/status/PersistentStatus;", "<init>", "(Lcom/cobblemon/mod/common/pokemon/status/PersistentStatus;I)V", "Companion", "common"})
public final class PersistentStatusContainer {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final PersistentStatus status;
    private int secondsLeft;

    public PersistentStatusContainer(@NotNull PersistentStatus status, int secondsLeft) {
        Intrinsics.checkNotNullParameter((Object)status, (String)"status");
        this.status = status;
        this.secondsLeft = secondsLeft;
    }

    public /* synthetic */ PersistentStatusContainer(PersistentStatus persistentStatus, int n, int n2, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n2 & 2) != 0) {
            n = 0;
        }
        this(persistentStatus, n);
    }

    @NotNull
    public final PersistentStatus getStatus() {
        return this.status;
    }

    public final int getSecondsLeft() {
        return this.secondsLeft;
    }

    public final void setSecondsLeft(int n) {
        this.secondsLeft = n;
    }

    public final boolean isExpired() {
        return this.secondsLeft <= 0;
    }

    public final void tickTimer() {
        int n = this.secondsLeft;
        this.secondsLeft = n + -1;
    }

    @NotNull
    public final CompoundTag saveToNBT(@NotNull CompoundTag nbt) {
        Intrinsics.checkNotNullParameter((Object)nbt, (String)"nbt");
        nbt.m_128359_("StatusName", this.status.getName().toString());
        nbt.m_128405_("StatusTimer", this.secondsLeft);
        return nbt;
    }

    @NotNull
    public final JsonObject saveToJSON(@NotNull JsonObject json) {
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        json.addProperty("StatusName", this.status.getName().toString());
        json.addProperty("StatusTimer", (Number)this.secondsLeft);
        return json;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u000b\u0010\fJ\u0017\u0010\u0005\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u0017\u0010\t\u001a\u0004\u0018\u00010\u00042\u0006\u0010\b\u001a\u00020\u0007\u00a2\u0006\u0004\b\t\u0010\n\u00a8\u0006\r"}, d2={"Lcom/cobblemon/mod/common/pokemon/status/PersistentStatusContainer$Companion;", "", "Lcom/google/gson/JsonObject;", "json", "Lcom/cobblemon/mod/common/pokemon/status/PersistentStatusContainer;", "loadFromJSON", "(Lcom/google/gson/JsonObject;)Lcom/cobblemon/mod/common/pokemon/status/PersistentStatusContainer;", "Lnet/minecraft/nbt/CompoundTag;", "nbt", "loadFromNBT", "(Lnet/minecraft/nbt/CompoundTag;)Lcom/cobblemon/mod/common/pokemon/status/PersistentStatusContainer;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @Nullable
        public final PersistentStatusContainer loadFromNBT(@NotNull CompoundTag nbt) {
            Intrinsics.checkNotNullParameter((Object)nbt, (String)"nbt");
            String statusId = nbt.m_128461_("StatusName");
            int activeSeconds = nbt.m_128451_("StatusTimer");
            Intrinsics.checkNotNullExpressionValue((Object)statusId, (String)"statusId");
            if (((CharSequence)statusId).length() == 0) {
                return null;
            }
            Status status = Statuses.INSTANCE.getStatus(new ResourceLocation(statusId));
            if (status == null) {
                return null;
            }
            Status status2 = status;
            if (!(status2 instanceof PersistentStatus)) {
                return null;
            }
            return new PersistentStatusContainer((PersistentStatus)status2, activeSeconds);
        }

        @Nullable
        public final PersistentStatusContainer loadFromJSON(@NotNull JsonObject json) {
            Intrinsics.checkNotNullParameter((Object)json, (String)"json");
            String statusId = json.get("StatusName").getAsString();
            int activeSeconds = json.get("StatusTimer").getAsInt();
            Intrinsics.checkNotNullExpressionValue((Object)statusId, (String)"statusId");
            if (((CharSequence)statusId).length() == 0) {
                return null;
            }
            Status status = Statuses.INSTANCE.getStatus(new ResourceLocation(statusId));
            if (status == null) {
                return null;
            }
            Status status2 = status;
            if (!(status2 instanceof PersistentStatus)) {
                return null;
            }
            return new PersistentStatusContainer((PersistentStatus)status2, activeSeconds);
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

