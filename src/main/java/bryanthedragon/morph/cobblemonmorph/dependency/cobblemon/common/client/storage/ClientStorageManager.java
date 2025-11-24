/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.storage;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.StorePosition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PartyPosition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.PCPosition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.storage.ClientPC;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.storage.ClientParty;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000T\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0018\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010%\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\f\u0018\u00002\u00020\u0001B\u0007\u00a2\u0006\u0004\bF\u0010\u0004J\r\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0003\u0010\u0004J%\u0010\u000b\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\t\u00a2\u0006\u0004\b\u000b\u0010\fJ\u001f\u0010\u0010\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\r\u001a\u00020\u00072\u0006\u0010\u000e\u001a\u00020\u0007\u00a2\u0006\u0004\b\u0010\u0010\u0011J%\u0010\u0014\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u00072\u0006\u0010\u000e\u001a\u00020\u00072\u0006\u0010\u0013\u001a\u00020\u0012\u00a2\u0006\u0004\b\u0014\u0010\u0015J%\u0010\u0017\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u00072\u0006\u0010\u000e\u001a\u00020\u00072\u0006\u0010\u0013\u001a\u00020\u0016\u00a2\u0006\u0004\b\u0017\u0010\u0018J\r\u0010\u0019\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0019\u0010\u0004J\r\u0010\u001a\u001a\u00020\u0002\u00a2\u0006\u0004\b\u001a\u0010\u0004J\u001d\u0010\u001b\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u00072\u0006\u0010\u000e\u001a\u00020\u0007\u00a2\u0006\u0004\b\u001b\u0010\u001cJ\u001d\u0010\u001d\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u00072\u0006\u0010\u000e\u001a\u00020\u0007\u00a2\u0006\u0004\b\u001d\u0010\u001cJ%\u0010 \u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u00072\u0006\u0010\u001e\u001a\u00020\u00122\u0006\u0010\u001f\u001a\u00020\u000f\u00a2\u0006\u0004\b \u0010!J%\u0010\"\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u00072\u0006\u0010\u001e\u001a\u00020\u00162\u0006\u0010\u001f\u001a\u00020\u000f\u00a2\u0006\u0004\b\"\u0010#J\u0015\u0010$\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u0007\u00a2\u0006\u0004\b$\u0010%J\u0015\u0010'\u001a\u00020\u00022\u0006\u0010&\u001a\u00020\u0005\u00a2\u0006\u0004\b'\u0010(J%\u0010+\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u00072\u0006\u0010)\u001a\u00020\u00072\u0006\u0010*\u001a\u00020\u0007\u00a2\u0006\u0004\b+\u0010,J%\u0010-\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u00072\u0006\u0010)\u001a\u00020\u00072\u0006\u0010*\u001a\u00020\u0007\u00a2\u0006\u0004\b-\u0010,J\u0015\u0010.\u001a\u00020\u00022\u0006\u0010\u001f\u001a\u00020\u0007\u00a2\u0006\u0004\b.\u0010%R\"\u00100\u001a\u00020/8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b0\u00101\u001a\u0004\b2\u00103\"\u0004\b4\u00105R#\u00107\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020/068\u0006\u00a2\u0006\f\n\u0004\b7\u00108\u001a\u0004\b9\u0010:R#\u0010<\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020;068\u0006\u00a2\u0006\f\n\u0004\b<\u00108\u001a\u0004\b=\u0010:R\u0018\u0010>\u001a\u0004\u0018\u00010\u00078\u0002@\u0002X\u0082\u000e\u00a2\u0006\u0006\n\u0004\b>\u0010?R\"\u0010@\u001a\u00020\t8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b@\u0010A\u001a\u0004\bB\u0010C\"\u0004\bD\u0010E\u00a8\u0006G"}, d2={"Lcom/cobblemon/mod/common/client/storage/ClientStorageManager;", "", "", "checkSelectedPokemon", "()V", "", "mine", "Ljava/util/UUID;", "uuid", "", "slots", "createParty", "(ZLjava/util/UUID;I)V", "storeID", "pokemonID", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "locatePokemon", "(Ljava/util/UUID;Ljava/util/UUID;)Lcom/cobblemon/mod/common/pokemon/Pokemon;", "Lcom/cobblemon/mod/common/api/storage/pc/PCPosition;", "newPosition", "moveInPC", "(Ljava/util/UUID;Ljava/util/UUID;Lcom/cobblemon/mod/common/api/storage/pc/PCPosition;)V", "Lcom/cobblemon/mod/common/api/storage/party/PartyPosition;", "moveInParty", "(Ljava/util/UUID;Ljava/util/UUID;Lcom/cobblemon/mod/common/api/storage/party/PartyPosition;)V", "onLogin", "onLogout", "removeFromPC", "(Ljava/util/UUID;Ljava/util/UUID;)V", "removeFromParty", "position", "pokemon", "setPCPokemon", "(Ljava/util/UUID;Lcom/cobblemon/mod/common/api/storage/pc/PCPosition;Lcom/cobblemon/mod/common/pokemon/Pokemon;)V", "setPartyPokemon", "(Ljava/util/UUID;Lcom/cobblemon/mod/common/api/storage/party/PartyPosition;Lcom/cobblemon/mod/common/pokemon/Pokemon;)V", "setPartyStore", "(Ljava/util/UUID;)V", "forward", "shiftSelected", "(Z)V", "pokemonID1", "pokemonID2", "swapInPC", "(Ljava/util/UUID;Ljava/util/UUID;Ljava/util/UUID;)V", "swapInParty", "switchToPokemon", "Lcom/cobblemon/mod/common/client/storage/ClientParty;", "myParty", "Lcom/cobblemon/mod/common/client/storage/ClientParty;", "getMyParty", "()Lcom/cobblemon/mod/common/client/storage/ClientParty;", "setMyParty", "(Lcom/cobblemon/mod/common/client/storage/ClientParty;)V", "", "partyStores", "Ljava/util/Map;", "getPartyStores", "()Ljava/util/Map;", "Lcom/cobblemon/mod/common/client/storage/ClientPC;", "pcStores", "getPcStores", "selectedPokemon", "Ljava/util/UUID;", "selectedSlot", "I", "getSelectedSlot", "()I", "setSelectedSlot", "(I)V", "<init>", "common"})
@SourceDebugExtension(value={"SMAP\nClientStorageManager.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ClientStorageManager.kt\ncom/cobblemon/mod/common/client/storage/ClientStorageManager\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,155:1\n1747#2,3:156\n288#2,2:159\n*S KotlinDebug\n*F\n+ 1 ClientStorageManager.kt\ncom/cobblemon/mod/common/client/storage/ClientStorageManager\n*L\n33#1:156,3\n63#1:159,2\n*E\n"})
public final class ClientStorageManager {
    @NotNull
    private ClientParty myParty;
    @NotNull
    private final Map<UUID, ClientParty> partyStores;
    @NotNull
    private final Map<UUID, ClientPC> pcStores;
    private int selectedSlot;
    @Nullable
    private UUID selectedPokemon;

