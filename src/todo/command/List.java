package todo.command;

import todo.TodoLogic;
import todo.dto.json.Todo;
import todo.exception.TodoException;

public class List extends Command {

	@Override
	public void execute() throws TodoException {
		java.util.List<Todo> todoList = TodoLogic.loadTodoList();
		int index = 1;
		for (Todo todo : todoList) {
			System.out.println(index++ + ":" + todo.title);
		}
	}

}