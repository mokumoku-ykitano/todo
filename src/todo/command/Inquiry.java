package todo.command;

import todo.TodoControl;
import todo.dto.ExecutingTodo;
import todo.exception.TodoException;
import todo.util.MessageUtil;
import todo.util.StringUtil;

public class Inquiry extends Command {

	private String title = "問い合わせ";

	private String detail = null;

	@Override
	public void setArguments(String[] args) throws TodoException {
		if (args.length != 0) {
			detail = args[0];
		}
	}

	@Override
	public void execute() throws TodoException {
		// 実行中のtodoがある場合、stopする
		if (TodoControl.isExecutingTodo()) {
			Command stopCommand = new Stop();
			stopCommand.execute();
			stopCommand.showMessage();
		}
		if (StringUtil.isNotEmpty(detail)) {
			title += "(" + detail + ")";
		}
		TodoControl.setExecutingTodo(new ExecutingTodo(title));
	}

	@Override
	public void showMessage() {
		System.out.println(MessageUtil.getMessage("info.command.start", title));
	}

}
