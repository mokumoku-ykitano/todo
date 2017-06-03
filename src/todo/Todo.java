package todo;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.LogManager;

import todo.command.Command;

public class Todo {

	public static void main(String[] args) throws SecurityException, IOException {

		// ログの設定
		LogManager.getLogManager()
				.readConfiguration(Todo.class.getClassLoader().getResourceAsStream("logging.properties"));

		try (Scanner scanner = new Scanner(System.in)) {
			
			boolean doContinue = true;
			String inputText = null;
			Command command = null;
			
			while (doContinue) {
				inputText = scanner.nextLine();
				try {
					command = Command.create(inputText);
					command.run();
					doContinue = command.nextCommandWaitIs();
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}
			}
		}
	}

}
