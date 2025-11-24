/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.text.StringsKt
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.Abilities;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.ShowdownIdentifiable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.Moves;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.CobblemonEffect;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\bf\u0018\u0000 \u000e2\u00020\u0001:\u0002\u000e\u000fR\u0014\u0010\u0005\u001a\u00020\u00028&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004R\u0014\u0010\u0007\u001a\u00020\u00028&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0006\u0010\u0004R\u0014\u0010\u000b\u001a\u00020\b8&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0014\u0010\r\u001a\u00020\u00028VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\f\u0010\u0004\u00a8\u0006\u0010"}, d2={"Lcom/cobblemon/mod/common/api/battles/interpreter/Effect;", "", "", "getId", "()Ljava/lang/String;", "id", "getRawData", "rawData", "Lcom/cobblemon/mod/common/api/battles/interpreter/Effect$Type;", "getType", "()Lcom/cobblemon/mod/common/api/battles/interpreter/Effect$Type;", "type", "getTypelessData", "typelessData", "Companion", "Type", "common"})
public interface Effect {
    @NotNull
    public static final Companion Companion = bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.Effect$Companion.$$INSTANCE;

    @NotNull
    public String getId();

    @NotNull
    public Type getType();

    @NotNull
    public String getRawData();

    @NotNull
    public String getTypelessData();

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\t\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0011\u0010\u0012J\u001d\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u001d\u0010\b\u001a\u00020\u00052\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u0002\u00a2\u0006\u0004\b\b\u0010\u0007J\u001d\u0010\t\u001a\u00020\u00052\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u0002\u00a2\u0006\u0004\b\t\u0010\u0007J'\u0010\f\u001a\u00020\u00052\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\u0004\u001a\u00020\u0002H\u0002\u00a2\u0006\u0004\b\f\u0010\rJ\u0017\u0010\u000e\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0004\u001a\u00020\u0002\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u001d\u0010\u0010\u001a\u00020\u00052\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0010\u0010\u0007\u00a8\u0006\u0013"}, d2={"Lcom/cobblemon/mod/common/api/battles/interpreter/Effect$Companion;", "", "", "id", "rawData", "Lcom/cobblemon/mod/common/api/battles/interpreter/Effect;", "ability", "(Ljava/lang/String;Ljava/lang/String;)Lcom/cobblemon/mod/common/api/battles/interpreter/Effect;", "item", "move", "Lcom/cobblemon/mod/common/api/battles/interpreter/Effect$Type;", "type", "of", "(Ljava/lang/String;Lcom/cobblemon/mod/common/api/battles/interpreter/Effect$Type;Ljava/lang/String;)Lcom/cobblemon/mod/common/api/battles/interpreter/Effect;", "parse", "(Ljava/lang/String;)Lcom/cobblemon/mod/common/api/battles/interpreter/Effect;", "pure", "<init>", "()V", "common"})
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE;

        private Companion() {
        }

        private final Effect of(String id, Type type, String rawData) {
            return new CobblemonEffect(id, type, rawData);
        }

        @NotNull
        public final Effect ability(@NotNull String id, @NotNull String rawData) {
            Intrinsics.checkNotNullParameter((Object)id, (String)"id");
            Intrinsics.checkNotNullParameter((Object)rawData, (String)"rawData");
            if (Abilities.INSTANCE.get(id) == null) {
                throw new IllegalArgumentException("Cannot instance ability effect with ID " + id);
            }
            return this.of(id, Type.ABILITY, rawData);
        }

        @NotNull
        public final Effect item(@NotNull String id, @NotNull String rawData) {
            Intrinsics.checkNotNullParameter((Object)id, (String)"id");
            Intrinsics.checkNotNullParameter((Object)rawData, (String)"rawData");
            return this.of(id, Type.ITEM, rawData);
        }

        @NotNull
        public final Effect move(@NotNull String id, @NotNull String rawData) {
            Intrinsics.checkNotNullParameter((Object)id, (String)"id");
            Intrinsics.checkNotNullParameter((Object)rawData, (String)"rawData");
            if (Moves.INSTANCE.getByName(id) == null) {
                throw new IllegalArgumentException("Cannot instance move effect with ID " + id);
            }
            return this.of(id, Type.MOVE, rawData);
        }

        @NotNull
        public final Effect pure(@NotNull String id, @NotNull String rawData) {
            Intrinsics.checkNotNullParameter((Object)id, (String)"id");
            Intrinsics.checkNotNullParameter((Object)rawData, (String)"rawData");
            return this.of(id, Type.PURE, rawData);
        }

