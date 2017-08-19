package todo;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import todo.command.Command;
import todo.dto.ExecutingTodo;
import todo.util.MessageUtil;

public class TodoControl {

	private static final Logger logger = Logger.getLogger(TodoControl.class.getSimpleName());

	/** 実行中のtodoオブジェクト */
	private static ExecutingTodo executingTodo;

	public static void main(String[] args) throws Exception {

		// ログの設定
		LogManager.getLogManager()
				.readConfiguration(TodoControl.class.getClassLoader().getResourceAsStream("logging.properties"));

		// 必要なフォルダを全て作成する
		TodoLogic.createTodoDirectories();

		try (Scanner scanner = new Scanner(System.in)) {

			boolean doContinue = true;
			String inputText = null;
			Command command = null;

			while (doContinue) {
				inputText = scanner.nextLine();
				try {
					command = CommandFactory.createAndSetArguments(inputText);
					command.execute();
					doContinue = command.nextCommandWaitIs();
				} catch (Exception e) {
					System.err.println(e.getMessage());
					logger.log(Level.SEVERE, e.getMessage(), e);
				}
				System.out.println();
			}
		}
	}

	public static ExecutingTodo getExecutingTodo() {
		return executingTodo;
	}

	public static void setExecutingTodo(ExecutingTodo executingTodo) {
		TodoControl.executingTodo = executingTodo;
	}

	public static boolean isExecutingTodo() {
		if (executingTodo == null) {
			System.out.println(MessageUtil.getMessage("info.command.noExecuting"));
			return false;
		} else {
			return true;
		}
	}

	public static boolean isNotExecutingTodo() {
		return !isExecutingTodo();
	}

}
