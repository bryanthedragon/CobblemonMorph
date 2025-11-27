/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.Reflection
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.server.packs.PackType
 *  net.minecraft.server.packs.resources.PreparableReloadListener
 *  net.minecraft.server.packs.resources.ResourceManager
 *  net.minecraft.server.packs.resources.ResourceManagerReloadListener
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.data;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonMechanics;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.Abilities;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.Berries;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.DataProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.DataRegistry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.Dialogues;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.CobblemonEvents;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.fossil.Fossils;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.fossil.NaturalMaterials;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.Moves;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.ActionEffects;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.PokeBalls;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonSpecies;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.GlobalSpeciesFeatures;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.SpeciesFeatureAssignments;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.SpeciesFeatures;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.Observable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.CobblemonSpawnPools;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.CobblemonSpawnRules;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnDetailPresets;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BagItems;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.data.CobblemonDataProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.PlatformEvents;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.SpeciesAdditions;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.properties.PropertiesCompletionProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.DistributionUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0010!\n\u0002\b\u0007\b\u00c6\u0002\u0018\u00002\u00020\u0001:\u0001)B\t\b\u0002\u00a2\u0006\u0004\b(\u0010\u0013J%\u0010\u0007\u001a\u00020\u00052\u0006\u0010\u0003\u001a\u00020\u00022\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H\u0016\u00a2\u0006\u0004\b\u0007\u0010\bJ\u0019\u0010\f\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\n\u001a\u00020\tH\u0016\u00a2\u0006\u0004\b\f\u0010\rJ!\u0010\u0010\u001a\u00028\u0000\"\b\b\u0000\u0010\u000e*\u00020\u000b2\u0006\u0010\u000f\u001a\u00028\u0000H\u0016\u00a2\u0006\u0004\b\u0010\u0010\u0011J\r\u0010\u0012\u001a\u00020\u0005\u00a2\u0006\u0004\b\u0012\u0010\u0013J\u0017\u0010\u0014\u001a\u00020\u00052\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0014\u0010\u0015R\"\u0010\u0017\u001a\u00020\u00168\u0000@\u0000X\u0080\u000e\u00a2\u0006\u0012\n\u0004\b\u0017\u0010\u0018\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001cR$\u0010\u001f\u001a\u0012\u0012\u0004\u0012\u00020\u000b0\u001dj\b\u0012\u0004\u0012\u00020\u000b`\u001e8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u001f\u0010 R,\u0010$\u001a\u001a\u0012\u0004\u0012\u00020\"\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040#0!8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b$\u0010%R\u001a\u0010&\u001a\b\u0012\u0004\u0012\u00020\"0#8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b&\u0010'\u00a8\u0006*"}, d2={"Lcom/cobblemon/mod/common/data/CobblemonDataProvider;", "Lcom/cobblemon/mod/common/api/data/DataProvider;", "Lnet/minecraft/server/level/ServerPlayer;", "player", "Lkotlin/Function0;", "", "action", "doAfterSync", "(Lnet/minecraft/server/level/ServerPlayer;Lkotlin/jvm/functions/Function0;)V", "Lnet/minecraft/resources/ResourceLocation;", "registryIdentifier", "Lcom/cobblemon/mod/common/api/data/DataRegistry;", "fromIdentifier", "(Lnet/minecraft/resources/ResourceLocation;)Lcom/cobblemon/mod/common/api/data/DataRegistry;", "T", "registry", "register", "(Lcom/cobblemon/mod/common/api/data/DataRegistry;)Lcom/cobblemon/mod/common/api/data/DataRegistry;", "registerDefaults", "()V", "sync", "(Lnet/minecraft/server/level/ServerPlayer;)V", "", "canReload", "Z", "getCanReload$common", "()Z", "setCanReload$common", "(Z)V", "Ljava/util/LinkedHashSet;", "Lkotlin/collections/LinkedHashSet;", "registries", "Ljava/util/LinkedHashSet;", "", "Ljava/util/UUID;", "", "scheduledActions", "Ljava/util/Map;", "synchronizedPlayerIds", "Ljava/util/List;", "<init>", "SimpleResourceReloader", "common"})
@SourceDebugExtension(value={"SMAP\nCobblemonDataProvider.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CobblemonDataProvider.kt\ncom/cobblemon/mod/common/data/CobblemonDataProvider\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,136:1\n1#2:137\n1855#3,2:138\n1855#3,2:140\n*S KotlinDebug\n*F\n+ 1 CobblemonDataProvider.kt\ncom/cobblemon/mod/common/data/CobblemonDataProvider\n*L\n105#1:138,2\n110#1:140,2\n*E\n"})
public final class CobblemonDataProvider
implements DataProvider {
    @NotNull
    public static final CobblemonDataProvider INSTANCE = new CobblemonDataProvider();
    private static boolean canReload = true;
    @NotNull
    private static final LinkedHashSet<DataRegistry> registries = new LinkedHashSet();
    @NotNull
    private static final List<UUID> synchronizedPlayerIds = new ArrayList();
    @NotNull
    private static final Map<UUID, List<Function0<Unit>>> scheduledActions = new LinkedHashMap();

    private CobblemonDataProvider() {
    }

    public final boolean getCanReload$common() {
        return canReload;
    }

    public final void setCanReload$common(boolean bl) {
        canReload = bl;
    }

    public final void registerDefaults() {
        this.register((DataRegistry)SpeciesFeatures.INSTANCE);
        this.register((DataRegistry)GlobalSpeciesFeatures.INSTANCE);
        this.register((DataRegistry)SpeciesFeatureAssignments.INSTANCE);
        this.register((DataRegistry)ActionEffects.INSTANCE);
        this.register((DataRegistry)Moves.INSTANCE);
        this.register((DataRegistry)Abilities.INSTANCE);
        this.register((DataRegistry)PokemonSpecies.INSTANCE);
        this.register((DataRegistry)SpeciesAdditions.INSTANCE);
        this.register((DataRegistry)PokeBalls.INSTANCE);
        this.register((DataRegistry)PropertiesCompletionProvider.INSTANCE);
        this.register((DataRegistry)SpawnDetailPresets.INSTANCE);
        this.register((DataRegistry)CobblemonSpawnRules.INSTANCE);
        this.register((DataRegistry)CobblemonMechanics.INSTANCE);
        this.register((DataRegistry)BagItems.INSTANCE);
        this.register((DataRegistry)Dialogues.INSTANCE);
        this.register((DataRegistry)NaturalMaterials.INSTANCE);
        this.register((DataRegistry)Fossils.INSTANCE);
        CobblemonSpawnPools.INSTANCE.load();
        this.register((DataRegistry)Berries.INSTANCE);
        Observable.DefaultImpls.subscribe$default(PlatformEvents.SERVER_PLAYER_LOGOUT, null, registerDefaults.1.INSTANCE, 1, null);
        DistributionUtilsKt.ifClient(CobblemonDataProvider::registerDefaults$lambda$0);
        Cobblemon.INSTANCE.getImplementation().registerResourceReloader(MiscUtils.cobblemonResource("data_resources"), (PreparableReloadListener)new SimpleResourceReloader(PackType.SERVER_DATA), PackType.SERVER_DATA, CollectionsKt.emptyList());
    }

    @Override
    @NotNull
    public <T extends DataRegistry> T register(@NotNull T registry) {
        Intrinsics.checkNotNullParameter(registry, (String)"registry");
        if (registries.isEmpty()) {
            Cobblemon.INSTANCE.getLOGGER().info("Note: Cobblemon data registries are only loaded once per server instance as Pok\u00e9mon species are not safe to reload.");
        }
        registries.add(registry);
        Cobblemon.INSTANCE.getLOGGER().info("Registered the {} registry", (Object)registry.getId().toString());
        Cobblemon.INSTANCE.getLOGGER().debug("Registered the {} registry of class {}", (Object)registry.getId().toString(), (Object)Reflection.getOrCreateKotlinClass(registry.getClass()).getQualifiedName());
        return registry;
    }

    @Override
    @Nullable
    public DataRegistry fromIdentifier(@NotNull ResourceLocation registryIdentifier) {
        Object v0;
        block1: {
            Intrinsics.checkNotNullParameter((Object)registryIdentifier, (String)"registryIdentifier");
            Iterable iterable = registries;
            for (Object t : iterable) {
                DataRegistry it = (DataRegistry)t;
                boolean bl = false;
                if (!Intrinsics.areEqual((Object)it.getId(), (Object)registryIdentifier)) continue;
                v0 = t;
                break block1;
            }
            v0 = null;
        }
        return v0;
    }

    @Override
    public void sync(@NotNull ServerPlayer player) {
        ServerPlayer[] $this$forEach$iv;
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        if (!player.f_8906_.f_9742_.m_129531_()) {
            $this$forEach$iv = (ServerPlayer[])registries;
            boolean $i$f$forEach = false;
            for (Object t : $this$forEach$iv) {
                DataRegistry registry = (DataRegistry)t;
                boolean bl = false;
                registry.sync(player);
            }
        }
        $this$forEach$iv = new ServerPlayer[]{player};
        CobblemonEvents.DATA_SYNCHRONIZED.emit((ServerPlayer[])$this$forEach$iv);
        List<Function0<Unit>> list = scheduledActions.remove(player.m_20148_());
        if (list == null) {
            return;
        }
        List<Function0<Unit>> waitingActions = list;
        Iterable $this$forEach$iv2 = waitingActions;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv2) {
            Function0 it = (Function0)element$iv;
            boolean bl = false;
            it.invoke();
        }
    }

    @Override
    public void doAfterSync(@NotNull ServerPlayer player, @NotNull Function0<Unit> action2) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter(action2, (String)"action");
        if (synchronizedPlayerIds.contains(player.m_20148_())) {
            action2.invoke();
        } else {
            scheduledActions.computeIfAbsent(player.m_20148_(), arg_0 -> CobblemonDataProvider.doAfterSync$lambda$4(doAfterSync.1.INSTANCE, arg_0)).add(action2);
        }
    }

    private static final void registerDefaults$lambda$0() {
        Cobblemon.INSTANCE.getImplementation().registerResourceReloader(MiscUtils.cobblemonResource("client_resources"), (PreparableReloadListener)new SimpleResourceReloader(PackType.CLIENT_RESOURCES), PackType.CLIENT_RESOURCES, CollectionsKt.emptyList());
    }

    private static final List doAfterSync$lambda$4(Function1 $tmp0, Object p0) {
        Intrinsics.checkNotNullParameter((Object)$tmp0, (String)"$tmp0");
        return (List)$tmp0.invoke(p0);
    }

    public static final /* synthetic */ List access$getSynchronizedPlayerIds$p() {
        return synchronizedPlayerIds;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0002\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\b\u001a\u00020\u0007\u00a2\u0006\u0004\b\n\u0010\u000bJ\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0014\u0010\b\u001a\u00020\u00078\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\b\u0010\t\u00a8\u0006\f"}, d2={"Lcom/cobblemon/mod/common/data/CobblemonDataProvider$SimpleResourceReloader;", "Lnet/minecraft/server/packs/resources/ResourceManagerReloadListener;", "Lnet/minecraft/server/packs/resources/ResourceManager;", "manager", "", "reload", "(Lnet/minecraft/server/packs/resources/ResourceManager;)V", "Lnet/minecraft/server/packs/PackType;", "type", "Lnet/minecraft/server/packs/PackType;", "<init>", "(Lnet/minecraft/server/packs/PackType;)V", "common"})
    @SourceDebugExtension(value={"SMAP\nCobblemonDataProvider.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CobblemonDataProvider.kt\ncom/cobblemon/mod/common/data/CobblemonDataProvider$SimpleResourceReloader\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,136:1\n766#2:137\n857#2,2:138\n1855#2,2:140\n*S KotlinDebug\n*F\n+ 1 CobblemonDataProvider.kt\ncom/cobblemon/mod/common/data/CobblemonDataProvider$SimpleResourceReloader\n*L\n128#1:137\n128#1:138,2\n129#1:140,2\n*E\n"})
    private static final class SimpleResourceReloader
    implements ResourceManagerReloadListener {
        @NotNull
        private final PackType type;

        public SimpleResourceReloader(@NotNull PackType type) {
            Intrinsics.checkNotNullParameter((Object)type, (String)"type");
            this.type = type;
        }

        /*
         * WARNING - void declaration
         */
        public void m_6213_(@NotNull ResourceManager manager) {
            void $this$filterTo$iv$iv;
            boolean isInGame;
            Intrinsics.checkNotNullParameter((Object)manager, (String)"manager");
            boolean bl = isInGame = DistributionUtilsKt.server() != null;
            if (isInGame && this.type == PackType.SERVER_DATA && !INSTANCE.getCanReload$common()) {
                return;
            }
            Iterable $this$filter$iv = registries;
            boolean $i$f$filter = false;
            Iterable iterable = $this$filter$iv;
            Collection destination$iv$iv = new ArrayList();
            boolean $i$f$filterTo = false;
            for (Object element$iv$iv : $this$filterTo$iv$iv) {
                DataRegistry it = (DataRegistry)element$iv$iv;
                boolean bl2 = false;
                if (!(it.getType() == this.type)) continue;
                destination$iv$iv.add(element$iv$iv);
            }
            Iterable $this$forEach$iv = (List)destination$iv$iv;
            boolean $i$f$forEach = false;
            for (Object element$iv : $this$forEach$iv) {
                DataRegistry it = (DataRegistry)element$iv;
                boolean bl3 = false;
                it.reload(manager);
            }
            if (isInGame && this.type == PackType.SERVER_DATA) {
                INSTANCE.setCanReload$common(false);
            }
        }
    }
}

