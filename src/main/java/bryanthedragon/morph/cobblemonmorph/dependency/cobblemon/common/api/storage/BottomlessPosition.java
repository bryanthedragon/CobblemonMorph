/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.StorePosition;
import kotlin.Metadata;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0007\u0010\bR\u0017\u0010\u0003\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\t"}, d2={"Lcom/cobblemon/mod/common/api/storage/BottomlessPosition;", "Lcom/cobblemon/mod/common/api/storage/StorePosition;", "", "currentIndex", "I", "getCurrentIndex", "()I", "<init>", "(I)V", "common"})
public final class BottomlessPosition
implements StorePosition {
    private final int currentIndex;

    public BottomlessPosition(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    public final int getCurrentIndex() {
        return this.currentIndex;
    }
}

