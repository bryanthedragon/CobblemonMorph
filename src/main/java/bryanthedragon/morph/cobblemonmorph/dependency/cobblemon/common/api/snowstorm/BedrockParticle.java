/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.ListCodec
 *  com.mojang.serialization.codecs.PrimitiveCodec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  com.mojang.serialization.codecs.RecordCodecBuilder$Instance
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.Expression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.MoLang;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.ast.BooleanExpression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.ast.NumberExpression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.codec.CodecMapped;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.DynamicParticleRotation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.EventTriggerTimeline;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ExpressionParticleTinting;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.FromMotionViewDirection;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleCameraMode;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleCollision;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleMaterial;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleMotion;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleRotation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleTinting;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleUVMode;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleViewDirection;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.RotateXYZCameraMode;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.SimpleEventTrigger;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.StaticParticleMotion;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.StaticParticleUVMode;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.codec.ExpressionCodecKt;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.ListCodec;
import com.mojang.serialization.codecs.PrimitiveCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0082\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\f\u0018\u0000 x2\u00020\u0001:\u0003xyzB\u00dd\u0001\u0012\b\b\u0002\u0010Q\u001a\u00020P\u0012\b\b\u0002\u00100\u001a\u00020/\u0012\b\b\u0002\u0010i\u001a\u00020h\u0012\b\b\u0002\u0010J\u001a\u00020(\u0012\b\b\u0002\u0010M\u001a\u00020(\u0012\b\b\u0002\u00106\u001a\u00020(\u0012\b\b\u0002\u0010)\u001a\u00020(\u0012\u000e\b\u0002\u0010e\u001a\b\u0012\u0004\u0012\u00020(0\u0016\u0012\u000e\b\u0002\u0010@\u001a\b\u0012\u0004\u0012\u00020(0\u0016\u0012\b\b\u0002\u0010:\u001a\u000209\u0012\b\b\u0002\u0010D\u001a\u00020C\u0012\b\b\u0002\u0010p\u001a\u00020o\u0012\b\b\u0002\u0010\t\u001a\u00020\b\u0012\b\b\u0002\u0010_\u001a\u00020^\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u000f\u0012\b\b\u0002\u0010\u001f\u001a\u00020\u001e\u0012\u000e\b\u0002\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00170\u0016\u0012\u000e\b\u0002\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00170\u0016\u0012\b\b\u0002\u0010X\u001a\u00020W\u00a2\u0006\u0004\bv\u0010wJ\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u0015\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0007\u0010\u0006R\"\u0010\t\u001a\u00020\b8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\t\u0010\n\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\"\u0010\u0010\u001a\u00020\u000f8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R(\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00170\u00168\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0018\u0010\u0019\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR\"\u0010\u001f\u001a\u00020\u001e8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u001f\u0010 \u001a\u0004\b!\u0010\"\"\u0004\b#\u0010$R(\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00170\u00168\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b%\u0010\u0019\u001a\u0004\b&\u0010\u001b\"\u0004\b'\u0010\u001dR\"\u0010)\u001a\u00020(8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b)\u0010*\u001a\u0004\b+\u0010,\"\u0004\b-\u0010.R\"\u00100\u001a\u00020/8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b0\u00101\u001a\u0004\b2\u00103\"\u0004\b4\u00105R\"\u00106\u001a\u00020(8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b6\u0010*\u001a\u0004\b7\u0010,\"\u0004\b8\u0010.R\"\u0010:\u001a\u0002098\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b:\u0010;\u001a\u0004\b<\u0010=\"\u0004\b>\u0010?R(\u0010@\u001a\b\u0012\u0004\u0012\u00020(0\u00168\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b@\u0010\u0019\u001a\u0004\bA\u0010\u001b\"\u0004\bB\u0010\u001dR\"\u0010D\u001a\u00020C8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bD\u0010E\u001a\u0004\bF\u0010G\"\u0004\bH\u0010IR\"\u0010J\u001a\u00020(8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bJ\u0010*\u001a\u0004\bK\u0010,\"\u0004\bL\u0010.R\"\u0010M\u001a\u00020(8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bM\u0010*\u001a\u0004\bN\u0010,\"\u0004\bO\u0010.R\"\u0010Q\u001a\u00020P8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bQ\u0010R\u001a\u0004\bS\u0010T\"\u0004\bU\u0010VR\"\u0010X\u001a\u00020W8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bX\u0010Y\u001a\u0004\bZ\u0010[\"\u0004\b\\\u0010]R\"\u0010_\u001a\u00020^8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b_\u0010`\u001a\u0004\ba\u0010b\"\u0004\bc\u0010dR(\u0010e\u001a\b\u0012\u0004\u0012\u00020(0\u00168\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\be\u0010\u0019\u001a\u0004\bf\u0010\u001b\"\u0004\bg\u0010\u001dR\"\u0010i\u001a\u00020h8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bi\u0010j\u001a\u0004\bk\u0010l\"\u0004\bm\u0010nR\"\u0010p\u001a\u00020o8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bp\u0010q\u001a\u0004\br\u0010s\"\u0004\bt\u0010u\u00a8\u0006{"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/BedrockParticle;", "", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "", "readFromBuffer", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "writeToBuffer", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleCameraMode;", "cameraMode", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleCameraMode;", "getCameraMode", "()Lcom/cobblemon/mod/common/api/snowstorm/ParticleCameraMode;", "setCameraMode", "(Lcom/cobblemon/mod/common/api/snowstorm/ParticleCameraMode;)V", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleCollision;", "collision", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleCollision;", "getCollision", "()Lcom/cobblemon/mod/common/api/snowstorm/ParticleCollision;", "setCollision", "(Lcom/cobblemon/mod/common/api/snowstorm/ParticleCollision;)V", "", "Lcom/cobblemon/mod/common/api/snowstorm/SimpleEventTrigger;", "creationEvents", "Ljava/util/List;", "getCreationEvents", "()Ljava/util/List;", "setCreationEvents", "(Ljava/util/List;)V", "", "environmentLighting", "Z", "getEnvironmentLighting", "()Z", "setEnvironmentLighting", "(Z)V", "expirationEvents", "getExpirationEvents", "setExpirationEvents", "Lcom/bedrockk/molang/Expression;", "killExpression", "Lcom/bedrockk/molang/Expression;", "getKillExpression", "()Lcom/bedrockk/molang/Expression;", "setKillExpression", "(Lcom/bedrockk/molang/Expression;)V", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleMaterial;", "material", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleMaterial;", "getMaterial", "()Lcom/cobblemon/mod/common/api/snowstorm/ParticleMaterial;", "setMaterial", "(Lcom/cobblemon/mod/common/api/snowstorm/ParticleMaterial;)V", "maxAge", "getMaxAge", "setMaxAge", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleMotion;", "motion", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleMotion;", "getMotion", "()Lcom/cobblemon/mod/common/api/snowstorm/ParticleMotion;", "setMotion", "(Lcom/cobblemon/mod/common/api/snowstorm/ParticleMotion;)V", "renderExpressions", "getRenderExpressions", "setRenderExpressions", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleRotation;", "rotation", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleRotation;", "getRotation", "()Lcom/cobblemon/mod/common/api/snowstorm/ParticleRotation;", "setRotation", "(Lcom/cobblemon/mod/common/api/snowstorm/ParticleRotation;)V", "sizeX", "getSizeX", "setSizeX", "sizeY", "getSizeY", "setSizeY", "Lnet/minecraft/resources/ResourceLocation;", "texture", "Lnet/minecraft/resources/ResourceLocation;", "getTexture", "()Lnet/minecraft/resources/ResourceLocation;", "setTexture", "(Lnet/minecraft/resources/ResourceLocation;)V", "Lcom/cobblemon/mod/common/api/snowstorm/EventTriggerTimeline;", "timeline", "Lcom/cobblemon/mod/common/api/snowstorm/EventTriggerTimeline;", "getTimeline", "()Lcom/cobblemon/mod/common/api/snowstorm/EventTriggerTimeline;", "setTimeline", "(Lcom/cobblemon/mod/common/api/snowstorm/EventTriggerTimeline;)V", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleTinting;", "tinting", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleTinting;", "getTinting", "()Lcom/cobblemon/mod/common/api/snowstorm/ParticleTinting;", "setTinting", "(Lcom/cobblemon/mod/common/api/snowstorm/ParticleTinting;)V", "updateExpressions", "getUpdateExpressions", "setUpdateExpressions", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleUVMode;", "uvMode", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleUVMode;", "getUvMode", "()Lcom/cobblemon/mod/common/api/snowstorm/ParticleUVMode;", "setUvMode", "(Lcom/cobblemon/mod/common/api/snowstorm/ParticleUVMode;)V", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleViewDirection;", "viewDirection", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleViewDirection;", "getViewDirection", "()Lcom/cobblemon/mod/common/api/snowstorm/ParticleViewDirection;", "setViewDirection", "(Lcom/cobblemon/mod/common/api/snowstorm/ParticleViewDirection;)V", "<init>", "(Lnet/minecraft/resources/ResourceLocation;Lcom/cobblemon/mod/common/api/snowstorm/ParticleMaterial;Lcom/cobblemon/mod/common/api/snowstorm/ParticleUVMode;Lcom/bedrockk/molang/Expression;Lcom/bedrockk/molang/Expression;Lcom/bedrockk/molang/Expression;Lcom/bedrockk/molang/Expression;Ljava/util/List;Ljava/util/List;Lcom/cobblemon/mod/common/api/snowstorm/ParticleMotion;Lcom/cobblemon/mod/common/api/snowstorm/ParticleRotation;Lcom/cobblemon/mod/common/api/snowstorm/ParticleViewDirection;Lcom/cobblemon/mod/common/api/snowstorm/ParticleCameraMode;Lcom/cobblemon/mod/common/api/snowstorm/ParticleTinting;Lcom/cobblemon/mod/common/api/snowstorm/ParticleCollision;ZLjava/util/List;Ljava/util/List;Lcom/cobblemon/mod/common/api/snowstorm/EventTriggerTimeline;)V", "Companion", "EventSet", "ExpressionSet", "common"})
@SourceDebugExtension(value={"SMAP\nBedrockParticle.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BedrockParticle.kt\ncom/cobblemon/mod/common/api/snowstorm/BedrockParticle\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,194:1\n1#2:195\n*E\n"})
public final class BedrockParticle {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private ResourceLocation texture;
    @NotNull
    private ParticleMaterial material;
    @NotNull
    private ParticleUVMode uvMode;
    @NotNull
    private Expression sizeX;
    @NotNull
    private Expression sizeY;
    @NotNull
    private Expression maxAge;
    @NotNull
    private Expression killExpression;
    @NotNull
    private List<Expression> updateExpressions;
    @NotNull
    private List<Expression> renderExpressions;
    @NotNull
    private ParticleMotion motion;
    @NotNull
    private ParticleRotation rotation;
    @NotNull
    private ParticleViewDirection viewDirection;
    @NotNull
    private ParticleCameraMode cameraMode;
    @NotNull
    private ParticleTinting tinting;
    @NotNull
    private ParticleCollision collision;
    private boolean environmentLighting;
    @NotNull
    private List<SimpleEventTrigger> creationEvents;
    @NotNull
    private List<SimpleEventTrigger> expirationEvents;
    @NotNull
    private EventTriggerTimeline timeline;
    private static final Codec<ExpressionSet> EXPRESSION_SET_CODEC = RecordCodecBuilder.create(BedrockParticle::EXPRESSION_SET_CODEC$lambda$16);
    private static final Codec<EventSet> EVENT_SET_CODEC = RecordCodecBuilder.create(BedrockParticle::EVENT_SET_CODEC$lambda$20);
    @NotNull
    private static final Codec<BedrockParticle> CODEC;

    public BedrockParticle(@NotNull ResourceLocation texture, @NotNull ParticleMaterial material, @NotNull ParticleUVMode uvMode, @NotNull Expression sizeX, @NotNull Expression sizeY, @NotNull Expression maxAge, @NotNull Expression killExpression, @NotNull List<Expression> updateExpressions, @NotNull List<Expression> renderExpressions, @NotNull ParticleMotion motion, @NotNull ParticleRotation rotation, @NotNull ParticleViewDirection viewDirection, @NotNull ParticleCameraMode cameraMode, @NotNull ParticleTinting tinting, @NotNull ParticleCollision collision, boolean environmentLighting, @NotNull List<SimpleEventTrigger> creationEvents, @NotNull List<SimpleEventTrigger> expirationEvents, @NotNull EventTriggerTimeline timeline) {
        Intrinsics.checkNotNullParameter((Object)texture, (String)"texture");
        Intrinsics.checkNotNullParameter((Object)((Object)material), (String)"material");
        Intrinsics.checkNotNullParameter((Object)uvMode, (String)"uvMode");
        Intrinsics.checkNotNullParameter((Object)sizeX, (String)"sizeX");
        Intrinsics.checkNotNullParameter((Object)sizeY, (String)"sizeY");
        Intrinsics.checkNotNullParameter((Object)maxAge, (String)"maxAge");
        Intrinsics.checkNotNullParameter((Object)killExpression, (String)"killExpression");
        Intrinsics.checkNotNullParameter(updateExpressions, (String)"updateExpressions");
        Intrinsics.checkNotNullParameter(renderExpressions, (String)"renderExpressions");
        Intrinsics.checkNotNullParameter((Object)motion, (String)"motion");
        Intrinsics.checkNotNullParameter((Object)rotation, (String)"rotation");
        Intrinsics.checkNotNullParameter((Object)viewDirection, (String)"viewDirection");
        Intrinsics.checkNotNullParameter((Object)cameraMode, (String)"cameraMode");
        Intrinsics.checkNotNullParameter((Object)tinting, (String)"tinting");
        Intrinsics.checkNotNullParameter((Object)collision, (String)"collision");
        Intrinsics.checkNotNullParameter(creationEvents, (String)"creationEvents");
        Intrinsics.checkNotNullParameter(expirationEvents, (String)"expirationEvents");
        Intrinsics.checkNotNullParameter((Object)timeline, (String)"timeline");
        this.texture = texture;
        this.material = material;
        this.uvMode = uvMode;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.maxAge = maxAge;
        this.killExpression = killExpression;
        this.updateExpressions = updateExpressions;
        this.renderExpressions = renderExpressions;
        this.motion = motion;
        this.rotation = rotation;
        this.viewDirection = viewDirection;
        this.cameraMode = cameraMode;
        this.tinting = tinting;
        this.collision = collision;
        this.environmentLighting = environmentLighting;
        this.creationEvents = creationEvents;
        this.expirationEvents = expirationEvents;
        this.timeline = timeline;
    }

    public /* synthetic */ BedrockParticle(ResourceLocation resourceLocation, ParticleMaterial particleMaterial, ParticleUVMode particleUVMode, Expression expression, Expression expression2, Expression expression3, Expression expression4, List list, List list2, ParticleMotion particleMotion, ParticleRotation particleRotation, ParticleViewDirection particleViewDirection, ParticleCameraMode particleCameraMode, ParticleTinting particleTinting, ParticleCollision particleCollision, boolean bl, List list3, List list4, EventTriggerTimeline eventTriggerTimeline, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 1) != 0) {
            resourceLocation = new ResourceLocation("minecraft:textures/particles/bubble.png");
        }
        if ((n & 2) != 0) {
            particleMaterial = ParticleMaterial.ALPHA;
        }
        if ((n & 4) != 0) {
            particleUVMode = new StaticParticleUVMode(null, null, 0, 0, null, null, 63, null);
        }
        if ((n & 8) != 0) {
            expression = new NumberExpression(0.15);
        }
        if ((n & 0x10) != 0) {
            expression2 = new NumberExpression(0.15);
        }
        if ((n & 0x20) != 0) {
            expression3 = new NumberExpression(1.0);
        }
        if ((n & 0x40) != 0) {
            expression4 = new BooleanExpression(false);
        }
        if ((n & 0x80) != 0) {
            list = new ArrayList();
        }
        if ((n & 0x100) != 0) {
            list2 = new ArrayList();
        }
        if ((n & 0x200) != 0) {
            particleMotion = new StaticParticleMotion();
        }
        if ((n & 0x400) != 0) {
            particleRotation = new DynamicParticleRotation(null, null, null, null, 15, null);
        }
        if ((n & 0x800) != 0) {
            particleViewDirection = new FromMotionViewDirection(0.0, 1, null);
        }
        if ((n & 0x1000) != 0) {
            particleCameraMode = new RotateXYZCameraMode();
        }
        if ((n & 0x2000) != 0) {
            particleTinting = new ExpressionParticleTinting(null, null, null, null, 15, null);
        }
        if ((n & 0x4000) != 0) {
            particleCollision = new ParticleCollision(null, null, null, null, false, 31, null);
        }
        if ((n & 0x8000) != 0) {
            bl = false;
        }
        if ((n & 0x10000) != 0) {
            list3 = new ArrayList();
        }
        if ((n & 0x20000) != 0) {
            list4 = new ArrayList();
        }
        if ((n & 0x40000) != 0) {
            eventTriggerTimeline = new EventTriggerTimeline(new LinkedHashMap());
        }
        this(resourceLocation, particleMaterial, particleUVMode, expression, expression2, expression3, expression4, list, list2, particleMotion, particleRotation, particleViewDirection, particleCameraMode, particleTinting, particleCollision, bl, list3, list4, eventTriggerTimeline);
    }

    @NotNull
    public final ResourceLocation getTexture() {
        return this.texture;
    }

    public final void setTexture(@NotNull ResourceLocation resourceLocation) {
        Intrinsics.checkNotNullParameter((Object)resourceLocation, (String)"<set-?>");
        this.texture = resourceLocation;
    }

    @NotNull
    public final ParticleMaterial getMaterial() {
        return this.material;
    }

    public final void setMaterial(@NotNull ParticleMaterial particleMaterial) {
        Intrinsics.checkNotNullParameter((Object)((Object)particleMaterial), (String)"<set-?>");
        this.material = particleMaterial;
    }

    @NotNull
    public final ParticleUVMode getUvMode() {
        return this.uvMode;
    }

    public final void setUvMode(@NotNull ParticleUVMode particleUVMode) {
        Intrinsics.checkNotNullParameter((Object)particleUVMode, (String)"<set-?>");
        this.uvMode = particleUVMode;
    }

    @NotNull
    public final Expression getSizeX() {
        return this.sizeX;
    }

    public final void setSizeX(@NotNull Expression expression) {
        Intrinsics.checkNotNullParameter((Object)expression, (String)"<set-?>");
        this.sizeX = expression;
    }

    @NotNull
    public final Expression getSizeY() {
        return this.sizeY;
    }

    public final void setSizeY(@NotNull Expression expression) {
        Intrinsics.checkNotNullParameter((Object)expression, (String)"<set-?>");
        this.sizeY = expression;
    }

    @NotNull
    public final Expression getMaxAge() {
        return this.maxAge;
    }

    public final void setMaxAge(@NotNull Expression expression) {
        Intrinsics.checkNotNullParameter((Object)expression, (String)"<set-?>");
        this.maxAge = expression;
    }

    @NotNull
    public final Expression getKillExpression() {
        return this.killExpression;
    }

    public final void setKillExpression(@NotNull Expression expression) {
        Intrinsics.checkNotNullParameter((Object)expression, (String)"<set-?>");
        this.killExpression = expression;
    }

    @NotNull
    public final List<Expression> getUpdateExpressions() {
        return this.updateExpressions;
    }

    public final void setUpdateExpressions(@NotNull List<Expression> list) {
        Intrinsics.checkNotNullParameter(list, (String)"<set-?>");
        this.updateExpressions = list;
    }

    @NotNull
    public final List<Expression> getRenderExpressions() {
        return this.renderExpressions;
    }

    public final void setRenderExpressions(@NotNull List<Expression> list) {
        Intrinsics.checkNotNullParameter(list, (String)"<set-?>");
        this.renderExpressions = list;
    }

    @NotNull
    public final ParticleMotion getMotion() {
        return this.motion;
    }

    public final void setMotion(@NotNull ParticleMotion particleMotion) {
        Intrinsics.checkNotNullParameter((Object)particleMotion, (String)"<set-?>");
        this.motion = particleMotion;
    }

    @NotNull
    public final ParticleRotation getRotation() {
        return this.rotation;
    }

    public final void setRotation(@NotNull ParticleRotation particleRotation) {
        Intrinsics.checkNotNullParameter((Object)particleRotation, (String)"<set-?>");
        this.rotation = particleRotation;
    }

    @NotNull
    public final ParticleViewDirection getViewDirection() {
        return this.viewDirection;
    }

    public final void setViewDirection(@NotNull ParticleViewDirection particleViewDirection) {
        Intrinsics.checkNotNullParameter((Object)particleViewDirection, (String)"<set-?>");
        this.viewDirection = particleViewDirection;
    }

    @NotNull
    public final ParticleCameraMode getCameraMode() {
        return this.cameraMode;
    }

    public final void setCameraMode(@NotNull ParticleCameraMode particleCameraMode) {
        Intrinsics.checkNotNullParameter((Object)particleCameraMode, (String)"<set-?>");
        this.cameraMode = particleCameraMode;
    }

    @NotNull
    public final ParticleTinting getTinting() {
        return this.tinting;
    }

    public final void setTinting(@NotNull ParticleTinting particleTinting) {
        Intrinsics.checkNotNullParameter((Object)particleTinting, (String)"<set-?>");
        this.tinting = particleTinting;
    }

    @NotNull
    public final ParticleCollision getCollision() {
        return this.collision;
    }

    public final void setCollision(@NotNull ParticleCollision particleCollision) {
        Intrinsics.checkNotNullParameter((Object)particleCollision, (String)"<set-?>");
        this.collision = particleCollision;
    }

    public final boolean getEnvironmentLighting() {
        return this.environmentLighting;
    }

    public final void setEnvironmentLighting(boolean bl) {
        this.environmentLighting = bl;
    }

    @NotNull
    public final List<SimpleEventTrigger> getCreationEvents() {
        return this.creationEvents;
    }

    public final void setCreationEvents(@NotNull List<SimpleEventTrigger> list) {
        Intrinsics.checkNotNullParameter(list, (String)"<set-?>");
        this.creationEvents = list;
    }

    @NotNull
    public final List<SimpleEventTrigger> getExpirationEvents() {
        return this.expirationEvents;
    }

    public final void setExpirationEvents(@NotNull List<SimpleEventTrigger> list) {
        Intrinsics.checkNotNullParameter(list, (String)"<set-?>");
        this.expirationEvents = list;
    }

    @NotNull
    public final EventTriggerTimeline getTimeline() {
        return this.timeline;
    }

    public final void setTimeline(@NotNull EventTriggerTimeline eventTriggerTimeline) {
        Intrinsics.checkNotNullParameter((Object)eventTriggerTimeline, (String)"<set-?>");
        this.timeline = eventTriggerTimeline;
    }

    public final void writeToBuffer(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        buffer.m_130085_(this.texture);
        buffer.m_130070_(this.material.name());
        ParticleUVMode.Companion.writeToBuffer(buffer, (CodecMapped)this.uvMode);
        buffer.m_130070_(MoLangExtensionsKt.getString(this.sizeX));
        buffer.m_130070_(MoLangExtensionsKt.getString(this.sizeY));
        buffer.m_130070_(MoLangExtensionsKt.getString(this.maxAge));
        buffer.m_130070_(MoLangExtensionsKt.getString(this.killExpression));
        buffer.m_236828_((Collection)this.updateExpressions, BedrockParticle::writeToBuffer$lambda$0);
        buffer.m_236828_((Collection)this.renderExpressions, BedrockParticle::writeToBuffer$lambda$1);
        ParticleMotion.Companion.writeToBuffer(buffer, (CodecMapped)this.motion);
        ParticleRotation.Companion.writeToBuffer(buffer, (CodecMapped)this.rotation);
        ParticleViewDirection.Companion.writeToBuffer(buffer, (CodecMapped)this.viewDirection);
        ParticleCameraMode.Companion.writeToBuffer(buffer, (CodecMapped)this.cameraMode);
        ParticleTinting.Companion.writeToBuffer(buffer, (CodecMapped)this.tinting);
        this.collision.writeToBuffer(buffer);
        buffer.writeBoolean(this.environmentLighting);
        buffer.m_236828_((Collection)this.creationEvents, BedrockParticle::writeToBuffer$lambda$2);
        buffer.m_236828_((Collection)this.expirationEvents, BedrockParticle::writeToBuffer$lambda$3);
        this.timeline.encode(buffer);
    }

    public final void readFromBuffer(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        ResourceLocation resourceLocation = buffer.m_130281_();
        Intrinsics.checkNotNullExpressionValue((Object)resourceLocation, (String)"buffer.readIdentifier()");
        this.texture = resourceLocation;
        String string = buffer.m_130277_();
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"buffer.readString()");
        this.material = ParticleMaterial.valueOf(string);
        this.uvMode = (ParticleUVMode)ParticleUVMode.Companion.readFromBuffer(buffer);
        Expression expression = MoLang.createParser(buffer.m_130277_()).parseExpression();
        Intrinsics.checkNotNullExpressionValue((Object)expression, (String)"createParser(buffer.read\u2026ring()).parseExpression()");
        this.sizeX = expression;
        Expression expression2 = MoLang.createParser(buffer.m_130277_()).parseExpression();
        Intrinsics.checkNotNullExpressionValue((Object)expression2, (String)"createParser(buffer.read\u2026ring()).parseExpression()");
        this.sizeY = expression2;
        Expression expression3 = MoLang.createParser(buffer.m_130277_()).parseExpression();
        Intrinsics.checkNotNullExpressionValue((Object)expression3, (String)"createParser(buffer.read\u2026ring()).parseExpression()");
        this.maxAge = expression3;
        Expression expression4 = MoLang.createParser(buffer.m_130277_()).parseExpression();
        Intrinsics.checkNotNullExpressionValue((Object)expression4, (String)"createParser(buffer.read\u2026ring()).parseExpression()");
        this.killExpression = expression4;
        List list = buffer.m_236845_(BedrockParticle::readFromBuffer$lambda$4);
        Intrinsics.checkNotNullExpressionValue((Object)list, (String)"buffer.readList { MoLang\u2026ng()).parseExpression() }");
        this.updateExpressions = list;
        List list2 = buffer.m_236845_(BedrockParticle::readFromBuffer$lambda$5);
        Intrinsics.checkNotNullExpressionValue((Object)list2, (String)"buffer.readList { MoLang\u2026ng()).parseExpression() }");
        this.renderExpressions = list2;
        this.motion = (ParticleMotion)ParticleMotion.Companion.readFromBuffer(buffer);
        this.rotation = (ParticleRotation)ParticleRotation.Companion.readFromBuffer(buffer);
        this.viewDirection = (ParticleViewDirection)ParticleViewDirection.Companion.readFromBuffer(buffer);
        this.cameraMode = (ParticleCameraMode)ParticleCameraMode.Companion.readFromBuffer(buffer);
        this.tinting = (ParticleTinting)ParticleTinting.Companion.readFromBuffer(buffer);
        this.collision.readFromBuffer(buffer);
        this.environmentLighting = buffer.readBoolean();
        List list3 = buffer.m_236845_(arg_0 -> BedrockParticle.readFromBuffer$lambda$7(buffer, arg_0));
        Intrinsics.checkNotNullExpressionValue((Object)list3, (String)"buffer.readList { Simple\u2026o { it.decode(buffer) } }");
        this.creationEvents = list3;
        List list4 = buffer.m_236845_(arg_0 -> BedrockParticle.readFromBuffer$lambda$9(buffer, arg_0));
        Intrinsics.checkNotNullExpressionValue((Object)list4, (String)"buffer.readList { Simple\u2026o { it.decode(buffer) } }");
        this.expirationEvents = list4;
        this.timeline.decode(buffer);
    }

    private static final void writeToBuffer$lambda$0(FriendlyByteBuf pb, Expression expression) {
        Intrinsics.checkNotNullExpressionValue((Object)expression, (String)"expression");
        pb.m_130070_(MoLangExtensionsKt.getString(expression));
    }

    private static final void writeToBuffer$lambda$1(FriendlyByteBuf pb, Expression expression) {
        Intrinsics.checkNotNullExpressionValue((Object)expression, (String)"expression");
        pb.m_130070_(MoLangExtensionsKt.getString(expression));
    }

    private static final void writeToBuffer$lambda$2(FriendlyByteBuf pb, SimpleEventTrigger event) {
        Intrinsics.checkNotNullExpressionValue((Object)pb, (String)"pb");
        event.encode(pb);
    }

    private static final void writeToBuffer$lambda$3(FriendlyByteBuf pb, SimpleEventTrigger event) {
        Intrinsics.checkNotNullExpressionValue((Object)pb, (String)"pb");
        event.encode(pb);
    }

    private static final Expression readFromBuffer$lambda$4(FriendlyByteBuf it) {
        return MoLang.createParser(it.m_130277_()).parseExpression();
    }

    private static final Expression readFromBuffer$lambda$5(FriendlyByteBuf it) {
        return MoLang.createParser(it.m_130277_()).parseExpression();
    }

    private static final SimpleEventTrigger readFromBuffer$lambda$7(FriendlyByteBuf $buffer, FriendlyByteBuf it) {
        SimpleEventTrigger simpleEventTrigger;
        Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
        SimpleEventTrigger it2 = simpleEventTrigger = new SimpleEventTrigger("");
        boolean bl = false;
        it2.decode($buffer);
        return simpleEventTrigger;
    }

    private static final SimpleEventTrigger readFromBuffer$lambda$9(FriendlyByteBuf $buffer, FriendlyByteBuf it) {
        SimpleEventTrigger simpleEventTrigger;
        Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
        SimpleEventTrigger it2 = simpleEventTrigger = new SimpleEventTrigger("");
        boolean bl = false;
        it2.decode($buffer);
        return simpleEventTrigger;
    }

    private static final Expression EXPRESSION_SET_CODEC$lambda$16$lambda$10(ExpressionSet it) {
        return it.getSizeX();
    }

    private static final Expression EXPRESSION_SET_CODEC$lambda$16$lambda$11(ExpressionSet it) {
        return it.getSizeY();
    }

    private static final Expression EXPRESSION_SET_CODEC$lambda$16$lambda$12(ExpressionSet it) {
        return it.getMaxAge();
    }

    private static final Expression EXPRESSION_SET_CODEC$lambda$16$lambda$13(ExpressionSet it) {
        return it.getKillExpression();
    }

    private static final List EXPRESSION_SET_CODEC$lambda$16$lambda$14(ExpressionSet it) {
        return it.getUpdateExpressions();
    }

    private static final List EXPRESSION_SET_CODEC$lambda$16$lambda$15(ExpressionSet it) {
        return it.getRenderExpressions();
    }

    private static final App EXPRESSION_SET_CODEC$lambda$16(RecordCodecBuilder.Instance instance) {
        return instance.group((App)ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("sizeX").forGetter(BedrockParticle::EXPRESSION_SET_CODEC$lambda$16$lambda$10), (App)ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("sizeY").forGetter(BedrockParticle::EXPRESSION_SET_CODEC$lambda$16$lambda$11), (App)ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("maxAge").forGetter(BedrockParticle::EXPRESSION_SET_CODEC$lambda$16$lambda$12), (App)ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("killExpression").forGetter(BedrockParticle::EXPRESSION_SET_CODEC$lambda$16$lambda$13), (App)new ListCodec((Codec)ExpressionCodecKt.getEXPRESSION_CODEC()).fieldOf("updateExpressions").forGetter(BedrockParticle::EXPRESSION_SET_CODEC$lambda$16$lambda$14), (App)new ListCodec((Codec)ExpressionCodecKt.getEXPRESSION_CODEC()).fieldOf("renderExpressions").forGetter(BedrockParticle::EXPRESSION_SET_CODEC$lambda$16$lambda$15)).apply((Applicative)instance, ExpressionSet::new);
    }

    private static final List EVENT_SET_CODEC$lambda$20$lambda$17(EventSet it) {
        return it.getCreationEvents();
    }

    private static final List EVENT_SET_CODEC$lambda$20$lambda$18(EventSet it) {
        return it.getExpirationEvents();
    }

    private static final EventTriggerTimeline EVENT_SET_CODEC$lambda$20$lambda$19(EventSet it) {
        return it.getTimeline();
    }

    private static final App EVENT_SET_CODEC$lambda$20(RecordCodecBuilder.Instance instance) {
        return instance.group((App)new ListCodec(SimpleEventTrigger.Companion.getCODEC()).fieldOf("creationEvents").forGetter(BedrockParticle::EVENT_SET_CODEC$lambda$20$lambda$17), (App)new ListCodec(SimpleEventTrigger.Companion.getCODEC()).fieldOf("expirationEvents").forGetter(BedrockParticle::EVENT_SET_CODEC$lambda$20$lambda$18), (App)EventTriggerTimeline.Companion.getCODEC().fieldOf("timeline").forGetter(BedrockParticle::EVENT_SET_CODEC$lambda$20$lambda$19)).apply((Applicative)instance, EventSet::new);
    }

    private static final ResourceLocation CODEC$lambda$34$lambda$21(BedrockParticle it) {
        return it.texture;
    }

    private static final String CODEC$lambda$34$lambda$22(BedrockParticle it) {
        return it.material.name();
    }

    private static final ParticleUVMode CODEC$lambda$34$lambda$23(BedrockParticle it) {
        return it.uvMode;
    }

    private static final ExpressionSet CODEC$lambda$34$lambda$24(BedrockParticle it) {
        return new ExpressionSet(it.sizeX, it.sizeY, it.maxAge, it.killExpression, it.updateExpressions, it.renderExpressions);
    }

    private static final ParticleMotion CODEC$lambda$34$lambda$25(BedrockParticle it) {
        return it.motion;
    }

    private static final ParticleRotation CODEC$lambda$34$lambda$26(BedrockParticle it) {
        return it.rotation;
    }

    private static final ParticleViewDirection CODEC$lambda$34$lambda$27(BedrockParticle it) {
        return it.viewDirection;
    }

    private static final ParticleCameraMode CODEC$lambda$34$lambda$28(BedrockParticle it) {
        return it.cameraMode;
    }

    private static final ParticleTinting CODEC$lambda$34$lambda$29(BedrockParticle it) {
        return it.tinting;
    }

    private static final ParticleCollision CODEC$lambda$34$lambda$30(BedrockParticle it) {
        return it.collision;
    }

    private static final Boolean CODEC$lambda$34$lambda$31(BedrockParticle it) {
        return it.environmentLighting;
    }

    private static final EventSet CODEC$lambda$34$lambda$32(BedrockParticle it) {
        return new EventSet(it.creationEvents, it.expirationEvents, it.timeline);
    }

    private static final BedrockParticle CODEC$lambda$34$lambda$33(ResourceLocation texture, String materialStr, ParticleUVMode uvMode, ExpressionSet expressionSet, ParticleMotion motion, ParticleRotation rotation, ParticleViewDirection viewDirection, ParticleCameraMode cameraMode, ParticleTinting tinting, ParticleCollision collision, Boolean environmentLighting, EventSet eventSet) {
        Intrinsics.checkNotNullExpressionValue((Object)texture, (String)"texture");
        Intrinsics.checkNotNullExpressionValue((Object)materialStr, (String)"materialStr");
        ParticleMaterial particleMaterial = ParticleMaterial.valueOf(materialStr);
        Intrinsics.checkNotNullExpressionValue((Object)uvMode, (String)"uvMode");
        Expression expression = expressionSet.getSizeX();
        Expression expression2 = expressionSet.getSizeY();
        Expression expression3 = expressionSet.getMaxAge();
        Expression expression4 = expressionSet.getKillExpression();
        List<Expression> list = expressionSet.getUpdateExpressions();
        List<Expression> list2 = expressionSet.getRenderExpressions();
        Intrinsics.checkNotNullExpressionValue((Object)motion, (String)"motion");
        Intrinsics.checkNotNullExpressionValue((Object)rotation, (String)"rotation");
        Intrinsics.checkNotNullExpressionValue((Object)viewDirection, (String)"viewDirection");
        Intrinsics.checkNotNullExpressionValue((Object)cameraMode, (String)"cameraMode");
        Intrinsics.checkNotNullExpressionValue((Object)tinting, (String)"tinting");
        Intrinsics.checkNotNullExpressionValue((Object)collision, (String)"collision");
        Intrinsics.checkNotNullExpressionValue((Object)environmentLighting, (String)"environmentLighting");
        return new BedrockParticle(texture, particleMaterial, uvMode, expression, expression2, expression3, expression4, list, list2, motion, rotation, viewDirection, cameraMode, tinting, collision, environmentLighting, eventSet.getCreationEvents(), eventSet.getExpirationEvents(), eventSet.getTimeline());
    }

    private static final App CODEC$lambda$34(RecordCodecBuilder.Instance instance) {
        return instance.group((App)ResourceLocation.f_135803_.fieldOf("texture").forGetter(BedrockParticle::CODEC$lambda$34$lambda$21), (App)PrimitiveCodec.STRING.fieldOf("material").forGetter(BedrockParticle::CODEC$lambda$34$lambda$22), (App)ParticleUVMode.Companion.getCodec().fieldOf("uvMode").forGetter(BedrockParticle::CODEC$lambda$34$lambda$23), (App)EXPRESSION_SET_CODEC.fieldOf("expressionSet").forGetter(BedrockParticle::CODEC$lambda$34$lambda$24), (App)ParticleMotion.Companion.getCodec().fieldOf("motion").forGetter(BedrockParticle::CODEC$lambda$34$lambda$25), (App)ParticleRotation.Companion.getCodec().fieldOf("rotation").forGetter(BedrockParticle::CODEC$lambda$34$lambda$26), (App)ParticleViewDirection.Companion.getCodec().fieldOf("viewDirection").forGetter(BedrockParticle::CODEC$lambda$34$lambda$27), (App)ParticleCameraMode.Companion.getCodec().fieldOf("cameraMode").forGetter(BedrockParticle::CODEC$lambda$34$lambda$28), (App)ParticleTinting.Companion.getCodec().fieldOf("tinting").forGetter(BedrockParticle::CODEC$lambda$34$lambda$29), (App)ParticleCollision.Companion.getCODEC().fieldOf("collision").forGetter(BedrockParticle::CODEC$lambda$34$lambda$30), (App)PrimitiveCodec.BOOL.fieldOf("environmentLighting").forGetter(BedrockParticle::CODEC$lambda$34$lambda$31), (App)EVENT_SET_CODEC.fieldOf("eventSet").forGetter(BedrockParticle::CODEC$lambda$34$lambda$32)).apply((Applicative)instance, BedrockParticle::CODEC$lambda$34$lambda$33);
    }

    public BedrockParticle() {
        this(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, false, null, null, null, 524287, null);
    }

    static {
        Codec codec2 = RecordCodecBuilder.create(BedrockParticle::CODEC$lambda$34);
        Intrinsics.checkNotNullExpressionValue((Object)codec2, (String)"create { instance ->\n   \u2026)\n            }\n        }");
        CODEC = codec2;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u000f\u0010\u0010R\u001d\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007R;\u0010\n\u001a&\u0012\f\u0012\n \t*\u0004\u0018\u00010\b0\b \t*\u0012\u0012\f\u0012\n \t*\u0004\u0018\u00010\b0\b\u0018\u00010\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\n\u0010\u0005\u001a\u0004\b\u000b\u0010\u0007R;\u0010\r\u001a&\u0012\f\u0012\n \t*\u0004\u0018\u00010\f0\f \t*\u0012\u0012\f\u0012\n \t*\u0004\u0018\u00010\f0\f\u0018\u00010\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\r\u0010\u0005\u001a\u0004\b\u000e\u0010\u0007\u00a8\u0006\u0011"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/BedrockParticle$Companion;", "", "Lcom/mojang/serialization/Codec;", "Lcom/cobblemon/mod/common/api/snowstorm/BedrockParticle;", "CODEC", "Lcom/mojang/serialization/Codec;", "getCODEC", "()Lcom/mojang/serialization/Codec;", "Lcom/cobblemon/mod/common/api/snowstorm/BedrockParticle$EventSet;", "kotlin.jvm.PlatformType", "EVENT_SET_CODEC", "getEVENT_SET_CODEC", "Lcom/cobblemon/mod/common/api/snowstorm/BedrockParticle$ExpressionSet;", "EXPRESSION_SET_CODEC", "getEXPRESSION_SET_CODEC", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public final Codec<ExpressionSet> getEXPRESSION_SET_CODEC() {
            return EXPRESSION_SET_CODEC;
        }

        public final Codec<EventSet> getEVENT_SET_CODEC() {
            return EVENT_SET_CODEC;
        }

        @NotNull
        public final Codec<BedrockParticle> getCODEC() {
            return CODEC;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B+\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002\u0012\u0006\u0010\u000b\u001a\u00020\n\u00a2\u0006\u0004\b\u000f\u0010\u0010R\u001d\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007R\u001d\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00030\u00028\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\u0005\u001a\u0004\b\t\u0010\u0007R\u0017\u0010\u000b\u001a\u00020\n8\u0006\u00a2\u0006\f\n\u0004\b\u000b\u0010\f\u001a\u0004\b\r\u0010\u000e\u00a8\u0006\u0011"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/BedrockParticle$EventSet;", "", "", "Lcom/cobblemon/mod/common/api/snowstorm/SimpleEventTrigger;", "creationEvents", "Ljava/util/List;", "getCreationEvents", "()Ljava/util/List;", "expirationEvents", "getExpirationEvents", "Lcom/cobblemon/mod/common/api/snowstorm/EventTriggerTimeline;", "timeline", "Lcom/cobblemon/mod/common/api/snowstorm/EventTriggerTimeline;", "getTimeline", "()Lcom/cobblemon/mod/common/api/snowstorm/EventTriggerTimeline;", "<init>", "(Ljava/util/List;Ljava/util/List;Lcom/cobblemon/mod/common/api/snowstorm/EventTriggerTimeline;)V", "common"})
    public static final class EventSet {
        @NotNull
        private final List<SimpleEventTrigger> creationEvents;
        @NotNull
        private final List<SimpleEventTrigger> expirationEvents;
        @NotNull
        private final EventTriggerTimeline timeline;

        public EventSet(@NotNull List<SimpleEventTrigger> creationEvents, @NotNull List<SimpleEventTrigger> expirationEvents, @NotNull EventTriggerTimeline timeline) {
            Intrinsics.checkNotNullParameter(creationEvents, (String)"creationEvents");
            Intrinsics.checkNotNullParameter(expirationEvents, (String)"expirationEvents");
            Intrinsics.checkNotNullParameter((Object)timeline, (String)"timeline");
            this.creationEvents = creationEvents;
            this.expirationEvents = expirationEvents;
            this.timeline = timeline;
        }

        @NotNull
        public final List<SimpleEventTrigger> getCreationEvents() {
            return this.creationEvents;
        }

        @NotNull
        public final List<SimpleEventTrigger> getExpirationEvents() {
            return this.expirationEvents;
        }

        @NotNull
        public final EventTriggerTimeline getTimeline() {
            return this.timeline;
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010!\n\u0002\b\r\u0018\u00002\u00020\u0001BC\u0012\u0006\u0010\u000e\u001a\u00020\u0002\u0012\u0006\u0010\u0010\u001a\u00020\u0002\u0012\u0006\u0010\u0007\u001a\u00020\u0002\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00020\t\u0012\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00020\t\u00a2\u0006\u0004\b\u0014\u0010\u0015R\u0017\u0010\u0003\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006R\u0017\u0010\u0007\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0007\u0010\u0004\u001a\u0004\b\b\u0010\u0006R\u001d\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00020\t8\u0006\u00a2\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\f\u0010\rR\u0017\u0010\u000e\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u000e\u0010\u0004\u001a\u0004\b\u000f\u0010\u0006R\u0017\u0010\u0010\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0010\u0010\u0004\u001a\u0004\b\u0011\u0010\u0006R\u001d\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00020\t8\u0006\u00a2\u0006\f\n\u0004\b\u0012\u0010\u000b\u001a\u0004\b\u0013\u0010\r\u00a8\u0006\u0016"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/BedrockParticle$ExpressionSet;", "", "Lcom/bedrockk/molang/Expression;", "killExpression", "Lcom/bedrockk/molang/Expression;", "getKillExpression", "()Lcom/bedrockk/molang/Expression;", "maxAge", "getMaxAge", "", "renderExpressions", "Ljava/util/List;", "getRenderExpressions", "()Ljava/util/List;", "sizeX", "getSizeX", "sizeY", "getSizeY", "updateExpressions", "getUpdateExpressions", "<init>", "(Lcom/bedrockk/molang/Expression;Lcom/bedrockk/molang/Expression;Lcom/bedrockk/molang/Expression;Lcom/bedrockk/molang/Expression;Ljava/util/List;Ljava/util/List;)V", "common"})
    public static final class ExpressionSet {
        @NotNull
        private final Expression sizeX;
        @NotNull
        private final Expression sizeY;
        @NotNull
        private final Expression maxAge;
        @NotNull
        private final Expression killExpression;
        @NotNull
        private final List<Expression> updateExpressions;
        @NotNull
        private final List<Expression> renderExpressions;

        public ExpressionSet(@NotNull Expression sizeX, @NotNull Expression sizeY, @NotNull Expression maxAge, @NotNull Expression killExpression, @NotNull List<Expression> updateExpressions, @NotNull List<Expression> renderExpressions) {
            Intrinsics.checkNotNullParameter((Object)sizeX, (String)"sizeX");
            Intrinsics.checkNotNullParameter((Object)sizeY, (String)"sizeY");
            Intrinsics.checkNotNullParameter((Object)maxAge, (String)"maxAge");
            Intrinsics.checkNotNullParameter((Object)killExpression, (String)"killExpression");
            Intrinsics.checkNotNullParameter(updateExpressions, (String)"updateExpressions");
            Intrinsics.checkNotNullParameter(renderExpressions, (String)"renderExpressions");
            this.sizeX = sizeX;
            this.sizeY = sizeY;
            this.maxAge = maxAge;
            this.killExpression = killExpression;
            this.updateExpressions = updateExpressions;
            this.renderExpressions = renderExpressions;
        }

        @NotNull
        public final Expression getSizeX() {
            return this.sizeX;
        }

        @NotNull
        public final Expression getSizeY() {
            return this.sizeY;
        }

        @NotNull
        public final Expression getMaxAge() {
            return this.maxAge;
        }

        @NotNull
        public final Expression getKillExpression() {
            return this.killExpression;
        }

        @NotNull
        public final List<Expression> getUpdateExpressions() {
            return this.updateExpressions;
        }

        @NotNull
        public final List<Expression> getRenderExpressions() {
            return this.renderExpressions;
        }
    }
}

