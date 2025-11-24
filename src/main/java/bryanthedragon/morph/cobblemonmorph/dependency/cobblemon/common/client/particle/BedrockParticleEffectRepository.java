/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function2
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.packs.resources.ResourceManager
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.particle;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.BedrockParticleEffect;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.particle.BedrockParticleEffectRepository;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.IdentifierExtensionsKt;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0013\u0010\u0014J\u0017\u0010\u0005\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u0015\u0010\n\u001a\u00020\t2\u0006\u0010\b\u001a\u00020\u0007\u00a2\u0006\u0004\b\n\u0010\u000bR\u001c\u0010\u000e\u001a\n \r*\u0004\u0018\u00010\f0\f8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u000e\u0010\u000fR \u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00040\u00108\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0011\u0010\u0012\u00a8\u0006\u0015"}, d2={"Lcom/cobblemon/mod/common/client/particle/BedrockParticleEffectRepository;", "", "Lnet/minecraft/resources/ResourceLocation;", "identifier", "Lcom/cobblemon/mod/common/api/snowstorm/BedrockParticleEffect;", "getEffect", "(Lnet/minecraft/resources/ResourceLocation;)Lcom/cobblemon/mod/common/api/snowstorm/BedrockParticleEffect;", "Lnet/minecraft/server/packs/resources/ResourceManager;", "resourceManager", "", "loadEffects", "(Lnet/minecraft/server/packs/resources/ResourceManager;)V", "Lcom/google/gson/Gson;", "kotlin.jvm.PlatformType", "GSON", "Lcom/google/gson/Gson;", "", "effects", "Ljava/util/Map;", "<init>", "()V", "common"})
public final class BedrockParticleEffectRepository {
    @NotNull
    public static final BedrockParticleEffectRepository INSTANCE = new BedrockParticleEffectRepository();
    private static final Gson GSON = new GsonBuilder().create();
    @NotNull
    private static final Map<ResourceLocation, BedrockParticleEffect> effects = new LinkedHashMap();

    private BedrockParticleEffectRepository() {
    }

    public final void loadEffects(@NotNull ResourceManager resourceManager) {
        Intrinsics.checkNotNullParameter((Object)resourceManager, (String)"resourceManager");
        Cobblemon.INSTANCE.getLOGGER().info("Loading particle effects...");
        effects.clear();
        resourceManager.m_214159_("bedrock/particles", BedrockParticleEffectRepository::loadEffects$lambda$0).forEach((arg_0, arg_1) -> BedrockParticleEffectRepository.loadEffects$lambda$1(loadEffects.2.INSTANCE, arg_0, arg_1));
        Cobblemon.INSTANCE.getLOGGER().info("Loaded " + effects.size() + " particle effects");
    }

    @Nullable
    public final BedrockParticleEffect getEffect(@NotNull ResourceLocation identifier) {
        Intrinsics.checkNotNullParameter((Object)identifier, (String)"identifier");
        return effects.get(identifier);
    }

    private static final boolean loadEffects$lambda$0(ResourceLocation path) {
        Intrinsics.checkNotNullExpressionValue((Object)path, (String)"path");
        return IdentifierExtensionsKt.endsWith(path, ".particle.json");
    }

    private static final void loadEffects$lambda$1(Function2 $tmp0, Object p0, Object p1) {
        Intrinsics.checkNotNullParameter((Object)$tmp0, (String)"$tmp0");
        $tmp0.invoke(p0, p1);
    }

    public static final /* synthetic */ Gson access$getGSON$p() {
        return GSON;
    }

    public static final /* synthetic */ Map access$getEffects$p() {
        return effects;
    }
}

