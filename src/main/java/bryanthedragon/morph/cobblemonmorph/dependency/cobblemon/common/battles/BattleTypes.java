/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.network.chat.MutableComponent
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000f\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0016\u0010\u0017J/\u0010\n\u001a\u00020\t2\u0006\u0010\u0003\u001a\u00020\u00022\b\b\u0002\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u0006\u00a2\u0006\u0004\b\n\u0010\u000bR\u0017\u0010\f\u001a\u00020\t8\u0006\u00a2\u0006\f\n\u0004\b\f\u0010\r\u001a\u0004\b\u000e\u0010\u000fR\u0017\u0010\u0010\u001a\u00020\t8\u0006\u00a2\u0006\f\n\u0004\b\u0010\u0010\r\u001a\u0004\b\u0011\u0010\u000fR\u0017\u0010\u0012\u001a\u00020\t8\u0006\u00a2\u0006\f\n\u0004\b\u0012\u0010\r\u001a\u0004\b\u0013\u0010\u000fR\u0017\u0010\u0014\u001a\u00020\t8\u0006\u00a2\u0006\f\n\u0004\b\u0014\u0010\r\u001a\u0004\b\u0015\u0010\u000f\u00a8\u0006\u0018"}, d2={"Lcom/cobblemon/mod/common/battles/BattleTypes;", "", "", "name", "Lnet/minecraft/network/chat/MutableComponent;", "displayName", "", "actorsPerSide", "slotsPerActor", "Lcom/cobblemon/mod/common/battles/BattleType;", "makeBattleType", "(Ljava/lang/String;Lnet/minecraft/network/chat/MutableComponent;II)Lcom/cobblemon/mod/common/battles/BattleType;", "DOUBLES", "Lcom/cobblemon/mod/common/battles/BattleType;", "getDOUBLES", "()Lcom/cobblemon/mod/common/battles/BattleType;", "MULTI", "getMULTI", "SINGLES", "getSINGLES", "TRIPLES", "getTRIPLES", "<init>", "()V", "common"})
public final class BattleTypes {
    @NotNull
    public static final BattleTypes INSTANCE = new BattleTypes();
    @NotNull
    private static final BattleType SINGLES = BattleTypes.makeBattleType$default(INSTANCE, "singles", null, 1, 1, 2, null);
    @NotNull
    private static final BattleType DOUBLES = BattleTypes.makeBattleType$default(INSTANCE, "doubles", null, 1, 2, 2, null);
    @NotNull
    private static final BattleType TRIPLES = BattleTypes.makeBattleType$default(INSTANCE, "triples", null, 1, 3, 2, null);
    @NotNull
    private static final BattleType MULTI = BattleTypes.makeBattleType$default(INSTANCE, "multi", null, 2, 1, 2, null);

    private BattleTypes() {
    }

    @NotNull
    public final BattleType getSINGLES() {
        return SINGLES;
    }

    @NotNull
    public final BattleType getDOUBLES() {
        return DOUBLES;
    }

    @NotNull
    public final BattleType getTRIPLES() {
        return TRIPLES;
    }

    @NotNull
    public final BattleType getMULTI() {
        return MULTI;
    }

    @NotNull
    public final BattleType makeBattleType(@NotNull String name, @NotNull MutableComponent displayName, int actorsPerSide, int slotsPerActor) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        Intrinsics.checkNotNullParameter((Object)displayName, (String)"displayName");
        return new BattleType(name, displayName, actorsPerSide, slotsPerActor){
            @NotNull
            private final String name;
            @NotNull
            private final MutableComponent displayName;
            private final int actorsPerSide;
            private final int slotsPerActor;
            {
                this.name = $name;
                this.displayName = $displayName;
                this.actorsPerSide = $actorsPerSide;
                this.slotsPerActor = $slotsPerActor;
            }

            @NotNull
            public String getName() {
                return this.name;
            }

            @NotNull
            public MutableComponent getDisplayName() {
                return this.displayName;
            }

            public int getActorsPerSide() {
                return this.actorsPerSide;
            }

            public int getSlotsPerActor() {
                return this.slotsPerActor;
            }

            public int getPokemonPerSide() {
                return BattleType.DefaultImpls.getPokemonPerSide(this);
            }

            @NotNull
            public FriendlyByteBuf saveToBuffer(@NotNull FriendlyByteBuf buffer) {
                return BattleType.DefaultImpls.saveToBuffer(this, buffer);
            }
        };
    }

    public static /* synthetic */ BattleType makeBattleType$default(BattleTypes battleTypes, String string, MutableComponent mutableComponent, int n, int n2, int n3, Object object) {
        if ((n3 & 2) != 0) {
            MutableComponent mutableComponent2 = LocalizationUtilsKt.lang("battle.types." + string, new Object[0]);
            Intrinsics.checkNotNullExpressionValue((Object)mutableComponent2, (String)"lang(\"battle.types.$name\")");
            mutableComponent = mutableComponent2;
        }
        return battleTypes.makeBattleType(string, mutableComponent, n, n2);
    }
}

