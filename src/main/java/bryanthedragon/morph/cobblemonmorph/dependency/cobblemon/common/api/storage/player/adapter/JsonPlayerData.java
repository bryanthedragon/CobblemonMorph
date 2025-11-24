/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.Reflection
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.reflect.KClass
 *  kotlin.reflect.KMutableProperty
 *  kotlin.reflect.full.KClasses
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.world.level.storage.LevelResource
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.adapter;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.PlayerData;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.PlayerDataExtension;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.adapter.PlayerDataExtensionAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.adapter.PlayerDataStoreAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.IdentifierAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.reflect.KClass;
import kotlin.reflect.KMutableProperty;
import kotlin.reflect.full.KClasses;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.storage.LevelResource;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\n\u0018\u0000 &2\u00020\u0001:\u0001&B\u0007\u00a2\u0006\u0004\b$\u0010%J\u001f\u0010\u0006\u001a\n \u0005*\u0004\u0018\u00010\u00040\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0002\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u0015\u0010\t\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\t\u0010\nJ\u0017\u0010\f\u001a\u00020\u000b2\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\f\u0010\rJ\u0017\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u000e\u001a\u00020\u000bH\u0016\u00a2\u0006\u0004\b\u0010\u0010\u0011J\u0015\u0010\u0014\u001a\u00020\u000f2\u0006\u0010\u0013\u001a\u00020\u0012\u00a2\u0006\u0004\b\u0014\u0010\u0015R\"\u0010\u0017\u001a\u00020\u00168\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b\u0017\u0010\u0018\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001cR\"\u0010\u001e\u001a\u00020\u001d8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u001e\u0010\u001f\u001a\u0004\b \u0010!\"\u0004\b\"\u0010#\u00a8\u0006'"}, d2={"Lcom/cobblemon/mod/common/api/storage/player/adapter/JsonPlayerData;", "Lcom/cobblemon/mod/common/api/storage/player/adapter/PlayerDataStoreAdapter;", "Ljava/util/UUID;", "uuid", "Ljava/io/File;", "kotlin.jvm.PlatformType", "file", "(Ljava/util/UUID;)Ljava/io/File;", "", "getSubFile", "(Ljava/util/UUID;)Ljava/lang/String;", "Lcom/cobblemon/mod/common/api/storage/player/PlayerData;", "load", "(Ljava/util/UUID;)Lcom/cobblemon/mod/common/api/storage/player/PlayerData;", "playerData", "", "save", "(Lcom/cobblemon/mod/common/api/storage/player/PlayerData;)V", "Lnet/minecraft/server/MinecraftServer;", "server", "setup", "(Lnet/minecraft/server/MinecraftServer;)V", "Ljava/nio/file/Path;", "savePath", "Ljava/nio/file/Path;", "getSavePath", "()Ljava/nio/file/Path;", "setSavePath", "(Ljava/nio/file/Path;)V", "", "useNestedStructure", "Z", "getUseNestedStructure", "()Z", "setUseNestedStructure", "(Z)V", "<init>", "()V", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nJsonPlayerData.kt\nKotlin\n*S Kotlin\n*F\n+ 1 JsonPlayerData.kt\ncom/cobblemon/mod/common/api/storage/player/adapter/JsonPlayerData\n+ 2 GsonExtensions.kt\ncom/cobblemon/mod/common/util/GsonExtensionsKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 4 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,79:1\n17#2:80\n800#3,11:81\n766#3:92\n857#3,2:93\n1855#3,2:95\n1#4:97\n*S KotlinDebug\n*F\n+ 1 JsonPlayerData.kt\ncom/cobblemon/mod/common/api/storage/player/adapter/JsonPlayerData\n*L\n58#1:80\n60#1:81,11\n60#1:92\n60#1:93,2\n63#1:95,2\n*E\n"})
public final class JsonPlayerData
implements PlayerDataStoreAdapter {
    @NotNull
    public static final Companion Companion = new Companion(null);
    public Path savePath;
    private boolean useNestedStructure = true;
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().registerTypeAdapter((Type)((Object)PlayerDataExtension.class), (Object)PlayerDataExtensionAdapter.INSTANCE).registerTypeAdapter((Type)((Object)ResourceLocation.class), (Object)IdentifierAdapter.INSTANCE).create();

    @NotNull
    public final Path getSavePath() {
        Path path = this.savePath;
        if (path != null) {
            return path;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"savePath");
        return null;
    }

    public final void setSavePath(@NotNull Path path) {
        Intrinsics.checkNotNullParameter((Object)path, (String)"<set-?>");
        this.savePath = path;
    }

    public final boolean getUseNestedStructure() {
        return this.useNestedStructure;
    }

    public final void setUseNestedStructure(boolean bl) {
        this.useNestedStructure = bl;
    }

    public final void setup(@NotNull MinecraftServer server) {
        Intrinsics.checkNotNullParameter((Object)server, (String)"server");
        Path path = server.m_129843_(LevelResource.f_78176_).getParent();
        Intrinsics.checkNotNullExpressionValue((Object)path, (String)"server.getSavePath(World\u2026vePath.PLAYERDATA).parent");
        this.setSavePath(path);
    }

    @NotNull
    public final String getSubFile(@NotNull UUID uuid2) {
        String string;
        Intrinsics.checkNotNullParameter((Object)uuid2, (String)"uuid");
        if (this.useNestedStructure) {
            String string2 = uuid2.toString();
            Intrinsics.checkNotNullExpressionValue((Object)string2, (String)"uuid.toString()");
            String string3 = string2;
            int n = 0;
            int n2 = 2;
            String string4 = string3.substring(n, n2);
            Intrinsics.checkNotNullExpressionValue((Object)string4, (String)"this as java.lang.String\u2026ing(startIndex, endIndex)");
            string = string4 + "/" + uuid2 + ".json";
        } else {
            string = uuid2 + ".json";
        }
        return string;
    }

    private final File file(UUID uuid2) {
        return this.getSavePath().resolve("cobblemonplayerdata/" + this.getSubFile(uuid2)).toFile();
    }

    /*
     * WARNING - void declaration
     */
    @Override
    @NotNull
    public PlayerData load(@NotNull UUID uuid2) {
        PlayerData playerData;
        Intrinsics.checkNotNullParameter((Object)uuid2, (String)"uuid");
        File playerFile = this.file(uuid2);
        playerFile.getParentFile().mkdirs();
        if (playerFile.exists()) {
            void $this$filterTo$iv$iv;
            void $this$filter$iv;
            void $this$filterIsInstanceTo$iv$iv;
            Object $this$fromJson$iv;
            Gson gson2 = gson;
            Intrinsics.checkNotNullExpressionValue((Object)gson2, (String)"gson");
            Gson gson3 = gson2;
            Reader reader$iv = new BufferedReader(new FileReader(playerFile));
            boolean $i$f$fromJson = false;
            $this$fromJson$iv = $this$fromJson$iv.fromJson(reader$iv, PlayerData.class);
            PlayerData it = (PlayerData)$this$fromJson$iv;
            boolean bl = false;
            Iterable $this$filterIsInstance$iv = KClasses.getMemberProperties((KClass)Reflection.getOrCreateKotlinClass(it.getClass()));
            boolean $i$f$filterIsInstance = false;
            Iterable iterable = $this$filterIsInstance$iv;
            Collection destination$iv$iv = new ArrayList();
            boolean $i$f$filterIsInstanceTo = false;
            for (Object element$iv$iv : $this$filterIsInstanceTo$iv$iv) {
                if (!(element$iv$iv instanceof KMutableProperty)) continue;
                destination$iv$iv.add(element$iv$iv);
            }
            $this$filterIsInstance$iv = (List)destination$iv$iv;
            boolean $i$f$filter = false;
            $this$filterIsInstanceTo$iv$iv = $this$filter$iv;
            destination$iv$iv = new ArrayList();
            boolean $i$f$filterTo = false;
            for (Object element$iv$iv : $this$filterTo$iv$iv) {
                KMutableProperty member = (KMutableProperty)element$iv$iv;
                boolean bl2 = false;
                Object[] objectArray = new Object[]{it};
                if (!(member.getGetter().call(objectArray) == null)) continue;
                destination$iv$iv.add(element$iv$iv);
            }
            List newProps = (List)destination$iv$iv;
            if (!((Collection)newProps).isEmpty()) {
                PlayerData defaultData = PlayerData.Companion.defaultData(uuid2);
                Iterable $this$forEach$iv = newProps;
                boolean $i$f$forEach = false;
                for (Object element$iv : $this$forEach$iv) {
                    KMutableProperty member = (KMutableProperty)element$iv;
                    boolean bl3 = false;
                    Object[] objectArray = new Object[2];
                    objectArray[0] = it;
                    Object[] objectArray2 = new Object[]{defaultData};
                    objectArray[1] = member.getGetter().call(objectArray2);
                    member.getSetter().call(objectArray);
                }
            }
            Object object = $this$fromJson$iv;
            Intrinsics.checkNotNullExpressionValue((Object)object, (String)"{\n            gson.fromJ\u2026}\n            }\n        }");
            playerData = (PlayerData)object;
        } else {
            PlayerData playerData2;
            PlayerData p0 = playerData2 = PlayerData.Companion.defaultData(uuid2);
            boolean bl = false;
            this.save(p0);
            playerData = playerData2;
        }
        return playerData;
    }

    @Override
    public void save(@NotNull PlayerData playerData) {
        Intrinsics.checkNotNullParameter((Object)playerData, (String)"playerData");
        File file = this.file(playerData.getUuid());
        file.getParentFile().mkdirs();
        PrintWriter pw = new PrintWriter(this.file(playerData.getUuid()));
        pw.write(gson.toJson((Object)playerData));
        pw.flush();
        pw.close();
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\b\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\b\u0010\tR\u001f\u0010\u0004\u001a\n \u0003*\u0004\u0018\u00010\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\n"}, d2={"Lcom/cobblemon/mod/common/api/storage/player/adapter/JsonPlayerData$Companion;", "", "Lcom/google/gson/Gson;", "kotlin.jvm.PlatformType", "gson", "Lcom/google/gson/Gson;", "getGson", "()Lcom/google/gson/Gson;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public final Gson getGson() {
            return gson;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

