package com.kanyideveloper.letsgoshopping


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var nFirebaseDatabase: FirebaseDatabase
    var itemList : ArrayList<Item> ? = null
    private lateinit var reference: DatabaseReference
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nFirebaseDatabase = FirebaseDatabase.getInstance()
        mRecyclerView = findViewById(R.id.recyclerView)

        shopping_cart.setOnClickListener {
            startActivity(Intent(applicationContext,CheckoutActivity::class.java))
        }

        itemList = arrayListOf()
        reference = FirebaseDatabase.getInstance().getReference("items")


        reference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    for (i in p0.children) {
                        val itm = i.getValue(Item::class.java)
                        itemList!!.add(itm!!)
                        Log.d(TAG, "onDataChange: $itemList.toString()")
                    }

                    val adapter = ItemsAdapter(applicationContext, itemList!!)
                    mRecyclerView.adapter = adapter
                }
            }
        })
    }
}