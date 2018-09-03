package aayush.com.collegeproject.Presenter;

import android.content.Context;

import aayush.com.collegeproject.Dtos.LoginDto;

public interface LoginPresenter {
    void performLogin(LoginDto loginDto, Context context);
    void forgetPassword(String email);
}
