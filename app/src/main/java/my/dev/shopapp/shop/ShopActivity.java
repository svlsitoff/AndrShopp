package my.dev.shopapp.shop;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import my.dev.shopapp.MainActivity;
import my.dev.shopapp.R;
import my.dev.shopapp.models.Product;
import my.dev.shopapp.models.User;

/*
* This class implements a user dialog,
* in which the registered user has
*  the ability to add / remove items
* from the cart. And also add your
* own products to the cart after
* the pop-up dialogue.*/
public class ShopActivity extends AppCompatActivity implements DialogInterface.OnClickListener {

    ArrayList<Product> products;
    ListView productList;
    TextView textView;
    User user;
    ProductAdapter adapter;
    /*At the first launch, we determine the user
     who launched the activity, and also
     fill the basket with initial data from the list.*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        products = new ArrayList<>();
        textView = findViewById(R.id.textV);
        Bundle arguments = getIntent().getExtras();
        user=null;
        //if the user is not null his name is displayed in the window title.
        if(arguments!=null){
            user = (User) arguments.getSerializable(User.class.getSimpleName());
            textView.setText("Hello! "+user.getName() + "\n Here you can delete or add products to the grocery cart.");
        }
        if(products.size()==0){
            products.add(new Product("Potato", 20));
            products.add(new Product("Tea", 10));
            products.add(new Product("Eggs", 1.45));
            products.add(new Product("Milc", 0.46));
            products.add(new Product("Pasta", 1.33));
        }
        productList = (ListView) findViewById(R.id.productList);
        //we define the adapter class for the list of products, the adapter also adds information about displaying the units of products.
        adapter = new ProductAdapter(this, R.layout.product_item, products);
        adapter.notifyDataSetChanged();
        productList.setAdapter(adapter);
    }
    public void AddNewProduct(View view){
        AddProductDialog addProductDialog = new AddProductDialog();
        addProductDialog.show(getSupportFragmentManager(),"dialog");
    }
   /*Determine the operation of the dialog buttons, as well as the use of data from the dialog box forms.*/
    @Override
    public void onClick( DialogInterface dialog, int whichButton ) {
        Dialog x = (Dialog)dialog;
        EditText editNameFromDialog = (EditText)x.findViewById(R.id.editNameProductDialog);
        EditText editCostFromDialog = (EditText)x.findViewById(R.id.editCostProductDialog);
        if ( whichButton == DialogInterface.BUTTON_POSITIVE ) {
            String name = editNameFromDialog.getText().toString();
            double cost =0;
            name = name.trim();
            boolean unicproduct = Unical(name,products);
            String coststr = editCostFromDialog.getText().toString();
            boolean correct = tryParseDouble(coststr);
            if(correct) cost = Double.parseDouble(coststr);
            if(cost>0 && !name.isEmpty()&&unicproduct){
                products.add(new Product(name,cost));
                adapter.notifyDataSetChanged();

            }else {
                editNameFromDialog.setText("");
                editCostFromDialog.setText("0");
            }
        }else {
            x.cancel();
        }
    }
    //this method checks the correctness of entering numerical data on the cost of the product
    boolean tryParseDouble(String value) {
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    /*this method checks the entered product names for uniqueness*/
    boolean Unical(String item , List<Product> items){

        for (Product product : items){
            if(product.getName().equals(item)) return false;
        }
        return true;

    }
    //this method displays a pop-up message with payment information
    public  void ShowPaymentInformation(View view){
        String info = "";
        DecimalFormat df = new DecimalFormat("#.##");
        info +="User name :"+ user.getName()+"\n";
        double totalCost = 0;
        int AllKindProducts = 0;
        for(Product item : products){
            if(item.getCount()>0){
                double costItem  = item.GetCost();

                info+="Product name :"+item.getName()+" Prise : "+item.getPrice()+" Count :" + item.getCount()+" Cost : " +df.format(costItem)+"\n";
                AllKindProducts++;
                totalCost+=item.GetCost();

            }

        }
        if(AllKindProducts>0){
            info+=" Total cost : " +df.format(totalCost)+"\n";
        }else {
            info+=" Nothing has been purchased yet ";
        }
        Toast.makeText(getApplicationContext(), info, Toast.LENGTH_SHORT).show();
    }
    public void  GoMain(View view){
        Intent intent = new Intent(ShopActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

}