package todo.dto;

import java.util.Date;

public class ExecutingTodo {

	/** 実行中のtodoタイトル */
	private String title;
	/** 開始日時 */
	private Date startDate;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
}
