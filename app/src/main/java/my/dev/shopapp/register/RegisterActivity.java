package my.dev.shopapp.register;

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

import my.dev.shopapp.MainActivity;
import my.dev.shopapp.R;
import my.dev.shopapp.models.User;

/* In this class, we define the
work for registering a new user,
 the work takes place through the
  sqlitehelper class, it is through
   it that we determine whether there
    is a user in the database with the
    same names, and also add a new
    user to the database*/
public class RegisterActivity extends AppCompatActivity {

    Button SaveBtn,CancelBtn;
    EditText Elogin,Epassword,ERepPassword;
    public static SqliteUserHelper sqLiteHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        SaveBtn = findViewById(R.id.ConfirmBtn);
        CancelBtn = findViewById(R.id.CancelBtn);
        Elogin = findViewById(R.id.editLogin);
        Epassword = findViewById(R.id.editPassword);
        ERepPassword = findViewById(R.id.editRepPassword);
        sqLiteHelper = MainActivity.sqLiteHelper;
        SaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String login = Elogin.getText().toString().trim();
                String password = Epassword.getText().toString().trim();
                String repeatPassword = ERepPassword.getText().toString().trim();
                if(CorrectInput(login,password,repeatPassword)){
                    if(CorrectInput(password,repeatPassword)){
                        try {
                            final List<User> userList = new ArrayList<>();
                            Cursor cursor = sqLiteHelper.getData("SELECT * FROM USERS");
                            while (cursor.moveToNext()) {
                                int id = cursor.getInt(0);
                                String userLogin = cursor.getString(1);
                                String userPassword = cursor.getString(2);
                                userList.add(new User(id,userLogin,userPassword));
                            }
                            if(!userList.isEmpty()){
                                for(User user : userList ){
                                    if(user.getName().equals(login)){
                                        Toast.makeText(getApplicationContext(), "A user with the same username exists. Please enter a different name for registration.", Toast.LENGTH_SHORT).show();
                                    }else {
                                        sqLiteHelper.insertData(login, password);
                                        Toast.makeText(getApplicationContext(), "User add succefully!", Toast.LENGTH_SHORT).show();
                                    }
                                    break;
                                }
                            }else {
                                sqLiteHelper.insertData(login, password);
                                Toast.makeText(getApplicationContext(), "User add succefully!", Toast.LENGTH_SHORT).show();
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }else {
                        Toast.makeText(getApplicationContext(), "To confirm registration, the passwords in the input fields must match.", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(), "Input fields must have at least 6 characters", Toast.LENGTH_SHORT).show();
                }
            }
        });
        CancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });

    }
    boolean CorrectInput(String log,String pas1,String pas2){
        if(log.length()<6&&pas1.length()<6&&pas2.length()<6) return false;
        return true;
    }
    boolean CorrectInput(String pas1,String pas2){
        if(pas1.equals(pas2)) return true;
        return false;
    }



}