/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.reflect.TypeToken
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.server.packs.PackType
 *  net.minecraft.server.packs.resources.ResourceManager
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.Expression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.JsonDataRegistry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition.SpawningCondition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.rules.SpawnRule;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.rules.component.FilterRuleComponent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.rules.component.LocationRuleCalculator;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.rules.component.SpawnRuleComponent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.rules.component.WeightTweakRuleComponent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.rules.selector.ConditionalSpawningContextSelector;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.rules.selector.ExpressionSpawnDetailSelector;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.rules.selector.ExpressionSpawningContextSelector;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.rules.selector.SpawnDetailSelector;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.rules.selector.SpawningContextSelector;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtils;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.ExpressionAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.SpawnDetailSelectorAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.SpawnRuleComponentAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.SpawningConditionAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.SpawningContextSelectorAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.TextAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010%\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b0\u00101J#\u0010\u0007\u001a\u00020\u00062\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00020\u0003H\u0016\u00a2\u0006\u0004\b\u0007\u0010\bJ\u0017\u0010\u000b\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\tH\u0016\u00a2\u0006\u0004\b\u000b\u0010\fR\"\u0010\u000f\u001a\n \u000e*\u0004\u0018\u00010\r0\r8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\u0011\u0010\u0012R\u001a\u0010\u0013\u001a\u00020\u00048\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0015\u0010\u0016R \u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00000\u00178\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0018\u0010\u0019\u001a\u0004\b\u001a\u0010\u001bR\u001a\u0010\u001d\u001a\u00020\u001c8\u0016X\u0096D\u00a2\u0006\f\n\u0004\b\u001d\u0010\u001e\u001a\u0004\b\u001f\u0010 R#\u0010\"\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00020!8\u0006\u00a2\u0006\f\n\u0004\b\"\u0010#\u001a\u0004\b$\u0010%R\u001a\u0010'\u001a\u00020&8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b'\u0010(\u001a\u0004\b)\u0010*R>\u0010,\u001a&\u0012\f\u0012\n \u000e*\u0004\u0018\u00010\u00020\u0002 \u000e*\u0012\u0012\f\u0012\n \u000e*\u0004\u0018\u00010\u00020\u0002\u0018\u00010+0+8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b,\u0010-\u001a\u0004\b.\u0010/\u00a8\u00062"}, d2={"Lcom/cobblemon/mod/common/api/spawning/CobblemonSpawnRules;", "Lcom/cobblemon/mod/common/api/data/JsonDataRegistry;", "Lcom/cobblemon/mod/common/api/spawning/rules/SpawnRule;", "", "Lnet/minecraft/resources/ResourceLocation;", "data", "", "reload", "(Ljava/util/Map;)V", "Lnet/minecraft/server/level/ServerPlayer;", "player", "sync", "(Lnet/minecraft/server/level/ServerPlayer;)V", "Lcom/google/gson/Gson;", "kotlin.jvm.PlatformType", "gson", "Lcom/google/gson/Gson;", "getGson", "()Lcom/google/gson/Gson;", "id", "Lnet/minecraft/resources/ResourceLocation;", "getId", "()Lnet/minecraft/resources/ResourceLocation;", "Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "observable", "Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "getObservable", "()Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "", "resourcePath", "Ljava/lang/String;", "getResourcePath", "()Ljava/lang/String;", "", "rules", "Ljava/util/Map;", "getRules", "()Ljava/util/Map;", "Lnet/minecraft/server/packs/PackType;", "type", "Lnet/minecraft/server/packs/PackType;", "getType", "()Lnet/minecraft/server/packs/PackType;", "Lcom/google/gson/reflect/TypeToken;", "typeToken", "Lcom/google/gson/reflect/TypeToken;", "getTypeToken", "()Lcom/google/gson/reflect/TypeToken;", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nCobblemonSpawnRules.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CobblemonSpawnRules.kt\ncom/cobblemon/mod/common/api/spawning/CobblemonSpawnRules\n+ 2 _Maps.kt\nkotlin/collections/MapsKt___MapsKt\n+ 3 SpawnRuleComponent.kt\ncom/cobblemon/mod/common/api/spawning/rules/component/SpawnRuleComponent$Companion\n+ 4 SpawnDetailSelector.kt\ncom/cobblemon/mod/common/api/spawning/rules/selector/SpawnDetailSelector$Companion\n+ 5 SpawningContextSelector.kt\ncom/cobblemon/mod/common/api/spawning/rules/selector/SpawningContextSelector$Companion\n*L\n1#1,84:1\n215#2,2:85\n24#3,2:87\n24#3,2:89\n24#3,2:91\n18#4,2:93\n18#5,2:95\n18#5,2:97\n*S KotlinDebug\n*F\n+ 1 CobblemonSpawnRules.kt\ncom/cobblemon/mod/common/api/spawning/CobblemonSpawnRules\n*L\n75#1:85,2\n60#1:87,2\n61#1:89,2\n62#1:91,2\n64#1:93,2\n66#1:95,2\n67#1:97,2\n*E\n"})
public final class CobblemonSpawnRules
implements JsonDataRegistry<SpawnRule> {
    @NotNull
    public static final CobblemonSpawnRules INSTANCE;
    private static final Gson gson;
    private static final TypeToken<SpawnRule> typeToken;
    @NotNull
    private static final String resourcePath;
    @NotNull
    private static final Map<ResourceLocation, SpawnRule> rules;
    @NotNull
    private static final ResourceLocation id;
    @NotNull
    private static final PackType type;
    @NotNull
    private static final SimpleObservable<CobblemonSpawnRules> observable;

    private CobblemonSpawnRules() {
    }

    @Override
    public Gson getGson() {
        return gson;
    }

    @Override
    public TypeToken<SpawnRule> getTypeToken() {
        return typeToken;
    }

    @Override
    @NotNull
    public String getResourcePath() {
        return resourcePath;
    }

    @NotNull
    public final Map<ResourceLocation, SpawnRule> getRules() {
        return rules;
    }

    @Override
    public void reload(@NotNull Map<ResourceLocation, SpawnRule> data) {
        Intrinsics.checkNotNullParameter(data, (String)"data");
        rules.clear();
        rules.putAll(data);
        Map<ResourceLocation, SpawnRule> $this$forEach$iv = data;
        boolean $i$f$forEach = false;
        Iterator<Map.Entry<ResourceLocation, SpawnRule>> iterator = $this$forEach$iv.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<ResourceLocation, SpawnRule> element$iv;
            Map.Entry<ResourceLocation, SpawnRule> entry = element$iv = iterator.next();
            boolean bl = false;
            ResourceLocation id = entry.getKey();
            SpawnRule value2 = entry.getValue();
            value2.setId(id);
        }
        CobblemonSpawnRules[] cobblemonSpawnRulesArray = new CobblemonSpawnRules[]{this};
        this.getObservable().emit((CobblemonSpawnRules[])cobblemonSpawnRulesArray);
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
    public SimpleObservable<CobblemonSpawnRules> getObservable() {
        return observable;
    }

    @Override
    public void sync(@NotNull ServerPlayer player) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
    }

    @Override
    public void reload(@NotNull ResourceManager manager) {
        JsonDataRegistry.DefaultImpls.reload(this, manager);
    }

    static {
        Object this_$iv;
        INSTANCE = new CobblemonSpawnRules();
        gson = new GsonBuilder().registerTypeAdapter((Type)((Object)SpawnRuleComponent.class), (Object)SpawnRuleComponentAdapter.INSTANCE).registerTypeAdapter((Type)((Object)SpawnDetailSelector.class), (Object)SpawnDetailSelectorAdapter.INSTANCE).registerTypeAdapter((Type)((Object)SpawningContextSelector.class), (Object)SpawningContextSelectorAdapter.INSTANCE).registerTypeAdapter((Type)((Object)SpawningCondition.class), (Object)SpawningConditionAdapter.INSTANCE).registerTypeAdapter((Type)((Object)Expression.class), (Object)ExpressionAdapter.INSTANCE).registerTypeAdapter((Type)((Object)Component.class), (Object)TextAdapter.INSTANCE).create();
        typeToken = TypeToken.get(SpawnRule.class);
        resourcePath = "spawn_rules";
        SpawnRuleComponent.Companion companion = SpawnRuleComponent.Companion;
        String type$iv = "weight";
        boolean $i$f$register = false;
        ((SpawnRuleComponent.Companion)this_$iv).getTypes().put(type$iv, WeightTweakRuleComponent.class);
        this_$iv = SpawnRuleComponent.Companion;
        type$iv = "filter";
        $i$f$register = false;
        ((SpawnRuleComponent.Companion)this_$iv).getTypes().put(type$iv, FilterRuleComponent.class);
        this_$iv = SpawnRuleComponent.Companion;
        type$iv = "location";
        $i$f$register = false;
        ((SpawnRuleComponent.Companion)this_$iv).getTypes().put(type$iv, LocationRuleCalculator.class);
        this_$iv = SpawnDetailSelector.Companion;
        type$iv = "expression";
        $i$f$register = false;
        ((SpawnDetailSelector.Companion)this_$iv).getTypes().put(type$iv, ExpressionSpawnDetailSelector.class);
        this_$iv = SpawningContextSelector.Companion;
        type$iv = "expression";
        $i$f$register = false;
        ((SpawningContextSelector.Companion)this_$iv).getTypes().put(type$iv, ExpressionSpawningContextSelector.class);
        this_$iv = SpawningContextSelector.Companion;
        type$iv = "conditional";
        $i$f$register = false;
        ((SpawningContextSelector.Companion)this_$iv).getTypes().put(type$iv, ConditionalSpawningContextSelector.class);
        rules = new LinkedHashMap();
        id = MiscUtils.cobblemonResource("spawn_rules");
        type = PackType.SERVER_DATA;
        observable = new SimpleObservable();
    }
}

