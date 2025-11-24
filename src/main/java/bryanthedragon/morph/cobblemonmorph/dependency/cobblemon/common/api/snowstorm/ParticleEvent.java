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
 *  kotlin.NoWhenBranchMatchedException
 *  kotlin.Unit
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoLangRuntime;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ExpressionLike;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.Decodable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.Encodable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.EventParticleEffect;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.EventSoundEffect;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.particle.BedrockParticleEffectRepository;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.particle.ParticleStorm;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.MatrixWrapper;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.SnowstormParticle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.PrimitiveCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u0000 &2\u00020\u00012\u00020\u0002:\u0001&B+\u0012\n\b\u0002\u0010\u0017\u001a\u0004\u0018\u00010\u0016\u0012\n\b\u0002\u0010\u001e\u001a\u0004\u0018\u00010\u001d\u0012\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u000f\u00a2\u0006\u0004\b$\u0010%J\u0017\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u0017\u0010\b\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\b\u0010\u0007J\u001f\u0010\r\u001a\u00020\u00052\u0006\u0010\n\u001a\u00020\t2\b\u0010\f\u001a\u0004\u0018\u00010\u000b\u00a2\u0006\u0004\b\r\u0010\u000eR$\u0010\u0010\u001a\u0004\u0018\u00010\u000f8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R$\u0010\u0017\u001a\u0004\u0018\u00010\u00168\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0017\u0010\u0018\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001cR$\u0010\u001e\u001a\u0004\u0018\u00010\u001d8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u001e\u0010\u001f\u001a\u0004\b \u0010!\"\u0004\b\"\u0010#\u00a8\u0006'"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/ParticleEvent;", "Lcom/cobblemon/mod/common/api/net/Encodable;", "Lcom/cobblemon/mod/common/api/net/Decodable;", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "", "decode", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "encode", "Lcom/cobblemon/mod/common/client/particle/ParticleStorm;", "storm", "Lcom/cobblemon/mod/common/client/render/SnowstormParticle;", "particle", "run", "(Lcom/cobblemon/mod/common/client/particle/ParticleStorm;Lcom/cobblemon/mod/common/client/render/SnowstormParticle;)V", "Lcom/cobblemon/mod/common/api/molang/ExpressionLike;", "expression", "Lcom/cobblemon/mod/common/api/molang/ExpressionLike;", "getExpression", "()Lcom/cobblemon/mod/common/api/molang/ExpressionLike;", "setExpression", "(Lcom/cobblemon/mod/common/api/molang/ExpressionLike;)V", "Lcom/cobblemon/mod/common/api/snowstorm/EventParticleEffect;", "particleEffect", "Lcom/cobblemon/mod/common/api/snowstorm/EventParticleEffect;", "getParticleEffect", "()Lcom/cobblemon/mod/common/api/snowstorm/EventParticleEffect;", "setParticleEffect", "(Lcom/cobblemon/mod/common/api/snowstorm/EventParticleEffect;)V", "Lcom/cobblemon/mod/common/api/snowstorm/EventSoundEffect;", "soundEffect", "Lcom/cobblemon/mod/common/api/snowstorm/EventSoundEffect;", "getSoundEffect", "()Lcom/cobblemon/mod/common/api/snowstorm/EventSoundEffect;", "setSoundEffect", "(Lcom/cobblemon/mod/common/api/snowstorm/EventSoundEffect;)V", "<init>", "(Lcom/cobblemon/mod/common/api/snowstorm/EventParticleEffect;Lcom/cobblemon/mod/common/api/snowstorm/EventSoundEffect;Lcom/cobblemon/mod/common/api/molang/ExpressionLike;)V", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nParticleEvent.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ParticleEvent.kt\ncom/cobblemon/mod/common/api/snowstorm/ParticleEvent\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,174:1\n1#2:175\n*E\n"})
public final class ParticleEvent
implements Encodable,
Decodable {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @Nullable
    private EventParticleEffect particleEffect;
    @Nullable
    private EventSoundEffect soundEffect;
    @Nullable
    private ExpressionLike expression;
    @NotNull
    private static final Codec<ParticleEvent> CODEC;

    public ParticleEvent(@Nullable EventParticleEffect particleEffect, @Nullable EventSoundEffect soundEffect, @Nullable ExpressionLike expression) {
        this.particleEffect = particleEffect;
        this.soundEffect = soundEffect;
        this.expression = expression;
    }

    public /* synthetic */ ParticleEvent(EventParticleEffect eventParticleEffect, EventSoundEffect eventSoundEffect, ExpressionLike expressionLike, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 1) != 0) {
            eventParticleEffect = null;
        }
        if ((n & 2) != 0) {
            eventSoundEffect = null;
        }
        if ((n & 4) != 0) {
            expressionLike = null;
        }
        this(eventParticleEffect, eventSoundEffect, expressionLike);
    }

    @Nullable
    public final EventParticleEffect getParticleEffect() {
        return this.particleEffect;
    }

    public final void setParticleEffect(@Nullable EventParticleEffect eventParticleEffect) {
        this.particleEffect = eventParticleEffect;
    }

    @Nullable
    public final EventSoundEffect getSoundEffect() {
        return this.soundEffect;
    }

    public final void setSoundEffect(@Nullable EventSoundEffect eventSoundEffect) {
        this.soundEffect = eventSoundEffect;
    }

    @Nullable
    public final ExpressionLike getExpression() {
        return this.expression;
    }

    public final void setExpression(@Nullable ExpressionLike expressionLike) {
        this.expression = expressionLike;
    }

    @Override
    public void encode(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        buffer.m_236821_((Object)this.particleEffect, ParticleEvent::encode$lambda$1);
        buffer.m_236821_((Object)this.soundEffect, ParticleEvent::encode$lambda$2);
        buffer.m_236821_((Object)this.expression, ParticleEvent::encode$lambda$3);
    }

    @Override
    public void decode(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        this.particleEffect = (EventParticleEffect)buffer.m_236868_(ParticleEvent::decode$lambda$5);
        this.soundEffect = (EventSoundEffect)buffer.m_236868_(ParticleEvent::decode$lambda$6);
        this.expression = (ExpressionLike)buffer.m_236868_(ParticleEvent::decode$lambda$7);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     */
    public final void run(@NotNull ParticleStorm storm, @Nullable SnowstormParticle particle) {
        Intrinsics.checkNotNullParameter((Object)storm, (String)"storm");
        v0 = this.particleEffect;
        if (v0 != null) {
            effect = v0;
            $i$a$-let-ParticleEvent$run$1 = false;
            v1 = BedrockParticleEffectRepository.INSTANCE.getEffect(effect.getEffect());
            if (v1 != null) {
                bedrockParticleEffect = v1;
                switch (WhenMappings.$EnumSwitchMapping$0[effect.getType().ordinal()]) {
                    case 1: 
                    case 2: 
                    case 3: 
                    case 4: {
                        v2 = particle;
                        if (v2 != null) {
                            it = v2;
                            $i$a$-let-ParticleEvent$run$1$rootMatrix$1 = false;
                            v3 = new Vec3(it.getX(), it.getY(), it.getZ());
                        } else {
                            v3 = new Vec3(storm.getX(), storm.getY(), storm.getZ());
                        }
                        it = v3;
                        $i$a$-let-ParticleEvent$run$1$rootMatrix$2 = false;
                        ** break;
lbl22:
                        // 1 sources

                        rootMatrix = new MatrixWrapper().updatePosition((Vec3)it);
                        break;
                    }
                    default: {
                        throw new NoWhenBranchMatchedException();
                    }
                }
                switch (WhenMappings.$EnumSwitchMapping$0[effect.getType().ordinal()]) {
                    case 1: 
                    case 2: 
                    case 3: {
                        v4 = run.1.sourceVelocity.1.INSTANCE;
                        break;
                    }
                    case 4: {
                        v5 = particle;
                        if (v5 != null) {
                            it = v5;
                            $i$a$-let-ParticleEvent$run$1$sourceVelocity$2 = false;
                            v6 = new Vec3(it.getVelocityX(), it.getVelocityY(), it.getVelocityZ());
                        } else {
                            v6 = Vec3.f_82478_;
                        }
                        it = v6;
                        $i$a$-let-ParticleEvent$run$1$sourceVelocity$3 = false;
                        v4 = (Function0)new Function0<Vec3>(it){
                            final /* synthetic */ Vec3 $it;
                            {
                                this.$it = $it;
                                super(0);
                            }

                            public final Vec3 invoke() {
                                return this.$it;
                            }
                        };
                        break;
                    }
                    default: {
                        throw new NoWhenBranchMatchedException();
                    }
                }
                sourceVelocity = v4;
                var8_18 = MoLangFunctions.INSTANCE.setup(new MoLangRuntime());
                it = var8_18;
                var13_19 = run.1.newStorm.1.INSTANCE;
                var14_20 = storm.getSourceVisible();
                var15_21 = storm.getSourceAlive();
                var16_22 = sourceVelocity;
                var17_23 = storm.getWorld();
                var18_24 = rootMatrix;
                var19_25 = bedrockParticleEffect;
                $i$a$-also-ParticleEvent$run$1$newStorm$2 = false;
                v7 = it.getEnvironment().getStructs();
                Intrinsics.checkNotNullExpressionValue(v7, (String)"it.environment.structs");
                $i$a$-let-ParticleEvent$run$1$sourceVelocity$3 = v7;
                var11_16 = "query";
                v8 = storm.getRuntime().getEnvironment();
                Intrinsics.checkNotNullExpressionValue((Object)v8, (String)"storm.runtime.environment");
                var20_26 = MoLangFunctions.getQueryStruct$default(MoLangFunctions.INSTANCE, v8, null, 1, null);
                $i$a$-let-ParticleEvent$run$1$sourceVelocity$3.put(var11_16, var20_26);
                var21_27 = Unit.INSTANCE;
                var23_28 = storm.getEntity();
                var24_29 = var8_18;
                var25_30 = var13_19;
                var26_31 = var14_20;
                var27_32 = var15_21;
                var28_33 = var16_22;
                var29_34 = var17_23;
                var30_35 = var18_24;
                var31_36 = var19_25;
                newStorm = new ParticleStorm(var31_36, var30_35, var29_34, (Function0<? extends Vec3>)var28_33, var27_32, var26_31, (Function0<Unit>)var25_30, var24_29, var23_28);
                v9 = effect.getExpression();
                if (v9 != null) {
                    v9.resolve(newStorm.getRuntime());
                }
                newStorm.spawn();
            }
        }
        v10 = this.soundEffect;
        if (v10 != null) {
            effect = v10;
            $i$a$-let-ParticleEvent$run$2 = false;
            v11 = particle;
            if (v11 != null) {
                it = v11;
                $i$a$-let-ParticleEvent$run$2$position$1 = false;
                v12 = new Vec3(it.getX(), it.getY(), it.getZ());
            } else {
                v12 = new Vec3(storm.getX(), storm.getY(), storm.getZ());
            }
            position = v12;
            world = storm.getWorld();
            soundEvent = SoundEvent.m_262824_((ResourceLocation)effect.getSound());
            world.m_7785_(position.f_82479_, position.f_82480_, position.f_82481_, soundEvent, SoundSource.NEUTRAL, 1.0f, 1.0f, true);
        }
        v13 = this.expression;
        if (v13 == null) return;
        v13.resolve(storm.getRuntime());
    }

    private static final void encode$lambda$1$lambda$0(FriendlyByteBuf pb, ExpressionLike expr) {
        pb.m_130070_(expr.toString());
    }

    private static final void encode$lambda$1(FriendlyByteBuf pb, EventParticleEffect effect) {
        pb.m_130085_(effect.getEffect());
        pb.m_130068_((Enum)effect.getType());
        pb.m_236821_((Object)effect.getExpression(), ParticleEvent::encode$lambda$1$lambda$0);
    }

    private static final void encode$lambda$2(FriendlyByteBuf pb, EventSoundEffect effect) {
        pb.m_130085_(effect.getSound());
    }

    private static final void encode$lambda$3(FriendlyByteBuf pb, ExpressionLike expr) {
        pb.m_130070_(expr.toString());
    }

    private static final ExpressionLike decode$lambda$5$lambda$4(FriendlyByteBuf $pb, FriendlyByteBuf it) {
        String string = $pb.m_130277_();
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"pb.readString()");
        return MoLangExtensionsKt.asExpressionLike(string);
    }

    private static final EventParticleEffect decode$lambda$5(FriendlyByteBuf pb) {
        ResourceLocation resourceLocation = pb.m_130281_();
        Intrinsics.checkNotNullExpressionValue((Object)resourceLocation, (String)"pb.readIdentifier()");
        Enum enum_ = pb.m_130066_(EventParticleEffect.EventParticleType.class);
        Intrinsics.checkNotNullExpressionValue((Object)enum_, (String)"pb.readEnumConstant(Even\u2026ParticleType::class.java)");
        return new EventParticleEffect(resourceLocation, (EventParticleEffect.EventParticleType)enum_, (ExpressionLike)pb.m_236868_(arg_0 -> ParticleEvent.decode$lambda$5$lambda$4(pb, arg_0)));
    }

    private static final EventSoundEffect decode$lambda$6(FriendlyByteBuf pb) {
        ResourceLocation resourceLocation = pb.m_130281_();
        Intrinsics.checkNotNullExpressionValue((Object)resourceLocation, (String)"pb.readIdentifier()");
        return new EventSoundEffect(resourceLocation);
    }

    private static final ExpressionLike decode$lambda$7(FriendlyByteBuf pb) {
        String string = pb.m_130277_();
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"pb.readString()");
        return MoLangExtensionsKt.asExpressionLike(string);
    }

    private static final EventParticleEffect CODEC$lambda$20$lambda$16(ParticleEvent it) {
        return it.particleEffect;
    }

    private static final EventSoundEffect CODEC$lambda$20$lambda$17(ParticleEvent it) {
        return it.soundEffect;
    }

    private static final String CODEC$lambda$20$lambda$18(ParticleEvent it) {
        ExpressionLike expressionLike = it.expression;
        return expressionLike != null ? expressionLike.toString() : null;
    }

    private static final ParticleEvent CODEC$lambda$20$lambda$19(EventParticleEffect particleEffect, EventSoundEffect soundEffect, String expression) {
        String string = expression;
        return new ParticleEvent(particleEffect, soundEffect, string != null ? MoLangExtensionsKt.asExpressionLike(string) : null);
    }

    private static final App CODEC$lambda$20(RecordCodecBuilder.Instance instance) {
        return instance.group((App)EventParticleEffect.Companion.getCODEC().optionalFieldOf("particle_effect", null).forGetter(ParticleEvent::CODEC$lambda$20$lambda$16), (App)EventSoundEffect.Companion.getCODEC().optionalFieldOf("sound_effect", null).forGetter(ParticleEvent::CODEC$lambda$20$lambda$17), (App)PrimitiveCodec.STRING.optionalFieldOf("expression", null).forGetter(ParticleEvent::CODEC$lambda$20$lambda$18)).apply((Applicative)instance, ParticleEvent::CODEC$lambda$20$lambda$19);
    }

    public ParticleEvent() {
        this(null, null, null, 7, null);
    }

    static {
        Codec codec2 = RecordCodecBuilder.create(ParticleEvent::CODEC$lambda$20);
        Intrinsics.checkNotNullExpressionValue((Object)codec2, (String)"create { instance ->\n   \u2026)\n            }\n        }");
        CODEC = codec2;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\b\u0010\tR\u001d\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\n"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/ParticleEvent$Companion;", "", "Lcom/mojang/serialization/Codec;", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleEvent;", "CODEC", "Lcom/mojang/serialization/Codec;", "getCODEC", "()Lcom/mojang/serialization/Codec;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final Codec<ParticleEvent> getCODEC() {
            return CODEC;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }

    @Metadata(mv={1, 8, 0}, k=3, xi=48)
    public final class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] nArray = new int[EventParticleEffect.EventParticleType.values().length];
            try {
                nArray[EventParticleEffect.EventParticleType.EMITTER.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[EventParticleEffect.EventParticleType.EMITTER_BOUND.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[EventParticleEffect.EventParticleType.PARTICLE.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[EventParticleEffect.EventParticleType.PARTICLE_WITH_VELOCITY.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            $EnumSwitchMapping$0 = nArray;
        }
    }
}

