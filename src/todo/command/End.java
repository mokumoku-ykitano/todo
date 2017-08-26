package todo.command;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import todo.TodoControl;
import todo.TodoLogic;
import todo.dto.ExecutingTodo;
import todo.dto.json.Todo;
import todo.dto.json.TodoLog;
import todo.exception.TodoException;
import todo.util.MessageUtil;

public class End extends Command {

	private String todoTitle;

	@Override
	public void execute() throws TodoException {

		if (TodoControl.isNotExecutingTodo()) {
			System.out.println(MessageUtil.getMessage("info.command.noExecuting"));
			return;
		}

		ExecutingTodo executingTodo = TodoControl.getExecutingTodo();
		todoTitle = executingTodo.title;

		try {
			List<TodoLog> todoLogList = TodoLogic.loadTodoLogList();
			TodoLog todoLog = new TodoLog(executingTodo.startDate, new Date(), todoTitle);
			todoLogList.add(todoLog);
			TodoLogic.writeTodoLog(todoLogList);

			List<Todo> todoList = TodoLogic.loadTodoList();
			int index = -1;
			for (int i = 0; i < todoList.size(); i++) {
				if (todoList.get(i).title.equals(todoTitle)) {
					index = i;
					break;
				}
			}

			// 見つからなかったら何もしない
			if (index != -1) {
				todoList.remove(index);
				TodoLogic.writeTodoList(todoList);
			}

			TodoControl.setExecutingTodo(null);

		} catch (IOException e) {
			throw new TodoException(e, "error.command.end", todoTitle);
		}
	}

	@Override
	public void showMessage() {
		System.out.println(MessageUtil.getMessage("info.command.end", todoTitle));
	}
}
