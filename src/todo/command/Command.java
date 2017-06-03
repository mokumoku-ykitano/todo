package todo.command;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import todo.exception.TodoException;
import todo.util.MessageUtil;
import todo.util.StringUtil;

public abstract class Command {

	private static final Logger logger = Logger.getLogger(Command.class.getSimpleName());

	public static Command create(String inputText) throws TodoException {
		// 入力文字列(コマンド、引数)を配列化
		String[] text = inputText.split(" ");
		// パッケージ名 + クラス名
		String fullClassName = makeFullClassName(text[0]);
		try {
			Command command = (Command) Class.forName(fullClassName).newInstance();
			// 引数のみの配列を作成
			String[] args = Arrays.stream(text).filter(arg -> !arg.equals(text[0])).toArray(size -> new String[size]);
			// 引数設定時にチェックを行う
			command.setArguments(args);
			return command;
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			String message = MessageUtil.getMessage("error.command.create");
			logger.log(Level.SEVERE, message, e);
			throw new TodoException(message, e);
		}
	}

	/*
	 * (パッケージ名 + クラス)名の文字列を作成します。
	 */
	private static String makeFullClassName(String className) {
		return Command.class.getPackage().getName() + "." + StringUtil.capitalize(className);
	}

	/**
	 * コマンドの引数を設定します。<br>
	 * 引数のチェックも行います。
	 * 
	 * @param args
	 */
	public abstract void setArguments(String[] args);

	/**
	 * コマンドを実行します。
	 */
	public abstract void run();

	/**
	 * 次の入力を待つかどうか。<br>
	 * 終了する場合はfalseを返す。
	 * 
	 * @return true(次の入力を待つ) / false(終了する)
	 */
	public boolean nextCommandWaitIs() {
		return true;
	}

}
