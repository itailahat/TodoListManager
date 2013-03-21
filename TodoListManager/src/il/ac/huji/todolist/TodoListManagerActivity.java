package il.ac.huji.todolist;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class TodoListManagerActivity extends Activity {
	
	private ArrayList<TodoItem> todos;
	private ListView todolist;
	private ArrayAdapter<TodoItem> todoAdapter;
	
	public final static int ADD_ACTIVITY_REQUEST_CODE = 1;
	public final static String ADD_ITEM_RESULT_STRING="title";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo_list_manager);
		
		todos = new ArrayList<TodoItem>();
		
		
		todolist = (ListView)findViewById(R.id.lstTodoItems);
		todoAdapter = new TodoListArrayAdapter(this,android.R.layout.simple_list_item_1,todos);
		todolist.setAdapter(todoAdapter);		
	}


	public void OpenAddItemActivity()
	{
		Intent intent = new Intent(this,AddNewTodoItemActivity.class);
		startActivityForResult(intent,ADD_ACTIVITY_REQUEST_CODE);
	}
	
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		switch(requestCode)
		{
		case ADD_ACTIVITY_REQUEST_CODE:
			if (RESULT_OK == resultCode)
			{
				String AddItemTitle = data.getStringExtra(ADD_ITEM_RESULT_STRING);
				AddItemInTodoList(AddItemTitle);
			}
			break;
		}
	}


	public void AddItemInTodoList(String thestring)
	{
    	if (null == thestring || thestring == "" || thestring.length()==0)
    	{
    		return;
    	}	
    	todoAdapter.add(new TodoItem(thestring,null));
    	
	}
	
	public void DeleteSelectedTodoItem()
	{
		ListView todoList = (ListView)findViewById(R.id.lstTodoItems);
		int selected = todoList.getSelectedItemPosition();
		if (selected >=0 )
		{
			todos.remove(selected);				
			todoAdapter.notifyDataSetChanged();
		}
		
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.todo_list_manager, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		switch (item.getItemId())
		{
		case R.id.menuItemAdd:
			OpenAddItemActivity();
			break;
		case R.id.menuItemDelete:
			DeleteSelectedTodoItem();
			break;
		default:
			return super.onOptionsItemSelected(item);	
		}
		return true;
	}

}
