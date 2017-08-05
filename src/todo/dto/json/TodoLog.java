package todo.dto.json;

import java.util.Date;

public class TodoLog {

	/** 開始日時 */
	public Date startDate;
	/** 終了日時 */
	public Date endDate;
	/** タイトル */
	public String title;
	/** 作業時間(分) */
	public long workingMinutes;

	public TodoLog() {

	}

	public TodoLog(Date startDate, Date endDate, String title) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.title = title;
		workingMinutes = (endDate.getTime() - startDate.getTime()) / (1000 * 60);
	}

}
