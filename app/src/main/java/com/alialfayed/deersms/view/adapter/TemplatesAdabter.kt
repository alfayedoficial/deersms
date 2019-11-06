package com.alialfayed.deersms.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alialfayed.deersms.R
import com.alialfayed.deersms.model.Template
import kotlinx.android.synthetic.main.template_item.view.*

/**
 * Class do :
 * Created by (Eng Ali)
 */
class TemplatesAdabter(var context: Context) : RecyclerView.Adapter<TemplatesAdabter.MyViewHolder>() {
    var list: List<Template> = ArrayList<Template>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TemplatesAdabter.MyViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.template_item,parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {

        var count = if (list.size > 0) {
            list.size
        } else {
            0
        }
        return count

    }

    override fun onBindViewHolder(holder: TemplatesAdabter.MyViewHolder, position: Int) {
        holder.title.text = list[position].templateText
    }

    fun setAdapter(list: List<Template>){
        this.list = list
        notifyDataSetChanged()
    }


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val title = itemView.textnote
        val title = itemView.text_view_title
    }



}

