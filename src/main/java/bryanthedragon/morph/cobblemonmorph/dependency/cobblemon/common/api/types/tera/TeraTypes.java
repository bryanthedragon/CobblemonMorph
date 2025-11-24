/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.JvmStatic
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.random.Random
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.tera;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.ElementalType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.ElementalTypes;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.tera.TeraType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.tera.elemental.ElementalTypeTeraType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.tera.gimmick.StellarTeraType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.random.Random;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b?\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\bS\u0010\u0018J\u001f\u0010\u0006\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0002\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u0017\u0010\t\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\bH\u0007\u00a2\u0006\u0004\b\t\u0010\nJ\u0019\u0010\f\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0003\u001a\u00020\u000bH\u0007\u00a2\u0006\u0004\b\f\u0010\rJ\u0019\u0010\f\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0007\u00a2\u0006\u0004\b\f\u0010\u000eJ\u0017\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u000fH\u0007\u00a2\u0006\u0004\b\u0011\u0010\u0012R \u0010\u0013\u001a\u00020\u00048\u0006X\u0087\u0004\u00a2\u0006\u0012\n\u0004\b\u0013\u0010\u0014\u0012\u0004\b\u0017\u0010\u0018\u001a\u0004\b\u0015\u0010\u0016R \u0010\u0019\u001a\u00020\u00048\u0006X\u0087\u0004\u00a2\u0006\u0012\n\u0004\b\u0019\u0010\u0014\u0012\u0004\b\u001b\u0010\u0018\u001a\u0004\b\u001a\u0010\u0016R \u0010\u001c\u001a\u00020\u00048\u0006X\u0087\u0004\u00a2\u0006\u0012\n\u0004\b\u001c\u0010\u0014\u0012\u0004\b\u001e\u0010\u0018\u001a\u0004\b\u001d\u0010\u0016R \u0010\u001f\u001a\u00020\u00048\u0006X\u0087\u0004\u00a2\u0006\u0012\n\u0004\b\u001f\u0010\u0014\u0012\u0004\b!\u0010\u0018\u001a\u0004\b \u0010\u0016R \u0010\"\u001a\u00020\u00048\u0006X\u0087\u0004\u00a2\u0006\u0012\n\u0004\b\"\u0010\u0014\u0012\u0004\b$\u0010\u0018\u001a\u0004\b#\u0010\u0016R \u0010%\u001a\u00020\u00048\u0006X\u0087\u0004\u00a2\u0006\u0012\n\u0004\b%\u0010\u0014\u0012\u0004\b'\u0010\u0018\u001a\u0004\b&\u0010\u0016R \u0010(\u001a\u00020\u00048\u0006X\u0087\u0004\u00a2\u0006\u0012\n\u0004\b(\u0010\u0014\u0012\u0004\b*\u0010\u0018\u001a\u0004\b)\u0010\u0016R \u0010+\u001a\u00020\u00048\u0006X\u0087\u0004\u00a2\u0006\u0012\n\u0004\b+\u0010\u0014\u0012\u0004\b-\u0010\u0018\u001a\u0004\b,\u0010\u0016R \u0010.\u001a\u00020\u00048\u0006X\u0087\u0004\u00a2\u0006\u0012\n\u0004\b.\u0010\u0014\u0012\u0004\b0\u0010\u0018\u001a\u0004\b/\u0010\u0016R \u00101\u001a\u00020\u00048\u0006X\u0087\u0004\u00a2\u0006\u0012\n\u0004\b1\u0010\u0014\u0012\u0004\b3\u0010\u0018\u001a\u0004\b2\u0010\u0016R \u00104\u001a\u00020\u00048\u0006X\u0087\u0004\u00a2\u0006\u0012\n\u0004\b4\u0010\u0014\u0012\u0004\b6\u0010\u0018\u001a\u0004\b5\u0010\u0016R \u00107\u001a\u00020\u00048\u0006X\u0087\u0004\u00a2\u0006\u0012\n\u0004\b7\u0010\u0014\u0012\u0004\b9\u0010\u0018\u001a\u0004\b8\u0010\u0016R \u0010:\u001a\u00020\u00048\u0006X\u0087\u0004\u00a2\u0006\u0012\n\u0004\b:\u0010\u0014\u0012\u0004\b<\u0010\u0018\u001a\u0004\b;\u0010\u0016R \u0010=\u001a\u00020\u00048\u0006X\u0087\u0004\u00a2\u0006\u0012\n\u0004\b=\u0010\u0014\u0012\u0004\b?\u0010\u0018\u001a\u0004\b>\u0010\u0016R \u0010@\u001a\u00020\u00048\u0006X\u0087\u0004\u00a2\u0006\u0012\n\u0004\b@\u0010\u0014\u0012\u0004\bB\u0010\u0018\u001a\u0004\bA\u0010\u0016R \u0010C\u001a\u00020\u00048\u0006X\u0087\u0004\u00a2\u0006\u0012\n\u0004\bC\u0010\u0014\u0012\u0004\bE\u0010\u0018\u001a\u0004\bD\u0010\u0016R \u0010F\u001a\u00020\u00048\u0006X\u0087\u0004\u00a2\u0006\u0012\n\u0004\bF\u0010\u0014\u0012\u0004\bH\u0010\u0018\u001a\u0004\bG\u0010\u0016R \u0010I\u001a\u00020\u00048\u0006X\u0087\u0004\u00a2\u0006\u0012\n\u0004\bI\u0010\u0014\u0012\u0004\bK\u0010\u0018\u001a\u0004\bJ\u0010\u0016R \u0010L\u001a\u00020\u00048\u0006X\u0087\u0004\u00a2\u0006\u0012\n\u0004\bL\u0010\u0014\u0012\u0004\bN\u0010\u0018\u001a\u0004\bM\u0010\u0016R0\u0010Q\u001a\u001e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00040Oj\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0004`P8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\bQ\u0010R\u00a8\u0006T"}, d2={"Lcom/cobblemon/mod/common/api/types/tera/TeraTypes;", "", "Lnet/minecraft/resources/ResourceLocation;", "id", "Lcom/cobblemon/mod/common/api/types/tera/TeraType;", "type", "create", "(Lnet/minecraft/resources/ResourceLocation;Lcom/cobblemon/mod/common/api/types/tera/TeraType;)Lcom/cobblemon/mod/common/api/types/tera/TeraType;", "Lcom/cobblemon/mod/common/api/types/ElementalType;", "forElementalType", "(Lcom/cobblemon/mod/common/api/types/ElementalType;)Lcom/cobblemon/mod/common/api/types/tera/TeraType;", "", "get", "(Ljava/lang/String;)Lcom/cobblemon/mod/common/api/types/tera/TeraType;", "(Lnet/minecraft/resources/ResourceLocation;)Lcom/cobblemon/mod/common/api/types/tera/TeraType;", "", "legalOnly", "random", "(Z)Lcom/cobblemon/mod/common/api/types/tera/TeraType;", "BUG", "Lcom/cobblemon/mod/common/api/types/tera/TeraType;", "getBUG", "()Lcom/cobblemon/mod/common/api/types/tera/TeraType;", "getBUG$annotations", "()V", "DARK", "getDARK", "getDARK$annotations", "DRAGON", "getDRAGON", "getDRAGON$annotations", "ELECTRIC", "getELECTRIC", "getELECTRIC$annotations", "FAIRY", "getFAIRY", "getFAIRY$annotations", "FIGHTING", "getFIGHTING", "getFIGHTING$annotations", "FIRE", "getFIRE", "getFIRE$annotations", "FLYING", "getFLYING", "getFLYING$annotations", "GHOST", "getGHOST", "getGHOST$annotations", "GRASS", "getGRASS", "getGRASS$annotations", "GROUND", "getGROUND", "getGROUND$annotations", "ICE", "getICE", "getICE$annotations", "NORMAL", "getNORMAL", "getNORMAL$annotations", "POISON", "getPOISON", "getPOISON$annotations", "PSYCHIC", "getPSYCHIC", "getPSYCHIC$annotations", "ROCK", "getROCK", "getROCK$annotations", "STEEL", "getSTEEL", "getSTEEL$annotations", "STELLAR", "getSTELLAR", "getSTELLAR$annotations", "WATER", "getWATER", "getWATER$annotations", "Ljava/util/HashMap;", "Lkotlin/collections/HashMap;", "types", "Ljava/util/HashMap;", "<init>", "common"})
@SourceDebugExtension(value={"SMAP\nTeraTypes.kt\nKotlin\n*S Kotlin\n*F\n+ 1 TeraTypes.kt\ncom/cobblemon/mod/common/api/types/tera/TeraTypes\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,128:1\n766#2:129\n857#2,2:130\n*S KotlinDebug\n*F\n+ 1 TeraTypes.kt\ncom/cobblemon/mod/common/api/types/tera/TeraTypes\n*L\n92#1:129\n92#1:130,2\n*E\n"})
public final class TeraTypes {
    @NotNull
    public static final TeraTypes INSTANCE = new TeraTypes();
    @NotNull
    private static final HashMap<ResourceLocation, TeraType> types = new HashMap();
    @NotNull
    private static final TeraType NORMAL = INSTANCE.create(MiscUtilsKt.cobblemonResource("normal"), new ElementalTypeTeraType(ElementalTypes.INSTANCE.getNORMAL()));
    @NotNull
    private static final TeraType FIRE = INSTANCE.create(MiscUtilsKt.cobblemonResource("fire"), new ElementalTypeTeraType(ElementalTypes.INSTANCE.getFIRE()));
    @NotNull
    private static final TeraType WATER = INSTANCE.create(MiscUtilsKt.cobblemonResource("water"), new ElementalTypeTeraType(ElementalTypes.INSTANCE.getWATER()));
    @NotNull
    private static final TeraType GRASS = INSTANCE.create(MiscUtilsKt.cobblemonResource("grass"), new ElementalTypeTeraType(ElementalTypes.INSTANCE.getGRASS()));
    @NotNull
    private static final TeraType ELECTRIC = INSTANCE.create(MiscUtilsKt.cobblemonResource("electric"), new ElementalTypeTeraType(ElementalTypes.INSTANCE.getELECTRIC()));
    @NotNull
    private static final TeraType ICE = INSTANCE.create(MiscUtilsKt.cobblemonResource("ice"), new ElementalTypeTeraType(ElementalTypes.INSTANCE.getICE()));
    @NotNull
    private static final TeraType FIGHTING = INSTANCE.create(MiscUtilsKt.cobblemonResource("fighting"), new ElementalTypeTeraType(ElementalTypes.INSTANCE.getFIGHTING()));
    @NotNull
    private static final TeraType POISON = INSTANCE.create(MiscUtilsKt.cobblemonResource("poison"), new ElementalTypeTeraType(ElementalTypes.INSTANCE.getPOISON()));
    @NotNull
    private static final TeraType GROUND = INSTANCE.create(MiscUtilsKt.cobblemonResource("ground"), new ElementalTypeTeraType(ElementalTypes.INSTANCE.getGROUND()));
    @NotNull
    private static final TeraType FLYING = INSTANCE.create(MiscUtilsKt.cobblemonResource("flying"), new ElementalTypeTeraType(ElementalTypes.INSTANCE.getFLYING()));
    @NotNull
    private static final TeraType PSYCHIC = INSTANCE.create(MiscUtilsKt.cobblemonResource("psychic"), new ElementalTypeTeraType(ElementalTypes.INSTANCE.getPSYCHIC()));
    @NotNull
    private static final TeraType BUG = INSTANCE.create(MiscUtilsKt.cobblemonResource("bug"), new ElementalTypeTeraType(ElementalTypes.INSTANCE.getBUG()));
    @NotNull
    private static final TeraType ROCK = INSTANCE.create(MiscUtilsKt.cobblemonResource("rock"), new ElementalTypeTeraType(ElementalTypes.INSTANCE.getROCK()));
    @NotNull
    private static final TeraType GHOST = INSTANCE.create(MiscUtilsKt.cobblemonResource("ghost"), new ElementalTypeTeraType(ElementalTypes.INSTANCE.getGHOST()));
    @NotNull
    private static final TeraType DRAGON = INSTANCE.create(MiscUtilsKt.cobblemonResource("dragon"), new ElementalTypeTeraType(ElementalTypes.INSTANCE.getDRAGON()));
    @NotNull
    private static final TeraType DARK = INSTANCE.create(MiscUtilsKt.cobblemonResource("dark"), new ElementalTypeTeraType(ElementalTypes.INSTANCE.getDARK()));
    @NotNull
    private static final TeraType STEEL = INSTANCE.create(MiscUtilsKt.cobblemonResource("steel"), new ElementalTypeTeraType(ElementalTypes.INSTANCE.getSTEEL()));
    @NotNull
    private static final TeraType FAIRY = INSTANCE.create(MiscUtilsKt.cobblemonResource("fairy"), new ElementalTypeTeraType(ElementalTypes.INSTANCE.getFAIRY()));
    @NotNull
    private static final TeraType STELLAR = INSTANCE.create(StellarTeraType.Companion.getID(), new StellarTeraType());

