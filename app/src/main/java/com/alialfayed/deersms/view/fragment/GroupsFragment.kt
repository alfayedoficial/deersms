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
import com.alialfayed.deersms.model.GroupFirebase
import com.alialfayed.deersms.view.adapter.GroupAdabter
import com.alialfayed.deersms.view.adapter.PendingAdabter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

/**
 * A simple [Fragment] subclass.
 */
class GroupsFragment : Fragment() {

    lateinit var groupAdabter : GroupAdabter
    lateinit var mrecyclerView: RecyclerView
    lateinit var mdatabaseReference : DatabaseReference
    internal lateinit var groupsList: ArrayList<GroupFirebase>
    internal lateinit var view: View



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_groups, container, false)
        mrecyclerView = view.findViewById(R.id.recyclerView_Group)
        mdatabaseReference =  FirebaseDatabase.getInstance().getReference("Groups")
        mdatabaseReference.keepSynced(true)
        groupsList = ArrayList()
        return view
    }

    override fun onStart() {
        super.onStart()
        mdatabaseReference.addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                groupsList.clear()
                for (groupSnapShot in p0.children){
                    val group = groupSnapShot.getValue(GroupFirebase::class.java)
                    if (group!!.getUserID() ==FirebaseAuth.getInstance().currentUser!!.uid){
                        groupsList.add(group)
                    }
                    setGroupsListAdabter()
                }
                if (groupsList.size <=0){
                    view.findViewById<LinearLayout>(R.id.layoutNoHaveItem_GroupsFragent).visibility = View.VISIBLE
                }else{
                    view.findViewById<LinearLayout>(R.id.layoutNoHaveItem_GroupsFragent).visibility = View.GONE
                }

            }

        })
    }

    private fun setGroupsListAdabter(){
        val linearLayoutManager = LinearLayoutManager(activity)
        mrecyclerView!!.layoutManager = linearLayoutManager
        groupAdabter = GroupAdabter(this.activity!!)
        mrecyclerView!!.adapter = groupAdabter
        groupAdabter.setDataToAdapter(groupsList)

    }


}
