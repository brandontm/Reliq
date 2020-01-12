/*
 * Copyright (C) 2019  Brandon Tirado
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.brandontm.reliq.ui.contacts.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.brandontm.reliq.R
import com.brandontm.reliq.data.model.entities.Contact
import kotlinx.android.synthetic.main.layout_contact_card.view.*

class ContactListAdapter : RecyclerView.Adapter<ContactListAdapter.ViewHolder>() {
    var onContactClicked: ((contact: Contact) -> Unit)? = null
    var onItemLongClick: ((item: Contact, position: Int) -> Unit)? = null
    var onItemSelectedChanged: ((item: Contact, position: Int, isSelected: Boolean) -> Unit)? = null

    var isSelectable: Boolean = false
    set(value) {
        field = value

        if(!isSelectable) selectedItems.clear()

        notifyDataSetChanged()
    }
    private val selectedItems: MutableMap<Int, Contact> = mutableMapOf()

    private var items = mutableListOf<Contact>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.layout_contact_card, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.itemView) {
            val contact = items[position]

            chk_selected.isChecked = selectedItems.containsKey(position)
            chk_selected.visibility =
                if(isSelectable) View.VISIBLE
                else View.GONE

            card_contact.setOnLongClickListener {
                if (onItemLongClick != null) {
                    onItemLongClick?.invoke(contact, position)
                    true
                } else { false }
            }

            card_contact.setOnClickListener {
                if(isSelectable) {
                    itemSwitchSelected(position)
                } else onContactClicked?.invoke(contact)
            }
            lbl_contact_name.text = contact.name
            lbl_contact_score.text = "${contact.score} <3"
        }
    }

    override fun getItemCount(): Int = items.size

    fun getSelectedItems(): Map<Int, Contact> {
        return selectedItems
    }

    fun itemSwitchSelected(position: Int) {
        val contact = items[position]

        if(selectedItems.containsKey(position)) {
            selectedItems.remove(position)
            onItemSelectedChanged?.invoke(contact, position, false)
        } else {
            selectedItems[position] = contact
            onItemSelectedChanged?.invoke(contact, position, true)
        }

        notifyItemChanged(position)
    }

    fun updateItems(items: List<Contact>) {
        this.items = items.toMutableList()
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
}
