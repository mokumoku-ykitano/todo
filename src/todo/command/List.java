package todo.command;

import todo.dto.json.Todo;
import todo.exception.TodoException;
import todo.util.FilesUtil;

public class List extends Command {

	@Override
	public void execute() throws TodoException {
		java.util.List<Todo> todoList = FilesUtil.loadTodoList();
		int index = 1;
		for (Todo todo : todoList) {
			System.out.println(index++ + ":" + todo.title);
		}
	}

}