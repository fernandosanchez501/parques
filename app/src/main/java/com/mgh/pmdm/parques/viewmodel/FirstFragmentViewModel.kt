package com.mgh.pmdm.parques.viewmodel

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.mgh.pmdm.parques.model.Park
import com.mgh.pmdm.parques.repository.ParkRepository

class FirstFragmentViewModel(application: Application): AndroidViewModel(application) {

    val parkLongClicked: MutableLiveData<Park> by lazy{
        MutableLiveData<Park>()
    }
    val parkClicked: MutableLiveData<Park> by lazy{
        MutableLiveData<Park>()
    }

    private val _adaptador= MutableLiveData<AdaptadorParques>().apply {
        value= AdaptadorParques(
            {park:Park,v: View -> parkClickedManager(park,v)},
            {park:Park,v:View -> parkLongClickedManager(park,v)},
            getApplication<Application>().applicationContext
        )
    }

    val adaptador=_adaptador

    private fun parkClickedManager(park: Park,v: View){
        parkClicked.value=park
    }

    private fun parkLongClickedManager(park: Park,v:View):Boolean{
        parkLongClicked.value=park
        return true
    }

    fun removePArk(park: Park):Int{
        val index= ParkRepository.getInscance(getApplication<Application>()
            .applicationContext).removePark(park)
        adaptador.value?.notifyItemRemoved(index)
        return index
    }
}