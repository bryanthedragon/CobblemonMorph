/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.player.Player
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.starter;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.CobblemonCriteria;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.Cancelable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.CobblemonEvents;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.starter.StarterChosenEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.EventObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.starter.StarterHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PlayerPartyStore;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.PlayerData;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.config.starter.StarterCategory;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.starter.OpenStarterUIPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.gamerules.CobblemonGameRules;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\b\b\u0016\u0018\u00002\u00020\u0001B\u0007\u00a2\u0006\u0004\b\u0012\u0010\u0013J'\u0010\t\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u0006H\u0016\u00a2\u0006\u0004\b\t\u0010\nJ\u001d\u0010\r\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\r\u0010\u000eJ\u0017\u0010\u000f\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u0017\u0010\u0011\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0011\u0010\u0010\u00a8\u0006\u0014"}, d2={"Lcom/cobblemon/mod/common/starter/CobblemonStarterHandler;", "Lcom/cobblemon/mod/common/api/starter/StarterHandler;", "Lnet/minecraft/server/level/ServerPlayer;", "player", "", "categoryName", "", "index", "", "chooseStarter", "(Lnet/minecraft/server/level/ServerPlayer;Ljava/lang/String;I)V", "", "Lcom/cobblemon/mod/common/config/starter/StarterCategory;", "getStarterList", "(Lnet/minecraft/server/level/ServerPlayer;)Ljava/util/List;", "handleJoin", "(Lnet/minecraft/server/level/ServerPlayer;)V", "requestStarterChoice", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nCobbledStarterHandler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CobbledStarterHandler.kt\ncom/cobblemon/mod/common/starter/CobblemonStarterHandler\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/CancelableObservable\n+ 4 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/EventObservable\n+ 5 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 6 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/CancelableObservable$postThen$1\n*L\n1#1,73:1\n1#2:74\n39#3,2:75\n41#3,2:80\n44#3,3:83\n47#3:88\n17#4,2:77\n19#4:87\n13579#5:79\n13580#5:86\n39#6:82\n*S KotlinDebug\n*F\n+ 1 CobbledStarterHandler.kt\ncom/cobblemon/mod/common/starter/CobblemonStarterHandler\n*L\n59#1:75,2\n59#1:80,2\n59#1:83,3\n59#1:88\n59#1:77,2\n59#1:87\n59#1:79\n59#1:86\n59#1:82\n*E\n"})
public class CobblemonStarterHandler
implements StarterHandler {
    @Override
    @NotNull
    public List<StarterCategory> getStarterList(@NotNull ServerPlayer player) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        return Cobblemon.INSTANCE.getStarterConfig().getStarters();
    }

    @Override
    public void handleJoin(@NotNull ServerPlayer player) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
    }

    @Override
    public void requestStarterChoice(@NotNull ServerPlayer player) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        PlayerData playerData = Cobblemon.INSTANCE.getPlayerData().get((Player)player);
        if (playerData.getStarterSelected()) {
            playerData.sendToPlayer(player);
            MutableComponent mutableComponent = LocalizationUtilsKt.lang("ui.starter.alreadyselected", new Object[0]);
            Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"lang(\"ui.starter.alreadyselected\")");
            player.m_5661_((Component)TextKt.red(mutableComponent), true);
        } else if (playerData.getStarterLocked()) {
            MutableComponent mutableComponent = LocalizationUtilsKt.lang("ui.starter.cannotchoose", new Object[0]);
            Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"lang(\"ui.starter.cannotchoose\")");
            player.m_5661_((Component)TextKt.red(mutableComponent), true);
        } else {
            new OpenStarterUIPacket((Collection<StarterCategory>)this.getStarterList(player)).sendToPlayer(player);
            playerData.setStarterPrompted(true);
            Cobblemon.INSTANCE.getPlayerData().saveSingle(playerData);
        }
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void chooseStarter(@NotNull ServerPlayer player, @NotNull String categoryName, int index) {
        void this_$iv$iv;
        void $this$iv;
        Object v2;
        Object object;
        PlayerData playerData;
        block8: {
            Intrinsics.checkNotNullParameter((Object)player, (String)"player");
            Intrinsics.checkNotNullParameter((Object)categoryName, (String)"categoryName");
            playerData = Cobblemon.INSTANCE.getPlayerData().get((Player)player);
            if (playerData.getStarterSelected()) {
                MutableComponent mutableComponent = LocalizationUtilsKt.lang("ui.starter.alreadyselected", new Object[0]);
                Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"lang(\"ui.starter.alreadyselected\")");
                player.m_5661_((Component)TextKt.red(mutableComponent), true);
                return;
            }
            if (playerData.getStarterLocked()) {
                MutableComponent mutableComponent = LocalizationUtilsKt.lang("ui.starter.cannotchoose", new Object[0]);
                Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"lang(\"ui.starter.cannotchoose\")");
                player.m_5661_((Component)TextKt.red(mutableComponent), true);
                return;
            }
            object = this.getStarterList(player);
            Iterator iterator = object.iterator();
            while (iterator.hasNext()) {
                Object t = iterator.next();
                StarterCategory it = (StarterCategory)t;
                boolean bl = false;
                if (!Intrinsics.areEqual((Object)it.getName(), (Object)categoryName)) continue;
                v2 = t;
                break block8;
            }
            v2 = null;
        }
        StarterCategory starterCategory = v2;
        if (starterCategory == null) {
            return;
        }
        StarterCategory category = starterCategory;
        if (index > category.getPokemon().size()) {
            return;
        }
        PokemonProperties properties2 = category.getPokemon().get(index);
        Pokemon pokemon = properties2.create();
        object = CobblemonEvents.STARTER_CHOSEN;
        Cancelable event$iv = new StarterChosenEvent(player, properties2, pokemon);
        boolean $i$f$postThen = false;
        EventObservable bl = (EventObservable)$this$iv;
        Cancelable[] cancelableArray = new Cancelable[]{event$iv};
        Cancelable[] events$iv$iv = cancelableArray;
        boolean $i$f$post = false;
        this_$iv$iv.emit(Arrays.copyOf(events$iv$iv, events$iv$iv.length));
        Cancelable[] $this$forEach$iv$iv$iv = events$iv$iv;
        boolean $i$f$forEach = false;
        int n = $this$forEach$iv$iv$iv.length;
        for (int i = 0; i < n; ++i) {
            void it;
            Pokemon pokemon2;
            Cancelable it2;
            Cancelable element$iv$iv$iv;
            Cancelable it$iv = element$iv$iv$iv = $this$forEach$iv$iv$iv[i];
            boolean bl2 = false;
            if (it$iv.isCanceled()) {
                Cancelable cancelable = it$iv;
                boolean bl3 = false;
                it2 = cancelable;
                continue;
            }
            it2 = (StarterChosenEvent)it$iv;
            boolean bl4 = false;
            Pokemon pokemon3 = pokemon2 = ((StarterChosenEvent)it2).getPokemon();
            PlayerPartyStore playerPartyStore = Cobblemon.INSTANCE.getStorage().getParty(player);
            boolean bl5 = false;
            playerData.setStarterSelected(true);
            playerData.setStarterUUID(it.getUuid());
            if (player.m_9236_().m_46469_().m_46207_(CobblemonGameRules.SHINY_STARTERS)) {
                pokemon.setShiny(true);
            }
            playerPartyStore.add(pokemon2);
            CobblemonCriteria.INSTANCE.getPICK_STARTER().trigger(player, pokemon);
            Cobblemon.INSTANCE.getPlayerData().saveSingle(playerData);
            playerData.sendToPlayer(player);
        }
    }
}

