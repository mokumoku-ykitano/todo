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

	@Override
	public void execute() throws TodoException {

		if (TodoControl.isNotExecutingTodo()) {
			return;
		}

		ExecutingTodo executingTodo = TodoControl.getExecutingTodo();

		try {
			List<TodoLog> todoLogList = TodoLogic.loadTodoLogList();
			TodoLog todoLog = new TodoLog(executingTodo.startDate, new Date(), executingTodo.title);
			todoLogList.add(todoLog);
			TodoLogic.writeTodoLog(todoLogList);
			TodoControl.setExecutingTodo(null);
			System.out.println(MessageUtil.getMessage("info.command.stop", executingTodo.title));
		} catch (IOException e) {
			throw new TodoException(e, "error.command.stop", executingTodo.title);
		}
	}

}
