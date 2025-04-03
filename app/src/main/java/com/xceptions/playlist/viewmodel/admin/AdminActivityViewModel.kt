package com.xceptions.playlist.viewmodel.admin

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.xceptions.playlist.views.admin.AddSongFragment

class AdminActivityViewModel:ViewModel() {


    private val _activeFragment = MutableLiveData<Fragment>(AddSongFragment())
    val activeFragment: LiveData<Fragment> = _activeFragment

    private val _activeNavItemId = MutableLiveData<Int>()
    val activeItemId : LiveData<Int> = _activeNavItemId

    init {
        _activeNavItemId.value = -1
    }

    fun setActiveFragment(fragment: Fragment,navItemId:Int){
        _activeFragment.value = fragment
        _activeNavItemId.value = navItemId
    }
}