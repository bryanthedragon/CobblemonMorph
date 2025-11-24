/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.functions.Function3
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerPlayer
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.BattleActor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownMoveset;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownSide;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.Targetable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.IntSize;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.NetExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerExtensionsKt;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000V\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010!\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\f\u0018\u00002\u00020\u0001BI\u0012\b\b\u0002\u00103\u001a\u00020\t\u0012\u0010\b\u0002\u0010\u001e\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u001d\u0012\u000e\b\u0002\u0010\f\u001a\b\u0012\u0004\u0012\u00020\t0\u0005\u0012\b\b\u0002\u0010&\u001a\u00020\t\u0012\n\b\u0002\u0010-\u001a\u0004\u0018\u00010,\u00a2\u0006\u0004\b6\u00107Jb\u0010\u000e\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005\"\u0004\b\u0000\u0010\u0002\"\b\b\u0001\u0010\u0004*\u00020\u00032\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00010\u00052/\u0010\r\u001a+\u0012\u0004\u0012\u00028\u0001\u0012\u0006\u0012\u0004\u0018\u00010\b\u0012\u0013\u0012\u00110\t\u00a2\u0006\f\b\n\u0012\b\b\u000b\u0012\u0004\b\b(\f\u0012\u0004\u0012\u00028\u00000\u0007\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u0015\u0010\u0012\u001a\u00020\u00002\u0006\u0010\u0011\u001a\u00020\u0010\u00a2\u0006\u0004\b\u0012\u0010\u0013J\u001d\u0010\u0019\u001a\u00020\u00182\u0006\u0010\u0015\u001a\u00020\u00142\u0006\u0010\u0017\u001a\u00020\u0016\u00a2\u0006\u0004\b\u0019\u0010\u001aJ\u0015\u0010\u001b\u001a\u00020\u00182\u0006\u0010\u0011\u001a\u00020\u0010\u00a2\u0006\u0004\b\u001b\u0010\u001cR*\u0010\u001e\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u001d8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u001e\u0010\u001f\u001a\u0004\b \u0010!\"\u0004\b\"\u0010#R(\u0010\f\u001a\b\u0012\u0004\u0012\u00020\t0\u00058\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\f\u0010\u001f\u001a\u0004\b$\u0010!\"\u0004\b%\u0010#R\"\u0010&\u001a\u00020\t8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b&\u0010'\u001a\u0004\b(\u0010)\"\u0004\b*\u0010+R$\u0010-\u001a\u0004\u0018\u00010,8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b-\u0010.\u001a\u0004\b/\u00100\"\u0004\b1\u00102R\"\u00103\u001a\u00020\t8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b3\u0010'\u001a\u0004\b4\u0010)\"\u0004\b5\u0010+\u00a8\u00068"}, d2={"Lcom/cobblemon/mod/common/battles/ShowdownActionRequest;", "", "T", "Lcom/cobblemon/mod/common/battles/Targetable;", "E", "", "activePokemon", "Lkotlin/Function3;", "Lcom/cobblemon/mod/common/battles/ShowdownMoveset;", "", "Lkotlin/ParameterName;", "name", "forceSwitch", "iterator", "iterate", "(Ljava/util/List;Lkotlin/jvm/functions/Function3;)Ljava/util/List;", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "loadFromBuffer", "(Lnet/minecraft/network/FriendlyByteBuf;)Lcom/cobblemon/mod/common/battles/ShowdownActionRequest;", "Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;", "battle", "Lcom/cobblemon/mod/common/api/battles/model/actor/BattleActor;", "battleActor", "", "sanitize", "(Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;Lcom/cobblemon/mod/common/api/battles/model/actor/BattleActor;)V", "saveToBuffer", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "", "active", "Ljava/util/List;", "getActive", "()Ljava/util/List;", "setActive", "(Ljava/util/List;)V", "getForceSwitch", "setForceSwitch", "noCancel", "Z", "getNoCancel", "()Z", "setNoCancel", "(Z)V", "Lcom/cobblemon/mod/common/battles/ShowdownSide;", "side", "Lcom/cobblemon/mod/common/battles/ShowdownSide;", "getSide", "()Lcom/cobblemon/mod/common/battles/ShowdownSide;", "setSide", "(Lcom/cobblemon/mod/common/battles/ShowdownSide;)V", "wait", "getWait", "setWait", "<init>", "(ZLjava/util/List;Ljava/util/List;ZLcom/cobblemon/mod/common/battles/ShowdownSide;)V", "common"})
@SourceDebugExtension(value={"SMAP\nShowdownActionRequest.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ShowdownActionRequest.kt\ncom/cobblemon/mod/common/battles/ShowdownActionRequest\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,452:1\n1#2:453\n1855#3,2:454\n1855#3,2:456\n1855#3:458\n1855#3,2:459\n1856#3:461\n*S KotlinDebug\n*F\n+ 1 ShowdownActionRequest.kt\ncom/cobblemon/mod/common/battles/ShowdownActionRequest\n*L\n49#1:454,2\n51#1:456,2\n77#1:458\n78#1:459,2\n77#1:461\n*E\n"})
public final class ShowdownActionRequest {
    private boolean wait;
    @Nullable
    private List<ShowdownMoveset> active;
    @NotNull
    private List<Boolean> forceSwitch;
    private boolean noCancel;
    @Nullable
    private ShowdownSide side;

