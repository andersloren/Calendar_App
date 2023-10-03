package se.lexicon.exception;

import javax.swing.text.rtf.RTFEditorKit;

public class MySQLException extends RuntimeException {

    public MySQLException(String message) {
        super(message);
    }

    public MySQLException(String message, Throwable cause) {
        super(message, cause);
    }
}
