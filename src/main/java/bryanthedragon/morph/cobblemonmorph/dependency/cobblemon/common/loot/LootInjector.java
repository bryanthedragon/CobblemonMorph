/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.collections.SetsKt
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.Reflection
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.level.storage.loot.BuiltInLootTables
 *  net.minecraft.world.level.storage.loot.LootPool
 *  net.minecraft.world.level.storage.loot.LootPool$Builder
 *  net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer$Builder
 *  net.minecraft.world.level.storage.loot.entries.LootTableReference
 *  net.minecraft.world.level.storage.loot.providers.number.NumberProvider
 *  net.minecraft.world.level.storage.loot.providers.number.UniformGenerator
 *  org.jetbrains.annotations.ApiStatus$Internal
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.loot;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtils;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.HashSet;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u00c7\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u001c\u0010\u001dJ)\u0010\t\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00020\u00022\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004\u00a2\u0006\u0004\b\t\u0010\nJ\u0017\u0010\f\u001a\u00020\u00022\u0006\u0010\u000b\u001a\u00020\u0002H\u0002\u00a2\u0006\u0004\b\f\u0010\rJ\u0017\u0010\u000f\u001a\u00020\u00052\u0006\u0010\u000e\u001a\u00020\u0002H\u0002\u00a2\u0006\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0012\u001a\u00020\u00118\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0012\u0010\u0013R\u0014\u0010\u0014\u001a\u00020\u00028\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0014\u0010\u0015R4\u0010\u0019\u001a\"\u0012\f\u0012\n \u0017*\u0004\u0018\u00010\u00020\u00020\u0016j\u0010\u0012\f\u0012\n \u0017*\u0004\u0018\u00010\u00020\u0002`\u00188\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0019\u0010\u001aR4\u0010\u001b\u001a\"\u0012\f\u0012\n \u0017*\u0004\u0018\u00010\u00020\u00020\u0016j\u0010\u0012\f\u0012\n \u0017*\u0004\u0018\u00010\u00020\u0002`\u00188\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u001b\u0010\u001a\u00a8\u0006\u001e"}, d2={"Lcom/cobblemon/mod/common/loot/LootInjector;", "", "Lnet/minecraft/resources/ResourceLocation;", "id", "Lkotlin/Function1;", "Lnet/minecraft/loot/LootPool$Builder;", "", "provider", "", "attemptInjection", "(Lnet/minecraft/resources/ResourceLocation;Lkotlin/jvm/functions/Function1;)Z", "source", "convertToPotentialInjected", "(Lnet/minecraft/resources/ResourceLocation;)Lnet/minecraft/resources/ResourceLocation;", "resulting", "injectLootPool", "(Lnet/minecraft/resources/ResourceLocation;)Lnet/minecraft/world/level/storage/loot/LootPool$Builder;", "", "PREFIX", "Ljava/lang/String;", "VILLAGE_HOUSE", "Lnet/minecraft/resources/ResourceLocation;", "Ljava/util/HashSet;", "kotlin.jvm.PlatformType", "Lkotlin/collections/HashSet;", "injections", "Ljava/util/HashSet;", "villageHouseLootTables", "<init>", "()V", "common"})
@ApiStatus.Internal
@SourceDebugExtension(value={"SMAP\nLootInjector.kt\nKotlin\n*S Kotlin\n*F\n+ 1 LootInjector.kt\ncom/cobblemon/mod/common/loot/LootInjector\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,103:1\n1#2:104\n*E\n"})
public final class LootInjector {
    @NotNull
    public static final LootInjector INSTANCE = new LootInjector();
    @NotNull
    private static final String PREFIX = "injection/";
    @NotNull
    private static final ResourceLocation VILLAGE_HOUSE = MiscUtils.cobblemonResource("injection/chests/village_house");
    @NotNull
    private static final HashSet<ResourceLocation> villageHouseLootTables;
    @NotNull
    private static final HashSet<ResourceLocation> injections;

    private LootInjector() {
    }

    public final boolean attemptInjection(@NotNull ResourceLocation id, @NotNull Function1<? super LootPool.Builder, Unit> provider) {
        Intrinsics.checkNotNullParameter((Object)id, (String)"id");
        Intrinsics.checkNotNullParameter(provider, (String)"provider");
        if (!injections.contains(id)) {
            return false;
        }
        ResourceLocation resulting = this.convertToPotentialInjected(id);
        Cobblemon.INSTANCE.getLOGGER().debug("{}: Injected {} to {}", (Object)Reflection.getOrCreateKotlinClass(this.getClass()).getSimpleName(), (Object)resulting, (Object)id);
        provider.invoke((Object)this.injectLootPool(resulting));
        return true;
    }

    private final ResourceLocation convertToPotentialInjected(ResourceLocation source) {
        if (villageHouseLootTables.contains(source)) {
            return VILLAGE_HOUSE;
        }
        return MiscUtils.cobblemonResource(PREFIX + source.m_135815_());
    }

    private final LootPool.Builder injectLootPool(ResourceLocation resulting) {
        LootPool.Builder builder = LootPool.m_79043_().m_79076_((LootPoolEntryContainer.Builder)LootTableReference.m_79776_((ResourceLocation)resulting).m_79707_(1)).m_165135_((NumberProvider)UniformGenerator.m_165780_((float)0.0f, (float)1.0f));
        Intrinsics.checkNotNullExpressionValue((Object)builder, (String)"builder()\n            .w\u2026rProvider.create(0F, 1F))");
        return builder;
    }

    static {
        Object object = new ResourceLocation[]{BuiltInLootTables.f_78754_, BuiltInLootTables.f_78755_, BuiltInLootTables.f_78758_, BuiltInLootTables.f_78757_, BuiltInLootTables.f_78756_};
        villageHouseLootTables = SetsKt.hashSetOf((Object[])object);
        object = new ResourceLocation[]{BuiltInLootTables.f_78759_, BuiltInLootTables.f_230876_, BuiltInLootTables.f_78699_, BuiltInLootTables.f_78700_, BuiltInLootTables.f_78698_, BuiltInLootTables.f_78697_, BuiltInLootTables.f_78741_, BuiltInLootTables.f_78688_, BuiltInLootTables.f_78686_, BuiltInLootTables.f_78760_, BuiltInLootTables.f_78696_, BuiltInLootTables.f_78694_, BuiltInLootTables.f_78742_, BuiltInLootTables.f_78740_, BuiltInLootTables.f_78763_, BuiltInLootTables.f_78689_};
        Object $this$injections_u24lambda_u240 = object = SetsKt.hashSetOf((Object[])object);
        boolean bl = false;
        ((AbstractCollection)$this$injections_u24lambda_u240).addAll((Collection)villageHouseLootTables);
        injections = object;
    }
}

