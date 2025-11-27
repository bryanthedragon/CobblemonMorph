/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.jvm.JvmStatic
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.CreativeModeTab
 *  net.minecraft.world.item.CreativeModeTab$DisplayItemsGenerator
 *  net.minecraft.world.item.CreativeModeTab$ItemDisplayParameters
 *  net.minecraft.world.item.CreativeModeTab$Output
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.group;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonItems;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.group.CobblemonItemGroups;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000z\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u001e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u000f\b\u00c6\u0002\u0018\u00002\u00020\u0001:\u0002qrB\t\b\u0002\u00a2\u0006\u0004\bp\u00102J\u001f\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0002\u00a2\u0006\u0004\b\u0007\u0010\bJ\u001f\u0010\t\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0002\u00a2\u0006\u0004\b\t\u0010\bJ\u001f\u0010\n\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0002\u00a2\u0006\u0004\b\n\u0010\bJ\u001f\u0010\u000b\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0002\u00a2\u0006\u0004\b\u000b\u0010\bJ3\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00140\u00132\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u000e2\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010H\u0002\u00a2\u0006\u0004\b\u0015\u0010\u0016J\u001f\u0010\u0017\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0002\u00a2\u0006\u0004\b\u0017\u0010\bJ\u0017\u0010\u001a\u001a\u00020\u00062\u0006\u0010\u0019\u001a\u00020\u0018H\u0002\u00a2\u0006\u0004\b\u001a\u0010\u001bJ\u001f\u0010\u001c\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0002\u00a2\u0006\u0004\b\u001c\u0010\bJ\u0017\u0010\u001d\u001a\u00020\u00062\u0006\u0010\u0019\u001a\u00020\u0018H\u0002\u00a2\u0006\u0004\b\u001d\u0010\u001bJ[\u0010\"\u001a\u001d\u0012\u0013\u0012\u00110\u0018\u00a2\u0006\f\b \u0012\b\b\r\u0012\u0004\b\b(\u0019\u0012\u0004\u0012\u00020\u00060\u001f2\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00140\u00132!\u0010!\u001a\u001d\u0012\u0013\u0012\u00110\u0018\u00a2\u0006\f\b \u0012\b\b\r\u0012\u0004\b\b(\u0019\u0012\u0004\u0012\u00020\u00060\u001fH\u0002\u00a2\u0006\u0004\b\"\u0010#J#\u0010\"\u001a\u00020\u00062\f\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00140\u00132\u0006\u0010\u0019\u001a\u00020\u0018\u00a2\u0006\u0004\b\"\u0010%J\u0019\u0010'\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00140\u00130&\u00a2\u0006\u0004\b'\u0010(J\u001f\u0010)\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0002\u00a2\u0006\u0004\b)\u0010\bJ0\u0010,\u001a\u00020\u00062!\u0010!\u001a\u001d\u0012\u0013\u0012\u00110*\u00a2\u0006\f\b \u0012\b\b\r\u0012\u0004\b\b(+\u0012\u0004\u0012\u00020\u00140\u001f\u00a2\u0006\u0004\b,\u0010-J\u0017\u0010.\u001a\u00020\u00062\u0006\u0010\u0019\u001a\u00020\u0018H\u0002\u00a2\u0006\u0004\b.\u0010\u001bR\u001c\u00103\u001a\u0004\u0018\u00010\u00148FX\u0087\u0004\u00a2\u0006\f\u0012\u0004\b1\u00102\u001a\u0004\b/\u00100R&\u00104\u001a\b\u0012\u0004\u0012\u00020\u00140\u00138\u0006X\u0087\u0004\u00a2\u0006\u0012\n\u0004\b4\u00105\u0012\u0004\b8\u00102\u001a\u0004\b6\u00107R$\u0010;\u001a\u0012\u0012\u0004\u0012\u00020*09j\b\u0012\u0004\u0012\u00020*`:8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b;\u0010<R\u001c\u0010?\u001a\u0004\u0018\u00010\u00148FX\u0087\u0004\u00a2\u0006\f\u0012\u0004\b>\u00102\u001a\u0004\b=\u00100R&\u0010@\u001a\b\u0012\u0004\u0012\u00020\u00140\u00138\u0006X\u0087\u0004\u00a2\u0006\u0012\n\u0004\b@\u00105\u0012\u0004\bB\u00102\u001a\u0004\bA\u00107R\u001c\u0010E\u001a\u0004\u0018\u00010\u00148FX\u0087\u0004\u00a2\u0006\f\u0012\u0004\bD\u00102\u001a\u0004\bC\u00100R&\u0010F\u001a\b\u0012\u0004\u0012\u00020\u00140\u00138\u0006X\u0087\u0004\u00a2\u0006\u0012\n\u0004\bF\u00105\u0012\u0004\bH\u00102\u001a\u0004\bG\u00107R\u001c\u0010K\u001a\u0004\u0018\u00010\u00148FX\u0087\u0004\u00a2\u0006\f\u0012\u0004\bJ\u00102\u001a\u0004\bI\u00100R&\u0010L\u001a\b\u0012\u0004\u0012\u00020\u00140\u00138\u0006X\u0087\u0004\u00a2\u0006\u0012\n\u0004\bL\u00105\u0012\u0004\bN\u00102\u001a\u0004\bM\u00107R\u001c\u0010Q\u001a\u0004\u0018\u00010\u00148FX\u0087\u0004\u00a2\u0006\f\u0012\u0004\bP\u00102\u001a\u0004\bO\u00100R&\u0010R\u001a\b\u0012\u0004\u0012\u00020\u00140\u00138\u0006X\u0087\u0004\u00a2\u0006\u0012\n\u0004\bR\u00105\u0012\u0004\bT\u00102\u001a\u0004\bS\u00107R;\u0010U\u001a\u001d\u0012\u0013\u0012\u00110\u0018\u00a2\u0006\f\b \u0012\b\b\r\u0012\u0004\b\b(\u0019\u0012\u0004\u0012\u00020\u00060\u001f8\u0006X\u0087\u0004\u00a2\u0006\u0012\n\u0004\bU\u0010V\u0012\u0004\bY\u00102\u001a\u0004\bW\u0010XR\u001c\u0010\\\u001a\u0004\u0018\u00010\u00148FX\u0087\u0004\u00a2\u0006\f\u0012\u0004\b[\u00102\u001a\u0004\bZ\u00100R&\u0010]\u001a\b\u0012\u0004\u0012\u00020\u00140\u00138\u0006X\u0087\u0004\u00a2\u0006\u0012\n\u0004\b]\u00105\u0012\u0004\b_\u00102\u001a\u0004\b^\u00107R;\u0010`\u001a\u001d\u0012\u0013\u0012\u00110\u0018\u00a2\u0006\f\b \u0012\b\b\r\u0012\u0004\b\b(\u0019\u0012\u0004\u0012\u00020\u00060\u001f8\u0006X\u0087\u0004\u00a2\u0006\u0012\n\u0004\b`\u0010V\u0012\u0004\bb\u00102\u001a\u0004\ba\u0010XRr\u0010e\u001a`\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00140\u0013\u0012\u001f\u0012\u001d\u0012\u0013\u0012\u00110\u0018\u00a2\u0006\f\b \u0012\b\b\r\u0012\u0004\b\b(\u0019\u0012\u0004\u0012\u00020\u00060\u001f0cj/\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00140\u0013\u0012\u001f\u0012\u001d\u0012\u0013\u0012\u00110\u0018\u00a2\u0006\f\b \u0012\b\b\r\u0012\u0004\b\b(\u0019\u0012\u0004\u0012\u00020\u00060\u001f`d8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\be\u0010fR\u001c\u0010i\u001a\u0004\u0018\u00010\u00148FX\u0087\u0004\u00a2\u0006\f\u0012\u0004\bh\u00102\u001a\u0004\bg\u00100R&\u0010j\u001a\b\u0012\u0004\u0012\u00020\u00140\u00138\u0006X\u0087\u0004\u00a2\u0006\u0012\n\u0004\bj\u00105\u0012\u0004\bl\u00102\u001a\u0004\bk\u00107R;\u0010m\u001a\u001d\u0012\u0013\u0012\u00110\u0018\u00a2\u0006\f\b \u0012\b\b\r\u0012\u0004\b\b(\u0019\u0012\u0004\u0012\u00020\u00060\u001f8\u0006X\u0087\u0004\u00a2\u0006\u0012\n\u0004\bm\u0010V\u0012\u0004\bo\u00102\u001a\u0004\bn\u0010X\u00a8\u0006s"}, d2={"Lcom/cobblemon/mod/common/item/group/CobblemonItemGroups;", "", "Lnet/minecraft/item/ItemGroup$DisplayContext;", "displayContext", "Lnet/minecraft/item/ItemGroup$Entries;", "entries", "", "agricultureEntries", "(Lnet/minecraft/world/item/CreativeModeTab$ItemDisplayParameters;Lnet/minecraft/world/item/CreativeModeTab$Output;)V", "archaeologyEntries", "blockEntries", "consumableEntries", "", "name", "Lnet/minecraft/item/ItemGroup$EntryCollector;", "entryCollector", "Lkotlin/Function0;", "Lnet/minecraft/world/item/ItemStack;", "displayIconProvider", "Lnet/minecraft/resources/ResourceKey;", "Lnet/minecraft/world/item/CreativeModeTab;", "create", "(Ljava/lang/String;Lnet/minecraft/world/item/CreativeModeTab$DisplayItemsGenerator;Lkotlin/jvm/functions/Function0;)Lnet/minecraft/resources/ResourceKey;", "evolutionItemEntries", "Lcom/cobblemon/mod/common/item/group/CobblemonItemGroups$Injector;", "injector", "foodInjections", "(Lcom/cobblemon/mod/common/item/group/CobblemonItemGroups$Injector;)V", "heldItemEntries", "ingredientsInjections", "key", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "consumer", "inject", "(Lnet/minecraft/resources/ResourceKey;Lkotlin/jvm/functions/Function1;)Lkotlin/jvm/functions/Function1;", "tabKey", "(Lnet/minecraft/resources/ResourceKey;Lcom/cobblemon/mod/common/item/group/CobblemonItemGroups$Injector;)V", "", "injectorKeys", "()Ljava/util/Collection;", "pokeballentries", "Lcom/cobblemon/mod/common/item/group/CobblemonItemGroups$ItemGroupHolder;", "holder", "register", "(Lkotlin/jvm/functions/Function1;)V", "toolsAndUtilitiesInjections", "getAGRICULTURE", "()Lnet/minecraft/world/item/CreativeModeTab;", "getAGRICULTURE$annotations", "()V", "AGRICULTURE", "AGRICULTURE_KEY", "Lnet/minecraft/resources/ResourceKey;", "getAGRICULTURE_KEY", "()Lnet/minecraft/resources/ResourceKey;", "getAGRICULTURE_KEY$annotations", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "ALL", "Ljava/util/ArrayList;", "getARCHAEOLOGY", "getARCHAEOLOGY$annotations", "ARCHAEOLOGY", "ARCHAEOLOGY_KEY", "getARCHAEOLOGY_KEY", "getARCHAEOLOGY_KEY$annotations", "getBLOCKS", "getBLOCKS$annotations", "BLOCKS", "BLOCKS_KEY", "getBLOCKS_KEY", "getBLOCKS_KEY$annotations", "getCONSUMABLES", "getCONSUMABLES$annotations", "CONSUMABLES", "CONSUMABLES_KEY", "getCONSUMABLES_KEY", "getCONSUMABLES_KEY$annotations", "getEVOLUTION_ITEMS", "getEVOLUTION_ITEMS$annotations", "EVOLUTION_ITEMS", "EVOLUTION_ITEMS_KEY", "getEVOLUTION_ITEMS_KEY", "getEVOLUTION_ITEMS_KEY$annotations", "FOOD_INJECTIONS", "Lkotlin/jvm/functions/Function1;", "getFOOD_INJECTIONS", "()Lkotlin/jvm/functions/Function1;", "getFOOD_INJECTIONS$annotations", "getHELD_ITEMS", "getHELD_ITEMS$annotations", "HELD_ITEMS", "HELD_ITEMS_KEY", "getHELD_ITEMS_KEY", "getHELD_ITEMS_KEY$annotations", "INGREDIENTS_INJECTIONS", "getINGREDIENTS_INJECTIONS", "getINGREDIENTS_INJECTIONS$annotations", "Ljava/util/HashMap;", "Lkotlin/collections/HashMap;", "INJECTORS", "Ljava/util/HashMap;", "getPOKEBALLS", "getPOKEBALLS$annotations", "POKEBALLS", "POKEBALLS_KEY", "getPOKEBALLS_KEY", "getPOKEBALLS_KEY$annotations", "TOOLS_AND_UTILITIES_INJECTIONS", "getTOOLS_AND_UTILITIES_INJECTIONS", "getTOOLS_AND_UTILITIES_INJECTIONS$annotations", "<init>", "Injector", "ItemGroupHolder", "common"})
@SourceDebugExtension(value={"SMAP\nCobblemonItemGroups.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CobblemonItemGroups.kt\ncom/cobblemon/mod/common/item/group/CobblemonItemGroups\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,604:1\n1855#2,2:605\n1855#2,2:607\n1855#2,2:609\n*S KotlinDebug\n*F\n+ 1 CobblemonItemGroups.kt\ncom/cobblemon/mod/common/item/group/CobblemonItemGroups\n*L\n52#1:605,2\n130#1:607,2\n536#1:609,2\n*E\n"})
public final class CobblemonItemGroups {
    @NotNull
    public static final CobblemonItemGroups INSTANCE = new CobblemonItemGroups();
    @NotNull
    private static final ArrayList<ItemGroupHolder> ALL = new ArrayList();
    @NotNull
    private static final HashMap<ResourceKey<CreativeModeTab>, Function1<Injector, Unit>> INJECTORS = new HashMap();
    @NotNull
    private static final ResourceKey<CreativeModeTab> BLOCKS_KEY = INSTANCE.create("blocks", INSTANCE::blockEntries, (Function0<ItemStack>)((Function0)BLOCKS_KEY.2.INSTANCE));
    @NotNull
    private static final ResourceKey<CreativeModeTab> POKEBALLS_KEY = INSTANCE.create("pokeball", INSTANCE::pokeballentries, (Function0<ItemStack>)((Function0)POKEBALLS_KEY.2.INSTANCE));
    @NotNull
    private static final ResourceKey<CreativeModeTab> AGRICULTURE_KEY = INSTANCE.create("agriculture", INSTANCE::agricultureEntries, (Function0<ItemStack>)((Function0)AGRICULTURE_KEY.2.INSTANCE));
    @NotNull
    private static final ResourceKey<CreativeModeTab> ARCHAEOLOGY_KEY = INSTANCE.create("archaeology", INSTANCE::archaeologyEntries, (Function0<ItemStack>)((Function0)ARCHAEOLOGY_KEY.2.INSTANCE));
    @NotNull
    private static final ResourceKey<CreativeModeTab> CONSUMABLES_KEY = INSTANCE.create("consumables", INSTANCE::consumableEntries, (Function0<ItemStack>)((Function0)CONSUMABLES_KEY.2.INSTANCE));
    @NotNull
    private static final ResourceKey<CreativeModeTab> HELD_ITEMS_KEY = INSTANCE.create("held_item", INSTANCE::heldItemEntries, (Function0<ItemStack>)((Function0)HELD_ITEMS_KEY.2.INSTANCE));
    @NotNull
    private static final ResourceKey<CreativeModeTab> EVOLUTION_ITEMS_KEY = INSTANCE.create("evolution_item", INSTANCE::evolutionItemEntries, (Function0<ItemStack>)((Function0)EVOLUTION_ITEMS_KEY.2.INSTANCE));
    @NotNull
    private static final Function1<Injector, Unit> FOOD_INJECTIONS;
    @NotNull
    private static final Function1<Injector, Unit> TOOLS_AND_UTILITIES_INJECTIONS;
    @NotNull
    private static final Function1<Injector, Unit> INGREDIENTS_INJECTIONS;

