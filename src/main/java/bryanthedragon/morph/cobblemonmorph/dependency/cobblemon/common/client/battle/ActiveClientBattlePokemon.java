/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.player.LocalPlayer
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleFormat;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.Targetable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ClientBallDisplay;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ClientBattle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ClientBattleActor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ClientBattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ClientBattleSide;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.animations.TileAnimation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000p\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u001c\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0011\u0018\u00002\u00020\u0001B\u0019\u0012\u0006\u0010\u001f\u001a\u00020\u001e\u0012\b\u00103\u001a\u0004\u0018\u000102\u00a2\u0006\u0004\bA\u0010BJ\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u0015\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00000\u0007H\u0016\u00a2\u0006\u0004\b\b\u0010\tJ\u000f\u0010\u000b\u001a\u00020\nH\u0016\u00a2\u0006\u0004\b\u000b\u0010\fJ\u0015\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00000\rH\u0016\u00a2\u0006\u0004\b\u000e\u0010\tJ\u000f\u0010\u0010\u001a\u00020\u000fH\u0016\u00a2\u0006\u0004\b\u0010\u0010\u0011J\r\u0010\u0013\u001a\u00020\u0012\u00a2\u0006\u0004\b\u0013\u0010\u0014J\u0015\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00000\u0015H\u0016\u00a2\u0006\u0004\b\u0016\u0010\u0017J\u000f\u0010\u0019\u001a\u00020\u0018H\u0016\u00a2\u0006\u0004\b\u0019\u0010\u001aJ\u0017\u0010\u001c\u001a\u00020\u00182\u0006\u0010\u001b\u001a\u00020\u0001H\u0016\u00a2\u0006\u0004\b\u001c\u0010\u001dR\u0017\u0010\u001f\u001a\u00020\u001e8\u0006\u00a2\u0006\f\n\u0004\b\u001f\u0010 \u001a\u0004\b!\u0010\"R(\u0010%\u001a\b\u0012\u0004\u0012\u00020$0#8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b%\u0010&\u001a\u0004\b'\u0010(\"\u0004\b)\u0010*R$\u0010,\u001a\u0004\u0018\u00010+8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b,\u0010-\u001a\u0004\b.\u0010/\"\u0004\b0\u00101R$\u00103\u001a\u0004\u0018\u0001028\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b3\u00104\u001a\u0004\b5\u00106\"\u0004\b7\u00108R\"\u00109\u001a\u00020\u00028\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b9\u0010:\u001a\u0004\b;\u0010<\"\u0004\b=\u0010\u0006R\"\u0010>\u001a\u00020\u00028\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b>\u0010:\u001a\u0004\b?\u0010<\"\u0004\b@\u0010\u0006\u00a8\u0006C"}, d2={"Lcom/cobblemon/mod/common/client/battle/ActiveClientBattlePokemon;", "Lcom/cobblemon/mod/common/battles/Targetable;", "", "deltaTicks", "", "animate", "(F)V", "", "getActorPokemon", "()Ljava/util/List;", "", "getActorShowdownId", "()Ljava/lang/String;", "", "getAllActivePokemon", "Lcom/cobblemon/mod/common/battles/BattleFormat;", "getFormat", "()Lcom/cobblemon/mod/common/battles/BattleFormat;", "", "getHue", "()I", "", "getSidePokemon", "()Ljava/lang/Iterable;", "", "hasPokemon", "()Z", "other", "isAllied", "(Lcom/cobblemon/mod/common/battles/Targetable;)Z", "Lcom/cobblemon/mod/common/client/battle/ClientBattleActor;", "actor", "Lcom/cobblemon/mod/common/client/battle/ClientBattleActor;", "getActor", "()Lcom/cobblemon/mod/common/client/battle/ClientBattleActor;", "Ljava/util/concurrent/ConcurrentLinkedQueue;", "Lcom/cobblemon/mod/common/client/battle/animations/TileAnimation;", "animations", "Ljava/util/concurrent/ConcurrentLinkedQueue;", "getAnimations", "()Ljava/util/concurrent/ConcurrentLinkedQueue;", "setAnimations", "(Ljava/util/concurrent/ConcurrentLinkedQueue;)V", "Lcom/cobblemon/mod/common/client/battle/ClientBallDisplay;", "ballCapturing", "Lcom/cobblemon/mod/common/client/battle/ClientBallDisplay;", "getBallCapturing", "()Lcom/cobblemon/mod/common/client/battle/ClientBallDisplay;", "setBallCapturing", "(Lcom/cobblemon/mod/common/client/battle/ClientBallDisplay;)V", "Lcom/cobblemon/mod/common/client/battle/ClientBattlePokemon;", "battlePokemon", "Lcom/cobblemon/mod/common/client/battle/ClientBattlePokemon;", "getBattlePokemon", "()Lcom/cobblemon/mod/common/client/battle/ClientBattlePokemon;", "setBattlePokemon", "(Lcom/cobblemon/mod/common/client/battle/ClientBattlePokemon;)V", "invisibleX", "F", "getInvisibleX", "()F", "setInvisibleX", "xDisplacement", "getXDisplacement", "setXDisplacement", "<init>", "(Lcom/cobblemon/mod/common/client/battle/ClientBattleActor;Lcom/cobblemon/mod/common/client/battle/ClientBattlePokemon;)V", "common"})
@SourceDebugExtension(value={"SMAP\nActiveClientBattlePokemon.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ActiveClientBattlePokemon.kt\ncom/cobblemon/mod/common/client/battle/ActiveClientBattlePokemon\n+ 2 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,71:1\n10242#2:72\n10664#2,5:73\n10242#2:78\n10664#2,5:79\n1#3:84\n*S KotlinDebug\n*F\n+ 1 ActiveClientBattlePokemon.kt\ncom/cobblemon/mod/common/client/battle/ActiveClientBattlePokemon\n*L\n25#1:72\n25#1:73,5\n37#1:78\n37#1:79,5\n*E\n"})
public final class ActiveClientBattlePokemon
implements Targetable {
    @NotNull
    private final ClientBattleActor actor;
    @Nullable
    private ClientBattlePokemon battlePokemon;
    @NotNull
    private ConcurrentLinkedQueue<TileAnimation> animations;
    private float xDisplacement;
    private float invisibleX;
    @Nullable
    private ClientBallDisplay ballCapturing;

    public ActiveClientBattlePokemon(@NotNull ClientBattleActor actor, @Nullable ClientBattlePokemon battlePokemon) {
        Intrinsics.checkNotNullParameter((Object)actor, (String)"actor");
        this.actor = actor;
        this.battlePokemon = battlePokemon;
        this.animations = new ConcurrentLinkedQueue();
        this.invisibleX = -1.0f;
    }

    @NotNull
    public final ClientBattleActor getActor() {
        return this.actor;
    }

    @Nullable
    public final ClientBattlePokemon getBattlePokemon() {
        return this.battlePokemon;
    }

    public final void setBattlePokemon(@Nullable ClientBattlePokemon clientBattlePokemon) {
        this.battlePokemon = clientBattlePokemon;
    }

    @NotNull
    public final ConcurrentLinkedQueue<TileAnimation> getAnimations() {
        return this.animations;
    }

    public final void setAnimations(@NotNull ConcurrentLinkedQueue<TileAnimation> concurrentLinkedQueue) {
        Intrinsics.checkNotNullParameter(concurrentLinkedQueue, (String)"<set-?>");
        this.animations = concurrentLinkedQueue;
    }

    public final float getXDisplacement() {
        return this.xDisplacement;
    }

    public final void setXDisplacement(float f) {
        this.xDisplacement = f;
    }

    public final float getInvisibleX() {
        return this.invisibleX;
    }

    public final void setInvisibleX(float f) {
        this.invisibleX = f;
    }

    @Nullable
    public final ClientBallDisplay getBallCapturing() {
        return this.ballCapturing;
    }

    public final void setBallCapturing(@Nullable ClientBallDisplay clientBallDisplay) {
        this.ballCapturing = clientBallDisplay;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public List<ActiveClientBattlePokemon> getAllActivePokemon() {
        void $this$flatMapTo$iv$iv;
        ClientBattleSide[] $this$flatMap$iv = this.actor.getSide().getBattle().getSides();
        boolean $i$f$flatMap = false;
        ClientBattleSide[] clientBattleSideArray = $this$flatMap$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$flatMapTo = false;
        int n = ((void)$this$flatMapTo$iv$iv).length;
        for (int i = 0; i < n; ++i) {
            void element$iv$iv;
            void it = element$iv$iv = $this$flatMapTo$iv$iv[i];
            boolean bl = false;
            Iterable<ActiveClientBattlePokemon> list$iv$iv = it.getActiveClientBattlePokemon();
            CollectionsKt.addAll((Collection)destination$iv$iv, list$iv$iv);
        }
        return (List)destination$iv$iv;
    }

    @NotNull
    public List<ActiveClientBattlePokemon> getActorPokemon() {
        return this.actor.getActivePokemon();
    }

    @NotNull
    public Iterable<ActiveClientBattlePokemon> getSidePokemon() {
        return this.actor.getSide().getActiveClientBattlePokemon();
    }

    @Override
    @NotNull
    public String getActorShowdownId() {
        return this.actor.getShowdownId();
    }

    @Override
    @NotNull
    public BattleFormat getFormat() {
        return this.actor.getSide().getBattle().getBattleFormat();
    }

    @Override
    public boolean isAllied(@NotNull Targetable other) {
        Intrinsics.checkNotNullParameter((Object)other, (String)"other");
        return Intrinsics.areEqual((Object)this.actor.getSide(), (Object)((ActiveClientBattlePokemon)other).actor.getSide());
    }

    @Override
    public boolean hasPokemon() {
        return this.battlePokemon != null;
    }

    /*
     * WARNING - void declaration
     */
    public final int getHue() {
        Object v3;
        ClientBattle battle2;
        ClientBattleSide side;
        Object actor;
        block4: {
            void $this$flatMapTo$iv$iv;
            LocalPlayer localPlayer = Minecraft.m_91087_().f_91074_;
            UUID uUID = localPlayer != null ? localPlayer.m_20148_() : null;
            if (uUID == null) {
                return 0xFAFAFA;
            }
            UUID playerUUID = uUID;
            Object object = this.battlePokemon;
            if (object == null || (object = ((ClientBattlePokemon)object).getActor()) == null) {
                return 0xFAFAFA;
            }
            actor = object;
            side = ((ClientBattleActor)actor).getSide();
            battle2 = ((ClientBattleActor)actor).getSide().getBattle();
            ClientBattleSide[] $this$flatMap$iv = battle2.getSides();
            boolean $i$f$flatMap = false;
            ClientBattleSide[] clientBattleSideArray = $this$flatMap$iv;
            Collection destination$iv$iv = new ArrayList();
            boolean $i$f$flatMapTo = false;
            int n = ((void)$this$flatMapTo$iv$iv).length;
            for (int i = 0; i < n; ++i) {
                void element$iv$iv;
                void it = element$iv$iv = $this$flatMapTo$iv$iv[i];
                boolean bl = false;
                Iterable list$iv$iv = it.getActors();
                CollectionsKt.addAll((Collection)destination$iv$iv, (Iterable)list$iv$iv);
            }
            Iterable iterable = (List)destination$iv$iv;
            for (Object e : iterable) {
                ClientBattleActor it = (ClientBattleActor)e;
                boolean bl = false;
                if (!Intrinsics.areEqual((Object)it.getUuid(), (Object)playerUUID)) continue;
                v3 = e;
                break block4;
            }
            v3 = null;
        }
        ClientBattleActor playerActor = v3;
        return playerActor != null ? (!Intrinsics.areEqual((Object)playerActor.getSide(), (Object)side) ? (side.getActors().indexOf(actor) == 0 ? 14956600 : 13982394) : (Intrinsics.areEqual((Object)actor, (Object)playerActor) ? 2724857 : 3457708)) : (Intrinsics.areEqual((Object)side, (Object)battle2.getSide1()) ? 2724857 : 14956600);
    }

    public final void animate(float deltaTicks) {
        TileAnimation tileAnimation = this.animations.peek();
        if (tileAnimation == null) {
            return;
        }
        TileAnimation animation = tileAnimation;
        if (animation.invoke(this, deltaTicks) && (!animation.shouldHoldUntilNextAnimation() || this.animations.size() > 1)) {
            this.animations.remove();
        }
    }

    @Override
    @NotNull
    public String getPNX() {
        return Targetable.DefaultImpls.getPNX(this);
    }

    @Override
    @NotNull
    public List<Targetable> getAdjacent() {
        return Targetable.DefaultImpls.getAdjacent(this);
    }

    @Override
    @NotNull
    public List<Targetable> getAdjacentAllies() {
        return Targetable.DefaultImpls.getAdjacentAllies(this);
    }

    @Override
    @NotNull
    public List<Targetable> getAdjacentOpponents() {
        return Targetable.DefaultImpls.getAdjacentOpponents(this);
    }

    @Override
    @NotNull
    public String getSignedDigitRelativeTo(@NotNull Targetable other) {
        return Targetable.DefaultImpls.getSignedDigitRelativeTo(this, other);
    }

    @Override
    public int getDigitRelativeTo(@NotNull Targetable other) {
        return Targetable.DefaultImpls.getDigitRelativeTo(this, other);
    }

    @Override
    public int getDigit(boolean asAlly) {
        return Targetable.DefaultImpls.getDigit(this, asAlly);
    }

    @Override
    public char getLetter() {
        return Targetable.DefaultImpls.getLetter(this);
    }
}

