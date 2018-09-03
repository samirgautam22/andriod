package aayush.com.collegeproject.Fragment;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import aayush.com.collegeproject.Activities.MainActivity;
import aayush.com.collegeproject.Dtos.LoginDto;
import aayush.com.collegeproject.Model.LoginModel;
import aayush.com.collegeproject.Presenter.LoginPresenter;
import aayush.com.collegeproject.R;
import aayush.com.collegeproject.View.LoginView;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements View.OnClickListener, LoginView {
    private Button btn_login;
    private EditText username, password;
    private LoginPresenter mLoginPresenter;
    private TextView forgetPassword,link_singup;


    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_login, container, false);
        defineView(view);
        if(isAlreadyLoggedIn()){
            Intent intent=new Intent(getContext(),MainActivity.class);
            startActivity(intent);
        }
        mLoginPresenter = new LoginModel(this);
        btn_login.setOnClickListener(this);
        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askForRegisterEmail();
            }
        });
        return view;
    }

    private void askForRegisterEmail() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle("Registered Email");

        final EditText input = new EditText(getActivity());
        input.setHint("registered@email.com");

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);
        alertDialog.setIcon(R.drawable.ic_menu_camera);

        alertDialog.setPositiveButton("Send me password reset code",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String[] registeredEmails = {"uv@gmail.com", "oli@mail.com", "admin@softech.com", "karki@gmail.com"};
                        for (int i = 0; i < registeredEmails.length; i++) {
                            if (input.getText().toString().equals(registeredEmails[i])) {
                                Toast.makeText(getContext(), "Password reset code successfully send to your email", Toast.LENGTH_SHORT).show();
                                break;
                            }
                        }
                        Toast.makeText(getContext(), "Please provide registered email", Toast.LENGTH_SHORT).show();

                    }
                });
        alertDialog.show();
    }


    private void defineView(View view) {
        btn_login = view.findViewById(R.id.btn_login);
        username = view.findViewById(R.id.username);
        password = view.findViewById(R.id.password);
        forgetPassword=view.findViewById(R.id.forgetPassword);
        link_singup=view.findViewById(R.id.link_signup);
    }


    @Override
    public void onClick(View v) {
        LoginDto loginDto = new LoginDto(username.getText().toString(), password.getText().toString(), "token");
        mLoginPresenter.performLogin(loginDto,getContext());

    }

    @Override
    public void loginValidation() {
        Toast.makeText(getActivity(), "All Fields Required", Toast.LENGTH_LONG).show();

    }

    @Override
    public void loginSuccess() {
        SharedPreferences sharedPref = getActivity().getSharedPreferences("loginDetails", Context.MODE_PRIVATE);
        String token=sharedPref.getString("token","");
        // Toast.makeText(getActivity(), "Successfully Logged In with token: "+token, Toast.LENGTH_SHORT).show();
        Intent mainActivityLoginIntent = new Intent(getActivity(), MainActivity.class);
        startActivity(mainActivityLoginIntent);
        getActivity().finish();

    }

    @Override
    public void loginError() {
        //Toast.makeText(getActivity(), "Wrong Username or Password", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void userVerificationSuccess() {
        Toast.makeText(getActivity(), "Code is successfully send", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void userVerificationFailed() {
        Toast.makeText(getActivity(), "Please enter valid and register email", Toast.LENGTH_SHORT).show();

    }

    private boolean isAlreadyLoggedIn(){
        SharedPreferences sharedPref = getActivity().getSharedPreferences("loginDetails", Context.MODE_PRIVATE);
        String token=sharedPref.getString("token","");
        if(!("".equals(token))){
            return true;
        }
        return false;
    }

}
