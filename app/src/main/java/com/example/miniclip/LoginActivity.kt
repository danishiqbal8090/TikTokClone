package com.example.miniclip

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.ItemTouchUIUtil
import com.example.miniclip.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import java.util.UUID

class LoginActivity : AppCompatActivity() {
    lateinit var binding:ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityLoginBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        binding.submitBtn.setOnClickListener{
            login()
        }
        binding.goToSignupBtn.setOnClickListener{
            startActivity(Intent(this,SignupActivity::class.java))
            finish()
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    fun setInProgress(inProgress:Boolean)
    {
        if(inProgress)
        {
            binding.progressBar.visibility= View.VISIBLE
            binding.submitBtn.visibility= View.GONE
        }
        else
        {
            binding.progressBar.visibility= View.GONE
            binding.submitBtn.visibility= View.VISIBLE

        }

    }

    fun login()
    {
        val email=binding.emailInput.text.toString()
        val password=binding.passwordInput.text.toString()

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
        loginWithFirebase(email,password)
    }
    fun loginWithFirebase(email:String, password:String)
    {
        setInProgress(true)
        FirebaseAuth.getInstance().signInWithEmailAndPassword(
            email,
            password
        ).addOnSuccessListener {
//            UIUtil.showToast(this,"Login successfully")
            setInProgress(false)
        }
    }

}
