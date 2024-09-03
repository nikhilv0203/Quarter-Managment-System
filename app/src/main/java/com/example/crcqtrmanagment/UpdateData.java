package com.example.crcqtrmanagment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class UpdateData extends AppCompatActivity {

    EditText updateQtrNo, updateEmpName, updateDesignation, updateNEIS, updateUnit, updateLegal, updatePhoneNo, updateRemark;
    Button updateButton;
    DatabaseReference databaseReference;

    // Variables to hold the original values
    String _qtrNo, _employeName, _designation, _neisno, _unit, _other, _phoneNo, _remark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_data);

        databaseReference = FirebaseDatabase.getInstance().getReference("QTR List");

        // Initialize views
        updateQtrNo = findViewById(R.id.UpdateQtrNo);
        updateEmpName = findViewById(R.id.UpdateEmployeName);
        updateDesignation = findViewById(R.id.UpdateDesignation);
        updateNEIS = findViewById(R.id.Updateneisno);
        updateUnit = findViewById(R.id.Updateunit);
        updateLegal = findViewById(R.id.UpdateLegal);
        updatePhoneNo = findViewById(R.id.UpdatephoneNo);
        updateRemark = findViewById(R.id.UpdateRemark);
        updateButton = findViewById(R.id.UpdateButton);

        // Retrieve data from the intent
        ShowAllData();

        // Set up listener for update button click
        updateButton.setOnClickListener(v -> updateData());
    }

    private void ShowAllData() {
        _qtrNo = getIntent().getStringExtra("QtrNo");
        _employeName = getIntent().getStringExtra("EmployeName");
        _designation = getIntent().getStringExtra("Designation");
        _neisno = getIntent().getStringExtra("Neisno");
        _unit = getIntent().getStringExtra("Unit");
        _other = getIntent().getStringExtra("Other");
        _phoneNo = getIntent().getStringExtra("PhoneNo");
        _remark = getIntent().getStringExtra("Remark");

        // Logging to check the values
        Log.d("UpdateData", "QtrNo: " + _qtrNo);
        Log.d("UpdateData", "EmployeName: " + _employeName);
        Log.d("UpdateData", "Designation: " + _designation);
        Log.d("UpdateData", "Neisno: " + _neisno);
        Log.d("UpdateData", "Unit: " + _unit);
        Log.d("UpdateData", "Other: " + _other);
        Log.d("UpdateData", "PhoneNo: " + _phoneNo);
        Log.d("UpdateData", "Remark: " + _remark);

        // Set the values to EditTexts
        updateQtrNo.setText(_qtrNo);
        updateEmpName.setText(_employeName);
        updateDesignation.setText(_designation);
        updateNEIS.setText(_neisno);
        updateUnit.setText(_unit);
        updateLegal.setText(_other);
        updatePhoneNo.setText(_phoneNo);
        updateRemark.setText(_remark);
    }

    private void updateData() {
        String newQtrNo = updateQtrNo.getText().toString().trim();
        String newEmployeName = updateEmpName.getText().toString().trim();
        String newDesignation = updateDesignation.getText().toString().trim();
        String newNeisno = updateNEIS.getText().toString().trim();
        String newUnit = updateUnit.getText().toString().trim();
        String newOther = updateLegal.getText().toString().trim();
        String newPhoneNo = updatePhoneNo.getText().toString().trim();
        String newRemark = updateRemark.getText().toString().trim();

        if (newQtrNo.isEmpty()) {
            Toast.makeText(this, "Quarter Number cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> map = new HashMap<>();
        boolean isAnyFieldUpdated = false;

        if (!newEmployeName.equals(_employeName)) {
            map.put("employeName", newEmployeName);
            isAnyFieldUpdated = true;
        }
        if (!newDesignation.equals(_designation)) {
            map.put("designation", newDesignation);
            isAnyFieldUpdated = true;
        }
        if (!newNeisno.equals(_neisno)) {
            map.put("neisno", newNeisno);
            isAnyFieldUpdated = true;
        }
        if (!newUnit.equals(_unit)) {
            map.put("unit", newUnit);
            isAnyFieldUpdated = true;
        }
        if (!newOther.equals(_other)) {
            map.put("other", newOther);
            isAnyFieldUpdated = true;
        }
        if (!newPhoneNo.equals(_phoneNo)) {
            map.put("phoneNo", newPhoneNo);
            isAnyFieldUpdated = true;
        }
        if (!newRemark.equals(_remark)) {
            map.put("remark", newRemark);
            isAnyFieldUpdated = true;
        }

        if (isAnyFieldUpdated) {
            // Update the data using the original quarter number as the key
            databaseReference.child(_qtrNo).updateChildren(map)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(UpdateData.this, "Data updated successfully", Toast.LENGTH_SHORT).show();
                        // Redirect to the Dashboard activity
                        Intent intent = new Intent(UpdateData.this, Dashboard.class);
                        startActivity(intent);
                        finish(); // Close the current activity
                    })
                    .addOnFailureListener(e -> Toast.makeText(UpdateData.this, "Failed to update data: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        } else {
            Toast.makeText(this, "No changes to update", Toast.LENGTH_SHORT).show();
        }
    }
}
