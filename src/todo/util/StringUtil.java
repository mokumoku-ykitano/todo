package todo.util;

public final class StringUtil {
	
	/*
	 * インスタンス化禁止
	 */
	private StringUtil(){
		
	}

	/**
	 * 文字列が空か評価します。
	 * 
	 * @param text
	 * @return 文字列がnullまたは空文字列ならtrue
	 */
	public static boolean isEmpty(String text) {
		return text == null || text.length() == 0;
	}

	/**
	 * 先頭の文字を大文字に変換します。
	 * 
	 * @param text
	 * @return 変換後の文字列
	 */
	public static String capitalize(String text) {
		if (isEmpty(text)) {
			return text;
		}
		char chars[] = text.toCharArray();
		chars[0] = Character.toUpperCase(chars[0]);
		return new String(chars);
	}

}
