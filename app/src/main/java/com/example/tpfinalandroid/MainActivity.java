package com.example.tpfinalandroid;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ListView questionsListView;
    List<Question> questions = new ArrayList<>();
    List<String> listeQuestions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questionsListView = findViewById(R.id.liste_questions);
        db = FirebaseFirestore.getInstance();
        chargerQuestions();


        questionsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Question questionSelectionne = questions.get(position);

                Intent i = new Intent(MainActivity.this, AjouterActivity.class);

                i.putExtra("id", questionSelectionne.getId());
                i.putExtra("question", questionSelectionne.getQuestion());

                startActivity(i);
            }
        });


        FloatingActionButton ajouter = findViewById(R.id.btn_ajouter);
        ajouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AjouterActivity.class);
                startActivity(i);
            }
        });

    }

    public void chargerQuestions() {
        db.collection("questions").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.e("ERROR", "Listen failed.", e);
                }

                questions = new ArrayList<>();
                listeQuestions = new ArrayList<>();
                for (QueryDocumentSnapshot document : value) {
                    questions.add(new Question(document.getId(), document.getString("question"), document.getString("reponse")));
                    listeQuestions.add(document.getString("question"));
                }
                    questionsListView.setAdapter(new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, listeQuestions));
            }
        });
    }
}