package com.p2p.fragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.p2p.R;
import com.p2p.activities.AboutUsActivity;
import com.p2p.activities.CommunityActivity;
import com.p2p.activities.ContactUsActivity;
import com.p2p.activities.HistoryTransactionActivity;
import com.p2p.activities.LoginActivity;
import com.p2p.activities.MessageActivity;
import com.p2p.activities.ReferlinkActivity;
import com.p2p.activities.SettingsActivity;
import com.p2p.adapters.OptionAdapter;
import com.p2p.models.Options;
import com.p2p.models.User;
import com.p2p.services.APIServices;
import com.p2p.services.RetrofitClientServices;
import com.p2p.utils.APIUtils;
import com.p2p.utils.PrefsUtils;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    APIServices apiServices;
    ProgressDialog progressDialog;
    TextView myNameView;
    TextView myPhoneView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        ListView listViewOptions = rootView.findViewById(R.id.mine_options);
        ArrayList<Options> options = new ArrayList<>();
        options.add(new Options(R.drawable.ic_qrcode, "Referlink"));
        options.add(new Options(R.drawable.ic_user_settings, "Account Settings"));
        options.add(new Options(R.drawable.ic_community, "Community"));
        options.add(new Options(R.drawable.ic_notifications_active_black_24dp, "Message"));
        options.add(new Options(R.drawable.ic_history_black_24dp, "History Transaction"));
        options.add(new Options(R.drawable.ic_call_black_24dp, "Contact Us"));
        options.add(new Options(R.drawable.ic_info_black_24dp, "About Us"));
        options.add(new Options(R.drawable.ic_call_missed_outgoing_black_24dp, "Sign out"));
        OptionAdapter adapter = new OptionAdapter(getActivity(), R.layout.row_options_listview, options);
        listViewOptions.setAdapter(adapter);
        progressDialog = new ProgressDialog(getContext());
        apiServices = RetrofitClientServices.getAPIServices();
        myNameView = rootView.findViewById(R.id.mine_name);
        myPhoneView = rootView.findViewById(R.id.mine_phone);
        User user = PrefsUtils.getPreferenceValue(getActivity(), PrefsUtils.CURRENT_USER, new User());
        myPhoneView.setText(user.getPhoneNumber());
        myNameView.setText(user.getFullName());
        listViewOptions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myIntent;
                switch (position) {
                    case 0:
                        myIntent = new Intent(view.getContext(), ReferlinkActivity.class);
                        startActivity(myIntent);
                        break;
                    case 1:
                        myIntent = new Intent(view.getContext(), SettingsActivity.class);
                        startActivity(myIntent);
                        break;
                    case 2:
                        myIntent = new Intent(view.getContext(), CommunityActivity.class);
                        startActivity(myIntent);
                        break;
                    case 3:
                        myIntent = new Intent(view.getContext(), MessageActivity.class);
                        startActivity(myIntent);
                        break;
                    case 4:
                        myIntent = new Intent(view.getContext(), HistoryTransactionActivity.class);
                        startActivity(myIntent);
                        break;
                    case 5:
                        myIntent = new Intent(view.getContext(), ContactUsActivity.class);
                        startActivity(myIntent);
                        break;
                    case 6:
                        myIntent = new Intent(view.getContext(), AboutUsActivity.class);
                        startActivity(myIntent);
                        break;
                    case 7:
                        AlertDialog.Builder adb = new AlertDialog.Builder(view.getContext());
                        adb.setTitle("Are you sure want to sign out?");
                        adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                logout();


                            }
                        });
                        adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        adb.show();
                        break;
                }
            }
        });

        return rootView;
    }

    private void logout() {
        progressDialog.setMessage("Signing out...");
        progressDialog.show();
        Call<Void> caller = apiServices.revokeToken(PrefsUtils.getPreferenceValue(getContext(), PrefsUtils.ACCESS_TOKEN, ""));
        caller.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    PrefsUtils.removePreferenceValue(getContext(), PrefsUtils.ACCESS_TOKEN);
                    Intent i = new Intent(getContext(), LoginActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                } else {
                    try {
                        Toast.makeText(getContext(), response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), "Network error!!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
