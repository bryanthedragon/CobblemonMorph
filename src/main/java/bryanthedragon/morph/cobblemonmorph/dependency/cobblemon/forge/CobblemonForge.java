/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.arguments.ArgumentType
 *  kotlin.Metadata
 *  kotlin.Triple
 *  kotlin.Unit
 *  kotlin.io.CloseableKt
 *  kotlin.io.FilesKt
 *  kotlin.jvm.JvmClassMappingKt
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.functions.Function2
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.reflect.KClass
 *  kotlin.text.Charsets
 *  net.minecraft.advancements.CriteriaTriggers
 *  net.minecraft.advancements.CriterionTrigger
 *  net.minecraft.commands.CommandBuildContext
 *  net.minecraft.commands.CommandSourceStack
 *  net.minecraft.commands.Commands$CommandSelection
 *  net.minecraft.commands.synchronization.ArgumentTypeInfo
 *  net.minecraft.commands.synchronization.ArgumentTypeInfo$Template
 *  net.minecraft.commands.synchronization.ArgumentTypeInfos
 *  net.minecraft.core.particles.ParticleType
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.server.packs.PackResources
 *  net.minecraft.server.packs.PackType
 *  net.minecraft.server.packs.repository.Pack
 *  net.minecraft.server.packs.repository.Pack$Position
 *  net.minecraft.server.packs.repository.Pack$ResourcesSupplier
 *  net.minecraft.server.packs.repository.PackSource
 *  net.minecraft.server.packs.resources.PreparableReloadListener
 *  net.minecraft.server.packs.resources.Resource
 *  net.minecraft.server.packs.resources.ResourceManager
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.tags.TagKey
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.ai.attributes.Attribute
 *  net.minecraft.world.entity.ai.attributes.AttributeSupplier$Builder
 *  net.minecraft.world.entity.npc.VillagerProfession
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.entity.schedule.Activity
 *  net.minecraft.world.item.CreativeModeTab
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.GameRules
 *  net.minecraft.world.level.GameRules$Category
 *  net.minecraft.world.level.GameRules$Key
 *  net.minecraft.world.level.GameRules$Type
 *  net.minecraft.world.level.GameRules$Value
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.biome.Biome
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.ComposterBlock
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.levelgen.GenerationStep$Decoration
 *  net.minecraft.world.level.levelgen.feature.Feature
 *  net.minecraft.world.level.levelgen.placement.PlacedFeature
 *  net.minecraft.world.level.storage.loot.LootPool$Builder
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.common.ForgeMod
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.common.ToolActions
 *  net.minecraftforge.event.AddPackFindersEvent
 *  net.minecraftforge.event.AddReloadListenerEvent
 *  net.minecraftforge.event.LootTableLoadEvent
 *  net.minecraftforge.event.OnDatapackSyncEvent
 *  net.minecraftforge.event.RegisterCommandsEvent
 *  net.minecraftforge.event.entity.EntityAttributeCreationEvent
 *  net.minecraftforge.event.entity.player.PlayerEvent$PlayerLoggedInEvent
 *  net.minecraftforge.event.entity.player.PlayerEvent$PlayerLoggedOutEvent
 *  net.minecraftforge.event.entity.player.PlayerWakeUpEvent
 *  net.minecraftforge.event.level.BlockEvent$BlockToolModificationEvent
 *  net.minecraftforge.event.server.ServerAboutToStartEvent
 *  net.minecraftforge.event.village.VillagerTradesEvent
 *  net.minecraftforge.event.village.WandererTradesEvent
 *  net.minecraftforge.eventbus.api.IEventBus
 *  net.minecraftforge.fml.DistExecutor
 *  net.minecraftforge.fml.DistExecutor$SafeRunnable
 *  net.minecraftforge.fml.InterModComms
 *  net.minecraftforge.fml.ModList
 *  net.minecraftforge.fml.common.Mod
 *  net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
 *  net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent
 *  net.minecraftforge.fml.loading.FMLEnvironment
 *  net.minecraftforge.forgespi.locating.IModFile
 *  net.minecraftforge.registries.DeferredRegister
 *  net.minecraftforge.registries.ForgeRegistries
 *  net.minecraftforge.registries.RegisterEvent
 *  net.minecraftforge.registries.RegisterEvent$RegisterHelper
 *  net.minecraftforge.resource.PathPackResources
 *  net.minecraftforge.server.ServerLifecycleHooks
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 *  thedarkcolour.kotlinforforge.KotlinModLoadingContext
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.forge;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonActivities;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonBlockEntities;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonBlocks;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonCommands;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonEntities;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonImplementation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonItems;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonSounds;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonTradeOffers;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Environment;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.ModAPI;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.NetworkManager;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.ResourcePackActivationBehaviour;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.JsonDataRegistry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.group.CobblemonItemGroups;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.loot.LootInjector;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.sherds.CobblemonSherds;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.IdentifierExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtils;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.CobblemonStructures;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.placementmodifier.CobblemonPlacementModifierTypes;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.predicate.CobblemonBlockPredicates;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.structureprocessors.CobblemonProcessorTypes;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.structureprocessors.CobblemonStructureProcessorListOverrides;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

