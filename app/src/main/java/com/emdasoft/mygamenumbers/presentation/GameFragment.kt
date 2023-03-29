package com.emdasoft.mygamenumbers.presentation

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.emdasoft.mygamenumbers.R
import com.emdasoft.mygamenumbers.databinding.FragmentGameBinding
import com.emdasoft.mygamenumbers.domain.entity.GameResult
import com.emdasoft.mygamenumbers.domain.entity.Level
import com.emdasoft.mygamenumbers.domain.entity.Question

class GameFragment : Fragment() {

    private lateinit var level: Level

    private val viewModelFactory by lazy {
        GameViewModelFactory(level, requireActivity().application)
    }

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[GameViewModel::class.java]
    }

    private val tvOptions by lazy {
        mutableListOf<TextView>().apply {
            add(binding.tvOption1)
            add(binding.tvOption2)
            add(binding.tvOption3)
            add(binding.tvOption4)
            add(binding.tvOption5)
            add(binding.tvOption6)
        }
    }

    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw RuntimeException("FragmentGameBinding = null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOptionsClickListeners()

        viewModelObserve()

    }

    private fun parseArgs() {
        requireArguments().getParcelable<Level>(KEY_LEVEL)?.let {
            level = it
        }
    }

    private fun viewModelObserve() {
        viewModel.question.observe(viewLifecycleOwner) {
            showQuestion(it)
        }

        viewModel.progressAnswers.observe(viewLifecycleOwner) {
            binding.tvAnswersProgress.text = it
        }

        viewModel.formattedTime.observe(viewLifecycleOwner) {
            binding.tvTimer.text = it
        }

        viewModel.percentOfRightAnswers.observe(viewLifecycleOwner) {
            binding.barAnswersProgress.setProgress(it, true)
        }

        viewModel.enoughCount.observe(viewLifecycleOwner) {
            val color = getColorByState(it)
            binding.tvAnswersProgress.setTextColor(color)
        }

        viewModel.enoughPercent.observe(viewLifecycleOwner) {
            val color = getColorByState(it)
            binding.barAnswersProgress.progressTintList = ColorStateList.valueOf(color)
        }

        viewModel.minPercent.observe(viewLifecycleOwner) {
            binding.barAnswersProgress.secondaryProgress = it
        }

        viewModel.gameResult.observe(viewLifecycleOwner) {
            launchResultFragment(it)
        }

    }

    private fun showQuestion(it: Question) {
        with(binding) {
            tvSum.text = it.sum.toString()
            tvLeftNumber.text = it.visibleNumber.toString()
            for (i in 0 until tvOptions.size) {
                tvOptions[i].text = it.options[i].toString()
            }
        }
    }

    private fun launchResultFragment(it: GameResult) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, ResultFragment.newInstance(it))
            .addToBackStack(null)
            .commit()
    }

    private fun getColorByState(it: Boolean): Int {
        val colorResId = if (it) {
            android.R.color.holo_green_light
        } else {
            android.R.color.holo_red_light
        }
        return ContextCompat.getColor(requireContext(), colorResId)
    }

    private fun setOptionsClickListeners() {
        for (tvOption in tvOptions) {
            tvOption.setOnClickListener {
                viewModel.chooseAnswer(tvOption.text.toString().toInt())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        private const val KEY_LEVEL = "level"
        const val FRAGMENT_NAME = "GameFragment"

        @JvmStatic
        fun newInstance(level: Level): GameFragment {
            return GameFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_LEVEL, level)
                }
            }
        }
    }
}