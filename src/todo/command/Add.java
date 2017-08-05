package todo.command;

import java.io.IOException;
import java.util.List;

import todo.dto.json.Todo;
import todo.exception.TodoException;
import todo.util.MessageUtil;
import todo.util.FilesUtil;

public class Add extends Command {

	private String todoTitle;

	@Override
	public void setArguments(String[] args) {
		if (args == null || args.length == 0) {
			throw new IllegalArgumentException(MessageUtil.getMessage("error.command.add.argument"));
		}
		todoTitle = args[0];
	}

	@Override
	public void execute() throws TodoException {
		try {
			List<Todo> todoList = FilesUtil.loadTodoList();
			todoList.add(new Todo(todoTitle));

			FilesUtil.writeTodoList(todoList);

			System.out.println(MessageUtil.getMessage("info.command.add.finish", todoTitle));

		} catch (IOException e) {
			throw new TodoException(e, "error.command.add.list", todoTitle);
		}
	}
}
