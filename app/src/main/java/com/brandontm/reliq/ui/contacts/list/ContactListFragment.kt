package com.brandontm.reliq.ui.contacts.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.brandontm.reliq.R
import com.brandontm.reliq.base.BaseFragment
import com.brandontm.reliq.di.viewModel.ViewModelProviderFactory
import kotlinx.android.synthetic.main.contact_list_fragment.*
import javax.inject.Inject

class ContactListFragment : BaseFragment() {

    @Inject
    lateinit var vmProviderFactory: ViewModelProviderFactory

    private lateinit var viewModel: ContactListViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.contact_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this, vmProviderFactory)
            .get(ContactListViewModel::class.java)


        val adapter = ContactListAdapter()
        rv_contacts.layoutManager = LinearLayoutManager(context)
        rv_contacts.adapter = adapter

        viewModel.contacts.observe(this) {
            adapter.updateItems(it)
        }

        viewModel.retrieveContacts()

        btnNext.setOnClickListener { navigateToDetail() }
    }

    fun navigateToDetail() {
        val action = ContactListFragmentDirections.actionMainFragmentToDetailFragment()
        this.findNavController().navigate(action)
    }
}
