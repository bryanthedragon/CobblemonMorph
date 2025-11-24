
package com.oracle.js.parser;

import com.oracle.js.parser.ParserException;
import com.oracle.js.parser.Source;
import com.oracle.js.parser.Token;

public abstract class ErrorManager {
    private int errors;
    private int warnings;
    private int limit = 100;
    private boolean warningsAsErrors = false;
    private ParserException parserException;

    protected ErrorManager() {
    }

    private void checkLimit() {
        int count = this.errors;
        if (this.warningsAsErrors) {
            count += this.warnings;
        }
        if (this.limit != 0 && count > this.limit) {
            throw new RuntimeException("too many errors");
        }
    }

    public static String format(String message, Source source, int line, int column, long token) {
        String eoln = System.lineSeparator();
        int position = Token.descPosition(token);
        StringBuilder sb = new StringBuilder();
        sb.append(source.getName()).append(':').append(line).append(':').append(column).append(' ').append(message).append(eoln);
        String sourceLine = source.getSourceLine(position);
        sb.append(sourceLine).append(eoln);
        for (int i = 0; i < column; ++i) {
            if (sourceLine.charAt(i) == '\t') {
                sb.append('\t');
                continue;
            }
            sb.append(' ');
        }
        sb.append('^');
        return sb.toString();
    }

    protected void message(String message) {
    }

    public void error(ParserException e) {
        if (this.parserException == null) {
            this.parserException = e;
        }
        this.error(e.getMessage());
    }

    public void error(String message) {
        this.message(message);
        ++this.errors;
        this.checkLimit();
    }

    public void warning(ParserException e) {
        this.warning(e.getMessage());
    }

    public void warning(String message) {
        this.message(message);
        ++this.warnings;
        this.checkLimit();
    }

    public boolean hasErrors() {
        return this.errors != 0;
    }

    public int getLimit() {
        return this.limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public boolean isWarningsAsErrors() {
        return this.warningsAsErrors;
    }

    public void setWarningsAsErrors(boolean warningsAsErrors) {
        this.warningsAsErrors = warningsAsErrors;
    }

    public int getNumberOfErrors() {
        return this.errors;
    }

    public int getNumberOfWarnings() {
        return this.warnings;
    }

    public ParserException getParserException() {
        return this.parserException;
    }

    public static class ThrowErrorManager
    extends ErrorManager {
        @Override
        public void error(String message) {
            throw new ParserException(message);
        }

        @Override
        public void error(ParserException e) {
            throw e;
        }

        @Override
        protected void message(String message) {
            System.err.println(message);
        }
    }

    public static class StringBuilderErrorManager
    extends ErrorManager {
        private final StringBuilder buffer = new StringBuilder(0);

        @Override
        protected void message(String message) {
            this.buffer.append(message).append('\n');
        }

        public String getOutput() {
            return this.buffer.toString();
        }
    }
}

