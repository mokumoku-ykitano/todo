package todo.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import todo.dto.json.Todo;
import todo.dto.json.TodoLog;
import todo.exception.TodoException;

public final class FilesUtil {

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	/*
	 * インスタンス化禁止
	 */
	private FilesUtil() {

	}

	/**
	 * Todoで使用するフォルダを全て作成します。
	 * 
	 * @throws TodoException
	 */
	public static void createTodoDirectories() throws TodoException {
		try {
			Files.createDirectories(Paths.get(getFullTodoLogDirectoryPath()));
		} catch (IOException e) {
			throw new TodoException(e, "error.util.create.directories");
		}
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

	/**
	 * todoリストを引数の内容で上書きします。
	 * 
	 * @param todoList
	 * @throws TodoException
	 * @throws IOException
	 */
	public static void writeTodoList(List<Todo> todoList) throws TodoException, IOException {
		try {
			String jsonText = new ObjectMapper().writeValueAsString(todoList);
			List<String> list = new ArrayList<>();
			list.add(jsonText);

			Files.write(FilesUtil.getTodoListPath(), list, StandardOpenOption.CREATE,
					StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
		} catch (JsonProcessingException e) {
			throw new TodoException(e, "error.util.json");
		} catch (IOException e) {
			// 呼び出し元でキャッチしてエラーを設定する
			throw e;
		}
	}

	/**
	 * Todoログファイルにログを追記します。
	 * 
	 * @param startDate
	 * @param endDate
	 * @param title
	 * @throws TodoException
	 * @throws IOException
	 */
	public static void writeTodoLog(Date startDate, Date endDate, String title) throws TodoException, IOException {
		try {
			TodoLog todoLog = new TodoLog(startDate, endDate, title);
			String jsonText = new ObjectMapper().writeValueAsString(todoLog);
			List<String> list = new ArrayList<>();
			list.add(jsonText);

			Files.write(getTodoLogPath(new Date()), list, StandardOpenOption.CREATE, StandardOpenOption.WRITE,
					StandardOpenOption.APPEND);

		} catch (JsonProcessingException e) {
			throw new TodoException(e, "error.util.json");
		} catch (IOException e) {
			// 呼び出し元でキャッチしてエラーを設定する
			throw e;
		}
	}

	/**
	 * Todoログフォルダのフルパスを取得します。
	 * 
	 * @return Todoログフォルダのフルパス
	 */
	private static String getFullTodoLogDirectoryPath() {
		ResourceBundle todoProp = ResourceBundle.getBundle("todo");
		return todoProp.getString("DIRECTORY_PATH") + "log/";
	}

	/**
	 * Todoログのフルパスを取得します。
	 * 
	 * @param date
	 * @return Todoログのフルパス
	 */
	private static String getFullTodoLogPath(Date date) {
		return getFullTodoLogDirectoryPath() + sdf.format(date) + ".log";
	}

	/**
	 * TodoログのPathオブジェクトを取得します。
	 * 
	 * @return TodoログのPathオブジェクト
	 */
	public static Path getTodoLogPath(Date date) {
		return Paths.get(getFullTodoLogPath(date));
	}

}