    private TeraTypes() {
    }

    @NotNull
    public static final TeraType getNORMAL() {
        return NORMAL;
    }

    @JvmStatic
    public static /* synthetic */ void getNORMAL$annotations() {
    }

    @NotNull
    public static final TeraType getFIRE() {
        return FIRE;
    }

    @JvmStatic
    public static /* synthetic */ void getFIRE$annotations() {
    }

    @NotNull
    public static final TeraType getWATER() {
        return WATER;
    }

    @JvmStatic
    public static /* synthetic */ void getWATER$annotations() {
    }

    @NotNull
    public static final TeraType getGRASS() {
        return GRASS;
    }

    @JvmStatic
    public static /* synthetic */ void getGRASS$annotations() {
    }

    @NotNull
    public static final TeraType getELECTRIC() {
        return ELECTRIC;
    }

    @JvmStatic
    public static /* synthetic */ void getELECTRIC$annotations() {
    }

    @NotNull
    public static final TeraType getICE() {
        return ICE;
    }

    @JvmStatic
    public static /* synthetic */ void getICE$annotations() {
    }

    @NotNull
    public static final TeraType getFIGHTING() {
        return FIGHTING;
    }

    @JvmStatic
    public static /* synthetic */ void getFIGHTING$annotations() {
    }

