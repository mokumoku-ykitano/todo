package todo.command;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CommandTest {

	@Test
	public void createAdd() throws ReflectiveOperationException {
		Command add = Command.create("add");
		assertTrue(add instanceof Add);
	}

}
