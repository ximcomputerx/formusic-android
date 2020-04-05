package com.ximcomputerx.formusic.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.ximcomputerx.formusic.R;
import com.ximcomputerx.formusic.util.ToastUtil;

/**
 * @CREATED HACKER
 */
public class CustomSearchView extends LinearLayout implements View.OnClickListener {
    private Context mContext;

    private EditText et_input;

    private ImageView iv_delete;

    private String search;

    private SearchViewListener mListener;

    public CustomSearchView(Context context) {
        super(context);
    }

    public CustomSearchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.item_toolbar_search_input, this);
        initView();
    }

    private void initView() {
        et_input = (EditText) findViewById(R.id.et_input);
        iv_delete = (ImageView) findViewById(R.id.iv_delete);

        iv_delete.setOnClickListener(this);

        et_input.addTextChangedListener(new EditChangedListener());
        et_input.setOnClickListener(this);
        et_input.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    notifyStartSearching(et_input.getText().toString());
                }
                return true;
            }

        });

        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(et_input, 0);
    }

    private class EditChangedListener implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            if (!"".equals(charSequence.toString())) {
                iv_delete.setVisibility(VISIBLE);
            } else {
                iv_delete.setVisibility(GONE);
            }
            if (mListener != null) {
                mListener.onRefreshAutoComplete(charSequence.toString());
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    }

    private void notifyStartSearching(String text) {
        if (!TextUtils.isEmpty(text)) {
            search = text;
        } else {
            ToastUtil.showShortToast("请输入关键字");
        }
        if (mListener != null) {
            mListener.onSearch(et_input.getText().toString());
        }
        hideKeyboard();
    }

    public void hideKeyboard() {
        try {
            InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(et_input.getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.et_input:
                break;
            case R.id.iv_delete:
                et_input.setText("");
                iv_delete.setVisibility(GONE);
                break;
        }

    }

    public interface SearchViewListener {

        void onRefreshAutoComplete(String text);

        void onSearch(String text);
    }

    public void setSearchViewListener(SearchViewListener listener) {
        mListener = listener;
    }
}
