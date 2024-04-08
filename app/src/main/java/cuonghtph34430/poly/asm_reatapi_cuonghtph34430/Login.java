package cuonghtph34430.poly.asm_reatapi_cuonghtph34430;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import cuonghtph34430.poly.asm_reatapi_cuonghtph34430.API.APIService;
import cuonghtph34430.poly.asm_reatapi_cuonghtph34430.DTO.User;
import cuonghtph34430.poly.asm_reatapi_cuonghtph34430.MainActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    private FirebaseAuth auth;
    private EditText txtUser, txtPass;
    private TextView txtRegister;
    private Button btnLogin;
    private Context context = this;
    private APIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtUser = findViewById(R.id.sign_username);
        txtPass = findViewById(R.id.sign_password);
        btnLogin = findViewById(R.id.sign_button);
        txtRegister = findViewById(R.id.loginRegister);
        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });

        auth = FirebaseAuth.getInstance();
        apiService = RetrofitClient.getClient().create(APIService.class);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txtUser.getText().toString();
                String password = txtPass.getText().toString();

                // Gửi yêu cầu đăng nhập đến API
                User user = new User(email, password);
                Call<Void> call = apiService.loginUser(user);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            // Đăng nhập thành công
                            Toast.makeText(getApplicationContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();

                            // Chuyển sang màn hình Logout
                            Intent intent = new Intent(Login.this, MainActivity.class);
                            startActivity(intent);
                            finish(); // Đóng màn hình Login
                        } else {
                            // Đăng nhập thất bại
                            Toast.makeText(getApplicationContext(), "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        // Xử lý lỗi
                        Toast.makeText(getApplicationContext(), "Đã xảy ra lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
