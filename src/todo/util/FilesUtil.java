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

	/** todoログファイル名 */
	private static final SimpleDateFormat todoLogFileName = new SimpleDateFormat("yyyy-MM-dd");
	/** todoログに出力する日付 */
	private static final SimpleDateFormat todoLogDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

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
			Files.createDirectories(Paths.get(getFullTodoLogDirectoryPathText()));
		} catch (IOException e) {
			throw new TodoException(e, "error.util.create.directories");
		}
	}

	/**
	 * Todoリストのフルパスを取得します。
	 * 
	 * @return Todoリストのフルパス
	 */
	private static String getFullTodoListPathText() {
		ResourceBundle todoProp = ResourceBundle.getBundle("todo");
		return todoProp.getString("DIRECTORY_PATH") + todoProp.getString("TODO_LIST_FILE_NAME");
	}

	/**
	 * TodoリストのPathオブジェクトを取得します。
	 * 
	 * @return TodoリストのPathオブジェクト
	 */
	public static Path getTodoListPath() {
		return Paths.get(getFullTodoListPathText());
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

		Path todoListPath = getTodoListPath();

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
			String jsonText = makeJsonText(todoList);
			List<String> list = new ArrayList<>();
			list.add(jsonText);

			Files.write(FilesUtil.getTodoListPath(), list, StandardOpenOption.CREATE,
					StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
		} catch (IOException e) {
			// 呼び出し元でキャッチしてエラーを設定する
			throw e;
		}
	}

	/**
	 * Todoログ用のマッパーを生成します。
	 * 
	 * @return Todoログ用のマッパー
	 */
	private static ObjectMapper createTodoLogMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setDateFormat(todoLogDate);
		return objectMapper;
	}

	/**
	 * 引数からJSON文字列を作成します。
	 * 
	 * @param object
	 * @return JSON文字列
	 * @throws TodoException
	 */
	private static String makeJsonText(Object object) throws TodoException {
		return makeJsonText(new ObjectMapper(), object);
	}

	/**
	 * 引数からJSON文字列を作成します。
	 * 
	 * @param objectMapper
	 * @param object
	 * @return JSON文字列
	 * @throws TodoException
	 */
	private static String makeJsonText(ObjectMapper objectMapper, Object object) throws TodoException {
		try {
			return objectMapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			throw new TodoException(e, "error.util.json");
		}
	}

	/**
	 * Todoログを引数の内容で上書きします。
	 * 
	 * @param todologList
	 * @throws TodoException
	 * @throws IOException
	 */
	public static void writeTodoLog(List<TodoLog> todologList) throws TodoException, IOException {
		try {
			String jsonText = makeJsonText(createTodoLogMapper(), todologList);
			List<String> list = new ArrayList<>();
			list.add(jsonText);

			Files.write(FilesUtil.getTodoLogPath(new Date()), list, StandardOpenOption.CREATE,
					StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
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
	private static String getFullTodoLogDirectoryPathText() {
		ResourceBundle todoProp = ResourceBundle.getBundle("todo");
		return todoProp.getString("DIRECTORY_PATH") + "log/";
	}

	/**
	 * Todoログのフルパスを取得します。
	 * 
	 * @param date
	 * @return Todoログのフルパス
	 */
	private static String getFullTodoLogPathText(Date date) {
		return getFullTodoLogDirectoryPathText() + todoLogFileName.format(date) + ".log";
	}

	/**
	 * TodoログのPathオブジェクトを取得します。
	 * 
	 * @return TodoログのPathオブジェクト
	 */
	public static Path getTodoLogPath(Date date) {
		return Paths.get(getFullTodoLogPathText(date));
	}

	/**
	 * Todoログを読み込んで、リストに変換して返します。<br>
	 * Todoログが存在しない場合、空のリストを返します。
	 * 
	 * @return Todoログリスト
	 * @throws TodoException
	 */
	public static List<TodoLog> loadTodoLogList() throws TodoException {

		Path todoListPath = getTodoLogPath(new Date());

		if (Files.exists(todoListPath)) {
			// 既存リストの読み込み
			try (InputStream is = Files.newInputStream(todoListPath)) {
				return createTodoLogMapper().readValue(is, new TypeReference<List<TodoLog>>() {
				});
			} catch (IOException e) {
				throw new TodoException(e, "error.util.load.todoLogList");
			}
		} else {
			return new ArrayList<>();
		}
	}

}
