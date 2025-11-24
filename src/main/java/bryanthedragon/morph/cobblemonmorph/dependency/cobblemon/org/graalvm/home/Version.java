
package org.graalvm.home;

import java.util.Arrays;
import java.util.Objects;
import org.graalvm.home.HomeFinder;

public final class Version
implements Comparable<Version> {
    private static final String SNAPSHOT_STRING = "snapshot";
    private static final String SNAPSHOT_SUFFIX = "dev";
    private static final int MIN_VERSION_DIGITS = 3;
    private final int[] versions;
    private final String suffix;
    private final boolean snapshot;

    Version(int ... versions) {
        this.versions = versions;
        this.suffix = null;
        this.snapshot = false;
    }

    Version(String v) {
        if (v.equals(SNAPSHOT_STRING) || v.equals(SNAPSHOT_SUFFIX)) {
            this.snapshot = true;
            this.versions = new int[0];
            this.suffix = SNAPSHOT_STRING;
        } else {
            int end2;
            int dash = v.indexOf(45);
            if (dash != -1) {
                this.suffix = v.substring(dash + 1, v.length());
                this.snapshot = this.suffix.equals(SNAPSHOT_SUFFIX);
                end2 = dash;
            } else {
                this.suffix = null;
                this.snapshot = false;
                end2 = v.length();
            }
            String versionsString = v.substring(0, end2);
            String[] versionChunks = versionsString.split("\\.", -1);
            int[] intVersions = new int[versionChunks.length];
            for (int i = 0; i < versionChunks.length; ++i) {
                try {
                    intVersions[i] = Integer.parseInt(versionChunks[i]);
                }
                catch (NumberFormatException f) {
                    throw Version.invalid(v);
                }
                assert (intVersions[i] >= 0);
            }
            if ((intVersions = Version.trimTrailingZeros(intVersions)).length == 0) {
                throw Version.invalid(v);
            }
            this.versions = intVersions;
        }
    }

    private static int[] trimTrailingZeros(int[] intVersions) {
        int trimVersions;
        for (trimVersions = intVersions.length - 1; trimVersions >= 0 && intVersions[trimVersions] == 0; --trimVersions) {
        }
        if (trimVersions != intVersions.length - 1) {
            return Arrays.copyOf(intVersions, trimVersions + 1);
        }
        return intVersions;
    }

    private static IllegalArgumentException invalid(String v) {
        return new IllegalArgumentException("Invalid version string '" + v + "'.");
    }

    public boolean isRelease() {
        return !this.snapshot;
    }

    public boolean isSnapshot() {
        return this.snapshot;
    }

    @Override
    public int compareTo(Version o) {
        int[] thisVersions = this.versions;
        int[] otherVersions = o.versions;
        for (int i = 0; i < Math.max(otherVersions.length, thisVersions.length); ++i) {
            int otherVersion;
            int version = i >= thisVersions.length ? 0 : thisVersions[i];
            int cmp = Integer.compare(version, otherVersion = i >= otherVersions.length ? 0 : otherVersions[i]);
            if (cmp == 0) continue;
            return cmp;
        }
        return 0;
    }

    @Override
    public int compareTo(int ... compareVersions) {
        return this.compareTo(Version.create(compareVersions));
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Version)) {
            return false;
        }
        Version other = (Version)obj;
        return Arrays.equals(this.versions, other.versions) && this.snapshot == other.snapshot && Objects.equals(this.suffix, other.suffix);
    }

    public int hashCode() {
        return Objects.hash(Arrays.hashCode(this.versions), this.snapshot, this.suffix);
    }

    public String toString() {
        if (this.versions.length == 0) {
            assert (this.suffix != null && this.suffix.equals(SNAPSHOT_STRING));
            return this.suffix;
        }
        StringBuilder b = new StringBuilder();
        String sep = "";
        for (int i = 0; i < Math.max(3, this.versions.length); ++i) {
            b.append(sep);
            if (i < this.versions.length) {
                b.append(this.versions[i]);
            } else {
                b.append(0);
            }
            sep = ".";
        }
        if (this.suffix != null) {
            b.append("-").append(this.suffix);
        }
        return b.toString();
    }

    public static Version parse(String versionString) throws IllegalArgumentException {
        Objects.requireNonNull(versionString);
        return new Version(versionString);
    }

    public static Version create(int ... versions) throws IllegalArgumentException {
        Objects.requireNonNull(versions);
        int[] useVersions = Version.trimTrailingZeros(versions);
        if (useVersions.length == 0) {
            throw new IllegalArgumentException("At least one non-zero version must be specified.");
        }
        for (int i = 0; i < useVersions.length; ++i) {
            if (useVersions[i] >= 0) continue;
            throw new IllegalArgumentException("Versions must not be negative.");
        }
        return new Version(useVersions);
    }

    public static Version getCurrent() {
        return Version.parse(HomeFinder.getInstance().getVersion());
    }
}

