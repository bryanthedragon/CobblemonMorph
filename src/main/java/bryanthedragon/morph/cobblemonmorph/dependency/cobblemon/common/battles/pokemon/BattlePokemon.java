/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Lazy
 *  kotlin.LazyKt
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.network.chat.MutableComponent
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.struct.VariableStruct;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.BattleActor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.MoveSet;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.helditem.HeldItemManager;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.helditem.HeldItemProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stat;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ActiveBattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownActionRequest;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownPokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownSide;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.actor.MultiPokemonBattleActor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.actor.PokemonBattleActor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.ContextManager;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleUpdateTeamPokemonPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.IVs;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Nature;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0094\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010#\n\u0002\b\t\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\n\b\u0016\u0018\u0000 _2\u00020\u0001:\u0001_B/\u0012\u0006\u0010I\u001a\u00020\u001e\u0012\b\b\u0002\u0010\u001f\u001a\u00020\u001e\u0012\u0014\b\u0002\u0010L\u001a\u000e\u0012\u0004\u0012\u00020#\u0012\u0004\u0012\u00020\u000b0K\u00a2\u0006\u0004\b]\u0010^J\r\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u000f\u0010\u0005\u001a\u0004\u0018\u00010\u0000\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u000f\u0010\b\u001a\u00020\u0007H\u0016\u00a2\u0006\u0004\b\b\u0010\tJ\r\u0010\n\u001a\u00020\u0002\u00a2\u0006\u0004\b\n\u0010\u0004J\r\u0010\f\u001a\u00020\u000b\u00a2\u0006\u0004\b\f\u0010\rJ\u0015\u0010\u0010\u001a\u00020\u000b2\u0006\u0010\u000f\u001a\u00020\u000e\u00a2\u0006\u0004\b\u0010\u0010\u0011R\"\u0010\u0013\u001a\u00020\u00128\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u0017\u0010\u001a\u001a\u00020\u00198\u0006\u00a2\u0006\f\n\u0004\b\u001a\u0010\u001b\u001a\u0004\b\u001c\u0010\u001dR\u0017\u0010\u001f\u001a\u00020\u001e8\u0006\u00a2\u0006\f\n\u0004\b\u001f\u0010 \u001a\u0004\b!\u0010\"R\u0013\u0010&\u001a\u0004\u0018\u00010#8F\u00a2\u0006\u0006\u001a\u0004\b$\u0010%R\u001d\u0010(\u001a\b\u0012\u0004\u0012\u00020\u00000'8\u0006\u00a2\u0006\f\n\u0004\b(\u0010)\u001a\u0004\b*\u0010+R\"\u0010,\u001a\u00020\u00028\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b,\u0010-\u001a\u0004\b.\u0010\u0004\"\u0004\b/\u00100R\u0011\u00104\u001a\u0002018F\u00a2\u0006\u0006\u001a\u0004\b2\u00103R\u001b\u0010:\u001a\u0002058FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b6\u00107\u001a\u0004\b8\u00109R\u0011\u0010>\u001a\u00020;8F\u00a2\u0006\u0006\u001a\u0004\b<\u0010=R\u0011\u0010@\u001a\u0002018F\u00a2\u0006\u0006\u001a\u0004\b?\u00103R\u0011\u0010D\u001a\u00020A8F\u00a2\u0006\u0006\u001a\u0004\bB\u0010CR\u0011\u0010H\u001a\u00020E8F\u00a2\u0006\u0006\u001a\u0004\bF\u0010GR\u0017\u0010I\u001a\u00020\u001e8\u0006\u00a2\u0006\f\n\u0004\bI\u0010 \u001a\u0004\bJ\u0010\"R#\u0010L\u001a\u000e\u0012\u0004\u0012\u00020#\u0012\u0004\u0012\u00020\u000b0K8\u0006\u00a2\u0006\f\n\u0004\bL\u0010M\u001a\u0004\bN\u0010OR#\u0010R\u001a\u000e\u0012\u0004\u0012\u00020Q\u0012\u0004\u0012\u0002010P8\u0006\u00a2\u0006\f\n\u0004\bR\u0010S\u001a\u0004\bT\u0010UR\u0011\u0010Y\u001a\u00020V8F\u00a2\u0006\u0006\u001a\u0004\bW\u0010XR\"\u0010Z\u001a\u00020\u00028\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bZ\u0010-\u001a\u0004\b[\u0010\u0004\"\u0004\b\\\u00100\u00a8\u0006`"}, d2={"Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;", "", "", "canBeSentOut", "()Z", "getIllusion", "()Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;", "Lnet/minecraft/network/chat/MutableComponent;", "getName", "()Lnet/minecraft/network/chat/MutableComponent;", "isSentOut", "", "sendUpdate", "()V", "Lcom/bedrockk/molang/runtime/struct/VariableStruct;", "struct", "writeVariables", "(Lcom/bedrockk/molang/runtime/struct/VariableStruct;)V", "Lcom/cobblemon/mod/common/api/battles/model/actor/BattleActor;", "actor", "Lcom/cobblemon/mod/common/api/battles/model/actor/BattleActor;", "getActor", "()Lcom/cobblemon/mod/common/api/battles/model/actor/BattleActor;", "setActor", "(Lcom/cobblemon/mod/common/api/battles/model/actor/BattleActor;)V", "Lcom/cobblemon/mod/common/battles/interpreter/ContextManager;", "contextManager", "Lcom/cobblemon/mod/common/battles/interpreter/ContextManager;", "getContextManager", "()Lcom/cobblemon/mod/common/battles/interpreter/ContextManager;", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "effectedPokemon", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "getEffectedPokemon", "()Lcom/cobblemon/mod/common/pokemon/Pokemon;", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "getEntity", "()Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "entity", "", "facedOpponents", "Ljava/util/Set;", "getFacedOpponents", "()Ljava/util/Set;", "gone", "Z", "getGone", "setGone", "(Z)V", "", "getHealth", "()I", "health", "Lcom/cobblemon/mod/common/api/pokemon/helditem/HeldItemManager;", "heldItemManager$delegate", "Lkotlin/Lazy;", "getHeldItemManager", "()Lcom/cobblemon/mod/common/api/pokemon/helditem/HeldItemManager;", "heldItemManager", "Lcom/cobblemon/mod/common/pokemon/IVs;", "getIvs", "()Lcom/cobblemon/mod/common/pokemon/IVs;", "ivs", "getMaxHealth", "maxHealth", "Lcom/cobblemon/mod/common/api/moves/MoveSet;", "getMoveSet", "()Lcom/cobblemon/mod/common/api/moves/MoveSet;", "moveSet", "Lcom/cobblemon/mod/common/pokemon/Nature;", "getNature", "()Lcom/cobblemon/mod/common/pokemon/Nature;", "nature", "originalPokemon", "getOriginalPokemon", "Lkotlin/Function1;", "postBattleEntityOperation", "Lkotlin/jvm/functions/Function1;", "getPostBattleEntityOperation", "()Lkotlin/jvm/functions/Function1;", "", "Lcom/cobblemon/mod/common/api/pokemon/stats/Stat;", "statChanges", "Ljava/util/Map;", "getStatChanges", "()Ljava/util/Map;", "Ljava/util/UUID;", "getUuid", "()Ljava/util/UUID;", "uuid", "willBeSwitchedIn", "getWillBeSwitchedIn", "setWillBeSwitchedIn", "<init>", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;Lcom/cobblemon/mod/common/pokemon/Pokemon;Lkotlin/jvm/functions/Function1;)V", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nBattlePokemon.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BattlePokemon.kt\ncom/cobblemon/mod/common/battles/pokemon/BattlePokemon\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,108:1\n1747#2,3:109\n1#3:112\n*S KotlinDebug\n*F\n+ 1 BattlePokemon.kt\ncom/cobblemon/mod/common/battles/pokemon/BattlePokemon\n*L\n95#1:109,3\n*E\n"})
public class BattlePokemon {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final Pokemon originalPokemon;
    @NotNull
    private final Pokemon effectedPokemon;
    @NotNull
    private final Function1<PokemonEntity, Unit> postBattleEntityOperation;
    public BattleActor actor;
    @NotNull
    private final Map<Stat, Integer> statChanges;
    private boolean gone;
    private boolean willBeSwitchedIn;
    @NotNull
    private final Set<BattlePokemon> facedOpponents;
    @NotNull
    private final Lazy heldItemManager$delegate;
    @NotNull
    private final ContextManager contextManager;

