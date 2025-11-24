/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  io.netty.buffer.ByteBuf
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.collections.ArraysKt
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.jvm.internal.markers.KMappedMarker
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.network.FriendlyByteBuf
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.Move;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.Observable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.IntSize;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.NetExtensionsKt;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.jvm.internal.markers.KMappedMarker;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000n\n\u0002\u0018\u0002\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010(\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u0000 E2\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001EB\u0007\u00a2\u0006\u0004\bD\u0010\bJ\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006J\r\u0010\u0007\u001a\u00020\u0004\u00a2\u0006\u0004\b\u0007\u0010\bJ\u0015\u0010\n\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\u0000\u00a2\u0006\u0004\b\n\u0010\u000bJ\u001b\u0010\u000e\u001a\u00020\u00042\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00040\f\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u001a\u0010\u0012\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u0011\u001a\u00020\u0010H\u0086\u0002\u00a2\u0006\u0004\b\u0012\u0010\u0013J\u0013\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00020\u0014\u00a2\u0006\u0004\b\u0015\u0010\u0016J\r\u0010\u0018\u001a\u00020\u0017\u00a2\u0006\u0004\b\u0018\u0010\u0019J\r\u0010\u001b\u001a\u00020\u001a\u00a2\u0006\u0004\b\u001b\u0010\u001cJ\r\u0010\u001d\u001a\u00020\u0004\u00a2\u0006\u0004\b\u001d\u0010\bJ\u0016\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00020\u001eH\u0096\u0002\u00a2\u0006\u0004\b\u001f\u0010 J\u0015\u0010#\u001a\u00020\u00002\u0006\u0010\"\u001a\u00020!\u00a2\u0006\u0004\b#\u0010$J\u0015\u0010'\u001a\u00020\u00002\u0006\u0010&\u001a\u00020%\u00a2\u0006\u0004\b'\u0010(J\u0015\u0010+\u001a\u00020\u00002\u0006\u0010*\u001a\u00020)\u00a2\u0006\u0004\b+\u0010,J\r\u0010-\u001a\u00020\u0004\u00a2\u0006\u0004\b-\u0010\bJ\u0015\u0010.\u001a\u00020\u00042\u0006\u0010\"\u001a\u00020!\u00a2\u0006\u0004\b.\u0010/J\u0015\u00100\u001a\u00020%2\u0006\u0010&\u001a\u00020%\u00a2\u0006\u0004\b0\u00101J\u001f\u00103\u001a\u00020\u00042\u0006\u00102\u001a\u00020\u00102\b\u0010\u0003\u001a\u0004\u0018\u00010\u0002\u00a2\u0006\u0004\b3\u00104J\u001d\u00107\u001a\u00020\u00042\u0006\u00105\u001a\u00020\u00102\u0006\u00106\u001a\u00020\u0010\u00a2\u0006\u0004\b7\u00108J\r\u00109\u001a\u00020\u0004\u00a2\u0006\u0004\b9\u0010\bR\u0016\u0010:\u001a\u00020\u001a8\u0002@\u0002X\u0082\u000e\u00a2\u0006\u0006\n\u0004\b:\u0010;R\u001c\u0010=\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020<8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b=\u0010>R\u001d\u0010@\u001a\b\u0012\u0004\u0012\u00020\u00000?8\u0006\u00a2\u0006\f\n\u0004\b@\u0010A\u001a\u0004\bB\u0010C\u00a8\u0006F"}, d2={"Lcom/cobblemon/mod/common/api/moves/MoveSet;", "", "Lcom/cobblemon/mod/common/api/moves/Move;", "move", "", "add", "(Lcom/cobblemon/mod/common/api/moves/Move;)V", "clear", "()V", "other", "copyFrom", "(Lcom/cobblemon/mod/common/api/moves/MoveSet;)V", "Lkotlin/Function0;", "action", "doWithoutEmitting", "(Lkotlin/jvm/functions/Function0;)V", "", "index", "get", "(I)Lcom/cobblemon/mod/common/api/moves/Move;", "", "getMoves", "()Ljava/util/List;", "Lnet/minecraft/nbt/ListTag;", "getNBT", "()Lnet/minecraft/nbt/ListTag;", "", "hasSpace", "()Z", "heal", "", "iterator", "()Ljava/util/Iterator;", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "loadFromBuffer", "(Lnet/minecraft/network/FriendlyByteBuf;)Lcom/cobblemon/mod/common/api/moves/MoveSet;", "Lcom/google/gson/JsonObject;", "json", "loadFromJSON", "(Lcom/google/gson/JsonObject;)Lcom/cobblemon/mod/common/api/moves/MoveSet;", "Lnet/minecraft/nbt/CompoundTag;", "nbt", "loadFromNBT", "(Lnet/minecraft/nbt/CompoundTag;)Lcom/cobblemon/mod/common/api/moves/MoveSet;", "partialHeal", "saveToBuffer", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "saveToJSON", "(Lcom/google/gson/JsonObject;)Lcom/google/gson/JsonObject;", "pos", "setMove", "(ILcom/cobblemon/mod/common/api/moves/Move;)V", "pos1", "pos2", "swapMove", "(II)V", "update", "emit", "Z", "", "moves", "[Lcom/cobblemon/mod/common/api/moves/Move;", "Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "observable", "Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "getObservable", "()Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "<init>", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nMoveSet.kt\nKotlin\n*S Kotlin\n*F\n+ 1 MoveSet.kt\ncom/cobblemon/mod/common/api/moves/MoveSet\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 4 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,192:1\n1#2:193\n12744#3,2:194\n1855#4,2:196\n1855#4,2:198\n1549#4:200\n1620#4,3:201\n1855#4,2:204\n1747#4,3:206\n*S KotlinDebug\n*F\n+ 1 MoveSet.kt\ncom/cobblemon/mod/common/api/moves/MoveSet\n*L\n41#1:194,2\n64#1:196,2\n69#1:198,2\n98#1:200\n98#1:201,3\n107#1:204,2\n121#1:206,3\n*E\n"})
public final class MoveSet
implements Iterable<Move>,
KMappedMarker {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final SimpleObservable<MoveSet> observable = new SimpleObservable();
    private boolean emit = true;
    @NotNull
    private final Move[] moves = new Move[4];
    public static final int MOVE_COUNT = 4;

    @NotNull
    public final SimpleObservable<MoveSet> getObservable() {
        return this.observable;
    }

    @Override
    @NotNull
    public Iterator<Move> iterator() {
        return ArraysKt.filterNotNull((Object[])this.moves).iterator();
    }

    @Nullable
    public final Move get(int index) {
        Move move;
        Integer n = index;
        int it = ((Number)n).intValue();
        boolean bl = false;
        Integer n2 = (0 <= it ? it < 4 : false) ? n : null;
        if (n2 != null) {
            it = ((Number)n2).intValue();
            boolean bl2 = false;
            move = this.moves[it];
        } else {
            move = null;
        }
        return move;
    }

    @NotNull
    public final List<Move> getMoves() {
        return ArraysKt.filterNotNull((Object[])this.moves);
    }

    public final boolean hasSpace() {
        boolean bl;
        block1: {
            Move[] $this$any$iv = this.moves;
            boolean $i$f$any = false;
            int n = $this$any$iv.length;
            for (int i = 0; i < n; ++i) {
                Move element$iv;
                Move it = element$iv = $this$any$iv[i];
                boolean bl2 = false;
                if (!(it == null)) continue;
                bl = true;
                break block1;
            }
            bl = false;
        }
        return bl;
    }

    public final void setMove(int pos, @Nullable Move move) {
        if (!(0 <= pos ? pos < 4 : false)) {
            return;
        }
        this.moves[pos] = move;
        Object object = move;
        if (object != null && (object = ((Move)object).getObservable()) != null) {
            Observable.DefaultImpls.subscribe$default((Observable)object, null, (Function1)new Function1<Move, Unit>(this){
                final /* synthetic */ MoveSet this$0;
                {
                    this.this$0 = $receiver;
                    super(1);
                }

                public final void invoke(@NotNull Move it) {
                    Intrinsics.checkNotNullParameter((Object)it, (String)"it");
                    this.this$0.update();
                }
            }, 1, null);
        }
        this.update();
    }

    public final void copyFrom(@NotNull MoveSet other) {
        Intrinsics.checkNotNullParameter((Object)other, (String)"other");
        this.doWithoutEmitting((Function0<Unit>)((Function0)new Function0<Unit>(this, other){
            final /* synthetic */ MoveSet this$0;
            final /* synthetic */ MoveSet $other;
            {
                this.this$0 = $receiver;
                this.$other = $other;
                super(0);
            }

            /*
             * WARNING - void declaration
             */
            public final void invoke() {
                void $this$forEach$iv;
                this.this$0.clear();
                Iterable iterable = this.$other.getMoves();
                MoveSet moveSet = this.this$0;
                boolean $i$f$forEach = false;
                for (T element$iv : $this$forEach$iv) {
                    Move it = (Move)element$iv;
                    boolean bl = false;
                    moveSet.add(it);
                }
            }
        }));
        this.update();
    }

    public final void heal() {
        Iterable $this$forEach$iv = this.getMoves();
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            Move it = (Move)element$iv;
            boolean bl = false;
            it.setCurrentPp(it.getMaxPp());
        }
        this.update();
    }

    public final void partialHeal() {
        Iterable $this$forEach$iv = this.getMoves();
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            Move it = (Move)element$iv;
            boolean bl = false;
            it.setCurrentPp(Math.min(it.getCurrentPp() + it.getMaxPp() / 2, it.getMaxPp()));
        }
        this.update();
    }

    public final void clear() {
        this.doWithoutEmitting((Function0<Unit>)((Function0)new Function0<Unit>(this){
            final /* synthetic */ MoveSet this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            public final void invoke() {
                for (int i = 0; i < 4; ++i) {
                    this.this$0.setMove(i, null);
                }
            }
        }));
        this.update();
    }

    public final void swapMove(int pos1, int pos2) {
        Move move;
        Move move2 = move = this.moves[pos2];
        int n = pos1;
        Move[] moveArray = this.moves;
        boolean bl = false;
        this.moves[pos2] = this.moves[pos1];
        Unit unit = Unit.INSTANCE;
        moveArray[n] = move;
        this.update();
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public final ListTag getNBT() {
        void $this$mapTo$iv$iv;
        void $this$map$iv;
        ListTag listTag = new ListTag();
        Iterable iterable = this.getMoves();
        ListTag listTag2 = listTag;
        boolean $i$f$map = false;
        void var4_5 = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it;
            Move move = (Move)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl = false;
            collection.add(it.saveToNBT(new CompoundTag()));
        }
        listTag2.addAll((Collection)((List)destination$iv$iv));
        return listTag;
    }

    public final void saveToBuffer(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        NetExtensionsKt.writeSizedInt((ByteBuf)buffer, IntSize.U_BYTE, this.getMoves().size());
        Iterable $this$forEach$iv = this.getMoves();
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            Move it = (Move)element$iv;
            boolean bl = false;
            it.saveToBuffer(buffer);
        }
    }

    @NotNull
    public final JsonObject saveToJSON(@NotNull JsonObject json) {
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        Iterator iterator = ArraysKt.filterNotNull((Object[])this.moves).iterator();
        int n = 0;
        while (iterator.hasNext()) {
            int i = n++;
            Move move = (Move)iterator.next();
            JsonObject moveJSON = move.saveToJSON(new JsonObject());
            json.add("MoveSet" + i, (JsonElement)moveJSON);
        }
        return json;
    }

    public final void add(@NotNull Move move) {
        boolean bl;
        block5: {
            Intrinsics.checkNotNullParameter((Object)move, (String)"move");
            Iterable $this$any$iv = this;
            boolean $i$f$any = false;
            if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                bl = false;
            } else {
                for (Object element$iv : $this$any$iv) {
                    Move it = (Move)element$iv;
                    boolean bl2 = false;
                    if (!Intrinsics.areEqual((Object)it.getTemplate(), (Object)move.getTemplate())) continue;
                    bl = true;
                    break block5;
                }
                bl = false;
            }
        }
        if (bl) {
            return;
        }
        for (int i = 0; i < 4; ++i) {
            if (this.moves[i] != null) continue;
            this.moves[i] = move;
            this.update();
            return;
        }
    }

    public final void update() {
        if (this.emit) {
            MoveSet[] moveSetArray = new MoveSet[]{this};
            this.observable.emit((MoveSet[])moveSetArray);
        }
    }

    public final void doWithoutEmitting(@NotNull Function0<Unit> action2) {
        Intrinsics.checkNotNullParameter(action2, (String)"action");
        boolean previousEmit = this.emit;
        this.emit = false;
        action2.invoke();
        this.emit = previousEmit;
    }

    @NotNull
    public final MoveSet loadFromNBT(@NotNull CompoundTag nbt) {
        Intrinsics.checkNotNullParameter((Object)nbt, (String)"nbt");
        this.doWithoutEmitting((Function0<Unit>)((Function0)new Function0<Unit>(this, nbt){
            final /* synthetic */ MoveSet this$0;
            final /* synthetic */ CompoundTag $nbt;
            {
                this.this$0 = $receiver;
                this.$nbt = $nbt;
                super(0);
            }

            /*
             * WARNING - void declaration
             */
            public final void invoke() {
                void $this$forEachIndexed$iv;
                this.this$0.clear();
                ListTag listTag = this.$nbt.m_128437_("MoveSet", 10);
                Intrinsics.checkNotNullExpressionValue((Object)listTag, (String)"nbt.getList(DataKeys.POK\u2026nt.COMPOUND_TYPE.toInt())");
                Iterable iterable = (Iterable)listTag;
                MoveSet moveSet = this.this$0;
                boolean $i$f$forEachIndexed = false;
                int index$iv = 0;
                for (T item$iv : $this$forEachIndexed$iv) {
                    void tag;
                    int n;
                    if ((n = index$iv++) < 0) {
                        CollectionsKt.throwIndexOverflow();
                    }
                    Tag tag2 = (Tag)item$iv;
                    int index = n;
                    boolean bl = false;
                    Intrinsics.checkNotNull((Object)tag, (String)"null cannot be cast to non-null type net.minecraft.nbt.NbtCompound");
                    moveSet.setMove(index, Move.Companion.loadFromNBT((CompoundTag)tag));
                }
            }
        }));
        this.update();
        return this;
    }

    @NotNull
    public final MoveSet loadFromBuffer(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        this.doWithoutEmitting((Function0<Unit>)((Function0)new Function0<Unit>(this, buffer){
            final /* synthetic */ MoveSet this$0;
            final /* synthetic */ FriendlyByteBuf $buffer;
            {
                this.this$0 = $receiver;
                this.$buffer = $buffer;
                super(0);
            }

            public final void invoke() {
                this.this$0.clear();
                int amountMoves = NetExtensionsKt.readSizedInt((ByteBuf)this.$buffer, IntSize.U_BYTE);
                for (int i = 0; i < amountMoves; ++i) {
                    this.this$0.setMove(i, Move.Companion.loadFromBuffer(this.$buffer));
                }
            }
        }));
        this.update();
        return this;
    }

    @NotNull
    public final MoveSet loadFromJSON(@NotNull JsonObject json) {
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        this.doWithoutEmitting((Function0<Unit>)((Function0)new Function0<Unit>(this, json){
            final /* synthetic */ MoveSet this$0;
            final /* synthetic */ JsonObject $json;
            {
                this.this$0 = $receiver;
                this.$json = $json;
                super(0);
            }

            public final void invoke() {
                this.this$0.clear();
                for (int i = 0; i < 4; ++i) {
                    JsonElement moveJSON;
                    if (this.$json.get("MoveSet" + i) == null) continue;
                    JsonObject jsonObject = moveJSON.getAsJsonObject();
                    Intrinsics.checkNotNullExpressionValue((Object)jsonObject, (String)"moveJSON.asJsonObject");
                    Move move = Move.Companion.loadFromJSON(jsonObject);
                    this.this$0.add(move);
                }
            }
        }));
        this.update();
        return this;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\b\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0003\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0003\u0010\u0004\u00a8\u0006\u0007"}, d2={"Lcom/cobblemon/mod/common/api/moves/MoveSet$Companion;", "", "", "MOVE_COUNT", "I", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

