package pe.edu.cibertec.reportaya.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import pe.edu.cibertec.reportaya.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val user = binding.etUser.text?.toString()?.trim().orEmpty()
            val password = binding.etPassword.text?.toString().orEmpty()
            binding.tilUser.error = if (user.isBlank()) getString(pe.edu.cibertec.reportaya.R.string.error_user_required) else null
            binding.tilPassword.error = if (password.isBlank()) getString(pe.edu.cibertec.reportaya.R.string.error_password_required) else null
            if (user.isNotBlank() && password.isNotBlank()) {
                startActivity(Intent(this, IncidentListActivity::class.java))
            }
        }
    }
}
