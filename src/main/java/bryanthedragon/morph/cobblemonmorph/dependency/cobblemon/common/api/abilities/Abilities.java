/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.server.packs.PackType
 *  net.minecraft.server.packs.resources.ResourceManager
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.DataRegistry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.runner.ShowdownService;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.data.AbilityRegistrySyncPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.abilities.HiddenAbilityType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;

import com.google.gson.JsonArray;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u001e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010%\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b6\u00107J\u0013\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002\u00a2\u0006\u0004\b\u0004\u0010\u0005J\r\u0010\u0007\u001a\u00020\u0006\u00a2\u0006\u0004\b\u0007\u0010\bJ\r\u0010\t\u001a\u00020\u0003\u00a2\u0006\u0004\b\t\u0010\nJ\u0017\u0010\r\u001a\u0004\u0018\u00010\u00032\u0006\u0010\f\u001a\u00020\u000b\u00a2\u0006\u0004\b\r\u0010\u000eJ\u0015\u0010\u000f\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\u000b\u00a2\u0006\u0004\b\u000f\u0010\u000eJ\u001d\u0010\u0015\u001a\u00020\u00122\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00030\u0010H\u0000\u00a2\u0006\u0004\b\u0013\u0010\u0014J\u0015\u0010\u0017\u001a\u00020\u00032\u0006\u0010\u0016\u001a\u00020\u0003\u00a2\u0006\u0004\b\u0017\u0010\u0018J\u0017\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u001a\u001a\u00020\u0019H\u0016\u00a2\u0006\u0004\b\u001b\u0010\u001cJ\u0017\u0010\u001f\u001a\u00020\u00122\u0006\u0010\u001e\u001a\u00020\u001dH\u0016\u00a2\u0006\u0004\b\u001f\u0010 R\u0017\u0010!\u001a\u00020\u00038\u0006\u00a2\u0006\f\n\u0004\b!\u0010\"\u001a\u0004\b#\u0010\nR \u0010%\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00030$8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b%\u0010&R\u001a\u0010(\u001a\u00020'8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b(\u0010)\u001a\u0004\b*\u0010+R \u0010-\u001a\b\u0012\u0004\u0012\u00020\u00000,8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b-\u0010.\u001a\u0004\b/\u00100R\u001a\u00102\u001a\u0002018\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b2\u00103\u001a\u0004\b4\u00105\u00a8\u00068"}, d2={"Lcom/cobblemon/mod/common/api/abilities/Abilities;", "Lcom/cobblemon/mod/common/api/data/DataRegistry;", "", "Lcom/cobblemon/mod/common/api/abilities/AbilityTemplate;", "all", "()Ljava/util/List;", "", "count", "()I", "first", "()Lcom/cobblemon/mod/common/api/abilities/AbilityTemplate;", "", "name", "get", "(Ljava/lang/String;)Lcom/cobblemon/mod/common/api/abilities/AbilityTemplate;", "getOrException", "", "abilities", "", "receiveSyncPacket$common", "(Ljava/util/Collection;)V", "receiveSyncPacket", "ability", "register", "(Lcom/cobblemon/mod/common/api/abilities/AbilityTemplate;)Lcom/cobblemon/mod/common/api/abilities/AbilityTemplate;", "Lnet/minecraft/server/packs/resources/ResourceManager;", "manager", "reload", "(Lnet/minecraft/server/packs/resources/ResourceManager;)V", "Lnet/minecraft/server/level/ServerPlayer;", "player", "sync", "(Lnet/minecraft/server/level/ServerPlayer;)V", "DUMMY", "Lcom/cobblemon/mod/common/api/abilities/AbilityTemplate;", "getDUMMY", "", "abilityMap", "Ljava/util/Map;", "Lnet/minecraft/resources/ResourceLocation;", "id", "Lnet/minecraft/resources/ResourceLocation;", "getId", "()Lnet/minecraft/resources/ResourceLocation;", "Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "observable", "Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "getObservable", "()Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "Lnet/minecraft/server/packs/PackType;", "type", "Lnet/minecraft/server/packs/PackType;", "getType", "()Lnet/minecraft/server/packs/PackType;", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nAbilities.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Abilities.kt\ncom/cobblemon/mod/common/api/abilities/Abilities\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,70:1\n1855#2,2:71\n*S KotlinDebug\n*F\n+ 1 Abilities.kt\ncom/cobblemon/mod/common/api/abilities/Abilities\n*L\n67#1:71,2\n*E\n"})
public final class Abilities
implements DataRegistry {
    @NotNull
    public static final Abilities INSTANCE = new Abilities();
    @NotNull
    private static final ResourceLocation id = MiscUtilsKt.cobblemonResource("abilities");
    @NotNull
    private static final PackType type = PackType.SERVER_DATA;
    @NotNull
    private static final SimpleObservable<Abilities> observable = new SimpleObservable();
    @NotNull
    private static final AbilityTemplate DUMMY = new AbilityTemplate("dummy", null, null, null, 14, null);
    @NotNull
    private static final Map<String, AbilityTemplate> abilityMap = new LinkedHashMap();

    private Abilities() {
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
    public SimpleObservable<Abilities> getObservable() {
        return observable;
    }

    @NotNull
    public final AbilityTemplate getDUMMY() {
        return DUMMY;
    }

    @Override
    public void reload(@NotNull ResourceManager manager) {
        Intrinsics.checkNotNullParameter((Object)manager, (String)"manager");
        PotentialAbility.Companion.getTypes().clear();
        PotentialAbility.Companion.getTypes().add(CommonAbilityType.INSTANCE);
        PotentialAbility.Companion.getTypes().add(HiddenAbilityType.INSTANCE);
        abilityMap.clear();
        JsonArray abilitiesJson = ShowdownService.Companion.getService().getAbilityIds();
        int n = abilitiesJson.size();
        for (int i = 0; i < n; ++i) {
            String id = abilitiesJson.get(i).getAsString();
            Intrinsics.checkNotNullExpressionValue((Object)id, (String)"id");
            AbilityTemplate ability = new AbilityTemplate(id, null, null, null, 14, null);
            this.register(ability);
        }
        Cobblemon.INSTANCE.getLOGGER().info("Loaded {} abilities", (Object)abilityMap.size());
        Abilities[] abilitiesArray = new Abilities[]{this};
        this.getObservable().emit((Abilities[])abilitiesArray);
    }

    @Override
    public void sync(@NotNull ServerPlayer player) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        new AbilityRegistrySyncPacket((Collection<AbilityTemplate>)this.all()).sendToPlayer(player);
    }

    @NotNull
    public final AbilityTemplate register(@NotNull AbilityTemplate ability) {
        Intrinsics.checkNotNullParameter((Object)ability, (String)"ability");
        String string = ability.getName().toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"this as java.lang.String).toLowerCase(Locale.ROOT)");
        abilityMap.put(string, ability);
        return ability;
    }

    @NotNull
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public final List<AbilityTemplate> all() {
        return CollectionsKt.toList((Iterable)abilityMap.values());
    }

    @NotNull
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public final AbilityTemplate first() {
        return (AbilityTemplate)CollectionsKt.first((Iterable)abilityMap.values());
    }

    @Nullable
    public final AbilityTemplate get(@NotNull String name) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        String string = name.toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"this as java.lang.String).toLowerCase(Locale.ROOT)");
        return abilityMap.get(string);
    }

    @NotNull
    public final AbilityTemplate getOrException(@NotNull String name) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        AbilityTemplate abilityTemplate = this.get(name);
        if (abilityTemplate == null) {
            throw new IllegalArgumentException("Unable to find ability of name: " + name);
        }
        return abilityTemplate;
    }

    public final int count() {
        return abilityMap.size();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public final void receiveSyncPacket$common(@NotNull Collection<AbilityTemplate> abilities) {
        Intrinsics.checkNotNullParameter(abilities, (String)"abilities");
        abilityMap.clear();
        Iterable $this$forEach$iv = abilities;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            AbilityTemplate ability = (AbilityTemplate)element$iv;
            boolean bl = false;
            this.register(ability);
        }
    }
}

