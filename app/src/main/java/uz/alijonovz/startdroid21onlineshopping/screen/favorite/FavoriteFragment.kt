package uz.alijonovz.startdroid21onlineshopping.screen.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import uz.alijonovz.startdroid21onlineshopping.adapter.ProductAdapter
import uz.alijonovz.startdroid21onlineshopping.databinding.FragmentFavoriteBinding
import uz.alijonovz.startdroid21onlineshopping.screen.MainViewModel
import uz.alijonovz.startdroid21onlineshopping.utils.PrefUtils

class FavoriteFragment : Fragment() {

    lateinit var binding: FragmentFavoriteBinding
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.topProductData.observe(this, Observer{
            binding.recyclerView.adapter = ProductAdapter(it)
        })

        viewModel.error.observe(this, Observer{
            Toast.makeText(requireActivity(), it, Toast.LENGTH_LONG).show()
        })

        viewModel.progress.observe(this, Observer {
            binding.swipe.isRefreshing = it
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())

        binding.swipe.setOnRefreshListener{
            loadData()
        }
        loadData()
    }

    fun loadData(){
        viewModel.loadFavProductsByIds(PrefUtils.getFavoriteList())
    }

    companion object {
        @JvmStatic
        fun newInstance() = FavoriteFragment()
    }
}