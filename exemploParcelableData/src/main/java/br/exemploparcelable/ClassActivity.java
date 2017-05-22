package br.exemploparcelable;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import br.exemploparcelable.adapter.ClassAdapter;
import br.exemploparcelable.domain.Student;

public class ClassActivity extends Activity {
	private ArrayList<Student> students;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_class);
		
		
		students = getIntent().getParcelableArrayListExtra("students");
		
		ListView lv = (ListView) findViewById(R.id.listView);
		lv.setAdapter(new ClassAdapter(this, students));
	}

}
