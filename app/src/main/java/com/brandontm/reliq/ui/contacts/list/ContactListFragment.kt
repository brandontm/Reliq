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
import com.brandontm.reliq.data.model.entities.Contact
import com.brandontm.reliq.data.model.entities.Result
import com.brandontm.reliq.data.model.entities.User
import com.brandontm.reliq.di.viewModel.ViewModelProviderFactory
import kotlinx.android.synthetic.main.contact_list_fragment.*
import javax.inject.Inject

class ContactListFragment : BaseFragment() {

    @Inject
    lateinit var vmProviderFactory: ViewModelProviderFactory

    private lateinit var viewModel: ContactListViewModel

    private val adapter = ContactListAdapter()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.contact_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this, vmProviderFactory)
            .get(ContactListViewModel::class.java)



        viewModel.user.observe(this) { // TODO: Move session to other activity/fragment
            setupContactsRecyclerView()

            viewModel.retrieveContacts()

            viewModel.contacts.observe(this) {
                swipe_contacts_refresh.isRefreshing = (it is Result.Loading)

                when(it) {
                    is Result.Success -> {
                        adapter.updateItems(it.data)
                    }
                }

            }

            btn_next.setOnClickListener { navigateToDetail() }
        }
    }

    private fun setupContactsRecyclerView() {
        rv_contacts.layoutManager = LinearLayoutManager(context)
        rv_contacts.adapter = adapter

        swipe_contacts_refresh.setOnRefreshListener {
            viewModel.retrieveContacts()
        }
    }

    private fun navigateToDetail() {
        val action = ContactListFragmentDirections.actionMainFragmentToDetailFragment()
        this.findNavController().navigate(action)
    }
}
