package todo.command;

import java.io.IOException;
import java.util.List;

import todo.TodoLogic;
import todo.dto.json.Todo;
import todo.exception.TodoException;
import todo.util.MessageUtil;
import todo.util.StringUtil;

public class Add extends Command {

	private String todoTitles;

	@Override
	public void setArguments(String[] args) {
		if (args == null || args.length == 0) {
			throw new IllegalArgumentException(MessageUtil.getMessage("error.command.add.argument"));
		}
		todoTitles = args[0];
	}

	@Override
	public void execute() throws TodoException {
		try {
			List<Todo> todoList = TodoLogic.loadTodoList();
			for (String todoTitle : todoTitles.split(",")) {
				if (StringUtil.isNotEmpty(todoTitle)) {
					todoList.add(new Todo(todoTitle));
				}
			}

			TodoLogic.writeTodoList(todoList);

			System.out.println(MessageUtil.getMessage("info.command.add.finish", todoTitles));

			Command listCommand = new todo.command.List();
			listCommand.execute();

		} catch (IOException e) {
			throw new TodoException(e, "error.command.add.list", todoTitles);
		}
	}
}
