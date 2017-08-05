package todo.command;

import java.io.IOException;
import java.util.List;

import todo.dto.json.Todo;
import todo.exception.TodoException;
import todo.util.MessageUtil;
import todo.util.StringUtil;
import todo.util.FilesUtil;

public class Delete extends Command {

	private int todoIndex;

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

		int index = todoIndex - 1;
		List<Todo> todoList = FilesUtil.loadTodoList();

		if (todoIndex <= 0 || todoIndex > todoList.size()) {
			System.err.println(createErrorMessage());
			return;
		}

		String todoTitle = todoList.get(index).title;
		todoList.remove(index);

		try {
			FilesUtil.writeTodoList(todoList);
			System.out.println(MessageUtil.getMessage("info.command.delete.finish", todoTitle));
			Command listCommand = new todo.command.List();
			listCommand.execute();
		} catch (IOException e) {
			throw new TodoException(e, "error.command.delete", todoTitle);
		}
	}

	private String createErrorMessage() {
		return MessageUtil.getMessage("error.command.argument.todoNumber", "削除", "delete");
	}

}
