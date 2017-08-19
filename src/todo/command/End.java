package todo.command;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import todo.TodoControl;
import todo.dto.ExecutingTodo;
import todo.dto.json.Todo;
import todo.dto.json.TodoLog;
import todo.exception.TodoException;
import todo.util.FilesUtil;
import todo.util.MessageUtil;

public class End extends Command {

	@Override
	public void execute() throws TodoException {

		if (TodoControl.isNotExecutingTodo()) {
			return;
		}
		
		ExecutingTodo executingTodo = TodoControl.getExecutingTodo();

		try {
			List<TodoLog> todoLogList = FilesUtil.loadTodoLogList();
			TodoLog todoLog = new TodoLog(executingTodo.startDate, new Date(), executingTodo.title);
			todoLogList.add(todoLog);
			FilesUtil.writeTodoLog(todoLogList);
			
			List<Todo> todoList = FilesUtil.loadTodoList();
			int index = -1; 
			for(int i = 0;i<todoList.size();i++){
				if(todoList.get(i).title.equals(executingTodo.title)){
					index = i;
					break;
				}
			}
			
			if(index != -1){
				todoList.remove(index);
			}
			
			FilesUtil.writeTodoList(todoList);
			
			TodoControl.setExecutingTodo(null);
		
			System.out.println(MessageUtil.getMessage("info.command.end", executingTodo.title));
		} catch (IOException e) {
			throw new TodoException(e, "error.command.end", executingTodo.title);
		}
	}

}
