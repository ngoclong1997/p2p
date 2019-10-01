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

public class ProfitSendingActivity extends AppCompatActivity implements View.OnClickListener {

    RelativeLayout bodyView;
    RelativeLayout selectSendingTypeView;
    int selectedPos = 0;
    TextView walletTypeView;
    ImageView scannerView;
    EditText receiverView;
    EditText amountEditText;
    String[] options = new String[] {"My Bitcoin Wallet", "My ETH Wallet", "My BAT Wallet", "My LTC Wallet", "My USDC Wallet", "My ZRX Wallet", "My ETC Wallet"};
    Button sendButton;
    APIServices apiServices;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profit_sending);
        ImageView btnBack = findViewById(R.id.btn_back);
        bodyView = findViewById(R.id.profit_sending_body);
        walletTypeView = findViewById(R.id.wallet_type_value);
        selectSendingTypeView = findViewById(R.id.profit_sending_type);
        receiverView = findViewById(R.id.profit_sending_receiver_address);
        sendButton = findViewById(R.id.send_profit_button);
        scannerView = findViewById(R.id.profit_sending_receiver_address_qr_scanner);
        amountEditText = findViewById(R.id.profit_sending_amount);
        walletTypeView.setText(options[selectedPos]);
        apiServices = RetrofitClientServices.getAPIServices();
        progressDialog = new ProgressDialog(this);
        bodyView.setOnClickListener(this);
        sendButton.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        selectSendingTypeView.setOnClickListener(this);
        scannerView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.profit_sending_body:
                AppUtils.hideKeyboard(this, v);
                break;
            case R.id.profit_sending_type:

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
            case R.id.profit_sending_receiver_address_qr_scanner:
                IntentIntegrator intentIntegrator = new IntentIntegrator(this);
                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                intentIntegrator.setPrompt("Scan for address");
                intentIntegrator.setCameraId(0);
                intentIntegrator.setBeepEnabled(true);
                intentIntegrator.setBarcodeImageEnabled(false);
                intentIntegrator.initiateScan();
                break;
            case R.id.send_profit_button:

                if (receiverView.getText().toString().equals("")) {
                    Toast.makeText(this, "Please provide receiver address", Toast.LENGTH_LONG).show();
                    break;
                }


                sendProfit();

                break;
        }
    }

    private void sendProfit() {
        progressDialog.setMessage("Sending...");
        progressDialog.show();

        HashMap<String, String> params = new HashMap<>();
        params.put("toWallet", receiverView.getText().toString());
        params.put("option", "3");
        params.put("ppn_amount", String.valueOf(ConversionUtils.stringToInt(amountEditText.getText().toString(), 0)));
        params.put("bat_amount", "0");
        params.put("ltc_amount", "0");
        params.put("btc_amount", "0");
        params.put("eth_amount", "0");
        params.put("usdc_amount", "0");
        params.put("zrx_amount", "0");
        params.put("etc_amount", "0");
        params.put("wallet_type", ConversionUtils.intToString(selectedPos + 1));

        Call<JsonObject> caller = apiServices.sendProfit(APIUtils.getOAuthToken(this), params);

        caller.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    JsonObject jsonObject = response.body();
                    try {
                        assert jsonObject != null;
                        Toast.makeText(ProfitSendingActivity.this, jsonObject.get("message").getAsString(), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(ProfitSendingActivity.this, "Send success", Toast.LENGTH_LONG).show();
                    }
                    amountEditText.setText("");
                    receiverView.setText("");
                    walletTypeView.setText(options[0]);
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
                Toast.makeText(ProfitSendingActivity.this, "Network error!!", Toast.LENGTH_SHORT).show();
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
