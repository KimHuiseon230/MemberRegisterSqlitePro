package com.example.memberregistersqlitepro

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.memberregistersqlitepro.databinding.ActivityListBinding

class ListActivity : AppCompatActivity() {
    val binding by lazy { ActivityListBinding.inflate(layoutInflater) }
    val db = DBHelper(applicationContext, MainActivity.DB_NAME, MainActivity.VERSION)
    lateinit var data: MutableList<Member>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val LinearLayoutManager = RecyclerViewAdapter(data)
        binding.recyclerView.adapter = LinearLayoutManager
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.setHasFixedSize(true)

        binding.btnMain.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}
