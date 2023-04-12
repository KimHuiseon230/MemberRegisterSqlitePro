package com.example.memberregistersqlitepro

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.memberregistersqlitepro.databinding.ItemRecyclerBinding

class RecyclerViewAdapter(val mutableList: MutableList<Member>):
    RecyclerView.Adapter<RecyclerViewAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
     val binding =ItemRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return Holder(binding)
    }

    override fun getItemCount(): Int =mutableList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
     val binding = holder.binding
        binding.textName.text= mutableList.get(position).name


    }
    inner class Holder(val binding:ItemRecyclerBinding): RecyclerView.ViewHolder(binding.root)
}