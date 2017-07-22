package todo.command;

import todo.exception.TodoException;
import todo.json.Todo;
import todo.util.TodoListUtil;

public class List extends Command {

	@Override
	public void execute() throws TodoException {
		java.util.List<Todo> todoList = TodoListUtil.loadTodoList();
		int index = 1;
		for (Todo todo : todoList) {
			System.out.println(index++ + ":" + todo.title);
		}
	}

}