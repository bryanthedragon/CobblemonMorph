/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.Unit
 *  kotlin.collections.CollectionsKt
 *  kotlin.collections.MapsKt
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.functions.Function2
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.Ref$ObjectRef
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.network.chat.MutableComponent
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.callback.partymove;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.callback.MoveSelectDTO;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.callback.PartySelectPokemonDTO;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.interact.moveselect.MoveSelectConfiguration;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.interact.moveselect.MoveSelectGUI;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.interact.partyselect.PartySelectConfiguration;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.interact.partyselect.PartySelectGUI;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.callback.OpenPartyMoveCallbackPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.callback.partymove.PartyMoveSelectCancelledPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.callback.partymove.PartyPokemonMoveSelectedPacket;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\t\u0010\nJ\u001f\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u0007\u0010\b\u00a8\u0006\u000b"}, d2={"Lcom/cobblemon/mod/common/client/net/callback/partymove/OpenPartyMoveCallbackHandler;", "Lcom/cobblemon/mod/common/api/net/ClientNetworkPacketHandler;", "Lcom/cobblemon/mod/common/net/messages/client/callback/OpenPartyMoveCallbackPacket;", "packet", "Lnet/minecraft/client/Minecraft;", "client", "", "handle", "(Lcom/cobblemon/mod/common/net/messages/client/callback/OpenPartyMoveCallbackPacket;Lnet/minecraft/client/Minecraft;)V", "<init>", "()V", "common"})
public final class OpenPartyMoveCallbackHandler
implements ClientNetworkPacketHandler<OpenPartyMoveCallbackPacket> {
    @NotNull
    public static final OpenPartyMoveCallbackHandler INSTANCE = new OpenPartyMoveCallbackHandler();

    private OpenPartyMoveCallbackHandler() {
    }

    @Override
    public void handle(@NotNull OpenPartyMoveCallbackPacket packet, @NotNull Minecraft client) {
        PartySelectConfiguration partySelectConfiguration;
        Intrinsics.checkNotNullParameter((Object)packet, (String)"packet");
        Intrinsics.checkNotNullParameter((Object)client, (String)"client");
        Map pokemonToMoves = MapsKt.toMap((Iterable)packet.getPokemonList());
        Function1 cancel2 = (Function1)new Function1<Object, Unit>(packet){
            final /* synthetic */ OpenPartyMoveCallbackPacket $packet;
            {
                this.$packet = $packet;
                super(1);
            }

            public final void invoke(@NotNull Object it) {
                Intrinsics.checkNotNullParameter((Object)it, (String)"it");
                CobblemonNetwork.INSTANCE.sendToServer(new PartyMoveSelectCancelledPacket(this.$packet.getUuid()));
                if (it instanceof MoveSelectGUI) {
                    ((MoveSelectGUI)((Object)it)).closeProperly();
                } else if (it instanceof PartySelectGUI) {
                    ((PartySelectGUI)((Object)it)).closeProperly();
                }
            }
        };
        Ref.ObjectRef partySelectConfiguration2 = new Ref.ObjectRef();
        partySelectConfiguration2.element = new PartySelectConfiguration(packet.getPartyTitle(), CollectionsKt.toList((Iterable)pokemonToMoves.keySet()), (Function1<? super PartySelectGUI, Unit>)cancel2, (Function1<? super PartySelectGUI, Unit>)cancel2, (Function2<? super PartySelectGUI, ? super PartySelectPokemonDTO, Unit>)((Function2)new Function2<PartySelectGUI, PartySelectPokemonDTO, Unit>((Map<PartySelectPokemonDTO, ? extends List<MoveSelectDTO>>)pokemonToMoves, (Function1<Object, Unit>)cancel2, (Ref.ObjectRef<PartySelectConfiguration>)partySelectConfiguration2, packet){
            final /* synthetic */ Map<PartySelectPokemonDTO, List<MoveSelectDTO>> $pokemonToMoves;
            final /* synthetic */ Function1<Object, Unit> $cancel;
            final /* synthetic */ Ref.ObjectRef<PartySelectConfiguration> $partySelectConfiguration;
            final /* synthetic */ OpenPartyMoveCallbackPacket $packet;
            {
                this.$pokemonToMoves = $pokemonToMoves;
                this.$cancel = $cancel;
                this.$partySelectConfiguration = $partySelectConfiguration;
                this.$packet = $packet;
                super(2);
            }

            public final void invoke(@NotNull PartySelectGUI partySelectGUI, @NotNull PartySelectPokemonDTO it) {
                Intrinsics.checkNotNullParameter((Object)((Object)partySelectGUI), (String)"<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter((Object)it, (String)"it");
                Minecraft.m_91087_().m_91152_((Screen)new MoveSelectGUI(OpenPartyMoveCallbackHandler.access$handle$makeMoveSelectConfiguration(this.$pokemonToMoves, this.$cancel, this.$partySelectConfiguration, this.$packet, it)));
            }
        }));
        Minecraft minecraft = Minecraft.m_91087_();
        if (partySelectConfiguration2.element == null) {
            Intrinsics.throwUninitializedPropertyAccessException((String)"partySelectConfiguration");
            partySelectConfiguration = null;
        } else {
            partySelectConfiguration = (PartySelectConfiguration)partySelectConfiguration2.element;
        }
        minecraft.m_91152_((Screen)new PartySelectGUI(partySelectConfiguration));
    }

    @Override
    public void handleOnNettyThread(@NotNull OpenPartyMoveCallbackPacket packet) {
        ClientNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, (NetworkPacket)packet);
    }

    private static final MoveSelectConfiguration handle$makeMoveSelectConfiguration(Map<PartySelectPokemonDTO, ? extends List<MoveSelectDTO>> pokemonToMoves, Function1<Object, Unit> cancel2, Ref.ObjectRef<PartySelectConfiguration> partySelectConfiguration, OpenPartyMoveCallbackPacket $packet, PartySelectPokemonDTO pokemonSelectDTO) {
        MutableComponent mutableComponent = TextKt.text("");
        List<MoveSelectDTO> list = pokemonToMoves.get(pokemonSelectDTO);
        Intrinsics.checkNotNull(list);
        return new MoveSelectConfiguration(mutableComponent, list, cancel2, (Function1<? super MoveSelectGUI, Unit>)((Function1)new Function1<MoveSelectGUI, Unit>(partySelectConfiguration){
            final /* synthetic */ Ref.ObjectRef<PartySelectConfiguration> $partySelectConfiguration;
            {
                this.$partySelectConfiguration = $partySelectConfiguration;
                super(1);
            }

            public final void invoke(@NotNull MoveSelectGUI it) {
                PartySelectConfiguration partySelectConfiguration;
                Intrinsics.checkNotNullParameter((Object)((Object)it), (String)"it");
                Minecraft minecraft = Minecraft.m_91087_();
                if (this.$partySelectConfiguration.element == null) {
                    Intrinsics.throwUninitializedPropertyAccessException((String)"partySelectConfiguration");
                    partySelectConfiguration = null;
                } else {
                    partySelectConfiguration = (PartySelectConfiguration)this.$partySelectConfiguration.element;
                }
                minecraft.m_91152_((Screen)new PartySelectGUI(partySelectConfiguration));
            }
        }), (Function2<? super MoveSelectGUI, ? super MoveSelectDTO, Unit>)((Function2)new Function2<MoveSelectGUI, MoveSelectDTO, Unit>($packet, pokemonToMoves, pokemonSelectDTO){
            final /* synthetic */ OpenPartyMoveCallbackPacket $packet;
            final /* synthetic */ Map<PartySelectPokemonDTO, List<MoveSelectDTO>> $pokemonToMoves;
            final /* synthetic */ PartySelectPokemonDTO $pokemonSelectDTO;
            {
                this.$packet = $packet;
                this.$pokemonToMoves = $pokemonToMoves;
                this.$pokemonSelectDTO = $pokemonSelectDTO;
                super(2);
            }

            /*
             * WARNING - void declaration
             */
            public final void invoke(@NotNull MoveSelectGUI gui, @NotNull MoveSelectDTO moveSelectDTO) {
                int n;
                block2: {
                    void $this$indexOfFirst$iv;
                    Intrinsics.checkNotNullParameter((Object)((Object)gui), (String)"gui");
                    Intrinsics.checkNotNullParameter((Object)moveSelectDTO, (String)"moveSelectDTO");
                    List<Pair<PartySelectPokemonDTO, List<MoveSelectDTO>>> list = this.$packet.getPokemonList();
                    PartySelectPokemonDTO partySelectPokemonDTO = this.$pokemonSelectDTO;
                    boolean $i$f$indexOfFirst = false;
                    int index$iv = 0;
                    for (E item$iv : $this$indexOfFirst$iv) {
                        Pair it = (Pair)item$iv;
                        boolean bl = false;
                        if (Intrinsics.areEqual((Object)it.getFirst(), (Object)partySelectPokemonDTO)) {
                            n = index$iv;
                            break block2;
                        }
                        ++index$iv;
                    }
                    n = -1;
                }
                int pokemonIndex = n;
                List<MoveSelectDTO> list = this.$pokemonToMoves.get(this.$pokemonSelectDTO);
                Intrinsics.checkNotNull(list);
                int moveIndex = list.indexOf(moveSelectDTO);
                CobblemonNetwork.INSTANCE.sendToServer(new PartyPokemonMoveSelectedPacket(this.$packet.getUuid(), pokemonIndex, moveIndex));
                gui.closeProperly();
            }
        }));
    }

    public static final /* synthetic */ MoveSelectConfiguration access$handle$makeMoveSelectConfiguration(Map pokemonToMoves, Function1 cancel2, Ref.ObjectRef partySelectConfiguration, OpenPartyMoveCallbackPacket $packet, PartySelectPokemonDTO pokemonSelectDTO) {
        return OpenPartyMoveCallbackHandler.handle$makeMoveSelectConfiguration(pokemonToMoves, (Function1<Object, Unit>)cancel2, (Ref.ObjectRef<PartySelectConfiguration>)partySelectConfiguration, $packet, pokemonSelectDTO);
    }
}

