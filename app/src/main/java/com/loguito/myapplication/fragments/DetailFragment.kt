package com.loguito.myapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.loguito.myapplication.R
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : Fragment() {
    private val args: DetailFragmentArgs by navArgs()

    //TODO: Paso 3 Agregar resto fragmentos
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navigation1Button.setOnClickListener {
            val action = DetailFragmentDirections.actionDetailFragmentToNav1Fragment(args.user.email)
            findNavController().navigate(action)
        }

        navigation2Button.setOnClickListener {
            val action = DetailFragmentDirections.actionDetailFragmentToNav2Fragment()
            findNavController().navigate(action)
        }

        textView.text = args.user.email
    }
}