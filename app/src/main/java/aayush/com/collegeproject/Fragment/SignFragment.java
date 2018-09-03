package aayush.com.collegeproject.Fragment;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import aayush.com.collegeproject.Dtos.SignupDto;
import aayush.com.collegeproject.R;
import aayush.com.collegeproject.RetrofitService.ApiClient;
import aayush.com.collegeproject.RetrofitService.ApiInterface;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//import android.widget.Spinner;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignFragment extends Fragment {
    private EditText email,firstName,lastName,username,password,cpassword,phoneNo;
    private RadioGroup gender;
    ProgressBar showProgressBar;

    private Button btnRegister;
    //private Spinner SpinnerArea, SpinnerDistrict, SpinnerStreet;


    public SignFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_sign, container, false);
        doRegister(view);
        initializeViews(view);
        return view;
    }

    private void doRegister( View view) {
        showProgressBar=view.findViewById(R.id.show_pb);
        /*SpinnerArea=view.findViewById(R.id.area);
        SpinnerDistrict=view.findViewById(R.id.district);
        SpinnerStreet=view.findViewById(R.id.street);*/
        email=view.findViewById(R.id.email);
        firstName=view.findViewById(R.id.firstName);
        gender=view.findViewById(R.id.gender);
        lastName=view.findViewById(R.id.lastName);
        password=view.findViewById(R.id.password);
        cpassword=view.findViewById(R.id.conform_password);
        phoneNo=view.findViewById(R.id.phoneNo);
        username=view.findViewById(R.id.username);
        btnRegister =view.findViewById(R.id.btnRegister);

    }
    private void initializeViews(final View view) {

        btnRegister.setOnClickListener(new View.OnClickListener() {
            String  useremail,userfirstName,userlastName,usergender,userusername,userpassword,usercpassword,userphone;
            /*userarea,userdistrict,userstreet,*/
            @Override
            public void onClick(  View v) {
                if(isAllDataValid()) {

                    /*userarea = SpinnerArea.getSelectedItem().toString();
                    userdistrict = SpinnerDistrict.getSelectedItem().toString();
                    userstreet = SpinnerStreet.getSelectedItem().toString();*/
                    useremail = email.getText().toString();
                    userfirstName = firstName.getText().toString();
                    userlastName = lastName.getText().toString();

                    int selectedRadioButtonID = gender.getCheckedRadioButtonId();
                    //gender1=(RadioButton) v.findViewById(selectedRadioButtonID);
                    // If nothing is selected from Radio Group, then it return -1
                    if (selectedRadioButtonID != -1) {

                        RadioButton selectedRadioButton = (RadioButton) view.findViewById(selectedRadioButtonID);
                        //Toast.makeText(getContext(), String.valueOf(selectedRadioButtonID), Toast.LENGTH_SHORT).show();
                        //Log.d("value", String.valueOf(selectedRadioButtonID));
                        usergender = selectedRadioButton.getText().toString();
                    } else {
                        usergender = "gender";
                    }
                    userusername = username.getText().toString();
                    userpassword = password.getText().toString();
                    usercpassword= cpassword.getText().toString();
                    userphone = phoneNo.getText().toString();

                    /*AddressList addressList = new AddressList();
                    List<Address> addresses=new ArrayList<>();
                    addresses.add(new Address(userarea,userdistrict,userstreet));
                    addressList.setAddressList(addresses);*/



                    showProgressBar.setVisibility(View.VISIBLE);
                    SignupDto signDto = new SignupDto(useremail, userfirstName, userlastName, userusername, usergender, userpassword,usercpassword,userphone);
                    ApiInterface apiClient =
                            ApiClient.getClient().create(ApiInterface.class);
                    Call<ResponseBody> call = apiClient.registerUser(signDto);
                    call.enqueue(new Callback<ResponseBody>() {
                        @RequiresApi(api = Build.VERSION_CODES.M)
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            showProgressBar.setVisibility(View.GONE);
                            if (response.isSuccessful()) {
//                            String signUpResponse=response.body();
//                            Toast.makeText(RegisterFragment.this, signUpResponse, Toast.LENGTH_SHORT).show();

                                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                                fragmentTransaction.replace(R.id.container,new DashboardFragment());
                                fragmentTransaction.commit();


                                String signUpResponse = response.body().toString();
                                Toast.makeText(getContext(), signUpResponse, Toast.LENGTH_SHORT).show();



                            }


                        }

                        @RequiresApi(api = Build.VERSION_CODES.M)
                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            showProgressBar.setVisibility(View.GONE);
                            Toast.makeText(getContext(), "Failed to hit server", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });
    }
    private boolean isAllDataValid() {
        if (CommonOperations.isFieldEmpty(firstName.getText().toString())) {
            firstName.setError("Required");
            return false;
        } else {
            if (CommonOperations.isFieldEmpty(email.getText().toString())) {
                email.setError("Required");
                return false;
            } else {
                if (!(CommonOperations.isEmailValid(email.getText().toString()))) {
                    email.setError("Enter valid email");
                    return false;
                } else {
                    if (CommonOperations.isFieldEmpty(lastName.getText().toString())) {
                        lastName.setError("Required");
                        return false;
                    }else{
                        if (CommonOperations.isFieldEmpty(phoneNo.getText().toString())) {
                            phoneNo.setError("Required");
                            return false;
                        }


                        else {
                            if (CommonOperations.isFieldEmpty(username.getText().toString())) {
                                username.setError("Required");
                                return false;
                            } else {
                                if (CommonOperations.isFieldEmpty(password.getText().toString())) {
                                    password.setError("Required");
                                    return false;
                                } else {
                                    return true;
                                }
                            }
                        }
                    }

                }
            }

        }


    }


}



