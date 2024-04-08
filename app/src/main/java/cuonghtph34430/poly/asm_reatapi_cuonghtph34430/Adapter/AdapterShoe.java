package cuonghtph34430.poly.asm_reatapi_cuonghtph34430.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide; // Import thư viện Glide
import com.google.android.material.imageview.ShapeableImageView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cuonghtph34430.poly.asm_reatapi_cuonghtph34430.API.APIService;
import cuonghtph34430.poly.asm_reatapi_cuonghtph34430.DTO.ShoeDTO;
import cuonghtph34430.poly.asm_reatapi_cuonghtph34430.Dat_hang;
import cuonghtph34430.poly.asm_reatapi_cuonghtph34430.MainActivity;
import cuonghtph34430.poly.asm_reatapi_cuonghtph34430.NewCreateAndAddActivity;
import cuonghtph34430.poly.asm_reatapi_cuonghtph34430.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdapterShoe extends RecyclerView.Adapter<AdapterShoe.ViewHolder> {

    Context context;
    List<ShoeDTO> list;

    public AdapterShoe(Context context, List<ShoeDTO> list) {
        this.context = context;
        this.list = list;
    }





    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("RecyclerView")
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String id = list.get(position).get_id();
        holder.tvNameShoe.setText(String.valueOf(list.get(position).getName()));
        holder.tvBrandShoe.setText(String.valueOf(list.get(position).getDescription()));
        holder.tvPriceShoe.setText(String.valueOf(list.get(position).getPrice()));


        // Load ảnh từ URL vào ImageView bằng Glide
        Glide.with(context)
                .load(list.get(position).getImg()) // Sử dụng đường dẫn URL từ API
                .into(holder.ivShoe);

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                @SuppressLint("InflateParams") View view = LayoutInflater.from(context).inflate(R.layout.layout_dialog_mess, null, false);
                builder.setView(view);

                AlertDialog dialog = builder.create();

                Window window = dialog.getWindow();
                assert window != null;
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                dialog.show();

                AppCompatButton btnHuy, btnXacNhan;
                btnHuy = view.findViewById(R.id.btnHuy);
                btnXacNhan = view.findViewById(R.id.btnXacNhan);

                btnHuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                btnXacNhan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(APIService.DOMAIN)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        APIService apiService = retrofit.create(APIService.class);

                        Call<ShoeDTO> call = apiService.deleteShoe(id);

                        call.enqueue(new Callback<ShoeDTO>() {
                            @SuppressLint("NotifyDataSetChanged")
                            @Override
                            public void onResponse(Call<ShoeDTO> call, Response<ShoeDTO> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(context, "Delete successfully", Toast.LENGTH_SHORT).show();
                                    MainActivity.CallAPI(retrofit);
                                    dialog.dismiss();
                                    notifyDataSetChanged();
                                }
                            }
                            @Override
                            public void onFailure(Call<ShoeDTO> call, Throwable t) {
                                Log.e("cccccc", "onFailure: " + t.getMessage());
                            }
                        });
                    }
                });
            }
        });
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NewCreateAndAddActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("titleEdit", "Update shoe");
                intent.putExtra("titleBtnEdit", "Update");
                intent.putExtra("name", list.get(position).getName());
                intent.putExtra("description", list.get(position).getDescription());
                intent.putExtra("price", list.get(position).getPrice());
                intent.putExtra("img", list.get(position).getImg());
                intent.putExtra("id", list.get(position).get_id());
                context.startActivity(intent);
            }
        });

        holder.btnDathang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Dat_hang.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("titleDathang", "Đặt hàng");
                intent.putExtra("titleBtnDathang", "Đặt hàng");
                intent.putExtra("name1", list.get(position).getName());
                intent.putExtra("description1", list.get(position).getDescription());
                intent.putExtra("price1", list.get(position).getPrice());
                intent.putExtra("id1", list.get(position).get_id());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvNameShoe, tvPriceShoe, tvBrandShoe;
        Button btnEdit, btnDelete,btnDathang;
        ShapeableImageView ivShoe;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNameShoe = itemView.findViewById(R.id.tvName);
            tvBrandShoe = itemView.findViewById(R.id.tvBrand);
            tvPriceShoe = itemView.findViewById(R.id.tvPrice);
            ivShoe = itemView.findViewById(R.id.ivShoe);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDathang=itemView.findViewById(R.id.btnDathang);
        }
    }
}
