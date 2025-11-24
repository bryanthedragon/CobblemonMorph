
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.mod.relocations.ibm.icu.impl.locale;

public class Extension {
    private char _key;
    protected String _value;

    protected Extension(char key) {
        this._key = key;
    }

    Extension(char key, String value2) {
        this._key = key;
        this._value = value2;
    }

    public char getKey() {
        return this._key;
    }

    public String getValue() {
        return this._value;
    }

    public String getID() {
        return this._key + "-" + this._value;
    }

    public String toString() {
        return this.getID();
    }
}

