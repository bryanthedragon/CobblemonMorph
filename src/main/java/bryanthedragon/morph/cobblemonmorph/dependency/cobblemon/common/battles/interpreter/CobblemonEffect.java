/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.Effect;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u000b\b\u0080\b\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\t\u001a\u00020\u0002\u0012\u0006\u0010\n\u001a\u00020\u0005\u0012\u0006\u0010\u000b\u001a\u00020\u0002\u00a2\u0006\u0004\b\u001c\u0010\u001dJ\u0010\u0010\u0003\u001a\u00020\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u0010\u0010\u0006\u001a\u00020\u0005H\u00c6\u0003\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u0010\u0010\b\u001a\u00020\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\b\u0010\u0004J.\u0010\f\u001a\u00020\u00002\b\b\u0002\u0010\t\u001a\u00020\u00022\b\b\u0002\u0010\n\u001a\u00020\u00052\b\b\u0002\u0010\u000b\u001a\u00020\u0002H\u00c6\u0001\u00a2\u0006\u0004\b\f\u0010\rJ\u001a\u0010\u0011\u001a\u00020\u00102\b\u0010\u000f\u001a\u0004\u0018\u00010\u000eH\u00d6\u0003\u00a2\u0006\u0004\b\u0011\u0010\u0012J\u0010\u0010\u0014\u001a\u00020\u0013H\u00d6\u0001\u00a2\u0006\u0004\b\u0014\u0010\u0015J\u0010\u0010\u0016\u001a\u00020\u0002H\u00d6\u0001\u00a2\u0006\u0004\b\u0016\u0010\u0004R\u001a\u0010\t\u001a\u00020\u00028\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\t\u0010\u0017\u001a\u0004\b\u0018\u0010\u0004R\u001a\u0010\u000b\u001a\u00020\u00028\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u000b\u0010\u0017\u001a\u0004\b\u0019\u0010\u0004R\u001a\u0010\n\u001a\u00020\u00058\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\n\u0010\u001a\u001a\u0004\b\u001b\u0010\u0007\u00a8\u0006\u001e"}, d2={"Lcom/cobblemon/mod/common/battles/interpreter/CobblemonEffect;", "Lcom/cobblemon/mod/common/api/battles/interpreter/Effect;", "", "component1", "()Ljava/lang/String;", "Lcom/cobblemon/mod/common/api/battles/interpreter/Effect$Type;", "component2", "()Lcom/cobblemon/mod/common/api/battles/interpreter/Effect$Type;", "component3", "id", "type", "rawData", "copy", "(Ljava/lang/String;Lcom/cobblemon/mod/common/api/battles/interpreter/Effect$Type;Ljava/lang/String;)Lcom/cobblemon/mod/common/battles/interpreter/CobblemonEffect;", "", "other", "", "equals", "(Ljava/lang/Object;)Z", "", "hashCode", "()I", "toString", "Ljava/lang/String;", "getId", "getRawData", "Lcom/cobblemon/mod/common/api/battles/interpreter/Effect$Type;", "getType", "<init>", "(Ljava/lang/String;Lcom/cobblemon/mod/common/api/battles/interpreter/Effect$Type;Ljava/lang/String;)V", "common"})
public final class CobblemonEffect
implements Effect {
    @NotNull
    private final String id;
    @NotNull
    private final Effect.Type type;
    @NotNull
    private final String rawData;

    public CobblemonEffect(@NotNull String id, @NotNull Effect.Type type, @NotNull String rawData) {
        Intrinsics.checkNotNullParameter((Object)id, (String)"id");
        Intrinsics.checkNotNullParameter((Object)((Object)type), (String)"type");
        Intrinsics.checkNotNullParameter((Object)rawData, (String)"rawData");
        this.id = id;
        this.type = type;
        this.rawData = rawData;
    }

    @Override
    @NotNull
    public String getId() {
        return this.id;
    }

    @Override
    @NotNull
    public Effect.Type getType() {
        return this.type;
    }

    @Override
    @NotNull
    public String getRawData() {
        return this.rawData;
    }

    @Override
    @NotNull
    public String getTypelessData() {
        return Effect.DefaultImpls.getTypelessData(this);
    }

    @NotNull
    public final String component1() {
        return this.id;
    }

    @NotNull
    public final Effect.Type component2() {
        return this.type;
    }

    @NotNull
    public final String component3() {
        return this.rawData;
    }

    @NotNull
    public final CobblemonEffect copy(@NotNull String id, @NotNull Effect.Type type, @NotNull String rawData) {
        Intrinsics.checkNotNullParameter((Object)id, (String)"id");
        Intrinsics.checkNotNullParameter((Object)((Object)type), (String)"type");
        Intrinsics.checkNotNullParameter((Object)rawData, (String)"rawData");
        return new CobblemonEffect(id, type, rawData);
    }

    public static /* synthetic */ CobblemonEffect copy$default(CobblemonEffect cobblemonEffect, String string, Effect.Type type, String string2, int n, Object object) {
        if ((n & 1) != 0) {
            string = cobblemonEffect.id;
        }
        if ((n & 2) != 0) {
            type = cobblemonEffect.type;
        }
        if ((n & 4) != 0) {
            string2 = cobblemonEffect.rawData;
        }
        return cobblemonEffect.copy(string, type, string2);
    }

    @NotNull
    public String toString() {
        return "CobblemonEffect(id=" + this.id + ", type=" + this.type + ", rawData=" + this.rawData + ")";
    }

    public int hashCode() {
        int result = this.id.hashCode();
        result = result * 31 + this.type.hashCode();
        result = result * 31 + this.rawData.hashCode();
        return result;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof CobblemonEffect)) {
            return false;
        }
        CobblemonEffect cobblemonEffect = (CobblemonEffect)other;
        if (!Intrinsics.areEqual((Object)this.id, (Object)cobblemonEffect.id)) {
            return false;
        }
        if (this.type != cobblemonEffect.type) {
            return false;
        }
        return Intrinsics.areEqual((Object)this.rawData, (Object)cobblemonEffect.rawData);
    }
}

