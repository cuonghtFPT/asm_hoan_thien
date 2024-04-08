package cuonghtph34430.poly.asm_reatapi_cuonghtph34430;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import java.io.IOException;

import cuonghtph34430.poly.asm_reatapi_cuonghtph34430.API.APIService;
import cuonghtph34430.poly.asm_reatapi_cuonghtph34430.DTO.ShoeDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Dat_hang extends AppCompatActivity {
    private static final int MY_RES_CODE = 10;
    TextView tvDathang;
    ImageView ivBack, ivImageShoe;
    AppCompatButton btnDathang;
    EditText edhName, edhPrice, edhBrand, edthSoluong;
    Button btnThanhtoan;
    LinearLayout btnChooseImage;
    Uri selectedImageUri; // Biến để lưu đường dẫn của ảnh đã chọn

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dat_hang);

        tvDathang=findViewById(R.id.tvTitledh);
//        ivBack=findViewById(R.id.ivBackCreUpdh);
        btnDathang=findViewById(R.id.btnDH);
        edhName=findViewById(R.id.edhName);
        edhPrice=findViewById(R.id.edhPrice);
        edhBrand=findViewById(R.id.edhBrand);
        edthSoluong=findViewById(R.id.edhSoluong);
        btnThanhtoan=findViewById(R.id.btnThanhtoan);

        ChangeUI();

        btnDathang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titleAdd = getIntent().getStringExtra("titleDH");
                if (titleAdd == null) {
                    UpdateShoe();
                } else {

                }
            }
        });
    }

    private boolean CheckCreateShoe() {
        // Viết mã kiểm tra dữ liệu nhập vào ở đây
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MY_RES_CODE && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                ivImageShoe.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void UpdateShoe() {
        String name = edhName.getText().toString();
        String description = edhBrand.getText().toString();
        String price = edhPrice.getText().toString();
        String id = getIntent().getStringExtra("id1");

        if (CheckCreateShoe()) {
            // Kiểm tra nếu người dùng đã chọn ảnh mới từ bộ nhớ máy ảo
            if (selectedImageUri != null) {
                String imageUrl = selectedImageUri.toString(); // Lấy đường dẫn của ảnh đã chọn
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(APIService.DOMAIN)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                APIService apiService = retrofit.create(APIService.class);

                Call<ShoeDTO> call = apiService.updateShoe(id, new ShoeDTO(name, description, Long.parseLong(price), imageUrl));

                call.enqueue(new Callback<ShoeDTO>() {
                    @Override
                    public void onResponse(Call<ShoeDTO> call, Response<ShoeDTO> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(Dat_hang.this, "Sửa thành công", Toast.LENGTH_SHORT).show();
                            // Hiển thị ảnh đã chọn lên sau khi cập nhật
                            ivImageShoe.setImageURI(selectedImageUri);
                            startActivity(new Intent(Dat_hang.this, MainActivity.class));
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<ShoeDTO> call, Throwable t) {
                        Log.e("zzzzz", "onFailure: " + t.getMessage());
                    }
                });
            } else {
                // Nếu không chọn ảnh mới, chỉ cập nhật thông tin mà không thay đổi ảnh
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(APIService.DOMAIN)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                APIService apiService = retrofit.create(APIService.class);

                Call<ShoeDTO> call = apiService.updateShoe(id, new ShoeDTO(name, description, Long.parseLong(price),""));

                call.enqueue(new Callback<ShoeDTO>() {
                    @Override
                    public void onResponse(Call<ShoeDTO> call, Response<ShoeDTO> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(Dat_hang.this, "Sửa thành công", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Dat_hang.this, MainActivity.class));
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<ShoeDTO> call, Throwable t) {
                        Log.e("zzzzz", "onFailure: " + t.getMessage());
                    }
                });
            }
        }
    }


    private void ChangeUI() {
        String titleAdd = getIntent().getStringExtra("titleAdd");
        String titleBtnAdd = getIntent().getStringExtra("titleBtnAdd");
        String titleUpdate = getIntent().getStringExtra("titleEdit");
        String titleBtnUp = getIntent().getStringExtra("titleBtnEdit");
        String titleDH = getIntent().getStringExtra("titleDathang");
        String titleBtnDH = getIntent().getStringExtra("titleBtnDathang");
        String name = getIntent().getStringExtra("name1");
        String description = getIntent().getStringExtra("description1");
        Long price = getIntent().getLongExtra("price1", 0);
        if (titleUpdate == null) {
            tvDathang.setText(titleDH);
            btnDathang.setText(titleBtnDH);
        } else {
            tvDathang.setText(titleDH);
            edhName.setText(name);
            edhBrand.setText(description);
            edhPrice.setText(price + "");
            btnDathang.setText(titleBtnDH);
        }

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dat_hang.this, MainActivity.class));
                finish();
            }
        });
    }

}
