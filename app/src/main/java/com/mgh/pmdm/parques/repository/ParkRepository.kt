package com.mgh.pmdm.parques.repository

import android.content.Context
import com.mgh.pmdm.parques.R
import com.mgh.pmdm.parques.model.Park
import com.mgh.pmdm.parques.model.Parks

class ParkRepository private constructor(
    private var context: Context
){
    init {
        Parks.populate(context, R.raw.parks)
    }
    companion object{
        private var INSTANCE: ParkRepository?=null
        fun getInscance(context: Context): ParkRepository{
            if (INSTANCE==null){
                INSTANCE= ParkRepository(context)
            }
            return INSTANCE!!
        }
    }

    fun getParks()= Parks.parks
    fun getNUmberParks()= Parks.parks.size
    fun removePark(p: Park)= Parks.remove(p)
}