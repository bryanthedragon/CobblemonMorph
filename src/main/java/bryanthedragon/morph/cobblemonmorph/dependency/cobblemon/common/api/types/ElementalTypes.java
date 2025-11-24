/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.text.StringsKt
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.ElementalType;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b*\n\u0002\u0010!\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b?\u0010@J\u0013\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002\u00a2\u0006\u0004\b\u0004\u0010\u0005J\r\u0010\u0007\u001a\u00020\u0006\u00a2\u0006\u0004\b\u0007\u0010\bJ\u0017\u0010\u000b\u001a\u0004\u0018\u00010\u00032\u0006\u0010\n\u001a\u00020\t\u00a2\u0006\u0004\b\u000b\u0010\fJ\u0015\u0010\r\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\t\u00a2\u0006\u0004\b\r\u0010\fJ\u0015\u0010\u000f\u001a\u00020\u00032\u0006\u0010\u000e\u001a\u00020\u0003\u00a2\u0006\u0004\b\u000f\u0010\u0010J-\u0010\u000f\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0013\u001a\u00020\u00062\u0006\u0010\u0014\u001a\u00020\u0006\u00a2\u0006\u0004\b\u000f\u0010\u0015R\u0017\u0010\u0016\u001a\u00020\u00038\u0006\u00a2\u0006\f\n\u0004\b\u0016\u0010\u0017\u001a\u0004\b\u0018\u0010\u0019R\u0017\u0010\u001a\u001a\u00020\u00038\u0006\u00a2\u0006\f\n\u0004\b\u001a\u0010\u0017\u001a\u0004\b\u001b\u0010\u0019R\u0017\u0010\u001c\u001a\u00020\u00038\u0006\u00a2\u0006\f\n\u0004\b\u001c\u0010\u0017\u001a\u0004\b\u001d\u0010\u0019R\u0017\u0010\u001e\u001a\u00020\u00038\u0006\u00a2\u0006\f\n\u0004\b\u001e\u0010\u0017\u001a\u0004\b\u001f\u0010\u0019R\u0017\u0010 \u001a\u00020\u00038\u0006\u00a2\u0006\f\n\u0004\b \u0010\u0017\u001a\u0004\b!\u0010\u0019R\u0017\u0010\"\u001a\u00020\u00038\u0006\u00a2\u0006\f\n\u0004\b\"\u0010\u0017\u001a\u0004\b#\u0010\u0019R\u0017\u0010$\u001a\u00020\u00038\u0006\u00a2\u0006\f\n\u0004\b$\u0010\u0017\u001a\u0004\b%\u0010\u0019R\u0017\u0010&\u001a\u00020\u00038\u0006\u00a2\u0006\f\n\u0004\b&\u0010\u0017\u001a\u0004\b'\u0010\u0019R\u0017\u0010(\u001a\u00020\u00038\u0006\u00a2\u0006\f\n\u0004\b(\u0010\u0017\u001a\u0004\b)\u0010\u0019R\u0017\u0010*\u001a\u00020\u00038\u0006\u00a2\u0006\f\n\u0004\b*\u0010\u0017\u001a\u0004\b+\u0010\u0019R\u0017\u0010,\u001a\u00020\u00038\u0006\u00a2\u0006\f\n\u0004\b,\u0010\u0017\u001a\u0004\b-\u0010\u0019R\u0017\u0010.\u001a\u00020\u00038\u0006\u00a2\u0006\f\n\u0004\b.\u0010\u0017\u001a\u0004\b/\u0010\u0019R\u0017\u00100\u001a\u00020\u00038\u0006\u00a2\u0006\f\n\u0004\b0\u0010\u0017\u001a\u0004\b1\u0010\u0019R\u0017\u00102\u001a\u00020\u00038\u0006\u00a2\u0006\f\n\u0004\b2\u0010\u0017\u001a\u0004\b3\u0010\u0019R\u0017\u00104\u001a\u00020\u00038\u0006\u00a2\u0006\f\n\u0004\b4\u0010\u0017\u001a\u0004\b5\u0010\u0019R\u0017\u00106\u001a\u00020\u00038\u0006\u00a2\u0006\f\n\u0004\b6\u0010\u0017\u001a\u0004\b7\u0010\u0019R\u0017\u00108\u001a\u00020\u00038\u0006\u00a2\u0006\f\n\u0004\b8\u0010\u0017\u001a\u0004\b9\u0010\u0019R\u0017\u0010:\u001a\u00020\u00038\u0006\u00a2\u0006\f\n\u0004\b:\u0010\u0017\u001a\u0004\b;\u0010\u0019R\u001a\u0010=\u001a\b\u0012\u0004\u0012\u00020\u00030<8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b=\u0010>\u00a8\u0006A"}, d2={"Lcom/cobblemon/mod/common/api/types/ElementalTypes;", "", "", "Lcom/cobblemon/mod/common/api/types/ElementalType;", "all", "()Ljava/util/List;", "", "count", "()I", "", "name", "get", "(Ljava/lang/String;)Lcom/cobblemon/mod/common/api/types/ElementalType;", "getOrException", "elementalType", "register", "(Lcom/cobblemon/mod/common/api/types/ElementalType;)Lcom/cobblemon/mod/common/api/types/ElementalType;", "Lnet/minecraft/network/chat/MutableComponent;", "displayName", "hue", "textureXMultiplier", "(Ljava/lang/String;Lnet/minecraft/network/chat/MutableComponent;II)Lcom/cobblemon/mod/common/api/types/ElementalType;", "BUG", "Lcom/cobblemon/mod/common/api/types/ElementalType;", "getBUG", "()Lcom/cobblemon/mod/common/api/types/ElementalType;", "DARK", "getDARK", "DRAGON", "getDRAGON", "ELECTRIC", "getELECTRIC", "FAIRY", "getFAIRY", "FIGHTING", "getFIGHTING", "FIRE", "getFIRE", "FLYING", "getFLYING", "GHOST", "getGHOST", "GRASS", "getGRASS", "GROUND", "getGROUND", "ICE", "getICE", "NORMAL", "getNORMAL", "POISON", "getPOISON", "PSYCHIC", "getPSYCHIC", "ROCK", "getROCK", "STEEL", "getSTEEL", "WATER", "getWATER", "", "allTypes", "Ljava/util/List;", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nElementalTypes.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ElementalTypes.kt\ncom/cobblemon/mod/common/api/types/ElementalTypes\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,175:1\n288#2,2:176\n223#2,2:178\n*S KotlinDebug\n*F\n+ 1 ElementalTypes.kt\ncom/cobblemon/mod/common/api/types/ElementalTypes\n*L\n164#1:176,2\n168#1:178,2\n*E\n"})
public final class ElementalTypes {
    @NotNull
    public static final ElementalTypes INSTANCE = new ElementalTypes();
    @NotNull
    private static final List<ElementalType> allTypes = new ArrayList();
    @NotNull
    private static final ElementalType NORMAL;
    @NotNull
    private static final ElementalType FIRE;
    @NotNull
    private static final ElementalType WATER;
    @NotNull
    private static final ElementalType GRASS;
    @NotNull
    private static final ElementalType ELECTRIC;
    @NotNull
    private static final ElementalType ICE;
    @NotNull
    private static final ElementalType FIGHTING;
    @NotNull
    private static final ElementalType POISON;
    @NotNull
    private static final ElementalType GROUND;
    @NotNull
    private static final ElementalType FLYING;
    @NotNull
    private static final ElementalType PSYCHIC;
    @NotNull
    private static final ElementalType BUG;
    @NotNull
    private static final ElementalType ROCK;
    @NotNull
    private static final ElementalType GHOST;
    @NotNull
    private static final ElementalType DRAGON;
    @NotNull
    private static final ElementalType DARK;
    @NotNull
    private static final ElementalType STEEL;
    @NotNull
    private static final ElementalType FAIRY;

    private ElementalTypes() {
    }

    @NotNull
    public final ElementalType getNORMAL() {
        return NORMAL;
    }

    @NotNull
    public final ElementalType getFIRE() {
        return FIRE;
    }

    @NotNull
    public final ElementalType getWATER() {
        return WATER;
    }

    @NotNull
    public final ElementalType getGRASS() {
        return GRASS;
    }

    @NotNull
    public final ElementalType getELECTRIC() {
        return ELECTRIC;
    }

    @NotNull
    public final ElementalType getICE() {
        return ICE;
    }

    @NotNull
    public final ElementalType getFIGHTING() {
        return FIGHTING;
    }

    @NotNull
    public final ElementalType getPOISON() {
        return POISON;
    }

    @NotNull
    public final ElementalType getGROUND() {
        return GROUND;
    }

    @NotNull
    public final ElementalType getFLYING() {
        return FLYING;
    }

    @NotNull
    public final ElementalType getPSYCHIC() {
        return PSYCHIC;
    }

    @NotNull
    public final ElementalType getBUG() {
        return BUG;
    }

    @NotNull
    public final ElementalType getROCK() {
        return ROCK;
    }

    @NotNull
    public final ElementalType getGHOST() {
        return GHOST;
    }

    @NotNull
    public final ElementalType getDRAGON() {
        return DRAGON;
    }

    @NotNull
    public final ElementalType getDARK() {
        return DARK;
    }

    @NotNull
    public final ElementalType getSTEEL() {
        return STEEL;
    }

    @NotNull
    public final ElementalType getFAIRY() {
        return FAIRY;
    }

    @NotNull
    public final ElementalType register(@NotNull String name, @NotNull MutableComponent displayName, int hue, int textureXMultiplier) {
        ElementalType elementalType;
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        Intrinsics.checkNotNullParameter((Object)displayName, (String)"displayName");
        ElementalType it = elementalType = new ElementalType(name, displayName, hue, textureXMultiplier, null, 16, null);
        boolean bl = false;
        allTypes.add(it);
        return elementalType;
    }

    @NotNull
    public final ElementalType register(@NotNull ElementalType elementalType) {
        Intrinsics.checkNotNullParameter((Object)elementalType, (String)"elementalType");
        allTypes.add(elementalType);
        return elementalType;
    }

    @Nullable
    public final ElementalType get(@NotNull String name) {
        Object v0;
        block1: {
            Intrinsics.checkNotNullParameter((Object)name, (String)"name");
            Iterable $this$firstOrNull$iv = allTypes;
            boolean $i$f$firstOrNull = false;
            for (Object element$iv : $this$firstOrNull$iv) {
                ElementalType type = (ElementalType)element$iv;
                boolean bl = false;
                if (!StringsKt.equals((String)type.getName(), (String)name, (boolean)true)) continue;
                v0 = element$iv;
                break block1;
            }
            v0 = null;
        }
        return v0;
    }

    @NotNull
    public final ElementalType getOrException(@NotNull String name) {
        Object element$iv2;
        block1: {
            Intrinsics.checkNotNullParameter((Object)name, (String)"name");
            Iterable $this$first$iv = allTypes;
            boolean $i$f$first = false;
            for (Object element$iv2 : $this$first$iv) {
                ElementalType type = (ElementalType)element$iv2;
                boolean bl = false;
                if (!StringsKt.equals((String)type.getName(), (String)name, (boolean)true)) continue;
                break block1;
            }
            throw new NoSuchElementException("Collection contains no element matching the predicate.");
        }
        return (ElementalType)element$iv2;
    }

    public final int count() {
        return allTypes.size();
    }

    @NotNull
    public final List<ElementalType> all() {
        return CollectionsKt.toList((Iterable)allTypes);
    }

    static {
        MutableComponent mutableComponent = Component.m_237115_((String)"cobblemon.type.normal");
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"translatable(\"cobblemon.type.normal\")");
        NORMAL = INSTANCE.register("normal", mutableComponent, 0xDDDDCF, 0);
        MutableComponent mutableComponent2 = Component.m_237115_((String)"cobblemon.type.fire");
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent2, (String)"translatable(\"cobblemon.type.fire\")");
        FIRE = INSTANCE.register("fire", mutableComponent2, 15031346, 1);
        MutableComponent mutableComponent3 = Component.m_237115_((String)"cobblemon.type.water");
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent3, (String)"translatable(\"cobblemon.type.water\")");
        WATER = INSTANCE.register("water", mutableComponent3, 4889576, 2);
        MutableComponent mutableComponent4 = Component.m_237115_((String)"cobblemon.type.grass");
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent4, (String)"translatable(\"cobblemon.type.grass\")");
        GRASS = INSTANCE.register("grass", mutableComponent4, 5094460, 3);
        MutableComponent mutableComponent5 = Component.m_237115_((String)"cobblemon.type.electric");
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent5, (String)"translatable(\"cobblemon.type.electric\")");
        ELECTRIC = INSTANCE.register("electric", mutableComponent5, 15716648, 4);
        MutableComponent mutableComponent6 = Component.m_237115_((String)"cobblemon.type.ice");
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent6, (String)"translatable(\"cobblemon.type.ice\")");
        ICE = INSTANCE.register("ice", mutableComponent6, 7062511, 5);
        MutableComponent mutableComponent7 = Component.m_237115_((String)"cobblemon.type.fighting");
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent7, (String)"translatable(\"cobblemon.type.fighting\")");
        FIGHTING = INSTANCE.register("fighting", mutableComponent7, 0xC44C5C, 6);
        MutableComponent mutableComponent8 = Component.m_237115_((String)"cobblemon.type.poison");
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent8, (String)"translatable(\"cobblemon.type.poison\")");
        POISON = INSTANCE.register("poison", mutableComponent8, 10636248, 7);
        MutableComponent mutableComponent9 = Component.m_237115_((String)"cobblemon.type.ground");
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent9, (String)"translatable(\"cobblemon.type.ground\")");
        GROUND = INSTANCE.register("ground", mutableComponent9, 14195024, 8);
        MutableComponent mutableComponent10 = Component.m_237115_((String)"cobblemon.type.flying");
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent10, (String)"translatable(\"cobblemon.type.flying\")");
        FLYING = INSTANCE.register("flying", mutableComponent10, 12370431, 9);
        MutableComponent mutableComponent11 = Component.m_237115_((String)"cobblemon.type.psychic");
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent11, (String)"translatable(\"cobblemon.type.psychic\")");
        PSYCHIC = INSTANCE.register("psychic", mutableComponent11, 14183126, 10);
        MutableComponent mutableComponent12 = Component.m_237115_((String)"cobblemon.type.bug");
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent12, (String)"translatable(\"cobblemon.type.bug\")");
        BUG = INSTANCE.register("bug", mutableComponent12, 10668081, 11);
        MutableComponent mutableComponent13 = Component.m_237115_((String)"cobblemon.type.rock");
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent13, (String)"translatable(\"cobblemon.type.rock\")");
        ROCK = INSTANCE.register("rock", mutableComponent13, 0xAA9666, 12);
        MutableComponent mutableComponent14 = Component.m_237115_((String)"cobblemon.type.ghost");
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent14, (String)"translatable(\"cobblemon.type.ghost\")");
        GHOST = INSTANCE.register("ghost", mutableComponent14, 9794277, 13);
        MutableComponent mutableComponent15 = Component.m_237115_((String)"cobblemon.type.dragon");
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent15, (String)"translatable(\"cobblemon.type.dragon\")");
        DRAGON = INSTANCE.register("dragon", mutableComponent15, 5463528, 14);
        MutableComponent mutableComponent16 = Component.m_237115_((String)"cobblemon.type.dark");
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent16, (String)"translatable(\"cobblemon.type.dark\")");
        DARK = INSTANCE.register("dark", mutableComponent16, 6057138, 15);
        MutableComponent mutableComponent17 = Component.m_237115_((String)"cobblemon.type.steel");
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent17, (String)"translatable(\"cobblemon.type.steel\")");
        STEEL = INSTANCE.register("steel", mutableComponent17, 12831968, 16);
        MutableComponent mutableComponent18 = Component.m_237115_((String)"cobblemon.type.fairy");
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent18, (String)"translatable(\"cobblemon.type.fairy\")");
        FAIRY = INSTANCE.register("fairy", mutableComponent18, 15364734, 17);
    }
}

