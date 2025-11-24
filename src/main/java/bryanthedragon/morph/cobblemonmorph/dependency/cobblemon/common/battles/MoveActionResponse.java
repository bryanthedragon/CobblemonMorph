/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.network.FriendlyByteBuf
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ActiveBattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.InBattleGimmickMove;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.InBattleMove;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.MoveTarget;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownActionResponse;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownActionResponseType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownMoveset;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0010\b\u0086\b\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\u0007\u001a\u00020\u0002\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0002\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0002\u00a2\u0006\u0004\b-\u0010.J\u0010\u0010\u0003\u001a\u00020\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u0012\u0010\u0005\u001a\u0004\u0018\u00010\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0005\u0010\u0004J\u0012\u0010\u0006\u001a\u0004\u0018\u00010\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0006\u0010\u0004J2\u0010\n\u001a\u00020\u00002\b\b\u0002\u0010\u0007\u001a\u00020\u00022\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00022\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0002H\u00c6\u0001\u00a2\u0006\u0004\b\n\u0010\u000bJ\u001a\u0010\u000f\u001a\u00020\u000e2\b\u0010\r\u001a\u0004\u0018\u00010\fH\u00d6\u0003\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u0010\u0010\u0012\u001a\u00020\u0011H\u00d6\u0001\u00a2\u0006\u0004\b\u0012\u0010\u0013J)\u0010\u0019\u001a\u00020\u000e2\u0006\u0010\u0015\u001a\u00020\u00142\b\u0010\u0017\u001a\u0004\u0018\u00010\u00162\u0006\u0010\u0018\u001a\u00020\u000eH\u0016\u00a2\u0006\u0004\b\u0019\u0010\u001aJ\u0017\u0010\u001d\u001a\u00020\u00012\u0006\u0010\u001c\u001a\u00020\u001bH\u0016\u00a2\u0006\u0004\b\u001d\u0010\u001eJ\u0017\u0010 \u001a\u00020\u001f2\u0006\u0010\u001c\u001a\u00020\u001bH\u0016\u00a2\u0006\u0004\b \u0010!J!\u0010\"\u001a\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u00142\b\u0010\u0017\u001a\u0004\u0018\u00010\u0016H\u0016\u00a2\u0006\u0004\b\"\u0010#J\u0010\u0010$\u001a\u00020\u0002H\u00d6\u0001\u00a2\u0006\u0004\b$\u0010\u0004R$\u0010\t\u001a\u0004\u0018\u00010\u00028\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\t\u0010%\u001a\u0004\b&\u0010\u0004\"\u0004\b'\u0010(R\"\u0010\u0007\u001a\u00020\u00028\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0007\u0010%\u001a\u0004\b)\u0010\u0004\"\u0004\b*\u0010(R$\u0010\b\u001a\u0004\u0018\u00010\u00028\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\b\u0010%\u001a\u0004\b+\u0010\u0004\"\u0004\b,\u0010(\u00a8\u0006/"}, d2={"Lcom/cobblemon/mod/common/battles/MoveActionResponse;", "Lcom/cobblemon/mod/common/battles/ShowdownActionResponse;", "", "component1", "()Ljava/lang/String;", "component2", "component3", "moveName", "targetPnx", "gimmickID", "copy", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Lcom/cobblemon/mod/common/battles/MoveActionResponse;", "", "other", "", "equals", "(Ljava/lang/Object;)Z", "", "hashCode", "()I", "Lcom/cobblemon/mod/common/battles/ActiveBattlePokemon;", "activeBattlePokemon", "Lcom/cobblemon/mod/common/battles/ShowdownMoveset;", "showdownMoveSet", "forceSwitch", "isValid", "(Lcom/cobblemon/mod/common/battles/ActiveBattlePokemon;Lcom/cobblemon/mod/common/battles/ShowdownMoveset;Z)Z", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "loadFromBuffer", "(Lnet/minecraft/network/FriendlyByteBuf;)Lcom/cobblemon/mod/common/battles/ShowdownActionResponse;", "", "saveToBuffer", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "toShowdownString", "(Lcom/cobblemon/mod/common/battles/ActiveBattlePokemon;Lcom/cobblemon/mod/common/battles/ShowdownMoveset;)Ljava/lang/String;", "toString", "Ljava/lang/String;", "getGimmickID", "setGimmickID", "(Ljava/lang/String;)V", "getMoveName", "setMoveName", "getTargetPnx", "setTargetPnx", "<init>", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "common"})
@SourceDebugExtension(value={"SMAP\nShowdownActionRequest.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ShowdownActionRequest.kt\ncom/cobblemon/mod/common/battles/MoveActionResponse\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,452:1\n1#2:453\n350#3,7:454\n*S KotlinDebug\n*F\n+ 1 ShowdownActionRequest.kt\ncom/cobblemon/mod/common/battles/MoveActionResponse\n*L\n146#1:454,7\n*E\n"})
public final class MoveActionResponse
extends ShowdownActionResponse {
    @NotNull
    private String moveName;
    @Nullable
    private String targetPnx;
    @Nullable
    private String gimmickID;

    public MoveActionResponse(@NotNull String moveName, @Nullable String targetPnx, @Nullable String gimmickID) {
        Intrinsics.checkNotNullParameter((Object)moveName, (String)"moveName");
        super(ShowdownActionResponseType.MOVE);
        this.moveName = moveName;
        this.targetPnx = targetPnx;
        this.gimmickID = gimmickID;
    }

    public /* synthetic */ MoveActionResponse(String string, String string2, String string3, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 2) != 0) {
            string2 = null;
        }
        if ((n & 4) != 0) {
            string3 = null;
        }
        this(string, string2, string3);
    }

    @NotNull
    public final String getMoveName() {
        return this.moveName;
    }

    public final void setMoveName(@NotNull String string) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"<set-?>");
        this.moveName = string;
    }

    @Nullable
    public final String getTargetPnx() {
        return this.targetPnx;
    }

    public final void setTargetPnx(@Nullable String string) {
        this.targetPnx = string;
    }

    @Nullable
    public final String getGimmickID() {
        return this.gimmickID;
    }

    public final void setGimmickID(@Nullable String string) {
        this.gimmickID = string;
    }

    @Override
    public boolean isValid(@NotNull ActiveBattlePokemon activeBattlePokemon, @Nullable ShowdownMoveset showdownMoveSet, boolean forceSwitch) {
        block16: {
            block15: {
                Object object;
                block14: {
                    block13: {
                        boolean validGimmickMove;
                        Object v0;
                        Object it;
                        block12: {
                            Intrinsics.checkNotNullParameter((Object)activeBattlePokemon, (String)"activeBattlePokemon");
                            if (forceSwitch || showdownMoveSet == null) {
                                return false;
                            }
                            Iterable iterable = showdownMoveSet.getMoves();
                            for (Object t : iterable) {
                                it = (InBattleMove)t;
                                boolean bl = false;
                                if (!Intrinsics.areEqual((Object)((InBattleMove)it).getId(), (Object)this.moveName)) continue;
                                v0 = t;
                                break block12;
                            }
                            v0 = null;
                        }
                        InBattleMove inBattleMove = v0;
                        if (inBattleMove == null) {
                            return false;
                        }
                        InBattleMove move = inBattleMove;
                        InBattleGimmickMove gimmickMove = move.getGimmickMove();
                        boolean bl = validGimmickMove = gimmickMove != null && !gimmickMove.getDisabled();
                        if (!validGimmickMove && !move.canBeUsed()) {
                            return false;
                        }
                        Object object2 = gimmickMove;
                        if (object2 == null || (object2 = ((InBattleGimmickMove)object2).getTarget()) == null) {
                            object2 = move.getTarget();
                        }
                        if ((object = (List)((MoveTarget)((Object)object2)).getTargetList().invoke((Object)activeBattlePokemon)) == null) break block13;
                        Object it2 = it = object;
                        boolean bl2 = false;
                        object = !((Collection)it2).isEmpty() ? it : null;
                        if (object != null) break block14;
                    }
                    return true;
                }
                Object availableTargets = object;
                String string = this.targetPnx;
                if (string == null) {
                    return false;
                }
                String pnx = string;
                ActiveBattlePokemon targetPokemon = (ActiveBattlePokemon)activeBattlePokemon.getActor().getBattle().getActorAndActiveSlotFromPNX(pnx).component2();
                if (!availableTargets.contains(targetPokemon) || targetPokemon.getBattlePokemon() == null) break block15;
                BattlePokemon battlePokemon = targetPokemon.getBattlePokemon();
                Intrinsics.checkNotNull((Object)battlePokemon);
                if (battlePokemon.getHealth() > 0) break block16;
            }
            return false;
        }
        return true;
    }

    @Override
    @NotNull
    public String toShowdownString(@NotNull ActiveBattlePokemon activeBattlePokemon, @Nullable ShowdownMoveset showdownMoveSet) {
        Object object;
        String string;
        block10: {
            block9: {
                int n;
                Object object2;
                String pnx;
                block8: {
                    Intrinsics.checkNotNullParameter((Object)activeBattlePokemon, (String)"activeBattlePokemon");
                    pnx = this.targetPnx;
                    Intrinsics.checkNotNull((Object)showdownMoveSet);
                    List<InBattleMove> $this$indexOfFirst$iv = showdownMoveSet.getMoves();
                    boolean $i$f$indexOfFirst = false;
                    int index$iv = 0;
                    object2 = $this$indexOfFirst$iv.iterator();
                    while (object2.hasNext()) {
                        InBattleMove item$iv;
                        InBattleMove it = item$iv = object2.next();
                        boolean bl = false;
                        if (Intrinsics.areEqual((Object)it.getId(), (Object)this.moveName)) {
                            n = index$iv;
                            break block8;
                        }
                        ++index$iv;
                    }
                    n = -1;
                }
                int moveIndex = n + 1;
                if (pnx != null) {
                    ActiveBattlePokemon targetPokemon = (ActiveBattlePokemon)activeBattlePokemon.getActor().getBattle().getActorAndActiveSlotFromPNX(pnx).component2();
                    String digit = targetPokemon.getSignedDigitRelativeTo(activeBattlePokemon);
                    string = "move " + moveIndex + " " + digit;
                } else {
                    string = "move " + moveIndex;
                }
                if ((object = this.gimmickID) == null) break block9;
                object2 = object;
                String string2 = string;
                boolean bl = false;
                String string3 = " " + this.gimmickID;
                string = string2;
                object = string3;
                if (string3 != null) break block10;
            }
            object = "";
        }
        return string + (String)object;
    }

    @Override
    public void saveToBuffer(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        super.saveToBuffer(buffer);
        buffer.m_130070_(this.moveName);
        buffer.m_236821_((Object)this.targetPnx, (arg_0, arg_1) -> MoveActionResponse.saveToBuffer$lambda$4(buffer, arg_0, arg_1));
        buffer.m_236821_((Object)this.gimmickID, (arg_0, arg_1) -> MoveActionResponse.saveToBuffer$lambda$5(buffer, arg_0, arg_1));
    }

    @Override
    @NotNull
    public ShowdownActionResponse loadFromBuffer(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        super.loadFromBuffer(buffer);
        String string = buffer.m_130277_();
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"buffer.readString()");
        this.moveName = string;
        this.targetPnx = (String)buffer.m_236868_(arg_0 -> MoveActionResponse.loadFromBuffer$lambda$6(buffer, arg_0));
        this.gimmickID = (String)buffer.m_236868_(arg_0 -> MoveActionResponse.loadFromBuffer$lambda$7(buffer, arg_0));
        return this;
    }

    @NotNull
    public final String component1() {
        return this.moveName;
    }

    @Nullable
    public final String component2() {
        return this.targetPnx;
    }

    @Nullable
    public final String component3() {
        return this.gimmickID;
    }

    @NotNull
    public final MoveActionResponse copy(@NotNull String moveName, @Nullable String targetPnx, @Nullable String gimmickID) {
        Intrinsics.checkNotNullParameter((Object)moveName, (String)"moveName");
        return new MoveActionResponse(moveName, targetPnx, gimmickID);
    }

    public static /* synthetic */ MoveActionResponse copy$default(MoveActionResponse moveActionResponse, String string, String string2, String string3, int n, Object object) {
        if ((n & 1) != 0) {
            string = moveActionResponse.moveName;
        }
        if ((n & 2) != 0) {
            string2 = moveActionResponse.targetPnx;
        }
        if ((n & 4) != 0) {
            string3 = moveActionResponse.gimmickID;
        }
        return moveActionResponse.copy(string, string2, string3);
    }

    @NotNull
    public String toString() {
        return "MoveActionResponse(moveName=" + this.moveName + ", targetPnx=" + this.targetPnx + ", gimmickID=" + this.gimmickID + ")";
    }

    public int hashCode() {
        int result = this.moveName.hashCode();
        result = result * 31 + (this.targetPnx == null ? 0 : this.targetPnx.hashCode());
        result = result * 31 + (this.gimmickID == null ? 0 : this.gimmickID.hashCode());
        return result;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof MoveActionResponse)) {
            return false;
        }
        MoveActionResponse moveActionResponse = (MoveActionResponse)other;
        if (!Intrinsics.areEqual((Object)this.moveName, (Object)moveActionResponse.moveName)) {
            return false;
        }
        if (!Intrinsics.areEqual((Object)this.targetPnx, (Object)moveActionResponse.targetPnx)) {
            return false;
        }
        return Intrinsics.areEqual((Object)this.gimmickID, (Object)moveActionResponse.gimmickID);
    }

    private static final void saveToBuffer$lambda$4(FriendlyByteBuf $buffer, FriendlyByteBuf friendlyByteBuf, String targetPnx) {
        Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
        $buffer.m_130070_(targetPnx);
    }

    private static final void saveToBuffer$lambda$5(FriendlyByteBuf $buffer, FriendlyByteBuf friendlyByteBuf, String gimmickID) {
        Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
        $buffer.m_130070_(gimmickID);
    }

    private static final String loadFromBuffer$lambda$6(FriendlyByteBuf $buffer, FriendlyByteBuf it) {
        Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
        return $buffer.m_130277_();
    }

    private static final String loadFromBuffer$lambda$7(FriendlyByteBuf $buffer, FriendlyByteBuf it) {
        Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
        return $buffer.m_130277_();
    }
}

