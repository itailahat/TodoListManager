package il.ac.huji.todolist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TodoListDB extends SQLiteOpenHelper {
	
	public static final String TODO_TABLE_NAME = "todo";
	public static final String TODO_ID_COLUMN_NAME = "_id";
	public static final String TODO_TITLE_COLUMN_NAME = "title";
	public static final String TODO_DUE_COLUMN_NAME = "due";
	
	public TodoListDB(Context context) {
		super(context, "todo_db",null, 1);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + TODO_TABLE_NAME +" (" +
					TODO_ID_COLUMN_NAME + " integer primary key autoincrement, " +
					TODO_TITLE_COLUMN_NAME + " text, " +
					TODO_DUE_COLUMN_NAME + " long);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		

	}

}
