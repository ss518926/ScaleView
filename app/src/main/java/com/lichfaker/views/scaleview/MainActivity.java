package com.lichfaker.views.scaleview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;

import com.lichfaker.scaleview.HorizontalScaleScrollView;

public class MainActivity extends AppCompatActivity {

    EditText mTvHorizontalScale;
    HorizontalScaleScrollView scaleScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mTvHorizontalScale = (EditText) findViewById(R.id.horizontalScaleValue);


        scaleScrollView = (HorizontalScaleScrollView) findViewById(R.id.horizontalScale);
        scaleScrollView.setOnScrollListener(new HorizontalScaleScrollView.OnScrollListener() {
            @Override
            public void onScaleScroll(int scale) {
                mTvHorizontalScale.setText("" + scale);
            }
        });


        setPricePoint(mTvHorizontalScale, 100000);
    }

    public void setPricePoint(final EditText editText, final float max) {
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                //控制金额格式
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        editText.setText(s);
                        editText.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    editText.setText(s);
                    editText.setSelection(2);
                }

                String input = s.toString().trim();
                if (!TextUtils.isEmpty(input)) {
                    float value = Float.valueOf(input);
                    if (value > max) {
                        s = String.valueOf(max);
                        editText.setText(String.valueOf(max));
                    }
                }

                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        editText.setText(s.subSequence(0, 1));
                        editText.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString().trim()))
                    return;
                float value = Float.valueOf(s.toString().trim());
                int position = (int) (value);
                if (value > max) {
                    position = (int) (max);
                }
                scaleScrollView.setFinal(position);
                //控制游标变化
            }

        });

    }
}
