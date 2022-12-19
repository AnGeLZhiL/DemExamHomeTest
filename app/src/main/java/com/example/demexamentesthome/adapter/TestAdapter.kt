package com.example.demexamentesthome.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.demexamentesthome.Global
import com.example.demexamentesthome.R
import com.example.demexamentesthome.databinding.FragmentClickBinding
import com.example.demexamentesthome.model.DepartamentModel

class TestAdapter(val listner: Listener) : RecyclerView.Adapter<TestAdapter.TestViewHolder>() {

    class TestViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView) {
        val binding = FragmentClickBinding.bind(itemView)
        fun bind(departamentModel: DepartamentModel, listner: Listener) = with(binding){
            boss.text = departamentModel.boss

        }
    }

    interface Listener {
        fun onClickDepartament(departamentModel: DepartamentModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestViewHolder {
        return TestViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_departament, parent, false))
    }

    override fun onBindViewHolder(holder: TestViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount() = Global.departaments.size
}