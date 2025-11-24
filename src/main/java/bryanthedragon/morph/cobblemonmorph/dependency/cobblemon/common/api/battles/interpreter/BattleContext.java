/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\bf\u0018\u00002\u00020\u0001:\u0001\u0012R\u0014\u0010\u0005\u001a\u00020\u00028&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004R\u0016\u0010\t\u001a\u0004\u0018\u00010\u00068&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\r\u001a\u00020\n8&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\u0011\u001a\u00020\u000e8&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010\u00a8\u0006\u0013"}, d2={"Lcom/cobblemon/mod/common/api/battles/interpreter/BattleContext;", "", "", "getId", "()Ljava/lang/String;", "id", "Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;", "getOrigin", "()Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;", "origin", "", "getTurn", "()I", "turn", "Lcom/cobblemon/mod/common/api/battles/interpreter/BattleContext$Type;", "getType", "()Lcom/cobblemon/mod/common/api/battles/interpreter/BattleContext$Type;", "type", "Type", "common"})
public interface BattleContext {
    @NotNull
    public String getId();

    public int getTurn();

    @NotNull
    public Type getType();

    @Nullable
    public BattlePokemon getOrigin();

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\u0010\u000b\n\u0002\b\u0018\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0019\b\u0002\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0007\u001a\u00020\u0002\u00a2\u0006\u0004\b\t\u0010\nR\u0017\u0010\u0003\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006R\u0017\u0010\u0007\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0007\u0010\u0004\u001a\u0004\b\b\u0010\u0006j\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000fj\u0002\b\u0010j\u0002\b\u0011j\u0002\b\u0012j\u0002\b\u0013j\u0002\b\u0014j\u0002\b\u0015j\u0002\b\u0016j\u0002\b\u0017j\u0002\b\u0018j\u0002\b\u0019\u00a8\u0006\u001a"}, d2={"Lcom/cobblemon/mod/common/api/battles/interpreter/BattleContext$Type;", "", "", "damaging", "Z", "getDamaging", "()Z", "exclusive", "getExclusive", "<init>", "(Ljava/lang/String;IZZ)V", "ITEM", "STATUS", "VOLATILE", "HAZARD", "WEATHER", "ROOM", "SPORT", "TERRAIN", "GRAVITY", "TAILWIND", "SCREEN", "FAINT", "BOOST", "UNBOOST", "MISC", "common"})
    public static final class Type
    extends Enum<Type> {
        private final boolean damaging;
        private final boolean exclusive;
        public static final /* enum */ Type ITEM = new Type(true, true);
        public static final /* enum */ Type STATUS = new Type(true, false);
        public static final /* enum */ Type VOLATILE = new Type(true, false);
        public static final /* enum */ Type HAZARD = new Type(true, false);
        public static final /* enum */ Type WEATHER = new Type(true, true);
        public static final /* enum */ Type ROOM = new Type(false, true);
        public static final /* enum */ Type SPORT = new Type(false, false);
        public static final /* enum */ Type TERRAIN = new Type(false, true);
        public static final /* enum */ Type GRAVITY = new Type(false, true);
        public static final /* enum */ Type TAILWIND = new Type(false, true);
        public static final /* enum */ Type SCREEN = new Type(false, false);
        public static final /* enum */ Type FAINT = new Type(false, false);
        public static final /* enum */ Type BOOST = new Type(false, false);
        public static final /* enum */ Type UNBOOST = new Type(false, false);
        public static final /* enum */ Type MISC = new Type(false, false);
        private static final /* synthetic */ Type[] $VALUES;

        private Type(boolean damaging, boolean exclusive) {
            this.damaging = damaging;
            this.exclusive = exclusive;
        }

        public final boolean getDamaging() {
            return this.damaging;
        }

        public final boolean getExclusive() {
            return this.exclusive;
        }

        public static Type[] values() {
            return (Type[])$VALUES.clone();
        }

        public static Type valueOf(String value2) {
            return Enum.valueOf(Type.class, value2);
        }

        static {
            $VALUES = typeArray = new Type[]{Type.ITEM, Type.STATUS, Type.VOLATILE, Type.HAZARD, Type.WEATHER, Type.ROOM, Type.SPORT, Type.TERRAIN, Type.GRAVITY, Type.TAILWIND, Type.SCREEN, Type.FAINT, Type.BOOST, Type.UNBOOST, Type.MISC};
        }
    }
}

