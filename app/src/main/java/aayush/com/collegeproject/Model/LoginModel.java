package aayush.com.collegeproject.Model;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import aayush.com.collegeproject.Dtos.LoginDto;
import aayush.com.collegeproject.Dtos.LoginResponseDto;
import aayush.com.collegeproject.Presenter.LoginPresenter;
import aayush.com.collegeproject.RetrofitService.ApiClient;
import aayush.com.collegeproject.RetrofitService.ApiInterface;
import aayush.com.collegeproject.View.LoginView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginModel implements LoginPresenter {
    private LoginView mLoginView;
    private   boolean returnValue;
    public LoginModel(LoginView loginView){
        this.mLoginView=loginView;
    }


    @Override
    public void performLogin(LoginDto loginDto, Context context) {
        if(TextUtils.isEmpty(loginDto.getUsername())|| TextUtils.isEmpty(loginDto.getPassword())){
            mLoginView.loginValidation();
        }else if(doLogin(loginDto,context)){
            mLoginView.loginSuccess();
        }else{
            mLoginView.loginError();
        }
    }

    private boolean doLogin(final LoginDto loginPojo, final Context context) {

        ApiInterface apiClient =
                ApiClient.getClient().create(ApiInterface.class);
        Call<LoginResponseDto> call = apiClient.logIntoSystem(loginPojo);
        call.enqueue(new Callback<LoginResponseDto>() {
            @Override
            public void onResponse(Call<LoginResponseDto> call, Response<LoginResponseDto> response) {
                LoginResponseDto loginResponse=response.body();
                if(response.isSuccessful()) {
                    if (loginPojo != null) {


                        Toast.makeText(context, "login successful", Toast.LENGTH_SHORT).show();


                        SharedPreferences sharedPref = context.getSharedPreferences("loginDetails", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("userId", String.valueOf(loginResponse.getId()));
                        editor.putString("token", loginResponse.getToken());
                        //editor.putString("username",loginResponse.getUsername());
                        editor.apply();
                        returnValue=true;
                    }
                }else{
                    Toast.makeText(context, "Username and password mismatch", Toast.LENGTH_SHORT).show();
                    Log.d("Error message====",response.message());
                    returnValue=false;
                }

            }

            @Override
            public void onFailure(Call<LoginResponseDto> call, Throwable t) {
                Toast.makeText(context, "Unable to hit server", Toast.LENGTH_SHORT).show();
                returnValue=false;
            }
        });
        return returnValue;

    }

    @Override
    public void forgetPassword(String email) {
        String[] registeredEmails={"aayushma81@gmail.com"};
        for (int i=0;i<registeredEmails.length;i++){
            if(email.equals(registeredEmails[i])){
                mLoginView.userVerificationSuccess();
                break;

            }
        }
        mLoginView.userVerificationFailed();
    }

}
