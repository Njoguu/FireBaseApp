package com.example.myapplication



import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*



/**
 * Created by Belal on 6/19/2017.
 */

class CustomAdapter(var context: Context, var data: ArrayList<UserSchema>): BaseAdapter() {

    private class ViewHolder(row:View?){
        var firstname:TextView
        var lastname:TextView
        var email:TextView
        var myLinaerLayout:LinearLayout


        init {
            this.firstname = row?.findViewById(R.id.tv_firstname) as TextView
            this.lastname = row?.findViewById(R.id.tv_lastname) as TextView
            this.email = row?.findViewById(R.id.tv_email) as TextView
            this.myLinaerLayout = row?.findViewById(R.id.linearlayoutID) as LinearLayout
        }
    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view:View?
        val viewHolder:ViewHolder
        if (convertView == null){
            val layout = LayoutInflater.from(context)
            view = layout.inflate(R.layout.card_layout,parent,false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        }else{
            view = convertView
            viewHolder = view.tag as ViewHolder
        }
        val userschema:UserSchema = getItem(position) as UserSchema
        viewHolder.firstname.text = userschema.first_name
        viewHolder.lastname.text = userschema.last_name
        viewHolder.email.text = userschema.user_email

        return view as View
    }
    override fun getItem(position: Int): Any {
        return  data.get(position)
    }
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
    override fun getCount(): Int {
        return data.count()
    }
}