package com.supplytrack.adapter;

/**
 * Created by Mario on 23/08/2016.
 */


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.supplytrack.LoginActivity;
import com.supplytrack.MainActivity;
import com.supplytrack.R;
import com.supplytrack.ViewSupplyActivity;
import com.supplytrack.api.ApiGenerators;
import com.supplytrack.helpers.Utils;
import com.supplytrack.model.Customer;
import com.supplytrack.model.KResponse;
import com.supplytrack.model.Supply;
import com.supplytrack.ui.CustomProgressDialog;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by Kene on 9/11/18.
 */
public class SupplyRecycleAdapter extends RecyclerView.Adapter<SupplyRecycleAdapter.ViewHolder> {

    private List<Supply> mItems, list;
    private Supply item;
    private Context mContext;
    private Customer customer;


    public SupplyRecycleAdapter(Context mContext, List<Supply> items, Customer customer)
    {
        this.mContext = mContext;
        this.mItems = items;
        this.customer = customer;
        this.list = new ArrayList<Supply>();
        this.list.addAll(items);
    }

    @Override
    public SupplyRecycleAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.supply_card, viewGroup, false);

        return new SupplyRecycleAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final SupplyRecycleAdapter.ViewHolder holder, int i) {
        item = mItems.get(i);

        holder.hospital.setText(item.hospital_name);
        holder.department.setText(item.department_name);
        holder.year.setText(item.year);
        holder.month.setText(item.month);
        holder.week.setText(item.week);

        if(item.status.equalsIgnoreCase("0"))
        {
            holder.status.setText("No Items");
            holder.status.setTextColor(mContext.getResources().getColor(R.color.red));
            holder.btnclose.setVisibility(View.GONE);
        }
        else if(item.status.equalsIgnoreCase("1"))
        {
            holder.status.setText("Closed");
            holder.status.setTextColor(mContext.getResources().getColor(R.color.green));
            holder.btnclose.setVisibility(View.GONE);
        }
        else if(item.status.equalsIgnoreCase("2"))
        {
            holder.status.setText("Open");
            holder.status.setTextColor(mContext.getResources().getColor(R.color.yellow));
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {



        public final TextView hospital,department, year, month, week, status;
        public final Button btn;
        private final Button btnclose;
        private AlertDialog myQuittingDialogBox;
        private Dialog dialog;

        public ViewHolder(View view) {
            super(view);

            hospital = (TextView) view.findViewById(R.id.hospital);
            department = (TextView) view.findViewById(R.id.department);
            year = (TextView) view.findViewById(R.id.year);
            month = (TextView) view.findViewById(R.id.month);
            week = (TextView) view.findViewById(R.id.week);
            status = (TextView) view.findViewById(R.id.status);
            btn = (Button)view.findViewById(R.id.btnmore);
            btnclose = (Button)view.findViewById(R.id.btnclose);

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(mContext, ViewSupplyActivity.class);
                    i.putExtra("supply", item);
                    mContext.startActivity(i);
                }
            });

            btnclose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog d = AskOption();
                    d.show();
                }
            });
        }


        private AlertDialog AskOption()
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                myQuittingDialogBox = new AlertDialog.Builder(mContext,R.style.AlertDialogTheme)
                        //set message, title, and icon
                        .setTitle("Supply Track")
                        .setMessage("Do you want to Close supply")
                        .setIcon(R.drawable.ic_shopping_basket)

                        .setPositiveButton("Close Supply", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {


                                dialog.dismiss();
                                closeSupply(customer,item);
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
                        myQuittingDialogBox.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(mContext.getResources().getColor(R.color.black));
                        myQuittingDialogBox.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(mContext.getResources().getColor(R.color.black));
                    }
                });
            }
            else
            {
                myQuittingDialogBox =new AlertDialog.Builder(mContext)
                        //set message, title, and icon
                        .setTitle("Supply Track")
                        .setMessage("Do you want to Close Supply")
                        .setIcon(R.drawable.ic_shopping_basket)

                        .setPositiveButton("Close Supply", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {


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
                        myQuittingDialogBox.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(mContext.getResources().getColor(R.color.black));
                        myQuittingDialogBox.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(mContext.getResources().getColor(R.color.black));
                    }
                });
            }
            return myQuittingDialogBox;

        }

        private void closeSupply(final Customer customer, Supply supply)
        {
            dialog = CustomProgressDialog.getDialog(mContext,"Closing supply...");

            dialog.show();



            ApiGenerators.generateSupplyAPI().closeSupply(customer.id, supply.supply_id).enqueue(new Callback<KResponse>() {
                @Override
                public void onResponse(@NonNull Call<KResponse> call, @NonNull retrofit2.Response<KResponse> response) {
                    KResponse messageBody = response.body();

                    dialog.dismiss();

                    if (messageBody != null)
                    {


                        if(messageBody.status)
                        {

                            Utils.alertView(messageBody.response_message, mContext);
                            btnclose.setVisibility(View.GONE);
                            status.setText("Closed");
                            status.setTextColor(mContext.getResources().getColor(R.color.green));
                            //mContext.startActivity(i);


                        }
                        else
                            Toast.makeText(mContext, messageBody.response_message, Toast.LENGTH_LONG).show();


                    }
                    else
                        Toast.makeText(mContext, "Error: empty response", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(@NonNull Call<KResponse> call, @NonNull Throwable t) {
                    dialog.dismiss();
                    Toast.makeText(mContext, String.valueOf(t.getLocalizedMessage()), Toast.LENGTH_LONG).show();
                }
            });
        }

    }






}