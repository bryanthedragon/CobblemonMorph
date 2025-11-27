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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtils;

import com.google.gson.JsonArray;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public final class Abilities implements DataRegistry {
    @NotNull
    public static final Abilities INSTANCE = new Abilities();
    @NotNull
    private static final ResourceLocation id = MiscUtils.cobblemonResource("abilities");
    @NotNull
    private static final PackType type = PackType.SERVER_DATA;

    @NotNull
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static final SimpleObservable<Abilities> observable = new SimpleObservable();

    @NotNull
    private static final AbilityTemplate DUMMY = new AbilityTemplate("dummy", null, null, null, 14, null);

    @NotNull
    @SuppressWarnings({ "rawtypes", "unchecked" })
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

    @SuppressWarnings({"rawtypes", "unused" })
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

