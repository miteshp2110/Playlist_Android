package com.xceptions.playlist.viewmodel.user

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.xceptions.playlist.R
import com.xceptions.playlist.views.admin.AddSongFragment

class UserActivityViewModel:ViewModel() {


    var activeItemId : Int? = null

    init {
        activeItemId = R.id.nav_home
    }

    fun setActiveFragment( navItemId:Int){
        activeItemId = navItemId
    }
}