/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.random.Random
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.Flavor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stats;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Nature;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.random.Random;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b:\n\u0002\u0010!\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\bH\u0010IJ\u0013\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002\u00a2\u0006\u0004\b\u0004\u0010\u0005J\u0017\u0010\b\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0007\u001a\u00020\u0006\u00a2\u0006\u0004\b\b\u0010\tJ\u0017\u0010\b\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u000b\u001a\u00020\n\u00a2\u0006\u0004\b\b\u0010\fJ\r\u0010\r\u001a\u00020\u0003\u00a2\u0006\u0004\b\r\u0010\u000eJ\u0015\u0010\u0010\u001a\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u0003\u00a2\u0006\u0004\b\u0010\u0010\u0011R\u0017\u0010\u0012\u001a\u00020\u00038\u0006\u00a2\u0006\f\n\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u0014\u0010\u000eR\u0017\u0010\u0015\u001a\u00020\u00038\u0006\u00a2\u0006\f\n\u0004\b\u0015\u0010\u0013\u001a\u0004\b\u0016\u0010\u000eR\u0017\u0010\u0017\u001a\u00020\u00038\u0006\u00a2\u0006\f\n\u0004\b\u0017\u0010\u0013\u001a\u0004\b\u0018\u0010\u000eR\u0017\u0010\u0019\u001a\u00020\u00038\u0006\u00a2\u0006\f\n\u0004\b\u0019\u0010\u0013\u001a\u0004\b\u001a\u0010\u000eR\u0017\u0010\u001b\u001a\u00020\u00038\u0006\u00a2\u0006\f\n\u0004\b\u001b\u0010\u0013\u001a\u0004\b\u001c\u0010\u000eR\u0017\u0010\u001d\u001a\u00020\u00038\u0006\u00a2\u0006\f\n\u0004\b\u001d\u0010\u0013\u001a\u0004\b\u001e\u0010\u000eR\u0017\u0010\u001f\u001a\u00020\u00038\u0006\u00a2\u0006\f\n\u0004\b\u001f\u0010\u0013\u001a\u0004\b \u0010\u000eR\u0017\u0010!\u001a\u00020\u00038\u0006\u00a2\u0006\f\n\u0004\b!\u0010\u0013\u001a\u0004\b\"\u0010\u000eR\u0017\u0010#\u001a\u00020\u00038\u0006\u00a2\u0006\f\n\u0004\b#\u0010\u0013\u001a\u0004\b$\u0010\u000eR\u0017\u0010%\u001a\u00020\u00038\u0006\u00a2\u0006\f\n\u0004\b%\u0010\u0013\u001a\u0004\b&\u0010\u000eR\u0017\u0010'\u001a\u00020\u00038\u0006\u00a2\u0006\f\n\u0004\b'\u0010\u0013\u001a\u0004\b(\u0010\u000eR\u0017\u0010)\u001a\u00020\u00038\u0006\u00a2\u0006\f\n\u0004\b)\u0010\u0013\u001a\u0004\b*\u0010\u000eR\u0017\u0010+\u001a\u00020\u00038\u0006\u00a2\u0006\f\n\u0004\b+\u0010\u0013\u001a\u0004\b,\u0010\u000eR\u0017\u0010-\u001a\u00020\u00038\u0006\u00a2\u0006\f\n\u0004\b-\u0010\u0013\u001a\u0004\b.\u0010\u000eR\u0017\u0010/\u001a\u00020\u00038\u0006\u00a2\u0006\f\n\u0004\b/\u0010\u0013\u001a\u0004\b0\u0010\u000eR\u0017\u00101\u001a\u00020\u00038\u0006\u00a2\u0006\f\n\u0004\b1\u0010\u0013\u001a\u0004\b2\u0010\u000eR\u0017\u00103\u001a\u00020\u00038\u0006\u00a2\u0006\f\n\u0004\b3\u0010\u0013\u001a\u0004\b4\u0010\u000eR\u0017\u00105\u001a\u00020\u00038\u0006\u00a2\u0006\f\n\u0004\b5\u0010\u0013\u001a\u0004\b6\u0010\u000eR\u0017\u00107\u001a\u00020\u00038\u0006\u00a2\u0006\f\n\u0004\b7\u0010\u0013\u001a\u0004\b8\u0010\u000eR\u0017\u00109\u001a\u00020\u00038\u0006\u00a2\u0006\f\n\u0004\b9\u0010\u0013\u001a\u0004\b:\u0010\u000eR\u0017\u0010;\u001a\u00020\u00038\u0006\u00a2\u0006\f\n\u0004\b;\u0010\u0013\u001a\u0004\b<\u0010\u000eR\u0017\u0010=\u001a\u00020\u00038\u0006\u00a2\u0006\f\n\u0004\b=\u0010\u0013\u001a\u0004\b>\u0010\u000eR\u0017\u0010?\u001a\u00020\u00038\u0006\u00a2\u0006\f\n\u0004\b?\u0010\u0013\u001a\u0004\b@\u0010\u000eR\u0017\u0010A\u001a\u00020\u00038\u0006\u00a2\u0006\f\n\u0004\bA\u0010\u0013\u001a\u0004\bB\u0010\u000eR\u0017\u0010C\u001a\u00020\u00038\u0006\u00a2\u0006\f\n\u0004\bC\u0010\u0013\u001a\u0004\bD\u0010\u000eR\u001a\u0010F\u001a\b\u0012\u0004\u0012\u00020\u00030E8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\bF\u0010G\u00a8\u0006J"}, d2={"Lcom/cobblemon/mod/common/api/pokemon/Natures;", "", "", "Lcom/cobblemon/mod/common/pokemon/Nature;", "all", "()Ljava/util/Collection;", "", "identifier", "getNature", "(Ljava/lang/String;)Lcom/cobblemon/mod/common/pokemon/Nature;", "Lnet/minecraft/resources/ResourceLocation;", "name", "(Lnet/minecraft/resources/ResourceLocation;)Lcom/cobblemon/mod/common/pokemon/Nature;", "getRandomNature", "()Lcom/cobblemon/mod/common/pokemon/Nature;", "nature", "registerNature", "(Lcom/cobblemon/mod/common/pokemon/Nature;)Lcom/cobblemon/mod/common/pokemon/Nature;", "ADAMANT", "Lcom/cobblemon/mod/common/pokemon/Nature;", "getADAMANT", "BASHFUL", "getBASHFUL", "BOLD", "getBOLD", "BRAVE", "getBRAVE", "CALM", "getCALM", "CAREFUL", "getCAREFUL", "DOCILE", "getDOCILE", "GENTLE", "getGENTLE", "HARDY", "getHARDY", "HASTY", "getHASTY", "IMPISH", "getIMPISH", "JOLLY", "getJOLLY", "LAX", "getLAX", "LONELY", "getLONELY", "MILD", "getMILD", "MODEST", "getMODEST", "NAIVE", "getNAIVE", "NAUGHTY", "getNAUGHTY", "QUIET", "getQUIET", "QUIRKY", "getQUIRKY", "RASH", "getRASH", "RELAXED", "getRELAXED", "SASSY", "getSASSY", "SERIOUS", "getSERIOUS", "TIMID", "getTIMID", "", "allNatures", "Ljava/util/List;", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nNatures.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Natures.kt\ncom/cobblemon/mod/common/api/pokemon/Natures\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,189:1\n1#2:190\n*E\n"})
public final class Natures {
    @NotNull
    public static final Natures INSTANCE = new Natures();
    @NotNull
    private static final List<Nature> allNatures = new ArrayList();
    @NotNull
    private static final Nature HARDY = INSTANCE.registerNature(new Nature(MiscUtils.cobblemonResource("hardy"), "cobblemon.nature.hardy", null, null, null, null));
    @NotNull
    private static final Nature LONELY = INSTANCE.registerNature(new Nature(MiscUtils.cobblemonResource("lonely"), "cobblemon.nature.lonely", Stats.ATTACK, Stats.DEFENCE, Flavor.SPICY, Flavor.SOUR));
    @NotNull
    private static final Nature BRAVE = INSTANCE.registerNature(new Nature(MiscUtils.cobblemonResource("brave"), "cobblemon.nature.brave", Stats.ATTACK, Stats.SPEED, Flavor.SPICY, Flavor.SWEET));
    @NotNull
    private static final Nature ADAMANT = INSTANCE.registerNature(new Nature(MiscUtils.cobblemonResource("adamant"), "cobblemon.nature.adamant", Stats.ATTACK, Stats.SPECIAL_ATTACK, Flavor.SPICY, Flavor.DRY));
    @NotNull
    private static final Nature NAUGHTY = INSTANCE.registerNature(new Nature(MiscUtils.cobblemonResource("naughty"), "cobblemon.nature.naughty", Stats.ATTACK, Stats.SPECIAL_DEFENCE, Flavor.SPICY, Flavor.BITTER));
    @NotNull
    private static final Nature BOLD = INSTANCE.registerNature(new Nature(MiscUtils.cobblemonResource("bold"), "cobblemon.nature.bold", Stats.DEFENCE, Stats.ATTACK, Flavor.SOUR, Flavor.SPICY));
    @NotNull
    private static final Nature DOCILE = INSTANCE.registerNature(new Nature(MiscUtils.cobblemonResource("docile"), "cobblemon.nature.docile", null, null, null, null));
    @NotNull
    private static final Nature RELAXED = INSTANCE.registerNature(new Nature(MiscUtils.cobblemonResource("relaxed"), "cobblemon.nature.relaxed", Stats.DEFENCE, Stats.SPEED, Flavor.SOUR, Flavor.SWEET));
    @NotNull
    private static final Nature IMPISH = INSTANCE.registerNature(new Nature(MiscUtils.cobblemonResource("impish"), "cobblemon.nature.impish", Stats.DEFENCE, Stats.SPECIAL_ATTACK, Flavor.SOUR, Flavor.DRY));
    @NotNull
    private static final Nature LAX = INSTANCE.registerNature(new Nature(MiscUtils.cobblemonResource("lax"), "cobblemon.nature.lax", Stats.DEFENCE, Stats.SPECIAL_DEFENCE, Flavor.SOUR, Flavor.BITTER));
    @NotNull
    private static final Nature TIMID = INSTANCE.registerNature(new Nature(MiscUtils.cobblemonResource("timid"), "cobblemon.nature.timid", Stats.SPEED, Stats.ATTACK, Flavor.SWEET, Flavor.SPICY));
    @NotNull
    private static final Nature HASTY = INSTANCE.registerNature(new Nature(MiscUtils.cobblemonResource("hasty"), "cobblemon.nature.hasty", Stats.SPEED, Stats.DEFENCE, Flavor.SWEET, Flavor.SOUR));
    @NotNull
    private static final Nature SERIOUS = INSTANCE.registerNature(new Nature(MiscUtils.cobblemonResource("serious"), "cobblemon.nature.serious", null, null, null, null));
    @NotNull
    private static final Nature JOLLY = INSTANCE.registerNature(new Nature(MiscUtils.cobblemonResource("jolly"), "cobblemon.nature.jolly", Stats.SPEED, Stats.SPECIAL_ATTACK, Flavor.SWEET, Flavor.DRY));
    @NotNull
    private static final Nature NAIVE = INSTANCE.registerNature(new Nature(MiscUtils.cobblemonResource("naive"), "cobblemon.nature.naive", Stats.SPEED, Stats.SPECIAL_DEFENCE, Flavor.SWEET, Flavor.BITTER));
    @NotNull
    private static final Nature MODEST = INSTANCE.registerNature(new Nature(MiscUtils.cobblemonResource("modest"), "cobblemon.nature.modest", Stats.SPECIAL_ATTACK, Stats.ATTACK, null, null));
    @NotNull
    private static final Nature MILD = INSTANCE.registerNature(new Nature(MiscUtils.cobblemonResource("mild"), "cobblemon.nature.mild", Stats.SPECIAL_ATTACK, Stats.DEFENCE, Flavor.DRY, Flavor.SOUR));
    @NotNull
    private static final Nature QUIET = INSTANCE.registerNature(new Nature(MiscUtils.cobblemonResource("quiet"), "cobblemon.nature.quiet", Stats.SPECIAL_ATTACK, Stats.SPEED, Flavor.DRY, Flavor.SWEET));
    @NotNull
    private static final Nature BASHFUL = INSTANCE.registerNature(new Nature(MiscUtils.cobblemonResource("bashful"), "cobblemon.nature.bashful", null, null, null, null));
    @NotNull
    private static final Nature RASH = INSTANCE.registerNature(new Nature(MiscUtils.cobblemonResource("rash"), "cobblemon.nature.rash", Stats.SPECIAL_ATTACK, Stats.SPECIAL_DEFENCE, Flavor.DRY, Flavor.BITTER));
    @NotNull
    private static final Nature CALM = INSTANCE.registerNature(new Nature(MiscUtils.cobblemonResource("calm"), "cobblemon.nature.calm", Stats.SPECIAL_DEFENCE, Stats.ATTACK, Flavor.BITTER, Flavor.SPICY));
    @NotNull
    private static final Nature GENTLE = INSTANCE.registerNature(new Nature(MiscUtils.cobblemonResource("gentle"), "cobblemon.nature.gentle", Stats.SPECIAL_DEFENCE, Stats.DEFENCE, Flavor.BITTER, Flavor.SOUR));
    @NotNull
    private static final Nature SASSY = INSTANCE.registerNature(new Nature(MiscUtils.cobblemonResource("sassy"), "cobblemon.nature.sassy", Stats.SPECIAL_DEFENCE, Stats.SPEED, Flavor.BITTER, Flavor.SWEET));
    @NotNull
    private static final Nature CAREFUL = INSTANCE.registerNature(new Nature(MiscUtils.cobblemonResource("careful"), "cobblemon.nature.careful", Stats.SPECIAL_DEFENCE, Stats.SPECIAL_ATTACK, Flavor.BITTER, Flavor.DRY));
    @NotNull
    private static final Nature QUIRKY = INSTANCE.registerNature(new Nature(MiscUtils.cobblemonResource("quirky"), "cobblemon.nature.quirky", null, null, null, null));

    private Natures() {
    }

    @NotNull
    public final Nature getHARDY() {
        return HARDY;
    }

    @NotNull
    public final Nature getLONELY() {
        return LONELY;
    }

    @NotNull
    public final Nature getBRAVE() {
        return BRAVE;
    }

    @NotNull
    public final Nature getADAMANT() {
        return ADAMANT;
    }

    @NotNull
    public final Nature getNAUGHTY() {
        return NAUGHTY;
    }

    @NotNull
    public final Nature getBOLD() {
        return BOLD;
    }

    @NotNull
    public final Nature getDOCILE() {
        return DOCILE;
    }

    @NotNull
    public final Nature getRELAXED() {
        return RELAXED;
    }

    @NotNull
    public final Nature getIMPISH() {
        return IMPISH;
    }

    @NotNull
    public final Nature getLAX() {
        return LAX;
    }

    @NotNull
    public final Nature getTIMID() {
        return TIMID;
    }

    @NotNull
    public final Nature getHASTY() {
        return HASTY;
    }

    @NotNull
    public final Nature getSERIOUS() {
        return SERIOUS;
    }

    @NotNull
    public final Nature getJOLLY() {
        return JOLLY;
    }

    @NotNull
    public final Nature getNAIVE() {
        return NAIVE;
    }

    @NotNull
    public final Nature getMODEST() {
        return MODEST;
    }

    @NotNull
    public final Nature getMILD() {
        return MILD;
    }

    @NotNull
    public final Nature getQUIET() {
        return QUIET;
    }

    @NotNull
    public final Nature getBASHFUL() {
        return BASHFUL;
    }

    @NotNull
    public final Nature getRASH() {
        return RASH;
    }

    @NotNull
    public final Nature getCALM() {
        return CALM;
    }

    @NotNull
    public final Nature getGENTLE() {
        return GENTLE;
    }

    @NotNull
    public final Nature getSASSY() {
        return SASSY;
    }

    @NotNull
    public final Nature getCAREFUL() {
        return CAREFUL;
    }

    @NotNull
    public final Nature getQUIRKY() {
        return QUIRKY;
    }

    @NotNull
    public final Nature registerNature(@NotNull Nature nature) {
        Intrinsics.checkNotNullParameter((Object)nature, (String)"nature");
        allNatures.add(nature);
        return nature;
    }

    @Nullable
    public final Nature getNature(@NotNull ResourceLocation name) {
        Object v0;
        block1: {
            Intrinsics.checkNotNullParameter((Object)name, (String)"name");
            Iterable iterable = allNatures;
            for (Object t : iterable) {
                Nature nature = (Nature)t;
                boolean bl = false;
                if (!Intrinsics.areEqual((Object)nature.getName(), (Object)name)) continue;
                v0 = t;
                break block1;
            }
            v0 = null;
        }
        return v0;
    }

    @Nullable
    public final Nature getNature(@NotNull String identifier) {
        Intrinsics.checkNotNullParameter((Object)identifier, (String)"identifier");
        Nature nature = this.getNature(MiscUtils.cobblemonResource(identifier));
        if (nature != null) {
            return nature;
        }
        return this.getNature(new ResourceLocation(identifier));
    }

    @NotNull
    public final Nature getRandomNature() {
        return (Nature)CollectionsKt.random((Collection)allNatures, (Random)((Random)Random.Default));
    }

    @NotNull
    public final Collection<Nature> all() {
        return CollectionsKt.toList((Iterable)allNatures);
    }
}

