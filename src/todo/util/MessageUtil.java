package todo.util;

import java.util.ResourceBundle;

public final class MessageUtil {

	public static String getMessage(String key) {
		ResourceBundle resourceBundle = ResourceBundle.getBundle("message");
		return resourceBundle.getString(key);
	}

}
