package com.example.crcqtrmanagment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UploadData extends AppCompatActivity {

    private static final int IMPORT_REQUEST_CODE = 1;  // Request code for import activity

    private EditText QtrNo, EmployeName, Designation, Neisno, Unit, Other, PhoneNo, Remark;
    private Button saveButton, importButton;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_upload_data);

        // Initialize Firebase reference
        databaseReference = FirebaseDatabase.getInstance().getReference("QTR List");

        // Initialize views
        QtrNo = findViewById(R.id.QtrNo);
        EmployeName = findViewById(R.id.EmployeName);
        Designation = findViewById(R.id.Designation);
        Neisno = findViewById(R.id.neisno);
        Unit = findViewById(R.id.unit);
        Other = findViewById(R.id.other);
        PhoneNo = findViewById(R.id.phoneNo);
        Remark = findViewById(R.id.Remark);
        saveButton = findViewById(R.id.saveButton);
        importButton = findViewById(R.id.importButton);

        // Redirect to ImportData
        importButton.setOnClickListener(v -> {
            Intent intent = new Intent(UploadData.this, ImportData.class);
            startActivity(intent);
        });

        // Set up listener for save button click
        saveButton.setOnClickListener(v -> uploadData());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMPORT_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                // Retrieve and set the imported data
                QtrNo.setText(data.getStringExtra("QtrNo"));
                EmployeName.setText(data.getStringExtra("EmployeName"));
                Designation.setText(data.getStringExtra("Designation"));
                Neisno.setText(data.getStringExtra("Neisno"));
                Unit.setText(data.getStringExtra("Unit"));
                Other.setText(data.getStringExtra("Other"));
                PhoneNo.setText(data.getStringExtra("PhoneNo"));
                Remark.setText(data.getStringExtra("Remark"));
            }
        }
    }


    private void uploadData() {
        // Get data from EditText fields
        String qtrNo = QtrNo.getText().toString().trim();
        String employeName = EmployeName.getText().toString().trim();
        String designation = Designation.getText().toString().trim();
        String neisno = Neisno.getText().toString().trim();
        String unit = Unit.getText().toString().trim();
        String other = Other.getText().toString().trim();
        String phoneNo = PhoneNo.getText().toString().trim();
        String remark = Remark.getText().toString().trim();

        // Validate input fields
        if (qtrNo.isEmpty()) {
            Toast.makeText(UploadData.this, "Please enter a Quarter Number", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a DataClass object with the entered data
        DataClass dataClass = new DataClass(qtrNo, employeName, designation, neisno, unit, other, phoneNo, remark);

        // Use the Quarter Number as the key in Firebase
        databaseReference.child(qtrNo)
                .setValue(dataClass)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(UploadData.this, "Data inserted successfully", Toast.LENGTH_SHORT).show();
                        clearFields(); // Clear fields after successful save
                    } else {
                        Toast.makeText(UploadData.this, "Data insertion failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void clearFields() {
        QtrNo.setText("");
        EmployeName.setText("");
        Designation.setText("");
        Neisno.setText("");
        Unit.setText("");
        Other.setText("");
        PhoneNo.setText("");
        Remark.setText("");
    }
}
