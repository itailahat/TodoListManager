package il.ac.huji.todolist;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class TodoListManagerActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo_list_manager);
		
		ArrayList<String> todos = new ArrayList<String>();
		// TODO: remove this addings:
		todos.add("Some todo");
		todos.add("Some todo2");
		todos.add("Some todo3");
		todos.add("Some todo4");
		ListView list = (ListView)findViewById(R.id.lstTodoItems);
		ArrayAdapter<String> todoAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,todos);
		list.setAdapter(todoAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.todo_list_manager, menu);
		return true;
	}

}
