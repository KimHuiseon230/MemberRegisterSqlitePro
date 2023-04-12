package com.example.memberregistersqlitepro

import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DBHelper(val context: Context?,val name: String?, val version: Int):
    SQLiteOpenHelper(context, name,null, version) {


    //멤버테이블정의(앱이 설치돼서 DBHelper() 딱 한번만 실행
    override fun onCreate(db: SQLiteDatabase?) {
        if (db != null) {
            db.execSQL(
                "create table memberTBL(" +
                        "id text primary key," +
                        "name text not null," +
                        "password text not null," +
                        "phone text not null," +
                        "email text," +
                        "address text," +
                        "level text)"
            )
        }
    } //onCreate

    //데이타베이스 버전이 바뀔때마다 실행된다.
    // 해당 부분은 db를 줌. 콜백함수를 제공
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("drop table if exists member")
        onCreate(db)
    }

    //memberTBL member 삽입
    fun insertTBL (member: Member): Boolean {
        val db: SQLiteDatabase = this.writableDatabase
        var flag = false
        try {
            db.execSQL("insert into memberTBL values( '${member.id}','${member.name}','${member.password}', '${member.phone}', '${member.email}', '${member.address}', '${member.level}')")
            Log.d("DBHelper", "insert ${member.id} ${member.name} 성공")
            flag = true
        } catch (e: SQLException) {
            flag = false
        }
        return flag
    }


    //memberTBL id가 존재하는지 유무
    fun selectCheckID(id: String): Boolean{
        val db: SQLiteDatabase = this.readableDatabase
        var cursor: Cursor? = null
        var flag = false

        try {
            cursor = db.rawQuery("select id from memberTBL where id = '${id}'", null)
//            cursor.count <=0 // 데이터를 조회 --> 아이디가 없다는 것을 확인 -> 사용가능
            if(cursor.count>=1){
                Log.d("DBHelper", "selectCheckID ${id}가 존재함")
            }else{
                Log.d("DBHelper", "selectCheckID ${id}가 존재하지 않음")
            }
        }catch (e: SQLException){
            Log.d("DBHelper", "selectCheckID ${id} 예외 발생 ${e.printStackTrace()}")
            flag = false
        }
        return flag
    }// selectCheckID end

    //memberTBL 로긴기능
    fun selectLogin(id:String, password: String): Boolean {
        val db: SQLiteDatabase = this.readableDatabase
        var cursor: Cursor? = null
        var flag = false//기본적으로는  false라 굳이 else를 만들 필요가 없음

        try {
            cursor = db.rawQuery("select id, password from memberTBL where id = '${id}' and password = '${password}'", null)

            if(cursor.count>=1){
                Log.d("DBHelper", " selectLogin 로긴 성공")
                flag = true
            }
        }catch (e: SQLException){
            Log.d("DBHelper", " selectLogin 로긴 예외 발생")
        }
        return flag
    }//

    // memberTBL id --> 해당 id의 되는 모든 정보를 class가져온다 record를 가져오는 것
    // null 여부를 통해서 값을 통해서 결정됨. 잘되면 null 돌려주지 x 안되면 null 돌려줌
    fun selectID(id: String): Member?{
        val db: SQLiteDatabase = this.readableDatabase
        var cursor: Cursor? = null
        var member: Member? = null
        try {
            cursor = db.rawQuery("select * from memberTBL where id = '${id}'", null)
            // 여기서는 moveToFirst() 사용
            if(cursor.moveToFirst()){
                Log.d("DBHelper", "selectID ${id} 데이터 정보 로드 성공")
                member = Member(cursor.getString(0), cursor.getString(1),
                    cursor.getString(2), cursor.getString(3),
                    cursor.getString(4), cursor.getString(5),
                    cursor.getString(6))
            }
        } catch (e: SQLException) {
            Log.d("DBHelper", "selectID ${id} 예외 발생 ${e.printStackTrace()}")
        }
        return member
    }

    fun selectAll(): MutableList<Member>? {
        val db: SQLiteDatabase = this.readableDatabase
        var cursor: Cursor? = null
        val mutableList: MutableList<Member>? = null
        try {
            cursor = db.rawQuery("select * from memberTBL", null)
            if (cursor.count >= 1) {
                Log.d("DBHelper", "selectAll 성공 ")
                while (cursor.moveToNext()) {
                    val member = Member(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6)
                    )
                    mutableList?.add(member)
                }
            }
        } catch (e: SQLException) {
            Log.d("DBHelper", "selectAll 예외발생 ${e.printStackTrace()}")
        }
//        db.close()
        return mutableList
    }

    //memberTBL 삭제기능
    fun deleteTBL(id: String): Boolean {
        val db: SQLiteDatabase = this.writableDatabase
        var flag = false
        try {
            if (selectCheckID(id)){
                db.execSQL("delete from memberTBL where id = '${id}'")
                Log.d("DBHelper", "delete ${id} 성공")
                flag = true
            }else{
                Log.d("DBHelper", "delete ${id} 존재하지 않음")
            }
        } catch (e: SQLException) {
            Log.e("DBHelper", "delete ${id} 예외 발생 ${e.printStackTrace()}")
        }
        return flag
    }

    //memberTBl 수정기능
    fun updateTBL(member: Member?): Boolean{
        val db: SQLiteDatabase = this.writableDatabase
        var flag = false
            try {
                if(member != null){
                    //
                db.execSQL(
                    "update memberTBL set phone = '${member?.phone}',name = '${member?.name}',level = '${member?.level}' " +
                            "where id = '${member?.id}'")
                Log.d("DBHelper", "update ${member?.id}  성공")
                flag = true
                }
            } catch (e: SQLException) {
                Log.e("DBHelper", "update 예외 발생 ${e.printStackTrace()}")
            }
        return flag
    }


}// class DBHelper end