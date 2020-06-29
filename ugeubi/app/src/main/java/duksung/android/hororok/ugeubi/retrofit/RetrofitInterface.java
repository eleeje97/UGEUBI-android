package duksung.android.hororok.ugeubi.retrofit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitInterface {

    /** Header **/


    /** 회원가입 **/

    // 아이디 중복확인
    @Headers({
            "Content-Type: application/json;charset=UTF-8" ,
            "Transfer-Encoding: chunked"})
    @GET("users/check-id")
    Call<Check_id_data> check_id(@Query("userId") String userId);

    // 이메일 인증번호 전송
    @Headers({
            "Content-Type: application/json;charset=UTF-8" ,
            "Transfer-Encoding: chunked"})
    @POST("authentication-numbers/sign-up")
    Call<Sign_up_email_data> sendnum(@Body Sign_up_email_data email);



    // 이메일 인증 번호 확인
    @Headers({
            "Content-Type: application/json;charset=UTF-8" ,
            "Transfer-Encoding: chunked"})
    @POST("authentication/sign-up")
    Call<Sign_up_email_num> authenticate_num(@Body Sign_up_email_num num);



    // 회원 가입 내용 보내기
    @FormUrlEncoded  // key = value 형태로 데이터를 전달하는 것을 의미, @Field와 함께 사용함
    @POST("users/sign-up")
    Call<Sign_up_data> signup(@Field("email") String email,
                              @Field("userId") String userId,
                              @Field("password") String password,
                              @Field("userName") String userName);

}
