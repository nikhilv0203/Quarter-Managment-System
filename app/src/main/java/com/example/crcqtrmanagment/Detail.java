package com.example.crcqtrmanagment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.github.clans.fab.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Detail extends AppCompatActivity {

    TextView detailQtrNo, detailEmpName, detailDesignation, detailNEIS, detailUnit, detailLegal, detailPhoneNo, detailRemark;
    FloatingActionButton editButton, deleteButton;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);

        detailQtrNo = findViewById(R.id.detailQTR);
        detailEmpName = findViewById(R.id.DetailEmployeName);
        detailDesignation = findViewById(R.id.DetailDesignation);
        detailNEIS = findViewById(R.id.DetailNeisno);
        detailUnit = findViewById(R.id.DetailUnit);
        detailLegal = findViewById(R.id.DetailLegal);
        detailPhoneNo = findViewById(R.id.DetailPhoneNo);


        editButton = findViewById(R.id.editButton);
        deleteButton = findViewById(R.id.deleteButton);

        // Get the data from the intent
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            detailQtrNo.setText(bundle.getString("QtrNo"));
            detailEmpName.setText(bundle.getString("EmployeName"));
            detailDesignation.setText(bundle.getString("Designation"));
            detailNEIS.setText(bundle.getString("Neisno"));
            detailUnit.setText(bundle.getString("Unit"));
            detailLegal.setText(bundle.getString("Other"));
            detailPhoneNo.setText(bundle.getString("PhoneNo"));
            //recRemark.setText(bundle.getString("Remark"));
        }

        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("QTR List"); // Adjust path as needed

        // Set up edit button
        editButton.setOnClickListener(view -> {
            Intent editIntent = new Intent(Detail.this, UpdateData.class);
            editIntent.putExtra("QtrNo", detailQtrNo.getText().toString());
            editIntent.putExtra("EmployeName", detailEmpName.getText().toString());
            editIntent.putExtra("Designation", detailDesignation.getText().toString());
            editIntent.putExtra("Neisno", detailNEIS.getText().toString());
            editIntent.putExtra("Unit", detailUnit.getText().toString());
            editIntent.putExtra("Other", detailLegal.getText().toString());
            editIntent.putExtra("PhoneNo", detailPhoneNo.getText().toString());
            //editIntent.putExtra("Remark", recRemark.getText().toString());
            startActivity(editIntent);
        });

        // Set up delete button
        deleteButton.setOnClickListener(v -> {
            String qtrNo = detailQtrNo.getText().toString().trim();

            if (!qtrNo.isEmpty()) {
                databaseReference.orderByChild("qtrNo").equalTo(qtrNo).get().addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        boolean recordFound = false;
                        for (DataSnapshot snapshot : task.getResult().getChildren()) {
                            recordFound = true;
                            snapshot.getRef().removeValue()
                                    .addOnCompleteListener(deleteTask -> {
                                        if (deleteTask.isSuccessful()) {
                                            Toast.makeText(Detail.this, "Record deleted successfully", Toast.LENGTH_SHORT).show();
                                            finish();
                                        } else {
                                            Toast.makeText(Detail.this, "Failed to delete record", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                        if (!recordFound) {
                            Toast.makeText(Detail.this, "No record found to delete", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(Detail.this, "Failed to find record", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(Detail.this, "Please provide a valid Quarter Number", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
