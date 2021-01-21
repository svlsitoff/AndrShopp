package my.dev.shopapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import my.dev.shopapp.models.User;
import my.dev.shopapp.register.RegisterActivity;
import my.dev.shopapp.register.SqliteUserHelper;
import my.dev.shopapp.shop.ShopActivity;

/*The main application class,
it contains the entry point.
As soon as the application starts,
the existence of the database and the
table with users is checked. When
trying to log in, the application checks
the list of users, and if the list
contains users with these properties,
the user is redirected to the store
 to make purchases. If not, then the
 user needs to register.*/
public class MainActivity extends AppCompatActivity {

    Button RegBtn, EntBtn;
    EditText EditLog,EditPassw;

    public static SqliteUserHelper sqLiteHelper;
    List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EntBtn = findViewById(R.id.EnterBtn);
        RegBtn = findViewById(R.id.RegisterBtn);
        EditLog = findViewById(R.id.editTextTextPersonName);
        EditPassw = findViewById(R.id.editTextTextPassword);
        sqLiteHelper = new SqliteUserHelper(this, "Usersdb.sqlite", null, 1);
        sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS USERS(Id INTEGER PRIMARY KEY AUTOINCREMENT, login VARCHAR, password VARCHAR)");
        userList = new ArrayList<>();
        Cursor cursor = sqLiteHelper.getData("SELECT * FROM USERS");
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String userLogin = cursor.getString(1);
            String userPassword = cursor.getString(2);
            userList.add(new User(id,userLogin,userPassword));
        }
        EntBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String login = EditLog.getText().toString().trim();
                String password = EditPassw.getText().toString().trim();
                boolean find = false;
                if(!login.isEmpty()&&!password.isEmpty()){
                    try {
                        if(!userList.isEmpty()){
                            for (User user : userList){
                                if(user.getName().equals(login)&&user.getPassword().equals(password)){
                                    Intent intent = new Intent(MainActivity.this, ShopActivity.class);
                                    intent.putExtra(User.class.getSimpleName(),user);
                                    find = true;
                                    startActivity(intent);
                                    break;
                                }
                            }
                         if(find==false)
                        Toast.makeText(getApplicationContext(), "Incorrect username or password", Toast.LENGTH_SHORT).show();

                        }else {
                            Toast.makeText(getApplicationContext(), "There are no records in the database yet", Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else {
                    Toast.makeText(getApplicationContext(), "Input data is incorrect", Toast.LENGTH_SHORT).show();
                }
            }
        });
        RegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
               startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        userList.clear();
        Cursor cursor = sqLiteHelper.getData("SELECT * FROM USERS");
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String userLogin = cursor.getString(1);
            String userPassword = cursor.getString(2);
            userList.add(new User(id,userLogin,userPassword));
        }

    }
}