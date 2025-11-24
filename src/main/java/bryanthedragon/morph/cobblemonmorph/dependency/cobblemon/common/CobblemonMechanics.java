/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  kotlin.Metadata
 *  kotlin.io.CloseableKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.text.Charsets
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.server.packs.PackType
 *  net.minecraft.server.packs.resources.ResourceManager
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.Expression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.DataRegistry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mechanics.BerriesMechanic;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mechanics.PotionsMechanic;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mechanics.RemediesMechanic;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.ExpressionAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.Closeable;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import kotlin.Metadata;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b<\u0010=J3\u0010\t\u001a\u00028\u0000\"\u0004\b\u0000\u0010\u00022\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u00052\f\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\u0007H\u0002\u00a2\u0006\u0004\b\t\u0010\nJ\u0017\u0010\f\u001a\u00020\u000b2\u0006\u0010\u0004\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\f\u0010\rJ\u0017\u0010\u0010\u001a\u00020\u000b2\u0006\u0010\u000f\u001a\u00020\u000eH\u0016\u00a2\u0006\u0004\b\u0010\u0010\u0011R\"\u0010\u0013\u001a\u00020\u00128\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u001f\u0010\u001b\u001a\n \u001a*\u0004\u0018\u00010\u00190\u00198\u0006\u00a2\u0006\f\n\u0004\b\u001b\u0010\u001c\u001a\u0004\b\u001d\u0010\u001eR\u001a\u0010 \u001a\u00020\u001f8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b \u0010!\u001a\u0004\b\"\u0010#R \u0010%\u001a\b\u0012\u0004\u0012\u00020\u00000$8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b%\u0010&\u001a\u0004\b'\u0010(R\"\u0010*\u001a\u00020)8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b*\u0010+\u001a\u0004\b,\u0010-\"\u0004\b.\u0010/R\"\u00101\u001a\u0002008\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b1\u00102\u001a\u0004\b3\u00104\"\u0004\b5\u00106R\u001a\u00108\u001a\u0002078\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b8\u00109\u001a\u0004\b:\u0010;\u00a8\u0006>"}, d2={"Lcom/cobblemon/mod/common/CobblemonMechanics;", "Lcom/cobblemon/mod/common/api/data/DataRegistry;", "T", "Lnet/minecraft/server/packs/resources/ResourceManager;", "manager", "", "name", "Ljava/lang/Class;", "clazz", "loadMechanic", "(Lnet/minecraft/server/packs/resources/ResourceManager;Ljava/lang/String;Ljava/lang/Class;)Ljava/lang/Object;", "", "reload", "(Lnet/minecraft/server/packs/resources/ResourceManager;)V", "Lnet/minecraft/server/level/ServerPlayer;", "player", "sync", "(Lnet/minecraft/server/level/ServerPlayer;)V", "Lcom/cobblemon/mod/common/mechanics/BerriesMechanic;", "berries", "Lcom/cobblemon/mod/common/mechanics/BerriesMechanic;", "getBerries", "()Lcom/cobblemon/mod/common/mechanics/BerriesMechanic;", "setBerries", "(Lcom/cobblemon/mod/common/mechanics/BerriesMechanic;)V", "Lcom/google/gson/Gson;", "kotlin.jvm.PlatformType", "gson", "Lcom/google/gson/Gson;", "getGson", "()Lcom/google/gson/Gson;", "Lnet/minecraft/resources/ResourceLocation;", "id", "Lnet/minecraft/resources/ResourceLocation;", "getId", "()Lnet/minecraft/resources/ResourceLocation;", "Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "observable", "Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "getObservable", "()Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "Lcom/cobblemon/mod/common/mechanics/PotionsMechanic;", "potions", "Lcom/cobblemon/mod/common/mechanics/PotionsMechanic;", "getPotions", "()Lcom/cobblemon/mod/common/mechanics/PotionsMechanic;", "setPotions", "(Lcom/cobblemon/mod/common/mechanics/PotionsMechanic;)V", "Lcom/cobblemon/mod/common/mechanics/RemediesMechanic;", "remedies", "Lcom/cobblemon/mod/common/mechanics/RemediesMechanic;", "getRemedies", "()Lcom/cobblemon/mod/common/mechanics/RemediesMechanic;", "setRemedies", "(Lcom/cobblemon/mod/common/mechanics/RemediesMechanic;)V", "Lnet/minecraft/server/packs/PackType;", "type", "Lnet/minecraft/server/packs/PackType;", "getType", "()Lnet/minecraft/server/packs/PackType;", "<init>", "()V", "common"})
public final class CobblemonMechanics
implements DataRegistry {
    @NotNull
    public static final CobblemonMechanics INSTANCE = new CobblemonMechanics();
    @NotNull
    private static final ResourceLocation id = MiscUtilsKt.cobblemonResource("mechanics");
    @NotNull
    private static final PackType type = PackType.SERVER_DATA;
    @NotNull
    private static final SimpleObservable<CobblemonMechanics> observable = new SimpleObservable();
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter((Type)((Object)Expression.class), (Object)ExpressionAdapter.INSTANCE).create();
    @NotNull
    private static RemediesMechanic remedies = new RemediesMechanic();
    @NotNull
    private static BerriesMechanic berries = new BerriesMechanic();
    @NotNull
    private static PotionsMechanic potions = new PotionsMechanic();

    private CobblemonMechanics() {
    }

    @Override
    @NotNull
    public ResourceLocation getId() {
        return id;
    }

    @Override
    @NotNull
    public PackType getType() {
        return type;
    }

    @NotNull
    public SimpleObservable<CobblemonMechanics> getObservable() {
        return observable;
    }

    public final Gson getGson() {
        return gson;
    }

    @NotNull
    public final RemediesMechanic getRemedies() {
        return remedies;
    }

    public final void setRemedies(@NotNull RemediesMechanic remediesMechanic) {
        Intrinsics.checkNotNullParameter((Object)remediesMechanic, (String)"<set-?>");
        remedies = remediesMechanic;
    }

    @NotNull
    public final BerriesMechanic getBerries() {
        return berries;
    }

    public final void setBerries(@NotNull BerriesMechanic berriesMechanic) {
        Intrinsics.checkNotNullParameter((Object)berriesMechanic, (String)"<set-?>");
        berries = berriesMechanic;
    }

    @NotNull
    public final PotionsMechanic getPotions() {
        return potions;
    }

    public final void setPotions(@NotNull PotionsMechanic potionsMechanic) {
        Intrinsics.checkNotNullParameter((Object)potionsMechanic, (String)"<set-?>");
        potions = potionsMechanic;
    }

    @Override
    public void sync(@NotNull ServerPlayer player) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
    }

    @Override
    public void reload(@NotNull ResourceManager manager) {
        Intrinsics.checkNotNullParameter((Object)manager, (String)"manager");
        remedies = this.loadMechanic(manager, "remedies", RemediesMechanic.class);
        berries = this.loadMechanic(manager, "berries", BerriesMechanic.class);
        potions = this.loadMechanic(manager, "potions", PotionsMechanic.class);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private final <T> T loadMechanic(ResourceManager manager, String name, Class<T> clazz) {
        Closeable closeable = manager.m_215593_(MiscUtilsKt.cobblemonResource("mechanics/" + name + ".json")).m_215507_();
        Throwable throwable = null;
        try {
            InputStream it = (InputStream)closeable;
            boolean bl = false;
            Intrinsics.checkNotNullExpressionValue((Object)it, (String)"it");
            InputStream inputStream = it;
            Charset charset = Charsets.UTF_8;
            Object object = gson.fromJson((Reader)new InputStreamReader(inputStream, charset), clazz);
            return (T)object;
        }
        catch (Throwable throwable2) {
            throwable = throwable2;
            throw throwable2;
        }
        finally {
            CloseableKt.closeFinally((Closeable)closeable, (Throwable)throwable);
        }
    }
}