    @NotNull
    public static final TeraType getPOISON() {
        return POISON;
    }

    @JvmStatic
    public static /* synthetic */ void getPOISON$annotations() {
    }

    @NotNull
    public static final TeraType getGROUND() {
        return GROUND;
    }

    @JvmStatic
    public static /* synthetic */ void getGROUND$annotations() {
    }

    @NotNull
    public static final TeraType getFLYING() {
        return FLYING;
    }

    @JvmStatic
    public static /* synthetic */ void getFLYING$annotations() {
    }

    @NotNull
    public static final TeraType getPSYCHIC() {
        return PSYCHIC;
    }

    @JvmStatic
    public static /* synthetic */ void getPSYCHIC$annotations() {
    }

    @NotNull
    public static final TeraType getBUG() {
        return BUG;
    }

    @JvmStatic
    public static /* synthetic */ void getBUG$annotations() {
    }

    @NotNull
    public static final TeraType getROCK() {
        return ROCK;
    }

    @JvmStatic
    public static /* synthetic */ void getROCK$annotations() {
    }

    @NotNull
    public static final TeraType getGHOST() {
        return GHOST;
    }

    @JvmStatic
    public static /* synthetic */ void getGHOST$annotations() {
    }

    @NotNull
    public static final TeraType getDRAGON() {
        return DRAGON;
    }

