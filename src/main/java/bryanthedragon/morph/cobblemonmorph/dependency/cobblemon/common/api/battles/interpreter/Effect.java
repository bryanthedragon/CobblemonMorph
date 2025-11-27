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

import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Effect {
    @NotNull
    @SuppressWarnings("static-access")
    public static final Companion Companion = Effect.Companion.INSTANCE;

    @NotNull
    public String getId();

    @NotNull
    public Type getType();

    @NotNull
    public String getRawData();

    @NotNull
    public String getTypelessData();

    public static final class Companion {
        static final /* synthetic */ Companion INSTANCE;

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
            INSTANCE = new Companion();
        }
    }

    public static final class DefaultImpls {
        @NotNull
        public static String getTypelessData(@NotNull Effect $this) {
            return ((Object)StringsKt.trim((CharSequence)StringsKt.substringAfter$default((String)$this.getRawData(), (String)$this.getType().getPrefix(), null, (int)2, null))).toString();
        }
    }

    public static final class Type extends Enum<Type> {
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

