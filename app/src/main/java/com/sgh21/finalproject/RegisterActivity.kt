package com.sgh21.finalproject

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.sgh21.finalproject.databinding.ActivityRegisterBinding
import ActivityUtils.minimumLength
import ActivityUtils.EMPTY
import ActivityUtils.validateEmail
import androidx.core.widget.doAfterTextChanged

class RegisterActivity : AppCompatActivity() {

    private lateinit var registerBinding: ActivityRegisterBinding
    private lateinit var user: User
    private var password: String = EMPTY
    private var repPassword: String = EMPTY

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(registerBinding.root)

        registerBinding.saveButton.setOnClickListener {
            val email = registerBinding.emailEditText.text.toString()
            val name = registerBinding.nameEditText.text.toString()
            val lastName = registerBinding.lastNameEditText.text.toString()
            if (name.isEmpty()) {
                Toast.makeText(this, getString(R.string.name_error), Toast.LENGTH_SHORT).show()
            } else if (lastName.isEmpty()) {
                Toast.makeText(this, getString(R.string.last_name_error), Toast.LENGTH_SHORT).show()
            } else if (!validateEmail(email)) {
                Toast.makeText(this, getString(R.string.email_error), Toast.LENGTH_SHORT).show()
            } else {
                if (password == repPassword && password != EMPTY) {
                    user = User(name, lastName, email, password)
                    goToLoginActivity()
                } else {
                    Toast.makeText(this, getString(R.string.password_errors), Toast.LENGTH_SHORT).show()
                }
            }
        }

        registerBinding.emailEditText.doAfterTextChanged {
            registerBinding.emailTextInputLayout.error = if (!validateEmail(registerBinding.emailEditText.text.toString())) getString(R.string.email_error)  else null
        }

        registerBinding.passwordEditText.doAfterTextChanged {
            password = registerBinding.passwordEditText.text.toString()
            registerBinding.passwordTextInputLayout.error = if (!minimumLength(password)) getString(R.string.password_error) else null
        }

        registerBinding.repPasswordEditText.doAfterTextChanged {
            repPassword = registerBinding.repPasswordEditText.text.toString()
            registerBinding.repPasswordTextInputLayout.error = if (password != repPassword) getString(R.string.coincidence_error) else null
        }
    }

    private fun goToLoginActivity() {
        val  intent = Intent(this, LoginActivity::class.java)
        intent.putExtra("user",user)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }
}