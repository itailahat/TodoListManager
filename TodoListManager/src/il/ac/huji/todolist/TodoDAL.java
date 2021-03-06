package il.ac.huji.todolist;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.parse.Parse;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class TodoDAL {
	
	public final static String PARSE_OBJECT_TODO_CLASS = "todo";
	public final static String PARSE_KEY_TITLE = "title";
	public final static String PARSE_KEY_DUE = "due";
	public final static String PARSE_KEY_USER = "user";
	private TodoListDB TodoDB;
	private SQLiteDatabase Db;
	private ParseUser currentUser;
	
	
	public TodoDAL(Context context) 
	{
		Parse.initialize(context, context.getString(R.string.parseApplication), 
				context.getString(R.string.clientKey));
		ParseUser.enableAutomaticUser();
		currentUser = ParseUser.getCurrentUser();
		//Parse.initialize(this, "148sQStrtsiLhDuRR6qewGuPHnoHHmYdg6joyhIX", 
		//				"w5OAVrGA5tEq14nYdGUAv2XYQG7cRr5wq7ezlxUd");
		
		
		TodoDB = new TodoListDB(context);
		Db = TodoDB.getWritableDatabase();
	}
	
	public boolean insert(ITodoItem todoItem) 
	{
		// Local DB:
    	ContentValues TDItem = new ContentValues();
    	TDItem.put(TodoListDB.TODO_TITLE_COLUMN_NAME,todoItem.getTitle());
    	TDItem.put(TodoListDB.TODO_DUE_COLUMN_NAME, todoItem.getDueDate().getTime()); 
    	if (Db.insert(TodoListDB.TODO_TABLE_NAME, null, TDItem)<0)
    	{
    		return false;
    	}
		
    	try
    	{
    		
    		ParseObject po = new ParseObject(PARSE_OBJECT_TODO_CLASS);
	    	po.put(PARSE_KEY_USER, currentUser);
    		po.put(PARSE_KEY_TITLE, todoItem.getTitle());
	    	po.put(PARSE_KEY_DUE, todoItem.getDueDate().getTime());
	    	po.save();
    	}
    	catch (Exception e)
    	{
    		Log.d("Parse","Failed adding item:" + e.getMessage());
    		return false;
    	}
		return true;
		
	}
	
	public boolean update(ITodoItem todoItem) 
	{
		ContentValues TDItem = new ContentValues();
    	TDItem.put(TodoListDB.TODO_DUE_COLUMN_NAME, todoItem.getDueDate().getTime());
    	if (0==Db.update(TodoListDB.TODO_TABLE_NAME, 
    					TDItem, 
    					TodoListDB.TODO_TITLE_COLUMN_NAME+"=?",
    					new String[]{todoItem.getTitle()}))
    	{
    		return false;
    	}
		
		
		try
		{
			ParseQuery pq = new ParseQuery(PARSE_OBJECT_TODO_CLASS);
			pq.whereEqualTo(PARSE_KEY_USER, currentUser);
			pq.whereEqualTo(PARSE_KEY_TITLE, todoItem.getTitle());
			ParseObject po = pq.getFirst();
			po.put(PARSE_KEY_DUE, todoItem.getDueDate().getTime());
			po.save();
		}
		catch (Exception e) {			
			Log.d("Parse","Failed updating item:" + e.getMessage());
			return false;
		}
		
		return true;
	}
	
	public boolean delete(ITodoItem todoItem) 
	{
		
		
		//Delete from Db:
		Db.delete(TodoListDB.TODO_TABLE_NAME, TodoListDB.TODO_TITLE_COLUMN_NAME + "=?", 
				new String[]{todoItem.getTitle()});
		
		try
		{
			ParseQuery pq = new ParseQuery(PARSE_OBJECT_TODO_CLASS);
			pq.whereEqualTo(PARSE_KEY_USER, currentUser);
			pq.whereEqualTo(PARSE_KEY_TITLE, todoItem.getTitle());
			ParseObject po = pq.getFirst();
			po.delete();
		}
		catch (Exception e)
		{
			Log.d("Parse","Failed deleting item:" + e.getMessage());
			return false;
		}
		/*ParseQuery pq = new ParseQuery(PARSE_OBJECT_TODO_CLASS);
		pq.whereEqualTo(PARSE_KEY_TITLE, todoItem.getTitle());
		pq.findInBackground(new FindCallback(){
			public void done(List<ParseObject> scoreList, ParseException e)
			{
				if (null==e)
				{
					for (Iterator<ParseObject> i = scoreList.iterator(); i.hasNext();)
					{
						ParseObject foundItem = i.next();
						//Log.d("todo-Parse","found item:" + foundItem.getString(PARSE_KEY_TITLE));
						foundItem.deleteInBackground();
					}
				}
				else
				{
					Log.d("todo-Parse", "Could not find Parse object");
				}
				
			}
		});*/
		return true;
		
	}
	
	public List<ITodoItem> all() 
	{
		List<ITodoItem> todos = new ArrayList<ITodoItem>();
		Cursor cursor = Db.query(TodoListDB.TODO_TABLE_NAME, 
				new String[]{TodoListDB.TODO_TITLE_COLUMN_NAME, TodoListDB.TODO_DUE_COLUMN_NAME}, null, null, null, null, null);
		if (cursor.moveToFirst())
		{
			do {
				todos.add(new TodoItem(cursor.getString(0), new Date(cursor.getLong(1))));
			} while (cursor.moveToNext());
		}
		return todos;
		/*ParseQuery pq = new ParseQuery(PARSE_OBJECT_TODO_CLASS);
		try {
			List<ITodoItem> retList = new ArrayList<ITodoItem>();
			List <ParseObject> pl = pq.find();
			for (Iterator<ParseObject> i = pl.iterator(); i.hasNext();)
			{
				ParseObject po = i.next();
				
				Log.d("todo-get","the date is :" + po.getLong(PARSE_KEY_DUE));
				retList.add(new TodoItem(po.getString(PARSE_KEY_TITLE),new Date(po.getLong(PARSE_KEY_DUE))));
			}
			return retList;
		} catch (ParseException e) {
			Log.d("todo-PARSE", "Could not get parse data.");
			return null;
		}*/
				
	}
}
