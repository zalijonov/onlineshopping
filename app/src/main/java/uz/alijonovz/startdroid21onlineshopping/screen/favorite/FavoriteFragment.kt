package uz.alijonovz.startdroid21onlineshopping.screen.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import uz.alijonovz.startdroid21onlineshopping.adapter.ProductAdapter
import uz.alijonovz.startdroid21onlineshopping.databinding.FragmentFavoriteBinding
import uz.alijonovz.startdroid21onlineshopping.screen.MainViewModel
import uz.alijonovz.startdroid21onlineshopping.utils.BaseFragment
import uz.alijonovz.startdroid21onlineshopping.utils.PrefUtils

class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>() {

    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.topProductData.observe(this, Observer {
            binding.recyclerView.adapter = ProductAdapter(it)
        })

        viewModel.error.observe(this, Observer {
            Toast.makeText(requireActivity(), it, Toast.LENGTH_LONG).show()
        })

        viewModel.progress.observe(this, Observer {
            binding.swipe.isRefreshing = it
        })
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFavoriteBinding {
        return FragmentFavoriteBinding.inflate(inflater, container, false)
    }

    override fun initView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())

        binding.swipe.setOnRefreshListener {
            loadData()
        }
    }

    override fun loadData() {
        viewModel.loadFavProductsByIds(PrefUtils.getFavoriteList())
    }

    override fun loadUpdate() {

    }

    companion object {
        @JvmStatic
        fun newInstance() = FavoriteFragment()
    }
}