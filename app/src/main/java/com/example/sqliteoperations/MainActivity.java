// This App is simply storing updatinga and deleting user data using SQLite
//SQLite is a Structure query base database, open source
/*Whenever an application needs to store large amount of data
then using sqlite is more preferable than other*/
/*
----------------------CREATINGA AND UPDATING DATABASE IN ANDROID------
For creating, updating and other operations you need to create a subclass or SQLiteOpenHelper class.
SQLiteOpenHelper is a helper class to manage database creation and version management.
It provides two methods onCreate(SQLiteDatabase db),
onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion).
*/
package com.example.sqliteoperations;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import static android.os.Message.*;
import static com.example.sqliteoperations.Message.message;

public class MainActivity extends AppCompatActivity {
    EditText Name, Pass , updateold, updatenew, delete;
    //Creating myDbAdapter object/variable
    myDbAdapter helper;
/*
* -----------When an Activity first call or launched
* ------------then onCreate(Bundle savedInstanceState) method is responsible to create the activity.
* ------------When ever orientation(i.e. from horizontal to vertical or vertical to horizontal)
* -------of activity gets changed or
* ------ when an Activity gets forcefully terminated by any Operating System
* ------ then savedInstanceState i.e. object of Bundle Class will save the state of an Activity.
* ---------After Orientation changed then onCreate(Bundle savedInstanceState)
* will call and recreate the activity
* and load all data from savedInstanceState.
Basically Bundle class is used to stored the data of activity whenever above condition occur in app.
onCreate() is not required for apps.
* But the reason it is used in app is because that method is the best place to put initialization code.
You could also put your initialization code in onStart() or
* ----onResume() and when you app will load first, it will work same as in onCreate().

*
 * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //super.onCreate(savedInstanceState);,
        // you tell the Dalvik VM to run your code in addition to the existing code
        // in the onCreate() of the parent class.
        // If you leave out this line,
        // then only your code is run. The existing code is ignored completely
        super.onCreate(savedInstanceState);
        //SetContentView is used to fill the window with the UI provided from layout file
        // incase of setContentView(R. layout. somae_file).
        // Here layoutfile is inflated to view and added to the Activity context(Window)
        setContentView(R.layout.activity_main);
        Name= (EditText) findViewById(R.id.editName);
        Pass= (EditText) findViewById(R.id.editPass);
        updateold= (EditText) findViewById(R.id.editText3);
        updatenew= (EditText) findViewById(R.id.editText5);
        delete = (EditText) findViewById(R.id.editText6);
//calling the constructr of myDbAdapter with the parameter context
//context of the current state of the application. It can be used to get information regarding the activity and application.
// It can be used to get access to resources, databases, and shared preferences, and etc.
// Both the Activity and Application classes extend the Context class.
        helper = new myDbAdapter(this);
    }
    //Function that we created for adding user
    //When user will click the add button this function will be called
    //While writing a click event you might need to know which object is clicked. (View view)
    // In android mostly all the UI components will extend View Class.
    public void addUser(View view)
    {
        //it get the text from Name convert it into string and then store it
        String t1 = Name.getText().toString();
        String t2 = Pass.getText().toString();
        //it checks weather t1 and t2  is empty or not
        if(t1.isEmpty() || t2.isEmpty())
        {
            //message is a method in Class Method it has two parameter one is a context and other is the message it displays
            //Since message method is declared as static it don't need the class to get called
            //if it was not static we could have used it like
            //Message.message(context,"Message you want to display")
            message(getApplicationContext(),"Enter Both Name and Password");

        }
        else
        {
            //if user has entered both the text fields then
            //Here we use helper object of the myDbAdapter class that we have declared above
            //to call insertData() method it takes two parameter username and password
            long id = helper.insertData(t1,t2); //long is a data type used to store if the data is inserted or not
            //here we checks if something was store in id
            //if id is less than or equal to 0
            //then it will display the message that insertion was unsuccessful
            if(id<=0)
            {
                //messae method in Message class explained above
                //takes two parametere the context and message to be dispalayed
                message(getApplicationContext(),"Insertion Unsuccessful");
                Name.setText("");
                Pass.setText("");
            } else
            {
                //messae method in Message class explained above
                //takes two parametere the context and message to be dispalayed
                message(getApplicationContext(),"Insertion Successful");
                Name.setText("");
                Pass.setText("");
            }
        }
    }
//This is anothet method  called when user click on the View Data Button
    public void viewdata(View view)
    {
        // here we are declaring a variable data with the String type
        //helper is the object of myDbAdapter class
        //it helps us in calling different method of myDbAdapter class
        //Here we are calling getData method
        String data = helper.getData();
        //messae method in Message class explained above
        //takes two parametere the context and message to be dispalayed
        message(this,data);
    }
//When user wants to update his data he clicks that update button
    public void update( View view)
    {
        //updateold get the the current name that we want to change
        String u1 = updateold.getText().toString();
        //updatenew get the new name you want to change
        String u2 = updatenew.getText().toString();
        if(u1.isEmpty() || u2.isEmpty())
        {
            message(getApplicationContext(),"Enter Data");
        }
        else
        {
            //helper is the object of myDbAdapter class
            //it helps us in calling different method of myDbAdapter class
            //Here we are calling updateName method

            int a= helper.updateName( u1, u2);
            if(a<=0)
            {
                message(getApplicationContext(),"Unsuccessful");
                updateold.setText("");
                updatenew.setText("");
            } else {
                message(getApplicationContext(),"Updated");
                updateold.setText("");
                updatenew.setText("");
            }
        }

    }
    //delete button function
    public void delete( View view)
    {
        String uname = delete.getText().toString();
        if(uname.isEmpty())
        {
            message(getApplicationContext(),"Enter Data");
        }
        else{
            //        //helper is the object of myDbAdapter class
            //        //it helps us in calling different method of myDbAdapter clas
            //         Here we are calling delete method
            int a= helper.delete(uname);
            if(a<=0)
            {
                message(getApplicationContext(),"Unsuccessful");
                delete.setText("");
            }
            else
            {
                message(this, "DELETED");
                delete.setText("");
            }
        }
    }
}
