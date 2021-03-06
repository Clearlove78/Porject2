package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CartAdapter extends BaseAdapter {

    private Context context;
    private List<HashMap<String,String>> dataList;
    private ViewHolder vh;
    private Map<String,Integer> pitchOnMap;
    private RefreshPriceInterface refreshPriceInterface;

    public CartAdapter(Context context,List<HashMap<String,String>> list){
        this.context=context;
        this.dataList=list;

        pitchOnMap=new HashMap<>();
        for(int i=0;i<dataList.size();i++){
            pitchOnMap.put(dataList.get(i).get("id"),0);
        }
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        vh=new ViewHolder();
        if(view==null){
            view= LayoutInflater.from(context).inflate(R.layout.item_shopcart_product,null);

            vh.checkBox=(CheckBox)view.findViewById(R.id.check_box);
            vh.icon=(ImageView)view.findViewById(R.id.iv_adapter_list_pic);
            vh.name=(TextView)view.findViewById(R.id.tv_goods_name);
            vh.price=(TextView)view.findViewById(R.id.tv_goods_price);
            vh.type=(TextView)view.findViewById(R.id.tv_type_size);
            vh.num=(TextView)view.findViewById(R.id.tv_num);
            vh.reduce=(TextView)view.findViewById(R.id.tv_reduce);
            vh.add=(TextView)view.findViewById(R.id.tv_add);

            view.setTag(vh);
        }else {
            vh=(ViewHolder)view.getTag();
        }

        if(dataList.size()>0){

            if(pitchOnMap.get(dataList.get(position).get("id"))==0)vh.checkBox.setChecked(false);
            else vh.checkBox.setChecked(true);
            HashMap<String,String> map=dataList.get(position);
            vh.name.setText(map.get("name"));
            vh.type.setText(map.get("type"));
            vh.price.setText("$ "+(Double.valueOf(map.get("price"))));

            vh.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final int index=position;
                    if(((CheckBox)view).isChecked())pitchOnMap.put(dataList.get(index).get("id"),1);else pitchOnMap.put(dataList.get(index).get("id"),0);
                    refreshPriceInterface.refreshPrice(pitchOnMap);
                }
            });
        }

        return view;
    }

    public Map<String,Integer> getPitchOnMap(){
        return pitchOnMap;
    }
    public void setPitchOnMap(Map<String,Integer> pitchOnMap){
        this.pitchOnMap=new HashMap<>();
        this.pitchOnMap.putAll(pitchOnMap);
    }

    public interface RefreshPriceInterface{
        void refreshPrice(Map<String,Integer> pitchOnMap);
    }
    public void setRefreshPriceInterface(RefreshPriceInterface refreshPriceInterface){
        this.refreshPriceInterface=refreshPriceInterface;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getCount() {
        if (dataList != null) {
            return dataList.size();
        } else {
            return 0;
        }
    }

    class ViewHolder{
        CheckBox checkBox;
        ImageView icon;
        TextView name,price,num,type,reduce,add;
    }

}
