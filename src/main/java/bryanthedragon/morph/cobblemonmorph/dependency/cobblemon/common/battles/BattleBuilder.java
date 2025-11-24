/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.TuplesKt
 *  kotlin.Unit
 *  kotlin.collections.CollectionsKt
 *  kotlin.comparisons.ComparisonsKt
 *  kotlin.jvm.JvmOverloads
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.server.level.ServerPlayer
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.BattleActor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PartyStore;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleBuilder;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleFormat;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleRegistry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleSide;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleStartError;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleStartResult;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ErroredBattleStart;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.actor.PlayerBattleActor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.actor.PokemonBattleActor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerExtensionsKt;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u001c\u0010\u001dJ]\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00062\b\b\u0002\u0010\t\u001a\u00020\b2\b\b\u0002\u0010\u000b\u001a\u00020\n2\b\b\u0002\u0010\f\u001a\u00020\n2\b\b\u0002\u0010\u000e\u001a\u00020\r2\b\b\u0002\u0010\u0010\u001a\u00020\u000fH\u0007\u00a2\u0006\u0004\b\u0012\u0010\u0013Jk\u0010\u001a\u001a\u00020\u00112\u0006\u0010\u0014\u001a\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u00022\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u0017\u001a\u0004\u0018\u00010\u00062\b\b\u0002\u0010\t\u001a\u00020\b2\b\b\u0002\u0010\u000b\u001a\u00020\n2\b\b\u0002\u0010\f\u001a\u00020\n2\u0014\b\u0002\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u000f0\u0018H\u0007\u00a2\u0006\u0004\b\u001a\u0010\u001b\u00a8\u0006\u001e"}, d2={"Lcom/cobblemon/mod/common/battles/BattleBuilder;", "", "Lnet/minecraft/server/level/ServerPlayer;", "player", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "pokemonEntity", "Ljava/util/UUID;", "leadingPokemon", "Lcom/cobblemon/mod/common/battles/BattleFormat;", "battleFormat", "", "cloneParties", "healFirst", "", "fleeDistance", "Lcom/cobblemon/mod/common/api/storage/party/PartyStore;", "party", "Lcom/cobblemon/mod/common/battles/BattleStartResult;", "pve", "(Lnet/minecraft/server/level/ServerPlayer;Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;Ljava/util/UUID;Lcom/cobblemon/mod/common/battles/BattleFormat;ZZFLcom/cobblemon/mod/common/api/storage/party/PartyStore;)Lcom/cobblemon/mod/common/battles/BattleStartResult;", "player1", "player2", "leadingPokemonPlayer1", "leadingPokemonPlayer2", "Lkotlin/Function1;", "partyAccessor", "pvp1v1", "(Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/server/level/ServerPlayer;Ljava/util/UUID;Ljava/util/UUID;Lcom/cobblemon/mod/common/battles/BattleFormat;ZZLkotlin/jvm/functions/Function1;)Lcom/cobblemon/mod/common/battles/BattleStartResult;", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nBattleBuilder.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BattleBuilder.kt\ncom/cobblemon/mod/common/battles/BattleBuilder\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,285:1\n1045#2:286\n*S KotlinDebug\n*F\n+ 1 BattleBuilder.kt\ncom/cobblemon/mod/common/battles/BattleBuilder\n*L\n102#1:286\n*E\n"})
public final class BattleBuilder {
    @NotNull
    public static final BattleBuilder INSTANCE = new BattleBuilder();

    private BattleBuilder() {
    }

