package com.mgh.pmdm.parques.view.dialogs

import android.R
import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment

class MiDialogFragment(
    var title: String ="Título por defecto",
    var content: String ="Contenido por defecto",
    var frag: Fragment
) : DialogFragment() {

        interface OnOKOrCancelListener {
            fun onPositiveClick()
            fun onCancelClick()
        }

        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder: AlertDialog.Builder = AlertDialog.Builder(requireActivity())

            builder.setTitle(title).setMessage(content)
                .setPositiveButton(R.string.ok) { _, _ ->
                    //val listener = activity as OnOKOrCancelListener?
                    val listener = frag as OnOKOrCancelListener?
                    listener!!.onPositiveClick()
                }
                .setNegativeButton(R.string.cancel) { _, _ ->
                    //val listener = activity as OnOKOrCancelListener?
                    val listener = frag as OnOKOrCancelListener?
                    listener!!.onCancelClick()
                }

            return builder.create() // Devuelve un AlertDialog, tal y como lo hemos definido al Builder
        } ?: throw IllegalStateException("El fragmento no está asociado a ninguna actividad")
    }
}