import kotlin.Metadata;
import kotlin.Triple;
import kotlin.Unit;
import kotlin.io.CloseableKt;
import kotlin.io.FilesKt;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.reflect.KClass;
import kotlin.text.Charsets;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.commands.synchronization.ArgumentTypeInfos;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.storage.loot.LootPool;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.OnDatapackSyncEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.forgespi.locating.IModFile;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import net.minecraftforge.resource.PathPackResources;
import net.minecraftforge.server.ServerLifecycleHooks;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Mod(value="cobblemon")
@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0084\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001B\b\u00a2\u0006\u0005\b\u00a0\u0001\u0010\u0012J\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006J5\u0010\u000f\u001a\u00020\u00042\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\u0006\u0010\u000b\u001a\u00020\n2\u000e\u0010\u000e\u001a\n\u0012\u0004\u0012\u00020\r\u0018\u00010\fH\u0016\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u000f\u0010\u0011\u001a\u00020\u0004H\u0002\u00a2\u0006\u0004\b\u0011\u0010\u0012J\u000f\u0010\u0014\u001a\u00020\u0013H\u0016\u00a2\u0006\u0004\b\u0014\u0010\u0015J\u0017\u0010\u0018\u001a\u00020\u00042\u0006\u0010\u0017\u001a\u00020\u0016H\u0002\u00a2\u0006\u0004\b\u0018\u0010\u0019J\u0015\u0010\u001b\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u001a\u00a2\u0006\u0004\b\u001b\u0010\u001cJ\u0017\u0010 \u001a\u00020\u001f2\u0006\u0010\u001e\u001a\u00020\u001dH\u0016\u00a2\u0006\u0004\b \u0010!J\u0015\u0010#\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\"\u00a2\u0006\u0004\b#\u0010$J\u0015\u0010&\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020%\u00a2\u0006\u0004\b&\u0010'J\u0015\u0010)\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020(\u00a2\u0006\u0004\b)\u0010*J\u0015\u0010,\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020+\u00a2\u0006\u0004\b,\u0010-J\u0015\u0010/\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020.\u00a2\u0006\u0004\b/\u00100J\u0017\u00102\u001a\u00020\u00042\u0006\u0010\u0017\u001a\u000201H\u0002\u00a2\u0006\u0004\b2\u00103J\u0017\u00105\u001a\u00020\u00042\u0006\u0010\u0017\u001a\u000204H\u0002\u00a2\u0006\u0004\b5\u00106J\u0017\u00108\u001a\u00020\u00042\u0006\u0010\u0017\u001a\u000207H\u0002\u00a2\u0006\u0004\b8\u00109J\u0017\u0010;\u001a\u00020\u00042\u0006\u0010\u0017\u001a\u00020:H\u0002\u00a2\u0006\u0004\b;\u0010<J\u000f\u0010=\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b=\u0010\u0012J\u000f\u0010>\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b>\u0010\u0012J'\u0010D\u001a\u00020\u00042\u0006\u0010\u001e\u001a\u00020?2\u0006\u0010A\u001a\u00020@2\u0006\u0010C\u001a\u00020BH\u0016\u00a2\u0006\u0004\bD\u0010EJW\u0010O\u001a\u00020\u0004\"\f\b\u0000\u0010G*\u0006\u0012\u0002\b\u00030F\"\u000e\b\u0001\u0010I*\b\u0012\u0004\u0012\u00028\u00000H2\u0006\u0010J\u001a\u00020?2\f\u0010L\u001a\b\u0012\u0004\u0012\u00028\u00000K2\u0012\u0010N\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010MH\u0016\u00a2\u0006\u0004\bO\u0010PJ\u0017\u0010R\u001a\u00020\u00042\u0006\u0010\u0017\u001a\u00020QH\u0002\u00a2\u0006\u0004\bR\u0010SJ\u001f\u0010X\u001a\u00020\u00042\u0006\u0010U\u001a\u00020T2\u0006\u0010W\u001a\u00020VH\u0016\u00a2\u0006\u0004\bX\u0010YJ%\u0010\\\u001a\u00028\u0000\"\f\b\u0000\u0010I*\u0006\u0012\u0002\b\u00030Z2\u0006\u0010[\u001a\u00028\u0000H\u0016\u00a2\u0006\u0004\b\\\u0010]J\u000f\u0010^\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b^\u0010\u0012J\u000f\u0010_\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b_\u0010\u0012JC\u0010g\u001a\b\u0012\u0004\u0012\u00028\u00000f\"\u000e\b\u0000\u0010I*\b\u0012\u0004\u0012\u00028\u00000`2\u0006\u0010a\u001a\u00020\u001d2\u0006\u0010c\u001a\u00020b2\f\u0010e\u001a\b\u0012\u0004\u0012\u00028\u00000dH\u0016\u00a2\u0006\u0004\bg\u0010hJ\u000f\u0010i\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\bi\u0010\u0012J\u000f\u0010j\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\bj\u0010\u0012J\u000f\u0010k\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\bk\u0010\u0012J5\u0010q\u001a\u00020\u00042\u0006\u0010J\u001a\u00020?2\u0006\u0010m\u001a\u00020l2\u0006\u0010e\u001a\u00020n2\f\u0010p\u001a\b\u0012\u0004\u0012\u00020?0oH\u0016\u00a2\u0006\u0004\bq\u0010rJ\u000f\u0010s\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\bs\u0010\u0012J\u000f\u0010t\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\bt\u0010\u0012J7\u0010z\u001a\u000e\u0012\u0004\u0012\u00020?\u0012\u0004\u0012\u00028\u00000y\"\u0004\b\u0000\u0010I2\f\u0010v\u001a\b\u0012\u0004\u0012\u00028\u00000u2\u0006\u0010x\u001a\u00020wH\u0016\u00a2\u0006\u0004\bz\u0010{J\u0011\u0010}\u001a\u0004\u0018\u00010|H\u0016\u00a2\u0006\u0004\b}\u0010~J\u0018\u0010\u0080\u0001\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u007f\u00a2\u0006\u0006\b\u0080\u0001\u0010\u0081\u0001J\u0019\u0010\u0083\u0001\u001a\u00020\u00042\u0007\u0010\u0003\u001a\u00030\u0082\u0001\u00a2\u0006\u0006\b\u0083\u0001\u0010\u0084\u0001R`\u0010\u0087\u0001\u001aK\u0012\u001d\u0012\u001b\u0012\u0002\b\u0003\u0012\u0002\b\u0003 \u0086\u0001*\f\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0018\u00010M0M \u0086\u0001*$\u0012\u001d\u0012\u001b\u0012\u0002\b\u0003\u0012\u0002\b\u0003 \u0086\u0001*\f\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0018\u00010M0M\u0018\u00010\u0085\u00010\u0085\u00018\u0002X\u0082\u0004\u00a2\u0006\b\n\u0006\b\u0087\u0001\u0010\u0088\u0001R+\u0010\u008c\u0001\u001a\u0016\u0012\u0005\u0012\u00030\u008a\u00010\u0089\u0001j\n\u0012\u0005\u0012\u00030\u008a\u0001`\u008b\u00018\u0002X\u0082\u0004\u00a2\u0006\b\n\u0006\b\u008c\u0001\u0010\u008d\u0001R \u0010\u008f\u0001\u001a\u00030\u008e\u00018\u0016X\u0096\u0004\u00a2\u0006\u0010\n\u0006\b\u008f\u0001\u0010\u0090\u0001\u001a\u0006\b\u0091\u0001\u0010\u0092\u0001R \u0010\u0094\u0001\u001a\u00030\u0093\u00018\u0016X\u0096\u0004\u00a2\u0006\u0010\n\u0006\b\u0094\u0001\u0010\u0095\u0001\u001a\u0006\b\u0096\u0001\u0010\u0097\u0001RO\u0010\u009b\u0001\u001a:\u0012\u0017\u0012\u0015\u0012\u0004\u0012\u00020?\u0012\u0004\u0012\u00020@\u0012\u0004\u0012\u00020B0\u0099\u00010\u0098\u0001j\u001c\u0012\u0017\u0012\u0015\u0012\u0004\u0012\u00020?\u0012\u0004\u0012\u00020@\u0012\u0004\u0012\u00020B0\u0099\u0001`\u009a\u00018\u0002X\u0082\u0004\u00a2\u0006\b\n\u0006\b\u009b\u0001\u0010\u009c\u0001R7\u0010\u009e\u0001\u001a\"\u0012\u000b\u0012\t\u0012\u0004\u0012\u00020\u00040\u009d\u00010\u0098\u0001j\u0010\u0012\u000b\u0012\t\u0012\u0004\u0012\u00020\u00040\u009d\u0001`\u009a\u00018\u0002X\u0082\u0004\u00a2\u0006\b\n\u0006\b\u009e\u0001\u0010\u009c\u0001R)\u0010\u009f\u0001\u001a\u0014\u0012\u0004\u0012\u00020l0\u0098\u0001j\t\u0012\u0004\u0012\u00020l`\u009a\u00018\u0002X\u0082\u0004\u00a2\u0006\b\n\u0006\b\u009f\u0001\u0010\u009c\u0001\u00a8\u0006\u00a1\u0001"}, d2={"Lcom/cobblemon/mod/forge/CobblemonForge;", "Lcom/cobblemon/mod/common/CobblemonImplementation;", "Lnet/minecraftforge/event/server/ServerAboutToStartEvent;", "event", "", "addCobblemonStructures", "(Lnet/minecraftforge/event/server/ServerAboutToStartEvent;)V", "Lnet/minecraft/resources/ResourceKey;", "Lnet/minecraft/world/level/levelgen/placement/PlacedFeature;", "feature", "Lnet/minecraft/world/gen/GenerationStep$Feature;", "step", "Lnet/minecraft/tags/TagKey;", "Lnet/minecraft/world/level/biome/Biome;", "validTag", "addFeatureToWorldGen", "(Lnet/minecraft/resources/ResourceKey;Lnet/minecraft/world/level/levelgen/GenerationStep$Decoration;Lnet/minecraft/tags/TagKey;)V", "attemptModCompat", "()V", "Lcom/cobblemon/mod/common/Environment;", "environment", "()Lcom/cobblemon/mod/common/Environment;", "Lnet/minecraftforge/event/level/BlockEvent$BlockToolModificationEvent;", "e", "handleBlockStripping", "(Lnet/minecraftforge/event/level/BlockEvent$BlockToolModificationEvent;)V", "Lnet/minecraftforge/fml/event/lifecycle/FMLCommonSetupEvent;", "initialize", "(Lnet/minecraftforge/fml/event/lifecycle/FMLCommonSetupEvent;)V", "", "id", "", "isModInstalled", "(Ljava/lang/String;)Z", "Lnet/minecraftforge/registries/RegisterEvent;", "on", "(Lnet/minecraftforge/registries/RegisterEvent;)V", "Lnet/minecraftforge/event/AddPackFindersEvent;", "onAddPackFindersEvent", "(Lnet/minecraftforge/event/AddPackFindersEvent;)V", "Lnet/minecraftforge/event/OnDatapackSyncEvent;", "onDataPackSync", "(Lnet/minecraftforge/event/OnDatapackSyncEvent;)V", "Lnet/minecraftforge/event/entity/player/PlayerEvent$PlayerLoggedInEvent;", "onLogin", "(Lnet/minecraftforge/event/entity/player/PlayerEvent$PlayerLoggedInEvent;)V", "Lnet/minecraftforge/event/entity/player/PlayerEvent$PlayerLoggedOutEvent;", "onLogout", "(Lnet/minecraftforge/event/entity/player/PlayerEvent$PlayerLoggedOutEvent;)V", "Lnet/minecraftforge/event/LootTableLoadEvent;", "onLootTableLoad", "(Lnet/minecraftforge/event/LootTableLoadEvent;)V", "Lnet/minecraftforge/event/AddReloadListenerEvent;", "onReload", "(Lnet/minecraftforge/event/AddReloadListenerEvent;)V", "Lnet/minecraftforge/event/village/VillagerTradesEvent;", "onVillagerTradesRegistry", "(Lnet/minecraftforge/event/village/VillagerTradesEvent;)V", "Lnet/minecraftforge/event/village/WandererTradesEvent;", "onWanderingTraderRegistry", "(Lnet/minecraftforge/event/village/WandererTradesEvent;)V", "registerBlockEntityTypes", "registerBlocks", "Lnet/minecraft/resources/ResourceLocation;", "Lnet/minecraft/network/chat/Component;", "title", "Lcom/cobblemon/mod/common/ResourcePackActivationBehaviour;", "activationBehaviour", "registerBuiltinResourcePack", "(Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/network/chat/Component;Lcom/cobblemon/mod/common/ResourcePackActivationBehaviour;)V", "Lcom/mojang/brigadier/arguments/ArgumentType;", "A", "Lnet/minecraft/command/argument/serialize/ArgumentSerializer$ArgumentTypeProperties;", "T", "identifier", "Lkotlin/reflect/KClass;", "argumentClass", "Lnet/minecraft/commands/synchronization/ArgumentTypeInfo;", "serializer", "registerCommandArgument", "(Lnet/minecraft/resources/ResourceLocation;Lkotlin/reflect/KClass;Lnet/minecraft/commands/synchronization/ArgumentTypeInfo;)V", "Lnet/minecraftforge/event/RegisterCommandsEvent;", "registerCommands", "(Lnet/minecraftforge/event/RegisterCommandsEvent;)V", "Lnet/minecraft/world/level/ItemLike;", "item", "", "chance", "registerCompostable", "(Lnet/minecraft/world/level/ItemLike;F)V", "Lnet/minecraft/advancements/CriterionTrigger;", "criteria", "registerCriteria", "(Lnet/minecraft/advancements/CriterionTrigger;)Lnet/minecraft/advancements/CriterionTrigger;", "registerEntityAttributes", "registerEntityTypes", "Lnet/minecraft/world/GameRules$Rule;", "name", "Lnet/minecraft/world/GameRules$Category;", "category", "Lnet/minecraft/world/GameRules$Type;", "type", "Lnet/minecraft/world/GameRules$Key;", "registerGameRule", "(Ljava/lang/String;Lnet/minecraft/world/level/GameRules$Category;Lnet/minecraft/world/level/GameRules$Type;)Lnet/minecraft/world/level/GameRules$Key;", "registerItems", "registerParticles", "registerPermissionValidator", "Lnet/minecraft/server/packs/resources/PreparableReloadListener;", "reloader", "Lnet/minecraft/server/packs/PackType;", "", "dependencies", "registerResourceReloader", "(Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/server/packs/resources/PreparableReloadListener;Lnet/minecraft/server/packs/PackType;Ljava/util/Collection;)V", "registerSoundEvents", "registerWorldGenFeatures", "Lcom/cobblemon/mod/common/api/data/JsonDataRegistry;", "registry", "Lnet/minecraft/server/packs/resources/ResourceManager;", "manager", "Ljava/util/HashMap;", "reloadJsonRegistry", "(Lcom/cobblemon/mod/common/api/data/JsonDataRegistry;Lnet/minecraft/server/packs/resources/ResourceManager;)Ljava/util/HashMap;", "Lnet/minecraft/server/MinecraftServer;", "server", "()Lnet/minecraft/server/MinecraftServer;", "Lnet/minecraftforge/fml/event/lifecycle/FMLDedicatedServerSetupEvent;", "serverInit", "(Lnet/minecraftforge/fml/event/lifecycle/FMLDedicatedServerSetupEvent;)V", "Lnet/minecraftforge/event/entity/player/PlayerWakeUpEvent;", "wakeUp", "(Lnet/minecraftforge/event/entity/player/PlayerWakeUpEvent;)V", "Lnet/minecraftforge/registries/DeferredRegister;", "kotlin.jvm.PlatformType", "commandArgumentTypes", "Lnet/minecraftforge/registries/DeferredRegister;", "Ljava/util/HashSet;", "Ljava/util/UUID;", "Lkotlin/collections/HashSet;", "hasBeenSynced", "Ljava/util/HashSet;", "Lcom/cobblemon/mod/common/ModAPI;", "modAPI", "Lcom/cobblemon/mod/common/ModAPI;", "getModAPI", "()Lcom/cobblemon/mod/common/ModAPI;", "Lcom/cobblemon/mod/common/NetworkManager;", "networkManager", "Lcom/cobblemon/mod/common/NetworkManager;", "getNetworkManager", "()Lcom/cobblemon/mod/common/NetworkManager;", "Ljava/util/ArrayList;", "Lkotlin/Triple;", "Lkotlin/collections/ArrayList;", "queuedBuiltinResourcePacks", "Ljava/util/ArrayList;", "Lkotlin/Function0;", "queuedWork", "reloadableResources", "<init>", "forge"})
@SourceDebugExtension(value={"SMAP\nCobblemonForge.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CobblemonForge.kt\ncom/cobblemon/mod/forge/CobblemonForge\n+ 2 Forge.kt\nthedarkcolour/kotlinforforge/forge/ForgeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 4 _Maps.kt\nkotlin/collections/MapsKt___MapsKt\n*L\n1#1,400:1\n39#2:401\n39#2:402\n39#2:403\n39#2:404\n39#2:405\n39#2:406\n39#2:407\n39#2:408\n39#2:409\n1855#3,2:410\n1855#3,2:414\n1855#3,2:416\n1855#3,2:418\n1855#3,2:420\n1855#3,2:422\n215#4,2:412\n*S KotlinDebug\n*F\n+ 1 CobblemonForge.kt\ncom/cobblemon/mod/forge/CobblemonForge\n*L\n97#1:401\n194#1:402\n202#1:403\n210#1:404\n226#1:405\n249#1:406\n257#1:407\n268#1:408\n276#1:409\n309#1:410,2\n356#1:414,2\n374#1:416,2\n381#1:418,2\n142#1:420,2\n165#1:422,2\n317#1:412,2\n*E\n"})
public final class CobblemonForge
implements CobblemonImplementation {
    @NotNull
    private final ModAPI modAPI = ModAPI.FORGE;

    @NotNull
    @SuppressWarnings({ "unchecked", "rawtypes" })  
    private final HashSet<UUID> hasBeenSynced = new HashSet();

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private final DeferredRegister<ArgumentTypeInfo<?, ?>> commandArgumentTypes = DeferredRegister.create((ResourceKey)Registries.f_256982_, (String)"cobblemon");

    @NotNull
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private final ArrayList<PreparableReloadListener> reloadableResources = new ArrayList();

    @NotNull
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private final ArrayList<Function0<Unit>> queuedWork = new ArrayList();

    @NotNull
    private final ArrayList<Triple<ResourceLocation, Component, ResourcePackActivationBehaviour>> queuedBuiltinResourcePacks = new ArrayList();

    @NotNull
    private final CobblemonForgeNetworkManager networkManager = CobblemonForgeNetworkManager.INSTANCE;

    public CobblemonForge() {
        boolean $i$f$getMOD_BUS = false;
        IEventBus $this$_init__u24lambda_u240 = KotlinModLoadingContext.Companion.get().getKEventBus();
        boolean bl = false;
        this.commandArgumentTypes.register($this$_init__u24lambda_u240);
        $this$_init__u24lambda_u240.addListener(this::initialize);
        $this$_init__u24lambda_u240.addListener(this::serverInit);
        Cobblemon.INSTANCE.preInitialize(this);
        $this$_init__u24lambda_u240.addListener(CobblemonBiomeModifiers.INSTANCE::register);
        $this$_init__u24lambda_u240.addListener(this::on);
        $this$_init__u24lambda_u240.addListener(this::onAddPackFindersEvent);
        IEventBus $this$_init__u24lambda_u241 = MinecraftForge.EVENT_BUS;
        boolean bl2 = false;
        $this$_init__u24lambda_u241.addListener(this::onDataPackSync);
        $this$_init__u24lambda_u241.addListener(this::onLogin);
        $this$_init__u24lambda_u241.addListener(this::onLogout);
        $this$_init__u24lambda_u241.addListener(this::wakeUp);
        $this$_init__u24lambda_u241.addListener(this::handleBlockStripping);
        $this$_init__u24lambda_u241.addListener(this::registerCommands);
        $this$_init__u24lambda_u241.addListener(this::onReload);
        $this$_init__u24lambda_u241.addListener(this::addCobblemonStructures);
        $this$_init__u24lambda_u241.addListener(this::onVillagerTradesRegistry);
        $this$_init__u24lambda_u241.addListener(this::onWanderingTraderRegistry);
        $this$_init__u24lambda_u241.addListener(this::onLootTableLoad);
        ForgePlatformEventHandler.INSTANCE.register();
        DistExecutor.safeRunWhenOn((Dist)Dist.CLIENT, CobblemonForge::_init_$lambda$2);
    }

    @Override
    @NotNull
    public ModAPI getModAPI() {
        return this.modAPI;
    }

    @Override
    @NotNull
    public NetworkManager getNetworkManager() {
        return this.networkManager;
    }

    public final void addCobblemonStructures(@NotNull ServerAboutToStartEvent event) {
        Intrinsics.checkNotNullParameter((Object)event, (String)"event");
        MinecraftServer minecraftServer = event.getServer();
        Intrinsics.checkNotNullExpressionValue((Object)minecraftServer, (String)"event.server");
        CobblemonStructures.INSTANCE.registerJigsaws(minecraftServer);
        MinecraftServer minecraftServer2 = event.getServer();
        Intrinsics.checkNotNullExpressionValue((Object)minecraftServer2, (String)"event.server");
        CobblemonStructureProcessorListOverrides.INSTANCE.register(minecraftServer2);
    }

    public final void wakeUp(@NotNull PlayerWakeUpEvent event) {
        Intrinsics.checkNotNullParameter((Object)event, (String)"event");
        Player player = event.getEntity();
        ServerPlayer serverPlayer = player instanceof ServerPlayer ? (ServerPlayer)player : null;
        if (serverPlayer == null) {
            return;
        }
        ServerPlayer playerEntity = serverPlayer;
        PlayerExtensionsKt.didSleep(playerEntity);
    }

    public final void serverInit(@NotNull FMLDedicatedServerSetupEvent event) {
        Intrinsics.checkNotNullParameter((Object)event, (String)"event");
    }

    public final void initialize(@NotNull FMLCommonSetupEvent event) {
        Intrinsics.checkNotNullParameter((Object)event, (String)"event");
        Cobblemon.INSTANCE.getLOGGER().info("Initializing...");
        this.getNetworkManager().registerClientBound();
        this.getNetworkManager().registerServerBound();
        event.enqueueWork(() -> CobblemonForge.initialize$lambda$4(this));
        Cobblemon.INSTANCE.initialize();
    }

    public final void on(@NotNull RegisterEvent event) {
        Intrinsics.checkNotNullParameter((Object)event, (String)"event");
        event.register(Registries.f_256774_, CobblemonForge::on$lambda$5);
        event.register(Registries.f_256843_, CobblemonForge::on$lambda$6);
        event.register(Registries.f_271200_, CobblemonForge::on$lambda$7);
        event.register(Registries.f_256983_, CobblemonForge::on$lambda$8);
        event.register(Registries.f_257025_, CobblemonForge::on$lambda$10);
    }

    public final void onDataPackSync(@NotNull OnDatapackSyncEvent event) {
        Intrinsics.checkNotNullParameter((Object)event, (String)"event");
        ServerPlayer serverPlayer = event.getPlayer();
        if (serverPlayer == null) {
            return;
        }
        Cobblemon.INSTANCE.getDataProvider().sync(serverPlayer);
    }

    public final void onLogin(@NotNull PlayerEvent.PlayerLoggedInEvent event) {
        Intrinsics.checkNotNullParameter((Object)event, (String)"event");
        this.hasBeenSynced.add(event.getEntity().m_20148_());
    }

    public final void onLogout(@NotNull PlayerEvent.PlayerLoggedOutEvent event) {
        Intrinsics.checkNotNullParameter((Object)event, (String)"event");
        this.hasBeenSynced.remove(event.getEntity().m_20148_());
    }

    @Override
    public boolean isModInstalled(@NotNull String id) {
        Intrinsics.checkNotNullParameter((Object)id, (String)"id");
        return ModList.get().isLoaded(id);
    }

    @Override
    @NotNull
    public Environment environment() {
        return FMLEnvironment.dist.isClient() ? Environment.CLIENT : Environment.SERVER;
    }

    @Override
    public void registerPermissionValidator() {
        Cobblemon.INSTANCE.setPermissionValidator(ForgePermissionValidator.INSTANCE);
    }

    @Override
    public void registerSoundEvents() {
        boolean $i$f$getMOD_BUS = false;
        KotlinModLoadingContext.Companion.get().getKEventBus().addListener(CobblemonForge::registerSoundEvents$lambda$12);
    }

    @Override
    public void registerBlocks() {
        boolean $i$f$getMOD_BUS = false;
        KotlinModLoadingContext.Companion.get().getKEventBus().addListener(CobblemonForge::registerBlocks$lambda$14);
    }

    @Override
    public void registerParticles() {
        boolean $i$f$getMOD_BUS = false;
        KotlinModLoadingContext.Companion.get().getKEventBus().addListener(CobblemonForge::registerParticles$lambda$16);
    }

    private final void handleBlockStripping(BlockEvent.BlockToolModificationEvent e) {
        if (Intrinsics.areEqual((Object)e.getToolAction(), (Object)ToolActions.AXE_STRIP)) {
            Block start2 = e.getState().m_60734_();
            Block block = CobblemonBlocks.INSTANCE.strippedBlocks().get(start2);
            if (block == null) {
                return;
            }
            Block result = block;
            e.setFinalState(result.m_152465_(e.getState()));
        }
    }

    @Override
    public void registerItems() {
        boolean $i$f$getMOD_BUS = false;
        IEventBus $this$registerItems_u24lambda_u2421 = KotlinModLoadingContext.Companion.get().getKEventBus();
        boolean bl = false;
        $this$registerItems_u24lambda_u2421.addListener(CobblemonForge::registerItems$lambda$21$lambda$18);
        $this$registerItems_u24lambda_u2421.addListener(CobblemonForge::registerItems$lambda$21$lambda$20);
    }

    @Override
    public void registerEntityTypes() {
        boolean $i$f$getMOD_BUS = false;
        KotlinModLoadingContext.Companion.get().getKEventBus().addListener(CobblemonForge::registerEntityTypes$lambda$23);
    }

    @Override
    public void registerEntityAttributes() {
        boolean $i$f$getMOD_BUS = false;
        KotlinModLoadingContext.Companion.get().getKEventBus().addListener(CobblemonForge::registerEntityAttributes$lambda$24);
    }

    @Override
    public void registerBlockEntityTypes() {
        boolean $i$f$getMOD_BUS = false;
        KotlinModLoadingContext.Companion.get().getKEventBus().addListener(CobblemonForge::registerBlockEntityTypes$lambda$26);
    }

    @Override
    public void registerWorldGenFeatures() {
        boolean $i$f$getMOD_BUS = false;
        KotlinModLoadingContext.Companion.get().getKEventBus().addListener(CobblemonForge::registerWorldGenFeatures$lambda$28);
    }

    @Override
    public void addFeatureToWorldGen(@NotNull ResourceKey<PlacedFeature> feature, @NotNull GenerationStep.Decoration step, @Nullable TagKey<Biome> validTag) {
        Intrinsics.checkNotNullParameter(feature, (String)"feature");
        Intrinsics.checkNotNullParameter((Object)step, (String)"step");
        CobblemonBiomeModifiers.INSTANCE.add(feature, step, validTag);
    }

    @Override
    public <A extends ArgumentType<?>, T extends ArgumentTypeInfo.Template<A>> void registerCommandArgument(@NotNull ResourceLocation identifier, @NotNull KClass<A> argumentClass, @NotNull ArgumentTypeInfo<A, T> serializer) {
        Intrinsics.checkNotNullParameter((Object)identifier, (String)"identifier");
        Intrinsics.checkNotNullParameter(argumentClass, (String)"argumentClass");
        Intrinsics.checkNotNullParameter(serializer, (String)"serializer");
        this.commandArgumentTypes.register(identifier.m_135815_(), () -> CobblemonForge.registerCommandArgument$lambda$29(argumentClass, serializer));
    }

    private final void registerCommands(RegisterCommandsEvent e) {
        CommandDispatcher commandDispatcher = e.getDispatcher();
        Intrinsics.checkNotNullExpressionValue((Object)commandDispatcher, (String)"e.dispatcher");
        CommandBuildContext commandBuildContext = e.getBuildContext();
        Intrinsics.checkNotNullExpressionValue((Object)commandBuildContext, (String)"e.buildContext");
        Commands.CommandSelection commandSelection = e.getCommandSelection();
        Intrinsics.checkNotNullExpressionValue((Object)commandSelection, (String)"e.commandSelection");
        CobblemonCommands.INSTANCE.register((CommandDispatcher<CommandSourceStack>)commandDispatcher, commandBuildContext, commandSelection);
    }

    @Override
    @NotNull
    public <T extends GameRules.Value<T>> GameRules.Key<T> registerGameRule(@NotNull String name, @NotNull GameRules.Category category, @NotNull GameRules.Type<T> type) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        Intrinsics.checkNotNullParameter((Object)category, (String)"category");
        Intrinsics.checkNotNullParameter(type, (String)"type");
        GameRules.Key key = GameRules.m_46189_((String)name, (GameRules.Category)category, type);
        Intrinsics.checkNotNullExpressionValue((Object)key, (String)"register(name, category, type)");
        return key;
    }

    @Override
    @NotNull
    public <T extends CriterionTrigger<?>> T registerCriteria(@NotNull T criteria) {
        Intrinsics.checkNotNullParameter(criteria, (String)"criteria");
        CriterionTrigger criterionTrigger = CriteriaTriggers.m_10595_(criteria);
        Intrinsics.checkNotNullExpressionValue((Object)criterionTrigger, (String)"register(criteria)");
        return (T)criterionTrigger;
    }

    @Override
    public void registerResourceReloader(@NotNull ResourceLocation identifier, @NotNull PreparableReloadListener reloader, @NotNull PackType type, @NotNull Collection<? extends ResourceLocation> dependencies) {
        Intrinsics.checkNotNullParameter((Object)identifier, (String)"identifier");
        Intrinsics.checkNotNullParameter((Object)reloader, (String)"reloader");
        Intrinsics.checkNotNullParameter((Object)type, (String)"type");
        Intrinsics.checkNotNullParameter(dependencies, (String)"dependencies");
        if (type == PackType.SERVER_DATA) {
            ((Collection)this.reloadableResources).add(reloader);
        } else {
            CobblemonForgeClient.INSTANCE.registerResourceReloader$forge(reloader);
        }
    }

    private final void onReload(AddReloadListenerEvent e) {
        Iterable $this$forEach$iv = this.reloadableResources;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            PreparableReloadListener p0 = (PreparableReloadListener)element$iv;
            boolean bl = false;
            e.addListener(p0);
        }
    }

    @Override
    @Nullable
    public MinecraftServer server() {
        return ServerLifecycleHooks.getCurrentServer();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    @NotNull
    public <T> HashMap<ResourceLocation, T> reloadJsonRegistry(@NotNull JsonDataRegistry<T> registry, @NotNull ResourceManager manager) {
        Intrinsics.checkNotNullParameter(registry, (String)"registry");
        Intrinsics.checkNotNullParameter((Object)manager, (String)"manager");
        HashMap data = new HashMap();
        Map map = manager.m_214159_(registry.getResourcePath(), CobblemonForge::reloadJsonRegistry$lambda$30);
        Intrinsics.checkNotNullExpressionValue((Object)map, (String)"manager.findResources(re\u2026egistry.JSON_EXTENSION) }");
        Map $this$forEach$iv = map;
        boolean $i$f$forEach = false;
        Iterator iterator = $this$forEach$iv.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry element$iv;
            Map.Entry entry = element$iv = iterator.next();
            boolean bl = false;
            ResourceLocation identifier = (ResourceLocation)entry.getKey();
            Resource resource = (Resource)entry.getValue();
            if (Intrinsics.areEqual((Object)identifier.m_135827_(), (Object)"pixelmon")) continue;
            Closeable closeable = resource.m_215507_();
            Throwable throwable = null;
            try {
                InputStream stream = (InputStream)closeable;
                boolean bl2 = false;
                Intrinsics.checkNotNullExpressionValue((Object)stream, (String)"stream");
                Closeable closeable2 = stream;
                Object object = Charsets.UTF_8;
                Reader reader = new InputStreamReader((InputStream)closeable2, (Charset)object);
                int n = 8192;
                closeable2 = reader instanceof BufferedReader ? (BufferedReader)reader : new BufferedReader(reader, n);
                object = null;
                try {
                    BufferedReader reader2 = (BufferedReader)closeable2;
                    boolean bl3 = false;
                    ResourceLocation resolvedIdentifier = new ResourceLocation(identifier.m_135827_(), FilesKt.getNameWithoutExtension((File)new File(identifier.m_135815_())));
                    try {
                        ((Map)data).put(resolvedIdentifier, registry.getGson().fromJson((Reader)reader2, registry.getTypeToken().getType()));
                    }
                    catch (Exception exception) {
                        throw new ExecutionException("Error loading JSON for data: " + identifier, exception);
                    }
                    reader = Unit.INSTANCE;
                }
                catch (Throwable throwable2) {
                    object = throwable2;
                    throw throwable2;
                }
                finally {
                    CloseableKt.closeFinally((Closeable)closeable2, (Throwable)object);
                }
                Unit unit = Unit.INSTANCE;
            }
            catch (Throwable throwable3) {
                throwable = throwable3;
                throw throwable3;
            }
            finally {
                CloseableKt.closeFinally((Closeable)closeable, (Throwable)throwable);
            }
        }
        return data;
    }

    @Override
    public void registerCompostable(@NotNull ItemLike item, float chance) {
        Intrinsics.checkNotNullParameter((Object)item, (String)"item");
        ((Collection)this.queuedWork).add(new Function0<Unit>(item, chance){
            final /* synthetic */ ItemLike $item;
            final /* synthetic */ float $chance;
            {
                this.$item = $item;
                this.$chance = $chance;
                super(0);
            }

            public final void invoke() {
                ComposterBlock.f_51914_.put((Object)this.$item, this.$chance);
            }
        });
    }

    @Override
    public void registerBuiltinResourcePack(@NotNull ResourceLocation id, @NotNull Component title, @NotNull ResourcePackActivationBehaviour activationBehaviour) {
        Intrinsics.checkNotNullParameter((Object)id, (String)"id");
        Intrinsics.checkNotNullParameter((Object)title, (String)"title");
        Intrinsics.checkNotNullParameter((Object)((Object)activationBehaviour), (String)"activationBehaviour");
        ((Collection)this.queuedBuiltinResourcePacks).add(new Triple((Object)id, (Object)title, (Object)activationBehaviour));
    }

    public final void onAddPackFindersEvent(@NotNull AddPackFindersEvent event) {
        Intrinsics.checkNotNullParameter((Object)event, (String)"event");
        if (event.getPackType() != PackType.CLIENT_RESOURCES) {
            return;
        }
        if (this.isModInstalled("adorn")) {
            ResourceLocation resourceLocation = MiscUtils.cobblemonResource("adorncompatibility");
            MutableComponent mutableComponent = Component.m_237113_((String)"Adorn Compatibility");
            Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"literal(\"Adorn Compatibility\")");
            this.registerBuiltinResourcePack(resourceLocation, (Component)mutableComponent, ResourcePackActivationBehaviour.ALWAYS_ENABLED);
        }
        IModFile modFile = ModList.get().getModFileById("cobblemon").getFile();
        Iterable $this$forEach$iv = this.queuedBuiltinResourcePacks;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            Triple triple = (Triple)element$iv;
            boolean bl = false;
            ResourceLocation id = (ResourceLocation)triple.component1();
            Component title = (Component)triple.component2();
            ResourcePackActivationBehaviour activationBehaviour = (ResourcePackActivationBehaviour)((Object)triple.component3());
            String[] stringArray = new String[]{"resourcepacks/" + id.m_135815_()};
            Path path = modFile.findResource(stringArray);
            Pack.ResourcesSupplier factory = arg_0 -> CobblemonForge.onAddPackFindersEvent$lambda$36$lambda$34(path, arg_0);
            Pack profile = Pack.m_245429_((String)id.toString(), (Component)title, (activationBehaviour == ResourcePackActivationBehaviour.ALWAYS_ENABLED ? 1 : 0) != 0, (Pack.ResourcesSupplier)factory, (PackType)PackType.CLIENT_RESOURCES, (Pack.Position)Pack.Position.TOP, (PackSource)PackSource.f_10528_);
            event.addRepositorySource(arg_0 -> CobblemonForge.onAddPackFindersEvent$lambda$36$lambda$35(profile, arg_0));
        }
    }

    private final void onVillagerTradesRegistry(VillagerTradesEvent e) {
        VillagerProfession villagerProfession = e.getType();
        Intrinsics.checkNotNullExpressionValue((Object)villagerProfession, (String)"e.type");
        Iterable $this$forEach$iv = CobblemonTradeOffers.INSTANCE.tradeOffersFor(villagerProfession);
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            CobblemonTradeOffers.VillagerTradeOffer tradeOffer = (CobblemonTradeOffers.VillagerTradeOffer)element$iv;
            boolean bl = false;
            List list = (List)e.getTrades().get(tradeOffer.getRequiredLevel());
            if (list == null) continue;
            list.addAll((Collection)tradeOffer.getTradeOffers());
        }
    }

    private final void onWanderingTraderRegistry(WandererTradesEvent e) {
        Iterable $this$forEach$iv = CobblemonTradeOffers.INSTANCE.resolveWanderingTradeOffers();
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            CobblemonTradeOffers.WandererTradeOffer tradeOffer = (CobblemonTradeOffers.WandererTradeOffer)element$iv;
            boolean bl = false;
            if (tradeOffer.isRareTrade()) {
                e.getRareTrades().addAll((Collection)tradeOffer.getTradeOffers());
                continue;
            }
            e.getGenericTrades().addAll((Collection)tradeOffer.getTradeOffers());
        }
    }

    private final void onLootTableLoad(LootTableLoadEvent e) {
        ResourceLocation resourceLocation = e.getName();
        Intrinsics.checkNotNullExpressionValue((Object)resourceLocation, (String)"e.name");
        LootInjector.INSTANCE.attemptInjection(resourceLocation, (Function1<? super LootPool.Builder, Unit>)((Function1)new Function1<LootPool.Builder, Unit>(e){
            final /* synthetic */ LootTableLoadEvent $e;
            {
                this.$e = $e;
                super(1);
            }

            public final void invoke(@NotNull LootPool.Builder builder) {
                Intrinsics.checkNotNullParameter((Object)builder, (String)"builder");
                this.$e.getTable().addPool(builder.m_79082_());
            }
        }));
    }

    private final void attemptModCompat() {
        if (this.isModInstalled("carryon")) {
            InterModComms.sendTo((String)"carryon", (String)"blacklistEntity", CobblemonForge::attemptModCompat$lambda$39);
            InterModComms.sendTo((String)"carryon", (String)"blacklistEntity", CobblemonForge::attemptModCompat$lambda$40);
        }
    }

    private static final DistExecutor.SafeRunnable _init_$lambda$2() {
        return CobblemonForgeClient.INSTANCE::init;
    }

    private static final void initialize$lambda$4(CobblemonForge this$0) {
        Intrinsics.checkNotNullParameter((Object)this$0, (String)"this$0");
        Iterable $this$forEach$iv = this$0.queuedWork;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            Function0 it = (Function0)element$iv;
            boolean bl = false;
            it.invoke();
        }
        CobblemonForgeBrewingRegistry.INSTANCE.register();
    }

    private static final void on$lambda$5(RegisterEvent.RegisterHelper it) {
        CobblemonBlockPredicates.INSTANCE.touch();
    }

    private static final void on$lambda$6(RegisterEvent.RegisterHelper it) {
        CobblemonPlacementModifierTypes.INSTANCE.touch();
    }

    private static final void on$lambda$7(RegisterEvent.RegisterHelper it) {
        CobblemonSherds.INSTANCE.registerSherds();
    }

    private static final void on$lambda$8(RegisterEvent.RegisterHelper it) {
        CobblemonProcessorTypes.INSTANCE.touch();
    }

    private static final void on$lambda$10(RegisterEvent.RegisterHelper it) {
        Iterable $this$forEach$iv = CobblemonActivities.INSTANCE.getActivities();
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            Activity it2 = (Activity)element$iv;
            boolean bl = false;
            String string = it2.m_37998_();
            Intrinsics.checkNotNullExpressionValue((Object)string, (String)"it.id");
            ForgeRegistries.ACTIVITIES.register(MiscUtils.cobblemonResource(string), (Object)it2);
        }
    }

    private static final void registerSoundEvents$lambda$12$lambda$11(RegisterEvent.RegisterHelper helper) {
        CobblemonSounds.INSTANCE.register((Function2)new Function2<ResourceLocation, SoundEvent, Unit>((RegisterEvent.RegisterHelper<SoundEvent>)helper){
            final /* synthetic */ RegisterEvent.RegisterHelper<SoundEvent> $helper;
            {
                this.$helper = $helper;
                super(2);
            }

            public final void invoke(@NotNull ResourceLocation identifier, @NotNull SoundEvent sounds) {
                Intrinsics.checkNotNullParameter((Object)identifier, (String)"identifier");
                Intrinsics.checkNotNullParameter((Object)sounds, (String)"sounds");
                this.$helper.register(identifier, (Object)sounds);
            }
        });
    }

    private static final void registerSoundEvents$lambda$12(RegisterEvent event) {
        event.register(CobblemonSounds.INSTANCE.getRegistryKey(), CobblemonForge::registerSoundEvents$lambda$12$lambda$11);
    }

    private static final void registerBlocks$lambda$14$lambda$13(RegisterEvent.RegisterHelper helper) {
        CobblemonBlocks.INSTANCE.register((Function2)new Function2<ResourceLocation, Block, Unit>((RegisterEvent.RegisterHelper<Block>)helper){
            final /* synthetic */ RegisterEvent.RegisterHelper<Block> $helper;
            {
                this.$helper = $helper;
                super(2);
            }

            public final void invoke(@NotNull ResourceLocation identifier, @NotNull Block block) {
                Intrinsics.checkNotNullParameter((Object)identifier, (String)"identifier");
                Intrinsics.checkNotNullParameter((Object)block, (String)"block");
                this.$helper.register(identifier, (Object)block);
            }
        });
    }

    private static final void registerBlocks$lambda$14(RegisterEvent event) {
        event.register(CobblemonBlocks.INSTANCE.getRegistryKey(), CobblemonForge::registerBlocks$lambda$14$lambda$13);
    }

    private static final void registerParticles$lambda$16$lambda$15(RegisterEvent.RegisterHelper helper) {
        CobblemonParticles.INSTANCE.register((Function2)new Function2<ResourceLocation, ParticleType<?>, Unit>(helper){
            final /* synthetic */ RegisterEvent.RegisterHelper<ParticleType<?>> $helper;
            {
                this.$helper = $helper;
                super(2);
            }

            public final void invoke(@NotNull ResourceLocation identifier, @NotNull ParticleType<?> particleType) {
                Intrinsics.checkNotNullParameter((Object)identifier, (String)"identifier");
                Intrinsics.checkNotNullParameter(particleType, (String)"particleType");
                this.$helper.register(identifier, particleType);
            }
        });
    }

    private static final void registerParticles$lambda$16(RegisterEvent event) {
        event.register(CobblemonParticles.INSTANCE.getRegistryKey(), CobblemonForge::registerParticles$lambda$16$lambda$15);
    }

    private static final void registerItems$lambda$21$lambda$18$lambda$17(RegisterEvent.RegisterHelper helper) {
        CobblemonItems.INSTANCE.register((Function2)new Function2<ResourceLocation, Item, Unit>((RegisterEvent.RegisterHelper<Item>)helper){
            final /* synthetic */ RegisterEvent.RegisterHelper<Item> $helper;
            {
                this.$helper = $helper;
                super(2);
            }

            public final void invoke(@NotNull ResourceLocation identifier, @NotNull Item item) {
                Intrinsics.checkNotNullParameter((Object)identifier, (String)"identifier");
                Intrinsics.checkNotNullParameter((Object)item, (String)"item");
                this.$helper.register(identifier, (Object)item);
            }
        });
    }

    private static final void registerItems$lambda$21$lambda$18(RegisterEvent event) {
        event.register(CobblemonItems.INSTANCE.getRegistryKey(), CobblemonForge::registerItems$lambda$21$lambda$18$lambda$17);
    }

    private static final void registerItems$lambda$21$lambda$20$lambda$19(RegisterEvent.RegisterHelper helper) {
        CobblemonItemGroups.INSTANCE.register((Function1<? super CobblemonItemGroups.ItemGroupHolder, ? extends CreativeModeTab>)((Function1)new Function1<CobblemonItemGroups.ItemGroupHolder, CreativeModeTab>((RegisterEvent.RegisterHelper<CreativeModeTab>)helper){
            final /* synthetic */ RegisterEvent.RegisterHelper<CreativeModeTab> $helper;
            {
                this.$helper = $helper;
                super(1);
            }

            @NotNull
            public final CreativeModeTab invoke(@NotNull CobblemonItemGroups.ItemGroupHolder holder) {
                Intrinsics.checkNotNullParameter((Object)holder, (String)"holder");
                CreativeModeTab itemGroup = CreativeModeTab.builder().m_257941_(holder.getDisplayName()).m_257737_(() -> registerItems.1.2.1.1.invoke$lambda$0(holder.getDisplayIconProvider())).m_257501_(holder.getEntryCollector()).m_257652_();
                this.$helper.register(holder.getKey(), (Object)itemGroup);
                Intrinsics.checkNotNullExpressionValue((Object)itemGroup, (String)"itemGroup");
                return itemGroup;
            }

            private static final ItemStack invoke$lambda$0(Function0 $tmp0) {
                Intrinsics.checkNotNullParameter((Object)$tmp0, (String)"$tmp0");
                return (ItemStack)$tmp0.invoke();
            }
        }));
    }

    private static final void registerItems$lambda$21$lambda$20(RegisterEvent event) {
        event.register(Registries.f_279569_, CobblemonForge::registerItems$lambda$21$lambda$20$lambda$19);
    }

    private static final void registerEntityTypes$lambda$23$lambda$22(RegisterEvent.RegisterHelper helper) {
        CobblemonEntities.INSTANCE.register((Function2)new Function2<ResourceLocation, EntityType<?>, Unit>(helper){
            final /* synthetic */ RegisterEvent.RegisterHelper<EntityType<?>> $helper;
            {
                this.$helper = $helper;
                super(2);
            }

            public final void invoke(@NotNull ResourceLocation identifier, @NotNull EntityType<?> type) {
                Intrinsics.checkNotNullParameter((Object)identifier, (String)"identifier");
                Intrinsics.checkNotNullParameter(type, (String)"type");
                this.$helper.register(identifier, type);
            }
        });
    }

    private static final void registerEntityTypes$lambda$23(RegisterEvent event) {
        event.register(CobblemonEntities.INSTANCE.getRegistryKey(), CobblemonForge::registerEntityTypes$lambda$23$lambda$22);
    }

    private static final void registerEntityAttributes$lambda$24(EntityAttributeCreationEvent event) {
        CobblemonEntities.INSTANCE.registerAttributes((Function2<? super EntityType<? extends LivingEntity>, ? super AttributeSupplier.Builder, Unit>)((Function2)new Function2<EntityType<? extends LivingEntity>, AttributeSupplier.Builder, Unit>(event){
            final /* synthetic */ EntityAttributeCreationEvent $event;
            {
                this.$event = $event;
                super(2);
            }

            public final void invoke(@NotNull EntityType<? extends LivingEntity> entityType, @NotNull AttributeSupplier.Builder builder) {
                Intrinsics.checkNotNullParameter(entityType, (String)"entityType");
                Intrinsics.checkNotNullParameter((Object)builder, (String)"builder");
                builder.m_22266_((Attribute)ForgeMod.ENTITY_GRAVITY.get()).m_22266_((Attribute)ForgeMod.NAMETAG_DISTANCE.get()).m_22266_((Attribute)ForgeMod.SWIM_SPEED.get());
                this.$event.put(entityType, builder.m_22265_());
            }
        }));
    }

    private static final void registerBlockEntityTypes$lambda$26$lambda$25(RegisterEvent.RegisterHelper helper) {
        CobblemonBlockEntities.INSTANCE.register((Function2)new Function2<ResourceLocation, BlockEntityType<?>, Unit>(helper){
            final /* synthetic */ RegisterEvent.RegisterHelper<BlockEntityType<?>> $helper;
            {
                this.$helper = $helper;
                super(2);
            }

            public final void invoke(@NotNull ResourceLocation identifier, @NotNull BlockEntityType<?> type) {
                Intrinsics.checkNotNullParameter((Object)identifier, (String)"identifier");
                Intrinsics.checkNotNullParameter(type, (String)"type");
                this.$helper.register(identifier, type);
            }
        });
    }

    private static final void registerBlockEntityTypes$lambda$26(RegisterEvent event) {
        event.register(CobblemonBlockEntities.INSTANCE.getRegistryKey(), CobblemonForge::registerBlockEntityTypes$lambda$26$lambda$25);
    }

    private static final void registerWorldGenFeatures$lambda$28$lambda$27(RegisterEvent.RegisterHelper helper) {
        CobblemonFeatures.INSTANCE.register((Function2)new Function2<ResourceLocation, Feature<?>, Unit>(helper){
            final /* synthetic */ RegisterEvent.RegisterHelper<Feature<?>> $helper;
            {
                this.$helper = $helper;
                super(2);
            }

            public final void invoke(@NotNull ResourceLocation identifier, @NotNull Feature<?> feature) {
                Intrinsics.checkNotNullParameter((Object)identifier, (String)"identifier");
                Intrinsics.checkNotNullParameter(feature, (String)"feature");
                this.$helper.register(identifier, feature);
            }
        });
    }

    private static final void registerWorldGenFeatures$lambda$28(RegisterEvent event) {
        event.register(CobblemonFeatures.INSTANCE.getRegistryKey(), CobblemonForge::registerWorldGenFeatures$lambda$28$lambda$27);
    }

    private static final ArgumentTypeInfo registerCommandArgument$lambda$29(KClass $argumentClass, ArgumentTypeInfo $serializer) {
        Intrinsics.checkNotNullParameter((Object)$argumentClass, (String)"$argumentClass");
        Intrinsics.checkNotNullParameter((Object)$serializer, (String)"$serializer");
        return ArgumentTypeInfos.registerByClass((Class)JvmClassMappingKt.getJavaClass((KClass)$argumentClass), (ArgumentTypeInfo)$serializer);
    }

    private static final boolean reloadJsonRegistry$lambda$30(ResourceLocation path) {
        Intrinsics.checkNotNullExpressionValue((Object)path, (String)"path");
        return IdentifierExtensionsKt.endsWith(path, ".json");
    }

    private static final PackResources onAddPackFindersEvent$lambda$36$lambda$34(Path $path, String name) {
        return (PackResources)new PathPackResources(name, true, $path);
    }

    private static final void onAddPackFindersEvent$lambda$36$lambda$35(Pack $profile, Consumer consumer) {
        consumer.accept($profile);
    }

    private static final Object attemptModCompat$lambda$39() {
        return CobblemonEntities.POKEMON_KEY.toString();
    }

    private static final Object attemptModCompat$lambda$40() {
        return CobblemonEntities.EMPTY_POKEBALL_KEY.toString();
    }
}

