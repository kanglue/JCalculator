package com.ianglei.jcalculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout containerView;
    private ImageButton plusBtn;
    private ImageButton minusBtn;
    private View tableRow;
    private int num = 1;

    EditText priceText;
    EditText discountText;
    EditText account;

    EditText price1;
    EditText slot1;
    EditText price2;
    EditText slot2;

    public String TAG = this.getClass().getSimpleName();
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CrashHandler.getInstance().init(this);

        try {
            setContentView(R.layout.activity_main);

            containerView = (LinearLayout) findViewById(R.id.container);
            findViewById(R.id.equal).setOnClickListener(this);
            addRow();
            findViewById(R.id.compare).setOnClickListener(this);
        }
        catch (Exception e)
        {
            Log.d(TAG, e.toString());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.plus:
                addRow();
                break;
            case R.id.minus:
                if(containerView.getChildCount() > 1) {
                    containerView.removeView((LinearLayout) v.getParent());
                }
                break;
            case R.id.equal:
                calc();
                break;
            case R.id.compare:
                compare();
                break;
        }
    }

    private void addRow()
    {
            View rowView = View.inflate(this, R.layout.tablerow, null);
            ImageButton plusBtn = (ImageButton)rowView.findViewById(R.id.plus);
            ImageButton minusBtn = (ImageButton)rowView.findViewById(R.id.minus);
            plusBtn.setOnClickListener(this);
            minusBtn.setOnClickListener(this);
            containerView.addView(rowView);

    }

    private void calc()
    {
        Log.d(TAG, "calc");
        double total = 0;
        for(int row = 0; row < containerView.getChildCount(); row++)
        {
            View rowView = containerView.getChildAt(row);
            priceText = (EditText)rowView.findViewById(R.id.price);
            discountText = (EditText)rowView.findViewById(R.id.discount);
            account = (EditText)rowView.findViewById(R.id.account);

            PriceObj priceObj = new PriceObj(priceText.getText().toString(),
                    discountText.getText().toString(), account.getText().toString());
            if(!priceObj.isLegal())
            {
                Log.d(TAG, "Illegal param");
                Toast.makeText(this, "不合法输入", Toast.LENGTH_SHORT).show();
            }
            else
            {
                total += priceObj.calc();
                Log.d(TAG, "total: " + total);
            }
        }

        TextView totalText = (TextView)findViewById(R.id.total);
        totalText.setText(String.valueOf(total));
    }

    private void compare()
    {
        price1 = (EditText)findViewById(R.id.compareprice1);
        slot1 = (EditText)findViewById(R.id.compareslot1);
        price2 = (EditText)findViewById(R.id.compareprice2);
        slot2 = (EditText)findViewById(R.id.compareslot2);

        String sPrice1 = price1.getText().toString();
        String sSlot1 = slot1.getText().toString();
        String sPrice2 = price2.getText().toString();
        String sSlot2 = slot2.getText().toString();

        if(sPrice1 == "" || sSlot1 == "" || sPrice2 == "" || sSlot2 == "")
        {
            Toast.makeText(this,"不输入吗？",Toast.LENGTH_SHORT).show();
        }
        else
        {
            findViewById(R.id.win2).setVisibility(View.INVISIBLE);
            findViewById(R.id.win1).setVisibility(View.INVISIBLE);

            double first = Arith.div(Double.valueOf(sPrice1), Double.valueOf(sSlot1), 10);
            double second = Arith.div(Double.valueOf(sPrice2), Double.valueOf(sSlot2), 10);
            Log.d(TAG, "first: " + first + " second: " +second);

            if(first > second)
            {
                findViewById(R.id.win2).setVisibility(View.VISIBLE);
            }
            else{
                findViewById(R.id.win1).setVisibility(View.VISIBLE);
            }
        }
    }
}
