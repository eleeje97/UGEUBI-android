package duksung.android.hororok.ugeubi.retrofit;

import duksung.android.hororok.ugeubi.retrofit.Login.LoginDTO;
import duksung.android.hororok.ugeubi.retrofit.Login.LoginResultDTO;
import duksung.android.hororok.ugeubi.retrofit.Login.UserDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
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
    Call<authenticationDTO> authenticate_num(@Body authenticationDTO authenticationDTO);


    // 회원 가입 내용 보내기

    @POST("users/sign-up")
    Call<Sign_up_DTO> signup(@Body Sign_up_DTO sign_up_dto);


    /** 로그인 **/
    @Headers({
            "Content-Type: application/json;charset=UTF-8" ,
            "Transfer-Encoding: chunked"})
    @POST("users/sign-in")
    Call<LoginResultDTO> login(@Body LoginDTO loginDTO);


    /** 아이디 찾기 **/

    @Headers({
            "Content-Type: application/json;charset=UTF-8" ,
            "Transfer-Encoding: chunked"})
    @GET("users/find-id")
    Call<FindIdDTO> find_id(@Query("userName") String userName, @Query("email") String email);




    /** 약/DUR 정보 검색 **/

    // 일반검색
    @Headers({
            "Content-Type: application/json;charset=UTF-8" ,
            "Transfer-Encoding: chunked"})
    @POST("medicine/DURPrdlstInfoService/getDurPrdlstInfoList")
    Call<DURInfoSearchResultDTO> getDurPrdlstInfoList(@Header("Authorization") String token, @Body DURInfoSearchDTO durInfoSearchDTO);

    // 병용금기
    @Headers({
            "Content-Type: application/json;charset=UTF-8" ,
            "Transfer-Encoding: chunked"})
    @POST("medicine/DURPrdlstInfoService/getUsjntTabooInfoList")
    Call<DURInfoSearchResultDTO> getUsjntTabooInfoList(@Header("Authorization") String token, @Body DURInfoSearchDTO durInfoSearchDTO);

    // 특정연령대금기
    @Headers({
            "Content-Type: application/json;charset=UTF-8" ,
            "Transfer-Encoding: chunked"})
    @POST("medicine/DURPrdlstInfoService/getSpcifyAgrdeTabooInfoList")
    Call<DURInfoSearchResultDTO> getSpcifyAgrdeTabooInfoList(@Header("Authorization") String token, @Body DURInfoSearchDTO durInfoSearchDTO);

    // 임부금기
    @Headers({
            "Content-Type: application/json;charset=UTF-8" ,
            "Transfer-Encoding: chunked"})
    @POST("medicine/DURPrdlstInfoService/getPwnmTabooInfoList")
    Call<DURInfoSearchResultDTO> getPwnmTabooInfoList(@Header("Authorization") String token, @Body DURInfoSearchDTO durInfoSearchDTO);

    // 용량주의
    @Headers({
            "Content-Type: application/json;charset=UTF-8" ,
            "Transfer-Encoding: chunked"})
    @POST("medicine/DURPrdlstInfoService/getCpctyAtentInfoList")
    Call<DURInfoSearchResultDTO> getCpctyAtentInfoList(@Header("Authorization") String token, @Body DURInfoSearchDTO durInfoSearchDTO);

    // 투여기간주의
    @Headers({
            "Content-Type: application/json;charset=UTF-8" ,
            "Transfer-Encoding: chunked"})
    @POST("medicine/DURPrdlstInfoService/getMdctnPdAtentInfoList")
    Call<DURInfoSearchResultDTO> getMdctnPdAtentInfoList(@Header("Authorization") String token, @Body DURInfoSearchDTO durInfoSearchDTO);

    // 노인주의
    @Headers({
            "Content-Type: application/json;charset=UTF-8" ,
            "Transfer-Encoding: chunked"})
    @POST("medicine/DURPrdlstInfoService/getOdsnAtentInfoList")
    Call<DURInfoSearchResultDTO> getOdsnAtentInfoList(@Header("Authorization") String token, @Body DURInfoSearchDTO durInfoSearchDTO);

    // 효능군중복
    @Headers({
            "Content-Type: application/json;charset=UTF-8" ,
            "Transfer-Encoding: chunked"})
    @POST("medicine/DURPrdlstInfoService/getEfcyDplctInfoList")
    Call<DURInfoSearchResultDTO> getEfcyDplctInfoList(@Header("Authorization") String token, @Body DURInfoSearchDTO durInfoSearchDTO);

    // 서방형제제 분할주의
    @Headers({
            "Content-Type: application/json;charset=UTF-8" ,
            "Transfer-Encoding: chunked"})
    @POST("medicine/DURPrdlstInfoService/getSeobangjeongPartitnAtentInfoList")
    Call<DURInfoSearchResultDTO> getSeobangjeongPartitnAtentInfoList(@Header("Authorization") String token, @Body DURInfoSearchDTO durInfoSearchDTO);


}
