package cuonghtph34430.poly.asm_reatapi_cuonghtph34430;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import cuonghtph34430.poly.asm_reatapi_cuonghtph34430.API.APIService;
import cuonghtph34430.poly.asm_reatapi_cuonghtph34430.DTO.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Register extends AppCompatActivity {
    EditText txtUser, txtPass;
    Button btnregister;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        txtUser = findViewById(R.id.register_username);
        txtPass = findViewById(R.id.register_password);
        btnregister = findViewById(R.id.register_button);

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy giá trị email và password tại thời điểm người dùng nhấn nút đăng ký
                String email = txtUser.getText().toString().trim();
                String password = txtPass.getText().toString();

                // Kiểm tra email có đúng định dạng và không được để trống
                if (!isValidEmail(email)) {
                    Toast.makeText(Register.this, "Email không hợp lệ", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (email.isEmpty()) {
                    Toast.makeText(Register.this, "Vui lòng nhập email", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Kiểm tra mật khẩu không được để trống
                if (password.isEmpty()) {
                    Toast.makeText(Register.this, "Vui lòng nhập mật khẩu", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Tạo một đối tượng User mới
                User user = new User(email, password);

                // Tạo Retrofit instance
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(APIService.DOMAIN)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                // Khởi tạo APIService từ Retrofit instance
                APIService apiService = retrofit.create(APIService.class);

                // Gọi phương thức đăng ký từ API
                Call<Void> call = apiService.registerUser(user);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                        if (response.isSuccessful()) {
                            // Đăng ký thành công, chuyển sang màn hình đăng nhập
                            Toast.makeText(Register.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Register.this, Login.class);
                            startActivity(intent);
                        } else {
                            // Đăng ký thất bại
                            Toast.makeText(Register.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                        // Xử lý lỗi khi gửi yêu cầu
                        Toast.makeText(Register.this, "Có lỗi xảy ra: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    private boolean isValidEmail (String email){
        return !email.isEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    }
