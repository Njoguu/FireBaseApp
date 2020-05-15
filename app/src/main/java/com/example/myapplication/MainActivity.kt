package com.example.myapplication

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        show all users in  UsersActivity
        img_users.setOnClickListener {
            startActivity(Intent(this, UsersActivity::class.java))
        }

//        show password
        img_show.setOnClickListener {
            showPassword()
        }

//        add data into database
        btn_signup.setOnClickListener {
            addUser()
        }

    }

    fun addUser(){
//        grab data from edit text
        val firstname = et_firstname.text.trim().toString()
        val lastname = et_lastname.text.trim().toString()
        val email = et_email.text.trim().toString()
        val password = et_password.text.trim().toString()
        val id = System.currentTimeMillis() //time in mili-sec

        //initialize show progress bar
        val appProgress = showProgress()

//        validate input
        if (firstname.isEmpty() || lastname.isEmpty() || email.isEmpty() || password.isEmpty()){
            showMessage("Empty fields", "Please make sure all fields have the required data")
        }else{
//            firebase database
//            1.Save data into the database
            val my_ref = FirebaseDatabase.getInstance().reference.child("Users/$id")
//            143431234 || john || Developer || email || password
            val user_object = UserSchema(firstname, lastname, email, password)

//            show progress as data is being added
            appProgress.show()
            my_ref.setValue(user_object).addOnCompleteListener{task ->
                appProgress.dismiss()
                if(task.isSuccessful){
                    showMessage("Saving successful", "$firstname data has been saved successfully")
                    clearEditText()
                }else{
                    showMessage("Saving unsuccessful", "Data has not been saved")
                }
            }
        }

    }

//    show message
    fun showMessage(title: String, message: String){
        val dialogBox = AlertDialog.Builder(this)
        dialogBox.setTitle(title)
        dialogBox.setMessage(message)
        dialogBox.setPositiveButton("OK", { dialog, which -> dialog.dismiss() })
        dialogBox.create().show()
    }

//    progress bar
    fun showProgress():ProgressDialog{
        val progress = ProgressDialog(this)
        progress.setTitle("Saving...")
        progress.setMessage("Please wait as data is being saved")
        return progress
    }

    fun clearEditText(){
        et_firstname.setText(null)
        et_lastname.setText(null)
        et_email.setText(null)
        et_password.setText(null)
    }

    fun showPassword(){
        if (img_show.tag.toString().equals("Show")){
            et_password.transformationMethod = HideReturnsTransformationMethod.getInstance()
            img_show.tag = "Hide"
        }else{
            et_password.transformationMethod = PasswordTransformationMethod.getInstance()
            et_password.tag = "Show"
        }
    }


}
