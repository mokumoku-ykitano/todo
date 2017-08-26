package todo.command;

import java.util.Date;

import todo.TodoControl;
import todo.dto.ExecutingTodo;
import todo.exception.TodoException;
import todo.util.MessageUtil;

public class Meeting extends Command {

	private String title = "打ち合わせ";

	@Override
	public void execute() throws TodoException {

		// 実行中のtodoがある場合、stopする
		if (TodoControl.isExecutingTodo()) {
			Command stopCommand = new Stop();
			stopCommand.execute();
			stopCommand.showMessage();
		}

		ExecutingTodo executingTodo = new ExecutingTodo();
		executingTodo.title = title;
		executingTodo.startDate = new Date();
		TodoControl.setExecutingTodo(executingTodo);
	}

	@Override
	public void showMessage() {
		System.out.println(MessageUtil.getMessage("info.command.start", title));
	}

}
