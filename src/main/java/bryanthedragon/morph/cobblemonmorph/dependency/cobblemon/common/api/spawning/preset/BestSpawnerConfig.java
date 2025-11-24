/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.TuplesKt
 *  kotlin.collections.CollectionsKt
 *  kotlin.collections.MapsKt
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.preset;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnBucket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.RegisteredSpawningContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.RegisteredSpawningContextAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\b\u0018\u0000 \u001b2\u00020\u0001:\u0001\u001bB\u0007\u00a2\u0006\u0004\b\u0019\u0010\u001aR\u001d\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007R#\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\b8\u0006\u00a2\u0006\f\n\u0004\b\u000b\u0010\f\u001a\u0004\b\r\u0010\u000eR\u001a\u0010\u0010\u001a\u00020\u000f8\u0006X\u0086D\u00a2\u0006\f\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u0012\u0010\u0013R\u001a\u0010\u0015\u001a\u00020\u00148\u0006X\u0086D\u00a2\u0006\f\n\u0004\b\u0015\u0010\u0016\u001a\u0004\b\u0017\u0010\u0018\u00a8\u0006\u001c"}, d2={"Lcom/cobblemon/mod/common/api/spawning/preset/BestSpawnerConfig;", "", "", "Lcom/cobblemon/mod/common/api/spawning/SpawnBucket;", "buckets", "Ljava/util/List;", "getBuckets", "()Ljava/util/List;", "", "", "", "contextWeights", "Ljava/util/Map;", "getContextWeights", "()Ljava/util/Map;", "", "replaceWithNewVersion", "Z", "getReplaceWithNewVersion", "()Z", "", "version", "I", "getVersion", "()I", "<init>", "()V", "Companion", "common"})
public final class BestSpawnerConfig {
    @NotNull
    public static final Companion Companion = new Companion(null);
    private final int version;
    private final boolean replaceWithNewVersion;
    @NotNull
    private final Map<String, Float> contextWeights;
    @NotNull
    private final List<SpawnBucket> buckets;
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().registerTypeAdapter((Type)((Object)RegisteredSpawningContext.class), (Object)RegisteredSpawningContextAdapter.INSTANCE).setLenient().disableHtmlEscaping().create();
    @NotNull
    public static final String CONFIG_NAME = "best-spawner-config";

    public BestSpawnerConfig() {
        this.replaceWithNewVersion = true;
        Object[] objectArray = new Pair[]{TuplesKt.to((Object)"grounded", (Object)Float.valueOf(1.0f)), TuplesKt.to((Object)"submerged", (Object)Float.valueOf(0.99f)), TuplesKt.to((Object)"surface", (Object)Float.valueOf(0.01f))};
        this.contextWeights = MapsKt.mutableMapOf((Pair[])objectArray);
        objectArray = new SpawnBucket[]{new SpawnBucket("common", 93.8f), new SpawnBucket("uncommon", 5.0f), new SpawnBucket("rare", 1.0f), new SpawnBucket("ultra-rare", 0.2f)};
        this.buckets = CollectionsKt.mutableListOf((Object[])objectArray);
    }

    public final int getVersion() {
        return this.version;
    }

    public final boolean getReplaceWithNewVersion() {
        return this.replaceWithNewVersion;
    }

    @NotNull
    public final Map<String, Float> getContextWeights() {
        return this.contextWeights;
    }

    @NotNull
    public final List<SpawnBucket> getBuckets() {
        return this.buckets;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0013\u0010\tJ\r\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u0011\u0010\u0005\u001a\u0004\u0018\u00010\u0002H\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0004J\u000f\u0010\u0006\u001a\u00020\u0002H\u0002\u00a2\u0006\u0004\b\u0006\u0010\u0004J\r\u0010\b\u001a\u00020\u0007\u00a2\u0006\u0004\b\b\u0010\tR\u0014\u0010\u000b\u001a\u00020\n8\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u000b\u0010\fR\u001f\u0010\u000f\u001a\n \u000e*\u0004\u0018\u00010\r0\r8\u0006\u00a2\u0006\f\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\u0011\u0010\u0012\u00a8\u0006\u0014"}, d2={"Lcom/cobblemon/mod/common/api/spawning/preset/BestSpawnerConfig$Companion;", "", "Lcom/cobblemon/mod/common/api/spawning/preset/BestSpawnerConfig;", "load", "()Lcom/cobblemon/mod/common/api/spawning/preset/BestSpawnerConfig;", "loadExternal", "loadInternal", "", "saveExternal", "()V", "", "CONFIG_NAME", "Ljava/lang/String;", "Lcom/google/gson/Gson;", "kotlin.jvm.PlatformType", "GSON", "Lcom/google/gson/Gson;", "getGSON", "()Lcom/google/gson/Gson;", "<init>", "common"})
    public static final class Companion {
        private Companion() {
        }

        public final Gson getGSON() {
            return GSON;
        }

        @NotNull
        public final BestSpawnerConfig load() {
            BestSpawnerConfig internal = this.loadInternal();
            if (Cobblemon.INSTANCE.getConfig().getExportSpawnConfig()) {
                BestSpawnerConfig bestSpawnerConfig;
                BestSpawnerConfig external = this.loadExternal();
                if (external == null) {
                    this.saveExternal();
                    bestSpawnerConfig = internal;
                } else if (external.getReplaceWithNewVersion() && internal.getVersion() > external.getVersion()) {
                    this.saveExternal();
                    bestSpawnerConfig = internal;
                } else {
                    bestSpawnerConfig = external;
                }
                return bestSpawnerConfig;
            }
            return internal;
        }

        private final BestSpawnerConfig loadInternal() {
            InputStream inputStream = Cobblemon.class.getResourceAsStream("/assets/cobblemon/spawning/best-spawner-config.json");
            Intrinsics.checkNotNull((Object)inputStream);
            InputStreamReader reader = new InputStreamReader(inputStream);
            BestSpawnerConfig config = (BestSpawnerConfig)this.getGSON().fromJson((Reader)reader, BestSpawnerConfig.class);
            reader.close();
            Intrinsics.checkNotNullExpressionValue((Object)config, (String)"config");
            return config;
        }

        private final BestSpawnerConfig loadExternal() {
            BestSpawnerConfig bestSpawnerConfig;
            File configFile = new File("config/cobblemon/spawning/best-spawner-config.json");
            configFile.getParentFile().mkdirs();
            if (configFile.exists()) {
                BestSpawnerConfig bestSpawnerConfig2;
                try {
                    FileReader reader = new FileReader(configFile);
                    BestSpawnerConfig config = (BestSpawnerConfig)this.getGSON().fromJson((Reader)reader, BestSpawnerConfig.class);
                    reader.close();
                    bestSpawnerConfig2 = config;
                }
                catch (Exception e) {
                    Cobblemon.INSTANCE.getLOGGER().error("Unable to load external Best Spawner configuration", (Throwable)e);
                    bestSpawnerConfig2 = null;
                }
                bestSpawnerConfig = bestSpawnerConfig2;
            } else {
                bestSpawnerConfig = null;
            }
            return bestSpawnerConfig;
        }

        public final void saveExternal() {
            InputStream inputStream = Cobblemon.class.getResourceAsStream("/assets/cobblemon/spawning/best-spawner-config.json");
            Intrinsics.checkNotNull((Object)inputStream);
            InputStream stream = inputStream;
            byte[] bytes = stream.readAllBytes();
            stream.close();
            File configFile = new File("config/cobblemon/spawning/best-spawner-config.json");
            configFile.getParentFile().mkdirs();
            configFile.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(configFile);
            outputStream.write(bytes);
            outputStream.close();
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