    private CobblemonItemGroups() {
    }

    @NotNull
    public static final ResourceKey<CreativeModeTab> getBLOCKS_KEY() {
        return BLOCKS_KEY;
    }

    @JvmStatic
    public static /* synthetic */ void getBLOCKS_KEY$annotations() {
    }

    @NotNull
    public static final ResourceKey<CreativeModeTab> getPOKEBALLS_KEY() {
        return POKEBALLS_KEY;
    }

    @JvmStatic
    public static /* synthetic */ void getPOKEBALLS_KEY$annotations() {
    }

    @NotNull
    public static final ResourceKey<CreativeModeTab> getAGRICULTURE_KEY() {
        return AGRICULTURE_KEY;
    }

    @JvmStatic
    public static /* synthetic */ void getAGRICULTURE_KEY$annotations() {
    }

    @NotNull
    public static final ResourceKey<CreativeModeTab> getARCHAEOLOGY_KEY() {
        return ARCHAEOLOGY_KEY;
    }

    @JvmStatic
    public static /* synthetic */ void getARCHAEOLOGY_KEY$annotations() {
    }

    @NotNull
    public static final ResourceKey<CreativeModeTab> getCONSUMABLES_KEY() {
        return CONSUMABLES_KEY;
    }

    @JvmStatic
    public static /* synthetic */ void getCONSUMABLES_KEY$annotations() {
    }

    @NotNull
    public static final ResourceKey<CreativeModeTab> getHELD_ITEMS_KEY() {
        return HELD_ITEMS_KEY;
    }

    @JvmStatic
    public static /* synthetic */ void getHELD_ITEMS_KEY$annotations() {
    }

    @NotNull
    public static final ResourceKey<CreativeModeTab> getEVOLUTION_ITEMS_KEY() {
        return EVOLUTION_ITEMS_KEY;
    }

    @JvmStatic
    public static /* synthetic */ void getEVOLUTION_ITEMS_KEY$annotations() {
    }

    @Nullable
    public static final CreativeModeTab getBLOCKS() {
        return (CreativeModeTab)BuiltInRegistries.f_279662_.m_6246_(BLOCKS_KEY);
    }

    @JvmStatic
    public static /* synthetic */ void getBLOCKS$annotations() {
    }

    @Nullable
    public static final CreativeModeTab getPOKEBALLS() {
        return (CreativeModeTab)BuiltInRegistries.f_279662_.m_6246_(POKEBALLS_KEY);
    }

    @JvmStatic
    public static /* synthetic */ void getPOKEBALLS$annotations() {
    }

    @Nullable
    public static final CreativeModeTab getAGRICULTURE() {
        return (CreativeModeTab)BuiltInRegistries.f_279662_.m_6246_(AGRICULTURE_KEY);
    }

    @JvmStatic
    public static /* synthetic */ void getAGRICULTURE$annotations() {
    }

    @Nullable
    public static final CreativeModeTab getARCHAEOLOGY() {
        return (CreativeModeTab)BuiltInRegistries.f_279662_.m_6246_(ARCHAEOLOGY_KEY);
    }

    @JvmStatic
    public static /* synthetic */ void getARCHAEOLOGY$annotations() {
    }

    @Nullable
    public static final CreativeModeTab getCONSUMABLES() {
        return (CreativeModeTab)BuiltInRegistries.f_279662_.m_6246_(CONSUMABLES_KEY);
    }

