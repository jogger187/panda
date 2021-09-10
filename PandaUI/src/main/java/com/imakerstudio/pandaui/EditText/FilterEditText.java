
package com.imakerstudio.pandaui.EditText;

import android.content.*;
import android.content.res.*;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.*;
import android.util.*;
import android.widget.EditText;
import com.imakerstudio.pandaui.R;

public class FilterEditText extends EditText implements EditTextInterface{
  private String mLimitChar = "";
  private int mLimitLength = 0;
  private boolean mAlert = false;
  private String mAlertTip = "";
  private String[] mAlertTipArray;
  private int mType;
  private boolean backgroundEnable;
  private Drawable backgroundNormal;
  private Drawable backgroundAlert;

  public FilterEditText(Context context) {
    super(context);
    initView(context, null);
  }

  public FilterEditText(Context context, AttributeSet attrs) {
    super(context, attrs);
    initView(context, attrs);
  }

  private void initView(Context context, AttributeSet attrs){
    mLimitChar = "";
    mAlertTip = "";
    mLimitLength = 0;
    backgroundEnable = false;
    setPadding(15,3,15, 3);

    if (attrs != null) {
      final TypedArray
          attrArray = context.obtainStyledAttributes(attrs, R.styleable.pandaEditText, 0, 0);
      try {
        mAlert = attrArray.getBoolean(R.styleable.pandaEditText_customAlert, false);

        if(attrArray.getString(R.styleable.pandaEditText_customAlertTip) == null){
          mAlertTip = "";
        }else {
          mAlertTip = attrArray.getString(R.styleable.pandaEditText_customAlertTip);
        }

        backgroundEnable = attrArray.getBoolean(R.styleable.pandaEditText_customBackgroundEnable, false);

        if(attrArray.getDrawable(R.styleable.pandaEditText_customBackgroundNormal) == null){
          backgroundNormal = getContext().getDrawable(R.drawable.edit_background);
        }else {
          backgroundNormal = attrArray.getDrawable(R.styleable.pandaEditText_customBackgroundNormal);
        }

        if(attrArray.getDrawable(R.styleable.pandaEditText_customBackgroundAlert) == null){
          backgroundAlert = getContext().getDrawable(R.drawable.edit_background_alert);
        }else {
          backgroundAlert = attrArray.getDrawable(R.styleable.pandaEditText_customBackgroundAlert);
        }

        mType = attrArray.getInteger(R.styleable.pandaEditText_customType, 0);

      } finally {
        attrArray.recycle();
      }
    }
    setAlert(mAlert);


  }

  public void setAlert(boolean tf){
    mAlert = tf;
    if (backgroundEnable){
      if (tf) {
        setBackgroundDrawable(backgroundAlert);
      } else {
        setBackgroundDrawable(backgroundNormal);
      }
    }else {
      setBackground(null);
    }

  }

  @Override
  public boolean getAlert() {
    return mAlert;
  }

  @Override public void setAlertTip(String word) {
    mAlertTip = word;
  }

  @Override public String getAlertTip() {
    return mAlertTip;
  }

  @Override public void setAlertArray(String[] index) {
   mAlertTipArray = index;
  }

  @Override public String getAlertArray(int index) {
    if (index < 0 || index >= mAlertTipArray.length){
      return "";
    }
    return mAlertTipArray[index];
  }

  public void setInputWord(inputType type, int minLength, int maxLength) {
    if (type == inputType.ASCII){
      setKeyListener(DigitsKeyListener.getInstance("abcdefghijklmnopqrstuvwxyz ABCDEFGHIJKLMNOPQRSTUVWXYZ 1234567890 !@#$%^*?"));
    }
  }

  public void setInputInteger(int min, int max) {
    setInputType(InputType.TYPE_CLASS_NUMBER);

    addTextChangedListener(new TextWatcher() {
      String lastDecimalStr;

      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        lastDecimalStr = s.toString();
      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {

        if (null == s || "".contentEquals(s)) {
          setAlert(true);
          return;
        }
        int valueNow = Integer.parseInt(s.toString());
        removeTextChangedListener(this);
        if(valueNow > max){
          setAlert(true);
        }else if(valueNow<min){
          setAlert(true);
        }else {
          setAlert(false);
        }
        addTextChangedListener(this);
      }

      @Override
      public void afterTextChanged(Editable s) {

      }
    });

  }

  public void setInputFloat(double min, double max, int decimalScale){
    setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

    addTextChangedListener(new TextWatcher() {
      String lastDecimalStr;

      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        lastDecimalStr = s.toString();
      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {

        if (null == s || "".contentEquals(s)) {
          setAlert(true);
          return;
        }

        float valueNow = Float.parseFloat(s.toString());
        removeTextChangedListener(this);

        if(valueNow > max){
          setAlert(true);
        }else if(valueNow<min){
          setAlert(true);
        }else {
          setAlert(false);
        }

        String inputString = s.toString();
        if (inputString.contains(".") && inputString.lastIndexOf(".") + 1 + decimalScale < inputString.length()){
          setText(inputString.substring(0, inputString.lastIndexOf(".") + 1 + decimalScale));
          setAlert(false);
          setSelection(inputString.lastIndexOf(".") + 1 + decimalScale);
        }

        addTextChangedListener(this);
      }

      @Override
      public void afterTextChanged(Editable s) {

      }
    });
  }




}
