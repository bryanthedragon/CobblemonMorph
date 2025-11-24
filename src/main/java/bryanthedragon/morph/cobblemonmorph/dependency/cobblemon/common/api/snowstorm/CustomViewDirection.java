/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.DataResult
 *  com.mojang.serialization.DynamicOps
 *  com.mojang.serialization.codecs.PrimitiveCodec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  com.mojang.serialization.codecs.RecordCodecBuilder$Instance
 *  kotlin.Metadata
 *  kotlin.Triple
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.Expression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoLangRuntime;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleViewDirection;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleViewDirectionType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.codec.ExpressionCodecKt;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.codecs.PrimitiveCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import kotlin.Metadata;
import kotlin.Triple;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u0000 #2\u00020\u0001:\u0001#B!\u0012\u0018\u0010\u0018\u001a\u0014\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u00170\u0016\u00a2\u0006\u0004\b\"\u0010\u001cJG\u0010\u0007\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00018\u00008\u0000 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00018\u00008\u0000\u0018\u00010\u00050\u0005\"\u0004\b\u0000\u0010\u00022\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0003H\u0016\u00a2\u0006\u0004\b\u0007\u0010\bJ'\u0010\u000e\u001a\u00020\u000b2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000bH\u0016\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u0017\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0011\u001a\u00020\u0010H\u0016\u00a2\u0006\u0004\b\u0013\u0010\u0014J\u0017\u0010\u0015\u001a\u00020\u00122\u0006\u0010\u0011\u001a\u00020\u0010H\u0016\u00a2\u0006\u0004\b\u0015\u0010\u0014R4\u0010\u0018\u001a\u0014\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u00170\u00168\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0018\u0010\u0019\u001a\u0004\b\u000e\u0010\u001a\"\u0004\b\u001b\u0010\u001cR\u001a\u0010\u001e\u001a\u00020\u001d8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u001e\u0010\u001f\u001a\u0004\b \u0010!\u00a8\u0006$"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/CustomViewDirection;", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleViewDirection;", "T", "Lcom/mojang/serialization/DynamicOps;", "ops", "Lcom/mojang/serialization/DataResult;", "kotlin.jvm.PlatformType", "encode", "(Lcom/mojang/serialization/DynamicOps;)Lcom/mojang/serialization/DataResult;", "Lcom/bedrockk/molang/runtime/MoLangRuntime;", "runtime", "Lnet/minecraft/world/phys/Vec3;", "lastDirection", "currentVelocity", "getDirection", "(Lcom/bedrockk/molang/runtime/MoLangRuntime;Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/Vec3;)Lnet/minecraft/world/phys/Vec3;", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "", "readFromBuffer", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "writeToBuffer", "Lkotlin/Triple;", "Lcom/bedrockk/molang/Expression;", "direction", "Lkotlin/Triple;", "()Lkotlin/Triple;", "setDirection", "(Lkotlin/Triple;)V", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleViewDirectionType;", "type", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleViewDirectionType;", "getType", "()Lcom/cobblemon/mod/common/api/snowstorm/ParticleViewDirectionType;", "<init>", "Companion", "common"})
public final class CustomViewDirection
implements ParticleViewDirection {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private Triple<? extends Expression, ? extends Expression, ? extends Expression> direction;
    @NotNull
    private final ParticleViewDirectionType type;
    @NotNull
    private static final Codec<CustomViewDirection> CODEC;

    public CustomViewDirection(@NotNull Triple<? extends Expression, ? extends Expression, ? extends Expression> direction) {
        Intrinsics.checkNotNullParameter(direction, (String)"direction");
        this.direction = direction;
        this.type = ParticleViewDirectionType.CUSTOM;
    }

    @NotNull
    public final Triple<Expression, Expression, Expression> getDirection() {
        return this.direction;
    }

    public final void setDirection(@NotNull Triple<? extends Expression, ? extends Expression, ? extends Expression> triple) {
        Intrinsics.checkNotNullParameter(triple, (String)"<set-?>");
        this.direction = triple;
    }

    @Override
    @NotNull
    public ParticleViewDirectionType getType() {
        return this.type;
    }

    @Override
    public <T> DataResult<T> encode(@NotNull DynamicOps<T> ops) {
        Intrinsics.checkNotNullParameter(ops, (String)"ops");
        return CODEC.encodeStart(ops, (Object)this);
    }

    @Override
    public void writeToBuffer(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        buffer.m_130070_(MoLangExtensionsKt.getString((Expression)this.direction.getFirst()));
        buffer.m_130070_(MoLangExtensionsKt.getString((Expression)this.direction.getSecond()));
        buffer.m_130070_(MoLangExtensionsKt.getString((Expression)this.direction.getThird()));
    }

    @Override
    public void readFromBuffer(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        String string = buffer.m_130277_();
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"buffer.readString()");
        Expression expression = MoLangExtensionsKt.asExpression(string);
        String string2 = buffer.m_130277_();
        Intrinsics.checkNotNullExpressionValue((Object)string2, (String)"buffer.readString()");
        Expression expression2 = MoLangExtensionsKt.asExpression(string2);
        String string3 = buffer.m_130277_();
        Intrinsics.checkNotNullExpressionValue((Object)string3, (String)"buffer.readString()");
        this.direction = new Triple((Object)expression, (Object)expression2, (Object)MoLangExtensionsKt.asExpression(string3));
    }

    @Override
    @NotNull
    public Vec3 getDirection(@NotNull MoLangRuntime runtime2, @NotNull Vec3 lastDirection, @NotNull Vec3 currentVelocity) {
        Intrinsics.checkNotNullParameter((Object)runtime2, (String)"runtime");
        Intrinsics.checkNotNullParameter((Object)lastDirection, (String)"lastDirection");
        Intrinsics.checkNotNullParameter((Object)currentVelocity, (String)"currentVelocity");
        return MoLangExtensionsKt.resolveVec3d(runtime2, this.direction);
    }

    private static final String CODEC$lambda$5$lambda$0(CustomViewDirection it) {
        return it.getType().name();
    }

    private static final Expression CODEC$lambda$5$lambda$1(CustomViewDirection it) {
        return (Expression)it.direction.getFirst();
    }

    private static final Expression CODEC$lambda$5$lambda$2(CustomViewDirection it) {
        return (Expression)it.direction.getSecond();
    }

    private static final Expression CODEC$lambda$5$lambda$3(CustomViewDirection it) {
        return (Expression)it.direction.getThird();
    }

    private static final CustomViewDirection CODEC$lambda$5$lambda$4(String string, Expression directionX, Expression directionY, Expression directionZ) {
        return new CustomViewDirection((Triple<? extends Expression, ? extends Expression, ? extends Expression>)new Triple((Object)directionX, (Object)directionY, (Object)directionZ));
    }

    private static final App CODEC$lambda$5(RecordCodecBuilder.Instance instance) {
        return instance.group((App)PrimitiveCodec.STRING.fieldOf("type").forGetter(CustomViewDirection::CODEC$lambda$5$lambda$0), (App)ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("directionX").forGetter(CustomViewDirection::CODEC$lambda$5$lambda$1), (App)ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("directionY").forGetter(CustomViewDirection::CODEC$lambda$5$lambda$2), (App)ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("directionZ").forGetter(CustomViewDirection::CODEC$lambda$5$lambda$3)).apply((Applicative)instance, CustomViewDirection::CODEC$lambda$5$lambda$4);
    }

    static {
        Codec codec2 = RecordCodecBuilder.create(CustomViewDirection::CODEC$lambda$5);
        Intrinsics.checkNotNullExpressionValue((Object)codec2, (String)"create { instance ->\n   \u2026 directionZ)) }\n        }");
        CODEC = codec2;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\b\u0010\tR\u001d\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\n"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/CustomViewDirection$Companion;", "", "Lcom/mojang/serialization/Codec;", "Lcom/cobblemon/mod/common/api/snowstorm/CustomViewDirection;", "CODEC", "Lcom/mojang/serialization/Codec;", "getCODEC", "()Lcom/mojang/serialization/Codec;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final Codec<CustomViewDirection> getCODEC() {
            return CODEC;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

