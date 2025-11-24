/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonPrimitive
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.random.Random
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.server.packs.PackType
 *  net.minecraft.server.packs.resources.ResourceManager
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.DataRegistry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.MoveTemplate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.ActionEffectTimeline;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.ActionEffects;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.categories.DamageCategories;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.categories.DamageCategory;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.ElementalType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.ElementalTypes;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.MoveTarget;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.runner.ShowdownService;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.data.MovesRegistrySyncPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.ResourceLocationExtensionsKt;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.random.Random;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u001e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b8\u00109J\u0013\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002\u00a2\u0006\u0004\b\u0004\u0010\u0005J\r\u0010\u0007\u001a\u00020\u0006\u00a2\u0006\u0004\b\u0007\u0010\bJ\u0017\u0010\u000b\u001a\u0004\u0018\u00010\u00032\u0006\u0010\n\u001a\u00020\t\u00a2\u0006\u0004\b\u000b\u0010\fJ\u0015\u0010\r\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\t\u00a2\u0006\u0004\b\r\u0010\fJ\u0017\u0010\u000f\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u000e\u001a\u00020\u0006\u00a2\u0006\u0004\b\u000f\u0010\u0010J\r\u0010\u0011\u001a\u00020\u0003\u00a2\u0006\u0004\b\u0011\u0010\u0012J\u0013\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\t0\u0013\u00a2\u0006\u0004\b\u0014\u0010\u0015J\u001d\u0010\u001a\u001a\u00020\u00172\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00030\u0013H\u0000\u00a2\u0006\u0004\b\u0018\u0010\u0019J\u0017\u0010\u001c\u001a\u00020\u00172\u0006\u0010\u001b\u001a\u00020\u0003H\u0002\u00a2\u0006\u0004\b\u001c\u0010\u001dJ\u0017\u0010 \u001a\u00020\u00172\u0006\u0010\u001f\u001a\u00020\u001eH\u0016\u00a2\u0006\u0004\b \u0010!J\u0017\u0010$\u001a\u00020\u00172\u0006\u0010#\u001a\u00020\"H\u0016\u00a2\u0006\u0004\b$\u0010%R \u0010'\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00030&8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b'\u0010(R\u001a\u0010\u000e\u001a\u00020)8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u000e\u0010*\u001a\u0004\b+\u0010,R \u0010-\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00030&8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b-\u0010(R \u0010/\u001a\b\u0012\u0004\u0012\u00020\u00000.8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b/\u00100\u001a\u0004\b1\u00102R\u001a\u00104\u001a\u0002038\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b4\u00105\u001a\u0004\b6\u00107\u00a8\u0006:"}, d2={"Lcom/cobblemon/mod/common/api/moves/Moves;", "Lcom/cobblemon/mod/common/api/data/DataRegistry;", "", "Lcom/cobblemon/mod/common/api/moves/MoveTemplate;", "all", "()Ljava/util/List;", "", "count", "()I", "", "name", "getByName", "(Ljava/lang/String;)Lcom/cobblemon/mod/common/api/moves/MoveTemplate;", "getByNameOrDummy", "id", "getByNumericalId", "(I)Lcom/cobblemon/mod/common/api/moves/MoveTemplate;", "getExceptional", "()Lcom/cobblemon/mod/common/api/moves/MoveTemplate;", "", "names", "()Ljava/util/Collection;", "moves", "", "receiveSyncPacket$common", "(Ljava/util/Collection;)V", "receiveSyncPacket", "move", "register", "(Lcom/cobblemon/mod/common/api/moves/MoveTemplate;)V", "Lnet/minecraft/server/packs/resources/ResourceManager;", "manager", "reload", "(Lnet/minecraft/server/packs/resources/ResourceManager;)V", "Lnet/minecraft/server/level/ServerPlayer;", "player", "sync", "(Lnet/minecraft/server/level/ServerPlayer;)V", "", "allMoves", "Ljava/util/Map;", "Lnet/minecraft/resources/ResourceLocation;", "Lnet/minecraft/resources/ResourceLocation;", "getId", "()Lnet/minecraft/resources/ResourceLocation;", "idMapping", "Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "observable", "Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "getObservable", "()Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "Lnet/minecraft/server/packs/PackType;", "type", "Lnet/minecraft/server/packs/PackType;", "getType", "()Lnet/minecraft/server/packs/PackType;", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nMoves.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Moves.kt\ncom/cobblemon/mod/common/api/moves/Moves\n+ 2 ArraysJVM.kt\nkotlin/collections/ArraysKt__ArraysJVMKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,120:1\n37#2,2:121\n1855#3,2:123\n*S KotlinDebug\n*F\n+ 1 Moves.kt\ncom/cobblemon/mod/common/api/moves/Moves\n*L\n89#1:121,2\n112#1:123,2\n*E\n"})
public final class Moves
implements DataRegistry {
    @NotNull
    public static final Moves INSTANCE = new Moves();
    @NotNull
    private static final ResourceLocation id = MiscUtilsKt.cobblemonResource("moves");
    @NotNull
    private static final PackType type = PackType.SERVER_DATA;
    @NotNull
    private static final SimpleObservable<Moves> observable = new SimpleObservable();
    @NotNull
    private static final Map<String, MoveTemplate> allMoves = new LinkedHashMap();
    @NotNull
    private static final Map<Integer, MoveTemplate> idMapping = new LinkedHashMap();

    private Moves() {
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
    public SimpleObservable<Moves> getObservable() {
        return observable;
    }

    @Override
    public void reload(@NotNull ResourceManager manager) {
        Intrinsics.checkNotNullParameter((Object)manager, (String)"manager");
        allMoves.clear();
        idMapping.clear();
        JsonArray movesJson = ShowdownService.Companion.getService().getMoves();
        int n = movesJson.size();
        for (int i = 0; i < n; ++i) {
            JsonObject jsMove = movesJson.get(i).getAsJsonObject();
            String id = jsMove.get("id").getAsString();
            try {
                int num = jsMove.get("num").getAsInt();
                String string = jsMove.get("type").getAsString();
                Intrinsics.checkNotNullExpressionValue((Object)string, (String)"jsMove.get(\"type\").asString");
                ElementalType elementalType = ElementalTypes.INSTANCE.getOrException(string);
                String string2 = jsMove.get("category").getAsString();
                Intrinsics.checkNotNullExpressionValue((Object)string2, (String)"jsMove.get(\"category\").asString");
                DamageCategory damageCategory = DamageCategories.INSTANCE.getOrException(string2);
                double power = jsMove.get("basePower").getAsDouble();
                String string3 = jsMove.get("target").getAsString();
                Intrinsics.checkNotNullExpressionValue((Object)string3, (String)"jsMove.get(\"target\").asString");
                MoveTarget target = MoveTarget.Companion.fromShowdownId(string3);
                JsonPrimitive accuracyJson = jsMove.get("accuracy").getAsJsonPrimitive();
                double accuracy = accuracyJson.isNumber() ? accuracyJson.getAsDouble() : -1.0;
                int pp = jsMove.get("pp").getAsInt();
                int priority = jsMove.get("priority").getAsInt();
                JsonElement jsonElement = jsMove.get("critRatio");
                double critRatio = jsonElement != null ? jsonElement.getAsDouble() : 1.0;
                ArrayList effectChances = new ArrayList();
                JsonElement secondariesMember = jsMove.get("secondaries");
                JsonElement secondaryMember = jsMove.get("secondary");
                if (secondariesMember != null && secondariesMember instanceof JsonArray) {
                    int n2 = ((JsonArray)secondariesMember).size();
                    for (int j = 0; j < n2; ++j) {
                        JsonObject element = ((JsonArray)secondariesMember).get(j).getAsJsonObject();
                        if (!element.has("chance")) continue;
                        ((Collection)effectChances).add(element.get("chance").getAsDouble());
                    }
                } else if (secondaryMember != null && secondaryMember instanceof JsonObject && ((JsonObject)secondaryMember).has("chance")) {
                    ((Collection)effectChances).add(((JsonObject)secondaryMember).get("chance").getAsDouble());
                }
                Map<ResourceLocation, ActionEffectTimeline> map = ActionEffects.INSTANCE.getActionEffects();
                Intrinsics.checkNotNullExpressionValue((Object)id, (String)"id");
                ActionEffectTimeline actionEffectTimeline = map.get(ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default(id, null, 1, null));
                if (actionEffectTimeline == null) {
                    Moves $this$reload_u24lambda_u240 = this;
                    boolean bl = false;
                    actionEffectTimeline = ActionEffects.INSTANCE.getActionEffects().get(ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default("generic_move", null, 1, null));
                }
                ActionEffectTimeline actionEffect = actionEffectTimeline;
                Collection $this$toTypedArray$iv = effectChances;
                boolean $i$f$toTypedArray = false;
                Collection thisCollection$iv = $this$toTypedArray$iv;
                MoveTemplate move = new MoveTemplate(id, num, elementalType, damageCategory, power, target, accuracy, pp, priority, critRatio, thisCollection$iv.toArray(new Double[0]), actionEffect);
                this.register(move);
                continue;
            }
            catch (Exception e) {
                Cobblemon.INSTANCE.getLOGGER().error("Caught exception trying to resolve the move '{}'", (Object)id, (Object)e);
            }
        }
        Cobblemon.INSTANCE.getLOGGER().info("Loaded {} moves", (Object)allMoves.size());
        Moves[] movesArray = new Moves[]{this};
        this.getObservable().emit((Moves[])movesArray);
    }

    @Override
    public void sync(@NotNull ServerPlayer player) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        new MovesRegistrySyncPacket(this.all()).sendToPlayer(player);
    }

    @Nullable
    public final MoveTemplate getByName(@NotNull String name) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        String string = name.toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"this as java.lang.String).toLowerCase(Locale.ROOT)");
        return allMoves.get(string);
    }

    @Nullable
    public final MoveTemplate getByNumericalId(int id) {
        return idMapping.get(id);
    }

    @NotNull
    public final MoveTemplate getByNameOrDummy(@NotNull String name) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        String string = name.toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"this as java.lang.String).toLowerCase(Locale.ROOT)");
        MoveTemplate moveTemplate = allMoves.get(string);
        if (moveTemplate == null) {
            String string2 = name.toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue((Object)string2, (String)"this as java.lang.String).toLowerCase(Locale.ROOT)");
            moveTemplate = MoveTemplate.Companion.dummy(string2);
        }
        return moveTemplate;
    }

    @NotNull
    public final MoveTemplate getExceptional() {
        MoveTemplate moveTemplate = this.getByName("tackle");
        if (moveTemplate == null) {
            moveTemplate = (MoveTemplate)CollectionsKt.random(allMoves.values(), (Random)((Random)Random.Default));
        }
        return moveTemplate;
    }

    public final int count() {
        return allMoves.size();
    }

    @NotNull
    public final Collection<String> names() {
        return CollectionsKt.toSet((Iterable)allMoves.keySet());
    }

    @NotNull
    public final List<MoveTemplate> all() {
        return CollectionsKt.toList((Iterable)allMoves.values());
    }

    public final void receiveSyncPacket$common(@NotNull Collection<? extends MoveTemplate> moves) {
        Intrinsics.checkNotNullParameter(moves, (String)"moves");
        Iterable $this$forEach$iv = moves;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            MoveTemplate p0 = (MoveTemplate)element$iv;
            boolean bl = false;
            this.register(p0);
        }
    }

    private final void register(MoveTemplate move) {
        allMoves.put(move.getName(), move);
        idMapping.put(move.getNum(), move);
    }
}

