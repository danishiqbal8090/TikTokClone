package com.example.miniclip

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.miniclip.databinding.ActivitySignupBinding
import com.example.miniclip.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.auth.User
//import com.google.firebase.firestore.firestore

class SignupActivity : AppCompatActivity() {
    lateinit var binding : ActivitySignupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        binding.submitBtn.setOnClickListener{
            signup()
        }
        binding.goToLoginBtn.setOnClickListener{
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
//        binding.goToLoginBtn.setOnClickListener{
//            startActivity(Intent(this.LoginActivity::class.java))
//            finish()
        }
    fun setInProgress(inProgress:Boolean)
    {
        if(inProgress)
        {
            binding.progressBar.visibility= View.VISIBLE
            binding.submitBtn.visibility=View.GONE
        }
        else
        {
                binding.progressBar.visibility= View.GONE
                binding.submitBtn.visibility=View.VISIBLE

        }

    }
    fun signup(){
        val email=binding.emailInput.text.toString()
        val password=binding.passwordInput.text.toString()
        val confirmPassword=binding.confirmPasswordInput.text.toString()

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            binding.emailInput.setError("Email not valid")
                return;
        }
        if(password.length<6)
        {
            binding.passwordInput.setError("Minimum 6 character")
            return
        }
        if(password!=confirmPassword)
        {
            binding.confirmPasswordInput.setError("Password not matched")
            return
        }
        signupWithFirebase(email,password)

    }
    fun signupWithFirebase(email : String, password : String)
    {
        setInProgress(true)
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password).addOnSuccessListener{
                  it.user?.let { user->
                      val userModel = UserModel(user.uid,email,email.substringBefore("@"))
//                          Firebase.firestore.collection("users")
//                          .document(user.uid)
//                          .set(userModel).addOnSuccessListner{

                          }
                  }
        }



    }

