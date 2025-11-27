/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stat;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtils;
import java.util.EnumSet;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0010\b\u0086\u0001\u0018\u0000 \u00192\b\u0012\u0004\u0012\u00020\u00000\u00012\u00020\u0002:\u0001\u0019B)\b\u0002\u0012\u0006\u0010\t\u001a\u00020\b\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0013\u001a\u00020\u0012\u0012\u0006\u0010\u000e\u001a\u00020\r\u00a2\u0006\u0004\b\u0017\u0010\u0018R\u001a\u0010\u0004\u001a\u00020\u00038\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007R\u001a\u0010\t\u001a\u00020\b8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u000b\u0010\fR\u001a\u0010\u000e\u001a\u00020\r8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0013\u001a\u00020\u00128\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0015\u0010\u0016j\u0002\b\u001aj\u0002\b\u001bj\u0002\b\u001cj\u0002\b\u001dj\u0002\b\u001ej\u0002\b\u001fj\u0002\b j\u0002\b!\u00a8\u0006\""}, d2={"Lcom/cobblemon/mod/common/api/pokemon/stats/Stats;", "", "Lcom/cobblemon/mod/common/api/pokemon/stats/Stat;", "Lnet/minecraft/network/chat/Component;", "displayName", "Lnet/minecraft/network/chat/Component;", "getDisplayName", "()Lnet/minecraft/network/chat/Component;", "Lnet/minecraft/resources/ResourceLocation;", "identifier", "Lnet/minecraft/resources/ResourceLocation;", "getIdentifier", "()Lnet/minecraft/resources/ResourceLocation;", "", "showdownId", "Ljava/lang/String;", "getShowdownId", "()Ljava/lang/String;", "Lcom/cobblemon/mod/common/api/pokemon/stats/Stat$Type;", "type", "Lcom/cobblemon/mod/common/api/pokemon/stats/Stat$Type;", "getType", "()Lcom/cobblemon/mod/common/api/pokemon/stats/Stat$Type;", "<init>", "(Ljava/lang/String;ILnet/minecraft/resources/ResourceLocation;Lnet/minecraft/network/chat/Component;Lcom/cobblemon/mod/common/api/pokemon/stats/Stat$Type;Ljava/lang/String;)V", "Companion", "HP", "ATTACK", "DEFENCE", "SPECIAL_ATTACK", "SPECIAL_DEFENCE", "SPEED", "EVASION", "ACCURACY", "common"})
public final class Stats
extends Enum<Stats>
implements Stat {
    @NotNull
    public static final Companion Companion;
    @NotNull
    private final ResourceLocation identifier;
    @NotNull
    private final Component displayName;
    @NotNull
    private final Stat.Type type;
    @NotNull
    private final String showdownId;
    @NotNull
    private static final Set<Stat> ALL;
    @NotNull
    private static final Set<Stat> PERMANENT;
    @NotNull
    private static final Set<Stat> BATTLE_ONLY;
    public static final /* enum */ Stats HP;
    public static final /* enum */ Stats ATTACK;
    public static final /* enum */ Stats DEFENCE;
    public static final /* enum */ Stats SPECIAL_ATTACK;
    public static final /* enum */ Stats SPECIAL_DEFENCE;
    public static final /* enum */ Stats SPEED;
    public static final /* enum */ Stats EVASION;
    public static final /* enum */ Stats ACCURACY;
    private static final /* synthetic */ Stats[] $VALUES;

    private Stats(ResourceLocation identifier, Component displayName, Stat.Type type, String showdownId) {
        this.identifier = identifier;
        this.displayName = displayName;
        this.type = type;
        this.showdownId = showdownId;
    }

    @Override
    @NotNull
    public ResourceLocation getIdentifier() {
        return this.identifier;
    }

    @Override
    @NotNull
    public Component getDisplayName() {
        return this.displayName;
    }

    @Override
    @NotNull
    public Stat.Type getType() {
        return this.type;
    }

    @Override
    @NotNull
    public String getShowdownId() {
        return this.showdownId;
    }

    public static Stats[] values() {
        return (Stats[])$VALUES.clone();
    }

    public static Stats valueOf(String value2) {
        return Enum.valueOf(Stats.class, value2);
    }

    static {
        ResourceLocation resourceLocation = MiscUtils.cobblemonResource("hp");
        MutableComponent mutableComponent = LocalizationUtilsKt.lang("stat.hp.name", new Object[0]);
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"lang(\"stat.hp.name\")");
        HP = new Stats(resourceLocation, (Component)mutableComponent, Stat.Type.PERMANENT, "hp");
        ResourceLocation resourceLocation2 = MiscUtils.cobblemonResource("attack");
        MutableComponent mutableComponent2 = LocalizationUtilsKt.lang("stat.attack.name", new Object[0]);
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent2, (String)"lang(\"stat.attack.name\")");
        ATTACK = new Stats(resourceLocation2, (Component)mutableComponent2, Stat.Type.PERMANENT, "atk");
        ResourceLocation resourceLocation3 = MiscUtils.cobblemonResource("defence");
        MutableComponent mutableComponent3 = LocalizationUtilsKt.lang("stat.defence.name", new Object[0]);
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent3, (String)"lang(\"stat.defence.name\")");
        DEFENCE = new Stats(resourceLocation3, (Component)mutableComponent3, Stat.Type.PERMANENT, "def");
        ResourceLocation resourceLocation4 = MiscUtils.cobblemonResource("special_attack");
        MutableComponent mutableComponent4 = LocalizationUtilsKt.lang("stat.special_attack.name", new Object[0]);
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent4, (String)"lang(\"stat.special_attack.name\")");
        SPECIAL_ATTACK = new Stats(resourceLocation4, (Component)mutableComponent4, Stat.Type.PERMANENT, "spa");
        ResourceLocation resourceLocation5 = MiscUtils.cobblemonResource("special_defence");
        MutableComponent mutableComponent5 = LocalizationUtilsKt.lang("stat.special_defence.name", new Object[0]);
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent5, (String)"lang(\"stat.special_defence.name\")");
        SPECIAL_DEFENCE = new Stats(resourceLocation5, (Component)mutableComponent5, Stat.Type.PERMANENT, "spd");
        ResourceLocation resourceLocation6 = MiscUtils.cobblemonResource("speed");
        MutableComponent mutableComponent6 = LocalizationUtilsKt.lang("stat.speed.name", new Object[0]);
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent6, (String)"lang(\"stat.speed.name\")");
        SPEED = new Stats(resourceLocation6, (Component)mutableComponent6, Stat.Type.PERMANENT, "spe");
        ResourceLocation resourceLocation7 = MiscUtils.cobblemonResource("evasion");
        MutableComponent mutableComponent7 = LocalizationUtilsKt.lang("stat.evasion.name", new Object[0]);
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent7, (String)"lang(\"stat.evasion.name\")");
        EVASION = new Stats(resourceLocation7, (Component)mutableComponent7, Stat.Type.BATTLE_ONLY, "evasion");
        ResourceLocation resourceLocation8 = MiscUtils.cobblemonResource("accuracy");
        MutableComponent mutableComponent8 = LocalizationUtilsKt.lang("stat.accuracy.name", new Object[0]);
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent8, (String)"lang(\"stat.accuracy.name\")");
        ACCURACY = new Stats(resourceLocation8, (Component)mutableComponent8, Stat.Type.BATTLE_ONLY, "accuracy");
        $VALUES = statsArray = new Stats[]{Stats.HP, Stats.ATTACK, Stats.DEFENCE, Stats.SPECIAL_ATTACK, Stats.SPECIAL_DEFENCE, Stats.SPEED, Stats.EVASION, Stats.ACCURACY};
        Companion = new Companion(null);
        EnumSet<Stats> enumSet = EnumSet.allOf(Stats.class);
        Intrinsics.checkNotNullExpressionValue(enumSet, (String)"allOf(Stats::class.java)");
        ALL = enumSet;
        Stats[] statsArray = new Stats[]{ATTACK, DEFENCE, SPECIAL_ATTACK, SPECIAL_DEFENCE, SPEED};
        EnumSet<Enum[]> enumSet2 = EnumSet.of((Enum)HP, (Enum[])statsArray);
        Intrinsics.checkNotNullExpressionValue(enumSet2, (String)"of(HP, ATTACK, DEFENCE, \u2026, SPECIAL_DEFENCE, SPEED)");
        PERMANENT = enumSet2;
        EnumSet<Enum> enumSet3 = EnumSet.of((Enum)EVASION, (Enum)ACCURACY);
        Intrinsics.checkNotNullExpressionValue(enumSet3, (String)"of(EVASION, ACCURACY)");
        BATTLE_ONLY = enumSet3;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u000b\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0015\u0010\u0016J\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u0015\u0010\t\u001a\u00020\b2\u0006\u0010\u0007\u001a\u00020\u0004\u00a2\u0006\u0004\b\t\u0010\nR\u001d\u0010\r\u001a\b\u0012\u0004\u0012\u00020\f0\u000b8\u0006\u00a2\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000f\u0010\u0010R\u001d\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\f0\u000b8\u0006\u00a2\u0006\f\n\u0004\b\u0011\u0010\u000e\u001a\u0004\b\u0012\u0010\u0010R\u001d\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\f0\u000b8\u0006\u00a2\u0006\f\n\u0004\b\u0013\u0010\u000e\u001a\u0004\b\u0014\u0010\u0010\u00a8\u0006\u0017"}, d2={"Lcom/cobblemon/mod/common/api/pokemon/stats/Stats$Companion;", "", "", "stages", "", "getSeverity", "(I)Ljava/lang/String;", "statKey", "Lcom/cobblemon/mod/common/api/pokemon/stats/Stats;", "getStat", "(Ljava/lang/String;)Lcom/cobblemon/mod/common/api/pokemon/stats/Stats;", "", "Lcom/cobblemon/mod/common/api/pokemon/stats/Stat;", "ALL", "Ljava/util/Set;", "getALL", "()Ljava/util/Set;", "BATTLE_ONLY", "getBATTLE_ONLY", "PERMANENT", "getPERMANENT", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final Set<Stat> getALL() {
            return ALL;
        }

        @NotNull
        public final Set<Stat> getPERMANENT() {
            return PERMANENT;
        }

        @NotNull
        public final Set<Stat> getBATTLE_ONLY() {
            return BATTLE_ONLY;
        }

        @NotNull
        public final Stats getStat(@NotNull String statKey) {
            Intrinsics.checkNotNullParameter((Object)statKey, (String)"statKey");
            return switch (statKey) {
                case "atk", "Attack" -> ATTACK;
                case "def", "Defense" -> DEFENCE;
                case "spa" -> SPECIAL_ATTACK;
                case "spd" -> SPECIAL_DEFENCE;
                case "spe" -> SPEED;
                case "evasion" -> EVASION;
                default -> ACCURACY;
            };
        }

        @NotNull
        public final String getSeverity(int stages) {
            return switch (stages) {
                case 0 -> "cap.single";
                case 1 -> "slight";
                case 2 -> "sharp";
                default -> "severe";
            };
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

