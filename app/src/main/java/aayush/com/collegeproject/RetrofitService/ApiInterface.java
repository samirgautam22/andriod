package aayush.com.collegeproject.RetrofitService;

import aayush.com.collegeproject.Dtos.LoginDto;
import aayush.com.collegeproject.Dtos.LoginResponseDto;
import aayush.com.collegeproject.Dtos.SignupDto;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiInterface {
    @POST("customer")
    Call<ResponseBody> registerUser(@Body SignupDto SignupDto);

    @POST("login")
    Call<LoginResponseDto> logIntoSystem(@Body LoginDto loginPojo);

}
