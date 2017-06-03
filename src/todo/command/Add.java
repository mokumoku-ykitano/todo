package todo.command;

import todo.util.MessageUtil;

public class Add extends Command {

	private String todoTitle;

	@Override
	public void setArguments(String[] args) {
		if (args == null || args.length == 0) {
			throw new IllegalArgumentException(MessageUtil.getMessage("error.command.add.argument"));
		}
		todoTitle = args[0];
	}

	@Override
	public void run() {
		// TODO 自動生成されたメソッド・スタブ
	}

}
