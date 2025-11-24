/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.NbtIo
 *  net.minecraft.nbt.Tag
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.molang;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.struct.VariableStruct;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value.MoValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.Observable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.molang.MoLangDataStoreFactory;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.PlatformEvents;
import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.Tag;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010%\n\u0002\b\u0004\n\u0002\u0010!\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u000b\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b,\u0010\u0010J\u001f\u0010\u0006\u001a\n \u0005*\u0004\u0018\u00010\u00040\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0002\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u0017\u0010\t\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\t\u0010\nJ\u0017\u0010\f\u001a\u00020\u000b2\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\f\u0010\rJ\u0015\u0010\u000e\u001a\u00020\u000b2\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u000e\u0010\rJ\r\u0010\u000f\u001a\u00020\u000b\u00a2\u0006\u0004\b\u000f\u0010\u0010R#\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\b0\u00118\u0006\u00a2\u0006\f\n\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u0014\u0010\u0015R\u001d\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00020\u00168\u0006\u00a2\u0006\f\n\u0004\b\u0017\u0010\u0018\u001a\u0004\b\u0019\u0010\u001aR\"\u0010\u001c\u001a\u00020\u001b8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b\u001c\u0010\u001d\u001a\u0004\b\u001e\u0010\u001f\"\u0004\b \u0010!R\"\u0010#\u001a\u00020\"8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b#\u0010$\u001a\u0004\b%\u0010&\"\u0004\b'\u0010(R\"\u0010)\u001a\u00020\"8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b)\u0010$\u001a\u0004\b*\u0010&\"\u0004\b+\u0010(\u00a8\u0006-"}, d2={"Lcom/cobblemon/mod/common/api/storage/molang/NbtMoLangDataStoreFactory;", "Lcom/cobblemon/mod/common/api/storage/molang/MoLangDataStoreFactory;", "Ljava/util/UUID;", "uuid", "Ljava/io/File;", "kotlin.jvm.PlatformType", "file", "(Ljava/util/UUID;)Ljava/io/File;", "Lcom/bedrockk/molang/runtime/struct/VariableStruct;", "load", "(Ljava/util/UUID;)Lcom/bedrockk/molang/runtime/struct/VariableStruct;", "", "markDirty", "(Ljava/util/UUID;)V", "save", "saveAll", "()V", "", "cache", "Ljava/util/Map;", "getCache", "()Ljava/util/Map;", "", "dirty", "Ljava/util/List;", "getDirty", "()Ljava/util/List;", "Ljava/nio/file/Path;", "savePath", "Ljava/nio/file/Path;", "getSavePath", "()Ljava/nio/file/Path;", "setSavePath", "(Ljava/nio/file/Path;)V", "", "saveTicks", "I", "getSaveTicks", "()I", "setSaveTicks", "(I)V", "ticker", "getTicker", "setTicker", "<init>", "common"})
@SourceDebugExtension(value={"SMAP\nNbtMoLangDataStoreFactory.kt\nKotlin\n*S Kotlin\n*F\n+ 1 NbtMoLangDataStoreFactory.kt\ncom/cobblemon/mod/common/api/storage/molang/NbtMoLangDataStoreFactory\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,89:1\n1855#2,2:90\n*S KotlinDebug\n*F\n+ 1 NbtMoLangDataStoreFactory.kt\ncom/cobblemon/mod/common/api/storage/molang/NbtMoLangDataStoreFactory\n*L\n52#1:90,2\n*E\n"})
public final class NbtMoLangDataStoreFactory
implements MoLangDataStoreFactory {
    @NotNull
    public static final NbtMoLangDataStoreFactory INSTANCE = new NbtMoLangDataStoreFactory();
    public static Path savePath;
    @NotNull
    private static final Map<UUID, VariableStruct> cache;
    @NotNull
    private static final List<UUID> dirty;
    private static int ticker;
    private static int saveTicks;

    private NbtMoLangDataStoreFactory() {
    }

    @NotNull
    public final Path getSavePath() {
        Path path = savePath;
        if (path != null) {
            return path;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"savePath");
        return null;
    }

    public final void setSavePath(@NotNull Path path) {
        Intrinsics.checkNotNullParameter((Object)path, (String)"<set-?>");
        savePath = path;
    }

    @NotNull
    public final Map<UUID, VariableStruct> getCache() {
        return cache;
    }

    @NotNull
    public final List<UUID> getDirty() {
        return dirty;
    }

    public final int getTicker() {
        return ticker;
    }

    public final void setTicker(int n) {
        ticker = n;
    }

    public final int getSaveTicks() {
        return saveTicks;
    }

    public final void setSaveTicks(int n) {
        saveTicks = n;
    }

    public final void saveAll() {
        Iterable $this$forEach$iv = CollectionsKt.toList((Iterable)dirty);
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            UUID p0 = (UUID)element$iv;
            boolean bl = false;
            this.save(p0);
        }
    }

    @Override
    public void markDirty(@NotNull UUID uuid2) {
        Intrinsics.checkNotNullParameter((Object)uuid2, (String)"uuid");
        dirty.add(uuid2);
    }

    @Override
    @NotNull
    public VariableStruct load(@NotNull UUID uuid2) {
        VariableStruct variableStruct;
        Intrinsics.checkNotNullParameter((Object)uuid2, (String)"uuid");
        if (cache.containsKey(uuid2)) {
            VariableStruct variableStruct2 = cache.get(uuid2);
            Intrinsics.checkNotNull((Object)variableStruct2);
            variableStruct = variableStruct2;
        } else {
            File file = this.file(uuid2);
            if (!file.exists()) {
                VariableStruct data = new VariableStruct();
                cache.put(uuid2, data);
                return data;
            }
            CompoundTag nbt = NbtIo.m_128937_((File)this.file(uuid2));
            Intrinsics.checkNotNullExpressionValue((Object)nbt, (String)"nbt");
            MoValue moValue = MoLangFunctions.INSTANCE.readMoValueFromNBT((Tag)nbt);
            Intrinsics.checkNotNull((Object)moValue, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.struct.VariableStruct");
            VariableStruct data = (VariableStruct)moValue;
            cache.put(uuid2, data);
            variableStruct = data;
        }
        return variableStruct;
    }

    public final void save(@NotNull UUID uuid2) {
        Intrinsics.checkNotNullParameter((Object)uuid2, (String)"uuid");
        File file = this.file(uuid2);
        VariableStruct variableStruct = cache.get(uuid2);
        if (variableStruct == null) {
            return;
        }
        VariableStruct data = variableStruct;
        Tag tag = MoLangFunctions.INSTANCE.writeMoValueToNBT(data);
        Intrinsics.checkNotNull((Object)tag);
        CompoundTag nbt = (CompoundTag)tag;
        file.getParentFile().mkdirs();
        NbtIo.m_128944_((CompoundTag)nbt, (File)file);
        ((Collection)dirty).remove(uuid2);
    }

    private final File file(UUID uuid2) {
        Path path = this.getSavePath();
        String string = uuid2.toString();
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"uuid.toString()");
        String string2 = string;
        int n = 0;
        int n2 = 2;
        String string3 = string2.substring(n, n2);
        Intrinsics.checkNotNullExpressionValue((Object)string3, (String)"this as java.lang.String\u2026ing(startIndex, endIndex)");
        return path.resolve("playermolangdata/" + string3 + "/" + uuid2 + ".dat").toFile();
    }

    static {
        cache = new LinkedHashMap();
        dirty = new ArrayList();
        saveTicks = 100;
        Observable.DefaultImpls.subscribe$default(PlatformEvents.SERVER_STARTED, null, 1.INSTANCE, 1, null);
        Observable.DefaultImpls.subscribe$default(PlatformEvents.SERVER_PLAYER_LOGOUT, null, 2.INSTANCE, 1, null);
        Observable.DefaultImpls.subscribe$default(PlatformEvents.SERVER_STOPPING, null, 3.INSTANCE, 1, null);
        Observable.DefaultImpls.subscribe$default(PlatformEvents.SERVER_TICK_POST, null, 4.INSTANCE, 1, null);
    }
}

