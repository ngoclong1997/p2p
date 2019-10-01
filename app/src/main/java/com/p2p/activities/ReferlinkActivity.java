package com.p2p.activities;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.WriterException;
import com.p2p.R;
import com.p2p.models.User;
import com.p2p.utils.PrefsUtils;
import com.p2p.utils.QRCodeUtils;

public class ReferlinkActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView qrCodeView;
    TextView appInfoVersionView;
    Button copyInvitationCode;
    Button openURLButton;
    TextView myInvitationCode;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_referlink);
        ImageView btnBack = findViewById(R.id.btn_back);
        appInfoVersionView = findViewById(R.id.app_info_version);
        qrCodeView = findViewById(R.id.qr_code_invitation);
        openURLButton = findViewById(R.id.btn_copy_url);
        copyInvitationCode = findViewById(R.id.btn_copy_invitation_code);
        myInvitationCode = findViewById(R.id.my_invitation_code);
        try {
            PackageInfo pInfo = getApplicationContext().getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pInfo.versionName;
            String versionName = getResources().getString(R.string.version) + " " + version;
            appInfoVersionView.setText(versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        user = PrefsUtils.getPreferenceValue(this, PrefsUtils.CURRENT_USER, new User());
        myInvitationCode.setText(user.getLogin());
        try {
            Bitmap bitmap = QRCodeUtils.textToImage(user.getLogin());
            qrCodeView.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        copyInvitationCode.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        openURLButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_copy_invitation_code:
                saveToClipboard(user.getLogin());
                break;
            case R.id.btn_copy_url:
                String url = "https://p2ppitpanex.com/download/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
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
