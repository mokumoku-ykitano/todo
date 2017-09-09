package todo.command;

import todo.TodoControl;
import todo.exception.TodoException;
import todo.util.MessageUtil;

public class Now extends Command {

	@Override
	public void execute() throws TodoException {
		if (TodoControl.isExecutingTodo()) {
			System.out.println(MessageUtil.getMessage("info.command.now", TodoControl.getExecutingTodo().title));
		} else {
			System.out.println(MessageUtil.getMessage("info.command.noExecuting"));
		}
	}

}
