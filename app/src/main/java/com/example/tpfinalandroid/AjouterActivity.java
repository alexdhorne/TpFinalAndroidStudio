package com.example.tpfinalandroid;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class AjouterActivity extends AppCompatActivity {

    FirebaseFirestore db;
    TextInputEditText questionTextView;
    FloatingActionButton validerButton;
    String questionId;
    Button supprimerButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter);

        db = FirebaseFirestore.getInstance();

        questionTextView = findViewById(R.id.question);

        questionId = getIntent().getStringExtra("id");

        supprimerButton = findViewById(R.id.supprimer);
        if (questionId != null && !questionId.trim().isEmpty()) {
            questionTextView.setText(getIntent().getStringExtra("question"));

           supprimerButton.setVisibility(View.VISIBLE);
        }

        supprimerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                supprimer();
            }
        });

        validerButton = findViewById(R.id.btn_valider);
        validerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> question = new HashMap<>();
                question.put("question", questionTextView.getText().toString());

                if (questionId != null && !questionId.trim().isEmpty()) {
                    modifier(question);
                } else {
                    ajouter(question);
                }
            }
        });
    }

    public void ajouter(Map<String, Object> question) {
        db.collection("questions")
                .add(question)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Snackbar.make(findViewById(R.id.activity_ajouter), "Questions ajoutée avec succès !", Snackbar.LENGTH_LONG).show();
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Snackbar.make(findViewById(R.id.activity_ajouter), "Une erreur est survenue", Snackbar.LENGTH_LONG).show();
                    }
                });
    }

    public void modifier(Map<String, Object> question) {
        db.collection("questions")
                .document(questionId)
                .set(question)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Snackbar.make(findViewById(R.id.activity_ajouter), "Questions modifiée avec succès ! ", Snackbar.LENGTH_LONG).show();
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Snackbar.make(findViewById(R.id.activity_ajouter), "Une erreur est survenue", Snackbar.LENGTH_LONG).show();
                    }
                });
    }


    public void supprimer() {
        if (questionId != null && !questionId.trim().isEmpty()) {
            db.collection("questions")
                    .document(questionId)
                    .delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Snackbar.make(findViewById(R.id.activity_ajouter), "Question supprimée avec succès ! ", Snackbar.LENGTH_LONG).show();
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Snackbar.make(findViewById(R.id.activity_ajouter), "Une erreur est survenue", Snackbar.LENGTH_LONG).show();
                        }
                    });
        }
    }

}
