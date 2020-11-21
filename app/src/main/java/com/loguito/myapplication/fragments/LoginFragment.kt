package com.loguito.myapplication.fragments

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.jakewharton.rxbinding4.view.clicks
import com.jakewharton.rxbinding4.widget.textChanges
import com.loguito.myapplication.R
import com.loguito.myapplication.models.User
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.functions.BiFunction
import kotlinx.android.synthetic.main.fragment_login.*
import java.util.concurrent.TimeUnit

class LoginFragment : Fragment() {
    private val disposables = CompositeDisposable()
    //TODO: Paso 1 Crear fragmento
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onDestroyView() {
        disposables.clear()
        super.onDestroyView()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val emailChanged = emailEditText.textChanges()
            .skipInitialValue()
            .debounce(400, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())

        disposables.add(
            emailChanged
                .subscribe {
                    textInputLayout.error =
                        if (TextUtils.isEmpty(it)) "Campo requerido" else null
                }
        )

        val passwordChanged = passwordEditText.textChanges()
            .skipInitialValue()
            .debounce(400, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())

        disposables.add(
            Observable.combineLatest(emailChanged, passwordChanged,
                { email, password -> email.isNotEmpty() && password.isNotEmpty() })
                .subscribe {
                    loginButton.isEnabled = it
                }
        )

        disposables.add(
            loginButton.clicks()
                .throttleFirst(1000, TimeUnit.MILLISECONDS)
                .subscribe {
                    Log.d("TEST", "Clicked!")
                }
        )

    }
}