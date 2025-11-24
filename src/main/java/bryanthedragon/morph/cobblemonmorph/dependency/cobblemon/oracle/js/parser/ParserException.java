
package com.oracle.js.parser;

import com.oracle.js.parser.ErrorManager;
import com.oracle.js.parser.JSErrorType;
import com.oracle.js.parser.Source;
import com.oracle.js.parser.Token;
import com.oracle.js.parser.TokenType;

public final class ParserException
extends RuntimeException {
    private String fileName;
    private int line;
    private int column;
    private final Source source;
    private final long token;
    private final JSErrorType errorType;

    public ParserException(String msg) {
        this(JSErrorType.SyntaxError, msg, null, -1, -1, -1L);
    }

    public ParserException(JSErrorType errorType, String msg, Source source, int line, int column, long token) {
        super(msg);
        this.fileName = source != null ? source.getName() : null;
        this.line = line;
        this.column = column;
        this.source = source;
        this.token = token;
        this.errorType = errorType;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getLineNumber() {
        return this.line;
    }

    public void setLineNumber(int line) {
        this.line = line;
    }

    public int getColumnNumber() {
        return this.column;
    }

    public void setColumnNumber(int column) {
        this.column = column;
    }

    public Source getSource() {
        return this.source;
    }

    public long getToken() {
        return this.token;
    }

    public int getPosition() {
        return Token.descPosition(this.token);
    }

    public JSErrorType getErrorType() {
        return this.errorType;
    }

    public boolean isIncompleteSource() {
        return Token.descType(this.token) == TokenType.EOF;
    }

    @Override
    public String getMessage() {
        String message = super.getMessage();
        if (this.source != null) {
            message = ErrorManager.format(message, this.source, this.line, this.column, this.token);
        }
        return message;
    }
}

