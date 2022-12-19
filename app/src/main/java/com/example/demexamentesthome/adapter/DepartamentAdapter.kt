package com.example.demexamentesthome.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.demexamentesthome.Global
import com.example.demexamentesthome.R
import com.example.demexamentesthome.databinding.RecyclerViewDepartamentBinding
import com.example.demexamentesthome.model.DepartamentModel


class DepartamentAdapter(val listner: Listner) : RecyclerView.Adapter<DepartamentAdapter.DepartamentViewHolder>() {
    class DepartamentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val binding = RecyclerViewDepartamentBinding.bind(itemView)
        fun bind(departamentModel: DepartamentModel, listner: Listner) = with(binding){
            boss.text = departamentModel.boss
            adress.text = departamentModel.address
            itemView.setOnClickListener {
                listner.onClickDepartament(departamentModel)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DepartamentViewHolder {
        return DepartamentViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_departament, parent, false))
    }

    override fun onBindViewHolder(holder: DepartamentViewHolder, position: Int) {
        holder.bind(Global.departaments[position], listner)
    }

    override fun getItemCount() = Global.departaments.size

    interface Listner {
        fun onClickDepartament(departamentModel: DepartamentModel)
    }
}