    public ShowdownActionRequest(boolean wait, @Nullable List<ShowdownMoveset> active, @NotNull List<Boolean> forceSwitch, boolean noCancel, @Nullable ShowdownSide side) {
        Intrinsics.checkNotNullParameter(forceSwitch, (String)"forceSwitch");
        this.wait = wait;
        this.active = active;
        this.forceSwitch = forceSwitch;
        this.noCancel = noCancel;
        this.side = side;
    }

    public /* synthetic */ ShowdownActionRequest(boolean bl, List list, List list2, boolean bl2, ShowdownSide showdownSide, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 1) != 0) {
            bl = false;
        }
        if ((n & 2) != 0) {
            list = null;
        }
        if ((n & 4) != 0) {
            list2 = CollectionsKt.emptyList();
        }
        if ((n & 8) != 0) {
            bl2 = false;
        }
        if ((n & 0x10) != 0) {
            showdownSide = null;
        }
        this(bl, list, list2, bl2, showdownSide);
    }

    public final boolean getWait() {
        return this.wait;
    }

    public final void setWait(boolean bl) {
        this.wait = bl;
    }

    @Nullable
    public final List<ShowdownMoveset> getActive() {
        return this.active;
    }

    public final void setActive(@Nullable List<ShowdownMoveset> list) {
        this.active = list;
    }

    @NotNull
    public final List<Boolean> getForceSwitch() {
        return this.forceSwitch;
    }

    public final void setForceSwitch(@NotNull List<Boolean> list) {
        Intrinsics.checkNotNullParameter(list, (String)"<set-?>");
        this.forceSwitch = list;
    }

    public final boolean getNoCancel() {
        return this.noCancel;
    }

    public final void setNoCancel(boolean bl) {
        this.noCancel = bl;
    }

    @Nullable
    public final ShowdownSide getSide() {
        return this.side;
    }

    public final void setSide(@Nullable ShowdownSide showdownSide) {
        this.side = showdownSide;
    }

    @NotNull
    public final <T, E extends Targetable> List<T> iterate(@NotNull List<? extends E> activePokemon, @NotNull Function3<? super E, ? super ShowdownMoveset, ? super Boolean, ? extends T> iterator) {
        Intrinsics.checkNotNullParameter(activePokemon, (String)"activePokemon");
        Intrinsics.checkNotNullParameter(iterator, (String)"iterator");
        List<ShowdownMoveset> list = this.active;
        int size = Integer.max(list != null ? list.size() : 0, this.forceSwitch.size());
        List responses = new ArrayList();
        for (int i = 0; i < size; ++i) {
            ShowdownMoveset showdownMoveset;
            List<Object> it;
            int index = i;
            boolean bl = false;
            List<E> it2 = activePokemon;
            boolean bl2 = false;
            if (it2.size() <= index) {
                throw new IllegalStateException("No active Pok\u00e9mon for slot " + index + " but needed to choose action for it?");
            }
            Targetable activeBattlePokemon = (Targetable)it2.get(index);
            List<ShowdownMoveset> list2 = this.active;
            if (list2 != null) {
                it = list2;
                boolean bl3 = false;
                showdownMoveset = it.size() > index ? (ShowdownMoveset)it.get(index) : null;
            } else {
                showdownMoveset = null;
            }
            ShowdownMoveset moveset = showdownMoveset;
            it = this.forceSwitch;
            boolean bl4 = false;
            boolean forceSwitch = it.size() > index ? (Boolean)it.get(index) : false;
            responses.add(iterator.invoke((Object)activeBattlePokemon, (Object)moveset, (Object)forceSwitch));
        }
        return responses;
    }

    public final void saveToBuffer(@NotNull FriendlyByteBuf buffer) {
        block3: {
            Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
            buffer.writeBoolean(this.wait);
            List<ShowdownMoveset> list = this.active;
            NetExtensionsKt.writeSizedInt((ByteBuf)buffer, IntSize.U_BYTE, list != null ? list.size() : 0);
            List<ShowdownMoveset> list2 = this.active;
            if (list2 != null) {
                Iterable $this$forEach$iv = list2;
                boolean $i$f$forEach = false;
                for (Object element$iv : $this$forEach$iv) {
                    ShowdownMoveset it = (ShowdownMoveset)element$iv;
                    boolean bl = false;
                    it.saveToBuffer(buffer);
                }
            }
            NetExtensionsKt.writeSizedInt((ByteBuf)buffer, IntSize.U_BYTE, this.forceSwitch.size());
            Iterable $this$forEach$iv = this.forceSwitch;
            boolean $i$f$forEach = false;
            for (Object element$iv : $this$forEach$iv) {
                boolean p0 = (Boolean)element$iv;
                boolean bl = false;
                buffer.writeBoolean(p0);
            }
            buffer.writeBoolean(this.noCancel);
            buffer.writeBoolean(this.side != null);
            ShowdownSide showdownSide = this.side;
            if (showdownSide == null) break block3;
            showdownSide.saveToBuffer(buffer);
        }
    }

    @NotNull
    public final ShowdownActionRequest loadFromBuffer(@NotNull FriendlyByteBuf buffer) {
        int n;
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        this.wait = buffer.readBoolean();
        int activeSize = NetExtensionsKt.readSizedInt((ByteBuf)buffer, IntSize.U_BYTE);
        if (activeSize > 0) {
            List active = new ArrayList();
            n = 0;
            while (n < activeSize) {
                int it = n++;
                boolean bl = false;
                active.add(new ShowdownMoveset().loadFromBuffer(buffer));
            }
            this.active = active;
        }
        List forceSwitch = new ArrayList();
        n = NetExtensionsKt.readSizedInt((ByteBuf)buffer, IntSize.U_BYTE);
        int n2 = 0;
        while (n2 < n) {
            int it = n2++;
            boolean bl = false;
            forceSwitch.add(buffer.readBoolean());
        }
        this.forceSwitch = forceSwitch;
        this.noCancel = buffer.readBoolean();
        if (buffer.readBoolean()) {
            this.side = new ShowdownSide().loadFromBuffer(buffer);
        }
        return this;
    }

    public final void sanitize(@NotNull PokemonBattle battle2, @NotNull BattleActor battleActor) {
        block10: {
            Object v0;
            block9: {
                Intrinsics.checkNotNullParameter((Object)battle2, (String)"battle");
                Intrinsics.checkNotNullParameter((Object)battleActor, (String)"battleActor");
                Iterable iterable = battle2.getPlayers();
                for (Object t : iterable) {
                    ServerPlayer it = (ServerPlayer)t;
                    boolean bl = false;
                    if (!Intrinsics.areEqual((Object)it.m_20148_(), (Object)battleActor.getUuid())) continue;
                    v0 = t;
                    break block9;
                }
                v0 = null;
            }
            ServerPlayer serverPlayer = v0;
            if (serverPlayer == null) {
                return;
            }
            ServerPlayer player = serverPlayer;
            List<ShowdownMoveset> list = this.active;
            if (list == null) break block10;
            Iterable $this$forEach$iv = list;
            boolean $i$f$forEach = false;
            for (Object element$iv : $this$forEach$iv) {
                ShowdownMoveset moveset = (ShowdownMoveset)element$iv;
                boolean bl = false;
                Iterable $this$forEach$iv2 = moveset.getGimmicks();
                boolean $i$f$forEach2 = false;
                for (Object element$iv2 : $this$forEach$iv2) {
                    ResourceLocation triggerItem;
                    ShowdownMoveset.Gimmick gimmick = (ShowdownMoveset.Gimmick)((Object)element$iv2);
                    boolean bl2 = false;
                    if (PlayerExtensionsKt.hasKeyItem(player, triggerItem = (switch (WhenMappings.$EnumSwitchMapping$0[gimmick.ordinal()]) {
                        case 1 -> MiscUtilsKt.cobblemonResource("key_stone");
                        case 2 -> MiscUtilsKt.cobblemonResource("dynamax_band");
                        case 3 -> MiscUtilsKt.cobblemonResource("tera_orb");
                        default -> MiscUtilsKt.cobblemonResource("z_ring");
                    }))) continue;
                    moveset.blockGimmick(gimmick);
                }
            }
        }
    }

    public ShowdownActionRequest() {
        this(false, null, null, false, null, 31, null);
    }

    @Metadata(mv={1, 8, 0}, k=3, xi=48)
    public final class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] nArray = new int[ShowdownMoveset.Gimmick.values().length];
            try {
                nArray[ShowdownMoveset.Gimmick.MEGA_EVOLUTION.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[ShowdownMoveset.Gimmick.DYNAMAX.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[ShowdownMoveset.Gimmick.TERASTALLIZATION.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            $EnumSwitchMapping$0 = nArray;
        }
    }
}

