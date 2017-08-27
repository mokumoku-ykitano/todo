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
	private Date startDate;

	@Override
	public void setArguments(String[] args) throws TodoException {
		if (TodoControl.isNotExecutingTodo()) {
			throw new TodoException(MessageUtil.getMessage("info.command.noExecuting"));
		}
		ExecutingTodo executingTodo = TodoControl.getExecutingTodo();
		todoTitle = executingTodo.title;
		startDate = executingTodo.startDate;
	}

	@Override
	public void execute() throws TodoException {
		try {
			List<TodoLog> todoLogList = TodoLogic.loadTodoLogList();
			todoLogList.add(new TodoLog(startDate, new Date(), todoTitle));
			TodoLogic.writeTodoLog(todoLogList);

			List<Todo> todoList = TodoLogic.loadTodoList();
			Todo removeTarget = todoList.stream().filter(todo -> todo.title.equals(todoTitle)).findFirst().orElse(null);

			// 見つからなかったら何もしない
			if (removeTarget != null) {
				todoList.remove(removeTarget);
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
