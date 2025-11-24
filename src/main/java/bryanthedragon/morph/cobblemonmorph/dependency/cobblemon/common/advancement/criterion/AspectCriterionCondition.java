/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.advancements.critereon.ContextAwarePredicate
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerPlayer
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.SimpleCriterionCondition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.ResourceLocationExtensionsKt;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;

import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0010#\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u001a\u0012\u0016\u0012\u0014\u0012\u0004\u0012\u00020\u0003\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u00020\u0001B\u0017\u0012\u0006\u0010\u001f\u001a\u00020\u0003\u0012\u0006\u0010!\u001a\u00020 \u00a2\u0006\u0004\b\"\u0010#J\u0017\u0010\t\u001a\u00020\b2\u0006\u0010\u0007\u001a\u00020\u0006H\u0016\u00a2\u0006\u0004\b\t\u0010\nJ1\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\f\u001a\u00020\u000b2\u0018\u0010\r\u001a\u0014\u0012\u0004\u0012\u00020\u0003\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u0002H\u0016\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u0017\u0010\u0011\u001a\u00020\b2\u0006\u0010\u0007\u001a\u00020\u0006H\u0016\u00a2\u0006\u0004\b\u0011\u0010\nR(\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00050\u00128\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\"\u0010\u0019\u001a\u00020\u00038\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0019\u0010\u001a\u001a\u0004\b\u001b\u0010\u001c\"\u0004\b\u001d\u0010\u001e\u00a8\u0006$"}, d2={"Lcom/cobblemon/mod/common/advancement/criterion/AspectCriterionCondition;", "Lcom/cobblemon/mod/common/advancement/criterion/SimpleCriterionCondition;", "", "Lnet/minecraft/resources/ResourceLocation;", "", "", "Lcom/google/gson/JsonObject;", "json", "", "fromJson", "(Lcom/google/gson/JsonObject;)V", "Lnet/minecraft/server/level/ServerPlayer;", "player", "context", "", "matches", "(Lnet/minecraft/server/level/ServerPlayer;Ljava/util/Map;)Z", "toJson", "", "aspects", "Ljava/util/List;", "getAspects", "()Ljava/util/List;", "setAspects", "(Ljava/util/List;)V", "pokemon", "Lnet/minecraft/resources/ResourceLocation;", "getPokemon", "()Lnet/minecraft/resources/ResourceLocation;", "setPokemon", "(Lnet/minecraft/resources/ResourceLocation;)V", "id", "Lnet/minecraft/advancements/critereon/ContextAwarePredicate;", "predicate", "<init>", "(Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/advancements/critereon/ContextAwarePredicate;)V", "common"})
@SourceDebugExtension(value={"SMAP\nAspectCriterion.kt\nKotlin\n*S Kotlin\n*F\n+ 1 AspectCriterion.kt\ncom/cobblemon/mod/common/advancement/criterion/AspectCriterionCondition\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,45:1\n1855#2,2:46\n1855#2,2:48\n1726#2,3:50\n*S KotlinDebug\n*F\n+ 1 AspectCriterion.kt\ncom/cobblemon/mod/common/advancement/criterion/AspectCriterionCondition\n*L\n27#1:46,2\n34#1:48,2\n42#1:50,3\n*E\n"})
public final class AspectCriterionCondition
extends SimpleCriterionCondition<Map<ResourceLocation, Set<String>>> {
    @NotNull
    private ResourceLocation pokemon;
    @NotNull
    private List<String> aspects;

    @SuppressWarnings({ "removal", "rawtypes", "unchecked" })
    public AspectCriterionCondition(@NotNull ResourceLocation id, @NotNull ContextAwarePredicate predicate) {
        Intrinsics.checkNotNullParameter((Object)id, (String)"id");
        Intrinsics.checkNotNullParameter((Object)predicate, (String)"predicate");
        super(id, predicate);
        this.pokemon = new ResourceLocation("cobblemon:pikachu");
        this.aspects = new ArrayList();
    }

    @NotNull
    public final ResourceLocation getPokemon() {
        return this.pokemon;
    }

    public final void setPokemon(@NotNull ResourceLocation resourceLocation) {
        Intrinsics.checkNotNullParameter((Object)resourceLocation, (String)"<set-?>");
        this.pokemon = resourceLocation;
    }

    @NotNull
    public final List<String> getAspects() {
        return this.aspects;
    }

    public final void setAspects(@NotNull List<String> list) {
        Intrinsics.checkNotNullParameter(list, (String)"<set-?>");
        this.aspects = list;
    }

    /*
     * WARNING - void declaration
     */
    @SuppressWarnings("rawtypes")
    @Override
    public void toJson(@NotNull JsonObject json) {
        JsonArray jsonArray;
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        JsonArray jsonArray2 = jsonArray = new JsonArray(this.aspects.size());
        String string = "aspects";
        JsonObject jsonObject = json;
        boolean bl = false;
        Iterable $this$forEach$iv = this.aspects;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            void it;
            String aspect = (String)element$iv;
            boolean bl2 = false;
            it.add(aspect);
        }
        Unit unit = Unit.INSTANCE;
        jsonObject.add(string, (JsonElement)jsonArray);
        json.addProperty("pokemon", this.pokemon.toString());
    }

    @SuppressWarnings({ "unused", "rawtypes" })
    @Override
    public void fromJson(@NotNull JsonObject json) {
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        this.aspects.clear();
        JsonArray jsonArray = json.getAsJsonArray("aspects");
        Intrinsics.checkNotNullExpressionValue((Object)jsonArray, (String)"json.getAsJsonArray(\"aspects\")");
        Iterable $this$forEach$iv = (Iterable)jsonArray;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            JsonElement element = (JsonElement)element$iv;
            boolean bl = false;
            String string = element.getAsString();
            Intrinsics.checkNotNullExpressionValue((Object)string, (String)"element.asString");
            this.aspects.add(string);
        }
        String string = json.get("pokemon").getAsString();
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"json.get(\"pokemon\").asString");
        this.pokemon = ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default(string, null, 1, null);
    }

    @SuppressWarnings({ "rawtypes", "unchecked", "unused" })
    @Override
    public boolean matches(@NotNull ServerPlayer player, @NotNull Map<ResourceLocation, Set<String>> context) {
        boolean bl;
        block3: {
            Intrinsics.checkNotNullParameter((Object)player, (String)"player");
            Intrinsics.checkNotNullParameter(context, (String)"context");
            Set<String> caughtAspects = context.getOrDefault(this.pokemon, new LinkedHashSet());
            Iterable $this$all$iv = this.aspects;
            boolean $i$f$all = false;
            if ($this$all$iv instanceof Collection && ((Collection)$this$all$iv).isEmpty()) {
                bl = true;
            } else {
                for (Object element$iv : $this$all$iv) {
                    String it = (String)element$iv;
                    boolean bl2 = false;
                    if (caughtAspects.contains(it)) continue;
                    bl = false;
                    break block3;
                }
                bl = true;
            }
        }
        return bl;
    }
}

