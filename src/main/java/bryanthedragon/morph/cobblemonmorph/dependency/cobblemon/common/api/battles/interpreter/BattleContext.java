/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface BattleContext {
    @NotNull
    public String getId();

    public int getTurn();

    @NotNull
    public Type getType();

    @Nullable
    public BattlePokemon getOrigin();

    public enum Type {
        ITEM(true, true),
        STATUS(true, false),
        VOLATILE(true, false),
        HAZARD(true, false),
        WEATHER(true, true),
        ROOM(false, true),
        SPORT(false, false),
        TERRAIN(false, true),
        GRAVITY(false, true),
        TAILWIND(false, true),
        SCREEN(false, false),
        FAINT(false, false),
        BOOST(false, false),
        UNBOOST(false, false),
        MISC(false, false);

        private final boolean damaging;
        private final boolean exclusive;

        private Type(boolean damaging, boolean exclusive) {
            this.damaging = damaging;
            this.exclusive = exclusive;
        }

        public final boolean getDamaging() {
            return this.damaging;
        }

        public final boolean getExclusive() {
            return this.exclusive;
        }
    }
}

