package com.example.memberregistersqlitepro

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.memberregistersqlitepro.databinding.ActivityListBinding

class ListActivity : AppCompatActivity() {
    val binding by lazy { ActivityListBinding.inflate(layoutInflater) }
    var mutableList: MutableList<Member> = mutableListOf()
    lateinit  var dataList:RecyclerViewAdapter
    lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        dbHelper = DBHelper(applicationContext, MainActivity.DB_NAME, MainActivity.VERSION)
        mutableList = dbHelper.selectAll()!!
        dataList = RecyclerViewAdapter(mutableList) // RecyclerViewAdapter를 초기화
        binding.recyclerView.adapter = dataList
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        binding.btnMain.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
