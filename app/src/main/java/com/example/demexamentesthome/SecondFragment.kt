package com.example.demexamentesthome

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.demexamentesthome.adapter.DepartamentAdapter
import com.example.demexamentesthome.databinding.FragmentSecondBinding
import com.example.demexamentesthome.model.DepartamentModel
import okhttp3.*
import okio.IOException
import org.json.JSONObject

class SecondFragment : Fragment(), DepartamentAdapter.Listner {
    private lateinit var binding: FragmentSecondBinding
    private val client = OkHttpClient()
    private val adapter = DepartamentAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val request = Request.Builder()
            .url("http://mad2019.hakta.pro/api/department")
            .build()

        client.newCall(request).enqueue(object : Callback{
            override fun onFailure(call: Call, e: IOException) {
                TODO("Not yet implemented")
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.code == 200){
                    val jsonObject = JSONObject(response.body.string()).getJSONArray("data")
                    for (i in 0 until jsonObject.length()){
                        val array = jsonObject[i] as JSONObject
                        Global.departaments.add(
                            DepartamentModel(
                                id = jsonObject.getJSONObject(i).getInt("id"),
                                address = array.getString("address"),
                                boss = array.getString("boss"),
                                name = array.getString("name"),
                                phone = array.getString("phone"),
                                email = array.getString("email"),
                                description = array.getString("description")
                            )
                        )
                    }
                    Handler(Looper.getMainLooper()).post {
                        binding.recycler.adapter = adapter
                    }
                }
            }

        })
    }

    override fun onClickDepartament(departamentModel: DepartamentModel) {
        findNavController().navigate(R.id.action_secondFragment_to_clickFragment)
        Global.id_departament = departamentModel.id
    }
}