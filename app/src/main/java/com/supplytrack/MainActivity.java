package com.supplytrack;

import android.app.Activity;
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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.supplytrack.adapter.SupplyRecycleAdapter;
import com.supplytrack.api.ApiGenerators;
import com.supplytrack.model.Customer;
import com.supplytrack.model.Hospital;
import com.supplytrack.model.Supply;
import com.supplytrack.ui.CustomProgressDialog;

import java.util.ArrayList;
import java.util.List;

import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.SearchResultListener;
import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecycleView;
    private SupplyRecycleAdapter adapter;
    private List<Supply> supplies;
    private ArrayList<Hospital> hospitals;
    private List<Hospital> hospitalList;
    private ShimmerFrameLayout mShimmerViewContainer;
    private Customer customer;
    private Supply supply;
    private AlertDialog myQuittingDialogBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        customer = AppController.getInstance().getCustomer(this);

        mRecycleView = (RecyclerView) findViewById(R.id.recyclerview);

        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        mRecycleView.setNestedScrollingEnabled(false);


        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);

        loadSupplies();

        hospitals = new ArrayList<Hospital>();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadHospitals();
//                final Intent intent = new Intent(MainActivity.this, HospitalListActivity.class);
//
//                startActivityForResult(intent, 1);

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == Activity.RESULT_OK){
            Hospital h = (Hospital)data.getSerializableExtra(HospitalListActivity.RESULT_ID);
            Toast.makeText(this, "You selected countrycode: " + h.getHospital_name(), Toast.LENGTH_LONG).show();
        }
    }


    private void loadSupplies() {


        //Toast.makeText(this,customer.id, Toast.LENGTH_LONG).show();

        ApiGenerators.generateSupplyAPI().listSupplies(customer.id).enqueue(new Callback<List<Supply>>() {
            @Override
            public void onResponse(@NonNull Call<List<Supply>> call, @NonNull retrofit2.Response<List<Supply>> response) {
                try
                {
                    supplies = response.body();
                    if (null!=supplies && supplies.size()>0)
                    {



                        adapter = new SupplyRecycleAdapter( MainActivity.this, response.body(), customer);

                        mRecycleView.setAdapter(adapter);



                        mShimmerViewContainer.stopShimmerAnimation();
                        mShimmerViewContainer.setVisibility(View.GONE);
                    }
                    else
                    {
//                    binding.content.emptyList.getRoot().setVisibility(View.VISIBLE);
                        Toast.makeText(MainActivity.this,"Nt found",Toast.LENGTH_LONG).show();
                    }

                } catch(Exception ex)
                {
                    ex.printStackTrace();
                    Toast.makeText(MainActivity.this,ex.toString(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Supply>> call, @NonNull Throwable t) {
                t.printStackTrace();
                Toast.makeText(MainActivity.this,t.toString(),Toast.LENGTH_LONG).show();
//                binding.content.swipeRefreshLayout.setRefreshing(false);
//                binding.content.tryAgain.getRoot().setVisibility(View.VISIBLE);
//                binding.content.emptyList.getRoot().setVisibility(View.INVISIBLE);
            }
        });
    }

    private void loadHospitals() {


        //Toast.makeText(this,customer.id, Toast.LENGTH_LONG).show();
        final Dialog loading_hospitals = CustomProgressDialog.getDialog(this, "Loading Hospitals");

        loading_hospitals.show();

        ApiGenerators.generateSupplyAPI().listHospitals(customer.id).enqueue(new Callback<List<Hospital>>() {
            @Override
            public void onResponse(@NonNull Call<List<Hospital>> call, @NonNull retrofit2.Response<List<Hospital>> response) {
                try
                {
                    loading_hospitals.dismiss();

                    hospitalList = response.body();
                    if (null!=hospitalList && hospitalList.size()>0)
                    {

                        for(Hospital hospital: hospitalList)
                        {
                            Hospital h = new Hospital(hospital.getHospital_name());
                            h.setHospital_name(hospital.getHospital_name());
                            h.setId(hospital.getId());
                            hospitals.add(h);
                        }


                        new SimpleSearchDialogCompat(MainActivity.this, "Search...",
                                "Search for a hospital...?", null, hospitals,
                                new SearchResultListener<Hospital>() {
                                    @Override
                                    public void onSelected(BaseSearchDialogCompat dialog,
                                                           Hospital item, int position) {
//                                        Toast.makeText(MainActivity.this, item.getHospital_name(),
//                                                Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(MainActivity.this,CreateSupplyActivity.class);
                                        i.putExtra("hospital", item);
                                        i.putExtra("customer", customer);
                                        startActivity(i);
                                        dialog.dismiss();
                                    }
                                }).show();
                    }
                    else
                    {
//                    binding.content.emptyList.getRoot().setVisibility(View.VISIBLE);
                        Toast.makeText(MainActivity.this,"Nt found",Toast.LENGTH_LONG).show();
                    }

                } catch(Exception ex)
                {
                    ex.printStackTrace();
                    Toast.makeText(MainActivity.this,ex.getMessage(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Hospital>> call, @NonNull Throwable t) {
                t.printStackTrace();
                Toast.makeText(MainActivity.this,t.toString(),Toast.LENGTH_LONG).show();
//                binding.content.swipeRefreshLayout.setRefreshing(false);
//                binding.content.tryAgain.getRoot().setVisibility(View.VISIBLE);
//                binding.content.emptyList.getRoot().setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            AlertDialog d = AskOption();
            d.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private AlertDialog AskOption()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            myQuittingDialogBox = new AlertDialog.Builder(this,R.style.AlertDialogTheme)
                    //set message, title, and icon
                    .setTitle("Logout")
                    .setMessage("Do you want to Logout")
                    .setIcon(R.drawable.ic_shopping_basket)

                    .setPositiveButton("Logout", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {

                            AppController.getInstance().logout(MainActivity.this);
                            //finish(); // close this activity and return to preview activity (if there is any)
                            dialog.dismiss();
                            finishAffinity();
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
                    .setTitle("Logout")
                    .setMessage("Do you want to Logout")
                    .setIcon(R.drawable.ic_shopping_basket)

                    .setPositiveButton("Logout", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {

                            AppController.getInstance().logout(MainActivity.this);
                            //finish(); // close this activity and return to preview activity (if there is any)
                            finishAffinity();
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


}
