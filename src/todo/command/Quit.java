package todo.command;

import todo.exception.TodoException;
import todo.util.MessageUtil;

public class Quit extends Command {

	@Override
	public void execute() throws TodoException {
		System.out.println(MessageUtil.getMessage("info.command.quit"));
	}

	@Override
	public boolean nextCommandWaitIs() {
		return false;
	}

}
