package com.sgh21.finalproject

import ActivityUtils.minimumLength
import ActivityUtils.validateEmail
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import com.sgh21.finalproject.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var loginBinding: ActivityLoginBinding
    private lateinit var user: User
    private var password: String = EMPTY

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)

        loginBinding.loginButton.setOnClickListener {

            val email = loginBinding.emailLoginEditText.text.toString()
            password = loginBinding.passwordEditText.text.toString()
            if(intent.extras != null){
                user = intent.extras?.getSerializable("user") as User
                if (user.email == email && user.password == password) {
                    mainactivity()
                }else{
                    Toast.makeText(this, getString(R.string.login_error), Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this, getString(R.string.unregistered_user), Toast.LENGTH_SHORT).show()
            }
        }

        loginBinding.emailLoginEditText.doAfterTextChanged {
            loginBinding.emailLoginTextInputLayout.error = if (!validateEmail(loginBinding.emailLoginEditText.text.toString())) getString(R.string.email_error)  else null
        }

        loginBinding.passwordEditText.doAfterTextChanged {
            password = loginBinding.passwordEditText.text.toString()
            loginBinding.passwordLoginTextInputLayout.error = if (!minimumLength(password)) getString(R.string.password_error) else null
        }

        loginBinding.signUpTextView.setOnClickListener {
            val  intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
    private fun mainactivity() {
        val  intent = Intent(this, MainActivity::class.java)
        intent.putExtra("user",user)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }
}