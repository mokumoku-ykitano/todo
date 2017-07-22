package todo.util;

import java.text.MessageFormat;
import java.util.ResourceBundle;

public final class MessageUtil {

	public static String getMessage(String key) {
		ResourceBundle resourceBundle = ResourceBundle.getBundle("message");
		return resourceBundle.getString(key);
	}

	public static String getMessage(String key, Object... arguments) {
		return MessageFormat.format(getMessage(key), arguments);
	}

}
