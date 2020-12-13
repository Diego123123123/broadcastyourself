package cba.ucb.com.broadcastyoursselft

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        loginButton.setOnClickListener {
            val email = userEmail.text.toString()
            val passowrd = userPassword.text.toString()
            if(email.isBlank() || passowrd.isBlank()) {
                Toast.makeText(this, "Email/password cannot be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val authentication = FirebaseAuth.getInstance()
            authentication.signInWithEmailAndPassword(email, passowrd).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show()
                    goPostsActivity()
                } else {
                    Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun goPostsActivity() {
        val intent = Intent(this, Posts::class.java)
        startActivity(intent)
        finish()
    }
}