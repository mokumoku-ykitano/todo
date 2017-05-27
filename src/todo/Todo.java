package todo;

import java.util.ResourceBundle;
import java.util.Scanner;

import todo.command.Command;

public class Todo {

	public static void main(String[] args) {
		try (Scanner scanner = new Scanner(System.in)) {
			boolean doContinue = true;
			String inputText = null;
			Command command = null;
			while (doContinue) {
				inputText = scanner.nextLine();
				command = Command.create(inputText);
				command.run();
				doContinue = command.nextCommandWaitIs();
			}
		} catch (ReflectiveOperationException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

}
