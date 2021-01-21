package my.dev.shopapp.shop;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import my.dev.shopapp.R;
import my.dev.shopapp.models.Product;

/*
* this class defines work with product lists*/
class ProductAdapter extends ArrayAdapter<Product> {
    private LayoutInflater inflater;
    private int layout;
    private ArrayList<Product> productList;

    ProductAdapter(Context context, int resource, ArrayList<Product> products) {
        super(context, resource, products);
        this.productList = products;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder;
        if(convertView==null){
            convertView = inflater.inflate(this.layout, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final Product product = productList.get(position);
        viewHolder.nameView.setText(product.getName());
        viewHolder.priceView.setText(Double.toString(product.getPrice()));
        viewHolder.countView.setText(Integer.toString(product.getCount()));
        viewHolder.removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                product.RemoveProduct();
                viewHolder.countView.setText(Integer.toString(product.getCount()));
            }
        });
        viewHolder.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                product.AddProduct();
                viewHolder.countView.setText(Integer.toString(product.getCount()));
            }
        });
        return convertView;
    }
    private class ViewHolder {
        final Button addButton, removeButton;
        final TextView nameView, priceView, countView;
        ViewHolder(View view){
            addButton = (Button) view.findViewById(R.id.addButton);
            removeButton = (Button) view.findViewById(R.id.removeButton);
            nameView = (TextView) view.findViewById(R.id.nameView);
            priceView = (TextView) view.findViewById(R.id.priceView);
            countView = (TextView) view.findViewById(R.id.countView);
        }
    }
}
