/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.FieldNamingPolicy
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.text.Charsets
 *  kotlin.text.StringsKt
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.packs.resources.Resource
 *  net.minecraft.server.packs.resources.ResourceManager
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.BedrockAnimationReferenceFactory;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation.BedrockAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation.BedrockAnimationAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation.BedrockAnimationGroup;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.JsonPokemonPoseableModel;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.Closeable;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0018\u0010\u0019J\u001d\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0006\u0010\u0007J#\u0010\r\u001a\u00020\f2\u0006\u0010\t\u001a\u00020\b2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00020\n\u00a2\u0006\u0004\b\r\u0010\u000eJ\u001f\u0010\u000f\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u0002\u00a2\u0006\u0004\b\u000f\u0010\u0007R \u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00110\u00108\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0012\u0010\u0013R\u001c\u0010\u0016\u001a\n \u0015*\u0004\u0018\u00010\u00140\u00148\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0016\u0010\u0017\u00a8\u0006\u001a"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockAnimationRepository;", "", "", "fileName", "animationName", "Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockAnimation;", "getAnimation", "(Ljava/lang/String;Ljava/lang/String;)Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockAnimation;", "Lnet/minecraft/server/packs/resources/ResourceManager;", "resourceManager", "", "directories", "", "loadAnimations", "(Lnet/minecraft/server/packs/resources/ResourceManager;Ljava/util/List;)V", "tryGetAnimation", "", "Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockAnimationGroup;", "animationGroups", "Ljava/util/Map;", "Lcom/google/gson/Gson;", "kotlin.jvm.PlatformType", "gson", "Lcom/google/gson/Gson;", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nBedrockAnimationRepository.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BedrockAnimationRepository.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockAnimationRepository\n+ 2 _Maps.kt\nkotlin/collections/MapsKt___MapsKt\n+ 3 GsonExtensions.kt\ncom/cobblemon/mod/common/util/GsonExtensionsKt\n*L\n1#1,70:1\n215#2:71\n216#2:73\n17#3:72\n*S KotlinDebug\n*F\n+ 1 BedrockAnimationRepository.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockAnimationRepository\n*L\n44#1:71\n44#1:73\n46#1:72\n*E\n"})
public final class BedrockAnimationRepository {
    @NotNull
    public static final BedrockAnimationRepository INSTANCE = new BedrockAnimationRepository();
    private static final Gson gson = new GsonBuilder().disableHtmlEscaping().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).registerTypeAdapter((Type)((Object)BedrockAnimation.class), (Object)BedrockAnimationAdapter.INSTANCE).create();
    @NotNull
    private static final Map<String, BedrockAnimationGroup> animationGroups = new LinkedHashMap();

    private BedrockAnimationRepository() {
    }

    /*
     * WARNING - void declaration
     */
    public final void loadAnimations(@NotNull ResourceManager resourceManager, @NotNull List<String> directories) {
        Intrinsics.checkNotNullParameter((Object)resourceManager, (String)"resourceManager");
        Intrinsics.checkNotNullParameter(directories, (String)"directories");
        JsonPokemonPoseableModel.Companion.registerFactory("bedrock", BedrockAnimationReferenceFactory.INSTANCE);
        Cobblemon.INSTANCE.getLOGGER().info("Loading animations...");
        int animationCount = 0;
        animationGroups.clear();
        for (String directory : directories) {
            void $this$forEach$iv;
            Intrinsics.checkNotNullExpressionValue((Object)resourceManager.m_214159_(directory, BedrockAnimationRepository::loadAnimations$lambda$0), (String)"resourceManager.findReso\u2026With(\".animation.json\") }");
            boolean $i$f$forEach = false;
            Iterator iterator = $this$forEach$iv.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry element$iv;
                Map.Entry entry = element$iv = iterator.next();
                boolean bl = false;
                ResourceLocation identifier = (ResourceLocation)entry.getKey();
                Resource resource = (Resource)entry.getValue();
                try {
                    void reader$iv;
                    void $this$fromJson$iv;
                    Intrinsics.checkNotNullExpressionValue((Object)gson, (String)"gson");
                    InputStream inputStream = resource.m_215507_();
                    Intrinsics.checkNotNullExpressionValue((Object)inputStream, (String)"resource.inputStream");
                    Closeable closeable = inputStream;
                    Charset charset = Charsets.UTF_8;
                    closeable = new InputStreamReader((InputStream)closeable, charset);
                    boolean $i$f$fromJson = false;
                    BedrockAnimationGroup animationGroup = (BedrockAnimationGroup)$this$fromJson$iv.fromJson((Reader)reader$iv, BedrockAnimationGroup.class);
                    String string = identifier.m_135815_();
                    Intrinsics.checkNotNullExpressionValue((Object)string, (String)"identifier.path");
                    String animationGroupName = StringsKt.replace$default((String)StringsKt.substringAfterLast$default((String)string, (String)"/", null, (int)2, null), (String)".animation.json", (String)"", (boolean)false, (int)4, null);
                    Intrinsics.checkNotNullExpressionValue((Object)animationGroup, (String)"animationGroup");
                    animationGroups.put(animationGroupName, animationGroup);
                    animationCount += animationGroup.getAnimations().size();
                }
                catch (Exception e) {
                    Cobblemon.INSTANCE.getLOGGER().error("Failed to load animation group " + identifier, (Throwable)e);
                }
            }
        }
        Cobblemon.INSTANCE.getLOGGER().info("Loaded " + animationCount + " animations from " + animationGroups.size() + " animation groups");
    }

    @Nullable
    public final BedrockAnimation tryGetAnimation(@NotNull String fileName, @NotNull String animationName) {
        Intrinsics.checkNotNullParameter((Object)fileName, (String)"fileName");
        Intrinsics.checkNotNullParameter((Object)animationName, (String)"animationName");
        BedrockAnimationGroup bedrockAnimationGroup = animationGroups.get(fileName);
        if (bedrockAnimationGroup == null) {
            return null;
        }
        BedrockAnimationGroup animationGroup = bedrockAnimationGroup;
        return animationGroup.getAnimations().get(animationName);
    }

    @NotNull
    public final BedrockAnimation getAnimation(@NotNull String fileName, @NotNull String animationName) {
        Intrinsics.checkNotNullParameter((Object)fileName, (String)"fileName");
        Intrinsics.checkNotNullParameter((Object)animationName, (String)"animationName");
        BedrockAnimationGroup bedrockAnimationGroup = animationGroups.get(fileName);
        if (bedrockAnimationGroup == null) {
            throw new IllegalArgumentException("Unknown animation group: " + fileName);
        }
        BedrockAnimationGroup animationGroup = bedrockAnimationGroup;
        BedrockAnimation bedrockAnimation = animationGroup.getAnimations().get(animationName);
        if (bedrockAnimation == null) {
            throw new IllegalArgumentException("Animation " + animationName + " not found in animation group " + fileName);
        }
        return bedrockAnimation;
    }

    private static final boolean loadAnimations$lambda$0(ResourceLocation it) {
        String string = it.m_135815_();
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"it.path");
        return StringsKt.endsWith$default((String)string, (String)".animation.json", (boolean)false, (int)2, null);
    }
}

