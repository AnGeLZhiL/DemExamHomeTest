package com.example.demexamentesthome

import com.example.demexamentesthome.model.DepartamentModel
import com.example.demexamentesthome.model.UserModel

class Global {
    companion object{
        val base_url = "http://wsk2019.mad.hakta.pro/api/"
        var token: String = ""
        var userModel = UserModel("", "", "", "")
        var departaments = ArrayList<DepartamentModel>()
        var id_departament: Int? = null
    }
}