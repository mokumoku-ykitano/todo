package todo.command;

import todo.exception.TodoException;

public abstract class Command {

	/**
	 * コマンドの引数を設定します。<br>
	 * 引数のチェックも行います。
	 * 
	 * @param args
	 */
	public void setArguments(String[] args) {

	}

	/**
	 * コマンドを実行します。
	 */
	public abstract void execute() throws TodoException;

	/**
	 * 次の入力を待つかどうか。<br>
	 * 終了する場合はfalseを返す。
	 * 
	 * @return true(次の入力を待つ) / false(終了する)
	 */
	public boolean nextCommandWaitIs() {
		return true;
	}

}
