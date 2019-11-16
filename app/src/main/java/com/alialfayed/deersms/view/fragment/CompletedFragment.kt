package com.alialfayed.deersms.view.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.alialfayed.deersms.R
import com.alialfayed.deersms.model.MessageFirebase
import com.alialfayed.deersms.view.adapter.CompletedAdabter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

/**
 * A simple [Fragment] subclass.
 */
class CompletedFragment : Fragment() {

    lateinit var completedAdabter: CompletedAdabter
    lateinit var recyclerView_Completed:RecyclerView
    lateinit var mdatabaseReference : DatabaseReference
    internal lateinit var completedList :ArrayList<MessageFirebase>
    internal lateinit var view: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_completed, container, false)
        recyclerView_Completed = view.findViewById(R.id.recyclerView_Completed)
        mdatabaseReference = FirebaseDatabase.getInstance().getReference("Message")
        // Offline Firebase
        mdatabaseReference.keepSynced(true)
        completedList = ArrayList()

        return view
    }

    override fun onStart() {
        super.onStart()
        mdatabaseReference.addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                //TODO Result if Have Error on database
            }

            override fun onDataChange(p0: DataSnapshot) {
                completedList.clear()
                for (completeSnapShot in p0.children){
                    val completedMessage = completeSnapShot.getValue(MessageFirebase::class.java)
                    if (completedMessage!!.getUserID() == FirebaseAuth.getInstance().currentUser!!.uid){
                        if (completedMessage.getSmsStatus() == "Completed"){
                            completedList.add(completedMessage)
                        }
                    }
                    setCompletedListAdabter()
                }
                if (completedList.size <=0){
                    view.findViewById<LinearLayout>(R.id.layoutNoHaveItem_CompletedFragent).visibility = View.VISIBLE
                }else{
                    view.findViewById<LinearLayout>(R.id.layoutNoHaveItem_CompletedFragent).visibility = View.GONE
                }
            }

        })
    }
    fun setCompletedListAdabter(){
        val linearLayoutManager = LinearLayoutManager(activity)
        recyclerView_Completed.layoutManager =linearLayoutManager
        completedAdabter = CompletedAdabter(this.activity!!)
        recyclerView_Completed.adapter = completedAdabter
        completedAdabter.setDataToAdapter(completedList)

    }


}
