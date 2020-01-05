package com.brandontm.reliq.ui.main.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.brandontm.reliq.R
import com.brandontm.reliq.base.BaseFragment
import com.brandontm.reliq.di.viewModel.ViewModelProviderFactory
import kotlinx.android.synthetic.main.friends_list_fragment.*
import javax.inject.Inject

class FriendsListFragment : BaseFragment() {

    @Inject
    lateinit var vmProviderFactory: ViewModelProviderFactory

    private lateinit var viewModel: FriendsListViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.friends_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this, vmProviderFactory)
            .get(FriendsListViewModel::class.java)

        btnNext.setOnClickListener { navigateToDetail() }
    }

    fun navigateToDetail() {
        val action = FriendsListFragmentDirections.actionMainFragmentToDetailFragment()
        this.findNavController().navigate(action)
    }
}
