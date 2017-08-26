package todo.command;

import java.util.Date;
import java.util.List;

import todo.TodoControl;
import todo.TodoLogic;
import todo.dto.ExecutingTodo;
import todo.dto.json.Todo;
import todo.exception.TodoException;
import todo.util.MessageUtil;
import todo.util.StringUtil;

public class Start extends Command {

	private int todoIndex;

	private String todoTitle;

	@Override
	public void setArguments(String[] args) {
		if (args == null || args.length == 0) {
			throw new IllegalArgumentException(createErrorMessage());
		}
		if (StringUtil.isNumber(args[0])) {
			todoIndex = Integer.parseInt(args[0]);
		} else {
			throw new IllegalArgumentException(MessageUtil.getMessage("error.command.argument.number"));
		}
	}

	@Override
	public void execute() throws TodoException {

		// 実行中のtodoがある場合、stopする
		if (TodoControl.isExecutingTodo()) {
			Command stopCommand = new Stop();
			stopCommand.execute();
			stopCommand.showMessage();
		}

		int index = todoIndex - 1;
		List<Todo> todoList = TodoLogic.loadTodoList();

		if (todoIndex <= 0 || todoIndex > todoList.size()) {
			System.err.println(createErrorMessage());
			return;
		}

		todoTitle = todoList.get(index).title;

		ExecutingTodo executingTodo = new ExecutingTodo();
		executingTodo.title = todoTitle;
		executingTodo.startDate = new Date();
		TodoControl.setExecutingTodo(executingTodo);
	}

	private String createErrorMessage() {
		return MessageUtil.getMessage("error.command.argument.todoNumber", "開始", "start");
	}

	@Override
	public void showMessage() {
		System.out.println(MessageUtil.getMessage("info.command.start", todoTitle));
	}

}
