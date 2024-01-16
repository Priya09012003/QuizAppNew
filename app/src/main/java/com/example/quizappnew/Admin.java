package com.example.quizappnew;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quizappnew.databinding.ActivityAdminBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Admin extends AppCompatActivity {

    ActivityAdminBinding binding;
    String question, opt1, opt2, opt3, answer;
    FirebaseDatabase db;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                question = binding.question.getText().toString();
                opt1 = binding.option1.getText().toString();
                opt2 = binding.option2.getText().toString();
                opt3 = binding.option3.getText().toString();
                answer = binding.optionans.getText().toString();

                if (!question.isEmpty() && !opt1.isEmpty() && !opt2.isEmpty() && !opt3.isEmpty() && !answer.isEmpty()) {
                    Questions questions = new Questions(question, opt1, opt2, opt3, answer);
                    db = FirebaseDatabase.getInstance();
                    reference = db.getReference("Quizes");
                    reference.child(question).setValue(questions).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d("Firebase", "Data saved successfully");
                                binding.question.setText("");
                                binding.option1.setText("");
                                binding.option2.setText("");
                                binding.option3.setText("");
                                binding.optionans.setText(""); // Clear answer field as well
                                Toast.makeText(Admin.this, "Successfully saved", Toast.LENGTH_SHORT).show();
                            } else {
                                Log.e("Firebase", "Error saving data: " + task.getException());
                                Toast.makeText(Admin.this, "Error saving data", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    // Handle the case where any of the fields is empty
                    Toast.makeText(Admin.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
