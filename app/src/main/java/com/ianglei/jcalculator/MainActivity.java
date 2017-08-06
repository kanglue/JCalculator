package com.ianglei.jcalculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout containerView;
    private ImageButton plusBtn;
    private ImageButton minusBtn;
    private View tableRow;
    private int num = 1;

    public String TAG = this.getClass().getSimpleName();
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        containerView = (LinearLayout)findViewById(R.id.container);
        findViewById(R.id.equal).setOnClickListener(this);
        addRow();

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
        float total = 0;
        for(int row = 0; row < containerView.getChildCount(); row++)
        {
            View rowView = containerView.getChildAt(row);
            EditText priceText = (EditText)rowView.findViewById(R.id.price);
            EditText discountText = (EditText)rowView.findViewById(R.id.discount);
            EditText account = (EditText)rowView.findViewById(R.id.account);
            PriceObj priceObj = new PriceObj(priceText.getText().toString(), discountText.getText().toString(), account.getText().toString());
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
}
