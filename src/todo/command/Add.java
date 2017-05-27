package todo.command;

public class Add extends Command {

	private String todoTitle;

	@Override
	public void setArguments(String[] args) {
		if (args.length == 0) {
			throw new IllegalArgumentException();
		}
		todoTitle = args[0];
	}

	@Override
	public void run() {
		// TODO 自動生成されたメソッド・スタブ
	}

}
