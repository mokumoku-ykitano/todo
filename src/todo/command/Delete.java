package todo.command;

import java.io.IOException;
import java.util.List;

import todo.exception.TodoException;
import todo.json.Todo;
import todo.util.MessageUtil;
import todo.util.StringUtil;
import todo.util.TodoListUtil;

public class Delete extends Command {

	private int todoIndex;

	@Override
	public void setArguments(String[] args) {
		if (args == null || args.length == 0) {
			throw new IllegalArgumentException(MessageUtil.getMessage("error.command.delete.argument"));
		}
		if (StringUtil.isNumber(args[0])) {
			todoIndex = Integer.parseInt(args[0]);
		} else {
			throw new IllegalArgumentException(MessageUtil.getMessage("error.command.delete.argument.number"));
		}
	}

	@Override
	public void execute() throws TodoException {

		int index = todoIndex - 1;
		List<Todo> todoList = TodoListUtil.loadTodoList();

		if (todoIndex <= 0 || todoIndex > todoList.size()) {
			System.err.println(MessageUtil.getMessage("error.command.delete.argument.number"));
			return;
		}

		String todoTitle = todoList.get(index).title;
		todoList.remove(index);

		try {
			TodoListUtil.write(todoList);
			System.out.println(MessageUtil.getMessage("info.delete.finish", todoTitle));
		} catch (IOException e) {
			throw new TodoException(e, "error.command.delete", todoTitle);
		}
	}

}
