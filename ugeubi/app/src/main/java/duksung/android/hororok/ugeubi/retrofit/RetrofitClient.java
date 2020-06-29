package duksung.android.hororok.ugeubi.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    final static String baseUrl = "http://ec2-15-164-21-63.ap-northeast-2.compute.amazonaws.com:8080/";


    private static Retrofit retrofit = null;



    public static  Retrofit getClient(){

        // 생성된 retrofit이 없다면
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                        .baseUrl(baseUrl)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
        }

        return retrofit;

    }

    public  static RetrofitInterface getService(){
        return  getClient().create(RetrofitInterface.class);
    }

}
