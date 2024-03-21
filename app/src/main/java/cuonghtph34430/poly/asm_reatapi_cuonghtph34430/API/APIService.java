package cuonghtph34430.poly.asm_reatapi_cuonghtph34430.API;

import java.util.List;
import cuonghtph34430.poly.asm_reatapi_cuonghtph34430.DTO.ShoeDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface APIService {


    String DOMAIN = "http://192.168.0.102:3000";

    @GET("/api/get-list-shoe")
    Call<List<ShoeDTO>> getShoe();

    @POST("/api/post-shoe")
    Call<ShoeDTO> createShoe(@Body ShoeDTO shoe);


    @PUT("/api/update-shoe-by-id/{id}")
    Call<ShoeDTO> updateShoe(@Path("id") String id, @Body ShoeDTO shoe);

    @DELETE("/api/delete-shoe-by-id/{id}")
    Call<ShoeDTO> deleteShoe(@Path("id") String id);

}
