package com.p2p.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.p2p.R;
import com.p2p.services.APIServices;
import com.p2p.services.RetrofitClientServices;
import com.p2p.utils.APIUtils;
import com.p2p.utils.AppUtils;
import com.p2p.utils.ConversionUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TotalSendingActivity extends AppCompatActivity implements View.OnClickListener {

    RelativeLayout bodyView;
    RelativeLayout selectSendingTypeView;
    RelativeLayout selectSendingAssetTypeView;
    int selectedPos = 0;
    int selectedPos1 = 0;
    TextView walletTypeView;
    TextView assetTypeView;
    ImageView scannerView;
    EditText receiverView;
    EditText amountEditText;
    String[] options = new String[]{"My Bitcoin Wallet", "My ETH Wallet", "My BAT Wallet", "My LTC Wallet", "My USDC Wallet", "My ZRX Wallet", "My ETC Wallet", "Other PPN User"};
    String[] options1 = new String[]{"BTC Asset", "ETH Asset", "BAT Asset", "LTC Asset", "USDC Asset", "ZRX Asset", "ETC Asset"};
    Button sendButton;
    APIServices apiServices;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_sending);
        ImageView btnBack = findViewById(R.id.btn_back);
        bodyView = findViewById(R.id.total_sending_body);
        walletTypeView = findViewById(R.id.wallet_type_value);
        selectSendingTypeView = findViewById(R.id.total_sending_type);
        selectSendingAssetTypeView = findViewById(R.id.total_sending_asset_type);
        receiverView = findViewById(R.id.total_sending_receiver_address);
        assetTypeView = findViewById(R.id.asset_type_value);
        amountEditText = findViewById(R.id.total_sending_amount);
        scannerView = findViewById(R.id.total_sending_receiver_address_qr_scanner);
        sendButton = findViewById(R.id.send_total_button);
        walletTypeView.setText(options[selectedPos]);
        apiServices = RetrofitClientServices.getAPIServices();
        progressDialog = new ProgressDialog(this);

        assetTypeView.setText(options1[selectedPos1]);
        bodyView.setOnClickListener(this);
        assetTypeView.setOnClickListener(this);
        selectSendingAssetTypeView.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        sendButton.setOnClickListener(this);
        selectSendingTypeView.setOnClickListener(this);
        scannerView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.total_sending_body:
                AppUtils.hideKeyboard(this, v);
                break;
            case R.id.total_sending_type:
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(v.getContext());
                alertDialogBuilder.setTitle("Select sending type");
                alertDialogBuilder.setIcon(R.drawable.ic_bitcoin);
                alertDialogBuilder.setSingleChoiceItems(options, selectedPos, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectedPos = which;
                    }
                });
                alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        walletTypeView.setText(options[selectedPos]);
                        //Toast.makeText(getA, selectedPos, Toast.LENGTH_SHORT).show();
                    }
                });
                alertDialogBuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                break;
            case R.id.total_sending_receiver_address_qr_scanner:
                IntentIntegrator intentIntegrator = new IntentIntegrator(this);
                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                intentIntegrator.setPrompt("Scan for address");
                intentIntegrator.setCameraId(0);
                intentIntegrator.setBeepEnabled(true);
                intentIntegrator.setBarcodeImageEnabled(false);
                intentIntegrator.initiateScan();
                break;
            case R.id.total_sending_asset_type:
                AlertDialog.Builder alertDialogBuilder1 = new AlertDialog.Builder(v.getContext());
                alertDialogBuilder1.setTitle("Choose asset");
                alertDialogBuilder1.setIcon(R.drawable.ic_bitcoin);
                alertDialogBuilder1.setSingleChoiceItems(options1, selectedPos1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectedPos1 = which;
                    }
                });
                alertDialogBuilder1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        assetTypeView.setText(options1[selectedPos1]);
                        //Toast.makeText(getA, selectedPos, Toast.LENGTH_SHORT).show();
                    }
                });
                alertDialogBuilder1.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog1 = alertDialogBuilder1.create();
                alertDialog1.show();
                break;
            case R.id.send_total_button:
                if (receiverView.getText().toString().equals("")) {
                    Toast.makeText(this, "Please provide receiver address", Toast.LENGTH_LONG).show();
                    break;
                }
                sendTotal();
                break;
        }
    }

    private void sendTotal() {
        progressDialog.setMessage("Sending...");
        progressDialog.show();
        HashMap<String, String> params = new HashMap<>();
        params.put("toWallet", receiverView.getText().toString());
        params.put("option", "2");
        String amount = String.valueOf(ConversionUtils.stringToInt(amountEditText.getText().toString(), 0));
        params.put("btc_amount", selectedPos1 == 0 ? amount : "0");
        params.put("eth_amount", selectedPos1 == 1 ? amount : "0");
        params.put("bat_amount", selectedPos1 == 2 ? amount : "0");
        params.put("ltc_amount", selectedPos1 == 3 ? amount : "0");
        params.put("usdc_amount", selectedPos1 == 4 ? amount : "0");
        params.put("zrx_amount", selectedPos1 == 5 ? amount : "0");
        params.put("etc_amount", selectedPos1 == 6 ? amount : "0");
        params.put("ppn_amount", "0");
        params.put("wallet_type", ConversionUtils.intToString(selectedPos + 1));


        Call<JsonObject> caller = apiServices.sendTotal(APIUtils.getOAuthToken(this), params);

        caller.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    JsonObject jsonObject = response.body();
                    try {
                        assert jsonObject != null;
                        Toast.makeText(TotalSendingActivity.this, jsonObject.get("message").getAsString(), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(TotalSendingActivity.this, "Send success", Toast.LENGTH_LONG).show();
                    }
                    amountEditText.setText("");
                    receiverView.setText("");
                    walletTypeView.setText(options[0]);
                    assetTypeView.setText(options1[0]);
                } else {
                    try {
                        Toast.makeText(getApplicationContext(), response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(TotalSendingActivity.this, "Network error!!", Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult != null) {
            if (intentResult.getContents() == null) {
                Toast.makeText(this, "You cancelled the scanning", Toast.LENGTH_SHORT).show();
            } else {
                receiverView.setText(intentResult.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