    @JvmStatic
    public static /* synthetic */ void getCONSUMABLES$annotations() {
    }

    @Nullable
    public static final CreativeModeTab getHELD_ITEMS() {
        return (CreativeModeTab)BuiltInRegistries.f_279662_.m_6246_(HELD_ITEMS_KEY);
    }

    @JvmStatic
    public static /* synthetic */ void getHELD_ITEMS$annotations() {
    }

    @Nullable
    public static final CreativeModeTab getEVOLUTION_ITEMS() {
        return (CreativeModeTab)BuiltInRegistries.f_279662_.m_6246_(EVOLUTION_ITEMS_KEY);
    }

    @JvmStatic
    public static /* synthetic */ void getEVOLUTION_ITEMS$annotations() {
    }

    @NotNull
    public static final Function1<Injector, Unit> getFOOD_INJECTIONS() {
        return FOOD_INJECTIONS;
    }

    @JvmStatic
    public static /* synthetic */ void getFOOD_INJECTIONS$annotations() {
    }

    @NotNull
    public static final Function1<Injector, Unit> getTOOLS_AND_UTILITIES_INJECTIONS() {
        return TOOLS_AND_UTILITIES_INJECTIONS;
    }

    @JvmStatic
    public static /* synthetic */ void getTOOLS_AND_UTILITIES_INJECTIONS$annotations() {
    }

    @NotNull
    public static final Function1<Injector, Unit> getINGREDIENTS_INJECTIONS() {
        return INGREDIENTS_INJECTIONS;
    }

    @JvmStatic
    public static /* synthetic */ void getINGREDIENTS_INJECTIONS$annotations() {
    }

