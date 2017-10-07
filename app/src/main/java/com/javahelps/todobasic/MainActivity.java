package com.javahelps.todobasic;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<Todo> list ;
    CustomAdapter adapter ;
    String id ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listview);
        list = new ArrayList<>();

        TodoOpenHelper openHelper = TodoOpenHelper.getInstance(getApplicationContext());
        final SQLiteDatabase db = openHelper.getWritableDatabase();
        Cursor cursor = db.query(Contract.TODO_DATA_TABLE , null , null ,null , null , null ,null);
        while (cursor.moveToNext()){

            id = cursor.getString(cursor.getColumnIndex(Contract.TODO_ID));
            String title = cursor.getString(cursor.getColumnIndex(Contract.TODO_TASK));
            String date = cursor.getString(cursor.getColumnIndex(Contract.TODO_DATE));
            String time = cursor.getString(cursor.getColumnIndex(Contract.TODO_TIME));
            Todo todo = new Todo(title , date, time, id);
            list.add(todo);
        }

        adapter = new CustomAdapter(this, list , new CustomAdapter.DeleteButtonClickListener() {
            @Override
            public void onLongClicked(int position,View view) {

                id = list.get(position).getId() ;
                Log.i("TAG", "onLongClicked: "+position+ " "+id);
                db.delete(Contract.TODO_DATA_TABLE , Contract.TODO_ID + " =?" ,new String[]{id + ""});
                list.remove(position);
                adapter.notifyDataSetChanged();
            }
        });

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Todo todo = list.get(position);

                Intent intent = new Intent(MainActivity.this , DetailActivity.class);
                intent.putExtra("task" , todo);
                intent.putExtra("position" , position);
                startActivityForResult(intent , 1);

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == 10){
            if(requestCode == 1){
                long id = data.getLongExtra(Contract.TODO_TASK_ID , -1L);
                if (id > -1){
                    TodoOpenHelper openHelper = TodoOpenHelper.getInstance(getApplicationContext());
                    SQLiteDatabase db = openHelper.getReadableDatabase();

                    Cursor cursor = db.query(Contract.TODO_DATA_TABLE , null , Contract.TODO_ID+ " = ?" ,
                            new String[]{id + ""} , null ,null,null);
                    if (cursor.moveToFirst()){
                        String title = cursor.getString(cursor.getColumnIndex(Contract.TODO_TASK));
                        String date = cursor.getString(cursor.getColumnIndex(Contract.TODO_DATE));
                        String time = cursor.getString(cursor.getColumnIndex(Contract.TODO_TIME));
                        Todo todo = new Todo(title , date , time);
                        list.add(todo);
                    }
                }
            }
        }
        else if (resultCode == 20){
            if (requestCode == 2){
                long id = data.getLongExtra(Contract.TODO_TASK_ID , -1L);
                if (id > -1){
                    TodoOpenHelper openHelper = TodoOpenHelper.getInstance(getApplicationContext());
                    SQLiteDatabase db = openHelper.getReadableDatabase();

                    Cursor cursor = db.query(Contract.TODO_DATA_TABLE , null , Contract.TODO_ID+ " = ?" ,
                            new String[]{id + ""} , null ,null,null);
                    if (cursor.moveToFirst()){
                        String title = cursor.getString(cursor.getColumnIndex(Contract.TODO_TASK));
                        String date = cursor.getString(cursor.getColumnIndex(Contract.TODO_DATE));
                        String time = cursor.getString(cursor.getColumnIndex(Contract.TODO_TIME));
                        Todo todo = new Todo(title , date , time);
                        list.add(todo);
                    }
                }

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu , menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id= item.getItemId();

        if (id == R.id.add){
            Intent intent = new Intent(MainActivity.this , AddActivity.class);
            startActivityForResult(intent,2);
        }
        else if (id == R.id.feedback){
            Intent feedback = new Intent();
            feedback.setAction(Intent.ACTION_SENDTO);
            feedback.setData(Uri.parse("mailto:mittalsneha.1403@gmail.com"));
            startActivity(feedback);
        }
        else if (id == R.id.howTo){
            Intent intent = new Intent(MainActivity.this , HowToUseActivity.class);
            startActivity(intent);
        }
        return true ;
    }
}

