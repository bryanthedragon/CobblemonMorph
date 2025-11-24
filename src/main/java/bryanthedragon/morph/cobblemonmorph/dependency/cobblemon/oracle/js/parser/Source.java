
package com.oracle.js.parser;

import com.oracle.js.parser.Token;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Objects;

public final class Source {
    private static final int BUF_SIZE = 8192;
    private final String name;
    private final String base;
    private final Data data;
    private int hash;
    private volatile byte[] digest;
    private String explicitURL;

    private Source(String name, String base, Data data) {
        this.name = name;
        this.base = base;
        this.data = data;
    }

    private String data() {
        return this.data.data();
    }

    private int length() {
        return this.data.length();
    }

    public static Source sourceFor(String name, CharSequence content, boolean isEval) {
        return new Source(name, Source.baseName(name), new RawData(content, isEval));
    }

    public static Source sourceFor(String name, String content) {
        return Source.sourceFor(name, content, false);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Source)) {
            return false;
        }
        Source other = (Source)obj;
        return Objects.equals(this.name, other.name) && this.data.equals(other.data);
    }

    public int hashCode() {
        int h = this.hash;
        if (h == 0) {
            h = this.hash = this.data.hashCode() ^ Objects.hashCode(this.name);
        }
        return h;
    }

    public String getName() {
        return this.name;
    }

    public long getLastModified() {
        return this.data.lastModified();
    }

    public String getBase() {
        return this.base;
    }

    public String getString(int start2, int len) {
        return this.data().substring(start2, start2 + len);
    }

    public String getString(long token) {
        int start2 = Token.descPosition(token);
        int len = Token.descLength(token);
        return this.getString(start2, len);
    }

    public URL getURL() {
        return this.data.url();
    }

    public String getExplicitURL() {
        return this.explicitURL;
    }

    public void setExplicitURL(String explicitURL) {
        this.explicitURL = explicitURL;
    }

    public boolean isEvalCode() {
        return this.data.isEvalCode();
    }

    private int findBOLN(int position) {
        String d = this.data();
        for (int i = position - 1; i >= 0; --i) {
            char ch = d.charAt(i);
            if (ch != '\n' && ch != '\r') continue;
            return i + 1;
        }
        return 0;
    }

    private int findEOLN(int position) {
        String d = this.data();
        int length = this.length();
        for (int i = position; i < length; ++i) {
            char ch = d.charAt(i);
            if (ch != '\n' && ch != '\r') continue;
            return i - 1;
        }
        return length - 1;
    }

    public int getLine(int position) {
        String d = this.data();
        int line = 1;
        for (int i = 0; i < position; ++i) {
            char ch = d.charAt(i);
            if (ch != '\n') continue;
            ++line;
        }
        return line;
    }

    public int getColumn(int position) {
        return position - this.findBOLN(position);
    }

    public String getSourceLine(int position) {
        int first = this.findBOLN(position);
        int last = this.findEOLN(position);
        return this.getString(first, last + 1 - first);
    }

    public String getContent() {
        return this.data();
    }

    public int getLength() {
        return this.data.length();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static String readFully(Reader reader) throws IOException {
        char[] arr = new char[8192];
        StringBuilder sb = new StringBuilder();
        try {
            int numChars;
            while ((numChars = reader.read(arr, 0, arr.length)) > 0) {
                sb.append(arr, 0, numChars);
            }
        }
        finally {
            reader.close();
        }
        return sb.toString();
    }

    public String getDigest() {
        return new String(this.getDigestBytes(), StandardCharsets.US_ASCII);
    }

    private byte[] getDigestBytes() {
        byte[] ldigest = this.digest;
        if (ldigest == null) {
            byte[] bytes = this.data().getBytes(StandardCharsets.UTF_16LE);
            try {
                MessageDigest md = MessageDigest.getInstance("SHA-1");
                if (this.name != null) {
                    md.update(this.name.getBytes(StandardCharsets.UTF_8));
                }
                if (this.base != null) {
                    md.update(this.base.getBytes(StandardCharsets.UTF_8));
                }
                if (this.getURL() != null) {
                    md.update(this.getURL().toString().getBytes(StandardCharsets.UTF_8));
                }
                Base64.Encoder base64 = Base64.getUrlEncoder().withoutPadding();
                this.digest = ldigest = base64.encode(md.digest(bytes));
            }
            catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }
        return ldigest;
    }

    private static String baseName(String name) {
        int idx = name.lastIndexOf(47);
        if (idx == -1) {
            idx = name.lastIndexOf(92);
        }
        return idx != -1 ? name.substring(0, idx + 1) : null;
    }

    public String toString() {
        return this.getName();
    }

    private static final class RawData
    implements Data {
        private final String source;
        private final boolean evalCode;
        private int hash;

        private RawData(CharSequence source, boolean evalCode) {
            this.source = source.toString();
            this.evalCode = evalCode;
        }

        private RawData(Reader reader) throws IOException {
            this(Source.readFully(reader), false);
        }

        public int hashCode() {
            int h = this.hash;
            if (h == 0) {
                h = this.hash = this.source.hashCode() ^ (this.evalCode ? 1 : 0);
            }
            return h;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof RawData) {
                RawData other = (RawData)obj;
                return this.source.equals(other.source) && this.evalCode == other.evalCode;
            }
            return false;
        }

        public String toString() {
            return this.data();
        }

        @Override
        public URL url() {
            return null;
        }

        @Override
        public int length() {
            return this.source.length();
        }

        @Override
        public long lastModified() {
            return 0L;
        }

        @Override
        public String data() {
            return this.source;
        }

        @Override
        public boolean isEvalCode() {
            return this.evalCode;
        }
    }

    private static interface Data {
        public URL url();

        public int length();

        public long lastModified();

        public String data();

        public boolean isEvalCode();
    }
}

