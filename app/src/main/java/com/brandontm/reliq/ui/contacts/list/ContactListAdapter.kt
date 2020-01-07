package com.brandontm.reliq.ui.contacts.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.brandontm.reliq.R
import com.brandontm.reliq.data.model.entities.Contact
import kotlinx.android.synthetic.main.layout_contact_card.view.*

class ContactListAdapter : RecyclerView.Adapter<ContactListAdapter.ViewHolder>() {
    var items = mutableListOf<Contact>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.layout_contact_card, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.itemView) {
            lbl_contact_name.text = items[position].name
        }
    }

    fun updateItems(items: List<Contact>) {
        this.items = items.toMutableList()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
}
