package com.example.sqldelightexamplee

import android.R.attr
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sqldelightexamplee.databinding.ListItemBinding
import example.persondb.PersonEntity


// val persons: kotlinx.coroutines.flow.Flow<List<PersonEntity>>
class DataAdapter : RecyclerView.Adapter<DataAdapter.ProfileViewHolder>() {
    // Initializing binding variable
    private lateinit var binding: ListItemBinding
    // private var listitems = arrayListOf<WatchListModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
        binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProfileViewHolder(binding)
    }

    private var users: List<PersonEntity>? = ArrayList()

//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ProfileViewHolder{
//            val view = LayoutInflater.from(parent.context)
//            .inflate(R.layout.list_item, parent, false)
//
//        return ProfileViewHolder(view)
//    }

    override fun getItemCount() = users?.size ?: 0

    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {

        holder.itemView.setOnClickListener {
            // Display user..
            if (onButtonClickListener != null) onButtonClickListener!!.onButtonCLick(
                position,
                users?.get(position),
                "details"
            )
        }

        holder.nameView.text = ""+users?.get(position)?.firstname

        holder.deleteView.setOnClickListener {
            // Delete from database through interface..
            if (onButtonClickListener != null) onButtonClickListener!!.onButtonCLick(
                position,
                users?.get(position),
                "delete"
            )

            // Remove from list and refresh listener..
            removeUser(position)
        }

    }

    fun removeUser(position: Int){
        //users?.removeAt(position)
        notifyItemRemoved(position)
    }

    fun setUsers(data: List<PersonEntity>) {
        // add new list
        users = data
        // notify adapter
        notifyDataSetChanged()
        Log.d("flow","data size in adapter: "+data.size)
    }

    fun addUser(data: PersonEntity) {
        // users?.add(data)
        notifyItemInserted(users?.size!!)
    }

    var onButtonClickListener: OnButtonClickListener? = null

    @JvmName("setOnButtonClickListener1")
    fun setOnButtonClickListener(onButtonClickListener: OnButtonClickListener?) {
        this.onButtonClickListener = onButtonClickListener
    }

    interface OnButtonClickListener {
        fun onButtonCLick(
            position: Int,
            model: PersonEntity?,
            action: String?
        )
    }

    class ProfileViewHolder(binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        var nameView: TextView
        var deleteView: ImageView
        // var bidView: TextView

        init {
            nameView = binding.nameTv
            deleteView = binding.deleteImageview
            // bidView = binding.bidTv
        }
    }
}