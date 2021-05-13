package com.culturetrip.article.ui.articles

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Database
import com.culturetrip.article.data.local.AppDatabase
import com.culturetrip.article.databinding.FragmentArticlesShowBinding
import com.culturetrip.article.util.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

private const val TAG = "ArticlesShowFragment"

@AndroidEntryPoint
class ArticlesShowFragment : Fragment() {

    private var binding: FragmentArticlesShowBinding by autoCleared()
    private val viewModel: ArticleViewModel by viewModels()
    private lateinit var adapter: ArticleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (shouldInterceptBackPress()) {
                    viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                        withContext(Dispatchers.IO) {
                            clearDatabase()
                        }

                        requireActivity().finish()
                    }
                } else {
                    isEnabled = false
                    activity?.onBackPressed()
                }
            }
        })
    }

    fun shouldInterceptBackPress() = true

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentArticlesShowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycelerView()
        setupObserver()
    }

    private fun setupRecycelerView() {
        adapter = ArticleAdapter()
        binding.articlesRv.layoutManager = LinearLayoutManager(requireContext())
        binding.articlesRv.adapter = adapter
    }

    private fun setupObserver() {
        viewModel.article.observe(viewLifecycleOwner) {
            it.let {
                adapter.setItems(it)
            }
        }
    }

    fun Fragment.clearDatabase() {
        AppDatabase.getDatabase(requireActivity()).clearAllTables()
    }

}