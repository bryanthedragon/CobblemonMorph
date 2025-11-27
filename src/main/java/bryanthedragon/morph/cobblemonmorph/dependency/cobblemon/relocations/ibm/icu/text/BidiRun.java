
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.relocations.ibm.icu.text;

public class BidiRun {
    int start;
    int limit;
    int insertRemove;
    byte level;

    BidiRun() {
        this(0, 0, 0);
    }

    BidiRun(int start2, int limit, byte embeddingLevel) {
        this.start = start2;
        this.limit = limit;
        this.level = embeddingLevel;
    }

    void copyFrom(BidiRun run2) {
        this.start = run2.start;
        this.limit = run2.limit;
        this.level = run2.level;
        this.insertRemove = run2.insertRemove;
    }

    public int getStart() {
        return this.start;
    }

    public int getLimit() {
        return this.limit;
    }

    public int getLength() {
        return this.limit - this.start;
    }

    public byte getEmbeddingLevel() {
        return this.level;
    }

    public boolean isOddRun() {
        return (this.level & 1) == 1;
    }

    public boolean isEvenRun() {
        return (this.level & 1) == 0;
    }

    public byte getDirection() {
        return (byte)(this.level & 1);
    }

    public String toString() {
        return "BidiRun " + this.start + " - " + this.limit + " @ " + this.level;
    }
}

