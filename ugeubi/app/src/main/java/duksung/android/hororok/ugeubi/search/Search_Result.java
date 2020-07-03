package duksung.android.hororok.ugeubi.search;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import java.util.ArrayList;
import java.util.Random;

import duksung.android.hororok.ugeubi.R;
import duksung.android.hororok.ugeubi.retrofit.RetrofitClient;
import duksung.android.hororok.ugeubi.retrofit.RetrofitInterface;
import duksung.android.hororok.ugeubi.retrofit.Search.DURInfoSearchDTO;
import duksung.android.hororok.ugeubi.retrofit.Search.DURInfoSearchResultDTO;
import duksung.android.hororok.ugeubi.retrofit.Search.ItemInfoDTO;
import duksung.android.hororok.ugeubi.retrofit.Search.UsjntTabooResultDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// 검색 결과 페이지
public class Search_Result extends Activity {


    TextView keyword_textView;
    GridView search_result_view;
    Search_result_adapter adapter;

    Button back_btn;
    TextView noResult_textView;

    SwipyRefreshLayout swipeRefreshLayout;

    RetrofitInterface apiService;
    int currentPage = 1;
    String keyword;
    public final String PREFERENCE = "ugeubi.preference";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        keyword_textView = findViewById(R.id.keyword);
        back_btn = findViewById(R.id.search_back_btn);
        search_result_view = findViewById(R.id.gridview_search_result);
        noResult_textView = findViewById(R.id.noResult_textView);

        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);

        apiService = RetrofitClient.getService();





        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        // 어댑터 생성, 설정
        adapter = new Search_result_adapter();
        search_result_view.setAdapter(adapter);


        // 결과리스트 인텐트 가져오기
        Intent intent = getIntent();
        String durType = intent.getStringExtra("DURType");
        int totalPage = intent.getIntExtra("totalPage", 1);
        keyword = intent.getStringExtra("keyword");
        keyword_textView.setText(keyword);



        // 결과리스트를 그리드뷰에 추가
        if (durType.equals("UsjntTaboo")) {
            ArrayList<UsjntTabooResultDTO> resultList = (ArrayList<UsjntTabooResultDTO>) intent.getSerializableExtra("resultList");

            if (resultList == null) {
                noResult_textView.setVisibility(View.VISIBLE);
            } else {
                noResult_textView.setVisibility(View.INVISIBLE);
                Log.e("병용금기", resultList.toString() + ", size: " + resultList.size());

                for (UsjntTabooResultDTO usjntTabooResultDTO : resultList) {
                    Log.e("병용금기", "ITEM_NAME: " + usjntTabooResultDTO.getITEM_NAME());
                    adapter.addItem(usjntTabooResultDTO.getITEM_NAME(), usjntTabooResultDTO.getENTP_NAME());
                }
            }

        } else {
            ArrayList<ItemInfoDTO> resultList = (ArrayList<ItemInfoDTO>) intent.getSerializableExtra("resultList");

            if (resultList == null) {
                noResult_textView.setVisibility(View.VISIBLE);
            } else {
                noResult_textView.setVisibility(View.INVISIBLE);
                for (ItemInfoDTO itemInfoDTO : resultList) {
                    adapter.addItem(itemInfoDTO.getITEM_NAME(), itemInfoDTO.getENTP_NAME());
                }
            }
        }




        // 아이템 리스너 설정
        search_result_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Toast.makeText(getApplicationContext(), ""+position, Toast.LENGTH_SHORT).show();



                // TEST
                Intent intent = new Intent(getApplicationContext(), SearchResultDetail.class);
                startActivity(intent);


            }
        });


        // Swipe Refresh
        swipeRefreshLayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                Toast.makeText(getApplicationContext(), "새로고침", Toast.LENGTH_SHORT).show();

                if (!durType.equals("UsjntTaboo")&&(currentPage<totalPage)) {
                    currentPage++;
                    refresh(durType, currentPage);

                    Log.e("Refresh", "["+currentPage+"/"+totalPage+"]");
                }


                swipeRefreshLayout.setRefreshing(false);
            }
        });


    }


    // 어댑터
    class Search_result_adapter extends BaseAdapter {
        // 검색 데이터 결과
        ArrayList<Search_data> items = new ArrayList<Search_data>();

        // 알약 아이콘을 랜덤으로 보여주기 위해 사용
        int[] pill_icons = {R.drawable.medicine_icon_pill1, R.drawable.pill_icon2,R.drawable.icon_pill3};
        Random random = new Random();

        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(String medicine_name, String entp_name){
            int num = random.nextInt(3);
            if (medicine_name.length() > 7) {
                medicine_name = medicine_name.substring(0,7) + "...";
            }
            Search_data data = new Search_data(pill_icons[num], medicine_name, entp_name);
            items.add(data);

            notifyDataSetChanged();
        }

        @Override
        public Search_data getItem(int i) {
            return items.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            Search_viewer searchViewer = new Search_viewer(getApplication());
            searchViewer.setItem(items.get(i));
            return searchViewer;

        }
    }



    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }


    public void refresh(String durType, int pageNo) {

        SharedPreferences pref = getSharedPreferences(PREFERENCE, MODE_PRIVATE);
        String accessToken = "Bearer " + pref.getString("accessToken", "");

        DURInfoSearchDTO durInfoSearchDTO = new DURInfoSearchDTO(keyword, pageNo+"");

        if (durType.equals("None")) {
            apiService.getDurPrdlstInfoList(accessToken, durInfoSearchDTO).enqueue(new Callback<DURInfoSearchResultDTO>() {
                @Override
                public void onResponse(Call<DURInfoSearchResultDTO> call, Response<DURInfoSearchResultDTO> response) {
                    Log.e("Search", "code: " + response.code());

                    if (response.isSuccessful()) {
                        DURInfoSearchResultDTO durInfoSearchResultResponse = response.body();
                        ArrayList<ItemInfoDTO> resultList = durInfoSearchResultResponse.getItems();

                        for (ItemInfoDTO itemInfoDTO : resultList) {
                            adapter.addItem(itemInfoDTO.getITEM_NAME(), itemInfoDTO.getENTP_NAME());
                        }
                    }
                }

                @Override
                public void onFailure(Call<DURInfoSearchResultDTO> call, Throwable t) {
                    Log.e("Search", "통신실패! " + t.getMessage());
                }
            });
        } else if (durType.equals("SpcifyAgrdeTaboo")) {
            apiService.getSpcifyAgrdeTabooInfoList(accessToken, durInfoSearchDTO).enqueue(new Callback<DURInfoSearchResultDTO>() {
                @Override
                public void onResponse(Call<DURInfoSearchResultDTO> call, Response<DURInfoSearchResultDTO> response) {
                    Log.e("Search", "code: " + response.code());

                    if (response.isSuccessful()) {
                        DURInfoSearchResultDTO durInfoSearchResultResponse = response.body();
                        ArrayList<ItemInfoDTO> resultList = durInfoSearchResultResponse.getItems();

                        for (ItemInfoDTO itemInfoDTO : resultList) {
                            adapter.addItem(itemInfoDTO.getITEM_NAME(), itemInfoDTO.getENTP_NAME());
                        }
                    }
                }
                @Override
                public void onFailure(Call<DURInfoSearchResultDTO> call, Throwable t) {
                    Log.e("Search", "통신실패! " + t.getMessage());
                }
            });
        } else if (durType.equals("PwnmTaboo")) {
            apiService.getPwnmTabooInfoList(accessToken, durInfoSearchDTO).enqueue(new Callback<DURInfoSearchResultDTO>() {
                @Override
                public void onResponse(Call<DURInfoSearchResultDTO> call, Response<DURInfoSearchResultDTO> response) {
                    Log.e("Search", "code: " + response.code());

                    if (response.isSuccessful()) {
                        DURInfoSearchResultDTO durInfoSearchResultResponse = response.body();
                        ArrayList<ItemInfoDTO> resultList = durInfoSearchResultResponse.getItems();

                        for (ItemInfoDTO itemInfoDTO : resultList) {
                            adapter.addItem(itemInfoDTO.getITEM_NAME(), itemInfoDTO.getENTP_NAME());
                        }
                    }
                }
                @Override
                public void onFailure(Call<DURInfoSearchResultDTO> call, Throwable t) {
                    Log.e("Search", "통신실패! " + t.getMessage());
                }
            });

        } else if (durType.equals("CpctyAtent")) {
            apiService.getCpctyAtentInfoList(accessToken, durInfoSearchDTO).enqueue(new Callback<DURInfoSearchResultDTO>() {
                @Override
                public void onResponse(Call<DURInfoSearchResultDTO> call, Response<DURInfoSearchResultDTO> response) {
                    Log.e("Search", "code: " + response.code());

                    if (response.isSuccessful()) {
                        DURInfoSearchResultDTO durInfoSearchResultResponse = response.body();
                        ArrayList<ItemInfoDTO> resultList = durInfoSearchResultResponse.getItems();

                        for (ItemInfoDTO itemInfoDTO : resultList) {
                            adapter.addItem(itemInfoDTO.getITEM_NAME(), itemInfoDTO.getENTP_NAME());
                        }
                    }
                }
                @Override
                public void onFailure(Call<DURInfoSearchResultDTO> call, Throwable t) {
                    Log.e("Search", "통신실패! " + t.getMessage());
                }
            });

        } else if (durType.equals("MdctnPdAtent")) {
            apiService.getMdctnPdAtentInfoList(accessToken, durInfoSearchDTO).enqueue(new Callback<DURInfoSearchResultDTO>() {
                @Override
                public void onResponse(Call<DURInfoSearchResultDTO> call, Response<DURInfoSearchResultDTO> response) {
                    Log.e("Search", "code: " + response.code());

                    if (response.isSuccessful()) {
                        DURInfoSearchResultDTO durInfoSearchResultResponse = response.body();
                        ArrayList<ItemInfoDTO> resultList = durInfoSearchResultResponse.getItems();

                        for (ItemInfoDTO itemInfoDTO : resultList) {
                            adapter.addItem(itemInfoDTO.getITEM_NAME(), itemInfoDTO.getENTP_NAME());
                        }
                    }
                }
                @Override
                public void onFailure(Call<DURInfoSearchResultDTO> call, Throwable t) {
                    Log.e("Search", "통신실패! " + t.getMessage());
                }
            });

        } else if (durType.equals("OdsnAtent")) {
            apiService.getOdsnAtentInfoList(accessToken, durInfoSearchDTO).enqueue(new Callback<DURInfoSearchResultDTO>() {
                @Override
                public void onResponse(Call<DURInfoSearchResultDTO> call, Response<DURInfoSearchResultDTO> response) {
                    Log.e("Search", "code: " + response.code());

                    if (response.isSuccessful()) {
                        DURInfoSearchResultDTO durInfoSearchResultResponse = response.body();
                        ArrayList<ItemInfoDTO> resultList = durInfoSearchResultResponse.getItems();

                        for (ItemInfoDTO itemInfoDTO : resultList) {
                            adapter.addItem(itemInfoDTO.getITEM_NAME(), itemInfoDTO.getENTP_NAME());
                        }
                    }
                }
                @Override
                public void onFailure(Call<DURInfoSearchResultDTO> call, Throwable t) {
                    Log.e("Search", "통신실패! " + t.getMessage());
                }
            });

        } else if (durType.equals("EfcyDplct")) {
            apiService.getEfcyDplctInfoList(accessToken, durInfoSearchDTO).enqueue(new Callback<DURInfoSearchResultDTO>() {
                @Override
                public void onResponse(Call<DURInfoSearchResultDTO> call, Response<DURInfoSearchResultDTO> response) {
                    Log.e("Search", "code: " + response.code());

                    if (response.isSuccessful()) {
                        DURInfoSearchResultDTO durInfoSearchResultResponse = response.body();
                        ArrayList<ItemInfoDTO> resultList = durInfoSearchResultResponse.getItems();

                        for (ItemInfoDTO itemInfoDTO : resultList) {
                            adapter.addItem(itemInfoDTO.getITEM_NAME(), itemInfoDTO.getENTP_NAME());
                        }
                    }
                }
                @Override
                public void onFailure(Call<DURInfoSearchResultDTO> call, Throwable t) {
                    Log.e("Search", "통신실패! " + t.getMessage());
                }
            });

        } else if (durType.equals("SeobangjeongPartitnAtent")) {
            apiService.getSeobangjeongPartitnAtentInfoList(accessToken, durInfoSearchDTO).enqueue(new Callback<DURInfoSearchResultDTO>() {
                @Override
                public void onResponse(Call<DURInfoSearchResultDTO> call, Response<DURInfoSearchResultDTO> response) {
                    Log.e("Search", "code: " + response.code());

                    if (response.isSuccessful()) {
                        DURInfoSearchResultDTO durInfoSearchResultResponse = response.body();
                        ArrayList<ItemInfoDTO> resultList = durInfoSearchResultResponse.getItems();

                        for (ItemInfoDTO itemInfoDTO : resultList) {
                            adapter.addItem(itemInfoDTO.getITEM_NAME(), itemInfoDTO.getENTP_NAME());
                        }
                    }
                }
                @Override
                public void onFailure(Call<DURInfoSearchResultDTO> call, Throwable t) {
                    Log.e("Search", "통신실패! " + t.getMessage());
                }
            });

        }

    }
}