    public ClientStorageManager() {
        UUID uUID = UUID.randomUUID();
        Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"randomUUID()");
        this.myParty = new ClientParty(uUID, 1);
        this.partyStores = new LinkedHashMap();
        this.pcStores = new LinkedHashMap();
        this.selectedSlot = -1;
    }

    @NotNull
    public final ClientParty getMyParty() {
        return this.myParty;
    }

    public final void setMyParty(@NotNull ClientParty clientParty) {
        Intrinsics.checkNotNullParameter((Object)clientParty, (String)"<set-?>");
        this.myParty = clientParty;
    }

    @NotNull
    public final Map<UUID, ClientParty> getPartyStores() {
        return this.partyStores;
    }

    @NotNull
    public final Map<UUID, ClientPC> getPcStores() {
        return this.pcStores;
    }

    public final int getSelectedSlot() {
        return this.selectedSlot;
    }

    public final void setSelectedSlot(int n) {
        this.selectedSlot = n;
    }

    public final void shiftSelected(boolean forward) {
        boolean partyHasSome;
        block10: {
            Iterable $this$any$iv = this.myParty.getSlots();
            boolean $i$f$any = false;
            if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                v0 = false;
            } else {
                for (Object element$iv : $this$any$iv) {
                    Pokemon it = (Pokemon)element$iv;
                    boolean bl = false;
                    if (!(it != null)) continue;
                    v0 = true;
                    break block10;
                }
                v0 = partyHasSome = false;
            }
        }
        if (!partyHasSome) {
            this.selectedSlot = 0;
            this.selectedPokemon = null;
            return;
        }
        this.selectedSlot += forward ? 1 : -1;
        if (this.selectedSlot >= this.myParty.getSlots().size()) {
            this.selectedSlot = -1;
            this.shiftSelected(forward);
        } else if (this.selectedSlot < 0) {
            this.selectedSlot = this.myParty.getSlots().size();
            this.shiftSelected(forward);
        } else if (this.myParty.get(this.selectedSlot) == null) {
            this.shiftSelected(forward);
        } else {
            Pokemon pokemon = this.myParty.get(this.selectedSlot);
            this.selectedPokemon = pokemon != null ? pokemon.getUuid() : null;
        }
    }

    public final void switchToPokemon(@NotNull UUID pokemon) {
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        this.selectedPokemon = pokemon;
        this.selectedSlot = CollectionsKt.indexOf((Iterable)this.myParty, (Object)this.myParty.findByUUID(pokemon));
        this.checkSelectedPokemon();
    }

    /*
     * WARNING - void declaration
     */
    public final void checkSelectedPokemon() {
        if (this.selectedSlot == -1) {
            Object v0;
            block12: {
                Iterable $this$firstOrNull$iv = this.myParty;
                boolean $i$f$firstOrNull = false;
                for (Object element$iv : $this$firstOrNull$iv) {
                    Pokemon it = (Pokemon)element$iv;
                    boolean bl = false;
                    if (!(it != null)) continue;
                    v0 = element$iv;
                    break block12;
                }
                v0 = null;
            }
            Pokemon pokemon = v0;
            if (pokemon == null) {
                return;
            }
            Pokemon pokemon2 = pokemon;
            this.selectedSlot = this.myParty.getSlots().indexOf(pokemon2);
            this.selectedPokemon = pokemon2.getUuid();
        } else if (this.selectedPokemon == null) {
            ClientStorageManager clientStorageManager = this;
            Object object = this.myParty.get(new PartyPosition(this.selectedSlot));
            if (object == null || (object = ((Pokemon)object).getUuid()) == null) {
                void $this$checkSelectedPokemon_u24lambda_u242;
                ClientStorageManager $i$f$firstOrNull = this;
                ClientStorageManager clientStorageManager2 = clientStorageManager;
                boolean bl = false;
                $this$checkSelectedPokemon_u24lambda_u242.selectedSlot = -1;
                $this$checkSelectedPokemon_u24lambda_u242.checkSelectedPokemon();
                object = null;
                clientStorageManager = clientStorageManager2;
            }
            clientStorageManager.selectedPokemon = object;
        } else {
            UUID uUID = this.selectedPokemon;
            Intrinsics.checkNotNull((Object)uUID);
            if (this.myParty.getPosition(uUID) != this.selectedSlot) {
                UUID uUID2 = this.selectedPokemon;
                Intrinsics.checkNotNull((Object)uUID2);
                int foundSlot = this.myParty.getPosition(uUID2);
                if (foundSlot != -1) {
                    this.selectedSlot = foundSlot;
                } else {
                    this.selectedPokemon = null;
                    this.checkSelectedPokemon();
                }
            } else if (this.selectedSlot >= this.myParty.getSlots().size()) {
                this.selectedSlot = -1;
                this.checkSelectedPokemon();
            }
        }
    }

    @Nullable
    public final Pokemon locatePokemon(@NotNull UUID storeID, @NotNull UUID pokemonID) {
        Intrinsics.checkNotNullParameter((Object)storeID, (String)"storeID");
        Intrinsics.checkNotNullParameter((Object)pokemonID, (String)"pokemonID");
        Object object = this.partyStores.get(storeID);
        if (object == null || (object = ((ClientParty)object).findByUUID(pokemonID)) == null) {
            ClientPC clientPC = this.pcStores.get(storeID);
            object = clientPC != null ? clientPC.findByUUID(pokemonID) : null;
        }
        return object;
    }

    public final void createParty(boolean mine, @NotNull UUID uuid2, int slots) {
        Intrinsics.checkNotNullParameter((Object)uuid2, (String)"uuid");
        ClientParty party = new ClientParty(uuid2, slots);
        this.partyStores.put(uuid2, party);
        if (mine) {
            this.myParty = party;
            this.checkSelectedPokemon();
        }
    }

    public final void setPartyPokemon(@NotNull UUID storeID, @NotNull PartyPosition position, @NotNull Pokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)storeID, (String)"storeID");
        Intrinsics.checkNotNullParameter((Object)position, (String)"position");
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        ClientParty clientParty = this.partyStores.get(storeID);
        if (clientParty == null) {
            Cobblemon.INSTANCE.getLOGGER().error("Tried setting a Pok\u00e9mon in position " + position + " for party store " + storeID + " but no such store found.");
            return;
        }
        ClientParty party = clientParty;
        party.set(position, pokemon);
        this.checkSelectedPokemon();
    }

    public final void setPCPokemon(@NotNull UUID storeID, @NotNull PCPosition position, @NotNull Pokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)storeID, (String)"storeID");
        Intrinsics.checkNotNullParameter((Object)position, (String)"position");
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        ClientPC clientPC = this.pcStores.get(storeID);
        if (clientPC == null) {
            Cobblemon.INSTANCE.getLOGGER().error("Tried setting a Pok\u00e9mon in position " + position + " for PC store " + storeID + " but no such store found.");
            return;
        }
        ClientPC pc = clientPC;
        pc.set(position, pokemon);
    }

    public final void setPartyStore(@NotNull UUID storeID) {
        Intrinsics.checkNotNullParameter((Object)storeID, (String)"storeID");
        ClientParty clientParty = this.partyStores.get(storeID);
        if (clientParty == null) {
            throw new IllegalArgumentException("Was told to set party store to " + storeID + " but no such store is known!");
        }
        this.myParty = clientParty;
        this.checkSelectedPokemon();
    }

    public final void removeFromParty(@NotNull UUID storeID, @NotNull UUID pokemonID) {
        Intrinsics.checkNotNullParameter((Object)storeID, (String)"storeID");
        Intrinsics.checkNotNullParameter((Object)pokemonID, (String)"pokemonID");
        ClientParty clientParty = this.partyStores.get(storeID);
        if (clientParty != null) {
            clientParty.remove(pokemonID);
        }
        this.checkSelectedPokemon();
    }

    public final void moveInParty(@NotNull UUID storeID, @NotNull UUID pokemonID, @NotNull PartyPosition newPosition) {
        Intrinsics.checkNotNullParameter((Object)storeID, (String)"storeID");
        Intrinsics.checkNotNullParameter((Object)pokemonID, (String)"pokemonID");
        Intrinsics.checkNotNullParameter((Object)newPosition, (String)"newPosition");
        ClientParty clientParty = this.partyStores.get(storeID);
        if (clientParty != null) {
            clientParty.move(pokemonID, (StorePosition)newPosition);
        }
        this.checkSelectedPokemon();
    }

    public final void swapInParty(@NotNull UUID storeID, @NotNull UUID pokemonID1, @NotNull UUID pokemonID2) {
        Intrinsics.checkNotNullParameter((Object)storeID, (String)"storeID");
        Intrinsics.checkNotNullParameter((Object)pokemonID1, (String)"pokemonID1");
        Intrinsics.checkNotNullParameter((Object)pokemonID2, (String)"pokemonID2");
        ClientParty clientParty = this.partyStores.get(storeID);
        if (clientParty != null) {
            clientParty.swap(pokemonID1, pokemonID2);
        }
        this.checkSelectedPokemon();
    }

    public final void swapInPC(@NotNull UUID storeID, @NotNull UUID pokemonID1, @NotNull UUID pokemonID2) {
        block0: {
            Intrinsics.checkNotNullParameter((Object)storeID, (String)"storeID");
            Intrinsics.checkNotNullParameter((Object)pokemonID1, (String)"pokemonID1");
            Intrinsics.checkNotNullParameter((Object)pokemonID2, (String)"pokemonID2");
            ClientPC clientPC = this.pcStores.get(storeID);
            if (clientPC == null) break block0;
            clientPC.swap(pokemonID1, pokemonID2);
        }
    }

    public final void moveInPC(@NotNull UUID storeID, @NotNull UUID pokemonID, @NotNull PCPosition newPosition) {
        Intrinsics.checkNotNullParameter((Object)storeID, (String)"storeID");
        Intrinsics.checkNotNullParameter((Object)pokemonID, (String)"pokemonID");
        Intrinsics.checkNotNullParameter((Object)newPosition, (String)"newPosition");
        ClientPC clientPC = this.pcStores.get(storeID);
        if (clientPC != null) {
            clientPC.move(pokemonID, (StorePosition)newPosition);
        }
        this.checkSelectedPokemon();
    }

    public final void removeFromPC(@NotNull UUID storeID, @NotNull UUID pokemonID) {
        block0: {
            Intrinsics.checkNotNullParameter((Object)storeID, (String)"storeID");
            Intrinsics.checkNotNullParameter((Object)pokemonID, (String)"pokemonID");
            ClientPC clientPC = this.pcStores.get(storeID);
            if (clientPC == null) break block0;
            clientPC.remove(pokemonID);
        }
    }

    public final void onLogin() {
        UUID uUID = UUID.randomUUID();
        Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"randomUUID()");
        this.myParty = new ClientParty(uUID, 1);
        this.checkSelectedPokemon();
    }

    public final void onLogout() {
        this.partyStores.clear();
        this.pcStores.clear();
    }
}

