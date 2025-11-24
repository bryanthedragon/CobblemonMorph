
package com.oracle.js.parser;

public class Scanner {
    protected final String content;
    protected int position;
    protected final int limit;
    protected int line;
    protected char ch0;
    protected char ch1;
    protected char ch2;
    protected char ch3;

    protected Scanner(String content, int line, int start2, int length) {
        this.content = content;
        this.position = start2;
        this.limit = start2 + length;
        this.line = line;
        this.reset(this.position);
    }

    Scanner(Scanner scanner, State state) {
        this.content = scanner.content;
        this.position = state.position;
        this.limit = state.limit;
        this.line = state.line;
        this.reset(this.position);
    }

    State saveState() {
        return new State(this.position, this.limit, this.line);
    }

    void restoreState(State state) {
        this.position = state.position;
        this.line = state.line;
        this.reset(this.position);
    }

    protected final boolean atEOF() {
        return this.position == this.limit;
    }

    protected final char charAt(int i) {
        return i < this.limit ? this.content.charAt(i) : (char)'\u0000';
    }

    protected final void reset(int i) {
        this.ch0 = this.charAt(i);
        this.ch1 = this.charAt(i + 1);
        this.ch2 = this.charAt(i + 2);
        this.ch3 = this.charAt(i + 3);
        this.position = Math.min(i, this.limit);
    }

    protected final void skip(int n) {
        if (n == 1 && !this.atEOF()) {
            this.ch0 = this.ch1;
            this.ch1 = this.ch2;
            this.ch2 = this.ch3;
            this.ch3 = this.charAt(this.position + 4);
            ++this.position;
        } else if (n != 0) {
            this.reset(this.position + n);
        }
    }

    static class State {
        public final int position;
        private int limit;
        public final int line;

        State(int position, int limit, int line) {
            this.position = position;
            this.limit = limit;
            this.line = line;
        }

        void setLimit(int limit) {
            this.limit = limit;
        }

        int getLimit() {
            return this.limit;
        }

        boolean isEmpty() {
            return this.position == this.limit;
        }
    }
}

