
package org.graalvm.shadowed.org.jcodings.transcode;

public enum EConvResult {
    InvalidByteSequence,
    UndefinedConversion,
    DestinationBufferFull,
    SourceBufferEmpty,
    Finished,
    AfterOutput,
    IncompleteInput;

    private final String symbolicName;

    private EConvResult() {
        String name = this.name();
        StringBuilder snakeName = new StringBuilder(name.length() + 3);
        for (int i = 0; i < name.length(); ++i) {
            char c = name.charAt(i);
            if (Character.isLowerCase(c)) {
                snakeName.append(c);
                continue;
            }
            if (!Character.isUpperCase(c)) continue;
            if (i > 0) {
                snakeName.append('_');
            }
            snakeName.append(Character.toLowerCase(c));
        }
        this.symbolicName = snakeName.toString().intern();
    }

    public boolean isInvalidByteSequence() {
        return this == InvalidByteSequence;
    }

    public boolean isUndefinedConversion() {
        return this == UndefinedConversion;
    }

    public boolean isDestinationBufferFull() {
        return this == DestinationBufferFull;
    }

    public boolean isSourceBufferEmpty() {
        return this == SourceBufferEmpty;
    }

    public boolean isFinished() {
        return this == Finished;
    }

    public boolean isAfterOutput() {
        return this == AfterOutput;
    }

    public boolean isIncompleteInput() {
        return this == IncompleteInput;
    }

    public String symbolicName() {
        return this.symbolicName;
    }
}