        @Nullable
        public final Effect parse(@NotNull String rawData) {
            Object object;
            Intrinsics.checkNotNullParameter((Object)rawData, (String)"rawData");
            if (StringsKt.isBlank((CharSequence)rawData)) {
                return null;
            }
            try {
                Effect effect;
                if (StringsKt.startsWith$default((String)rawData, (String)Type.ABILITY.getPrefix(), (boolean)false, (int)2, null)) {
                    String string = rawData.toLowerCase(Locale.ROOT);
                    Intrinsics.checkNotNullExpressionValue((Object)string, (String)"this as java.lang.String).toLowerCase(Locale.ROOT)");
                    object = StringsKt.substringAfter$default((String)string, (String)Type.ABILITY.getPrefix(), null, (int)2, null);
                    var3_3 = ShowdownIdentifiable.Companion.getREGEX$common();
                    var4_5 = "";
                    effect = this.ability(var3_3.replace((CharSequence)object, var4_5), rawData);
                } else if (StringsKt.startsWith$default((String)rawData, (String)Type.ITEM.getPrefix(), (boolean)false, (int)2, null)) {
                    String string = rawData.toLowerCase(Locale.ROOT);
                    Intrinsics.checkNotNullExpressionValue((Object)string, (String)"this as java.lang.String).toLowerCase(Locale.ROOT)");
                    object = StringsKt.substringAfter$default((String)string, (String)Type.ITEM.getPrefix(), null, (int)2, null);
                    var3_3 = ShowdownIdentifiable.Companion.getREGEX$common();
                    var4_5 = "";
                    effect = this.item(var3_3.replace((CharSequence)object, var4_5), rawData);
                } else if (StringsKt.startsWith$default((String)rawData, (String)Type.MOVE.getPrefix(), (boolean)false, (int)2, null)) {
                    String string = rawData.toLowerCase(Locale.ROOT);
                    Intrinsics.checkNotNullExpressionValue((Object)string, (String)"this as java.lang.String).toLowerCase(Locale.ROOT)");
                    object = StringsKt.substringAfter$default((String)string, (String)Type.MOVE.getPrefix(), null, (int)2, null);
                    var3_3 = ShowdownIdentifiable.Companion.getREGEX$common();
                    var4_5 = "";
                    effect = this.move(var3_3.replace((CharSequence)object, var4_5), rawData);
                } else {
                    String string = rawData.toLowerCase(Locale.ROOT);
                    Intrinsics.checkNotNullExpressionValue((Object)string, (String)"this as java.lang.String).toLowerCase(Locale.ROOT)");
                    object = string;
                    var3_3 = ShowdownIdentifiable.Companion.getREGEX$common();
                    var4_5 = "";
                    effect = this.pure(var3_3.replace((CharSequence)object, var4_5), rawData);
                }
                object = effect;
            }
            catch (Exception exception) {
                object = null;
            }
            return object;
        }

        static {
            $$INSTANCE = new Companion();
        }
    }

    @Metadata(mv={1, 8, 0}, k=3, xi=48)
    public static final class DefaultImpls {
        @NotNull
        public static String getTypelessData(@NotNull Effect $this) {
            return ((Object)StringsKt.trim((CharSequence)StringsKt.substringAfter$default((String)$this.getRawData(), (String)$this.getType().getPrefix(), null, (int)2, null))).toString();
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\u0010\u000e\n\u0002\b\f\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0011\b\u0002\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0007\u0010\bR\u0017\u0010\u0003\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\r\u00a8\u0006\u000e"}, d2={"Lcom/cobblemon/mod/common/api/battles/interpreter/Effect$Type;", "", "", "prefix", "Ljava/lang/String;", "getPrefix", "()Ljava/lang/String;", "<init>", "(Ljava/lang/String;ILjava/lang/String;)V", "ABILITY", "ITEM", "MOVE", "BAGITEM", "PURE", "common"})
    public static final class Type
    extends Enum<Type> {
        @NotNull
        private final String prefix;
        public static final /* enum */ Type ABILITY = new Type("ability:");
        public static final /* enum */ Type ITEM = new Type("item:");
        public static final /* enum */ Type MOVE = new Type("move:");
        public static final /* enum */ Type BAGITEM = new Type("bagitem:");
        public static final /* enum */ Type PURE = new Type("");
        private static final /* synthetic */ Type[] $VALUES;

        private Type(String prefix) {
            this.prefix = prefix;
        }

        @NotNull
        public final String getPrefix() {
            return this.prefix;
        }

        public static Type[] values() {
            return (Type[])$VALUES.clone();
        }

        public static Type valueOf(String value2) {
            return Enum.valueOf(Type.class, value2);
        }

        static {
            $VALUES = typeArray = new Type[]{Type.ABILITY, Type.ITEM, Type.MOVE, Type.BAGITEM, Type.PURE};
        }
    }
}

