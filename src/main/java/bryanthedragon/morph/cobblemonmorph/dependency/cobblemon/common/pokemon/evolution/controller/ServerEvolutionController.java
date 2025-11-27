/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonPrimitive
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.CollectionToArray
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.text.StringsKt
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.StringTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.sounds.SoundSource
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.controller;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonSounds;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.Cancelable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.CobblemonEvents;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.evolution.EvolutionAcceptedEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.Evolution;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.EvolutionController;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.EvolutionDisplay;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.progress.EvolutionProgress;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.progress.EvolutionProgressFactory;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.CancelableObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.EventObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update.evolution.AddEvolutionPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update.evolution.ClearEvolutionsPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update.evolution.RemoveEvolutionPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.JsonExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.CollectionToArray;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0090\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010)\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\u0018\u0000 R2\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001RB\u000f\u0012\u0006\u0010E\u001a\u00020D\u00a2\u0006\u0004\bP\u0010QJ\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u001d\u0010\t\u001a\u00020\u00042\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00020\u0007H\u0016\u00a2\u0006\u0004\b\t\u0010\nJ\u000f\u0010\f\u001a\u00020\u000bH\u0016\u00a2\u0006\u0004\b\f\u0010\rJ\u0018\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0096\u0002\u00a2\u0006\u0004\b\u000e\u0010\u0006J\u001d\u0010\u000f\u001a\u00020\u00042\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00020\u0007H\u0016\u00a2\u0006\u0004\b\u000f\u0010\nJ\u0019\u0010\u0012\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u0011\u001a\u00020\u0010H\u0002\u00a2\u0006\u0004\b\u0012\u0010\u0013J\u000f\u0010\u0014\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u0014\u0010\u0015J\u0016\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00020\u0016H\u0096\u0002\u00a2\u0006\u0004\b\u0017\u0010\u0018J\u0017\u0010\u001b\u001a\u00020\u000b2\u0006\u0010\u001a\u001a\u00020\u0019H\u0016\u00a2\u0006\u0004\b\u001b\u0010\u001cJ\u0017\u0010\u001f\u001a\u00020\u000b2\u0006\u0010\u001e\u001a\u00020\u001dH\u0016\u00a2\u0006\u0004\b\u001f\u0010 J\u0017\u0010#\u001a\u00020\u000b2\u0006\u0010\"\u001a\u00020!H\u0016\u00a2\u0006\u0004\b#\u0010$J\u0019\u0010&\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030%0\u0007H\u0016\u00a2\u0006\u0004\b&\u0010'JR\u0010/\u001a\u00028\u0000\"\f\b\u0000\u0010(*\u0006\u0012\u0002\b\u00030%2%\u0010,\u001a!\u0012\u0017\u0012\u0015\u0012\u0002\b\u00030%\u00a2\u0006\f\b*\u0012\b\b+\u0012\u0004\b\b(&\u0012\u0004\u0012\u00020\u00040)2\f\u0010.\u001a\b\u0012\u0004\u0012\u00028\u00000-H\u0016\u00a2\u0006\u0004\b/\u00100J\u0017\u00101\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b1\u0010\u0006J\u001d\u00102\u001a\u00020\u00042\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00020\u0007H\u0016\u00a2\u0006\u0004\b2\u0010\nJ\u001d\u00103\u001a\u00020\u00042\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00020\u0007H\u0016\u00a2\u0006\u0004\b3\u0010\nJ\u001f\u00105\u001a\u00020\u000b2\u0006\u0010\u001a\u001a\u00020\u00192\u0006\u00104\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b5\u00106J\u000f\u00107\u001a\u00020\u001dH\u0016\u00a2\u0006\u0004\b7\u00108J\u000f\u00109\u001a\u00020!H\u0016\u00a2\u0006\u0004\b9\u0010:J\u0017\u0010<\u001a\u00020\u000b2\u0006\u0010;\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b<\u0010=J%\u0010>\u001a\u00028\u0000\"\f\b\u0000\u0010(*\u0006\u0012\u0002\b\u00030%2\u0006\u0010&\u001a\u00028\u0000H\u0016\u00a2\u0006\u0004\b>\u0010?R$\u0010B\u001a\u0012\u0012\u0004\u0012\u00020\u00020@j\b\u0012\u0004\u0012\u00020\u0002`A8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\bB\u0010CR\u001a\u0010E\u001a\u00020D8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\bE\u0010F\u001a\u0004\bG\u0010HR,\u0010&\u001a\u001a\u0012\b\u0012\u0006\u0012\u0002\b\u00030%0Ij\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030%`J8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b&\u0010KR\u0014\u0010O\u001a\u00020L8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\bM\u0010N\u00a8\u0006S"}, d2={"Lcom/cobblemon/mod/common/pokemon/evolution/controller/ServerEvolutionController;", "Lcom/cobblemon/mod/common/api/pokemon/evolution/EvolutionController;", "Lcom/cobblemon/mod/common/api/pokemon/evolution/Evolution;", "element", "", "add", "(Lcom/cobblemon/mod/common/api/pokemon/evolution/Evolution;)Z", "", "elements", "addAll", "(Ljava/util/Collection;)Z", "", "clear", "()V", "contains", "containsAll", "", "id", "findEvolutionFromId", "(Ljava/lang/String;)Lcom/cobblemon/mod/common/api/pokemon/evolution/Evolution;", "isEmpty", "()Z", "", "iterator", "()Ljava/util/Iterator;", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "loadFromBuffer", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "Lcom/google/gson/JsonElement;", "json", "loadFromJson", "(Lcom/google/gson/JsonElement;)V", "Lnet/minecraft/nbt/Tag;", "nbt", "loadFromNBT", "(Lnet/minecraft/nbt/Tag;)V", "Lcom/cobblemon/mod/common/api/pokemon/evolution/progress/EvolutionProgress;", "progress", "()Ljava/util/Collection;", "P", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "predicate", "Lkotlin/Function0;", "progressFactory", "progressFirstOrCreate", "(Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function0;)Lcom/cobblemon/mod/common/api/pokemon/evolution/progress/EvolutionProgress;", "remove", "removeAll", "retainAll", "toClient", "saveToBuffer", "(Lnet/minecraft/network/FriendlyByteBuf;Z)V", "saveToJson", "()Lcom/google/gson/JsonElement;", "saveToNBT", "()Lnet/minecraft/nbt/Tag;", "evolution", "start", "(Lcom/cobblemon/mod/common/api/pokemon/evolution/Evolution;)V", "trackProgress", "(Lcom/cobblemon/mod/common/api/pokemon/evolution/progress/EvolutionProgress;)Lcom/cobblemon/mod/common/api/pokemon/evolution/progress/EvolutionProgress;", "Ljava/util/HashSet;", "Lkotlin/collections/HashSet;", "evolutions", "Ljava/util/HashSet;", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "getPokemon", "()Lcom/cobblemon/mod/common/pokemon/Pokemon;", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "Ljava/util/ArrayList;", "", "getSize", "()I", "size", "<init>", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;)V", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nServerEvolutionController.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ServerEvolutionController.kt\ncom/cobblemon/mod/common/pokemon/evolution/controller/ServerEvolutionController\n+ 2 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/CancelableObservable\n+ 3 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/EventObservable\n+ 4 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 5 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/CancelableObservable$postThen$1\n+ 6 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 7 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,244:1\n39#2,2:245\n41#2,2:250\n44#2,3:253\n47#2:258\n17#3,2:247\n19#3:257\n13579#4:249\n13580#4:256\n39#5:252\n288#6,2:259\n1855#6,2:261\n1855#6:263\n1856#6:265\n800#6,11:266\n800#6,11:277\n1549#6:288\n1620#6,3:289\n1549#6:292\n1620#6,3:293\n1855#6,2:296\n1855#6,2:298\n288#6,2:300\n1#7:264\n*S KotlinDebug\n*F\n+ 1 ServerEvolutionController.kt\ncom/cobblemon/mod/common/pokemon/evolution/controller/ServerEvolutionController\n*L\n47#1:245,2\n47#1:250,2\n47#1:253,3\n47#1:258\n47#1:247,2\n47#1:257\n47#1:249\n47#1:256\n47#1:252\n64#1:259,2\n76#1:261,2\n81#1:263\n81#1:265\n100#1:266,11\n105#1:277,11\n118#1:288\n118#1:289,3\n122#1:292\n122#1:293,3\n179#1:296,2\n215#1:298,2\n237#1:300,2\n*E\n"})
public final class ServerEvolutionController
implements EvolutionController<Evolution> {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final Pokemon pokemon;
    @NotNull
    private final HashSet<Evolution> evolutions;
    @NotNull
    private final ArrayList<EvolutionProgress<?>> progress;
    @NotNull
    private static final String PENDING = "pending";
    @NotNull
    private static final String PROGRESS = "progress";
    @NotNull
    private static final String ID = "id";

    public ServerEvolutionController(@NotNull Pokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        this.pokemon = pokemon;
        this.evolutions = new HashSet();
        this.progress = new ArrayList();
    }

    @Override
    @NotNull
    public Pokemon getPokemon() {
        return this.pokemon;
    }

    public int getSize() {
        return this.evolutions.size();
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void start(@NotNull Evolution evolution) {
        void this_$iv$iv;
        void $this$iv;
        Intrinsics.checkNotNullParameter((Object)evolution, (String)"evolution");
        CancelableObservable<EvolutionAcceptedEvent> cancelableObservable = CobblemonEvents.EVOLUTION_ACCEPTED;
        Cancelable event$iv = new EvolutionAcceptedEvent(this.getPokemon(), evolution);
        boolean $i$f$postThen = false;
        EventObservable eventObservable = (EventObservable)$this$iv;
        Cancelable[] cancelableArray = new Cancelable[]{event$iv};
        Cancelable[] events$iv$iv = cancelableArray;
        boolean $i$f$post = false;
        this_$iv$iv.emit(Arrays.copyOf(events$iv$iv, events$iv$iv.length));
        Cancelable[] $this$forEach$iv$iv$iv = events$iv$iv;
        boolean $i$f$forEach = false;
        int n = $this$forEach$iv$iv$iv.length;
        for (int i = 0; i < n; ++i) {
            Cancelable it;
            Cancelable element$iv$iv$iv;
            Cancelable it$iv = element$iv$iv$iv = $this$forEach$iv$iv$iv[i];
            boolean bl = false;
            if (it$iv.isCanceled()) {
                Cancelable cancelable = it$iv;
                boolean bl2 = false;
                it = cancelable;
                continue;
            }
            it = (EvolutionAcceptedEvent)it$iv;
            boolean bl3 = false;
            evolution.forceEvolve(this.getPokemon());
        }
    }

    @Override
    @NotNull
    public Collection<EvolutionProgress<?>> progress() {
        return CollectionsKt.toList((Iterable)this.progress);
    }

    @Override
    @NotNull
    public <P extends EvolutionProgress<?>> P trackProgress(@NotNull P progress2) {
        Intrinsics.checkNotNullParameter(progress2, (String)PROGRESS);
        this.progress.add(progress2);
        return progress2;
    }

    @Override
    @NotNull
    public <P extends EvolutionProgress<?>> P progressFirstOrCreate(@NotNull Function1<? super EvolutionProgress<?>, Boolean> predicate, @NotNull Function0<? extends P> progressFactory) {
        Object v0;
        block2: {
            Intrinsics.checkNotNullParameter(predicate, (String)"predicate");
            Intrinsics.checkNotNullParameter(progressFactory, (String)"progressFactory");
            Iterable $this$firstOrNull$iv = this.progress;
            boolean $i$f$firstOrNull = false;
            for (Object element$iv : $this$firstOrNull$iv) {
                if (!((Boolean)predicate.invoke(element$iv)).booleanValue()) continue;
                v0 = element$iv;
                break block2;
            }
            v0 = null;
        }
        EvolutionProgress existing = v0;
        if (existing == null) {
            EvolutionProgress created = (EvolutionProgress)progressFactory.invoke();
            this.progress.add(created);
            return (P)created;
        }
        return (P)existing;
    }

    @Override
    @NotNull
    public Tag saveToNBT() {
        CompoundTag nbt = new CompoundTag();
        ListTag pendingList = new ListTag();
        Iterable $this$forEach$iv = this.evolutions;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            Evolution evolution = (Evolution)element$iv;
            boolean bl = false;
            ((Collection)pendingList).add(StringTag.m_129297_((String)evolution.getId()));
        }
        nbt.m_128365_(PENDING, (Tag)pendingList);
        ListTag progressList = new ListTag();
        Iterable $this$forEach$iv2 = this.progress;
        boolean $i$f$forEach2 = false;
        for (Object element$iv : $this$forEach$iv2) {
            EvolutionProgress progress2 = (EvolutionProgress)element$iv;
            boolean bl = false;
            Collection collection = (Collection)progressList;
            Object n = progress2.saveToNBT();
            CompoundTag $this$saveToNBT_u24lambda_u243_u24lambda_u242 = (CompoundTag)n;
            boolean bl2 = false;
            $this$saveToNBT_u24lambda_u243_u24lambda_u242.m_128359_(ID, progress2.id().toString());
            collection.add(n);
        }
        nbt.m_128365_(PROGRESS, (Tag)progressList);
        return (Tag)nbt;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void loadFromNBT(@NotNull Tag nbt) {
        Iterable evolution;
        void $this$filterIsInstanceTo$iv$iv;
        Intrinsics.checkNotNullParameter((Object)nbt, (String)"nbt");
        this.clear();
        ListTag pendingList = null;
        ListTag progressList = null;
        if (nbt instanceof CompoundTag) {
            ListTag listTag = ((CompoundTag)nbt).m_128437_(PENDING, 8);
            Intrinsics.checkNotNullExpressionValue((Object)listTag, (String)"nbt.getList(PENDING, Nbt\u2026ment.STRING_TYPE.toInt())");
            pendingList = listTag;
            ListTag listTag2 = ((CompoundTag)nbt).m_128437_(PROGRESS, 10);
            Intrinsics.checkNotNullExpressionValue((Object)listTag2, (String)"nbt.getList(PROGRESS, Nb\u2026nt.COMPOUND_TYPE.toInt())");
            progressList = listTag2;
        } else {
            ListTag listTag = nbt instanceof ListTag ? (ListTag)nbt : null;
            if (listTag == null) {
                return;
            }
            pendingList = listTag;
            progressList = new ListTag();
        }
        Iterable $this$filterIsInstance$iv = (Iterable)pendingList;
        boolean $i$f$filterIsInstance = false;
        Iterable iterable = $this$filterIsInstance$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$filterIsInstanceTo = false;
        for (Object element$iv$iv : $this$filterIsInstanceTo$iv$iv) {
            if (!(element$iv$iv instanceof StringTag)) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        for (StringTag tag : (List)destination$iv$iv) {
            String id = tag.m_7916_();
            Intrinsics.checkNotNullExpressionValue((Object)id, (String)ID);
            if (this.findEvolutionFromId(id) == null) continue;
            this.add((Evolution)((Object)evolution));
        }
        $this$filterIsInstance$iv = (Iterable)progressList;
        $i$f$filterIsInstance = false;
        evolution = $this$filterIsInstance$iv;
        destination$iv$iv = new ArrayList();
        $i$f$filterIsInstanceTo = false;
        for (Object element$iv$iv : $this$filterIsInstanceTo$iv$iv) {
            if (!(element$iv$iv instanceof CompoundTag)) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        for (StringTag tag : (List)destination$iv$iv) {
            EvolutionProgress<?> progress2;
            String string = tag.m_128461_(ID);
            Intrinsics.checkNotNullExpressionValue((Object)string, (String)"tag.getString(ID)");
            if (EvolutionProgressFactory.INSTANCE.create(string) == null) continue;
            boolean bl = false;
            progress2.loadFromNBT((Tag)tag);
            if (!progress2.shouldKeep(this.getPokemon())) continue;
            this.progress.add(progress2);
        }
    }

    /*
     * WARNING - void declaration
     */
    @Override
    @NotNull
    public JsonElement saveToJson() {
        void $this$mapTo$iv$iv;
        void $this$map$iv;
        Collection collection;
        void $this$mapTo$iv$iv2;
        JsonObject json = new JsonObject();
        Iterable $this$map$iv2 = this.evolutions;
        boolean $i$f$map22 = false;
        Iterable iterable = $this$map$iv2;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv2, (int)10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv2) {
            void evolution;
            Evolution evolution2 = (Evolution)item$iv$iv;
            collection = destination$iv$iv;
            boolean bl = false;
            collection.add(evolution.getId());
        }
        JsonArray pendingArray = JsonExtensionsKt.toJsonArrayString((List)destination$iv$iv);
        json.add(PENDING, (JsonElement)pendingArray);
        Iterable $i$f$map22 = this.progress;
        boolean $i$f$map = false;
        destination$iv$iv = $this$map$iv;
        Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
        boolean $i$f$mapTo2 = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void progress2;
            EvolutionProgress bl = (EvolutionProgress)item$iv$iv;
            collection = destination$iv$iv2;
            boolean bl2 = false;
            Object j = progress2.saveToJson();
            JsonObject $this$saveToJson_u24lambda_u247_u24lambda_u246 = (JsonObject)j;
            boolean bl3 = false;
            $this$saveToJson_u24lambda_u247_u24lambda_u246.addProperty(ID, progress2.id().toString());
            collection.add((JsonObject)j);
        }
        JsonArray progressArray = JsonExtensionsKt.toJsonArrayJsonElement((List)destination$iv$iv2);
        json.add(PROGRESS, (JsonElement)progressArray);
        return (JsonElement)json;
    }

    @Override
    public void loadFromJson(@NotNull JsonElement json) {
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        this.clear();
        JsonArray pendingArray = null;
        JsonArray progressArray = null;
        if (json instanceof JsonObject) {
            JsonArray jsonArray = ((JsonObject)json).getAsJsonArray(PENDING);
            Intrinsics.checkNotNullExpressionValue((Object)jsonArray, (String)"json.getAsJsonArray(PENDING)");
            pendingArray = jsonArray;
            JsonArray jsonArray2 = ((JsonObject)json).getAsJsonArray(PROGRESS);
            Intrinsics.checkNotNullExpressionValue((Object)jsonArray2, (String)"json.getAsJsonArray(PROGRESS)");
            progressArray = jsonArray2;
        } else {
            JsonArray jsonArray = json instanceof JsonArray ? (JsonArray)json : null;
            if (jsonArray == null) {
                return;
            }
            pendingArray = jsonArray;
            progressArray = new JsonArray();
        }
        for (JsonElement element : pendingArray) {
            Evolution evolution;
            String id;
            JsonPrimitive jsonPrimitive = element instanceof JsonPrimitive ? (JsonPrimitive)element : null;
            String string = jsonPrimitive != null ? jsonPrimitive.getAsString() : null;
            if (string == null || this.findEvolutionFromId(id = string) == null) continue;
            this.add(evolution);
        }
        for (JsonElement element : progressArray) {
            EvolutionProgress<?> progress2;
            JsonObject jObject;
            if ((element instanceof JsonObject ? (JsonObject)element : null) == null) continue;
            jObject = jObject;
            String string = jObject.get(ID).getAsString();
            Intrinsics.checkNotNullExpressionValue((Object)string, (String)"jObject.get(ID).asString");
            if (EvolutionProgressFactory.INSTANCE.create(string) == null) continue;
            boolean bl = false;
            progress2.loadFromJson((JsonElement)jObject);
            if (!progress2.shouldKeep(this.getPokemon())) continue;
            this.progress.add(progress2);
        }
    }

    @Override
    public void saveToBuffer(@NotNull FriendlyByteBuf buffer, boolean toClient) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        if (!toClient) {
            return;
        }
        buffer.m_236828_((Collection)this.evolutions, (arg_0, arg_1) -> ServerEvolutionController.saveToBuffer$lambda$9(this, arg_0, arg_1));
    }

    @Override
    public void loadFromBuffer(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
    }

    @Override
    public boolean add(@NotNull Evolution element) {
        Intrinsics.checkNotNullParameter((Object)element, (String)"element");
        if (this.evolutions.add(element)) {
            ServerPlayer serverPlayer = this.getPokemon().getOwnerPlayer();
            if (serverPlayer != null) {
                Object[] objectArray = new Object[]{this.getPokemon().getDisplayName()};
                MutableComponent mutableComponent = MiscUtils.asTranslated("cobblemon.ui.evolve.hint", objectArray);
                Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"cobblemon.ui.evolve.hint\u2026pokemon.getDisplayName())");
                serverPlayer.m_213846_((Component)TextKt.green(mutableComponent));
            }
            this.getPokemon().notify(new AddEvolutionPacket(this.getPokemon(), element));
            ServerPlayer serverPlayer2 = this.getPokemon().getOwnerPlayer();
            if (serverPlayer2 != null) {
                serverPlayer2.m_6330_(CobblemonSounds.CAN_EVOLVE, SoundSource.NEUTRAL, 1.0f, 1.0f);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean addAll(@NotNull Collection<? extends Evolution> elements2) {
        Intrinsics.checkNotNullParameter(elements2, (String)"elements");
        boolean result = false;
        Iterable $this$forEach$iv = elements2;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            Evolution element = (Evolution)element$iv;
            boolean bl = false;
            if (!this.add(element)) continue;
            result = true;
        }
        return result;
    }

    @Override
    public void clear() {
        Pokemon pokemon = this.getPokemon();
        if (!((Collection)this.evolutions).isEmpty()) {
            this.evolutions.clear();
            this.getPokemon().notify(new ClearEvolutionsPacket((Function0<? extends Pokemon>)((Function0)new Function0<Pokemon>(pokemon){
                final /* synthetic */ Pokemon $pokemon;
                {
                    this.$pokemon = $pokemon;
                    super(0);
                }

                @NotNull
                public final Pokemon invoke() {
                    return this.$pokemon;
                }
            })));
        }
        this.progress.clear();
    }

    public boolean contains(@NotNull Evolution element) {
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
    @NotNull
    public Iterator<Evolution> iterator() {
        Iterator<Evolution> iterator = this.evolutions.iterator();
        Intrinsics.checkNotNullExpressionValue(iterator, (String)"this.evolutions.iterator()");
        return iterator;
    }

    public boolean remove(@NotNull Evolution element) {
        Intrinsics.checkNotNullParameter((Object)element, (String)"element");
        if (this.evolutions.remove(element)) {
            this.getPokemon().notify(new RemoveEvolutionPacket(this.getPokemon(), element));
            return true;
        }
        return false;
    }

    @Override
    public boolean removeAll(@NotNull Collection<? extends Object> elements2) {
        Intrinsics.checkNotNullParameter(elements2, (String)"elements");
        boolean result = false;
        Iterable $this$forEach$iv = elements2;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            Evolution element = (Evolution)element$iv;
            boolean bl = false;
            if (!this.remove((Object)element)) continue;
            result = true;
        }
        return result;
    }

    @Override
    public boolean retainAll(@NotNull Collection<? extends Object> elements2) {
        Intrinsics.checkNotNullParameter(elements2, (String)"elements");
        boolean result = false;
        Set comparedSet = CollectionsKt.toSet((Iterable)elements2);
        Iterator<Evolution> iterator = this.iterator();
        while (iterator.hasNext()) {
            Evolution element;
            Intrinsics.checkNotNullExpressionValue((Object)iterator.next(), (String)"iterator.next()");
            if (comparedSet.contains(element)) continue;
            iterator.remove();
            result = true;
        }
        return result;
    }

    private final Evolution findEvolutionFromId(String id) {
        Evolution evolution;
        block1: {
            Iterable<Evolution> $this$firstOrNull$iv = this.getPokemon().getEvolutions();
            boolean $i$f$firstOrNull = false;
            Iterator<Evolution> iterator = $this$firstOrNull$iv.iterator();
            while (iterator.hasNext()) {
                Evolution element$iv;
                Evolution evolution2 = element$iv = iterator.next();
                boolean bl = false;
                if (!StringsKt.equals((String)evolution2.getId(), (String)id, (boolean)true)) continue;
                evolution = element$iv;
                break block1;
            }
            evolution = null;
        }
        return evolution;
    }

    private static final void saveToBuffer$lambda$9(ServerEvolutionController this$0, FriendlyByteBuf pb, Evolution value2) {
        Intrinsics.checkNotNullParameter((Object)this$0, (String)"this$0");
        Intrinsics.checkNotNullExpressionValue((Object)value2, (String)"value");
        EvolutionDisplay evolutionDisplay = AddEvolutionPacket.Companion.convertToDisplay$common(value2, this$0.getPokemon());
        Intrinsics.checkNotNullExpressionValue((Object)pb, (String)"pb");
        AddEvolutionPacket.Companion.encode$common(evolutionDisplay, pb);
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

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0007\u0010\bR\u0014\u0010\u0003\u001a\u00020\u00028\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0003\u0010\u0004R\u0014\u0010\u0005\u001a\u00020\u00028\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0005\u0010\u0004R\u0014\u0010\u0006\u001a\u00020\u00028\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0006\u0010\u0004\u00a8\u0006\t"}, d2={"Lcom/cobblemon/mod/common/pokemon/evolution/controller/ServerEvolutionController$Companion;", "", "", "ID", "Ljava/lang/String;", "PENDING", "PROGRESS", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

