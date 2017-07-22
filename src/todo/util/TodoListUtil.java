package todo.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import todo.exception.TodoException;
import todo.json.Todo;

public final class TodoListUtil {

	/*
	 * インスタンス化禁止
	 */
	private TodoListUtil() {

	}

	/**
	 * Todoリストのフルパスを取得します。
	 * 
	 * @return Todoリストのフルパス
	 */
	private static String getFullTodoListPath() {
		ResourceBundle todoProp = ResourceBundle.getBundle("todo");
		return todoProp.getString("DIRECTORY_PATH") + todoProp.getString("TODO_LIST_FILE_NAME");
	}

	/**
	 * TodoリストのPathオブジェクトを取得します。
	 * 
	 * @return TodoリストのPathオブジェクト
	 */
	public static Path getTodoListPath() {
		return Paths.get(getFullTodoListPath());
	}

	/**
	 * Todoリストを読み込んで、リストに変換して返します。<br>
	 * Todoリストが存在しない場合、空のリストを返します。
	 * 
	 * @param todoListPath
	 * @return Todoリスト
	 * @throws TodoException
	 */
	public static List<Todo> loadTodoList() throws TodoException {

		Path todoListPath = Paths.get(getFullTodoListPath());

		if (Files.exists(todoListPath)) {
			// 既存リストの読み込み
			try (InputStream is = Files.newInputStream(todoListPath)) {
				return new ObjectMapper().readValue(is, new TypeReference<List<Todo>>() {
				});
			} catch (IOException e) {
				throw new TodoException(e, "error.util.load.todoList");
			}
		} else {
			return new ArrayList<>();
		}
	}

}
