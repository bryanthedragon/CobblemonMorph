/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.Expression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoLangEnvironment;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoLangRuntime;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.struct.MoStruct;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.struct.QueryStruct;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.BedrockParticleEffect;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.particle.ParticleStorm;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.MatrixWrapper;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation.BedrockEffectKeyframe;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\u0004\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u001f\u001a\u00020\u001e\u0012\u0006\u0010\u000f\u001a\u00020\u000e\u0012\u0006\u0010\u0014\u001a\u00020\u0013\u0012\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00190\u0018\u00a2\u0006\u0004\b \u0010!J\u0015\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0002\u001a\u00020\u0000\u00a2\u0006\u0004\b\u0004\u0010\u0005J/\u0010\f\u001a\u00020\u000b\"\b\b\u0000\u0010\u0007*\u00020\u00062\u0006\u0010\b\u001a\u00028\u00002\f\u0010\n\u001a\b\u0012\u0004\u0012\u00028\u00000\tH\u0016\u00a2\u0006\u0004\b\f\u0010\rR\u0017\u0010\u000f\u001a\u00020\u000e8\u0006\u00a2\u0006\f\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\u0011\u0010\u0012R\u0017\u0010\u0014\u001a\u00020\u00138\u0006\u00a2\u0006\f\n\u0004\b\u0014\u0010\u0015\u001a\u0004\b\u0016\u0010\u0017R\u001d\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00190\u00188\u0006\u00a2\u0006\f\n\u0004\b\u001a\u0010\u001b\u001a\u0004\b\u001c\u0010\u001d\u00a8\u0006\""}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockParticleKeyframe;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockEffectKeyframe;", "other", "", "isSameAs", "(Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockParticleKeyframe;)Z", "Lnet/minecraft/world/entity/Entity;", "T", "entity", "Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState;", "state", "", "run", "(Lnet/minecraft/world/entity/Entity;Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState;)V", "Lcom/cobblemon/mod/common/api/snowstorm/BedrockParticleEffect;", "effect", "Lcom/cobblemon/mod/common/api/snowstorm/BedrockParticleEffect;", "getEffect", "()Lcom/cobblemon/mod/common/api/snowstorm/BedrockParticleEffect;", "", "locator", "Ljava/lang/String;", "getLocator", "()Ljava/lang/String;", "", "Lcom/bedrockk/molang/Expression;", "scripts", "Ljava/util/List;", "getScripts", "()Ljava/util/List;", "", "seconds", "<init>", "(FLcom/cobblemon/mod/common/api/snowstorm/BedrockParticleEffect;Ljava/lang/String;Ljava/util/List;)V", "common"})
@SourceDebugExtension(value={"SMAP\nBedrockAnimation.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BedrockAnimation.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockParticleKeyframe\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,346:1\n1549#2:347\n1620#2,3:348\n*S KotlinDebug\n*F\n+ 1 BedrockAnimation.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockParticleKeyframe\n*L\n68#1:347\n68#1:348,3\n*E\n"})
public final class BedrockParticleKeyframe
extends BedrockEffectKeyframe {
    @NotNull
    private final BedrockParticleEffect effect;
    @NotNull
    private final String locator;
    @NotNull
    private final List<Expression> scripts;

    public BedrockParticleKeyframe(float seconds, @NotNull BedrockParticleEffect effect, @NotNull String locator, @NotNull List<? extends Expression> scripts) {
        Intrinsics.checkNotNullParameter((Object)effect, (String)"effect");
        Intrinsics.checkNotNullParameter((Object)locator, (String)"locator");
        Intrinsics.checkNotNullParameter(scripts, (String)"scripts");
        super(seconds);
        this.effect = effect;
        this.locator = locator;
        this.scripts = scripts;
    }

    @NotNull
    public final BedrockParticleEffect getEffect() {
        return this.effect;
    }

    @NotNull
    public final String getLocator() {
        return this.locator;
    }

    @NotNull
    public final List<Expression> getScripts() {
        return this.scripts;
    }

    public final boolean isSameAs(@NotNull BedrockParticleKeyframe other) {
        boolean bl;
        Intrinsics.checkNotNullParameter((Object)other, (String)"other");
        if (!(this.getSeconds() == other.getSeconds())) {
            bl = false;
        } else if (!Intrinsics.areEqual((Object)this.effect, (Object)other.effect)) {
            bl = false;
        } else if (!Intrinsics.areEqual((Object)this.locator, (Object)other.locator)) {
            bl = false;
        } else {
            Expression it;
            Collection collection;
            Iterable $this$mapTo$iv$iv;
            Iterable $this$map$iv = this.scripts;
            boolean $i$f$map = false;
            Iterable iterable = $this$map$iv;
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
            boolean $i$f$mapTo = false;
            for (Object item$iv$iv : $this$mapTo$iv$iv) {
                Expression expression = (Expression)item$iv$iv;
                collection = destination$iv$iv;
                boolean bl2 = false;
                collection.add(MoLangExtensionsKt.getString(it));
            }
            $this$map$iv = other.scripts;
            collection = CollectionsKt.toSet((Iterable)((List)destination$iv$iv));
            $i$f$map = false;
            $this$mapTo$iv$iv = $this$map$iv;
            destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
            $i$f$mapTo = false;
            for (Object item$iv$iv : $this$mapTo$iv$iv) {
                it = (Expression)item$iv$iv;
                Collection collection2 = destination$iv$iv;
                boolean bl3 = false;
                collection2.add(MoLangExtensionsKt.getString(it));
            }
            bl = Intrinsics.areEqual((Object)collection, (Object)CollectionsKt.toSet((Iterable)((List)destination$iv$iv)));
        }
        return bl;
    }

    @Override
    public <T extends Entity> void run(@NotNull T entity2, @NotNull PoseableEntityState<T> state) {
        MatrixWrapper matrixWrapper;
        Intrinsics.checkNotNullParameter(entity2, (String)"entity");
        Intrinsics.checkNotNullParameter(state, (String)"state");
        Level level = entity2.m_9236_();
        ClientLevel clientLevel = level instanceof ClientLevel ? (ClientLevel)level : null;
        if (clientLevel == null) {
            return;
        }
        ClientLevel world = clientLevel;
        MatrixWrapper matrixWrapper2 = state.getLocatorStates().get(this.locator);
        if (matrixWrapper2 == null) {
            MatrixWrapper matrixWrapper3 = state.getLocatorStates().get("root");
            Intrinsics.checkNotNull((Object)matrixWrapper3);
            matrixWrapper2 = matrixWrapper = matrixWrapper3;
        }
        if (state.getPoseParticles().contains(this)) {
            return;
        }
        MoLangRuntime particleRuntime = new MoLangRuntime();
        HashMap<String, MoStruct> hashMap = particleRuntime.getEnvironment().getStructs();
        Intrinsics.checkNotNullExpressionValue(hashMap, (String)"particleRuntime.environment.structs");
        Map map = hashMap;
        Object object = "query";
        MoLangEnvironment moLangEnvironment = state.getRuntime().getEnvironment();
        Intrinsics.checkNotNullExpressionValue((Object)moLangEnvironment, (String)"state.runtime.environment");
        QueryStruct queryStruct = MoLangFunctions.getQueryStruct$default(MoLangFunctions.INSTANCE, moLangEnvironment, null, 1, null);
        map.put(object, queryStruct);
        object = this.effect;
        ParticleStorm storm2 = new ParticleStorm((BedrockParticleEffect)object, matrixWrapper, world, (Function0<? extends Vec3>)((Function0)new Function0<Vec3>(entity2){
            final /* synthetic */ T $entity;
            {
                this.$entity = $entity;
                super(0);
            }

            @NotNull
            public final Vec3 invoke() {
                Vec3 vec3 = this.$entity.m_20184_();
                Intrinsics.checkNotNullExpressionValue((Object)vec3, (String)"entity.velocity");
                return vec3;
            }
        }), (Function0<Boolean>)((Function0)new Function0<Boolean>(entity2, state, this){
            final /* synthetic */ T $entity;
            final /* synthetic */ PoseableEntityState<T> $state;
            final /* synthetic */ BedrockParticleKeyframe this$0;
            {
                this.$entity = $entity;
                this.$state = $state;
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Boolean invoke() {
                return !this.$entity.m_213877_() && this.$state.getPoseParticles().contains(this.this$0);
            }
        }), (Function0<Boolean>)((Function0)new Function0<Boolean>(entity2){
            final /* synthetic */ T $entity;
            {
                this.$entity = $entity;
                super(0);
            }

            @NotNull
            public final Boolean invoke() {
                return !this.$entity.m_20145_();
            }
        }), (Function0<Unit>)((Function0)new Function0<Unit>(state, this){
            final /* synthetic */ PoseableEntityState<T> $state;
            final /* synthetic */ BedrockParticleKeyframe this$0;
            {
                this.$state = $state;
                this.this$0 = $receiver;
                super(0);
            }

            public final void invoke() {
                this.$state.getPoseParticles().remove(this.this$0);
            }
        }), particleRuntime, entity2);
        state.getPoseParticles().add(this);
        storm2.getRuntime().execute(this.scripts);
        storm2.spawn();
    }
}

