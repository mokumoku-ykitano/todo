package todo.command;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import todo.TodoControl;
import todo.dto.ExecutingTodo;
import todo.dto.json.TodoLog;
import todo.exception.TodoException;
import todo.util.FilesUtil;
import todo.util.MessageUtil;

public class Stop extends Command {

	@Override
	public void execute() throws TodoException {
		
		ExecutingTodo executingTodo = TodoControl.getExecutingTodo();

		if (executingTodo == null) {
			System.out.println(MessageUtil.getMessage("info.command.stop.nothing"));
			return;
		}

		try {
			List<TodoLog> todoLogList = FilesUtil.loadTodoLogList();
			TodoLog todoLog = new TodoLog(executingTodo.getStartDate(), new Date(), executingTodo.getTitle());
			todoLogList.add(todoLog);
			FilesUtil.writeTodoLog(todoLogList);
			TodoControl.setExecutingTodo(null);
		} catch (IOException e) {
			throw new TodoException(e, "error.command.stop", executingTodo.getTitle());
		}
	}

}
