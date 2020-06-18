package duksung.android.hororok.ugeubi.ugeubi;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import duksung.android.hororok.ugeubi.R;

public class UgeubiDialog extends Dialog {

    private Button registerButton;
    private Button cancelButton;

    private View.OnClickListener positiveListener;
    private View.OnClickListener negativeListener;

    public UgeubiDialog(Context context, View.OnClickListener positiveListener, View.OnClickListener negativeListener) {
        super(context);
        this.positiveListener = positiveListener;
        this.negativeListener = negativeListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 다이얼로그 외의 화면은 흐리게
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.8f;
        getWindow().setAttributes(layoutParams);

        setContentView(R.layout.ugeubi_dialog);

        registerButton = findViewById(R.id.register_btn);
        cancelButton = findViewById(R.id.cancel_btn);
        registerButton.setOnClickListener(positiveListener);
        cancelButton.setOnClickListener(negativeListener);

    }
}
