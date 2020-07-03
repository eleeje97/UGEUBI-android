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
import duksung.android.hororok.ugeubi.retrofit.Search.MixtureItemDTO;
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

    ArrayList<UsjntTabooResultDTO> usjntTabooResultList = null;
    ArrayList<ItemInfoDTO> resultList = null;

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
            usjntTabooResultList = (ArrayList<UsjntTabooResultDTO>) intent.getSerializableExtra("resultList");

            if (usjntTabooResultList == null) {
                noResult_textView.setVisibility(View.VISIBLE);
            } else {
                noResult_textView.setVisibility(View.INVISIBLE);
                Log.e("병용금기", usjntTabooResultList.toString() + ", size: " + usjntTabooResultList.size());

                for (UsjntTabooResultDTO usjntTabooResultDTO : usjntTabooResultList) {
                    Log.e("병용금기", "ITEM_NAME: " + usjntTabooResultDTO.getITEM_NAME());
                    adapter.addItem(usjntTabooResultDTO.getITEM_NAME(), usjntTabooResultDTO.getENTP_NAME());
                }
            }

        } else {
            resultList = (ArrayList<ItemInfoDTO>) intent.getSerializableExtra("resultList");

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
            ArrayList<SearchResultDetailData> searchResultDetailDataArrayList= new ArrayList<>();

            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Toast.makeText(getApplicationContext(), ""+position, Toast.LENGTH_SHORT).show();
                searchResultDetailDataArrayList.clear();

                if (durType.equals("None")) {
                    ItemInfoDTO itemInfoDTO = resultList.get(position);
                    searchResultDetailDataArrayList.add(new SearchResultDetailData("품목명", itemInfoDTO.getITEM_NAME()));
                    searchResultDetailDataArrayList.add(new SearchResultDetailData("업체명", itemInfoDTO.getENTP_NAME()));
                    searchResultDetailDataArrayList.add(new SearchResultDetailData("분류", itemInfoDTO.getCLASS_NO()));
                    searchResultDetailDataArrayList.add(new SearchResultDetailData("성상", itemInfoDTO.getCHART()));
                    searchResultDetailDataArrayList.add(new SearchResultDetailData("저장방법", itemInfoDTO.getSTORAGE_METHOD()));
                    searchResultDetailDataArrayList.add(new SearchResultDetailData("원료성분", itemInfoDTO.getMATERIAL_NAME()));
                    searchResultDetailDataArrayList.add(new SearchResultDetailData("유효기간", itemInfoDTO.getVALID_TERM()));
                    searchResultDetailDataArrayList.add(new SearchResultDetailData("용법용량", itemInfoDTO.getUD_DOC_ID()));
                    searchResultDetailDataArrayList.add(new SearchResultDetailData("주의사항", itemInfoDTO.getNB_DOC_ID()));

                } else if (durType.equals("UsjntTaboo")) {
                    UsjntTabooResultDTO usjntTabooResultDTO = usjntTabooResultList.get(position);
                    searchResultDetailDataArrayList.add(new SearchResultDetailData("품목명", usjntTabooResultDTO.getITEM_NAME()));
                    searchResultDetailDataArrayList.add(new SearchResultDetailData("약효분류", usjntTabooResultDTO.getCLASS_NAME()));

                    ArrayList<MixtureItemDTO> mixtureItemList = usjntTabooResultDTO.getMixtureItems();
                    for (MixtureItemDTO mixtureItemDTO : mixtureItemList) {
                        searchResultDetailDataArrayList.add(new SearchResultDetailData("병용금기약품", mixtureItemDTO.getMIXTURE_ITEM_NAME()));
                        searchResultDetailDataArrayList.add(new SearchResultDetailData("주의사항", mixtureItemDTO.getPROHBT_CONTENT()));
                    }

                } else {
                    ItemInfoDTO itemInfoDTO = resultList.get(position);
                    searchResultDetailDataArrayList.add(new SearchResultDetailData("품목명", itemInfoDTO.getITEM_NAME()));
                    searchResultDetailDataArrayList.add(new SearchResultDetailData("업체명", itemInfoDTO.getENTP_NAME()));
                    searchResultDetailDataArrayList.add(new SearchResultDetailData("약효분류", itemInfoDTO.getCLASS_NAME()));
                    searchResultDetailDataArrayList.add(new SearchResultDetailData("성상", itemInfoDTO.getCHART()));
                    searchResultDetailDataArrayList.add(new SearchResultDetailData("주성분", itemInfoDTO.getMAIN_INGR()));
                    searchResultDetailDataArrayList.add(new SearchResultDetailData("금기내용", itemInfoDTO.getPROHBT_CONTENT()));
                }



                Intent intent = new Intent(getApplicationContext(), SearchResultDetail.class);
                intent.putExtra("resultList", searchResultDetailDataArrayList);
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
                        ArrayList<ItemInfoDTO> responseItems = durInfoSearchResultResponse.getItems();

                        for (ItemInfoDTO itemInfoDTO : responseItems) {
                            adapter.addItem(itemInfoDTO.getITEM_NAME(), itemInfoDTO.getENTP_NAME());
                            resultList.add(itemInfoDTO);
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
                        ArrayList<ItemInfoDTO> responseItems = durInfoSearchResultResponse.getItems();

                        for (ItemInfoDTO itemInfoDTO : responseItems) {
                            adapter.addItem(itemInfoDTO.getITEM_NAME(), itemInfoDTO.getENTP_NAME());
                            resultList.add(itemInfoDTO);
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
                        ArrayList<ItemInfoDTO> responseItems = durInfoSearchResultResponse.getItems();

                        for (ItemInfoDTO itemInfoDTO : responseItems) {
                            adapter.addItem(itemInfoDTO.getITEM_NAME(), itemInfoDTO.getENTP_NAME());
                            resultList.add(itemInfoDTO);
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
                        ArrayList<ItemInfoDTO> responseItems = durInfoSearchResultResponse.getItems();

                        for (ItemInfoDTO itemInfoDTO : responseItems) {
                            adapter.addItem(itemInfoDTO.getITEM_NAME(), itemInfoDTO.getENTP_NAME());
                            resultList.add(itemInfoDTO);
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
                        ArrayList<ItemInfoDTO> responseItems = durInfoSearchResultResponse.getItems();

                        for (ItemInfoDTO itemInfoDTO : responseItems) {
                            adapter.addItem(itemInfoDTO.getITEM_NAME(), itemInfoDTO.getENTP_NAME());
                            resultList.add(itemInfoDTO);
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
                        ArrayList<ItemInfoDTO> responseItems = durInfoSearchResultResponse.getItems();

                        for (ItemInfoDTO itemInfoDTO : responseItems) {
                            adapter.addItem(itemInfoDTO.getITEM_NAME(), itemInfoDTO.getENTP_NAME());
                            resultList.add(itemInfoDTO);
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
                        ArrayList<ItemInfoDTO> responseItems = durInfoSearchResultResponse.getItems();

                        for (ItemInfoDTO itemInfoDTO : responseItems) {
                            adapter.addItem(itemInfoDTO.getITEM_NAME(), itemInfoDTO.getENTP_NAME());
                            resultList.add(itemInfoDTO);
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
                        ArrayList<ItemInfoDTO> responseItems = durInfoSearchResultResponse.getItems();

                        for (ItemInfoDTO itemInfoDTO : responseItems) {
                            adapter.addItem(itemInfoDTO.getITEM_NAME(), itemInfoDTO.getENTP_NAME());
                            resultList.add(itemInfoDTO);
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
