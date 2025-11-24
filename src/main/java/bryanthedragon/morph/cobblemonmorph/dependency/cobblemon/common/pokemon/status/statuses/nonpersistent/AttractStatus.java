/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.statuses.nonpersistent;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.VolatileStatus;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import kotlin.Metadata;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0007\u00a2\u0006\u0004\b\u0002\u0010\u0003\u00a8\u0006\u0004"}, d2={"Lcom/cobblemon/mod/common/pokemon/status/statuses/nonpersistent/AttractStatus;", "Lcom/cobblemon/mod/common/pokemon/status/VolatileStatus;", "<init>", "()V", "common"})
public final class AttractStatus
extends VolatileStatus {
    public AttractStatus() {
        super(MiscUtilsKt.cobblemonResource("attract"), "attract", "cobblemon.battle.attract_start", "cobblemon.battle.attract_snapped");
    }
}

