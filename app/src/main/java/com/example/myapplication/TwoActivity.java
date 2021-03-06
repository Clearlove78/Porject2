package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class TwoActivity extends Activity implements CartAdapter.RefreshPriceInterface ,View.OnClickListener{

    private ListView listView;
    private CheckBox cb_check_all;
    private TextView tv_total_price;
    private TextView tv_go_to_pay;

    private CartAdapter adapter;

    private double totalPrice = 0.00;
    private int totalCount = 0;
    private List<HashMap<String,String>> goodsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.two);

        initDate();
    }

    private void priceControl(Map<String, Integer> pitchOnMap){
        totalCount = 0;
        totalPrice = 0.00;
        for(int i=0;i<goodsList.size();i++){
            if(pitchOnMap.get(goodsList.get(i).get("id"))==1){
                totalCount=totalCount+Integer.valueOf(goodsList.get(i).get("count"));
                double goodsPrice=Integer.valueOf(goodsList.get(i).get("count"))*Double.valueOf(goodsList.get(i).get("price"));
                totalPrice=totalPrice+goodsPrice;
            }
        }
        tv_total_price.setText("$ "+totalPrice);
        tv_go_to_pay.setText("Pay：("+totalCount+")");
    }

    @Override
    public void refreshPrice(Map<String, Integer> pitchOnMap) {
        priceControl(pitchOnMap);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.all_chekbox:
                AllTheSelected();
                break;
            case R.id.tv_go_to_pay:
                if(totalCount<=0){
                    Toast.makeText(this,"select product",Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(this,"good",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void AllTheSelected(){
        Map<String,Integer> map=adapter.getPitchOnMap();
        boolean isCheck=false;
        boolean isUnCheck=false;
        Iterator iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            if(Integer.valueOf(entry.getValue().toString())==1)isCheck=true;
            else isUnCheck=true;
        }
        if(isCheck==true&&isUnCheck==false){
            for(int i=0;i<goodsList.size();i++){
                map.put(goodsList.get(i).get("id"),0);
            }
            cb_check_all.setChecked(false);
        }else if(isCheck==true && isUnCheck==true){
            for(int i=0;i<goodsList.size();i++){
                map.put(goodsList.get(i).get("id"),1);
            }
            cb_check_all.setChecked(true);
        }else if(isCheck==false && isUnCheck==true){
            for(int i=0;i<goodsList.size();i++){
                map.put(goodsList.get(i).get("id"),1);
            }
            cb_check_all.setChecked(true);
        }
        priceControl(map);
        adapter.setPitchOnMap(map);
        adapter.notifyDataSetChanged();
    }

    private void initView(){
        listView = (ListView) findViewById(R.id.listview);
        cb_check_all = (CheckBox) findViewById(R.id.all_chekbox);
        tv_total_price = (TextView) findViewById(R.id.tv_total_price);
        tv_go_to_pay = (TextView) findViewById(R.id.tv_go_to_pay);
        tv_go_to_pay.setOnClickListener(this);
        cb_check_all.setOnClickListener(this);

        adapter=new CartAdapter(this,goodsList);
        adapter.setRefreshPriceInterface(this);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void initDate(){
        goodsList=new ArrayList<>();
        for(int i=0;i<5;i++){
            HashMap<String,String> map=new HashMap<>();
            map.put("id",(new Random().nextInt(10000)%(10000-2900+2900) + 2900)+"");
            map.put("name","Cart "+(i+1)+" product");
            map.put("price",(new Random().nextInt(100)%(100-29+29) + 29)+"");
            map.put("count",("1"));
            goodsList.add(map);
        }

        initView();
    }

}
