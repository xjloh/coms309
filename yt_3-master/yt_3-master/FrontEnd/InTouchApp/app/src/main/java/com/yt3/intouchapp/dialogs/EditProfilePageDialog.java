package com.yt3.intouchapp.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.yt3.intouchapp.LocalStorage;
import com.yt3.intouchapp.R;
import com.yt3.intouchapp.net_utils.AppController;
import com.yt3.intouchapp.net_utils.Const;
import com.yt3.intouchapp.utils.UserInfo;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EditProfilePageDialog extends AppCompatDialogFragment {

    private EditText editTextName;
    private EditText editTextEmail;
    private EditText editTextBio;
    private ProfilePageDialogListener listener;
    private UserInfo userInfo;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder profilePageBuilder= new AlertDialog.Builder(getActivity());
        LayoutInflater inflater= getActivity().getLayoutInflater();
        View editLayout = inflater.inflate(R.layout.edit_profile_page_dialog, null);
        profilePageBuilder.setView(editLayout)
                .setTitle("Edit")
                .setNegativeButton("Don't Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Save Changes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newName=editTextName.getText().toString();
                        String newEmail=editTextEmail.getText().toString();
                        String newBio=editTextBio.getText().toString();
                        listener.transferInfo(newName, newEmail, newBio);
                        if(newName.isEmpty()){
                            newName = userInfo.getUsername();
                        }
                        if(newEmail.isEmpty()) {
                            newEmail = userInfo.getEmail();
                        }
                        updateDB(newName,newEmail,newBio);
                        listener.transferInfo(newName, newEmail, newBio);
                    }
                });
        editTextName=editLayout.findViewById(R.id.dialog_edit_name);
        editTextEmail=editLayout.findViewById(R.id.dialog_edit_email);
        editTextBio=editLayout.findViewById(R.id.dialog_edit_bio);

        return profilePageBuilder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener=(ProfilePageDialogListener) context;
        } catch (Exception e) {
            throw new ClassCastException(context.toString() + "must implement ProfilePageDialogListener");
        }
    }

    public interface ProfilePageDialogListener{
        void transferInfo(String name, String email, String bio);
    }
    public void updateDB(String name, String email,String bio){
        final JSONObject jsonBody = new JSONObject();
        try {
            // user_id, comment_id,status
            jsonBody.put("username", name);
            jsonBody.put("email",email);
            jsonBody.put("bio",bio);
            jsonBody.put("uid",userInfo.getUid());
        } catch (Exception e) {
            e.printStackTrace();
        }
        String url = Const.URL_UPDATE_USERINFO;
        StringRequest strRequest = new StringRequest(Request.Method.POST, Const.URL_UPDATE_USERINFO, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                // Parse success
                //Toast.makeText(activity.getApplicationContext(), "Thread submitted!!", Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                NetworkResponse networkResponse = error.networkResponse;
                String errorMessage;

                if (networkResponse.statusCode == 404)
                {
                    errorMessage = "404 Not Found";
                }
                else if (networkResponse.statusCode == 511)
                {
                    errorMessage = "511 Network Authentication Needed";
                }
                else if (networkResponse.statusCode == 502)
                {
                    errorMessage = "502 Bad Gateway";
                }
                else
                {
                    errorMessage = "Undefined Error";
                }
                Log.i("Volley Error", errorMessage);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                final Map<String, String> headers = new HashMap<>();

                // Add session key if exist
                String sessionKey = LocalStorage.getSessionKey(getActivity().getApplicationContext());
                if (!sessionKey.isEmpty())
                {
                    headers.put("Authorization", sessionKey);
                }

                return headers;
            }

            @Override
            public byte[] getBody() throws AuthFailureError
            {
                return jsonBody.toString().getBytes();
            }
            @Override
            public String getBodyContentType()
            {
                return "application/json";
            }
        };
        AppController.getInstance().addToRequestQueue(strRequest);

    }


    public void setUserInfo(UserInfo u){
        userInfo = u;
    }
}
