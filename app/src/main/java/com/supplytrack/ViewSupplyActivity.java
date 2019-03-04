package com.supplytrack;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.supplytrack.api.ApiGenerators;
import com.supplytrack.helpers.Utils;
import com.supplytrack.model.Customer;
import com.supplytrack.model.Hospital;
import com.supplytrack.model.KResponse;
import com.supplytrack.model.Supply;
import com.supplytrack.ui.CustomProgressDialog;

import retrofit2.Call;
import retrofit2.Callback;

public class ViewSupplyActivity extends AppCompatActivity {

    private Supply supply;
    private TextView txthospital, txtdepartment;
    private EditText txtitem1,txtitem2,txtitem3,txtitem4, txtitem5,txtitem6,txtitem7,txtitem8;
    private TextView ttxtitem1,ttxtitem2,ttxtitem3,ttxtitem4, ttxtitem5,ttxtitem6,ttxtitem7,ttxtitem8;
    private Dialog dialog;
    private Customer customer;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState == null)
        {
            supply = (Supply) getIntent().getSerializableExtra("supply");
        }
        else
        {
            supply = (Supply) savedInstanceState.getSerializable(Constants.SUPPLY_OBJ);
        }

        if(supply.status.equalsIgnoreCase("1"))
        {
            setContentView(R.layout.activity_view_supplier);
            ttxtitem1 = findViewById(R.id.item1);
            ttxtitem2 = findViewById(R.id.item2);
            ttxtitem3 = findViewById(R.id.item3);
            ttxtitem4 = findViewById(R.id.item4);
            ttxtitem5 = findViewById(R.id.item5);
            ttxtitem6 = findViewById(R.id.item6);
            ttxtitem7 = findViewById(R.id.item7);
            ttxtitem8 = findViewById(R.id.item8);

            txthospital = findViewById(R.id.hospital);
            txtdepartment = findViewById(R.id.department);

            populateItem();
        }
        else
        {
            setContentView(R.layout.activity_view_supply);
            txtitem1 = findViewById(R.id.item1);
            txtitem2 = findViewById(R.id.item2);
            txtitem3 = findViewById(R.id.item3);
            txtitem4 = findViewById(R.id.item4);
            txtitem5 = findViewById(R.id.item5);
            txtitem6 = findViewById(R.id.item6);
            txtitem7 = findViewById(R.id.item7);
            txtitem8 = findViewById(R.id.item8);

            txthospital = findViewById(R.id.hospital);
            txtdepartment = findViewById(R.id.department);

            populateItems();



            btn = findViewById(R.id.update_supply_btn);

            btn.setVisibility(View.VISIBLE);


            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    updateSupplyItems();
                }
            });
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);






        customer = AppController.getInstance().getCustomer(this);

        if(savedInstanceState == null)
        {
            supply = (Supply) getIntent().getSerializableExtra("supply");
        }
        else
        {
            supply = (Supply) savedInstanceState.getSerializable(Constants.SUPPLY_OBJ);
        }








    }

    private void populateItems()
    {
        txthospital.setText(supply.hospital_name);
        txtdepartment.setText(supply.department_name);

        txtitem1.setText(supply.item1);
        txtitem2.setText(supply.item2);
        txtitem3.setText(supply.item3);
        txtitem4.setText(supply.item4);
        txtitem5.setText(supply.item5);
        txtitem6.setText(supply.item6);
        txtitem7.setText(supply.item7);
        txtitem8.setText(supply.item8);


//        if(supply.status.equalsIgnoreCase("1"))
//        {
//            btn.setVisibility(View.GONE);
//        }
    }

    private void populateItem()
    {
        txthospital.setText(supply.hospital_name);
        txtdepartment.setText(supply.department_name);

        ttxtitem1.setText(supply.item1);
        ttxtitem2.setText(supply.item2);
        ttxtitem3.setText(supply.item3);
        ttxtitem4.setText(supply.item4);
        ttxtitem5.setText(supply.item5);
        ttxtitem6.setText(supply.item6);
        ttxtitem7.setText(supply.item7);
        ttxtitem8.setText(supply.item8);


//        if(supply.status.equalsIgnoreCase("1"))
//        {
//            btn.setVisibility(View.GONE);
//        }
    }

    private void updateSupplyItems() {

        // Reset errors.
        txtitem1.setError(null);
        txtitem2.setError(null);
        txtitem3.setError(null);
        txtitem4.setError(null);
        txtitem5.setError(null);
        txtitem6.setError(null);
        txtitem7.setError(null);
        txtitem8.setError(null);


        boolean cancel = false;
        View focusView = null;

        // Check for a valid week.
        if (TextUtils.isEmpty(txtitem1.getText().toString())) {
            txtitem1.setError("Enter Item 1");
            focusView = txtitem1;
            cancel = true;
        }

        if (TextUtils.isEmpty(txtitem2.getText().toString())) {
            txtitem2.setError("Enter Item 2");
            focusView = txtitem2;
            cancel = true;
        }

        if (TextUtils.isEmpty(txtitem3.getText().toString())) {
            txtitem3.setError("Enter Item 3");
            focusView = txtitem3;
            cancel = true;
        }

        if (TextUtils.isEmpty(txtitem4.getText().toString())) {
            txtitem4.setError("Enter Item 4");
            focusView = txtitem4;
            cancel = true;
        }

        if (TextUtils.isEmpty(txtitem5.getText().toString())) {
            txtitem5.setError("Enter Item 5");
            focusView = txtitem5;
            cancel = true;
        }

        if (TextUtils.isEmpty(txtitem6.getText().toString())) {
            txtitem6.setError("Enter Item 6");
            focusView = txtitem6;
            cancel = true;
        }

        if (TextUtils.isEmpty(txtitem7.getText().toString())) {
            txtitem7.setError("Enter Item 7");
            focusView = txtitem7;
            cancel = true;
        }

        if (TextUtils.isEmpty(txtitem8.getText().toString())) {
            txtitem8.setError("Enter Item 8");
            focusView = txtitem8;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            dialog = CustomProgressDialog.getDialog(ViewSupplyActivity.this,"Updating supply items...");

            dialog.show();



            ApiGenerators.generateSupplyAPI().updateSupplyItems(customer.id, supply.supply_id, txtitem1.getText().toString(),txtitem2.getText().toString(), txtitem3.getText().toString(),
                    txtitem4.getText().toString(), txtitem5.getText().toString(), txtitem6.getText().toString()
                    , txtitem7.getText().toString(), txtitem8.getText().toString()).enqueue(new Callback<KResponse>() {
                @Override
                public void onResponse(@NonNull Call<KResponse> call, @NonNull retrofit2.Response<KResponse> response) {
                    KResponse messageBody = response.body();

                    dialog.dismiss();

                    if (messageBody != null)
                    {


                        if(messageBody.status)
                        {

                            Utils.alertView(messageBody.response_message, ViewSupplyActivity.this);
//                            Intent i = new Intent(ViewSupplyActivity.this, MainActivity.class);
//                            i.putExtra("customer", customer);
//                            startActivity(i);
//                            finish();

                        }
                        else
                            Toast.makeText(ViewSupplyActivity.this, messageBody.response_message, Toast.LENGTH_LONG).show();


                    }
                    else
                        Toast.makeText(ViewSupplyActivity.this, "Error: empty response", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(@NonNull Call<KResponse> call, @NonNull Throwable t) {
                    dialog.dismiss();
                    Toast.makeText(ViewSupplyActivity.this, String.valueOf(t.getLocalizedMessage()), Toast.LENGTH_LONG).show();
                }
            });

        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game stat
        savedInstanceState.putSerializable(Constants.SUPPLY_OBJ, supply);
        super.onSaveInstanceState(savedInstanceState);
    }

}
