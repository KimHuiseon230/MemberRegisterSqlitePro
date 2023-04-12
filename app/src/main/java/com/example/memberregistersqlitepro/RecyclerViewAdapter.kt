package com.example.memberregistersqlitepro

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.memberregistersqlitepro.databinding.ItemRecyclerBinding

class RecyclerViewAdapter(var dataList: MutableList<Member>) :
    RecyclerView.Adapter<RecyclerViewAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val binding = holder.binding
        binding.tvId.text=dataList.get(position).id
        binding.tvName.text = dataList.get(position).name
        binding.tvPhone.text = dataList.get(position).phone
        binding.tvEmail.text = dataList.get(position).email
        binding.tvAddress.text = dataList.get(position).address
        binding.tvLevel.text = dataList.get(position).level
    }

    inner class Holder(val binding: ItemRecyclerBinding) : RecyclerView.ViewHolder(binding.root)
}
