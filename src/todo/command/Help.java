package todo.command;

import java.io.File;
import java.net.URL;
import java.util.MissingResourceException;

import todo.CommandFactory;
import todo.exception.TodoException;

public class Help extends Command {

	@Override
	public void execute() throws TodoException {

		File[] files = getCommandFiles();
		Command command = null;

		for (File file : files) {
			try {
				command = CommandFactory.create(file.getName().replaceAll(".class$", ""));
				System.out.println(command.getHelpText());
			} catch (MissingResourceException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * todo.commandパッケージ配下のクラス(Commandを除く)から<br>
	 * Fileオブジェクト配列を作成します。
	 * 
	 * @return コマンドのFileオブジェクト配列
	 */
	private File[] getCommandFiles() {
		URL url = getClass().getClassLoader().getResource("todo/command");
		File directory = new File(url.getFile());
		return directory.listFiles((dir, name) -> !name.equals("Command.class"));
	}

}
