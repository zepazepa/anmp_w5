package com.example.studentanmpw3.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.studentanmpw3.R
import com.example.studentanmpw3.databinding.FragmentStudentListBinding
import com.example.studentanmpw3.viewmodel.ListViewModel
import java.util.zip.Inflater

class StudentListFragment : Fragment() {
    private lateinit var binding:FragmentStudentListBinding
    private val adapter = StudentListAdapter(arrayListOf())
    private lateinit var viewModel: ListViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // init viewModel
        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        viewModel.refresh()

        //init recycle view
        binding.recView.layoutManager = LinearLayoutManager(context)
        binding.recView.adapter = adapter

        //implement swipe refresh
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.refresh()
            binding.swipeRefresh.isRefreshing = false

        }

        observeViewModel()
    }
    fun observeViewModel(){
        viewModel.studentsLD.observe(viewLifecycleOwner,Observer{
            adapter.updateStudentList(it)
        })
        viewModel.loadingLD.observe(viewLifecycleOwner,Observer{
            if(it == true){
                binding.progressLoad.visibility = View.VISIBLE
            }
            else{
                binding.progressLoad.visibility = View.GONE
            }
        })
        viewModel.errorLD.observe(viewLifecycleOwner,Observer{
            if(it == true){
                binding.txtError.visibility = View.VISIBLE
            }
            else{
                binding.txtError.visibility = View.GONE
            }
        })
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentStudentListBinding.inflate(inflater,container,false)
        return binding.root
    }

}