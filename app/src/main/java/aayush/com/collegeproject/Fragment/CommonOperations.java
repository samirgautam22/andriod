package aayush.com.collegeproject.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Patterns;

class CommonOperations{
        public static boolean isEmailValid(String string) {
            return string != null && Patterns.EMAIL_ADDRESS.matcher(string).matches();
        }

        public static boolean isFieldEmpty(String string) {
            return TextUtils.isEmpty(string);
        }

        public static String getToken(Context context){
            SharedPreferences sharedPref = ((Activity)context).getSharedPreferences("loginDetails", Context.MODE_PRIVATE);
            return sharedPref.getString("token","");
        }
        public static Long getLoginId(Context context){
            SharedPreferences sharedPref = ((Activity)context).getSharedPreferences("loginDetails", Context.MODE_PRIVATE);
            return Long.parseLong(sharedPref.getString("userId",""));
        }

}
