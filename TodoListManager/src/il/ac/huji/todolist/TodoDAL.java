package il.ac.huji.todolist;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.parse.Parse;

import android.content.Context;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseException;

public class TodoDAL {
	
	public final static String PARSE_OBJECT_TODO_CLASS = "todo";
	public final static String PARSE_KEY_TITLE = "title";
	public final static String PARSE_KEY_DUE = "due";
	
	public TodoDAL(Context context) 
	{
		Parse.initialize(context, context.getString(R.string.parseApplication), 
				context.getString(R.string.clientKey));
		
		
	}
	
	public boolean insert(ITodoItem todoItem) 
	{
		//TODO: wrap with try catch - return false on exception
		ParseObject po = new ParseObject(PARSE_OBJECT_TODO_CLASS);
    	po.put(PARSE_KEY_TITLE, todoItem.getTitle());
    	po.put(PARSE_KEY_DUE, todoItem.getDueDate().getTime());
    	po.saveInBackground();
    	
		return true;
		
	}
	
	public boolean update(ITodoItem todoItem) 
	{
		return false;

	}
	
	public boolean delete(ITodoItem todoItem) 
	{
		//TODO: wrap with try catch
		ParseQuery pq = new ParseQuery(PARSE_OBJECT_TODO_CLASS);
		pq.whereEqualTo(PARSE_KEY_TITLE, todoItem.getTitle());
		pq.findInBackground(new FindCallback(){
			public void done(List<ParseObject> scoreList, ParseException e)
			{
				if (null==e)
				{
					for (Iterator<ParseObject> i = scoreList.iterator(); i.hasNext();)
					{
						ParseObject foundItem = i.next();
						//Log.d("TODO-Parse","found item:" + foundItem.getString(PARSE_KEY_TITLE));
						foundItem.deleteInBackground();//TODO: consider remove from DB on callback
					}
				}
				else
				{
					Log.d("TODO-Parse", "Could not find Parse object");
				}
				
			}
		});
		return true;
		
	}
	
	public List<ITodoItem> all() 
	{
		ParseQuery pq = new ParseQuery(PARSE_OBJECT_TODO_CLASS);
		try {
			List <ParseObject> pl = pq.find();
			List<ITodoItem> retList = new ArrayList<ITodoItem>();
			for (Iterator<ParseObject> i = pl.iterator(); i.hasNext();)
			{
				ParseObject po = i.next();
				retList.add(new TodoItem(po.getString(PARSE_KEY_TITLE),po.getDate(PARSE_KEY_DUE)));
			}
			return retList;
		} catch (ParseException e) {
			Log.d("TODO-PARSE", "Could not get parse data.");
			return null;
		}
		
	}
}
