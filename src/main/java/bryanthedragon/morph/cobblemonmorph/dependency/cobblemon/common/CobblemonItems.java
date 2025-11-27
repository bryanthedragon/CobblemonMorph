/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.collections.MapsKt
 *  kotlin.jvm.JvmField
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.core.DefaultedRegistry
 *  net.minecraft.core.Registry
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.food.FoodProperties$Builder
 *  net.minecraft.world.item.BlockItem
 *  net.minecraft.world.item.BowlFoodItem
 *  net.minecraft.world.item.HangingSignItem
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.item.SignItem
 *  net.minecraft.world.item.SmithingTemplateItem
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.ButtonBlock
 *  net.minecraft.world.level.block.DoorBlock
 *  net.minecraft.world.level.block.PressurePlateBlock
 *  net.minecraft.world.level.block.StairBlock
 *  net.minecraft.world.level.block.TrapDoorBlock
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.Expression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonBlocks;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonItems;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.CommonAbility;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.item.ability.AbilityChanger;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.mulch.MulchVariant;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.PokeBalls;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.Natures;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stats;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status.Status;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status.Statuses;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.BerryBlock;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.MintBlock;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.boat.CobblemonBoatType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.ApricornItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.ApricornSeedItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.BerryItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.CobblemonBoatItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.CobblemonItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.MedicinalLeekItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.MintLeafItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.MulchItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.PokeBallItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.PokemonItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.RevivalHerbItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.TumblestoneItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.VivichokeItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.armor.CobblemonArmorTrims;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.battle.DireHitItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.battle.GuardSpecItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.battle.XStatItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.berry.FriendshipRaisingBerryItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.berry.HealingBerryItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.berry.PPRestoringBerryItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.berry.PortionHealingBerryItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.berry.StatusCuringBerryItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.berry.VolatileCuringBerryItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.interactive.BerryJuiceItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.interactive.CandyItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.interactive.ElixirItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.interactive.EnergyRootItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.interactive.EtherItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.interactive.FeatherItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.interactive.HealPowderItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.interactive.LinkCableItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.interactive.MintItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.interactive.PPUpItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.interactive.PotionItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.interactive.PotionType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.interactive.RemedyItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.interactive.ReviveItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.interactive.StatusCureItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.interactive.VitaminItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.interactive.ability.AbilityChangeItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.PlatformRegistry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokeball.PokeBall;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.abilities.HiddenAbility;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.helditem.CobblemonHeldItemManager;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtils;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.MapsKt;
import kotlin.jvm.JvmField;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.core.DefaultedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BowlFoodItem;
import net.minecraft.world.item.HangingSignItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.item.SmithingTemplateItem;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u00fd\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u001a\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0013\n\u0002\u0018\u0002\n\u0002\b\u0015\n\u0002\u0018\u0002\n\u0002\b\u001f\n\u0002\u0018\u0002\n\u0002\b\u0014\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0018\u0002\n\u0002\b\u0014\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b$\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0016\n\u0002\u0018\u0002\n\u0002\b;\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0003\b\u0082\u0001\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010%\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\b\r\b\u00c6\u0002\u0018\u00002&\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u0002\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u00020\u0004\u0012\u0004\u0012\u00020\u00030\u0001B\u000b\b\u0002\u00a2\u0006\u0006\b\u00f4\u0004\u0010\u00f5\u0004J\u001f\u0010\b\u001a\u00020\u00072\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0007H\u0002\u00a2\u0006\u0004\b\b\u0010\tJ\u001f\u0010\u000b\u001a\u00020\n2\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\nH\u0002\u00a2\u0006\u0004\b\u000b\u0010\fJ\u0019\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f0\r\u00a2\u0006\u0004\b\u0010\u0010\u0011J\u001f\u0010\u0014\u001a\u00020\u000f2\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0013\u001a\u00020\u0012H\u0002\u00a2\u0006\u0004\b\u0014\u0010\u0015J\u001f\u0010\u0014\u001a\u00020\u000f2\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0014\u001a\u00020\u000fH\u0002\u00a2\u0006\u0004\b\u0014\u0010\u0016J\u001f\u0010\u001a\u001a\u00020\u00192\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0018\u001a\u00020\u0017H\u0002\u00a2\u0006\u0004\b\u001a\u0010\u001bJ\u001f\u0010\u001f\u001a\u00020\u001e2\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u001d\u001a\u00020\u001cH\u0002\u00a2\u0006\u0004\b\u001f\u0010 J\u001f\u0010%\u001a\u00020$2\u0006\u0010!\u001a\u00020\u00032\u0006\u0010#\u001a\u00020\"H\u0002\u00a2\u0006\u0004\b%\u0010&J)\u0010'\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0018\u001a\u00020\u00172\b\b\u0002\u0010#\u001a\u00020\"H\u0002\u00a2\u0006\u0004\b'\u0010(J-\u0010+\u001a\u00020*2\u0006\u0010\u0006\u001a\u00020\u00052\n\b\u0002\u0010)\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010#\u001a\u00020\"H\u0002\u00a2\u0006\u0004\b+\u0010,J-\u0010-\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u00052\n\b\u0002\u0010!\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010#\u001a\u00020\"H\u0002\u00a2\u0006\u0004\b-\u0010.J#\u0010/\u001a\u00020*2\u0006\u0010\u0006\u001a\u00020\u00052\n\b\u0002\u0010)\u001a\u0004\u0018\u00010\u0005H\u0002\u00a2\u0006\u0004\b/\u00100J+\u0010/\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010!\u001a\u00020\u00032\n\b\u0002\u0010)\u001a\u0004\u0018\u00010\u0005H\u0002\u00a2\u0006\u0004\b/\u00101J\u001f\u00103\u001a\u0002022\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u00103\u001a\u000202H\u0002\u00a2\u0006\u0004\b3\u00104J\u001f\u00107\u001a\u0002052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u00106\u001a\u000205H\u0002\u00a2\u0006\u0004\b7\u00108J\u001f\u0010;\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010:\u001a\u000209H\u0002\u00a2\u0006\u0004\b;\u0010<J\u001f\u0010@\u001a\u00020?2\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010>\u001a\u00020=H\u0002\u00a2\u0006\u0004\b@\u0010AJ\u0017\u0010B\u001a\u00020*2\u0006\u0010\u0006\u001a\u00020\u0005H\u0002\u00a2\u0006\u0004\bB\u0010CJ\u0017\u0010G\u001a\u00020F2\u0006\u0010E\u001a\u00020DH\u0002\u00a2\u0006\u0004\bG\u0010HR\u001a\u0010K\u001a\b\u0012\u0004\u0012\u00020J0I8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bK\u0010LR\u001a\u0010N\u001a\b\u0012\u0004\u0012\u00020M0I8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bN\u0010LR\u0014\u0010O\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bO\u0010PR\u0014\u0010Q\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bQ\u0010PR\u0014\u0010R\u001a\u0002028\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bR\u0010SR\u0014\u0010T\u001a\u00020\u000f8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bT\u0010UR\u0014\u0010V\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bV\u0010PR\u0014\u0010W\u001a\u00020F8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bW\u0010XR\u0014\u0010Y\u001a\u00020F8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bY\u0010XR\u0014\u0010Z\u001a\u00020F8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bZ\u0010XR\u0014\u0010[\u001a\u00020F8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b[\u0010XR\u0014\u0010\\\u001a\u00020F8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\\\u0010XR\u0014\u0010]\u001a\u00020F8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b]\u0010XR\u0014\u0010^\u001a\u00020F8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b^\u0010XR\u0014\u0010_\u001a\u00020F8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b_\u0010XR\u0014\u0010`\u001a\u00020F8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b`\u0010XR\u0014\u0010a\u001a\u00020F8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\ba\u0010XR\u0014\u0010b\u001a\u00020F8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bb\u0010XR\u0014\u0010c\u001a\u00020F8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bc\u0010XR\u0014\u0010d\u001a\u00020F8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bd\u0010XR\u0014\u0010e\u001a\u00020F8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\be\u0010XR\u0014\u0010f\u001a\u00020F8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bf\u0010XR\u0014\u0010g\u001a\u00020F8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bg\u0010XR\u0014\u0010i\u001a\u00020h8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bi\u0010jR\u0014\u0010k\u001a\u00020\u000f8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bk\u0010UR\u0014\u0010m\u001a\u00020l8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bm\u0010nR\u0014\u0010o\u001a\u00020\u00198\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bo\u0010pR\u0014\u0010q\u001a\u00020l8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bq\u0010nR\u0014\u0010r\u001a\u00020\u00198\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\br\u0010pR\u0014\u0010s\u001a\u00020\u00198\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bs\u0010pR\u0014\u0010t\u001a\u00020\u00198\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bt\u0010pR\u0014\u0010v\u001a\u00020u8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bv\u0010wR\u0014\u0010x\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bx\u0010yR\u0014\u0010z\u001a\u00020\u00198\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bz\u0010pR\u0014\u0010{\u001a\u00020\u00198\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b{\u0010pR\u0014\u0010|\u001a\u00020\u00198\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b|\u0010pR\u0014\u0010~\u001a\u00020}8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b~\u0010\u007fR\u0016\u0010\u0080\u0001\u001a\u00020\u00198\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u0080\u0001\u0010pR\u0016\u0010\u0081\u0001\u001a\u00020\u00198\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u0081\u0001\u0010pR\u0016\u0010\u0082\u0001\u001a\u00020\u00198\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u0082\u0001\u0010pR\u0016\u0010\u0083\u0001\u001a\u00020\u00198\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u0083\u0001\u0010pR\u0016\u0010\u0084\u0001\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u0084\u0001\u0010PR\u0016\u0010\u0085\u0001\u001a\u00020\u000f8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u0085\u0001\u0010UR\u0016\u0010\u0086\u0001\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u0086\u0001\u0010PR\u0016\u0010\u0087\u0001\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u0087\u0001\u0010PR\u0018\u0010\u0089\u0001\u001a\u00030\u0088\u00018\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u0089\u0001\u0010\u008a\u0001R\u0016\u0010\u008b\u0001\u001a\u00020h8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u008b\u0001\u0010jR\u0016\u0010\u008c\u0001\u001a\u00020F8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u008c\u0001\u0010XR\u0016\u0010\u008d\u0001\u001a\u00020\u000f8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u008d\u0001\u0010UR\u0016\u0010\u008e\u0001\u001a\u00020F8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u008e\u0001\u0010XR\u0016\u0010\u008f\u0001\u001a\u00020\u000f8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u008f\u0001\u0010UR\u0018\u0010\u0091\u0001\u001a\u00030\u0090\u00018\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u0091\u0001\u0010\u0092\u0001R\u0016\u0010\u0093\u0001\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u0093\u0001\u0010PR\u0016\u0010\u0094\u0001\u001a\u00020\u00198\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u0094\u0001\u0010pR\u0016\u0010\u0095\u0001\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u0095\u0001\u0010PR\u0017\u0010\u0096\u0001\u001a\u00020\u00078\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u0096\u0001\u0010\u0097\u0001R\u0017\u0010\u0098\u0001\u001a\u00020\n8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u0098\u0001\u0010\u0099\u0001R\u0016\u0010\u009a\u0001\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u009a\u0001\u0010PR\u0016\u0010\u009b\u0001\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u009b\u0001\u0010PR\u0016\u0010\u009c\u0001\u001a\u00020\u00198\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u009c\u0001\u0010pR\u0016\u0010\u009d\u0001\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u009d\u0001\u0010PR\u0016\u0010\u009e\u0001\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u009e\u0001\u0010PR\u0018\u0010\u00a0\u0001\u001a\u00030\u009f\u00018\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u00a0\u0001\u0010\u00a1\u0001R\u0016\u0010\u00a2\u0001\u001a\u00020\u00198\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00a2\u0001\u0010pR\u0016\u0010\u00a3\u0001\u001a\u00020\u00198\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00a3\u0001\u0010pR\u0017\u0010\u00a4\u0001\u001a\u00020\u00078\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u00a4\u0001\u0010\u0097\u0001R\u0017\u0010\u00a5\u0001\u001a\u00020\n8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u00a5\u0001\u0010\u0099\u0001R\u0016\u0010\u00a6\u0001\u001a\u00020\u00198\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00a6\u0001\u0010pR\u0017\u0010\u00a7\u0001\u001a\u0002058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u00a7\u0001\u0010\u00a8\u0001R\u0016\u0010\u00a9\u0001\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00a9\u0001\u0010yR\u0016\u0010\u00aa\u0001\u001a\u00020\u000f8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00aa\u0001\u0010UR\u0016\u0010\u00ab\u0001\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00ab\u0001\u0010PR\u0016\u0010\u00ac\u0001\u001a\u0002028\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00ac\u0001\u0010SR\u0016\u0010\u00ad\u0001\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00ad\u0001\u0010yR\u0016\u0010\u00ae\u0001\u001a\u0002028\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00ae\u0001\u0010SR\u0016\u0010\u00af\u0001\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00af\u0001\u0010PR\u0016\u0010\u00b0\u0001\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00b0\u0001\u0010PR\u0016\u0010\u00b1\u0001\u001a\u00020h8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00b1\u0001\u0010jR\u0016\u0010\u00b2\u0001\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00b2\u0001\u0010PR\u0018\u0010\u00b4\u0001\u001a\u00030\u00b3\u00018\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u00b4\u0001\u0010\u00b5\u0001R\u0016\u0010\u00b6\u0001\u001a\u0002028\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00b6\u0001\u0010SR\u0016\u0010\u00b7\u0001\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00b7\u0001\u0010PR\u0018\u0010\u00b8\u0001\u001a\u00030\u00b3\u00018\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u00b8\u0001\u0010\u00b5\u0001R\u0016\u0010\u00b9\u0001\u001a\u0002028\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00b9\u0001\u0010SR\u0016\u0010\u00ba\u0001\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00ba\u0001\u0010PR\u0016\u0010\u00bb\u0001\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00bb\u0001\u0010PR\u0016\u0010\u00bc\u0001\u001a\u00020\u000f8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00bc\u0001\u0010UR\u0016\u0010\u00bd\u0001\u001a\u00020F8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00bd\u0001\u0010XR\u0016\u0010\u00be\u0001\u001a\u00020\u000f8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00be\u0001\u0010UR\u0016\u0010\u00bf\u0001\u001a\u00020\u000f8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00bf\u0001\u0010UR\u0016\u0010\u00c0\u0001\u001a\u00020\u000f8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00c0\u0001\u0010UR\u0016\u0010\u00c1\u0001\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00c1\u0001\u0010PR\u0016\u0010\u00c2\u0001\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00c2\u0001\u0010PR\u0016\u0010\u00c3\u0001\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00c3\u0001\u0010PR\u0016\u0010\u00c4\u0001\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00c4\u0001\u0010PR\u0016\u0010\u00c5\u0001\u001a\u00020\u000f8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00c5\u0001\u0010UR\u0016\u0010\u00c6\u0001\u001a\u00020F8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00c6\u0001\u0010XR\u0016\u0010\u00c7\u0001\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00c7\u0001\u0010PR\u0016\u0010\u00c8\u0001\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00c8\u0001\u0010PR\u0018\u0010\u00ca\u0001\u001a\u00030\u00c9\u00018\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u00ca\u0001\u0010\u00cb\u0001R\u0016\u0010\u00cc\u0001\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00cc\u0001\u0010PR\u0017\u0010\u00cd\u0001\u001a\u00020?8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u00cd\u0001\u0010\u00ce\u0001R\u0016\u0010\u00cf\u0001\u001a\u00020\u000f8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00cf\u0001\u0010UR\u0016\u0010\u00d0\u0001\u001a\u00020\u000f8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00d0\u0001\u0010UR\u0016\u0010\u00d1\u0001\u001a\u00020\u000f8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00d1\u0001\u0010UR\u0016\u0010\u00d2\u0001\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00d2\u0001\u0010PR\u0016\u0010\u00d3\u0001\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00d3\u0001\u0010PR\u0016\u0010\u00d4\u0001\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00d4\u0001\u0010PR\u0016\u0010\u00d5\u0001\u001a\u00020\u000f8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00d5\u0001\u0010UR\u0017\u0010\u00d6\u0001\u001a\u0002058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u00d6\u0001\u0010\u00a8\u0001R\u0016\u0010\u00d7\u0001\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00d7\u0001\u0010yR\u0016\u0010\u00d8\u0001\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00d8\u0001\u0010PR\u0016\u0010\u00d9\u0001\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00d9\u0001\u0010PR\u0016\u0010\u00da\u0001\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00da\u0001\u0010PR\u0016\u0010\u00db\u0001\u001a\u00020\u00198\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00db\u0001\u0010pR\u0016\u0010\u00dc\u0001\u001a\u00020\u00198\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00dc\u0001\u0010pR\u0016\u0010\u00dd\u0001\u001a\u00020\u00198\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00dd\u0001\u0010pR\u0016\u0010\u00de\u0001\u001a\u00020\u00198\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00de\u0001\u0010pR\u0016\u0010\u00df\u0001\u001a\u00020\u00198\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00df\u0001\u0010pR\u0016\u0010\u00e0\u0001\u001a\u00020\u00198\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00e0\u0001\u0010pR\u0016\u0010\u00e1\u0001\u001a\u00020\u00198\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00e1\u0001\u0010pR\u0016\u0010\u00e2\u0001\u001a\u00020\u00198\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00e2\u0001\u0010pR\u0016\u0010\u00e3\u0001\u001a\u00020\u00198\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00e3\u0001\u0010pR\u0016\u0010\u00e4\u0001\u001a\u00020\u00198\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00e4\u0001\u0010pR\u0016\u0010\u00e5\u0001\u001a\u00020\u00198\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00e5\u0001\u0010pR\u0016\u0010\u00e6\u0001\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00e6\u0001\u0010PR\u0016\u0010\u00e7\u0001\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00e7\u0001\u0010PR\u0016\u0010\u00e8\u0001\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00e8\u0001\u0010PR\u0018\u0010\u00ea\u0001\u001a\u00030\u00e9\u00018\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u00ea\u0001\u0010\u00eb\u0001R\u0016\u0010\u00ec\u0001\u001a\u00020\u00198\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00ec\u0001\u0010pR\u0016\u0010\u00ed\u0001\u001a\u00020F8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00ed\u0001\u0010XR\u0016\u0010\u00ee\u0001\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00ee\u0001\u0010PR\u0016\u0010\u00ef\u0001\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00ef\u0001\u0010PR\u0016\u0010\u00f0\u0001\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00f0\u0001\u0010PR\u0016\u0010\u00f1\u0001\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00f1\u0001\u0010PR\u0016\u0010\u00f2\u0001\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00f2\u0001\u0010PR\u0016\u0010\u00f3\u0001\u001a\u00020F8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00f3\u0001\u0010XR\u0016\u0010\u00f4\u0001\u001a\u00020\u00198\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00f4\u0001\u0010pR\u0016\u0010\u00f5\u0001\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00f5\u0001\u0010PR\u0016\u0010\u00f6\u0001\u001a\u00020\u000f8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00f6\u0001\u0010UR\u0016\u0010\u00f7\u0001\u001a\u00020F8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00f7\u0001\u0010XR\u0016\u0010\u00f8\u0001\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00f8\u0001\u0010PR\u0016\u0010\u00f9\u0001\u001a\u00020\u00198\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00f9\u0001\u0010pR\u0016\u0010\u00fa\u0001\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00fa\u0001\u0010PR\u0016\u0010\u00fb\u0001\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00fb\u0001\u0010PR\u0016\u0010\u00fc\u0001\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00fc\u0001\u0010PR\u0016\u0010\u00fd\u0001\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00fd\u0001\u0010PR\u0018\u0010\u00ff\u0001\u001a\u00030\u00fe\u00018\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u00ff\u0001\u0010\u0080\u0002R\u0016\u0010\u0081\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u0081\u0002\u0010yR\u0016\u0010\u0082\u0002\u001a\u00020\u000f8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u0082\u0002\u0010UR\u0018\u0010\u0084\u0002\u001a\u00030\u0083\u00028\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u0084\u0002\u0010\u0085\u0002R\u0016\u0010\u0086\u0002\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u0086\u0002\u0010PR\u0016\u0010\u0087\u0002\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u0087\u0002\u0010PR\u0017\u0010\u0088\u0002\u001a\u00020\u001e8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u0088\u0002\u0010\u0089\u0002R\u0017\u0010\u008a\u0002\u001a\u00020\u001e8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u008a\u0002\u0010\u0089\u0002R\u0017\u0010\u008b\u0002\u001a\u00020\u001e8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u008b\u0002\u0010\u0089\u0002R\u0017\u0010\u008c\u0002\u001a\u00020\u001e8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u008c\u0002\u0010\u0089\u0002R\u0017\u0010\u008d\u0002\u001a\u00020\u001e8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u008d\u0002\u0010\u0089\u0002R\u0016\u0010\u008e\u0002\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u008e\u0002\u0010PR\u0016\u0010\u008f\u0002\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u008f\u0002\u0010PR\u0016\u0010\u0090\u0002\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u0090\u0002\u0010PR\u0016\u0010\u0091\u0002\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u0091\u0002\u0010PR\u0016\u0010\u0092\u0002\u001a\u00020F8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u0092\u0002\u0010XR\u0016\u0010\u0093\u0002\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u0093\u0002\u0010PR\u0016\u0010\u0094\u0002\u001a\u00020\u000f8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u0094\u0002\u0010UR\u0018\u0010\u0096\u0002\u001a\u00030\u0095\u00028\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u0096\u0002\u0010\u0097\u0002R\u0016\u0010\u0098\u0002\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u0098\u0002\u0010PR\u0016\u0010\u0099\u0002\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u0099\u0002\u0010PR\u0016\u0010\u009a\u0002\u001a\u00020\u00198\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u009a\u0002\u0010pR\u0016\u0010\u009b\u0002\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u009b\u0002\u0010PR\u0016\u0010\u009c\u0002\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u009c\u0002\u0010PR\u0016\u0010\u009d\u0002\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u009d\u0002\u0010PR\u0016\u0010\u009e\u0002\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u009e\u0002\u0010PR\u0016\u0010\u009f\u0002\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u009f\u0002\u0010PR\u0016\u0010\u00a0\u0002\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00a0\u0002\u0010PR\u0016\u0010\u00a1\u0002\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00a1\u0002\u0010PR\u0016\u0010\u00a2\u0002\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00a2\u0002\u0010PR\u0016\u0010\u00a3\u0002\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00a3\u0002\u0010PR\u0016\u0010\u00a4\u0002\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00a4\u0002\u0010PR\u0016\u0010\u00a5\u0002\u001a\u00020\u00198\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00a5\u0002\u0010pR\u0016\u0010\u00a6\u0002\u001a\u00020F8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00a6\u0002\u0010XR\u0016\u0010\u00a7\u0002\u001a\u00020h8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00a7\u0002\u0010jR\u0018\u0010\u00a9\u0002\u001a\u00030\u00a8\u00028\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u00a9\u0002\u0010\u00aa\u0002R\u0016\u0010\u00ab\u0002\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00ab\u0002\u0010PR\u0016\u0010\u00ac\u0002\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00ac\u0002\u0010PR\u0016\u0010\u00ad\u0002\u001a\u00020\u000f8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00ad\u0002\u0010UR\u0018\u0010\u00ae\u0002\u001a\u00030\u00c9\u00018\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u00ae\u0002\u0010\u00cb\u0001R\u0016\u0010\u00af\u0002\u001a\u0002028\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00af\u0002\u0010SR\u0016\u0010\u00b0\u0002\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00b0\u0002\u0010PR\u0016\u0010\u00b1\u0002\u001a\u00020\u00198\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00b1\u0002\u0010pR\u0016\u0010\u00b2\u0002\u001a\u00020\u00198\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00b2\u0002\u0010pR\u0016\u0010\u00b3\u0002\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00b3\u0002\u0010PR\u0016\u0010\u00b4\u0002\u001a\u00020F8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00b4\u0002\u0010XR\u0017\u0010\u00b5\u0002\u001a\u00020\u00078\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u00b5\u0002\u0010\u0097\u0001R\u0017\u0010\u00b6\u0002\u001a\u00020\n8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u00b6\u0002\u0010\u0099\u0001R\u0016\u0010\u00b7\u0002\u001a\u00020\u00198\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00b7\u0002\u0010pR\u0017\u0010\u00b8\u0002\u001a\u0002058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u00b8\u0002\u0010\u00a8\u0001R\u0016\u0010\u00b9\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00b9\u0002\u0010yR\u0016\u0010\u00ba\u0002\u001a\u00020\u000f8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00ba\u0002\u0010UR\u0016\u0010\u00bb\u0002\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00bb\u0002\u0010PR\u0017\u0010\u00bc\u0002\u001a\u00020?8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u00bc\u0002\u0010\u00ce\u0001R\u0018\u0010\u00be\u0002\u001a\u00030\u00bd\u00028\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u00be\u0002\u0010\u00bf\u0002R\u0016\u0010\u00c0\u0002\u001a\u00020\u000f8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00c0\u0002\u0010UR\u0016\u0010\u00c1\u0002\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00c1\u0002\u0010PR\u0016\u0010\u00c2\u0002\u001a\u0002028\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00c2\u0002\u0010SR\u0016\u0010\u00c3\u0002\u001a\u00020\u00198\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00c3\u0002\u0010pR\u0018\u0010\u00c4\u0002\u001a\u00030\u00c9\u00018\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u00c4\u0002\u0010\u00cb\u0001R\u0016\u0010\u00c5\u0002\u001a\u00020F8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00c5\u0002\u0010XR\u0018\u0010\u00c7\u0002\u001a\u00030\u00c6\u00028\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u00c7\u0002\u0010\u00c8\u0002R\u0016\u0010\u00c9\u0002\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00c9\u0002\u0010PR\u0016\u0010\u00ca\u0002\u001a\u00020F8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00ca\u0002\u0010XR\u0016\u0010\u00cb\u0002\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00cb\u0002\u0010PR\u0016\u0010\u00cc\u0002\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00cc\u0002\u0010PR\u0016\u0010\u00cd\u0002\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00cd\u0002\u0010PR\u0016\u0010\u00ce\u0002\u001a\u00020\u000f8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00ce\u0002\u0010UR\u0016\u0010\u00cf\u0002\u001a\u00020\u000f8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00cf\u0002\u0010UR\u0018\u0010\u00d0\u0002\u001a\u00030\u00b3\u00018\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u00d0\u0002\u0010\u00b5\u0001R\u0017\u0010\u00d1\u0002\u001a\u00020?8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u00d1\u0002\u0010\u00ce\u0001R\u0018\u0010\u00d2\u0002\u001a\u00030\u00a8\u00028\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u00d2\u0002\u0010\u00aa\u0002R\u0016\u0010\u00d3\u0002\u001a\u00020\u000f8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00d3\u0002\u0010UR\u0016\u0010\u00d4\u0002\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00d4\u0002\u0010PR\u0016\u0010\u00d5\u0002\u001a\u00020h8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00d5\u0002\u0010jR\u0016\u0010\u00d6\u0002\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00d6\u0002\u0010PR\u0016\u0010\u00d7\u0002\u001a\u00020\u00198\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00d7\u0002\u0010pR\u0016\u0010\u00d8\u0002\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00d8\u0002\u0010PR\u0016\u0010\u00d9\u0002\u001a\u0002028\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00d9\u0002\u0010SR\u0018\u0010\u00da\u0002\u001a\u00030\u00b3\u00018\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u00da\u0002\u0010\u00b5\u0001R\u0016\u0010\u00db\u0002\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00db\u0002\u0010PR\u0016\u0010\u00dc\u0002\u001a\u00020\u000f8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00dc\u0002\u0010UR\u0016\u0010\u00dd\u0002\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00dd\u0002\u0010PR\u0016\u0010\u00de\u0002\u001a\u0002028\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00de\u0002\u0010SR\u0016\u0010\u00df\u0002\u001a\u00020\u000f8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00df\u0002\u0010UR\u0016\u0010\u00e0\u0002\u001a\u00020\u000f8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00e0\u0002\u0010UR\u0016\u0010\u00e1\u0002\u001a\u00020\u000f8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00e1\u0002\u0010UR\u0016\u0010\u00e2\u0002\u001a\u00020\u000f8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00e2\u0002\u0010UR\u0016\u0010\u00e3\u0002\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00e3\u0002\u0010PR\u0016\u0010\u00e4\u0002\u001a\u00020\u000f8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00e4\u0002\u0010UR\u0016\u0010\u00e5\u0002\u001a\u00020\u00198\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00e5\u0002\u0010pR\u0016\u0010\u00e6\u0002\u001a\u00020\u00198\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00e6\u0002\u0010pR\u0016\u0010\u00e7\u0002\u001a\u00020\u00198\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00e7\u0002\u0010pR\u0016\u0010\u00e8\u0002\u001a\u0002028\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00e8\u0002\u0010SR\u0016\u0010\u00e9\u0002\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00e9\u0002\u0010PR\u0016\u0010\u00ea\u0002\u001a\u00020\u00198\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00ea\u0002\u0010pR\u0018\u0010\u00ec\u0002\u001a\u00030\u00eb\u00028\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u00ec\u0002\u0010\u00ed\u0002R\u0016\u0010\u00ee\u0002\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00ee\u0002\u0010PR\u0016\u0010\u00ef\u0002\u001a\u00020\u000f8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00ef\u0002\u0010UR\u0016\u0010\u00f0\u0002\u001a\u00020F8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00f0\u0002\u0010XR\u0016\u0010\u00f1\u0002\u001a\u00020\u000f8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00f1\u0002\u0010UR\u0016\u0010\u00f2\u0002\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00f2\u0002\u0010PR\u0016\u0010\u00f3\u0002\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00f3\u0002\u0010PR\u0016\u0010\u00f4\u0002\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00f4\u0002\u0010PR\u0018\u0010\u00f6\u0002\u001a\u00030\u00f5\u00028\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u00f6\u0002\u0010\u00f7\u0002R\u0016\u0010\u00f8\u0002\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00f8\u0002\u0010PR\u0017\u0010\u00f9\u0002\u001a\u00020?8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u00f9\u0002\u0010\u00ce\u0001R\u0016\u0010\u00fa\u0002\u001a\u0002028\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00fa\u0002\u0010SR\u0016\u0010\u00fb\u0002\u001a\u00020F8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00fb\u0002\u0010XR\u0016\u0010\u00fc\u0002\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00fc\u0002\u0010PR\u0016\u0010\u00fd\u0002\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00fd\u0002\u0010PR\u0016\u0010\u00fe\u0002\u001a\u00020\u000f8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00fe\u0002\u0010UR\u0016\u0010\u00ff\u0002\u001a\u00020F8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00ff\u0002\u0010XR\u0016\u0010\u0080\u0003\u001a\u00020F8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u0080\u0003\u0010XR\u0016\u0010\u0081\u0003\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u0081\u0003\u0010PR\u0016\u0010\u0082\u0003\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u0082\u0003\u0010PR\u0016\u0010\u0083\u0003\u001a\u00020\u000f8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u0083\u0003\u0010UR\u0016\u0010\u0084\u0003\u001a\u00020\u000f8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u0084\u0003\u0010UR\u0016\u0010\u0085\u0003\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u0085\u0003\u0010PR\u0016\u0010\u0086\u0003\u001a\u00020\u000f8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u0086\u0003\u0010UR\u0016\u0010\u0087\u0003\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u0087\u0003\u0010PR\u0016\u0010\u0088\u0003\u001a\u00020F8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u0088\u0003\u0010XR\u0018\u0010\u0089\u0003\u001a\u00030\u00fe\u00018\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u0089\u0003\u0010\u0080\u0002R\u0018\u0010\u008a\u0003\u001a\u00030\u0083\u00028\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u008a\u0003\u0010\u0085\u0002R\u0018\u0010\u008b\u0003\u001a\u00030\u00a8\u00028\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u008b\u0003\u0010\u00aa\u0002R\u0018\u0010\u008d\u0003\u001a\u00030\u008c\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u008d\u0003\u0010\u008e\u0003R\u0016\u0010\u008f\u0003\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u008f\u0003\u0010yR\u0016\u0010\u0090\u0003\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u0090\u0003\u0010yR\u0016\u0010\u0091\u0003\u001a\u00020\u00198\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u0091\u0003\u0010pR\u0016\u0010\u0092\u0003\u001a\u00020\u00198\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u0092\u0003\u0010pR\u0016\u0010\u0093\u0003\u001a\u00020\u00198\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u0093\u0003\u0010pR\u0016\u0010\u0094\u0003\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u0094\u0003\u0010PR\u0016\u0010\u0095\u0003\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u0095\u0003\u0010PR\u0016\u0010\u0096\u0003\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u0096\u0003\u0010PR\u0016\u0010\u0097\u0003\u001a\u00020\u000f8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u0097\u0003\u0010UR\u0016\u0010\u0098\u0003\u001a\u0002028\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u0098\u0003\u0010SR\u0016\u0010\u0099\u0003\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u0099\u0003\u0010PR\u0016\u0010\u009a\u0003\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u009a\u0003\u0010PR\u0016\u0010\u009b\u0003\u001a\u0002028\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u009b\u0003\u0010SR\u0016\u0010\u009c\u0003\u001a\u00020\u00198\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u009c\u0003\u0010pR\u0016\u0010\u009d\u0003\u001a\u00020F8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u009d\u0003\u0010XR\u0016\u0010\u009e\u0003\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u009e\u0003\u0010PR\u0016\u0010\u009f\u0003\u001a\u00020\u00198\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u009f\u0003\u0010pR\u0016\u0010\u00a0\u0003\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00a0\u0003\u0010PR\u0016\u0010\u00a1\u0003\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00a1\u0003\u0010PR\u0018\u0010\u00a2\u0003\u001a\u00030\u00c9\u00018\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u00a2\u0003\u0010\u00cb\u0001R\u0016\u0010\u00a3\u0003\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00a3\u0003\u0010PR\u0016\u0010\u00a4\u0003\u001a\u0002028\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00a4\u0003\u0010SR\u0016\u0010\u00a5\u0003\u001a\u00020\u000f8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00a5\u0003\u0010UR\u0016\u0010\u00a6\u0003\u001a\u0002028\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00a6\u0003\u0010SR\u0016\u0010\u00a7\u0003\u001a\u00020F8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00a7\u0003\u0010XR\u0016\u0010\u00a8\u0003\u001a\u00020\u00198\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00a8\u0003\u0010pR\u0016\u0010\u00a9\u0003\u001a\u00020F8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00a9\u0003\u0010XR\u0016\u0010\u00aa\u0003\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00aa\u0003\u0010PR\u0016\u0010\u00ab\u0003\u001a\u00020\u000f8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00ab\u0003\u0010UR\u0016\u0010\u00ac\u0003\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00ac\u0003\u0010PR\u0016\u0010\u00ad\u0003\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00ad\u0003\u0010PR\u0016\u0010\u00ae\u0003\u001a\u00020\u000f8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00ae\u0003\u0010UR\u0016\u0010\u00af\u0003\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00af\u0003\u0010PR\u0016\u0010\u00b0\u0003\u001a\u00020\u000f8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00b0\u0003\u0010UR\u0016\u0010\u00b1\u0003\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00b1\u0003\u0010PR\u0016\u0010\u00b2\u0003\u001a\u00020\u000f8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00b2\u0003\u0010UR\u0016\u0010\u00b3\u0003\u001a\u00020h8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00b3\u0003\u0010jR\u0016\u0010\u00b4\u0003\u001a\u00020F8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00b4\u0003\u0010XR\u0016\u0010\u00b5\u0003\u001a\u00020\u000f8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00b5\u0003\u0010UR\u0016\u0010\u00b6\u0003\u001a\u00020\u00198\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00b6\u0003\u0010pR\u0016\u0010\u00b7\u0003\u001a\u00020\u000f8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00b7\u0003\u0010UR\u0016\u0010\u00b8\u0003\u001a\u00020\u00198\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00b8\u0003\u0010pR\u0016\u0010\u00b9\u0003\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00b9\u0003\u0010PR\u0017\u0010\u00ba\u0003\u001a\u00020?8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u00ba\u0003\u0010\u00ce\u0001R\u0016\u0010\u00bb\u0003\u001a\u00020\u000f8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00bb\u0003\u0010UR\u0016\u0010\u00bc\u0003\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00bc\u0003\u0010yR\u0016\u0010\u00bd\u0003\u001a\u00020\u000f8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00bd\u0003\u0010UR\u0016\u0010\u00be\u0003\u001a\u00020\u000f8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00be\u0003\u0010UR\u0016\u0010\u00bf\u0003\u001a\u00020\u000f8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00bf\u0003\u0010UR\u0017\u0010\u00c0\u0003\u001a\u00020\u00078\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u00c0\u0003\u0010\u0097\u0001R\u0017\u0010\u00c1\u0003\u001a\u00020\n8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u00c1\u0003\u0010\u0099\u0001R\u0016\u0010\u00c2\u0003\u001a\u00020\u00198\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00c2\u0003\u0010pR\u0017\u0010\u00c3\u0003\u001a\u0002058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u00c3\u0003\u0010\u00a8\u0001R\u0016\u0010\u00c4\u0003\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00c4\u0003\u0010yR\u0016\u0010\u00c5\u0003\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00c5\u0003\u0010PR\u0016\u0010\u00c6\u0003\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00c6\u0003\u0010PR\u0016\u0010\u00c7\u0003\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00c7\u0003\u0010PR\u0018\u0010\u00c9\u0003\u001a\u00030\u00c8\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u00c9\u0003\u0010\u00ca\u0003R\u0016\u0010\u00cb\u0003\u001a\u00020F8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00cb\u0003\u0010XR\u0016\u0010\u00cc\u0003\u001a\u00020\u000f8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00cc\u0003\u0010UR\u0018\u0010\u00cd\u0003\u001a\u00030\u00a8\u00028\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u00cd\u0003\u0010\u00aa\u0002R\u0016\u0010\u00ce\u0003\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00ce\u0003\u0010PR\u0016\u0010\u00cf\u0003\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00cf\u0003\u0010PR\u0016\u0010\u00d0\u0003\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00d0\u0003\u0010PR\u0016\u0010\u00d1\u0003\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00d1\u0003\u0010PR\u0016\u0010\u00d2\u0003\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00d2\u0003\u0010PR\u0016\u0010\u00d3\u0003\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00d3\u0003\u0010PR\u0016\u0010\u00d4\u0003\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00d4\u0003\u0010PR\u0018\u0010\u00d6\u0003\u001a\u00030\u00d5\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u00d6\u0003\u0010\u00d7\u0003R\u0018\u0010\u00d8\u0003\u001a\u00030\u00d5\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u00d8\u0003\u0010\u00d7\u0003R\u0016\u0010\u00d9\u0003\u001a\u00020F8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00d9\u0003\u0010XR\u0016\u0010\u00da\u0003\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00da\u0003\u0010PR\u0016\u0010\u00db\u0003\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00db\u0003\u0010PR\u0018\u0010\u00dc\u0003\u001a\u00030\u00b3\u00018\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u00dc\u0003\u0010\u00b5\u0001R\u0016\u0010\u00dd\u0003\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00dd\u0003\u0010PR\u0016\u0010\u00de\u0003\u001a\u00020\u000f8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00de\u0003\u0010UR\u0016\u0010\u00df\u0003\u001a\u00020F8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00df\u0003\u0010XR\u0016\u0010\u00e0\u0003\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00e0\u0003\u0010PR\u0016\u0010\u00e1\u0003\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00e1\u0003\u0010PR\u0016\u0010\u00e2\u0003\u001a\u0002028\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00e2\u0003\u0010SR\u0016\u0010\u00e3\u0003\u001a\u00020\u000f8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00e3\u0003\u0010UR\u0017\u0010\u00e4\u0003\u001a\u00020\u001e8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u00e4\u0003\u0010\u0089\u0002R\u0016\u0010\u00e5\u0003\u001a\u0002028\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00e5\u0003\u0010SR\u0016\u0010\u00e6\u0003\u001a\u00020\u000f8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00e6\u0003\u0010UR\u0016\u0010\u00e7\u0003\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00e7\u0003\u0010PR\u0016\u0010\u00e8\u0003\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00e8\u0003\u0010PR\u0016\u0010\u00e9\u0003\u001a\u00020\u000f8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00e9\u0003\u0010UR\u0016\u0010\u00ea\u0003\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00ea\u0003\u0010PR\u0017\u0010\u00eb\u0003\u001a\u00020\u00078\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u00eb\u0003\u0010\u0097\u0001R\u0017\u0010\u00ec\u0003\u001a\u00020\n8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u00ec\u0003\u0010\u0099\u0001R\u0016\u0010\u00ed\u0003\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00ed\u0003\u0010PR\u0017\u0010\u00ee\u0003\u001a\u0002058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u00ee\u0003\u0010\u00a8\u0001R\u0016\u0010\u00ef\u0003\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00ef\u0003\u0010yR\u0016\u0010\u00f0\u0003\u001a\u0002028\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00f0\u0003\u0010SR\u0016\u0010\u00f1\u0003\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00f1\u0003\u0010PR\u0016\u0010\u00f2\u0003\u001a\u00020\u00198\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00f2\u0003\u0010pR\u0016\u0010\u00f3\u0003\u001a\u00020\u00198\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00f3\u0003\u0010pR\u0018\u0010\u00f4\u0003\u001a\u00030\u0095\u00028\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u00f4\u0003\u0010\u0097\u0002R\u0016\u0010\u00f5\u0003\u001a\u00020F8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00f5\u0003\u0010XR\u0018\u0010\u00f6\u0003\u001a\u00030\u00c9\u00018\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u00f6\u0003\u0010\u00cb\u0001R\u0016\u0010\u00f7\u0003\u001a\u00020\u00198\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00f7\u0003\u0010pR\u0016\u0010\u00f8\u0003\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00f8\u0003\u0010yR\u0018\u0010\u00f9\u0003\u001a\u00030\u008c\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u00f9\u0003\u0010\u008e\u0003R\u0016\u0010\u00fa\u0003\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00fa\u0003\u0010PR\u0017\u0010\u00fb\u0003\u001a\u00020?8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u00fb\u0003\u0010\u00ce\u0001R\u0016\u0010\u00fc\u0003\u001a\u00020\u000f8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00fc\u0003\u0010UR\u0016\u0010\u00fd\u0003\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00fd\u0003\u0010PR\u0016\u0010\u00fe\u0003\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00fe\u0003\u0010yR\u0016\u0010\u00ff\u0003\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00ff\u0003\u0010PR\u0016\u0010\u0080\u0004\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u0080\u0004\u0010PR\u0016\u0010\u0081\u0004\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u0081\u0004\u0010PR\u0016\u0010\u0082\u0004\u001a\u00020F8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u0082\u0004\u0010XR\u0016\u0010\u0083\u0004\u001a\u00020\u000f8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u0083\u0004\u0010UR\u0016\u0010\u0084\u0004\u001a\u00020\u000f8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u0084\u0004\u0010UR\u0016\u0010\u0085\u0004\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u0085\u0004\u0010PR\u0016\u0010\u0086\u0004\u001a\u00020F8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u0086\u0004\u0010XR\u0016\u0010\u0087\u0004\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u0087\u0004\u0010PR\u0016\u0010\u0088\u0004\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u0088\u0004\u0010PR\u0016\u0010\u0089\u0004\u001a\u00020\u000f8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u0089\u0004\u0010UR\u0017\u0010\u008a\u0004\u001a\u00020?8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u008a\u0004\u0010\u00ce\u0001R\u0016\u0010\u008b\u0004\u001a\u0002028\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u008b\u0004\u0010SR\u0016\u0010\u008c\u0004\u001a\u0002028\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u008c\u0004\u0010SR\u0016\u0010\u008d\u0004\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u008d\u0004\u0010PR\u0016\u0010\u008e\u0004\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u008e\u0004\u0010PR\u0016\u0010\u008f\u0004\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u008f\u0004\u0010PR\u0016\u0010\u0090\u0004\u001a\u00020\u00198\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u0090\u0004\u0010pR\u0016\u0010\u0091\u0004\u001a\u00020\u000f8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u0091\u0004\u0010UR\u0016\u0010\u0092\u0004\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u0092\u0004\u0010PR\u0016\u0010\u0093\u0004\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u0093\u0004\u0010PR\u0016\u0010\u0094\u0004\u001a\u00020\u000f8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u0094\u0004\u0010UR\u0016\u0010\u0095\u0004\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u0095\u0004\u0010PR\u0018\u0010\u0096\u0004\u001a\u00030\u009f\u00018\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u0096\u0004\u0010\u00a1\u0001R\u0016\u0010\u0097\u0004\u001a\u00020\u00198\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u0097\u0004\u0010pR\u0016\u0010\u0098\u0004\u001a\u00020\u00198\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u0098\u0004\u0010pR\u0016\u0010\u0099\u0004\u001a\u00020F8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u0099\u0004\u0010XR\u0016\u0010\u009a\u0004\u001a\u00020\u00198\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u009a\u0004\u0010pR\u0016\u0010\u009b\u0004\u001a\u00020\u00198\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u009b\u0004\u0010pR\u0016\u0010\u009c\u0004\u001a\u00020\u00198\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u009c\u0004\u0010pR\u0016\u0010\u009d\u0004\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u009d\u0004\u0010PR\u0016\u0010\u009e\u0004\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u009e\u0004\u0010PR\u0016\u0010\u009f\u0004\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u009f\u0004\u0010PR\u0016\u0010\u00a0\u0004\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00a0\u0004\u0010PR\u0016\u0010\u00a1\u0004\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00a1\u0004\u0010PR\u0016\u0010\u00a2\u0004\u001a\u00020\u000f8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00a2\u0004\u0010UR\u0016\u0010\u00a3\u0004\u001a\u00020F8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00a3\u0004\u0010XR\u0016\u0010\u00a4\u0004\u001a\u00020\u000f8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00a4\u0004\u0010UR\u0016\u0010\u00a5\u0004\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00a5\u0004\u0010PR\u0016\u0010\u00a6\u0004\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00a6\u0004\u0010PR\u0016\u0010\u00a7\u0004\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00a7\u0004\u0010PR\u0016\u0010\u00a8\u0004\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00a8\u0004\u0010PR\u0016\u0010\u00a9\u0004\u001a\u00020\u00198\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00a9\u0004\u0010pR\u0016\u0010\u00aa\u0004\u001a\u00020\u00198\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00aa\u0004\u0010pR\u0016\u0010\u00ab\u0004\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00ab\u0004\u0010PR\u0016\u0010\u00ac\u0004\u001a\u00020\u00198\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00ac\u0004\u0010pR\u0018\u0010\u00ad\u0004\u001a\u00030\u0095\u00028\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u00ad\u0004\u0010\u0097\u0002R\u0018\u0010\u00ae\u0004\u001a\u00030\u00a8\u00028\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u00ae\u0004\u0010\u00aa\u0002R\u0017\u0010\u00af\u0004\u001a\u00020?8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u00af\u0004\u0010\u00ce\u0001R\u0016\u0010\u00b0\u0004\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00b0\u0004\u0010PR\u0016\u0010\u00b1\u0004\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00b1\u0004\u0010PR\u0018\u0010\u00b2\u0004\u001a\u00030\u00c9\u00018\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u00b2\u0004\u0010\u00cb\u0001R\u0016\u0010\u00b3\u0004\u001a\u00020\u000f8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00b3\u0004\u0010UR\u0016\u0010\u00b4\u0004\u001a\u00020\u000f8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00b4\u0004\u0010UR\u0016\u0010\u00b5\u0004\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00b5\u0004\u0010PR\u0016\u0010\u00b6\u0004\u001a\u00020\u00198\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00b6\u0004\u0010pR\u0016\u0010\u00b7\u0004\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00b7\u0004\u0010PR\u0016\u0010\u00b8\u0004\u001a\u00020\u00198\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00b8\u0004\u0010pR\u0016\u0010\u00b9\u0004\u001a\u00020F8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00b9\u0004\u0010XR\u0016\u0010\u00ba\u0004\u001a\u0002028\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00ba\u0004\u0010SR\u0016\u0010\u00bb\u0004\u001a\u00020\u000f8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00bb\u0004\u0010UR\u0016\u0010\u00bc\u0004\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00bc\u0004\u0010PR\u0018\u0010\u00bd\u0004\u001a\u00030\u009f\u00018\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u00bd\u0004\u0010\u00a1\u0001R\u0016\u0010\u00be\u0004\u001a\u00020\u00198\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00be\u0004\u0010pR\u0016\u0010\u00bf\u0004\u001a\u00020\u00198\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00bf\u0004\u0010pR\u0016\u0010\u00c0\u0004\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00c0\u0004\u0010PR\u0016\u0010\u00c1\u0004\u001a\u00020F8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00c1\u0004\u0010XR\u0016\u0010\u00c2\u0004\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00c2\u0004\u0010PR\u0016\u0010\u00c3\u0004\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00c3\u0004\u0010PR\u0016\u0010\u00c4\u0004\u001a\u00020F8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00c4\u0004\u0010XR\u0016\u0010\u00c5\u0004\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00c5\u0004\u0010yR\u0018\u0010\u00c6\u0004\u001a\u00030\u00eb\u00028\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u00c6\u0004\u0010\u00ed\u0002R\u0016\u0010\u00c7\u0004\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00c7\u0004\u0010yR\u0016\u0010\u00c8\u0004\u001a\u00020\u000f8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00c8\u0004\u0010UR\u0016\u0010\u00c9\u0004\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00c9\u0004\u0010PR\u0016\u0010\u00ca\u0004\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00ca\u0004\u0010PR\u0016\u0010\u00cb\u0004\u001a\u00020\u00198\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00cb\u0004\u0010pR\u0016\u0010\u00cc\u0004\u001a\u00020\u000f8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00cc\u0004\u0010UR\u0016\u0010\u00cd\u0004\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00cd\u0004\u0010PR\u0016\u0010\u00ce\u0004\u001a\u00020\u000f8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00ce\u0004\u0010UR\u0016\u0010\u00cf\u0004\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00cf\u0004\u0010PR\u0017\u0010\u00d0\u0004\u001a\u00020\u00078\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u00d0\u0004\u0010\u0097\u0001R\u0017\u0010\u00d1\u0004\u001a\u00020\n8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u00d1\u0004\u0010\u0099\u0001R\u0016\u0010\u00d2\u0004\u001a\u00020\u00198\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00d2\u0004\u0010pR\u0016\u0010\u00d3\u0004\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00d3\u0004\u0010PR\u0017\u0010\u00d4\u0004\u001a\u0002058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u00d4\u0004\u0010\u00a8\u0001R\u0016\u0010\u00d5\u0004\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00d5\u0004\u0010yR\u0016\u0010\u00d6\u0004\u001a\u00020\u000f8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00d6\u0004\u0010UR\u0016\u0010\u00d7\u0004\u001a\u00020*8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00d7\u0004\u0010PR\u0018\u0010\u00d9\u0004\u001a\u00030\u00d8\u00048\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u00d9\u0004\u0010\u00da\u0004R\u0018\u0010\u00db\u0004\u001a\u00030\u00d8\u00048\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u00db\u0004\u0010\u00da\u0004R\u0018\u0010\u00dc\u0004\u001a\u00030\u00d8\u00048\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u00dc\u0004\u0010\u00da\u0004R\u0018\u0010\u00dd\u0004\u001a\u00030\u00d8\u00048\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u00dd\u0004\u0010\u00da\u0004R\u0018\u0010\u00de\u0004\u001a\u00030\u00d8\u00048\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u00de\u0004\u0010\u00da\u0004R\u0018\u0010\u00df\u0004\u001a\u00030\u00d8\u00048\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u00df\u0004\u0010\u00da\u0004R\u0016\u0010\u00e0\u0004\u001a\u00020\u000f8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00e0\u0004\u0010UR\u0017\u0010\u00e1\u0004\u001a\u00020\u00078\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u00e1\u0004\u0010\u0097\u0001R\u0017\u0010\u00e2\u0004\u001a\u00020\n8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u00e2\u0004\u0010\u0099\u0001R\u0016\u0010\u00e3\u0004\u001a\u00020\u00198\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00e3\u0004\u0010pR\u0018\u0010\u00e4\u0004\u001a\u00030\u00b3\u00018\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u00e4\u0004\u0010\u00b5\u0001R\"\u0010\u0010\u001a\u000f\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f0\u00e5\u00048\u0002X\u0082\u0004\u00a2\u0006\u0007\n\u0005\b\u0010\u0010\u00e6\u0004R(\u0010\u00e7\u0004\u001a\u000f\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002020\u00e5\u00048\u0006\u00a2\u0006\u000f\n\u0006\b\u00e7\u0004\u0010\u00e6\u0004\u001a\u0005\b\u00e8\u0004\u0010\u0011R\u001e\u0010\u00ea\u0004\u001a\t\u0012\u0004\u0012\u00020F0\u00e9\u00048\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u00ea\u0004\u0010\u00eb\u0004R%\u0010\u00ec\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00028\u0016X\u0096\u0004\u00a2\u0006\u0010\n\u0006\b\u00ec\u0004\u0010\u00ed\u0004\u001a\u0006\b\u00ee\u0004\u0010\u00ef\u0004R+\u0010\u00f0\u0004\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u00020\u00048\u0016X\u0096\u0004\u00a2\u0006\u0010\n\u0006\b\u00f0\u0004\u0010\u00f1\u0004\u001a\u0006\b\u00f2\u0004\u0010\u00f3\u0004\u00a8\u0006\u00f6\u0004"}, d2={"Lcom/cobblemon/mod/common/CobblemonItems;", "Lcom/cobblemon/mod/common/platform/PlatformRegistry;", "Lnet/minecraft/core/Registry;", "Lnet/minecraft/world/item/Item;", "Lnet/minecraft/resources/ResourceKey;", "", "name", "Lcom/cobblemon/mod/common/item/ApricornItem;", "apricornItem", "(Ljava/lang/String;Lcom/cobblemon/mod/common/item/ApricornItem;)Lcom/cobblemon/mod/common/item/ApricornItem;", "Lcom/cobblemon/mod/common/item/ApricornSeedItem;", "apricornSeedItem", "(Ljava/lang/String;Lcom/cobblemon/mod/common/item/ApricornSeedItem;)Lcom/cobblemon/mod/common/item/ApricornSeedItem;", "", "Lnet/minecraft/resources/ResourceLocation;", "Lcom/cobblemon/mod/common/item/BerryItem;", "berries", "()Ljava/util/Map;", "Lcom/cobblemon/mod/common/block/BerryBlock;", "berryBlock", "berryItem", "(Ljava/lang/String;Lcom/cobblemon/mod/common/block/BerryBlock;)Lcom/cobblemon/mod/common/item/BerryItem;", "(Ljava/lang/String;Lcom/cobblemon/mod/common/item/BerryItem;)Lcom/cobblemon/mod/common/item/BerryItem;", "Lnet/minecraft/world/level/block/Block;", "block", "Lnet/minecraft/world/item/BlockItem;", "blockItem", "(Ljava/lang/String;Lnet/minecraft/world/level/block/Block;)Lnet/minecraft/world/item/BlockItem;", "Lcom/cobblemon/mod/common/item/interactive/CandyItem$Calculator;", "calculator", "Lcom/cobblemon/mod/common/item/interactive/CandyItem;", "candyItem", "(Ljava/lang/String;Lcom/cobblemon/mod/common/item/interactive/CandyItem$Calculator;)Lcom/cobblemon/mod/common/item/interactive/CandyItem;", "item", "", "increaseLevelChance", "", "compostable", "(Lnet/minecraft/world/item/Item;F)V", "compostableBlockItem", "(Ljava/lang/String;Lnet/minecraft/world/level/block/Block;F)Lnet/minecraft/world/item/Item;", "remappedName", "Lcom/cobblemon/mod/common/item/CobblemonItem;", "compostableHeldItem", "(Ljava/lang/String;Ljava/lang/String;F)Lcom/cobblemon/mod/common/item/CobblemonItem;", "compostableItem", "(Ljava/lang/String;Lnet/minecraft/world/item/Item;F)Lnet/minecraft/world/item/Item;", "heldItem", "(Ljava/lang/String;Ljava/lang/String;)Lcom/cobblemon/mod/common/item/CobblemonItem;", "(Ljava/lang/String;Lnet/minecraft/world/item/Item;Ljava/lang/String;)Lnet/minecraft/world/item/Item;", "Lcom/cobblemon/mod/common/item/interactive/MintItem;", "mintItem", "(Ljava/lang/String;Lcom/cobblemon/mod/common/item/interactive/MintItem;)Lcom/cobblemon/mod/common/item/interactive/MintItem;", "Lcom/cobblemon/mod/common/item/MintLeafItem;", "mintLeafItem", "mintLeaf", "(Ljava/lang/String;Lcom/cobblemon/mod/common/item/MintLeafItem;)Lcom/cobblemon/mod/common/item/MintLeafItem;", "Lcom/cobblemon/mod/common/block/MintBlock;", "mintBlock", "mintSeed", "(Ljava/lang/String;Lcom/cobblemon/mod/common/block/MintBlock;)Lnet/minecraft/world/item/Item;", "Lcom/cobblemon/mod/common/api/mulch/MulchVariant;", "mulchVariant", "Lcom/cobblemon/mod/common/item/MulchItem;", "mulchItem", "(Ljava/lang/String;Lcom/cobblemon/mod/common/api/mulch/MulchVariant;)Lcom/cobblemon/mod/common/item/MulchItem;", "noSettingsItem", "(Ljava/lang/String;)Lcom/cobblemon/mod/common/item/CobblemonItem;", "Lcom/cobblemon/mod/common/pokeball/PokeBall;", "pokeBall", "Lcom/cobblemon/mod/common/item/PokeBallItem;", "pokeBallItem", "(Lcom/cobblemon/mod/common/pokeball/PokeBall;)Lcom/cobblemon/mod/common/item/PokeBallItem;", "Lcom/cobblemon/mod/common/item/interactive/ability/AbilityChangeItem;", "Lcom/cobblemon/mod/common/api/abilities/CommonAbility;", "ABILITY_CAPSULE", "Lcom/cobblemon/mod/common/item/interactive/ability/AbilityChangeItem;", "Lcom/cobblemon/mod/common/pokemon/abilities/HiddenAbility;", "ABILITY_PATCH", "ABILITY_SHIELD", "Lcom/cobblemon/mod/common/item/CobblemonItem;", "ABSORB_BULB", "ADAMANT_MINT", "Lcom/cobblemon/mod/common/item/interactive/MintItem;", "AGUAV_BERRY", "Lcom/cobblemon/mod/common/item/BerryItem;", "AIR_BALLOON", "ANCIENT_AZURE_BALL", "Lcom/cobblemon/mod/common/item/PokeBallItem;", "ANCIENT_CITRINE_BALL", "ANCIENT_FEATHER_BALL", "ANCIENT_GIGATON_BALL", "ANCIENT_GREAT_BALL", "ANCIENT_HEAVY_BALL", "ANCIENT_IVORY_BALL", "ANCIENT_JET_BALL", "ANCIENT_LEADEN_BALL", "ANCIENT_ORIGIN_BALL", "ANCIENT_POKE_BALL", "ANCIENT_ROSEATE_BALL", "ANCIENT_SLATE_BALL", "ANCIENT_ULTRA_BALL", "ANCIENT_VERDANT_BALL", "ANCIENT_WING_BALL", "Lcom/cobblemon/mod/common/item/interactive/StatusCureItem;", "ANTIDOTE", "Lcom/cobblemon/mod/common/item/interactive/StatusCureItem;", "APICOT_BERRY", "Lcom/cobblemon/mod/common/item/CobblemonBoatItem;", "APRICORN_BOAT", "Lcom/cobblemon/mod/common/item/CobblemonBoatItem;", "APRICORN_BUTTON", "Lnet/minecraft/world/item/BlockItem;", "APRICORN_CHEST_BOAT", "APRICORN_DOOR", "APRICORN_FENCE", "APRICORN_FENCE_GATE", "Lnet/minecraft/world/item/HangingSignItem;", "APRICORN_HANGING_SIGN", "Lnet/minecraft/world/item/HangingSignItem;", "APRICORN_LEAVES", "Lnet/minecraft/world/item/Item;", "APRICORN_LOG", "APRICORN_PLANKS", "APRICORN_PRESSURE_PLATE", "Lnet/minecraft/world/item/SignItem;", "APRICORN_SIGN", "Lnet/minecraft/world/item/SignItem;", "APRICORN_SLAB", "APRICORN_STAIRS", "APRICORN_TRAPDOOR", "APRICORN_WOOD", "ARMOR_FOSSIL", "ASPEAR_BERRY", "ASSAULT_VEST", "AUSPICIOUS_ARMOR", "Lnet/minecraft/world/item/SmithingTemplateItem;", "AUTOMATON_ARMOR_TRIM_SMITHING_TEMPLATE", "Lnet/minecraft/world/item/SmithingTemplateItem;", "AWAKENING", "AZURE_BALL", "BABIRI_BERRY", "BEAST_BALL", "BELUE_BERRY", "Lcom/cobblemon/mod/common/item/interactive/BerryJuiceItem;", "BERRY_JUICE", "Lcom/cobblemon/mod/common/item/interactive/BerryJuiceItem;", "BERRY_SWEET", "BIG_ROOT", "BINDING_BAND", "BLACK_APRICORN", "Lcom/cobblemon/mod/common/item/ApricornItem;", "BLACK_APRICORN_SEED", "Lcom/cobblemon/mod/common/item/ApricornSeedItem;", "BLACK_AUGURITE", "BLACK_BELT", "BLACK_GILDED_CHEST", "BLACK_GLASSES", "BLACK_SLUDGE", "Lcom/cobblemon/mod/common/item/TumblestoneItem;", "BLACK_TUMBLESTONE", "Lcom/cobblemon/mod/common/item/TumblestoneItem;", "BLACK_TUMBLESTONE_BLOCK", "BLACK_TUMBLESTONE_CLUSTER", "BLUE_APRICORN", "BLUE_APRICORN_SEED", "BLUE_GILDED_CHEST", "BLUE_MINT_LEAF", "Lcom/cobblemon/mod/common/item/MintLeafItem;", "BLUE_MINT_SEEDS", "BLUK_BERRY", "BLUNDER_POLICY", "BOLD_MINT", "BRAISED_VIVICHOKE", "BRAVE_MINT", "BRIGHT_POWDER", "BUG_GEM", "BURN_HEAL", "BYGONE_SHERD", "Lcom/cobblemon/mod/common/item/interactive/VitaminItem;", "CALCIUM", "Lcom/cobblemon/mod/common/item/interactive/VitaminItem;", "CALM_MINT", "CAPTURE_SHERD", "CARBOS", "CAREFUL_MINT", "CELL_BATTERY", "CHARCOAL", "CHARTI_BERRY", "CHERISH_BALL", "CHERI_BERRY", "CHESTO_BERRY", "CHILAN_BERRY", "CHIPPED_POT", "CHOICE_BAND", "CHOICE_SCARF", "CHOICE_SPECS", "CHOPLE_BERRY", "CITRINE_BALL", "CLAW_FOSSIL", "CLEANSE_TAG", "Lcom/cobblemon/mod/common/item/interactive/FeatherItem;", "CLEVER_FEATHER", "Lcom/cobblemon/mod/common/item/interactive/FeatherItem;", "CLOVER_SWEET", "COARSE_MULCH", "Lcom/cobblemon/mod/common/item/MulchItem;", "COBA_BERRY", "COLBUR_BERRY", "CORNN_BERRY", "COVERT_CLOAK", "COVER_FOSSIL", "CRACKED_POT", "CUSTAP_BERRY", "CYAN_MINT_LEAF", "CYAN_MINT_SEEDS", "DAMP_ROCK", "DARK_GEM", "DAWN_STONE", "DAWN_STONE_ORE", "DEEPSLATE_DAWN_STONE_ORE", "DEEPSLATE_DUSK_STONE_ORE", "DEEPSLATE_FIRE_STONE_ORE", "DEEPSLATE_ICE_STONE_ORE", "DEEPSLATE_LEAF_STONE_ORE", "DEEPSLATE_MOON_STONE_ORE", "DEEPSLATE_SHINY_STONE_ORE", "DEEPSLATE_SUN_STONE_ORE", "DEEPSLATE_THUNDER_STONE_ORE", "DEEPSLATE_WATER_STONE_ORE", "DEEP_SEA_SCALE", "DEEP_SEA_TOOTH", "DESTINY_KNOT", "Lcom/cobblemon/mod/common/item/battle/DireHitItem;", "DIRE_HIT", "Lcom/cobblemon/mod/common/item/battle/DireHitItem;", "DISPLAY_CASE", "DIVE_BALL", "DOME_FOSSIL", "DOME_SHERD", "DRAGON_FANG", "DRAGON_GEM", "DRAGON_SCALE", "DREAM_BALL", "DRIPSTONE_MOON_STONE_ORE", "DUBIOUS_DISC", "DURIN_BERRY", "DUSK_BALL", "DUSK_STONE", "DUSK_STONE_ORE", "EJECT_BUTTON", "EJECT_PACK", "ELECTIRIZER", "ELECTRIC_GEM", "Lcom/cobblemon/mod/common/item/interactive/ElixirItem;", "ELIXIR", "Lcom/cobblemon/mod/common/item/interactive/ElixirItem;", "ENERGY_ROOT", "ENIGMA_BERRY", "Lcom/cobblemon/mod/common/item/interactive/EtherItem;", "ETHER", "Lcom/cobblemon/mod/common/item/interactive/EtherItem;", "EVERSTONE", "EVIOLITE", "EXPERIENCE_CANDY_L", "Lcom/cobblemon/mod/common/item/interactive/CandyItem;", "EXPERIENCE_CANDY_M", "EXPERIENCE_CANDY_S", "EXPERIENCE_CANDY_XL", "EXPERIENCE_CANDY_XS", "EXPERT_BELT", "EXP_SHARE", "FAIRY_FEATHER", "FAIRY_GEM", "FAST_BALL", "FIGHTING_GEM", "FIGY_BERRY", "Lcom/cobblemon/mod/common/item/interactive/RemedyItem;", "FINE_REMEDY", "Lcom/cobblemon/mod/common/item/interactive/RemedyItem;", "FIRE_GEM", "FIRE_STONE", "FIRE_STONE_ORE", "FLAME_ORB", "FLOAT_STONE", "FLOWER_SWEET", "FLYING_GEM", "FOCUS_BAND", "FOCUS_SASH", "FOSSILIZED_BIRD", "FOSSILIZED_DINO", "FOSSILIZED_DRAKE", "FOSSILIZED_FISH", "FOSSIL_ANALYZER", "FRIEND_BALL", "FULL_HEAL", "Lcom/cobblemon/mod/common/item/interactive/PotionItem;", "FULL_RESTORE", "Lcom/cobblemon/mod/common/item/interactive/PotionItem;", "GALARICA_CUFF", "GALARICA_WREATH", "GANLON_BERRY", "GENIUS_FEATHER", "GENTLE_MINT", "GHOST_GEM", "GILDED_CHEST", "GIMMIGHOUL_CHEST", "GRASS_GEM", "GREAT_BALL", "GREEN_APRICORN", "GREEN_APRICORN_SEED", "GREEN_GILDED_CHEST", "GREEN_MINT_LEAF", "GREEN_MINT_SEEDS", "GREPA_BERRY", "GROUND_GEM", "GROWTH_MULCH", "Lcom/cobblemon/mod/common/item/battle/GuardSpecItem;", "GUARD_SPEC", "Lcom/cobblemon/mod/common/item/battle/GuardSpecItem;", "HABAN_BERRY", "HARD_STONE", "HASTY_MINT", "HEALING_MACHINE", "HEALTH_FEATHER", "HEAL_BALL", "Lcom/cobblemon/mod/common/item/interactive/HealPowderItem;", "HEAL_POWDER", "Lcom/cobblemon/mod/common/item/interactive/HealPowderItem;", "HEAT_ROCK", "HEAVY_BALL", "HEAVY_DUTY_BOOTS", "HELIX_FOSSIL", "HELIX_SHERD", "HONDEW_BERRY", "HOPO_BERRY", "HP_UP", "HUMID_MULCH", "HYPER_POTION", "IAPAPA_BERRY", "ICE_GEM", "ICE_HEAL", "ICE_STONE", "ICE_STONE_ORE", "ICY_ROCK", "IMPISH_MINT", "IRON", "IRON_BALL", "JABOCA_BERRY", "JAW_FOSSIL", "JOLLY_MINT", "KASIB_BERRY", "KEBIA_BERRY", "KEE_BERRY", "KELPSY_BERRY", "KINGS_ROCK", "LANSAT_BERRY", "LARGE_BUDDING_BLACK_TUMBLESTONE", "LARGE_BUDDING_SKY_TUMBLESTONE", "LARGE_BUDDING_TUMBLESTONE", "LAX_MINT", "LEAF_STONE", "LEAF_STONE_ORE", "Lnet/minecraft/world/item/BowlFoodItem;", "LEEK_AND_POTATO_STEW", "Lnet/minecraft/world/item/BowlFoodItem;", "LEFTOVERS", "LEPPA_BERRY", "LEVEL_BALL", "LIECHI_BERRY", "LIFE_ORB", "LIGHT_BALL", "LIGHT_CLAY", "Lcom/cobblemon/mod/common/item/interactive/LinkCableItem;", "LINK_CABLE", "Lcom/cobblemon/mod/common/item/interactive/LinkCableItem;", "LOADED_DICE", "LOAMY_MULCH", "LONELY_MINT", "LOVE_BALL", "LOVE_SWEET", "LUCKY_EGG", "LUM_BERRY", "LURE_BALL", "LUXURY_BALL", "MAGMARIZER", "MAGNET", "MAGOST_BERRY", "MAGO_BERRY", "MALICIOUS_ARMOR", "MARANGA_BERRY", "MASTERPIECE_TEACUP", "MASTER_BALL", "MAX_ELIXIR", "MAX_ETHER", "MAX_POTION", "Lcom/cobblemon/mod/common/item/interactive/ReviveItem;", "MAX_REVIVE", "Lcom/cobblemon/mod/common/item/interactive/ReviveItem;", "MEDICINAL_BREW", "MEDICINAL_LEEK", "MEDIUM_BUDDING_BLACK_TUMBLESTONE", "MEDIUM_BUDDING_SKY_TUMBLESTONE", "MEDIUM_BUDDING_TUMBLESTONE", "MENTAL_HERB", "METAL_COAT", "METAL_POWDER", "MICLE_BERRY", "MILD_MINT", "MIRACLE_SEED", "MIRROR_HERB", "MODEST_MINT", "MONITOR", "MOON_BALL", "MOON_STONE", "MOON_STONE_ORE", "MULCH_BASE", "MUSCLE_BAND", "MUSCLE_FEATHER", "MYSTIC_WATER", "NAIVE_MINT", "NANAB_BERRY", "NAUGHTY_MINT", "NEST_BALL", "NETHER_FIRE_STONE_ORE", "NET_BALL", "NEVER_MELT_ICE", "NOMEL_BERRY", "NORMAL_GEM", "NOSTALGIC_SHERD", "OCCA_BERRY", "OLD_AMBER_FOSSIL", "ORAN_BERRY", "OVAL_STONE", "PAMTRE_BERRY", "PARALYZE_HEAL", "PARK_BALL", "PASSHO_BERRY", "PASTURE", "PAYAPA_BERRY", "PC", "PEAT_BLOCK", "PEAT_MULCH", "PECHA_BERRY", "PEP_UP_FLOWER", "PERSIM_BERRY", "PETAYA_BERRY", "PINAP_BERRY", "PINK_APRICORN", "PINK_APRICORN_SEED", "PINK_GILDED_CHEST", "PINK_MINT_LEAF", "PINK_MINT_SEEDS", "PLUME_FOSSIL", "POISON_BARB", "POISON_GEM", "Lcom/cobblemon/mod/common/item/PokemonItem;", "POKEMON_MODEL", "Lcom/cobblemon/mod/common/item/PokemonItem;", "POKE_BALL", "POMEG_BERRY", "POTION", "POWER_ANKLET", "POWER_BAND", "POWER_BELT", "POWER_BRACER", "POWER_HERB", "POWER_LENS", "POWER_WEIGHT", "Lcom/cobblemon/mod/common/item/interactive/PPUpItem;", "PP_MAX", "Lcom/cobblemon/mod/common/item/interactive/PPUpItem;", "PP_UP", "PREMIER_BALL", "PRISM_SCALE", "PROTECTOR", "PROTEIN", "PSYCHIC_GEM", "QUALOT_BERRY", "QUICK_BALL", "QUICK_CLAW", "QUICK_POWDER", "QUIET_MINT", "RABUTA_BERRY", "RARE_CANDY", "RASH_MINT", "RAWST_BERRY", "RAZOR_CLAW", "RAZOR_FANG", "RAZZ_BERRY", "REAPER_CLOTH", "RED_APRICORN", "RED_APRICORN_SEED", "RED_CARD", "RED_MINT_LEAF", "RED_MINT_SEEDS", "RELAXED_MINT", "RELIC_COIN", "RELIC_COIN_POUCH", "RELIC_COIN_SACK", "REMEDY", "REPEAT_BALL", "RESIST_FEATHER", "RESTORATION_TANK", "REVIVAL_HERB", "REVIVE", "RIBBON_SWEET", "RICH_MULCH", "RINDO_BERRY", "RING_TARGET", "ROASTED_LEEK", "ROCKY_HELMET", "ROCK_GEM", "ROOT_FOSSIL", "ROSEATE_BALL", "ROSELI_BERRY", "ROWAP_BERRY", "SACHET", "SAFARI_BALL", "SAFETY_GOGGLES", "SAIL_FOSSIL", "SALAC_BERRY", "SANDY_MULCH", "SASSY_MINT", "SERIOUS_MINT", "SHARP_BEAK", "SHELL_BELL", "SHINY_STONE", "SHINY_STONE_ORE", "SHUCA_BERRY", "SILK_SCARF", "SILVER_POWDER", "SITRUS_BERRY", "SKULL_FOSSIL", "SKY_TUMBLESTONE", "SKY_TUMBLESTONE_BLOCK", "SKY_TUMBLESTONE_CLUSTER", "SLATE_BALL", "SMALL_BUDDING_BLACK_TUMBLESTONE", "SMALL_BUDDING_SKY_TUMBLESTONE", "SMALL_BUDDING_TUMBLESTONE", "SMOKE_BALL", "SMOOTH_ROCK", "SOFT_SAND", "SOOTHE_BELL", "SPELL_TAG", "SPELON_BERRY", "SPORT_BALL", "STARF_BERRY", "STAR_SWEET", "STEEL_GEM", "STICKY_BARB", "STRAWBERRY_SWEET", "STRIPPED_APRICORN_LOG", "STRIPPED_APRICORN_WOOD", "SUN_STONE", "SUN_STONE_ORE", "SUPERB_REMEDY", "SUPER_POTION", "SURPRISE_MULCH", "SUSPICIOUS_SHERD", "SWEET_APPLE", "SWIFT_FEATHER", "TAMATO_BERRY", "TANGA_BERRY", "TART_APPLE", "TERRACOTTA_SUN_STONE_ORE", "THUNDER_STONE", "THUNDER_STONE_ORE", "TIMER_BALL", "TIMID_MINT", "TOUGA_BERRY", "TOXIC_ORB", "TUMBLESTONE", "TUMBLESTONE_BLOCK", "TUMBLESTONE_CLUSTER", "TWISTED_SPOON", "ULTRA_BALL", "UNREMARKABLE_TEACUP", "UPGRADE", "VERDANT_BALL", "VIVICHOKE", "VIVICHOKE_DIP", "VIVICHOKE_SEEDS", "WACAN_BERRY", "WATER_GEM", "WATER_STONE", "WATER_STONE_ORE", "WATMEL_BERRY", "WEAKNESS_POLICY", "WEPEAR_BERRY", "WHIPPED_DREAM", "WHITE_APRICORN", "WHITE_APRICORN_SEED", "WHITE_GILDED_CHEST", "WHITE_HERB", "WHITE_MINT_LEAF", "WHITE_MINT_SEEDS", "WIKI_BERRY", "WISE_GLASSES", "Lcom/cobblemon/mod/common/item/battle/XStatItem;", "X_ACCURACY", "Lcom/cobblemon/mod/common/item/battle/XStatItem;", "X_ATTACK", "X_DEFENSE", "X_SPEED", "X_SP_ATK", "X_SP_DEF", "YACHE_BERRY", "YELLOW_APRICORN", "YELLOW_APRICORN_SEED", "YELLOW_GILDED_CHEST", "ZINC", "", "Ljava/util/Map;", "mints", "getMints", "", "pokeBalls", "Ljava/util/List;", "registry", "Lnet/minecraft/core/Registry;", "getRegistry", "()Lnet/minecraft/core/Registry;", "registryKey", "Lnet/minecraft/resources/ResourceKey;", "getRegistryKey", "()Lnet/minecraft/resources/ResourceKey;", "<init>", "()V", "common"})
public final class CobblemonItems
extends PlatformRegistry<Registry<Item>, ResourceKey<Registry<Item>>, Item> {
    @NotNull
    public static final CobblemonItems INSTANCE = new CobblemonItems();
    @NotNull
    private static final Registry<Item> registry;
    @NotNull
    private static final ResourceKey<Registry<Item>> registryKey;
    @JvmField
    @NotNull
    public static final List<PokeBallItem> pokeBalls;
    @JvmField
    @NotNull
    public static final PokeBallItem POKE_BALL;
    @JvmField
    @NotNull
    public static final PokeBallItem CITRINE_BALL;
    @JvmField
    @NotNull
    public static final PokeBallItem VERDANT_BALL;
    @JvmField
    @NotNull
    public static final PokeBallItem AZURE_BALL;
    @JvmField
    @NotNull
    public static final PokeBallItem ROSEATE_BALL;
    @JvmField
    @NotNull
    public static final PokeBallItem SLATE_BALL;
    @JvmField
    @NotNull
    public static final PokeBallItem PREMIER_BALL;
    @JvmField
    @NotNull
    public static final PokeBallItem GREAT_BALL;
    @JvmField
    @NotNull
    public static final PokeBallItem ULTRA_BALL;
    @JvmField
    @NotNull
    public static final PokeBallItem SAFARI_BALL;
    @JvmField
    @NotNull
    public static final PokeBallItem FAST_BALL;
    @JvmField
    @NotNull
    public static final PokeBallItem LEVEL_BALL;
    @JvmField
    @NotNull
    public static final PokeBallItem LURE_BALL;
    @JvmField
    @NotNull
    public static final PokeBallItem HEAVY_BALL;
    @JvmField
    @NotNull
    public static final PokeBallItem LOVE_BALL;
    @JvmField
    @NotNull
    public static final PokeBallItem FRIEND_BALL;
    @JvmField
    @NotNull
    public static final PokeBallItem MOON_BALL;
    @JvmField
    @NotNull
    public static final PokeBallItem SPORT_BALL;
    @JvmField
    @NotNull
    public static final PokeBallItem PARK_BALL;
    @JvmField
    @NotNull
    public static final PokeBallItem NET_BALL;
    @JvmField
    @NotNull
    public static final PokeBallItem DIVE_BALL;
    @JvmField
    @NotNull
    public static final PokeBallItem NEST_BALL;
    @JvmField
    @NotNull
    public static final PokeBallItem REPEAT_BALL;
    @JvmField
    @NotNull
    public static final PokeBallItem TIMER_BALL;
    @JvmField
    @NotNull
    public static final PokeBallItem LUXURY_BALL;
    @JvmField
    @NotNull
    public static final PokeBallItem DUSK_BALL;
    @JvmField
    @NotNull
    public static final PokeBallItem HEAL_BALL;
    @JvmField
    @NotNull
    public static final PokeBallItem QUICK_BALL;
    @JvmField
    @NotNull
    public static final PokeBallItem DREAM_BALL;
    @JvmField
    @NotNull
    public static final PokeBallItem BEAST_BALL;
    @JvmField
    @NotNull
    public static final PokeBallItem MASTER_BALL;
    @JvmField
    @NotNull
    public static final PokeBallItem CHERISH_BALL;
    @JvmField
    @NotNull
    public static final PokeBallItem ANCIENT_POKE_BALL;
    @JvmField
    @NotNull
    public static final PokeBallItem ANCIENT_CITRINE_BALL;
    @JvmField
    @NotNull
    public static final PokeBallItem ANCIENT_VERDANT_BALL;
    @JvmField
    @NotNull
    public static final PokeBallItem ANCIENT_AZURE_BALL;
    @JvmField
    @NotNull
    public static final PokeBallItem ANCIENT_ROSEATE_BALL;
    @JvmField
    @NotNull
    public static final PokeBallItem ANCIENT_SLATE_BALL;
    @JvmField
    @NotNull
    public static final PokeBallItem ANCIENT_IVORY_BALL;
    @JvmField
    @NotNull
    public static final PokeBallItem ANCIENT_GREAT_BALL;
    @JvmField
    @NotNull
    public static final PokeBallItem ANCIENT_ULTRA_BALL;
    @JvmField
    @NotNull
    public static final PokeBallItem ANCIENT_FEATHER_BALL;
    @JvmField
    @NotNull
    public static final PokeBallItem ANCIENT_WING_BALL;
    @JvmField
    @NotNull
    public static final PokeBallItem ANCIENT_JET_BALL;
    @JvmField
    @NotNull
    public static final PokeBallItem ANCIENT_HEAVY_BALL;
    @JvmField
    @NotNull
    public static final PokeBallItem ANCIENT_LEADEN_BALL;
    @JvmField
    @NotNull
    public static final PokeBallItem ANCIENT_GIGATON_BALL;
    @JvmField
    @NotNull
    public static final PokeBallItem ANCIENT_ORIGIN_BALL;
    @JvmField
    @NotNull
    public static final Item VIVICHOKE;
    @JvmField
    @NotNull
    public static final Item VIVICHOKE_SEEDS;
    @JvmField
    @NotNull
    public static final ApricornItem RED_APRICORN;
    @JvmField
    @NotNull
    public static final ApricornItem YELLOW_APRICORN;
    @JvmField
    @NotNull
    public static final ApricornItem GREEN_APRICORN;
    @JvmField
    @NotNull
    public static final ApricornItem BLUE_APRICORN;
    @JvmField
    @NotNull
    public static final ApricornItem PINK_APRICORN;
    @JvmField
    @NotNull
    public static final ApricornItem BLACK_APRICORN;
    @JvmField
    @NotNull
    public static final ApricornItem WHITE_APRICORN;
    @JvmField
    @NotNull
    public static final ApricornSeedItem RED_APRICORN_SEED;
    @JvmField
    @NotNull
    public static final ApricornSeedItem YELLOW_APRICORN_SEED;
    @JvmField
    @NotNull
    public static final ApricornSeedItem GREEN_APRICORN_SEED;
    @JvmField
    @NotNull
    public static final ApricornSeedItem BLUE_APRICORN_SEED;
    @JvmField
    @NotNull
    public static final ApricornSeedItem PINK_APRICORN_SEED;
    @JvmField
    @NotNull
    public static final ApricornSeedItem BLACK_APRICORN_SEED;
    @JvmField
    @NotNull
    public static final ApricornSeedItem WHITE_APRICORN_SEED;
    @JvmField
    @NotNull
    public static final BlockItem APRICORN_LOG;
    @JvmField
    @NotNull
    public static final BlockItem STRIPPED_APRICORN_LOG;
    @JvmField
    @NotNull
    public static final BlockItem APRICORN_WOOD;
    @JvmField
    @NotNull
    public static final BlockItem STRIPPED_APRICORN_WOOD;
    @JvmField
    @NotNull
    public static final BlockItem APRICORN_PLANKS;
    @JvmField
    @NotNull
    public static final Item APRICORN_LEAVES;
    @JvmField
    @NotNull
    public static final CobblemonBoatItem APRICORN_BOAT;
    @JvmField
    @NotNull
    public static final CobblemonBoatItem APRICORN_CHEST_BOAT;
    @JvmField
    @NotNull
    public static final BlockItem APRICORN_DOOR;
    @JvmField
    @NotNull
    public static final BlockItem APRICORN_TRAPDOOR;
    @JvmField
    @NotNull
    public static final BlockItem APRICORN_FENCE;
    @JvmField
    @NotNull
    public static final BlockItem APRICORN_FENCE_GATE;
    @JvmField
    @NotNull
    public static final BlockItem APRICORN_BUTTON;
    @JvmField
    @NotNull
    public static final BlockItem APRICORN_PRESSURE_PLATE;
    @JvmField
    @NotNull
    public static final BlockItem APRICORN_SLAB;
    @JvmField
    @NotNull
    public static final BlockItem APRICORN_STAIRS;
    @JvmField
    @NotNull
    public static final SignItem APRICORN_SIGN;
    @JvmField
    @NotNull
    public static final HangingSignItem APRICORN_HANGING_SIGN;
    @JvmField
    @NotNull
    public static final BlockItem GILDED_CHEST;
    @JvmField
    @NotNull
    public static final BlockItem BLUE_GILDED_CHEST;
    @JvmField
    @NotNull
    public static final BlockItem YELLOW_GILDED_CHEST;
    @JvmField
    @NotNull
    public static final BlockItem PINK_GILDED_CHEST;
    @JvmField
    @NotNull
    public static final BlockItem BLACK_GILDED_CHEST;
    @JvmField
    @NotNull
    public static final BlockItem WHITE_GILDED_CHEST;
    @JvmField
    @NotNull
    public static final BlockItem GREEN_GILDED_CHEST;
    @JvmField
    @NotNull
    public static final BlockItem GIMMIGHOUL_CHEST;
    @JvmField
    @NotNull
    public static final BlockItem RESTORATION_TANK;
    @JvmField
    @NotNull
    public static final BlockItem FOSSIL_ANALYZER;
    @JvmField
    @NotNull
    public static final BlockItem MONITOR;
    @JvmField
    @NotNull
    public static final BlockItem HEALING_MACHINE;
    @JvmField
    @NotNull
    public static final BlockItem PC;
    @JvmField
    @NotNull
    public static final BlockItem PASTURE;
    @JvmField
    @NotNull
    public static final BlockItem DISPLAY_CASE;
    @JvmField
    @NotNull
    public static final LinkCableItem LINK_CABLE;
    @JvmField
    @NotNull
    public static final CobblemonItem DRAGON_SCALE;
    @JvmField
    @NotNull
    public static final CobblemonItem KINGS_ROCK;
    @JvmField
    @NotNull
    public static final CobblemonItem METAL_COAT;
    @JvmField
    @NotNull
    public static final CobblemonItem UPGRADE;
    @JvmField
    @NotNull
    public static final CobblemonItem DUBIOUS_DISC;
    @JvmField
    @NotNull
    public static final CobblemonItem DEEP_SEA_SCALE;
    @JvmField
    @NotNull
    public static final CobblemonItem DEEP_SEA_TOOTH;
    @JvmField
    @NotNull
    public static final CobblemonItem ELECTIRIZER;
    @JvmField
    @NotNull
    public static final CobblemonItem MAGMARIZER;
    @JvmField
    @NotNull
    public static final CobblemonItem OVAL_STONE;
    @JvmField
    @NotNull
    public static final CobblemonItem PROTECTOR;
    @JvmField
    @NotNull
    public static final CobblemonItem REAPER_CLOTH;
    @JvmField
    @NotNull
    public static final CobblemonItem PRISM_SCALE;
    @JvmField
    @NotNull
    public static final CobblemonItem SACHET;
    @JvmField
    @NotNull
    public static final CobblemonItem WHIPPED_DREAM;
    @JvmField
    @NotNull
    public static final CobblemonItem STRAWBERRY_SWEET;
    @JvmField
    @NotNull
    public static final CobblemonItem LOVE_SWEET;
    @JvmField
    @NotNull
    public static final CobblemonItem BERRY_SWEET;
    @JvmField
    @NotNull
    public static final CobblemonItem CLOVER_SWEET;
    @JvmField
    @NotNull
    public static final CobblemonItem FLOWER_SWEET;
    @JvmField
    @NotNull
    public static final CobblemonItem STAR_SWEET;
    @JvmField
    @NotNull
    public static final CobblemonItem RIBBON_SWEET;
    @JvmField
    @NotNull
    public static final CobblemonItem CHIPPED_POT;
    @JvmField
    @NotNull
    public static final CobblemonItem CRACKED_POT;
    @JvmField
    @NotNull
    public static final CobblemonItem MASTERPIECE_TEACUP;
    @JvmField
    @NotNull
    public static final CobblemonItem UNREMARKABLE_TEACUP;
    @JvmField
    @NotNull
    public static final CobblemonItem SWEET_APPLE;
    @JvmField
    @NotNull
    public static final CobblemonItem TART_APPLE;
    @JvmField
    @NotNull
    public static final CobblemonItem GALARICA_CUFF;
    @JvmField
    @NotNull
    public static final CobblemonItem GALARICA_WREATH;
    @JvmField
    @NotNull
    public static final CobblemonItem BLACK_AUGURITE;
    @JvmField
    @NotNull
    public static final CobblemonItem PEAT_BLOCK;
    @JvmField
    @NotNull
    public static final CobblemonItem RAZOR_CLAW;
    @JvmField
    @NotNull
    public static final CobblemonItem RAZOR_FANG;
    @JvmField
    @NotNull
    public static final CobblemonItem AUSPICIOUS_ARMOR;
    @JvmField
    @NotNull
    public static final CobblemonItem MALICIOUS_ARMOR;
    @NotNull
    private static final Map<ResourceLocation, BerryItem> berries;
    @JvmField
    @NotNull
    public static final BerryItem ORAN_BERRY;
    @JvmField
    @NotNull
    public static final BerryItem CHERI_BERRY;
    @JvmField
    @NotNull
    public static final BerryItem CHESTO_BERRY;
    @JvmField
    @NotNull
    public static final BerryItem PECHA_BERRY;
    @JvmField
    @NotNull
    public static final BerryItem RAWST_BERRY;
    @JvmField
    @NotNull
    public static final BerryItem ASPEAR_BERRY;
    @JvmField
    @NotNull
    public static final BerryItem PERSIM_BERRY;
    @JvmField
    @NotNull
    public static final BerryItem RAZZ_BERRY;
    @JvmField
    @NotNull
    public static final BerryItem BLUK_BERRY;
    @JvmField
    @NotNull
    public static final BerryItem NANAB_BERRY;
    @JvmField
    @NotNull
    public static final BerryItem WEPEAR_BERRY;
    @JvmField
    @NotNull
    public static final BerryItem PINAP_BERRY;
    @JvmField
    @NotNull
    public static final BerryItem OCCA_BERRY;
    @JvmField
    @NotNull
    public static final BerryItem PASSHO_BERRY;
    @JvmField
    @NotNull
    public static final BerryItem WACAN_BERRY;
    @JvmField
    @NotNull
    public static final BerryItem RINDO_BERRY;
    @JvmField
    @NotNull
    public static final BerryItem YACHE_BERRY;
    @JvmField
    @NotNull
    public static final BerryItem CHOPLE_BERRY;
    @JvmField
    @NotNull
    public static final BerryItem KEBIA_BERRY;
    @JvmField
    @NotNull
    public static final BerryItem SHUCA_BERRY;
    @JvmField
    @NotNull
    public static final BerryItem COBA_BERRY;
    @JvmField
    @NotNull
    public static final BerryItem PAYAPA_BERRY;
    @JvmField
    @NotNull
    public static final BerryItem TANGA_BERRY;
    @JvmField
    @NotNull
    public static final BerryItem CHARTI_BERRY;
    @JvmField
    @NotNull
    public static final BerryItem KASIB_BERRY;
    @JvmField
    @NotNull
    public static final BerryItem HABAN_BERRY;
    @JvmField
    @NotNull
    public static final BerryItem COLBUR_BERRY;
    @JvmField
    @NotNull
    public static final BerryItem BABIRI_BERRY;
    @JvmField
    @NotNull
    public static final BerryItem CHILAN_BERRY;
    @JvmField
    @NotNull
    public static final BerryItem ROSELI_BERRY;
    @JvmField
    @NotNull
    public static final BerryItem LEPPA_BERRY;
    @JvmField
    @NotNull
    public static final BerryItem LUM_BERRY;
    @JvmField
    @NotNull
    public static final BerryItem FIGY_BERRY;
    @JvmField
    @NotNull
    public static final BerryItem WIKI_BERRY;
    @JvmField
    @NotNull
    public static final BerryItem MAGO_BERRY;
    @JvmField
    @NotNull
    public static final BerryItem AGUAV_BERRY;
    @JvmField
    @NotNull
    public static final BerryItem IAPAPA_BERRY;
    @JvmField
    @NotNull
    public static final BerryItem SITRUS_BERRY;
    @JvmField
    @NotNull
    public static final BerryItem TOUGA_BERRY;
    @JvmField
    @NotNull
    public static final BerryItem CORNN_BERRY;
    @JvmField
    @NotNull
    public static final BerryItem MAGOST_BERRY;
    @JvmField
    @NotNull
    public static final BerryItem RABUTA_BERRY;
    @JvmField
    @NotNull
    public static final BerryItem NOMEL_BERRY;
    @JvmField
    @NotNull
    public static final BerryItem ENIGMA_BERRY;
    @JvmField
    @NotNull
    public static final BerryItem POMEG_BERRY;
    @JvmField
    @NotNull
    public static final BerryItem KELPSY_BERRY;
    @JvmField
    @NotNull
    public static final BerryItem QUALOT_BERRY;
    @JvmField
    @NotNull
    public static final BerryItem HONDEW_BERRY;
    @JvmField
    @NotNull
    public static final BerryItem GREPA_BERRY;
    @JvmField
    @NotNull
    public static final BerryItem TAMATO_BERRY;
    @JvmField
    @NotNull
    public static final BerryItem SPELON_BERRY;
    @JvmField
    @NotNull
    public static final BerryItem PAMTRE_BERRY;
    @JvmField
    @NotNull
    public static final BerryItem WATMEL_BERRY;
    @JvmField
    @NotNull
    public static final BerryItem DURIN_BERRY;
    @JvmField
    @NotNull
    public static final BerryItem BELUE_BERRY;
    @JvmField
    @NotNull
    public static final BerryItem KEE_BERRY;
    @JvmField
    @NotNull
    public static final BerryItem MARANGA_BERRY;
    @JvmField
    @NotNull
    public static final BerryItem HOPO_BERRY;
    @JvmField
    @NotNull
    public static final BerryItem LIECHI_BERRY;
    @JvmField
    @NotNull
    public static final BerryItem GANLON_BERRY;
    @JvmField
    @NotNull
    public static final BerryItem SALAC_BERRY;
    @JvmField
    @NotNull
    public static final BerryItem PETAYA_BERRY;
    @JvmField
    @NotNull
    public static final BerryItem APICOT_BERRY;
    @JvmField
    @NotNull
    public static final BerryItem LANSAT_BERRY;
    @JvmField
    @NotNull
    public static final BerryItem STARF_BERRY;
    @JvmField
    @NotNull
    public static final BerryItem MICLE_BERRY;
    @JvmField
    @NotNull
    public static final BerryItem CUSTAP_BERRY;
    @JvmField
    @NotNull
    public static final BerryItem JABOCA_BERRY;
    @JvmField
    @NotNull
    public static final BerryItem ROWAP_BERRY;
    @JvmField
    @NotNull
    public static final BerryJuiceItem BERRY_JUICE;
    @JvmField
    @NotNull
    public static final CandyItem RARE_CANDY;
    @JvmField
    @NotNull
    public static final CandyItem EXPERIENCE_CANDY_XS;
    @JvmField
    @NotNull
    public static final CandyItem EXPERIENCE_CANDY_S;
    @JvmField
    @NotNull
    public static final CandyItem EXPERIENCE_CANDY_M;
    @JvmField
    @NotNull
    public static final CandyItem EXPERIENCE_CANDY_L;
    @JvmField
    @NotNull
    public static final CandyItem EXPERIENCE_CANDY_XL;
    @JvmField
    @NotNull
    public static final VitaminItem CALCIUM;
    @JvmField
    @NotNull
    public static final VitaminItem CARBOS;
    @JvmField
    @NotNull
    public static final VitaminItem HP_UP;
    @JvmField
    @NotNull
    public static final VitaminItem IRON;
    @JvmField
    @NotNull
    public static final VitaminItem PROTEIN;
    @JvmField
    @NotNull
    public static final VitaminItem ZINC;
    @JvmField
    @NotNull
    public static final FeatherItem GENIUS_FEATHER;
    @JvmField
    @NotNull
    public static final FeatherItem SWIFT_FEATHER;
    @JvmField
    @NotNull
    public static final FeatherItem HEALTH_FEATHER;
    @JvmField
    @NotNull
    public static final FeatherItem RESIST_FEATHER;
    @JvmField
    @NotNull
    public static final FeatherItem MUSCLE_FEATHER;
    @JvmField
    @NotNull
    public static final FeatherItem CLEVER_FEATHER;
    @JvmField
    @NotNull
    public static final Item MEDICINAL_LEEK;
    @JvmField
    @NotNull
    public static final Item ROASTED_LEEK;
    @JvmField
    @NotNull
    public static final Item BRAISED_VIVICHOKE;
    @JvmField
    @NotNull
    public static final BowlFoodItem VIVICHOKE_DIP;
    @JvmField
    @NotNull
    public static final Item ENERGY_ROOT;
    @JvmField
    @NotNull
    public static final Item REVIVAL_HERB;
    @JvmField
    @NotNull
    public static final Item PEP_UP_FLOWER;
    @JvmField
    @NotNull
    public static final Item MEDICINAL_BREW;
    @JvmField
    @NotNull
    public static final RemedyItem REMEDY;
    @JvmField
    @NotNull
    public static final RemedyItem FINE_REMEDY;
    @JvmField
    @NotNull
    public static final RemedyItem SUPERB_REMEDY;
    @JvmField
    @NotNull
    public static final PotionItem POTION;
    @JvmField
    @NotNull
    public static final PotionItem SUPER_POTION;
    @JvmField
    @NotNull
    public static final PotionItem HYPER_POTION;
    @JvmField
    @NotNull
    public static final PotionItem MAX_POTION;
    @JvmField
    @NotNull
    public static final PotionItem FULL_RESTORE;
    @JvmField
    @NotNull
    public static final HealPowderItem HEAL_POWDER;
    @JvmField
    @NotNull
    public static final BowlFoodItem LEEK_AND_POTATO_STEW;
    @JvmField
    @NotNull
    public static final ReviveItem REVIVE;
    @JvmField
    @NotNull
    public static final ReviveItem MAX_REVIVE;
    @JvmField
    @NotNull
    public static final PPUpItem PP_UP;
    @JvmField
    @NotNull
    public static final PPUpItem PP_MAX;
    @JvmField
    @NotNull
    public static final Item RED_MINT_SEEDS;
    @JvmField
    @NotNull
    public static final MintLeafItem RED_MINT_LEAF;
    @JvmField
    @NotNull
    public static final Item BLUE_MINT_SEEDS;
    @JvmField
    @NotNull
    public static final MintLeafItem BLUE_MINT_LEAF;
    @JvmField
    @NotNull
    public static final Item CYAN_MINT_SEEDS;
    @JvmField
    @NotNull
    public static final MintLeafItem CYAN_MINT_LEAF;
    @JvmField
    @NotNull
    public static final Item PINK_MINT_SEEDS;
    @JvmField
    @NotNull
    public static final MintLeafItem PINK_MINT_LEAF;
    @JvmField
    @NotNull
    public static final Item GREEN_MINT_SEEDS;
    @JvmField
    @NotNull
    public static final MintLeafItem GREEN_MINT_LEAF;
    @JvmField
    @NotNull
    public static final Item WHITE_MINT_SEEDS;
    @JvmField
    @NotNull
    public static final MintLeafItem WHITE_MINT_LEAF;
    @NotNull
    private static final Map<String, MintItem> mints;
    @JvmField
    @NotNull
    public static final MintItem LONELY_MINT;
    @JvmField
    @NotNull
    public static final MintItem ADAMANT_MINT;
    @JvmField
    @NotNull
    public static final MintItem NAUGHTY_MINT;
    @JvmField
    @NotNull
    public static final MintItem BRAVE_MINT;
    @JvmField
    @NotNull
    public static final MintItem BOLD_MINT;
    @JvmField
    @NotNull
    public static final MintItem IMPISH_MINT;
    @JvmField
    @NotNull
    public static final MintItem LAX_MINT;
    @JvmField
    @NotNull
    public static final MintItem RELAXED_MINT;
    @JvmField
    @NotNull
    public static final MintItem MODEST_MINT;
    @JvmField
    @NotNull
    public static final MintItem MILD_MINT;
    @JvmField
    @NotNull
    public static final MintItem RASH_MINT;
    @JvmField
    @NotNull
    public static final MintItem QUIET_MINT;
    @JvmField
    @NotNull
    public static final MintItem CALM_MINT;
    @JvmField
    @NotNull
    public static final MintItem GENTLE_MINT;
    @JvmField
    @NotNull
    public static final MintItem CAREFUL_MINT;
    @JvmField
    @NotNull
    public static final MintItem SASSY_MINT;
    @JvmField
    @NotNull
    public static final MintItem TIMID_MINT;
    @JvmField
    @NotNull
    public static final MintItem HASTY_MINT;
    @JvmField
    @NotNull
    public static final MintItem JOLLY_MINT;
    @JvmField
    @NotNull
    public static final MintItem NAIVE_MINT;
    @JvmField
    @NotNull
    public static final MintItem SERIOUS_MINT;
    @JvmField
    @NotNull
    public static final XStatItem X_ACCURACY;
    @JvmField
    @NotNull
    public static final XStatItem X_ATTACK;
    @JvmField
    @NotNull
    public static final XStatItem X_DEFENSE;
    @JvmField
    @NotNull
    public static final XStatItem X_SP_ATK;
    @JvmField
    @NotNull
    public static final XStatItem X_SP_DEF;
    @JvmField
    @NotNull
    public static final XStatItem X_SPEED;
    @JvmField
    @NotNull
    public static final DireHitItem DIRE_HIT;
    @JvmField
    @NotNull
    public static final GuardSpecItem GUARD_SPEC;
    @JvmField
    @NotNull
    public static final StatusCureItem BURN_HEAL;
    @JvmField
    @NotNull
    public static final StatusCureItem PARALYZE_HEAL;
    @JvmField
    @NotNull
    public static final StatusCureItem ICE_HEAL;
    @JvmField
    @NotNull
    public static final StatusCureItem ANTIDOTE;
    @JvmField
    @NotNull
    public static final StatusCureItem AWAKENING;
    @JvmField
    @NotNull
    public static final StatusCureItem FULL_HEAL;
    @JvmField
    @NotNull
    public static final EtherItem ETHER;
    @JvmField
    @NotNull
    public static final EtherItem MAX_ETHER;
    @JvmField
    @NotNull
    public static final ElixirItem ELIXIR;
    @JvmField
    @NotNull
    public static final ElixirItem MAX_ELIXIR;
    @JvmField
    @NotNull
    public static final AbilityChangeItem<CommonAbility> ABILITY_CAPSULE;
    @JvmField
    @NotNull
    public static final AbilityChangeItem<HiddenAbility> ABILITY_PATCH;
    @JvmField
    @NotNull
    public static final BlockItem DAWN_STONE_ORE;
    @JvmField
    @NotNull
    public static final BlockItem DUSK_STONE_ORE;
    @JvmField
    @NotNull
    public static final BlockItem FIRE_STONE_ORE;
    @JvmField
    @NotNull
    public static final BlockItem ICE_STONE_ORE;
    @JvmField
    @NotNull
    public static final BlockItem LEAF_STONE_ORE;
    @JvmField
    @NotNull
    public static final BlockItem MOON_STONE_ORE;
    @JvmField
    @NotNull
    public static final BlockItem SHINY_STONE_ORE;
    @JvmField
    @NotNull
    public static final BlockItem SUN_STONE_ORE;
    @JvmField
    @NotNull
    public static final BlockItem TERRACOTTA_SUN_STONE_ORE;
    @JvmField
    @NotNull
    public static final BlockItem THUNDER_STONE_ORE;
    @JvmField
    @NotNull
    public static final BlockItem WATER_STONE_ORE;
    @JvmField
    @NotNull
    public static final BlockItem DEEPSLATE_DAWN_STONE_ORE;
    @JvmField
    @NotNull
    public static final BlockItem DEEPSLATE_DUSK_STONE_ORE;
    @JvmField
    @NotNull
    public static final BlockItem DEEPSLATE_FIRE_STONE_ORE;
    @JvmField
    @NotNull
    public static final BlockItem DEEPSLATE_ICE_STONE_ORE;
    @JvmField
    @NotNull
    public static final BlockItem DEEPSLATE_LEAF_STONE_ORE;
    @JvmField
    @NotNull
    public static final BlockItem DEEPSLATE_MOON_STONE_ORE;
    @JvmField
    @NotNull
    public static final BlockItem DEEPSLATE_SHINY_STONE_ORE;
    @JvmField
    @NotNull
    public static final BlockItem DEEPSLATE_SUN_STONE_ORE;
    @JvmField
    @NotNull
    public static final BlockItem DEEPSLATE_THUNDER_STONE_ORE;
    @JvmField
    @NotNull
    public static final BlockItem DEEPSLATE_WATER_STONE_ORE;
    @JvmField
    @NotNull
    public static final BlockItem DRIPSTONE_MOON_STONE_ORE;
    @JvmField
    @NotNull
    public static final BlockItem NETHER_FIRE_STONE_ORE;
    @JvmField
    @NotNull
    public static final CobblemonItem DAWN_STONE;
    @JvmField
    @NotNull
    public static final CobblemonItem DUSK_STONE;
    @JvmField
    @NotNull
    public static final CobblemonItem FIRE_STONE;
    @JvmField
    @NotNull
    public static final CobblemonItem ICE_STONE;
    @JvmField
    @NotNull
    public static final CobblemonItem LEAF_STONE;
    @JvmField
    @NotNull
    public static final CobblemonItem MOON_STONE;
    @JvmField
    @NotNull
    public static final CobblemonItem SHINY_STONE;
    @JvmField
    @NotNull
    public static final CobblemonItem SUN_STONE;
    @JvmField
    @NotNull
    public static final CobblemonItem THUNDER_STONE;
    @JvmField
    @NotNull
    public static final CobblemonItem WATER_STONE;
    @JvmField
    @NotNull
    public static final CobblemonItem ABILITY_SHIELD;
    @JvmField
    @NotNull
    public static final CobblemonItem ABSORB_BULB;
    @JvmField
    @NotNull
    public static final CobblemonItem AIR_BALLOON;
    @JvmField
    @NotNull
    public static final CobblemonItem ASSAULT_VEST;
    @JvmField
    @NotNull
    public static final BlockItem BIG_ROOT;
    @JvmField
    @NotNull
    public static final CobblemonItem BINDING_BAND;
    @JvmField
    @NotNull
    public static final CobblemonItem BLACK_BELT;
    @JvmField
    @NotNull
    public static final CobblemonItem BLACK_GLASSES;
    @JvmField
    @NotNull
    public static final CobblemonItem BLACK_SLUDGE;
    @JvmField
    @NotNull
    public static final CobblemonItem BLUNDER_POLICY;
    @JvmField
    @NotNull
    public static final CobblemonItem CELL_BATTERY;
    @JvmField
    @NotNull
    public static final CobblemonItem CHARCOAL;
    @JvmField
    @NotNull
    public static final CobblemonItem CHOICE_BAND;
    @JvmField
    @NotNull
    public static final CobblemonItem CHOICE_SCARF;
    @JvmField
    @NotNull
    public static final CobblemonItem CHOICE_SPECS;
    @JvmField
    @NotNull
    public static final CobblemonItem CLEANSE_TAG;
    @JvmField
    @NotNull
    public static final CobblemonItem COVERT_CLOAK;
    @JvmField
    @NotNull
    public static final CobblemonItem DESTINY_KNOT;
    @JvmField
    @NotNull
    public static final CobblemonItem DRAGON_FANG;
    @JvmField
    @NotNull
    public static final CobblemonItem EJECT_BUTTON;
    @JvmField
    @NotNull
    public static final CobblemonItem EJECT_PACK;
    @JvmField
    @NotNull
    public static final CobblemonItem EVERSTONE;
    @JvmField
    @NotNull
    public static final CobblemonItem EVIOLITE;
    @JvmField
    @NotNull
    public static final CobblemonItem EXP_SHARE;
    @JvmField
    @NotNull
    public static final CobblemonItem EXPERT_BELT;
    @JvmField
    @NotNull
    public static final CobblemonItem FAIRY_FEATHER;
    @JvmField
    @NotNull
    public static final CobblemonItem FLAME_ORB;
    @JvmField
    @NotNull
    public static final CobblemonItem FLOAT_STONE;
    @JvmField
    @NotNull
    public static final CobblemonItem FOCUS_BAND;
    @JvmField
    @NotNull
    public static final CobblemonItem FOCUS_SASH;
    @JvmField
    @NotNull
    public static final CobblemonItem HARD_STONE;
    @JvmField
    @NotNull
    public static final CobblemonItem HEAVY_DUTY_BOOTS;
    @JvmField
    @NotNull
    public static final CobblemonItem IRON_BALL;
    @JvmField
    @NotNull
    public static final CobblemonItem LEFTOVERS;
    @JvmField
    @NotNull
    public static final CobblemonItem LIFE_ORB;
    @JvmField
    @NotNull
    public static final CobblemonItem LIGHT_BALL;
    @JvmField
    @NotNull
    public static final CobblemonItem LIGHT_CLAY;
    @JvmField
    @NotNull
    public static final CobblemonItem LOADED_DICE;
    @JvmField
    @NotNull
    public static final CobblemonItem LUCKY_EGG;
    @JvmField
    @NotNull
    public static final CobblemonItem MAGNET;
    @JvmField
    @NotNull
    public static final CobblemonItem MIRACLE_SEED;
    @JvmField
    @NotNull
    public static final CobblemonItem MUSCLE_BAND;
    @JvmField
    @NotNull
    public static final CobblemonItem MYSTIC_WATER;
    @JvmField
    @NotNull
    public static final CobblemonItem NEVER_MELT_ICE;
    @JvmField
    @NotNull
    public static final CobblemonItem POISON_BARB;
    @JvmField
    @NotNull
    public static final CobblemonItem POWER_ANKLET;
    @JvmField
    @NotNull
    public static final CobblemonItem POWER_BAND;
    @JvmField
    @NotNull
    public static final CobblemonItem POWER_BELT;
    @JvmField
    @NotNull
    public static final CobblemonItem POWER_BRACER;
    @JvmField
    @NotNull
    public static final CobblemonItem POWER_LENS;
    @JvmField
    @NotNull
    public static final CobblemonItem POWER_WEIGHT;
    @JvmField
    @NotNull
    public static final CobblemonItem QUICK_CLAW;
    @JvmField
    @NotNull
    public static final CobblemonItem RED_CARD;
    @JvmField
    @NotNull
    public static final CobblemonItem RING_TARGET;
    @JvmField
    @NotNull
    public static final CobblemonItem ROCKY_HELMET;
    @JvmField
    @NotNull
    public static final CobblemonItem SAFETY_GOGGLES;
    @JvmField
    @NotNull
    public static final CobblemonItem SHARP_BEAK;
    @JvmField
    @NotNull
    public static final CobblemonItem SHELL_BELL;
    @JvmField
    @NotNull
    public static final CobblemonItem SILK_SCARF;
    @JvmField
    @NotNull
    public static final CobblemonItem SILVER_POWDER;
    @JvmField
    @NotNull
    public static final CobblemonItem SOFT_SAND;
    @JvmField
    @NotNull
    public static final CobblemonItem SPELL_TAG;
    @JvmField
    @NotNull
    public static final CobblemonItem SMOKE_BALL;
    @JvmField
    @NotNull
    public static final CobblemonItem SOOTHE_BELL;
    @JvmField
    @NotNull
    public static final CobblemonItem STICKY_BARB;
    @JvmField
    @NotNull
    public static final CobblemonItem TOXIC_ORB;
    @JvmField
    @NotNull
    public static final CobblemonItem TWISTED_SPOON;
    @JvmField
    @NotNull
    public static final CobblemonItem WEAKNESS_POLICY;
    @JvmField
    @NotNull
    public static final CobblemonItem WISE_GLASSES;
    @JvmField
    @NotNull
    public static final CobblemonItem MENTAL_HERB;
    @JvmField
    @NotNull
    public static final CobblemonItem MIRROR_HERB;
    @JvmField
    @NotNull
    public static final CobblemonItem POWER_HERB;
    @JvmField
    @NotNull
    public static final CobblemonItem WHITE_HERB;
    @JvmField
    @NotNull
    public static final CobblemonItem BRIGHT_POWDER;
    @JvmField
    @NotNull
    public static final CobblemonItem METAL_POWDER;
    @JvmField
    @NotNull
    public static final CobblemonItem QUICK_POWDER;
    @JvmField
    @NotNull
    public static final CobblemonItem DAMP_ROCK;
    @JvmField
    @NotNull
    public static final CobblemonItem HEAT_ROCK;
    @JvmField
    @NotNull
    public static final CobblemonItem SMOOTH_ROCK;
    @JvmField
    @NotNull
    public static final CobblemonItem ICY_ROCK;
    @JvmField
    @NotNull
    public static final CobblemonItem MULCH_BASE;
    @JvmField
    @NotNull
    public static final MulchItem COARSE_MULCH;
    @JvmField
    @NotNull
    public static final MulchItem GROWTH_MULCH;
    @JvmField
    @NotNull
    public static final MulchItem HUMID_MULCH;
    @JvmField
    @NotNull
    public static final MulchItem LOAMY_MULCH;
    @JvmField
    @NotNull
    public static final MulchItem PEAT_MULCH;
    @JvmField
    @NotNull
    public static final MulchItem RICH_MULCH;
    @JvmField
    @NotNull
    public static final MulchItem SANDY_MULCH;
    @JvmField
    @NotNull
    public static final MulchItem SURPRISE_MULCH;
    @JvmField
    @NotNull
    public static final CobblemonItem ARMOR_FOSSIL;
    @JvmField
    @NotNull
    public static final CobblemonItem FOSSILIZED_BIRD;
    @JvmField
    @NotNull
    public static final CobblemonItem CLAW_FOSSIL;
    @JvmField
    @NotNull
    public static final CobblemonItem COVER_FOSSIL;
    @JvmField
    @NotNull
    public static final CobblemonItem FOSSILIZED_DINO;
    @JvmField
    @NotNull
    public static final CobblemonItem DOME_FOSSIL;
    @JvmField
    @NotNull
    public static final CobblemonItem FOSSILIZED_DRAKE;
    @JvmField
    @NotNull
    public static final CobblemonItem FOSSILIZED_FISH;
    @JvmField
    @NotNull
    public static final CobblemonItem HELIX_FOSSIL;
    @JvmField
    @NotNull
    public static final CobblemonItem JAW_FOSSIL;
    @JvmField
    @NotNull
    public static final CobblemonItem OLD_AMBER_FOSSIL;
    @JvmField
    @NotNull
    public static final CobblemonItem PLUME_FOSSIL;
    @JvmField
    @NotNull
    public static final CobblemonItem ROOT_FOSSIL;
    @JvmField
    @NotNull
    public static final CobblemonItem SAIL_FOSSIL;
    @JvmField
    @NotNull
    public static final CobblemonItem SKULL_FOSSIL;
    @JvmField
    @NotNull
    public static final CobblemonItem BYGONE_SHERD;
    @JvmField
    @NotNull
    public static final CobblemonItem CAPTURE_SHERD;
    @JvmField
    @NotNull
    public static final CobblemonItem DOME_SHERD;
    @JvmField
    @NotNull
    public static final CobblemonItem HELIX_SHERD;
    @JvmField
    @NotNull
    public static final CobblemonItem NOSTALGIC_SHERD;
    @JvmField
    @NotNull
    public static final CobblemonItem SUSPICIOUS_SHERD;
    @JvmField
    @NotNull
    public static final TumblestoneItem TUMBLESTONE;
    @JvmField
    @NotNull
    public static final TumblestoneItem BLACK_TUMBLESTONE;
    @JvmField
    @NotNull
    public static final TumblestoneItem SKY_TUMBLESTONE;
    @JvmField
    @NotNull
    public static final BlockItem SMALL_BUDDING_TUMBLESTONE;
    @JvmField
    @NotNull
    public static final BlockItem MEDIUM_BUDDING_TUMBLESTONE;
    @JvmField
    @NotNull
    public static final BlockItem LARGE_BUDDING_TUMBLESTONE;
    @JvmField
    @NotNull
    public static final BlockItem TUMBLESTONE_CLUSTER;
    @JvmField
    @NotNull
    public static final BlockItem SMALL_BUDDING_SKY_TUMBLESTONE;
    @JvmField
    @NotNull
    public static final BlockItem MEDIUM_BUDDING_SKY_TUMBLESTONE;
    @JvmField
    @NotNull
    public static final BlockItem LARGE_BUDDING_SKY_TUMBLESTONE;
    @JvmField
    @NotNull
    public static final BlockItem SKY_TUMBLESTONE_CLUSTER;
    @JvmField
    @NotNull
    public static final BlockItem SMALL_BUDDING_BLACK_TUMBLESTONE;
    @JvmField
    @NotNull
    public static final BlockItem MEDIUM_BUDDING_BLACK_TUMBLESTONE;
    @JvmField
    @NotNull
    public static final BlockItem LARGE_BUDDING_BLACK_TUMBLESTONE;
    @JvmField
    @NotNull
    public static final BlockItem BLACK_TUMBLESTONE_CLUSTER;
    @JvmField
    @NotNull
    public static final BlockItem TUMBLESTONE_BLOCK;
    @JvmField
    @NotNull
    public static final BlockItem SKY_TUMBLESTONE_BLOCK;
    @JvmField
    @NotNull
    public static final BlockItem BLACK_TUMBLESTONE_BLOCK;
    @JvmField
    @NotNull
    public static final SmithingTemplateItem AUTOMATON_ARMOR_TRIM_SMITHING_TEMPLATE;
    @JvmField
    @NotNull
    public static final PokemonItem POKEMON_MODEL;
    @JvmField
    @NotNull
    public static final CobblemonItem RELIC_COIN;
    @JvmField
    @NotNull
    public static final BlockItem RELIC_COIN_POUCH;
    @JvmField
    @NotNull
    public static final BlockItem RELIC_COIN_SACK;
    @JvmField
    @NotNull
    public static final CobblemonItem NORMAL_GEM;
    @JvmField
    @NotNull
    public static final CobblemonItem FIRE_GEM;
    @JvmField
    @NotNull
    public static final CobblemonItem WATER_GEM;
    @JvmField
    @NotNull
    public static final CobblemonItem GRASS_GEM;
    @JvmField
    @NotNull
    public static final CobblemonItem ELECTRIC_GEM;
    @JvmField
    @NotNull
    public static final CobblemonItem ICE_GEM;
    @JvmField
    @NotNull
    public static final CobblemonItem FIGHTING_GEM;
    @JvmField
    @NotNull
    public static final CobblemonItem POISON_GEM;
    @JvmField
    @NotNull
    public static final CobblemonItem GROUND_GEM;
    @JvmField
    @NotNull
    public static final CobblemonItem FLYING_GEM;
    @JvmField
    @NotNull
    public static final CobblemonItem PSYCHIC_GEM;
    @JvmField
    @NotNull
    public static final CobblemonItem BUG_GEM;
    @JvmField
    @NotNull
    public static final CobblemonItem ROCK_GEM;
    @JvmField
    @NotNull
    public static final CobblemonItem GHOST_GEM;
    @JvmField
    @NotNull
    public static final CobblemonItem DRAGON_GEM;
    @JvmField
    @NotNull
    public static final CobblemonItem DARK_GEM;
    @JvmField
    @NotNull
    public static final CobblemonItem STEEL_GEM;
    @JvmField
    @NotNull
    public static final CobblemonItem FAIRY_GEM;

    private CobblemonItems() {
    }

    @Override
    @NotNull
    public Registry<Item> getRegistry() {
        return registry;
    }

    @Override
    @NotNull
    public ResourceKey<Registry<Item>> getRegistryKey() {
        return registryKey;
    }

    @NotNull
    public final Map<String, MintItem> getMints() {
        return mints;
    }

    private final BlockItem blockItem(String name, Block block) {
        return this.create(name, new BlockItem(block, new Item.Properties()));
    }

    private final CobblemonItem noSettingsItem(String name) {
        return this.create(name, new CobblemonItem(new Item.Properties()));
    }

    @NotNull
    public final Map<ResourceLocation, BerryItem> berries() {
        return MapsKt.toMap(berries);
    }

    private final MulchItem mulchItem(String name, MulchVariant mulchVariant) {
        return this.create(name, new MulchItem(mulchVariant));
    }

    private final PokeBallItem pokeBallItem(PokeBall pokeBall) {
        String string = pokeBall.getName().m_135815_();
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"pokeBall.name.path");
        PokeBallItem item = this.create(string, new PokeBallItem(pokeBall));
        pokeBall.setItem$common(item);
        pokeBalls.add(item);
        return item;
    }

    private final CandyItem candyItem(String name, CandyItem.Calculator calculator) {
        return this.create(name, new CandyItem(calculator));
    }

    /*
     * WARNING - void declaration
     */
    private final CobblemonItem heldItem(String name, String remappedName) {
        CobblemonItem cobblemonItem;
        CobblemonItem cobblemonItem2 = cobblemonItem = new CobblemonItem(new Item.Properties());
        String string = name;
        CobblemonItems cobblemonItems = this;
        boolean bl = false;
        if (remappedName != null) {
            void it;
            CobblemonHeldItemManager.INSTANCE.registerRemap((Item)it, remappedName);
        }
        Unit unit = Unit.INSTANCE;
        return cobblemonItems.create(string, cobblemonItem);
    }

    static /* synthetic */ CobblemonItem heldItem$default(CobblemonItems cobblemonItems, String string, String string2, int n, Object object) {
        if ((n & 2) != 0) {
            string2 = null;
        }
        return cobblemonItems.heldItem(string, string2);
    }

    /*
     * WARNING - void declaration
     */
    private final Item heldItem(String name, Item item, String remappedName) {
        Item item2;
        Item item3 = item2 = item;
        String string = name;
        CobblemonItems cobblemonItems = this;
        boolean bl = false;
        if (remappedName != null) {
            void it;
            CobblemonHeldItemManager.INSTANCE.registerRemap((Item)it, remappedName);
            Item item4 = Items.f_42500_;
            Intrinsics.checkNotNullExpressionValue((Object)item4, (String)"BONE");
            CobblemonHeldItemManager.INSTANCE.registerRemap(item4, "thickclub");
            Item item5 = Items.f_42452_;
            Intrinsics.checkNotNullExpressionValue((Object)item5, (String)"SNOWBALL");
            CobblemonHeldItemManager.INSTANCE.registerRemap(item5, "snowball");
        }
        Unit unit = Unit.INSTANCE;
        return cobblemonItems.create(string, item2);
    }

    static /* synthetic */ Item heldItem$default(CobblemonItems cobblemonItems, String string, Item item, String string2, int n, Object object) {
        if ((n & 4) != 0) {
            string2 = null;
        }
        return cobblemonItems.heldItem(string, item, string2);
    }

    private final void compostable(Item item, float increaseLevelChance) {
        Cobblemon.INSTANCE.getImplementation().registerCompostable((ItemLike)item, increaseLevelChance);
    }

    private final BerryItem berryItem(String name, BerryBlock berryBlock) {
        String finalName = name + "_berry";
        BerryItem item = this.create(finalName, new BerryItem(berryBlock));
        this.compostable((Item)item, 0.65f);
        berries.put(MiscUtils.cobblemonResource(finalName), item);
        return item;
    }

    private final BerryItem berryItem(String name, BerryItem berryItem) {
        String finalName = name + "_berry";
        BerryItem item = this.create(finalName, berryItem);
        this.compostable((Item)item, 0.65f);
        berries.put(MiscUtils.cobblemonResource(finalName), item);
        return item;
    }

    private final MintItem mintItem(String name, MintItem mintItem) {
        MintItem item = this.create(name, mintItem);
        mints.put(item.getNature().getDisplayName(), item);
        this.compostable(item, 0.65f);
        return item;
    }

    private final ApricornItem apricornItem(String name, ApricornItem apricornItem) {
        String finalName = name + "_apricorn";
        ApricornItem item = this.create(finalName, apricornItem);
        this.compostable((Item)item, 0.65f);
        return item;
    }

    private final ApricornSeedItem apricornSeedItem(String name, ApricornSeedItem apricornSeedItem) {
        String finalName = name + "_apricorn_seed";
        ApricornSeedItem item = this.create(finalName, apricornSeedItem);
        this.compostable((Item)item, 0.65f);
        return item;
    }

    private final Item mintSeed(String name, MintBlock mintBlock) {
        String finalName = name + "_mint_seeds";
        BlockItem item = this.blockItem(finalName, (Block)mintBlock);
        this.compostable((Item)item, 0.65f);
        return (Item)item;
    }

    private final MintLeafItem mintLeaf(String name, MintLeafItem mintLeafItem) {
        String finalName = name + "_mint_leaf";
        MintLeafItem item = this.create(finalName, mintLeafItem);
        this.compostable(item, 0.65f);
        return item;
    }

    private final Item compostableItem(String name, Item item, float increaseLevelChance) {
        Item item2 = item;
        if (item2 == null) {
            item2 = new Item(new Item.Properties());
        }
        Item createdItem = this.create(name, item2);
        this.compostable(createdItem, increaseLevelChance);
        return createdItem;
    }

    static /* synthetic */ Item compostableItem$default(CobblemonItems cobblemonItems, String string, Item item, float f, int n, Object object) {
        if ((n & 2) != 0) {
            item = null;
        }
        if ((n & 4) != 0) {
            f = 0.65f;
        }
        return cobblemonItems.compostableItem(string, item, f);
    }

    private final CobblemonItem compostableHeldItem(String name, String remappedName, float increaseLevelChance) {
        CobblemonItem createdItem = this.heldItem(name, remappedName);
        this.compostable(createdItem, increaseLevelChance);
        return createdItem;
    }

    static /* synthetic */ CobblemonItem compostableHeldItem$default(CobblemonItems cobblemonItems, String string, String string2, float f, int n, Object object) {
        if ((n & 2) != 0) {
            string2 = null;
        }
        if ((n & 4) != 0) {
            f = 0.65f;
        }
        return cobblemonItems.compostableHeldItem(string, string2, f);
    }

    private final Item compostableBlockItem(String name, Block block, float increaseLevelChance) {
        BlockItem createdItem = this.blockItem(name, block);
        this.compostable((Item)createdItem, increaseLevelChance);
        return (Item)createdItem;
    }

    static /* synthetic */ Item compostableBlockItem$default(CobblemonItems cobblemonItems, String string, Block block, float f, int n, Object object) {
        if ((n & 4) != 0) {
            f = 0.65f;
        }
        return cobblemonItems.compostableBlockItem(string, block, f);
    }

    private static final int RARE_CANDY$lambda$0(ServerPlayer serverPlayer, Pokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)serverPlayer, (String)"<anonymous parameter 0>");
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        return pokemon.getExperienceToNextLevel();
    }

    private static final int EXPERIENCE_CANDY_XS$lambda$1(ServerPlayer serverPlayer, Pokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)serverPlayer, (String)"<anonymous parameter 0>");
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"<anonymous parameter 1>");
        return 100;
    }

    private static final int EXPERIENCE_CANDY_S$lambda$2(ServerPlayer serverPlayer, Pokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)serverPlayer, (String)"<anonymous parameter 0>");
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"<anonymous parameter 1>");
        return 800;
    }

    private static final int EXPERIENCE_CANDY_M$lambda$3(ServerPlayer serverPlayer, Pokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)serverPlayer, (String)"<anonymous parameter 0>");
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"<anonymous parameter 1>");
        return 3000;
    }

    private static final int EXPERIENCE_CANDY_L$lambda$4(ServerPlayer serverPlayer, Pokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)serverPlayer, (String)"<anonymous parameter 0>");
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"<anonymous parameter 1>");
        return 10000;
    }

    private static final int EXPERIENCE_CANDY_XL$lambda$5(ServerPlayer serverPlayer, Pokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)serverPlayer, (String)"<anonymous parameter 0>");
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"<anonymous parameter 1>");
        return 30000;
    }

    static {
        DefaultedRegistry defaultedRegistry = BuiltInRegistries.f_257033_;
        Intrinsics.checkNotNullExpressionValue((Object)defaultedRegistry, (String)"ITEM");
        registry = (Registry)defaultedRegistry;
        ResourceKey resourceKey = Registries.f_256913_;
        Intrinsics.checkNotNullExpressionValue((Object)resourceKey, (String)"ITEM");
        registryKey = resourceKey;
        pokeBalls = new ArrayList();
        POKE_BALL = INSTANCE.pokeBallItem(PokeBalls.INSTANCE.getPOKE_BALL());
        CITRINE_BALL = INSTANCE.pokeBallItem(PokeBalls.INSTANCE.getCITRINE_BALL());
        VERDANT_BALL = INSTANCE.pokeBallItem(PokeBalls.INSTANCE.getVERDANT_BALL());
        AZURE_BALL = INSTANCE.pokeBallItem(PokeBalls.INSTANCE.getAZURE_BALL());
        ROSEATE_BALL = INSTANCE.pokeBallItem(PokeBalls.INSTANCE.getROSEATE_BALL());
        SLATE_BALL = INSTANCE.pokeBallItem(PokeBalls.INSTANCE.getSLATE_BALL());
        PREMIER_BALL = INSTANCE.pokeBallItem(PokeBalls.INSTANCE.getPREMIER_BALL());
        GREAT_BALL = INSTANCE.pokeBallItem(PokeBalls.INSTANCE.getGREAT_BALL());
        ULTRA_BALL = INSTANCE.pokeBallItem(PokeBalls.INSTANCE.getULTRA_BALL());
        SAFARI_BALL = INSTANCE.pokeBallItem(PokeBalls.INSTANCE.getSAFARI_BALL());
        FAST_BALL = INSTANCE.pokeBallItem(PokeBalls.INSTANCE.getFAST_BALL());
        LEVEL_BALL = INSTANCE.pokeBallItem(PokeBalls.INSTANCE.getLEVEL_BALL());
        LURE_BALL = INSTANCE.pokeBallItem(PokeBalls.INSTANCE.getLURE_BALL());
        HEAVY_BALL = INSTANCE.pokeBallItem(PokeBalls.INSTANCE.getHEAVY_BALL());
        LOVE_BALL = INSTANCE.pokeBallItem(PokeBalls.INSTANCE.getLOVE_BALL());
        FRIEND_BALL = INSTANCE.pokeBallItem(PokeBalls.INSTANCE.getFRIEND_BALL());
        MOON_BALL = INSTANCE.pokeBallItem(PokeBalls.INSTANCE.getMOON_BALL());
        SPORT_BALL = INSTANCE.pokeBallItem(PokeBalls.INSTANCE.getSPORT_BALL());
        PARK_BALL = INSTANCE.pokeBallItem(PokeBalls.INSTANCE.getPARK_BALL());
        NET_BALL = INSTANCE.pokeBallItem(PokeBalls.INSTANCE.getNET_BALL());
        DIVE_BALL = INSTANCE.pokeBallItem(PokeBalls.INSTANCE.getDIVE_BALL());
        NEST_BALL = INSTANCE.pokeBallItem(PokeBalls.INSTANCE.getNEST_BALL());
        REPEAT_BALL = INSTANCE.pokeBallItem(PokeBalls.INSTANCE.getREPEAT_BALL());
        TIMER_BALL = INSTANCE.pokeBallItem(PokeBalls.INSTANCE.getTIMER_BALL());
        LUXURY_BALL = INSTANCE.pokeBallItem(PokeBalls.INSTANCE.getLUXURY_BALL());
        DUSK_BALL = INSTANCE.pokeBallItem(PokeBalls.INSTANCE.getDUSK_BALL());
        HEAL_BALL = INSTANCE.pokeBallItem(PokeBalls.INSTANCE.getHEAL_BALL());
        QUICK_BALL = INSTANCE.pokeBallItem(PokeBalls.INSTANCE.getQUICK_BALL());
        DREAM_BALL = INSTANCE.pokeBallItem(PokeBalls.INSTANCE.getDREAM_BALL());
        BEAST_BALL = INSTANCE.pokeBallItem(PokeBalls.INSTANCE.getBEAST_BALL());
        MASTER_BALL = INSTANCE.pokeBallItem(PokeBalls.INSTANCE.getMASTER_BALL());
        CHERISH_BALL = INSTANCE.pokeBallItem(PokeBalls.INSTANCE.getCHERISH_BALL());
        ANCIENT_POKE_BALL = INSTANCE.pokeBallItem(PokeBalls.INSTANCE.getANCIENT_POKE_BALL());
        ANCIENT_CITRINE_BALL = INSTANCE.pokeBallItem(PokeBalls.INSTANCE.getANCIENT_CITRINE_BALL());
        ANCIENT_VERDANT_BALL = INSTANCE.pokeBallItem(PokeBalls.INSTANCE.getANCIENT_VERDANT_BALL());
        ANCIENT_AZURE_BALL = INSTANCE.pokeBallItem(PokeBalls.INSTANCE.getANCIENT_AZURE_BALL());
        ANCIENT_ROSEATE_BALL = INSTANCE.pokeBallItem(PokeBalls.INSTANCE.getANCIENT_ROSEATE_BALL());
        ANCIENT_SLATE_BALL = INSTANCE.pokeBallItem(PokeBalls.INSTANCE.getANCIENT_SLATE_BALL());
        ANCIENT_IVORY_BALL = INSTANCE.pokeBallItem(PokeBalls.INSTANCE.getANCIENT_IVORY_BALL());
        ANCIENT_GREAT_BALL = INSTANCE.pokeBallItem(PokeBalls.INSTANCE.getANCIENT_GREAT_BALL());
        ANCIENT_ULTRA_BALL = INSTANCE.pokeBallItem(PokeBalls.INSTANCE.getANCIENT_ULTRA_BALL());
        ANCIENT_FEATHER_BALL = INSTANCE.pokeBallItem(PokeBalls.INSTANCE.getANCIENT_FEATHER_BALL());
        ANCIENT_WING_BALL = INSTANCE.pokeBallItem(PokeBalls.INSTANCE.getANCIENT_WING_BALL());
        ANCIENT_JET_BALL = INSTANCE.pokeBallItem(PokeBalls.INSTANCE.getANCIENT_JET_BALL());
        ANCIENT_HEAVY_BALL = INSTANCE.pokeBallItem(PokeBalls.INSTANCE.getANCIENT_HEAVY_BALL());
        ANCIENT_LEADEN_BALL = INSTANCE.pokeBallItem(PokeBalls.INSTANCE.getANCIENT_LEADEN_BALL());
        ANCIENT_GIGATON_BALL = INSTANCE.pokeBallItem(PokeBalls.INSTANCE.getANCIENT_GIGATON_BALL());
        ANCIENT_ORIGIN_BALL = INSTANCE.pokeBallItem(PokeBalls.INSTANCE.getANCIENT_ORIGIN_BALL());
        VIVICHOKE = CobblemonItems.compostableItem$default(INSTANCE, "vivichoke", null, 0.0f, 6, null);
        VIVICHOKE_SEEDS = CobblemonItems.compostableItem$default(INSTANCE, "vivichoke_seeds", (Item)new VivichokeItem(CobblemonBlocks.VIVICHOKE_SEEDS), 0.0f, 4, null);
        RED_APRICORN = INSTANCE.apricornItem("red", new ApricornItem(CobblemonBlocks.RED_APRICORN));
        YELLOW_APRICORN = INSTANCE.apricornItem("yellow", new ApricornItem(CobblemonBlocks.YELLOW_APRICORN));
        GREEN_APRICORN = INSTANCE.apricornItem("green", new ApricornItem(CobblemonBlocks.GREEN_APRICORN));
        BLUE_APRICORN = INSTANCE.apricornItem("blue", new ApricornItem(CobblemonBlocks.BLUE_APRICORN));
        PINK_APRICORN = INSTANCE.apricornItem("pink", new ApricornItem(CobblemonBlocks.PINK_APRICORN));
        BLACK_APRICORN = INSTANCE.apricornItem("black", new ApricornItem(CobblemonBlocks.BLACK_APRICORN));
        WHITE_APRICORN = INSTANCE.apricornItem("white", new ApricornItem(CobblemonBlocks.WHITE_APRICORN));
        RED_APRICORN_SEED = INSTANCE.apricornSeedItem("red", new ApricornSeedItem(CobblemonBlocks.RED_APRICORN_SAPLING, CobblemonBlocks.RED_APRICORN));
        YELLOW_APRICORN_SEED = INSTANCE.apricornSeedItem("yellow", new ApricornSeedItem(CobblemonBlocks.YELLOW_APRICORN_SAPLING, CobblemonBlocks.YELLOW_APRICORN));
        GREEN_APRICORN_SEED = INSTANCE.apricornSeedItem("green", new ApricornSeedItem(CobblemonBlocks.GREEN_APRICORN_SAPLING, CobblemonBlocks.GREEN_APRICORN));
        BLUE_APRICORN_SEED = INSTANCE.apricornSeedItem("blue", new ApricornSeedItem(CobblemonBlocks.BLUE_APRICORN_SAPLING, CobblemonBlocks.BLUE_APRICORN));
        PINK_APRICORN_SEED = INSTANCE.apricornSeedItem("pink", new ApricornSeedItem(CobblemonBlocks.PINK_APRICORN_SAPLING, CobblemonBlocks.PINK_APRICORN));
        BLACK_APRICORN_SEED = INSTANCE.apricornSeedItem("black", new ApricornSeedItem(CobblemonBlocks.BLACK_APRICORN_SAPLING, CobblemonBlocks.BLACK_APRICORN));
        WHITE_APRICORN_SEED = INSTANCE.apricornSeedItem("white", new ApricornSeedItem(CobblemonBlocks.WHITE_APRICORN_SAPLING, CobblemonBlocks.WHITE_APRICORN));
        APRICORN_LOG = INSTANCE.blockItem("apricorn_log", (Block)CobblemonBlocks.APRICORN_LOG);
        STRIPPED_APRICORN_LOG = INSTANCE.blockItem("stripped_apricorn_log", (Block)CobblemonBlocks.STRIPPED_APRICORN_LOG);
        APRICORN_WOOD = INSTANCE.blockItem("apricorn_wood", (Block)CobblemonBlocks.APRICORN_WOOD);
        STRIPPED_APRICORN_WOOD = INSTANCE.blockItem("stripped_apricorn_wood", (Block)CobblemonBlocks.STRIPPED_APRICORN_WOOD);
        APRICORN_PLANKS = INSTANCE.blockItem("apricorn_planks", CobblemonBlocks.APRICORN_PLANKS);
        APRICORN_LEAVES = CobblemonItems.compostableBlockItem$default(INSTANCE, "apricorn_leaves", (Block)CobblemonBlocks.APRICORN_LEAVES, 0.0f, 4, null);
        Item.Properties properties2 = new Item.Properties().m_41487_(1);
        Intrinsics.checkNotNullExpressionValue((Object)properties2, (String)"Settings().maxCount(1)");
        APRICORN_BOAT = INSTANCE.create("apricorn_boat", new CobblemonBoatItem(CobblemonBoatType.APRICORN, false, properties2));
        Item.Properties properties3 = new Item.Properties().m_41487_(1);
        Intrinsics.checkNotNullExpressionValue((Object)properties3, (String)"Settings().maxCount(1)");
        APRICORN_CHEST_BOAT = INSTANCE.create("apricorn_chest_boat", new CobblemonBoatItem(CobblemonBoatType.APRICORN, true, properties3));
        DoorBlock doorBlock = CobblemonBlocks.APRICORN_DOOR;
        Intrinsics.checkNotNullExpressionValue((Object)doorBlock, (String)"APRICORN_DOOR");
        APRICORN_DOOR = INSTANCE.blockItem("apricorn_door", (Block)doorBlock);
        TrapDoorBlock trapDoorBlock = CobblemonBlocks.APRICORN_TRAPDOOR;
        Intrinsics.checkNotNullExpressionValue((Object)trapDoorBlock, (String)"APRICORN_TRAPDOOR");
        APRICORN_TRAPDOOR = INSTANCE.blockItem("apricorn_trapdoor", (Block)trapDoorBlock);
        APRICORN_FENCE = INSTANCE.blockItem("apricorn_fence", (Block)CobblemonBlocks.APRICORN_FENCE);
        APRICORN_FENCE_GATE = INSTANCE.blockItem("apricorn_fence_gate", (Block)CobblemonBlocks.APRICORN_FENCE_GATE);
        ButtonBlock buttonBlock = CobblemonBlocks.APRICORN_BUTTON;
        Intrinsics.checkNotNullExpressionValue((Object)buttonBlock, (String)"APRICORN_BUTTON");
        APRICORN_BUTTON = INSTANCE.blockItem("apricorn_button", (Block)buttonBlock);
        PressurePlateBlock pressurePlateBlock = CobblemonBlocks.APRICORN_PRESSURE_PLATE;
        Intrinsics.checkNotNullExpressionValue((Object)pressurePlateBlock, (String)"APRICORN_PRESSURE_PLATE");
        APRICORN_PRESSURE_PLATE = INSTANCE.blockItem("apricorn_pressure_plate", (Block)pressurePlateBlock);
        APRICORN_SLAB = INSTANCE.blockItem("apricorn_slab", (Block)CobblemonBlocks.APRICORN_SLAB);
        StairBlock stairBlock = CobblemonBlocks.APRICORN_STAIRS;
        Intrinsics.checkNotNullExpressionValue((Object)stairBlock, (String)"APRICORN_STAIRS");
        APRICORN_STAIRS = INSTANCE.blockItem("apricorn_stairs", (Block)stairBlock);
        APRICORN_SIGN = INSTANCE.create("apricorn_sign", new SignItem(new Item.Properties().m_41487_(16), (Block)CobblemonBlocks.APRICORN_SIGN, (Block)CobblemonBlocks.APRICORN_WALL_SIGN));
        APRICORN_HANGING_SIGN = INSTANCE.create("apricorn_hanging_sign", new HangingSignItem((Block)CobblemonBlocks.APRICORN_HANGING_SIGN, (Block)CobblemonBlocks.APRICORN_WALL_HANGING_SIGN, new Item.Properties().m_41487_(16)));
        GILDED_CHEST = INSTANCE.create("gilded_chest", new BlockItem((Block)CobblemonBlocks.GILDED_CHEST, new Item.Properties()));
        BLUE_GILDED_CHEST = INSTANCE.create("blue_gilded_chest", new BlockItem((Block)CobblemonBlocks.BLUE_GILDED_CHEST, new Item.Properties()));
        YELLOW_GILDED_CHEST = INSTANCE.create("yellow_gilded_chest", new BlockItem((Block)CobblemonBlocks.YELLOW_GILDED_CHEST, new Item.Properties()));
        PINK_GILDED_CHEST = INSTANCE.create("pink_gilded_chest", new BlockItem((Block)CobblemonBlocks.PINK_GILDED_CHEST, new Item.Properties()));
        BLACK_GILDED_CHEST = INSTANCE.create("black_gilded_chest", new BlockItem((Block)CobblemonBlocks.BLACK_GILDED_CHEST, new Item.Properties()));
        WHITE_GILDED_CHEST = INSTANCE.create("white_gilded_chest", new BlockItem((Block)CobblemonBlocks.WHITE_GILDED_CHEST, new Item.Properties()));
        GREEN_GILDED_CHEST = INSTANCE.create("green_gilded_chest", new BlockItem((Block)CobblemonBlocks.GREEN_GILDED_CHEST, new Item.Properties()));
        GIMMIGHOUL_CHEST = INSTANCE.create("gimmighoul_chest", new BlockItem((Block)CobblemonBlocks.GIMMIGHOUL_CHEST, new Item.Properties()));
        RESTORATION_TANK = INSTANCE.blockItem("restoration_tank", (Block)CobblemonBlocks.RESTORATION_TANK);
        FOSSIL_ANALYZER = INSTANCE.blockItem("fossil_analyzer", (Block)CobblemonBlocks.FOSSIL_ANALYZER);
        MONITOR = INSTANCE.blockItem("monitor", (Block)CobblemonBlocks.MONITOR);
        HEALING_MACHINE = INSTANCE.blockItem("healing_machine", (Block)CobblemonBlocks.HEALING_MACHINE);
        PC = INSTANCE.blockItem("pc", (Block)CobblemonBlocks.PC);
        PASTURE = INSTANCE.blockItem("pasture", (Block)CobblemonBlocks.PASTURE);
        DISPLAY_CASE = INSTANCE.blockItem("display_case", (Block)CobblemonBlocks.DISPLAY_CASE);
        LINK_CABLE = INSTANCE.create("link_cable", new LinkCableItem());
        DRAGON_SCALE = INSTANCE.noSettingsItem("dragon_scale");
        KINGS_ROCK = INSTANCE.noSettingsItem("kings_rock");
        METAL_COAT = INSTANCE.noSettingsItem("metal_coat");
        UPGRADE = INSTANCE.noSettingsItem("upgrade");
        DUBIOUS_DISC = INSTANCE.noSettingsItem("dubious_disc");
        DEEP_SEA_SCALE = INSTANCE.noSettingsItem("deep_sea_scale");
        DEEP_SEA_TOOTH = INSTANCE.noSettingsItem("deep_sea_tooth");
        ELECTIRIZER = INSTANCE.noSettingsItem("electirizer");
        MAGMARIZER = INSTANCE.noSettingsItem("magmarizer");
        OVAL_STONE = INSTANCE.noSettingsItem("oval_stone");
        PROTECTOR = INSTANCE.noSettingsItem("protector");
        REAPER_CLOTH = INSTANCE.noSettingsItem("reaper_cloth");
        PRISM_SCALE = INSTANCE.noSettingsItem("prism_scale");
        SACHET = INSTANCE.noSettingsItem("sachet");
        WHIPPED_DREAM = INSTANCE.noSettingsItem("whipped_dream");
        STRAWBERRY_SWEET = INSTANCE.noSettingsItem("strawberry_sweet");
        LOVE_SWEET = INSTANCE.noSettingsItem("love_sweet");
        BERRY_SWEET = INSTANCE.noSettingsItem("berry_sweet");
        CLOVER_SWEET = INSTANCE.noSettingsItem("clover_sweet");
        FLOWER_SWEET = INSTANCE.noSettingsItem("flower_sweet");
        STAR_SWEET = INSTANCE.noSettingsItem("star_sweet");
        RIBBON_SWEET = INSTANCE.noSettingsItem("ribbon_sweet");
        CHIPPED_POT = INSTANCE.noSettingsItem("chipped_pot");
        CRACKED_POT = INSTANCE.noSettingsItem("cracked_pot");
        MASTERPIECE_TEACUP = INSTANCE.noSettingsItem("masterpiece_teacup");
        UNREMARKABLE_TEACUP = INSTANCE.noSettingsItem("unremarkable_teacup");
        SWEET_APPLE = INSTANCE.noSettingsItem("sweet_apple");
        TART_APPLE = INSTANCE.noSettingsItem("tart_apple");
        GALARICA_CUFF = INSTANCE.noSettingsItem("galarica_cuff");
        GALARICA_WREATH = INSTANCE.noSettingsItem("galarica_wreath");
        BLACK_AUGURITE = INSTANCE.noSettingsItem("black_augurite");
        PEAT_BLOCK = INSTANCE.noSettingsItem("peat_block");
        RAZOR_CLAW = INSTANCE.noSettingsItem("razor_claw");
        RAZOR_FANG = INSTANCE.noSettingsItem("razor_fang");
        AUSPICIOUS_ARMOR = CobblemonItems.heldItem$default(INSTANCE, "auspicious_armor", null, 2, null);
        MALICIOUS_ARMOR = CobblemonItems.heldItem$default(INSTANCE, "malicious_armor", null, 2, null);
        berries = new LinkedHashMap();
        ORAN_BERRY = INSTANCE.berryItem("oran", new HealingBerryItem(CobblemonBlocks.INSTANCE.getORAN_BERRY(), (Function0<? extends Expression>)((Function0)ORAN_BERRY.1.INSTANCE)));
        Status[] statusArray = new Status[]{Statuses.INSTANCE.getPARALYSIS()};
        CHERI_BERRY = INSTANCE.berryItem("cheri", new StatusCuringBerryItem(CobblemonBlocks.INSTANCE.getCHERI_BERRY(), statusArray));
        statusArray = new Status[]{Statuses.INSTANCE.getSLEEP()};
        CHESTO_BERRY = INSTANCE.berryItem("chesto", new StatusCuringBerryItem(CobblemonBlocks.INSTANCE.getCHESTO_BERRY(), statusArray));
        statusArray = new Status[]{Statuses.INSTANCE.getPOISON(), Statuses.INSTANCE.getPOISON_BADLY()};
        PECHA_BERRY = INSTANCE.berryItem("pecha", new StatusCuringBerryItem(CobblemonBlocks.INSTANCE.getPECHA_BERRY(), statusArray));
        statusArray = new Status[]{Statuses.INSTANCE.getBURN()};
        RAWST_BERRY = INSTANCE.berryItem("rawst", new StatusCuringBerryItem(CobblemonBlocks.INSTANCE.getRAWST_BERRY(), statusArray));
        statusArray = new Status[]{Statuses.INSTANCE.getFROZEN()};
        ASPEAR_BERRY = INSTANCE.berryItem("aspear", new StatusCuringBerryItem(CobblemonBlocks.INSTANCE.getASPEAR_BERRY(), statusArray));
        PERSIM_BERRY = INSTANCE.berryItem("persim", new VolatileCuringBerryItem(CobblemonBlocks.INSTANCE.getPERSIM_BERRY(), "confusion"));
        RAZZ_BERRY = INSTANCE.berryItem("razz", CobblemonBlocks.INSTANCE.getRAZZ_BERRY());
        BLUK_BERRY = INSTANCE.berryItem("bluk", CobblemonBlocks.INSTANCE.getBLUK_BERRY());
        NANAB_BERRY = INSTANCE.berryItem("nanab", CobblemonBlocks.INSTANCE.getNANAB_BERRY());
        WEPEAR_BERRY = INSTANCE.berryItem("wepear", CobblemonBlocks.INSTANCE.getWEPEAR_BERRY());
        PINAP_BERRY = INSTANCE.berryItem("pinap", CobblemonBlocks.INSTANCE.getPINAP_BERRY());
        OCCA_BERRY = INSTANCE.berryItem("occa", CobblemonBlocks.INSTANCE.getOCCA_BERRY());
        PASSHO_BERRY = INSTANCE.berryItem("passho", CobblemonBlocks.INSTANCE.getPASSHO_BERRY());
        WACAN_BERRY = INSTANCE.berryItem("wacan", CobblemonBlocks.INSTANCE.getWACAN_BERRY());
        RINDO_BERRY = INSTANCE.berryItem("rindo", CobblemonBlocks.INSTANCE.getRINDO_BERRY());
        YACHE_BERRY = INSTANCE.berryItem("yache", CobblemonBlocks.INSTANCE.getYACHE_BERRY());
        CHOPLE_BERRY = INSTANCE.berryItem("chople", CobblemonBlocks.INSTANCE.getCHOPLE_BERRY());
        KEBIA_BERRY = INSTANCE.berryItem("kebia", CobblemonBlocks.INSTANCE.getKEBIA_BERRY());
        SHUCA_BERRY = INSTANCE.berryItem("shuca", CobblemonBlocks.INSTANCE.getSHUCA_BERRY());
        COBA_BERRY = INSTANCE.berryItem("coba", CobblemonBlocks.INSTANCE.getCOBA_BERRY());
        PAYAPA_BERRY = INSTANCE.berryItem("payapa", CobblemonBlocks.INSTANCE.getPAYAPA_BERRY());
        TANGA_BERRY = INSTANCE.berryItem("tanga", CobblemonBlocks.INSTANCE.getTANGA_BERRY());
        CHARTI_BERRY = INSTANCE.berryItem("charti", CobblemonBlocks.INSTANCE.getCHARTI_BERRY());
        KASIB_BERRY = INSTANCE.berryItem("kasib", CobblemonBlocks.INSTANCE.getKASIB_BERRY());
        HABAN_BERRY = INSTANCE.berryItem("haban", CobblemonBlocks.INSTANCE.getHABAN_BERRY());
        COLBUR_BERRY = INSTANCE.berryItem("colbur", CobblemonBlocks.INSTANCE.getCOLBUR_BERRY());
        BABIRI_BERRY = INSTANCE.berryItem("babiri", CobblemonBlocks.INSTANCE.getBABIRI_BERRY());
        CHILAN_BERRY = INSTANCE.berryItem("chilan", CobblemonBlocks.INSTANCE.getCHILAN_BERRY());
        ROSELI_BERRY = INSTANCE.berryItem("roseli", CobblemonBlocks.INSTANCE.getROSELI_BERRY());
        LEPPA_BERRY = INSTANCE.berryItem("leppa", new PPRestoringBerryItem(CobblemonBlocks.INSTANCE.getLEPPA_BERRY(), (Function0<? extends Expression>)((Function0)LEPPA_BERRY.1.INSTANCE)));
        LUM_BERRY = INSTANCE.berryItem("lum", new StatusCuringBerryItem(CobblemonBlocks.INSTANCE.getLUM_BERRY(), new Status[0]));
        FIGY_BERRY = INSTANCE.berryItem("figy", new PortionHealingBerryItem(CobblemonBlocks.INSTANCE.getFIGY_BERRY(), true, (Function0<? extends Expression>)((Function0)FIGY_BERRY.1.INSTANCE)));
        WIKI_BERRY = INSTANCE.berryItem("wiki", new PortionHealingBerryItem(CobblemonBlocks.INSTANCE.getWIKI_BERRY(), true, (Function0<? extends Expression>)((Function0)WIKI_BERRY.1.INSTANCE)));
        MAGO_BERRY = INSTANCE.berryItem("mago", new PortionHealingBerryItem(CobblemonBlocks.INSTANCE.getMAGO_BERRY(), true, (Function0<? extends Expression>)((Function0)MAGO_BERRY.1.INSTANCE)));
        AGUAV_BERRY = INSTANCE.berryItem("aguav", new PortionHealingBerryItem(CobblemonBlocks.INSTANCE.getAGUAV_BERRY(), true, (Function0<? extends Expression>)((Function0)AGUAV_BERRY.1.INSTANCE)));
        IAPAPA_BERRY = INSTANCE.berryItem("iapapa", new PortionHealingBerryItem(CobblemonBlocks.INSTANCE.getIAPAPA_BERRY(), true, (Function0<? extends Expression>)((Function0)IAPAPA_BERRY.1.INSTANCE)));
        SITRUS_BERRY = INSTANCE.berryItem("sitrus", new HealingBerryItem(CobblemonBlocks.INSTANCE.getSITRUS_BERRY(), (Function0<? extends Expression>)((Function0)SITRUS_BERRY.1.INSTANCE)));
        TOUGA_BERRY = INSTANCE.berryItem("touga", CobblemonBlocks.INSTANCE.getTOUGA_BERRY());
        CORNN_BERRY = INSTANCE.berryItem("cornn", CobblemonBlocks.INSTANCE.getCORNN_BERRY());
        MAGOST_BERRY = INSTANCE.berryItem("magost", CobblemonBlocks.INSTANCE.getMAGOST_BERRY());
        RABUTA_BERRY = INSTANCE.berryItem("rabuta", CobblemonBlocks.INSTANCE.getRABUTA_BERRY());
        NOMEL_BERRY = INSTANCE.berryItem("nomel", CobblemonBlocks.INSTANCE.getNOMEL_BERRY());
        ENIGMA_BERRY = INSTANCE.berryItem("enigma", CobblemonBlocks.INSTANCE.getENIGMA_BERRY());
        POMEG_BERRY = INSTANCE.berryItem("pomeg", new FriendshipRaisingBerryItem(CobblemonBlocks.INSTANCE.getPOMEG_BERRY(), Stats.HP));
        KELPSY_BERRY = INSTANCE.berryItem("kelpsy", new FriendshipRaisingBerryItem(CobblemonBlocks.INSTANCE.getKELPSY_BERRY(), Stats.ATTACK));
        QUALOT_BERRY = INSTANCE.berryItem("qualot", new FriendshipRaisingBerryItem(CobblemonBlocks.INSTANCE.getQUALOT_BERRY(), Stats.DEFENCE));
        HONDEW_BERRY = INSTANCE.berryItem("hondew", new FriendshipRaisingBerryItem(CobblemonBlocks.INSTANCE.getHONDEW_BERRY(), Stats.SPECIAL_ATTACK));
        GREPA_BERRY = INSTANCE.berryItem("grepa", new FriendshipRaisingBerryItem(CobblemonBlocks.INSTANCE.getGREPA_BERRY(), Stats.SPECIAL_DEFENCE));
        TAMATO_BERRY = INSTANCE.berryItem("tamato", new FriendshipRaisingBerryItem(CobblemonBlocks.INSTANCE.getTAMATO_BERRY(), Stats.SPEED));
        SPELON_BERRY = INSTANCE.berryItem("spelon", CobblemonBlocks.INSTANCE.getSPELON_BERRY());
        PAMTRE_BERRY = INSTANCE.berryItem("pamtre", CobblemonBlocks.INSTANCE.getPAMTRE_BERRY());
        WATMEL_BERRY = INSTANCE.berryItem("watmel", CobblemonBlocks.INSTANCE.getWATMEL_BERRY());
        DURIN_BERRY = INSTANCE.berryItem("durin", CobblemonBlocks.INSTANCE.getDURIN_BERRY());
        BELUE_BERRY = INSTANCE.berryItem("belue", CobblemonBlocks.INSTANCE.getBELUE_BERRY());
        KEE_BERRY = INSTANCE.berryItem("kee", CobblemonBlocks.INSTANCE.getKEE_BERRY());
        MARANGA_BERRY = INSTANCE.berryItem("maranga", CobblemonBlocks.INSTANCE.getMARANGA_BERRY());
        HOPO_BERRY = INSTANCE.berryItem("hopo", new PPRestoringBerryItem(CobblemonBlocks.INSTANCE.getHOPO_BERRY(), (Function0<? extends Expression>)((Function0)HOPO_BERRY.1.INSTANCE)));
        LIECHI_BERRY = INSTANCE.berryItem("liechi", CobblemonBlocks.INSTANCE.getLIECHI_BERRY());
        GANLON_BERRY = INSTANCE.berryItem("ganlon", CobblemonBlocks.INSTANCE.getGANLON_BERRY());
        SALAC_BERRY = INSTANCE.berryItem("salac", CobblemonBlocks.INSTANCE.getSALAC_BERRY());
        PETAYA_BERRY = INSTANCE.berryItem("petaya", CobblemonBlocks.INSTANCE.getPETAYA_BERRY());
        APICOT_BERRY = INSTANCE.berryItem("apicot", CobblemonBlocks.INSTANCE.getAPICOT_BERRY());
        LANSAT_BERRY = INSTANCE.berryItem("lansat", CobblemonBlocks.INSTANCE.getLANSAT_BERRY());
        STARF_BERRY = INSTANCE.berryItem("starf", CobblemonBlocks.INSTANCE.getSTARF_BERRY());
        MICLE_BERRY = INSTANCE.berryItem("micle", CobblemonBlocks.INSTANCE.getMICLE_BERRY());
        CUSTAP_BERRY = INSTANCE.berryItem("custap", CobblemonBlocks.INSTANCE.getCUSTAP_BERRY());
        JABOCA_BERRY = INSTANCE.berryItem("jaboca", CobblemonBlocks.INSTANCE.getJABOCA_BERRY());
        ROWAP_BERRY = INSTANCE.berryItem("rowap", CobblemonBlocks.INSTANCE.getROWAP_BERRY());
        BERRY_JUICE = INSTANCE.create("berry_juice", new BerryJuiceItem());
        RARE_CANDY = INSTANCE.candyItem("rare_candy", CobblemonItems::RARE_CANDY$lambda$0);
        EXPERIENCE_CANDY_XS = INSTANCE.candyItem("exp_candy_xs", CobblemonItems::EXPERIENCE_CANDY_XS$lambda$1);
        EXPERIENCE_CANDY_S = INSTANCE.candyItem("exp_candy_s", CobblemonItems::EXPERIENCE_CANDY_S$lambda$2);
        EXPERIENCE_CANDY_M = INSTANCE.candyItem("exp_candy_m", CobblemonItems::EXPERIENCE_CANDY_M$lambda$3);
        EXPERIENCE_CANDY_L = INSTANCE.candyItem("exp_candy_l", CobblemonItems::EXPERIENCE_CANDY_L$lambda$4);
        EXPERIENCE_CANDY_XL = INSTANCE.candyItem("exp_candy_xl", CobblemonItems::EXPERIENCE_CANDY_XL$lambda$5);
        CALCIUM = INSTANCE.create("calcium", new VitaminItem(Stats.SPECIAL_ATTACK));
        CARBOS = INSTANCE.create("carbos", new VitaminItem(Stats.SPEED));
        HP_UP = INSTANCE.create("hp_up", new VitaminItem(Stats.HP));
        IRON = INSTANCE.create("iron", new VitaminItem(Stats.DEFENCE));
        PROTEIN = INSTANCE.create("protein", new VitaminItem(Stats.ATTACK));
        ZINC = INSTANCE.create("zinc", new VitaminItem(Stats.SPECIAL_DEFENCE));
        GENIUS_FEATHER = INSTANCE.create("genius_feather", new FeatherItem(Stats.SPECIAL_ATTACK));
        SWIFT_FEATHER = INSTANCE.create("swift_feather", new FeatherItem(Stats.SPEED));
        HEALTH_FEATHER = INSTANCE.create("health_feather", new FeatherItem(Stats.HP));
        RESIST_FEATHER = INSTANCE.create("resist_feather", new FeatherItem(Stats.DEFENCE));
        MUSCLE_FEATHER = INSTANCE.create("muscle_feather", new FeatherItem(Stats.ATTACK));
        CLEVER_FEATHER = INSTANCE.create("clever_feather", new FeatherItem(Stats.SPECIAL_DEFENCE));
        Item.Properties properties4 = new Item.Properties().m_41489_(new FoodProperties.Builder().m_38766_().m_38760_(1).m_38758_(0.2f).m_38767_());
        Intrinsics.checkNotNullExpressionValue((Object)properties4, (String)"Settings().food(FoodComp\u2026onModifier(0.2f).build())");
        MEDICINAL_LEEK = INSTANCE.heldItem("medicinal_leek", (Item)new MedicinalLeekItem(CobblemonBlocks.MEDICINAL_LEEK, properties4), "leek");
        ROASTED_LEEK = INSTANCE.create("roasted_leek", new Item(new Item.Properties().m_41489_(new FoodProperties.Builder().m_38766_().m_38760_(3).m_38758_(0.3f).m_38767_())));
        BRAISED_VIVICHOKE = INSTANCE.create("braised_vivichoke", new Item(new Item.Properties().m_41489_(new FoodProperties.Builder().m_38760_(6).m_38758_(0.6f).m_38767_())));
        statusArray = new Item.Properties().m_41487_(1).m_41489_(new FoodProperties.Builder().m_38760_(10).m_38758_(1.2f).m_38762_(new MobEffectInstance(MobEffects.f_19617_, 900, 0), 1.0f).m_38765_().m_38767_());
        VIVICHOKE_DIP = INSTANCE.create("vivichoke_dip", new BowlFoodItem((Item.Properties)statusArray){

            @NotNull
            public ItemStack m_5922_(@Nullable ItemStack stack, @Nullable Level world, @Nullable LivingEntity user) {
                LivingEntity livingEntity = user;
                if (livingEntity != null) {
                    livingEntity.m_21219_();
                }
                ItemStack itemStack = super.m_5922_(stack, world, user);
                Intrinsics.checkNotNullExpressionValue((Object)itemStack, (String)"super.finishUsing(stack, world, user)");
                return itemStack;
            }
        });
        Item.Properties properties5 = new Item.Properties().m_41489_(new FoodProperties.Builder().m_38760_(1).m_38766_().m_38758_(0.2f).m_38767_());
        Intrinsics.checkNotNullExpressionValue((Object)properties5, (String)"Settings().food(FoodComp\u2026onModifier(0.2f).build())");
        ENERGY_ROOT = CobblemonItems.compostableItem$default(INSTANCE, "energy_root", (Item)new EnergyRootItem(CobblemonBlocks.ENERGY_ROOT, properties5), 0.0f, 4, null);
        REVIVAL_HERB = CobblemonItems.compostableItem$default(INSTANCE, "revival_herb", (Item)new RevivalHerbItem(CobblemonBlocks.REVIVAL_HERB), 0.0f, 4, null);
        PEP_UP_FLOWER = CobblemonItems.compostableBlockItem$default(INSTANCE, "pep_up_flower", (Block)CobblemonBlocks.PEP_UP_FLOWER, 0.0f, 4, null);
        MEDICINAL_BREW = INSTANCE.create("medicinal_brew", new Item(new Item.Properties()));
        REMEDY = INSTANCE.create("remedy", new RemedyItem("normal"));
        FINE_REMEDY = INSTANCE.create("fine_remedy", new RemedyItem("fine"));
        SUPERB_REMEDY = INSTANCE.create("superb_remedy", new RemedyItem("superb"));
        POTION = INSTANCE.create("potion", new PotionItem(PotionType.POTION));
        SUPER_POTION = INSTANCE.create("super_potion", new PotionItem(PotionType.SUPER_POTION));
        HYPER_POTION = INSTANCE.create("hyper_potion", new PotionItem(PotionType.HYPER_POTION));
        MAX_POTION = INSTANCE.create("max_potion", new PotionItem(PotionType.MAX_POTION));
        FULL_RESTORE = INSTANCE.create("full_restore", new PotionItem(PotionType.FULL_RESTORE));
        HEAL_POWDER = INSTANCE.create("heal_powder", new HealPowderItem());
        LEEK_AND_POTATO_STEW = INSTANCE.create("leek_and_potato_stew", new BowlFoodItem(new Item.Properties().m_41489_(new FoodProperties.Builder().m_38760_(8).m_38758_(0.6f).m_38767_()).m_41487_(1)));
        REVIVE = INSTANCE.create("revive", new ReviveItem(false));
        MAX_REVIVE = INSTANCE.create("max_revive", new ReviveItem(true));
        PP_UP = INSTANCE.create("pp_up", new PPUpItem(1));
        PP_MAX = INSTANCE.create("pp_max", new PPUpItem(3));
        RED_MINT_SEEDS = INSTANCE.mintSeed("red", MintBlock.MintType.RED.getCropBlock());
        RED_MINT_LEAF = INSTANCE.mintLeaf("red", new MintLeafItem(MintBlock.MintType.RED));
        BLUE_MINT_SEEDS = INSTANCE.mintSeed("blue", MintBlock.MintType.BLUE.getCropBlock());
        BLUE_MINT_LEAF = INSTANCE.mintLeaf("blue", new MintLeafItem(MintBlock.MintType.BLUE));
        CYAN_MINT_SEEDS = INSTANCE.mintSeed("cyan", MintBlock.MintType.CYAN.getCropBlock());
        CYAN_MINT_LEAF = INSTANCE.mintLeaf("cyan", new MintLeafItem(MintBlock.MintType.CYAN));
        PINK_MINT_SEEDS = INSTANCE.mintSeed("pink", MintBlock.MintType.PINK.getCropBlock());
        PINK_MINT_LEAF = INSTANCE.mintLeaf("pink", new MintLeafItem(MintBlock.MintType.PINK));
        GREEN_MINT_SEEDS = INSTANCE.mintSeed("green", MintBlock.MintType.GREEN.getCropBlock());
        GREEN_MINT_LEAF = INSTANCE.mintLeaf("green", new MintLeafItem(MintBlock.MintType.GREEN));
        WHITE_MINT_SEEDS = INSTANCE.mintSeed("white", MintBlock.MintType.WHITE.getCropBlock());
        WHITE_MINT_LEAF = INSTANCE.mintLeaf("white", new MintLeafItem(MintBlock.MintType.WHITE));
        mints = new LinkedHashMap();
        LONELY_MINT = INSTANCE.mintItem("lonely_mint", new MintItem(Natures.INSTANCE.getLONELY()));
        ADAMANT_MINT = INSTANCE.mintItem("adamant_mint", new MintItem(Natures.INSTANCE.getADAMANT()));
        NAUGHTY_MINT = INSTANCE.mintItem("naughty_mint", new MintItem(Natures.INSTANCE.getNAUGHTY()));
        BRAVE_MINT = INSTANCE.mintItem("brave_mint", new MintItem(Natures.INSTANCE.getBRAVE()));
        BOLD_MINT = INSTANCE.mintItem("bold_mint", new MintItem(Natures.INSTANCE.getBOLD()));
        IMPISH_MINT = INSTANCE.mintItem("impish_mint", new MintItem(Natures.INSTANCE.getIMPISH()));
        LAX_MINT = INSTANCE.mintItem("lax_mint", new MintItem(Natures.INSTANCE.getLAX()));
        RELAXED_MINT = INSTANCE.mintItem("relaxed_mint", new MintItem(Natures.INSTANCE.getRELAXED()));
        MODEST_MINT = INSTANCE.mintItem("modest_mint", new MintItem(Natures.INSTANCE.getMODEST()));
        MILD_MINT = INSTANCE.mintItem("mild_mint", new MintItem(Natures.INSTANCE.getMILD()));
        RASH_MINT = INSTANCE.mintItem("rash_mint", new MintItem(Natures.INSTANCE.getRASH()));
        QUIET_MINT = INSTANCE.mintItem("quiet_mint", new MintItem(Natures.INSTANCE.getQUIET()));
        CALM_MINT = INSTANCE.mintItem("calm_mint", new MintItem(Natures.INSTANCE.getCALM()));
        GENTLE_MINT = INSTANCE.mintItem("gentle_mint", new MintItem(Natures.INSTANCE.getGENTLE()));
        CAREFUL_MINT = INSTANCE.mintItem("careful_mint", new MintItem(Natures.INSTANCE.getCAREFUL()));
        SASSY_MINT = INSTANCE.mintItem("sassy_mint", new MintItem(Natures.INSTANCE.getSASSY()));
        TIMID_MINT = INSTANCE.mintItem("timid_mint", new MintItem(Natures.INSTANCE.getTIMID()));
        HASTY_MINT = INSTANCE.mintItem("hasty_mint", new MintItem(Natures.INSTANCE.getHASTY()));
        JOLLY_MINT = INSTANCE.mintItem("jolly_mint", new MintItem(Natures.INSTANCE.getJOLLY()));
        NAIVE_MINT = INSTANCE.mintItem("naive_mint", new MintItem(Natures.INSTANCE.getNAIVE()));
        SERIOUS_MINT = INSTANCE.mintItem("serious_mint", new MintItem(Natures.INSTANCE.getSERIOUS()));
        X_ACCURACY = INSTANCE.create("x_" + Stats.ACCURACY.getIdentifier().m_135815_(), new XStatItem(Stats.ACCURACY, 0, 2, null));
        X_ATTACK = INSTANCE.create("x_" + Stats.ATTACK.getIdentifier().m_135815_(), new XStatItem(Stats.ATTACK, 0, 2, null));
        X_DEFENSE = INSTANCE.create("x_" + Stats.DEFENCE.getIdentifier().m_135815_(), new XStatItem(Stats.DEFENCE, 0, 2, null));
        X_SP_ATK = INSTANCE.create("x_" + Stats.SPECIAL_ATTACK.getIdentifier().m_135815_(), new XStatItem(Stats.SPECIAL_ATTACK, 0, 2, null));
        X_SP_DEF = INSTANCE.create("x_" + Stats.SPECIAL_DEFENCE.getIdentifier().m_135815_(), new XStatItem(Stats.SPECIAL_DEFENCE, 0, 2, null));
        X_SPEED = INSTANCE.create("x_" + Stats.SPEED.getIdentifier().m_135815_(), new XStatItem(Stats.SPEED, 0, 2, null));
        DIRE_HIT = INSTANCE.create("dire_hit", new DireHitItem());
        GUARD_SPEC = INSTANCE.create("guard_spec", new GuardSpecItem());
        statusArray = new Status[]{Statuses.INSTANCE.getBURN()};
        BURN_HEAL = INSTANCE.create("burn_heal", new StatusCureItem("item.cobblemon.burn_heal", statusArray));
        statusArray = new Status[]{Statuses.INSTANCE.getPARALYSIS()};
        PARALYZE_HEAL = INSTANCE.create("paralyze_heal", new StatusCureItem("item.cobblemon.paralyze_heal", statusArray));
        statusArray = new Status[]{Statuses.INSTANCE.getFROZEN()};
        ICE_HEAL = INSTANCE.create("ice_heal", new StatusCureItem("item.cobblemon.ice_heal", statusArray));
        statusArray = new Status[]{Statuses.INSTANCE.getPOISON(), Statuses.INSTANCE.getPOISON_BADLY()};
        ANTIDOTE = INSTANCE.create("antidote", new StatusCureItem("item.cobblemon.antidote", statusArray));
        statusArray = new Status[]{Statuses.INSTANCE.getSLEEP()};
        AWAKENING = INSTANCE.create("awakening", new StatusCureItem("item.cobblemon.awakening", statusArray));
        FULL_HEAL = INSTANCE.create("full_heal", new StatusCureItem("item.cobblemon.full_heal", new Status[0]));
        ETHER = INSTANCE.create("ether", new EtherItem(false));
        MAX_ETHER = INSTANCE.create("max_ether", new EtherItem(true));
        ELIXIR = INSTANCE.create("elixir", new ElixirItem(false));
        MAX_ELIXIR = INSTANCE.create("max_elixir", new ElixirItem(true));
        ABILITY_CAPSULE = INSTANCE.create("ability_capsule", new AbilityChangeItem<CommonAbility>(AbilityChanger.Companion.getCOMMON_ABILITY()));
        ABILITY_PATCH = INSTANCE.create("ability_patch", new AbilityChangeItem<HiddenAbility>(AbilityChanger.Companion.getHIDDEN_ABILITY()));
        DAWN_STONE_ORE = INSTANCE.blockItem("dawn_stone_ore", (Block)CobblemonBlocks.DAWN_STONE_ORE);
        DUSK_STONE_ORE = INSTANCE.blockItem("dusk_stone_ore", (Block)CobblemonBlocks.DUSK_STONE_ORE);
        FIRE_STONE_ORE = INSTANCE.blockItem("fire_stone_ore", (Block)CobblemonBlocks.FIRE_STONE_ORE);
        ICE_STONE_ORE = INSTANCE.blockItem("ice_stone_ore", (Block)CobblemonBlocks.ICE_STONE_ORE);
        LEAF_STONE_ORE = INSTANCE.blockItem("leaf_stone_ore", (Block)CobblemonBlocks.LEAF_STONE_ORE);
        MOON_STONE_ORE = INSTANCE.blockItem("moon_stone_ore", (Block)CobblemonBlocks.MOON_STONE_ORE);
        SHINY_STONE_ORE = INSTANCE.blockItem("shiny_stone_ore", (Block)CobblemonBlocks.SHINY_STONE_ORE);
        SUN_STONE_ORE = INSTANCE.blockItem("sun_stone_ore", (Block)CobblemonBlocks.SUN_STONE_ORE);
        TERRACOTTA_SUN_STONE_ORE = INSTANCE.blockItem("terracotta_sun_stone_ore", (Block)CobblemonBlocks.TERRACOTTA_SUN_STONE_ORE);
        THUNDER_STONE_ORE = INSTANCE.blockItem("thunder_stone_ore", (Block)CobblemonBlocks.THUNDER_STONE_ORE);
        WATER_STONE_ORE = INSTANCE.blockItem("water_stone_ore", (Block)CobblemonBlocks.WATER_STONE_ORE);
        DEEPSLATE_DAWN_STONE_ORE = INSTANCE.blockItem("deepslate_dawn_stone_ore", (Block)CobblemonBlocks.DEEPSLATE_DAWN_STONE_ORE);
        DEEPSLATE_DUSK_STONE_ORE = INSTANCE.blockItem("deepslate_dusk_stone_ore", (Block)CobblemonBlocks.DEEPSLATE_DUSK_STONE_ORE);
        DEEPSLATE_FIRE_STONE_ORE = INSTANCE.blockItem("deepslate_fire_stone_ore", (Block)CobblemonBlocks.DEEPSLATE_FIRE_STONE_ORE);
        DEEPSLATE_ICE_STONE_ORE = INSTANCE.blockItem("deepslate_ice_stone_ore", (Block)CobblemonBlocks.DEEPSLATE_ICE_STONE_ORE);
        DEEPSLATE_LEAF_STONE_ORE = INSTANCE.blockItem("deepslate_leaf_stone_ore", (Block)CobblemonBlocks.DEEPSLATE_LEAF_STONE_ORE);
        DEEPSLATE_MOON_STONE_ORE = INSTANCE.blockItem("deepslate_moon_stone_ore", (Block)CobblemonBlocks.DEEPSLATE_MOON_STONE_ORE);
        DEEPSLATE_SHINY_STONE_ORE = INSTANCE.blockItem("deepslate_shiny_stone_ore", (Block)CobblemonBlocks.DEEPSLATE_SHINY_STONE_ORE);
        DEEPSLATE_SUN_STONE_ORE = INSTANCE.blockItem("deepslate_sun_stone_ore", (Block)CobblemonBlocks.DEEPSLATE_SUN_STONE_ORE);
        DEEPSLATE_THUNDER_STONE_ORE = INSTANCE.blockItem("deepslate_thunder_stone_ore", (Block)CobblemonBlocks.DEEPSLATE_THUNDER_STONE_ORE);
        DEEPSLATE_WATER_STONE_ORE = INSTANCE.blockItem("deepslate_water_stone_ore", (Block)CobblemonBlocks.DEEPSLATE_WATER_STONE_ORE);
        DRIPSTONE_MOON_STONE_ORE = INSTANCE.blockItem("dripstone_moon_stone_ore", (Block)CobblemonBlocks.DRIPSTONE_MOON_STONE_ORE);
        NETHER_FIRE_STONE_ORE = INSTANCE.blockItem("nether_fire_stone_ore", (Block)CobblemonBlocks.NETHER_FIRE_STONE_ORE);
        DAWN_STONE = INSTANCE.noSettingsItem("dawn_stone");
        DUSK_STONE = INSTANCE.noSettingsItem("dusk_stone");
        FIRE_STONE = INSTANCE.noSettingsItem("fire_stone");
        ICE_STONE = INSTANCE.noSettingsItem("ice_stone");
        LEAF_STONE = INSTANCE.noSettingsItem("leaf_stone");
        MOON_STONE = INSTANCE.noSettingsItem("moon_stone");
        SHINY_STONE = INSTANCE.noSettingsItem("shiny_stone");
        SUN_STONE = INSTANCE.noSettingsItem("sun_stone");
        THUNDER_STONE = INSTANCE.noSettingsItem("thunder_stone");
        WATER_STONE = INSTANCE.noSettingsItem("water_stone");
        ABILITY_SHIELD = CobblemonItems.heldItem$default(INSTANCE, "ability_shield", null, 2, null);
        ABSORB_BULB = CobblemonItems.heldItem$default(INSTANCE, "absorb_bulb", null, 2, null);
        AIR_BALLOON = CobblemonItems.heldItem$default(INSTANCE, "air_balloon", null, 2, null);
        ASSAULT_VEST = CobblemonItems.heldItem$default(INSTANCE, "assault_vest", null, 2, null);
        BIG_ROOT = INSTANCE.blockItem("big_root", CobblemonBlocks.BIG_ROOT);
        BINDING_BAND = CobblemonItems.heldItem$default(INSTANCE, "binding_band", null, 2, null);
        BLACK_BELT = CobblemonItems.heldItem$default(INSTANCE, "black_belt", null, 2, null);
        BLACK_GLASSES = CobblemonItems.heldItem$default(INSTANCE, "black_glasses", null, 2, null);
        BLACK_SLUDGE = CobblemonItems.heldItem$default(INSTANCE, "black_sludge", null, 2, null);
        BLUNDER_POLICY = CobblemonItems.heldItem$default(INSTANCE, "blunder_policy", null, 2, null);
        CELL_BATTERY = CobblemonItems.heldItem$default(INSTANCE, "cell_battery", null, 2, null);
        CHARCOAL = INSTANCE.heldItem("charcoal_stick", "charcoal");
        CHOICE_BAND = CobblemonItems.heldItem$default(INSTANCE, "choice_band", null, 2, null);
        CHOICE_SCARF = CobblemonItems.heldItem$default(INSTANCE, "choice_scarf", null, 2, null);
        CHOICE_SPECS = CobblemonItems.heldItem$default(INSTANCE, "choice_specs", null, 2, null);
        CLEANSE_TAG = CobblemonItems.heldItem$default(INSTANCE, "cleanse_tag", null, 2, null);
        COVERT_CLOAK = CobblemonItems.heldItem$default(INSTANCE, "covert_cloak", null, 2, null);
        DESTINY_KNOT = CobblemonItems.heldItem$default(INSTANCE, "destiny_knot", null, 2, null);
        DRAGON_FANG = CobblemonItems.heldItem$default(INSTANCE, "dragon_fang", null, 2, null);
        EJECT_BUTTON = CobblemonItems.heldItem$default(INSTANCE, "eject_button", null, 2, null);
        EJECT_PACK = CobblemonItems.heldItem$default(INSTANCE, "eject_pack", null, 2, null);
        EVERSTONE = CobblemonItems.heldItem$default(INSTANCE, "everstone", null, 2, null);
        EVIOLITE = CobblemonItems.heldItem$default(INSTANCE, "eviolite", null, 2, null);
        EXP_SHARE = CobblemonItems.heldItem$default(INSTANCE, "exp_share", null, 2, null);
        EXPERT_BELT = CobblemonItems.heldItem$default(INSTANCE, "expert_belt", null, 2, null);
        FAIRY_FEATHER = CobblemonItems.heldItem$default(INSTANCE, "fairy_feather", null, 2, null);
        FLAME_ORB = CobblemonItems.heldItem$default(INSTANCE, "flame_orb", null, 2, null);
        FLOAT_STONE = CobblemonItems.heldItem$default(INSTANCE, "float_stone", null, 2, null);
        FOCUS_BAND = CobblemonItems.heldItem$default(INSTANCE, "focus_band", null, 2, null);
        FOCUS_SASH = CobblemonItems.heldItem$default(INSTANCE, "focus_sash", null, 2, null);
        HARD_STONE = CobblemonItems.heldItem$default(INSTANCE, "hard_stone", null, 2, null);
        HEAVY_DUTY_BOOTS = CobblemonItems.heldItem$default(INSTANCE, "heavy_duty_boots", null, 2, null);
        IRON_BALL = CobblemonItems.heldItem$default(INSTANCE, "iron_ball", null, 2, null);
        LEFTOVERS = CobblemonItems.heldItem$default(INSTANCE, "leftovers", null, 2, null);
        LIFE_ORB = CobblemonItems.heldItem$default(INSTANCE, "life_orb", null, 2, null);
        LIGHT_BALL = CobblemonItems.heldItem$default(INSTANCE, "light_ball", null, 2, null);
        LIGHT_CLAY = CobblemonItems.heldItem$default(INSTANCE, "light_clay", null, 2, null);
        LOADED_DICE = CobblemonItems.heldItem$default(INSTANCE, "loaded_dice", null, 2, null);
        LUCKY_EGG = CobblemonItems.heldItem$default(INSTANCE, "lucky_egg", null, 2, null);
        MAGNET = CobblemonItems.heldItem$default(INSTANCE, "magnet", null, 2, null);
        MIRACLE_SEED = CobblemonItems.heldItem$default(INSTANCE, "miracle_seed", null, 2, null);
        MUSCLE_BAND = CobblemonItems.heldItem$default(INSTANCE, "muscle_band", null, 2, null);
        MYSTIC_WATER = CobblemonItems.heldItem$default(INSTANCE, "mystic_water", null, 2, null);
        NEVER_MELT_ICE = CobblemonItems.heldItem$default(INSTANCE, "never_melt_ice", null, 2, null);
        POISON_BARB = CobblemonItems.heldItem$default(INSTANCE, "poison_barb", null, 2, null);
        POWER_ANKLET = CobblemonItems.heldItem$default(INSTANCE, "power_anklet", null, 2, null);
        POWER_BAND = CobblemonItems.heldItem$default(INSTANCE, "power_band", null, 2, null);
        POWER_BELT = CobblemonItems.heldItem$default(INSTANCE, "power_belt", null, 2, null);
        POWER_BRACER = CobblemonItems.heldItem$default(INSTANCE, "power_bracer", null, 2, null);
        POWER_LENS = CobblemonItems.heldItem$default(INSTANCE, "power_lens", null, 2, null);
        POWER_WEIGHT = CobblemonItems.heldItem$default(INSTANCE, "power_weight", null, 2, null);
        QUICK_CLAW = CobblemonItems.heldItem$default(INSTANCE, "quick_claw", null, 2, null);
        RED_CARD = CobblemonItems.heldItem$default(INSTANCE, "red_card", null, 2, null);
        RING_TARGET = CobblemonItems.heldItem$default(INSTANCE, "ring_target", null, 2, null);
        ROCKY_HELMET = CobblemonItems.heldItem$default(INSTANCE, "rocky_helmet", null, 2, null);
        SAFETY_GOGGLES = CobblemonItems.heldItem$default(INSTANCE, "safety_goggles", null, 2, null);
        SHARP_BEAK = CobblemonItems.heldItem$default(INSTANCE, "sharp_beak", null, 2, null);
        SHELL_BELL = CobblemonItems.heldItem$default(INSTANCE, "shell_bell", null, 2, null);
        SILK_SCARF = CobblemonItems.heldItem$default(INSTANCE, "silk_scarf", null, 2, null);
        SILVER_POWDER = CobblemonItems.heldItem$default(INSTANCE, "silver_powder", null, 2, null);
        SOFT_SAND = CobblemonItems.heldItem$default(INSTANCE, "soft_sand", null, 2, null);
        SPELL_TAG = CobblemonItems.heldItem$default(INSTANCE, "spell_tag", null, 2, null);
        SMOKE_BALL = CobblemonItems.heldItem$default(INSTANCE, "smoke_ball", null, 2, null);
        SOOTHE_BELL = CobblemonItems.heldItem$default(INSTANCE, "soothe_bell", null, 2, null);
        STICKY_BARB = CobblemonItems.heldItem$default(INSTANCE, "sticky_barb", null, 2, null);
        TOXIC_ORB = CobblemonItems.heldItem$default(INSTANCE, "toxic_orb", null, 2, null);
        TWISTED_SPOON = CobblemonItems.heldItem$default(INSTANCE, "twisted_spoon", null, 2, null);
        WEAKNESS_POLICY = CobblemonItems.heldItem$default(INSTANCE, "weakness_policy", null, 2, null);
        WISE_GLASSES = CobblemonItems.heldItem$default(INSTANCE, "wise_glasses", null, 2, null);
        MENTAL_HERB = INSTANCE.compostableHeldItem("mental_herb", null, 1.0f);
        MIRROR_HERB = INSTANCE.compostableHeldItem("mirror_herb", null, 1.0f);
        POWER_HERB = INSTANCE.compostableHeldItem("power_herb", null, 1.0f);
        WHITE_HERB = INSTANCE.compostableHeldItem("white_herb", null, 1.0f);
        BRIGHT_POWDER = CobblemonItems.heldItem$default(INSTANCE, "bright_powder", null, 2, null);
        METAL_POWDER = CobblemonItems.heldItem$default(INSTANCE, "metal_powder", null, 2, null);
        QUICK_POWDER = CobblemonItems.heldItem$default(INSTANCE, "quick_powder", null, 2, null);
        DAMP_ROCK = CobblemonItems.heldItem$default(INSTANCE, "damp_rock", null, 2, null);
        HEAT_ROCK = CobblemonItems.heldItem$default(INSTANCE, "heat_rock", null, 2, null);
        SMOOTH_ROCK = CobblemonItems.heldItem$default(INSTANCE, "smooth_rock", null, 2, null);
        ICY_ROCK = CobblemonItems.heldItem$default(INSTANCE, "icy_rock", null, 2, null);
        MULCH_BASE = INSTANCE.noSettingsItem("mulch_base");
        COARSE_MULCH = INSTANCE.mulchItem("coarse_mulch", MulchVariant.COARSE);
        GROWTH_MULCH = INSTANCE.mulchItem("growth_mulch", MulchVariant.GROWTH);
        HUMID_MULCH = INSTANCE.mulchItem("humid_mulch", MulchVariant.HUMID);
        LOAMY_MULCH = INSTANCE.mulchItem("loamy_mulch", MulchVariant.LOAMY);
        PEAT_MULCH = INSTANCE.mulchItem("peat_mulch", MulchVariant.PEAT);
        RICH_MULCH = INSTANCE.mulchItem("rich_mulch", MulchVariant.RICH);
        SANDY_MULCH = INSTANCE.mulchItem("sandy_mulch", MulchVariant.SANDY);
        SURPRISE_MULCH = INSTANCE.mulchItem("surprise_mulch", MulchVariant.SURPRISE);
        ARMOR_FOSSIL = INSTANCE.noSettingsItem("armor_fossil");
        FOSSILIZED_BIRD = INSTANCE.noSettingsItem("fossilized_bird");
        CLAW_FOSSIL = INSTANCE.noSettingsItem("claw_fossil");
        COVER_FOSSIL = INSTANCE.noSettingsItem("cover_fossil");
        FOSSILIZED_DINO = INSTANCE.noSettingsItem("fossilized_dino");
        DOME_FOSSIL = INSTANCE.noSettingsItem("dome_fossil");
        FOSSILIZED_DRAKE = INSTANCE.noSettingsItem("fossilized_drake");
        FOSSILIZED_FISH = INSTANCE.noSettingsItem("fossilized_fish");
        HELIX_FOSSIL = INSTANCE.noSettingsItem("helix_fossil");
        JAW_FOSSIL = INSTANCE.noSettingsItem("jaw_fossil");
        OLD_AMBER_FOSSIL = INSTANCE.noSettingsItem("old_amber_fossil");
        PLUME_FOSSIL = INSTANCE.noSettingsItem("plume_fossil");
        ROOT_FOSSIL = INSTANCE.noSettingsItem("root_fossil");
        SAIL_FOSSIL = INSTANCE.noSettingsItem("sail_fossil");
        SKULL_FOSSIL = INSTANCE.noSettingsItem("skull_fossil");
        BYGONE_SHERD = INSTANCE.noSettingsItem("bygone_sherd");
        CAPTURE_SHERD = INSTANCE.noSettingsItem("capture_sherd");
        DOME_SHERD = INSTANCE.noSettingsItem("dome_sherd");
        HELIX_SHERD = INSTANCE.noSettingsItem("helix_sherd");
        NOSTALGIC_SHERD = INSTANCE.noSettingsItem("nostalgic_sherd");
        SUSPICIOUS_SHERD = INSTANCE.noSettingsItem("suspicious_sherd");
        TUMBLESTONE = INSTANCE.create("tumblestone", new TumblestoneItem(new Item.Properties(), CobblemonBlocks.SMALL_BUDDING_TUMBLESTONE));
        BLACK_TUMBLESTONE = INSTANCE.create("black_tumblestone", new TumblestoneItem(new Item.Properties(), CobblemonBlocks.SMALL_BUDDING_BLACK_TUMBLESTONE));
        SKY_TUMBLESTONE = INSTANCE.create("sky_tumblestone", new TumblestoneItem(new Item.Properties(), CobblemonBlocks.SMALL_BUDDING_SKY_TUMBLESTONE));
        SMALL_BUDDING_TUMBLESTONE = INSTANCE.blockItem("small_budding_tumblestone", CobblemonBlocks.SMALL_BUDDING_TUMBLESTONE);
        MEDIUM_BUDDING_TUMBLESTONE = INSTANCE.blockItem("medium_budding_tumblestone", CobblemonBlocks.MEDIUM_BUDDING_TUMBLESTONE);
        LARGE_BUDDING_TUMBLESTONE = INSTANCE.blockItem("large_budding_tumblestone", CobblemonBlocks.LARGE_BUDDING_TUMBLESTONE);
        TUMBLESTONE_CLUSTER = INSTANCE.blockItem("tumblestone_cluster", CobblemonBlocks.TUMBLESTONE_CLUSTER);
        SMALL_BUDDING_SKY_TUMBLESTONE = INSTANCE.blockItem("small_budding_sky_tumblestone", CobblemonBlocks.SMALL_BUDDING_SKY_TUMBLESTONE);
        MEDIUM_BUDDING_SKY_TUMBLESTONE = INSTANCE.blockItem("medium_budding_sky_tumblestone", CobblemonBlocks.MEDIUM_BUDDING_SKY_TUMBLESTONE);
        LARGE_BUDDING_SKY_TUMBLESTONE = INSTANCE.blockItem("large_budding_sky_tumblestone", CobblemonBlocks.LARGE_BUDDING_SKY_TUMBLESTONE);
        SKY_TUMBLESTONE_CLUSTER = INSTANCE.blockItem("sky_tumblestone_cluster", CobblemonBlocks.SKY_TUMBLESTONE_CLUSTER);
        SMALL_BUDDING_BLACK_TUMBLESTONE = INSTANCE.blockItem("small_budding_black_tumblestone", CobblemonBlocks.SMALL_BUDDING_BLACK_TUMBLESTONE);
        MEDIUM_BUDDING_BLACK_TUMBLESTONE = INSTANCE.blockItem("medium_budding_black_tumblestone", CobblemonBlocks.MEDIUM_BUDDING_BLACK_TUMBLESTONE);
        LARGE_BUDDING_BLACK_TUMBLESTONE = INSTANCE.blockItem("large_budding_black_tumblestone", CobblemonBlocks.LARGE_BUDDING_BLACK_TUMBLESTONE);
        BLACK_TUMBLESTONE_CLUSTER = INSTANCE.blockItem("black_tumblestone_cluster", CobblemonBlocks.BLACK_TUMBLESTONE_CLUSTER);
        TUMBLESTONE_BLOCK = INSTANCE.blockItem("tumblestone_block", CobblemonBlocks.TUMBLESTONE_BLOCK);
        SKY_TUMBLESTONE_BLOCK = INSTANCE.blockItem("sky_tumblestone_block", CobblemonBlocks.SKY_TUMBLESTONE_BLOCK);
        BLACK_TUMBLESTONE_BLOCK = INSTANCE.blockItem("black_tumblestone_block", CobblemonBlocks.BLACK_TUMBLESTONE_BLOCK);
        SmithingTemplateItem smithingTemplateItem = INSTANCE.create("automaton_armor_trim_smithing_template", SmithingTemplateItem.m_266172_((ResourceLocation)CobblemonArmorTrims.INSTANCE.getAUTOMATON()));
        Intrinsics.checkNotNullExpressionValue((Object)smithingTemplateItem, (String)"this.create(\n        \"au\u2026morTrims.AUTOMATON)\n    )");
        AUTOMATON_ARMOR_TRIM_SMITHING_TEMPLATE = smithingTemplateItem;
        POKEMON_MODEL = INSTANCE.create("pokemon_model", new PokemonItem());
        RELIC_COIN = INSTANCE.noSettingsItem("relic_coin");
        RELIC_COIN_POUCH = INSTANCE.blockItem("relic_coin_pouch", (Block)CobblemonBlocks.RELIC_COIN_POUCH);
        RELIC_COIN_SACK = INSTANCE.blockItem("relic_coin_sack", (Block)CobblemonBlocks.RELIC_COIN_SACK);
        NORMAL_GEM = INSTANCE.noSettingsItem("normal_gem");
        FIRE_GEM = INSTANCE.noSettingsItem("fire_gem");
        WATER_GEM = INSTANCE.noSettingsItem("water_gem");
        GRASS_GEM = INSTANCE.noSettingsItem("grass_gem");
        ELECTRIC_GEM = INSTANCE.noSettingsItem("electric_gem");
        ICE_GEM = INSTANCE.noSettingsItem("ice_gem");
        FIGHTING_GEM = INSTANCE.noSettingsItem("fighting_gem");
        POISON_GEM = INSTANCE.noSettingsItem("poison_gem");
        GROUND_GEM = INSTANCE.noSettingsItem("ground_gem");
        FLYING_GEM = INSTANCE.noSettingsItem("flying_gem");
        PSYCHIC_GEM = INSTANCE.noSettingsItem("psychic_gem");
        BUG_GEM = INSTANCE.noSettingsItem("bug_gem");
        ROCK_GEM = INSTANCE.noSettingsItem("rock_gem");
        GHOST_GEM = INSTANCE.noSettingsItem("ghost_gem");
        DRAGON_GEM = INSTANCE.noSettingsItem("dragon_gem");
        DARK_GEM = INSTANCE.noSettingsItem("dark_gem");
        STEEL_GEM = INSTANCE.noSettingsItem("steel_gem");
        FAIRY_GEM = INSTANCE.noSettingsItem("fairy_gem");
    }
}

