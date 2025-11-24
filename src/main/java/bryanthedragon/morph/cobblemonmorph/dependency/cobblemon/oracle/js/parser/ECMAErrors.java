
package com.oracle.js.parser;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public final class ECMAErrors {
    private static final String MESSAGES_RESOURCE = "com.oracle.js.parser.resources.Messages";
    private static final ResourceBundle MESSAGES_BUNDLE = ResourceBundle.getBundle("com.oracle.js.parser.resources.Messages", Locale.getDefault());

    private ECMAErrors() {
    }

    public static String getMessage(String msgId, String ... args) {
        return ECMAErrors.getMessageFormat(msgId).format(args);
    }

    private static MessageFormat getMessageFormat(String msgId) {
        try {
            return new MessageFormat(MESSAGES_BUNDLE.getString(msgId));
        }
        catch (MissingResourceException e) {
            throw new RuntimeException("no message resource found for message id: " + msgId);
        }
    }
}

