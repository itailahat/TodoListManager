package il.ac.huji.todolist;

import java.util.Date;

public class TodoItem implements ITodoItem{
	public String title;
	public Date dueDate;
	
	public TodoItem(String title, Date dueDate) {
		super();
		this.title = title;
		this.dueDate = dueDate;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public Date getDueDate() {
		return dueDate;
	}
	
	
}
