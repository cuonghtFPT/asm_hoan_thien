package cuonghtph34430.poly.asm_reatapi_cuonghtph34430.API;

import java.util.ArrayList;
import java.util.List;

import cuonghtph34430.poly.asm_reatapi_cuonghtph34430.DTO.ShoeDTO;
import cuonghtph34430.poly.asm_reatapi_cuonghtph34430.DTO.User;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIService {


    String DOMAIN = "http://10.24.30.234:3000";

    @GET("/api/get-list-vegestable")
    Call<List<ShoeDTO>> getShoe();



    @POST("/api/post-vegestable")
    Call<ShoeDTO> createShoe(@Body ShoeDTO shoe);


    @PUT("/api/update-vegestable-by-id/{id}")
    Call<ShoeDTO> updateShoe(@Path("id") String id, @Body ShoeDTO shoe);

    @DELETE("/api/delete-vegestable-by-id/{id}")
    Call<ShoeDTO> deleteShoe(@Path("id") String id);


//    @GET("/api/get-list-vegestable-sorted")
//    Call<List<ShoeDTO>> getShoeSortedByPriceAscending(@Query("sort") String sortBy);
@GET("/api/get-list-vegestable-sorted")
Call<List<ShoeDTO>> getSortedShoeByPriceAscending();

   @GET("/api/search-vegestable-by-name")
    Call<Response<ArrayList<ShoeDTO>>> searchShoe(@Query("key") String key);


    @POST("/api/add-register") // Đường dẫn tới endpoint đăng ký
    Call<Void> registerUser(@Body User user);

    @POST("/api/add-login") // Đường dẫn tới endpoint đăng nhập
    Call<Void> loginUser(@Body User user); // Phương thức để đăng nhập người dùng
}
