package com.brandontm.reliq.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.brandontm.reliq.di.viewModel.ViewModelProviderFactory
import com.brandontm.reliq.ui.main.list.FriendsListViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class BaseFragment: DaggerFragment() {

}
