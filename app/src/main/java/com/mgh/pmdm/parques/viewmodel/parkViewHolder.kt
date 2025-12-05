package com.mgh.pmdm.parques.viewmodel

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mgh.pmdm.parques.R
import com.mgh.pmdm.parques.model.Park

class parkViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    // Propiedades del Viewholder que guardarán referencias a sus vistas
    val name = itemView.findViewById(R.id.parkName) as TextView
    val desc = itemView.findViewById(R.id.parkDesc) as TextView

    // Enlazamos los datos del contacto con la vista, y con sus gestores de eventos
    fun bind(parque: Park,
             eventListenerClick: (Park, View) -> Unit,
             eventListenerLongClick: (Park, View) -> Boolean) {
        name.text = parque.name
        desc.text = parque.desc

        itemView.setOnClickListener{ eventListenerClick(parque, itemView) }
        itemView.setOnLongClickListener{ eventListenerLongClick(parque, itemView) }


    }
}
