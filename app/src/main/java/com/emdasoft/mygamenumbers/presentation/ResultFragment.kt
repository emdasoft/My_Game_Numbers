package com.emdasoft.mygamenumbers.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.emdasoft.mygamenumbers.R
import com.emdasoft.mygamenumbers.databinding.FragmentResultBinding
import com.emdasoft.mygamenumbers.domain.entity.GameResult

class ResultFragment : Fragment() {

    private lateinit var result: GameResult

    private var _binding: FragmentResultBinding? = null
    private val binding: FragmentResultBinding
        get() = _binding ?: throw RuntimeException("FragmentResultBinding = null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListeners()
        with(binding) {
            imageSmile.setImageResource(getSmileResId())
            tvRequiredAnswer.text = String.format(
                getString(R.string.tv_required_answer),
                result.gameSettings.minCountOfRightAnswers
            )
            tvScoreAnswer.text =
                String.format(getString(R.string.tv_score_answer), result.countOfRightAnswers)
            tvRequiredPercent.text = String.format(
                getString(R.string.tv_required_percent),
                result.gameSettings.minPercentOfRightAnswers
            )
            tvScorePercent.text = String.format(
                getString(R.string.tv_score_percent), getPercentOfRightAnswers()
            )
        }
    }

    private fun getSmileResId(): Int {
        return if (result.winner) {
            R.drawable.fun_smile
        } else {
            R.drawable.sad_smile
        }
    }

    private fun getPercentOfRightAnswers() = with(result) {
        if (countOfQuestions == 0) {
            0
        } else {
            ((countOfRightAnswers / countOfQuestions.toDouble()) * 100).toInt()
        }
    }

    private fun parseArgs() {
        requireArguments().getParcelable<GameResult>(KEY_RESULT)?.let {
            result = it
        }
    }

    private fun setOnClickListeners() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                retryGame()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
        binding.retryButton.setOnClickListener {
            retryGame()
        }
    }

    private fun retryGame() {
        requireActivity().supportFragmentManager.popBackStack(
            GameFragment.FRAGMENT_NAME,
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    companion object {

        private const val KEY_RESULT = "result"

        @JvmStatic
        fun newInstance(result: GameResult): ResultFragment {
            return ResultFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_RESULT, result)
                }
            }
        }
    }
}