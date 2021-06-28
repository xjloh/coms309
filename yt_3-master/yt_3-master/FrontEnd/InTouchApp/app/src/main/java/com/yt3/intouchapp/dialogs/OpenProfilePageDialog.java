package com.yt3.intouchapp.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.yt3.intouchapp.R;

public class OpenProfilePageDialog extends AppCompatDialogFragment {
    private TextView dialogName;
    private TextView dialogEmail;
    private String dName;

    public OpenProfilePageDialog(String name){
        dName=name;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder profileBuilder=new AlertDialog.Builder(getActivity());
        LayoutInflater profileLayout=getActivity().getLayoutInflater();
        View pview=profileLayout.inflate(R.layout.dialog_display_profile, null);
        dialogName=pview.findViewById(R.id.dialog_profile_name);
        dialogName.setText(dName);
        profileBuilder.setView(pview)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Add friend", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        return profileBuilder.create();
    }
}
