package todo.command;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import todo.exception.TodoException;
import todo.json.Todo;
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
	public void execute() throws TodoException {
		try {
			Path todoListPath = Paths.get(getFullTodoListPath());

			List<Todo> todoList = loadTodoList(todoListPath);
			todoList.add(new Todo(todoTitle));

			String jsonText = new ObjectMapper().writeValueAsString(todoList);
			List<String> list = new ArrayList<>();
			list.add(jsonText);

			Files.write(todoListPath, list, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING,
					StandardOpenOption.WRITE);

			System.out.println(MessageUtil.getMessage("info.add.finish", todoTitle));

		} catch (JsonProcessingException e) {
			throw new TodoException(e, "error.add.json");
		} catch (IOException e) {
			throw new TodoException(e, "error.add.list", todoTitle);
		}
	}

	/**
	 * Todoリストのフルパスを取得します。
	 * 
	 * @return Todoリストのフルパス
	 */
	private String getFullTodoListPath() {
		ResourceBundle todoProp = ResourceBundle.getBundle("todo");
		return todoProp.getString("DIRECTORY_PATH") + todoProp.getString("TODO_LIST_FILE_NAME");
	}

	/**
	 * Todoリストを読み込んで、リストに変換して返します。<br>
	 * Todoリストが存在しない場合、空のリストを返します。
	 * 
	 * @param todoListPath
	 * @return Todoリスト
	 * @throws TodoException
	 */
	private List<Todo> loadTodoList(Path todoListPath) throws TodoException {
		if (Files.exists(todoListPath)) {
			// 既存リストの読み込み
			try (InputStream is = Files.newInputStream(todoListPath)) {
				return new ObjectMapper().readValue(is, new TypeReference<List<Todo>>() {
				});
			} catch (IOException e) {
				throw new TodoException(e, "error.add.load.list");
			}
		} else {
			return new ArrayList<>();
		}
	}

}
