package duksung.android.hororok.ugeubi.search;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import duksung.android.hororok.ugeubi.R;
import duksung.android.hororok.ugeubi.retrofit.DURInfoSearchDTO;
import duksung.android.hororok.ugeubi.retrofit.DURInfoSearchResultDTO;
import duksung.android.hororok.ugeubi.retrofit.ItemInfoDTO;
import duksung.android.hororok.ugeubi.retrofit.RetrofitClient;
import duksung.android.hororok.ugeubi.retrofit.RetrofitInterface;
import duksung.android.hororok.ugeubi.search.Search_Result;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class Search_fragment extends Fragment {

    EditText search_keyword;
    ImageButton search_btn;

    ToggleButton dur_none, dur_option1, dur_option2, dur_option3, dur_option4, dur_option5, dur_option6, dur_option7, dur_option8;
    String durType = "None";

    RetrofitInterface apiService;

    public final String PREFERENCE = "ugeubi.preference";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_search, container, false);

        search_keyword = rootView.findViewById(R.id.search_keyword);
        search_btn = rootView.findViewById(R.id.search_btn);

        // DUR 유형 버튼
        dur_none = rootView.findViewById(R.id.none);
        dur_option1 = rootView.findViewById(R.id.durOption1);
        dur_option2 = rootView.findViewById(R.id.durOption2);
        dur_option3 = rootView.findViewById(R.id.durOption3);
        dur_option4 = rootView.findViewById(R.id.durOption4);
        dur_option5 = rootView.findViewById(R.id.durOption5);
        dur_option6 = rootView.findViewById(R.id.durOption6);
        dur_option7 = rootView.findViewById(R.id.durOption7);
        dur_option8 = rootView.findViewById(R.id.durOption8);

        apiService = RetrofitClient.getService();


        // DUR 유형 버튼리스너 설정
        DURTypeBtnOnClickListener durTypeBtnOnClickListener = new DURTypeBtnOnClickListener();
        dur_none.setOnClickListener(durTypeBtnOnClickListener);
        dur_option1.setOnClickListener(durTypeBtnOnClickListener);
        dur_option2.setOnClickListener(durTypeBtnOnClickListener);
        dur_option3.setOnClickListener(durTypeBtnOnClickListener);
        dur_option4.setOnClickListener(durTypeBtnOnClickListener);
        dur_option5.setOnClickListener(durTypeBtnOnClickListener);
        dur_option6.setOnClickListener(durTypeBtnOnClickListener);
        dur_option7.setOnClickListener(durTypeBtnOnClickListener);
        dur_option8.setOnClickListener(durTypeBtnOnClickListener);



        // enter 키 눌렀을 때 검색
        search_keyword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                switch (actionId) {
                    case EditorInfo.IME_ACTION_SEARCH:
                        search();
                        break;
                    default:
                        return false;
                }
                return true;
            }
        });


        // 검색 버튼 눌렀을 때 검색
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });





        return rootView;
    }



    /** DUR유형 버튼 리스너 **/
    class DURTypeBtnOnClickListener implements Button.OnClickListener {
        @Override
        public void onClick(View view) {
            setAllBtnUnChecked();


            switch (view.getId()) {
                case R.id.none :
                    dur_none.setChecked(true);
                    durType = "None";
                    break ;
                case R.id.durOption1 :
                    dur_option1.setChecked(true);
                    durType = "UsjntTaboo";
                    break ;
                case R.id.durOption2 :
                    dur_option2.setChecked(true);
                    durType = "SpcifyAgrdeTaboo";
                    break ;
                case R.id.durOption3 :
                    dur_option3.setChecked(true);
                    durType = "PwnmTaboo";
                    break ;
                case R.id.durOption4 :
                    dur_option4.setChecked(true);
                    durType = "CpctyAtent";
                    break ;
                case R.id.durOption5 :
                    dur_option5.setChecked(true);
                    durType = "MdctnPdAtent";
                    break ;
                case R.id.durOption6 :
                    dur_option6.setChecked(true);
                    durType = "OdsnAtent";
                    break ;
                case R.id.durOption7 :
                    dur_option7.setChecked(true);
                    durType = "EfcyDplct";
                    break ;
                case R.id.durOption8 :
                    dur_option8.setChecked(true);
                    durType = "SeobangjeongPartitnAtent";
                    break ;
            }
        }

        void setAllBtnUnChecked() {
            dur_none.setChecked(false);
            dur_option1.setChecked(false);
            dur_option2.setChecked(false);
            dur_option3.setChecked(false);
            dur_option4.setChecked(false);
            dur_option5.setChecked(false);
            dur_option6.setChecked(false);
            dur_option7.setChecked(false);
            dur_option8.setChecked(false);
        }
    }

    public void search() {
        SharedPreferences pref = getActivity().getSharedPreferences(PREFERENCE, MODE_PRIVATE);
        String accessToken = "Bearer " + pref.getString("accessToken", "");
        DURInfoSearchDTO durInfoSearchDTO = new DURInfoSearchDTO(search_keyword.getText().toString(), "1");

        if (durType.equals("None")) {
            apiService.getDurPrdlstInfoList(accessToken, durInfoSearchDTO).enqueue(new Callback<DURInfoSearchResultDTO>() {
                @Override
                public void onResponse(Call<DURInfoSearchResultDTO> call, Response<DURInfoSearchResultDTO> response) {
                    Log.e("Search", "code: " + response.code());

                    if (response.isSuccessful()) {
                        DURInfoSearchResultDTO durInfoSearchResultResponse = response.body();
                        Log.e("Search", "body.size: " + durInfoSearchResultResponse.getItems().size());

                        for (ItemInfoDTO itemInfoDTO : durInfoSearchResultResponse.getItems()) {
                            Log.e("Search", "ITEM_NAME: " + itemInfoDTO.getITEM_NAME());
                        }

                        Intent intent = new Intent(getActivity(), Search_Result.class);
                        intent.putExtra("resultList", durInfoSearchResultResponse.getItems());
                        startActivity(intent);

                    }

                }

                @Override
                public void onFailure(Call<DURInfoSearchResultDTO> call, Throwable t) {
                    Log.e("Search", "통신실패! " + t.getMessage());
                }
            });
        } else if (durType.equals("UsjntTaboo")) {

        } else if (durType.equals("SpcifyAgrdeTaboo")) {
            apiService.getSpcifyAgrdeTabooInfoList(accessToken, durInfoSearchDTO).enqueue(new Callback<DURInfoSearchResultDTO>() {
                @Override
                public void onResponse(Call<DURInfoSearchResultDTO> call, Response<DURInfoSearchResultDTO> response) {
                    Log.e("Search", "code: " + response.code());

                    if (response.isSuccessful()) {
                        DURInfoSearchResultDTO durInfoSearchResultResponse = response.body();
                        Log.e("Search", "body.size: " + durInfoSearchResultResponse.getItems().size());

                        for (ItemInfoDTO itemInfoDTO : durInfoSearchResultResponse.getItems()) {
                            Log.e("Search", "ITEM_NAME: " + itemInfoDTO.getITEM_NAME());
                        }

                        Intent intent = new Intent(getActivity(), Search_Result.class);
                        intent.putExtra("resultList", durInfoSearchResultResponse.getItems());
                        startActivity(intent);
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
                        Log.e("Search", "body.size: " + durInfoSearchResultResponse.getItems().size());

                        for (ItemInfoDTO itemInfoDTO : durInfoSearchResultResponse.getItems()) {
                            Log.e("Search", "ITEM_NAME: " + itemInfoDTO.getITEM_NAME());
                        }

                        Intent intent = new Intent(getActivity(), Search_Result.class);
                        intent.putExtra("resultList", durInfoSearchResultResponse.getItems());
                        startActivity(intent);
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
                        Log.e("Search", "body.size: " + durInfoSearchResultResponse.getItems().size());

                        for (ItemInfoDTO itemInfoDTO : durInfoSearchResultResponse.getItems()) {
                            Log.e("Search", "ITEM_NAME: " + itemInfoDTO.getITEM_NAME());
                        }

                        Intent intent = new Intent(getActivity(), Search_Result.class);
                        intent.putExtra("resultList", durInfoSearchResultResponse.getItems());
                        startActivity(intent);
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
                        Log.e("Search", "body.size: " + durInfoSearchResultResponse.getItems().size());

                        for (ItemInfoDTO itemInfoDTO : durInfoSearchResultResponse.getItems()) {
                            Log.e("Search", "ITEM_NAME: " + itemInfoDTO.getITEM_NAME());
                        }

                        Intent intent = new Intent(getActivity(), Search_Result.class);
                        intent.putExtra("resultList", durInfoSearchResultResponse.getItems());
                        startActivity(intent);
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
                        Log.e("Search", "body.size: " + durInfoSearchResultResponse.getItems().size());

                        for (ItemInfoDTO itemInfoDTO : durInfoSearchResultResponse.getItems()) {
                            Log.e("Search", "ITEM_NAME: " + itemInfoDTO.getITEM_NAME());
                        }

                        Intent intent = new Intent(getActivity(), Search_Result.class);
                        intent.putExtra("resultList", durInfoSearchResultResponse.getItems());
                        startActivity(intent);
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
                        Log.e("Search", "body.size: " + durInfoSearchResultResponse.getItems().size());

                        for (ItemInfoDTO itemInfoDTO : durInfoSearchResultResponse.getItems()) {
                            Log.e("Search", "ITEM_NAME: " + itemInfoDTO.getITEM_NAME());
                        }

                        Intent intent = new Intent(getActivity(), Search_Result.class);
                        intent.putExtra("resultList", durInfoSearchResultResponse.getItems());
                        startActivity(intent);
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
                        Log.e("Search", "body.size: " + durInfoSearchResultResponse.getItems().size());

                        for (ItemInfoDTO itemInfoDTO : durInfoSearchResultResponse.getItems()) {
                            Log.e("Search", "ITEM_NAME: " + itemInfoDTO.getITEM_NAME());
                        }

                        Intent intent = new Intent(getActivity(), Search_Result.class);
                        intent.putExtra("resultList", durInfoSearchResultResponse.getItems());
                        startActivity(intent);
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