    public final void register(@NotNull Function1<? super ItemGroupHolder, ? extends CreativeModeTab> consumer) {
        Intrinsics.checkNotNullParameter(consumer, (String)"consumer");
        Iterable $this$forEach$iv = ALL;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            ItemGroupHolder p0 = (ItemGroupHolder)element$iv;
            boolean bl = false;
            consumer.invoke((Object)p0);
        }
    }

    public final void inject(@NotNull ResourceKey<CreativeModeTab> tabKey, @NotNull Injector injector) {
        block0: {
            Intrinsics.checkNotNullParameter(tabKey, (String)"tabKey");
            Intrinsics.checkNotNullParameter((Object)injector, (String)"injector");
            Function1<Injector, Unit> function1 = INJECTORS.get(tabKey);
            if (function1 == null) break block0;
            function1.invoke((Object)injector);
        }
    }

    @NotNull
    public final Collection<ResourceKey<CreativeModeTab>> injectorKeys() {
        Set<ResourceKey<CreativeModeTab>> set2 = INJECTORS.keySet();
        Intrinsics.checkNotNullExpressionValue(set2, (String)"INJECTORS.keys");
        return set2;
    }

    private final ResourceKey<CreativeModeTab> create(String name, CreativeModeTab.DisplayItemsGenerator entryCollector, Function0<ItemStack> displayIconProvider) {
        ResourceKey key = ResourceKey.m_135785_((ResourceKey)BuiltInRegistries.f_279662_.m_123023_(), (ResourceLocation)MiscUtils.cobblemonResource(name));
        Collection collection = ALL;
        Intrinsics.checkNotNullExpressionValue((Object)key, (String)"key");
        collection.add(new ItemGroupHolder(key, displayIconProvider, entryCollector, null, 8, null));
        return key;
    }

    private final Function1<Injector, Unit> inject(ResourceKey<CreativeModeTab> key, Function1<? super Injector, Unit> consumer) {
        ((Map)INJECTORS).put(key, consumer);
        return consumer;
    }

    private final void agricultureEntries(CreativeModeTab.ItemDisplayParameters displayContext, CreativeModeTab.Output entries) {
        entries.m_246326_((ItemLike)CobblemonItems.MEDICINAL_LEEK);
        entries.m_246326_((ItemLike)CobblemonItems.BIG_ROOT);
        entries.m_246326_((ItemLike)CobblemonItems.ENERGY_ROOT);
        entries.m_246326_((ItemLike)CobblemonItems.REVIVAL_HERB);
        entries.m_246326_((ItemLike)CobblemonItems.PEP_UP_FLOWER);
        entries.m_246326_((ItemLike)CobblemonItems.MENTAL_HERB);
        entries.m_246326_((ItemLike)CobblemonItems.POWER_HERB);
        entries.m_246326_((ItemLike)CobblemonItems.WHITE_HERB);
        entries.m_246326_((ItemLike)CobblemonItems.MIRROR_HERB);
        entries.m_246326_((ItemLike)CobblemonItems.VIVICHOKE);
        entries.m_246326_((ItemLike)CobblemonItems.VIVICHOKE_SEEDS);
        entries.m_246326_((ItemLike)CobblemonItems.RED_APRICORN);
        entries.m_246326_((ItemLike)CobblemonItems.YELLOW_APRICORN);
        entries.m_246326_((ItemLike)CobblemonItems.GREEN_APRICORN);
        entries.m_246326_((ItemLike)CobblemonItems.BLUE_APRICORN);
        entries.m_246326_((ItemLike)CobblemonItems.PINK_APRICORN);
        entries.m_246326_((ItemLike)CobblemonItems.BLACK_APRICORN);
        entries.m_246326_((ItemLike)CobblemonItems.WHITE_APRICORN);
        entries.m_246326_((ItemLike)CobblemonItems.RED_APRICORN_SEED);
        entries.m_246326_((ItemLike)CobblemonItems.YELLOW_APRICORN_SEED);
        entries.m_246326_((ItemLike)CobblemonItems.GREEN_APRICORN_SEED);
        entries.m_246326_((ItemLike)CobblemonItems.BLUE_APRICORN_SEED);
        entries.m_246326_((ItemLike)CobblemonItems.PINK_APRICORN_SEED);
        entries.m_246326_((ItemLike)CobblemonItems.BLACK_APRICORN_SEED);
        entries.m_246326_((ItemLike)CobblemonItems.WHITE_APRICORN_SEED);
        entries.m_246326_((ItemLike)CobblemonItems.RED_MINT_SEEDS);
        entries.m_246326_((ItemLike)CobblemonItems.RED_MINT_LEAF);
        entries.m_246326_((ItemLike)CobblemonItems.BLUE_MINT_SEEDS);
        entries.m_246326_((ItemLike)CobblemonItems.BLUE_MINT_LEAF);
        entries.m_246326_((ItemLike)CobblemonItems.CYAN_MINT_SEEDS);
        entries.m_246326_((ItemLike)CobblemonItems.CYAN_MINT_LEAF);
        entries.m_246326_((ItemLike)CobblemonItems.PINK_MINT_SEEDS);
        entries.m_246326_((ItemLike)CobblemonItems.PINK_MINT_LEAF);
        entries.m_246326_((ItemLike)CobblemonItems.GREEN_MINT_SEEDS);
        entries.m_246326_((ItemLike)CobblemonItems.GREEN_MINT_LEAF);
        entries.m_246326_((ItemLike)CobblemonItems.WHITE_MINT_SEEDS);
        entries.m_246326_((ItemLike)CobblemonItems.WHITE_MINT_LEAF);
        entries.m_246326_((ItemLike)CobblemonItems.GROWTH_MULCH);
        entries.m_246326_((ItemLike)CobblemonItems.RICH_MULCH);
        entries.m_246326_((ItemLike)CobblemonItems.SURPRISE_MULCH);
        entries.m_246326_((ItemLike)CobblemonItems.LOAMY_MULCH);
        entries.m_246326_((ItemLike)CobblemonItems.COARSE_MULCH);
        entries.m_246326_((ItemLike)CobblemonItems.PEAT_MULCH);
        entries.m_246326_((ItemLike)CobblemonItems.HUMID_MULCH);
        entries.m_246326_((ItemLike)CobblemonItems.SANDY_MULCH);
        entries.m_246326_((ItemLike)CobblemonItems.MULCH_BASE);
        Iterable $this$forEach$iv = CobblemonItems.INSTANCE.berries().values();
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            ItemLike p0 = (ItemLike)element$iv;
            boolean bl = false;
            entries.m_246326_(p0);
        }
    }

    private final void archaeologyEntries(CreativeModeTab.ItemDisplayParameters displayContext, CreativeModeTab.Output entries) {
        entries.m_246326_((ItemLike)CobblemonItems.HELIX_FOSSIL);
        entries.m_246326_((ItemLike)CobblemonItems.DOME_FOSSIL);
        entries.m_246326_((ItemLike)CobblemonItems.OLD_AMBER_FOSSIL);
        entries.m_246326_((ItemLike)CobblemonItems.ROOT_FOSSIL);
        entries.m_246326_((ItemLike)CobblemonItems.CLAW_FOSSIL);
        entries.m_246326_((ItemLike)CobblemonItems.SKULL_FOSSIL);
        entries.m_246326_((ItemLike)CobblemonItems.ARMOR_FOSSIL);
        entries.m_246326_((ItemLike)CobblemonItems.COVER_FOSSIL);
        entries.m_246326_((ItemLike)CobblemonItems.PLUME_FOSSIL);
        entries.m_246326_((ItemLike)CobblemonItems.JAW_FOSSIL);
        entries.m_246326_((ItemLike)CobblemonItems.SAIL_FOSSIL);
        entries.m_246326_((ItemLike)CobblemonItems.FOSSILIZED_BIRD);
        entries.m_246326_((ItemLike)CobblemonItems.FOSSILIZED_FISH);
        entries.m_246326_((ItemLike)CobblemonItems.FOSSILIZED_DRAKE);
        entries.m_246326_((ItemLike)CobblemonItems.FOSSILIZED_DINO);
        entries.m_246326_((ItemLike)CobblemonItems.TUMBLESTONE);
        entries.m_246326_((ItemLike)CobblemonItems.BLACK_TUMBLESTONE);
        entries.m_246326_((ItemLike)CobblemonItems.SKY_TUMBLESTONE);
        entries.m_246326_((ItemLike)CobblemonItems.SMALL_BUDDING_TUMBLESTONE);
        entries.m_246326_((ItemLike)CobblemonItems.SMALL_BUDDING_BLACK_TUMBLESTONE);
        entries.m_246326_((ItemLike)CobblemonItems.SMALL_BUDDING_SKY_TUMBLESTONE);
        entries.m_246326_((ItemLike)CobblemonItems.MEDIUM_BUDDING_TUMBLESTONE);
        entries.m_246326_((ItemLike)CobblemonItems.MEDIUM_BUDDING_BLACK_TUMBLESTONE);
        entries.m_246326_((ItemLike)CobblemonItems.MEDIUM_BUDDING_SKY_TUMBLESTONE);
        entries.m_246326_((ItemLike)CobblemonItems.LARGE_BUDDING_TUMBLESTONE);
        entries.m_246326_((ItemLike)CobblemonItems.LARGE_BUDDING_BLACK_TUMBLESTONE);
        entries.m_246326_((ItemLike)CobblemonItems.LARGE_BUDDING_SKY_TUMBLESTONE);
        entries.m_246326_((ItemLike)CobblemonItems.TUMBLESTONE_CLUSTER);
        entries.m_246326_((ItemLike)CobblemonItems.BLACK_TUMBLESTONE_CLUSTER);
        entries.m_246326_((ItemLike)CobblemonItems.SKY_TUMBLESTONE_CLUSTER);
        entries.m_246326_((ItemLike)CobblemonItems.TUMBLESTONE_BLOCK);
        entries.m_246326_((ItemLike)CobblemonItems.BLACK_TUMBLESTONE_BLOCK);
        entries.m_246326_((ItemLike)CobblemonItems.SKY_TUMBLESTONE_BLOCK);
        entries.m_246326_((ItemLike)CobblemonItems.BYGONE_SHERD);
        entries.m_246326_((ItemLike)CobblemonItems.CAPTURE_SHERD);
        entries.m_246326_((ItemLike)CobblemonItems.DOME_SHERD);
        entries.m_246326_((ItemLike)CobblemonItems.HELIX_SHERD);
        entries.m_246326_((ItemLike)CobblemonItems.NOSTALGIC_SHERD);
        entries.m_246326_((ItemLike)CobblemonItems.SUSPICIOUS_SHERD);
        entries.m_246326_((ItemLike)CobblemonItems.AUTOMATON_ARMOR_TRIM_SMITHING_TEMPLATE);
        entries.m_246326_((ItemLike)CobblemonItems.RELIC_COIN);
        entries.m_246326_((ItemLike)CobblemonItems.RELIC_COIN_POUCH);
        entries.m_246326_((ItemLike)CobblemonItems.RELIC_COIN_SACK);
        entries.m_246326_((ItemLike)CobblemonItems.GILDED_CHEST);
        entries.m_246326_((ItemLike)CobblemonItems.YELLOW_GILDED_CHEST);
        entries.m_246326_((ItemLike)CobblemonItems.GREEN_GILDED_CHEST);
        entries.m_246326_((ItemLike)CobblemonItems.BLUE_GILDED_CHEST);
        entries.m_246326_((ItemLike)CobblemonItems.PINK_GILDED_CHEST);
        entries.m_246326_((ItemLike)CobblemonItems.BLACK_GILDED_CHEST);
        entries.m_246326_((ItemLike)CobblemonItems.WHITE_GILDED_CHEST);
        entries.m_246326_((ItemLike)CobblemonItems.GIMMIGHOUL_CHEST);
        entries.m_246326_((ItemLike)CobblemonItems.NORMAL_GEM);
        entries.m_246326_((ItemLike)CobblemonItems.FIRE_GEM);
        entries.m_246326_((ItemLike)CobblemonItems.WATER_GEM);
        entries.m_246326_((ItemLike)CobblemonItems.GRASS_GEM);
        entries.m_246326_((ItemLike)CobblemonItems.ELECTRIC_GEM);
        entries.m_246326_((ItemLike)CobblemonItems.ICE_GEM);
        entries.m_246326_((ItemLike)CobblemonItems.FIGHTING_GEM);
        entries.m_246326_((ItemLike)CobblemonItems.POISON_GEM);
        entries.m_246326_((ItemLike)CobblemonItems.GROUND_GEM);
        entries.m_246326_((ItemLike)CobblemonItems.FLYING_GEM);
        entries.m_246326_((ItemLike)CobblemonItems.PSYCHIC_GEM);
        entries.m_246326_((ItemLike)CobblemonItems.BUG_GEM);
        entries.m_246326_((ItemLike)CobblemonItems.ROCK_GEM);
        entries.m_246326_((ItemLike)CobblemonItems.GHOST_GEM);
        entries.m_246326_((ItemLike)CobblemonItems.DRAGON_GEM);
        entries.m_246326_((ItemLike)CobblemonItems.DARK_GEM);
        entries.m_246326_((ItemLike)CobblemonItems.STEEL_GEM);
        entries.m_246326_((ItemLike)CobblemonItems.FAIRY_GEM);
    }

    private final void blockEntries(CreativeModeTab.ItemDisplayParameters displayContext, CreativeModeTab.Output entries) {
        entries.m_246326_((ItemLike)CobblemonItems.RESTORATION_TANK);
        entries.m_246326_((ItemLike)CobblemonItems.FOSSIL_ANALYZER);
        entries.m_246326_((ItemLike)CobblemonItems.MONITOR);
        entries.m_246326_((ItemLike)CobblemonItems.PC);
        entries.m_246326_((ItemLike)CobblemonItems.HEALING_MACHINE);
        entries.m_246326_((ItemLike)CobblemonItems.PASTURE);
        entries.m_246326_((ItemLike)CobblemonItems.GILDED_CHEST);
        entries.m_246326_((ItemLike)CobblemonItems.YELLOW_GILDED_CHEST);
        entries.m_246326_((ItemLike)CobblemonItems.GREEN_GILDED_CHEST);
        entries.m_246326_((ItemLike)CobblemonItems.BLUE_GILDED_CHEST);
        entries.m_246326_((ItemLike)CobblemonItems.PINK_GILDED_CHEST);
        entries.m_246326_((ItemLike)CobblemonItems.BLACK_GILDED_CHEST);
        entries.m_246326_((ItemLike)CobblemonItems.WHITE_GILDED_CHEST);
        entries.m_246326_((ItemLike)CobblemonItems.GIMMIGHOUL_CHEST);
        entries.m_246326_((ItemLike)CobblemonItems.RELIC_COIN_POUCH);
        entries.m_246326_((ItemLike)CobblemonItems.RELIC_COIN_SACK);
        entries.m_246326_((ItemLike)CobblemonItems.DISPLAY_CASE);
        entries.m_246326_((ItemLike)CobblemonItems.APRICORN_LOG);
        entries.m_246326_((ItemLike)CobblemonItems.APRICORN_WOOD);
        entries.m_246326_((ItemLike)CobblemonItems.STRIPPED_APRICORN_LOG);
        entries.m_246326_((ItemLike)CobblemonItems.STRIPPED_APRICORN_WOOD);
        entries.m_246326_((ItemLike)CobblemonItems.APRICORN_PLANKS);
        entries.m_246326_((ItemLike)CobblemonItems.APRICORN_STAIRS);
        entries.m_246326_((ItemLike)CobblemonItems.APRICORN_SLAB);
        entries.m_246326_((ItemLike)CobblemonItems.APRICORN_FENCE);
        entries.m_246326_((ItemLike)CobblemonItems.APRICORN_FENCE_GATE);
        entries.m_246326_((ItemLike)CobblemonItems.APRICORN_DOOR);
        entries.m_246326_((ItemLike)CobblemonItems.APRICORN_TRAPDOOR);
        entries.m_246326_((ItemLike)CobblemonItems.APRICORN_BUTTON);
        entries.m_246326_((ItemLike)CobblemonItems.APRICORN_PRESSURE_PLATE);
        entries.m_246326_((ItemLike)CobblemonItems.APRICORN_SIGN);
        entries.m_246326_((ItemLike)CobblemonItems.APRICORN_HANGING_SIGN);
        entries.m_246326_((ItemLike)CobblemonItems.APRICORN_LEAVES);
        entries.m_246326_((ItemLike)CobblemonItems.TUMBLESTONE_BLOCK);
        entries.m_246326_((ItemLike)CobblemonItems.BLACK_TUMBLESTONE_BLOCK);
        entries.m_246326_((ItemLike)CobblemonItems.SKY_TUMBLESTONE_BLOCK);
        entries.m_246326_((ItemLike)CobblemonItems.DAWN_STONE_ORE);
        entries.m_246326_((ItemLike)CobblemonItems.DEEPSLATE_DAWN_STONE_ORE);
        entries.m_246326_((ItemLike)CobblemonItems.DUSK_STONE_ORE);
        entries.m_246326_((ItemLike)CobblemonItems.DEEPSLATE_DUSK_STONE_ORE);
        entries.m_246326_((ItemLike)CobblemonItems.FIRE_STONE_ORE);
        entries.m_246326_((ItemLike)CobblemonItems.DEEPSLATE_FIRE_STONE_ORE);
        entries.m_246326_((ItemLike)CobblemonItems.NETHER_FIRE_STONE_ORE);
        entries.m_246326_((ItemLike)CobblemonItems.ICE_STONE_ORE);
        entries.m_246326_((ItemLike)CobblemonItems.DEEPSLATE_ICE_STONE_ORE);
        entries.m_246326_((ItemLike)CobblemonItems.LEAF_STONE_ORE);
        entries.m_246326_((ItemLike)CobblemonItems.DEEPSLATE_LEAF_STONE_ORE);
        entries.m_246326_((ItemLike)CobblemonItems.MOON_STONE_ORE);
        entries.m_246326_((ItemLike)CobblemonItems.DEEPSLATE_MOON_STONE_ORE);
        entries.m_246326_((ItemLike)CobblemonItems.DRIPSTONE_MOON_STONE_ORE);
        entries.m_246326_((ItemLike)CobblemonItems.SHINY_STONE_ORE);
        entries.m_246326_((ItemLike)CobblemonItems.DEEPSLATE_SHINY_STONE_ORE);
        entries.m_246326_((ItemLike)CobblemonItems.SUN_STONE_ORE);
        entries.m_246326_((ItemLike)CobblemonItems.DEEPSLATE_SUN_STONE_ORE);
        entries.m_246326_((ItemLike)CobblemonItems.TERRACOTTA_SUN_STONE_ORE);
        entries.m_246326_((ItemLike)CobblemonItems.THUNDER_STONE_ORE);
        entries.m_246326_((ItemLike)CobblemonItems.DEEPSLATE_THUNDER_STONE_ORE);
        entries.m_246326_((ItemLike)CobblemonItems.WATER_STONE_ORE);
        entries.m_246326_((ItemLike)CobblemonItems.DEEPSLATE_WATER_STONE_ORE);
    }

    private final void consumableEntries(CreativeModeTab.ItemDisplayParameters displayContext, CreativeModeTab.Output entries) {
        entries.m_246326_((ItemLike)CobblemonItems.ROASTED_LEEK);
        entries.m_246326_((ItemLike)CobblemonItems.LEEK_AND_POTATO_STEW);
        entries.m_246326_((ItemLike)CobblemonItems.BRAISED_VIVICHOKE);
        entries.m_246326_((ItemLike)CobblemonItems.VIVICHOKE_DIP);
        entries.m_246326_((ItemLike)CobblemonItems.BERRY_JUICE);
        entries.m_246326_((ItemLike)CobblemonItems.REMEDY);
        entries.m_246326_((ItemLike)CobblemonItems.FINE_REMEDY);
        entries.m_246326_((ItemLike)CobblemonItems.SUPERB_REMEDY);
        entries.m_246326_((ItemLike)CobblemonItems.HEAL_POWDER);
        entries.m_246326_((ItemLike)CobblemonItems.MEDICINAL_BREW);
        entries.m_246326_((ItemLike)CobblemonItems.POTION);
        entries.m_246326_((ItemLike)CobblemonItems.SUPER_POTION);
        entries.m_246326_((ItemLike)CobblemonItems.HYPER_POTION);
        entries.m_246326_((ItemLike)CobblemonItems.MAX_POTION);
        entries.m_246326_((ItemLike)CobblemonItems.FULL_RESTORE);
        entries.m_246326_((ItemLike)CobblemonItems.ANTIDOTE);
        entries.m_246326_((ItemLike)CobblemonItems.AWAKENING);
        entries.m_246326_((ItemLike)CobblemonItems.BURN_HEAL);
        entries.m_246326_((ItemLike)CobblemonItems.ICE_HEAL);
        entries.m_246326_((ItemLike)CobblemonItems.PARALYZE_HEAL);
        entries.m_246326_((ItemLike)CobblemonItems.FULL_HEAL);
        entries.m_246326_((ItemLike)CobblemonItems.ETHER);
        entries.m_246326_((ItemLike)CobblemonItems.MAX_ETHER);
        entries.m_246326_((ItemLike)CobblemonItems.ELIXIR);
        entries.m_246326_((ItemLike)CobblemonItems.MAX_ELIXIR);
        entries.m_246326_((ItemLike)CobblemonItems.REVIVE);
        entries.m_246326_((ItemLike)CobblemonItems.MAX_REVIVE);
        entries.m_246326_((ItemLike)CobblemonItems.X_ATTACK);
        entries.m_246326_((ItemLike)CobblemonItems.X_DEFENSE);
        entries.m_246326_((ItemLike)CobblemonItems.X_SP_ATK);
        entries.m_246326_((ItemLike)CobblemonItems.X_SP_DEF);
        entries.m_246326_((ItemLike)CobblemonItems.X_SPEED);
        entries.m_246326_((ItemLike)CobblemonItems.X_ACCURACY);
        entries.m_246326_((ItemLike)CobblemonItems.DIRE_HIT);
        entries.m_246326_((ItemLike)CobblemonItems.GUARD_SPEC);
        entries.m_246326_((ItemLike)CobblemonItems.HEALTH_FEATHER);
        entries.m_246326_((ItemLike)CobblemonItems.MUSCLE_FEATHER);
        entries.m_246326_((ItemLike)CobblemonItems.RESIST_FEATHER);
        entries.m_246326_((ItemLike)CobblemonItems.GENIUS_FEATHER);
        entries.m_246326_((ItemLike)CobblemonItems.CLEVER_FEATHER);
        entries.m_246326_((ItemLike)CobblemonItems.SWIFT_FEATHER);
        entries.m_246326_((ItemLike)CobblemonItems.HP_UP);
        entries.m_246326_((ItemLike)CobblemonItems.PROTEIN);
        entries.m_246326_((ItemLike)CobblemonItems.IRON);
        entries.m_246326_((ItemLike)CobblemonItems.CALCIUM);
        entries.m_246326_((ItemLike)CobblemonItems.ZINC);
        entries.m_246326_((ItemLike)CobblemonItems.CARBOS);
        entries.m_246326_((ItemLike)CobblemonItems.PP_UP);
        entries.m_246326_((ItemLike)CobblemonItems.PP_MAX);
        entries.m_246326_((ItemLike)CobblemonItems.EXPERIENCE_CANDY_XS);
        entries.m_246326_((ItemLike)CobblemonItems.EXPERIENCE_CANDY_S);
        entries.m_246326_((ItemLike)CobblemonItems.EXPERIENCE_CANDY_M);
        entries.m_246326_((ItemLike)CobblemonItems.EXPERIENCE_CANDY_L);
        entries.m_246326_((ItemLike)CobblemonItems.EXPERIENCE_CANDY_XL);
        entries.m_246326_((ItemLike)CobblemonItems.RARE_CANDY);
        entries.m_246326_((ItemLike)CobblemonItems.LONELY_MINT);
        entries.m_246326_((ItemLike)CobblemonItems.ADAMANT_MINT);
        entries.m_246326_((ItemLike)CobblemonItems.NAUGHTY_MINT);
        entries.m_246326_((ItemLike)CobblemonItems.BRAVE_MINT);
        entries.m_246326_((ItemLike)CobblemonItems.BOLD_MINT);
        entries.m_246326_((ItemLike)CobblemonItems.IMPISH_MINT);
        entries.m_246326_((ItemLike)CobblemonItems.LAX_MINT);
        entries.m_246326_((ItemLike)CobblemonItems.RELAXED_MINT);
        entries.m_246326_((ItemLike)CobblemonItems.MODEST_MINT);
        entries.m_246326_((ItemLike)CobblemonItems.MILD_MINT);
        entries.m_246326_((ItemLike)CobblemonItems.RASH_MINT);
        entries.m_246326_((ItemLike)CobblemonItems.QUIET_MINT);
        entries.m_246326_((ItemLike)CobblemonItems.CALM_MINT);
        entries.m_246326_((ItemLike)CobblemonItems.GENTLE_MINT);
        entries.m_246326_((ItemLike)CobblemonItems.CAREFUL_MINT);
        entries.m_246326_((ItemLike)CobblemonItems.SASSY_MINT);
        entries.m_246326_((ItemLike)CobblemonItems.TIMID_MINT);
        entries.m_246326_((ItemLike)CobblemonItems.HASTY_MINT);
        entries.m_246326_((ItemLike)CobblemonItems.JOLLY_MINT);
        entries.m_246326_((ItemLike)CobblemonItems.NAIVE_MINT);
        entries.m_246326_((ItemLike)CobblemonItems.SERIOUS_MINT);
        entries.m_246326_((ItemLike)CobblemonItems.ABILITY_CAPSULE);
        entries.m_246326_((ItemLike)CobblemonItems.ABILITY_PATCH);
    }

    private final void evolutionItemEntries(CreativeModeTab.ItemDisplayParameters displayContext, CreativeModeTab.Output entries) {
        entries.m_246326_((ItemLike)CobblemonItems.FIRE_STONE);
        entries.m_246326_((ItemLike)CobblemonItems.WATER_STONE);
        entries.m_246326_((ItemLike)CobblemonItems.THUNDER_STONE);
        entries.m_246326_((ItemLike)CobblemonItems.LEAF_STONE);
        entries.m_246326_((ItemLike)CobblemonItems.MOON_STONE);
        entries.m_246326_((ItemLike)CobblemonItems.SUN_STONE);
        entries.m_246326_((ItemLike)CobblemonItems.SHINY_STONE);
        entries.m_246326_((ItemLike)CobblemonItems.DUSK_STONE);
        entries.m_246326_((ItemLike)CobblemonItems.DAWN_STONE);
        entries.m_246326_((ItemLike)CobblemonItems.ICE_STONE);
        entries.m_246326_((ItemLike)CobblemonItems.LINK_CABLE);
        entries.m_246326_((ItemLike)CobblemonItems.KINGS_ROCK);
        entries.m_246326_((ItemLike)CobblemonItems.GALARICA_CUFF);
        entries.m_246326_((ItemLike)CobblemonItems.GALARICA_WREATH);
        entries.m_246326_((ItemLike)CobblemonItems.METAL_COAT);
        entries.m_246326_((ItemLike)CobblemonItems.BLACK_AUGURITE);
        entries.m_246326_((ItemLike)CobblemonItems.PROTECTOR);
        entries.m_246326_((ItemLike)CobblemonItems.OVAL_STONE);
        entries.m_246326_((ItemLike)CobblemonItems.DRAGON_SCALE);
        entries.m_246326_((ItemLike)CobblemonItems.ELECTIRIZER);
        entries.m_246326_((ItemLike)CobblemonItems.MAGMARIZER);
        entries.m_246326_((ItemLike)CobblemonItems.UPGRADE);
        entries.m_246326_((ItemLike)CobblemonItems.DUBIOUS_DISC);
        entries.m_246326_((ItemLike)CobblemonItems.RAZOR_FANG);
        entries.m_246326_((ItemLike)CobblemonItems.RAZOR_CLAW);
        entries.m_246326_((ItemLike)CobblemonItems.PEAT_BLOCK);
        entries.m_246326_((ItemLike)CobblemonItems.PRISM_SCALE);
        entries.m_246326_((ItemLike)CobblemonItems.REAPER_CLOTH);
        entries.m_246326_((ItemLike)CobblemonItems.DEEP_SEA_TOOTH);
        entries.m_246326_((ItemLike)CobblemonItems.DEEP_SEA_SCALE);
        entries.m_246326_((ItemLike)CobblemonItems.SACHET);
        entries.m_246326_((ItemLike)CobblemonItems.WHIPPED_DREAM);
        entries.m_246326_((ItemLike)CobblemonItems.TART_APPLE);
        entries.m_246326_((ItemLike)CobblemonItems.SWEET_APPLE);
        entries.m_246326_((ItemLike)CobblemonItems.CRACKED_POT);
        entries.m_246326_((ItemLike)CobblemonItems.CHIPPED_POT);
        entries.m_246326_((ItemLike)CobblemonItems.MASTERPIECE_TEACUP);
        entries.m_246326_((ItemLike)CobblemonItems.UNREMARKABLE_TEACUP);
        entries.m_246326_((ItemLike)CobblemonItems.STRAWBERRY_SWEET);
        entries.m_246326_((ItemLike)CobblemonItems.LOVE_SWEET);
        entries.m_246326_((ItemLike)CobblemonItems.BERRY_SWEET);
        entries.m_246326_((ItemLike)CobblemonItems.CLOVER_SWEET);
        entries.m_246326_((ItemLike)CobblemonItems.FLOWER_SWEET);
        entries.m_246326_((ItemLike)CobblemonItems.STAR_SWEET);
        entries.m_246326_((ItemLike)CobblemonItems.RIBBON_SWEET);
        entries.m_246326_((ItemLike)CobblemonItems.AUSPICIOUS_ARMOR);
        entries.m_246326_((ItemLike)CobblemonItems.MALICIOUS_ARMOR);
    }

    private final void heldItemEntries(CreativeModeTab.ItemDisplayParameters displayContext, CreativeModeTab.Output entries) {
        entries.m_246326_((ItemLike)CobblemonItems.ABILITY_SHIELD);
        entries.m_246326_((ItemLike)CobblemonItems.ABSORB_BULB);
        entries.m_246326_((ItemLike)CobblemonItems.AIR_BALLOON);
        entries.m_246326_((ItemLike)CobblemonItems.ASSAULT_VEST);
        entries.m_246326_((ItemLike)CobblemonItems.BIG_ROOT);
        entries.m_246326_((ItemLike)CobblemonItems.BINDING_BAND);
        entries.m_246326_((ItemLike)CobblemonItems.BLACK_BELT);
        entries.m_246326_((ItemLike)CobblemonItems.BLACK_GLASSES);
        entries.m_246326_((ItemLike)CobblemonItems.BLACK_SLUDGE);
        entries.m_246326_((ItemLike)CobblemonItems.BLUNDER_POLICY);
        entries.m_246326_((ItemLike)CobblemonItems.BRIGHT_POWDER);
        entries.m_246326_((ItemLike)CobblemonItems.CELL_BATTERY);
        entries.m_246326_((ItemLike)CobblemonItems.CHARCOAL);
        entries.m_246326_((ItemLike)CobblemonItems.CHOICE_BAND);
        entries.m_246326_((ItemLike)CobblemonItems.CHOICE_SCARF);
        entries.m_246326_((ItemLike)CobblemonItems.CHOICE_SPECS);
        entries.m_246326_((ItemLike)CobblemonItems.CLEANSE_TAG);
        entries.m_246326_((ItemLike)CobblemonItems.COVERT_CLOAK);
        entries.m_246326_((ItemLike)CobblemonItems.DAMP_ROCK);
        entries.m_246326_((ItemLike)CobblemonItems.DEEP_SEA_SCALE);
        entries.m_246326_((ItemLike)CobblemonItems.DEEP_SEA_TOOTH);
        entries.m_246326_((ItemLike)CobblemonItems.DESTINY_KNOT);
        entries.m_246326_((ItemLike)CobblemonItems.DRAGON_FANG);
        entries.m_246326_((ItemLike)CobblemonItems.EJECT_BUTTON);
        entries.m_246326_((ItemLike)CobblemonItems.EVERSTONE);
        entries.m_246326_((ItemLike)CobblemonItems.EVIOLITE);
        entries.m_246326_((ItemLike)CobblemonItems.EXPERT_BELT);
        entries.m_246326_((ItemLike)CobblemonItems.EXP_SHARE);
        entries.m_246326_((ItemLike)CobblemonItems.FAIRY_FEATHER);
        entries.m_246326_((ItemLike)CobblemonItems.FLAME_ORB);
        entries.m_246326_((ItemLike)CobblemonItems.FLOAT_STONE);
        entries.m_246326_((ItemLike)CobblemonItems.FOCUS_BAND);
        entries.m_246326_((ItemLike)CobblemonItems.FOCUS_SASH);
        entries.m_246326_((ItemLike)CobblemonItems.HARD_STONE);
        entries.m_246326_((ItemLike)CobblemonItems.HEAT_ROCK);
        entries.m_246326_((ItemLike)CobblemonItems.HEAVY_DUTY_BOOTS);
        entries.m_246326_((ItemLike)CobblemonItems.ICY_ROCK);
        entries.m_246326_((ItemLike)CobblemonItems.IRON_BALL);
        entries.m_246326_((ItemLike)CobblemonItems.KINGS_ROCK);
        entries.m_246326_((ItemLike)CobblemonItems.LEFTOVERS);
        entries.m_246326_((ItemLike)CobblemonItems.LIFE_ORB);
        entries.m_246326_((ItemLike)CobblemonItems.LIGHT_BALL);
        entries.m_246326_((ItemLike)CobblemonItems.LIGHT_CLAY);
        entries.m_246326_((ItemLike)CobblemonItems.LOADED_DICE);
        entries.m_246326_((ItemLike)CobblemonItems.LUCKY_EGG);
        entries.m_246326_((ItemLike)CobblemonItems.MAGNET);
        entries.m_246326_((ItemLike)CobblemonItems.MENTAL_HERB);
        entries.m_246326_((ItemLike)CobblemonItems.METAL_COAT);
        entries.m_246326_((ItemLike)CobblemonItems.METAL_POWDER);
        entries.m_246326_((ItemLike)CobblemonItems.MIRACLE_SEED);
        entries.m_246326_((ItemLike)CobblemonItems.MIRROR_HERB);
        entries.m_246326_((ItemLike)CobblemonItems.MUSCLE_BAND);
        entries.m_246326_((ItemLike)CobblemonItems.MYSTIC_WATER);
        entries.m_246326_((ItemLike)CobblemonItems.NEVER_MELT_ICE);
        entries.m_246326_((ItemLike)CobblemonItems.POISON_BARB);
        entries.m_246326_((ItemLike)CobblemonItems.POWER_ANKLET);
        entries.m_246326_((ItemLike)CobblemonItems.POWER_BAND);
        entries.m_246326_((ItemLike)CobblemonItems.POWER_BELT);
        entries.m_246326_((ItemLike)CobblemonItems.POWER_BRACER);
        entries.m_246326_((ItemLike)CobblemonItems.POWER_LENS);
        entries.m_246326_((ItemLike)CobblemonItems.POWER_WEIGHT);
        entries.m_246326_((ItemLike)CobblemonItems.POWER_HERB);
        entries.m_246326_((ItemLike)CobblemonItems.QUICK_CLAW);
        entries.m_246326_((ItemLike)CobblemonItems.QUICK_POWDER);
        entries.m_246326_((ItemLike)CobblemonItems.RAZOR_CLAW);
        entries.m_246326_((ItemLike)CobblemonItems.RAZOR_FANG);
        entries.m_246326_((ItemLike)CobblemonItems.RED_CARD);
        entries.m_246326_((ItemLike)CobblemonItems.RING_TARGET);
        entries.m_246326_((ItemLike)CobblemonItems.ROCKY_HELMET);
        entries.m_246326_((ItemLike)CobblemonItems.SAFETY_GOGGLES);
        entries.m_246326_((ItemLike)CobblemonItems.SHARP_BEAK);
        entries.m_246326_((ItemLike)CobblemonItems.SHELL_BELL);
        entries.m_246326_((ItemLike)CobblemonItems.SILK_SCARF);
        entries.m_246326_((ItemLike)CobblemonItems.SILVER_POWDER);
        entries.m_246326_((ItemLike)CobblemonItems.SMOKE_BALL);
        entries.m_246326_((ItemLike)CobblemonItems.SMOOTH_ROCK);
        entries.m_246326_((ItemLike)CobblemonItems.SOFT_SAND);
        entries.m_246326_((ItemLike)CobblemonItems.SOOTHE_BELL);
        entries.m_246326_((ItemLike)CobblemonItems.SPELL_TAG);
        entries.m_246326_((ItemLike)CobblemonItems.STICKY_BARB);
        entries.m_246326_((ItemLike)CobblemonItems.TOXIC_ORB);
        entries.m_246326_((ItemLike)CobblemonItems.TWISTED_SPOON);
        entries.m_246326_((ItemLike)CobblemonItems.WEAKNESS_POLICY);
        entries.m_246326_((ItemLike)CobblemonItems.WHITE_HERB);
        entries.m_246326_((ItemLike)CobblemonItems.WISE_GLASSES);
        entries.m_246326_((ItemLike)CobblemonItems.MEDICINAL_LEEK);
        entries.m_246326_((ItemLike)Items.f_42500_);
        entries.m_246326_((ItemLike)Items.f_42452_);
        entries.m_246326_((ItemLike)CobblemonItems.NORMAL_GEM);
        entries.m_246326_((ItemLike)CobblemonItems.FIRE_GEM);
        entries.m_246326_((ItemLike)CobblemonItems.WATER_GEM);
        entries.m_246326_((ItemLike)CobblemonItems.GRASS_GEM);
        entries.m_246326_((ItemLike)CobblemonItems.ELECTRIC_GEM);
        entries.m_246326_((ItemLike)CobblemonItems.ICE_GEM);
        entries.m_246326_((ItemLike)CobblemonItems.FIGHTING_GEM);
        entries.m_246326_((ItemLike)CobblemonItems.POISON_GEM);
        entries.m_246326_((ItemLike)CobblemonItems.GROUND_GEM);
        entries.m_246326_((ItemLike)CobblemonItems.FLYING_GEM);
        entries.m_246326_((ItemLike)CobblemonItems.PSYCHIC_GEM);
        entries.m_246326_((ItemLike)CobblemonItems.BUG_GEM);
        entries.m_246326_((ItemLike)CobblemonItems.ROCK_GEM);
        entries.m_246326_((ItemLike)CobblemonItems.GHOST_GEM);
        entries.m_246326_((ItemLike)CobblemonItems.DRAGON_GEM);
        entries.m_246326_((ItemLike)CobblemonItems.DARK_GEM);
        entries.m_246326_((ItemLike)CobblemonItems.STEEL_GEM);
        entries.m_246326_((ItemLike)CobblemonItems.FAIRY_GEM);
    }

    private final void pokeballentries(CreativeModeTab.ItemDisplayParameters displayContext, CreativeModeTab.Output entries) {
        Iterable $this$forEach$iv = CobblemonItems.pokeBalls;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            ItemLike p0 = (ItemLike)element$iv;
            boolean bl = false;
            entries.m_246326_(p0);
        }
    }

    private final void foodInjections(Injector injector) {
        ItemLike itemLike = (ItemLike)CobblemonItems.MEDICINAL_LEEK;
        Item item = Items.f_42675_;
        Intrinsics.checkNotNullExpressionValue((Object)item, (String)"POISONOUS_POTATO");
        injector.putAfter(itemLike, (ItemLike)item);
        injector.putAfter((ItemLike)CobblemonItems.ROASTED_LEEK, (ItemLike)CobblemonItems.MEDICINAL_LEEK);
        injector.putAfter((ItemLike)CobblemonItems.BRAISED_VIVICHOKE, (ItemLike)CobblemonItems.ROASTED_LEEK);
        ItemLike itemLike2 = (ItemLike)CobblemonItems.LEEK_AND_POTATO_STEW;
        Item item2 = Items.f_42699_;
        Intrinsics.checkNotNullExpressionValue((Object)item2, (String)"RABBIT_STEW");
        injector.putAfter(itemLike2, (ItemLike)item2);
        injector.putAfter((ItemLike)CobblemonItems.VIVICHOKE_DIP, (ItemLike)CobblemonItems.LEEK_AND_POTATO_STEW);
    }

    private final void toolsAndUtilitiesInjections(Injector injector) {
        ItemLike itemLike = (ItemLike)CobblemonItems.APRICORN_BOAT;
        Item item = Items.f_244260_;
        Intrinsics.checkNotNullExpressionValue((Object)item, (String)"BAMBOO_CHEST_RAFT");
        injector.putAfter(itemLike, (ItemLike)item);
        injector.putAfter((ItemLike)CobblemonItems.APRICORN_CHEST_BOAT, (ItemLike)CobblemonItems.APRICORN_BOAT);
    }

    private final void ingredientsInjections(Injector injector) {
        ItemLike itemLike = (ItemLike)CobblemonItems.BYGONE_SHERD;
        Item item = Items.f_279636_;
        Intrinsics.checkNotNullExpressionValue((Object)item, (String)"SNORT_POTTERY_SHERD");
        injector.putAfter(itemLike, (ItemLike)item);
        injector.putAfter((ItemLike)CobblemonItems.CAPTURE_SHERD, (ItemLike)CobblemonItems.BYGONE_SHERD);
        injector.putAfter((ItemLike)CobblemonItems.DOME_SHERD, (ItemLike)CobblemonItems.CAPTURE_SHERD);
        injector.putAfter((ItemLike)CobblemonItems.HELIX_SHERD, (ItemLike)CobblemonItems.DOME_SHERD);
        injector.putAfter((ItemLike)CobblemonItems.NOSTALGIC_SHERD, (ItemLike)CobblemonItems.HELIX_SHERD);
        injector.putAfter((ItemLike)CobblemonItems.SUSPICIOUS_SHERD, (ItemLike)CobblemonItems.NOSTALGIC_SHERD);
        ItemLike itemLike2 = (ItemLike)CobblemonItems.AUTOMATON_ARMOR_TRIM_SMITHING_TEMPLATE;
        Item item2 = Items.f_266114_;
        Intrinsics.checkNotNullExpressionValue((Object)item2, (String)"SPIRE_ARMOR_TRIM_SMITHING_TEMPLATE");
        injector.putAfter(itemLike2, (ItemLike)item2);
    }

    public static final /* synthetic */ void access$foodInjections(CobblemonItemGroups $this, Injector injector) {
        $this.foodInjections(injector);
    }

    public static final /* synthetic */ void access$toolsAndUtilitiesInjections(CobblemonItemGroups $this, Injector injector) {
        $this.toolsAndUtilitiesInjections(injector);
    }

    public static final /* synthetic */ void access$ingredientsInjections(CobblemonItemGroups $this, Injector injector) {
        $this.ingredientsInjections(injector);
    }

    static {
        ResourceKey resourceKey = ResourceKey.m_135785_((ResourceKey)BuiltInRegistries.f_279662_.m_123023_(), (ResourceLocation)new ResourceLocation("food_and_drinks"));
        Intrinsics.checkNotNullExpressionValue((Object)resourceKey, (String)"of(Registries.ITEM_GROUP\u2026ifier(\"food_and_drinks\"))");
        FOOD_INJECTIONS = INSTANCE.inject((ResourceKey<CreativeModeTab>)resourceKey, (Function1<? super Injector, Unit>)((Function1)new Function1<Injector, Unit>((Object)INSTANCE){

            public final void invoke(@NotNull Injector p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                CobblemonItemGroups.access$foodInjections((CobblemonItemGroups)this.receiver, p0);
            }
        }));
        ResourceKey resourceKey2 = ResourceKey.m_135785_((ResourceKey)BuiltInRegistries.f_279662_.m_123023_(), (ResourceLocation)new ResourceLocation("tools_and_utilities"));
        Intrinsics.checkNotNullExpressionValue((Object)resourceKey2, (String)"of(Registries.ITEM_GROUP\u2026r(\"tools_and_utilities\"))");
        TOOLS_AND_UTILITIES_INJECTIONS = INSTANCE.inject((ResourceKey<CreativeModeTab>)resourceKey2, (Function1<? super Injector, Unit>)((Function1)new Function1<Injector, Unit>((Object)INSTANCE){

            public final void invoke(@NotNull Injector p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                CobblemonItemGroups.access$toolsAndUtilitiesInjections((CobblemonItemGroups)this.receiver, p0);
            }
        }));
        ResourceKey resourceKey3 = ResourceKey.m_135785_((ResourceKey)BuiltInRegistries.f_279662_.m_123023_(), (ResourceLocation)new ResourceLocation("ingredients"));
        Intrinsics.checkNotNullExpressionValue((Object)resourceKey3, (String)"of(Registries.ITEM_GROUP\u2026dentifier(\"ingredients\"))");
        INGREDIENTS_INJECTIONS = INSTANCE.inject((ResourceKey<CreativeModeTab>)resourceKey3, (Function1<? super Injector, Unit>)((Function1)new Function1<Injector, Unit>((Object)INSTANCE){

            public final void invoke(@NotNull Injector p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                CobblemonItemGroups.access$ingredientsInjections((CobblemonItemGroups)this.receiver, p0);
            }
        }));
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0007\bf\u0018\u00002\u00020\u0001J\u001f\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u0002H&\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u001f\u0010\b\u001a\u00020\u00052\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u0002H&\u00a2\u0006\u0004\b\b\u0010\u0007J\u0017\u0010\t\u001a\u00020\u00052\u0006\u0010\u0003\u001a\u00020\u0002H&\u00a2\u0006\u0004\b\t\u0010\nJ\u0017\u0010\u000b\u001a\u00020\u00052\u0006\u0010\u0003\u001a\u00020\u0002H&\u00a2\u0006\u0004\b\u000b\u0010\n\u00a8\u0006\f"}, d2={"Lcom/cobblemon/mod/common/item/group/CobblemonItemGroups$Injector;", "", "Lnet/minecraft/world/level/ItemLike;", "item", "target", "", "putAfter", "(Lnet/minecraft/world/level/ItemLike;Lnet/minecraft/world/level/ItemLike;)V", "putBefore", "putFirst", "(Lnet/minecraft/world/level/ItemLike;)V", "putLast", "common"})
    public static interface Injector {
        public void putFirst(@NotNull ItemLike var1);

        public void putBefore(@NotNull ItemLike var1, @NotNull ItemLike var2);

        public void putAfter(@NotNull ItemLike var1, @NotNull ItemLike var2);

        public void putLast(@NotNull ItemLike var1);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\r\b\u0086\b\u0018\u00002\u00020\u0001B5\u0012\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002\u0012\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u0012\u0006\u0010\u0012\u001a\u00020\n\u0012\b\b\u0002\u0010\u0013\u001a\u00020\r\u00a2\u0006\u0004\b(\u0010)J\u0016\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0004\u0010\u0005J\u0016\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u00c6\u0003\u00a2\u0006\u0004\b\b\u0010\tJ\u0010\u0010\u000b\u001a\u00020\nH\u00c6\u0003\u00a2\u0006\u0004\b\u000b\u0010\fJ\u0010\u0010\u000e\u001a\u00020\rH\u00c6\u0003\u00a2\u0006\u0004\b\u000e\u0010\u000fJD\u0010\u0014\u001a\u00020\u00002\u000e\b\u0002\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00030\u00022\u000e\b\u0002\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\b\b\u0002\u0010\u0012\u001a\u00020\n2\b\b\u0002\u0010\u0013\u001a\u00020\rH\u00c6\u0001\u00a2\u0006\u0004\b\u0014\u0010\u0015J\u001a\u0010\u0018\u001a\u00020\u00172\b\u0010\u0016\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003\u00a2\u0006\u0004\b\u0018\u0010\u0019J\u0010\u0010\u001b\u001a\u00020\u001aH\u00d6\u0001\u00a2\u0006\u0004\b\u001b\u0010\u001cJ\u0010\u0010\u001e\u001a\u00020\u001dH\u00d6\u0001\u00a2\u0006\u0004\b\u001e\u0010\u001fR\u001d\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00070\u00068\u0006\u00a2\u0006\f\n\u0004\b\u0011\u0010 \u001a\u0004\b!\u0010\tR\u0017\u0010\u0013\u001a\u00020\r8\u0006\u00a2\u0006\f\n\u0004\b\u0013\u0010\"\u001a\u0004\b#\u0010\u000fR\u0017\u0010\u0012\u001a\u00020\n8\u0006\u00a2\u0006\f\n\u0004\b\u0012\u0010$\u001a\u0004\b%\u0010\fR\u001d\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00030\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0010\u0010&\u001a\u0004\b'\u0010\u0005\u00a8\u0006*"}, d2={"Lcom/cobblemon/mod/common/item/group/CobblemonItemGroups$ItemGroupHolder;", "", "Lnet/minecraft/resources/ResourceKey;", "Lnet/minecraft/world/item/CreativeModeTab;", "component1", "()Lnet/minecraft/resources/ResourceKey;", "Lkotlin/Function0;", "Lnet/minecraft/world/item/ItemStack;", "component2", "()Lkotlin/jvm/functions/Function0;", "Lnet/minecraft/item/ItemGroup$EntryCollector;", "component3", "()Lnet/minecraft/world/item/CreativeModeTab$DisplayItemsGenerator;", "Lnet/minecraft/network/chat/Component;", "component4", "()Lnet/minecraft/network/chat/Component;", "key", "displayIconProvider", "entryCollector", "displayName", "copy", "(Lnet/minecraft/resources/ResourceKey;Lkotlin/jvm/functions/Function0;Lnet/minecraft/world/item/CreativeModeTab$DisplayItemsGenerator;Lnet/minecraft/network/chat/Component;)Lcom/cobblemon/mod/common/item/group/CobblemonItemGroups$ItemGroupHolder;", "other", "", "equals", "(Ljava/lang/Object;)Z", "", "hashCode", "()I", "", "toString", "()Ljava/lang/String;", "Lkotlin/jvm/functions/Function0;", "getDisplayIconProvider", "Lnet/minecraft/network/chat/Component;", "getDisplayName", "Lnet/minecraft/world/item/CreativeModeTab$DisplayItemsGenerator;", "getEntryCollector", "Lnet/minecraft/resources/ResourceKey;", "getKey", "<init>", "(Lnet/minecraft/resources/ResourceKey;Lkotlin/jvm/functions/Function0;Lnet/minecraft/world/item/CreativeModeTab$DisplayItemsGenerator;Lnet/minecraft/network/chat/Component;)V", "common"})
    public static final class ItemGroupHolder {
        @NotNull
        private final ResourceKey<CreativeModeTab> key;
        @NotNull
        private final Function0<ItemStack> displayIconProvider;
        @NotNull
        private final CreativeModeTab.DisplayItemsGenerator entryCollector;
        @NotNull
        private final Component displayName;

        public ItemGroupHolder(@NotNull ResourceKey<CreativeModeTab> key, @NotNull Function0<ItemStack> displayIconProvider, @NotNull CreativeModeTab.DisplayItemsGenerator entryCollector, @NotNull Component displayName) {
            Intrinsics.checkNotNullParameter(key, (String)"key");
            Intrinsics.checkNotNullParameter(displayIconProvider, (String)"displayIconProvider");
            Intrinsics.checkNotNullParameter((Object)entryCollector, (String)"entryCollector");
            Intrinsics.checkNotNullParameter((Object)displayName, (String)"displayName");
            this.key = key;
            this.displayIconProvider = displayIconProvider;
            this.entryCollector = entryCollector;
            this.displayName = displayName;
        }

        public /* synthetic */ ItemGroupHolder(ResourceKey resourceKey, Function0 function0, CreativeModeTab.DisplayItemsGenerator displayItemsGenerator, Component component, int n, DefaultConstructorMarker defaultConstructorMarker) {
            if ((n & 8) != 0) {
                MutableComponent mutableComponent = Component.m_237115_((String)("itemGroup." + resourceKey.m_135782_().m_135827_() + "." + resourceKey.m_135782_().m_135815_()));
                Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"translatable(\"itemGroup.\u2026pace}.${key.value.path}\")");
                component = (Component)mutableComponent;
            }
            this((ResourceKey<CreativeModeTab>)resourceKey, (Function0<ItemStack>)function0, displayItemsGenerator, component);
        }

        @NotNull
        public final ResourceKey<CreativeModeTab> getKey() {
            return this.key;
        }

        @NotNull
        public final Function0<ItemStack> getDisplayIconProvider() {
            return this.displayIconProvider;
        }

        @NotNull
        public final CreativeModeTab.DisplayItemsGenerator getEntryCollector() {
            return this.entryCollector;
        }

        @NotNull
        public final Component getDisplayName() {
            return this.displayName;
        }

        @NotNull
        public final ResourceKey<CreativeModeTab> component1() {
            return this.key;
        }

        @NotNull
        public final Function0<ItemStack> component2() {
            return this.displayIconProvider;
        }

        @NotNull
        public final CreativeModeTab.DisplayItemsGenerator component3() {
            return this.entryCollector;
        }

        @NotNull
        public final Component component4() {
            return this.displayName;
        }

        @NotNull
        public final ItemGroupHolder copy(@NotNull ResourceKey<CreativeModeTab> key, @NotNull Function0<ItemStack> displayIconProvider, @NotNull CreativeModeTab.DisplayItemsGenerator entryCollector, @NotNull Component displayName) {
            Intrinsics.checkNotNullParameter(key, (String)"key");
            Intrinsics.checkNotNullParameter(displayIconProvider, (String)"displayIconProvider");
            Intrinsics.checkNotNullParameter((Object)entryCollector, (String)"entryCollector");
            Intrinsics.checkNotNullParameter((Object)displayName, (String)"displayName");
            return new ItemGroupHolder(key, displayIconProvider, entryCollector, displayName);
        }

        public static /* synthetic */ ItemGroupHolder copy$default(ItemGroupHolder itemGroupHolder, ResourceKey resourceKey, Function0 function0, CreativeModeTab.DisplayItemsGenerator displayItemsGenerator, Component component, int n, Object object) {
            if ((n & 1) != 0) {
                resourceKey = itemGroupHolder.key;
            }
            if ((n & 2) != 0) {
                function0 = itemGroupHolder.displayIconProvider;
            }
            if ((n & 4) != 0) {
                displayItemsGenerator = itemGroupHolder.entryCollector;
            }
            if ((n & 8) != 0) {
                component = itemGroupHolder.displayName;
            }
            return itemGroupHolder.copy(resourceKey, function0, displayItemsGenerator, component);
        }

        @NotNull
        public String toString() {
            return "ItemGroupHolder(key=" + this.key + ", displayIconProvider=" + this.displayIconProvider + ", entryCollector=" + this.entryCollector + ", displayName=" + this.displayName + ")";
        }

        public int hashCode() {
            int result = this.key.hashCode();
            result = result * 31 + this.displayIconProvider.hashCode();
            result = result * 31 + this.entryCollector.hashCode();
            result = result * 31 + this.displayName.hashCode();
            return result;
        }

        public boolean equals(@Nullable Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof ItemGroupHolder)) {
                return false;
            }
            ItemGroupHolder itemGroupHolder = (ItemGroupHolder)other;
            if (!Intrinsics.areEqual(this.key, itemGroupHolder.key)) {
                return false;
            }
            if (!Intrinsics.areEqual(this.displayIconProvider, itemGroupHolder.displayIconProvider)) {
                return false;
            }
            if (!Intrinsics.areEqual((Object)this.entryCollector, (Object)itemGroupHolder.entryCollector)) {
                return false;
            }
            return Intrinsics.areEqual((Object)this.displayName, (Object)itemGroupHolder.displayName);
        }
    }
}

