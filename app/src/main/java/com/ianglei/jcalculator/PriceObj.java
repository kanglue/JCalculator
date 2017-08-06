package com.ianglei.jcalculator;

import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jianglei on 05/08/2017.
 */


public class PriceObj {

    public String TAG = this.getClass().getSimpleName();

    public static String FLOAT = "^\\d*\\.*\\d{0,2}$";

    public static String ACCOUNT = "^[1-9]\\d*$";

    public static String DISCOUNT = "^100$|^(\\d|[1-9]\\d)$";

    private String price;

    private String discount;

    private String account;

    public PriceObj(String price, String account, String discount)
    {
        this.price = price;
        this.account = account;
        this.discount = discount;
    }

    public boolean isMatch(String regex, String source)
    {
        if(null == regex || null == source)
        {
            return false;
        }

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(source);
        return matcher.matches();
    }

    public boolean isLegal()
    {
        return isLegalPrice(price) && isLegalAccount(account) && isLegalDiscount(discount);
    }

    public boolean isLegalPrice(String price)
    {
        return price != null && !price.trim().equals("") && isMatch(FLOAT, price);
    }

    public boolean isLegalAccount(String account)
    {
        return account != null && !account.trim().equals("") && isMatch(ACCOUNT, account);
    }

    public boolean isLegalDiscount(String discount)
    {
        return discount != null && !discount.trim().equals("") && isMatch(DISCOUNT, discount);
    }

    public float calc()
    {
        Float result = (Float.valueOf(price)) * (Integer.valueOf(account)) * (Integer.valueOf(discount) / 100f);
        Log.d(TAG, "total: " + result);
        return result;
    }
}