    @JvmStatic
    public static /* synthetic */ void getDRAGON$annotations() {
    }

    @NotNull
    public static final TeraType getDARK() {
        return DARK;
    }

    @JvmStatic
    public static /* synthetic */ void getDARK$annotations() {
    }

    @NotNull
    public static final TeraType getSTEEL() {
        return STEEL;
    }

    @JvmStatic
    public static /* synthetic */ void getSTEEL$annotations() {
    }

    @NotNull
    public static final TeraType getFAIRY() {
        return FAIRY;
    }

    @JvmStatic
    public static /* synthetic */ void getFAIRY$annotations() {
    }

    @NotNull
    public static final TeraType getSTELLAR() {
        return STELLAR;
    }

    @JvmStatic
    public static /* synthetic */ void getSTELLAR$annotations() {
    }

    /*
     * WARNING - void declaration
     */
    @JvmStatic
    @NotNull
    public static final TeraType random(boolean legalOnly) {
        Collection<TeraType> collection = types.values();
        Intrinsics.checkNotNullExpressionValue(collection, (String)"types.values");
        Collection<TeraType> possible = collection;
        if (legalOnly) {
            void $this$filterTo$iv$iv;
            Iterable $this$filter$iv = possible;
            boolean $i$f$filter = false;
            Iterable iterable = $this$filter$iv;
            Collection destination$iv$iv = new ArrayList();
            boolean $i$f$filterTo = false;
            for (Object element$iv$iv : $this$filterTo$iv$iv) {
                TeraType p0 = (TeraType)element$iv$iv;
                boolean bl = false;
                if (!p0.getLegalAsStatic()) continue;
                destination$iv$iv.add(element$iv$iv);
            }
            return (TeraType)CollectionsKt.random((Collection)((List)destination$iv$iv), (Random)((Random)Random.Default));
        }
        return (TeraType)CollectionsKt.random(possible, (Random)((Random)Random.Default));
    }

    @JvmStatic
    @Nullable
    public static final TeraType get(@NotNull ResourceLocation id) {
        Intrinsics.checkNotNullParameter((Object)id, (String)"id");
        return types.get(id);
    }

    @JvmStatic
    @Nullable
    public static final TeraType get(@NotNull String id) {
        Intrinsics.checkNotNullParameter((Object)id, (String)"id");
        return TeraTypes.get(MiscUtilsKt.cobblemonResource(id));
    }

    @JvmStatic
    @NotNull
    public static final TeraType forElementalType(@NotNull ElementalType type) {
        Intrinsics.checkNotNullParameter((Object)type, (String)"type");
        TeraType teraType = TeraTypes.get(MiscUtilsKt.cobblemonResource(type.getName()));
        Intrinsics.checkNotNull((Object)teraType);
        return teraType;
    }

    private final TeraType create(ResourceLocation id, TeraType type) {
        ((Map)types).put(id, type);
        return type;
    }
}

