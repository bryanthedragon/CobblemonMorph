
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.relocations.ibm.icu.impl.duration.impl;

import com.cobblemon.mod.relocations.ibm.icu.impl.duration.impl.RecordWriter;
import com.cobblemon.mod.relocations.ibm.icu.lang.UCharacter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class XMLRecordWriter
implements RecordWriter {
    private Writer w;
    private List<String> nameStack;
    static final String NULL_NAME = "Null";
    private static final String INDENT = "    ";

    public XMLRecordWriter(Writer w) {
        this.w = w;
        this.nameStack = new ArrayList<String>();
    }

    @Override
    public boolean open(String title) {
        this.newline();
        this.writeString("<" + title + ">");
        this.nameStack.add(title);
        return true;
    }

    @Override
    public boolean close() {
        int ix = this.nameStack.size() - 1;
        if (ix >= 0) {
            String name = this.nameStack.remove(ix);
            this.newline();
            this.writeString("</" + name + ">");
            return true;
        }
        return false;
    }

    public void flush() {
        try {
            this.w.flush();
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

    @Override
    public void bool(String name, boolean value2) {
        this.internalString(name, String.valueOf(value2));
    }

    @Override
    public void boolArray(String name, boolean[] values) {
        if (values != null) {
            String[] stringValues = new String[values.length];
            for (int i = 0; i < values.length; ++i) {
                stringValues[i] = String.valueOf(values[i]);
            }
            this.stringArray(name, stringValues);
        }
    }

    private static String ctos(char value2) {
        if (value2 == '<') {
            return "&lt;";
        }
        if (value2 == '&') {
            return "&amp;";
        }
        return String.valueOf(value2);
    }

    @Override
    public void character(String name, char value2) {
        if (value2 != '\uffff') {
            this.internalString(name, XMLRecordWriter.ctos(value2));
        }
    }

    @Override
    public void characterArray(String name, char[] values) {
        if (values != null) {
            String[] stringValues = new String[values.length];
            for (int i = 0; i < values.length; ++i) {
                char value2 = values[i];
                stringValues[i] = value2 == '\uffff' ? NULL_NAME : XMLRecordWriter.ctos(value2);
            }
            this.internalStringArray(name, stringValues);
        }
    }

    @Override
    public void namedIndex(String name, String[] names, int value2) {
        if (value2 >= 0) {
            this.internalString(name, names[value2]);
        }
    }

    @Override
    public void namedIndexArray(String name, String[] names, byte[] values) {
        if (values != null) {
            String[] stringValues = new String[values.length];
            for (int i = 0; i < values.length; ++i) {
                byte value2 = values[i];
                stringValues[i] = value2 < 0 ? NULL_NAME : names[value2];
            }
            this.internalStringArray(name, stringValues);
        }
    }

    public static String normalize(String str) {
        if (str == null) {
            return null;
        }
        StringBuilder sb = null;
        boolean inWhitespace = false;
        char c = '\u0000';
        boolean special = false;
        for (int i = 0; i < str.length(); ++i) {
            c = str.charAt(i);
            if (UCharacter.isWhitespace(c)) {
                if (sb == null && (inWhitespace || c != ' ')) {
                    sb = new StringBuilder(str.substring(0, i));
                }
                if (inWhitespace) continue;
                inWhitespace = true;
                special = false;
                c = ' ';
            } else {
                inWhitespace = false;
                boolean bl = special = c == '<' || c == '&';
                if (special && sb == null) {
                    sb = new StringBuilder(str.substring(0, i));
                }
            }
            if (sb == null) continue;
            if (special) {
                sb.append(c == '<' ? "&lt;" : "&amp;");
                continue;
            }
            sb.append(c);
        }
        if (sb != null) {
            return sb.toString();
        }
        return str;
    }

    private void internalString(String name, String normalizedValue) {
        if (normalizedValue != null) {
            this.newline();
            this.writeString("<" + name + ">" + normalizedValue + "</" + name + ">");
        }
    }

    private void internalStringArray(String name, String[] normalizedValues) {
        if (normalizedValues != null) {
            this.push(name + "List");
            for (int i = 0; i < normalizedValues.length; ++i) {
                String value2 = normalizedValues[i];
                if (value2 == null) {
                    value2 = NULL_NAME;
                }
                this.string(name, value2);
            }
            this.pop();
        }
    }

    @Override
    public void string(String name, String value2) {
        this.internalString(name, XMLRecordWriter.normalize(value2));
    }

    @Override
    public void stringArray(String name, String[] values) {
        if (values != null) {
            this.push(name + "List");
            for (int i = 0; i < values.length; ++i) {
                String value2 = XMLRecordWriter.normalize(values[i]);
                if (value2 == null) {
                    value2 = NULL_NAME;
                }
                this.internalString(name, value2);
            }
            this.pop();
        }
    }

    @Override
    public void stringTable(String name, String[][] values) {
        if (values != null) {
            this.push(name + "Table");
            for (int i = 0; i < values.length; ++i) {
                String[] rowValues = values[i];
                if (rowValues == null) {
                    this.internalString(name + "List", NULL_NAME);
                    continue;
                }
                this.stringArray(name, rowValues);
            }
            this.pop();
        }
    }

    private void push(String name) {
        this.newline();
        this.writeString("<" + name + ">");
        this.nameStack.add(name);
    }

    private void pop() {
        int ix = this.nameStack.size() - 1;
        String name = this.nameStack.remove(ix);
        this.newline();
        this.writeString("</" + name + ">");
    }

    private void newline() {
        this.writeString("\n");
        for (int i = 0; i < this.nameStack.size(); ++i) {
            this.writeString(INDENT);
        }
    }

    private void writeString(String str) {
        if (this.w != null) {
            try {
                this.w.write(str);
            }
            catch (IOException e) {
                System.err.println(e.getMessage());
                this.w = null;
            }
        }
    }
}

