package il.ac.huji.todolist;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.app.Activity;
import android.content.Intent;

public class AddNewTodoItemActivity extends Activity {
		
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_new_item);
		
	}

	
	public void okClickHandler(View view)
	{
		Intent result = new Intent();
		EditText newTodo = (EditText)findViewById(R.id.edtNewItem);
		result.putExtra(TodoListManagerActivity.ADD_ITEM_RESULT_STRING, newTodo.getText().toString());
		setResult(RESULT_OK,result);
		finish();
	}
	
	public void cancleClickHandler(View view)
	{
		Intent result = new Intent();
		setResult(RESULT_CANCELED,result);
		finish();
	}
	

}
