package com.example.lenovo.smsgo;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.smsgo.Interfaces.ApiService;
import com.example.lenovo.smsgo.adapters.AdapterListUsers;
import com.example.lenovo.smsgo.models.ModelSms;
import com.example.lenovo.smsgo.utilities.ApiRetrofitUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    private int PERMISSIONS_REQUEST_RECEIVE_SMS = 130;
    private Button sendSMS;
    private ApiService mApiService;
    private List<ModelSms> modelSmses;
    private ProgressBar mProgressBar, mProgressCircle;
    private String number, message,email,mAll;
    private RecyclerView mUsersRecyclerView;
    private AdapterListUsers mAdapterListUsers;
    private ProgressDialog progress;
    private LinearLayoutManager lnH;
    private List<ModelSms> modelSMS;
    private TextView mTxtUsers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sendSMS = (Button)findViewById(R.id.sendSMS);
        mApiService = ApiRetrofitUtils.getApiService();
        mUsersRecyclerView = (RecyclerView)findViewById(R.id.containerUsers);
        mProgressBar = (ProgressBar)findViewById(R.id.progressBar);
        mProgressCircle = (ProgressBar)findViewById(R.id.progressBar4);
        lnH = new LinearLayoutManager(MainActivity.this);
        mTxtUsers = (TextView)findViewById(R.id.txtUsers);
        //lnH1 = new LinearLayoutManager(getActivity());
        lnH.setOrientation(LinearLayoutManager.VERTICAL);
        //lnH1.setOrientation(LinearLayoutManager.HORIZONTAL);
        setRequestListUsers();

        mUsersRecyclerView.setHasFixedSize(true);
        mUsersRecyclerView.setLayoutManager(lnH);


        mPermision();
        sendSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            int i = 0;
                            for (ModelSms model: modelSMS){
                                sendSMS(model.getNumber(),model.getMessage());
                                modelSmses.get(i).setMessage_sent(true);
                                UpdateMessageSent(model);
                                i++;
                                mProgressBar.setProgress(i);
                                mTxtUsers.setText(String.valueOf(i)+"/"+mAll);
                                Thread.sleep(5000);
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (Exception e){
                            Log.d("ok",e.getMessage());
                        }
                    }
                }).run();

                /*try {
                    final ModelSms modelSms1 = new ModelSms();
                    final ModelSms modelSms2 = new ModelSms();
                    final ModelSms modelSms3 = new ModelSms();
                    final ModelSms modelSms4 = new ModelSms();
                    modelSms1.setNumber("748512452");
                    modelSms1.setMessage("asadasdasda");
                    modelSms2.setNumber("946131543");
                    modelSms2.setMessage("Sdfsdfssdfbdsfb");
                    modelSms3.setNumber("748512452");
                    modelSms3.setMessage("asadasdasda");
                    modelSms4.setNumber("946131543");
                    modelSms4.setMessage("Sdfsdfssdfbdsfb");


                    List<ModelSms> smses = new ArrayList<ModelSms>();
                    smses.add(modelSms1);
                    smses.add(modelSms2);
                    smses.add(modelSms3);
                    smses.add(modelSms4);

                    mAdapterListUsers = new AdapterListUsers(MainActivity.this,smses);
                    mUsersRecyclerView.setAdapter(mAdapterListUsers);
                }catch (Exception e){
                    e.getMessage();
                }*/

                /*progress = ProgressDialog.show(MainActivity.this, "Enviando",
                        "Enviando..", true);
                //sendSMS("927737307","holas");
                try {
                    setRequest();
                }catch (Exception e){
                    e.getMessage();
                }*/
                //sendSMS("927737307","Hola Hiro Palacios, te saluda el equipo JOINNUS, el pago por tu entrada al evento Phoenix por primera vez en Lima por el monto de S/.240.5.00 no ha sido debitado de tu tarjeta. Regulariza el pago antes del evento para que puedas ingresar sin inconvenientes. Para mayor información comunícate con nosotros por el Facebook de Joinnus o a contacto@joinnus.com");
            }
        });

    }

    public void UpdateMessageSent(ModelSms mSms){
        mApiService.setEmail(mSms.getEmail()).enqueue(new Callback<ModelSms>() {
            @Override
            public void onResponse(Call<ModelSms> call, Response<ModelSms> response) {
                if (response.isSuccessful()){
                    if (response.body().getMessage_sent()) {
                        try {
                            mAdapterListUsers.setLsModelSms(modelSMS);
                            mAdapterListUsers.notifyDataSetChanged();
                        }catch (Exception e){
                            e.getMessage();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ModelSms> call, Throwable t) {
                Log.d("erro", t.getMessage());
                Toast.makeText(getApplication(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    public void sendSMS(String phoneNumber, String message) {
        try {
            /*Intent intent=new Intent(getApplicationContext(),MainActivity.class);
            PendingIntent pi=PendingIntent.getActivity(getApplicationContext(), 0, intent,0);*/
            SmsManager sms = SmsManager.getDefault();
            ArrayList<String> parts = sms.divideMessage(message);
            sms.sendMultipartTextMessage(phoneNumber, null, parts, null, null);
        }catch (Exception e){
            e.getMessage();
        }

    }

    private void setRequestListUsers(){
        mApiService.setGet("customers").enqueue(new Callback<List<ModelSms>>() {
            @Override
            public void onResponse(Call<List<ModelSms>> call, Response<List<ModelSms>> response) {
                if (response.isSuccessful()){
                    try {

                        modelSmses =  response.body();
                        modelSMS = response.body();
                        mAdapterListUsers = new AdapterListUsers(MainActivity.this,modelSmses);
                        mUsersRecyclerView.setAdapter(mAdapterListUsers);
                        mProgressCircle.setVisibility(View.GONE);
                        mAll = String.valueOf(modelSMS.size());
                        mTxtUsers.setText("0/"+mAll);
                        mProgressBar.setMax(modelSMS.size());
                        mProgressBar.setProgress(0);

                    }catch (Exception e){
                        e.getMessage();
                    }


                    /*Txtresult.setText(modelSmses.toString());*/
                    /*for (ModelSms modelSm :modelSmses){
                        number = modelSm.getNumber();
                        message = modelSm.getMessage();
                        //email = modelSm.getEmail();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(1000);
                                    sendSMS(number,message);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).run();
                    }
                    progress.dismiss();*/
                }
            }

            @Override
            public void onFailure(Call<List<ModelSms>> call, Throwable t) {
                Toast.makeText(getApplication(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void mPermision(){
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.SEND_SMS)!= PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            }else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        PERMISSIONS_REQUEST_RECEIVE_SMS);
                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

    }
}
