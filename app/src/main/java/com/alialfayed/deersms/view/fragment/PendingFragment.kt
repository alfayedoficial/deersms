package com.alialfayed.deersms.view.fragment


import android.os.Bundle
import android.os.Message
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.alialfayed.deersms.R
import com.alialfayed.deersms.R.*
import com.alialfayed.deersms.model.MessageFirebase
import com.alialfayed.deersms.view.adapter.PendingAdabter
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_pending.*

/**
 * A simple [Fragment] subclass.
 */
class PendingFragment : Fragment() {

    lateinit var pendingAdabter: PendingAdabter
    lateinit var mrecyclerView: RecyclerView
    lateinit var mdatabaseReference : DatabaseReference
    internal lateinit var pendingList: ArrayList<MessageFirebase>
    internal lateinit var view: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
         view = inflater.inflate(layout.fragment_pending, container, false)
        mrecyclerView  = view.findViewById(R.id.recyclerView_Pending)
        mdatabaseReference = FirebaseDatabase.getInstance().getReference("Message")
        mdatabaseReference.keepSynced(true)
        pendingList = ArrayList()
        return view
    }

    override fun onStart() {
        super.onStart()
        mdatabaseReference.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                pendingList.clear()
                for (pendingSnapShot in p0.children){
                    val pendingMessage = pendingSnapShot.getValue(MessageFirebase::class.java)
                    if (pendingMessage!!.getUserID() == FirebaseAuth.getInstance().currentUser!!.uid){
                       if (pendingMessage.getSmsStatus() == "Pending") {
                           pendingList.add(pendingMessage)
                       }
                    }
                    setPendingListAdabter()
                }
                if (pendingList.size <=0){
                    view.findViewById<LinearLayout>(R.id.layoutNoHaveItem_PendingFragent).visibility = View.VISIBLE
                }else{
                    view.findViewById<LinearLayout>(R.id.layoutNoHaveItem_PendingFragent).visibility = View.GONE
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                //TODO Result if Have Error on database
            }
        })
    }
    private fun setPendingListAdabter(){
        val linearLayoutManager = LinearLayoutManager(activity)
        mrecyclerView!!.layoutManager = linearLayoutManager
        pendingAdabter = PendingAdabter(this.activity!!)
        mrecyclerView!!.adapter =pendingAdabter
        pendingAdabter.setDataToAdapter(pendingList)
    }






}
