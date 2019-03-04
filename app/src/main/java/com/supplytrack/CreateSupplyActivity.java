package com.supplytrack;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.supplytrack.api.ApiGenerators;
import com.supplytrack.helpers.Utils;
import com.supplytrack.model.Customer;
import com.supplytrack.model.Department;
import com.supplytrack.model.Hospital;
import com.supplytrack.model.KResponse;
import com.supplytrack.ui.CustomProgressDialog;

import java.util.List;

import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.SearchResultListener;
import retrofit2.Call;
import retrofit2.Callback;

public class CreateSupplyActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private View mProgressView;
    private View mCreateSupplyFormView;
    private Hospital hospital;
    private Customer customer;
    private List<Department> departmentList;
    private String[] hospitals;
    private Spinner spin;
    private TextView txt;
    private int selectedIndex;
    private EditText week,month, year ,txtitem1,txtitem2,txtitem3,txtitem4, txtitem5,txtitem6,txtitem7,txtitem8;
    private Department department;
    private Dialog dialog;
    private Button btn;
    private AlertDialog myQuittingDialogBox;
    private AlertDialog d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_supply);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        customer = AppController.getInstance().getCustomer(this);

        if(savedInstanceState == null)
        {
            hospital = (Hospital)getIntent().getSerializableExtra("hospital");
        }
        else
        {
            hospital = (Hospital) savedInstanceState.getSerializable(Constants.HOSPITAL_OBJ);
        }


        btn = findViewById(R.id.create_supply_btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d = AskOption();

                d.show();
            }
        });

        spin = (Spinner) findViewById(R.id.spinner1);

        txt = (TextView)findViewById(R.id.hospital);

        week = findViewById(R.id.week);

        month = findViewById(R.id.month);

        year = findViewById(R.id.year);

        txtitem1 = findViewById(R.id.item1);
        txtitem2 = findViewById(R.id.item2);
        txtitem3 = findViewById(R.id.item3);
        txtitem4 = findViewById(R.id.item4);
        txtitem5 = findViewById(R.id.item5);
        txtitem6 = findViewById(R.id.item6);
        txtitem7 = findViewById(R.id.item7);
        txtitem8 = findViewById(R.id.item8);

        txt.setText(hospital.getHospital_name());

        spin.setPrompt("Select Department");

        spin.setOnItemSelectedListener(this);

        mCreateSupplyFormView = findViewById(R.id.create_supply_form);
        mProgressView = findViewById(R.id.load_progress);

        loadDepartments();
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mCreateSupplyFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mCreateSupplyFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mCreateSupplyFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mCreateSupplyFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    private void loadDepartments() {



        showProgress(true);

        ApiGenerators.generateSupplyAPI().listDepartments(hospital.getId()).enqueue(new Callback<List<Department>>() {
            @Override
            public void onResponse(@NonNull Call<List<Department>> call, @NonNull retrofit2.Response<List<Department>> response) {
                try
                {
                    showProgress(false);

                    int index = 0;

                    departmentList = response.body();
                    if (null!=departmentList && departmentList.size()>0)
                    {
                        hospitals = new String[departmentList.size()];

                        for(Department department: departmentList)
                        {
                            hospitals[index] = department.department_name;
                            ++index;

                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(CreateSupplyActivity.this, android.R.layout.simple_spinner_item, hospitals);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spin.setAdapter(adapter);

                        }
                        //Populate List
                    }
                    else
                    {
//                    binding.content.emptyList.getRoot().setVisibility(View.VISIBLE);
                        Toast.makeText(CreateSupplyActivity.this,"Nt found",Toast.LENGTH_LONG).show();
                    }

                } catch(Exception ex)
                {
                    ex.printStackTrace();
                    Toast.makeText(CreateSupplyActivity.this,ex.getMessage(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Department>> call, @NonNull Throwable t) {
                t.printStackTrace();
                Toast.makeText(CreateSupplyActivity.this,t.toString(),Toast.LENGTH_LONG).show();
//                binding.content.swipeRefreshLayout.setRefreshing(false);
//                binding.content.tryAgain.getRoot().setVisibility(View.VISIBLE);
//                binding.content.emptyList.getRoot().setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position,long id) {
        selectedIndex = position;
    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO - Custom Code
    }

    private void createSupply() {

        // Reset errors.
        week.setError(null);
        month.setError(null);
        year.setError(null);

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
        if (TextUtils.isEmpty(week.getText().toString())) {
            week.setError("Enter valid week");
            focusView = week;
            cancel = true;
        }

        // Check for a valid week.
        if (TextUtils.isEmpty(month.getText().toString())) {
            month.setError("Enter valid month");
            focusView = month;
            cancel = true;
        }

        // Check for a valid week.
        if (TextUtils.isEmpty(year.getText().toString())) {
            year.setError("Enter valid year");
            focusView = year;
            cancel = true;
        }

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
            dialog = CustomProgressDialog.getDialog(CreateSupplyActivity.this,"Creating supply...");

            dialog.show();

            department = departmentList.get(selectedIndex);

            ApiGenerators.generateSupplyAPI().createNewSupply(customer.id, hospital.getId(), department.id, week.getText().toString(),month.getText().toString(), year.getText().toString(),
                    txtitem1.getText().toString(),txtitem2.getText().toString(), txtitem3.getText().toString(),
                    txtitem4.getText().toString(), txtitem5.getText().toString(), txtitem6.getText().toString(),
                    txtitem7.getText().toString(), txtitem8.getText().toString()).enqueue(new Callback<KResponse>() {
                @Override
                public void onResponse(@NonNull Call<KResponse> call, @NonNull retrofit2.Response<KResponse> response) {
                    KResponse messageBody = response.body();

                    dialog.dismiss();


                    if (messageBody != null)
                    {


                        if(messageBody.status)
                        {

                            Utils.alertView(messageBody.response_message, CreateSupplyActivity.this);
//                            Intent i = new Intent(CreateSupplyActivity.this, MainActivity.class);
//                            i.putExtra("customer", customer);
//                            startActivity(i);
//                            finish();

                        }
                        else
                            Toast.makeText(CreateSupplyActivity.this, messageBody.response_message, Toast.LENGTH_LONG).show();


                    }
                    else
                        Toast.makeText(CreateSupplyActivity.this, "Error: empty response", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(@NonNull Call<KResponse> call, @NonNull Throwable t) {
                    dialog.dismiss();
                    Toast.makeText(CreateSupplyActivity.this, String.valueOf(t.getLocalizedMessage()), Toast.LENGTH_LONG).show();
                }
            });

        }
    }

    private AlertDialog AskOption()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            myQuittingDialogBox = new AlertDialog.Builder(this,R.style.AlertDialogTheme)
                    //set message, title, and icon
                    .setTitle("Create Supply")
                    .setMessage("Do you want to Create new Supply?")
                    .setIcon(R.drawable.ic_shopping_basket)

                    .setPositiveButton("Create Supply", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {

                            dialog.dismiss();
                            createSupply();
                            //System.exit(0);

                        }

                    })


                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();

                        }
                    })
                    .create();

            myQuittingDialogBox.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialogInterface) {
                    myQuittingDialogBox.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.black));
                    myQuittingDialogBox.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.black));
                }
            });
        }
        else
        {
            myQuittingDialogBox =new AlertDialog.Builder(this)
                    //set message, title, and icon
                    .setTitle("Create Supply")
                    .setMessage("Do you want to Create new Supply")
                    .setIcon(R.drawable.ic_shopping_basket)

                    .setPositiveButton("Create Supply", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {

                            createSupply();
                            dialog.dismiss();
                        }

                    })



                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();

                        }
                    })
                    .create();

            myQuittingDialogBox.setOnShowListener(new DialogInterface.OnShowListener(){
                @Override
                public void onShow(DialogInterface dialogInterface){
                    myQuittingDialogBox.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.black));
                    myQuittingDialogBox.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.black));
                }
            });
        }
        return myQuittingDialogBox;

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state
        savedInstanceState.putSerializable(Constants.HOSPITAL_OBJ, hospital);
        super.onSaveInstanceState(savedInstanceState);
    }

}
