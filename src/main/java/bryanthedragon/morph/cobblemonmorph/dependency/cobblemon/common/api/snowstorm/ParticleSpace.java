/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.PrimitiveCodec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  com.mojang.serialization.codecs.RecordCodecBuilder$Instance
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.network.FriendlyByteBuf
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.PrimitiveCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0011\u0018\u0000 \u00182\u00020\u0001:\u0001\u0018B%\u0012\b\b\u0002\u0010\u000b\u001a\u00020\b\u0012\b\b\u0002\u0010\u0010\u001a\u00020\b\u0012\b\b\u0002\u0010\u0013\u001a\u00020\b\u00a2\u0006\u0004\b\u0016\u0010\u0017J\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u0015\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0007\u0010\u0006R\u0011\u0010\t\u001a\u00020\b8F\u00a2\u0006\u0006\u001a\u0004\b\t\u0010\nR\"\u0010\u000b\u001a\u00020\b8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u000b\u0010\f\u001a\u0004\b\r\u0010\n\"\u0004\b\u000e\u0010\u000fR\"\u0010\u0010\u001a\u00020\b8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0010\u0010\f\u001a\u0004\b\u0011\u0010\n\"\u0004\b\u0012\u0010\u000fR\"\u0010\u0013\u001a\u00020\b8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0013\u0010\f\u001a\u0004\b\u0014\u0010\n\"\u0004\b\u0015\u0010\u000f\u00a8\u0006\u0019"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/ParticleSpace;", "", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "", "readFromBuffer", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "writeToBuffer", "", "isLocalSpace", "()Z", "localPosition", "Z", "getLocalPosition", "setLocalPosition", "(Z)V", "localRotation", "getLocalRotation", "setLocalRotation", "localVelocity", "getLocalVelocity", "setLocalVelocity", "<init>", "(ZZZ)V", "Companion", "common"})
public final class ParticleSpace {
    @NotNull
    public static final Companion Companion = new Companion(null);
    private boolean localPosition;
    private boolean localRotation;
    private boolean localVelocity;
    @NotNull
    private static final Codec<ParticleSpace> CODEC;

    public ParticleSpace(boolean localPosition, boolean localRotation, boolean localVelocity) {
        this.localPosition = localPosition;
        this.localRotation = localRotation;
        this.localVelocity = localVelocity;
    }

    public /* synthetic */ ParticleSpace(boolean bl, boolean bl2, boolean bl3, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 1) != 0) {
            bl = false;
        }
        if ((n & 2) != 0) {
            bl2 = false;
        }
        if ((n & 4) != 0) {
            bl3 = false;
        }
        this(bl, bl2, bl3);
    }

    public final boolean getLocalPosition() {
        return this.localPosition;
    }

    public final void setLocalPosition(boolean bl) {
        this.localPosition = bl;
    }

    public final boolean getLocalRotation() {
        return this.localRotation;
    }

    public final void setLocalRotation(boolean bl) {
        this.localRotation = bl;
    }

    public final boolean getLocalVelocity() {
        return this.localVelocity;
    }

    public final void setLocalVelocity(boolean bl) {
        this.localVelocity = bl;
    }

    public final boolean isLocalSpace() {
        return this.localPosition || this.localRotation;
    }

    public final void readFromBuffer(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        this.localPosition = buffer.readBoolean();
        this.localRotation = buffer.readBoolean();
        this.localVelocity = buffer.readBoolean();
    }

    public final void writeToBuffer(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        buffer.writeBoolean(this.localPosition);
        buffer.writeBoolean(this.localRotation);
        buffer.writeBoolean(this.localVelocity);
    }

    private static final Boolean CODEC$lambda$3$lambda$0(ParticleSpace it) {
        return it.localPosition;
    }

    private static final Boolean CODEC$lambda$3$lambda$1(ParticleSpace it) {
        return it.localRotation;
    }

    private static final Boolean CODEC$lambda$3$lambda$2(ParticleSpace it) {
        return it.localVelocity;
    }

    private static final App CODEC$lambda$3(RecordCodecBuilder.Instance instance) {
        return instance.group((App)PrimitiveCodec.BOOL.fieldOf("localPosition").forGetter(ParticleSpace::CODEC$lambda$3$lambda$0), (App)PrimitiveCodec.BOOL.fieldOf("localRotation").forGetter(ParticleSpace::CODEC$lambda$3$lambda$1), (App)PrimitiveCodec.BOOL.fieldOf("localVelocity").forGetter(ParticleSpace::CODEC$lambda$3$lambda$2)).apply((Applicative)instance, ParticleSpace::new);
    }

    public ParticleSpace() {
        this(false, false, false, 7, null);
    }

    static {
        Codec codec2 = RecordCodecBuilder.create(ParticleSpace::CODEC$lambda$3);
        Intrinsics.checkNotNullExpressionValue((Object)codec2, (String)"create { instance ->\n   \u2026:ParticleSpace)\n        }");
        CODEC = codec2;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\b\u0010\tR\u001d\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\n"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/ParticleSpace$Companion;", "", "Lcom/mojang/serialization/Codec;", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleSpace;", "CODEC", "Lcom/mojang/serialization/Codec;", "getCODEC", "()Lcom/mojang/serialization/Codec;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final Codec<ParticleSpace> getCODEC() {
            return CODEC;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

