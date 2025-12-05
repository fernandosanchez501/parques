package com.mgh.pmdm.parques.view.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.mgh.pmdm.parques.R
import com.mgh.pmdm.parques.databinding.FragmentSecondBinding
import com.mgh.pmdm.parques.model.Park
import com.mgh.pmdm.parques.model.Parks
import com.mgh.pmdm.parques.view.dialogs.MiDialogFragment

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment(), MiDialogFragment.OnOKOrCancelListener {

    ////val args: SecondFragmentArgs by navArgs()

private var _binding: FragmentSecondBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    // Propiedad para guardar temporalmente el parque que
    //  se está editando. Se utiliza cuando se guarda el parque.
    private var CurrentPark: Park?

    init{
        // Inicialización del parque actual
        CurrentPark=null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)

        populateSpinner()

        // Obtenemos el Park mediante arguments
        val park: Park? = arguments?.getSerializable("park") as Park?

        // ImageView, de momento fija
        binding.Image.setImageResource(R.drawable.appimg)

        park?.also { currentPark ->
            // Guardamos el objeto original


            // Actualizamos las vistas de la interfaz


            currentPark.run {
                binding.Name.setText(name)
                binding.Description.setText(desc)
                binding.Phone.setText(phone)
                binding.webSite.setText(webiste)
            }


            // Actualización del valor de los spinners
            for (i in 0 until binding.SpinnerOpeningTime.adapter.count) {
                if (binding.SpinnerOpeningTime.adapter.getItem(i) == currentPark.openingTime) {
                    binding.SpinnerOpeningTime.setSelection(i)
                    break
                }
            }

            for (i in 0 until binding.SpinnerClosingTime.adapter.count) {
                if (binding.SpinnerClosingTime.adapter.getItem(i) == currentPark.closingTime) {
                    binding.SpinnerClosingTime.setSelection(i)
                    break
                }
            }

            // Actualización de los Checkboxes
            currentPark.run {
                binding.cbSport.isChecked = currentPark.sports ?: false
                binding.cbChildren.isChecked = currentPark.children ?: false
                binding.cbBar.isChecked = currentPark.Bar ?: false
                binding.cbMascotas.isChecked = currentPark.Pets ?: false
            }
        }

        // Gestor del evento onClick sobre el botón de guardar
        binding.btSave.setOnClickListener {
            val miDialogo = MiDialogFragment(
                resources.getString(R.string.SaveData),
                resources.getString(R.string.AskSaveData),
                this
            )
            miDialogo.show(requireActivity().supportFragmentManager, "miDialogo")
        }

        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }*/
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    /*********************************
     * Métodos específicos de la clase
     **********************************/

    // Método específico para poblar el spinner para la hora de cierre
    // (la hora de apertura se ha hecho mediante el XML)
    private fun populateSpinner() {
        // Creamos un ArrayAdapter a partir de un recurso de tipo array
        // Requiere tres parámetros: El contexto, el recurso, y el
        // diseño de la entrada (utilizaremos el proporcionado por la
        // propia plataforma.
        ArrayAdapter.createFromResource(
            requireActivity(),
            R.array.horas,
            android.R.layout.simple_spinner_dropdown_item
        ).also { adapter ->
            // Si tenemos el adaptador preparado, seleccionamos el diseño
            // para la lista de opciones (lo proporcionado por la plataforma)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
            // Y finalmente, añadimos el adaptador al spinner
            // Al id del cual accedemos a través del binding
            binding.SpinnerClosingTime.adapter = adapter
        }
    }

    /******************************+++++++++++++++++++++++++++++++++++++++*
     * Implementación de la interfaz MiDialogFragment.OnOKOrCancelListener
     **********************************************************************/

    override fun onPositiveClick() {

        // Creamos un nuevo parque con la información del formulario
        val newPark= Park(
            binding.Name.text.toString(),
            binding.Description.text.toString(),
            binding.Phone.text.toString(),
            binding.webSite.text.toString(),
            binding.SpinnerOpeningTime.selectedItem.toString(),
            binding.SpinnerClosingTime.selectedItem.toString(),
            binding.cbSport.isChecked,
            binding.cbChildren.isChecked,
            binding.cbBar.isChecked,
            binding.cbMascotas.isChecked
        )

        // Comprobamos si currentPark existe o es nulo

        // Si existe, deberemos modificarlo
        if(CurrentPark!=null) Parks.replace(CurrentPark as Park, newPark)
        // Si no, deberemos añadirlo
        else Parks.add(newPark)

        // Actualizamos el CurrentPark con una copia del parque nuevo
        // Si se vuelven a modificar y guardar cambios, aunque hubiésemos
        // creado de nuevo el parque, se tratará como una modificación
        CurrentPark=newPark.copy()

        // Finalmente, mostramos un snackbar con la confirmación
        Snackbar.make(binding.root, resources.getString(R.string.dataSaved), Snackbar.LENGTH_LONG)
    }

    override fun onCancelClick() {
        // Si pulsa cancelar mostramos un toast
        Toast.makeText(
            requireActivity().applicationContext,
            resources.getString(R.string.actionCancelled), Toast.LENGTH_SHORT
        ).show()
    }



}