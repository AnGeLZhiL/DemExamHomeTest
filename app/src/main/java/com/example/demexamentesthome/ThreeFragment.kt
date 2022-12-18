package com.example.demexamentesthome

import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.demexamentesthome.databinding.FragmentThreeBinding
import okhttp3.*
import okio.IOException
import org.json.JSONObject

class ThreeFragment : Fragment() {
    private lateinit var binding: FragmentThreeBinding
    private val client = OkHttpClient()
    private lateinit var alertDialog: AlertDialog.Builder

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentThreeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        alertDialog = AlertDialog.Builder(activity)

        val request = Request.Builder()
            .url("${Global.base_url}/user/profile")
            .addHeader("Token", Global.token)
            .build()
        client.newCall(request).enqueue(object  : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Handler(Looper.getMainLooper()).post{
                    alertDialog
                        .setTitle("Error")
                        .setMessage("Error INTERNET")
                        .setCancelable(true)
                        .setPositiveButton("Ok") { dialog, it ->
                            dialog.cancel()
                        }
                        .show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val jsonObject = JSONObject(response.body.string()).getJSONObject("content")
                Global.userModel.id = jsonObject.getString("id")
                Global.userModel.nickname = jsonObject.getString("nickName")
                Global.userModel.email = jsonObject.getString("email")
                Global.userModel.city = jsonObject.getString("city")
                Handler(Looper.getMainLooper()).post {
                    binding.nickNameTextView.text = Global.userModel.nickname
                    binding.emailTextView.text = Global.userModel.email
                    binding.cityTextView.text = Global.userModel.city
                }
            }
        })
    }

}