    @JvmOverloads
    @NotNull
    public final BattleStartResult pvp1v1(@NotNull ServerPlayer player1, @NotNull ServerPlayer player2, @Nullable UUID leadingPokemonPlayer1, @Nullable UUID leadingPokemonPlayer2, @NotNull BattleFormat battleFormat, boolean cloneParties, boolean healFirst, @NotNull Function1<? super ServerPlayer, ? extends PartyStore> partyAccessor) {
        BattleStartResult battleStartResult;
        Intrinsics.checkNotNullParameter((Object)player1, (String)"player1");
        Intrinsics.checkNotNullParameter((Object)player2, (String)"player2");
        Intrinsics.checkNotNullParameter((Object)battleFormat, (String)"battleFormat");
        Intrinsics.checkNotNullParameter(partyAccessor, (String)"partyAccessor");
        List<BattlePokemon> team1 = ((PartyStore)partyAccessor.invoke((Object)player1)).toBattleTeam(cloneParties, !healFirst, leadingPokemonPlayer1);
        List<BattlePokemon> team2 = ((PartyStore)partyAccessor.invoke((Object)player2)).toBattleTeam(cloneParties, !healFirst, leadingPokemonPlayer2);
        UUID uUID = player1.m_20148_();
        Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"player1.uuid");
        PlayerBattleActor player1Actor = new PlayerBattleActor(uUID, (List<? extends BattlePokemon>)team1);
        UUID uUID2 = player2.m_20148_();
        Intrinsics.checkNotNullExpressionValue((Object)uUID2, (String)"player2.uuid");
        PlayerBattleActor player2Actor = new PlayerBattleActor(uUID2, (List<? extends BattlePokemon>)team2);
        ErroredBattleStart errors = new ErroredBattleStart(null, null, 3, null);
        Pair[] pairArray = new Pair[]{TuplesKt.to((Object)player1, (Object)player1Actor), TuplesKt.to((Object)player2, (Object)player2Actor)};
        for (Pair pair : pairArray) {
            ServerPlayer player = (ServerPlayer)pair.component1();
            PlayerBattleActor actor = (PlayerBattleActor)pair.component2();
            if (actor.getPokemonList().size() < battleFormat.getBattleType().getSlotsPerActor()) {
                ((Collection)errors.getParticipantErrors().get((Object)actor)).add(BattleStartError.Companion.insufficientPokemon(player, battleFormat.getBattleType().getSlotsPerActor(), actor.getPokemonList().size()));
            }
            if (BattleRegistry.INSTANCE.getBattleByParticipatingPlayer(player) == null) continue;
            ((Collection)errors.getParticipantErrors().get((Object)actor)).add(BattleStartError.Companion.alreadyInBattle(player));
        }
        if (errors.isEmpty()) {
            BattleActor[] battleActorArray2 = new BattleActor[]{player1Actor};
            battleActorArray2 = new BattleActor[]{player2Actor};
            battleStartResult = BattleRegistry.startBattle$default(BattleRegistry.INSTANCE, battleFormat, new BattleSide(battleActorArray), new BattleSide(battleActorArray2), false, 8, null).ifSuccessful((Function1<? super PokemonBattle, Unit>)((Function1)new Function1<PokemonBattle, Unit>(player1Actor, player2, player2Actor, player1){
                final /* synthetic */ PlayerBattleActor $player1Actor;
                final /* synthetic */ ServerPlayer $player2;
                final /* synthetic */ PlayerBattleActor $player2Actor;
                final /* synthetic */ ServerPlayer $player1;
                {
                    this.$player1Actor = $player1Actor;
                    this.$player2 = $player2;
                    this.$player2Actor = $player2Actor;
                    this.$player1 = $player1;
                    super(1);
                }

                public final void invoke(@NotNull PokemonBattle it) {
                    Intrinsics.checkNotNullParameter((Object)it, (String)"it");
                    this.$player1Actor.setBattleTheme(PlayerExtensionsKt.getBattleTheme(this.$player2));
                    this.$player2Actor.setBattleTheme(PlayerExtensionsKt.getBattleTheme(this.$player1));
                }
            }));
        } else {
            battleStartResult = errors;
        }
        return battleStartResult;
    }

    public static /* synthetic */ BattleStartResult pvp1v1$default(BattleBuilder battleBuilder, ServerPlayer serverPlayer, ServerPlayer serverPlayer2, UUID uUID, UUID uUID2, BattleFormat battleFormat, boolean bl, boolean bl2, Function1 function1, int n, Object object) {
        if ((n & 4) != 0) {
            uUID = null;
        }
        if ((n & 8) != 0) {
            uUID2 = null;
        }
        if ((n & 0x10) != 0) {
            battleFormat = BattleFormat.Companion.getGEN_9_SINGLES();
        }
        if ((n & 0x20) != 0) {
            bl = false;
        }
        if ((n & 0x40) != 0) {
            bl2 = false;
        }
        if ((n & 0x80) != 0) {
            function1 = pvp1v1.1.INSTANCE;
        }
        return battleBuilder.pvp1v1(serverPlayer, serverPlayer2, uUID, uUID2, battleFormat, bl, bl2, (Function1<? super ServerPlayer, ? extends PartyStore>)function1);
    }

    @JvmOverloads
    @NotNull
    public final BattleStartResult pve(@NotNull ServerPlayer player, @NotNull PokemonEntity pokemonEntity, @Nullable UUID leadingPokemon, @NotNull BattleFormat battleFormat, boolean cloneParties, boolean healFirst, float fleeDistance, @NotNull PartyStore party) {
        BattleStartResult battleStartResult;
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter((Object)pokemonEntity, (String)"pokemonEntity");
        Intrinsics.checkNotNullParameter((Object)battleFormat, (String)"battleFormat");
        Intrinsics.checkNotNullParameter((Object)party, (String)"party");
        Iterable $this$sortedBy$iv = party.toBattleTeam(cloneParties, !healFirst, leadingPokemon);
        boolean $i$f$sortedBy = false;
        List playerTeam = CollectionsKt.sortedWith((Iterable)$this$sortedBy$iv, (Comparator)new Comparator(){

            public final int compare(T a, T b) {
                BattlePokemon it = (BattlePokemon)a;
                boolean bl = false;
                boolean bl2 = it.getHealth() <= 0;
                it = (BattlePokemon)b;
                Comparable comparable = Boolean.valueOf(bl2);
                bl = false;
                return ComparisonsKt.compareValues((Comparable)comparable, (Comparable)Boolean.valueOf(it.getHealth() <= 0));
            }
        });
        UUID uUID = player.m_20148_();
        Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"player.uuid");
        PlayerBattleActor playerActor = new PlayerBattleActor(uUID, playerTeam);
        UUID uUID2 = pokemonEntity.getPokemon().getUuid();
        Intrinsics.checkNotNullExpressionValue((Object)uUID2, (String)"pokemonEntity.pokemon.uuid");
        PokemonBattleActor wildActor = new PokemonBattleActor(uUID2, new BattlePokemon(pokemonEntity.getPokemon(), null, null, 6, null), fleeDistance, null, 8, null);
        ErroredBattleStart errors = new ErroredBattleStart(null, null, 3, null);
        if (!((Collection)playerTeam).isEmpty() && ((BattlePokemon)playerTeam.get(0)).getHealth() <= 0) {
            ((Collection)errors.getParticipantErrors().get((Object)playerActor)).add(BattleStartError.Companion.insufficientPokemon(player, battleFormat.getBattleType().getSlotsPerActor(), playerActor.getPokemonList().size()));
        }
        if (playerActor.getPokemonList().size() < battleFormat.getBattleType().getSlotsPerActor()) {
            ((Collection)errors.getParticipantErrors().get((Object)playerActor)).add(BattleStartError.Companion.insufficientPokemon(player, battleFormat.getBattleType().getSlotsPerActor(), playerActor.getPokemonList().size()));
        }
        if (BattleRegistry.INSTANCE.getBattleByParticipatingPlayer(player) != null) {
            ((Collection)errors.getParticipantErrors().get((Object)playerActor)).add(BattleStartError.Companion.alreadyInBattle(playerActor));
        }
        if (pokemonEntity.getBattleId() != null) {
            ((Collection)errors.getParticipantErrors().get((Object)wildActor)).add(BattleStartError.Companion.alreadyInBattle(wildActor));
        }
        if (errors.isEmpty()) {
            BattleActor[] battleActorArray = new BattleActor[]{playerActor};
            BattleSide battleSide = new BattleSide(battleActorArray);
            battleActorArray = new BattleActor[]{wildActor};
            battleStartResult = BattleRegistry.startBattle$default(BattleRegistry.INSTANCE, battleFormat, battleSide, new BattleSide(battleActorArray), false, 8, null).ifSuccessful((Function1<? super PokemonBattle, Unit>)((Function1)new Function1<PokemonBattle, Unit>(cloneParties, pokemonEntity, playerActor){
                final /* synthetic */ boolean $cloneParties;
                final /* synthetic */ PokemonEntity $pokemonEntity;
                final /* synthetic */ PlayerBattleActor $playerActor;
                {
                    this.$cloneParties = $cloneParties;
                    this.$pokemonEntity = $pokemonEntity;
                    this.$playerActor = $playerActor;
                    super(1);
                }

                public final void invoke(@NotNull PokemonBattle it) {
                    Intrinsics.checkNotNullParameter((Object)it, (String)"it");
                    if (!this.$cloneParties) {
                        this.$pokemonEntity.setBattleId(it.getBattleId());
                    }
                    this.$playerActor.setBattleTheme(this.$pokemonEntity.getBattleTheme());
                }
            }));
        } else {
            battleStartResult = errors;
        }
        return battleStartResult;
    }

    public static /* synthetic */ BattleStartResult pve$default(BattleBuilder battleBuilder, ServerPlayer serverPlayer, PokemonEntity pokemonEntity, UUID uUID, BattleFormat battleFormat, boolean bl, boolean bl2, float f, PartyStore partyStore, int n, Object object) {
        if ((n & 4) != 0) {
            uUID = null;
        }
        if ((n & 8) != 0) {
            battleFormat = BattleFormat.Companion.getGEN_9_SINGLES();
        }
        if ((n & 0x10) != 0) {
            bl = false;
        }
        if ((n & 0x20) != 0) {
            bl2 = false;
        }
        if ((n & 0x40) != 0) {
            f = Cobblemon.INSTANCE.getConfig().getDefaultFleeDistance();
        }
        if ((n & 0x80) != 0) {
            partyStore = PlayerExtensionsKt.party(serverPlayer);
        }
        return battleBuilder.pve(serverPlayer, pokemonEntity, uUID, battleFormat, bl, bl2, f, partyStore);
    }

    @JvmOverloads
    @NotNull
    public final BattleStartResult pvp1v1(@NotNull ServerPlayer player1, @NotNull ServerPlayer player2, @Nullable UUID leadingPokemonPlayer1, @Nullable UUID leadingPokemonPlayer2, @NotNull BattleFormat battleFormat, boolean cloneParties, boolean healFirst) {
        Intrinsics.checkNotNullParameter((Object)player1, (String)"player1");
        Intrinsics.checkNotNullParameter((Object)player2, (String)"player2");
        Intrinsics.checkNotNullParameter((Object)battleFormat, (String)"battleFormat");
        return BattleBuilder.pvp1v1$default(this, player1, player2, leadingPokemonPlayer1, leadingPokemonPlayer2, battleFormat, cloneParties, healFirst, null, 128, null);
    }

    @JvmOverloads
    @NotNull
    public final BattleStartResult pvp1v1(@NotNull ServerPlayer player1, @NotNull ServerPlayer player2, @Nullable UUID leadingPokemonPlayer1, @Nullable UUID leadingPokemonPlayer2, @NotNull BattleFormat battleFormat, boolean cloneParties) {
        Intrinsics.checkNotNullParameter((Object)player1, (String)"player1");
        Intrinsics.checkNotNullParameter((Object)player2, (String)"player2");
        Intrinsics.checkNotNullParameter((Object)battleFormat, (String)"battleFormat");
        return BattleBuilder.pvp1v1$default(this, player1, player2, leadingPokemonPlayer1, leadingPokemonPlayer2, battleFormat, cloneParties, false, null, 192, null);
    }

    @JvmOverloads
    @NotNull
    public final BattleStartResult pvp1v1(@NotNull ServerPlayer player1, @NotNull ServerPlayer player2, @Nullable UUID leadingPokemonPlayer1, @Nullable UUID leadingPokemonPlayer2, @NotNull BattleFormat battleFormat) {
        Intrinsics.checkNotNullParameter((Object)player1, (String)"player1");
        Intrinsics.checkNotNullParameter((Object)player2, (String)"player2");
        Intrinsics.checkNotNullParameter((Object)battleFormat, (String)"battleFormat");
        return BattleBuilder.pvp1v1$default(this, player1, player2, leadingPokemonPlayer1, leadingPokemonPlayer2, battleFormat, false, false, null, 224, null);
    }

    @JvmOverloads
    @NotNull
    public final BattleStartResult pvp1v1(@NotNull ServerPlayer player1, @NotNull ServerPlayer player2, @Nullable UUID leadingPokemonPlayer1, @Nullable UUID leadingPokemonPlayer2) {
        Intrinsics.checkNotNullParameter((Object)player1, (String)"player1");
        Intrinsics.checkNotNullParameter((Object)player2, (String)"player2");
        return BattleBuilder.pvp1v1$default(this, player1, player2, leadingPokemonPlayer1, leadingPokemonPlayer2, null, false, false, null, 240, null);
    }

    @JvmOverloads
    @NotNull
    public final BattleStartResult pvp1v1(@NotNull ServerPlayer player1, @NotNull ServerPlayer player2, @Nullable UUID leadingPokemonPlayer1) {
        Intrinsics.checkNotNullParameter((Object)player1, (String)"player1");
        Intrinsics.checkNotNullParameter((Object)player2, (String)"player2");
        return BattleBuilder.pvp1v1$default(this, player1, player2, leadingPokemonPlayer1, null, null, false, false, null, 248, null);
    }

    @JvmOverloads
    @NotNull
    public final BattleStartResult pvp1v1(@NotNull ServerPlayer player1, @NotNull ServerPlayer player2) {
        Intrinsics.checkNotNullParameter((Object)player1, (String)"player1");
        Intrinsics.checkNotNullParameter((Object)player2, (String)"player2");
        return BattleBuilder.pvp1v1$default(this, player1, player2, null, null, null, false, false, null, 252, null);
    }

    @JvmOverloads
    @NotNull
    public final BattleStartResult pve(@NotNull ServerPlayer player, @NotNull PokemonEntity pokemonEntity, @Nullable UUID leadingPokemon, @NotNull BattleFormat battleFormat, boolean cloneParties, boolean healFirst, float fleeDistance) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter((Object)pokemonEntity, (String)"pokemonEntity");
        Intrinsics.checkNotNullParameter((Object)battleFormat, (String)"battleFormat");
        return BattleBuilder.pve$default(this, player, pokemonEntity, leadingPokemon, battleFormat, cloneParties, healFirst, fleeDistance, null, 128, null);
    }

    @JvmOverloads
    @NotNull
    public final BattleStartResult pve(@NotNull ServerPlayer player, @NotNull PokemonEntity pokemonEntity, @Nullable UUID leadingPokemon, @NotNull BattleFormat battleFormat, boolean cloneParties, boolean healFirst) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter((Object)pokemonEntity, (String)"pokemonEntity");
        Intrinsics.checkNotNullParameter((Object)battleFormat, (String)"battleFormat");
        return BattleBuilder.pve$default(this, player, pokemonEntity, leadingPokemon, battleFormat, cloneParties, healFirst, 0.0f, null, 192, null);
    }

    @JvmOverloads
    @NotNull
    public final BattleStartResult pve(@NotNull ServerPlayer player, @NotNull PokemonEntity pokemonEntity, @Nullable UUID leadingPokemon, @NotNull BattleFormat battleFormat, boolean cloneParties) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter((Object)pokemonEntity, (String)"pokemonEntity");
        Intrinsics.checkNotNullParameter((Object)battleFormat, (String)"battleFormat");
        return BattleBuilder.pve$default(this, player, pokemonEntity, leadingPokemon, battleFormat, cloneParties, false, 0.0f, null, 224, null);
    }

    @JvmOverloads
    @NotNull
    public final BattleStartResult pve(@NotNull ServerPlayer player, @NotNull PokemonEntity pokemonEntity, @Nullable UUID leadingPokemon, @NotNull BattleFormat battleFormat) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter((Object)pokemonEntity, (String)"pokemonEntity");
        Intrinsics.checkNotNullParameter((Object)battleFormat, (String)"battleFormat");
        return BattleBuilder.pve$default(this, player, pokemonEntity, leadingPokemon, battleFormat, false, false, 0.0f, null, 240, null);
    }

    @JvmOverloads
    @NotNull
    public final BattleStartResult pve(@NotNull ServerPlayer player, @NotNull PokemonEntity pokemonEntity, @Nullable UUID leadingPokemon) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter((Object)pokemonEntity, (String)"pokemonEntity");
        return BattleBuilder.pve$default(this, player, pokemonEntity, leadingPokemon, null, false, false, 0.0f, null, 248, null);
    }

    @JvmOverloads
    @NotNull
    public final BattleStartResult pve(@NotNull ServerPlayer player, @NotNull PokemonEntity pokemonEntity) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter((Object)pokemonEntity, (String)"pokemonEntity");
        return BattleBuilder.pve$default(this, player, pokemonEntity, null, null, false, false, 0.0f, null, 252, null);
    }
}

