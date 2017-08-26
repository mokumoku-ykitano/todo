package todo.command;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import todo.TodoControl;
import todo.TodoLogic;
import todo.dto.ExecutingTodo;
import todo.dto.json.TodoLog;
import todo.exception.TodoException;
import todo.util.MessageUtil;

public class Stop extends Command {

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
			TodoControl.setExecutingTodo(null);
		} catch (IOException e) {
			throw new TodoException(e, "error.command.stop", todoTitle);
		}
	}

	@Override
	public void showMessage() {
		System.out.println(MessageUtil.getMessage("info.command.stop", todoTitle));
	}

}
