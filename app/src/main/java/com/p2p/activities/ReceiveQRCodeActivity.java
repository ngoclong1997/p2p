package com.p2p.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.p2p.R;
import com.p2p.utils.QRCodeUtils;

import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class ReceiveQRCodeActivity extends AppCompatActivity implements View.OnClickListener {


    TextView codeView;
    ImageView qrCodeImageView;
    String code;
    Bitmap bitmap;
    int iconResouce;
    ImageView copyButtonView;
    ImageView iconView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_qr_code);
        ImageView btnBack = findViewById(R.id.btn_back);
        codeView = findViewById(R.id.qr_code_coin_code);
        qrCodeImageView = findViewById(R.id.qr_code_image);
        iconView = findViewById(R.id.qr_code_coin_icon);
        copyButtonView = findViewById(R.id.qr_code_btn_copy);
        btnBack.setOnClickListener(this);
        code = Objects.requireNonNull(getIntent().getExtras()).getString("code");
        iconResouce = getIntent().getExtras().getInt("icon");
        iconView.setImageResource(iconResouce);
        copyButtonView.setOnClickListener(this);

        codeView.setText(code);
        try {
            bitmap = QRCodeUtils.textToImage(code);
            qrCodeImageView.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.qr_code_btn_copy:
                saveToClipboard(code);
                break;
            case R.id.btn_back:
                finish();
                break;
        }
    }

    private void saveToClipboard(String value) {
        ClipboardManager clipboard = (ClipboardManager) getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("data", value);
        Toast.makeText(getApplicationContext(), "Copied to clipboard", Toast.LENGTH_LONG).show();
        clipboard.setPrimaryClip(clip);
    }

}
