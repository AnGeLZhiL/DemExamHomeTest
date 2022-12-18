package com.example.demexamentesthome

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.example.demexamentesthome.databinding.ActivitySignUpBinding
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okio.IOException
import org.json.JSONObject

class SignUp : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private val emailRegex = "[a-z0-9]+@[a-z]+\\.+[a-z]{2,3}"
    private lateinit var alertDialog: AlertDialog.Builder
    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        alertDialog = AlertDialog.Builder(this)

        binding.signUp.setOnClickListener {
            if (binding.email.text.isNotEmpty() and binding.password.text.isNotEmpty() and binding.nickname.text.isNotEmpty()){
                if (binding.email.text.toString().trim().matches(emailRegex.toRegex())){
                    val requestBody = RequestBody.create(
                        "application/json".toMediaTypeOrNull(),
                        JSONObject()
                            .put("email", binding.email.text.toString())
                            .put("nickName", binding.nickname.text.toString())
                            .put("password", binding.password.text.toString())
                            .put("phone", "89118273827")
                            .toString()
                    )
                    val request = Request.Builder()
                        .url("${Global.base_url}/users")
                        .post(requestBody)
                        .build()
                    client.newCall(request).enqueue(object : Callback {
                        override fun onFailure(call: Call, e: IOException) {
                            alertDialog
                                .setTitle("Error")
                                .setMessage("Error server")
                                .setCancelable(true)
                                .setPositiveButton("Ok"){ dialog, it ->
                                    dialog.cancel()
                                }
                                .show()
                        }

                        override fun onResponse(call: Call, response: Response) {
                            if (response.code == 200){
                                SignIn(binding.email.text.toString(), binding.password.text.toString())
//                                startActivity(Intent(this@SignUp, SignIn::class.java))
                            } else if (response.code == 465) {
                                this@SignUp.runOnUiThread(java.lang.Runnable {
                                    alertDialog
                                        .setTitle("Error")
                                        .setMessage("User with such email already exists")
                                        .setCancelable(true)
                                        .setPositiveButton("Ok"){ dialog, it ->
                                            dialog.cancel()
                                        }
                                        .show()
                                })
                            }
                        }

                    })
                } else binding.email.error = "Некоррекное заполнение"
            } else {
                if (binding.email.text.isEmpty()) binding.email.error = "Пустое поле"
                if (binding.password.text.isEmpty()) binding.password.error = "Пустое поле"
                if (binding.nickname.text.isEmpty()) binding.nickname.error = "Пустое поле"
            }
        }

        binding.signIn.setOnClickListener {
            startActivity(Intent(this, SignIn::class.java))
        }
    }

    private fun SignIn(email: String, password: String) {
        val requestBody = RequestBody.create(
            "application/json".toMediaTypeOrNull(),
            JSONObject()
                .put("email", email)
                .put("password", password)
                .toString()
        )
        val request = Request.Builder()
            .url("${Global.base_url}/user/login")
            .post(requestBody)
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                this@SignUp.runOnUiThread(java.lang.Runnable {
                    alertDialog
                        .setTitle("Error")
                        .setMessage("Kakaya-to erynda")
                        .setCancelable(true)
                        .setPositiveButton("Ok") { dialog, it ->
                            dialog.cancel()
                        }
                        .show()
                })
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body.string()
                if (response.code == 200){
                    Global.token = JSONObject(responseBody).getString("token")
                    println("------------------------------- " + {Global.token})
                    startActivity(Intent(this@SignUp, Menu::class.java))
                    finish()
                }
                else
                    if (response.code == 469){
                        this@SignUp.runOnUiThread(java.lang.Runnable {
                            alertDialog
                                .setTitle("Error")
                                .setMessage("Not found")
                                .setCancelable(true)
                                .setPositiveButton("Ok") { dialog, it ->
                                    dialog.cancel()
                                }
                                .show()
                        })
                    }
            }

        })
    }
}