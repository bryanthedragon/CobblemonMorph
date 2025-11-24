/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.CollectionToArray
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.network.FriendlyByteBuf
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.controller;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.EvolutionController;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.EvolutionDisplay;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.progress.EvolutionProgress;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update.evolution.AddEvolutionPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.pokemon.update.evolution.AcceptEvolutionPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.CollectionToArray;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000~\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010)\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0006\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u000f\u0012\u0006\u0010A\u001a\u00020@\u00a2\u0006\u0004\bI\u0010JJ\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u001d\u0010\t\u001a\u00020\u00042\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00020\u0007H\u0016\u00a2\u0006\u0004\b\t\u0010\nJ\u000f\u0010\f\u001a\u00020\u000bH\u0016\u00a2\u0006\u0004\b\f\u0010\rJ\u0018\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0096\u0002\u00a2\u0006\u0004\b\u000e\u0010\u0006J\u001d\u0010\u000f\u001a\u00020\u00042\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00020\u0007H\u0016\u00a2\u0006\u0004\b\u000f\u0010\nJ\u000f\u0010\u0010\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u0010\u0010\u0011J\u0016\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00020\u0012H\u0096\u0002\u00a2\u0006\u0004\b\u0013\u0010\u0014J\u0017\u0010\u0017\u001a\u00020\u000b2\u0006\u0010\u0016\u001a\u00020\u0015H\u0016\u00a2\u0006\u0004\b\u0017\u0010\u0018J\u0017\u0010\u001b\u001a\u00020\u000b2\u0006\u0010\u001a\u001a\u00020\u0019H\u0016\u00a2\u0006\u0004\b\u001b\u0010\u001cJ\u0017\u0010\u001f\u001a\u00020\u000b2\u0006\u0010\u001e\u001a\u00020\u001dH\u0016\u00a2\u0006\u0004\b\u001f\u0010 J\u0019\u0010\"\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030!0\u0007H\u0016\u00a2\u0006\u0004\b\"\u0010#JR\u0010+\u001a\u00028\u0000\"\f\b\u0000\u0010$*\u0006\u0012\u0002\b\u00030!2%\u0010(\u001a!\u0012\u0017\u0012\u0015\u0012\u0002\b\u00030!\u00a2\u0006\f\b&\u0012\b\b'\u0012\u0004\b\b(\"\u0012\u0004\u0012\u00020\u00040%2\f\u0010*\u001a\b\u0012\u0004\u0012\u00028\u00000)H\u0016\u00a2\u0006\u0004\b+\u0010,J\u0017\u0010-\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b-\u0010\u0006J\u001d\u0010.\u001a\u00020\u00042\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00020\u0007H\u0016\u00a2\u0006\u0004\b.\u0010\nJ\u001d\u0010/\u001a\u00020\u00042\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00020\u0007H\u0016\u00a2\u0006\u0004\b/\u0010\nJ\u001f\u00101\u001a\u00020\u000b2\u0006\u0010\u0016\u001a\u00020\u00152\u0006\u00100\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b1\u00102J\u000f\u00103\u001a\u00020\u0019H\u0016\u00a2\u0006\u0004\b3\u00104J\u000f\u00105\u001a\u00020\u001dH\u0016\u00a2\u0006\u0004\b5\u00106J\u0017\u00108\u001a\u00020\u000b2\u0006\u00107\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b8\u00109J%\u0010:\u001a\u00028\u0000\"\f\b\u0000\u0010$*\u0006\u0012\u0002\b\u00030!2\u0006\u0010\"\u001a\u00028\u0000H\u0016\u00a2\u0006\u0004\b:\u0010;R$\u0010>\u001a\u0012\u0012\u0004\u0012\u00020\u00020<j\b\u0012\u0004\u0012\u00020\u0002`=8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b>\u0010?R\u001a\u0010A\u001a\u00020@8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\bA\u0010B\u001a\u0004\bC\u0010DR\u0014\u0010H\u001a\u00020E8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\bF\u0010G\u00a8\u0006K"}, d2={"Lcom/cobblemon/mod/common/pokemon/evolution/controller/ClientEvolutionController;", "Lcom/cobblemon/mod/common/api/pokemon/evolution/EvolutionController;", "Lcom/cobblemon/mod/common/api/pokemon/evolution/EvolutionDisplay;", "element", "", "add", "(Lcom/cobblemon/mod/common/api/pokemon/evolution/EvolutionDisplay;)Z", "", "elements", "addAll", "(Ljava/util/Collection;)Z", "", "clear", "()V", "contains", "containsAll", "isEmpty", "()Z", "", "iterator", "()Ljava/util/Iterator;", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "loadFromBuffer", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "Lcom/google/gson/JsonElement;", "json", "loadFromJson", "(Lcom/google/gson/JsonElement;)V", "Lnet/minecraft/nbt/Tag;", "nbt", "loadFromNBT", "(Lnet/minecraft/nbt/Tag;)V", "Lcom/cobblemon/mod/common/api/pokemon/evolution/progress/EvolutionProgress;", "progress", "()Ljava/util/Collection;", "P", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "predicate", "Lkotlin/Function0;", "progressFactory", "progressFirstOrCreate", "(Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function0;)Lcom/cobblemon/mod/common/api/pokemon/evolution/progress/EvolutionProgress;", "remove", "removeAll", "retainAll", "toClient", "saveToBuffer", "(Lnet/minecraft/network/FriendlyByteBuf;Z)V", "saveToJson", "()Lcom/google/gson/JsonElement;", "saveToNBT", "()Lnet/minecraft/nbt/Tag;", "evolution", "start", "(Lcom/cobblemon/mod/common/api/pokemon/evolution/EvolutionDisplay;)V", "trackProgress", "(Lcom/cobblemon/mod/common/api/pokemon/evolution/progress/EvolutionProgress;)Lcom/cobblemon/mod/common/api/pokemon/evolution/progress/EvolutionProgress;", "Ljava/util/HashSet;", "Lkotlin/collections/HashSet;", "evolutions", "Ljava/util/HashSet;", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "getPokemon", "()Lcom/cobblemon/mod/common/pokemon/Pokemon;", "", "getSize", "()I", "size", "<init>", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;)V", "common"})
@SourceDebugExtension(value={"SMAP\nClientEvolutionController.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ClientEvolutionController.kt\ncom/cobblemon/mod/common/pokemon/evolution/controller/ClientEvolutionController\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,96:1\n1855#2,2:97\n*S KotlinDebug\n*F\n+ 1 ClientEvolutionController.kt\ncom/cobblemon/mod/common/pokemon/evolution/controller/ClientEvolutionController\n*L\n71#1:97,2\n*E\n"})
public final class ClientEvolutionController
implements EvolutionController<EvolutionDisplay> {
    @NotNull
    private final Pokemon pokemon;
    @NotNull
    private final HashSet<EvolutionDisplay> evolutions;

    public ClientEvolutionController(@NotNull Pokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        this.pokemon = pokemon;
        this.evolutions = new HashSet();
    }

    @Override
    @NotNull
    public Pokemon getPokemon() {
        return this.pokemon;
    }

    public int getSize() {
        return this.evolutions.size();
    }

    @Override
    public void start(@NotNull EvolutionDisplay evolution) {
        Intrinsics.checkNotNullParameter((Object)evolution, (String)"evolution");
        CobblemonNetwork.INSTANCE.sendPacketToServer(new AcceptEvolutionPacket(this.getPokemon(), evolution));
    }

    @Override
    @NotNull
    public Collection<EvolutionProgress<?>> progress() {
        return CollectionsKt.emptyList();
    }

    @Override
    @NotNull
    public <P extends EvolutionProgress<?>> P trackProgress(@NotNull P progress2) {
        Intrinsics.checkNotNullParameter(progress2, (String)"progress");
        return progress2;
    }

    @Override
    @NotNull
    public <P extends EvolutionProgress<?>> P progressFirstOrCreate(@NotNull Function1<? super EvolutionProgress<?>, Boolean> predicate, @NotNull Function0<? extends P> progressFactory) {
        Intrinsics.checkNotNullParameter(predicate, (String)"predicate");
        Intrinsics.checkNotNullParameter(progressFactory, (String)"progressFactory");
        return (P)((EvolutionProgress)progressFactory.invoke());
    }

    @Override
    @NotNull
    public Tag saveToNBT() {
        return (Tag)new CompoundTag();
    }

    @Override
    public void loadFromNBT(@NotNull Tag nbt) {
        Intrinsics.checkNotNullParameter((Object)nbt, (String)"nbt");
    }

    @Override
    @NotNull
    public JsonElement saveToJson() {
        return (JsonElement)new JsonArray();
    }

    @Override
    public void loadFromJson(@NotNull JsonElement json) {
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
    }

    @Override
    public void saveToBuffer(@NotNull FriendlyByteBuf buffer, boolean toClient) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
    }

    @Override
    public void loadFromBuffer(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        List list = buffer.m_236845_(AddEvolutionPacket.Companion::decodeDisplay$common);
        Intrinsics.checkNotNullExpressionValue((Object)list, (String)"buffer.readList(AddEvolutionPacket::decodeDisplay)");
        Iterable $this$forEach$iv = list;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            EvolutionDisplay it = (EvolutionDisplay)element$iv;
            boolean bl = false;
            Intrinsics.checkNotNullExpressionValue((Object)it, (String)"it");
            this.add(it);
        }
    }

    @Override
    public boolean add(@NotNull EvolutionDisplay element) {
        Intrinsics.checkNotNullParameter((Object)element, (String)"element");
        return this.evolutions.add(element);
    }

    @Override
    public boolean addAll(@NotNull Collection<? extends EvolutionDisplay> elements2) {
        Intrinsics.checkNotNullParameter(elements2, (String)"elements");
        return this.evolutions.addAll(elements2);
    }

    @Override
    public void clear() {
        this.evolutions.clear();
    }

    @Override
    @NotNull
    public Iterator<EvolutionDisplay> iterator() {
        Iterator<EvolutionDisplay> iterator = this.evolutions.iterator();
        Intrinsics.checkNotNullExpressionValue(iterator, (String)"this.evolutions.iterator()");
        return iterator;
    }

    public boolean remove(@NotNull EvolutionDisplay element) {
        Intrinsics.checkNotNullParameter((Object)element, (String)"element");
        return this.evolutions.remove(element);
    }

    @Override
    public boolean removeAll(@NotNull Collection<? extends Object> elements2) {
        Intrinsics.checkNotNullParameter(elements2, (String)"elements");
        return this.evolutions.removeAll(CollectionsKt.toSet((Iterable)elements2));
    }

    @Override
    public boolean retainAll(@NotNull Collection<? extends Object> elements2) {
        Intrinsics.checkNotNullParameter(elements2, (String)"elements");
        return this.evolutions.retainAll(CollectionsKt.toSet((Iterable)elements2));
    }

    public boolean contains(@NotNull EvolutionDisplay element) {
        Intrinsics.checkNotNullParameter((Object)element, (String)"element");
        return this.evolutions.contains(element);
    }

    @Override
    public boolean containsAll(@NotNull Collection<? extends Object> elements2) {
        Intrinsics.checkNotNullParameter(elements2, (String)"elements");
        return this.evolutions.containsAll(elements2);
    }

    @Override
    public boolean isEmpty() {
        return this.evolutions.isEmpty();
    }

    @Override
    public <T> T[] toArray(T[] array) {
        Intrinsics.checkNotNullParameter(array, (String)"array");
        return CollectionToArray.toArray((Collection)this, (Object[])array);
    }

    @Override
    public Object[] toArray() {
        return CollectionToArray.toArray((Collection)this);
    }
}

