package android.example.todolist;

import android.content.Intent;
import android.example.todolist.Model.Listdata;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText title,desc;
    String titlesend,descsend;

    private DatabaseReference mdatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        title=findViewById(R.id.title);
        desc=findViewById(R.id.desc);
        mdatabase = FirebaseDatabase.getInstance().getReference();


    }
    public void AddNotes(View view) {
        titlesend=title.getText().toString();
        descsend=desc.getText().toString();
        if(TextUtils.isEmpty(titlesend) || TextUtils.isEmpty(descsend)){
            return;
        }
        AddNotes(titlesend,descsend);

    }

    private void AddNotes(String titlesend, String descsend)
    {

        String id=mdatabase.push().getKey();
        Listdata listdata = new Listdata(id,titlesend, descsend);
        mdatabase.child("Notes").child(id).setValue(listdata).
                addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(MainActivity.this, "Notes Added", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),Home.class));
                    }
                });

    }
}
