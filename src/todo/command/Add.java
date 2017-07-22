package todo.command;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import todo.exception.TodoException;
import todo.json.Todo;
import todo.util.MessageUtil;
import todo.util.TodoListUtil;

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
			List<Todo> todoList = TodoListUtil.loadTodoList();
			todoList.add(new Todo(todoTitle));

			String jsonText = new ObjectMapper().writeValueAsString(todoList);
			List<String> list = new ArrayList<>();
			list.add(jsonText);

			Files.write(TodoListUtil.getTodoListPath(), list, StandardOpenOption.CREATE,
					StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);

			System.out.println(MessageUtil.getMessage("info.add.finish", todoTitle));

		} catch (JsonProcessingException e) {
			throw new TodoException(e, "error.add.json");
		} catch (IOException e) {
			throw new TodoException(e, "error.add.list", todoTitle);
		}
	}
}
