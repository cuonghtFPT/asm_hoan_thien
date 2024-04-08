package cuonghtph34430.poly.asm_reatapi_cuonghtph34430;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import cuonghtph34430.poly.asm_reatapi_cuonghtph34430.API.APIService;
import cuonghtph34430.poly.asm_reatapi_cuonghtph34430.Adapter.AdapterShoe;
import cuonghtph34430.poly.asm_reatapi_cuonghtph34430.DTO.ShoeDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    static List<ShoeDTO> list = new ArrayList<>();
    static AdapterShoe adapterShoe;
    static RecyclerView recyclerView;
    FloatingActionButton floaAdd;
    EditText edt_search;
    Button btnSearch;


    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.rcvList);
        floaAdd = findViewById(R.id.floatAdd);
        edt_search = findViewById(R.id.edt_Search);
        btnSearch = findViewById(R.id.btn_Search);

        //Connect
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIService.DOMAIN)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //Call Api Retrofit
        CallAPI(retrofit);

        // Set sự kiện khi người dùng nhấn enter để tìm kiếm
//        edt_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if (actionId == KeyEvent.ACTION_DOWN || actionId == KeyEvent.KEYCODE_ENTER) {
//                    String searchText = edt_search.getText().toString().trim();
//                    searchShoe(searchText, retrofit);
//                    return true;
//                }
//                return false;
//            }
//        });

        // Set sự kiện khi người dùng nhấn nút Search
//        btnSearch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String searchText = edt_search.getText().toString().trim();
//                searchShoe(searchText, retrofit);
//            }
//        });

        // Tìm kiếm khi người dùng click vào nút Search
        // Set sự kiện khi người dùng nhấn nút Search
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchText = edt_search.getText().toString().trim();
                searchShoe(searchText, retrofit);
            }
        });




        //setOnclick add
        floaAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewCreateAndAddActivity.class);
                intent.putExtra("titleAdd", "Create shoe");
                intent.putExtra("titleBtnAdd", "Create");
                startActivity(intent);
                finish();
            }
        });


    }

    public static void CallAPI(Retrofit retrofit) {

        //Khai báo API Service
        APIService apiService = retrofit.create(APIService.class);

        Call<List<ShoeDTO>> call = apiService.getShoe();

        call.enqueue(new Callback<List<ShoeDTO>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<List<ShoeDTO>> call, @NonNull Response<List<ShoeDTO>> response) {
                if (response.isSuccessful()) {
                    list = response.body();
                    sortShoeByPriceAscending(); // Sắp xếp giày theo giá tăng dần
                    adapterShoe = new AdapterShoe(recyclerView.getContext(), list);
                    LinearLayoutManager linearLayoutManager =
                            new LinearLayoutManager(recyclerView.getContext(), LinearLayoutManager.VERTICAL, false);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setAdapter(adapterShoe);
                    adapterShoe.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<ShoeDTO>> call, @NonNull Throwable t) {
                Log.e("zzzz", "onFailure: " + t.getMessage());
            }
        });
    }


    // Phương thức để sắp xếp danh sách giày theo giá tăng dần
    private static void sortShoeByPriceAscending() {
        Collections.sort(list, new Comparator<ShoeDTO>() {
            @Override
            public int compare(ShoeDTO o1, ShoeDTO o2) {
                return Double.compare(o1.getPrice(), o2.getPrice());
            }
        });
    }
    private void searchShoe(String key, Retrofit retrofit) {
        // Khai báo API Service
        APIService apiService = retrofit.create(APIService.class);

        // Gọi phương thức tìm kiếm giày
        Call<Response<ArrayList<ShoeDTO>>> call = apiService.searchShoe(key);

        // Thực hiện cuộc gọi bất đồng bộ
        call.enqueue(new Callback<Response<ArrayList<ShoeDTO>>>() {
            @Override
            public void onResponse(@NonNull Call<Response<ArrayList<ShoeDTO>>> call, @NonNull Response<Response<ArrayList<ShoeDTO>>> response) {
                if (response.isSuccessful()) {
                    Response<ArrayList<ShoeDTO>> result = response.body();
                    if (result != null) {
                        List<ShoeDTO> searchResult = result.body();
                        list.clear(); // Xóa toàn bộ dữ liệu hiện tại trong list
                        if (searchResult != null) {
                            list.addAll(searchResult); // Thêm tất cả các phần tử mới vào danh sách hiện có
                        }
                        adapterShoe.notifyDataSetChanged(); // Cập nhật RecyclerView với dữ liệu mới
                        Toast.makeText(MainActivity.this, "Tìm kiếm thành công", Toast.LENGTH_SHORT).show();

                    } else {
                        list.clear();
                        adapterShoe.notifyDataSetChanged();
                        Toast.makeText(MainActivity.this, "Không tìm thấy kết quả", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    list.clear();
                    adapterShoe.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this, "Không tìm thấy kết quả", Toast.LENGTH_SHORT).show();
                    Log.e("zzzz", "onResponse: " + response.errorBody());
                }
            }


            @Override
            public void onFailure(@NonNull Call<Response<ArrayList<ShoeDTO>>> call, @NonNull Throwable t) {
                Log.e("zzzz", "onFailure: " + t.getMessage());
            }
        });
    }
}
