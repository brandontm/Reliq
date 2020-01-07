package com.brandontm.reliq.ui.contacts.list

import com.brandontm.reliq.base.BaseViewModel
import com.brandontm.reliq.data.repository.UserRepository
import javax.inject.Inject

class ContactListViewModel @Inject constructor(val userRepository: UserRepository) : BaseViewModel() {

}
