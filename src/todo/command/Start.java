package todo.command;

import java.util.Date;
import java.util.List;

import todo.dto.ExecutingTodo;
import todo.dto.json.Todo;
import todo.exception.TodoException;
import todo.util.MessageUtil;
import todo.util.StringUtil;
import todo.util.FilesUtil;

public class Start extends Command {

	private int todoIndex;
	private ExecutingTodo executingTodo;

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

		// TODO 実行中のtodoがある場合、stopする
		
		int index = todoIndex - 1;
		List<Todo> todoList = FilesUtil.loadTodoList();

		if (todoIndex <= 0 || todoIndex > todoList.size()) {
			System.err.println(createErrorMessage());
			return;
		}

		executingTodo = new ExecutingTodo();
		executingTodo.setTitle(todoList.get(index).title);
		executingTodo.setStartDate(new Date());
	}

	@Override
	public ExecutingTodo getExecutingTodo() {
		return executingTodo;
	}

	private String createErrorMessage() {
		return MessageUtil.getMessage("error.command.argument.todoNumber", "開始", "start");
	}

}
