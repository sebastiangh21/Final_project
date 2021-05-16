package com.sgh21.finalproject

import ActivityUtils.minimumLength
import ActivityUtils.EMPTY
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
    private var check = arrayOf(false,false)

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

            if(!validateEmail(loginBinding.emailLoginEditText.text.toString())){
                check[0] = false
                loginBinding.emailLoginTextInputLayout.error = getString(R.string.email_error)
            }else{
                loginBinding.emailLoginTextInputLayout.error = null
                check[0] = true
            }
            enableButton()
        }

        loginBinding.passwordEditText.doAfterTextChanged {
            password = loginBinding.passwordEditText.text.toString()
            if (!minimumLength(password)){
                loginBinding.passwordLoginTextInputLayout.error = getString(R.string.password_error)
                check[1] = false
            }else{
                loginBinding.passwordLoginTextInputLayout.error = null
                check[1] = true
            }
            enableButton()
        }

        loginBinding.signUpTextView.setOnClickListener {
            val  intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun enableButton() {
        loginBinding.loginButton.isEnabled = check[0] && check[1]
    }

    private fun mainactivity() {
        val  intent = Intent(this, MainActivity::class.java)
        intent.putExtra("user",user)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }
}