    public BattlePokemon(@NotNull Pokemon originalPokemon, @NotNull Pokemon effectedPokemon, @NotNull Function1<? super PokemonEntity, Unit> postBattleEntityOperation) {
        Intrinsics.checkNotNullParameter((Object)originalPokemon, (String)"originalPokemon");
        Intrinsics.checkNotNullParameter((Object)effectedPokemon, (String)"effectedPokemon");
        Intrinsics.checkNotNullParameter(postBattleEntityOperation, (String)"postBattleEntityOperation");
        this.originalPokemon = originalPokemon;
        this.effectedPokemon = effectedPokemon;
        this.postBattleEntityOperation = postBattleEntityOperation;
        this.statChanges = new LinkedHashMap();
        this.facedOpponents = new LinkedHashSet();
        this.heldItemManager$delegate = LazyKt.lazy((Function0)((Function0)new Function0<HeldItemManager>(this){
            final /* synthetic */ BattlePokemon this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final HeldItemManager invoke() {
                return HeldItemProvider.INSTANCE.provide(this.this$0);
            }
        }));
        this.contextManager = new ContextManager();
    }

    public /* synthetic */ BattlePokemon(Pokemon pokemon, Pokemon pokemon2, Function1 function1, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 2) != 0) {
            pokemon2 = pokemon;
        }
        if ((n & 4) != 0) {
            function1 = 1.INSTANCE;
        }
        this(pokemon, pokemon2, (Function1<? super PokemonEntity, Unit>)function1);
    }

    @NotNull
    public final Pokemon getOriginalPokemon() {
        return this.originalPokemon;
    }

    @NotNull
    public final Pokemon getEffectedPokemon() {
        return this.effectedPokemon;
    }

    @NotNull
    public final Function1<PokemonEntity, Unit> getPostBattleEntityOperation() {
        return this.postBattleEntityOperation;
    }

    @NotNull
    public final BattleActor getActor() {
        BattleActor battleActor = this.actor;
        if (battleActor != null) {
            return battleActor;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"actor");
        return null;
    }

    public final void setActor(@NotNull BattleActor battleActor) {
        Intrinsics.checkNotNullParameter((Object)battleActor, (String)"<set-?>");
        this.actor = battleActor;
    }

    @NotNull
    public final UUID getUuid() {
        UUID uUID = this.effectedPokemon.getUuid();
        Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"effectedPokemon.uuid");
        return uUID;
    }

    public final int getHealth() {
        return this.effectedPokemon.getCurrentHealth();
    }

    public final int getMaxHealth() {
        return this.effectedPokemon.getHp();
    }

    @NotNull
    public final IVs getIvs() {
        return this.effectedPokemon.getIvs();
    }

    @NotNull
    public final Nature getNature() {
        return this.effectedPokemon.getNature();
    }

    @NotNull
    public final MoveSet getMoveSet() {
        return this.effectedPokemon.getMoveSet();
    }

    @NotNull
    public final Map<Stat, Integer> getStatChanges() {
        return this.statChanges;
    }

    public final boolean getGone() {
        return this.gone;
    }

    public final void setGone(boolean bl) {
        this.gone = bl;
    }

    @Nullable
    public final PokemonEntity getEntity() {
        return this.effectedPokemon.getEntity();
    }

    public final boolean getWillBeSwitchedIn() {
        return this.willBeSwitchedIn;
    }

    public final void setWillBeSwitchedIn(boolean bl) {
        this.willBeSwitchedIn = bl;
    }

    @NotNull
    public final Set<BattlePokemon> getFacedOpponents() {
        return this.facedOpponents;
    }

    @NotNull
    public final HeldItemManager getHeldItemManager() {
        Lazy lazy = this.heldItemManager$delegate;
        return (HeldItemManager)lazy.getValue();
    }

    @NotNull
    public final ContextManager getContextManager() {
        return this.contextManager;
    }

    @NotNull
    public MutableComponent getName() {
        MutableComponent mutableComponent;
        Object displayPokemon;
        Object object = this.getIllusion();
        if (object == null || (object = ((BattlePokemon)object).effectedPokemon) == null) {
            object = displayPokemon = this.effectedPokemon;
        }
        if (this.getActor() instanceof PokemonBattleActor || this.getActor() instanceof MultiPokemonBattleActor) {
            mutableComponent = ((Pokemon)displayPokemon).getDisplayName();
        } else {
            Object[] objectArray = new Object[]{this.getActor().getName(), ((Pokemon)displayPokemon).getDisplayName()};
            MutableComponent mutableComponent2 = LocalizationUtilsKt.battleLang("owned_pokemon", objectArray);
            mutableComponent = mutableComponent2;
            Intrinsics.checkNotNullExpressionValue((Object)mutableComponent2, (String)"{\n            battleLang\u2026tDisplayName())\n        }");
        }
        return mutableComponent;
    }

    public final void sendUpdate() {
        this.getActor().sendUpdate(new BattleUpdateTeamPokemonPacket(this.effectedPokemon));
    }

    public final boolean isSentOut() {
        boolean bl;
        block3: {
            Iterable<ActiveBattlePokemon> $this$any$iv = this.getActor().getBattle().getActivePokemon();
            boolean $i$f$any = false;
            if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                bl = false;
            } else {
                Iterator<ActiveBattlePokemon> iterator = $this$any$iv.iterator();
                while (iterator.hasNext()) {
                    ActiveBattlePokemon element$iv;
                    ActiveBattlePokemon it = element$iv = iterator.next();
                    boolean bl2 = false;
                    if (!Intrinsics.areEqual((Object)it.getBattlePokemon(), (Object)this)) continue;
                    bl = true;
                    break block3;
                }
                bl = false;
            }
        }
        return bl;
    }

    public final boolean canBeSentOut() {
        Object object = this.getActor().getRequest();
        return (object != null && (object = ((ShowdownActionRequest)object).getSide()) != null && (object = ((ShowdownSide)object).getPokemon()) != null && (object = object.get(0)) != null ? ((ShowdownPokemon)object).getReviving() : false) ? !this.isSentOut() && !this.willBeSwitchedIn && this.getHealth() <= 0 : !this.isSentOut() && !this.willBeSwitchedIn && this.getHealth() > 0;
    }

    public final void writeVariables(@NotNull VariableStruct struct2) {
        Intrinsics.checkNotNullParameter((Object)struct2, (String)"struct");
        this.effectedPokemon.writeVariables(struct2);
    }

    @Nullable
    public final BattlePokemon getIllusion() {
        Object v0;
        block1: {
            Iterable iterable = this.getActor().getActivePokemon();
            for (Object t : iterable) {
                ActiveBattlePokemon it = (ActiveBattlePokemon)t;
                boolean bl = false;
                if (!Intrinsics.areEqual((Object)it.getBattlePokemon(), (Object)this)) continue;
                v0 = t;
                break block1;
            }
            v0 = null;
        }
        ActiveBattlePokemon activeBattlePokemon = v0;
        return activeBattlePokemon != null ? activeBattlePokemon.getIllusion() : null;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\b\u0010\tJ\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u0015\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0007\u0010\u0006\u00a8\u0006\n"}, d2={"Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon$Companion;", "", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;", "playerOwned", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;)Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;", "safeCopyOf", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final BattlePokemon safeCopyOf(@NotNull Pokemon pokemon) {
            Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
            return new BattlePokemon(pokemon, Pokemon.clone$default(pokemon, false, false, 3, null), (Function1<? super PokemonEntity, Unit>)((Function1)safeCopyOf.1.INSTANCE));
        }

        @NotNull
        public final BattlePokemon playerOwned(@NotNull Pokemon pokemon) {
            Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
            return new BattlePokemon(pokemon, pokemon, (Function1<? super PokemonEntity, Unit>)((Function1)playerOwned.1.INSTANCE));
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

